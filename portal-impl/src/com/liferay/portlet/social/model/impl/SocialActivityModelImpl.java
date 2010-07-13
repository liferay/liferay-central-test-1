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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityModel;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialActivity table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityImpl
 * @see       com.liferay.portlet.social.model.SocialActivity
 * @see       com.liferay.portlet.social.model.SocialActivityModel
 * @generated
 */
public class SocialActivityModelImpl extends BaseModelImpl<SocialActivity>
	implements SocialActivityModel {
	public static final String TABLE_NAME = "SocialActivity";
	public static final Object[][] TABLE_COLUMNS = {
			{ "activityId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.BIGINT) },
			{ "mirrorActivityId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "classPK", new Integer(Types.BIGINT) },
			{ "type_", new Integer(Types.INTEGER) },
			{ "extraData", new Integer(Types.VARCHAR) },
			{ "receiverUserId", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate LONG,mirrorActivityId LONG,classNameId LONG,classPK LONG,type_ INTEGER,extraData STRING null,receiverUserId LONG)";
	public static final String TABLE_SQL_DROP = "drop table SocialActivity";
	public static final String ORDER_BY_JPQL = " ORDER BY socialActivity.createDate DESC";
	public static final String ORDER_BY_SQL = " ORDER BY SocialActivity.createDate DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.social.model.SocialActivity"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialActivity"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialActivity"));

	public SocialActivityModelImpl() {
	}

	public long getPrimaryKey() {
		return _activityId;
	}

	public void setPrimaryKey(long pk) {
		setActivityId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_activityId);
	}

	public long getActivityId() {
		return _activityId;
	}

	public void setActivityId(long activityId) {
		_activityId = activityId;
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

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;

		if (!_setOriginalCreateDate) {
			_setOriginalCreateDate = true;

			_originalCreateDate = createDate;
		}
	}

	public long getOriginalCreateDate() {
		return _originalCreateDate;
	}

	public long getMirrorActivityId() {
		return _mirrorActivityId;
	}

	public void setMirrorActivityId(long mirrorActivityId) {
		_mirrorActivityId = mirrorActivityId;

		if (!_setOriginalMirrorActivityId) {
			_setOriginalMirrorActivityId = true;

			_originalMirrorActivityId = mirrorActivityId;
		}
	}

	public long getOriginalMirrorActivityId() {
		return _originalMirrorActivityId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;

		if (!_setOriginalType) {
			_setOriginalType = true;

			_originalType = type;
		}
	}

	public int getOriginalType() {
		return _originalType;
	}

	public String getExtraData() {
		if (_extraData == null) {
			return StringPool.BLANK;
		}
		else {
			return _extraData;
		}
	}

	public void setExtraData(String extraData) {
		_extraData = extraData;
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		_receiverUserId = receiverUserId;

		if (!_setOriginalReceiverUserId) {
			_setOriginalReceiverUserId = true;

			_originalReceiverUserId = receiverUserId;
		}
	}

	public String getReceiverUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getReceiverUserId(), "uuid",
			_receiverUserUuid);
	}

	public void setReceiverUserUuid(String receiverUserUuid) {
		_receiverUserUuid = receiverUserUuid;
	}

	public long getOriginalReceiverUserId() {
		return _originalReceiverUserId;
	}

	public SocialActivity toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialActivity)this;
		}
		else {
			return (SocialActivity)Proxy.newProxyInstance(SocialActivity.class.getClassLoader(),
				new Class[] { SocialActivity.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					SocialActivity.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		SocialActivityImpl clone = new SocialActivityImpl();

		clone.setActivityId(getActivityId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setCreateDate(getCreateDate());
		clone.setMirrorActivityId(getMirrorActivityId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setType(getType());
		clone.setExtraData(getExtraData());
		clone.setReceiverUserId(getReceiverUserId());

		return clone;
	}

	public int compareTo(SocialActivity socialActivity) {
		int value = 0;

		if (getCreateDate() < socialActivity.getCreateDate()) {
			value = -1;
		}
		else if (getCreateDate() > socialActivity.getCreateDate()) {
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

		SocialActivity socialActivity = null;

		try {
			socialActivity = (SocialActivity)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialActivity.getPrimaryKey();

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
		StringBundler sb = new StringBundler(23);

		sb.append("{activityId=");
		sb.append(getActivityId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", mirrorActivityId=");
		sb.append(getMirrorActivityId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", extraData=");
		sb.append(getExtraData());
		sb.append(", receiverUserId=");
		sb.append(getReceiverUserId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(37);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.social.model.SocialActivity");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>activityId</column-name><column-value><![CDATA[");
		sb.append(getActivityId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mirrorActivityId</column-name><column-value><![CDATA[");
		sb.append(getMirrorActivityId());
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
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extraData</column-name><column-value><![CDATA[");
		sb.append(getExtraData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>receiverUserId</column-name><column-value><![CDATA[");
		sb.append(getReceiverUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _activityId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private long _createDate;
	private long _originalCreateDate;
	private boolean _setOriginalCreateDate;
	private long _mirrorActivityId;
	private long _originalMirrorActivityId;
	private boolean _setOriginalMirrorActivityId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private int _type;
	private int _originalType;
	private boolean _setOriginalType;
	private String _extraData;
	private long _receiverUserId;
	private String _receiverUserUuid;
	private long _originalReceiverUserId;
	private boolean _setOriginalReceiverUserId;
	private transient ExpandoBridge _expandoBridge;
}