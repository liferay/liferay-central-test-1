/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.XSSUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;

/**
 * <a href="JournalTemplateModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>JournalTemplate</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.model.JournalTemplate
 * @see com.liferay.portlet.journal.service.model.JournalTemplateModel
 * @see com.liferay.portlet.journal.service.model.impl.JournalTemplateImpl
 *
 */
public class JournalTemplateModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "JournalTemplate";
	public static Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			{ "id_", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "templateId", new Integer(Types.VARCHAR) },
			{ "structureId", new Integer(Types.VARCHAR) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) },
			{ "xsl", new Integer(Types.CLOB) },
			{ "langType", new Integer(Types.VARCHAR) },
			{ "smallImage", new Integer(Types.BOOLEAN) },
			{ "smallImageId", new Integer(Types.BIGINT) },
			{ "smallImageURL", new Integer(Types.VARCHAR) }
		};
	public static String TABLE_SQL_CREATE = "create table JournalTemplate (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,templateId VARCHAR(75) null,structureId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,xsl TEXT null,langType VARCHAR(75) null,smallImage BOOLEAN,smallImageId LONG,smallImageURL VARCHAR(75) null)";
	public static String TABLE_SQL_DROP = "drop table JournalTemplate";
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_UUID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.uuid"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_TEMPLATEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.templateId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_STRUCTUREID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.structureId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_DESCRIPTION = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.description"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_XSL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.xsl"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LANGTYPE = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.langType"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SMALLIMAGEURL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalTemplate.smallImageURL"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalTemplateModel"));

	public JournalTemplateModelImpl() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_id);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if (((uuid == null) && (_uuid != null)) ||
				((uuid != null) && (_uuid == null)) ||
				((uuid != null) && (_uuid != null) && !uuid.equals(_uuid))) {
			if (!XSS_ALLOW_UUID) {
				uuid = XSSUtil.strip(uuid);
			}

			_uuid = uuid;
		}
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		if (id != _id) {
			_id = id;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
		}
	}

	public String getTemplateId() {
		return GetterUtil.getString(_templateId);
	}

	public void setTemplateId(String templateId) {
		if (((templateId == null) && (_templateId != null)) ||
				((templateId != null) && (_templateId == null)) ||
				((templateId != null) && (_templateId != null) &&
				!templateId.equals(_templateId))) {
			if (!XSS_ALLOW_TEMPLATEID) {
				templateId = XSSUtil.strip(templateId);
			}

			_templateId = templateId;
		}
	}

	public String getStructureId() {
		return GetterUtil.getString(_structureId);
	}

	public void setStructureId(String structureId) {
		if (((structureId == null) && (_structureId != null)) ||
				((structureId != null) && (_structureId == null)) ||
				((structureId != null) && (_structureId != null) &&
				!structureId.equals(_structureId))) {
			if (!XSS_ALLOW_STRUCTUREID) {
				structureId = XSSUtil.strip(structureId);
			}

			_structureId = structureId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		if (((description == null) && (_description != null)) ||
				((description != null) && (_description == null)) ||
				((description != null) && (_description != null) &&
				!description.equals(_description))) {
			if (!XSS_ALLOW_DESCRIPTION) {
				description = XSSUtil.strip(description);
			}

			_description = description;
		}
	}

	public String getXsl() {
		return GetterUtil.getString(_xsl);
	}

	public void setXsl(String xsl) {
		if (((xsl == null) && (_xsl != null)) ||
				((xsl != null) && (_xsl == null)) ||
				((xsl != null) && (_xsl != null) && !xsl.equals(_xsl))) {
			if (!XSS_ALLOW_XSL) {
				xsl = XSSUtil.strip(xsl);
			}

			_xsl = xsl;
		}
	}

	public String getLangType() {
		return GetterUtil.getString(_langType);
	}

	public void setLangType(String langType) {
		if (((langType == null) && (_langType != null)) ||
				((langType != null) && (_langType == null)) ||
				((langType != null) && (_langType != null) &&
				!langType.equals(_langType))) {
			if (!XSS_ALLOW_LANGTYPE) {
				langType = XSSUtil.strip(langType);
			}

			_langType = langType;
		}
	}

	public boolean getSmallImage() {
		return _smallImage;
	}

	public boolean isSmallImage() {
		return _smallImage;
	}

	public void setSmallImage(boolean smallImage) {
		if (smallImage != _smallImage) {
			_smallImage = smallImage;
		}
	}

	public long getSmallImageId() {
		return _smallImageId;
	}

	public void setSmallImageId(long smallImageId) {
		if (smallImageId != _smallImageId) {
			_smallImageId = smallImageId;
		}
	}

	public String getSmallImageURL() {
		return GetterUtil.getString(_smallImageURL);
	}

	public void setSmallImageURL(String smallImageURL) {
		if (((smallImageURL == null) && (_smallImageURL != null)) ||
				((smallImageURL != null) && (_smallImageURL == null)) ||
				((smallImageURL != null) && (_smallImageURL != null) &&
				!smallImageURL.equals(_smallImageURL))) {
			if (!XSS_ALLOW_SMALLIMAGEURL) {
				smallImageURL = XSSUtil.strip(smallImageURL);
			}

			_smallImageURL = smallImageURL;
		}
	}

	public Object clone() {
		JournalTemplateImpl clone = new JournalTemplateImpl();
		clone.setUuid(getUuid());
		clone.setId(getId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTemplateId(getTemplateId());
		clone.setStructureId(getStructureId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setXsl(getXsl());
		clone.setLangType(getLangType());
		clone.setSmallImage(getSmallImage());
		clone.setSmallImageId(getSmallImageId());
		clone.setSmallImageURL(getSmallImageURL());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalTemplateImpl journalTemplate = (JournalTemplateImpl)obj;
		int value = 0;
		value = getTemplateId().compareTo(journalTemplate.getTemplateId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalTemplateImpl journalTemplate = null;

		try {
			journalTemplate = (JournalTemplateImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalTemplate.getPrimaryKey();

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
	private long _id;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _templateId;
	private String _structureId;
	private String _name;
	private String _description;
	private String _xsl;
	private String _langType;
	private boolean _smallImage;
	private long _smallImageId;
	private String _smallImageURL;
}