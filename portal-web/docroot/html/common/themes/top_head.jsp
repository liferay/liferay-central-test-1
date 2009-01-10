<%
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
%>

<%@ include file="/html/common/init.jsp" %>

<%@ include file="/html/common/themes/top_meta.jspf" %>
<%@ include file="/html/common/themes/top_meta-ext.jsp" %>

<link rel="Shortcut Icon" href="<%= themeDisplay.getPathThemeImages() %>/<%= PropsValues.THEME_SHORTCUT_ICON %>" />

<link href="<%= themeDisplay.getCDNHost() %><%= themeDisplay.getPathContext() %><%= Portal.PATH_MAIN %>/portal/css_cached?themeId=<%= theme.getThemeId() %>&amp;colorSchemeId=<%= colorScheme.getColorSchemeId() %>&amp;t=<%= theme.getTimestamp() %>" type="text/css" rel="stylesheet" />

<%
List<Portlet> portlets = null;

if (layout != null) {
	String type = layout.getType();

	if (type.equals(LayoutConstants.TYPE_PORTLET)) {
		portlets = layoutTypePortlet.getAllPortlets();

		if (themeDisplay.isStateMaximized()) {
			String ppid = ParamUtil.getString(request, "p_p_id");

			if (Validator.isNotNull(ppid)) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

				if (!portlets.contains(portlet)) {
					portlets.add(portlet);
				}
			}
		}
	}
	else if (type.equals(LayoutConstants.TYPE_CONTROL_PANEL) || type.equals(LayoutConstants.TYPE_PANEL)) {
		portlets = new ArrayList<Portlet>();

		String ppid = ParamUtil.getString(request, "p_p_id");

		if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
			ppid = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");
		}

		if (Validator.isNotNull(ppid)) {
			portlets.add(PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid));
		}
	}

	request.setAttribute(WebKeys.LAYOUT_PORTLETS, portlets);
}
%>

<c:if test="<%= portlets != null %>">

	<%

	List<String> allMarkupHeaders = (List<String>) request.getAttribute(MimeResponse.MARKUP_HEAD_ELEMENT);

	if (allMarkupHeaders != null) {

		for (String markupHeader : allMarkupHeaders) {
	%>

			<%= markupHeader %>

	<%
		}
	}
	%>

</c:if>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalCssList = portlet.getHeaderPortalCss();

		for (String headerPortalCss : headerPortalCssList) {
			String headerPortalCssPath = request.getContextPath() + headerPortalCss;

			if (!headerPortalCssPaths.contains(headerPortalCssPath)) {
				headerPortalCssPaths.add(headerPortalCssPath);

				if (headerPortalCssPath.endsWith(".jsp")) {
					headerPortalCssPath += "?themeId=" + theme.getThemeId() + "&amp;colorSchemeId=" + colorScheme.getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					headerPortalCssPath += "?t=" + portlet.getTimestamp();
				}

				if (themeDisplay.isThemeCssFastLoad()) {
					headerPortalCssPath += "&amp;minifierType=css";
				}
	%>

				<link href="<%= headerPortalCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}

	Set<String> headerPortletCssPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortletCssList = portlet.getHeaderPortletCss();

		for (String headerPortletCss : headerPortletCssList) {
			String headerPortletCssPath = portlet.getContextPath() + headerPortletCss;

			if (!headerPortletCssPaths.contains(headerPortletCssPath)) {
				headerPortletCssPaths.add(headerPortletCssPath);

				if (headerPortletCssPath.endsWith(".jsp")) {
					headerPortletCssPath += "?themeId=" + theme.getThemeId() + "&amp;colorSchemeId=" + colorScheme.getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					headerPortletCssPath += "?t=" + portlet.getTimestamp();
				}

				if (themeDisplay.isThemeCssFastLoad()) {
					headerPortletCssPath += "&amp;minifierType=css";
				}
	%>

				<link href="<%= headerPortletCssPath %>" rel="stylesheet" type="text/css" />

	<%
			}
		}
	}
	%>

	<style type="text/css">

		<%
		for (Portlet portlet : portlets) {
			PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portlet.getPortletId());

			String portletSetupCss = portletSetup.getValue("portlet-setup-css", StringPool.BLANK);
		%>

			<c:if test="<%= Validator.isNotNull(portletSetupCss) %>">

				<%
				try {
				%>

					<%@ include file="/html/common/themes/portlet_css.jspf" %>

				<%
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage());
					}
				}
				%>

			</c:if>

		<%
		}
		%>

	</style>
</c:if>

<c:if test="<%= (layout != null) && Validator.isNotNull(layout.getCssText()) %>">
	<style type="text/css">
		<%= layout.getCssText() %>
	</style>
</c:if>

<%@ include file="/html/common/themes/top_js.jspf" %>
<%@ include file="/html/common/themes/top_js-ext.jspf" %>

<c:if test="<%= portlets != null %>">

	<%
	Set<String> headerPortalJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortalJavaScriptList = portlet.getHeaderPortalJavaScript();

		for (String headerPortalJavaScript : headerPortalJavaScriptList) {
			String headerPortalJavaScriptPath = request.getContextPath() + headerPortalJavaScript;

			if (!headerPortalJavaScriptPaths.contains(headerPortalJavaScriptPath) && !themeDisplay.isIncludedJs(headerPortalJavaScriptPath)) {
				headerPortalJavaScriptPaths.add(headerPortalJavaScriptPath);

				if (headerPortalJavaScriptPath.endsWith(".jsp")) {
					headerPortalJavaScriptPath += "?themeId=" + theme.getThemeId() + "&amp;colorSchemeId=" + colorScheme.getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					headerPortalJavaScriptPath += "?t=" + portlet.getTimestamp();
				}

				if (themeDisplay.isThemeJsFastLoad()) {
					headerPortalJavaScriptPath += "&amp;minifierType=js";
				}
	%>

				<script src="<%= headerPortalJavaScriptPath %>" type="text/javascript"></script>

	<%
			}
		}
	}

	Set<String> headerPortletJavaScriptPaths = new LinkedHashSet<String>();

	for (Portlet portlet : portlets) {
		List<String> headerPortletJavaScriptList = portlet.getHeaderPortletJavaScript();

		for (String headerPortletJavaScript : headerPortletJavaScriptList) {
			String headerPortletJavaScriptPath = portlet.getContextPath() + headerPortletJavaScript;

			if (!headerPortletJavaScriptPaths.contains(headerPortletJavaScriptPath)) {
				headerPortletJavaScriptPaths.add(headerPortletJavaScriptPath);

				if (headerPortletJavaScriptPath.endsWith(".jsp")) {
					headerPortletJavaScriptPath += "?themeId=" + theme.getThemeId() + "&amp;colorSchemeId=" + colorScheme.getColorSchemeId() + "&amp;t=" + portlet.getTimestamp();
				}
				else {
					headerPortletJavaScriptPath += "?t=" + portlet.getTimestamp();
				}

				if (themeDisplay.isThemeJsFastLoad()) {
					headerPortletJavaScriptPath += "&amp;minifierType=js";
				}
	%>

				<script src="<%= headerPortletJavaScriptPath %>" type="text/javascript"></script>

	<%
			}
		}
	}
	%>

</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.common.themes.top_head.jsp");
%>