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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String tabs3 = (String)request.getAttribute("edit_organization_assignments.jsp-tabs3");

String cur = (String)request.getAttribute("edit_organization_assignments.jsp-cur");

Organization organization = (Organization)request.getAttribute("edit_organization_assignments.jsp-organization");

Group group = organization.getGroup();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_organization_assignments.jsp-portletURL");
%>

<aui:input name="addUserGroupIds" type="hidden" />
<aui:input name="removeUserGroupIds" type="hidden" />

<liferay-ui:tabs
	names="current,available"
	param="tabs3"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-container
	rowChecker="<%= new UserGroupGroupChecker(renderResponse, group) %>"
	searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_group_search.jsp"
	/>

	<%
	UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap userGroupParams = new LinkedHashMap();

	if (tabs3.equals("current")) {
		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));
	}
	%>

	<liferay-ui:search-container-results
		results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		total="<%= UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), userGroupParams) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			name="name"
			orderable="<%= true %>"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="description"
			orderable="<%= true %>"
			property="description"
		/>
	</liferay-ui:search-container-row>

	<div class="separator"><!-- --></div>

	<%
	String taglibOnClick = renderResponse.getNamespace() + "updateOrganizationUserGroups('" + portletURL.toString() + StringPool.AMPERSAND + renderResponse.getNamespace() + "cur=" + cur + "');";
	%>

	<aui:button value="update-associations" onClick="<%= taglibOnClick %>" />

	<br /><br />

	<liferay-ui:search-iterator />
</liferay-ui:search-container>