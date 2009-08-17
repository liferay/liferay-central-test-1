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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalContentSearchSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalContentSearchModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalContentSearch table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchImpl
 * @see       com.liferay.portlet.journal.model.JournalContentSearch
 * @see       com.liferay.portlet.journal.model.JournalContentSearchModel
 * @generated
 */
public class JournalContentSearchModelImpl extends BaseModelImpl<JournalContentSearch> {
	public static final String TABLE_NAME = "JournalContentSearch";
	public static final Object[][] TABLE_COLUMNS = {
			{ "contentSearchId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "privateLayout", new Integer(Types.BOOLEAN) },
			

			{ "layoutId", new Integer(Types.BIGINT) },
			

			{ "portletId", new Integer(Types.VARCHAR) },
			

			{ "articleId", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalContentSearch (contentSearchId LONG not null primary key,groupId LONG,companyId LONG,privateLayout BOOLEAN,layoutId LONG,portletId VARCHAR(200) null,articleId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table JournalContentSearch";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.journal.model.JournalContentSearch"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalContentSearch"),
			true);

	public static JournalContentSearch toModel(
		JournalContentSearchSoap soapModel) {
		JournalContentSearch model = new JournalContentSearchImpl();

		model.setContentSearchId(soapModel.getContentSearchId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setPrivateLayout(soapModel.getPrivateLayout());
		model.setLayoutId(soapModel.getLayoutId());
		model.setPortletId(soapModel.getPortletId());
		model.setArticleId(soapModel.getArticleId());

		return model;
	}

	public static List<JournalContentSearch> toModels(
		JournalContentSearchSoap[] soapModels) {
		List<JournalContentSearch> models = new ArrayList<JournalContentSearch>(soapModels.length);

		for (JournalContentSearchSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalContentSearch"));

	public JournalContentSearchModelImpl() {
	}

	public long getPrimaryKey() {
		return _contentSearchId;
	}

	public void setPrimaryKey(long pk) {
		setContentSearchId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_contentSearchId);
	}

	public long getContentSearchId() {
		return _contentSearchId;
	}

	public void setContentSearchId(long contentSearchId) {
		_contentSearchId = contentSearchId;
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

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;

		if (!_setOriginalPrivateLayout) {
			_setOriginalPrivateLayout = true;

			_originalPrivateLayout = privateLayout;
		}
	}

	public boolean getOriginalPrivateLayout() {
		return _originalPrivateLayout;
	}

	public long getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(long layoutId) {
		_layoutId = layoutId;

		if (!_setOriginalLayoutId) {
			_setOriginalLayoutId = true;

			_originalLayoutId = layoutId;
		}
	}

	public long getOriginalLayoutId() {
		return _originalLayoutId;
	}

	public String getPortletId() {
		return GetterUtil.getString(_portletId);
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;

		if (_originalPortletId == null) {
			_originalPortletId = portletId;
		}
	}

	public String getOriginalPortletId() {
		return GetterUtil.getString(_originalPortletId);
	}

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;

		if (_originalArticleId == null) {
			_originalArticleId = articleId;
		}
	}

	public String getOriginalArticleId() {
		return GetterUtil.getString(_originalArticleId);
	}

	public JournalContentSearch toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalContentSearch)this;
		}
		else {
			JournalContentSearch model = new JournalContentSearchImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setContentSearchId(getContentSearchId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setPrivateLayout(getPrivateLayout());
			model.setLayoutId(getLayoutId());
			model.setPortletId(HtmlUtil.escape(getPortletId()));
			model.setArticleId(HtmlUtil.escape(getArticleId()));

			model = (JournalContentSearch)Proxy.newProxyInstance(JournalContentSearch.class.getClassLoader(),
					new Class[] { JournalContentSearch.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(JournalContentSearch.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		JournalContentSearchImpl clone = new JournalContentSearchImpl();

		clone.setContentSearchId(getContentSearchId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setPrivateLayout(getPrivateLayout());
		clone.setLayoutId(getLayoutId());
		clone.setPortletId(getPortletId());
		clone.setArticleId(getArticleId());

		return clone;
	}

	public int compareTo(JournalContentSearch journalContentSearch) {
		long pk = journalContentSearch.getPrimaryKey();

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

		JournalContentSearch journalContentSearch = null;

		try {
			journalContentSearch = (JournalContentSearch)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalContentSearch.getPrimaryKey();

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

		sb.append("{contentSearchId=");
		sb.append(getContentSearchId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", privateLayout=");
		sb.append(getPrivateLayout());
		sb.append(", layoutId=");
		sb.append(getLayoutId());
		sb.append(", portletId=");
		sb.append(getPortletId());
		sb.append(", articleId=");
		sb.append(getArticleId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.journal.model.JournalContentSearch");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>contentSearchId</column-name><column-value><![CDATA[");
		sb.append(getContentSearchId());
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
			"<column><column-name>privateLayout</column-name><column-value><![CDATA[");
		sb.append(getPrivateLayout());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>layoutId</column-name><column-value><![CDATA[");
		sb.append(getLayoutId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portletId</column-name><column-value><![CDATA[");
		sb.append(getPortletId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>articleId</column-name><column-value><![CDATA[");
		sb.append(getArticleId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _contentSearchId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _originalPrivateLayout;
	private boolean _setOriginalPrivateLayout;
	private long _layoutId;
	private long _originalLayoutId;
	private boolean _setOriginalLayoutId;
	private String _portletId;
	private String _originalPortletId;
	private String _articleId;
	private String _originalArticleId;
	private transient ExpandoBridge _expandoBridge;
}