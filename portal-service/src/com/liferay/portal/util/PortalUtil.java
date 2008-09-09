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

package com.liferay.portal.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.Date;
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
 *
 */
public class PortalUtil {

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

	public static Company getCompany(HttpServletRequest request)
		throws PortalException, SystemException {

		return getPortal().getCompany(request);
	}

	public static Company getCompany(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return getPortal().getCompany(actionRequest);
	}

	public static Company getCompany(RenderRequest renderRequest)
		throws PortalException, SystemException {

		return getPortal().getCompany(renderRequest);
	}

	public static long getCompanyId(HttpServletRequest request) {
		return getPortal().getCompanyId(request);
	}

	public static long getCompanyId(ActionRequest actionRequest) {
		return getPortal().getCompanyId(actionRequest);
	}

	public static long getCompanyId(PortletRequest portletRequest) {
		return getPortal().getCompanyId(portletRequest);
	}

	public static long getCompanyId(RenderRequest renderRequest) {
		return getPortal().getCompanyId(renderRequest);
	}

	public static long getCompanyIdByWebId(ServletContext servletContext) {
		return getPortal().getCompanyIdByWebId(servletContext);
	}

	public static long getCompanyIdByWebId(String webId) {
		return getPortal().getCompanyIdByWebId(webId);
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

	public static Date getDate(int month, int day, int year, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, pe);
	}

	public static Date getDate(
			int month, int day, int year, TimeZone timeZone, PortalException pe)
		throws PortalException {

		return getPortal().getDate(month, day, year, timeZone, pe);
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

	public static String getFirstPageLayoutTypes(PageContext pageContext) {
		return getPortal().getFirstPageLayoutTypes(pageContext);
	}

	public static String getHost(HttpServletRequest request) {
		return getPortal().getHost(request);
	}

	public static String getHost(ActionRequest actionRequest) {
		return getPortal().getHost(actionRequest);
	}

	public static String getHost(RenderRequest renderRequest) {
		return getPortal().getHost(renderRequest);
	}

	public static HttpServletRequest getHttpServletRequest(
		PortletRequest portletRequest) {

		return getPortal().getHttpServletRequest(portletRequest);
	}

	public static HttpServletResponse getHttpServletResponse(
		PortletResponse portletResponse) {

		return getPortal().getHttpServletResponse(portletResponse);
	}

	public static String getLayoutEditPage(Layout layout) {
		return getPortal().getLayoutEditPage(layout);
	}

	public static String getLayoutViewPage(Layout layout) {
		return getPortal().getLayoutViewPage(layout);
	}

	public static String getLayoutURL(ThemeDisplay themeDisplay) {
		return getPortal().getLayoutURL(themeDisplay);
	}

	public static String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay) {

		return getPortal().getLayoutURL(layout, themeDisplay);
	}

