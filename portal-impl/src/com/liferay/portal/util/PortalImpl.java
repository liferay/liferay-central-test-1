/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchCompanyException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperThreadLocal;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.servlet.filters.i18n.I18nFilter;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.comparator.PortletControlPanelWeightComparator;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.ControlPanelEntry;
import com.liferay.portlet.DefaultControlPanelEntryFactory;
import com.liferay.portlet.PortletConfigFactory;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesWrapper;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.UserAttributes;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.expando.action.EditExpandoAction;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.Encryptor;
import com.liferay.util.JS;
import com.liferay.util.UniqueList;
import com.liferay.util.servlet.DynamicServletRequest;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.portlet.filter.PortletRequestWrapper;
import javax.portlet.filter.PortletResponseWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;

/**
 * <a href="PortalImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 * @author Raymond Augé
 * @author Eduardo Lundgren
 * @author Wesley Gong
 */
public class PortalImpl implements Portal {

	public PortalImpl() {

		// Computer name

		_computerName = System.getProperty("env.COMPUTERNAME");

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOST");
		}

		if (Validator.isNull(_computerName)) {
			_computerName = System.getProperty("env.HOSTNAME");
		}

		if (Validator.isNull(_computerName)) {
			try {
				_computerName = InetAddress.getLocalHost().getHostName();
			}
			catch (UnknownHostException uhe) {
			}
		}

		try {
			_computerAddress = InetAddress.getByName(
				_computerName).getHostAddress();
		}
		catch (UnknownHostException uhe) {
		}

		if (Validator.isNull(_computerAddress)) {
			try {
				_computerAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (UnknownHostException uhe) {
			}
		}

		// Global lib directory

		_globalLibDir = ClassUtil.getParentPath(
			ReleaseInfo.class.getClassLoader(), ReleaseInfo.class.getName());

		int pos = _globalLibDir.lastIndexOf(".jar!");

		pos = _globalLibDir.lastIndexOf(StringPool.SLASH, pos);

		_globalLibDir = _globalLibDir.substring(0, pos + 1);

		if (_log.isInfoEnabled()) {
			_log.info("Global lib directory " + _globalLibDir);
		}

		// Portal lib directory

		ClassLoader classLoader = getClass().getClassLoader();

		_portalLibDir = WebDirDetector.getLibDir(classLoader);

		String portalLibDir = System.getProperty("liferay.lib.portal.dir");

		if (portalLibDir != null) {
			if (!portalLibDir.endsWith(StringPool.SLASH)) {
				portalLibDir += StringPool.SLASH;
			}

			_portalLibDir = portalLibDir;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Portal lib directory " + _portalLibDir);
		}

		_portalWebDir = WebDirDetector.getRootDir(_portalLibDir);

		if (_log.isDebugEnabled()) {
			_log.debug("Portal web directory " + _portalWebDir);
		}

		// CDN host

		_cdnHostHttp = PropsValues.CDN_HOST_HTTP;

		if (_cdnHostHttp.startsWith("${")) {
			_cdnHostHttp = StringPool.BLANK;
		}

		_cdnHostHttps = PropsValues.CDN_HOST_HTTPS;

		if (_cdnHostHttps.startsWith("${")) {
			_cdnHostHttps = StringPool.BLANK;
		}

		// Paths

		_pathContext = PropsUtil.get(PropsKeys.PORTAL_CTX);

		if (_pathContext.equals(StringPool.SLASH)) {
			_pathContext = StringPool.BLANK;
		}

		_pathFriendlyURLPrivateGroup =
			_pathContext + _PRIVATE_GROUP_SERVLET_MAPPING;
		_pathFriendlyURLPrivateUser =
			_pathContext + _PRIVATE_USER_SERVLET_MAPPING;
		_pathFriendlyURLPublic = _pathContext + _PUBLIC_GROUP_SERVLET_MAPPING;
		_pathImage = _pathContext + PATH_IMAGE;
		_pathMain = _pathContext + PATH_MAIN;

		// Groups

		String customSystemGroups[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_GROUPS);

		if ((customSystemGroups == null) || (customSystemGroups.length == 0)) {
			_allSystemGroups = GroupConstants.SYSTEM_GROUPS;
		}
		else {
			_allSystemGroups = ArrayUtil.append(
				GroupConstants.SYSTEM_GROUPS, customSystemGroups);
		}

		_sortedSystemGroups = new String[_allSystemGroups.length];

		System.arraycopy(
			_allSystemGroups, 0, _sortedSystemGroups, 0,
			_allSystemGroups.length);

		Arrays.sort(_sortedSystemGroups, new StringComparator());

		// Regular roles

		String customSystemRoles[] = PropsUtil.getArray(PropsKeys.SYSTEM_ROLES);

		if ((customSystemRoles == null) || (customSystemRoles.length == 0)) {
			_allSystemRoles = RoleConstants.SYSTEM_ROLES;
		}
		else {
			_allSystemRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_ROLES, customSystemRoles);
		}

		_sortedSystemRoles = new String[_allSystemRoles.length];

		System.arraycopy(
			_allSystemRoles, 0, _sortedSystemRoles, 0, _allSystemRoles.length);

		Arrays.sort(_sortedSystemRoles, new StringComparator());

		// Community roles

		String customSystemCommunityRoles[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_COMMUNITY_ROLES);

