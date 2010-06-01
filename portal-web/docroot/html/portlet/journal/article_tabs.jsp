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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

PortletURL editArticleURL = renderResponse.createRenderURL();

editArticleURL.setParameter("struts_action", "/journal/edit_article");
editArticleURL.setParameter("redirect", redirect);

PortletURL viewArticleHistoryURL = renderResponse.createRenderURL();
viewArticleHistoryURL.setParameter("struts_action", "/journal/view_article_history");
viewArticleHistoryURL.setParameter("redirect", redirect);

if (article != null) {
	editArticleURL.setParameter("groupId", String.valueOf(article.getGroupId()));
	editArticleURL.setParameter("articleId", article.getArticleId());
	viewArticleHistoryURL.setParameter("groupId", String.valueOf(article.getGroupId()));
	viewArticleHistoryURL.setParameter("articleId", article.getArticleId());
}
%>

<c:choose>
	<c:when test="<%= article != null %>">
		<liferay-ui:tabs
			names='<%= article.getTitle() %>'
			backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
		/>

		<liferay-ui:tabs
			names="content,history"
			url0="<%= editArticleURL.toString() %>"
			url1="<%= viewArticleHistoryURL.toString() %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-ui:tabs
			names="new-web-content"
			backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
		/>
	</c:otherwise>
</c:choose>