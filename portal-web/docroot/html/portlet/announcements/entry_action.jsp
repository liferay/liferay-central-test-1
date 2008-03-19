<%
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
%>

<%@ include file="/html/portlet/announcements/init.jsp" %>

<%
	String strutsAction = "/announcements/edit_entry";

if (portletName.equals(PortletKeys.ALERTS)) {
	strutsAction = "/alerts/edit_entry";
}

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AnnouncementsEntry entry = (AnnouncementsEntry)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= AnnouncementsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
			<portlet:param name="struts_action" value="<%= strutsAction %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon image="edit" url="<%= editEntryURL %>" label="<%= true %>" />
	</c:if>

	<c:if test="<%= AnnouncementsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteEntryURL">
			<portlet:param name="struts_action" value="<%= strutsAction %>" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteEntryURL %>" label="<%= true %>" />
	</c:if>
</liferay-ui:icon-menu>