	public static String getLayoutURL(
		Layout layout, ThemeDisplay themeDisplay, boolean doAsUser) {

		return getPortal().getLayoutURL(layout, themeDisplay, doAsUser);
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

	public static String getLayoutFriendlyURL(
		Layout layout, ThemeDisplay themeDisplay) {

		return getPortal().getLayoutFriendlyURL(layout, themeDisplay);
	}

	public static String getLayoutSetFriendlyURL(
			LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getPortal().getLayoutSetFriendlyURL(layoutSet, themeDisplay);
	}

	public static String getLayoutTarget(Layout layout) {
		return getPortal().getLayoutTarget(layout);
	}

	public static String getJsSafePortletId(String portletId) {
		return getPortal().getJsSafePortletId(portletId);
	}

	public static Locale getLocale(HttpServletRequest request) {
		return getPortal().getLocale(request);
	}

	public static Locale getLocale(RenderRequest renderRequest) {
		return getPortal().getLocale(renderRequest);
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

	public static String getPortalURL(ThemeDisplay themeDisplay) {
		return getPortal().getPortalURL(themeDisplay);
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

	public static String getPortalWebDir() {
		return getPortal().getPortalWebDir();
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
	public static long getPortletGroupId(long plid) {
		return getPortal().getPortletGroupId(plid);
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
	public static long getPortletGroupId(HttpServletRequest request) {
		return getPortal().getPortletGroupId(request);
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
	public static long getPortletGroupId(RenderRequest renderRequest) {
		return getPortal().getPortletGroupId(renderRequest);
	}

	public static String getPortletId(HttpServletRequest request) {
		return getPortal().getPortletId(request);
	}

	public static String getPortletId(ActionRequest actionRequest) {
		return getPortal().getPortletId(actionRequest);
	}

	public static String getPortletId(RenderRequest renderRequest) {
		return getPortal().getPortletId(renderRequest);
	}

	public static String getPortletNamespace(String portletId) {
		return getPortal().getPortletNamespace(portletId);
	}

	public static String getPortletTitle(
		String portletId, long companyId, String languageId) {

		return getPortal().getPortletTitle(portletId, companyId, languageId);
	}

	public static String getPortletTitle(
		String portletId, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portletId, companyId, locale);
	}

	public static String getPortletTitle(String portletId, User user) {
		return getPortal().getPortletTitle(portletId, user);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, String languageId) {

		return getPortletTitle(portlet, companyId, languageId);
	}

	public static String getPortletTitle(
		Portlet portlet, long companyId, Locale locale) {

		return getPortal().getPortletTitle(portlet, companyId, locale);
	}

	public static String getPortletTitle(Portlet portlet, User user) {
		return getPortal().getPortletTitle(portlet, user);
	}

	public static String getPortletTitle(
		Portlet portlet, ServletContext servletContext, Locale locale) {

		return getPortal().getPortletTitle(portlet, servletContext, locale);
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

	public static long getScopeGroupId(long plid) {
		return getPortal().getScopeGroupId(plid);
	}

	public static long getScopeGroupId(Layout layout) {
		return getPortal().getScopeGroupId(layout);
	}

	public static long getScopeGroupId(HttpServletRequest request) {
		return getPortal().getScopeGroupId(request);
	}

	public static long getScopeGroupId(ActionRequest actionRequest) {
		return getPortal().getScopeGroupId(actionRequest);
	}

	public static long getScopeGroupId(RenderRequest renderRequest) {
		return getPortal().getScopeGroupId(renderRequest);
	}

	public static User getSelectedUser(HttpServletRequest request)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(request);
	}

	public static User getSelectedUser(
			HttpServletRequest request, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(request, checkPermission);
	}

	public static User getSelectedUser(ActionRequest actionRequest)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(actionRequest);
	}

	public static User getSelectedUser(
			ActionRequest actionRequest, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(actionRequest, checkPermission);
	}

	public static User getSelectedUser(RenderRequest renderRequest)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(renderRequest);
	}

	public static User getSelectedUser(
			RenderRequest renderRequest, boolean checkPermission)
		throws PortalException, RemoteException, SystemException {

		return getPortal().getSelectedUser(renderRequest, checkPermission);
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

	public static User getUser(ActionRequest actionRequest)
		throws PortalException, SystemException {

		return getPortal().getUser(actionRequest);
	}

	public static User getUser(RenderRequest renderRequest)
		throws PortalException, SystemException {

		return getPortal().getUser(renderRequest);
	}

	public static long getUserId(HttpServletRequest request) {
		return getPortal().getUserId(request);
	}

	public static long getUserId(ActionRequest actionRequest) {
		return getPortal().getUserId(actionRequest);
	}

	public static long getUserId(RenderRequest renderRequest) {
		return getPortal().getUserId(renderRequest);
	}

	public static String getUserName(long userId, String defaultUserName) {
		return getPortal().getUserName(userId, defaultUserName);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute) {

		return getPortal().getUserName(userId, defaultUserName, userAttribute);
	}

	public static String getUserName(
		long userId, String defaultUserName, HttpServletRequest request) {

		return getPortal().getUserName(userId, defaultUserName, request);
	}

	public static String getUserName(
		long userId, String defaultUserName, String userAttribute,
		HttpServletRequest request) {

		return getPortal().getUserName(
			userId, defaultUserName, userAttribute, request);
	}

	public static String getUserPassword(HttpSession session) {
		return getPortal().getUserPassword(session);
	}

	public static String getUserPassword(HttpServletRequest request) {
		return getPortal().getUserPassword(request);
	}

	public static String getUserPassword(ActionRequest actionRequest) {
		return getPortal().getUserPassword(actionRequest);
	}

	public static String getUserPassword(RenderRequest renderRequest) {
		return getPortal().getUserPassword(renderRequest);
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

	public static boolean isUpdateAvailable()
		throws PortalException, SystemException {

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

	public static void sendError(
			Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(e, request, response);
	}

	public static void sendError(
			int status, Exception e, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		getPortal().sendError(status, e, request, response);
	}

	public static void sendError(
			Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(e, actionRequest, actionResponse);
	}

	public static void sendError(
			int status, Exception e, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws IOException {

		getPortal().sendError(status, e, actionRequest, actionResponse);
	}

	/**
	 * Sets the subtitle for a page. This is just a hint and can be overridden
	 * by subsequent calls. The last call to this method wins.
	 *
	 * @param		subtitle the subtitle for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageSubtitle(
		String subtitle, HttpServletRequest request) {

		getPortal().setPageSubtitle(subtitle, request);
	}

	/**
	 * Sets the whole title for a page. This is just a hint and can be
	 * overridden by subsequent calls. The last call to this method wins.
	 *
	 * @param		title the whole title for a page
	 * @param		req the HTTP servlet request
	 */
	public static void setPageTitle(String title, HttpServletRequest request) {
		getPortal().setPageTitle(title, request);
	}

	/**
	 * Sets the port obtained on the first request to the portal.
	 *
	 * @param		req the HTTP servlet request
	 */
	public static void setPortalPort(HttpServletRequest request) {
		getPortal().setPortalPort(request);
	}

	public static void storePreferences(PortletPreferences prefs)
		throws IOException, ValidatorException {

		getPortal().storePreferences(prefs);
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