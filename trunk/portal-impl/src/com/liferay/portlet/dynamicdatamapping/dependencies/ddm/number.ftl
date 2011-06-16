<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input cssClass=cssClass helpMessage=field.tip label=label name=namespacedFieldName type="text" value=fieldValue!field.predefinedValue>
		<@aui.validator name="number" />

		<#if required>
			<@aui.validator name="required" />
		</#if>
	</@aui.input>

	${field.children}
</div>