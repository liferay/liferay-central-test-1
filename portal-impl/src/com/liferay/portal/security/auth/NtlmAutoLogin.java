/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.ldap.PortalLDAPImporterUtil;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Farache
 */
public class NtlmAutoLogin implements AutoLogin {

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		String[] credentials = null;

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!LDAPSettingsUtil.isNtlmEnabled(companyId)) {
				return credentials;
			}

			String screenName = (String)request.getAttribute(
				WebKeys.NTLM_REMOTE_USER);

			if (screenName == null) {
				return credentials;
			}

			request.removeAttribute(WebKeys.NTLM_REMOTE_USER);

			User user = getUser(companyId, screenName);

			if (user != null) {
				String redirect = ParamUtil.getString(request, "redirect");

				if (Validator.isNotNull(redirect)) {
					request.setAttribute(
						AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE, redirect);
				}

				credentials = new String[3];

				credentials[0] = String.valueOf(user.getUserId());
				credentials[1] = user.getPassword();
				credentials[2] = Boolean.TRUE.toString();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return credentials;
	}

	protected User getUser(long companyId, String screenName) throws Exception {
		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, screenName);

		SearchResult result = (SearchResult)PortalLDAPUtil.getUser(
			ldapServerId, companyId, screenName);

		if (result == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No user was found in LDAP with screenName " + screenName);
			}

			return null;
		}

		LdapContext ctx = PortalLDAPUtil.getContext(ldapServerId, companyId);

		User user = PortalLDAPImporterUtil.importLDAPUser(
			ldapServerId, companyId, ctx, result.getAttributes(),
			StringPool.BLANK);

		ctx.close();

		return user;
	}

	private static Log _log = LogFactoryUtil.getLog(NtlmAutoLogin.class);

}