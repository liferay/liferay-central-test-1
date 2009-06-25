<#assign parentPKColumn = "">

<#if entity.isHierarchicalTree()>
	<#assign pkColumn = entity.getPKList()?first>

	<#assign parentPKColumn = entity.getColumn("parent" + pkColumn.methodName)>
</#if>

package ${packagePath}.model.impl;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import ${packagePath}.model.${entity.name};
import ${packagePath}.model.${entity.name}Soap;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

<#if (entity.PKClassName == "long") && !stringUtil.startsWith(entity.name, "Expando")>
	import com.liferay.portlet.expando.model.ExpandoBridge;
	import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
</#if>

<#if entity.hasLocalizedColumn()>
	import com.liferay.portal.kernel.language.LanguageUtil;
	import com.liferay.portal.kernel.util.LocaleUtil;
	import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
	import com.liferay.portal.kernel.util.Validator;

	import com.liferay.util.LocalizationUtil;
	import java.util.Locale;
	import java.util.Map;
</#if>

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="${entity.name}ModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>${entity.name}</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see ${packagePath}.model.${entity.name}
 * @see ${packagePath}.model.${entity.name}Model
 * @see ${packagePath}.model.impl.${entity.name}Impl
 *
 */
public class ${entity.name}ModelImpl extends BaseModelImpl<${entity.name}> {

	public static final String TABLE_NAME = "${entity.table}";

	public static final Object[][] TABLE_COLUMNS = {
		<#list entity.getRegularColList() as column>
			<#assign sqlType = serviceBuilder.getSqlType(packagePath + ".model." + entity.getName(), column.getName(), column.getType())>

			{"${column.DBName}", new Integer(Types.${sqlType})}

			<#if column_has_next>
				,
			</#if>
		</#list>
	};

	public static final String TABLE_SQL_CREATE = "${serviceBuilder.getCreateTableSQL(entity)}";

	public static final String TABLE_SQL_DROP = "drop table ${entity.table}";

	public static final String DATA_SOURCE = "${entity.dataSource}";

	public static final String SESSION_FACTORY = "${entity.sessionFactory}";

	public static final String TX_MANAGER = "${entity.getTXManager()}";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(${propsUtil}.get("value.object.entity.cache.enabled.${packagePath}.model.${entity.name}"),

	<#if entity.isCacheEnabled()>
		true
	<#else>
		false
	</#if>

	);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(${propsUtil}.get("value.object.finder.cache.enabled.${packagePath}.model.${entity.name}"),

	<#if entity.isCacheEnabled()>
		true
	<#else>
		false
	</#if>

	);

	public static ${entity.name} toModel(${entity.name}Soap soapModel) {
		${entity.name} model = new ${entity.name}Impl();

		<#list entity.regularColList as column>
			model.set${column.methodName}(soapModel.get${column.methodName}());
		</#list>

		return model;
	}

	public static List<${entity.name}> toModels(${entity.name}Soap[] soapModels) {
		List<${entity.name}> models = new ArrayList<${entity.name}>(soapModels.length);

		for (${entity.name}Soap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	<#list entity.columnList as column>
		<#if column.mappingTable??>
			public static final boolean FINDER_CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)} =

			<#assign entityShortName = stringUtil.shorten(entity.name, 10, "")>

			<#if stringUtil.startsWith(column.mappingTable, entityShortName)>
				GetterUtil.getBoolean(${propsUtil}.get("value.object.finder.cache.enabled.${column.mappingTable}"), true)
			<#else>
				<#assign tempEntity = serviceBuilder.getEntity(column.getEJBName())>

				${tempEntity.packagePath}.model.impl.${tempEntity.name}ModelImpl.FINDER_CACHE_ENABLED_${stringUtil.upperCase(column.mappingTable)}
			</#if>

			;
		</#if>
	</#list>

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(${propsUtil}.get("lock.expiration.time.${packagePath}.model.${entity.name}"));

	public ${entity.name}ModelImpl() {
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
			<#if column.type == "String" && column.isConvertNull()>
				return GetterUtil.getString(_${column.name});
			<#else>
				return _${column.name};
			</#if>
		}

		<#if column.localized == true>
			public String get${column.methodName}(Locale locale) {
				String localeLanguageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(localeLanguageId);
			}

			public String get${column.methodName}(Locale locale, boolean useDefault) {
				String localeLanguageId = LocaleUtil.toLanguageId(locale);

				return get${column.methodName}(localeLanguageId, useDefault);
			}

			public String get${column.methodName}(String localeLanguageId) {
				String value = LocalizationUtil.getLocalization(
					get${column.methodName}(), localeLanguageId);

				if (isEscapedModel()) {
					return HtmlUtil.escape(value);
				}
				else {
					return value;
				}
			}

			public String get${column.methodName}(String localeLanguageId, boolean useDefault) {
				String value = LocalizationUtil.getLocalization(
					get${column.methodName}(), localeLanguageId, useDefault);

				if (isEscapedModel()) {
					return HtmlUtil.escape(value);
				}
				else {
					return value;
				}
			}

