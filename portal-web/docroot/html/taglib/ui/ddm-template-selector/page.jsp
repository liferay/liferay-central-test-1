<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.dynamicdatamapping.model.DDMTemplate" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.dynamicdatamapping.service.permission.DDMTemplatePermission" %>
<%@ page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>

<%
long classNameId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:ddm-template-select:classNameId"));
List<String> displayStyles = (List<String>)request.getAttribute("liferay-ui:ddm-template-select:displayStyles");
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:ddm-template-select:icon"), "configuration");
String label = (String)request.getAttribute("liferay-ui:ddm-template-select:label");
String preferenceName = (String)request.getAttribute("liferay-ui:ddm-template-select:preferenceName");
String preferenceValue = (String)request.getAttribute("liferay-ui:ddm-template-select:preferenceValue");
String refreshURL = (String)request.getAttribute("liferay-ui:ddm-template-select:refreshURL");

boolean showEmptyOption = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:ddm-template-select:showEmptyOption"));

DDMTemplate ddmTemplate = null;

long ddmTemplateGroupId = PortletDisplayTemplateUtil.getDDMTemplateGroupId(themeDisplay);

Group ddmTemplateGroup = GroupServiceUtil.getGroup(ddmTemplateGroupId);

if (preferenceValue.startsWith("ddmTemplate_")) {
	ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(ddmTemplateGroupId, preferenceValue);
}

List<DDMTemplate> companyPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(themeDisplay.getCompanyGroupId(), classNameId, 0);

List<DDMTemplate> groupPortletDDMTemplates = null;

if (ddmTemplateGroupId != themeDisplay.getCompanyGroupId()) {
	groupPortletDDMTemplates = DDMTemplateLocalServiceUtil.getTemplates(ddmTemplateGroupId, classNameId, 0);
}

String message = LanguageUtil.format(pageContext, "manage-display-templates-for-x", ddmTemplateGroup.getName(), false);

long controlPanelPlid = PortalUtil.getControlPanelPlid(company.getCompanyId());

LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(request, PortletKeys.PORTLET_DISPLAY_TEMPLATES, controlPanelPlid, PortletRequest.RENDER_PHASE);

liferayPortletURL.setDoAsGroupId(ddmTemplateGroupId);
liferayPortletURL.setParameter("struts_action", "/dynamic_data_mapping/view_template");
liferayPortletURL.setPortletMode(PortletMode.VIEW);
liferayPortletURL.setWindowState(LiferayWindowState.POP_UP);

String liferayPortletURLString = liferayPortletURL.toString();

liferayPortletURLString = HttpUtil.addParameter(liferayPortletURLString, "classNameId", classNameId);
%>

<aui:select id="displayStyle" label="<%= label %>" name='<%= "preferences--" + preferenceName + "--" %>'>
	<c:if test="<%= showEmptyOption %>">
		<aui:option label="default" selected="<%= Validator.isNull(preferenceValue) %>" />
	</c:if>

	<c:if test="<%= (displayStyles != null) && !displayStyles.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="default" />">

			<%
			for (String displayStyle : displayStyles) {
			%>

				<aui:option label="<%= HtmlUtil.escape(displayStyle) %>" selected="<%= preferenceValue.equals(displayStyle) %>" />

			<%
			}
			%>

		</optgroup>
	</c:if>

	<c:if test="<%= (companyPortletDDMTemplates != null) && !companyPortletDDMTemplates.isEmpty() %>">
		<optgroup label="<liferay-ui:message key="global" />">

			<%
			for (DDMTemplate companyPortletDDMTemplate : companyPortletDDMTemplates) {
				if (!DDMTemplatePermission.contains(permissionChecker, companyPortletDDMTemplate, ActionKeys.VIEW)) {
					continue;
				}
			%>

				<aui:option label="<%= HtmlUtil.escape(companyPortletDDMTemplate.getName(locale)) %>" selected="<%= (ddmTemplate != null) && (companyPortletDDMTemplate.getTemplateId() == ddmTemplate.getTemplateId()) %>" value='<%= "ddmTemplate_" + companyPortletDDMTemplate.getUuid() %>' />

			<%
			}
			%>

		</optgroup>
	</c:if>

	<c:if test="<%= (groupPortletDDMTemplates != null) && !groupPortletDDMTemplates.isEmpty() %>">
		<optgroup label="<%= themeDisplay.getScopeGroupName() %>">

		<%
		for (DDMTemplate groupPortletDDMTemplate : groupPortletDDMTemplates) {
			if (!DDMTemplatePermission.contains(permissionChecker, groupPortletDDMTemplate, ActionKeys.VIEW)) {
				continue;
			}
		%>

			<aui:option label="<%= HtmlUtil.escape(groupPortletDDMTemplate.getName(locale)) %>" selected="<%= (ddmTemplate != null) && (groupPortletDDMTemplate.getTemplateId() == ddmTemplate.getTemplateId()) %>" value='<%= "ddmTemplate_" + groupPortletDDMTemplate.getUuid() %>' />

		<%
		}
		%>

		</optgroup>
	</c:if>
</aui:select>

<liferay-ui:icon cssClass="manage-display-templates" id="selectDDMTemplate" image="<%= icon %>" label="<%= true %>" message="<%= message %>" url="javascript:;" />

<aui:script use="aui-base">
	var selectDDMTemplate = A.one('#<portlet:namespace />selectDDMTemplate');

	if (selectDDMTemplate) {
		var windowId = A.guid();

		selectDDMTemplate.on(
			'click',
			function (event) {
				Liferay.Util.openWindow(
					{
						dialog: {
							constrain: true,
							width: 820
						},
						id: windowId,
						title: '<%= UnicodeLanguageUtil.get(pageContext, "application-display-templates") %>',
						uri: '<%= liferayPortletURLString %>'
					},
					function (ddmPortletWindow) {
						ddmPortletWindow.once(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									submitForm(document.<portlet:namespace />fm, '<%= refreshURL %>');
								}
							}
						);
					}
				);
			}
		);
	}
</aui:script>