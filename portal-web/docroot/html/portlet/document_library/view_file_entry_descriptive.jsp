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
FileEntry fileEntry = (FileEntry)request.getAttribute("view_entries.jsp-fileEntry");

PortletURL tempRowURL = (PortletURL)request.getAttribute("view_entries.jsp-tempRowURL");

String thumbnailSrc = themeDisplay.getPathThemeImages() + "/file_system/large/" + DLUtil.getGenericName(fileEntry.getExtension()) + ".png";
String thumbnailStyle = "width: " + PropsValues.DL_FILE_ENTRY_THUMBNAIL_WIDTH + ";";

if (ImageProcessor.hasSmallImage(fileEntry.getFileVersion())) {
	Image smallImage = ImageLocalServiceUtil.getImage(fileEntry.getSmallImageId());

	long smallImageId = 0;
	int smallImageHeight = 100;
	int smallImageWidth = 100;

	if (smallImage != null) {
		smallImageId = smallImage.getImageId();
		smallImageHeight = smallImage.getHeight();
		smallImageWidth = smallImage.getWidth();
	}

	int topMargin = PrefsPropsUtil.getInteger(PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION) - smallImageHeight;
	int sideMargin = (PrefsPropsUtil.getInteger(PropsKeys.IG_IMAGE_THUMBNAIL_MAX_DIMENSION) - smallImageWidth) / 2;

	thumbnailSrc = themeDisplay.getPathImage() + "/image_gallery?img_id=" + smallImageId +"&fileEntryId=" + fileEntry.getFileEntryId() + "&dlSmallImage=1&t=" + ImageServletTokenUtil.getToken(smallImageId);
	thumbnailStyle = "height: " + smallImageHeight + "; margin: " + topMargin + "px " + sideMargin + "px 0px " + sideMargin + "px; width: " + smallImageWidth + ";";
}
else if (PDFProcessor.hasImages(fileEntry, fileEntry.getVersion())) {
	thumbnailSrc = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&documentThumbnail=1";
}
else if (VideoProcessor.hasVideo(fileEntry, fileEntry.getVersion())) {
	thumbnailSrc = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&videoThumbnail=1";
}

boolean showCheckBox = DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE);
%>

<div class="document-display-style descriptive <%= showCheckBox ? "selectable" : StringPool.BLANK %>">
	<a class="document-link" data-folder="<%= Boolean.FALSE.toString() %>" href="<%= tempRowURL.toString() %>" title="<%= HtmlUtil.escapeAttribute(HtmlUtil.unescape(fileEntry.getTitle()) + " - " + HtmlUtil.unescape(fileEntry.getDescription())) %>">
		<span class="document-thumbnail">
			<img alt="" border="no" src="<%= thumbnailSrc %>" style="<%= thumbnailStyle %>" />

			<c:if test="<%= fileEntry.isCheckedOut() %>">
				<img alt="<liferay-ui:message key="locked" />" class="locked-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/large/overlay_lock.png">
			</c:if>
		</span>

		<span class="document-title"><%= fileEntry.getTitle() %></span>

		<span class="document-description"><%= fileEntry.getDescription() %></span>
	</a>

	<liferay-util:include page="/html/portlet/document_library/file_entry_action.jsp" />

	<c:if test="<%= showCheckBox %>">
		<aui:input cssClass="overlay document-selector" label="" name="<%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>" type="checkbox" value="<%= fileEntry.getFileEntryId() %>" />
	</c:if>
</div>