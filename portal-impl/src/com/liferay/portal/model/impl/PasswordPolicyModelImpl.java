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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicySoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="PasswordPolicyModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PasswordPolicy table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordPolicyImpl
 * @see       com.liferay.portal.model.PasswordPolicy
 * @see       com.liferay.portal.model.PasswordPolicyModel
 * @generated
 */
public class PasswordPolicyModelImpl extends BaseModelImpl<PasswordPolicy> {
	public static final String TABLE_NAME = "PasswordPolicy";
	public static final Object[][] TABLE_COLUMNS = {
			{ "passwordPolicyId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "defaultPolicy", new Integer(Types.BOOLEAN) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "changeable", new Integer(Types.BOOLEAN) },
			{ "changeRequired", new Integer(Types.BOOLEAN) },
			{ "minAge", new Integer(Types.BIGINT) },
			{ "checkSyntax", new Integer(Types.BOOLEAN) },
			{ "allowDictionaryWords", new Integer(Types.BOOLEAN) },
			{ "minLength", new Integer(Types.INTEGER) },
			{ "history", new Integer(Types.BOOLEAN) },
			{ "historyCount", new Integer(Types.INTEGER) },
			{ "expireable", new Integer(Types.BOOLEAN) },
			{ "maxAge", new Integer(Types.BIGINT) },
			{ "warningTime", new Integer(Types.BIGINT) },
			{ "graceLimit", new Integer(Types.INTEGER) },
			{ "lockout", new Integer(Types.BOOLEAN) },
			{ "maxFailure", new Integer(Types.INTEGER) },
			{ "lockoutDuration", new Integer(Types.BIGINT) },
			{ "requireUnlock", new Integer(Types.BOOLEAN) },
			{ "resetFailureCount", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table PasswordPolicy (passwordPolicyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultPolicy BOOLEAN,name VARCHAR(75) null,description STRING null,changeable BOOLEAN,changeRequired BOOLEAN,minAge LONG,checkSyntax BOOLEAN,allowDictionaryWords BOOLEAN,minLength INTEGER,history BOOLEAN,historyCount INTEGER,expireable BOOLEAN,maxAge LONG,warningTime LONG,graceLimit INTEGER,lockout BOOLEAN,maxFailure INTEGER,lockoutDuration LONG,requireUnlock BOOLEAN,resetFailureCount LONG)";
	public static final String TABLE_SQL_DROP = "drop table PasswordPolicy";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.PasswordPolicy"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.PasswordPolicy"),
			true);

	public static PasswordPolicy toModel(PasswordPolicySoap soapModel) {
		PasswordPolicy model = new PasswordPolicyImpl();

		model.setPasswordPolicyId(soapModel.getPasswordPolicyId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setDefaultPolicy(soapModel.getDefaultPolicy());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setChangeable(soapModel.getChangeable());
		model.setChangeRequired(soapModel.getChangeRequired());
		model.setMinAge(soapModel.getMinAge());
		model.setCheckSyntax(soapModel.getCheckSyntax());
		model.setAllowDictionaryWords(soapModel.getAllowDictionaryWords());
		model.setMinLength(soapModel.getMinLength());
		model.setHistory(soapModel.getHistory());
		model.setHistoryCount(soapModel.getHistoryCount());
		model.setExpireable(soapModel.getExpireable());
		model.setMaxAge(soapModel.getMaxAge());
		model.setWarningTime(soapModel.getWarningTime());
		model.setGraceLimit(soapModel.getGraceLimit());
		model.setLockout(soapModel.getLockout());
		model.setMaxFailure(soapModel.getMaxFailure());
		model.setLockoutDuration(soapModel.getLockoutDuration());
		model.setRequireUnlock(soapModel.getRequireUnlock());
		model.setResetFailureCount(soapModel.getResetFailureCount());

		return model;
	}

	public static List<PasswordPolicy> toModels(PasswordPolicySoap[] soapModels) {
		List<PasswordPolicy> models = new ArrayList<PasswordPolicy>(soapModels.length);

		for (PasswordPolicySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.PasswordPolicy"));

	public PasswordPolicyModelImpl() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordPolicyId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_passwordPolicyId);
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyId = passwordPolicyId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getDefaultPolicy() {
		return _defaultPolicy;
	}

	public boolean isDefaultPolicy() {
		return _defaultPolicy;
	}

	public void setDefaultPolicy(boolean defaultPolicy) {
		_defaultPolicy = defaultPolicy;

		if (!_setOriginalDefaultPolicy) {
			_setOriginalDefaultPolicy = true;

			_originalDefaultPolicy = defaultPolicy;
		}
	}

	public boolean getOriginalDefaultPolicy() {
		return _originalDefaultPolicy;
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

	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public boolean getChangeable() {
		return _changeable;
	}

	public boolean isChangeable() {
		return _changeable;
	}

	public void setChangeable(boolean changeable) {
		_changeable = changeable;
	}

	public boolean getChangeRequired() {
		return _changeRequired;
	}

	public boolean isChangeRequired() {
		return _changeRequired;
	}

	public void setChangeRequired(boolean changeRequired) {
		_changeRequired = changeRequired;
	}

	public long getMinAge() {
		return _minAge;
	}

	public void setMinAge(long minAge) {
		_minAge = minAge;
	}

	public boolean getCheckSyntax() {
		return _checkSyntax;
	}

	public boolean isCheckSyntax() {
		return _checkSyntax;
	}

	public void setCheckSyntax(boolean checkSyntax) {
		_checkSyntax = checkSyntax;
	}

	public boolean getAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public boolean isAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		_allowDictionaryWords = allowDictionaryWords;
	}

	public int getMinLength() {
		return _minLength;
	}

	public void setMinLength(int minLength) {
		_minLength = minLength;
	}

	public boolean getHistory() {
		return _history;
	}

	public boolean isHistory() {
		return _history;
	}

	public void setHistory(boolean history) {
		_history = history;
	}

	public int getHistoryCount() {
		return _historyCount;
	}

	public void setHistoryCount(int historyCount) {
		_historyCount = historyCount;
	}

	public boolean getExpireable() {
		return _expireable;
	}

	public boolean isExpireable() {
		return _expireable;
	}

	public void setExpireable(boolean expireable) {
		_expireable = expireable;
	}

	public long getMaxAge() {
		return _maxAge;
	}

	public void setMaxAge(long maxAge) {
		_maxAge = maxAge;
	}

	public long getWarningTime() {
		return _warningTime;
	}

	public void setWarningTime(long warningTime) {
		_warningTime = warningTime;
	}

	public int getGraceLimit() {
		return _graceLimit;
	}

	public void setGraceLimit(int graceLimit) {
		_graceLimit = graceLimit;
	}

	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public int getMaxFailure() {
		return _maxFailure;
	}

	public void setMaxFailure(int maxFailure) {
		_maxFailure = maxFailure;
	}

	public long getLockoutDuration() {
		return _lockoutDuration;
	}

	public void setLockoutDuration(long lockoutDuration) {
		_lockoutDuration = lockoutDuration;
	}

	public boolean getRequireUnlock() {
		return _requireUnlock;
	}

	public boolean isRequireUnlock() {
		return _requireUnlock;
	}

	public void setRequireUnlock(boolean requireUnlock) {
		_requireUnlock = requireUnlock;
	}

	public long getResetFailureCount() {
		return _resetFailureCount;
	}

	public void setResetFailureCount(long resetFailureCount) {
		_resetFailureCount = resetFailureCount;
	}

	public PasswordPolicy toEscapedModel() {
		if (isEscapedModel()) {
			return (PasswordPolicy)this;
		}
		else {
			return (PasswordPolicy)Proxy.newProxyInstance(PasswordPolicy.class.getClassLoader(),
				new Class[] { PasswordPolicy.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					PasswordPolicy.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		PasswordPolicyImpl clone = new PasswordPolicyImpl();

		clone.setPasswordPolicyId(getPasswordPolicyId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setDefaultPolicy(getDefaultPolicy());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setChangeable(getChangeable());
		clone.setChangeRequired(getChangeRequired());
		clone.setMinAge(getMinAge());
		clone.setCheckSyntax(getCheckSyntax());
		clone.setAllowDictionaryWords(getAllowDictionaryWords());
		clone.setMinLength(getMinLength());
		clone.setHistory(getHistory());
		clone.setHistoryCount(getHistoryCount());
		clone.setExpireable(getExpireable());
		clone.setMaxAge(getMaxAge());
		clone.setWarningTime(getWarningTime());
		clone.setGraceLimit(getGraceLimit());
		clone.setLockout(getLockout());
		clone.setMaxFailure(getMaxFailure());
		clone.setLockoutDuration(getLockoutDuration());
		clone.setRequireUnlock(getRequireUnlock());
		clone.setResetFailureCount(getResetFailureCount());

		return clone;
	}

	public int compareTo(PasswordPolicy passwordPolicy) {
		long pk = passwordPolicy.getPrimaryKey();

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

		PasswordPolicy passwordPolicy = null;

		try {
			passwordPolicy = (PasswordPolicy)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = passwordPolicy.getPrimaryKey();

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
		StringBundler sb = new StringBundler(53);

		sb.append("{passwordPolicyId=");
		sb.append(getPasswordPolicyId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", defaultPolicy=");
		sb.append(getDefaultPolicy());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", changeable=");
		sb.append(getChangeable());
		sb.append(", changeRequired=");
		sb.append(getChangeRequired());
		sb.append(", minAge=");
		sb.append(getMinAge());
		sb.append(", checkSyntax=");
		sb.append(getCheckSyntax());
		sb.append(", allowDictionaryWords=");
		sb.append(getAllowDictionaryWords());
		sb.append(", minLength=");
		sb.append(getMinLength());
		sb.append(", history=");
		sb.append(getHistory());
		sb.append(", historyCount=");
		sb.append(getHistoryCount());
		sb.append(", expireable=");
		sb.append(getExpireable());
		sb.append(", maxAge=");
		sb.append(getMaxAge());
		sb.append(", warningTime=");
		sb.append(getWarningTime());
		sb.append(", graceLimit=");
		sb.append(getGraceLimit());
		sb.append(", lockout=");
		sb.append(getLockout());
		sb.append(", maxFailure=");
		sb.append(getMaxFailure());
		sb.append(", lockoutDuration=");
		sb.append(getLockoutDuration());
		sb.append(", requireUnlock=");
		sb.append(getRequireUnlock());
		sb.append(", resetFailureCount=");
		sb.append(getResetFailureCount());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(82);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.PasswordPolicy");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>passwordPolicyId</column-name><column-value><![CDATA[");
		sb.append(getPasswordPolicyId());
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
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>defaultPolicy</column-name><column-value><![CDATA[");
		sb.append(getDefaultPolicy());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>changeable</column-name><column-value><![CDATA[");
		sb.append(getChangeable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>changeRequired</column-name><column-value><![CDATA[");
		sb.append(getChangeRequired());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minAge</column-name><column-value><![CDATA[");
		sb.append(getMinAge());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>checkSyntax</column-name><column-value><![CDATA[");
		sb.append(getCheckSyntax());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>allowDictionaryWords</column-name><column-value><![CDATA[");
		sb.append(getAllowDictionaryWords());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>minLength</column-name><column-value><![CDATA[");
		sb.append(getMinLength());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>history</column-name><column-value><![CDATA[");
		sb.append(getHistory());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>historyCount</column-name><column-value><![CDATA[");
		sb.append(getHistoryCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>expireable</column-name><column-value><![CDATA[");
		sb.append(getExpireable());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxAge</column-name><column-value><![CDATA[");
		sb.append(getMaxAge());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>warningTime</column-name><column-value><![CDATA[");
		sb.append(getWarningTime());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>graceLimit</column-name><column-value><![CDATA[");
		sb.append(getGraceLimit());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockout</column-name><column-value><![CDATA[");
		sb.append(getLockout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>maxFailure</column-name><column-value><![CDATA[");
		sb.append(getMaxFailure());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lockoutDuration</column-name><column-value><![CDATA[");
		sb.append(getLockoutDuration());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>requireUnlock</column-name><column-value><![CDATA[");
		sb.append(getRequireUnlock());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>resetFailureCount</column-name><column-value><![CDATA[");
		sb.append(getResetFailureCount());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _passwordPolicyId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultPolicy;
	private boolean _originalDefaultPolicy;
	private boolean _setOriginalDefaultPolicy;
	private String _name;
	private String _originalName;
	private String _description;
	private boolean _changeable;
	private boolean _changeRequired;
	private long _minAge;
	private boolean _checkSyntax;
	private boolean _allowDictionaryWords;
	private int _minLength;
	private boolean _history;
	private int _historyCount;
	private boolean _expireable;
	private long _maxAge;
	private long _warningTime;
	private int _graceLimit;
	private boolean _lockout;
	private int _maxFailure;
	private long _lockoutDuration;
	private boolean _requireUnlock;
	private long _resetFailureCount;
	private transient ExpandoBridge _expandoBridge;
}