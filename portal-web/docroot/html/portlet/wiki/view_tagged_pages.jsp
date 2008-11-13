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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String tag = ParamUtil.getString(renderRequest, "tag");
boolean folksonomy = ParamUtil.getBoolean(renderRequest, "folksonomy", TagsEntryConstants.FOLKSONOMY_TAG);

String title = "pages-with-category-x";
String description = null;

try {
	TagsEntry tagsEntry = TagsEntryLocalServiceUtil.getEntry(scopeGroupId, tag, folksonomy);

	if (folksonomy) {
		title = "pages-with-tag-x";
	}

	TagsProperty tagsProperty = TagsPropertyLocalServiceUtil.getProperty(tagsEntry.getEntryId(), "description");

	description = tagsProperty.getValue();
}
catch (NoSuchEntryException nsee) {
}
catch (NoSuchPropertyException nspe) {
}
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<h1 class="page-title">
	<%= LanguageUtil.format(pageContext, title, tag) %>
</h1>

<c:if test="<%= Validator.isNotNull(description) %>">
	<p class="tag-description">
		<%= description %>
	</p>
</c:if>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp">
	<liferay-util:param name="type" value="tagged_pages" />
</liferay-util:include>