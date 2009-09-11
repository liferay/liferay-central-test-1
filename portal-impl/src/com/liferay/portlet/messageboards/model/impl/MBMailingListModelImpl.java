/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.model.MBMailingListSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingListModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the MBMailingList table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListImpl
 * @see       com.liferay.portlet.messageboards.model.MBMailingList
 * @see       com.liferay.portlet.messageboards.model.MBMailingListModel
 * @generated
 */
public class MBMailingListModelImpl extends BaseModelImpl<MBMailingList> {
	public static final String TABLE_NAME = "MBMailingList";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "mailingListId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "categoryId", new Integer(Types.BIGINT) },
			{ "emailAddress", new Integer(Types.VARCHAR) },
			{ "inProtocol", new Integer(Types.VARCHAR) },
			{ "inServerName", new Integer(Types.VARCHAR) },
			{ "inServerPort", new Integer(Types.INTEGER) },
			{ "inUseSSL", new Integer(Types.BOOLEAN) },
			{ "inUserName", new Integer(Types.VARCHAR) },
			{ "inPassword", new Integer(Types.VARCHAR) },
			{ "inReadInterval", new Integer(Types.INTEGER) },
			{ "outEmailAddress", new Integer(Types.VARCHAR) },
			{ "outCustom", new Integer(Types.BOOLEAN) },
			{ "outServerName", new Integer(Types.VARCHAR) },
			{ "outServerPort", new Integer(Types.INTEGER) },
			{ "outUseSSL", new Integer(Types.BOOLEAN) },
			{ "outUserName", new Integer(Types.VARCHAR) },
			{ "outPassword", new Integer(Types.VARCHAR) },
			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table MBMailingList (uuid_ VARCHAR(75) null,mailingListId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,emailAddress VARCHAR(75) null,inProtocol VARCHAR(75) null,inServerName VARCHAR(75) null,inServerPort INTEGER,inUseSSL BOOLEAN,inUserName VARCHAR(75) null,inPassword VARCHAR(75) null,inReadInterval INTEGER,outEmailAddress VARCHAR(75) null,outCustom BOOLEAN,outServerName VARCHAR(75) null,outServerPort INTEGER,outUseSSL BOOLEAN,outUserName VARCHAR(75) null,outPassword VARCHAR(75) null,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table MBMailingList";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.messageboards.model.MBMailingList"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBMailingList"),
			true);

