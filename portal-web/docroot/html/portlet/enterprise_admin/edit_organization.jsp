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
themeDisplay.setIncludeServiceJs(true);

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

Organization organization = (Organization)request.getAttribute(WebKeys.ORGANIZATION);

long organizationId = BeanParamUtil.getLong(organization, request, "organizationId");

String[] mainSections = PropsValues.ORGANIZATIONS_FORM_ADD_MAIN;
String[] identificationSections = PropsValues.ORGANIZATIONS_FORM_ADD_IDENTIFICATION;
String[] miscellaneousSections = PropsValues.ORGANIZATIONS_FORM_ADD_MISCELLANEOUS;

if (organization != null) {
	mainSections = PropsValues.ORGANIZATIONS_FORM_UPDATE_MAIN;
	identificationSections = PropsValues.ORGANIZATIONS_FORM_UPDATE_IDENTIFICATION;
	miscellaneousSections = PropsValues.ORGANIZATIONS_FORM_UPDATE_MISCELLANEOUS;
}

String[] allSections = ArrayUtil.append(mainSections, ArrayUtil.append(identificationSections, miscellaneousSections));

String[][] categorySections = {mainSections, identificationSections, miscellaneousSections};

String curSection = mainSections[0];
%>

<script type="text/javascript">
	function <portlet:namespace />createURL(href, value, onclick) {
		return '<a href="' + href + '"' + (onclick ? ' onclick="' + onclick + '" ' : '') + '>' + value + '</a>';
	};

	function <portlet:namespace />saveOrganization() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (organization == null) ? Constants.ADD : Constants.UPDATE %>";

		var redirect = "<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /><portlet:param name="backURL" value="<%= backURL %>"></portlet:param></portlet:renderURL>";

		if (location.hash) {
			redirect += location.hash;
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-util:include page="/html/portlet/enterprise_admin/organization/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value='<%= (organization == null) ? "add" : "view-all" %>' />
</liferay-util:include>

<c:if test="<%= Validator.isNotNull(backURL) %>">
	<div align="right">
		<a href="<%= HtmlUtil.escape(backURL) %>">&laquo;<liferay-ui:message key="back" /></a>
	</div>
</c:if>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_organization" /></portlet:actionURL>" class="uni-form" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveOrganization(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="" />
<input name="<portlet:namespace />backURL" type="hidden" value="<%= HtmlUtil.escape(backURL) %>" />
<input name="<portlet:namespace />organizationId" type="hidden" value="<%= organizationId %>" />

<div id="<portlet:namespace />sectionsContainer">
	<table class="organization-table" width="100%">
	<tr>
		<td>

			<%
			request.setAttribute("addresses.className", Organization.class.getName());
			request.setAttribute("addresses.classPK", organizationId);
			request.setAttribute("emailAddresses.className", Organization.class.getName());
			request.setAttribute("emailAddresses.classPK", organizationId);
			request.setAttribute("phones.className", Organization.class.getName());
			request.setAttribute("phones.classPK", organizationId);
			request.setAttribute("websites.className", Organization.class.getName());
			request.setAttribute("websites.classPK", organizationId);

			for (String section : allSections) {
				String sectionId = _getSectionId(section);
				String sectionJsp = "/html/portlet/enterprise_admin/organization/" + _getSectionJsp(section) + ".jsp";
			%>

				<div class="form-section <%= curSection.equals(section)? "selected" : StringPool.BLANK %>" id="<%= sectionId %>">
					<liferay-util:include page="<%= sectionJsp %>" />
				</div>

			<%
			}
			%>

			<div class="lfr-component form-navigation">
				<div class="organization-info">
					<p class="float-container">
						<c:if test="<%= organization != null %>">

							<%
							long logoId = organization.getLogoId();
							%>

							<img alt="<%= organization.getName() %>" class="avatar" src="<%= themeDisplay.getPathImage() %>/organization_logo?img_id=<%= logoId %>&t=<%= ImageServletTokenUtil.getToken(logoId) %>" />

							<span><%= organization.getName() %></span>
						</c:if>
					</p>
				</div>

				<%
				String[] categoryNames = _CATEGORY_NAMES;
				%>

				<%@ include file="/html/portlet/enterprise_admin/categories_navigation.jspf" %>

				<div class="button-holder">
					<input type="button" value="<liferay-ui:message key="save" />" onClick="<portlet:namespace />saveOrganization();" />

					<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(backURL) %>';" />
				</div>
			</div>
		</td>
	</tr>
	</table>
</div>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</script>
</c:if>

<%!
private static String[] _CATEGORY_NAMES = {"organization-information", "identification", "miscellaneous"};
%>