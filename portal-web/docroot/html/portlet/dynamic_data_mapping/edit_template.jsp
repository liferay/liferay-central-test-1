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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");

DDMTemplate template = (DDMTemplate)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE);

long templateId = BeanParamUtil.getLong(template, request, "templateId");

long groupId = BeanParamUtil.getLong(template, request, "groupId", scopeGroupId);

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

String structureKey = BeanParamUtil.getString(structure, request, "structureKey");

String type = BeanParamUtil.getString(template, request, "type", "detail");
String script = BeanParamUtil.getString(template, request, "script");

String availableFields = ParamUtil.getString(request, "availableFields");

if (Validator.isNull(availableFields)) {
	availableFields = renderResponse.getNamespace() + "structureAvailableFields";
}

String callback = ParamUtil.getString(request, "callback");
%>

<liferay-portlet:actionURL var="editTemplateURL" portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
</liferay-portlet:actionURL>

<aui:form action="<%= editTemplateURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveTemplate();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (template != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="templateId" type="hidden" value="<%= String.valueOf(templateId) %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />
	<aui:input name="availableFields" type="hidden" value="<%= availableFields %>" />
	<aui:input name="callback" type="hidden" value="<%= callback %>" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= TemplateNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= TemplateScriptException.class %>" message="please-enter-a-valid-script" />

	<%
	String title = null;

	if (structure != null) {
		if (template != null) {
			title = template.getName() + " (" + structure.getName() + ")";
		}
		else {
			title = LanguageUtil.format(pageContext, "new-template-for-structure-x", structure.getName(), false);
		}
	}
	%>

	<liferay-ui:header
		title="<%= title %>"
		backURL="<%= backURL %>"
	/>

	<aui:model-context bean="<%= template %>" model="<%= DDMTemplate.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<c:choose>
			<c:when test='<%= type.equals("detail") %>'>
				<%@ include file="/html/portlet/dynamic_data_mapping/edit_template_detail.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/html/portlet/dynamic_data_mapping/edit_template_list.jspf" %>
			</c:otherwise>
		</c:choose>
	</aui:fieldset>
</aui:form>

<c:if test='<%= type.equals("detail") %>'>
	<%@ include file="/html/portlet/dynamic_data_mapping/form_builder.jspf" %>
</c:if>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveTemplate();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<c:if test="<%= Validator.isNull(portletResourceNamespace) %>">
		<aui:button onClick="<%= redirect %>" type="cancel" />
	</c:if>
</aui:button-row>