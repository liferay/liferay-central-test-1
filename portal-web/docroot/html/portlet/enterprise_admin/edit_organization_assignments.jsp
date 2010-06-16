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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "users");
String tabs3 = ParamUtil.getString(request, "tabs3", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/enterprise_admin/edit_organization_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("organizationId", String.valueOf(organization.getOrganizationId()));

request.setAttribute("edit_organization_assignments.jsp-tabs2", tabs2);
request.setAttribute("edit_organization_assignments.jsp-tabs3", tabs3);

request.setAttribute("edit_organization_assignments.jsp-cur", cur);

request.setAttribute("edit_organization_assignments.jsp-redirect", redirect);

request.setAttribute("edit_organization_assignments.jsp-organization", organization);

request.setAttribute("edit_organization_assignments.jsp-portletURL", portletURL);
%>

<portlet:actionURL var="editAssignmentsURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_organization_assignments" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editAssignmentsURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="<%= tabs3 %>" />
	<aui:input name="assignmentsRedirect" type="hidden" />
	<aui:input name="organizationId" type="hidden" value="<%= organization.getOrganizationId() %>" />

	<liferay-ui:message key="edit-assignments-for-organization" />: <%= HtmlUtil.escape(organization.getName()) %>

	<br /><br />

	<%
	String tabs2Names = "users";

	if (PropsValues.ORGANIZATIONS_USER_GROUP_MEMBERSHIP_ENABLED) {
		tabs2Names += ",user-groups";
	}
	%>

	<liferay-ui:tabs
		names="<%= tabs2Names %>"
		param="tabs2"
		url="<%= portletURL.toString() %>"
		backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
	/>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<liferay-util:include page="/html/portlet/enterprise_admin/edit_organization_assignments_users.jsp" />
		</c:when>
		<c:when test='<%= tabs2.equals("user-groups") %>'>
			<liferay-util:include page="/html/portlet/enterprise_admin/edit_organization_assignments_user_groups.jsp" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script>
	function <portlet:namespace />updateOrganizationUserGroups(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'organization_user_groups';
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;

		Liferay.Util.listCheckedExcept(
			document.<portlet:namespace />fm,
			'<portlet:namespace />allRowIds',
			function(addGroups) {
				document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = addGroups;

				Liferay.Util.listUncheckedExcept(
					document.<portlet:namespace />fm,
					'<portlet:namespace />allRowIds',
					function(removeGroups) {
						document.<portlet:namespace />fm.<portlet:namespace />removeUserGroupIds.value = removeGroups;

						submitForm(document.<portlet:namespace />fm);
					}
				);
			}
		);
	}

	function <portlet:namespace />updateOrganizationUsers(assignmentsRedirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'organization_users';
		document.<portlet:namespace />fm.<portlet:namespace />assignmentsRedirect.value = assignmentsRedirect;

		Liferay.Util.listCheckedExcept(
			document.<portlet:namespace />fm,
			'<portlet:namespace />allRowIds',
			function(addUsers) {
				document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = addUsers;

				Liferay.Util.listUncheckedExcept(
					document.<portlet:namespace />fm,
					'<portlet:namespace />allRowIds',
					function(removeUsers) {
						document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = removeUsers;

						submitForm(document.<portlet:namespace />fm);
					}
				);
			}
		);
	}
</aui:script>

<%
EnterpriseAdminUtil.addPortletBreadcrumbEntries(organization, request, renderResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "assign-members"), currentURL);
%>