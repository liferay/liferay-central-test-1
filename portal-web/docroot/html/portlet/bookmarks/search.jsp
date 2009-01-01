<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};
}
else {
	List folderIds = new ArrayList();

	folderIds.add(new Long(searchFolderIds));

	BookmarksFolderLocalServiceUtil.getSubfolderIds(folderIds, scopeGroupId, searchFolderIds);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="searchURL"><portlet:param name="struts_action" value="/bookmarks/search" /></liferay-portlet:renderURL>

<form action="<%= searchURL %>" method="get" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="searchURL" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
<input name="<portlet:namespace />searchFolderId" type="hidden" value="<%= searchFolderId %>" />
<input name="<portlet:namespace />searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

<liferay-ui:tabs
	names="search"
	backURL="<%= redirect %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/bookmarks/search");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
portletURL.setParameter("keywords", keywords);

List<String> headerNames = new ArrayList<String>();

headerNames.add("#");
headerNames.add("folder");
headerNames.add("entry");
headerNames.add("score");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<b>" + HtmlUtil.escape(keywords) + "</b>"));

try {
	Hits results = BookmarksFolderLocalServiceUtil.search(company.getCompanyId(), scopeGroupId, themeDisplay.getUserId(), folderIdsArray, keywords, searchContainer.getStart(), searchContainer.getEnd());

	int total = results.getLength();

	searchContainer.setTotal(total);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.getDocs().length; i++) {
		Document doc = results.doc(i);

		ResultRow row = new ResultRow(doc, i, i);

		// Position

		row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

		// Folder and document

		long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

		BookmarksEntry entry = null;

		try {
			entry = BookmarksEntryLocalServiceUtil.getEntry(entryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Bookmarks search index is stale and contains entry " + entryId);
			}

			continue;
		}

		row.setObject(entry);

		BookmarksFolder folder = entry.getFolder();

		StringBuilder sb = new StringBuilder();

		sb.append(themeDisplay.getPathMain());
		sb.append("/bookmarks/open_entry?entryId=");
		sb.append(entry.getEntryId());

		String rowHREF = sb.toString();

		TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, folder.getName(), rowHREF, "_blank", entry.getComments());

		row.addText(rowTextEntry);

		rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

		rowTextEntry.setName(entry.getName());

		row.addText(rowTextEntry);

		// Score

		row.addScore(results.score(i));

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/bookmarks/entry_action.jsp");

		// Add result row

		resultRows.add(row);
	}
%>

	<input name="<portlet:namespace />keywords" size="30" type="text" value="<%= HtmlUtil.escape(keywords) %>" />

	<input type="submit" value="<liferay-ui:message key="search-file-entries" />" />

	<br /><br />

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<%
}
catch (Exception e) {
	_log.error(e.getMessage());
}
%>

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
	</script>
</c:if>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.bookmarks.search.jsp");
%>