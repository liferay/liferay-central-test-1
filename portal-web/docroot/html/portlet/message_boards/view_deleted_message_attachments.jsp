<%@ page import="com.liferay.portlet.documentlibrary.util.DLAppUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>

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
MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

long messageId = BeanParamUtil.getLong(message, request, "messageId");

long categoryId = MBUtil.getCategoryId(request, message);

String redirect = ParamUtil.getString(request, "redirect");

String[] attachments = message.getDeletedAttachmentsFiles();

MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/message_boards/edit_message");
portletURL.setParameter("messageId", String.valueOf(message.getMessageId()));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), portletURL.toString());

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "deleted-attachments"), currentURL);

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("struts_action", "/message_boards/view_deleted_message_attachments");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("messageId", String.valueOf(messageId));

List attachmentsList = ListUtil.fromArray(attachments);
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="deleted-attachments"
/>

<liferay-ui:search-container
	emptyResultsMessage="this-message-does-not-have-any-file-attachments-in-the-recycle-bin"
	iteratorURL="<%= iteratorURL %>"
>

	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(attachmentsList, searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= attachments.length %>"
	/>

	<liferay-ui:search-container-row
		className="java.lang.String"
		modelVar="fileName"
		rowVar="row"
	>

		<%
		String shortFileName = FileUtil.getShortFileName(fileName);

		long fileSize = DLStoreUtil.getFileSize(company.getCompanyId(), CompanyConstants.SYSTEM, fileName);

		row.setObject(new Object[] {categoryId, messageId, fileName});

		row.setPrimaryKey(fileName);
		%>

		<liferay-ui:search-container-column-text
			name="file-name"
		>
			<img align="left" border="0" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= DLUtil.getFileIcon(shortFileName) %>.png"> <%= DLAppUtil.stripTrashNamespace(shortFileName, StringPool.UNDERLINE) %>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="size"
			value="<%= TextFormatter.formatStorageSize(fileSize, locale) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/message_boards/deleted_message_attachment_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>