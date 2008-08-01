package ${packagePath}.service.base;

import ${packagePath}.service.${entity.name}${sessionTypeName}Service;

import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import ${beanLocatorUtil};

<#if sessionTypeName == "">
	import com.liferay.portal.service.base.PrincipalBean;
</#if>

<#if entity.hasColumns()>
	<#if entity.hasCompoundPK()>
		import ${packagePath}.service.persistence.${entity.name}PK;
	</#if>

	import ${packagePath}.model.${entity.name};
	import ${packagePath}.model.impl.${entity.name}Impl;

	import com.liferay.portal.PortalException;
	import com.liferay.portal.SystemException;
	import com.liferay.portal.kernel.dao.orm.DynamicQuery;

	import java.util.List;
</#if>

<#list referenceList as tempEntity>
	<#if entity.equals(tempEntity)>
		<#if sessionTypeName == "" && tempEntity.hasLocalService()>
			import ${tempEntity.packagePath}.service.${tempEntity.name}LocalService;
		</#if>
	<#else>
		<#if tempEntity.hasLocalService()>
			import ${tempEntity.packagePath}.service.${tempEntity.name}LocalService;
		</#if>
		<#if tempEntity.hasRemoteService()>
			import ${tempEntity.packagePath}.service.${tempEntity.name}Service;
		</#if>
	</#if>

	<#if tempEntity.hasColumns()>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Persistence;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Util;
	</#if>

	<#if tempEntity.hasFinderClass()>
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}Finder;
		import ${tempEntity.packagePath}.service.persistence.${tempEntity.name}FinderUtil;
	</#if>
</#list>

