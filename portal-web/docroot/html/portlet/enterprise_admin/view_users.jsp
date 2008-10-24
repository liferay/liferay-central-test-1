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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String exportProgressId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");

List<Organization> manageableOrganizations = null;
Long[] manageableOrganizationIds = null;

if (filterManageableOrganizations) {
	manageableOrganizations = OrganizationLocalServiceUtil.getManageableOrganizations(themeDisplay.getUserId());
	manageableOrganizationIds = EnterpriseAdminUtil.getOrganizationIds(manageableOrganizations);
}
%>

<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<liferay-util:include page="/html/portlet/enterprise_admin/user/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view-users" />
</liferay-util:include>

<liferay-ui:search-container
	rowChecker="<%= new RowChecker(renderResponse) %>"
	searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
>
	<input name="<portlet:namespace />deleteUserIds" type="hidden" value="" />
	<input name="<portlet:namespace />usersRedirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-form
		page="/html/portlet/enterprise_admin/user_search.jsp"
		showAddButton="<%= true %>"
	/>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

		long organizationId = searchTerms.getOrganizationId();
		long roleId = searchTerms.getRoleId();
		long userGroupId = searchTerms.getUserGroupId();

		LinkedHashMap userParams = new LinkedHashMap();

		if (organizationId > 0) {
			userParams.put("usersOrgs", new Long(organizationId));
		}
		else {
			if (filterManageableOrganizations) {
				userParams.put("usersOrgs", manageableOrganizationIds);
			}
		}

		if (roleId > 0) {
			userParams.put("usersRoles", new Long(roleId));
		}

		if (userGroupId > 0) {
			userParams.put("usersUserGroups", new Long(userGroupId));
		}
		%>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/enterprise_admin/user_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.User"
			keyProperty="userId"
			modelVar="user2"
		>
			<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" varImpl="rowURL">
				<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			</liferay-portlet:renderURL>

			<%@ include file="/html/portlet/enterprise_admin/user/search_columns.jspf" %>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/enterprise_admin/user_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<%
		Organization organization = null;

		if ((organizationId > 0)) {
			try {
				organization = OrganizationLocalServiceUtil.getOrganization(organizationId);
			}
			catch (NoSuchOrganizationException nsoe) {
			}
		}

		Role role = null;

		if (roleId > 0) {
			try {
				role = RoleLocalServiceUtil.getRole(roleId);
			}
			catch (NoSuchRoleException nsre) {
			}
		}

		UserGroup userGroup = null;

		if (userGroupId > 0) {
			try {
				userGroup = UserGroupLocalServiceUtil.getUserGroup(userGroupId);
			}
			catch (NoSuchUserGroupException nsuge) {
			}
		}
		%>

		<c:if test="<%= (organization != null) || (role != null) || (userGroup != null) %>">
			<br />
		</c:if>

		<c:if test="<%= organization != null %>">
			<input name="<portlet:namespace /><%= UserDisplayTerms.ORGANIZATION_ID %>" type="hidden" value="<%= organization.getOrganizationId() %>" />

			<liferay-ui:message key="filter-by-organization" />: <%= organization.getName() %><br />
		</c:if>

		<c:if test="<%= role != null %>">
			<input name="<portlet:namespace /><%= UserDisplayTerms.ROLE_ID %>" type="hidden" value="<%= role.getRoleId() %>" />

			<liferay-ui:message key="filter-by-role" />: <%= role.getName() %><br />
		</c:if>

		<c:if test="<%= userGroup != null %>">
			<input name="<portlet:namespace /><%= UserDisplayTerms.USER_GROUP_ID %>" type="hidden" value="<%= userGroup.getUserGroupId() %>" />

			<liferay-ui:message key="filter-by-user-group" />: <%= userGroup.getName() %><br />
		</c:if>

		<div class="separator"><!-- --></div>

		<%
		boolean hasButtons = false;
		%>

		<c:if test="<%= searchTerms.isActive() || (!searchTerms.isActive() && PropsValues.USERS_DELETE) %>">

			<%
			hasButtons = true;
			%>

			<input type="button" value='<%= LanguageUtil.get(pageContext, (searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE)) %>' onClick="<portlet:namespace />deleteUsers('<%= searchTerms.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>');" />
		</c:if>

		<c:if test="<%= !searchTerms.isActive() %>">

			<%
			hasButtons = true;
			%>

			<input type="button" value="<liferay-ui:message key="restore" />" onClick="<portlet:namespace />deleteUsers('<%= Constants.RESTORE %>');" />
		</c:if>

		<c:if test="<%= RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), RoleConstants.ADMINISTRATOR, true) %>">

			<%
			hasButtons = true;
			%>

			<input type="button" value="<liferay-ui:message key="export" />" onClick="<%= exportProgressId %>.startProgress(); <portlet:namespace />exportUsers('<%= exportProgressId %>');" />

			<liferay-ui:upload-progress
				id="<%= exportProgressId %>"
				message="exporting"
				redirect="<%= HtmlUtil.escape(currentURL) %>"
			/>
		</c:if>

		<c:if test="<%= hasButtons %>">
			<div>
				<br />
			</div>
		</c:if>

		<liferay-ui:search-iterator />
	</c:if>
</liferay-ui:search-container>