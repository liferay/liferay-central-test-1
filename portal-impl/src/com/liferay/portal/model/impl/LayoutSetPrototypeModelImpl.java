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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutSetPrototypeSoap;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutSetPrototypeModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>LayoutSetPrototype</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.LayoutSetPrototype
 * @see com.liferay.portal.model.LayoutSetPrototypeModel
 * @see com.liferay.portal.model.impl.LayoutSetPrototypeImpl
 *
 */
public class LayoutSetPrototypeModelImpl extends BaseModelImpl<LayoutSetPrototype> {
	public static final String TABLE_NAME = "LayoutSetPrototype";
	public static final Object[][] TABLE_COLUMNS = {
			{ "layoutSetPrototypeId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "title", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "settings_", new Integer(Types.VARCHAR) },
			

			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table LayoutSetPrototype (layoutSetPrototypeId LONG not null primary key,companyId LONG,name VARCHAR(75) null,title VARCHAR(75) null,description VARCHAR(75) null,settings_ STRING null,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table LayoutSetPrototype";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.LayoutSetPrototype"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.LayoutSetPrototype"),
			true);

	public static LayoutSetPrototype toModel(LayoutSetPrototypeSoap soapModel) {
		LayoutSetPrototype model = new LayoutSetPrototypeImpl();

		model.setLayoutSetPrototypeId(soapModel.getLayoutSetPrototypeId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());
		model.setDescription(soapModel.getDescription());
		model.setSettings(soapModel.getSettings());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<LayoutSetPrototype> toModels(
		LayoutSetPrototypeSoap[] soapModels) {
		List<LayoutSetPrototype> models = new ArrayList<LayoutSetPrototype>(soapModels.length);

		for (LayoutSetPrototypeSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.LayoutSetPrototype"));

	public LayoutSetPrototypeModelImpl() {
	}

	public long getPrimaryKey() {
		return _layoutSetPrototypeId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutSetPrototypeId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_layoutSetPrototypeId);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototypeId = layoutSetPrototypeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
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

	public String getSettings() {
		return GetterUtil.getString(_settings);
	}

	public void setSettings(String settings) {
		_settings = settings;
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

	public LayoutSetPrototype toEscapedModel() {
		if (isEscapedModel()) {
			return (LayoutSetPrototype)this;
		}
		else {
			LayoutSetPrototype model = new LayoutSetPrototypeImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setLayoutSetPrototypeId(getLayoutSetPrototypeId());
			model.setCompanyId(getCompanyId());
			model.setName(HtmlUtil.escape(getName()));
			model.setTitle(HtmlUtil.escape(getTitle()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setSettings(HtmlUtil.escape(getSettings()));
			model.setActive(getActive());

			model = (LayoutSetPrototype)Proxy.newProxyInstance(LayoutSetPrototype.class.getClassLoader(),
					new Class[] { LayoutSetPrototype.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(LayoutSetPrototype.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		LayoutSetPrototypeImpl clone = new LayoutSetPrototypeImpl();

		clone.setLayoutSetPrototypeId(getLayoutSetPrototypeId());
		clone.setCompanyId(getCompanyId());
		clone.setName(getName());
		clone.setTitle(getTitle());
		clone.setDescription(getDescription());
		clone.setSettings(getSettings());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(LayoutSetPrototype layoutSetPrototype) {
		long pk = layoutSetPrototype.getPrimaryKey();

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

		LayoutSetPrototype layoutSetPrototype = null;

		try {
			layoutSetPrototype = (LayoutSetPrototype)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = layoutSetPrototype.getPrimaryKey();

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

		sb.append("{layoutSetPrototypeId=");
		sb.append(getLayoutSetPrototypeId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", title=");
		sb.append(getTitle());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", settings=");
		sb.append(getSettings());
		sb.append(", active=");
		sb.append(getActive());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.LayoutSetPrototype");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>layoutSetPrototypeId</column-name><column-value><![CDATA[");
		sb.append(getLayoutSetPrototypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>title</column-name><column-value><![CDATA[");
		sb.append(getTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>settings</column-name><column-value><![CDATA[");
		sb.append(getSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>active</column-name><column-value><![CDATA[");
		sb.append(getActive());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _layoutSetPrototypeId;
	private long _companyId;
	private String _name;
	private String _title;
	private String _description;
	private String _settings;
	private boolean _active;
	private transient ExpandoBridge _expandoBridge;
}