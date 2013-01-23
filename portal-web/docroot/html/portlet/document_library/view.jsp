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
String strutsAction = ParamUtil.getString(request, "struts_action");

Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", rootFolderId);

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int entryRowsPerPage = entryEnd - entryStart;

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

int folderRowsPerPage = folderEnd - folderStart;

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<portlet:actionURL var="undoTrashURL">
	<portlet:param name="struts_action" value="/document_library/edit_entry" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo portletURL="<%= undoTrashURL %>" />

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:layout cssClass="lfr-app-column-view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />

			<div class="folder-paginator"></div>
		</aui:column>

		<aui:column columnWidth="<%= showFolderMenu ? 80 : 100 %>" cssClass="context-pane" last="<%= true %>">
			<liferay-ui:app-view-toolbar
				includeDisplayStyle="<%= true %>"
				includeSelectAll="<%= true %>"
				searchJsp='<%= showFoldersSearch ? "/html/portlet/document_library/file_entry_search.jsp" : StringPool.BLANK %>'
			>
				<liferay-util:include page="/html/portlet/document_library/toolbar.jsp" />
			</liferay-ui:app-view-toolbar>

			<%
			boolean showSyncMessage = GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true"));

			String cssClass = "show-sync-message-icon-container";

			if (showSyncMessage || !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE) {
				cssClass += " aui-helper-hidden";
			}
			%>

			<div class="<%= cssClass %>" id="<portlet:namespace />showSyncMessageIconContainer">
				<img alt="<%= LanguageUtil.get(pageContext, "show-liferay-sync-tip") %>" class="show-sync-message" id="<portlet:namespace />showSyncMessageIcon" src="<%= themeDisplay.getPathThemeImages() + "/common/liferay_sync.png" %>" title="<%= LanguageUtil.get(pageContext, "liferay-sync") %>" />
			</div>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
			</div>

			<div class="aui-helper-hidden" id="<portlet:namespace />syncNotification">
				<div class="lfr-message-info sync-notification" id="<portlet:namespace />syncNotificationContent">
					<a href="http://www.liferay.com/products/liferay-sync" target="_blank">
						<liferay-ui:message key="access-these-files-offline-using-liferay-sync" />
					</a>
				</div>
			</div>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= editFileEntryURL.toString() %>" method="get" name="fm2">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
				<aui:input name="newFolderId" type="hidden" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />
				<aui:input name="fileShortcutIds" type="hidden" />

				<div class="document-container" id="<portlet:namespace />entriesContainer">
					<c:choose>
						<c:when test='<%= strutsAction.equals("/document_library/search") %>'>
							<liferay-util:include page="/html/portlet/document_library/search_resources.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
						</c:otherwise>
					</c:choose>
				</div>

				<div class="document-entries-paginator"></div>
			</aui:form>
		</aui:column>
	</aui:layout>
</div>

<span id="<portlet:namespace />displayStyleButtonsContainer">
	<c:if test='<%= !strutsAction.equals("/document_library/search") %>'>
		<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
	</c:if>
</span>

<%
int entriesTotal = GetterUtil.getInteger((String)request.getAttribute("view.jsp-total"));
int foldersTotal = GetterUtil.getInteger((String)request.getAttribute("view_folders.jsp-total"));

if (folder != null) {
	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
		PortalUtil.setPageSubtitle(folder.getName(), request);
		PortalUtil.setPageDescription(folder.getDescription(), request);
	}
}
%>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			if (actionsButton) {
				actionsButton.toggle(!hide);
			}
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="liferay-document-library">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL" />

	new Liferay.Portlet.DocumentLibrary(
		{
			displayStyle: '<%= HtmlUtil.escapeJS(displayStyle) %>',
			folders: {
				defaultParams: {
					p_p_id: <%= portletId %>,
					p_p_lifecycle: 0
				},
				defaultParentFolderId: '<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
				mainUrl: '<%= mainURL %>',
				strutsAction: '/document_library/view'
			},
			move: {
				allRowIds: '<%= RowChecker.ALL_ROW_IDS %>',
				editEntryUrl: '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>',
				folderIdRegEx: /&?<portlet:namespace />folderId=([\d]+)/i,
				folderIdHashRegEx: /#.*&?<portlet:namespace />folderId=([\d]+)/i,
				form: {
					method: 'post',
					node: A.one(document.<portlet:namespace />fm2)
				},
				moveEntryRenderUrl: '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /></portlet:renderURL>',
				trashLinkId: '<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "_" + PortletKeys.CONTROL_PANEL_MENU + "_portlet_" + PortletKeys.TRASH : StringPool.BLANK %>',
				updateable: <%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.UPDATE) %>
			},
			paginator: {
				entriesTotal: <%= entriesTotal %>,
				entryEnd: <%= entryEnd %>,
				entryRowsPerPage: <%= entryRowsPerPage %>,
				entryRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
				entryStart: <%= entryStart %>,
				folderEnd: <%= folderEnd %>,
				folderId: <%= folderId %>,
				folderRowsPerPage: <%= folderRowsPerPage %>,
				folderRowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>],
				folderStart: <%= folderStart %>,
				foldersTotal: <%= foldersTotal %>
			},
			namespace: '<portlet:namespace />',
			portletId: '<%= portletId %>',
			repositories: [
				{
					id: '<%= scopeGroupId %>',
					name: '<%= LanguageUtil.get(pageContext, "local") %>'
				}

				<%
				List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (Folder mountFolder : mountFolders) {
				%>

					,{
						id: '<%= mountFolder.getRepositoryId() %>',
						name: '<%= mountFolder.getName() %>'
					}

				<%
				}
				%>

			],
			rowIds: '<%= RowChecker.ROW_IDS %>',
			select: {
				displayViews: ['<%= StringUtil.merge(displayViews, "','") %>']
			},
			syncMessageDisabled: <%= !PropsValues.DL_SHOW_LIFERAY_SYNC_MESSAGE %>,
			syncMessageSuppressed: <%= !GetterUtil.getBoolean(SessionClicks.get(request, liferayPortletResponse.getNamespace() + "show-sync-message", "true")) %>
		}
	);
</aui:script>