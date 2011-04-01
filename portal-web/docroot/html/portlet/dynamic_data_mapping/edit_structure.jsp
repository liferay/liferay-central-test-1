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

String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");
String availableFields = ParamUtil.getString(request, "availableFields");
String callback = ParamUtil.getString(request, "callback");

DDMStructure structure = (DDMStructure)request.getAttribute(WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE);

long groupId = BeanParamUtil.getLong(structure, request, "groupId", scopeGroupId);

String structureKey = BeanParamUtil.getString(structure, request, "structureKey");
String newStructureKey = ParamUtil.getString(request, "newStructureKey");

String xsd = BeanParamUtil.getString(structure, request, "xsd");
%>

<liferay-portlet:actionURL var="editStructureURL" portletName="<%= PortletKeys.FORMS %>">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_structure" />
</liferay-portlet:actionURL>

<aui:form action="<%= editStructureURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveStructure();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (structure != null) ? Constants.UPDATE : Constants.ADD %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="availableFields" type="hidden" value="<%= availableFields %>" />
	<aui:input name="callback" type="hidden" value="<%= callback %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="structureKey" type="hidden" value="<%= structureKey %>" />
	<aui:input name="xsd" type="hidden" />
	<aui:input name="saveAndContinue" type="hidden" value="<%= true %>" />

	<liferay-ui:error exception="<%= StructureDuplicateElementException.class %>" message="please-enter-unique-structure-field-names-(including-field-names-inherited-from-the-parent-structure)" />
	<liferay-ui:error exception="<%= StructureDuplicateStructureKeyException.class %>" message="please-enter-a-unique-id" />
	<liferay-ui:error exception="<%= StructureStructureKeyException.class %>" message="please-enter-a-valid-id" />
	<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= StructureXsdException.class %>" message="please-enter-a-valid-xsd" />

	<aui:model-context bean="<%= structure %>" model="<%= DDMStructure.class %>" />

	<aui:input name="name" />

	<liferay-ui:panel-container cssClass="lfr-structure-entry-details-container" extended="<%= false %>" id="structureDetailsPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="structureDetailsSectionPanel" persistState="<%= true %>" title='<%= LanguageUtil.get(pageContext, "details") %>'>
			<aui:layout cssClass="lfr-ddm-types-form-column">
				<aui:column first="true">
					<aui:field-wrapper>
						<aui:select disabled="<%= structure != null %>" label="type" name="classNameId">
							<aui:option label='<%= "model.resource." + DDMList.class.getName() %>' value="<%= PortalUtil.getClassNameId(DDMList.class.getName()) %>" />
						</aui:select>
					</aui:field-wrapper>
				</aui:column>

				<aui:column>
					<aui:field-wrapper>
						<aui:select disabled="<%= structure != null %>" name="storageType">

							<%
							for (StorageType type : StorageType.values()) {
							%>

								<aui:option label="<%= type %>" value="<%= type %>" />

							<%
							}
							%>

						</aui:select>
					</aui:field-wrapper>
				</aui:column>
			</aui:layout>

			<aui:input name="description" />

			<c:choose>
				<c:when test="<%= structure == null %>">
					<c:choose>
						<c:when test="<%= PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_FORCE_AUTOGENERATE_ID %>">
							<aui:input name="newStructureKey" type="hidden" />
							<aui:input name="autoStructureKey" type="hidden" value="<%= true %>" />
						</c:when>
						<c:otherwise>
							<aui:input cssClass="lfr-input-text-container" field="structureKey" fieldParam="newStructureKey" label="id" name="newStructureKey" value="<%= newStructureKey %>" />

							<aui:input label="autogenerate-id" name="autoStructureKey" type="checkbox" value="<%= true %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<aui:field-wrapper label="id">
						<%= structureKey %>
					</aui:field-wrapper>
				</c:otherwise>
			</c:choose>
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</aui:form>

<div class="separator"><!-- --></div>

