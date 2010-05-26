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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.model.WikiPageResourceSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WikiPageResourceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the WikiPageResource table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResourceImpl
 * @see       com.liferay.portlet.wiki.model.WikiPageResource
 * @see       com.liferay.portlet.wiki.model.WikiPageResourceModel
 * @generated
 */
public class WikiPageResourceModelImpl extends BaseModelImpl<WikiPageResource> {
	public static final String TABLE_NAME = "WikiPageResource";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "resourcePrimKey", new Integer(Types.BIGINT) },
			{ "nodeId", new Integer(Types.BIGINT) },
			{ "title", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table WikiPageResource (uuid_ VARCHAR(75) null,resourcePrimKey LONG not null primary key,nodeId LONG,title VARCHAR(255) null)";
	public static final String TABLE_SQL_DROP = "drop table WikiPageResource";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.wiki.model.WikiPageResource"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.wiki.model.WikiPageResource"),
			true);

	public static WikiPageResource toModel(WikiPageResourceSoap soapModel) {
		WikiPageResource model = new WikiPageResourceImpl();

		model.setUuid(soapModel.getUuid());
		model.setResourcePrimKey(soapModel.getResourcePrimKey());
		model.setNodeId(soapModel.getNodeId());
		model.setTitle(soapModel.getTitle());

		return model;
	}

	public static List<WikiPageResource> toModels(
		WikiPageResourceSoap[] soapModels) {
		List<WikiPageResource> models = new ArrayList<WikiPageResource>(soapModels.length);

		for (WikiPageResourceSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.wiki.model.WikiPageResource"));

	public WikiPageResourceModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourcePrimKey;
	}

	public void setPrimaryKey(long pk) {
		setResourcePrimKey(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_resourcePrimKey);
	}

	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_originalUuid == null) {
			_originalUuid = uuid;
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;

		if (!_setOriginalNodeId) {
			_setOriginalNodeId = true;

			_originalNodeId = nodeId;
		}
	}

	public long getOriginalNodeId() {
		return _originalNodeId;
	}

	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}
		else {
			return _title;
		}
	}

	public void setTitle(String title) {
		_title = title;

		if (_originalTitle == null) {
			_originalTitle = title;
		}
	}

	public String getOriginalTitle() {
		return GetterUtil.getString(_originalTitle);
	}

	public WikiPageResource toEscapedModel() {
		if (isEscapedModel()) {
			return (WikiPageResource)this;
		}
		else {
			return (WikiPageResource)Proxy.newProxyInstance(WikiPageResource.class.getClassLoader(),
				new Class[] { WikiPageResource.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					WikiPageResource.class.getName(), getResourcePrimKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		WikiPageResourceImpl clone = new WikiPageResourceImpl();

		clone.setUuid(getUuid());
		clone.setResourcePrimKey(getResourcePrimKey());
		clone.setNodeId(getNodeId());
		clone.setTitle(getTitle());

		return clone;
	}

	public int compareTo(WikiPageResource wikiPageResource) {
		long pk = wikiPageResource.getPrimaryKey();

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

		WikiPageResource wikiPageResource = null;

		try {
			wikiPageResource = (WikiPageResource)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = wikiPageResource.getPrimaryKey();

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
		StringBundler sb = new StringBundler(9);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", resourcePrimKey=");
		sb.append(getResourcePrimKey());
		sb.append(", nodeId=");
		sb.append(getNodeId());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.wiki.model.WikiPageResource");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>resourcePrimKey</column-name><column-value><![CDATA[");
		sb.append(getResourcePrimKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>nodeId</column-name><column-value><![CDATA[");
		sb.append(getNodeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _resourcePrimKey;
	private long _nodeId;
	private long _originalNodeId;
	private boolean _setOriginalNodeId;
	private String _title;
	private String _originalTitle;
	private transient ExpandoBridge _expandoBridge;
}