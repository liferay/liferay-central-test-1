package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ${entity.name}Clp extends BaseModelImpl<${entity.name}> implements ${entity.name} {

	public ${entity.name}Clp() {
	}

	public ${entity.PKClassName} getPrimaryKey() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return _${entity.PKList[0].name};
		</#if>
	}

	public void setPrimaryKey(${entity.PKClassName} pk) {
		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				set${column.methodName}(pk.${column.name});
			</#list>
		<#else>
			set${entity.PKList[0].methodName}(pk);
		</#if>
	}

	public Serializable getPrimaryKeyObj() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(

			<#list entity.PKList as column>
				_${column.name}

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			return

			<#if entity.hasPrimitivePK()>
				new ${serviceBuilder.getPrimitiveObj("${entity.PKClassName}")} (
			</#if>

			_${entity.PKList[0].name}

			<#if entity.hasPrimitivePK()>
				)
			</#if>

			;
		</#if>
	}

	<#list entity.regularColList as column>
		<#if column.name == "classNameId">
			public String getClassName() {
				if (getClassNameId() <= 0) {
					return StringPool.BLANK;
				}

				return PortalUtil.getClassName(getClassNameId());
			}
		</#if>

		public ${column.type} get${column.methodName}() {
			return _${column.name};
		}

		<#if column.localized>
			public String get${column.methodName}(Locale locale) {
				String languageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(languageId);
			}

			public String get${column.methodName}(Locale locale, boolean useDefault) {
				String languageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(languageId, useDefault);
			}

			public String get${column.methodName}(String languageId) {
				String value = LocalizationUtil.getLocalization(get${column.methodName}(), languageId);

				if (isEscapedModel()) {
					return HtmlUtil.escape(value);
				}
				else {
					return value;
				}
			}

			public String get${column.methodName}(String languageId, boolean useDefault) {
				String value = LocalizationUtil.getLocalization(get${column.methodName}(), languageId, useDefault);

				if (isEscapedModel()) {
					return HtmlUtil.escape(value);
				}
				else {
					return value;
				}
			}

			public Map<Locale, String> get${column.methodName}Map() {
				return LocalizationUtil.getLocalizationMap(get${column.methodName}());
			}
		</#if>

		<#if column.type== "boolean">
			public ${column.type} is${column.methodName}() {
				return _${column.name};
			}
		</#if>

		public void set${column.methodName}(${column.type} ${column.name}) {
			_${column.name} = ${column.name};
		}

		<#if column.localized>
			public void set${column.methodName}(Locale locale, String ${column.name}) {
				String languageId = LocaleUtil.toLanguageId(locale);

				if (Validator.isNotNull(${column.name})) {
					set${column.methodName}(LocalizationUtil.updateLocalization(get${column.methodName}(), "${column.methodName}", ${column.name}, languageId));
				}
				else {
					set${column.methodName}(LocalizationUtil.removeLocalization(get${column.methodName}(), "${column.methodName}", languageId));
				}
			}

			public void set${column.methodName}Map(Map<Locale, String> ${column.name}Map) {
				if (${column.name}Map == null) {
					return;
				}

				ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				try {
					if (contextClassLoader != portalClassLoader) {
						currentThread.setContextClassLoader(portalClassLoader);
					}

					Locale[] locales = LanguageUtil.getAvailableLocales();

					for (Locale locale : locales) {
						String ${column.name} = ${column.name}Map.get(locale);

						set${column.methodName}(locale, ${column.name});
					}
				}
				finally {
					if (contextClassLoader != portalClassLoader) {
						currentThread.setContextClassLoader(contextClassLoader);
					}
				}
			}
		</#if>

		<#if column.userUuid>
			public String get${column.methodUserUuidName}() throws SystemException {
				return PortalUtil.getUserValue(get${column.methodName}(), "uuid", _${column.userUuidName});
			}

			public void set${column.methodUserUuidName}(String ${column.userUuidName}) {
				_${column.userUuidName} = ${column.userUuidName};
			}
		</#if>
	</#list>

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic()>
			public ${serviceBuilder.getTypeGenericsName(method.returns)} ${method.name} (

			<#assign parameters = method.parameters>

			<#list parameters as parameter>
				${serviceBuilder.getTypeGenericsName(parameter.type)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#--<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>-->

			{
				throw new UnsupportedOperationException();
			}
		</#if>
	</#list>

	<#if entity.isWorkflowEnabled()>
		public boolean isApproved() {
			if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isDraft() {
			if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isExpired() {
			if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isPending() {
			if (getStatus() == WorkflowConstants.STATUS_PENDING) {
				return true;
			}
			else {
				return false;
			}
		}
	</#if>

	public ${entity.name} toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return (${entity.name})Proxy.newProxyInstance(${entity.name}.class.getClassLoader(), new Class[] {${entity.name}.class}, new AutoEscapeBeanHandler(this));
		}
	}

	public Object clone() {
		${entity.name}Clp clone = new ${entity.name}Clp();

		<#list entity.regularColList as column>
			clone.set${column.methodName}(

			<#if column.EJBName??>
				(${column.EJBName})get${column.methodName}().clone()
			<#else>
				get${column.methodName}()
			</#if>

			);
		</#list>

		return clone;
	}

	public int compareTo(${entity.name} ${entity.varName}) {
		<#if entity.isOrdered()>
			int value = 0;

			<#list entity.order.columns as column>
				<#if column.isPrimitiveType()>
					<#if column.type == "boolean">
						<#assign ltComparator = "==">
						<#assign gtComparator = "!=">
					<#else>
						<#assign ltComparator = "<">
						<#assign gtComparator = ">">
					</#if>

					if (get${column.methodName}() ${ltComparator} ${entity.varName}.get${column.methodName}()) {
						value = -1;
					}
					else if (get${column.methodName}() ${gtComparator} ${entity.varName}.get${column.methodName}()) {
						value = 1;
					}
					else {
						value = 0;
					}
				<#else>
					<#if column.type == "Date">
						value = DateUtil.compareTo(get${column.methodName}(), ${entity.varName}.get${column.methodName}());
					<#else>
						<#if column.isCaseSensitive()>
							value = get${column.methodName}().compareTo(${entity.varName}.get${column.methodName}());
						<#else>
							value = get${column.methodName}().toLowerCase().compareTo(${entity.varName}.get${column.methodName}().toLowerCase());
						</#if>
					</#if>
				</#if>

				<#if !column.isOrderByAscending()>
					value = value * -1;
				</#if>

				if (value != 0) {
					return value;
				}
			</#list>

			return 0;
		<#else>
			${entity.PKClassName} pk = ${entity.varName}.getPrimaryKey();

			<#if entity.hasPrimitivePK()>
				if (getPrimaryKey() < pk) {
					return -1;
				}
				else if (getPrimaryKey() > pk) {
					return 1;
				}
				else {
					return 0;
				}
			<#else>
				return getPrimaryKey().compareTo(pk);
			</#if>
		</#if>
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		${entity.name}Clp ${entity.varName} = null;

		try {
			${entity.varName} = (${entity.name}Clp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		${entity.PKClassName} pk = ${entity.varName}.getPrimaryKey();

		<#if entity.hasPrimitivePK()>
			if (getPrimaryKey() == pk) {
		<#else>
			if (getPrimaryKey().equals(pk)) {
		</#if>

			return true;
		}
		else{
			return false;
		}
	}

	public int hashCode() {
		<#if entity.hasPrimitivePK(false)>
			<#if entity.PKClassName == "int">
				return getPrimaryKey();
			<#else>
				return (int)getPrimaryKey();
			</#if>
		<#else>
			return getPrimaryKey().hashCode();
		</#if>
	}

	public String toString() {
		StringBundler sb = new StringBundler(${entity.regularColList?size * 2 + 1});

		<#list entity.regularColList as column>
			<#if column_index == 0>
				sb.append("{${column.name}=");
				sb.append(get${column.methodName}());
			<#elseif column_has_next>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
			<#else>
				sb.append(", ${column.name}=");
				sb.append(get${column.methodName}());
				sb.append("}");
			</#if>
		</#list>

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(${entity.regularColList?size * 3 + 4});

		sb.append("<model><model-name>");
		sb.append("${packagePath}.model.${entity.name}");
		sb.append("</model-name>");

		<#list entity.regularColList as column>
			sb.append("<column><column-name>${column.name}</column-name><column-value><![CDATA[");
			sb.append(get${column.methodName}());
			sb.append("]]></column-value></column>");
		</#list>

		sb.append("</model>");

		return sb.toString();
	}

	<#list entity.regularColList as column>
		private ${column.type} _${column.name};

		<#if column.userUuid>
			private String _${column.userUuidName};
		</#if>
	</#list>

}