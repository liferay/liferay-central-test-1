/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.shopping.model.ShoppingItemField;
import com.liferay.portlet.shopping.model.ShoppingItemFieldSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ShoppingItemFieldModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingItemField table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingItemFieldImpl
 * @see       com.liferay.portlet.shopping.model.ShoppingItemField
 * @see       com.liferay.portlet.shopping.model.ShoppingItemFieldModel
 * @generated
 */
public class ShoppingItemFieldModelImpl extends BaseModelImpl<ShoppingItemField> {
	public static final String TABLE_NAME = "ShoppingItemField";
	public static final Object[][] TABLE_COLUMNS = {
			{ "itemFieldId", new Integer(Types.BIGINT) },
			{ "itemId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "values_", new Integer(Types.VARCHAR) },
			{ "description", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingItemField (itemFieldId LONG not null primary key,itemId LONG,name VARCHAR(75) null,values_ STRING null,description STRING null)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingItemField";
	public static final String ORDER_BY_JPQL = " ORDER BY shoppingItemField.itemId ASC, shoppingItemField.name ASC";
	public static final String ORDER_BY_SQL = " ORDER BY ShoppingItemField.itemId ASC, ShoppingItemField.name ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.shopping.model.ShoppingItemField"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingItemField"),
			true);

	public static ShoppingItemField toModel(ShoppingItemFieldSoap soapModel) {
		ShoppingItemField model = new ShoppingItemFieldImpl();

		model.setItemFieldId(soapModel.getItemFieldId());
		model.setItemId(soapModel.getItemId());
		model.setName(soapModel.getName());
		model.setValues(soapModel.getValues());
		model.setDescription(soapModel.getDescription());

		return model;
	}

	public static List<ShoppingItemField> toModels(
		ShoppingItemFieldSoap[] soapModels) {
		List<ShoppingItemField> models = new ArrayList<ShoppingItemField>(soapModels.length);

		for (ShoppingItemFieldSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingItemField"));

	public ShoppingItemFieldModelImpl() {
	}

	public long getPrimaryKey() {
		return _itemFieldId;
	}

	public void setPrimaryKey(long pk) {
		setItemFieldId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_itemFieldId);
	}

	public long getItemFieldId() {
		return _itemFieldId;
	}

	public void setItemFieldId(long itemFieldId) {
		_itemFieldId = itemFieldId;
	}

	public long getItemId() {
		return _itemId;
	}

	public void setItemId(long itemId) {
		_itemId = itemId;
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getValues() {
		return GetterUtil.getString(_values);
	}

	public void setValues(String values) {
		_values = values;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public ShoppingItemField toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingItemField)this;
		}
		else {
			ShoppingItemField model = new ShoppingItemFieldImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setItemFieldId(getItemFieldId());
			model.setItemId(getItemId());
			model.setName(HtmlUtil.escape(getName()));
			model.setValues(HtmlUtil.escape(getValues()));
			model.setDescription(HtmlUtil.escape(getDescription()));

			model = (ShoppingItemField)Proxy.newProxyInstance(ShoppingItemField.class.getClassLoader(),
					new Class[] { ShoppingItemField.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(ShoppingItemField.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ShoppingItemFieldImpl clone = new ShoppingItemFieldImpl();

		clone.setItemFieldId(getItemFieldId());
		clone.setItemId(getItemId());
		clone.setName(getName());
		clone.setValues(getValues());
		clone.setDescription(getDescription());

		return clone;
	}

	public int compareTo(ShoppingItemField shoppingItemField) {
		int value = 0;

		if (getItemId() < shoppingItemField.getItemId()) {
			value = -1;
		}
		else if (getItemId() > shoppingItemField.getItemId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getName().toLowerCase()
					.compareTo(shoppingItemField.getName().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingItemField shoppingItemField = null;

		try {
			shoppingItemField = (ShoppingItemField)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = shoppingItemField.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{itemFieldId=");
		sb.append(getItemFieldId());
		sb.append(", itemId=");
		sb.append(getItemId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", values=");
		sb.append(getValues());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.shopping.model.ShoppingItemField");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>itemFieldId</column-name><column-value><![CDATA[");
		sb.append(getItemFieldId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>itemId</column-name><column-value><![CDATA[");
		sb.append(getItemId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>values</column-name><column-value><![CDATA[");
		sb.append(getValues());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _itemFieldId;
	private long _itemId;
	private String _name;
	private String _values;
	private String _description;
	private transient ExpandoBridge _expandoBridge;
}