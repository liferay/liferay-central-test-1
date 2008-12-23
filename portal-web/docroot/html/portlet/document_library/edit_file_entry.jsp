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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = ParamUtil.getString(request, "struts_action");

String tabs2 = ParamUtil.getString(request, "tabs2", "version-history");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

DLFileEntry fileEntry = (DLFileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long folderId = BeanParamUtil.getLong(fileEntry, request, "folderId");
String name = BeanParamUtil.getString(fileEntry, request, "name");

String extension = FileUtil.getExtension(name);

String titleWithExtension = BeanParamUtil.getString(fileEntry, request, "titleWithExtension");

String tagsEntries = ParamUtil.getString(renderRequest, "tagsEntries");

String[] conversions = new String[0];

if (PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED)) {
	conversions = (String[])DocumentConversionUtil.getConversions(extension);
}

DLFolder folder = null;

Lock lock = null;
Boolean isLocked = Boolean.FALSE;
Boolean hasLock = Boolean.FALSE;

if (fileEntry != null) {
	folder = fileEntry.getFolder();

	try {
		lock = LockServiceUtil.getLock(DLFileEntry.class.getName(), DLUtil.getLockId(fileEntry.getFolderId(), fileEntry.getName()));

		isLocked = Boolean.TRUE;

		if (lock.getUserId() == user.getUserId()) {
			hasLock = Boolean.TRUE;
		}
	}
	catch (Exception e) {
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsAction);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("name", name);
%>

<script type="text/javascript">
	function <portlet:namespace />compare() {
		var rowIds = jQuery('input[@name=<portlet:namespace />rowIds]:checked');
		var sourceVersion = jQuery('input[@name="<portlet:namespace />sourceVersion"]');
		var targetVersion = jQuery('input[@name="<portlet:namespace />targetVersion"]');

		if (rowIds.length == 1) {
			sourceVersion.val(rowIds[0].value);
		}
		else if (rowIds.length == 2) {
			sourceVersion.val(rowIds[1].value);
			targetVersion.val(rowIds[0].value);
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />initRowsChecked() {
		var rowIds = jQuery('input[@name=<portlet:namespace />rowIds]');

		var found = 0;

		for (i = 0; i < rowIds.length; i++) {
			if (rowIds[i].checked && (found < 2)) {
				found++;
			}
			else {
				rowIds[i].checked = false;
			}
		}
	}

	function <portlet:namespace />lock() {
		submitForm(document.hrefFm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />unlock() {
		submitForm(document.hrefFm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /><portlet:param name="name" value="<%= name %>" /></portlet:actionURL>");
	}

	function <portlet:namespace />updateRowsChecked(element) {
		var rowsChecked = jQuery('input[@name=<portlet:namespace />rowIds]:checked');

		if (rowsChecked.length > 2) {
			if (rowsChecked[2] == element) {
				rowsChecked[1].checked = false;
			}
			else {
				rowsChecked[2].checked = false;
			}
		}
	}

	jQuery(document).ready(
		function() {
			<portlet:namespace />initRowsChecked();

			jQuery('input[@name=<portlet:namespace />rowIds]').click(
				function() {
					<portlet:namespace />updateRowsChecked(this);
				}
			);
		}
	);
</script>

<c:if test="<%= isLocked.booleanValue() %>">
	<c:choose>
		<c:when test="<%= hasLock.booleanValue() %>">

			<%
			String lockExpirationTime = LanguageUtil.getTimeDescription(pageContext, DLFileEntryImpl.LOCK_EXPIRATION_TIME).toLowerCase();
			%>

			<span class="portlet-msg-success">
				<%= LanguageUtil.format(pageContext, "you-now-have-a-lock-on-this-document", lockExpirationTime, false) %>
			</span>
		</c:when>
		<c:otherwise>
			<span class="portlet-msg-error">
				<%= LanguageUtil.format(pageContext, "you-cannot-modify-this-document-because-it-was-locked-by-x-on-x", new Object[] {PortalUtil.getUserName(lock.getUserId(), String.valueOf(lock.getUserId())), dateFormatDateTime.format(lock.getDate())}, false) %>
			</span>
		</c:otherwise>
	</c:choose>
</c:if>

<div class="breadcrumbs">
	<%= BreadcrumbsUtil.removeLastClass(DLUtil.getBreadcrumbs(folderId, null, rootFolderId, pageContext, renderRequest, renderResponse)) %> &raquo;

	<span class="last"><liferay-ui:message key='<%= ((fileEntry == null) ? Constants.ADD : Constants.UPDATE) + "-file-entry" %>' /></span>
</div>

<c:if test="<%= fileEntry != null %>">
	<table class="lfr-table">
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="name" />
		</td>
		<td>
			<a href="<%= themeDisplay.getPathMain() %>/document_library/get_file?p_l_id=<%= themeDisplay.getPlid() %>&folderId=<%= folderId %>&name=<%= HttpUtil.encodeURL(name) %>">
			<%= HtmlUtil.escape(fileEntry.getTitleWithExtension()) %>
			</a>
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="version" />
		</td>
		<td>
			<%= fileEntry.getVersion() %>
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="size" />
		</td>
		<td>
			<%= TextFormatter.formatKB(fileEntry.getSize(), locale) %>k
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="num-of-downloads" />
		</td>
		<td>
			<%= fileEntry.getReadCount() %>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="download" />
		</td>
		<td>
			<liferay-ui:icon
				image='<%= "../document_library/" + extension %>'
				message="<%= extension.toUpperCase() %>"
				url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + folderId + "&name=" + HttpUtil.encodeURL(name) %>'
				label="<%= true %>"
			/>
		</td>
	</tr>

	<c:if test="<%= conversions.length > 0 %>">
		<tr>
			<td class="lfr-label">
				<liferay-ui:message key="convert-to" />
			</td>
			<td>
				<table class="lfr-table">
				<tr>

				<%
				for (int i = 0; i < conversions.length; i++) {
					String conversion = conversions[i];

				%>

					<td>
						<liferay-ui:icon
							image='<%= "../document_library/" + conversion %>'
							message="<%= conversion.toUpperCase() %>"
							url='<%= themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + folderId + "&name=" + HttpUtil.encodeURL(name) + "&targetExtension=" + conversion %>'
							label="<%= true %>"
						/>
					</td>

				<%
				}
				%>

				</tr>
				</table>
			</td>
		</tr>
	</c:if>

	<tr>
		<td colspan="2">
			<br />
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="url" />
		</td>
		<td>
			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/document_library/get_file?uuid=" + fileEntry.getUuid() + "&groupId=" + folder.getGroupId() %>'
			/>
		</td>
	</tr>
	<tr>
		<td class="lfr-label">
			<liferay-ui:message key="webdav-url" />
		</td>
		<td>

			<%
			StringBuffer sb = new StringBuffer();

			DLFolder curFolder = DLFolderLocalServiceUtil.getFolder(folderId);

			while (true) {
				sb.insert(0, HttpUtil.encodeURL(curFolder.getName(), true));
				sb.insert(0, StringPool.SLASH);

				if (curFolder.getParentFolderId() == DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
					break;
				}
				else {
					curFolder = DLFolderLocalServiceUtil.getFolder(curFolder.getParentFolderId());
				}
			}

			sb.append(StringPool.SLASH);
			sb.append(HttpUtil.encodeURL(titleWithExtension, true));

			Group group = layout.getGroup();
			%>

			<liferay-ui:input-resource
				url='<%= themeDisplay.getPortalURL() + "/tunnel-web/secure/webdav/" + company.getWebId() + group.getFriendlyURL() + "/document_library" + sb.toString() %>'
			/>
		</td>
	</tr>
	</table>
</c:if>

<c:if test='<%= strutsAction.equals("/document_library/edit_file_entry") && ((fileEntry == null) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) %>'>
	<c:if test="<%= fileEntry != null %>">
		<br />
	</c:if>

	<%
	String uploadProgressId = "dlFileEntryUploadProgress";
	%>

	<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="uploadProgressURL">
		<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
		<portlet:param name="tabs2" value="<%= tabs2 %>" />
		<portlet:param name="redirect" value="<%= redirect %>" />

		<c:if test="<%= Validator.isNotNull(referringPortletResource) %>">
			<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
		</c:if>

		<portlet:param name="uploadProgressId" value="<%= uploadProgressId %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
		<portlet:param name="name" value="<%= name %>" />

		<c:if test="<%= Validator.isNotNull(tagsEntries) %>">
			<portlet:param name="tagsEntries" value="<%= tagsEntries %>" />
		</c:if>
	</portlet:renderURL>

	<c:if test="<%= fileEntry == null %>">
		<script type="text/javascript">
			jQuery(
				function() {
					new Liferay.Upload({
						allowedFileTypes: '<%= StringUtil.merge(PropsValues.DL_FILE_EXTENSIONS) %>',
						container: '#<portlet:namespace />fileUpload',
						fileDescription: '<%= StringUtil.merge(PropsValues.DL_FILE_EXTENSIONS) %>',
						fallbackContainer: '#<portlet:namespace />fallback',
						maxFileSize: <%= PropsValues.DL_FILE_MAX_SIZE %>,
						namespace: '<portlet:namespace />',
						uploadFile: '<liferay-portlet:actionURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" doAsUserId="<%= user.getUserId() %>"><portlet:param name="struts_action" value="/document_library/edit_file_entry" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL><liferay-ui:input-permissions-params modelName="<%= DLFileEntry.class.getName() %>" />'
					});
				}
			);
		</script>

		<div class="lfr-dynamic-uploader">
			<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
		</div>

		<div class="lfr-fallback" id="<portlet:namespace />fallback">
	</c:if>

	<liferay-ui:upload-progress
		id="<%= uploadProgressId %>"
		iframeSrc="<%= uploadProgressURL %>"
		redirect="<%= redirect %>"
	/>

	<c:if test="<%= fileEntry == null %>">
		</div>
	</c:if>

	<c:if test="<%= fileEntry != null %>">
		<br />
	</c:if>
</c:if>

<c:if test="<%= fileEntry != null %>">
	<br />

	<liferay-ui:ratings
		className="<%= DLFileEntry.class.getName() %>"
		classPK="<%= fileEntry.getFileEntryId() %>"
		url='<%= themeDisplay.getPathMain() + "/document_library/rate_entry?p_p_id=" + portletDisplay.getId() %>'
	/>

	<br />

	<%
	String tabs2Names = "version-history,comments";

	if (!DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.ADD_DISCUSSION)) {
		tabs2Names = "version-history";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url="<%= portletURL.toString() %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("version-history") %>'>

			<%
			boolean comparableFileEntry = false;

			String[] comparableFileExtensions = PropsValues.DL_COMPARABLE_FILE_EXTENSIONS;

			for (int i = 0; i < comparableFileExtensions.length; i++) {
				if (StringPool.STAR.equals(comparableFileExtensions[i]) ||
					StringUtil.endsWith(name, comparableFileExtensions[i])) {

					comparableFileEntry = true;

					break;
				}
			}

			SearchContainer searchContainer = new SearchContainer();

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("version");
			headerNames.add("date");
			headerNames.add("size");
			headerNames.add("download");

			if (conversions.length > 0) {
				headerNames.add("convert-to");
			}

			if (strutsAction.equals("/document_library/edit_file_entry")) {
				headerNames.add(StringPool.BLANK);
			}

			searchContainer.setHeaderNames(headerNames);

			if (comparableFileEntry) {
				searchContainer.setRowChecker(new RowChecker(renderResponse, RowChecker.ALIGN, RowChecker.VALIGN, RowChecker.FORM_NAME, null, RowChecker.ROW_IDS));
			}

			List results = DLFileVersionLocalServiceUtil.getFileVersions(folderId, name);
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(i);

				ResultRow row = new ResultRow(new Object[] {fileEntry, fileVersion, conversions, portletURL, isLocked, hasLock}, String.valueOf(fileVersion.getVersion()), i);

				StringBuilder sb = new StringBuilder();

				sb.append(themeDisplay.getPathMain());
				sb.append("/document_library/get_file?p_l_id=");
				sb.append(themeDisplay.getPlid());
				sb.append("&folderId=");
				sb.append(folderId);
				sb.append("&name=");
				sb.append(HttpUtil.encodeURL(name));
				sb.append("&version=");
				sb.append(String.valueOf(fileVersion.getVersion()));

				String rowHREF = sb.toString();

				// Statistics

				row.addText(String.valueOf(fileVersion.getVersion()), rowHREF);
				row.addText(dateFormatDateTime.format(fileVersion.getCreateDate()), rowHREF);
				row.addText(TextFormatter.formatKB(fileVersion.getSize(), locale) + "k", rowHREF);

				// Download

				row.addJSP("/html/portlet/document_library/file_version_download.jsp");

				// Convert to

				if (conversions.length > 0) {
					row.addJSP("/html/portlet/document_library/file_version_convert_to.jsp");
				}

				// Action

				if (strutsAction.equals("/document_library/edit_file_entry")) {
					row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_version_action.jsp");
				}

				// Add result row

				resultRows.add(row);
			}

			if (comparableFileEntry && (results.size() > 0)) {
				DLFileVersion fileVersion = (DLFileVersion)results.get(0);
			%>

				<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/document_library/compare_versions" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />compare(); return false;">
				<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(currentURL) %>" />
				<input name="<portlet:namespace />fileEntryId" type="hidden" value="<%= fileEntry.getFileEntryId() %>" />
				<input name="<portlet:namespace />folderId" type="hidden" value="<%= folderId %>" />
				<input name="<portlet:namespace />name" type="hidden" value="<%= name %>" />
				<input name="<portlet:namespace />titleWithExtension" type="hidden" value="<%= titleWithExtension %>" />
				<input name="<portlet:namespace />sourceVersion" type="hidden" value="<%= fileVersion.getVersion() %>" />
				<input name="<portlet:namespace />targetVersion" type="hidden" value="<%= fileEntry.getVersion() %>" />

				<input type="submit" value="<liferay-ui:message key="compare-versions" />" />

				</form>

				<br />

			<%
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("comments") %>'>
			<c:if test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.ADD_DISCUSSION) %>">
				<portlet:actionURL var="discussionURL">
					<portlet:param name="struts_action" value="/document_library/edit_file_entry_discussion" />
				</portlet:actionURL>

				<liferay-ui:discussion
					formAction="<%= discussionURL %>"
					className="<%= DLFileEntry.class.getName() %>"
					classPK="<%= fileEntry.getFileEntryId() %>"
					userId="<%= fileEntry.getUserId() %>"
					subject="<%= fileEntry.getTitle() %>"
					redirect="<%= currentURL %>"
					ratingsEnabled="<%= enableCommentRatings %>"
				/>
			</c:if>
		</c:when>
	</c:choose>
</c:if>