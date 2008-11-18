/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.tags.model.TagsVocabulary;
import com.liferay.portlet.tags.model.TagsVocabularySoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TagsVocabularyModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>TagsVocabulary</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.model.TagsVocabulary
 * @see com.liferay.portlet.tags.model.TagsVocabularyModel
 * @see com.liferay.portlet.tags.model.impl.TagsVocabularyImpl
 *
 */
public class TagsVocabularyModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "TagsVocabulary";
	public static final Object[][] TABLE_COLUMNS = {
			{ "vocabularyId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "folksonomy", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table TagsVocabulary (vocabularyId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null,folksonomy BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table TagsVocabulary";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.tags.model.TagsVocabulary"),
			true);

	public static TagsVocabulary toModel(TagsVocabularySoap soapModel) {
		TagsVocabulary model = new TagsVocabularyImpl();

		model.setVocabularyId(soapModel.getVocabularyId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setFolksonomy(soapModel.getFolksonomy());

		return model;
	}

	public static List<TagsVocabulary> toModels(TagsVocabularySoap[] soapModels) {
		List<TagsVocabulary> models = new ArrayList<TagsVocabulary>(soapModels.length);

		for (TagsVocabularySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tags.model.TagsVocabulary"));

	public TagsVocabularyModelImpl() {
	}

	public long getPrimaryKey() {
		return _vocabularyId;
	}

	public void setPrimaryKey(long pk) {
		setVocabularyId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_vocabularyId);
	}

	public long getVocabularyId() {
		return _vocabularyId;
	}

	public void setVocabularyId(long vocabularyId) {
		if (vocabularyId != _vocabularyId) {
			_vocabularyId = vocabularyId;
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

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
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
			_description = description;
		}
	}

	public boolean getFolksonomy() {
		return _folksonomy;
	}

	public boolean isFolksonomy() {
		return _folksonomy;
	}

	public void setFolksonomy(boolean folksonomy) {
		if (folksonomy != _folksonomy) {
			_folksonomy = folksonomy;
		}
	}

	public TagsVocabulary toEscapedModel() {
		if (isEscapedModel()) {
			return (TagsVocabulary)this;
		}
		else {
			TagsVocabulary model = new TagsVocabularyImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setVocabularyId(getVocabularyId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setFolksonomy(getFolksonomy());

			model = (TagsVocabulary)Proxy.newProxyInstance(TagsVocabulary.class.getClassLoader(),
					new Class[] { TagsVocabulary.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(TagsVocabulary.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		TagsVocabularyImpl clone = new TagsVocabularyImpl();

		clone.setVocabularyId(getVocabularyId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setFolksonomy(getFolksonomy());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		TagsVocabularyImpl tagsVocabulary = (TagsVocabularyImpl)obj;

		int value = 0;

		value = getName().compareTo(tagsVocabulary.getName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		TagsVocabularyImpl tagsVocabulary = null;

		try {
			tagsVocabulary = (TagsVocabularyImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tagsVocabulary.getPrimaryKey();

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

	private long _vocabularyId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private boolean _folksonomy;
	private transient ExpandoBridge _expandoBridge;
}