			public Map<Locale, String> get${column.methodName}sMap() {
				return LocalizationUtil.getLocalizedField(get${column.methodName}());
			}
		</#if>

		<#if column.type== "boolean">
			public ${column.type} is${column.methodName}() {
				return _${column.name};
			}
		</#if>

		public void set${column.methodName}(${column.type} ${column.name}) {
			<#if column.name == "uuid">
				_uuid = uuid;

				<#if column.isFetchFinderPath()>
					if (_originalUuid == null) {
						_originalUuid = uuid;
					}
				</#if>
			<#else>
				_${column.name} = ${column.name};

				<#if column.isFetchFinderPath() || ((parentPKColumn != "") && (parentPKColumn.name == column.name))>
					<#if column.isPrimitiveType()>
						if (!_setOriginal${column.methodName}) {
							_setOriginal${column.methodName} = true;
					<#else>
						if (_original${column.methodName} == null) {
					</#if>

						_original${column.methodName} = ${column.name};
					}
				</#if>
			</#if>
		}

		<#if column.localized == true>
			public void set${column.methodName}(String localizedValue, Locale locale) {
				String localeLanguageId = LocaleUtil.toLanguageId(locale);

				if (Validator.isNotNull(localizedValue)) {
					set${column.methodName}(
						LocalizationUtil.updateLocalization(
							get${column.methodName}(), "${column.methodName}", localizedValue,
							localeLanguageId));
				}
				else {
					set${column.methodName}(
						LocalizationUtil.removeLocalization(
							get${column.methodName}(), "${column.methodName}", localeLanguageId));
				}
			}

			public void set${column.methodName}(Map localizedValues) {
				if (localizedValues == null) {
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
						String value = (String)localizedValues.get(locale);

						this.set${column.methodName}(value, locale);
					}
				}
				finally {
					if (contextClassLoader != portalClassLoader) {
						currentThread.setContextClassLoader(contextClassLoader);
					}
				}
			}
		</#if>

		<#if column.isFetchFinderPath() || ((parentPKColumn != "") && (parentPKColumn.name == column.name))>
			public ${column.type} getOriginal${column.methodName}() {
				<#if column.type == "String" && column.isConvertNull()>
					return GetterUtil.getString(_original${column.methodName});
				<#else>
					return _original${column.methodName};
				</#if>
			}
		</#if>
	</#list>

	public ${entity.name} toEscapedModel() {
		if (isEscapedModel()) {
			return (${entity.name})this;
		}
		else {
			${entity.name} model = new ${entity.name}Impl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			<#list entity.regularColList as column>
				model.set${column.methodName}(

				<#if column.EJBName??>
					(${column.EJBName})get${column.methodName}().clone()
				<#else>
					<#assign autoEscape = true>

					<#assign modelName = packagePath + ".model." + entity.name>

					<#if modelHintsUtil.getHints(modelName, column.name)??>
						<#assign hints = modelHintsUtil.getHints(modelName, column.name)>

						<#if hints.get("auto-escape")??>
							<#assign autoEscapeHintValue = hints.get("auto-escape")>

							<#if autoEscapeHintValue == "false">
								<#assign autoEscape = false>
							</#if>
						</#if>
					</#if>

					<#if autoEscape && (column.type == "String") && (column.localized == false) >
						HtmlUtil.escape(
					</#if>

					get${column.methodName}()

					<#if autoEscape && (column.type == "String") && (column.localized == false) >
						)
					</#if>
				</#if>

				);
			</#list>

			model = (${entity.name})Proxy.newProxyInstance(${entity.name}.class.getClassLoader(), new Class[] {${entity.name}.class}, new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	<#if (entity.PKClassName == "long") && !stringUtil.startsWith(entity.name, "Expando")>
		public ExpandoBridge getExpandoBridge() {
			if (_expandoBridge == null) {
				_expandoBridge = new ExpandoBridgeImpl(${entity.name}.class.getName(), getPrimaryKey());
			}

			return _expandoBridge;
		}
	</#if>

	public Object clone() {
		${entity.name}Impl clone = new ${entity.name}Impl();

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

		${entity.name} ${entity.varName} = null;

		try {
			${entity.varName} = (${entity.name})obj;
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
		<#if entity.hasPrimitivePK()>
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
		StringBuilder sb = new StringBuilder();

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
		StringBuilder sb = new StringBuilder();

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

		<#if column.isFetchFinderPath() || ((parentPKColumn != "") && (parentPKColumn.name == column.name))>
			private ${column.type} _original${column.methodName};

			<#if column.isPrimitiveType()>
				private boolean _setOriginal${column.methodName};
			</#if>
		</#if>
	</#list>

	<#if (entity.PKClassName == "long") && !stringUtil.startsWith(entity.name, "Expando")>
		private transient ExpandoBridge _expandoBridge;
	</#if>

}