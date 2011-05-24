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
String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

long groupId = BeanParamUtil.getLong(group, request, "groupId");

String friendlyURL = BeanParamUtil.getString(group, request, "friendlyURL");

LayoutSetPrototype layoutSetPrototype = null;

long layoutSetPrototypeId = ParamUtil.getLong(request, "layoutSetPrototypeId");

if (layoutSetPrototypeId > 0) {
	layoutSetPrototype = LayoutSetPrototypeServiceUtil.getLayoutSetPrototype(layoutSetPrototypeId);
}
%>

<c:if test="<%= !portletName.equals(PortletKeys.COMMUNITIES) %>">
	<liferay-util:include page="/html/portlet//sites_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (group == null) ? "add" : "view-all" %>' />
	</liferay-util:include>
</c:if>

<%
String title = "new-site";

if (group != null) {
	title = group.getDescriptiveName();
}
else if (layoutSetPrototype != null) {
	title = layoutSetPrototype.getName(locale);
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="<%= title %>"
/>

<portlet:actionURL var="editCommunityURL">
	<portlet:param name="struts_action" value="/sites_admin/edit_site" />
</portlet:actionURL>

<aui:form action="<%= editCommunityURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="friendlyURL" type="hidden" value="<%= friendlyURL %>" />

	<liferay-ui:error exception="<%= DuplicateGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= GroupNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="old-group-name-is-a-required-system-group" />

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

	<aui:fieldset>
		<c:if test="<%= group != null %>">
			<aui:field-wrapper label="site-id">
				<%= groupId %>
			</aui:field-wrapper>
		</c:if>

		<c:choose>
			<c:when test="<%= (group != null) && PortalUtil.isSystemGroup(group.getName()) %>">
				<aui:input name="name" type="hidden" />
			</c:when>
			<c:otherwise>
				<aui:input name="name" />
			</c:otherwise>
		</c:choose>

		<aui:input name="description" />

		<aui:select name="type" label="membership-type">
			<aui:option label="open" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
			<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_RESTRICTED %>" />
			<aui:option label="private" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
		</aui:select>

		<aui:input inlineLabel="left" name="active" value="<%= true %>" />

		<aui:input name="categories" type="assetCategories" />

		<aui:input name="tags" type="assetTags" />

		<%
		List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<c:choose>
			<c:when test="<%= (group != null) || (!layoutSetPrototypes.isEmpty() && (layoutSetPrototype == null)) %>">
				<br />

				<aui:fieldset label="pages">
					<c:choose>
						<c:when test="<%= ((group == null) || (group.getPublicLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select label="public-pages" name="publicLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
								%>

									<aui:option value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= curLayoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

								<%
								}
								%>

							</aui:select>
						</c:when>
						<c:otherwise>
							<aui:field-wrapper label="public-pages">
								<c:choose>
									<c:when test="<%= (group != null) && (group.getPublicLayoutsPageCount() > 0) %>">
										<liferay-portlet:actionURL var="publicPagesURL" portletName="<%= PortletKeys.MY_PLACES %>">
											<portlet:param name="struts_action" value="/my_places/view" />
											<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
											<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
										</liferay-portlet:actionURL>

										<liferay-ui:icon
											image="view"
											label="<%= true %>"
											message="open-public-pages"
											method="get"
											target="_blank"
											url="<%= publicPagesURL.toString() %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="this-site-does-not-have-any-public-pages" />
									</c:otherwise>
								</c:choose>
							</aui:field-wrapper>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when test="<%= ((group == null) || (group.getPrivateLayoutsPageCount() == 0)) && !layoutSetPrototypes.isEmpty() %>">
							<aui:select label="private-pages" name="privateLayoutSetPrototypeId">
								<aui:option label="none" selected="<%= true %>" value="" />

								<%
								for (LayoutSetPrototype curLayoutSetPrototype : layoutSetPrototypes) {
								%>

									<aui:option value="<%= curLayoutSetPrototype.getLayoutSetPrototypeId() %>"><%= curLayoutSetPrototype.getName(user.getLanguageId()) %></aui:option>

								<%
								}
								%>

							</aui:select>
						</c:when>
						<c:otherwise>
							<aui:field-wrapper label="private-pages">
								<c:choose>
									<c:when test="<%= (group != null) && (group.getPrivateLayoutsPageCount() > 0) %>">
										<liferay-portlet:actionURL var="privatePagesURL" portletName="<%= PortletKeys.MY_PLACES %>">
											<portlet:param name="struts_action" value="/my_places/view" />
											<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
											<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
										</liferay-portlet:actionURL>

										<liferay-ui:icon
											image="view"
											label="<%= true %>"
											message="open-private-pages"
											method="get"
											target="_blank"
											url="<%= privatePagesURL.toString() %>"
										/>
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="this-site-does-not-have-any-private-pages" />
									</c:otherwise>
								</c:choose>
							</aui:field-wrapper>
						</c:otherwise>
					</c:choose>

					<aui:field-wrapper name="site-template-relationship">
						<aui:input checked="<%= true %>" inlineLabel="right" label="cloned" name="siteTemplateRelationship" type="radio" value="cloned" />

						<aui:input inlineLabel="right" label="inherited" name="siteTemplateRelationship" type="radio" value="inherited" />
					</aui:field-wrapper>
				</aui:fieldset>
			</c:when>
			<c:when test="<%= layoutSetPrototype != null %>">
				<aui:fieldset label="pages">
					<br />

					<aui:input name="layoutSetPrototypeId" type="hidden" value="<%= layoutSetPrototypeId %>" />

					<aui:select label="visibility" name="privateLayoutSetPrototype">
						<aui:option label="public" value="0" />
						<aui:option label="private" value="1" />
					</aui:select>

					<aui:field-wrapper name="site-template-relationship">
						<aui:input checked="<%= true %>" inlineLabel="right" label="cloned" name="siteTemplateRelationship" type="radio" value="cloned" />

						<aui:input inlineLabel="right" label="inherited" name="siteTemplateRelationship" type="radio" value="inherited" />
					</aui:field-wrapper>
				</aui:fieldset>
			</c:when>
		</c:choose>

		<%
		Set<String> servletContextNames = CustomJspRegistryUtil.getServletContextNames();
		%>

		<c:if test="<%= !servletContextNames.isEmpty() %>">

			<%
			String customJspServletContextName = StringPool.BLANK;

			if (group != null) {
				UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

				customJspServletContextName = GetterUtil.getString(typeSettingsProperties.get("customJspServletContextName"));
			}
			%>

			<aui:select label="apply-add-on" name="customJspServletContextName">
				<aui:option label="none" />

				<%
				for (String servletContextName : servletContextNames) {
				%>

					<aui:option label="<%= servletContextName %>" selected="<%= customJspServletContextName.equals(servletContextName) %>" value="<%= servletContextName %>" />

				<%
				}
				%>

			</aui:select>
		</c:if>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (group == null) ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (group != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.escape(group.getDescriptiveName()), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-site"), currentURL);
}
%>