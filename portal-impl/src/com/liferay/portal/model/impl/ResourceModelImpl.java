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
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceModel;
import com.liferay.portal.model.ResourceSoap;
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
 * This interface is a model that represents the Resource_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceImpl
 * @see       com.liferay.portal.model.Resource
 * @see       com.liferay.portal.model.ResourceModel
 * @generated
 */
public class ResourceModelImpl extends BaseModelImpl<Resource>
	implements ResourceModel {
	public static final String TABLE_NAME = "Resource_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "resourceId", new Integer(Types.BIGINT) },
			{ "codeId", new Integer(Types.BIGINT) },
			{ "primKey", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Resource_ (resourceId LONG not null primary key,codeId LONG,primKey VARCHAR(255) null)";
	public static final String TABLE_SQL_DROP = "drop table Resource_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Resource"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Resource"),
			true);

	public static Resource toModel(ResourceSoap soapModel) {
		Resource model = new ResourceImpl();

		model.setResourceId(soapModel.getResourceId());
		model.setCodeId(soapModel.getCodeId());
		model.setPrimKey(soapModel.getPrimKey());

		return model;
	}

	public static List<Resource> toModels(ResourceSoap[] soapModels) {
		List<Resource> models = new ArrayList<Resource>(soapModels.length);

		for (ResourceSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Resource"));

	public ResourceModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourceId;
	}

	public void setPrimaryKey(long pk) {
		setResourceId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_resourceId);
	}

	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		_resourceId = resourceId;
	}

	public long getCodeId() {
		return _codeId;
	}

	public void setCodeId(long codeId) {
		_codeId = codeId;

		if (!_setOriginalCodeId) {
			_setOriginalCodeId = true;

			_originalCodeId = codeId;
		}
	}

	public long getOriginalCodeId() {
		return _originalCodeId;
	}

	public String getPrimKey() {
		if (_primKey == null) {
			return StringPool.BLANK;
		}
		else {
			return _primKey;
		}
	}

	public void setPrimKey(String primKey) {
		_primKey = primKey;

		if (_originalPrimKey == null) {
			_originalPrimKey = primKey;
		}
	}

	public String getOriginalPrimKey() {
		return GetterUtil.getString(_originalPrimKey);
	}

	public Resource toEscapedModel() {
		if (isEscapedModel()) {
			return (Resource)this;
		}
		else {
			return (Resource)Proxy.newProxyInstance(Resource.class.getClassLoader(),
				new Class[] { Resource.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					Resource.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ResourceImpl clone = new ResourceImpl();

		clone.setResourceId(getResourceId());
		clone.setCodeId(getCodeId());
		clone.setPrimKey(getPrimKey());

		return clone;
	}

	public int compareTo(Resource resource) {
		long pk = resource.getPrimaryKey();

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

		Resource resource = null;

		try {
			resource = (Resource)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = resource.getPrimaryKey();

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
		StringBundler sb = new StringBundler(7);

		sb.append("{resourceId=");
		sb.append(getResourceId());
		sb.append(", codeId=");
		sb.append(getCodeId());
		sb.append(", primKey=");
		sb.append(getPrimKey());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Resource");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>resourceId</column-name><column-value><![CDATA[");
		sb.append(getResourceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>codeId</column-name><column-value><![CDATA[");
		sb.append(getCodeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>primKey</column-name><column-value><![CDATA[");
		sb.append(getPrimKey());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _resourceId;
	private long _codeId;
	private long _originalCodeId;
	private boolean _setOriginalCodeId;
	private String _primKey;
	private String _originalPrimKey;
	private transient ExpandoBridge _expandoBridge;
}