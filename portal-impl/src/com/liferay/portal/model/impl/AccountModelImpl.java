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
import com.liferay.portal.model.Account;
import com.liferay.portal.model.AccountModel;
import com.liferay.portal.model.AccountSoap;
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
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Account_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AccountImpl
 * @see       com.liferay.portal.model.Account
 * @see       com.liferay.portal.model.AccountModel
 * @generated
 */
public class AccountModelImpl extends BaseModelImpl<Account>
	implements AccountModel {
	public static final String TABLE_NAME = "Account_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "accountId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "parentAccountId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "legalName", new Integer(Types.VARCHAR) },
			{ "legalId", new Integer(Types.VARCHAR) },
			{ "legalType", new Integer(Types.VARCHAR) },
			{ "sicCode", new Integer(Types.VARCHAR) },
			{ "tickerSymbol", new Integer(Types.VARCHAR) },
			{ "industry", new Integer(Types.VARCHAR) },
			{ "type_", new Integer(Types.VARCHAR) },
			{ "size_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Account_ (accountId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentAccountId LONG,name VARCHAR(75) null,legalName VARCHAR(75) null,legalId VARCHAR(75) null,legalType VARCHAR(75) null,sicCode VARCHAR(75) null,tickerSymbol VARCHAR(75) null,industry VARCHAR(75) null,type_ VARCHAR(75) null,size_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Account_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Account"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Account"),
			true);

	public static Account toModel(AccountSoap soapModel) {
		Account model = new AccountImpl();

		model.setAccountId(soapModel.getAccountId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setParentAccountId(soapModel.getParentAccountId());
		model.setName(soapModel.getName());
		model.setLegalName(soapModel.getLegalName());
		model.setLegalId(soapModel.getLegalId());
		model.setLegalType(soapModel.getLegalType());
		model.setSicCode(soapModel.getSicCode());
		model.setTickerSymbol(soapModel.getTickerSymbol());
		model.setIndustry(soapModel.getIndustry());
		model.setType(soapModel.getType());
		model.setSize(soapModel.getSize());

		return model;
	}

	public static List<Account> toModels(AccountSoap[] soapModels) {
		List<Account> models = new ArrayList<Account>(soapModels.length);

		for (AccountSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Account"));

	public AccountModelImpl() {
	}

	public long getPrimaryKey() {
		return _accountId;
	}

	public void setPrimaryKey(long pk) {
		setAccountId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_accountId);
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getParentAccountId() {
		return _parentAccountId;
	}

	public void setParentAccountId(long parentAccountId) {
		_parentAccountId = parentAccountId;
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
	}

	public String getLegalName() {
		if (_legalName == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalName;
		}
	}

	public void setLegalName(String legalName) {
		_legalName = legalName;
	}

	public String getLegalId() {
		if (_legalId == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalId;
		}
	}

	public void setLegalId(String legalId) {
		_legalId = legalId;
	}

	public String getLegalType() {
		if (_legalType == null) {
			return StringPool.BLANK;
		}
		else {
			return _legalType;
		}
	}

	public void setLegalType(String legalType) {
		_legalType = legalType;
	}

	public String getSicCode() {
		if (_sicCode == null) {
			return StringPool.BLANK;
		}
		else {
			return _sicCode;
		}
	}

	public void setSicCode(String sicCode) {
		_sicCode = sicCode;
	}

	public String getTickerSymbol() {
		if (_tickerSymbol == null) {
			return StringPool.BLANK;
		}
		else {
			return _tickerSymbol;
		}
	}

	public void setTickerSymbol(String tickerSymbol) {
		_tickerSymbol = tickerSymbol;
	}

	public String getIndustry() {
		if (_industry == null) {
			return StringPool.BLANK;
		}
		else {
			return _industry;
		}
	}

	public void setIndustry(String industry) {
		_industry = industry;
	}

	public String getType() {
		if (_type == null) {
			return StringPool.BLANK;
		}
		else {
			return _type;
		}
	}

	public void setType(String type) {
		_type = type;
	}

	public String getSize() {
		if (_size == null) {
			return StringPool.BLANK;
		}
		else {
			return _size;
		}
	}

	public void setSize(String size) {
		_size = size;
	}

	public Account toEscapedModel() {
		if (isEscapedModel()) {
			return (Account)this;
		}
		else {
			return (Account)Proxy.newProxyInstance(Account.class.getClassLoader(),
				new Class[] { Account.class }, new AutoEscapeBeanHandler(this));
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					Account.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		AccountImpl clone = new AccountImpl();

		clone.setAccountId(getAccountId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setParentAccountId(getParentAccountId());
		clone.setName(getName());
		clone.setLegalName(getLegalName());
		clone.setLegalId(getLegalId());
		clone.setLegalType(getLegalType());
		clone.setSicCode(getSicCode());
		clone.setTickerSymbol(getTickerSymbol());
		clone.setIndustry(getIndustry());
		clone.setType(getType());
		clone.setSize(getSize());

		return clone;
	}

	public int compareTo(Account account) {
		long pk = account.getPrimaryKey();

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

		Account account = null;

		try {
			account = (Account)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = account.getPrimaryKey();

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
		StringBundler sb = new StringBundler(33);

		sb.append("{accountId=");
		sb.append(getAccountId());
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
		sb.append(", parentAccountId=");
		sb.append(getParentAccountId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", legalName=");
		sb.append(getLegalName());
		sb.append(", legalId=");
		sb.append(getLegalId());
		sb.append(", legalType=");
		sb.append(getLegalType());
		sb.append(", sicCode=");
		sb.append(getSicCode());
		sb.append(", tickerSymbol=");
		sb.append(getTickerSymbol());
		sb.append(", industry=");
		sb.append(getIndustry());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", size=");
		sb.append(getSize());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Account");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>accountId</column-name><column-value><![CDATA[");
		sb.append(getAccountId());
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
			"<column><column-name>parentAccountId</column-name><column-value><![CDATA[");
		sb.append(getParentAccountId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalName</column-name><column-value><![CDATA[");
		sb.append(getLegalName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalId</column-name><column-value><![CDATA[");
		sb.append(getLegalId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>legalType</column-name><column-value><![CDATA[");
		sb.append(getLegalType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sicCode</column-name><column-value><![CDATA[");
		sb.append(getSicCode());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>tickerSymbol</column-name><column-value><![CDATA[");
		sb.append(getTickerSymbol());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>industry</column-name><column-value><![CDATA[");
		sb.append(getIndustry());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>size</column-name><column-value><![CDATA[");
		sb.append(getSize());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _accountId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentAccountId;
	private String _name;
	private String _legalName;
	private String _legalId;
	private String _legalType;
	private String _sicCode;
	private String _tickerSymbol;
	private String _industry;
	private String _type;
	private String _size;
	private transient ExpandoBridge _expandoBridge;
}