<#if sessionTypeName == "Local">
	public abstract class ${entity.name}LocalServiceBaseImpl implements ${entity.name}LocalService, InitializingBean {
<#else>
	public abstract class ${entity.name}ServiceBaseImpl extends PrincipalBean implements ${entity.name}Service, InitializingBean {
</#if>

<#if sessionTypeName == "Local" && entity.hasColumns()>
	public ${entity.name} add${entity.name}(${entity.name} ${entity.varName}) throws SystemException {
		${entity.varName}.setNew(true);

		return ${entity.varName}Persistence.update(${entity.varName}, false);
	}

	public void delete${entity.name}(${entity.PKClassName} ${entity.PKVarName}) throws PortalException, SystemException {
		${entity.varName}Persistence.remove(${entity.PKVarName});
	}

	public void delete${entity.name}(${entity.name} ${entity.varName}) throws SystemException {
		${entity.varName}Persistence.remove(${entity.varName});
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery) throws SystemException {
		return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start, int end) throws SystemException {
		return ${entity.varName}Persistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	public ${entity.name} get${entity.name}(${entity.PKClassName} ${entity.PKVarName}) throws PortalException, SystemException {
		return ${entity.varName}Persistence.findByPrimaryKey(${entity.PKVarName});
	}

	public List<${entity.name}> get${entity.names}(int start, int end) throws SystemException {
		return ${entity.varName}Persistence.findAll(start, end);
	}

	public int get${entity.names}Count() throws SystemException {
		return ${entity.varName}Persistence.countAll();
	}

	public ${entity.name} update${entity.name}(${entity.name} ${entity.varName}) throws SystemException {
		${entity.varName}.setNew(false);

		return ${entity.varName}Persistence.update(${entity.varName}, true);
	}
</#if>

<#if entity.TXManager != "none">
	<#list referenceList as tempEntity>
		<#if entity.equals(tempEntity)>
			<#if sessionTypeName == "" && tempEntity.hasLocalService()>
				public ${tempEntity.name}LocalService get${tempEntity.name}LocalService() {
					return ${tempEntity.varName}LocalService;
				}

				public void set${tempEntity.name}LocalService(${tempEntity.name}LocalService ${tempEntity.varName}LocalService) {
					this.${tempEntity.varName}LocalService = ${tempEntity.varName}LocalService;
				}
			</#if>
		<#else>
			<#if tempEntity.hasLocalService()>
				public ${tempEntity.name}LocalService get${tempEntity.name}LocalService() {
					return ${tempEntity.varName}LocalService;
				}

				public void set${tempEntity.name}LocalService(${tempEntity.name}LocalService ${tempEntity.varName}LocalService) {
					this.${tempEntity.varName}LocalService = ${tempEntity.varName}LocalService;
				}
			</#if>

			<#if tempEntity.hasRemoteService()>
				public ${tempEntity.name}Service get${tempEntity.name}Service() {
					return ${tempEntity.varName}Service;
				}

				public void set${tempEntity.name}Service(${tempEntity.name}Service ${tempEntity.varName}Service) {
					this.${tempEntity.varName}Service = ${tempEntity.varName}Service;
				}
			</#if>
		</#if>

		<#if tempEntity.hasColumns()>
			public ${tempEntity.name}Persistence get${tempEntity.name}Persistence() {
				return ${tempEntity.varName}Persistence;
			}

			public void set${tempEntity.name}Persistence(${tempEntity.name}Persistence ${tempEntity.varName}Persistence) {
				this.${tempEntity.varName}Persistence = ${tempEntity.varName}Persistence;
			}
		</#if>

		<#if tempEntity.hasFinderClass()>
			public ${tempEntity.name}Finder get${tempEntity.name}Finder() {
				return ${tempEntity.varName}Finder;
			}

			public void set${tempEntity.name}Finder(${tempEntity.name}Finder ${tempEntity.varName}Finder) {
				this.${tempEntity.varName}Finder = ${tempEntity.varName}Finder;
			}
		</#if>
	</#list>

	public void afterPropertiesSet() {
		<#list referenceList as tempEntity>
			<#if tempEntity.isPortalReference()>
				<#assign tempBeanLocatorUtilShortName = "PortalBeanLocatorUtil">
			<#else>
				<#assign tempBeanLocatorUtilShortName = beanLocatorUtilShortName>
			</#if>

			<#if entity.equals(tempEntity)>
				<#if sessionTypeName == "" && tempEntity.hasLocalService()>
					if (${tempEntity.varName}LocalService == null) {
						${tempEntity.varName}LocalService = (${tempEntity.name}LocalService)${tempBeanLocatorUtilShortName}.locate(${tempEntity.name}LocalService.class.getName() + ".impl");
					}
				</#if>
			<#else>
				<#if tempEntity.hasLocalService()>
					if (${tempEntity.varName}LocalService == null) {
						${tempEntity.varName}LocalService = (${tempEntity.name}LocalService)${tempBeanLocatorUtilShortName}.locate(${tempEntity.name}LocalService.class.getName() + ".impl");
					}
				</#if>

				<#if tempEntity.hasRemoteService()>
					if (${tempEntity.varName}Service == null) {
						${tempEntity.varName}Service = (${tempEntity.name}Service)${tempBeanLocatorUtilShortName}.locate(${tempEntity.name}Service.class.getName() + ".impl");
					}
				</#if>
			</#if>

			<#if tempEntity.hasColumns()>
				if (${tempEntity.varName}Persistence == null) {
					${tempEntity.varName}Persistence = (${tempEntity.name}Persistence)${tempBeanLocatorUtilShortName}.locate(${tempEntity.name}Persistence.class.getName() + ".impl");
				}
			</#if>

			<#if tempEntity.hasFinderClass()>
				if (${tempEntity.varName}Finder == null) {
					${tempEntity.varName}Finder = (${tempEntity.name}Finder)${tempBeanLocatorUtilShortName}.locate(${tempEntity.name}Finder.class.getName() + ".impl");
				}
			</#if>
		</#list>
	}

	<#list referenceList as tempEntity>
		<#if entity.equals(tempEntity)>
			<#if (sessionTypeName == "") && tempEntity.hasLocalService()>
				protected ${tempEntity.name}LocalService ${tempEntity.varName}LocalService;
			</#if>
		<#else>
			<#if tempEntity.hasLocalService()>
				protected ${tempEntity.name}LocalService ${tempEntity.varName}LocalService;
			</#if>

			<#if tempEntity.hasRemoteService()>
				protected ${tempEntity.name}Service ${tempEntity.varName}Service;
			</#if>
		</#if>

		<#if tempEntity.hasColumns()>
			protected ${tempEntity.name}Persistence ${tempEntity.varName}Persistence;
		</#if>

		<#if tempEntity.hasFinderClass()>
			protected ${tempEntity.name}Finder ${tempEntity.varName}Finder;
		</#if>
	</#list>
<#else>
	public void afterPropertiesSet() {
	}
</#if>

}