<div class="aui-widget aui-component aui-form-builder" id="<portlet:namespace />formBuilder">
	<div class="aui-form-builder-content">
		<div class="aui-widget-bd aui-helper-clearfix">
			<ul class="aui-form-builder-drop-container">
				<c:choose>
					<c:when test="<%= Validator.isNotNull(xsd) %>">
						<li class="form-fields-loading">
							<div class="aui-loadingmask-message">
								<div class="aui-loadingmask-message-content"><liferay-ui:message key="loading" />...</div>
							</div>
						</li>
					</c:when>
					<c:otherwise>
						<li class="aui-form-builder-default-message"><liferay-ui:message key="drop-fields-here" /></li>
					</c:otherwise>
				</c:choose>
			</ul>

			<div class="aui-form-builder-tabs-container">
				<ul class="aui-tabview-list aui-widget-hd">
					<li class="aui-component aui-form-builder-tab-add aui-state-active aui-state-default aui-tab aui-tab-active aui-widget">
						<span class="aui-tab-content"><a class="aui-tab-label" href="javascript:;"><liferay-ui:message key="add-field" /></a></span>
					</li>
					<li class="aui-component aui-form-builder-tab-settings aui-tab aui-state-default aui-widget">
						<span class="aui-tab-content"><a class="aui-tab-label" href="javascript:;"><liferay-ui:message key="field-settings" /></a></span>
					</li>
				</ul>

				<div class="aui-tabview-content aui-widget-bd">
					<div class="aui-tabview-content-item">
						<ul class="aui-form-builder-drag-container"></ul>
					</div>

					<div class="aui-helper-hidden aui-tabview-content-item">
						<form class="aui-form-builder-settings"></form>

						<div class="aui-button-row aui-form-builder-settings-buttons">
							<span class="aui-button aui-button-submit aui-priority-primary aui-state-positive">
								<span class="aui-button-content">
									<input type="button" value="<liferay-ui:message key="save" />" class="aui-button-input aui-form-builder-button-save">
								</span>
							</span>

							<span class="aui-button aui-button-submit aui-priority-secondary aui-state-positive">
								<span class="aui-button-content">
									<input class="aui-button-input aui-form-builder-button-close" type="button" value="<liferay-ui:message key="close" />">
								</span>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveStructure();" %>' value='<%= LanguageUtil.get(pageContext, "save") %>' />

	<c:if test="<%= Validator.isNull(portletResourceNamespace) %>">
		<aui:button onClick="<%= redirect %>" type="cancel" />
	</c:if>
</aui:button-row>

<%@ include file="/html/portlet/dynamic_data_mapping/custom_fields.jspf" %>

<aui:script use="liferay-portlet-dynamic-data-mapping">
	var formBuilder = new Liferay.FormBuilder(
		{
			<c:if test="<%= Validator.isNotNull(availableFields) %>">
				availableFields: A.Object.getValue(Liferay.Util.getTop(), '<%= HtmlUtil.escapeJS(availableFields) %>'.split('.')),
			</c:if>

			boundingBox: '#<portlet:namespace />formBuilder',

			<c:if test="<%= Validator.isNotNull(xsd) %>">
				fields: <%= DDMXSDUtil.getJSONArray(xsd) %>,
			</c:if>

			portletNamespace: '<portlet:namespace />',
			portletResourceNamespace: '<%= HtmlUtil.escapeJS(portletResourceNamespace) %>',
			srcNode: '#<portlet:namespace />formBuilder .aui-form-builder-content'
		}
	).render();

	Liferay.provide(
		window,
		'<portlet:namespace />saveStructure',
		function() {
			document.<portlet:namespace />fm.<portlet:namespace />xsd.value = formBuilder.getXSD();

			<c:if test="<%= structure == null %>">
				document.<portlet:namespace />fm.<portlet:namespace />structureKey.value = document.<portlet:namespace />fm.<portlet:namespace />newStructureKey.value;
			</c:if>

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);

	<c:if test="<%= Validator.isNotNull(callback) && Validator.isNotNull(structureKey) %>">
		window.parent.<%= HtmlUtil.escapeJS(callback) %>('<%= HtmlUtil.escapeJS(structureKey) %>');
	</c:if>
</aui:script>