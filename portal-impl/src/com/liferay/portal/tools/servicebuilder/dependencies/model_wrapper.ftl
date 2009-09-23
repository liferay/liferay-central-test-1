package ${packagePath}.model;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

/**
 * <a href="${entity.name}Soap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ${entity.name}}.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}
 * @generated
 */
public class ${entity.name}Wrapper implements ${entity.name} {

	public ${entity.name}Wrapper(${entity.name} ${entity.varName}) {
		_${entity.varName} = ${entity.varName};
	}

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions("${method.returns.dimensions}")} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions("${parameter.type.dimensions}")} ${parameter.name}<#if parameter_has_next>,</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
					return
				</#if>

				_${entity.varName}.${method.name}(

				<#list method.parameters as parameter>
					${parameter.name}

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	private ${entity.name} _${entity.varName};

}