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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");
String backURL = ParamUtil.getString(request, "backURL", viewUsersRedirect);

String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/users_admin/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);

if (Validator.isNotNull(viewUsersRedirect)) {
	portletURL.setParameter("viewUsersRedirect", viewUsersRedirect);
}

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();
%>

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />
<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

<aui:form action="<%= portletURLString %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURLString %>" />

	<liferay-util:include page="/html/portlet/users_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value="view" />
	</liferay-util:include>

	<%
	String usersListView = request.getParameter("usersListView");

	PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

	if (usersListView == null) {
		usersListView = portalPreferences.getValue(PortletKeys.USERS_ADMIN, "users-list-view", PropsValues.USERS_LIST_VIEWS_DEFAULT);
	}
	else {
		boolean saveUsersListView = ParamUtil.getBoolean(request, "saveUsersListView");

		if (saveUsersListView && ArrayUtil.contains(PropsValues.USERS_LIST_VIEWS, usersListView)) {
			portalPreferences.setValue(PortletKeys.USERS_ADMIN, "users-list-view", usersListView);
		}
	}
	%>

	<c:choose>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_ORGANIZATIONS) %>">
			<%@ include file="/html/portlet/users_admin/view_flat_organizations.jspf" %>
		</c:when>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USER_GROUPS) %>">
			<%@ include file="/html/portlet/users_admin/view_flat_user_groups.jspf" %>
		</c:when>
		<c:when test="<%= usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS) %>">
			<%@ include file="/html/portlet/users_admin/view_flat_users.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/users_admin/view_tree.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteOrganization(organizationId) {
		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= Organization.class.getName() %>', organizationId);
	}

	function <portlet:namespace />deleteUserGroup(userGroupId) {
		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= UserGroup.class.getName() %>', userGroupId);
	}

	function <portlet:namespace />doDeleteOrganizationOrUserGroup(className, id) {
		var ids = id;

		<portlet:namespace />getUsersCount(
			className, ids, false,
			function(event, id, obj) {
				var responseData = this.get('responseData');
				var count = parseInt(responseData);

				if (count > 0) {
					<portlet:namespace />getUsersCount(
						className, ids, true,
						function(event, id, obj) {
							responseData = this.get('responseData')
							count = parseInt(responseData);

							if (count > 0) {
								if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
									<portlet:namespace />doDeleteOrganizations(ids);
								}
							}
							else {
								var message = null;

								if (id && (id.toString().split(",").length > 1)) {
									if (className == '<%= Organization.class.getName() %>') {
										message = '<%= UnicodeLanguageUtil.get(pageContext, "one-or-more-organizations-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organizations-by-automatically-unassociating-the-deactivated-users") %>';
									}
									else {
										message = '<%= UnicodeLanguageUtil.get(pageContext, "one-or-more-user-groups-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-groups-by-automatically-unassociating-the-deactivated-users") %>';
									}
								}
								else {
									if (className == '<%= Organization.class.getName() %>') {
										message = '<%= UnicodeLanguageUtil.get(pageContext, "the-selected-organization-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-organization-by-automatically-unassociating-the-deactivated-users") %>';
									}
									else {
										message = '<%= UnicodeLanguageUtil.get(pageContext, "the-selected-user-group-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-group-by-automatically-unassociating-the-deactivated-users") %>';
									}
								}

								if (confirm(message)) {
									if (className == '<%= Organization.class.getName() %>') {
										<portlet:namespace />doDeleteOrganizations(ids);
									}
									else {
										<portlet:namespace />doDeleteUserGroups(ids);
									}
								}
							}
						}
					);
				}
				else {
					if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
						if (className == '<%= Organization.class.getName() %>') {
							<portlet:namespace />doDeleteOrganizations(ids);
						}
						else {
							<portlet:namespace />doDeleteUserGroups(ids);
						}
					}
				}
			}
		);
	}

	function <portlet:namespace />doDeleteOrganizations(organizationIds) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />organizationsRedirect.value;
		document.<portlet:namespace />fm.<portlet:namespace />deleteOrganizationIds.value = organizationIds;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_organization" /></portlet:actionURL>");
	}

	function <portlet:namespace />doDeleteUserGroups(userGroupIds) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />userGroupsRedirect.value;
		document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = userGroupIds;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user_group" /></portlet:actionURL>");
	}

	Liferay.provide(
		window,
		'<portlet:namespace />deleteOrganizations',
		function() {
			var organizationIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (!organizationIds) {
				return;
			}

			<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= Organization.class.getName() %>', organizationIds);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />deleteUserGroups',
	function() {
			var userGroupIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (!userGroupIds) {
				return;
			}

			<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= UserGroup.class.getName() %>', userGroupIds);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />deleteUsers',
		function(cmd) {
			var deleteUsers = true;

			var deleteUserIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

			if (!deleteUserIds) {
				deleteUsers = false;
			}
			else if (cmd == "<%= Constants.DEACTIVATE %>") {
				if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-the-selected-users") %>')) {
					deleteUsers = false;
				}
			}
			else if (cmd == "<%= Constants.DELETE %>") {
				if (!confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-permanently-delete-the-selected-users") %>')) {
					deleteUsers = false;
				}
			}

			if (deleteUsers) {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
				document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />usersRedirect.value;
				document.<portlet:namespace />fm.<portlet:namespace />deleteUserIds.value = deleteUserIds;
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/users_admin/edit_user" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />getUsersCount',
		function(className, ids, active, callback) {
			var A = AUI();

			A.io.request(
				'<%= themeDisplay.getPathMain() %>/users_admin/get_users_count',
				{
					data: {
						active: active,
						className: className,
						ids: ids
					},
					on: {
						success: callback
					}
				}
			);
		},
		['aui-io']
	);
</aui:script>