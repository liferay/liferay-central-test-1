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
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoRowSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoRowModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ExpandoRow</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.model.ExpandoRow
 * @see com.liferay.portlet.expando.model.ExpandoRowModel
 * @see com.liferay.portlet.expando.model.impl.ExpandoRowImpl
 *
 */
public class ExpandoRowModelImpl extends BaseModelImpl<ExpandoRow> {
	public static final String TABLE_NAME = "ExpandoRow";
	public static final Object[][] TABLE_COLUMNS = {
			{ "rowId_", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "tableId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) }
		};
	public static final String TABLE_SQL_CREATE = "create table ExpandoRow (rowId_ LONG not null primary key,companyId LONG,tableId LONG,classPK LONG)";
	public static final String TABLE_SQL_DROP = "drop table ExpandoRow";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.expando.model.ExpandoRow"),
			true);

	public static ExpandoRow toModel(ExpandoRowSoap soapModel) {
		ExpandoRow model = new ExpandoRowImpl();

		model.setRowId(soapModel.getRowId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setTableId(soapModel.getTableId());
		model.setClassPK(soapModel.getClassPK());

		return model;
	}

	public static List<ExpandoRow> toModels(ExpandoRowSoap[] soapModels) {
		List<ExpandoRow> models = new ArrayList<ExpandoRow>(soapModels.length);

		for (ExpandoRowSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.expando.model.ExpandoRow"));

	public ExpandoRowModelImpl() {
	}

	public long getPrimaryKey() {
		return _rowId;
	}

	public void setPrimaryKey(long pk) {
		setRowId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_rowId);
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		if (rowId != _rowId) {
			_rowId = rowId;
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

	public ExpandoRow toEscapedModel() {
		if (isEscapedModel()) {
			return (ExpandoRow)this;
		}
		else {
			ExpandoRow model = new ExpandoRowImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setRowId(getRowId());
			model.setCompanyId(getCompanyId());
			model.setTableId(getTableId());
			model.setClassPK(getClassPK());

			model = (ExpandoRow)Proxy.newProxyInstance(ExpandoRow.class.getClassLoader(),
					new Class[] { ExpandoRow.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		ExpandoRowImpl clone = new ExpandoRowImpl();

		clone.setRowId(getRowId());
		clone.setCompanyId(getCompanyId());
		clone.setTableId(getTableId());
		clone.setClassPK(getClassPK());

		return clone;
	}

	public int compareTo(ExpandoRow expandoRow) {
		long pk = expandoRow.getPrimaryKey();

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

		ExpandoRow expandoRow = null;

		try {
			expandoRow = (ExpandoRow)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = expandoRow.getPrimaryKey();

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

	private long _rowId;
	private long _companyId;
	private long _tableId;
	private long _classPK;
}