/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.struts;

import com.liferay.portal.LayoutPermissionException;
import com.liferay.portal.PortletActiveException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.UserActiveException;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.UserTrackerPathUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletInstanceFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.RenderResponseImpl;

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

/**
 * <a href="PortalRequestProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class PortalRequestProcessor extends TilesRequestProcessor {

	public PortalRequestProcessor() {

		// auth.forward.last.path.

		_lastPaths = new HashSet<String>();

		_lastPaths.add(_PATH_PORTAL_LAYOUT);

		addPaths(_lastPaths, PropsKeys.AUTH_FORWARD_LAST_PATHS);

		// auth.public.path.

		_publicPaths = new HashSet<String>();

		_publicPaths.add(_PATH_C);
		_publicPaths.add(_PATH_PORTAL_CSS);
		_publicPaths.add(_PATH_PORTAL_CSS_CACHED);
		_publicPaths.add(_PATH_PORTAL_FLASH);
		_publicPaths.add(_PATH_PORTAL_J_LOGIN);
		_publicPaths.add(_PATH_PORTAL_JAVASCRIPT);
		_publicPaths.add(_PATH_PORTAL_JAVASCRIPT_CACHED);
		_publicPaths.add(_PATH_PORTAL_LAYOUT);
		_publicPaths.add(_PATH_PORTAL_LOGIN);
		_publicPaths.add(_PATH_PORTAL_LOGIN_CAPTCHA);
		_publicPaths.add(_PATH_PORTAL_RENDER_PORTLET);
		_publicPaths.add(_PATH_PORTAL_TCK);

		addPaths(_publicPaths, PropsKeys.AUTH_PUBLIC_PATHS);

		_trackerIgnorePaths = new HashSet<String>();

		addPaths(_trackerIgnorePaths, PropsKeys.SESSION_TRACKER_IGNORE_PATHS);
	}

	public void process(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String path = super.processPath(request, response);

		ActionMapping mapping = (ActionMapping)moduleConfig.findActionConfig(
			path);

		if (mapping == null) {
			String lastPath = getLastPath(request);

			if (_log.isDebugEnabled()) {
				_log.debug("Last path " + lastPath);
			}

			response.sendRedirect(lastPath);

			return;
		}

		super.process(request, response);

		try {
			if (isPortletPath(path)) {
				cleanUp(request);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void addPaths(Set<String> paths, String propsKey) {
		String[] pathsArray = PropsUtil.getArray(propsKey);

		for (String path : pathsArray) {
			paths.add(path);
		}
	}

	protected void callParentDoForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		super.doForward(uri, request, response);
	}

	protected HttpServletRequest callParentProcessMultipart(
		HttpServletRequest request) {

		return super.processMultipart(request);
	}

	protected String callParentProcessPath(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return super.processPath(request, response);
	}

	protected boolean callParentProcessRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping)
		throws IOException, ServletException {

		return super.processRoles(request, response, mapping);
	}

	protected void cleanUp(HttpServletRequest request) throws Exception {

		// Clean up portlet objects that may have been created by defineObjects
		// for portlets that are called directly from a Struts path

		RenderRequestImpl renderRequestImpl =
			(RenderRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (renderRequestImpl != null) {
			RenderRequestFactory.recycle(renderRequestImpl);
		}

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (renderResponseImpl != null) {
			RenderResponseFactory.recycle(renderResponseImpl);
		}
	}

	protected void defineObjects(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		String portletId = portlet.getPortletId();

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactory.create(
			portlet, servletContext);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		RenderRequestImpl renderRequestImpl = RenderRequestFactory.create(
			request, portlet, invokerPortlet, portletContext,
			WindowState.MAXIMIZED, PortletMode.VIEW, portletPreferences);

		RenderResponseImpl renderResponseImpl = RenderResponseFactory.create(
			renderRequestImpl, response, portletId, portlet.getCompanyId());

		renderRequestImpl.defineObjects(portletConfig, renderResponseImpl);

		request.setAttribute(WebKeys.PORTLET_STRUTS_EXECUTE, Boolean.TRUE);
	}

	protected void doForward(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws ServletException {

		StrutsUtil.forward(uri, getServletContext(), request, response);
	}

	protected void doInclude(
			String uri, HttpServletRequest request,
			HttpServletResponse response)
		throws ServletException {

		StrutsUtil.include(uri, getServletContext(), request, response);
	}

	protected StringBuilder getFriendlyTrackerPath(
			String path, ThemeDisplay themeDisplay, HttpServletRequest request)
		throws Exception {

		if (!path.equals(_PATH_PORTAL_LAYOUT)) {
			return null;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid == 0) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		String layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(
			layout, themeDisplay);

		StringBuilder sb = new StringBuilder();

		sb.append(layoutFriendlyURL);

		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return sb;
		}

		long companyId = PortalUtil.getCompanyId(request);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			String strutsPath = path.substring(
				1, path.lastIndexOf(StringPool.SLASH));

			portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
				companyId, strutsPath);
		}

		if ((portlet == null) || !portlet.isActive()) {
			sb.append(StringPool.QUESTION);
			sb.append(request.getQueryString());

			return sb;
		}

		String namespace = PortalUtil.getPortletNamespace(portletId);

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if (friendlyURLMapper == null) {
			sb.append(StringPool.QUESTION);
			sb.append(request.getQueryString());

			return sb;
		}

		PortletURLImpl portletURL = new PortletURLImpl(
			request, portletId, plid, PortletRequest.RENDER_PHASE);

		Iterator<Map.Entry<String, String[]>> itr =
			request.getParameterMap().entrySet().iterator();

		while (itr.hasNext()) {
			Entry<String, String[]> entry = itr.next();

			String key = entry.getKey();

			if (key.startsWith(namespace)) {
				key = key.substring(namespace.length());

				portletURL.setParameter(key, entry.getValue());
			}
		}

		String portletFriendlyURL = friendlyURLMapper.buildPath(portletURL);

		if (portletFriendlyURL != null) {
			sb.append(portletFriendlyURL);
		}
		else {
			sb.append(StringPool.QUESTION);
			sb.append(request.getQueryString());
		}

		return sb;
	}

	protected String getLastPath(HttpServletRequest request) {
		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean httpsInitial = (Boolean)session.getAttribute(
			WebKeys.HTTPS_INITIAL);

		String portalURL = null;

		if ((PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS) &&
			(httpsInitial != null) && (!httpsInitial.booleanValue())) {

			portalURL = PortalUtil.getPortalURL(request, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(request);
		}

		StringBuilder sb = new StringBuilder();

		sb.append(portalURL);
		sb.append(themeDisplay.getPathMain());
		sb.append(_PATH_PORTAL_LAYOUT);

		if (!PropsValues.AUTH_FORWARD_BY_LAST_PATH) {
			if (request.getRemoteUser() != null) {

				// If we do not forward by last path and the user is logged in,
				// forward to the user's default layout to prevent a lagging
				// loop

				sb.append(StringPool.QUESTION);
				sb.append("p_l_id");
				sb.append(StringPool.EQUAL);
				sb.append(LayoutConstants.DEFAULT_PLID);
			}

			return sb.toString();
		}

		LastPath lastPath = (LastPath)session.getAttribute(WebKeys.LAST_PATH);

		if (lastPath == null) {
			return sb.toString();
		}

		Map<String, String[]> parameterMap = lastPath.getParameterMap();

		// Only test for existing mappings for last paths that were set when the
		// user accessed a layout directly instead of through its friendly URL

		if (lastPath.getContextPath().equals(themeDisplay.getPathMain())) {
			ActionMapping mapping =
				(ActionMapping)moduleConfig.findActionConfig(
					lastPath.getPath());

			if ((mapping == null) || (parameterMap == null)) {
				return sb.toString();
			}
		}

		StringBuilder lastPathSB = new StringBuilder();

		lastPathSB.append(portalURL);
		lastPathSB.append(lastPath.getContextPath());
		lastPathSB.append(lastPath.getPath());
		lastPathSB.append(HttpUtil.parameterMapToString(parameterMap));

		return lastPathSB.toString();
	}

	protected boolean isPortletPath(String path) {
		if ((path != null) &&
			(!path.equals(_PATH_C)) &&
			(!path.startsWith(_PATH_COMMON)) &&
			(path.indexOf(_PATH_J_SECURITY_CHECK) == -1) &&
			(!path.startsWith(_PATH_PORTAL))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isPublicPath(String path) {
		if ((path != null) &&
			(_publicPaths.contains(path)) ||
			(path.startsWith(_PATH_COMMON))) {

			return true;
		}
		else {
			return false;
		}
	}

	protected ActionMapping processMapping(
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException {

		if (path == null) {
			return null;
		}

		ActionMapping mapping = super.processMapping(request, response, path);

		if (mapping == null) {
			String msg = getInternal().getMessage("processInvalid");

			_log.error("User ID " + request.getRemoteUser());
			_log.error("Current URL " + PortalUtil.getCurrentURL(request));
			_log.error("Referer " + request.getHeader("Referer"));
			_log.error("Remote address " + request.getRemoteAddr());

			_log.error(msg + " " + path);
		}

		return mapping;
	}

	protected HttpServletRequest processMultipart(HttpServletRequest request) {

		// Disable Struts from automatically wrapping a multipart request

		return request;
	}

	protected String processPath(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String path = super.processPath(request, response);

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		// Current users

		UserTracker userTracker = LiveUsers.getUserTracker(
			themeDisplay.getCompanyId(), session.getId());

		if ((userTracker != null) && (path != null) &&
			(!path.equals(_PATH_C)) &&
			(path.indexOf(_PATH_J_SECURITY_CHECK) == -1) &&
			(path.indexOf(_PATH_PORTAL_PROTECTED) == -1) &&
			(!_trackerIgnorePaths.contains(path))) {

			StringBuilder sb = null;

			try {
				if (PropsValues.SESSION_TRACKER_FRIENDLY_PATHS_ENABLED) {
					sb = getFriendlyTrackerPath(path, themeDisplay, request);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (sb == null) {
				sb = new StringBuilder();

				sb.append(path);
				sb.append(StringPool.QUESTION);
				sb.append(request.getQueryString());
			}

			UserTrackerPath userTrackerPath = UserTrackerPathUtil.create(0);

			userTrackerPath.setUserTrackerId(userTracker.getUserTrackerId());
			userTrackerPath.setPath(sb.toString());
			userTrackerPath.setPathDate(new Date());

			userTracker.addPath(userTrackerPath);
		}

		String remoteUser = request.getRemoteUser();

		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		// Last path

		if ((path != null) && (_lastPaths.contains(path)) &&
			(!_trackerIgnorePaths.contains(path))) {

			boolean saveLastPath = ParamUtil.getBoolean(
				request, "saveLastPath", true);

			if (themeDisplay.isLifecycleResource() ||
				themeDisplay.isStateExclusive() ||
				themeDisplay.isStatePopUp() ||
				!request.getMethod().equalsIgnoreCase(HttpMethods.GET)) {

				saveLastPath = false;
			}

			// Save last path

			if (saveLastPath) {

				// Was a last path set by another servlet that dispatched to
				// the MainServlet? If so, use that last path instead.

				LastPath lastPath = (LastPath)request.getAttribute(
					WebKeys.LAST_PATH);

				if (lastPath == null) {
					lastPath = new LastPath(
						themeDisplay.getPathMain(), path,
						request.getParameterMap());
				}

				session.setAttribute(WebKeys.LAST_PATH, lastPath);
			}
		}

		// Authenticated users can always log out

		if (((remoteUser != null) || (user != null)) && (path != null) &&
			(path.equals(_PATH_PORTAL_LOGOUT))) {

			return path;
		}

		// Authenticated users can always extend or confirm their session

		if (((remoteUser != null) || (user != null)) && (path != null) &&
			(path.equals(_PATH_PORTAL_EXPIRE_SESSION) ||
			 path.equals(_PATH_PORTAL_EXTEND_SESSION))) {

			return path;
		}

		// Authenticated users can retrieve CSS and JavaScript

		if (((remoteUser != null) || (user != null)) && (path != null) &&
			(path.equals(_PATH_PORTAL_CSS) ||
			 path.equals(_PATH_PORTAL_CSS_CACHED) ||
			 path.equals(_PATH_PORTAL_JAVASCRIPT) ||
			 path.equals(_PATH_PORTAL_JAVASCRIPT_CACHED))) {

			return path;
		}

		// Authenticated users can always agree to terms of use

		if (((remoteUser != null) || (user != null)) && (path != null) &&
			(path.equals(_PATH_PORTAL_UPDATE_TERMS_OF_USE))) {

			return path;
		}

		// Authenticated users must still exist in the system

		if ((remoteUser != null) && (user == null)) {
			return _PATH_PORTAL_LOGOUT;
		}

		// Authenticated users should agree to Terms of Use

		if ((user != null) && !user.isAgreedToTermsOfUse()) {
			if (PropsValues.TERMS_OF_USE_REQUIRED) {
				return _PATH_PORTAL_TERMS_OF_USE;
			}
		}

		// Authenticated users must be active

		if ((user != null) && !user.isActive()) {
			SessionErrors.add(request, UserActiveException.class.getName());

			return _PATH_PORTAL_ERROR;
		}

		// Authenticated users must have a current password

		if ((user != null) && user.isPasswordReset()) {
			return _PATH_PORTAL_UPDATE_PASSWORD;
		}

		// Authenticated users should have a reminder query

		if ((user != null) &&
			(Validator.isNull(user.getReminderQueryQuestion()) ||
			 Validator.isNull(user.getReminderQueryAnswer()))) {

			if (PropsValues.USERS_REMINDER_QUERIES_ENABLED) {
				return _PATH_PORTAL_UPDATE_REMINDER_QUERY;
			}
		}

		// Authenticated users must have at least one personalized page

		if (user != null) {
			List<Layout> layouts = themeDisplay.getLayouts();

			if ((layouts == null) || (layouts.size() == 0)) {
				SessionErrors.add(
					request, RequiredLayoutException.class.getName());

				return _PATH_PORTAL_ERROR;
			}
		}

		// Users must sign in

		if (!isPublicPath(path)) {
			if (user == null) {
				SessionErrors.add(request, PrincipalException.class.getName());

				return _PATH_PORTAL_LOGIN;
			}
		}

		ActionMapping mapping =
			(ActionMapping)moduleConfig.findActionConfig(path);

		path = mapping.getPath();

		// Authenticated users must have at least one role

		if (user != null) {
			try {

				// FIX ME

				if (false) {
					SessionErrors.add(
						request, RequiredRoleException.class.getName());

					return _PATH_PORTAL_ERROR;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Define the portlet objects

		if (isPortletPath(path)) {
			try {
				Portlet portlet = null;

				long companyId = PortalUtil.getCompanyId(request);
				String portletId = ParamUtil.getString(request, "p_p_id");

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						companyId, portletId);
				}

				if (portlet == null) {
					String strutsPath = path.substring(
						1, path.lastIndexOf(StringPool.SLASH));

					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						companyId, strutsPath);
				}

				if ((portlet != null) && portlet.isActive()) {
					defineObjects(request, response, portlet);
				}
			}
			catch (Exception e) {
				request.setAttribute(PageContext.EXCEPTION, e);

				path = _PATH_COMMON_ERROR;
			}
		}

		// Authenticated users must have access to at least one layout

		if (SessionErrors.contains(
				request, LayoutPermissionException.class.getName())) {

			return _PATH_PORTAL_ERROR;
		}

		return path;
	}

	protected boolean processRoles(
			HttpServletRequest request, HttpServletResponse response,
			ActionMapping mapping)
		throws IOException, ServletException {

		String path = mapping.getPath();

		if (isPublicPath(path)) {
			return true;
		}

		boolean authorized = true;

		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		if ((user != null) && isPortletPath(path)) {
			try {

				// Authenticated users can always log out

				if (path.equals(_PATH_PORTAL_LOGOUT)) {
					return true;
				}

				Portlet portlet = null;

				String portletId = ParamUtil.getString(request, "p_p_id");

				if (Validator.isNotNull(portletId)) {
					portlet = PortletLocalServiceUtil.getPortletById(
						user.getCompanyId(), portletId);
				}

				String strutsPath = path.substring(
					1, path.lastIndexOf(StringPool.SLASH));

				if (portlet != null) {
					if (!strutsPath.equals(portlet.getStrutsPath())) {
						throw new PrincipalException();
					}
				}
				else {
					portlet = PortletLocalServiceUtil.getPortletByStrutsPath(
						user.getCompanyId(), strutsPath);
				}

				if ((portlet != null) && portlet.isActive()) {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)request.getAttribute(
							WebKeys.THEME_DISPLAY);

					Layout layout = themeDisplay.getLayout();
					PermissionChecker permissionChecker =
						themeDisplay.getPermissionChecker();

					if (!PortletPermissionUtil.contains(
							permissionChecker, layout.getPlid(), portlet,
							ActionKeys.VIEW)) {

						throw new PrincipalException();
					}
				}
				else if (portlet != null && !portlet.isActive()) {
					SessionErrors.add(
						request, PortletActiveException.class.getName());

					authorized = false;
				}
			}
			catch (Exception e) {
				SessionErrors.add(request, PrincipalException.class.getName());

				authorized = false;
			}
		}

		if (!authorized) {
			ForwardConfig forwardConfig =
				mapping.findForward(_PATH_PORTAL_ERROR);

			processForwardConfig(request, response, forwardConfig);

			return false;
		}
		else {
			return true;
		}
	}

	private static String _PATH_C = "/c";

	private static String _PATH_COMMON = "/common";

	private static String _PATH_COMMON_ERROR = "/common/error";

	private static String _PATH_J_SECURITY_CHECK = "/j_security_check";

	private static String _PATH_PORTAL = "/portal";

	private static String _PATH_PORTAL_CSS = "/portal/css";

	private static String _PATH_PORTAL_CSS_CACHED = "/portal/css_cached";

	private static String _PATH_PORTAL_ERROR = "/portal/error";

	private static String _PATH_PORTAL_EXPIRE_SESSION =
		"/portal/expire_session";

	private static String _PATH_PORTAL_EXTEND_SESSION =
		"/portal/extend_session";

	private static String _PATH_PORTAL_FLASH = "/portal/flash";

	private static String _PATH_PORTAL_J_LOGIN = "/portal/j_login";

	private static String _PATH_PORTAL_JAVASCRIPT = "/portal/javascript";

	private static String _PATH_PORTAL_JAVASCRIPT_CACHED =
		"/portal/javascript_cached";

	private static String _PATH_PORTAL_LAYOUT = "/portal/layout";

	private static String _PATH_PORTAL_LOGIN = "/portal/login";

	private static String _PATH_PORTAL_LOGIN_CAPTCHA = "/portal/login_captcha";

	private static String _PATH_PORTAL_LOGOUT = "/portal/logout";

	private static String _PATH_PORTAL_PROTECTED = "/portal/protected";

	private static String _PATH_PORTAL_RENDER_PORTLET =
		"/portal/render_portlet";

	private static String _PATH_PORTAL_TCK = "/portal/tck";

	private static String _PATH_PORTAL_TERMS_OF_USE = "/portal/terms_of_use";

	private static String _PATH_PORTAL_UPDATE_PASSWORD =
		"/portal/update_password";

	private static String _PATH_PORTAL_UPDATE_REMINDER_QUERY =
		"/portal/update_reminder_query";

	private static String _PATH_PORTAL_UPDATE_TERMS_OF_USE =
		"/portal/update_terms_of_use";

	private static Log _log = LogFactory.getLog(PortalRequestProcessor.class);

	private Set<String> _lastPaths;
	private Set<String> _publicPaths;
	private Set<String> _trackerIgnorePaths;

}