		if ((customSystemCommunityRoles == null) ||
			(customSystemCommunityRoles.length == 0)) {

			_allSystemCommunityRoles = RoleConstants.SYSTEM_COMMUNITY_ROLES;
		}
		else {
			_allSystemCommunityRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_COMMUNITY_ROLES,
				customSystemCommunityRoles);
		}

		_sortedSystemCommunityRoles =
			new String[_allSystemCommunityRoles.length];

		System.arraycopy(
			_allSystemCommunityRoles, 0, _sortedSystemCommunityRoles, 0,
				_allSystemCommunityRoles.length);

		Arrays.sort(_sortedSystemCommunityRoles, new StringComparator());

		// Organization Roles

		String customSystemOrganizationRoles[] =
			PropsUtil.getArray(PropsKeys.SYSTEM_ORGANIZATION_ROLES);

		if ((customSystemOrganizationRoles == null) ||
			(customSystemOrganizationRoles.length == 0)) {

			_allSystemOrganizationRoles =
				RoleConstants.SYSTEM_ORGANIZATION_ROLES;
		}
		else {
			_allSystemOrganizationRoles = ArrayUtil.append(
				RoleConstants.SYSTEM_ORGANIZATION_ROLES,
				customSystemOrganizationRoles);
		}

		_sortedSystemOrganizationRoles =
			new String[_allSystemOrganizationRoles.length];

		System.arraycopy(
			_allSystemOrganizationRoles, 0, _sortedSystemOrganizationRoles, 0,
				_allSystemOrganizationRoles.length);

		Arrays.sort(_sortedSystemOrganizationRoles, new StringComparator());

		// Reserved parameter names

		_reservedParams = new HashSet<String>();

		_reservedParams.add("p_l_id");
		_reservedParams.add("p_l_reset");
		_reservedParams.add("p_p_id");
		_reservedParams.add("p_p_lifecycle");
		_reservedParams.add("p_p_url_type");
		_reservedParams.add("p_p_state");
		_reservedParams.add("p_p_mode");
		_reservedParams.add("p_p_resource_id");
		_reservedParams.add("p_p_cacheability");
		_reservedParams.add("p_p_width");
		_reservedParams.add("p_p_col_id");
		_reservedParams.add("p_p_col_pos");
		_reservedParams.add("p_p_col_count");
		_reservedParams.add("p_p_static");
		_reservedParams.add("p_p_static");
		_reservedParams.add("p_p_isolated");
		_reservedParams.add("saveLastPath");
	}

	/**
	 * Adds the description for a page. This appends to the existing page
	 * description.
	 */
	public void addPageDescription(
		String description, HttpServletRequest request) {

		String requestDescription = (String)request.getAttribute(
			WebKeys.PAGE_DESCRIPTION);

		if (requestDescription != null) {
			description = requestDescription + StringPool.SPACE + description;
		}

		request.setAttribute(WebKeys.PAGE_DESCRIPTION, description);
	}

	/**
	 * Adds the keywords for a page. This appends to the existing page keywords.
	 */
	public void addPageKeywords(String keywords, HttpServletRequest request) {
		List<String> requestKeywords = (List<String>)request.getAttribute(
			WebKeys.PAGE_KEYWORDS);

		if (requestKeywords == null) {
			requestKeywords = new UniqueList<String>();
		}

		String[] keywordsArray = StringUtil.split(keywords);

		for (String keyword : keywordsArray) {
			if (!requestKeywords.contains(keyword.toLowerCase())) {
				requestKeywords.add(keyword.toLowerCase());
			}
		}

		request.setAttribute(WebKeys.PAGE_KEYWORDS, requestKeywords);
	}

	/**
	 * Adds the subtitle for a page. This appends to the existing page subtitle.
	 */
	public void addPageSubtitle(String subtitle, HttpServletRequest request) {
		String requestSubtitle = (String)request.getAttribute(
			WebKeys.PAGE_SUBTITLE);

		if (requestSubtitle != null) {
			subtitle = requestSubtitle + StringPool.SPACE + subtitle;
		}

		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	/**
	 * Adds the whole title for a page. This appends to the existing page whole
	 * title.
	 */
	public void addPageTitle(String title, HttpServletRequest request) {
		String requestTitle = (String)request.getAttribute(WebKeys.PAGE_TITLE);

		if (requestTitle != null) {
			title = requestTitle + StringPool.SPACE + title;
		}

		request.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	public void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		List<KeyValuePair> portletBreadcrumbList =
			(List<KeyValuePair>)request.getAttribute(
				WebKeys.PORTLET_BREADCRUMB_MAP);

		if (portletBreadcrumbList == null) {
			portletBreadcrumbList = new ArrayList<KeyValuePair>();

			request.setAttribute(
				WebKeys.PORTLET_BREADCRUMB_MAP, portletBreadcrumbList);
		}

		portletBreadcrumbList.add(new KeyValuePair(title, url));
	}

	public void clearRequestParameters(RenderRequest renderRequest) {

		// Clear the render parameters if they were set during processAction

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleAction()) {
			((RenderRequestImpl)renderRequest).getRenderParameters().clear();
		}
	}

	public void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)actionResponse;

			Map<String, String[]> renderParameters =
				actionResponseImpl.getRenderParameterMap();

			actionResponse.setRenderParameter("p_p_lifecycle", "1");

			Enumeration<String> enu = actionRequest.getParameterNames();

			while (enu.hasMoreElements()) {
				String param = enu.nextElement();
				String[] values = actionRequest.getParameterValues(param);

				if (renderParameters.get(
						actionResponseImpl.getNamespace() + param) == null) {

					actionResponse.setRenderParameter(param, values);
				}
			}
		}
		catch (IllegalStateException ise) {

			// This should only happen if the developer called
			// sendRedirect of javax.portlet.ActionResponse

		}
	}

	public String escapeRedirect(String url) {
		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return url;
		}

		try {
			String securityMode = PropsValues.REDIRECT_URL_SECURITY_MODE;

			String domain = StringUtil.split(
				HttpUtil.getDomain(url), StringPool.COLON)[0];

			if (securityMode.equals("domain")) {
				String[] allowedDomains =
					PropsValues.REDIRECT_URL_DOMAINS_ALLOWED;

				if ((allowedDomains.length > 0) &&
					!ArrayUtil.contains(allowedDomains, domain)) {

					url = null;
				}
			}
			else if (securityMode.equals("ip")) {
				String[] allowedIps = PropsValues.REDIRECT_URL_IPS_ALLOWED;

				InetAddress inetAddress = InetAddress.getByName(domain);

				if ((allowedIps.length > 0) &&
					!ArrayUtil.contains(
						allowedIps, inetAddress.getHostAddress())) {

					String serverIp = getComputerAddress();

					if (!serverIp.equals(inetAddress.getHostAddress()) ||
						!ArrayUtil.contains(allowedIps, "SERVER_IP")) {

						url = null;
					}
				}
			}
		}
		catch (UnknownHostException uhe) {
			url = null;
		}

		return url;
	}

	public BaseModel<?> getBaseModel(Resource resource)
		throws PortalException, SystemException {

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());

		String modelName = resourceCode.getName();
		String primKey = resource.getPrimKey();

		return getBaseModel(modelName, primKey);
	}

	public BaseModel<?> getBaseModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		String modelName = resourcePermission.getName();
		String primKey = resourcePermission.getPrimKey();

		return getBaseModel(modelName, primKey);
	}

	public BaseModel<?> getBaseModel(String modelName, String primKey)
		throws PortalException, SystemException {

		if (!modelName.contains(".model.")) {
			return null;
		}

		String[] parts = StringUtil.split(modelName, StringPool.PERIOD);

		if ((parts.length <= 2) || !parts[parts.length - 2].equals("model")) {
			return null;
		}

		parts[parts.length - 2] = "service";

		String serviceName =
			StringUtil.merge(parts, StringPool.PERIOD) + "LocalServiceUtil";
		String methodName = "get" + parts[parts.length - 1];

		Method method = null;

		try {
			Class<?> serviceUtil = Class.forName(serviceName);

			if (Validator.isNumber(primKey)) {
				method = serviceUtil.getMethod(
					methodName, new Class[] {Long.TYPE});

				return (BaseModel<?>)method.invoke(null, new Long(primKey));
			}
			else {
				method = serviceUtil.getMethod(
					methodName, new Class[] {String.class});

				return (BaseModel<?>)method.invoke(null, primKey);
			}
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof PortalException) {
				throw (PortalException)cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException)cause;
			}
			else {
				throw new SystemException(cause);
			}
		}
	}

	public long getBasicAuthUserId(HttpServletRequest request)
		throws PortalException, SystemException {

		long companyId = PortalInstances.getCompanyId(request);

		return getBasicAuthUserId(request, companyId);
	}

	public long getBasicAuthUserId(HttpServletRequest request, long companyId)
		throws PortalException, SystemException {

		long userId = 0;

		String authorizationHeader = request.getHeader(
			HttpHeaders.AUTHORIZATION);

		if (Validator.isNull(authorizationHeader)) {
			return userId;
		}

		String[] authorizationArray = authorizationHeader.split("\\s+");

		String authorization = authorizationArray[0];
		String credentials = new String(Base64.decode(authorizationArray[1]));

		if (!authorization.equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
			return userId;
		}

		String[] loginAndPassword = StringUtil.split(
			credentials, StringPool.COLON);

		String login = loginAndPassword[0].trim();

		String password = null;

		if (loginAndPassword.length > 1) {
			password = loginAndPassword[1].trim();
		}

		// Strip @uid and @sn for backwards compatibility

		if (login.endsWith("@uid")) {
			int pos = login.indexOf("@uid");

			login = login.substring(0, pos);
		}
		else if (login.endsWith("@sn")) {
			int pos = login.indexOf("@sn");

			login = login.substring(0, pos);
		}

		// Try every authentication type

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_EA, login, password);

		if (userId > 0) {
			return userId;
		}

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_SN, login, password);

		if (userId > 0) {
			return userId;
		}

		userId = UserLocalServiceUtil.authenticateForBasic(
			companyId, CompanyConstants.AUTH_TYPE_ID, login, password);

		return userId;
	}

	/**
	 * @deprecated {@link #getCDNHost(boolean)}
	 */
	public String getCDNHost() {
		return getCDNHostHttp();
	}

	public String getCDNHost(boolean secure) {
		if (secure) {
			return getCDNHostHttps();
		}
		else {
			return getCDNHostHttp();
		}
	}

	public String getCDNHostHttp() {
		return _cdnHostHttp;
	}

	public String getCDNHostHttps() {
		return _cdnHostHttps;
	}

	public String getClassName(long classNameId) {
		try {
			ClassName className = ClassNameLocalServiceUtil.getClassName(
				classNameId);

			return className.getValue();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from id " + classNameId);
		}
	}

	public long getClassNameId(Class<?> classObj) {
		return ClassNameLocalServiceUtil.getClassNameId(classObj);
	}

	public long getClassNameId(String value) {
		return ClassNameLocalServiceUtil.getClassNameId(value);
	}

	public String getClassNamePortletId(String className) {
		String portletId = StringPool.BLANK;

		if (className.startsWith("com.liferay.portlet.blogs")) {
			portletId = PortletKeys.BLOGS;
		}
		else if (className.startsWith("com.liferay.portlet.bookmarks")) {
			portletId = PortletKeys.BOOKMARKS;
		}
		else if (className.startsWith("com.liferay.portlet.calendar")) {
			portletId = PortletKeys.CALENDAR;
		}
		else if (className.startsWith("com.liferay.portlet.documentlibrary")) {
			portletId = PortletKeys.DOCUMENT_LIBRARY;
		}
		else if (className.startsWith("com.liferay.portlet.imagegallery")) {
			portletId = PortletKeys.IMAGE_GALLERY;
		}
		else if (className.startsWith("com.liferay.portlet.journal")) {
			portletId = PortletKeys.JOURNAL;
		}
		else if (className.startsWith("com.liferay.portlet.messageboards")) {
			portletId = PortletKeys.MESSAGE_BOARDS;
		}
		else if (className.startsWith("com.liferay.portlet.wiki")) {
			portletId = PortletKeys.WIKI;
		}

		return portletId;
	}

	public String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		if (Validator.isNull(PropsValues.AUTH_LOGIN_COMMUNITY_URL)) {
			return null;
		}

		for (Layout layout : themeDisplay.getLayouts()) {
			if (layout.getFriendlyURL().equals(
					PropsValues.AUTH_LOGIN_COMMUNITY_URL)) {

				if (themeDisplay.getLayout() != null) {
					String layoutSetFriendlyURL = getLayoutSetFriendlyURL(
						themeDisplay.getLayout().getLayoutSet(), themeDisplay);

					return layoutSetFriendlyURL +
						PropsValues.AUTH_LOGIN_COMMUNITY_URL;
				}

				break;
			}
		}

		return null;
	}

	public String[] getCommunityPermissions(HttpServletRequest request) {
		return request.getParameterValues("communityPermissions");
	}

	public String[] getCommunityPermissions(PortletRequest portletRequest) {
		return portletRequest.getParameterValues("communityPermissions");
	}

	public Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		long companyId = getCompanyId(request);

		if (companyId <= 0) {
			return null;
		}

		Company company = (Company)request.getAttribute(WebKeys.COMPANY);

		if (company == null) {

			// LEP-5994

			try {
				company = CompanyLocalServiceUtil.getCompanyById(companyId);
			}
			catch (NoSuchCompanyException nsce) {
				company = CompanyLocalServiceUtil.getCompanyById(
					PortalInstances.getDefaultCompanyId());
			}

			request.setAttribute(WebKeys.COMPANY, company);
		}

		return company;
	}

	public Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getCompany(getHttpServletRequest(portletRequest));
	}

	public long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	public long getCompanyId(PortletRequest portletRequest) {
		return getCompanyId(getHttpServletRequest(portletRequest));
	}

	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	public String getComputerAddress() {
		return _computerAddress;
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		for (String category : PortletCategoryKeys.ALL) {
			List<Portlet> portlets = getControlPanelPortlets(
				category, themeDisplay);

			for (Portlet portlet : portlets) {
				if (portlet.getPortletId().equals(portletId)) {
					return category;
				}
			}
		}

		return StringPool.BLANK;
	}

	public String getControlPanelFullURL(
			long scopeGroupId, String ppid, Map<String, String[]> params)
		throws PortalException, SystemException {

		StringBundler sb = new StringBundler(6);

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		Company company = CompanyLocalServiceUtil.getCompany(
			group.getCompanyId());

		sb.append(
			getPortalURL(company.getVirtualHost(), getPortalPort(), false));
		sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
		sb.append(GroupConstants.CONTROL_PANEL_FRIENDLY_URL);
		sb.append(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		if (params != null) {
			params = new HashMap<String, String[]>(params);
		}
		else {
			params = new HashMap<String, String[]>();
		}

		params.put("p_p_id", new String[] {ppid});
		params.put("p_p_lifecycle", new String[] {"0"});
		params.put(
			"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

		sb.append(HttpUtil.parameterMapToString(params, true));

		return sb.toString();
	}

	public List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException {

		Set<Portlet> portletsSet = new TreeSet<Portlet>(
			new PortletControlPanelWeightComparator());

		List<Portlet> portletsList = PortletLocalServiceUtil.getPortlets(
			themeDisplay.getCompanyId());

		for (Portlet portlet : portletsList) {
			if (category.equals(portlet.getControlPanelEntryCategory())) {
				portletsSet.add(portlet);
			}
		}

		return filterControlPanelPortlets(portletsSet, category, themeDisplay);
	}

	public String getCurrentCompleteURL(HttpServletRequest request) {
		String currentCompleteURL = (String)request.getAttribute(
			WebKeys.CURRENT_COMPLETE_URL);

		if (currentCompleteURL == null) {
			currentCompleteURL = HttpUtil.getCompleteURL(request);

			request.setAttribute(
				WebKeys.CURRENT_COMPLETE_URL, currentCompleteURL);
		}

		return currentCompleteURL;
	}

	public String getCurrentURL(HttpServletRequest request) {
		String currentURL = (String)request.getAttribute(WebKeys.CURRENT_URL);

		if (currentURL == null) {
			currentURL = ParamUtil.getString(request, "currentURL");

			if (Validator.isNull(currentURL)) {
				currentURL = HttpUtil.getCompleteURL(request);

				if ((Validator.isNotNull(currentURL)) &&
					(currentURL.indexOf(_J_SECURITY_CHECK) == -1)) {

					currentURL = currentURL.substring(
						currentURL.indexOf(Http.PROTOCOL_DELIMITER) +
							Http.PROTOCOL_DELIMITER.length());

					currentURL = currentURL.substring(
						currentURL.indexOf(StringPool.SLASH));
				}

				if (Validator.isNotNull(currentURL) &&
					FacebookUtil.isFacebook(currentURL)) {

					String[] facebookData = FacebookUtil.getFacebookData(
						request);

					currentURL =
						FacebookUtil.FACEBOOK_APPS_URL + facebookData[0] +
							facebookData[2];
				}
			}

			if (Validator.isNull(currentURL)) {
				currentURL = getPathMain();
			}

			request.setAttribute(WebKeys.CURRENT_URL, currentURL);
		}

		return currentURL;
	}

	public String getCurrentURL(PortletRequest portletRequest) {
		return (String)portletRequest.getAttribute(WebKeys.CURRENT_URL);
	}

	public String getCustomSQLFunctionIsNotNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNOTNULL;
	}

	public String getCustomSQLFunctionIsNull() {
		return PropsValues.CUSTOM_SQL_FUNCTION_ISNULL;
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, hour, min, null, pe);
	}

	public Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		if (!Validator.isGregorianDate(month, day, year)) {
			throw pe;
		}
		else {
			Calendar cal = null;

			if (timeZone == null) {
				cal = CalendarFactoryUtil.getCalendar();
			}
			else {
				cal = CalendarFactoryUtil.getCalendar(timeZone);
			}

			if ((hour == -1) || (min == -1)) {
				cal.set(year, month, day, 0, 0, 0);
			}
			else {
				cal.set(year, month, day, hour, min, 0);
			}

			cal.set(Calendar.MILLISECOND, 0);

			Date date = cal.getTime();

			/*if (timeZone != null &&
				cal.before(CalendarFactoryUtil.getCalendar(timeZone))) {

				throw pe;
			}*/

			return date;
		}
	}

	public Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, null, pe);
	}

	public Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getDate(month, day, year, -1, -1, timeZone, pe);
	}

	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	public Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException {

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		List<String> names = new ArrayList<String>();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.indexOf("ExpandoAttributeName(") != -1) {
				String name = ParamUtil.getString(portletRequest, param);

				names.add(name);
			}
		}

		for (String name : names) {
			int type = expandoBridge.getAttributeType(name);

			Serializable value = EditExpandoAction.getValue(
				portletRequest, "ExpandoAttribute(" + name + ")", type);

			attributes.put(name, value);
		}

		return attributes;
	}

	public String getFirstPageLayoutTypes(PageContext pageContext) {
		StringBundler sb = new StringBundler();

		for (int i = 0; i < PropsValues.LAYOUT_TYPES.length; i++) {
			String type = PropsValues.LAYOUT_TYPES[i];

			if (isLayoutFirstPageable(type)) {
				sb.append(
					LanguageUtil.get(pageContext, "layout.types." + type));
				sb.append(StringPool.COMMA);
				sb.append(StringPool.SPACE);
			}
		}

		if (sb.index() >= 2) {
			sb.setIndex(sb.index() - 2);
		}

		return sb.toString();
	}

	public String getGlobalLibDir() {
		return _globalLibDir;
	}

	public String getGoogleGadgetURL(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return _getServletURL(
			portlet, PropsValues.GOOGLE_GADGET_SERVLET_MAPPING, themeDisplay);
	}

	public String[] getGuestPermissions(HttpServletRequest request) {
		String[] guestPermissions = request.getParameterValues(
			"guestPermissions");

		if (guestPermissions == null) {
			return null;
		}

		boolean inputPermissionsPublic = ParamUtil.getBoolean(
			request, "inputPermissionsPublic");
		boolean inputPermissionsShowConfigure = ParamUtil.getBoolean(
			request, "inputPermissionsShowConfigure");

		if (!inputPermissionsShowConfigure) {
			if (!inputPermissionsPublic) {
				guestPermissions = new String[0];
			}
			else if ((guestPermissions == null) ||
					 (guestPermissions.length == 0)) {

				guestPermissions = new String[] {ActionKeys.VIEW};
			}
		}

		return guestPermissions;
	}

	public String[] getGuestPermissions(PortletRequest portletRequest) {
		String[] guestPermissions = portletRequest.getParameterValues(
			"guestPermissions");

		if (guestPermissions == null) {
			return null;
		}

		boolean inputPermissionsPublic = ParamUtil.getBoolean(
			portletRequest, "inputPermissionsPublic");
		boolean inputPermissionsShowConfigure = ParamUtil.getBoolean(
			portletRequest, "inputPermissionsShowConfigure");

		if (!inputPermissionsShowConfigure) {
			if (!inputPermissionsPublic) {
				guestPermissions = new String[0];
			}
			else if ((guestPermissions == null) ||
					 (guestPermissions.length == 0)) {

				guestPermissions = new String[] {ActionKeys.VIEW};
			}
		}

		return guestPermissions;
	}

	public String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		String portalURL = getPortalURL(request);

		Company company = getCompany(request);

		String homeURL = company.getHomeURL();

		if (Validator.isNull(homeURL)) {
			homeURL = PropsValues.COMPANY_DEFAULT_HOME_URL;
		}

		return portalURL + _pathContext + homeURL;
	}

	public String getHost(HttpServletRequest request) {
		request = getOriginalServletRequest(request);

		String host = request.getHeader("Host");

		if (host != null) {
			host = host.trim().toLowerCase();

			int pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
		}
		else {
			host = null;
		}

		return host;
	}

	public String getHost(PortletRequest portletRequest) {
		return getHost(getHttpServletRequest(portletRequest));
	}

	public HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		if (portletRequest instanceof PortletRequestImpl) {
			PortletRequestImpl portletRequestImpl =
				(PortletRequestImpl)portletRequest;

			return portletRequestImpl.getHttpServletRequest();
		}
		else if (portletRequest instanceof PortletRequestWrapper) {
			PortletRequestWrapper portletRequestWrapper =
				(PortletRequestWrapper)portletRequest;

			return getHttpServletRequest(portletRequestWrapper.getRequest());
		}

		throw new RuntimeException(
			"Unable to get the HTTP servlet request from " +
				portletRequest.getClass().getName());
	}

	public HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		if (portletResponse instanceof ActionResponseImpl) {
			ActionResponseImpl actionResponseImpl =
				(ActionResponseImpl)portletResponse;

			return actionResponseImpl.getHttpServletResponse();
		}
		else if (portletResponse instanceof RenderResponseImpl) {
			RenderResponseImpl renderResponseImpl =
				(RenderResponseImpl)portletResponse;

			return renderResponseImpl.getHttpServletResponse();
		}
		else if (portletResponse instanceof PortletResponseWrapper) {
			PortletResponseWrapper portletResponseWrapper =
				(PortletResponseWrapper)portletResponse;

			return getHttpServletResponse(portletResponseWrapper.getResponse());
		}

		PortletResponseImpl portletResponseImpl =
			PortletResponseImpl.getPortletResponseImpl(portletResponse);

		return portletResponseImpl.getHttpServletResponse();
	}

	public String getJsSafePortletId(String portletId) {
		return JS.getSafeName(portletId);
	}

	public String getLayoutActualURL(Layout layout) {
		return getLayoutActualURL(layout, getPathMain());
	}

	public String getLayoutActualURL(Layout layout, String mainPath) {
		Map<String, String> variables = new HashMap<String, String>();

		variables.put("liferay:groupId", String.valueOf(layout.getGroupId()));
		variables.put("liferay:mainPath", mainPath);
		variables.put("liferay:plid", String.valueOf(layout.getPlid()));

		UnicodeProperties typeSettingsProperties =
			layout.getLayoutType().getTypeSettingsProperties();

		Iterator<Map.Entry<String, String>> itr =
			typeSettingsProperties.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();

			String key = entry.getKey();
			String value = entry.getValue();

			variables.put(key, value);
		}

		String href = PropsUtil.get(
			PropsKeys.LAYOUT_URL, new Filter(layout.getType(), variables));

		return href;
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, null);
	}

	public String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException {

		Layout layout = null;
		String queryString = StringPool.BLANK;

		if (Validator.isNull(friendlyURL)) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (layouts.size() > 0) {
				layout = layouts.get(0);
			}
			else {
				throw new NoSuchLayoutException(
					"{groupId=" + groupId + ",privateLayout=" + privateLayout +
						"} does not have any layouts");
			}
		}
		else {
			Object[] friendlyURLMapper = getPortletFriendlyURLMapper(
				groupId, privateLayout, friendlyURL, params);

			layout = (Layout)friendlyURLMapper[0];
			queryString = (String)friendlyURLMapper[1];
		}

		String layoutActualURL = getLayoutActualURL(layout, mainPath);

		if (Validator.isNotNull(queryString)) {
			layoutActualURL = layoutActualURL + queryString;
		}
		else if (params.isEmpty()) {
			UnicodeProperties typeSettingsProperties =
				layout.getLayoutType().getTypeSettingsProperties();

			queryString = typeSettingsProperties.getProperty("query-string");

			if (Validator.isNotNull(queryString) &&
				layoutActualURL.contains(StringPool.QUESTION)) {

				layoutActualURL =
					layoutActualURL + StringPool.AMPERSAND + queryString;
			}
		}

		return layoutActualURL;
	}

	public String getLayoutEditPage(Layout layout) {
		return PropsUtil.get(
			PropsKeys.LAYOUT_EDIT_PAGE, new Filter(layout.getType()));
	}

	public String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay) {

		if (!isLayoutFriendliable(layout)) {
			return null;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		LayoutSet layoutSet = layout.getLayoutSet();

		long curLayoutSetId =
			themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

		String portalURL = StringPool.BLANK;

		if (!themeDisplay.getServerName().equals(_LOCALHOST)) {
			String virtualHost = layoutSet.getVirtualHost();

			if (Validator.isNull(virtualHost) &&
				Validator.isNotNull(
					PropsValues.VIRTUAL_HOSTS_DEFAULT_COMMUNITY_NAME)) {

				try {
					Group group = GroupLocalServiceUtil.getGroup(
						themeDisplay.getCompanyId(),
						PropsValues.VIRTUAL_HOSTS_DEFAULT_COMMUNITY_NAME);

					if (layoutSet.getGroupId() == group.getGroupId()) {
						Company company = themeDisplay.getCompany();

						virtualHost = company.getVirtualHost();
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			if (Validator.isNotNull(virtualHost)) {
				virtualHost = getPortalURL(
					virtualHost, themeDisplay.getServerPort(),
					themeDisplay.isSecure());

				String portalDomain = HttpUtil.getDomain(
					themeDisplay.getPortalURL());

				if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
					(virtualHost.indexOf(portalDomain) != -1)) {

					if (themeDisplay.isWidget()) {
						layoutFriendlyURL =
							PropsValues.WIDGET_SERVLET_MAPPING +
								layoutFriendlyURL;
					}

					if (themeDisplay.isI18n()) {
						layoutFriendlyURL =
							themeDisplay.getI18nPath() + layoutFriendlyURL;
					}

					return virtualHost + _pathContext + layoutFriendlyURL;
				}
			}
			else {
				if ((layoutSet.getLayoutSetId() != curLayoutSetId) &&
					(layout.getGroup().getClassPK() !=
						themeDisplay.getUserId())) {

					virtualHost = themeDisplay.getCompany().getVirtualHost();

					if (!virtualHost.equalsIgnoreCase(_LOCALHOST)) {
						portalURL = getPortalURL(
							virtualHost, themeDisplay.getServerPort(),
							themeDisplay.isSecure());
					}
				}
			}
		}

		Group group = layout.getGroup();

		String friendlyURL = null;

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = _PRIVATE_USER_SERVLET_MAPPING;
			}
			else {
				friendlyURL = _PRIVATE_GROUP_SERVLET_MAPPING;
			}
		}
		else {
			friendlyURL = _PUBLIC_GROUP_SERVLET_MAPPING;
		}

		if (themeDisplay.isWidget()) {
			friendlyURL = PropsValues.WIDGET_SERVLET_MAPPING + friendlyURL;
		}

		if (themeDisplay.isI18n()) {
			friendlyURL = themeDisplay.getI18nPath() + friendlyURL;
		}

		return portalURL + _pathContext + friendlyURL + group.getFriendlyURL() +
			layoutFriendlyURL;
	}

	public String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay, Locale locale) {

		String i18nLanguageId = themeDisplay.getI18nLanguageId();
		String i18nPath = themeDisplay.getI18nPath();

		try {
			String tempI18nLanguageId = null;

			if ((I18nFilter.getLanguageIds().contains(locale.toString())) &&
				((PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 1) &&
				 (!locale.equals(LocaleUtil.getDefault()))) ||
				(PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE == 2)) {

				tempI18nLanguageId = locale.toString();
			}

			String tempI18nPath = null;

			if (Validator.isNotNull(tempI18nLanguageId)) {
				tempI18nPath = StringPool.SLASH + tempI18nLanguageId;

				if (!LanguageUtil.isDuplicateLanguageCode(
						locale.getLanguage())) {

					tempI18nPath = StringPool.SLASH + locale.getLanguage();
				}
				else {
					Locale priorityLocale = LanguageUtil.getLocale(
						locale.getLanguage());

					if (locale.equals(priorityLocale)) {
						tempI18nPath = StringPool.SLASH + locale.getLanguage();
					}
				}
			}

			themeDisplay.setI18nLanguageId(tempI18nLanguageId);
			themeDisplay.setI18nPath(tempI18nPath);

			return getLayoutFriendlyURL(layout, themeDisplay);
		}
		finally {
			themeDisplay.setI18nLanguageId(i18nLanguageId);
			themeDisplay.setI18nPath(i18nPath);
		}
	}

	public String getLayoutFullURL(Layout layout, ThemeDisplay themeDisplay) {
		return getLayoutFullURL(layout, themeDisplay, true);
	}

	public String getLayoutFullURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		String layoutURL = getLayoutURL(layout, themeDisplay, doAsUser);
		String portalURL = themeDisplay.getPortalURL();

		if (StringUtil.startsWith(layoutURL, portalURL)) {
			return layoutURL;
		}
		else {
			return portalURL + layoutURL;
		}
	}

	public String getLayoutFullURL(long groupId, String portletId)
		throws PortalException, SystemException {

		long plid = getPlidFromPortletId(groupId, portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			return null;
		}

		StringBundler sb = new StringBundler(4);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		String portalURL = getPortalURL(
			company.getVirtualHost(), getPortalPort(), false);

		sb.append(portalURL);

		if (layout.isPrivateLayout()) {
			if (group.isUser()) {
				sb.append(PortalUtil.getPathFriendlyURLPrivateUser());
			}
			else {
				sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
			}
		}
		else {
			sb.append(PortalUtil.getPathFriendlyURLPublic());
		}

		sb.append(group.getFriendlyURL());
		sb.append(layout.getFriendlyURL());

		return sb.toString();
	}

	public String getLayoutFullURL(ThemeDisplay themeDisplay) {
		return getLayoutFullURL(themeDisplay.getLayout(), themeDisplay);
	}

	public String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String virtualHost = layoutSet.getVirtualHost();

		if (Validator.isNull(virtualHost) &&
			Validator.isNotNull(
				PropsValues.VIRTUAL_HOSTS_DEFAULT_COMMUNITY_NAME)) {

			try {
				Group group = GroupLocalServiceUtil.getGroup(
					themeDisplay.getCompanyId(),
					PropsValues.VIRTUAL_HOSTS_DEFAULT_COMMUNITY_NAME);

				if (layoutSet.getGroupId() == group.getGroupId()) {
					Company company = themeDisplay.getCompany();

					virtualHost = company.getVirtualHost();
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (Validator.isNotNull(virtualHost)) {
			String portalURL = getPortalURL(
				virtualHost, themeDisplay.getServerPort(),
				themeDisplay.isSecure());

			// Use the layout set's virtual host setting only if the layout set
			// is already used for the current request

			long curLayoutSetId =
				themeDisplay.getLayout().getLayoutSet().getLayoutSetId();

			if ((layoutSet.getLayoutSetId() != curLayoutSetId) ||
				(portalURL.startsWith(themeDisplay.getURLPortal()))) {

				String layoutSetFriendlyURL = StringPool.BLANK;

				if (themeDisplay.isI18n()) {
					layoutSetFriendlyURL = themeDisplay.getI18nPath();
				}

				return portalURL + _pathContext + layoutSetFriendlyURL;
			}
		}

		Group group = GroupLocalServiceUtil.getGroup(layoutSet.getGroupId());

		String friendlyURL = null;

		if (layoutSet.isPrivateLayout()) {
			if (group.isUser()) {
				friendlyURL = _PRIVATE_USER_SERVLET_MAPPING;
			}
			else {
				friendlyURL = _PRIVATE_GROUP_SERVLET_MAPPING;
			}
		}
		else {
			friendlyURL = _PUBLIC_GROUP_SERVLET_MAPPING;
		}

		if (themeDisplay.isI18n()) {
			friendlyURL = themeDisplay.getI18nPath() + friendlyURL;
		}

		return _pathContext + friendlyURL + group.getFriendlyURL();
	}

	public String getLayoutTarget(Layout layout) {
		UnicodeProperties typeSettingsProps =
			layout.getTypeSettingsProperties();

		String target = typeSettingsProps.getProperty("target");

		if (Validator.isNull(target)) {
			target = StringPool.BLANK;
		}
		else {
			target = "target=\"" + target + "\"";
		}

		return target;
	}

	public String getLayoutURL(Layout layout, ThemeDisplay themeDisplay) {
		return getLayoutURL(layout, themeDisplay, true);
	}

	public String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		if (layout == null) {
			return themeDisplay.getPathMain() + PATH_PORTAL_LAYOUT;
		}

		if (!layout.getType().equals(LayoutConstants.TYPE_URL)) {
			String layoutFriendlyURL = getLayoutFriendlyURL(
				layout, themeDisplay);

			if (Validator.isNotNull(layoutFriendlyURL)) {
				if (doAsUser) {
					if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
						layoutFriendlyURL = HttpUtil.addParameter(
							layoutFriendlyURL, "doAsUserId",
							themeDisplay.getDoAsUserId());
					}

					if (Validator.isNotNull(
							themeDisplay.getDoAsUserLanguageId())) {

						layoutFriendlyURL = HttpUtil.addParameter(
							layoutFriendlyURL, "doAsUserLanguageId",
							themeDisplay.getDoAsUserLanguageId());
					}
				}

				return layoutFriendlyURL;
			}
		}

		String layoutURL = getLayoutActualURL(layout);

		if (doAsUser) {
			if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
				layoutURL = HttpUtil.addParameter(
					layoutURL, "doAsUserId", themeDisplay.getDoAsUserId());
			}

			if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
				layoutURL = HttpUtil.addParameter(
					layoutURL, "doAsUserLanguageId",
					themeDisplay.getDoAsUserLanguageId());
			}
		}

		return layoutURL;
	}

	public String getLayoutURL(ThemeDisplay themeDisplay) {
		return getLayoutURL(themeDisplay.getLayout(), themeDisplay);
	}

	public String getLayoutViewPage(Layout layout) {
		return PropsUtil.get(
			PropsKeys.LAYOUT_VIEW_PAGE, new Filter(layout.getType()));
	}

	public Locale getLocale(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return themeDisplay.getLocale();
		}
		else {
			HttpSession session = request.getSession();

			return (Locale)session.getAttribute(Globals.LOCALE_KEY);
		}
	}

	public Locale getLocale(RenderRequest renderRequest) {
		return getLocale(getHttpServletRequest(renderRequest));
	}

	public String getNetvibesURL(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return _getServletURL(
			portlet, PropsValues.NETVIBES_SERVLET_MAPPING, themeDisplay);
	}

	public HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		HttpServletRequest originalRequest = request;

		while (originalRequest.getClass().getName().startsWith(
					"com.liferay.")) {

			// Get original request so that portlets inside portlets render
			// properly

			originalRequest = (HttpServletRequest)
				((HttpServletRequestWrapper)originalRequest).getRequest();
		}

		return originalRequest;
	}

	public long getParentGroupId(long groupId)
		throws PortalException, SystemException {

		if (groupId <= 0) {
			return 0;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		long parentGroupId = groupId;

		if (group.isLayout()) {
			parentGroupId = group.getParentGroupId();
		}

		return parentGroupId;
	}

	public String getPathContext() {
		return _pathContext;
	}

	public String getPathFriendlyURLPrivateGroup() {
		return _pathFriendlyURLPrivateGroup;
	}

	public String getPathFriendlyURLPrivateUser() {
		return _pathFriendlyURLPrivateUser;
	}

	public String getPathFriendlyURLPublic() {
		return _pathFriendlyURLPublic;
	}

	public String getPathImage() {
		return _pathImage;
	}

	public String getPathMain() {
		return _pathMain;
	}

	public long getPlidFromFriendlyURL(long companyId, String friendlyURL) {
		if (Validator.isNull(friendlyURL)) {
			return LayoutConstants.DEFAULT_PLID;
		}

		String[] urlParts = friendlyURL.split("\\/", 4);

		if ((friendlyURL.charAt(0) != CharPool.SLASH) &&
			(urlParts.length != 4)) {

			return LayoutConstants.DEFAULT_PLID;
		}

		boolean privateLayout = true;

		String urlPrefix = StringPool.SLASH + urlParts[1];

		if (_PUBLIC_GROUP_SERVLET_MAPPING.equals(urlPrefix)) {
			privateLayout = false;
		}
		else if (_PRIVATE_GROUP_SERVLET_MAPPING.equals(urlPrefix) ||
				 _PRIVATE_USER_SERVLET_MAPPING.equals(urlPrefix)) {

			privateLayout = true;
		}
		else {
			return LayoutConstants.DEFAULT_PLID;
		}

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + urlParts[2]);
		}
		catch (Exception e) {
		}

		if (group != null) {
			Layout layout = null;

			try {
				String layoutFriendlyURL = null;

				if (urlParts.length == 4) {
					layoutFriendlyURL = StringPool.SLASH + urlParts[3];
				}
				else {
					layoutFriendlyURL = "/1";
				}

				layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
					group.getGroupId(), privateLayout, layoutFriendlyURL);

				return layout.getPlid();
			}
			catch (Exception e) {
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long plid = LayoutConstants.DEFAULT_PLID;

		StringBundler sb = new StringBundler(5);

		sb.append(groupId);
		sb.append(StringPool.SPACE);
		sb.append(privateLayout);
		sb.append(StringPool.SPACE);
		sb.append(portletId);

		String key = sb.toString();

		Long plidObj = _plidToPortletIdCache.get(key);

		if (plidObj == null) {
			plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

			if (plid != LayoutConstants.DEFAULT_PLID) {
				_plidToPortletIdCache.put(key, plid);
			}
		}
		else {
			plid = plidObj.longValue();

			boolean validPlid = false;

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(portletId)) {
					validPlid = true;
				}
			}
			catch (Exception e) {
			}

			if (!validPlid) {
				_plidToPortletIdCache.remove(key);

				plid = _getPlidFromPortletId(groupId, privateLayout, portletId);

				if (plid != LayoutConstants.DEFAULT_PLID) {
					_plidToPortletIdCache.put(key, plid);
				}
			}
		}

		return plid;
	}

	public long getPlidFromPortletId(long groupId, String portletId) {
		long plid = getPlidFromPortletId(groupId, false, portletId);

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = getPlidFromPortletId(groupId, true, portletId);
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Portlet " + portletId +
						" does not exist on a page in group " + groupId);
			}
		}

		return plid;
	}

	public String getPortalLibDir() {
		return _portalLibDir;
	}

	public int getPortalPort() {
		return _portalPort.intValue();
	}

	public Properties getPortalProperties() {
		return PropsUtil.getProperties();
	}

	public String getPortalURL(HttpServletRequest request) {
		return getPortalURL(request, request.isSecure());
	}

	public String getPortalURL(HttpServletRequest request, boolean secure) {
		return getPortalURL(
			request.getServerName(), request.getServerPort(), secure);
	}

	public String getPortalURL(PortletRequest portletRequest) {
		return getPortalURL(portletRequest, portletRequest.isSecure());
	}

	public String getPortalURL(PortletRequest portletRequest, boolean secure) {
		return getPortalURL(
			portletRequest.getServerName(), portletRequest.getServerPort(),
			secure);
	}

	public String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		StringBundler sb = new StringBundler();

		if (secure || Http.HTTPS.equals(PropsValues.WEB_SERVER_PROTOCOL)) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		if (Validator.isNull(PropsValues.WEB_SERVER_HOST)) {
			sb.append(serverName);
		}
		else {
			sb.append(PropsValues.WEB_SERVER_HOST);
		}

		if (!secure) {
			if (PropsValues.WEB_SERVER_HTTP_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if ((PropsValues.WEB_SERVER_HTTP_PORT != serverPort) &&
					(PropsValues.WEB_SERVER_HTTP_PORT != Http.HTTP_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTP_PORT);
				}
			}
		}

		if (secure) {
			if (PropsValues.WEB_SERVER_HTTPS_PORT == -1) {
				if ((serverPort != Http.HTTP_PORT) &&
					(serverPort != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(serverPort);
				}
			}
			else {
				if ((PropsValues.WEB_SERVER_HTTPS_PORT != serverPort) &&
					(PropsValues.WEB_SERVER_HTTPS_PORT != Http.HTTPS_PORT)) {

					sb.append(StringPool.COLON);
					sb.append(PropsValues.WEB_SERVER_HTTPS_PORT);
				}
			}
		}

		return sb.toString();
	}

	public String getPortalURL(ThemeDisplay themeDisplay) {
		String serverName = themeDisplay.getServerName();

		Layout layout = themeDisplay.getLayout();

		if (layout != null) {
			LayoutSet layoutSet = layout.getLayoutSet();

			String virtualHost = layoutSet.getVirtualHost();

			if (Validator.isNotNull(virtualHost)) {
				serverName = virtualHost;
			}
		}

		return getPortalURL(
			serverName, themeDisplay.getServerPort(), themeDisplay.isSecure());
	}

	public String getPortalWebDir() {
		return _portalWebDir;
	}

	public List<KeyValuePair> getPortletBreadcrumbList(
		HttpServletRequest request) {

		return (List<KeyValuePair>)request.getAttribute(
			WebKeys.PORTLET_BREADCRUMB_MAP);
	}

	public String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(
			JavaConstants.JAVAX_PORTLET_DESCRIPTION);
	}

	public String getPortletDescription(Portlet portlet, User user) {
		return getPortletDescription(portlet.getPortletId(), user);
	}

	public String getPortletDescription(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	public String getPortletDescription(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletDescription(portletId, locale);
	}

	public String getPortletDescription(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_DESCRIPTION.concat(
				StringPool.PERIOD).concat(portletId));
	}

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException {

		return getPortletFriendlyURLMapper(groupId, privateLayout, url, null);
	}

	public Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException {

		boolean foundFriendlyURLMapper = false;

		String friendlyURL = url;
		String queryString = StringPool.BLANK;

		List<Portlet> portlets =
			PortletLocalServiceUtil.getFriendlyURLMapperPortlets();

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			FriendlyURLMapper friendlyURLMapper =
				portlet.getFriendlyURLMapperInstance();

			if (url.endsWith(
					StringPool.SLASH + friendlyURLMapper.getMapping())) {

				url += StringPool.SLASH;
			}

			int pos = -1;

			if (friendlyURLMapper.isCheckMappingWithPrefix()) {
				pos = url.indexOf(
					FRIENDLY_URL_SEPARATOR + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}
			else {
				pos = url.indexOf(
					StringPool.SLASH + friendlyURLMapper.getMapping() +
						StringPool.SLASH);
			}

			if (pos != -1) {
				foundFriendlyURLMapper = true;

				friendlyURL = url.substring(0, pos);

				Map<String, String[]> actualParams =
					new HashMap<String, String[]>();

				/*Object lifecycle = actualParams.get("p_p_lifecycle");

				if ((lifecycle == null) ||
					(((String[])lifecycle).length == 0)) {

					actualParams.put("p_p_lifecycle", "0");
				}

				Object state = actualParams.get("p_p_state");

				if ((state == null) || (((String[])state).length == 0)) {
					actualParams.put(
						"p_p_state", WindowState.MAXIMIZED.toString());
				}*/

				Map<String, String> prpIdentifiers =
					new HashMap<String, String>();

				Set<PublicRenderParameter> publicRenderParameters =
					portlet.getPublicRenderParameters();

				for (PublicRenderParameter publicRenderParameter :
						publicRenderParameters) {

					QName qName = publicRenderParameter.getQName();

					String publicRenderParameterIdentifier =
						qName.getLocalPart();
					String publicRenderParameterName =
						PortletQNameUtil.getPublicRenderParameterName(qName);

					prpIdentifiers.put(
						publicRenderParameterIdentifier,
						publicRenderParameterName);
				}

				FriendlyURLMapperThreadLocal.setPRPIdentifiers(prpIdentifiers);

				if (friendlyURLMapper.isCheckMappingWithPrefix()) {
					friendlyURLMapper.populateParams(
						url.substring(pos + 2), actualParams);
				}
				else {
					friendlyURLMapper.populateParams(
						url.substring(pos), actualParams);
				}

				queryString =
					StringPool.AMPERSAND +
						HttpUtil.parameterMapToString(actualParams, false);

				break;
			}
		}

		if (!foundFriendlyURLMapper) {
			int x = url.indexOf(FRIENDLY_URL_SEPARATOR);

			if (x != -1) {
				int y = url.indexOf(StringPool.SLASH, x + 3);

				if (y == -1) {
					y = url.length();
				}

				String ppid = url.substring(x + 3, y);

				if (Validator.isNotNull(ppid)) {
					friendlyURL = url.substring(0, x);

					Map<String, String[]> actualParams = null;

					if (params != null) {
						actualParams = new HashMap<String, String[]>(params);
					}
					else {
						actualParams = new HashMap<String, String[]>();
					}

					actualParams.put("p_p_id", new String[] {ppid});
					actualParams.put("p_p_lifecycle", new String[] {"0"});
					actualParams.put(
						"p_p_state",
						new String[] {WindowState.MAXIMIZED.toString()});
					actualParams.put(
						"p_p_mode", new String[] {PortletMode.VIEW.toString()});

					queryString =
						StringPool.AMPERSAND +
							HttpUtil.parameterMapToString(actualParams, false);
				}
			}
		}

		friendlyURL = StringUtil.replace(
			friendlyURL, StringPool.DOUBLE_SLASH, StringPool.SLASH);

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(0, friendlyURL.length() - 1);
		}

		Layout layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
			groupId, privateLayout, friendlyURL);

		return new Object[] {layout, queryString};
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(ActionRequest actionRequest) {
		return getScopeGroupId(actionRequest);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(HttpServletRequest request) {
		return getScopeGroupId(request);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(Layout layout) {
		return getScopeGroupId(layout);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getPortletGroupId(layout);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public long getPortletGroupId(RenderRequest renderRequest) {
		return getScopeGroupId(renderRequest);
	}

	public String getPortletId(HttpServletRequest request) {
		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			return portletConfigImpl.getPortletId();
		}
		else {
			return null;
		}
	}

	public String getPortletId(PortletRequest portletRequest) {
		PortletConfigImpl portletConfigImpl =
			(PortletConfigImpl)portletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfigImpl != null) {
			return portletConfigImpl.getPortletId();
		}
		else {
			return null;
		}
	}

	public String getPortletNamespace(String portletId) {
		return StringPool.UNDERLINE.concat(portletId).concat(
			StringPool.UNDERLINE);
	}

	public String getPortletTitle(Portlet portlet, Locale locale) {
		return getPortletTitle(portlet.getPortletId(), locale);
	}

	public String getPortletTitle(Portlet portlet, String languageId) {
		return getPortletTitle(portlet.getPortletId(), languageId);
	}

	public String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		PortletConfig portletConfig = PortletConfigFactory.create(
			portlet, servletContext);

		ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

		return resourceBundle.getString(JavaConstants.JAVAX_PORTLET_TITLE);
	}

	public String getPortletTitle(Portlet portlet, User user) {
		return getPortletTitle(portlet.getPortletId(), user);
	}

	public String getPortletTitle(String portletId, Locale locale) {
		return LanguageUtil.get(
			locale,
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	public String getPortletTitle(String portletId, String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getPortletTitle(portletId, locale);
	}

	public String getPortletTitle(String portletId, User user) {
		return LanguageUtil.get(
			user.getLocale(),
			JavaConstants.JAVAX_PORTLET_TITLE.concat(StringPool.PERIOD).concat(
				portletId));
	}

	public String getPortletXmlFileName() throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				PropsKeys.AUTO_DEPLOY_CUSTOM_PORTLET_XML,
				PropsValues.AUTO_DEPLOY_CUSTOM_PORTLET_XML)) {

			return PORTLET_XML_FILE_NAME_CUSTOM;
		}
		else {
			return PORTLET_XML_FILE_NAME_STANDARD;
		}
	}

	public PortletPreferences getPreferences(HttpServletRequest request) {
		RenderRequest renderRequest = (RenderRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletPreferences preferences = null;

		if (renderRequest != null) {
			PortletPreferencesWrapper preferencesWrapper =
				(PortletPreferencesWrapper)renderRequest.getPreferences();

			preferences = preferencesWrapper.getPreferencesImpl();
		}

		return preferences;
	}

	public PreferencesValidator getPreferencesValidator(Portlet portlet) {
		PortletApp portletApp = portlet.getPortletApp();

		if (portletApp.isWARFile()) {
			PortletBag portletBag = PortletBagPool.get(
				portlet.getRootPortletId());

			return portletBag.getPreferencesValidatorInstance();
		}
		else {
			PreferencesValidator preferencesValidator = null;

			if (Validator.isNotNull(portlet.getPreferencesValidator())) {
				preferencesValidator =
					(PreferencesValidator)InstancePool.get(
						portlet.getPreferencesValidator());
			}

			return preferencesValidator;
		}
	}

	public long getScopeGroupId(HttpServletRequest request) {
		String portletId = getPortletId(request);

		return getScopeGroupId(request, portletId);
	}

	public long getScopeGroupId(HttpServletRequest request, String portletId) {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		long scopeGroupId = 0;

		if (layout != null) {
			Group group = layout.getGroup();

			if (group.getName().equals(GroupConstants.CONTROL_PANEL)) {
				long doAsGroupId = ParamUtil.getLong(request, "doAsGroupId");

				if (doAsGroupId <= 0) {
					try {
						Group guestGroup = GroupLocalServiceUtil.getGroup(
							group.getCompanyId(), GroupConstants.GUEST);

						doAsGroupId = guestGroup.getGroupId();
					}
					catch (Exception e) {
					}
				}

				if (doAsGroupId > 0) {
					scopeGroupId = doAsGroupId;
				}

				try {
					group = GroupLocalServiceUtil.getGroup(scopeGroupId);

					if (group.hasStagingGroup()) {
						Group stagingGroup = group.getStagingGroup();

						scopeGroupId = stagingGroup.getGroupId();
					}
				}
				catch (Exception e) {
				}
			}
		}

		if (scopeGroupId <= 0) {
			scopeGroupId = getScopeGroupId(layout, portletId);
		}

		return scopeGroupId;
	}

	public long getScopeGroupId(Layout layout) {
		if (layout == null) {
			return 0;
		}
		else {
			return layout.getGroupId();
		}
	}

	public long getScopeGroupId(Layout layout, String portletId) {
		if (layout == null) {
			return 0;
		}
		else {
			if (Validator.isNotNull(portletId)) {
				try {
					PortletPreferences portletSetup =
						PortletPreferencesFactoryUtil.getLayoutPortletSetup(
							layout, portletId);

					long scopeLayoutId = GetterUtil.getLong(
						portletSetup.getValue("lfr-scope-layout-id", null));

					if (scopeLayoutId > 0) {
						Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
							layout.getGroupId(), layout.isPrivateLayout(),
							scopeLayoutId);

						return scopeLayout.getScopeGroup().getGroupId();
					}
				}
				catch (Exception e) {
				}
			}

			return layout.getGroupId();
		}
	}

	public long getScopeGroupId(long plid) {
		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getLayout(plid);
		}
		catch (Exception e) {
		}

		return getScopeGroupId(layout);
	}

	public long getScopeGroupId(PortletRequest portletRequest) {
		return getScopeGroupId(getHttpServletRequest(portletRequest));
	}

	public User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getSelectedUser(request, true);
	}

	public User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException {

		long userId = ParamUtil.getLong(request, "p_u_i_d");

		User user = null;

		try {
			if (checkPermission) {
				user = UserServiceUtil.getUserById(userId);
			}
			else {
				user = UserLocalServiceUtil.getUserById(userId);
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	public User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getSelectedUser(portletRequest, true);
	}

	public User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException {

		return getSelectedUser(
			getHttpServletRequest(portletRequest), checkPermission);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri) {

		return getStaticResourceURL(request, uri, null, 0);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return getStaticResourceURL(request, uri, null, timestamp);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return getStaticResourceURL(request, uri, queryString, 0);
	}

	public String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp) {

		if (uri.contains(StringPool.QUESTION)) {
			return uri;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Theme theme = themeDisplay.getTheme();
		ColorScheme colorScheme = themeDisplay.getColorScheme();

		Map<String, String[]> parameterMap = null;

		if (Validator.isNotNull(queryString)) {
			parameterMap = HttpUtil.getParameterMap(queryString);
		}

		StringBundler sb = new StringBundler();

		// URI

		sb.append(uri);
		sb.append(StringPool.QUESTION);

		// Browser id

		if ((parameterMap == null) ||
			(!parameterMap.containsKey("browserId"))) {

			sb.append("&browserId=");
			sb.append(BrowserSnifferUtil.getBrowserId(request));
		}

		// Theme and color scheme

		if (uri.endsWith(".jsp")) {
			if ((parameterMap == null) ||
				(!parameterMap.containsKey("themeId"))) {

				sb.append("&themeId=");
				sb.append(theme.getThemeId());
			}

			if ((parameterMap == null) ||
				(!parameterMap.containsKey("colorSchemeId"))) {

				sb.append("&colorSchemeId=");
				sb.append(colorScheme.getColorSchemeId());
			}
		}

		// Minifier

		if ((parameterMap == null) ||
			(!parameterMap.containsKey("minifierType"))) {

			String minifierType = StringPool.BLANK;

			if (uri.endsWith(".css") || uri.endsWith("css.jsp")) {
				if (themeDisplay.isThemeCssFastLoad()) {
					minifierType = "css";
				}
			}
			else if (themeDisplay.isThemeJsFastLoad()) {
				minifierType = "js";
			}

			if (Validator.isNotNull(minifierType)) {
				sb.append("&minifierType=");
				sb.append(minifierType);
			}
		}

		// Query string

		if (Validator.isNotNull(queryString)) {
			if (!queryString.startsWith(StringPool.AMPERSAND)) {
				sb.append(StringPool.AMPERSAND);
			}

			sb.append(queryString);
		}

		// Language id

		sb.append("&languageId=");
		sb.append(themeDisplay.getLanguageId());

		// Timestamp

		boolean appendedTimestamp = false;

		if (uri.startsWith(StrutsUtil.TEXT_HTML_DIR)) {
			ServletContext servletContext =
				(ServletContext)request.getAttribute(WebKeys.CTX);

			String uriRealPath = ServletContextUtil.getRealPath(
				servletContext, uri);

			if (uriRealPath != null) {
				File uriFile = new File(uriRealPath);

				if (uriFile.exists()) {
					sb.append("&t=");
					sb.append(uriFile.lastModified());

					appendedTimestamp = true;
				}
			}
		}

		if (!appendedTimestamp &&
			((parameterMap == null) || !parameterMap.containsKey("t"))) {

			sb.append("&t=");
			sb.append(theme.getTimestamp());
		}

		String url = sb.toString();

		url = StringUtil.replace(url, "?&", StringPool.QUESTION);

		return url;
	}

	public String getStrutsAction(HttpServletRequest request) {
		String strutsAction = ParamUtil.getString(request, "struts_action");

		if (Validator.isNotNull(strutsAction)) {

			// This method should only return a Struts action if you're dealing
			// with a regular HTTP servlet request, not a portlet HTTP servlet
			// request.

			return StringPool.BLANK;
		}

		return _getPortletParam(request, "struts_action");
	}

	public String[] getSystemCommunityRoles() {
		return _allSystemCommunityRoles;
	}

	public String[] getSystemGroups() {
		return _allSystemGroups;
	}

	public String[] getSystemOrganizationRoles() {
		return _allSystemOrganizationRoles;
	}

	public String[] getSystemRoles() {
		return _allSystemRoles;
	}

	public UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		HttpServletRequestWrapper requestWrapper = null;

		if (request instanceof HttpServletRequestWrapper) {
			requestWrapper = (HttpServletRequestWrapper)request;
		}

		UploadServletRequest uploadRequest = null;

		while (uploadRequest == null) {

			// Find the underlying UploadServletRequest wrapper. For example,
			// WebSphere wraps all requests with ProtectedServletRequest.

			if (requestWrapper instanceof UploadServletRequest) {
				uploadRequest = (UploadServletRequest)requestWrapper;
			}
			else {
				HttpServletRequest parentRequest =
					(HttpServletRequest)requestWrapper.getRequest();

				if (!(parentRequest instanceof HttpServletRequestWrapper)) {

					// This block should never be reached unless this method is
					// called from a hot deployable portlet. See LayoutAction.

					uploadRequest = new UploadServletRequestImpl(parentRequest);

					break;
				}
				else {
					requestWrapper = (HttpServletRequestWrapper)parentRequest;
				}
			}
		}

		return uploadRequest;
	}

	public UploadPortletRequest getUploadPortletRequest(
		PortletRequest portletRequest) {

		PortletRequestImpl portletRequestImpl =
			(PortletRequestImpl)portletRequest;

		DynamicServletRequest dynamicRequest =
			(DynamicServletRequest)portletRequestImpl.getHttpServletRequest();

		HttpServletRequestWrapper requestWrapper =
			(HttpServletRequestWrapper)dynamicRequest.getRequest();

		UploadServletRequest uploadRequest = getUploadServletRequest(
			requestWrapper);

		return new UploadPortletRequestImpl(
			uploadRequest,
			PortalUtil.getPortletNamespace(
				portletRequestImpl.getPortletName()));
	}

	public Date getUptime() {
		return _UP_TIME;
	}

	public String getURLWithSessionId(String url, String sessionId) {
		if (!PropsValues.SESSION_ENABLE_URL_WITH_SESSION_ID) {
			return url;
		}

		// LEP-4787

		int x = url.indexOf(StringPool.SEMICOLON);

		if (x != -1) {
			return url;
		}

		x = url.indexOf(StringPool.QUESTION);

		if (x != -1) {
			StringBundler sb = new StringBundler(4);

			sb.append(url.substring(0, x));
			sb.append(_JSESSIONID);
			sb.append(sessionId);
			sb.append(url.substring(x));

			return sb.toString();
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
		// http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf(StringPool.DOUBLE_SLASH);

		StringBundler sb = new StringBundler(4);

		sb.append(url);

		if (x != -1) {
			int y = url.lastIndexOf(StringPool.SLASH);

			if (x + 1 == y) {
				sb.append(StringPool.SLASH);
			}
		}

		sb.append(_JSESSIONID);
		sb.append(sessionId);

		return sb.toString();
	}

	public User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		long userId = getUserId(request);

		if (userId <= 0) {

			// Portlet WARs may have the correct remote user and not have the
			// correct user id because the user id is saved in the session
			// and may not be accessible by the portlet WAR's session. This
			// behavior is inconsistent across different application servers.

			String remoteUser = request.getRemoteUser();

			if (remoteUser == null) {
				return null;
			}

			userId = GetterUtil.getLong(remoteUser);
		}

		User user = (User)request.getAttribute(WebKeys.USER);

		if (user == null) {
			user = UserLocalServiceUtil.getUserById(userId);

			request.setAttribute(WebKeys.USER, user);
		}

		return user;
	}

	public User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getUser(getHttpServletRequest(portletRequest));
	}

	public long getUserId(HttpServletRequest request) {
		Long userIdObj = (Long)request.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		String path = GetterUtil.getString(request.getPathInfo());
		String strutsAction = getStrutsAction(request);
		String actionName = _getPortletParam(request, "actionName");

		boolean alwaysAllowDoAsUser = false;

		if (path.equals("/portal/fckeditor") ||
			strutsAction.equals("/document_library/edit_file_entry") ||
			strutsAction.equals("/image_gallery/edit_image") ||
			strutsAction.equals("/wiki/edit_page_attachment") ||
			actionName.equals("addFile")) {

			alwaysAllowDoAsUser = true;
		}

		if ((!PropsValues.PORTAL_JAAS_ENABLE &&
			  PropsValues.PORTAL_IMPERSONATION_ENABLE) ||
			(alwaysAllowDoAsUser)) {

			String doAsUserIdString = ParamUtil.getString(
				request, "doAsUserId");

			try {
				long doAsUserId = getDoAsUserId(
					request, doAsUserIdString, alwaysAllowDoAsUser);

				if (doAsUserId > 0) {
					if (_log.isDebugEnabled()) {
						_log.debug("Impersonating user " + doAsUserId);
					}

					return doAsUserId;
				}
			}
			catch (Exception e) {
				_log.error("Unable to impersonate user " + doAsUserIdString, e);
			}
		}

		HttpSession session = request.getSession();

		userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			request.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	public long getUserId(PortletRequest portletRequest) {
		return getUserId(getHttpServletRequest(portletRequest));
	}

	public String getUserName(long userId, String defaultUserName) {
		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL);
	}

	public String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getUserName(
			userId, defaultUserName, UserAttributes.USER_NAME_FULL, request);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getUserName(userId, defaultUserName, userAttribute, null);
	}

	public String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		String userName = defaultUserName;

		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			if (userAttribute.equals(UserAttributes.USER_NAME_FULL)) {
				userName = user.getFullName();
			}
			else {
				userName = user.getScreenName();
			}

			if (request != null) {
				Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

				PortletURL portletURL = new PortletURLImpl(
					request, PortletKeys.DIRECTORY, layout.getPlid(),
					PortletRequest.RENDER_PHASE);

				portletURL.setWindowState(WindowState.MAXIMIZED);
				portletURL.setPortletMode(PortletMode.VIEW);

				portletURL.setParameter(
					"struts_action", "/directory/view_user");
				portletURL.setParameter(
					"p_u_i_d", String.valueOf(user.getUserId()));

				userName =
					"<a href=\"" + portletURL.toString() + "\">" + userName +
						"</a>";
			}
		}
		catch (Exception e) {
		}

		return userName;
	}

	public String getUserPassword(HttpServletRequest request) {
		HttpSession session = request.getSession();

		return getUserPassword(session);
	}

	public String getUserPassword(HttpSession session) {
		return (String)session.getAttribute(WebKeys.USER_PASSWORD);
	}

	public String getUserPassword(PortletRequest portletRequest) {
		return getUserPassword(getHttpServletRequest(portletRequest));
	}

	public String getUserValue(long userId, String param, String defaultValue)
		throws SystemException {

		if (Validator.isNotNull(defaultValue)) {
			return defaultValue;
		}
		else {
			try {
				User user = UserLocalServiceUtil.getUserById(userId);

				return BeanPropertiesUtil.getString(user, param, defaultValue);
			}
			catch (PortalException pe) {
				return StringPool.BLANK;
			}
		}
	}

	public String getWidgetURL(Portlet portlet, ThemeDisplay themeDisplay) {
		return _getServletURL(
			portlet, PropsValues.WIDGET_SERVLET_MAPPING, themeDisplay);
	}

	public boolean isLayoutFirstPageable(String type) {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_FIRST_PAGEABLE, new Filter(type)),
			false);
	}

	public boolean isLayoutFriendliable(Layout layout) {
		return GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.LAYOUT_URL_FRIENDLIABLE,
				new Filter(layout.getType())),
			true);
	}

	public boolean isLayoutParentable(Layout layout) {
		return isLayoutParentable(layout.getType());
	}

	public boolean isLayoutParentable(String type) {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_PARENTABLE, new Filter(type)), true);
	}

	public boolean isLayoutSitemapable(Layout layout) {
		if (layout.isPrivateLayout()) {
			return false;
		}

		return GetterUtil.getBoolean(PropsUtil.get(
			PropsKeys.LAYOUT_SITEMAPABLE, new Filter(layout.getType())), true);
	}

	public boolean isMethodGet(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.GET)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isMethodPost(PortletRequest portletRequest) {
		HttpServletRequest request = getHttpServletRequest(portletRequest);

		String method = GetterUtil.getString(request.getMethod());

		if (method.equalsIgnoreCase(HttpMethods.POST)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isReservedParameter(String name) {
		return _reservedParams.contains(name);
	}

	public boolean isSystemGroup(String groupName) {
		if (groupName == null) {
			return false;
		}

		groupName = groupName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemGroups, groupName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSystemRole(String roleName) {
		if (roleName == null) {
			return false;
		}

		roleName = roleName.trim();

		int pos = Arrays.binarySearch(
			_sortedSystemRoles, roleName, new StringComparator());

		if (pos >= 0) {
			return true;
		}
		else {
			pos = Arrays.binarySearch(
				_sortedSystemCommunityRoles, roleName, new StringComparator());

			if (pos >= 0) {
				return true;
			}
			else {
				pos = Arrays.binarySearch(
					_sortedSystemOrganizationRoles, roleName,
					new StringComparator());

				if (pos >= 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isUpdateAvailable() throws SystemException {
		return PluginPackageUtil.isUpdateAvailable();
	}

	public void renderPage(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		StringServletResponse stringResponse = new StringServletResponse(
			response);

		requestDispatcher.include(request, stringResponse);

		sb.append(stringResponse.getString());
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString)
		throws IOException, ServletException {

		renderPortlet(
			sb, servletContext, request, response, portlet, queryString, null,
			null, null);
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		renderPortlet(
			sb, servletContext, request, response, portlet, queryString,
			columnId, columnPos, columnCount, null);
	}

	public void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount, String path)
		throws IOException, ServletException {

		queryString = GetterUtil.getString(queryString);
		columnId = GetterUtil.getString(columnId);

		if (columnPos == null) {
			columnPos = new Integer(0);
		}

		if (columnCount == null) {
			columnCount = new Integer(0);
		}

		request.setAttribute(WebKeys.RENDER_PORTLET, portlet);
		request.setAttribute(WebKeys.RENDER_PORTLET_QUERY_STRING, queryString);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_ID, columnId);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_POS, columnPos);
		request.setAttribute(WebKeys.RENDER_PORTLET_COLUMN_COUNT, columnCount);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		if (sb != null) {
			StringServletResponse stringResponse = new StringServletResponse(
				response);

			requestDispatcher.include(request, stringResponse);

			boolean showPortlet = true;

			Boolean portletConfiguratorVisibility =
				(Boolean)request.getAttribute(
					WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

			if (portletConfiguratorVisibility != null) {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				try {
					if (!PortletPermissionUtil.contains(
							themeDisplay.getPermissionChecker(),
							themeDisplay.getPlid(), portlet.getPortletId(),
							ActionKeys.CONFIGURATION)) {

						showPortlet = false;
					}
				}
				catch (Exception e) {
					throw new ServletException(e);
				}

				request.removeAttribute(
					WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);
			}

			if (showPortlet) {
				sb.append(stringResponse.getString());
			}
		}
		else {

			// LEP-766

			response.setContentType(ContentTypes.TEXT_HTML_UTF8);

			requestDispatcher.include(request, response);
		}
	}

	public void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		sendError(0, e, actionRequest, actionResponse);
	}

	public void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		sendError(0, e, request, response);
	}

	public void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		StringBundler sb = new StringBundler(7);

		sb.append(_pathMain);
		sb.append("/portal/status?status=");
		sb.append(status);
		sb.append("&exception=");
		sb.append(e.getClass().getName());
		sb.append("&previousURL=");
		sb.append(HttpUtil.encodeURL(PortalUtil.getCurrentURL(actionRequest)));

		actionResponse.sendRedirect(sb.toString());
	}

	public void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isInfoEnabled()) {
			String currentURL = (String)request.getAttribute(
				WebKeys.CURRENT_URL);

			_log.info(
				"Current URL " + currentURL + " generates exception: " +
					e.getMessage());
		}

		if (_log.isWarnEnabled()) {
			_log.warn(e, e);
		}

		if (response.isCommitted()) {
			return;
		}

		if (status == 0) {
			if (e instanceof PrincipalException) {
				status = HttpServletResponse.SC_FORBIDDEN;
			}
			else {
				String name = e.getClass().getName();

				name = name.substring(name.lastIndexOf(StringPool.PERIOD) + 1);

				if (name.startsWith("NoSuch") && name.endsWith("Exception")) {
					status = HttpServletResponse.SC_NOT_FOUND;
				}
			}

			if (status == 0) {

				// LPS-5352

				if (PropsValues.TCK_URL) {
					status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
				}
				else {
					status = HttpServletResponse.SC_BAD_REQUEST;
				}
			}
		}

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		String redirect = PATH_MAIN + "/portal/status";

		if (e instanceof NoSuchLayoutException &&
			Validator.isNotNull(
				PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND)) {

			response.setStatus(status);

			redirect = PropsValues.LAYOUT_FRIENDLY_URL_PAGE_NOT_FOUND;

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else if (PropsValues.LAYOUT_SHOW_HTTP_STATUS) {
			response.setStatus(status);

			SessionErrors.add(request, e.getClass().getName(), e);

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(redirect);

			if (requestDispatcher != null) {
				requestDispatcher.forward(request, response);
			}
		}
		else {
			if (e != null) {
				response.sendError(status, e.getMessage());
			}
			else {
				response.sendError(status);
			}
		}
	}

	/**
	 * Sets the description for a page. This overrides the existing page
	 * description.
	 */
	public void setPageDescription(
		String description, HttpServletRequest request) {

		request.setAttribute(WebKeys.PAGE_DESCRIPTION, description);
	}

	/**
	 * Sets the keywords for a page. This overrides the existing page keywords.
	 */
	public void setPageKeywords(String keywords, HttpServletRequest request) {
		request.removeAttribute(WebKeys.PAGE_KEYWORDS);

		addPageKeywords(keywords, request);
	}

	/**
	 * Sets the subtitle for a page. This overrides the existing page subtitle.
	 */
	public void setPageSubtitle(String subtitle, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_SUBTITLE, subtitle);
	}

	/**
	 * Sets the whole title for a page. This overrides the existing page whole
	 * title.
	 */
	public void setPageTitle(String title, HttpServletRequest request) {
		request.setAttribute(WebKeys.PAGE_TITLE, title);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 */
	public void setPortalPort(HttpServletRequest request) {
		if (_portalPort.intValue() == -1) {
			synchronized (_portalPort) {
				_portalPort = new Integer(request.getServerPort());
			}
		}
	}

	public void storePreferences(PortletPreferences preferences)
		throws IOException, ValidatorException {

		PortletPreferencesWrapper preferencesWrapper =
			(PortletPreferencesWrapper)preferences;

		PortletPreferencesImpl preferencesImpl =
			preferencesWrapper.getPreferencesImpl();

		preferencesImpl.store();
	}

	public String transformCustomSQL(String sql) {
		if ((_customSqlClassNames == null) ||
			(_customSqlClassNameIds == null)) {

			_initCustomSQL();
		}

		return StringUtil.replace(
			sql, _customSqlClassNames, _customSqlClassNameIds);
	}

	public PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if (portletMode == null || Validator.isNull(portletMode.toString())) {
			if (layoutType.hasModeAboutPortletId(portletId)) {
				return LiferayPortletMode.ABOUT;
			}
			else if (layoutType.hasModeConfigPortletId(portletId)) {
				return LiferayPortletMode.CONFIG;
			}
			else if (layoutType.hasModeEditPortletId(portletId)) {
				return PortletMode.EDIT;
			}
			else if (layoutType.hasModeEditDefaultsPortletId(portletId)) {
				return LiferayPortletMode.EDIT_DEFAULTS;
			}
			else if (layoutType.hasModeEditGuestPortletId(portletId)) {
				return LiferayPortletMode.EDIT_GUEST;
			}
			else if (layoutType.hasModeHelpPortletId(portletId)) {
				return PortletMode.HELP;
			}
			else if (layoutType.hasModePreviewPortletId(portletId)) {
				return LiferayPortletMode.PREVIEW;
			}
			else if (layoutType.hasModePrintPortletId(portletId)) {
				return LiferayPortletMode.PRINT;
			}
			else {
				return PortletMode.VIEW;
			}
		}
		else {
			boolean updateLayout = false;

			if (portletMode.equals(LiferayPortletMode.ABOUT) &&
				!layoutType.hasModeAboutPortletId(portletId)) {

				layoutType.addModeAboutPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.CONFIG) &&
					 !layoutType.hasModeConfigPortletId(portletId)) {

				layoutType.addModeConfigPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.EDIT) &&
					 !layoutType.hasModeEditPortletId(portletId)) {

				layoutType.addModeEditPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS) &&
					 !layoutType.hasModeEditDefaultsPortletId(portletId)) {

				layoutType.addModeEditDefaultsPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST) &&
					 !layoutType.hasModeEditGuestPortletId(portletId)) {

				layoutType.addModeEditGuestPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.HELP) &&
					 !layoutType.hasModeHelpPortletId(portletId)) {

				layoutType.addModeHelpPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PREVIEW) &&
					 !layoutType.hasModePreviewPortletId(portletId)) {

				layoutType.addModePreviewPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(LiferayPortletMode.PRINT) &&
					 !layoutType.hasModePrintPortletId(portletId)) {

				layoutType.addModePrintPortletId(portletId);

				updateLayout = true;
			}
			else if (portletMode.equals(PortletMode.VIEW) &&
					 !layoutType.hasModeViewPortletId(portletId)) {

				layoutType.removeModesPortletId(portletId);

				updateLayout = true;
			}

			if (updateLayout) {
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}

			return portletMode;
		}
	}

	public WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		LayoutTypePortlet layoutType =
			(LayoutTypePortlet)layout.getLayoutType();

		if ((windowState == null) ||
			(Validator.isNull(windowState.toString()))) {

			if (layoutType.hasStateMaxPortletId(portletId)) {
				return WindowState.MAXIMIZED;
			}
			else if (layoutType.hasStateMinPortletId(portletId)) {
				return WindowState.MINIMIZED;
			}
			else {
				return WindowState.NORMAL;
			}
		}
		else {
			boolean updateLayout = false;

			if (windowState.equals(WindowState.MAXIMIZED) &&
				!layoutType.hasStateMaxPortletId(portletId)) {

				layoutType.addStateMaxPortletId(portletId);

				if (PropsValues.LAYOUT_REMEMBER_MAXIMIZED_WINDOW_STATE) {
					updateLayout = true;
				}
			}
			else if (windowState.equals(WindowState.MINIMIZED) &&
					 !layoutType.hasStateMinPortletId(portletId)) {

				layoutType.addStateMinPortletId(portletId);

				updateLayout = true;
			}
			else if (windowState.equals(WindowState.NORMAL) &&
					 !layoutType.hasStateNormalPortletId(portletId)) {

				layoutType.removeStatesPortletId(portletId);

				updateLayout = true;
			}

			if (portletId.equals(PortletKeys.LAYOUT_MANAGEMENT) ||
				portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {

				updateLayout = false;
			}

			if (updateLayout) {
				LayoutClone layoutClone = LayoutCloneFactory.getInstance();

				if (layoutClone != null) {
					layoutClone.update(
						request, layout.getPlid(), layout.getTypeSettings());
				}
			}

			return windowState;
		}
	}

	protected List<Portlet> filterControlPanelPortlets(
		Set<Portlet> portlets, String category, ThemeDisplay themeDisplay) {

		Group group = themeDisplay.getScopeGroup();

		List<Portlet> filteredPortlets = new ArrayList<Portlet>();

		if (category.equals(PortletCategoryKeys.CONTENT) && group.isLayout()) {
			for (Portlet portlet : portlets) {
				if (portlet.isScopeable()) {
					filteredPortlets.add(portlet);
				}
			}
		}
		else {
			filteredPortlets.addAll(portlets);
		}

		Iterator<Portlet> itr = filteredPortlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			try {
				ControlPanelEntry controlPanelEntry =
					portlet.getControlPanelEntryInstance();

				if (controlPanelEntry == null) {
					controlPanelEntry =
						DefaultControlPanelEntryFactory.getInstance();
				}

				if (!controlPanelEntry.isVisible(
						portlet, category, themeDisplay)) {

					itr.remove();
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				itr.remove();
			}
		}

		return filteredPortlets;
	}

	protected long getDoAsUserId(
			HttpServletRequest request, String doAsUserIdString,
			boolean alwaysAllowDoAsUser)
		throws Exception {

		if (Validator.isNull(doAsUserIdString)) {
			return 0;
		}

		long doAsUserId = 0;

		try {
			Company company = getCompany(request);

			doAsUserId = GetterUtil.getLong(
				Encryptor.decrypt(company.getKeyObj(), doAsUserIdString));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to impersonate " + doAsUserIdString +
						" because the string cannot be decrypted",
					e);
			}

			return 0;
		}

		if (_log.isDebugEnabled()) {
			if (alwaysAllowDoAsUser) {
				_log.debug(
					"doAsUserId path or Struts action is always allowed");
			}
			else {
				_log.debug(
					"doAsUserId path is Struts action not always allowed");
			}
		}

		if (alwaysAllowDoAsUser) {
			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}

		HttpSession session = request.getSession();

		Long realUserIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (realUserIdObj == null) {
			return 0;
		}

		User doAsUser = UserLocalServiceUtil.getUserById(doAsUserId);

		long[] organizationIds = doAsUser.getOrganizationIds();

		User realUser = UserLocalServiceUtil.getUserById(
			realUserIdObj.longValue());
		boolean checkGuest = true;

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(realUser, checkGuest);

		if (doAsUser.isDefaultUser() ||
			UserPermissionUtil.contains(
				permissionChecker, doAsUserId, organizationIds,
				ActionKeys.IMPERSONATE)) {

			request.setAttribute(WebKeys.USER_ID, new Long(doAsUserId));

			return doAsUserId;
		}
		else {
			_log.error(
				"User " + realUserIdObj + " does not have the permission " +
					"to impersonate " + doAsUserId);

			return 0;
		}
	}

	private long _getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		long scopeGroupId = groupId;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (group.isLayout()) {
				Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
					group.getClassPK());

				groupId = scopeLayout.getGroupId();
			}
		}
		catch (Exception e) {
		}

		long plid = LayoutConstants.DEFAULT_PLID;

		try {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, LayoutConstants.TYPE_PORTLET);

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(portletId)) {
					if (getScopeGroupId(layout, portletId) == scopeGroupId) {
						plid = layout.getPlid();

						break;
					}
				}
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se.getMessage(), se);
			}
		}

		return plid;
	}

	private String _getPortletParam(HttpServletRequest request, String name) {
		String value = null;

		int valueCount = 0;

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String curName = enu.nextElement();

			int pos = curName.indexOf(StringPool.UNDERLINE + name);

			if (pos != -1) {
				valueCount++;

				// There should never be more than one value

				if (valueCount > 1) {
					return StringPool.BLANK;
				}

				String curValue = ParamUtil.getString(request, curName);

				if (Validator.isNotNull(curValue)) {

					// The Struts action must be for the correct portlet

					String portletId1 = curName.substring(1, pos);
					String portletId2 = ParamUtil.getString(request, "p_p_id");

					if (portletId1.equals(portletId2)) {
						value = curValue;
					}
				}
			}
		}

		if (value == null) {
			value = StringPool.BLANK;
		}

		return value;
	}

	private String _getServletURL(
		Portlet portlet, String servletPath, ThemeDisplay themeDisplay) {

		String layoutURL = getLayoutURL(themeDisplay);

		Layout layout = themeDisplay.getLayout();

		StringBundler sb = new StringBundler();

		if (HttpUtil.hasDomain(layoutURL)) {
			String protocol = HttpUtil.getProtocol(layoutURL);
			String domain = HttpUtil.getDomain(layoutURL);
			HttpUtil.removeDomain(layoutURL);

			sb.append(protocol);
			sb.append(Http.PROTOCOL_DELIMITER);
			sb.append(domain);

			if (Validator.isNotNull(_pathContext)) {
				sb.append(_pathContext);
			}

			if (themeDisplay.isI18n()) {
				sb.append(themeDisplay.getI18nPath());
			}

			sb.append(servletPath);
			sb.append(layout.getFriendlyURL());
		}
		else {
			sb.append(themeDisplay.getPortalURL());

			if (Validator.isNotNull(_pathContext)) {
				sb.append(_pathContext);
			}

			if (themeDisplay.isI18n()) {
				sb.append(themeDisplay.getI18nPath());
			}

			sb.append(servletPath);

			Group group = layout.getGroup();

			if (layout.isPrivateLayout()) {
				if (group.isUser()) {
					sb.append(_PRIVATE_USER_SERVLET_MAPPING);
				}
				else {
					sb.append(_PRIVATE_GROUP_SERVLET_MAPPING);
				}
			}
			else {
				sb.append(_PUBLIC_GROUP_SERVLET_MAPPING);
			}

			sb.append(group.getFriendlyURL());
			sb.append(layout.getFriendlyURL());
		}

		sb.append(FRIENDLY_URL_SEPARATOR);

		FriendlyURLMapper friendlyURLMapper =
			portlet.getFriendlyURLMapperInstance();

		if ((friendlyURLMapper != null) && !portlet.isInstanceable()) {
			sb.append(friendlyURLMapper.getMapping());
		}
		else {
			sb.append(portlet.getPortletId());
		}

		return sb.toString();
	}

	private void _initCustomSQL() {
		_customSqlClassNames = new String[] {
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.GROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ORGANIZATION$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.ROLE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USER$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTAL.MODEL.USERGROUP$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BLOGS.MODEL.BLOGSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.BOOKMARKS.MODEL." +
				"BOOKMARKSENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.CALENDAR.MODEL.CALEVENT$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.DOCUMENTLIBRARY.MODEL." +
				"DLFILEENTRY$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.IMAGEGALLERY.MODEL.IGIMAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.MESSAGEBOARDS.MODEL." +
				"MBMESSAGE$]",
			"[$CLASS_NAME_ID_COM.LIFERAY.PORTLET.WIKI.MODEL.WIKIPAGE$]",
			"[$FALSE$]",
			"[$TRUE$]"
		};

		DB db = DBFactoryUtil.getDB();

		_customSqlClassNameIds = new String[] {
			String.valueOf(PortalUtil.getClassNameId(Group.class)),
			String.valueOf(PortalUtil.getClassNameId(Organization.class)),
			String.valueOf(PortalUtil.getClassNameId(Role.class)),
			String.valueOf(PortalUtil.getClassNameId(User.class)),
			String.valueOf(PortalUtil.getClassNameId(UserGroup.class)),
			String.valueOf(PortalUtil.getClassNameId(BlogsEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(BookmarksEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(CalEvent.class)),
			String.valueOf(PortalUtil.getClassNameId(DLFileEntry.class)),
			String.valueOf(PortalUtil.getClassNameId(IGImage.class)),
			String.valueOf(PortalUtil.getClassNameId(MBMessage.class)),
			String.valueOf(PortalUtil.getClassNameId(WikiPage.class)),
			db.getTemplateFalse(),
			db.getTemplateTrue()
		};
	}

	private static final String _J_SECURITY_CHECK = "j_security_check";

	private static final String _JSESSIONID = ";jsessionid=";

	private static final String _LOCALHOST = "localhost";

	private static final String  _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final Date _UP_TIME = new Date();

	private static Log _log = LogFactoryUtil.getLog(PortalImpl.class);

	private String[] _allSystemCommunityRoles;
	private String[] _allSystemGroups;
	private String[] _allSystemOrganizationRoles;
	private String[] _allSystemRoles;
	private String _cdnHostHttp;
	private String _cdnHostHttps;
	private String _computerAddress;
	private String _computerName;
	private String[] _customSqlClassNameIds;
	private String[] _customSqlClassNames;
	private String _globalLibDir;
	private String _pathContext;
	private String _pathFriendlyURLPrivateGroup;
	private String _pathFriendlyURLPrivateUser;
	private String _pathFriendlyURLPublic;
	private String _pathImage;
	private String _pathMain;
	private Map<String, Long> _plidToPortletIdCache =
		new ConcurrentHashMap<String, Long>();
	private String _portalLibDir;
	private Integer _portalPort = new Integer(-1);
	private String _portalWebDir;
	private Set<String> _reservedParams;
	private String[] _sortedSystemCommunityRoles;
	private String[] _sortedSystemGroups;
	private String[] _sortedSystemOrganizationRoles;
	private String[] _sortedSystemRoles;

}