/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="RoleModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Role_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleImpl
 * @see       com.liferay.portal.model.Role
 * @see       com.liferay.portal.model.RoleModel
 * @generated
 */
public class RoleModelImpl extends BaseModelImpl<Role> {
	public static final String TABLE_NAME = "Role_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "roleId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "classPK", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "title", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.INTEGER) },
			{ "subtype", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Role_ (roleId LONG not null primary key,companyId LONG,classNameId LONG,classPK LONG,name VARCHAR(75) null,title STRING null,description STRING null,type_ INTEGER,subtype VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Role_";
	public static final String ORDER_BY_JPQL = " ORDER BY role.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Role_.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Role"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Role"),
			true);

	public static Role toModel(RoleSoap soapModel) {
		Role model = new RoleImpl();

		model.setRoleId(soapModel.getRoleId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setType(soapModel.getType());
		model.setSubtype(soapModel.getSubtype());

		return model;
	}

	public static List<Role> toModels(RoleSoap[] soapModels) {
		List<Role> models = new ArrayList<Role>(soapModels.length);

		for (RoleSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final String MAPPING_TABLE_GROUPS_ROLES_NAME = com.liferay.portal.model.impl.GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME;
	public static final boolean FINDER_CACHE_ENABLED_GROUPS_ROLES = com.liferay.portal.model.impl.GroupModelImpl.FINDER_CACHE_ENABLED_GROUPS_ROLES;
	public static final String MAPPING_TABLE_ROLES_PERMISSIONS_NAME = "Roles_Permissions";
	public static final Object[][] MAPPING_TABLE_ROLES_PERMISSIONS_COLUMNS = {
			{ "roleId", new Integer(Types.BIGINT) },
			{ "permissionId", new Integer(Types.BIGINT) }
		};
	public static final String MAPPING_TABLE_ROLES_PERMISSIONS_SQL_CREATE = "create table Roles_Permissions (roleId LONG not null,permissionId LONG not null,primary key (roleId, permissionId))";
	public static final boolean FINDER_CACHE_ENABLED_ROLES_PERMISSIONS = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.Roles_Permissions"), true);
	public static final String MAPPING_TABLE_USERS_ROLES_NAME = com.liferay.portal.model.impl.UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME;
	public static final boolean FINDER_CACHE_ENABLED_USERS_ROLES = com.liferay.portal.model.impl.UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Role"));

	public RoleModelImpl() {
	}

	public long getPrimaryKey() {
		return _roleId;
	}

	public void setPrimaryKey(long pk) {
		setRoleId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_roleId);
	}

	public long getRoleId() {
		return _roleId;
	}

	public void setRoleId(long roleId) {
		_roleId = roleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = classNameId;
		}
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = classPK;
		}
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	public void setName(String name) {
		_name = name;

		if (_originalName == null) {
			_originalName = name;
		}
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	public String getTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId);
	}

	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	public String getTitle(String languageId) {
		String value = LocalizationUtil.getLocalization(getTitle(), languageId);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public String getTitle(String languageId, boolean useDefault) {
		String value = LocalizationUtil.getLocalization(getTitle(), languageId,
				useDefault);

		if (isEscapedModel()) {
			return HtmlUtil.escape(value);
		}
		else {
			return value;
		}
	}

	public Map<Locale, String> getTitleMap() {
		return LocalizationUtil.getLocalizationMap(getTitle());
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setTitle(Locale locale, String title) {
		String languageId = LocaleUtil.toLanguageId(locale);

		if (Validator.isNotNull(title)) {
			setTitle(LocalizationUtil.updateLocalization(getTitle(), "Title",
					title, languageId));
		}
		else {
			setTitle(LocalizationUtil.removeLocalization(getTitle(), "Title",
					languageId));
		}
	}

	public void setTitleMap(Map<Locale, String> titleMap) {
		if (titleMap == null) {
			return;
		}

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String title = titleMap.get(locale);

			setTitle(locale, title);
		}
	}

	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getSubtype() {
		if (_subtype == null) {
			return StringPool.BLANK;
		}
		else {
			return _subtype;
		}
	}

	public void setSubtype(String subtype) {
		_subtype = subtype;
	}

	public Role toEscapedModel() {
		if (isEscapedModel()) {
			return (Role)this;
		}
		else {
			return (Role)Proxy.newProxyInstance(Role.class.getClassLoader(),
				new Class[] { Role.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Role.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		RoleImpl clone = new RoleImpl();

		clone.setRoleId(getRoleId());
		clone.setCompanyId(getCompanyId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setType(getType());
		clone.setSubtype(getSubtype());

		return clone;
	}

	public int compareTo(Role role) {
		int value = 0;

		value = getName().compareTo(role.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Role role = null;

		try {
			role = (Role)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = role.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{roleId=");
		sb.append(getRoleId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", subtype=");
		sb.append(getSubtype());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Role");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>roleId</column-name><column-value><![CDATA[");
		sb.append(getRoleId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>subtype</column-name><column-value><![CDATA[");
		sb.append(getSubtype());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _roleId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _name;
	private String _originalName;
	private String _title;
	private String _description;
	private int _type;
	private String _subtype;
	private transient ExpandoBridge _expandoBridge;
}