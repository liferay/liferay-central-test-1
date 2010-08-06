package ${packagePath}.service;

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

@Transactional(isolation = Isolation.PORTAL, rollbackFor = {PortalException.class, SystemException.class})

<#if sessionTypeName == "Local">
/**
 * The interface for the ${entity.humanName} local service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link ${entity.name}LocalServiceUtil} to access the ${entity.humanName} local service. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}LocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}LocalServiceUtil
 * @see ${packagePath}.service.base.${entity.name}LocalServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}LocalServiceImpl
 * @generated
 */
<#else>
/**
 * The interface for the ${entity.humanName} remote service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link ${entity.name}ServiceUtil} to access the ${entity.humanName} remote service. Add custom service methods to {@link ${packagePath}.service.impl.${entity.name}ServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author ${author}
 * @see ${entity.name}ServiceUtil
 * @see ${packagePath}.service.base.${entity.name}ServiceBaseImpl
 * @see ${packagePath}.service.impl.${entity.name}ServiceImpl
 * @generated
 */
</#if>
public interface ${entity.name}${sessionTypeName}Service {

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method) && !serviceBuilder.isDuplicateMethod(method, tempMap)>
			${serviceBuilder.getJavadocComment(method)}

			<#if method.name = "dynamicQuery">
				@SuppressWarnings("rawtypes")
			</#if>

			<#if serviceBuilder.isServiceReadOnlyMethod(method, entity.txRequiredList)>
				@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
			</#if>
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name}(

			<#list method.parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#if sessionTypeName == "Local">
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			<#else>
				<#list method.exceptions as exception>
					<#if exception_index == 0>
						throws
					</#if>

					${exception.value}

					<#if exception_has_next>
						,
					</#if>
				</#list>
			</#if>

			;
		</#if>
	</#list>

}