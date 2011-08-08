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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute("view_entries.jsp-folder");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");
PortletURL viewEntriesURL = (PortletURL)request.getAttribute("view_entries.jsp-viewEntriesURL");

String thumbnailSrc = themeDisplay.getPathThemeImages() + "/file_system/large/folder_full_document.png";

boolean showCheckBox = DLFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE);
%>

<div class="document-display-style descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>">
	<a class="document-link" data-folder="<%= Boolean.TRUE.toString() %>" data-folder-id="<%= folder.getFolderId() %>" data-refresh-folders="<%= Boolean.TRUE.toString() %>" data-resource-url="<%= viewEntriesURL.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escape(folder.getName()) + " - " + HtmlUtil.escape(folder.getDescription()) %>">
		<span class="document-thumbnail">
			<img alt="" border="no" src="<%= thumbnailSrc %>" style="width: <%= PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH %>;" />
		</span>

		<span class="document-title"><%= HtmlUtil.escape(folder.getName()) %></span>

		<span class="document-description"><%= HtmlUtil.escape(folder.getDescription()) %></span>
	</a>

	<liferay-util:include page="/html/portlet/document_library/folder_action.jsp" />

	<c:if test="<%= showCheckBox %>">
		<aui:input cssClass="overlay document-selector" label="" name="<%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>" type="checkbox" value="<%= folder.getFolderId() %>" />
	</c:if>
</div>