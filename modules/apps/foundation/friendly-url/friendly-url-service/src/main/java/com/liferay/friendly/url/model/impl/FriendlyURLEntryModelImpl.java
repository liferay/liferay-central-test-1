/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.friendly.url.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryModel;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the FriendlyURLEntry service. Represents a row in the &quot;FriendlyURLEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link FriendlyURLEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FriendlyURLEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryImpl
 * @see FriendlyURLEntry
 * @see FriendlyURLEntryModel
 * @generated
 */
@ProviderType
public class FriendlyURLEntryModelImpl extends BaseModelImpl<FriendlyURLEntry>
	implements FriendlyURLEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a friendly url entry model instance should use the {@link FriendlyURLEntry} interface instead.
	 */
	public static final String TABLE_NAME = "FriendlyURLEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "friendlyURLEntryId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "classNameId", Types.BIGINT },
			{ "classPK", Types.BIGINT },
			{ "urlTitle", Types.VARCHAR },
			{ "main", Types.BOOLEAN }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("friendlyURLEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("main", Types.BOOLEAN);
	}

	public static final String TABLE_SQL_CREATE = "create table FriendlyURLEntry (uuid_ VARCHAR(75) null,friendlyURLEntryId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,urlTitle VARCHAR(255) null,main BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table FriendlyURLEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY friendlyURLEntry.friendlyURLEntryId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY FriendlyURLEntry.friendlyURLEntryId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.friendly.url.model.FriendlyURLEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.friendly.url.model.FriendlyURLEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.friendly.url.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.friendly.url.model.FriendlyURLEntry"),
			true);
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;
	public static final long CLASSPK_COLUMN_BITMASK = 2L;
	public static final long COMPANYID_COLUMN_BITMASK = 4L;
	public static final long GROUPID_COLUMN_BITMASK = 8L;
	public static final long MAIN_COLUMN_BITMASK = 16L;
	public static final long URLTITLE_COLUMN_BITMASK = 32L;
	public static final long UUID_COLUMN_BITMASK = 64L;
	public static final long FRIENDLYURLENTRYID_COLUMN_BITMASK = 128L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.friendly.url.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.friendly.url.model.FriendlyURLEntry"));

	public FriendlyURLEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setFriendlyURLEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("main", getMain());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryId = friendlyURLEntryId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@Override
	public String getUrlTitle() {
		if (_urlTitle == null) {
			return StringPool.BLANK;
		}
		else {
			return _urlTitle;
		}
	}

	@Override
	public void setUrlTitle(String urlTitle) {
		_columnBitmask |= URLTITLE_COLUMN_BITMASK;

		if (_originalUrlTitle == null) {
			_originalUrlTitle = _urlTitle;
		}

		_urlTitle = urlTitle;
	}

	public String getOriginalUrlTitle() {
		return GetterUtil.getString(_originalUrlTitle);
	}

	@Override
	public boolean getMain() {
		return _main;
	}

	@Override
	public boolean isMain() {
		return _main;
	}

	@Override
	public void setMain(boolean main) {
		_columnBitmask |= MAIN_COLUMN_BITMASK;

		if (!_setOriginalMain) {
			_setOriginalMain = true;

			_originalMain = _main;
		}

		_main = main;
	}

	public boolean getOriginalMain() {
		return _originalMain;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				FriendlyURLEntry.class.getName()), getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			FriendlyURLEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public FriendlyURLEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (FriendlyURLEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		FriendlyURLEntryImpl friendlyURLEntryImpl = new FriendlyURLEntryImpl();

		friendlyURLEntryImpl.setUuid(getUuid());
		friendlyURLEntryImpl.setFriendlyURLEntryId(getFriendlyURLEntryId());
		friendlyURLEntryImpl.setGroupId(getGroupId());
		friendlyURLEntryImpl.setCompanyId(getCompanyId());
		friendlyURLEntryImpl.setCreateDate(getCreateDate());
		friendlyURLEntryImpl.setModifiedDate(getModifiedDate());
		friendlyURLEntryImpl.setClassNameId(getClassNameId());
		friendlyURLEntryImpl.setClassPK(getClassPK());
		friendlyURLEntryImpl.setUrlTitle(getUrlTitle());
		friendlyURLEntryImpl.setMain(getMain());

		friendlyURLEntryImpl.resetOriginalValues();

		return friendlyURLEntryImpl;
	}

	@Override
	public int compareTo(FriendlyURLEntry friendlyURLEntry) {
		long primaryKey = friendlyURLEntry.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntry)) {
			return false;
		}

		FriendlyURLEntry friendlyURLEntry = (FriendlyURLEntry)obj;

		long primaryKey = friendlyURLEntry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		FriendlyURLEntryModelImpl friendlyURLEntryModelImpl = this;

		friendlyURLEntryModelImpl._originalUuid = friendlyURLEntryModelImpl._uuid;

		friendlyURLEntryModelImpl._originalGroupId = friendlyURLEntryModelImpl._groupId;

		friendlyURLEntryModelImpl._setOriginalGroupId = false;

		friendlyURLEntryModelImpl._originalCompanyId = friendlyURLEntryModelImpl._companyId;

		friendlyURLEntryModelImpl._setOriginalCompanyId = false;

		friendlyURLEntryModelImpl._setModifiedDate = false;

		friendlyURLEntryModelImpl._originalClassNameId = friendlyURLEntryModelImpl._classNameId;

		friendlyURLEntryModelImpl._setOriginalClassNameId = false;

		friendlyURLEntryModelImpl._originalClassPK = friendlyURLEntryModelImpl._classPK;

		friendlyURLEntryModelImpl._setOriginalClassPK = false;

		friendlyURLEntryModelImpl._originalUrlTitle = friendlyURLEntryModelImpl._urlTitle;

		friendlyURLEntryModelImpl._originalMain = friendlyURLEntryModelImpl._main;

		friendlyURLEntryModelImpl._setOriginalMain = false;

		friendlyURLEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<FriendlyURLEntry> toCacheModel() {
		FriendlyURLEntryCacheModel friendlyURLEntryCacheModel = new FriendlyURLEntryCacheModel();

		friendlyURLEntryCacheModel.uuid = getUuid();

		String uuid = friendlyURLEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			friendlyURLEntryCacheModel.uuid = null;
		}

		friendlyURLEntryCacheModel.friendlyURLEntryId = getFriendlyURLEntryId();

		friendlyURLEntryCacheModel.groupId = getGroupId();

		friendlyURLEntryCacheModel.companyId = getCompanyId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			friendlyURLEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			friendlyURLEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			friendlyURLEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			friendlyURLEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		friendlyURLEntryCacheModel.classNameId = getClassNameId();

		friendlyURLEntryCacheModel.classPK = getClassPK();

		friendlyURLEntryCacheModel.urlTitle = getUrlTitle();

		String urlTitle = friendlyURLEntryCacheModel.urlTitle;

		if ((urlTitle != null) && (urlTitle.length() == 0)) {
			friendlyURLEntryCacheModel.urlTitle = null;
		}

		friendlyURLEntryCacheModel.main = getMain();

		return friendlyURLEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", friendlyURLEntryId=");
		sb.append(getFriendlyURLEntryId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", urlTitle=");
		sb.append(getUrlTitle());
		sb.append(", main=");
		sb.append(getMain());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.friendly.url.model.FriendlyURLEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>friendlyURLEntryId</column-name><column-value><![CDATA[");
		sb.append(getFriendlyURLEntryId());
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
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
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
			"<column><column-name>urlTitle</column-name><column-value><![CDATA[");
		sb.append(getUrlTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>main</column-name><column-value><![CDATA[");
		sb.append(getMain());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = FriendlyURLEntry.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			FriendlyURLEntry.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _friendlyURLEntryId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _urlTitle;
	private String _originalUrlTitle;
	private boolean _main;
	private boolean _originalMain;
	private boolean _setOriginalMain;
	private long _columnBitmask;
	private FriendlyURLEntry _escapedModel;
}