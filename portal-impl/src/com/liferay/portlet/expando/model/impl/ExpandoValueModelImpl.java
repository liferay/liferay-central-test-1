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

package com.liferay.portlet.expando.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.ExpandoValueSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoValueModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoValue</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.model.ExpandoValue
 * @see com.liferay.portlet.expando.model.ExpandoValueModel
 * @see com.liferay.portlet.expando.model.impl.ExpandoValueImpl
 *
 */
public class ExpandoValueModelImpl extends BaseModelImpl<ExpandoValue> {
	public static final String TABLE_NAME = "ExpandoValue";
	public static final Object[][] TABLE_COLUMNS = {
			{ "valueId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "tableId", new Integer(Types.BIGINT) },
			

			{ "columnId", new Integer(Types.BIGINT) },
			

			{ "rowId_", new Integer(Types.BIGINT) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "data_", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoValue (valueId LONG not null primary key,companyId LONG,tableId LONG,columnId LONG,rowId_ LONG,classNameId LONG,classPK LONG,data_ STRING null)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoValue";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.expando.model.ExpandoValue"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoValue"),
			true);

	public static ExpandoValue toModel(ExpandoValueSoap soapModel) {
		ExpandoValue model = new ExpandoValueImpl();

		model.setValueId(soapModel.getValueId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setTableId(soapModel.getTableId());
		model.setColumnId(soapModel.getColumnId());
		model.setRowId(soapModel.getRowId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setData(soapModel.getData());

		return model;
	}

	public static List<ExpandoValue> toModels(ExpandoValueSoap[] soapModels) {
		List<ExpandoValue> models = new ArrayList<ExpandoValue>(soapModels.length);

		for (ExpandoValueSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoValue"));

	public ExpandoValueModelImpl() {
	}

	public long getPrimaryKey() {
		return _valueId;
	}

	public void setPrimaryKey(long pk) {
		setValueId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_valueId);
	}

	public long getValueId() {
		return _valueId;
	}

	public void setValueId(long valueId) {
		if (valueId != _valueId) {
			_valueId = valueId;
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

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		if (tableId != _tableId) {
			_tableId = tableId;

			if (!_setOriginalTableId) {
				_setOriginalTableId = true;

				_originalTableId = tableId;
			}
		}
	}

	public long getOriginalTableId() {
		return _originalTableId;
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		if (columnId != _columnId) {
			_columnId = columnId;

			if (!_setOriginalColumnId) {
				_setOriginalColumnId = true;

				_originalColumnId = columnId;
			}
		}
	}

	public long getOriginalColumnId() {
		return _originalColumnId;
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		if (rowId != _rowId) {
			_rowId = rowId;

			if (!_setOriginalRowId) {
				_setOriginalRowId = true;

				_originalRowId = rowId;
			}
		}
	}

	public long getOriginalRowId() {
		return _originalRowId;
	}

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
		}
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public String getData() {
		return GetterUtil.getString(_data);
	}

	public void setData(String data) {
		if ((data != _data) || ((data != null) && !data.equals(_data))) {
			_data = data;
		}
	}

	public ExpandoValue toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoValue)this;
		}
		else {
			ExpandoValue model = new ExpandoValueImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setValueId(getValueId());
			model.setCompanyId(getCompanyId());
			model.setTableId(getTableId());
			model.setColumnId(getColumnId());
			model.setRowId(getRowId());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setData(HtmlUtil.escape(getData()));

			model = (ExpandoValue)Proxy.newProxyInstance(ExpandoValue.class.getClassLoader(),
					new Class[] { ExpandoValue.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoValueImpl clone = new ExpandoValueImpl();

		clone.setValueId(getValueId());
		clone.setCompanyId(getCompanyId());
		clone.setTableId(getTableId());
		clone.setColumnId(getColumnId());
		clone.setRowId(getRowId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setData(getData());

		return clone;
	}

	public int compareTo(ExpandoValue expandoValue) {
		int value = 0;

		if (getTableId() < expandoValue.getTableId()) {
			value = -1;
		}
		else if (getTableId() > expandoValue.getTableId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getRowId() < expandoValue.getRowId()) {
			value = -1;
		}
		else if (getRowId() > expandoValue.getRowId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (getColumnId() < expandoValue.getColumnId()) {
			value = -1;
		}
		else if (getColumnId() > expandoValue.getColumnId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ExpandoValue expandoValue = null;

		try {
			expandoValue = (ExpandoValue)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoValue.getPrimaryKey();

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

	private long _valueId;
	private long _companyId;
	private long _tableId;
	private long _originalTableId;
	private boolean _setOriginalTableId;
	private long _columnId;
	private long _originalColumnId;
	private boolean _setOriginalColumnId;
	private long _rowId;
	private long _originalRowId;
	private boolean _setOriginalRowId;
	private long _classNameId;
	private long _classPK;
	private String _data;
}