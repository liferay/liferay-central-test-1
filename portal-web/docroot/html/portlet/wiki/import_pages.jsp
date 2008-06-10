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
String uploadProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
String importProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

long nodeId = BeanParamUtil.getLong(node, request, "nodeId");
%>

<form action="<portlet:actionURL><portlet:param name="struts_action" value="/wiki/import_pages" /></portlet:actionURL>" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />importProgressId" type="hidden" value="<%= importProgressId %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />nodeId" type="hidden" value="<%= nodeId %>" />

<liferay-ui:tabs
	names="import-pages"
	backURL="<%= redirect %>"
/>

<liferay-ui:tabs
	names="MediaWiki"
/>

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="file" />
	</td>
	<td>
		<input name="<portlet:namespace />file" type="file" />

		<liferay-ui:icon-help message="import-wiki-pages-help" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="user-emails-file" /> (<liferay-ui:message key="optional" />)
	</td>
	<td>
		<input name="<portlet:namespace />emailsFile" type="file" />

		<liferay-ui:icon-help message="import-wiki-emails-file-help" />
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="import" />" onClick="<%= uploadProgressId %>.startProgress(); <%= importProgressId %>.startProgress(); return true;" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<liferay-ui:upload-progress
	id="<%= uploadProgressId %>"
	message="uploading"
	redirect="<%= HtmlUtil.escape(redirect) %>"
/>

<liferay-ui:upload-progress
	id="<%= importProgressId %>"
	message="importing"
	redirect="<%= HtmlUtil.escape(redirect) %>"
/>