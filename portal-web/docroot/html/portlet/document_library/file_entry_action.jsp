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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = null;
DLFileShortcut fileShortcut = null;

boolean showWhenSingleIcon = false;

if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	showWhenSingleIcon = true;
}

if (row != null) {
	Object result = row.getObject();

	if (result instanceof AssetEntry) {
		AssetEntry assetEntry = (AssetEntry)result;

		if (assetEntry.getClassName().equals(DLFileEntryConstants.getClassName())) {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(assetEntry.getClassPK());
		}
		else {
			fileShortcut = DLAppLocalServiceUtil.getFileShortcut(assetEntry.getClassPK());
		}
	}
	else if (result instanceof FileEntry) {
		fileEntry = (FileEntry)result;
	}
	else {
		fileShortcut = (DLFileShortcut)result;
	}
}
else {
	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {
		if (request.getAttribute("view_file_entry.jsp-fileEntry") != null) {
			fileEntry = (FileEntry)request.getAttribute("view_file_entry.jsp-fileEntry");

			if (request.getAttribute("view_file_entry.jsp-fileShortcut") != null) {
				fileShortcut = (DLFileShortcut)request.getAttribute("view_file_entry.jsp-fileShortcut");
			}
		}
		else {
			fileShortcut = (DLFileShortcut)request.getAttribute("view_file_shortcut.jsp-fileShortcut");
		}
	}
	else {
		if (request.getAttribute("view_entries.jsp-fileEntry") != null) {
			fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

			if (request.getAttribute("view_entries.jsp-fileShortcut") != null) {
				fileShortcut = (DLFileShortcut)request.getAttribute("view_entries.jsp-fileShortcut");
			}

		}
		else {
			fileShortcut = (DLFileShortcut)request.getAttribute("view_file_shortcut.jsp-fileShortcut");
		}
	}
}

long folderId = 0;

if (fileEntry != null) {
	folderId = fileEntry.getFolderId();
}
else if (fileShortcut != null) {
	folderId = fileShortcut.getFolderId();
}

PortletURL viewFolderURL = liferayPortletResponse.createRenderURL();

viewFolderURL.setParameter("struts_action", "/document_library/view");
viewFolderURL.setParameter("folderId", String.valueOf(folderId));

if (fileShortcut != null) {
	fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
}
%>

<liferay-util:buffer var="iconMenu">
	<liferay-ui:icon-menu align='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? "right" : "auto" %>' direction='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? null : "down" %>' extended="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? true : false %>" icon="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? null : StringPool.BLANK %>" message='<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ? "actions" : StringPool.BLANK %>' showExpanded="<%= false %>" showWhenSingleIcon="<%= showWhenSingleIcon %>">
		<%@ include file="/html/portlet/document_library/action/download.jspf" %>
		<%@ include file="/html/portlet/document_library/action/open_document.jspf" %>
		<%@ include file="/html/portlet/document_library/action/view_original.jspf" %>
		<%@ include file="/html/portlet/document_library/action/edit.jspf" %>
		<%@ include file="/html/portlet/document_library/action/move.jspf" %>
		<%@ include file="/html/portlet/document_library/action/lock.jspf" %>
		<%@ include file="/html/portlet/document_library/action/permissions.jspf" %>
		<%@ include file="/html/portlet/document_library/action/delete.jspf" %>
	</liferay-ui:icon-menu>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.DOCUMENT_LIBRARY_DISPLAY) %>">

		<%= iconMenu %>

	</c:when>
	<c:otherwise>
		<span class="overlay document-action">

			<%= iconMenu %>

		</span>
	</c:otherwise>
</c:choose>