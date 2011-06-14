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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "sites-owned");

boolean showTabs1 = true;

if (portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES)) {
	if (permissionChecker.isCompanyAdmin()) {
		tabs1 = "all-sites";
	}
	else {
		tabs1 = "sites-joined";
	}

	showTabs1 = false;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/view");
portletURL.setParameter("tabs1", tabs1);

pageContext.setAttribute("portletURL", portletURL);
%>

<liferay-ui:success key="membership_request_sent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<c:choose>
		<c:when test="<%= showTabs1 %>">
			<liferay-ui:tabs
				names="sites-owned,sites-joined,available-sites"
				url="<%= portletURL.toString() %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/sites_admin/toolbar.jsp">
				<liferay-util:param name="toolbarItem" value="view-all" />
			</liferay-util:include>
		</c:otherwise>
	</c:choose>

	<%
	GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);
	%>

	<liferay-ui:search-form
		page="/html/portlet/users_admin/group_search.jsp"
		searchContainer="<%= searchContainer %>"
		showAddButton="<%= showTabs1 %>"
	/>

	<%
	GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap groupParams = new LinkedHashMap();

	groupParams.put("site", Boolean.TRUE);

	if (tabs1.equals("sites-owned")) {
		Role role = RoleLocalServiceUtil.getRole(company.getCompanyId(), RoleConstants.SITE_OWNER);

		List userGroupRole = new ArrayList();

		userGroupRole.add(new Long(user.getUserId()));
		userGroupRole.add(new Long(role.getRoleId()));

		groupParams.put("userGroupRole", userGroupRole);
		//groupParams.put("active", Boolean.TRUE);
	}
	else if (tabs1.equals("sites-joined")) {
		groupParams.put("usersGroups", new Long(user.getUserId()));
		//groupParams.put("active", Boolean.TRUE);
	}
	else if (tabs1.equals("available-sites")) {
		List types = new ArrayList();

		types.add(new Integer(GroupConstants.TYPE_SITE_OPEN));
		types.add(new Integer(GroupConstants.TYPE_SITE_RESTRICTED));

		groupParams.put("types", types);
		groupParams.put("active", Boolean.TRUE);
	}

	int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams);

	searchContainer.setTotal(total);

	List results = GroupLocalServiceUtil.search(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);
	%>

	<div class="separator"><!-- --></div>

	<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

		<%
		NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

		PKParser pkParser = new PKParser(nslse.getMessage());

		long groupId = pkParser.getLong("groupId");

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		%>

		<liferay-ui:message arguments="<%= group.getDescriptiveName() %>" key="site-x-does-not-have-any-private-pages" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= RequiredGroupException.class %>">

		<%
		RequiredGroupException rge = (RequiredGroupException)errorException;

		long groupId = GetterUtil.getLong(rge.getMessage());

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		%>

		<c:choose>
			<c:when test="<%= PortalUtil.isSystemGroup(group.getName()) %>">
				<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
			</c:otherwise>
		</c:choose>
	</liferay-ui:error>

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("name");
	headerNames.add("type");
	headerNames.add("members");
	headerNames.add("online-now");

	if (tabs1.equals("sites-owned") || tabs1.equals("sites-joined") || tabs1.equals("all-sites")) {
		headerNames.add("active");
	}

	if (tabs1.equals("sites-owned")) {
		headerNames.add("pending-requests");
	}

	headerNames.add(StringPool.BLANK);

	searchContainer.setHeaderNames(headerNames);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Group group = (Group)results.get(i);

		group = group.toEscapedModel();

		ResultRow row = new ResultRow(new Object[] {group, tabs1}, group.getGroupId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setWindowState(WindowState.NORMAL);

		rowURL.setParameter("struts_action", "/sites_admin/edit_site");
		rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		rowURL.setParameter("redirect", currentURL);

		// Name

		StringBundler sb = new StringBundler();

		sb.append("<a href=\"");
		sb.append(rowURL.toString());
		sb.append("\">");
		sb.append(HtmlUtil.escape(group.getDescriptiveName()));
		sb.append("</a>");

		if (group.isOrganization()) {
			Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());

			sb.append("<br />");
			sb.append(LanguageUtil.format(pageContext, "belongs-to-an-organization-of-type-x", LanguageUtil.get(pageContext, organization.getType())));
		}

		row.addText(sb.toString());

		// Type

		row.addText(LanguageUtil.get(pageContext, group.getTypeLabel()), rowURL);

		// Members

		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("usersGroups", new Long(group.getGroupId()));

		int membersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);

		row.addText(String.valueOf(membersCount));

		// Online Now

		int onlineCount = LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId());

		row.addText(String.valueOf(onlineCount));

		// Active

		if (tabs1.equals("sites-owned") || tabs1.equals("sites-joined") || tabs1.equals("all-sites")) {
			row.addText(LanguageUtil.get(pageContext, (group.isActive() ? "yes" : "no")));
		}

		// Restricted number of petitions

		if (tabs1.equals("sites-owned")) {
			int pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);

			if (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) {
				row.addText(String.valueOf(pendingRequests));
			}
			else {
				row.addText(StringPool.BLANK);
			}
		}

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/sites_admin/site_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>