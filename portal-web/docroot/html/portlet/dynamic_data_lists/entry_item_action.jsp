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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLEntryItem entryItem = (DDLEntryItem)row.getObject();
%>

<liferay-ui:icon-menu>
	<portlet:renderURL var="editEntryItemURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/edit_entry_item" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryItemId" value="<%= String.valueOf(entryItem.getEntryItemId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		image="edit"
		url="<%= editEntryItemURL %>"
	/>

	<portlet:actionURL var="deleteEntryItemURL">
		<portlet:param name="struts_action" value="/dynamic_data_mapping_list/edit_entry_item" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryItemId" value="<%= String.valueOf(entryItem.getEntryItemId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteEntryItemURL %>" />
</liferay-ui:icon-menu>