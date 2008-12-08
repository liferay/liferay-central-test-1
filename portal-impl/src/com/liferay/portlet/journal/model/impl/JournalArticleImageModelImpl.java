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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalArticleImageSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalArticleImageModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>JournalArticleImage</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.model.JournalArticleImage
 * @see com.liferay.portlet.journal.model.JournalArticleImageModel
 * @see com.liferay.portlet.journal.model.impl.JournalArticleImageImpl
 *
 */
public class JournalArticleImageModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "JournalArticleImage";
	public static final Object[][] TABLE_COLUMNS = {
			{ "articleImageId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "articleId", new Integer(Types.VARCHAR) },
			

			{ "version", new Integer(Types.DOUBLE) },
			

			{ "elInstanceId", new Integer(Types.VARCHAR) },
			

			{ "elName", new Integer(Types.VARCHAR) },
			

			{ "languageId", new Integer(Types.VARCHAR) },
			

			{ "tempImage", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalArticleImage (articleImageId LONG not null primary key,groupId LONG,articleId VARCHAR(75) null,version DOUBLE,elInstanceId VARCHAR(75) null,elName VARCHAR(75) null,languageId VARCHAR(75) null,tempImage BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table JournalArticleImage";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalArticleImage"),
			true);

	public static JournalArticleImage toModel(JournalArticleImageSoap soapModel) {
		JournalArticleImage model = new JournalArticleImageImpl();

		model.setArticleImageId(soapModel.getArticleImageId());
		model.setGroupId(soapModel.getGroupId());
		model.setArticleId(soapModel.getArticleId());
		model.setVersion(soapModel.getVersion());
		model.setElInstanceId(soapModel.getElInstanceId());
		model.setElName(soapModel.getElName());
		model.setLanguageId(soapModel.getLanguageId());
		model.setTempImage(soapModel.getTempImage());

		return model;
	}

	public static List<JournalArticleImage> toModels(
		JournalArticleImageSoap[] soapModels) {
		List<JournalArticleImage> models = new ArrayList<JournalArticleImage>(soapModels.length);

		for (JournalArticleImageSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalArticleImage"));

	public JournalArticleImageModelImpl() {
	}

	public long getPrimaryKey() {
		return _articleImageId;
	}

	public void setPrimaryKey(long pk) {
		setArticleImageId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_articleImageId);
	}

	public long getArticleImageId() {
		return _articleImageId;
	}

	public void setArticleImageId(long articleImageId) {
		if (articleImageId != _articleImageId) {
			_articleImageId = articleImageId;
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

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		if (((articleId == null) && (_articleId != null)) ||
				((articleId != null) && (_articleId == null)) ||
				((articleId != null) && (_articleId != null) &&
				!articleId.equals(_articleId))) {
			_articleId = articleId;
		}
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		if (version != _version) {
			_version = version;
		}
	}

	public String getElInstanceId() {
		return GetterUtil.getString(_elInstanceId);
	}

	public void setElInstanceId(String elInstanceId) {
		if (((elInstanceId == null) && (_elInstanceId != null)) ||
				((elInstanceId != null) && (_elInstanceId == null)) ||
				((elInstanceId != null) && (_elInstanceId != null) &&
				!elInstanceId.equals(_elInstanceId))) {
			_elInstanceId = elInstanceId;
		}
	}

	public String getElName() {
		return GetterUtil.getString(_elName);
	}

	public void setElName(String elName) {
		if (((elName == null) && (_elName != null)) ||
				((elName != null) && (_elName == null)) ||
				((elName != null) && (_elName != null) &&
				!elName.equals(_elName))) {
			_elName = elName;
		}
	}

	public String getLanguageId() {
		return GetterUtil.getString(_languageId);
	}

	public void setLanguageId(String languageId) {
		if (((languageId == null) && (_languageId != null)) ||
				((languageId != null) && (_languageId == null)) ||
				((languageId != null) && (_languageId != null) &&
				!languageId.equals(_languageId))) {
			_languageId = languageId;
		}
	}

	public boolean getTempImage() {
		return _tempImage;
	}

	public boolean isTempImage() {
		return _tempImage;
	}

	public void setTempImage(boolean tempImage) {
		if (tempImage != _tempImage) {
			_tempImage = tempImage;
		}
	}

	public JournalArticleImage toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalArticleImage)this;
		}
		else {
			JournalArticleImage model = new JournalArticleImageImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setArticleImageId(getArticleImageId());
			model.setGroupId(getGroupId());
			model.setArticleId(HtmlUtil.escape(getArticleId()));
			model.setVersion(getVersion());
			model.setElInstanceId(HtmlUtil.escape(getElInstanceId()));
			model.setElName(HtmlUtil.escape(getElName()));
			model.setLanguageId(HtmlUtil.escape(getLanguageId()));
			model.setTempImage(getTempImage());

			model = (JournalArticleImage)Proxy.newProxyInstance(JournalArticleImage.class.getClassLoader(),
					new Class[] { JournalArticleImage.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(JournalArticleImage.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		JournalArticleImageImpl clone = new JournalArticleImageImpl();

		clone.setArticleImageId(getArticleImageId());
		clone.setGroupId(getGroupId());
		clone.setArticleId(getArticleId());
		clone.setVersion(getVersion());
		clone.setElInstanceId(getElInstanceId());
		clone.setElName(getElName());
		clone.setLanguageId(getLanguageId());
		clone.setTempImage(getTempImage());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalArticleImageImpl journalArticleImage = (JournalArticleImageImpl)obj;

		long pk = journalArticleImage.getPrimaryKey();

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

		JournalArticleImageImpl journalArticleImage = null;

		try {
			journalArticleImage = (JournalArticleImageImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalArticleImage.getPrimaryKey();

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

	private long _articleImageId;
	private long _groupId;
	private String _articleId;
	private double _version;
	private String _elInstanceId;
	private String _elName;
	private String _languageId;
	private boolean _tempImage;
	private transient ExpandoBridge _expandoBridge;
}