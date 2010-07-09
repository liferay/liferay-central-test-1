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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PluginSetting;
import com.liferay.portal.model.PluginSettingSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PluginSetting table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSettingImpl
 * @see       com.liferay.portal.model.PluginSetting
 * @see       com.liferay.portal.model.PluginSettingModel
 * @generated
 */
public class PluginSettingModelImpl extends BaseModelImpl<PluginSetting> {
	public static final String TABLE_NAME = "PluginSetting";
	public static final Object[][] TABLE_COLUMNS = {
			{ "pluginSettingId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "pluginId", new Integer(Types.VARCHAR) },
			{ "pluginType", new Integer(Types.VARCHAR) },
			{ "roles", new Integer(Types.VARCHAR) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table PluginSetting (pluginSettingId LONG not null primary key,companyId LONG,pluginId VARCHAR(75) null,pluginType VARCHAR(75) null,roles STRING null,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table PluginSetting";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PluginSetting"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PluginSetting"),
			true);

	public static PluginSetting toModel(PluginSettingSoap soapModel) {
		PluginSetting model = new PluginSettingImpl();

		model.setPluginSettingId(soapModel.getPluginSettingId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setPluginId(soapModel.getPluginId());
		model.setPluginType(soapModel.getPluginType());
		model.setRoles(soapModel.getRoles());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<PluginSetting> toModels(PluginSettingSoap[] soapModels) {
		List<PluginSetting> models = new ArrayList<PluginSetting>(soapModels.length);

		for (PluginSettingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PluginSetting"));

	public PluginSettingModelImpl() {
	}

	public long getPrimaryKey() {
		return _pluginSettingId;
	}

	public void setPrimaryKey(long pk) {
		setPluginSettingId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_pluginSettingId);
	}

	public long getPluginSettingId() {
		return _pluginSettingId;
	}

	public void setPluginSettingId(long pluginSettingId) {
		_pluginSettingId = pluginSettingId;
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

	public String getPluginId() {
		if (_pluginId == null) {
			return StringPool.BLANK;
		}
		else {
			return _pluginId;
		}
	}

	public void setPluginId(String pluginId) {
		_pluginId = pluginId;

		if (_originalPluginId == null) {
			_originalPluginId = pluginId;
		}
	}

	public String getOriginalPluginId() {
		return GetterUtil.getString(_originalPluginId);
	}

	public String getPluginType() {
		if (_pluginType == null) {
			return StringPool.BLANK;
		}
		else {
			return _pluginType;
		}
	}

	public void setPluginType(String pluginType) {
		_pluginType = pluginType;

		if (_originalPluginType == null) {
			_originalPluginType = pluginType;
		}
	}

	public String getOriginalPluginType() {
		return GetterUtil.getString(_originalPluginType);
	}

	public String getRoles() {
		if (_roles == null) {
			return StringPool.BLANK;
		}
		else {
			return _roles;
		}
	}

	public void setRoles(String roles) {
		_roles = roles;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public PluginSetting toEscapedModel() {
		if (isEscapedModel()) {
			return (PluginSetting)this;
		}
		else {
			return (PluginSetting)Proxy.newProxyInstance(PluginSetting.class.getClassLoader(),
				new Class[] { PluginSetting.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					PluginSetting.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		PluginSettingImpl clone = new PluginSettingImpl();

		clone.setPluginSettingId(getPluginSettingId());
		clone.setCompanyId(getCompanyId());
		clone.setPluginId(getPluginId());
		clone.setPluginType(getPluginType());
		clone.setRoles(getRoles());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(PluginSetting pluginSetting) {
		long pk = pluginSetting.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		PluginSetting pluginSetting = null;

		try {
			pluginSetting = (PluginSetting)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = pluginSetting.getPrimaryKey();

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
		StringBundler sb = new StringBundler(13);

		sb.append("{pluginSettingId=");
		sb.append(getPluginSettingId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", pluginId=");
		sb.append(getPluginId());
		sb.append(", pluginType=");
		sb.append(getPluginType());
		sb.append(", roles=");
		sb.append(getRoles());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PluginSetting");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pluginSettingId</column-name><column-value><![CDATA[");
		sb.append(getPluginSettingId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pluginId</column-name><column-value><![CDATA[");
		sb.append(getPluginId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>pluginType</column-name><column-value><![CDATA[");
		sb.append(getPluginType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>roles</column-name><column-value><![CDATA[");
		sb.append(getRoles());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _pluginSettingId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private String _pluginId;
	private String _originalPluginId;
	private String _pluginType;
	private String _originalPluginType;
	private String _roles;
	private boolean _active;
	private transient ExpandoBridge _expandoBridge;
}