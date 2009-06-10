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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "backURL");

redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document assetDoc = (Document)row.getObject();

Element root = assetDoc.getRootElement();

int assetEntryOrder = GetterUtil.getInteger(root.elementText("asset-order"));
boolean last = GetterUtil.getBoolean(root.elementText("last"));
%>

<c:choose>
	<c:when test="<%= (assetEntryOrder == 0) && last %>">
	</c:when>
	<c:when test="<%= (assetEntryOrder > 0) && !last %>">

		<%
		String taglibDownURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_down.png" %>' message="down" url="<%= taglibDownURL %>" />

		<%
		String taglibUpURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_up.png" %>' message="up" url="<%= taglibUpURL %>" />
	</c:when>
	<c:when test="<%= assetEntryOrder == 0 %>">

		<%
		String taglibDownURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_down.png" %>' message="down" url="<%= taglibDownURL %>" />
	</c:when>
	<c:when test="<%= last %>">

		<%
		String taglibUpURL = "javascript:" + renderResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<liferay-ui:icon src='<%= themeDisplay.getPathThemeImages() + "/arrows/02_up.png" %>' message="up" url="<%= taglibUpURL %>" />
	</c:when>
</c:choose>

<liferay-portlet:actionURL portletConfiguration="true" windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="remove-selection" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="assetEntryOrder" value="<%= String.valueOf(assetEntryOrder) %>" />
</liferay-portlet:actionURL>

<liferay-ui:icon-delete url="<%= deleteURL %>" />