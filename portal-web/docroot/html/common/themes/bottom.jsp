<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/common/init.jsp" %>

<%@ page import="com.liferay.taglib.aui.ScriptTag" %>

<%-- Portlet CSS References --%>

<%
List<Portlet> portlets = (List<Portlet>)request.getAttribute(WebKeys.LAYOUT_PORTLETS);
%>

<%@ include file="/html/common/themes/bottom_portlet_resources_css.jspf" %>

<%-- Portlet JavaScript References --%>

<%@ include file="/html/common/themes/bottom_portlet_resources_js.jspf" %>

<c:if test="<%= PropsValues.JAVASCRIPT_LOG_ENABLED %>">
	<%@ include file="/html/common/themes/bottom_js_logging.jspf" %>
</c:if>

<%@ include file="/html/common/themes/bottom_js.jspf" %>

<%@ include file="/html/common/themes/session_timeout.jspf" %>

<%
ScriptTag.flushScriptData(pageContext);
%>

<%-- Raw Text --%>

<%
StringBundler pageBottomSB = (StringBundler)request.getAttribute(WebKeys.PAGE_BOTTOM);
%>

<c:if test="<%= pageBottomSB != null %>">

	<%
	pageBottomSB.writeTo(out);
	%>

</c:if>

<%-- Theme JavaScript --%>

<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeJavaScript() + "/main.js")) %>" type="text/javascript"></script>

<c:if test="<%= layout != null %>">

	<%-- User Inputted Layout and LayoutSet JavaScript --%>

	<%
	LayoutSet layoutSet = themeDisplay.getLayoutSet();

	UnicodeProperties layoutSetSettings = layoutSet.getSettingsProperties();

	UnicodeProperties layoutTypeSettings = layout.getTypeSettingsProperties();
	%>

	<script type="text/javascript">
		// <![CDATA[
			<%= GetterUtil.getString(layoutSetSettings.getProperty("javascript")) %>

			<%= GetterUtil.getString(layoutTypeSettings.getProperty("javascript")) %>
		// ]]>
	</script>

	<%-- Google Analytics --%>

	<%
	UnicodeProperties groupTypeSettings = layout.getGroup().getTypeSettingsProperties();

	String googleAnalyticsId = groupTypeSettings.getProperty("googleAnalyticsId");

	if (Validator.isNotNull(googleAnalyticsId)) {
	%>

		<script type="text/javascript">
			var _gaq = _gaq || [];

			_gaq.push(['_setAccount', '<%= googleAnalyticsId %>']);
			_gaq.push(['_trackPageview']);

			(function() {
				var ga = document.createElement('script');

				ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
				ga.setAttribute('async', 'true');

				document.documentElement.firstChild.appendChild(ga);
			})();
		</script>

	<%
	}
	%>

</c:if>

<c:if test="<%= PropsValues.MONITORING_PORTAL_REQUEST %>">
	<%@ include file="/html/common/themes/bottom_monitoring.jspf" %>
</c:if>

<liferay-util:include page="/html/common/themes/bottom-ext.jsp" />