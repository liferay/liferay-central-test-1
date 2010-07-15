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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnModel;
import com.liferay.portlet.expando.model.ExpandoColumnSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This interface is a model that represents the ExpandoColumn table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnImpl
 * @see       com.liferay.portlet.expando.model.ExpandoColumn
 * @see       com.liferay.portlet.expando.model.ExpandoColumnModel
 * @generated
 */
public class ExpandoColumnModelImpl extends BaseModelImpl<ExpandoColumn>
	implements ExpandoColumnModel {
	public static final String TABLE_NAME = "ExpandoColumn";
	public static final Object[][] TABLE_COLUMNS = {
			{ "columnId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "tableId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.INTEGER) },
			{ "defaultData", new Integer(Types.VARCHAR) },
			{ "typeSettings", new Integer(Types.CLOB) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoColumn (columnId LONG not null primary key,companyId LONG,tableId LONG,name VARCHAR(75) null,type_ INTEGER,defaultData STRING null,typeSettings TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoColumn";
	public static final String ORDER_BY_JPQL = " ORDER BY expandoColumn.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ExpandoColumn.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoColumn"),
			true);

	public static ExpandoColumn toModel(ExpandoColumnSoap soapModel) {
		ExpandoColumn model = new ExpandoColumnImpl();

		model.setColumnId(soapModel.getColumnId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setTableId(soapModel.getTableId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());
		model.setDefaultData(soapModel.getDefaultData());
		model.setTypeSettings(soapModel.getTypeSettings());

		return model;
	}

	public static List<ExpandoColumn> toModels(ExpandoColumnSoap[] soapModels) {
		List<ExpandoColumn> models = new ArrayList<ExpandoColumn>(soapModels.length);

		for (ExpandoColumnSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoColumn"));

	public ExpandoColumnModelImpl() {
	}

	public long getPrimaryKey() {
		return _columnId;
	}

	public void setPrimaryKey(long pk) {
		setColumnId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_columnId);
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		_columnId = columnId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;

		if (!_setOriginalTableId) {
			_setOriginalTableId = true;

			_originalTableId = tableId;
		}
	}

	public long getOriginalTableId() {
		return _originalTableId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getDefaultData() {
		if (_defaultData == null) {
			return StringPool.BLANK;
		}
		else {
			return _defaultData;
		}
	}

	public void setDefaultData(String defaultData) {
		_defaultData = defaultData;
	}

	public String getTypeSettings() {
		if (_typeSettings == null) {
			return StringPool.BLANK;
		}
		else {
			return _typeSettings;
		}
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public ExpandoColumn toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoColumn)this;
		}
		else {
			return (ExpandoColumn)Proxy.newProxyInstance(ExpandoColumn.class.getClassLoader(),
				new Class[] { ExpandoColumn.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public Object clone() {
		ExpandoColumnImpl clone = new ExpandoColumnImpl();

		clone.setColumnId(getColumnId());
		clone.setCompanyId(getCompanyId());
		clone.setTableId(getTableId());
		clone.setName(getName());
		clone.setType(getType());
		clone.setDefaultData(getDefaultData());
		clone.setTypeSettings(getTypeSettings());

		return clone;
	}

	public int compareTo(ExpandoColumn expandoColumn) {
		int value = 0;

		value = getName().compareTo(expandoColumn.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ExpandoColumn expandoColumn = null;

		try {
			expandoColumn = (ExpandoColumn)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoColumn.getPrimaryKey();

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
		StringBundler sb = new StringBundler(15);

		sb.append("{columnId=");
		sb.append(getColumnId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", tableId=");
		sb.append(getTableId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", defaultData=");
		sb.append(getDefaultData());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.expando.model.ExpandoColumn");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>columnId</column-name><column-value><![CDATA[");
		sb.append(getColumnId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tableId</column-name><column-value><![CDATA[");
		sb.append(getTableId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>defaultData</column-name><column-value><![CDATA[");
		sb.append(getDefaultData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _columnId;
	private long _companyId;
	private long _tableId;
	private long _originalTableId;
	private boolean _setOriginalTableId;
	private String _name;
	private String _originalName;
	private int _type;
	private String _defaultData;
	private String _typeSettings;
}