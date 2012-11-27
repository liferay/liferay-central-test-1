<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry attachmentsFileEntry = (FileEntry)row.getObject();

DLFileEntry dlFileEntry = (DLFileEntry)attachmentsFileEntry.getModel();

long folderId = dlFileEntry.getFolderId();

Folder folder = PortletFileRepositoryUtil.getPortletFolder(folderId);

long messageId = GetterUtil.getLong(folder.getName());

MBMessage message = MBMessageLocalServiceUtil.getMBMessage(messageId);
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.ADD_FILE) %>">

		<%
		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());
		%>

		<portlet:actionURL var="restoreEntryURL">
			<portlet:param name="struts_action" value="/message_boards/restore_page_attachment" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
		</portlet:actionURL>

		<%
		String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "checkEntry', {trashEntryId: " + trashEntry.getEntryId() + ", uri: '" + restoreEntryURL.toString() + "'});";
		%>

		<liferay-ui:icon
			image="undo"
			message="restore"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/message_boards/edit_message_attachments" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="messageId" value="<%= String.valueOf(messageId) %>" />
			<portlet:param name="fileName" value="<%= dlFileEntry.getTitle() %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>