	public static MBMailingList toModel(MBMailingListSoap soapModel) {
		MBMailingList model = new MBMailingListImpl();

		model.setUuid(soapModel.getUuid());
		model.setMailingListId(soapModel.getMailingListId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCategoryId(soapModel.getCategoryId());
		model.setEmailAddress(soapModel.getEmailAddress());
		model.setInProtocol(soapModel.getInProtocol());
		model.setInServerName(soapModel.getInServerName());
		model.setInServerPort(soapModel.getInServerPort());
		model.setInUseSSL(soapModel.getInUseSSL());
		model.setInUserName(soapModel.getInUserName());
		model.setInPassword(soapModel.getInPassword());
		model.setInReadInterval(soapModel.getInReadInterval());
		model.setOutEmailAddress(soapModel.getOutEmailAddress());
		model.setOutCustom(soapModel.getOutCustom());
		model.setOutServerName(soapModel.getOutServerName());
		model.setOutServerPort(soapModel.getOutServerPort());
		model.setOutUseSSL(soapModel.getOutUseSSL());
		model.setOutUserName(soapModel.getOutUserName());
		model.setOutPassword(soapModel.getOutPassword());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<MBMailingList> toModels(MBMailingListSoap[] soapModels) {
		List<MBMailingList> models = new ArrayList<MBMailingList>(soapModels.length);

		for (MBMailingListSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBMailingList"));

	public MBMailingListModelImpl() {
	}

	public long getPrimaryKey() {
		return _mailingListId;
	}

	public void setPrimaryKey(long pk) {
		setMailingListId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_mailingListId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
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

	public long getMailingListId() {
		return _mailingListId;
	}

	public void setMailingListId(long mailingListId) {
		_mailingListId = mailingListId;
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
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
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

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;

		if (!_setOriginalCategoryId) {
			_setOriginalCategoryId = true;

			_originalCategoryId = categoryId;
		}
	}

	public long getOriginalCategoryId() {
		return _originalCategoryId;
	}

	public String getEmailAddress() {
		return GetterUtil.getString(_emailAddress);
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public String getInProtocol() {
		return GetterUtil.getString(_inProtocol);
	}

	public void setInProtocol(String inProtocol) {
		_inProtocol = inProtocol;
	}

	public String getInServerName() {
		return GetterUtil.getString(_inServerName);
	}

	public void setInServerName(String inServerName) {
		_inServerName = inServerName;
	}

	public int getInServerPort() {
		return _inServerPort;
	}

	public void setInServerPort(int inServerPort) {
		_inServerPort = inServerPort;
	}

	public boolean getInUseSSL() {
		return _inUseSSL;
	}

	public boolean isInUseSSL() {
		return _inUseSSL;
	}

	public void setInUseSSL(boolean inUseSSL) {
		_inUseSSL = inUseSSL;
	}

	public String getInUserName() {
		return GetterUtil.getString(_inUserName);
	}

	public void setInUserName(String inUserName) {
		_inUserName = inUserName;
	}

	public String getInPassword() {
		return GetterUtil.getString(_inPassword);
	}

	public void setInPassword(String inPassword) {
		_inPassword = inPassword;
	}

	public int getInReadInterval() {
		return _inReadInterval;
	}

	public void setInReadInterval(int inReadInterval) {
		_inReadInterval = inReadInterval;
	}

	public String getOutEmailAddress() {
		return GetterUtil.getString(_outEmailAddress);
	}

	public void setOutEmailAddress(String outEmailAddress) {
		_outEmailAddress = outEmailAddress;
	}

	public boolean getOutCustom() {
		return _outCustom;
	}

	public boolean isOutCustom() {
		return _outCustom;
	}

	public void setOutCustom(boolean outCustom) {
		_outCustom = outCustom;
	}

	public String getOutServerName() {
		return GetterUtil.getString(_outServerName);
	}

	public void setOutServerName(String outServerName) {
		_outServerName = outServerName;
	}

	public int getOutServerPort() {
		return _outServerPort;
	}

	public void setOutServerPort(int outServerPort) {
		_outServerPort = outServerPort;
	}

	public boolean getOutUseSSL() {
		return _outUseSSL;
	}

	public boolean isOutUseSSL() {
		return _outUseSSL;
	}

	public void setOutUseSSL(boolean outUseSSL) {
		_outUseSSL = outUseSSL;
	}

	public String getOutUserName() {
		return GetterUtil.getString(_outUserName);
	}

	public void setOutUserName(String outUserName) {
		_outUserName = outUserName;
	}

	public String getOutPassword() {
		return GetterUtil.getString(_outPassword);
	}

	public void setOutPassword(String outPassword) {
		_outPassword = outPassword;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public MBMailingList toEscapedModel() {
		if (isEscapedModel()) {
			return (MBMailingList)this;
		}
		else {
			MBMailingList model = new MBMailingListImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setMailingListId(getMailingListId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setCategoryId(getCategoryId());
			model.setEmailAddress(HtmlUtil.escape(getEmailAddress()));
			model.setInProtocol(HtmlUtil.escape(getInProtocol()));
			model.setInServerName(HtmlUtil.escape(getInServerName()));
			model.setInServerPort(getInServerPort());
			model.setInUseSSL(getInUseSSL());
			model.setInUserName(HtmlUtil.escape(getInUserName()));
			model.setInPassword(HtmlUtil.escape(getInPassword()));
			model.setInReadInterval(getInReadInterval());
			model.setOutEmailAddress(HtmlUtil.escape(getOutEmailAddress()));
			model.setOutCustom(getOutCustom());
			model.setOutServerName(HtmlUtil.escape(getOutServerName()));
			model.setOutServerPort(getOutServerPort());
			model.setOutUseSSL(getOutUseSSL());
			model.setOutUserName(HtmlUtil.escape(getOutUserName()));
			model.setOutPassword(HtmlUtil.escape(getOutPassword()));
			model.setActive(getActive());

			model = (MBMailingList)Proxy.newProxyInstance(MBMailingList.class.getClassLoader(),
					new Class[] { MBMailingList.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(MBMailingList.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		MBMailingListImpl clone = new MBMailingListImpl();

		clone.setUuid(getUuid());
		clone.setMailingListId(getMailingListId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setCategoryId(getCategoryId());
		clone.setEmailAddress(getEmailAddress());
		clone.setInProtocol(getInProtocol());
		clone.setInServerName(getInServerName());
		clone.setInServerPort(getInServerPort());
		clone.setInUseSSL(getInUseSSL());
		clone.setInUserName(getInUserName());
		clone.setInPassword(getInPassword());
		clone.setInReadInterval(getInReadInterval());
		clone.setOutEmailAddress(getOutEmailAddress());
		clone.setOutCustom(getOutCustom());
		clone.setOutServerName(getOutServerName());
		clone.setOutServerPort(getOutServerPort());
		clone.setOutUseSSL(getOutUseSSL());
		clone.setOutUserName(getOutUserName());
		clone.setOutPassword(getOutPassword());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(MBMailingList mbMailingList) {
		long pk = mbMailingList.getPrimaryKey();

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

		MBMailingList mbMailingList = null;

		try {
			mbMailingList = (MBMailingList)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbMailingList.getPrimaryKey();

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
		StringBuilder sb = new StringBuilder();

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", mailingListId=");
		sb.append(getMailingListId());
		sb.append(", groupId=");
		sb.append(getGroupId());
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
		sb.append(", categoryId=");
		sb.append(getCategoryId());
		sb.append(", emailAddress=");
		sb.append(getEmailAddress());
		sb.append(", inProtocol=");
		sb.append(getInProtocol());
		sb.append(", inServerName=");
		sb.append(getInServerName());
		sb.append(", inServerPort=");
		sb.append(getInServerPort());
		sb.append(", inUseSSL=");
		sb.append(getInUseSSL());
		sb.append(", inUserName=");
		sb.append(getInUserName());
		sb.append(", inPassword=");
		sb.append(getInPassword());
		sb.append(", inReadInterval=");
		sb.append(getInReadInterval());
		sb.append(", outEmailAddress=");
		sb.append(getOutEmailAddress());
		sb.append(", outCustom=");
		sb.append(getOutCustom());
		sb.append(", outServerName=");
		sb.append(getOutServerName());
		sb.append(", outServerPort=");
		sb.append(getOutServerPort());
		sb.append(", outUseSSL=");
		sb.append(getOutUseSSL());
		sb.append(", outUserName=");
		sb.append(getOutUserName());
		sb.append(", outPassword=");
		sb.append(getOutPassword());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.messageboards.model.MBMailingList");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mailingListId</column-name><column-value><![CDATA[");
		sb.append(getMailingListId());
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
			"<column><column-name>categoryId</column-name><column-value><![CDATA[");
		sb.append(getCategoryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>emailAddress</column-name><column-value><![CDATA[");
		sb.append(getEmailAddress());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inProtocol</column-name><column-value><![CDATA[");
		sb.append(getInProtocol());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inServerName</column-name><column-value><![CDATA[");
		sb.append(getInServerName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inServerPort</column-name><column-value><![CDATA[");
		sb.append(getInServerPort());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inUseSSL</column-name><column-value><![CDATA[");
		sb.append(getInUseSSL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inUserName</column-name><column-value><![CDATA[");
		sb.append(getInUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inPassword</column-name><column-value><![CDATA[");
		sb.append(getInPassword());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>inReadInterval</column-name><column-value><![CDATA[");
		sb.append(getInReadInterval());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outEmailAddress</column-name><column-value><![CDATA[");
		sb.append(getOutEmailAddress());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outCustom</column-name><column-value><![CDATA[");
		sb.append(getOutCustom());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outServerName</column-name><column-value><![CDATA[");
		sb.append(getOutServerName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outServerPort</column-name><column-value><![CDATA[");
		sb.append(getOutServerPort());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outUseSSL</column-name><column-value><![CDATA[");
		sb.append(getOutUseSSL());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outUserName</column-name><column-value><![CDATA[");
		sb.append(getOutUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>outPassword</column-name><column-value><![CDATA[");
		sb.append(getOutPassword());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _mailingListId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _categoryId;
	private long _originalCategoryId;
	private boolean _setOriginalCategoryId;
	private String _emailAddress;
	private String _inProtocol;
	private String _inServerName;
	private int _inServerPort;
	private boolean _inUseSSL;
	private String _inUserName;
	private String _inPassword;
	private int _inReadInterval;
	private String _outEmailAddress;
	private boolean _outCustom;
	private String _outServerName;
	private int _outServerPort;
	private boolean _outUseSSL;
	private String _outUserName;
	private String _outPassword;
	private boolean _active;
	private transient ExpandoBridge _expandoBridge;
}