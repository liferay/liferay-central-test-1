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
String redirect = ParamUtil.getString(request, "redirect");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId");

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId");

Folder parentFolder = null;

long parentFolderId = BeanParamUtil.getLong(folder, request, "parentFolderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
%>

<liferay-util:include page="/html/portlet/document_library/top_links.jsp" />

<portlet:actionURL var="editFolderURL">
	<portlet:param name="struts_action" value="/document_library/edit_folder" />
</portlet:actionURL>

<liferay-util:buffer var="removeFileEntryTypeIcon">
	<liferay-ui:icon
		image="unlink"
		label="<%= true %>"
		message="remove"
	/>
</liferay-util:buffer>

<aui:form action="<%= editFolderURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (folder == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
	<aui:input name="parentFolderId" type="hidden" value="<%= parentFolderId %>" />

	<liferay-ui:header
		backURL="<%= redirect %>"
		title='<%= (folder != null) ? folder.getName() : "new-folder" %>'
	/>

	<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= DuplicateFolderNameException.class %>" message="please-enter-a-unique-folder-name" />
	<liferay-ui:error exception="<%= FolderNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= folder %>" model="<%= DLFolder.class %>" />

	<aui:fieldset>
		<c:if test="<%= folder != null %>">
			<aui:field-wrapper label="parent-folder">

				<%
				String parentFolderName = LanguageUtil.get(pageContext, "documents-home");

				try {
					if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
						parentFolder = DLAppLocalServiceUtil.getFolder(parentFolderId);

						parentFolderName = parentFolder.getName();
					}
				}
				catch (NoSuchFolderException nscce) {
				}
				%>

				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="struts_action" value="/document_library/view" />
					<portlet:param name="folderId" value="<%= String.valueOf(parentFolderId) %>" />
				</portlet:renderURL>

				<aui:a href="<%= viewFolderURL %>" id="parentFolderName"><%= parentFolderName %></aui:a>
			</aui:field-wrapper>
		</c:if>

		<aui:input name="name" />

		<c:if test="<%= (parentFolder == null) || parentFolder.isSupportsMetadata() %>">
			<aui:input name="description" />

			<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= DLFolderConstants.getClassName() %>"
					classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</c:if>

		<c:if test="<%= (folder != null) && (folder.getModel() instanceof DLFolder) %>">

			<%
			DLFolder dlFolder = (DLFolder)folder.getModel();

			List<DLFileEntryType> folderDLFileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(scopeGroupId, folderId, false);
			%>

			<aui:field-wrapper label="document-type-restrictions" helpMessage="document-type-restrictions-help">
				<div>
					<table class="lfr-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="override-inherited-restrictions" />
						</td>
						<td>
							<liferay-ui:input-checkbox defaultValue="<%= dlFolder.isOverrideFileEntryTypes() %>"  param="overrideFileEntryTypes" />
						</td>
					</tr>
					</table>
				</div>

				<div id="<portlet:namespace />overrideParentSettings">
					<liferay-ui:search-container
						id='<%= renderResponse.getNamespace() + "fileEntryTypeSearchContainer" %>'
						headerNames="name,default,null"
					>
						<liferay-ui:search-container-results
							results="<%= folderDLFileEntryTypes %>"
							total="<%= (folderDLFileEntryTypes != null) ? folderDLFileEntryTypes.size() : 0 %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
							escapedModel="<%= true %>"
							keyProperty="fileEntryTypeId"
							modelVar="dlFileEntryType"
						>
							<liferay-ui:search-container-column-text
								name="name"
								value="<%= dlFileEntryType.getName() %>"
							/>

							<liferay-ui:search-container-column-text name="default">
								<input class="default-file-entry-type" type="radio" name="defaultFileEntryTypeId" <%= (dlFolder.getDefaultFileEntryTypeId() == dlFileEntryType.getFileEntryTypeId()) ? "checked=\"true\"" : "" %> value="<%= dlFileEntryType.getFileEntryTypeId() %>" />
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text>
								<a class="modify-link" data-rowId="<%= dlFileEntryType.getFileEntryTypeId() %>" href="javascript:;"><%= removeFileEntryTypeIcon %></a>
							</liferay-ui:search-container-column-text>
						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator paginate="<%= false %>" />
					</liferay-ui:search-container>

					<liferay-ui:icon
						cssClass="modify-link select-file-entry-type"
						image="add"
						label="<%= true %>"
						message="select-document-type"
						url='<%= "javascript:" + renderResponse.getNamespace() + "openFileEntryTypeSelector();" %>'
					/>
				</div>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= folder == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= DLFolderConstants.getClassName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<aui:button-row>
			<aui:button type="submit" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	function <portlet:namespace />openFileEntryTypeSelector() {
		Liferay.Util.openWindow(
			{
				dialog: {
					stack: false,
					width: 680
				},
				title: '<liferay-ui:message key="document-types" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/document_library/select_file_entry_type" /><portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" /></portlet:renderURL>'
			}
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />selectFileEntryType',
		function(fileEntryTypeId, fileEntryTypeName, dialog) {
			var A = AUI();

			var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />fileEntryTypeSearchContainer');

			var defaultChecked = true;

			var fileEntryTypeDefault = '<input class="default-file-entry-type" type="radio" name="defaultFileEntryTypeId" checked="' + defaultChecked + '" value="' + fileEntryTypeId + '" />';

			var fileEntryTypeLink = '<a class="modify-link" data-rowId="' + fileEntryTypeId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeFileEntryTypeIcon) %></a>';

			A.one('#<portlet:namespace />fileEntryTypeSearchContainer').all('default-file-entry-type').set('checked', 'false');

			searchContainer.addRow([fileEntryTypeName, fileEntryTypeDefault, fileEntryTypeLink], fileEntryTypeId);

			searchContainer.updateDataStore();

			if (dialog) {
				dialog.close();
			}
		},
		['liferay-search-container']
	);

	Liferay.Util.toggleBoxes('<portlet:namespace />overrideFileEntryTypesCheckbox', '<portlet:namespace />overrideParentSettings');
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />fileEntryTypeSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var A = AUI();

			var link = event.currentTarget;

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			var button = A.one('#<portlet:namespace />fileEntryTypeSearchContainer .default-file-entry-type');

			if (button) {
				button.set('checked', 'true');
			}
		},
		'.modify-link'
	);
</aui:script>

<%
if (folder == null) {
	DLUtil.addPortletBreadcrumbEntries(parentFolderId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-folder"), currentURL);
}
else {
	DLUtil.addPortletBreadcrumbEntries(folder.getFolderId(), request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
%>