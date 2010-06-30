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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <a href="AssetTagStatsModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetTagStats table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagStatsImpl
 * @see       com.liferay.portlet.asset.model.AssetTagStats
 * @see       com.liferay.portlet.asset.model.AssetTagStatsModel
 * @generated
 */
public class AssetTagStatsModelImpl extends BaseModelImpl<AssetTagStats> {
	public static final String TABLE_NAME = "AssetTagStats";
	public static final Object[][] TABLE_COLUMNS = {
			{ "tagStatsId", new Integer(Types.BIGINT) },
			{ "tagId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "assetCount", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table AssetTagStats (tagStatsId LONG not null primary key,tagId LONG,classNameId LONG,assetCount INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table AssetTagStats";
	public static final String ORDER_BY_JPQL = " ORDER BY assetTagStats.assetCount DESC";
	public static final String ORDER_BY_SQL = " ORDER BY AssetTagStats.assetCount DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.asset.model.AssetTagStats"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.asset.model.AssetTagStats"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.asset.model.AssetTagStats"));

	public AssetTagStatsModelImpl() {
	}

	public long getPrimaryKey() {
		return _tagStatsId;
	}

	public void setPrimaryKey(long pk) {
		setTagStatsId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_tagStatsId);
	}

	public long getTagStatsId() {
		return _tagStatsId;
	}

	public void setTagStatsId(long tagStatsId) {
		_tagStatsId = tagStatsId;
	}

	public long getTagId() {
		return _tagId;
	}

	public void setTagId(long tagId) {
		_tagId = tagId;

		if (!_setOriginalTagId) {
			_setOriginalTagId = true;

			_originalTagId = tagId;
		}
	}

	public long getOriginalTagId() {
		return _originalTagId;
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

	public int getAssetCount() {
		return _assetCount;
	}

	public void setAssetCount(int assetCount) {
		_assetCount = assetCount;
	}

	public AssetTagStats toEscapedModel() {
		if (isEscapedModel()) {
			return (AssetTagStats)this;
		}
		else {
			return (AssetTagStats)Proxy.newProxyInstance(AssetTagStats.class.getClassLoader(),
				new Class[] { AssetTagStats.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					AssetTagStats.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		AssetTagStatsImpl clone = new AssetTagStatsImpl();

		clone.setTagStatsId(getTagStatsId());
		clone.setTagId(getTagId());
		clone.setClassNameId(getClassNameId());
		clone.setAssetCount(getAssetCount());

		return clone;
	}

	public int compareTo(AssetTagStats assetTagStats) {
		int value = 0;

		if (getAssetCount() < assetTagStats.getAssetCount()) {
			value = -1;
		}
		else if (getAssetCount() > assetTagStats.getAssetCount()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AssetTagStats assetTagStats = null;

		try {
			assetTagStats = (AssetTagStats)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = assetTagStats.getPrimaryKey();

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

		sb.append("{tagStatsId=");
		sb.append(getTagStatsId());
		sb.append(", tagId=");
		sb.append(getTagId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", assetCount=");
		sb.append(getAssetCount());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(16);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.asset.model.AssetTagStats");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>tagStatsId</column-name><column-value><![CDATA[");
		sb.append(getTagStatsId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tagId</column-name><column-value><![CDATA[");
		sb.append(getTagId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assetCount</column-name><column-value><![CDATA[");
		sb.append(getAssetCount());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _tagStatsId;
	private long _tagId;
	private long _originalTagId;
	private boolean _setOriginalTagId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private int _assetCount;
	private transient ExpandoBridge _expandoBridge;
}