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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
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
import java.util.Date;
import java.util.List;

/**
 * <a href="ExpandoValueLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueLocalServiceImpl
	extends ExpandoValueLocalServiceBaseImpl {

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setBoolean(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			boolean[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setBooleanArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setDate(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Date[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setDateArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setDouble(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			double[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setDoubleArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setFloat(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			float[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setFloatArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setInteger(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			int[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setIntegerArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setLong(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			long[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setLongArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setShort(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			short[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setShortArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setString(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			String[] data)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTableLocalService.getTable(
			className, tableName);

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			table.getTableId(), columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());
		value.setStringArray(data);

		return addValue(
			table.getClassNameId(), table.getTableId(), column.getColumnId(),
			classPK, value.getData());
	}

	public ExpandoValue addValue(
			String className, String tableName, String columnName, long classPK,
			Object data)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			className, tableName, columnName);

		int type = column.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return addValue(
				className, tableName, columnName, classPK,
				((Boolean)data).booleanValue());
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (boolean[])data);
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return addValue(
				className, tableName, columnName, classPK, (Date)data);
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (Date[])data);
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return addValue(
				className, tableName, columnName, classPK,
				((Double)data).doubleValue());
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (double[])data);
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return addValue(
				className, tableName, columnName, classPK,
				((Float)data).floatValue());
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (float[])data);
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return addValue(
				className, tableName, columnName, classPK,
				((Integer)data).intValue());
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (int[])data);
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return addValue(
				className, tableName, columnName, classPK,
				((Long)data).longValue());
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (long[])data);
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return addValue(
				className, tableName, columnName, classPK,
				((Short)data).shortValue());
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (short[])data);
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return addValue(
				className, tableName, columnName, classPK, (String[])data);
		}
		else {
			return addValue(
				className, tableName, columnName, classPK, (String)data);
		}
	}

	public ExpandoValue addValue(
			long classNameId, long tableId, long columnId, long classPK,
			String data)
		throws SystemException {

		ExpandoRow row = expandoRowPersistence.fetchByT_C(tableId, classPK);

		if (row == null) {
			long rowId = counterLocalService.increment();

			row = expandoRowPersistence.create(rowId);

			row.setTableId(tableId);
			row.setClassPK(classPK);

			expandoRowPersistence.update(row, false);
		}

		ExpandoValue value = expandoValuePersistence.fetchByT_C_R(
			tableId, columnId, row.getRowId());

		if (value == null) {
			long valueId = counterLocalService.increment();

			value = expandoValuePersistence.create(valueId);

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
			String className, String tableName, String columnName, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteValue(classNameId, tableName, columnName, classPK);
	}

	public void deleteValue(
			long classNameId, String tableName, String columnName, long classPK)
		throws PortalException, SystemException {

		ExpandoValue value = expandoValueFinder.fetchByTC_TN_CN_C(
			classNameId, tableName, columnName, classPK);

		if (value != null) {
			deleteValue(value.getValueId());
		}
	}

	public void deleteValues(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteValues(classNameId, classPK);
	}

	public void deleteValues(long classNameId, long classPK)
		throws SystemException {

		expandoValuePersistence.removeByC_C(classNameId, classPK);
	}

	public List<ExpandoValue> getColumnValues(long columnId, int start, int end)
		throws SystemException {

		return expandoValuePersistence.findByColumnId(columnId, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			String className, String tableName, String columnName, int start,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnValues(classNameId, tableName, columnName, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			long classNameId, String tableName, String columnName, int start,
			int end)
		throws SystemException {

		return expandoValueFinder.findByTC_TN_CN(
			classNameId, tableName, columnName, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			String className, String tableName, String columnName, String data,
			int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnValues(
			classNameId, tableName, columnName, data, start, end);
	}

	public List<ExpandoValue> getColumnValues(
			long classNameId, String tableName, String columnName, String data,
			int start, int end)
		throws SystemException {

		return expandoValueFinder.findByTC_TN_CN_D(
			classNameId, tableName, columnName, data, start, end);
	}

	public int getColumnValuesCount(long columnId) throws SystemException {
		return expandoValuePersistence.countByColumnId(columnId);
	}

	public int getColumnValuesCount(
			String className, String tableName, String columnName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnValuesCount(classNameId, tableName, columnName);
	}

	public int getColumnValuesCount(
			long classNameId, String tableName, String columnName)
		throws SystemException {

		return expandoValueFinder.countByTC_TN_CN(
			classNameId, tableName, columnName);
	}

	public int getColumnValuesCount(
			String className, String tableName, String columnName, String data)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getColumnValuesCount(classNameId, tableName, columnName, data);
	}

	public int getColumnValuesCount(
			long classNameId, String tableName, String columnName, String data)
		throws SystemException {

		return expandoValueFinder.countByTC_TN_CN_D(
			classNameId, tableName, columnName, data);
	}

	public boolean getData(
			String className, String tableName, String columnName, long classPK,
			boolean defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getBoolean();
		}
	}

	public boolean[] getData(
			String className, String tableName, String columnName, long classPK,
			boolean[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getBooleanArray();
		}
	}

	public Date getData(
			String className, String tableName, String columnName, long classPK,
			Date defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDate();
		}
	}

	public Date[] getData(
			String className, String tableName, String columnName, long classPK,
			Date[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDateArray();
		}
	}

	public double getData(
			String className, String tableName, String columnName, long classPK,
			double defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDouble();
		}
	}

	public double[] getData(
			String className, String tableName, String columnName, long classPK,
			double[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getDoubleArray();
		}
	}

	public float getData(
			String className, String tableName, String columnName, long classPK,
			float defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getFloat();
		}
	}

	public float[] getData(
			String className, String tableName, String columnName, long classPK,
			float[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getFloatArray();
		}
	}

	public int getData(
			String className, String tableName, String columnName, long classPK,
			int defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getInteger();
		}
	}

	public int[] getData(
			String className, String tableName, String columnName, long classPK,
			int[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getIntegerArray();
		}
	}

	public long getData(
			String className, String tableName, String columnName, long classPK,
			long defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getLong();
		}
	}

	public long[] getData(
			String className, String tableName, String columnName, long classPK,
			long[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getLongArray();
		}
	}

	public short getData(
			String className, String tableName, String columnName, long classPK,
			short defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getShort();
		}
	}

	public short[] getData(
			String className, String tableName, String columnName, long classPK,
			short[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getShortArray();
		}
	}

	public String getData(
			String className, String tableName, String columnName, long classPK,
			String defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getString();
		}
	}

	public String[] getData(
			String className, String tableName, String columnName, long classPK,
			String[] defaultData)
		throws PortalException, SystemException {

		ExpandoValue value = getValue(
			className, tableName, columnName, classPK);

		if (value == null) {
			return defaultData;
		}
		else {
			return value.getStringArray();
		}
	}

	public Serializable getData(
			String className, String tableName, String columnName, long classPK)
		throws PortalException, SystemException {

		ExpandoColumn column = expandoColumnLocalService.getColumn(
			className, tableName, columnName);

		ExpandoValue value = new ExpandoValueImpl();

		value.setColumnId(column.getColumnId());

		int type = column.getType();

		if (type == ExpandoColumnConstants.BOOLEAN) {
			return getData(
				className, tableName, columnName, classPK, value.getBoolean());
		}
		else if (type == ExpandoColumnConstants.BOOLEAN_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getBooleanArray());
		}
		else if (type == ExpandoColumnConstants.DATE) {
			return getData(
				className, tableName, columnName, classPK, value.getDate());
		}
		else if (type == ExpandoColumnConstants.DATE_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getDateArray());
		}
		else if (type == ExpandoColumnConstants.DOUBLE) {
			return getData(
				className, tableName, columnName, classPK, value.getDouble());
		}
		else if (type == ExpandoColumnConstants.DOUBLE_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getDoubleArray());
		}
		else if (type == ExpandoColumnConstants.FLOAT) {
			return getData(
				className, tableName, columnName, classPK, value.getFloat());
		}
		else if (type == ExpandoColumnConstants.FLOAT_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getFloatArray());
		}
		else if (type == ExpandoColumnConstants.INTEGER) {
			return getData(
				className, tableName, columnName, classPK, value.getInteger());
		}
		else if (type == ExpandoColumnConstants.INTEGER_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getIntegerArray());
		}
		else if (type == ExpandoColumnConstants.LONG) {
			return getData(
				className, tableName, columnName, classPK, value.getLong());
		}
		else if (type == ExpandoColumnConstants.LONG_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getLongArray());
		}
		else if (type == ExpandoColumnConstants.SHORT) {
			return getData(
				className, tableName, columnName, classPK, value.getShort());
		}
		else if (type == ExpandoColumnConstants.SHORT_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getShortArray());
		}
		else if (type == ExpandoColumnConstants.STRING_ARRAY) {
			return getData(
				className, tableName, columnName, classPK,
				value.getStringArray());
		}
		else {
			return getData(
				className, tableName, columnName, classPK, value.getString());
		}
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			String className, String columnName, int start, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableColumnValues(classNameId, columnName, start, end);
	}

	public List<ExpandoValue> getDefaultTableColumnValues(
			long classNameId, String columnName, int start, int end)
		throws SystemException {

		return getColumnValues(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME, columnName,
			start, end);
	}

	public int getDefaultTableColumnValuesCount(
			String className, String columnName)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getDefaultTableColumnValuesCount(classNameId, columnName);
	}

	public int getDefaultTableColumnValuesCount(
			long classNameId, String columnName)
		throws SystemException {

		return getColumnValuesCount(
			classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME, columnName);
	}

	public List<ExpandoValue> getRowValues(long rowId) throws SystemException {
		return expandoValuePersistence.findByRowId(rowId);
	}

	public List<ExpandoValue> getRowValues(long rowId, int start, int end)
		throws SystemException {

		return expandoValuePersistence.findByRowId(rowId, start, end);
	}

	public List<ExpandoValue> getRowValues(
			String className, String tableName, long classPK, int start,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRowValues(classNameId, tableName, classPK, start, end);
	}

	public List<ExpandoValue> getRowValues(
			long classNameId, String tableName, long classPK, int start,
			int end)
		throws SystemException {

		return expandoValueFinder.findByTC_TN_C(
			classNameId, tableName, classPK, start, end);
	}

	public int getRowValuesCount(long rowId) throws SystemException {
		return expandoValuePersistence.countByRowId(rowId);
	}

	public int getRowValuesCount(
			String className, String tableName, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getRowValuesCount(classNameId, tableName, classPK);
	}

	public int getRowValuesCount(
			long classNameId, String tableName, long classPK)
		throws SystemException {

		return expandoValueFinder.countByTC_TN_C(
			classNameId, tableName, classPK);
	}

	public ExpandoValue getValue(long valueId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByPrimaryKey(valueId);
	}

	public ExpandoValue getValue(long columnId, long rowId)
		throws PortalException, SystemException {

		return expandoValuePersistence.findByC_R(columnId, rowId);
	}

	public ExpandoValue getValue(
			String className, String tableName, String columnName, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getValue(classNameId, tableName, columnName, classPK);
	}

	public ExpandoValue getValue(
			long classNameId, String tableName, String columnName, long classPK)
		throws SystemException {

		return expandoValueFinder.fetchByTC_TN_CN_C(
			classNameId, tableName, columnName, classPK);
	}

}