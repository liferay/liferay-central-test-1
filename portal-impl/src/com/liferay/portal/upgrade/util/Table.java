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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.dao.orm.hibernate.BooleanType;
import com.liferay.portal.dao.orm.hibernate.DoubleType;
import com.liferay.portal.dao.orm.hibernate.FloatType;
import com.liferay.portal.dao.orm.hibernate.IntegerType;
import com.liferay.portal.dao.orm.hibernate.LongType;
import com.liferay.portal.dao.orm.hibernate.ShortType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.SystemProperties;

import java.io.FileReader;
import java.io.FileWriter;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.DateFormat;

import java.util.Date;

import org.hibernate.usertype.UserType;

/**
 * <a href="Table.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class Table {

	public static final int BATCH_SIZE = GetterUtil.getInteger(
		PropsUtil.get("hibernate.jdbc.batch_size"));

	public static final String SAFE_COMMA_CHARACTER =
		"_SAFE_COMMA_CHARACTER_";

	public static final String SAFE_NEWLINE_CHARACTER =
		"_SAFE_NEWLINE_CHARACTER_";

	public static final String SAFE_RETURN_CHARACTER =
		"_SAFE_RETURN_CHARACTER_";

	public static final String[][] SAFE_CHARS = {
		{StringPool.RETURN, StringPool.COMMA, StringPool.NEW_LINE},
		{SAFE_RETURN_CHARACTER, SAFE_COMMA_CHARACTER, SAFE_NEWLINE_CHARACTER}
	};

	public Table(String tableName) {
		_tableName = tableName;
	}

	public Table(String tableName, Object[][] columns) {
		_tableName = tableName;

		setColumns(columns);
	}

	public void appendColumn(StringBuilder sb, Object value, boolean last)
		throws Exception {

		if (value == null) {
			throw new UpgradeException(
				"Nulls should never be inserted into the database. " +
					"Attempted to append column to " + sb.toString() + ".");
		}
		else if (value instanceof Clob || value instanceof String) {
			value = StringUtil.replace(
				(String)value, SAFE_CHARS[0], SAFE_CHARS[1]);

			sb.append(value);
		}
		else if (value instanceof Date) {
			DateFormat df = DateUtil.getISOFormat();

			sb.append(df.format(value));
		}
		else {
			sb.append(value);
		}

		sb.append(StringPool.COMMA);

		if (last) {
			sb.append(StringPool.NEW_LINE);
		}
	}

	public void appendColumn(
			StringBuilder sb, ResultSet rs, String name, Integer type,
			boolean last)
		throws Exception {

		Object value = null;

		try {
			value = getValue(rs, name, type);
		}
		catch (SQLException sqle) {
			if (name.equals("uuid_")) {
				sb.append(PortalUUIDUtil.generate());
			}

			sb.append(StringPool.COMMA);

			if (last) {
				sb.append(StringPool.NEW_LINE);
			}

			return;
		}

		appendColumn(sb, value, last);
	}

	public Object[][] getColumns() {
		return _columns;
	}

	public String getCreateSQL() throws Exception {
		return _createSQL;
	}

	public String getDeleteSQL() throws Exception {
		return "DELETE FROM " + _tableName;
	}

	public String getExportedData(ResultSet rs) throws Exception {
		StringBuilder sb = new StringBuilder();

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			boolean last = false;

			if ((i + 1) == columns.length) {
				last = true;
			}

			appendColumn(
				sb, rs, (String)columns[i][0], (Integer)columns[i][1], last);
		}

		return sb.toString();
	}

	public String getInsertSQL() throws Exception {
		String sql = "INSERT INTO " + _tableName + " (";

		for (int i = 0; i < _order.length; i++) {
			int pos = _order[i];

			sql += _columns[pos][0];

			if ((i + 1) < _columns.length) {
				sql += ", ";
			}
			else {
				sql += ") VALUES (";
			}
		}

		for (int i = 0; i < _columns.length; i++) {
			sql += "?";

			if ((i + 1) < _columns.length) {
				sql += ", ";
			}
			else {
				sql += ")";
			}
		}

		return sql;
	}

	public int[] getOrder() {
		return _order;
	}

	public String getSelectSQL() throws Exception {
		if (_selectSQL == null) {
			/*String sql = "select ";

			for (int i = 0; i < _columns.length; i++) {
				sql += _columns[i][0];

				if ((i + 1) < _columns.length) {
					sql += ", ";
				}
				else {
					sql += " from " + _tableName;
				}
			}

			return sql;*/

			return "select * from " + _tableName;
		}
		else {
			return _selectSQL;
		}
	}

	public String getTableName() {
		return _tableName;
	}

	public long getTotalRows() {
		return _totalRows;
	}

	public Object getValue(ResultSet rs, String name, Integer type)
		throws Exception {

		Object value = null;

		int t = type.intValue();

		UserType userType = null;

		if (t == Types.BIGINT) {
			userType = new LongType();
		}
		else if (t == Types.BOOLEAN) {
			userType = new BooleanType();
		}
		else if (t == Types.CLOB) {
			try {
				Clob clob = rs.getClob(name);

				if (clob == null) {
					value = StringPool.BLANK;
				}
				else {
					UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(clob.getCharacterStream());

					StringBuilder sb = new StringBuilder();

					String line = null;

					while ((line = unsyncBufferedReader.readLine()) != null) {
						if (sb.length() != 0) {
							sb.append(SAFE_NEWLINE_CHARACTER);
						}

						sb.append(line);
					}

					value = sb.toString();
				}
			}
			catch (Exception e) {

				// If the database doesn't allow CLOB types for the column
				// value, then try retrieving it as a String

				value = GetterUtil.getString(rs.getString(name));
			}
		}
		else if (t == Types.DOUBLE) {
			userType = new DoubleType();
		}
		else if (t == Types.FLOAT) {
			userType = new FloatType();
		}
		else if (t == Types.INTEGER) {
			userType = new IntegerType();
		}
		else if (t == Types.SMALLINT) {
			userType = new ShortType();
		}
		else if (t == Types.TIMESTAMP) {
			try {
				value = rs.getTimestamp(name);
			}
			catch (Exception e) {
			}

			if (value == null) {
				value = StringPool.NULL;
			}
		}
		else if (t == Types.VARCHAR) {
			value = GetterUtil.getString(rs.getString(name));
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}

		if (userType != null) {
			try {
				value = userType.nullSafeGet(rs, new String[] {name}, null);
			}
			catch (Exception e) {
				_log.error(
					"Unable to nullSafeGet " + name + " with " +
						userType.getClass().getName());

				throw e;
			}
		}

		return value;
	}

	public String generateTempFile() throws Exception {
		Connection con = DataAccess.getConnection();

		try {
			return generateTempFile(con);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public String generateTempFile(Connection con) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean isEmpty = true;

		String tempFileName =
			SystemProperties.get(SystemProperties.TMP_DIR) + "/temp-db-" +
				_tableName + "-" + System.currentTimeMillis();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting backup of " + _tableName + " to " + tempFileName);
		}

		String selectSQL = getSelectSQL();

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new FileWriter(tempFileName));

		try {
			ps = con.prepareStatement(selectSQL);

			rs = ps.executeQuery();

			while (rs.next()) {
				String data = null;

				try {
					data = getExportedData(rs);

					unsyncBufferedWriter.write(data);

					_totalRows++;

					isEmpty = false;
				}
				catch (StagnantRowException sre) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Skipping stagnant data in " + _tableName + ": " +
								sre.getMessage());
					}
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished backup of " + _tableName + " to " + tempFileName);
			}
		}
		catch (Exception e) {
			FileUtil.delete(tempFileName);

			throw e;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);

			unsyncBufferedWriter.close();
		}

		if (!isEmpty) {
			return tempFileName;
		}
		else {
			FileUtil.delete(tempFileName);

			return null;
		}
	}

	public void populateTable(String tempFileName) throws Exception {
		Connection con = DataAccess.getConnection();

		try {
			populateTable(tempFileName, con);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public void populateTable(String tempFileName, Connection con)
		throws Exception {

		PreparedStatement ps = null;

		String insertSQL = getInsertSQL();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(tempFileName));

		String line = null;

		try {
			boolean useBatch = con.getMetaData().supportsBatchUpdates();

			if (!useBatch) {
				if (_log.isDebugEnabled()) {
					_log.debug("Database does not support batch updates");
				}
			}

			int count = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String[] values = StringUtil.split(line);

				Object[][] columns = getColumns();

				if ((values.length) != (columns.length)) {
					throw new UpgradeException(
						"Column lengths differ between temp file and schema. " +
							"Attempted to insert row " + line  + ".");
				}

				if (count == 0) {
					ps = con.prepareStatement(insertSQL);
				}

				int[] order = getOrder();

				for (int i = 0; i < order.length; i++) {
					int pos = order[i];

					setColumn(ps, i, (Integer)columns[pos][1], values[pos]);
				}

				if (useBatch) {
					ps.addBatch();

					if (count == BATCH_SIZE) {
						populateTableRows(ps, true);

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					populateTableRows(ps, false);
				}
			}

			if (useBatch) {
				if (count != 0) {
					populateTableRows(ps, true);
				}
			}
		}
		finally {
			DataAccess.cleanUp(null, ps);

			unsyncBufferedReader.close();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(getTableName() + " table populated with data");
		}
	}

	public void populateTableRows(PreparedStatement ps, boolean batch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Updating rows for " + getTableName());
		}

		if (batch) {
			ps.executeBatch();
		}
		else {
			ps.executeUpdate();
		}

		ps.close();
	}

	public void setColumn(
			PreparedStatement ps, int index, Integer type, String value)
		throws Exception {

		int t = type.intValue();

		int paramIndex = index + 1;

		if (t == Types.BIGINT) {
			ps.setLong(paramIndex, GetterUtil.getLong(value));
		}
		else if (t == Types.BOOLEAN) {
			ps.setBoolean(paramIndex, GetterUtil.getBoolean(value));
		}
		else if ((t == Types.CLOB) || (t == Types.VARCHAR)) {
			value = StringUtil.replace(value, SAFE_CHARS[1], SAFE_CHARS[0]);

			ps.setString(paramIndex, value);
		}
		else if (t == Types.DOUBLE) {
			ps.setDouble(paramIndex, GetterUtil.getDouble(value));
		}
		else if (t == Types.FLOAT) {
			ps.setFloat(paramIndex, GetterUtil.getFloat(value));
		}
		else if (t == Types.INTEGER) {
			ps.setInt(paramIndex, GetterUtil.getInteger(value));
		}
		else if (t == Types.SMALLINT) {
			ps.setShort(paramIndex, GetterUtil.getShort(value));
		}
		else if (t == Types.TIMESTAMP) {
			if (StringPool.NULL.equals(value)) {
				ps.setTimestamp(paramIndex, null);
			}
			else {
				DateFormat df = DateUtil.getISOFormat();

				ps.setTimestamp(
					paramIndex, new Timestamp(df.parse(value).getTime()));
			}
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}
	}

	public void setColumns(Object[][] columns) {
		_columns = columns;

		// LEP-7331

		_order = new int[_columns.length];

		int clobCount = 0;

		for (int i = 0; i < _columns.length; ++i) {
			Integer type = (Integer)columns[i][1];

			if (type.intValue() == Types.CLOB) {
				clobCount++;

				int pos = _columns.length - clobCount;

				_order[pos] = i;
			}
			else {
				int pos = i - clobCount;

				_order[pos] = i;
			}
		}
	}

	public void setCreateSQL(String createSQL) throws Exception {
		_createSQL = createSQL;
	}

	public void setSelectSQL(String selectSQL) throws Exception {
		_selectSQL = selectSQL;
	}

	private static Log _log = LogFactoryUtil.getLog(Table.class);

	private Object[][] _columns;
	private String _createSQL;
	private int[] _order;
	private String _selectSQL;
	private String _tableName;
	private long _totalRows = 0;

}