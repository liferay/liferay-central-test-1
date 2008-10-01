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
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<input name="<portlet:namespace />deleteOrganizationIds" type="hidden" value="" />

<liferay-ui:error exception="<%= RequiredOrganizationException.class %>" message="you-cannot-delete-organizations-that-have-suborganizations-or-users" />

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbar-item" value="view-organizations" />
</liferay-util:include>

<%
OrganizationSearch orgSearchContainer = new OrganizationSearch(renderRequest, portletURL);

portletURL.setParameter(orgSearchContainer.getCurParam(), String.valueOf(orgSearchContainer.getCurValue()));
%>

<input name="<portlet:namespace />organizationsRedirect" type="hidden" value="<%= portletURL.toString() %>" />

<liferay-ui:search-container
	searchContainer="<%= orgSearchContainer %>"
	rowChecker="<%= new RowChecker(renderResponse) %>"
>
	<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/organization_search.jsp"
	/>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">

		<%
		OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap organizationParams = new LinkedHashMap();

		if (filterManageableOrganizations) {
			List manageableOrganizations = OrganizationLocalServiceUtil.getManageableOrganizations(user.getUserId());

			Long[] manageableOrganizationIds = EnterpriseAdminUtil.getOrganizationIds(manageableOrganizations);

			organizationParams.put("organizations", manageableOrganizationIds);
		}

		long parentOrganizationId = ParamUtil.getLong(request, "parentOrganizationId", OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		if (parentOrganizationId <= 0) {
			parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;
		}
		%>

		<%@ include file="/html/portlet/enterprise_admin/organization_search_results.jspf" %>

		<liferay-ui:search-container-results
			results="<%= results1 %>"
			total="<%= total1 %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Organization"
			keyProperty="organizationId"
			modelVar="organization"
		>
			<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="rowURL" >
				<portlet:param name="struts_action" value="/enterprise_admin/edit_organization" />
				<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				orderable="<%= true %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				buffer="sb"
				href="<%= rowURL %>"
				name="parent-organization"
			>

				<%
				if (organization.getParentOrganizationId() > 0) {
					try {
						Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

						sb.append(parentOrganization.getName());
					}
					catch (Exception e) {
					}
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				orderable="<%= true %>"
				orderableProperty="type"
				value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="city"
				value="<%= organization.getAddress().getCity() %>"
			/>

			<liferay-ui:search-container-column-text
				buffer="sb"
				href="<%= rowURL %>"
				name="region"
			>

				<%
				String regionName = organization.getAddress().getRegion().getName();

				if (Validator.isNull(regionName)) {
					try {
						Region region = RegionServiceUtil.getRegion(organization.getRegionId());

						regionName = LanguageUtil.get(pageContext, region.getName());
					}
					catch (NoSuchRegionException nsce) {
					}
				}

				sb.append(regionName);
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				buffer="sb"
				href="<%= rowURL %>"
				name="country"
			>

				<%
				String countryName = organization.getAddress().getCountry().getName();

				if (Validator.isNull(countryName)) {
					try {
						Country country = CountryServiceUtil.getCountry(organization.getCountryId());

						countryName = LanguageUtil.get(pageContext, country.getName());
					}
					catch (NoSuchCountryException nsce) {
					}
				}

				sb.append(countryName);
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/enterprise_admin/organization_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<div class="separator"><!-- --></div>

		<input type="button" value="<liferay-ui:message key="delete" />" onClick="<portlet:namespace />deleteOrganizations();" />

		<br /><br />

		<liferay-ui:search-iterator />
	</c:if>
</liferay-ui:search-container>