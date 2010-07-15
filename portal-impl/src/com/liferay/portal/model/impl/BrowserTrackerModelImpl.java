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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.BrowserTrackerModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <p>
 * This interface is a model that represents the BrowserTracker table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerImpl
 * @see       com.liferay.portal.model.BrowserTracker
 * @see       com.liferay.portal.model.BrowserTrackerModel
 * @generated
 */
public class BrowserTrackerModelImpl extends BaseModelImpl<BrowserTracker>
	implements BrowserTrackerModel {
	public static final String TABLE_NAME = "BrowserTracker";
	public static final Object[][] TABLE_COLUMNS = {
			{ "browserTrackerId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "browserKey", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table BrowserTracker (browserTrackerId LONG not null primary key,userId LONG,browserKey LONG)";
	public static final String TABLE_SQL_DROP = "drop table BrowserTracker";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.BrowserTracker"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.BrowserTracker"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.BrowserTracker"));

	public BrowserTrackerModelImpl() {
	}

	public long getPrimaryKey() {
		return _browserTrackerId;
	}

	public void setPrimaryKey(long pk) {
		setBrowserTrackerId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_browserTrackerId);
	}

	public long getBrowserTrackerId() {
		return _browserTrackerId;
	}

	public void setBrowserTrackerId(long browserTrackerId) {
		_browserTrackerId = browserTrackerId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public long getBrowserKey() {
		return _browserKey;
	}

	public void setBrowserKey(long browserKey) {
		_browserKey = browserKey;
	}

	public BrowserTracker toEscapedModel() {
		if (isEscapedModel()) {
			return (BrowserTracker)this;
		}
		else {
			return (BrowserTracker)Proxy.newProxyInstance(BrowserTracker.class.getClassLoader(),
				new Class[] { BrowserTracker.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					BrowserTracker.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		BrowserTrackerImpl clone = new BrowserTrackerImpl();

		clone.setBrowserTrackerId(getBrowserTrackerId());
		clone.setUserId(getUserId());
		clone.setBrowserKey(getBrowserKey());

		return clone;
	}

	public int compareTo(BrowserTracker browserTracker) {
		long pk = browserTracker.getPrimaryKey();

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

		BrowserTracker browserTracker = null;

		try {
			browserTracker = (BrowserTracker)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = browserTracker.getPrimaryKey();

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

		sb.append("{browserTrackerId=");
		sb.append(getBrowserTrackerId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", browserKey=");
		sb.append(getBrowserKey());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.BrowserTracker");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>browserTrackerId</column-name><column-value><![CDATA[");
		sb.append(getBrowserTrackerId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>browserKey</column-name><column-value><![CDATA[");
		sb.append(getBrowserKey());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _browserTrackerId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _browserKey;
	private transient ExpandoBridge _expandoBridge;
}