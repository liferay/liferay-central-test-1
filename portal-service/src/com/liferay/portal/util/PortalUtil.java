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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.IOException;
import java.io.Serializable;

import java.sql.SQLException;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PreferencesValidator;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * <a href="PortalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class PortalUtil {

	/**
	 * Adds the description for a page. This appends to the existing page
	 * description.
	 */
	public static void addPageDescription(
		String description, HttpServletRequest request) {

		getPortal().addPageDescription(description, request);
	}

	/**
	 * Adds the keywords for a page. This appends to the existing page keywords.
	 */
	public static void addPageKeywords(
		String keywords, HttpServletRequest request) {

		getPortal().addPageKeywords(keywords, request);
	}

	/**
	 * Adds the subtitle for a page. This appends to the existing page subtitle.
	 */
	public static void addPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().addPageSubtitle(subtitle, request);
	}

	/**
	 * Adds the whole title for a page. This appends to the existing page whole
	 * title.
	 */
	public static void addPageTitle(String title, HttpServletRequest request) {
		getPortal().addPageTitle(title, request);
	}

	public static void addPortletBreadcrumbEntry(
		HttpServletRequest request, String title, String url) {

		getPortal().addPortletBreadcrumbEntry(request, title, url);
	}

	public static void clearRequestParameters(RenderRequest renderRequest) {
		getPortal().clearRequestParameters(renderRequest);
	}

	public static void copyRequestParameters(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		getPortal().copyRequestParameters(actionRequest, actionResponse);
	}

	public static String getCDNHost() {
		return getPortal().getCDNHost();
	}

	public static String getClassName(long classNameId) {
		return getPortal().getClassName(classNameId);
	}

	public static long getClassNameId(Class<?> classObj) {
		return getPortal().getClassNameId(classObj);
	}

	public static long getClassNameId(String value) {
		return getPortal().getClassNameId(value);
	}

	public static String getClassNamePortletId(String className) {
		return getPortal().getClassNamePortletId(className);
	}

	public static String getCommunityLoginURL(ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getCommunityLoginURL(themeDisplay);
	}

	public static String[] getCommunityPermissions(HttpServletRequest request) {
		return getPortal().getCommunityPermissions(request);
	}

	public static String[] getCommunityPermissions(
		PortletRequest portletRequest) {

		return getPortal().getCommunityPermissions(portletRequest);
	}

	public static Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getCompany(request);
	}

	public static Company getCompany(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getCompany(portletRequest);
	}

	public static long getCompanyId(HttpServletRequest request) {
		return getPortal().getCompanyId(request);
	}

	public static long getCompanyId(PortletRequest portletRequest) {
		return getPortal().getCompanyId(portletRequest);
	}

	public static long[] getCompanyIds() {
		return getPortal().getCompanyIds();
	}

	public static String getComputerAddress() {
		return getPortal().getComputerAddress();
	}

	public static String getComputerName() {
		return getPortal().getComputerName();
	}

	public static String getControlPanelCategory(
			String portletId, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().getControlPanelCategory(portletId, themeDisplay);
	}

	public static List<Portlet> getControlPanelPortlets(
			String category, ThemeDisplay themeDisplay)
		throws SystemException {

		return getPortal().getControlPanelPortlets(
			category, themeDisplay);
	}

	public static String getCurrentCompleteURL(HttpServletRequest request) {
		return getPortal().getCurrentCompleteURL(request);
	}

	public static String getCurrentURL(HttpServletRequest request) {
		return getPortal().getCurrentURL(request);
	}

	public static String getCurrentURL(PortletRequest portletRequest) {
		return getPortal().getCurrentURL(portletRequest);
	}

	public static String getCustomSQLFunctionIsNotNull() {
		return getPortal().getCustomSQLFunctionIsNotNull();
	}

	public static String getCustomSQLFunctionIsNull() {
		return getPortal().getCustomSQLFunctionIsNull();
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, pe);
	}

	public static Date getDate(
			int month, int day, int year, int hour, int min, TimeZone timeZone,
			PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, hour, min, timeZone, pe);
	}

	public static Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, pe);
	}

	public static long getDefaultCompanyId() {
		return getPortal().getDefaultCompanyId();
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
			ExpandoBridge expandoBridge, PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getExpandoBridgeAttributes(
			expandoBridge, portletRequest);
	}

	public static String getFirstPageLayoutTypes(PageContext pageContext) {
		return getPortal().getFirstPageLayoutTypes(pageContext);
	}

	public static String getGoogleGadgetURL(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return getPortal().getGoogleGadgetURL(portlet, themeDisplay);
	}

	public static String[] getGuestPermissions(HttpServletRequest request) {
		return getPortal().getGuestPermissions(request);
	}

	public static String[] getGuestPermissions(PortletRequest portletRequest) {
		return getPortal().getGuestPermissions(portletRequest);
	}

	public static String getHomeURL(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getHomeURL(request);
	}

	public static String getHost(HttpServletRequest request) {
		return getPortal().getHost(request);
	}

	public static String getHost(PortletRequest portletRequest) {
		return getPortal().getHost(portletRequest);
	}

	public static HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return getPortal().getHttpServletRequest(portletRequest);
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		return getPortal().getHttpServletResponse(portletResponse);
	}

	public static String getJsSafePortletId(String portletId) {
		return getPortal().getJsSafePortletId(portletId);
	}

	public static String getLayoutActualURL(Layout layout) {
		return getPortal().getLayoutActualURL(layout);
	}

	public static String getLayoutActualURL(Layout layout, String mainPath) {
		return getPortal().getLayoutActualURL(layout, mainPath);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL);
	}

	public static String getLayoutActualURL(
			long groupId, boolean privateLayout, String mainPath,
			String friendlyURL, Map<String, String[]> params)
		throws PortalException, SystemException {

		return getPortal().getLayoutActualURL(
			groupId, privateLayout, mainPath, friendlyURL, params);
	}

	public static String getLayoutEditPage(Layout layout) {
		return getPortal().getLayoutEditPage(layout);
	}

	public static String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay) {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public static String getLayoutFullURL(
		Layout layout, ThemeDisplay themeDisplay) {

		return getPortal().getLayoutFullURL(layout, themeDisplay);
	}

	public static String getLayoutFullURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		return getPortal().getLayoutFullURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutFullURL(ThemeDisplay themeDisplay) {
		return getPortal().getLayoutFullURL(themeDisplay);
	}

	public static String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getLayoutTarget(Layout layout) {
		return getPortal().getLayoutTarget(layout);
	}

	public static String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay) {

		return getPortal().getLayoutURL(layout, themeDisplay);
	}

	public static String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		return getPortal().getLayoutURL(layout, themeDisplay, doAsUser);
	}

	public static String getLayoutURL(ThemeDisplay themeDisplay) {
		return getPortal().getLayoutURL(themeDisplay);
	}

	public static String getLayoutViewPage(Layout layout) {
		return getPortal().getLayoutViewPage(layout);
	}

	public static Locale getLocale(HttpServletRequest request) {
		return getPortal().getLocale(request);
	}

	public static Locale getLocale(RenderRequest renderRequest) {
		return getPortal().getLocale(renderRequest);
	}

	public static BaseModel<?> getModel(Resource resource)
		throws PortalException, SystemException {

		return getPortal().getModel(resource);
	}

	public static BaseModel<?> getModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		return getPortal().getModel(resourcePermission);
	}

	public static BaseModel<?> getModel(String modelName, String primKey)
		throws PortalException, SystemException {

		return getPortal().getModel(modelName, primKey);
	}

	public static String getNetvibesURL(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return getPortal().getNetvibesURL(portlet, themeDisplay);
	}

	public static HttpServletRequest getOriginalServletRequest(
		HttpServletRequest request) {

		return getPortal().getOriginalServletRequest(request);
	}

	public static String getPathContext() {
		return getPortal().getPathContext();
	}

	public static String getPathFriendlyURLPrivateGroup() {
		return getPortal().getPathFriendlyURLPrivateGroup();
	}

	public static String getPathFriendlyURLPrivateUser() {
		return getPortal().getPathFriendlyURLPrivateUser();
	}

	public static String getPathFriendlyURLPublic() {
		return getPortal().getPathFriendlyURLPublic();
	}

	public static String getPathImage() {
		return getPortal().getPathImage();
	}

	public static String getPathMain() {
		return getPortal().getPathMain();
	}

	public static long getPlidFromFriendlyURL(
		long companyId, String friendlyURL) {

		return getPortal().getPlidFromFriendlyURL(companyId, friendlyURL);
	}

	public static long getPlidFromPortletId(
		long groupId, boolean privateLayout, String portletId) {

		return getPortal().getPlidFromPortletId(
			groupId, privateLayout, portletId);
	}

	public static long getPlidFromPortletId(long groupId, String portletId) {
		return getPortal().getPlidFromPortletId(groupId, portletId);
	}

	public static Portal getPortal() {
		return _portal;
	}

	public static String getPortalLibDir() {
		return getPortal().getPortalLibDir();
	}

	public static int getPortalPort() {
		return getPortal().getPortalPort();
	}

	public static Properties getPortalProperties() {
		return getPortal().getPortalProperties();
	}

	public static String getPortalURL(HttpServletRequest request) {
		return getPortal().getPortalURL(request);
	}

	public static String getPortalURL(
		HttpServletRequest request, boolean secure) {

		return getPortal().getPortalURL(request, secure);
	}

	public static String getPortalURL(PortletRequest portletRequest) {
		return getPortal().getPortalURL(portletRequest);
	}

	public static String getPortalURL(
		PortletRequest portletRequest, boolean secure) {

		return getPortal().getPortalURL(portletRequest, secure);
	}

	public static String getPortalURL(
		String serverName, int serverPort, boolean secure) {

		return getPortal().getPortalURL(serverName, serverPort, secure);
	}

	public static String getPortalURL(ThemeDisplay themeDisplay) {
		return getPortal().getPortalURL(themeDisplay);
	}

	public static String getPortalWebDir() {
		return getPortal().getPortalWebDir();
	}

	public static Map<String, String> getPortletBreadcrumbMap(
		HttpServletRequest request) {

		return getPortal().getPortletBreadcrumbMap(request);
	}

	public static String getPortletDescription(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletDescription(
			portlet, servletContext, locale);
	}

	public static String getPortletDescription(Portlet portlet, User user) {
		return getPortal().getPortletDescription(portlet, user);
	}

	public static String getPortletDescription(
		String portletId, long companyId, Locale locale) {

		return getPortal().getPortletDescription(portletId, companyId, locale);
	}

	public static String getPortletDescription(
		String portletId, long companyId, String languageId) {

		return getPortal().getPortletDescription(
			portletId, companyId, languageId);
	}

	public static String getPortletDescription(String portletId, User user) {
		return getPortal().getPortletDescription(portletId, user);
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url)
		throws PortalException, SystemException {

		return getPortal().getPortletFriendlyURLMapper(
			groupId, privateLayout, url);
	}

	public static Object[] getPortletFriendlyURLMapper(
			long groupId, boolean privateLayout, String url,
			Map<String, String[]> params)
		throws PortalException, SystemException {

		return getPortal().getPortletFriendlyURLMapper(
			groupId, privateLayout, url, params);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public static long getPortletGroupId(ActionRequest actionRequest) {
		return getPortal().getPortletGroupId(actionRequest);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public static long getPortletGroupId(HttpServletRequest request) {
		return getPortal().getPortletGroupId(request);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public static long getPortletGroupId(Layout layout) {
		return getPortal().getPortletGroupId(layout);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public static long getPortletGroupId(long plid) {
		return getPortal().getPortletGroupId(plid);
	}

	/**
	 * @deprecated Use <code>getScopeGroupId</code>.
	 */
	public static long getPortletGroupId(RenderRequest renderRequest) {
		return getPortal().getPortletGroupId(renderRequest);
	}

	public static String getPortletId(HttpServletRequest request) {
		return getPortal().getPortletId(request);
	}

	public static String getPortletId(PortletRequest portletRequest) {
		return getPortal().getPortletId(portletRequest);
	}

	public static String getPortletNamespace(String portletId) {
		return getPortal().getPortletNamespace(portletId);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portlet, companyId, locale);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, String languageId) {

		return getPortal().getPortletTitle(portlet, companyId, languageId);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletTitle(portlet, servletContext, locale);
	}

	public static String getPortletTitle(Portlet portlet, User user) {
		return getPortal().getPortletTitle(portlet, user);
	}

	public static String getPortletTitle(
		String portletId, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portletId, companyId, locale);
	}

	public static String getPortletTitle(
		String portletId, long companyId, String languageId) {

		return getPortal().getPortletTitle(portletId, companyId, languageId);
	}

	public static String getPortletTitle(String portletId, User user) {
		return getPortal().getPortletTitle(portletId, user);
	}

	public static String getPortletXmlFileName() throws SystemException {
		return getPortal().getPortletXmlFileName();
	}

	public static PortletPreferences getPreferences(
		HttpServletRequest request) {

		return getPortal().getPreferences(request);
	}

	public static PreferencesValidator getPreferencesValidator(
		Portlet portlet) {

		return getPortal().getPreferencesValidator(portlet);
	}

	public static long getScopeGroupId(HttpServletRequest request) {
		return getPortal().getScopeGroupId(request);
	}

	public static long getScopeGroupId(
		HttpServletRequest request, String portletId) {

		return getPortal().getScopeGroupId(request, portletId);
	}

	public static long getScopeGroupId(Layout layout) {
		return getPortal().getScopeGroupId(layout);
	}

	public static long getScopeGroupId(Layout layout, String portletId) {
		return getPortal().getScopeGroupId(layout, portletId);
	}

	public static long getScopeGroupId(long plid) {
		return getPortal().getScopeGroupId(plid);
	}

	public static long getScopeGroupId(PortletRequest portletRequest) {
		return getPortal().getScopeGroupId(portletRequest);
	}

	public static User getSelectedUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(request);
	}

	public static User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(request, checkPermission);
	}

	public static User getSelectedUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(portletRequest);
	}

	public static User getSelectedUser(
			PortletRequest portletRequest, boolean checkPermission)
		throws PortalException, SystemException {

		return getPortal().getSelectedUser(portletRequest, checkPermission);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri) {

		return getPortal().getStaticResourceURL(request, uri);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, long timestamp) {

		return getPortal().getStaticResourceURL(request, uri, timestamp);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString) {

		return getPortal().getStaticResourceURL(request, uri, queryString);
	}

	public static String getStaticResourceURL(
		HttpServletRequest request, String uri, String queryString,
		long timestamp) {

		return getPortal().getStaticResourceURL(
			request, uri, queryString, timestamp);
	}

	public static String getStrutsAction(HttpServletRequest request) {
		return getPortal().getStrutsAction(request);
	}

	public static String[] getSystemCommunityRoles() {
		return getPortal().getSystemCommunityRoles();
	}

	public static String[] getSystemGroups() {
		return getPortal().getSystemGroups();
	}

	public static String[] getSystemOrganizationRoles() {
		return getPortal().getSystemOrganizationRoles();
	}

	public static String[] getSystemRoles() {
		return getPortal().getSystemRoles();
	}

	public static UploadPortletRequest getUploadPortletRequest(
		ActionRequest actionRequest) {

		return getPortal().getUploadPortletRequest(actionRequest);
	}

	public static UploadServletRequest getUploadServletRequest(
		HttpServletRequest request) {

		return getPortal().getUploadServletRequest(request);
	}

	public static Date getUptime() {
		return getPortal().getUptime();
	}

	public static String getURLWithSessionId(String url, String sessionId) {
		return getPortal().getURLWithSessionId(url, sessionId);
	}

	public static User getUser(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getUser(request);
	}

	public static User getUser(PortletRequest portletRequest)
		throws PortalException, SystemException {

		return getPortal().getUser(portletRequest);
	}

	public static long getUserId(HttpServletRequest request) {
		return getPortal().getUserId(request);
	}

	public static long getUserId(PortletRequest portletRequest) {
		return getPortal().getUserId(portletRequest);
	}

	public static String getUserName(long userId, String defaultUserName) {
		return getPortal().getUserName(userId, defaultUserName);
	}

	public static String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getPortal().getUserName(userId, defaultUserName, request);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getPortal().getUserName(userId, defaultUserName, userAttribute);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		return getPortal().getUserName(
			userId, defaultUserName, userAttribute, request);
	}

	public static String getUserPassword(HttpServletRequest request) {
		return getPortal().getUserPassword(request);
	}

	public static String getUserPassword(HttpSession session) {
		return getPortal().getUserPassword(session);
	}

	public static String getUserPassword(PortletRequest portletRequest) {
		return getPortal().getUserPassword(portletRequest);
	}

	public static String getUserValue(
			long userId, String param, String defaultValue)
		throws SystemException {

		return getPortal().getUserValue(userId, param, defaultValue);
	}

	public static String getWidgetURL(
		Portlet portlet, ThemeDisplay themeDisplay) {

		return getPortal().getWidgetURL(portlet, themeDisplay);
	}

	public static boolean isLayoutFirstPageable(String type) {
		return getPortal().isLayoutFirstPageable(type);
	}

	public static boolean isLayoutFriendliable(Layout layout) {
		return getPortal().isLayoutFriendliable(layout);
	}

	public static boolean isLayoutParentable(Layout layout) {
		return getPortal().isLayoutParentable(layout);
	}

	public static boolean isLayoutParentable(String type) {
		return getPortal().isLayoutParentable(type);
	}

	public static boolean isLayoutSitemapable(Layout layout) {
		return getPortal().isLayoutSitemapable(layout);
	}

	public static boolean isMethodGet(PortletRequest portletRequest) {
		return getPortal().isMethodGet(portletRequest);
	}

	public static boolean isMethodPost(PortletRequest portletRequest) {
		return getPortal().isMethodPost(portletRequest);
	}

	public static boolean isReservedParameter(String name) {
		return getPortal().isReservedParameter(name);
	}

	public static boolean isSystemGroup(String groupName) {
		return getPortal().isSystemGroup(groupName);
	}

	public static boolean isSystemRole(String roleName) {
		return getPortal().isSystemRole(roleName);
	}

	public static boolean isUpdateAvailable() throws SystemException {
		return getPortal().isUpdateAvailable();
	}

	public static void renderPage(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			String path)
		throws IOException, ServletException {

		getPortal().renderPage(sb, servletContext, request, response, path);
	}

	public static void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sb, servletContext, request, response, portlet, queryString);
	}

	public static void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sb, servletContext, request, response, portlet, queryString,
			columnId, columnPos, columnCount);
	}

	public static void renderPortlet(
			StringBuilder sb, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, String queryString, String columnId,
			Integer columnPos, Integer columnCount, String path)
		throws IOException, ServletException {

		getPortal().renderPortlet(
			sb, servletContext, request, response, portlet, queryString,
			columnId, columnPos, columnCount, path);
	}

	public static void runSQL(String sql) throws IOException, SQLException {
		getPortal().runSQL(sql);
	}

	public static void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(e, actionRequest, actionResponse);
	}

	public static void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(e, request, response);
	}

	public static void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(status, e, actionRequest, actionResponse);
	}

	public static void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(status, e, request, response);
	}

	/**
	 * Sets the description for a page. This overrides the existing page
	 * description.
	 */
	public static void setPageDescription(
		String description, HttpServletRequest request) {

		getPortal().setPageDescription(description, request);
	}

	/**
	 * Sets the keywords for a page. This overrides the existing page keywords.
	 */
	public static void setPageKeywords(
		String keywords, HttpServletRequest request) {

		getPortal().setPageKeywords(keywords, request);
	}

	/**
	 * Sets the subtitle for a page. This overrides the existing page subtitle.
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().setPageSubtitle(subtitle, request);
	}

	/**
	 * Sets the whole title for a page. This overrides the existing page whole
	 * title.
	 */
	public static void setPageTitle(
		String title, HttpServletRequest request) {

		getPortal().setPageTitle(title, request);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 */
	public static void setPortalPort(HttpServletRequest request) {
		getPortal().setPortalPort(request);
	}

	public static void storePreferences(PortletPreferences preferences)
		throws IOException, ValidatorException {

		getPortal().storePreferences(preferences);
	}

	public static String transformCustomSQL(String sql) {
		return getPortal().transformCustomSQL(sql);
	}

	public static PortletMode updatePortletMode(
		String portletId, User user, Layout layout, PortletMode portletMode,
		HttpServletRequest request) {

		return getPortal().updatePortletMode(
			portletId, user, layout, portletMode, request);
	}

	public static WindowState updateWindowState(
		String portletId, User user, Layout layout, WindowState windowState,
		HttpServletRequest request) {

		return getPortal().updateWindowState(
			portletId, user, layout, windowState, request);
	}

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	private static Portal _portal;

}