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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * <a href="InformixUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Neil Griffin
 * @author Sandeep Soni
 * @author Ganesh Ram
 */
public class InformixUtil extends DBUtil {

	public static DBUtil getInstance() {
		return _instance;
	}

	public String buildSQL(String template) throws IOException {
		template = convertTimestamp(template);
		template = replaceTemplate(template, getTemplate());

		template = reword(template);
		template = removeNull(template);

		return template;
	}

	protected InformixUtil() {
		super(TYPE_INFORMIX);
	}

	protected void buildCreateFile(String databaseName, boolean minimal)
		throws IOException {

		String minimalSuffix = getMinimalSuffix(minimal);

		File file = new File(
			"../sql/create" + minimalSuffix + "/create" + minimalSuffix +
				"-informix.sql");

		StringBuilder sb = new StringBuilder();

		sb.append("database sysmaster;\n");
		sb.append("drop database " + databaseName + ";\n");
		sb.append("create database " + databaseName + " WITH LOG;\n");
		sb.append("\n");
		sb.append("create procedure 'lportal'.isnull(test_string varchar)\n");
		sb.append("returning boolean;\n");
		sb.append("IF test_string IS NULL THEN\n");
		sb.append("\tRETURN 't';\n");
		sb.append("ELSE\n");
		sb.append("\tRETURN 'f';\n");
		sb.append("END IF\n");
		sb.append("end procedure;\n");
		sb.append("\n\n");
		sb.append(
			FileUtil.read(
				"../sql/portal" + minimalSuffix + "/portal" + minimalSuffix +
					"-informix.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/indexes/indexes-informix.sql"));
		sb.append("\n\n");
		sb.append(FileUtil.read("../sql/sequences/sequences-informix.sql"));

		FileUtil.write(file, sb.toString());
	}

	protected String getServerName() {
		return "informix";
	}

	protected String[] getTemplate() {
		return _INFORMIX_TEMPLATE;
	}

	protected String reword(String data) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(data));

		StringBuilder sb = new StringBuilder();

		String line = null;

		boolean createTable = false;

		while ((line = br.readLine()) != null) {
			if (line.startsWith(ALTER_COLUMN_NAME)) {
				String[] template = buildColumnNameTokens(line);

				line = StringUtil.replace(
					"rename column @table@.@old-column@ TO @new-column@;",
					REWORD_TEMPLATE, template);
			}
			else if (line.startsWith(ALTER_COLUMN_TYPE)) {
				String[] template = buildColumnTypeTokens(line);

				line = StringUtil.replace(
					"alter table @table@ modify (@old-column@ @type@);",
					REWORD_TEMPLATE, template);
			}
			else if (line.indexOf("typeSettings text") > 0) {
				line = StringUtil.replace(
					line, "typeSettings text", "typeSettings lvarchar(4096)");
			}
			else if (line.indexOf("varchar(300)") > 0) {
				line = StringUtil.replace(
					line, "varchar(300)", "lvarchar(300)");
			}
			else if (line.indexOf("varchar(500)") > 0) {
				line = StringUtil.replace(
					line, "varchar(500)", "lvarchar(500)");
			}
			else if (line.indexOf("varchar(1000)") > 0) {
				line = StringUtil.replace(
					line, "varchar(1000)", "lvarchar(1000)");
			}
			else if (line.indexOf("varchar(1024)") > 0) {
				line = StringUtil.replace(
					line, "varchar(1024)", "lvarchar(1024)");
			}
			else if (line.indexOf("1970-01-01") > 0) {
				line = StringUtil.replace(
					line, "1970-01-01", "1970-01-01 00:00:00.0");
			}
			else if (line.indexOf("create table") >= 0) {
				createTable = true;
			}
			else if ((line.indexOf(");") >= 0) && createTable) {
				line = StringUtil.replace(
					line, ");",
					")\nextent size 16 next size 16\nlock mode row;");

				createTable = false;
			}
			else if (line.indexOf("commit;") >= 0) {
				line = StringPool.BLANK;
			}

			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}

	private static String[] _INFORMIX_TEMPLATE = {
		"--", "'T'", "'F'",
		"'1970-01-01'", "CURRENT YEAR TO FRACTION",
		" byte in table", " boolean", " datetime YEAR TO FRACTION",
		" float", " int", " int8",
		" lvarchar", " text", " varchar",
		"", "commit"
	};

	private static InformixUtil _instance = new InformixUtil();

}