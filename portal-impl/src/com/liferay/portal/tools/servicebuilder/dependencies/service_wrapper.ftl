package ${packagePath}.service;

/**
 * <a href="${entity.name}${sessionTypeName}ServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ${entity.name}${sessionTypeName}Service}.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}${sessionTypeName}Service
 * @generated
 */
public class ${entity.name}${sessionTypeName}ServiceWrapper implements ${entity.name}${sessionTypeName}Service {

	public ${entity.name}${sessionTypeName}ServiceWrapper(${entity.name}${sessionTypeName}Service ${entity.varName}${sessionTypeName}Service) {
		_${entity.varName}${sessionTypeName}Service = ${entity.varName}${sessionTypeName}Service;
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list method.parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions(parameter.type.dimensions)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
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

				_${entity.varName}${sessionTypeName}Service.${method.name}(

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

	public ${entity.name}${sessionTypeName}Service getWrapped${entity.name}${sessionTypeName}Service() {
		return _${entity.varName}${sessionTypeName}Service;
	}

	private ${entity.name}${sessionTypeName}Service _${entity.varName}${sessionTypeName}Service;

}