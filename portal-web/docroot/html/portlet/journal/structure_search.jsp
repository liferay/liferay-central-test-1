<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
StructureSearch searchContainer = (StructureSearch)request.getAttribute("liferay-ui:search:searchContainer");

StructureDisplayTerms displayTerms = (StructureDisplayTerms)searchContainer.getDisplayTerms();
%>

<table class="liferay-table">
<tr>
	<td>
		<liferay-ui:message key="id" />
	</td>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:message key="description" />
	</td>
</tr>
<tr>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.STRUCTURE_ID %>" size="20" type="text" value="<%= displayTerms.getStructureId() %>" />
	</td>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.NAME %>" size="20" type="text" value="<%= displayTerms.getName() %>" />
	</td>
	<td>
		<input name="<portlet:namespace /><%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />
	</td>
</tr>
</table>

<br />

<div>
	<select name="<portlet:namespace /><%= displayTerms.AND_OPERATOR %>">
		<option <%= displayTerms.isAndOperator() ? "selected" : "" %> value="1"><liferay-ui:message key="and" /></option>
		<option <%= !displayTerms.isAndOperator() ? "selected" : "" %> value="0"><liferay-ui:message key="or" /></option>
	</select>

	<input type="submit" value="<liferay-ui:message key="search-structures" />" />

	<c:if test="<%= PortletPermission.contains(permissionChecker, plid.longValue(), PortletKeys.JOURNAL, ActionKeys.ADD_STRUCTURE) %>">
		<input type="button" value="<liferay-ui:message key="add-structure" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" />
	</c:if>
</div>