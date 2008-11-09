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
String tabs2 = ParamUtil.getString(request, "tabs2");
String tabs3 = ParamUtil.getString(request, "tabs3");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/enterprise_admin/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("tabs3", tabs3);

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-portletURLString", portletURLString);
%>

<script type="text/javascript">
	function <portlet:namespace />deleteOrganization(organizationId) {
		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= Organization.class.getName() %>', organizationId);
	}

	function <portlet:namespace />deleteOrganizations() {
		var organizationIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

		if (!organizationIds) {
			return;
		}

		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= Organization.class.getName() %>', organizationIds);
	}

	function <portlet:namespace />deleteUserGroup(userGroupId) {
		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= UserGroup.class.getName() %>', userGroupId);
	}

	function <portlet:namespace />deleteUserGroups() {
		var userGroupIds = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");

		if (!userGroupIds) {
			return;
		}

		<portlet:namespace />doDeleteOrganizationOrUserGroup('<%= UserGroup.class.getName() %>', userGroupIds);
	}

	function <portlet:namespace />deleteUsers(cmd) {
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
			submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:actionURL>");
		}
	}

	function <portlet:namespace />doDeleteOrganizationOrUserGroup(className, id) {
		var ids = id;

		<portlet:namespace />getUsersCount(
			className, ids, false,
			function(count) {
				count = parseInt(count);

				if (count > 0) {
					<portlet:namespace />getUsersCount(
						className, ids, true,
						function(count) {
							count = parseInt(count);

							if (count > 0) {
								if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this") %>')) {
									<portlet:namespace />doDeleteOrganizations(ids);
								}
							}
							else {
								var message = null;

								if (id && (id.split(",").length > 1)) {
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
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /></portlet:actionURL>");
	}

	function <portlet:namespace />doDeleteUserGroups(userGroupIds) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />userGroupsRedirect.value;
		document.<portlet:namespace />fm.<portlet:namespace />deleteUserGroupIds.value = userGroupIds;
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user_group" /></portlet:actionURL>");
	}

	function <portlet:namespace />getUsersCount(className, ids, active, callback) {
		jQuery.ajax(
			{
				url: '<%= themeDisplay.getPathMain() %>/enterprise_admin/get_users_count',
				data: {
					className: className,
					ids: ids,
					active: active
				},
				success: callback
			}
		);
	}

	function <portlet:namespace />saveCompany() {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_company" /></portlet:actionURL>");
	}

	function <portlet:namespace />saveSettings(cmd) {
		document.<portlet:namespace />fm.method = "post";
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = cmd;
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /><portlet:param name="tabs2" value="<%= tabs2 %>" /><portlet:param name="tabs3" value="<%= tabs3 %>" /></portlet:renderURL>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_settings" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURLString %>" method="get" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<liferay-portlet:renderURLParams varImpl="portletURL" />
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escape(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escape(tabs2) %>" />
<input name="<portlet:namespace />tabs3" type="hidden" value="<%= HtmlUtil.escape(tabs3) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= portletURLString %>" />

<c:if test="<%= showTabs1 %>">
	<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp" />
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("users") %>'>
		<liferay-util:include page="/html/portlet/enterprise_admin/view_users.jsp" />
	</c:when>
	<c:when test='<%= tabs1.equals("organizations") %>'>
		<liferay-util:include page="/html/portlet/enterprise_admin/view_organizations.jsp" />
	</c:when>
	<c:when test='<%= tabs1.equals("user-groups") %>'>
		<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="you-cannot-delete-user-groups-that-have-users" />

		<liferay-util:include page="/html/portlet/enterprise_admin/user_group/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value="view-all" />
		</liferay-util:include>

		<%
		RowChecker rowChecker = null;

		if (PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER_GROUP)) {
			rowChecker = new RowChecker(renderResponse);
		}
		%>

		<liferay-ui:search-container
			rowChecker="<%= rowChecker %>"
			searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
		>
			<input name="<portlet:namespace />deleteUserGroupIds" type="hidden" value="" />
			<input name="<portlet:namespace />userGroupsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/user_group_search.jsp"
			/>

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

				<%
				UserGroupSearchTerms searchTerms = (UserGroupSearchTerms)searchContainer.getSearchTerms();
				%>

				<liferay-ui:search-container-results
					results="<%= UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
					total="<%= UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), null) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.model.UserGroup"
					escapedModel="<%= true %>"
					keyProperty="userGroupId"
					modelVar="userGroup"
				>
					<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL">
						<portlet:param name="struts_action" value="/enterprise_admin/edit_user_group" />
						<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
						<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="name"
						orderable="<%= true %>"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="description"
						orderable="<%= true %>"
						property="description"
					/>

					<liferay-ui:search-container-column-jsp
						align="right"
						path="/html/portlet/enterprise_admin/user_group_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<div class="separator"><!-- --></div>

				<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER_GROUP) %>">
					<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteUserGroups();" />

					<br /><br />
				</c:if>

				<liferay-ui:search-iterator />
			</c:if>
		</liferay-ui:search-container>
	</c:when>
	<c:when test='<%= tabs1.equals("roles") %>'>
		<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="you-cannot-delete-a-system-role" />

		<liferay-util:include page="/html/portlet/enterprise_admin/role/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value="view-all" />
		</liferay-util:include>

		<%
		RoleSearch searchContainer = new RoleSearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add(StringPool.BLANK);
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/role_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();

			int total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj());

			searchContainer.setTotal(total);

			List results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), searchTerms.getTypeObj(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
			%>

			<input name="<portlet:namespace />rolesRedirect" type="hidden" value="<%= portletURL.toString() %>" />

			<div class="separator"><!-- --></div>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				Role role = (Role)results.get(i);

				ResultRow row = new ResultRow(role, role.getRoleId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_role");
				rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));

				// Name

				row.addText(role.getTitle(locale), rowURL);

				// Type

				row.addText(LanguageUtil.get(pageContext, role.getTypeLabel()), rowURL);

				// Subtype

				if ((PropsValues.ROLES_COMMUNITY_SUBTYPES.length > 0) ||
					(PropsValues.ROLES_ORGANIZATION_SUBTYPES.length > 0) ||
					(PropsValues.ROLES_REGULAR_SUBTYPES.length > 0)) {

					row.addText(LanguageUtil.get(pageContext, role.getSubtype()), rowURL);
				}

				// Description

				row.addText(role.getDescription(), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/role_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("password-policies") %>'>

		<%
		boolean passwordPolicyEnabled = PortalLDAPUtil.isPasswordPolicyEnabled(company.getCompanyId());
		%>

		<c:if test="<%= passwordPolicyEnabled %>">
			<span class="portlet-msg-info">
				<liferay-ui:message key="you-are-using-ldaps-password-policy" />
			</span>
		</c:if>

		<liferay-util:include page="/html/portlet/enterprise_admin/password_policy/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value="view-all" />
		</liferay-util:include>

		<%
		PasswordPolicySearch searchContainer = new PasswordPolicySearch(renderRequest, portletURL);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.add(StringPool.BLANK);
		%>

		<c:if test="<%= !passwordPolicyEnabled %>">
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/password_policy_search.jsp"
				searchContainer="<%= searchContainer %>"
			/>
		</c:if>

		<c:if test="<%= !passwordPolicyEnabled && windowState.equals(WindowState.MAXIMIZED) %>">

			<%
			PasswordPolicySearchTerms searchTerms = (PasswordPolicySearchTerms)searchContainer.getSearchTerms();

			int total = PasswordPolicyLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName());

			searchContainer.setTotal(total);

			List results = PasswordPolicyLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			PortletURL passwordPoliciesRedirect = PortletURLUtil.clone(portletURL, renderResponse);

			passwordPoliciesRedirect.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
			%>

			<input name="<portlet:namespace />passwordPoliciesRedirect" type="hidden" value="<%= passwordPoliciesRedirect.toString() %>" />

			<div class="separator"><!-- --></div>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				PasswordPolicy passwordPolicy = (PasswordPolicy)results.get(i);

				ResultRow row = new ResultRow(passwordPolicy, passwordPolicy.getPasswordPolicyId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/enterprise_admin/edit_password_policy");
				rowURL.setParameter("redirect", searchContainer.getIteratorURL().toString());
				rowURL.setParameter("passwordPolicyId", String.valueOf(passwordPolicy.getPasswordPolicyId()));

				// Name

				row.addText(passwordPolicy.getName(), rowURL);

				// Description

				row.addText(passwordPolicy.getDescription(), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/enterprise_admin/password_policy_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
	</c:when>
	<c:when test='<%= tabs1.equals("settings") %>'>
		<liferay-util:include page="/html/portlet/enterprise_admin/view_settings.jsp" />
	</c:when>
	<c:when test='<%= tabs1.equals("monitoring") %>'>
		<c:choose>
			<c:when test="<%= PropsValues.SESSION_TRACKER_MEMORY_ENABLED %>">
				<liferay-ui:tabs
					names="live-sessions"
					param="tabs2"
					url="<%= portletURLString %>"
				/>

				<%
				SearchContainer searchContainer = new SearchContainer();

				List<String> headerNames = new ArrayList<String>();

				headerNames.add("session-id");
				headerNames.add("user-id");
				headerNames.add("name");
				headerNames.add("screen-name");
				//headerNames.add("email-address");
				headerNames.add("last-request");
				headerNames.add("num-of-hits");

				searchContainer.setHeaderNames(headerNames);
				searchContainer.setEmptyResultsMessage("there-are-no-live-sessions");

				List results = new ArrayList();

				Iterator itr = LiveUsers.getSessionUsers(company.getCompanyId()).entrySet().iterator();

				while (itr.hasNext()) {
					Map.Entry entry = (Map.Entry)itr.next();

					results.add(entry.getValue());
				}

				Collections.sort(results, new UserTrackerModifiedDateComparator());

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					UserTracker userTracker = (UserTracker)results.get(i);

					ResultRow row = new ResultRow(userTracker, userTracker.getUserTrackerId(), i);

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setWindowState(WindowState.MAXIMIZED);

					rowURL.setParameter("struts_action", "/enterprise_admin/edit_session");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("sessionId", userTracker.getSessionId());

					User user2 = null;

					try {
						user2 = UserLocalServiceUtil.getUserById(userTracker.getUserId());
					}
					catch (NoSuchUserException nsue) {
					}

					// Session ID

					row.addText(userTracker.getSessionId(), rowURL);

					// User ID

					row.addText(String.valueOf(userTracker.getUserId()), rowURL);

					// Name

					row.addText(((user2 != null) ? user2.getFullName() : LanguageUtil.get(pageContext, "not-available")), rowURL);

					// Screen Name

					row.addText(((user2 != null) ? user2.getScreenName() : LanguageUtil.get(pageContext, "not-available")), rowURL);

					// Email Address

					//row.addText(((user2 != null) ? user2.getEmailAddress() : LanguageUtil.get(pageContext, "not-available")), rowURL);

					// Last Request

					row.addText(dateFormatDateTime.format(userTracker.getModifiedDate()), rowURL);

					// # of Hits

					row.addText(String.valueOf(userTracker.getHits()), rowURL);

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</c:when>
			<c:otherwise>
				<%= LanguageUtil.format(pageContext, "display-of-live-session-data-is-disabled", PropsKeys.SESSION_TRACKER_MEMORY_ENABLED) %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test='<%= tabs1.equals("plugins") %>'>

		<%
		PortletURL installPluginsURL = null;

		boolean showEditPluginHREF = true;
		boolean showReindexButton = false;
		%>

		<%@ include file="/html/portlet/enterprise_admin/plugins.jspf" %>
	</c:when>
</c:choose>

</form>

<%!
private static final long[] _DURATIONS = {300, 600, 1800, 3600, 7200, 10800, 21600};
%>