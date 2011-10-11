<#-- Tag libraries -->

<#assign aui = PortalJspTagLibs["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#-- CSS class -->

<#assign cssClass = field.fieldCssClass!"">

<#-- Field name -->

<#assign fieldName = field.name>

<#assign parentName = parentField.name!"">
<#assign parentType = parentField.type!"">

<#assign isChildField = parentName?? && (parentName != "") && ((parentType == "radio") || (parentType == "select"))>

<#if isChildField>
	<#assign fieldName = parentName>
</#if>

<#assign namespacedFieldName = "${namespace}${fieldName}">

<#-- Predefined value -->

<#assign predefinedValue = field.predefinedValue!"">

<#if isChildField>
	<#assign predefinedValue = parentField.predefinedValue!"">
</#if>

<#-- Field value -->

<#assign fieldValue = predefinedValue>
<#assign fieldRawValue = "">

<#if fields?? && fields.get(fieldName)??>
	<#assign field = fields.get(fieldName)>

	<#assign fieldValue = field.getRenderedValue(themeDisplay)>
	<#assign fieldRawValue = field.getValue()>
</#if>

<#-- Label -->

<#assign label = field.label!"">

<#if field.showLabel?? && (field.showLabel == "false")>
	<#assign label = "">
</#if>

<#-- Required -->

<#assign required = false>

<#if field.required?? && (field.required == "true")>
	<#assign required = true>
</#if>

<#-- Util -->

<#assign jsonFactoryUtil = utilLocator.findUtil("com.liferay.portal.kernel.json.JSONFactory")>

<#function getFileJSONObject fieldValue>
	<#return jsonFactoryUtil.createJSONObject(fieldValue)>>
</#function>

<#assign dlAppServiceUtil = serviceLocator.findService("com.liferay.portlet.documentlibrary.service.DLAppService")>

<#function getFileEntry fileJSONObject>
	<#assign fileEntryUUID = fileJSONObject.getString("uuid")>

	<#return dlAppServiceUtil.getFileEntryByUuidAndGroupId(fileEntryUUID, scopeGroupId)>
</#function>

<#function getFileEntryURL fileEntry>
	<#return themeDisplay.getPathContext() + "/documents/" + scopeGroupId?c + "/" + (fileEntry.getFolderId())?c + "/" +  httpUtil.encodeURL(htmlUtil.unescape(fileEntry.getTitle()))>
</#function>