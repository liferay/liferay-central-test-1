<#include "../init.ftl">

<div class="aui-field-wrapper-content lfr-forms-field-wrapper">
	<@aui.input label=label name=field.name type="text" value=fieldValue!field.predefinedValue />

	${field.children}
</div>