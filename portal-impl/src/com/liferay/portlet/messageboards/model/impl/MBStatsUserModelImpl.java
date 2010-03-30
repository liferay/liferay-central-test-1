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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBStatsUserSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBStatsUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBStatsUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBStatsUserImpl
 * @see       com.liferay.portlet.messageboards.model.MBStatsUser
 * @see       com.liferay.portlet.messageboards.model.MBStatsUserModel
 * @generated
 */
public class MBStatsUserModelImpl extends BaseModelImpl<MBStatsUser> {
	public static final String TABLE_NAME = "MBStatsUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "statsUserId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "messageCount", new Integer(Types.INTEGER) },
			{ "lastPostDate", new Integer(Types.TIMESTAMP) }
		};
	public static final String TABLE_SQL_CREATE = "create table MBStatsUser (statsUserId LONG not null primary key,groupId LONG,userId LONG,messageCount INTEGER,lastPostDate DATE null)";
	public static final String TABLE_SQL_DROP = "drop table MBStatsUser";
	public static final String ORDER_BY_JPQL = " ORDER BY mbStatsUser.messageCount DESC";
	public static final String ORDER_BY_SQL = " ORDER BY MBStatsUser.messageCount DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBStatsUser"),
			true);

	public static MBStatsUser toModel(MBStatsUserSoap soapModel) {
		MBStatsUser model = new MBStatsUserImpl();

		model.setStatsUserId(soapModel.getStatsUserId());
		model.setGroupId(soapModel.getGroupId());
		model.setUserId(soapModel.getUserId());
		model.setMessageCount(soapModel.getMessageCount());
		model.setLastPostDate(soapModel.getLastPostDate());

		return model;
	}

	public static List<MBStatsUser> toModels(MBStatsUserSoap[] soapModels) {
		List<MBStatsUser> models = new ArrayList<MBStatsUser>(soapModels.length);

		for (MBStatsUserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBStatsUser"));

	public MBStatsUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long pk) {
		setStatsUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_statsUserId);
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
	}

	public String getStatsUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatsUserId(), "uuid", _statsUserUuid);
	}

	public void setStatsUserUuid(String statsUserUuid) {
		_statsUserUuid = statsUserUuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
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

	public int getMessageCount() {
		return _messageCount;
	}

	public void setMessageCount(int messageCount) {
		_messageCount = messageCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public MBStatsUser toEscapedModel() {
		if (isEscapedModel()) {
			return (MBStatsUser)this;
		}
		else {
			return (MBStatsUser)Proxy.newProxyInstance(MBStatsUser.class.getClassLoader(),
				new Class[] { MBStatsUser.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					MBStatsUser.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBStatsUserImpl clone = new MBStatsUserImpl();

		clone.setStatsUserId(getStatsUserId());
		clone.setGroupId(getGroupId());
		clone.setUserId(getUserId());
		clone.setMessageCount(getMessageCount());
		clone.setLastPostDate(getLastPostDate());

		return clone;
	}

	public int compareTo(MBStatsUser mbStatsUser) {
		int value = 0;

		if (getMessageCount() < mbStatsUser.getMessageCount()) {
			value = -1;
		}
		else if (getMessageCount() > mbStatsUser.getMessageCount()) {
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

		MBStatsUser mbStatsUser = null;

		try {
			mbStatsUser = (MBStatsUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbStatsUser.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{statsUserId=");
		sb.append(getStatsUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", messageCount=");
		sb.append(getMessageCount());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBStatsUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>statsUserId</column-name><column-value><![CDATA[");
		sb.append(getStatsUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageCount</column-name><column-value><![CDATA[");
		sb.append(getMessageCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _statsUserId;
	private String _statsUserUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private int _messageCount;
	private Date _lastPostDate;
	private transient ExpandoBridge _expandoBridge;
}