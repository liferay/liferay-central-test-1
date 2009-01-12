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

package com.liferay.portlet.login.util;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.LastPath;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.CookieKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.util.Encryptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LoginUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class LoginUtil {

	public static String getLogin(
			HttpServletRequest request, String paramName, Company company)
		throws SystemException {

		String login = request.getParameter(paramName);

		if ((login == null) || (login.equals(StringPool.NULL))) {
			login = GetterUtil.getString(
				CookieKeys.getCookie(request, CookieKeys.LOGIN));

			if (PropsValues.COMPANY_LOGIN_PREPOPULATE_DOMAIN &&
				Validator.isNull(login) &&
				company.getAuthType().equals(CompanyConstants.AUTH_TYPE_EA)) {

				login = "@" + company.getMx();
			}
		}

		return login;
	}

	public static PortletURL getLoginURL(
			HttpServletRequest request, long plid)
		throws PortletModeException, WindowStateException {

		PortletURL portletURL = new PortletURLImpl(
			request, PortletKeys.LOGIN, plid, PortletRequest.RENDER_PHASE);

		portletURL.setWindowState(WindowState.MAXIMIZED);
		portletURL.setPortletMode(PortletMode.VIEW);

		portletURL.setParameter("saveLastPath", "0");
		portletURL.setParameter("struts_action", "/login/login");

		return portletURL;
	}

	public static void login(
			HttpServletRequest request, HttpServletResponse response,
			String login, String password, boolean rememberMe, String authType)
		throws Exception {

		CookieKeys.validateSupportCookie(request);

		HttpSession session = request.getSession();

		long userId = GetterUtil.getLong(login);

		int authResult = Authenticator.FAILURE;

		Company company = PortalUtil.getCompany(request);

		Map<String, String[]> headerMap = new HashMap<String, String[]>();

		Enumeration<String> enu1 = request.getHeaderNames();

		while (enu1.hasMoreElements()) {
			String name = enu1.nextElement();

			Enumeration<String> enu2 = request.getHeaders(name);

			List<String> headers = new ArrayList<String>();

			while (enu2.hasMoreElements()) {
				String value = enu2.nextElement();

				headers.add(value);
			}

			headerMap.put(name, headers.toArray(new String[headers.size()]));
		}

		Map<String, String[]> parameterMap = request.getParameterMap();

		if (Validator.isNull(authType)) {
			authType = company.getAuthType();
		}

		if (authType.equals(CompanyConstants.AUTH_TYPE_EA)) {
			authResult = UserLocalServiceUtil.authenticateByEmailAddress(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);

			userId = UserLocalServiceUtil.getUserIdByEmailAddress(
				company.getCompanyId(), login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
			authResult = UserLocalServiceUtil.authenticateByScreenName(
				company.getCompanyId(), login, password, headerMap,
				parameterMap);

			userId = UserLocalServiceUtil.getUserIdByScreenName(
				company.getCompanyId(), login);
		}
		else if (authType.equals(CompanyConstants.AUTH_TYPE_ID)) {
			authResult = UserLocalServiceUtil.authenticateByUserId(
				company.getCompanyId(), userId, password, headerMap,
				parameterMap);
		}

		if (authResult == Authenticator.SUCCESS) {
			if (PropsValues.SESSION_ENABLE_PHISHING_PROTECTION) {

				// Invalidate the previous session to prevent phishing

				Boolean httpsInitial = (Boolean)session.getAttribute(
					WebKeys.HTTPS_INITIAL);

				LastPath lastPath = (LastPath)session.getAttribute(
					WebKeys.LAST_PATH);

				try {
					session.invalidate();
				}
				catch (IllegalStateException ise) {

					// This only happens in Geronimo

					if (_log.isWarnEnabled()) {
						_log.warn(ise.getMessage());
					}
				}

				session = request.getSession(true);

				if (httpsInitial != null) {
					session.setAttribute(WebKeys.HTTPS_INITIAL, httpsInitial);
				}

				if (lastPath != null) {
					session.setAttribute(WebKeys.LAST_PATH, lastPath);
				}
			}

			// Set cookies

			String domain = CookieKeys.getDomain(request);

			User user = UserLocalServiceUtil.getUserById(userId);

			String userIdString = String.valueOf(userId);

			session.setAttribute("j_username", userIdString);
			session.setAttribute("j_password", user.getPassword());
			session.setAttribute("j_remoteuser", userIdString);

			session.setAttribute(WebKeys.USER_PASSWORD, password);

			Cookie companyIdCookie = new Cookie(
				CookieKeys.COMPANY_ID, String.valueOf(company.getCompanyId()));

			if (Validator.isNotNull(domain)) {
				companyIdCookie.setDomain(domain);
			}

			companyIdCookie.setPath(StringPool.SLASH);

			Cookie idCookie = new Cookie(
				CookieKeys.ID,
				UserLocalServiceUtil.encryptUserId(userIdString));

			if (Validator.isNotNull(domain)) {
				idCookie.setDomain(domain);
			}

			idCookie.setPath(StringPool.SLASH);

			Cookie passwordCookie = new Cookie(
				CookieKeys.PASSWORD,
				Encryptor.encrypt(company.getKeyObj(), password));

			if (Validator.isNotNull(domain)) {
				passwordCookie.setDomain(domain);
			}

			passwordCookie.setPath(StringPool.SLASH);

			Cookie rememberMeCookie = new Cookie(
				CookieKeys.REMEMBER_ME, Boolean.TRUE.toString());

			if (Validator.isNotNull(domain)) {
				rememberMeCookie.setDomain(domain);
			}

			rememberMeCookie.setPath(StringPool.SLASH);

			int loginMaxAge = PropsValues.COMPANY_SECURITY_AUTO_LOGIN_MAX_AGE;

			if (PropsValues.SESSION_DISABLED) {
				rememberMe = true;
			}

			if (rememberMe) {
				companyIdCookie.setMaxAge(loginMaxAge);
				idCookie.setMaxAge(loginMaxAge);
				passwordCookie.setMaxAge(loginMaxAge);
				rememberMeCookie.setMaxAge(loginMaxAge);
			}
			else {

				// This was explicitly changed from 0 to -1 so that the cookie
				// lasts as long as the browser. This allows an external servlet
				// wrapped in AutoLoginFilter to work throughout the client
				// connection. The cookies ARE removed on an actual logout, so
				// there is no security issue. See LEP-4678 and LEP-5177.

				companyIdCookie.setMaxAge(-1);
				idCookie.setMaxAge(-1);
				passwordCookie.setMaxAge(-1);
				rememberMeCookie.setMaxAge(0);
			}

			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, login);

			if (Validator.isNotNull(domain)) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(loginMaxAge);
			loginCookie.setPath(StringPool.SLASH);

			Cookie screenNameCookie = new Cookie(
				CookieKeys.SCREEN_NAME,
				Encryptor.encrypt(company.getKeyObj(), user.getScreenName()));

			if (Validator.isNotNull(domain)) {
				screenNameCookie.setDomain(domain);
			}

			screenNameCookie.setMaxAge(loginMaxAge);
			screenNameCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, companyIdCookie, false);
			CookieKeys.addCookie(request, response, idCookie, false);
			CookieKeys.addCookie(request, response, passwordCookie, false);
			CookieKeys.addCookie(request, response, rememberMeCookie, false);
			CookieKeys.addCookie(request, response, loginCookie, false);
			CookieKeys.addCookie(request, response, screenNameCookie, false);
		}
		else {
			throw new AuthException();
		}
	}

	public static void sendPassword(ActionRequest actionRequest)
		throws Exception {

		sendPassword(actionRequest, null, null, null, null);
	}

	public static void sendPassword(
			ActionRequest actionRequest, String fromName,
			String fromAddress, String subject, String body)
		throws Exception {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Company company = themeDisplay.getCompany();

		if (!company.isSendPassword()) {
			return;
		}

		String emailAddress = ParamUtil.getString(request, "emailAddress");

		String remoteAddr = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		UserLocalServiceUtil.sendPassword(
			company.getCompanyId(), emailAddress, remoteAddr, remoteHost,
			userAgent, fromName, fromAddress, subject, body);

		SessionMessages.add(actionRequest, "request_processed", emailAddress);
	}

	private static Log _log = LogFactory.getLog(LoginUtil.class);

}