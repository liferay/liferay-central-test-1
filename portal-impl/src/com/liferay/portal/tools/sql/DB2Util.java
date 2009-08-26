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

package com.liferay.portal.tools.sql;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="DB2Util.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Bruno Farache
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class DB2Util extends DBUtil {

	public static DBUtil getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = removeLongInserts(template);
		template = removeNull(template);
		template = StringUtil.replace(template, "\\'", "''");

		return template;
	}

	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	public void runSQL(String template) throws IOException, SQLException {
		if (template.startsWith(ALTER_COLUMN_NAME) ||
			template.startsWith(ALTER_COLUMN_TYPE)) {

			String sql = buildSQL(template);

			String[] alterSqls = StringUtil.split(sql, StringPool.SEMICOLON);

			for (String alterSql : alterSqls) {
				if (!alterSql.startsWith("-- ")){
					runSQL(alterSql);
				}
			}
		}
		else {
			super.runSQL(template);
		}
	}

	public void runSQL(String[] templates) throws IOException, SQLException {
		super.runSQL(templates);

		_reorgTables(templates);
	}

	protected DB2Util() {
		super(TYPE_DB2);
	}

	protected String buildCreateFileContent(String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		StringBuilder sb = new StringBuilder();

		sb.append("drop database " + databaseName + ";\n");
		sb.append("create database " + databaseName + ";\n");
		sb.append("connect to " + databaseName + ";\n");
		sb.append(
			FileUtil.read("../sql/portal" + suffix + "/portal" + suffix +
				"-db2.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-db2.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-db2.sql"));

		return sb.toString();
	}

	protected String getServerName() {
		return "db2";
	}

	protected String[] getTemplate() {
		return _DB2;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"alter table @table@ add column @new-column@ @type@;\n",
					REWORD_TEMPLATE, template);

				line = line + StringUtil.replace(
					"update @table@ set @new-column@ = @old-column@;\n",
					REWORD_TEMPLATE, template);

				line = line + StringUtil.replace(
					"alter table @table@ drop column @old-column@",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				line = "-- " + line;
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private void _reorgTables(String[] templates) throws SQLException {
		Set<String> tableNames = new HashSet<String>();

		for (String template : templates) {
			if (template.startsWith("alter table")) {
				tableNames.add(template.split(" ")[2]);
			}
		}

		if (tableNames.size() == 0) {
			return;
		}

		Connection con = null;
		CallableStatement callStmt = null;

		try {
			con = DataAccess.getConnection();

			for (String tableName : tableNames) {
				String sql = "call sysproc.admin_cmd(?)";

				callStmt = con.prepareCall(sql);

				String param = "reorg table " + tableName;

				callStmt.setString(1, param);

				callStmt.execute();
			}
		}
		finally {
			DataAccess.cleanUp(con, callStmt);
		}
	}

	private static String[] _DB2 = {
		"--", "1", "0",
		"'1970-01-01-00.00.00.000000'", "current timestamp",
		" blob(2000)", " smallint", " timestamp",
		" double", " integer", " bigint",
		" varchar(500)", " clob", " varchar",
		" generated always as identity", "commit"
	};

	private static boolean _SUPPORTS_ALTER_COLUMN_TYPE;

	private static DB2Util _instance = new DB2Util();

}