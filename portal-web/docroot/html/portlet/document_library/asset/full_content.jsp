<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
DLFileEntry fileEntry = (DLFileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);
%>

<div class="asset-resource-info">
	<a href="<%= themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) %>">
		<img align="left" border="0" class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= DLUtil.getFileIcon(fileEntry.getTitle()) %>.png" /><%= fileEntry.getTitle() %>
	</a>
</div>

<p class="asset-description"><%= fileEntry.getDescription() %></p>

<div class="custom-attributes">
	<liferay-ui:custom-attributes-available className="<%= DLFileEntry.class.getName() %>">
		<liferay-ui:custom-attribute-list
			className="<%= DLFileEntry.class.getName() %>"
			classPK="<%= (fileEntry != null) ? fileEntry.getFileEntryId() : 0 %>"
			editable="<%= false %>"
			label="<%= true %>"
		/>
	</liferay-ui:custom-attributes-available>
</div>