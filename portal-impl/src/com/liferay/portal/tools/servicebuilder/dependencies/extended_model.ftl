package ${packagePath}.model;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ${entity.table} table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link ${packagePath}.model.impl.${entity.name}Impl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}Model
 * @see       ${packagePath}.model.impl.${entity.name}Impl
 * @see       ${packagePath}.model.impl.${entity.name}ModelImpl
 * @generated
 */
public interface ${entity.name} extends ${entity.name}Model {

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			${serviceBuilder.getJavadocComment(method)}
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

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

			;
		</#if>
	</#list>

}