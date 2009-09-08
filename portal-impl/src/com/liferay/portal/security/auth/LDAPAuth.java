/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * <a href="LDAPAuth.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 */
public class LDAPAuth implements Authenticator {

	public static final String AUTH_METHOD_BIND = "bind";

	public static final String AUTH_METHOD_PASSWORD_COMPARE =
		"password-compare";

	public static final String RESULT_PASSWORD_RESET =
		"2.16.840.1.113730.3.4.4";

	public static final String RESULT_PASSWORD_EXP_WARNING =
		"2.16.840.1.113730.3.4.5";

	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, emailAddress, StringPool.BLANK, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	public int authenticateByScreenName(
			long companyId, String screenName, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, screenName, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	public int authenticateByUserId(
			long companyId, long userId, String password,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, StringPool.BLANK, userId,
				password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	protected int authenticate(
			long companyId, String emailAddress, String screenName, long userId,
			String password)
		throws Exception {

		if (!LDAPSettingsUtil.isAuthEnabled(companyId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Authenticator is not enabled");
			}

			return SUCCESS;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Authenticator is enabled");
		}

		LdapContext ctx = PortalLDAPUtil.getContext(companyId);

		if (ctx == null) {
			return authenticateRequired(
				companyId, userId, emailAddress, screenName, true, FAILURE);
		}

		try {
			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN);

			//  Process LDAP auth search filter

			String filter = LDAPSettingsUtil.getAuthSearchFilter(
				companyId, emailAddress, screenName, String.valueOf(userId));

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			NamingEnumeration<SearchResult> enu = ctx.search(
				baseDN, filter, cons);

			if (enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				SearchResult result = enu.nextElement();

				String fullUserDN = PortalLDAPUtil.getNameInNamespace(
					companyId, result);

				Attributes attrs = PortalLDAPUtil.getUserAttributes(
					companyId, ctx, fullUserDN);

				LDAPAuthResult ldapAuthResult = authenticate(
					ctx, companyId, attrs, fullUserDN, password);

				// Process LDAP failure codes

				String errorMessage = ldapAuthResult.getErrorMessage();

				if (errorMessage != null) {
					if (errorMessage.indexOf(PrefsPropsUtil.getString(
							companyId, PropsKeys.LDAP_ERROR_USER_LOCKOUT))
								!= -1) {

						throw new UserLockoutException();
					}
					else if (errorMessage.indexOf(PrefsPropsUtil.getString(
						companyId, PropsKeys.LDAP_ERROR_PASSWORD_EXPIRED))
							!= -1) {

						throw new PasswordExpiredException();
					}
				}

				if (!ldapAuthResult.isAuthenticated()) {
					return authenticateRequired(
						companyId, userId, emailAddress, screenName, false,
						FAILURE);
				}

				// Get user or create from LDAP

				User user = PortalLDAPUtil.importLDAPUser(
					companyId, ctx, attrs, password, true);

				// Process LDAP success codes

				String resultCode = ldapAuthResult.getResponseControl();

				if (resultCode.equals(LDAPAuth.RESULT_PASSWORD_RESET)) {
					UserLocalServiceUtil.updatePasswordReset(
						user.getUserId(), true);
				}
				else if (
					resultCode.equals(LDAPAuth.RESULT_PASSWORD_EXP_WARNING)) {

					UserLocalServiceUtil.updatePasswordReset(
						user.getUserId(), true);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter did not return any results");
				}

				return authenticateRequired(
					companyId, userId, emailAddress, screenName, true, DNE);
			}

			enu.close();
		}
		catch (Exception e) {
			_log.error("Problem accessing LDAP server: " + e.getMessage());

			int authResult = authenticateRequired(
				companyId, userId, emailAddress, screenName, true, FAILURE);

			if (authResult == FAILURE) {
				throw e;
			}
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}

		return SUCCESS;
	}

	protected LDAPAuthResult authenticate(
			LdapContext ctx, long companyId, Attributes attrs, String userDN,
			String password)
		throws Exception {

		LDAPAuthResult ldapAuthResult = new LDAPAuthResult();

		// Check passwords by either doing a comparison between the passwords or
		// by binding to the LDAP server. If using LDAP password policies, bind
		// auth method must be used in order to get the result control codes.

		String authMethod = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_AUTH_METHOD);
		InitialLdapContext innerCtx = null;

		if (authMethod.equals(AUTH_METHOD_BIND)) {
			try {
				Hashtable<String, Object> env =
					(Hashtable<String, Object>)ctx.getEnvironment();

				env.put(Context.SECURITY_PRINCIPAL, userDN);
				env.put(Context.SECURITY_CREDENTIALS, password);
				env.put(
					Context.REFERRAL,
					PrefsPropsUtil.getString(
						companyId, PropsKeys.LDAP_REFERRAL));

				// Do not use pooling because principal changes

				env.put("com.sun.jndi.ldap.connect.pool", "false");

				innerCtx = new InitialLdapContext(env, null);

				// Get LDAP bind results

				Control[] responseControls =  innerCtx.getResponseControls();

				ldapAuthResult.setAuthenticated(true);
				ldapAuthResult.setResponseControl(responseControls);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Failed to bind to the LDAP server with userDN "
							+ userDN + " and password " + password);
				}

				_log.error(
					"Failed to bind to the LDAP server: " + e.getMessage());

				ldapAuthResult.setAuthenticated(false);
				ldapAuthResult.setErrorMessage(e.getMessage());
			}
			finally {
				if (innerCtx != null) {
					innerCtx.close();
				}
			}
		}
		else if (authMethod.equals(AUTH_METHOD_PASSWORD_COMPARE)) {
			Attribute userPassword = attrs.get("userPassword");

			if (userPassword != null) {
				String ldapPassword = new String((byte[])userPassword.get());

				String encryptedPassword = password;

				String algorithm = PrefsPropsUtil.getString(
					companyId,
					PropsKeys.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM);

				if (Validator.isNotNull(algorithm)) {
					encryptedPassword =
						"{" + algorithm + "}" +
							PwdEncryptor.encrypt(
								algorithm, password, ldapPassword);
				}

				if (ldapPassword.equals(encryptedPassword)) {
					ldapAuthResult.setAuthenticated(true);
				}
				else {
					ldapAuthResult.setAuthenticated(false);

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Passwords do not match for userDN " + userDN);
					}
				}
			}
		}

		return ldapAuthResult;
	}

	protected int authenticateOmniadmin(
			long companyId, String emailAddress, String screenName, long userId)
		throws Exception {

		// Only allow omniadmin if Liferay password checking is enabled

		if (PropsValues.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK) {
			if (userId > 0) {
				if (OmniadminUtil.isOmniadmin(userId)) {
					return SUCCESS;
				}
			}
			else if (Validator.isNotNull(emailAddress)) {
				try {
					User user = UserLocalServiceUtil.getUserByEmailAddress(
						companyId, emailAddress);

					if (OmniadminUtil.isOmniadmin(user.getUserId())) {
						return SUCCESS;
					}
				}
				catch (NoSuchUserException nsue) {
				}
			}
			else if (Validator.isNotNull(screenName)) {
				try {
					User user = UserLocalServiceUtil.getUserByScreenName(
						companyId, screenName);

					if (OmniadminUtil.isOmniadmin(user.getUserId())) {
						return SUCCESS;
					}
				}
				catch (NoSuchUserException nsue) {
				}
			}
		}

		return FAILURE;
	}

	protected int authenticateRequired(
			long companyId, long userId, String emailAddress, String screenName,
			boolean allowOmniadmin, int failureCode)
		throws Exception {

		// Make exceptions for omniadmins so that if they break the LDAP
		// configuration, they can still login to fix the problem

		if (allowOmniadmin &&
			(authenticateOmniadmin(
				companyId, emailAddress, screenName, userId) == SUCCESS)) {

			return SUCCESS;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_REQUIRED)) {

			return failureCode;
		}
		else {
			return SUCCESS;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LDAPAuth.class);

}