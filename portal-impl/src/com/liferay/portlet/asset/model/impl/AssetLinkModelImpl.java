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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkModel;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.Date;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the AssetLink table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkImpl
 * @see       com.liferay.portlet.asset.model.AssetLink
 * @see       com.liferay.portlet.asset.model.AssetLinkModel
 * @generated
 */
public class AssetLinkModelImpl extends BaseModelImpl<AssetLink>
	implements AssetLinkModel {
	public static final String TABLE_NAME = "AssetLink";
	public static final Object[][] TABLE_COLUMNS = {
			{ "linkId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "entryId1", new Integer(Types.BIGINT) },
			{ "entryId2", new Integer(Types.BIGINT) },
			{ "type_", new Integer(Types.INTEGER) },
			{ "weight", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table AssetLink (linkId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,entryId1 LONG,entryId2 LONG,type_ INTEGER,weight INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table AssetLink";
	public static final String ORDER_BY_JPQL = " ORDER BY assetLink.weight ASC";
	public static final String ORDER_BY_SQL = " ORDER BY AssetLink.weight ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.asset.model.AssetLink"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.asset.model.AssetLink"),
			true);
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.asset.model.AssetLink"));

	public AssetLinkModelImpl() {
	}

	public long getPrimaryKey() {
		return _linkId;
	}

	public void setPrimaryKey(long pk) {
		setLinkId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_linkId);
	}

	public long getLinkId() {
		return _linkId;
	}

	public void setLinkId(long linkId) {
		_linkId = linkId;
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
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getEntryId1() {
		return _entryId1;
	}

	public void setEntryId1(long entryId1) {
		_entryId1 = entryId1;
	}

	public long getEntryId2() {
		return _entryId2;
	}

	public void setEntryId2(long entryId2) {
		_entryId2 = entryId2;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getWeight() {
		return _weight;
	}

	public void setWeight(int weight) {
		_weight = weight;
	}

	public AssetLink toEscapedModel() {
		if (isEscapedModel()) {
			return (AssetLink)this;
		}
		else {
			return (AssetLink)Proxy.newProxyInstance(AssetLink.class.getClassLoader(),
				new Class[] { AssetLink.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					AssetLink.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		AssetLinkImpl clone = new AssetLinkImpl();

		clone.setLinkId(getLinkId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setEntryId1(getEntryId1());
		clone.setEntryId2(getEntryId2());
		clone.setType(getType());
		clone.setWeight(getWeight());

		return clone;
	}

	public int compareTo(AssetLink assetLink) {
		int value = 0;

		if (getWeight() < assetLink.getWeight()) {
			value = -1;
		}
		else if (getWeight() > assetLink.getWeight()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		AssetLink assetLink = null;

		try {
			assetLink = (AssetLink)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = assetLink.getPrimaryKey();

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

		sb.append("{linkId=");
		sb.append(getLinkId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", entryId1=");
		sb.append(getEntryId1());
		sb.append(", entryId2=");
		sb.append(getEntryId2());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", weight=");
		sb.append(getWeight());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.asset.model.AssetLink");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>linkId</column-name><column-value><![CDATA[");
		sb.append(getLinkId());
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
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId1</column-name><column-value><![CDATA[");
		sb.append(getEntryId1());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>entryId2</column-name><column-value><![CDATA[");
		sb.append(getEntryId2());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>weight</column-name><column-value><![CDATA[");
		sb.append(getWeight());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _linkId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private long _entryId1;
	private long _entryId2;
	private int _type;
	private int _weight;
	private transient ExpandoBridge _expandoBridge;
}