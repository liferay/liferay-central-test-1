/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.service.base.ExpandoValueLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoValueLocalServiceImpl
	extends ExpandoValueLocalServiceBaseImpl {

	public ExpandoValue addValue(
			long classNameId, long tableId, long columnId, long classPK,
			String data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		ExpandoRow row = expandoRowPersistence.fetchByT_C(tableId, classPK);

		if (row == null) {
			long rowId = counterLocalService.increment();

			row = expandoRowPersistence.create(rowId);

			row.setCompanyId(table.getCompanyId());
			row.setTableId(tableId);
			row.setClassPK(classPK);

			expandoRowPersistence.update(row, false);
		}

		ExpandoValue value = expandoValuePersistence.fetchByC_R(
			columnId, row.getRowId());

		if (value == null) {
			long valueId = counterLocalService.increment();

			value = expandoValuePersistence.create(valueId);

			value.setCompanyId(table.getCompanyId());
			value.setTableId(tableId);
			value.setColumnId(columnId);
			value.setRowId(row.getRowId());
			value.setClassNameId(classNameId);
			value.setClassPK(classPK);
		}

		value.setData(data);

		expandoValuePersistence.update(value, false);

		return value;
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, boolean data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setBoolean(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, boolean[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setBooleanArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Date data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setDate(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Date[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setDateArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, double data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setDouble(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, double[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setDoubleArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, float data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setFloat(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, float[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setFloatArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, int data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setInteger(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, int[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setIntegerArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, long data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setLong(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, long[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setLongArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, Object data)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		int type = column.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Boolean)data).booleanValue());
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(boolean[])data);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(Date)data);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(Date[])data);
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Double)data).doubleValue());
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(double[])data);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Float)data).floatValue());
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(float[])data);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Integer)data).intValue());
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(int[])data);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Long)data).longValue());
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(long[])data);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				((Short)data).shortValue());
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(short[])data);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(String[])data);
		}
		else {
			return expandoValueLocalService.addValue(
				companyId, className, tableName, columnName, classPK,
				(String)data);
		}
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, short data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setShort(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, short[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setShortArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, String data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setString(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			long companyId, String className, String tableName,
			String columnName, long classPK, String[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			companyId, className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setCompanyId(table.getCompanyId());
		value.setColumnId(column.getColumnId());
		value.setStringArray(data);

		return expandoValueLocalService.addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             boolean[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             boolean[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, Date[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, Date[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             double[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             double[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             float[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             float[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, int[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, int[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, long[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, long[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long, Object)}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Object data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             short[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             short[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             String[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	/**
	 * @deprecated {@link #addValue(long, String, String, String, long,
	 *             String[])}
	 */
	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String[] data)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.addValue(
			companyId, className, tableName, columnName, classPK, data);
	}

	public void addValues(
			long classNameId, long tableId, List<ExpandoColumn> columns,
			long classPK, Map<String, String> data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		ExpandoRow row = expandoRowPersistence.fetchByT_C(tableId, classPK);

		if (row == null) {
			long rowId = counterLocalService.increment();

			row = expandoRowPersistence.create(rowId);

			row.setCompanyId(table.getCompanyId());
			row.setTableId(tableId);
			row.setClassPK(classPK);

			expandoRowPersistence.update(row, false);
		}

		for (ExpandoColumn column : columns) {
			String dataString = data.get(column.getName());

			if (dataString != null) {
				ExpandoValue value = expandoValuePersistence.fetchByC_R(
					column.getColumnId(), row.getRowId());

				if (value == null) {
					long valueId = counterLocalService.increment();

					value = expandoValuePersistence.create(valueId);

					value.setCompanyId(table.getCompanyId());
					value.setTableId(tableId);
					value.setColumnId(column.getColumnId());
					value.setRowId(row.getRowId());
					value.setClassNameId(classNameId);
					value.setClassPK(classPK);
				}

				value.setData(dataString);

				expandoValuePersistence.update(value, false);
			}
		}
	}

	public void deleteColumnValues(long columnId) throws SystemException {
		expandoValuePersistence.removeByColumnId(columnId);
	}

	public void deleteRowValues(long rowId) throws SystemException {
		expandoValuePersistence.removeByRowId(rowId);
	}

	public void deleteTableValues(long tableId) throws SystemException {
		expandoValuePersistence.removeByTableId(tableId);
	}

	public void deleteValue(long valueId)
		throws PortalException, SystemException {

		expandoValuePersistence.remove(valueId);
	}

	public void deleteValue(long columnId, long rowId)
		throws PortalException, SystemException {

		expandoValuePersistence.removeByC_R(columnId, rowId);
	}

	public void deleteValue(
			long companyId, long classNameId, String tableName,
			String columnName, long classPK)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return;
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			table.getTableId(), columnName);

		if (column == null) {
			return;
		}

		ExpandoValue value = expandoValuePersistence.fetchByT_C_C(
			table.getTableId(), column.getColumnId(), classPK);

		if (value != null) {
			deleteValue(value.getValueId());
		}
	}

	public void deleteValue(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		expandoValueLocalService.deleteValue(
			companyId, classNameId, tableName, columnName, classPK);
	}

	public void deleteValues(long classNameId, long classPK)
		throws SystemException {

		expandoValuePersistence.removeByC_C(classNameId, classPK);
	}

	public void deleteValues(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		expandoValueLocalService.deleteValues(classNameId, classPK);
	}

	public List<ExpandoValue> getColumnValues(long columnId, int start, int end)
		throws SystemException {

		return expandoValuePersistence.findByColumnId(columnId, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			long companyId, long classNameId, String tableName,
			String columnName, int start, int end)
		throws SystemException {

		return expandoValueLocalService.getColumnValues(
			companyId, classNameId, tableName, columnName, null, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			long companyId, long classNameId, String tableName,
			String columnName, String data, int start, int end)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return Collections.emptyList();
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			table.getTableId(), columnName);

		if (column == null) {
			return Collections.emptyList();
		}

		if (data == null) {
			return expandoValuePersistence.findByT_C(
				table.getTableId(), column.getColumnId(), start, end);
		}
		else {
			return expandoValuePersistence.findByT_C_D(
				table.getTableId(), column.getColumnId(), data, start, end);
		}
	}

	public List<ExpandoValue> getColumnValues(
			long companyId, String className, String tableName,
			String columnName, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getColumnValues(
			companyId, classNameId, tableName, columnName, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			long companyId, String className, String tableName,
			String columnName, String data, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getColumnValues(
			companyId, classNameId, tableName, columnName, data, start, end);
	}

	/**
	 * @deprecated {@link #getColumnValues(long, String, String, String, String,
	 *             int, int)}
	 */
	public List<ExpandoValue> getColumnValues(
			String className, String tableName, String columnName, String data,
			int start, int end)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getColumnValues(
			companyId, className, tableName, columnName, data, start, end);
	}

	public int getColumnValuesCount(long columnId) throws SystemException {
		return expandoValuePersistence.countByColumnId(columnId);
	}

	public int getColumnValuesCount(
			long companyId, long classNameId, String tableName,
			String columnName)
		throws SystemException {

		return expandoValueLocalService.getColumnValuesCount(
			companyId, classNameId, tableName, columnName, null);
	}

	public int getColumnValuesCount(
			long companyId, long classNameId, String tableName,
			String columnName, String data)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return 0;
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			table.getTableId(), columnName);

		if (column == null) {
			return 0;
		}

		if (data == null) {
			return expandoValuePersistence.countByT_C(
				table.getTableId(), column.getColumnId());
		}
		else {
			return expandoValuePersistence.countByT_C_D(
				table.getTableId(), column.getColumnId(), data);
		}
	}

	public int getColumnValuesCount(
			long companyId, String className, String tableName,
			String columnName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getColumnValuesCount(
			companyId, classNameId, tableName, columnName);
	}

	public int getColumnValuesCount(
			long companyId, String className, String tableName,
			String columnName, String data)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getColumnValuesCount(
			companyId, classNameId, tableName, columnName, data);
	}

	/**
	 * @deprecated {@link #getColumnValuesCount(long, String, String, String,
	 *             String)}
	 */
	public int getColumnValuesCount(
			String className, String tableName, String columnName, String data)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getColumnValuesCount(
			companyId, className, tableName, columnName, data);
	}

	public Serializable getData(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			companyId, className, tableName, columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setData(column.getDefaultData());

		int type = column.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getBoolean());
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getBooleanArray());
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getDate());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getDateArray());
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getDouble());
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getDoubleArray());
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getFloat());
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getFloatArray());
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getInteger());
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getIntegerArray());
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getLong());
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getLongArray());
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getShort());
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getShortArray());
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getStringArray());
		}
		else {
			return expandoValueLocalService.getData(
				companyId, className, tableName, columnName, classPK,
				value.getString());
		}
	}

	public boolean getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, boolean defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getBoolean();
		}
	}

	public boolean[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, boolean[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getBooleanArray();
		}
	}

	public Date getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, Date defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDate();
		}
	}

	public Date[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, Date[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDateArray();
		}
	}

	public double getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, double defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDouble();
		}
	}

	public double[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, double[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDoubleArray();
		}
	}

	public float getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, float defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getFloat();
		}
	}

	public float[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, float[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getFloatArray();
		}
	}

	public int getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, int defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getInteger();
		}
	}

	public int[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, int[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getIntegerArray();
		}
	}

	public long getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, long defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getLong();
		}
	}

	public long[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, long[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getLongArray();
		}
	}

	public short getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, short defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getShort();
		}
	}

	public short[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, short[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getShortArray();
		}
	}

	public String getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, String defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getString();
		}
	}

	public String[] getData(
			long companyId, String className, String tableName,
			String columnName, long classPK, String[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getStringArray();
		}
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long)}
	 */
	public Serializable getData(
			String className, String tableName, String columnName, long classPK)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             boolean[])}
	 */
	public boolean getData(
			String className, String tableName, String columnName, long classPK,
			boolean defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             boolean[])}
	 */
	public boolean[] getData(
			String className, String tableName, String columnName, long classPK,
			boolean[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, Date[])}
	 */
	public Date getData(
			String className, String tableName, String columnName, long classPK,
			Date defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, Date[])}
	 */
	public Date[] getData(
			String className, String tableName, String columnName, long classPK,
			Date[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             double[])}
	 */
	public double getData(
			String className, String tableName, String columnName, long classPK,
			double defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             double[])}
	 */
	public double[] getData(
			String className, String tableName, String columnName, long classPK,
			double[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, float[])}
	 */
	public float getData(
			String className, String tableName, String columnName, long classPK,
			float defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, float[])}
	 */
	public float[] getData(
			String className, String tableName, String columnName, long classPK,
			float[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, int[])}
	 */
	public int getData(
			String className, String tableName, String columnName, long classPK,
			int defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, int[])}
	 */
	public int[] getData(
			String className, String tableName, String columnName, long classPK,
			int[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, long[])}
	 */
	public long getData(
			String className, String tableName, String columnName, long classPK,
			long defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, long[])}
	 */
	public long[] getData(
			String className, String tableName, String columnName, long classPK,
			long[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, short[])}
	 */
	public short getData(
			String className, String tableName, String columnName, long classPK,
			short defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long, short[])}
	 */
	public short[] getData(
			String className, String tableName, String columnName, long classPK,
			short[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             String[])}
	 */
	public String getData(
			String className, String tableName, String columnName, long classPK,
			String defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	/**
	 * @deprecated {@link #getData(long, String, String, String, long,
	 *             String[])}
	 */
	public String[] getData(
			String className, String tableName, String columnName, long classPK,
			String[] defaultData)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getData(
			companyId, className, tableName, columnName, classPK, defaultData);
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			long companyId, long classNameId, String columnName, int start,
			int end)
		throws SystemException {

		return expandoValueLocalService.getColumnValues(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME,
			columnName, start, end);
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			long companyId, String className, String columnName, int start,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getDefaultTableColumnValues(
			companyId, classNameId, columnName, start, end);
	}

	public int getDefaultTableColumnValuesCount(
			long companyId, long classNameId, String columnName)
		throws SystemException {

		return expandoValueLocalService.getColumnValuesCount(
			companyId, classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME,
			columnName);
	}

	public int getDefaultTableColumnValuesCount(
			long companyId, String className, String columnName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getDefaultTableColumnValuesCount(
			companyId, classNameId, columnName);
	}

	public List<ExpandoValue> getRowValues(long rowId) throws SystemException {
		return expandoValuePersistence.findByRowId(rowId);
	}

	public List<ExpandoValue> getRowValues(long rowId, int start, int end)
		throws SystemException {

		return expandoValuePersistence.findByRowId(rowId, start, end);
	}

	public List<ExpandoValue> getRowValues(
			long companyId, long classNameId, String tableName, long classPK,
			int start, int end)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return Collections.emptyList();
		}

		return expandoValuePersistence.findByT_CPK(
			table.getTableId(), classPK, start, end);
	}

	public List<ExpandoValue> getRowValues(
			long companyId, String className, String tableName, long classPK,
			int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getRowValues(
			companyId, classNameId, tableName, classPK, start, end);
	}

	public int getRowValuesCount(long rowId) throws SystemException {
		return expandoValuePersistence.countByRowId(rowId);
	}

	public int getRowValuesCount(
			long companyId, long classNameId, String tableName, long classPK)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return 0;
		}

		return expandoValuePersistence.countByT_CPK(
			table.getTableId(), classPK);
	}

	public int getRowValuesCount(
			long companyId, String className, String tableName, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getRowValuesCount(
			companyId, classNameId, tableName, classPK);
	}

	public ExpandoValue getValue(long valueId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByPrimaryKey(valueId);
	}

	public ExpandoValue getValue(long columnId, long rowId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByC_R(columnId, rowId);
	}

	public ExpandoValue getValue(long tableId, long columnId, long classPK)
		throws SystemException {

		return expandoValuePersistence.fetchByT_C_C(
			tableId, columnId, classPK);
	}

	public ExpandoValue getValue(
			long companyId, long classNameId, String tableName,
			String columnName, long classPK)
		throws SystemException {

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, tableName);

		if (table == null) {
			return null;
		}

		ExpandoColumn column = expandoColumnPersistence.fetchByT_N(
			table.getTableId(), columnName);

		if (column == null) {
			return null;
		}

		return expandoValuePersistence.fetchByT_C_C(
			table.getTableId(), column.getColumnId(), classPK);
	}

	/**
	 * @deprecated {@link #getValue(long, long, String, String, long)}
	 */
	public ExpandoValue getValue(
			long classNameId, String tableName, String columnName, long classPK)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getValue(
			companyId, classNameId, tableName, columnName, classPK);
	}

	public ExpandoValue getValue(
			long companyId, String className, String tableName,
			String columnName, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return expandoValueLocalService.getValue(
			companyId, classNameId, tableName, columnName, classPK);
	}

	/**
	 * @deprecated {@link #getValue(long, String, String, String, long)}
	 */
	public ExpandoValue getValue(
			String className, String tableName, String columnName, long classPK)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoValueLocalService.getValue(
			companyId, className, tableName, columnName, classPK);
	}

}