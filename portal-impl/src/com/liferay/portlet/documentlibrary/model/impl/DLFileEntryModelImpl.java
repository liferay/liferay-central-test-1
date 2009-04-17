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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntrySoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileEntryModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>DLFileEntry</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.model.DLFileEntry
 * @see com.liferay.portlet.documentlibrary.model.DLFileEntryModel
 * @see com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl
 *
 */
public class DLFileEntryModelImpl extends BaseModelImpl<DLFileEntry> {
	public static final String TABLE_NAME = "DLFileEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "fileEntryId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "versionUserId", new Integer(Types.BIGINT) },
			

			{ "versionUserName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "folderId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "version", new Integer(Types.DOUBLE) },
			

			{ "size_", new Integer(Types.INTEGER) },
			

			{ "readCount", new Integer(Types.INTEGER) },
			

			{ "extraSettings", new Integer(Types.CLOB) }
		};
	public static final String TABLE_SQL_CREATE = "create table DLFileEntry (uuid_ VARCHAR(75) null,fileEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,folderId LONG,name VARCHAR(255) null,title VARCHAR(255) null,description STRING null,version DOUBLE,size_ INTEGER,readCount INTEGER,extraSettings TEXT null)";
	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.documentlibrary.model.DLFileEntry"),
			true);

	public static DLFileEntry toModel(DLFileEntrySoap soapModel) {
		DLFileEntry model = new DLFileEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setFileEntryId(soapModel.getFileEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setVersionUserId(soapModel.getVersionUserId());
		model.setVersionUserName(soapModel.getVersionUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFolderId(soapModel.getFolderId());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setVersion(soapModel.getVersion());
		model.setSize(soapModel.getSize());
		model.setReadCount(soapModel.getReadCount());
		model.setExtraSettings(soapModel.getExtraSettings());

		return model;
	}

	public static List<DLFileEntry> toModels(DLFileEntrySoap[] soapModels) {
		List<DLFileEntry> models = new ArrayList<DLFileEntry>(soapModels.length);

		for (DLFileEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.documentlibrary.model.DLFileEntry"));

	public DLFileEntryModelImpl() {
	}

	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public void setPrimaryKey(long pk) {
		setFileEntryId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_fileEntryId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
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

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public long getVersionUserId() {
		return _versionUserId;
	}

	public void setVersionUserId(long versionUserId) {
		_versionUserId = versionUserId;
	}

	public String getVersionUserName() {
		return GetterUtil.getString(_versionUserName);
	}

	public void setVersionUserName(String versionUserName) {
		_versionUserName = versionUserName;
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

	public long getFolderId() {
		return _folderId;
	}

	public void setFolderId(long folderId) {
		_folderId = folderId;

		if (!_setOriginalFolderId) {
			_setOriginalFolderId = true;

			_originalFolderId = folderId;
		}
	}

	public long getOriginalFolderId() {
		return _originalFolderId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
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

	public String getTitle() {
		return GetterUtil.getString(_title);
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	public int getSize() {
		return _size;
	}

	public void setSize(int size) {
		_size = size;
	}

	public int getReadCount() {
		return _readCount;
	}

	public void setReadCount(int readCount) {
		_readCount = readCount;
	}

	public String getExtraSettings() {
		return GetterUtil.getString(_extraSettings);
	}

	public void setExtraSettings(String extraSettings) {
		_extraSettings = extraSettings;
	}

	public DLFileEntry toEscapedModel() {
		if (isEscapedModel()) {
			return (DLFileEntry)this;
		}
		else {
			DLFileEntry model = new DLFileEntryImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setFileEntryId(getFileEntryId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setVersionUserId(getVersionUserId());
			model.setVersionUserName(HtmlUtil.escape(getVersionUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFolderId(getFolderId());
			model.setName(HtmlUtil.escape(getName()));
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setVersion(getVersion());
			model.setSize(getSize());
			model.setReadCount(getReadCount());
			model.setExtraSettings(HtmlUtil.escape(getExtraSettings()));

			model = (DLFileEntry)Proxy.newProxyInstance(DLFileEntry.class.getClassLoader(),
					new Class[] { DLFileEntry.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(DLFileEntry.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		DLFileEntryImpl clone = new DLFileEntryImpl();

		clone.setUuid(getUuid());
		clone.setFileEntryId(getFileEntryId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setVersionUserId(getVersionUserId());
		clone.setVersionUserName(getVersionUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFolderId(getFolderId());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setVersion(getVersion());
		clone.setSize(getSize());
		clone.setReadCount(getReadCount());
		clone.setExtraSettings(getExtraSettings());

		return clone;
	}

	public int compareTo(DLFileEntry dlFileEntry) {
		int value = 0;

		if (getFolderId() < dlFileEntry.getFolderId()) {
			value = -1;
		}
		else if (getFolderId() > dlFileEntry.getFolderId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().compareTo(dlFileEntry.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		DLFileEntry dlFileEntry = null;

		try {
			dlFileEntry = (DLFileEntry)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = dlFileEntry.getPrimaryKey();

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

	private String _uuid;
	private long _fileEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private long _versionUserId;
	private String _versionUserName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _folderId;
	private long _originalFolderId;
	private boolean _setOriginalFolderId;
	private String _name;
	private String _originalName;
	private String _title;
	private String _description;
	private double _version;
	private int _size;
	private int _readCount;
	private String _extraSettings;
	private transient ExpandoBridge _expandoBridge;
}