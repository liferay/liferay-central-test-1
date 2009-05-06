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
int step = ParamUtil.getInteger(request, "step");
long userId = ParamUtil.getLong(request, "userId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/enterprise_admin/select_community_role");
portletURL.setParameter("userId", String.valueOf(userId));

User selUser = null;
long uniqueGroupId = 0;

List<Group> groups = null;

if (step == 1) {
	selUser = UserServiceUtil.getUserById(userId);

	groups = selUser.getGroups();

	if (filterManageableGroups) {
		groups = EnterpriseAdminUtil.filterGroups(permissionChecker, groups);
	}

	if (groups.size() == 1) {
		step = 2;

		uniqueGroupId = groups.get(0).getGroupId();
	}
}
%>

<form method="post" name="<portlet:namespace />fm">

<c:choose>
	<c:when test="<%= step == 1 %>">
		<script type="text/javascript">
			function <portlet:namespace />selectGroup(groupId) {
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = groupId;

				submitForm(document.<portlet:namespace />fm, "<%= portletURL.toString() %>");
			}
		</script>

		<input name="<portlet:namespace />step" type="hidden" value="2">
		<input name="<portlet:namespace />groupId" type="hidden" value="">

		<liferay-ui:tabs names="community-roles" />

		<div class="portlet-msg-info">
			<liferay-ui:message key="please-select-a-community-to-which-you-will-assign-a-community-role" />
		</div>

		<liferay-ui:search-container
			searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-container-results>

				<%
				total = groups.size();
				results = ListUtil.subList(groups, searchContainer.getStart(), searchContainer.getEnd());

				pageContext.setAttribute("results", results);
				pageContext.setAttribute("total", total);
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Group"
				escapedModel="<%= true %>"
				keyProperty="groupId"
				modelVar="group"
			>

				<%
				StringBuilder sb = new StringBuilder();

				sb.append("javascript: ");
				sb.append(renderResponse.getNamespace());
				sb.append("selectGroup('");
				sb.append(group.getGroupId());
				sb.append("');");

				String rowHREF = sb.toString();
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="name"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="type"
					value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>

	<c:when test="<%= step == 2 %>">
		<liferay-ui:tabs names="community-roles" />

		<%
		long groupId = ParamUtil.getLong(request, "groupId", uniqueGroupId);

		Group group = GroupServiceUtil.getGroup(groupId);

		portletURL.setParameter("step", "1");

		String breadcrumbs = "<a href=\"" + portletURL.toString() + "\">" + LanguageUtil.get(pageContext, "communities") + "</a> &raquo; " + HtmlUtil.escape(group.getDescriptiveName());
		%>

		<div class="breadcrumbs">
			<%= breadcrumbs %>
		</div>

		<liferay-ui:search-container
			headerNames="name"
			searchContainer="<%= new RoleSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/enterprise_admin/role_search.jsp"
			/>

			<%
			RoleSearchTerms searchTerms = (RoleSearchTerms)searchContainer.getSearchTerms();
			%>

			<liferay-ui:search-container-results>

				<%
				if (filterManageableRoles) {
					List<Role> roles = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(),  RoleConstants.TYPE_COMMUNITY, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

					roles = EnterpriseAdminUtil.filterRoles(permissionChecker, roles);

					total = roles.size();
					results = ListUtil.subList(roles, searchContainer.getStart(), searchContainer.getEnd());
				}
				else {
					results = RoleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_COMMUNITY, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
					total = RoleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), RoleConstants.TYPE_COMMUNITY);
				}

				pageContext.setAttribute("results", results);
				pageContext.setAttribute("total", total);
				%>

			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Role"
				escapedModel="<%= false %>"
				keyProperty="roleId"
				modelVar="role"
			>
				<liferay-util:param name="className" value="<%= EnterpriseAdminUtil.getCssClassName(role) %>" />
				<liferay-util:param name="classHoverName" value="<%= EnterpriseAdminUtil.getCssClassName(role) %>" />

				<%
				StringBuilder sb = new StringBuilder();

				sb.append("javascript: opener.");
				sb.append(renderResponse.getNamespace());
				sb.append("selectRole('");
				sb.append(role.getRoleId());
				sb.append("', '");
				sb.append(UnicodeFormatter.toString(role.getTitle(locale)));
				sb.append("', '");
				sb.append("communityRoles");
				sb.append("', '");
				sb.append(UnicodeFormatter.toString(group.getDescriptiveName()));
				sb.append("', '");
				sb.append(group.getGroupId());
				sb.append("');");
				sb.append("window.close();");

				String rowHREF = sb.toString();
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowHREF %>"
					name="title"
					value="<%= role.getTitle(locale) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<script type="text/javascript">
			Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
		</script>
	</c:when>
</c:choose>

</form>