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
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.ListTypeSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ListTypeModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ListType</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.model.ListType
 * @see com.liferay.portal.model.ListTypeModel
 * @see com.liferay.portal.model.impl.ListTypeImpl
 *
 */
public class ListTypeModelImpl extends BaseModelImpl<ListType> {
	public static final String TABLE_NAME = "ListType";
	public static final Object[][] TABLE_COLUMNS = {
			{ "listTypeId", new Integer(Types.INTEGER) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ListType (listTypeId INTEGER not null primary key,name VARCHAR(75) null,type_ VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table ListType";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.ListType"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.ListType"),
			true);

	public static ListType toModel(ListTypeSoap soapModel) {
		ListType model = new ListTypeImpl();

		model.setListTypeId(soapModel.getListTypeId());
		model.setName(soapModel.getName());
		model.setType(soapModel.getType());

		return model;
	}

	public static List<ListType> toModels(ListTypeSoap[] soapModels) {
		List<ListType> models = new ArrayList<ListType>(soapModels.length);

		for (ListTypeSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.ListType"));

	public ListTypeModelImpl() {
	}

	public int getPrimaryKey() {
		return _listTypeId;
	}

	public void setPrimaryKey(int pk) {
		setListTypeId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Integer(_listTypeId);
	}

	public int getListTypeId() {
		return _listTypeId;
	}

	public void setListTypeId(int listTypeId) {
		_listTypeId = listTypeId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public ListType toEscapedModel() {
		if (isEscapedModel()) {
			return (ListType)this;
		}
		else {
			ListType model = new ListTypeImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setListTypeId(getListTypeId());
			model.setName(HtmlUtil.escape(getName()));
			model.setType(HtmlUtil.escape(getType()));

			model = (ListType)Proxy.newProxyInstance(ListType.class.getClassLoader(),
					new Class[] { ListType.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ListTypeImpl clone = new ListTypeImpl();

		clone.setListTypeId(getListTypeId());
		clone.setName(getName());
		clone.setType(getType());

		return clone;
	}

	public int compareTo(ListType listType) {
		int value = 0;

		value = getName().toLowerCase()
					.compareTo(listType.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ListType listType = null;

		try {
			listType = (ListType)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		int pk = listType.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{listTypeId=");
		sb.append(getListTypeId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", type=");
		sb.append(getType());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.ListType");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>listTypeId</column-name><column-value><![CDATA[");
		sb.append(getListTypeId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private int _listTypeId;
	private String _name;
	private String _type;
}