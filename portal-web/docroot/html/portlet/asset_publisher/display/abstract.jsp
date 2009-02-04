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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List results = (List)request.getAttribute("view.jsp-results");

int assetIndex = ((Integer)request.getAttribute("view.jsp-assetIndex")).intValue();

TagsAsset asset = (TagsAsset)request.getAttribute("view.jsp-asset");

String title = (String)request.getAttribute("view.jsp-title");
String summary = (String)request.getAttribute("view.jsp-summary");
String viewURL = (String)request.getAttribute("view.jsp-viewURL");
String viewURLMessage = (String)request.getAttribute("view.jsp-viewURLMessage");
String cssClassName = StringPool.BLANK;

String className = (String)request.getAttribute("view.jsp-className");
long classPK = ((Long)request.getAttribute("view.jsp-classPK")).longValue();

boolean show = ((Boolean)request.getAttribute("view.jsp-show")).booleanValue();

request.setAttribute("view.jsp-showIconLabel", true);

PortletURL viewFullContentURL = renderResponse.createRenderURL();

viewFullContentURL.setParameter("struts_action", "/asset_publisher/view_content");
viewFullContentURL.setParameter("redirect", currentURL);
viewFullContentURL.setParameter("assetId", String.valueOf(asset.getAssetId()));

if (className.equals(BlogsEntry.class.getName())) {
	BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getTitle();
	}

	summary = StringUtil.shorten(HtmlUtil.stripHtml(entry.getContent()), abstractLength);
	viewURL = viewInContext ? themeDisplay.getURLPortal() + themeDisplay.getPathMain() + "/blogs/find_entry?entryId=" + entry.getEntryId() + "&noSuchEntryRedirect=" + HttpUtil.encodeURL(viewFullContentURL.toString()) : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "view-in-context" : "read-more";
	cssClassName = "blog";
}
else if (className.equals(BookmarksEntry.class.getName())) {
	BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

	if (Validator.isNull(title)) {
		title = entry.getName();
	}

	summary = entry.getComments();
	viewURL = viewInContext ? entry.getUrl() : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "go" : "read-more";
	cssClassName = "bookmark";
}
else if (className.equals(DLFileEntry.class.getName())) {
	DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(classPK);

	String fileEntryURL = themeDisplay.getPathMain() + "/document_library/get_file?p_l_id=" + themeDisplay.getPlid() + "&folderId=" + fileEntry.getFolderId() + "&name=" + HttpUtil.encodeURL(fileEntry.getName());

	StringBuilder sb = new StringBuilder();

	sb.append("<a href=\"");
	sb.append(fileEntryURL);
	sb.append("\"><img align=\"left\" border=\"0\" src=\"");
	sb.append(themeDisplay.getPathThemeImages());
	sb.append("/document_library/");
	sb.append(DLUtil.getFileExtension(fileEntry.getName()));
	sb.append(".png\" />");
	sb.append(fileEntry.getTitle());
	sb.append("</a>");

	summary = fileEntry.getDescription();

	if (Validator.isNull(title)) {
		title = sb.toString();
	}
	else {
		sb.append("<br />");
		sb.append(summary);

		summary = sb.toString();
	}

	viewURL = viewInContext ? fileEntryURL : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "download" : "read-more";
	cssClassName = "document";
}
else if (className.equals(IGImage.class.getName())) {
	IGImage image = IGImageLocalServiceUtil.getImage(classPK);

	PortletURL imageURL = new PortletURLImpl(request, PortletKeys.IMAGE_GALLERY, plid, PortletRequest.RENDER_PHASE);

	imageURL.setWindowState(WindowState.MAXIMIZED);

	imageURL.setParameter("struts_action", "/image_gallery/view");
	imageURL.setParameter("folderId", String.valueOf(image.getFolderId()));

	viewURL = viewInContext ? imageURL.toString() : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "view-album" : "view";
	cssClassName = "image";

	StringBuilder sb = new StringBuilder();

	Image smallImage = ImageLocalServiceUtil.getImage(image.getSmallImageId());

	if (smallImage != null) {
		long smallImageId = smallImage.getImageId();

		sb.append("<a href=\"");
		sb.append(viewURL);
		sb.append("\"><img align=\"left\" alt=\"");
		sb.append(LanguageUtil.get(pageContext, viewURLMessage));
		sb.append("\" border=\"0\" src=\"");
		sb.append(themeDisplay.getPathImage());
		sb.append("/image_gallery?img_id=");
		sb.append(smallImageId);
		sb.append("\" style=\"float: left; padding-right: 10px;\" /></a>");
	}

	sb.append(StringUtil.shorten(image.getDescription(), abstractLength));

	summary = sb.toString();
}
else if (className.equals(JournalArticle.class.getName())) {
	JournalArticleResource articleResource = JournalArticleResourceLocalServiceUtil.getArticleResource(classPK);

	String languageId = LanguageUtil.getLanguageId(request);

	JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(articleResource.getGroupId(), articleResource.getArticleId(), null, null, languageId, themeDisplay);

	if (articleDisplay != null) {
		if (Validator.isNull(title)) {
			title = articleDisplay.getTitle();
		}

		StringBuilder sb = new StringBuilder();

		if (articleDisplay.isSmallImage()) {
			sb.append("<div style=\"float: left; padding-right: 10px;\"><img alt=\"");
			sb.append(LanguageUtil.get(pageContext, "web-content-image"));
			sb.append("\" src=\"");

			if (Validator.isNotNull(articleDisplay.getSmallImageURL())) {
				sb.append(articleDisplay.getSmallImageURL());
			}
			else {
				sb.append(themeDisplay.getPathImage());
				sb.append("/journal/article?img_id=");
				sb.append(articleDisplay.getSmallImageId());
				sb.append("&t=");
				sb.append(ImageServletTokenUtil.getToken(articleDisplay.getSmallImageId()));
			}

			sb.append("\" /></div>");
		}

		sb.append(articleDisplay.getDescription());

		summary = sb.toString();

		if (Validator.isNull(summary)) {
			summary = StringUtil.shorten(HtmlUtil.stripHtml(articleDisplay.getContent()), abstractLength);
		}

		viewURL = viewFullContentURL.toString();
		viewURLMessage = "read-more";
		cssClassName = "web-content";
	}
	else {
		show = false;
	}
}
else if (className.equals(MBMessage.class.getName())) {
	MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

	summary = StringUtil.shorten(message.getBody(), abstractLength);
	viewURL = viewInContext ? themeDisplay.getURLPortal() + themeDisplay.getPathMain() + "/message_boards/find_message?messageId=" + message.getMessageId() : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "view-in-context" : "read-more";
	cssClassName = "thread";
}
else if (className.equals(WikiPage.class.getName())) {
	WikiPageResource pageResource = WikiPageResourceLocalServiceUtil.getPageResource(classPK);

	WikiPage wikiPage = WikiPageLocalServiceUtil.getPage(pageResource.getNodeId(), pageResource.getTitle());

	summary = wikiPage.getContent();

	if (wikiPage.getFormat().equals("html")) {
		summary = HtmlUtil.stripHtml(summary);
	}

	summary = StringUtil.shorten(summary, abstractLength);
	viewURL = viewInContext ? themeDisplay.getURLPortal() + themeDisplay.getPathMain() + "/wiki/find_page?pageResourcePrimKey=" + wikiPage.getResourcePrimKey() : viewFullContentURL.toString();
	viewURLMessage = viewInContext ? "view-in-context" : "read-more";
	cssClassName = "wiki";
}
%>

<c:if test="<%= show %>">
	<div class="asset-abstract">
		<h3 class="asset-title <%= cssClassName %>">
			<liferay-util:include page="/html/portlet/asset_publisher/asset_actions.jsp" />

			<c:choose>
				<c:when test="<%= Validator.isNotNull(viewURL) %>">
					<a href="<%= viewURL %>"><%= title %></a>
				</c:when>
				<c:otherwise>
					<%= title %>
				</c:otherwise>
			</c:choose>
		</h3>

		<div class="asset-content">
			<p class="asset-summary">
				<%= summary %>
			</p>

			<c:if test="<%= Validator.isNotNull(viewURL) %>">
				<div class="asset-more">
					<a href="<%= viewURL %>"><liferay-ui:message key="<%= viewURLMessage %>" /> &raquo; </a>
				</div>
			</c:if>
		</div>

		<div class="asset-metadata">
			<%@ include file="/html/portlet/asset_publisher/asset_metadata.jspf" %>
		</div>
	</div>

	<c:if test="<%= (assetIndex + 1) == results.size() %>">
		<div class="final-separator"><!-- --></div>
	</c:if>
</c:if>