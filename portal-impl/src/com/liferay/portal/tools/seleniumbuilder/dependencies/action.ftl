package ${seleniumBuilderContext.getActionPackageName(actionName)};

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb2.util.block.action.BaseAction;

public class ${seleniumBuilderContext.getActionSimpleClassName(actionName)} extends BaseAction {

	public ${seleniumBuilderContext.getActionSimpleClassName(actionName)}(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);

		paths = ${seleniumBuilderContext.getPathSimpleClassName(actionName)}.getPaths();
	}

	<#if seleniumBuilderContext.getActionRootElement(actionName)??>
		<#assign rootElement = seleniumBuilderContext.getActionRootElement(actionName)>

		<#assign actionCommandElements = rootElement.elements("action-command")>

		<#list actionCommandElements as actionCommandElement>
			<#assign actionCommandName = actionCommandElement.attributeValue("name")>

			public ${seleniumBuilderContext.getFunctionReturnType(actionCommandName)} ${seleniumBuilderFileUtil.getVariableName(actionCommandName)}(

			<#list 1..seleniumBuilderContext.getFunctionTargetCount(actionCommandName) as i>
				String target${i}, String value${i}

				<#if i_has_next>
					,
				</#if>
			</#list>

			) throws Exception {
			}
		</#list>
	</#if>

}