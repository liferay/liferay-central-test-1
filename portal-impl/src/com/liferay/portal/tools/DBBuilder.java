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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.util.InitUtil;

import java.io.IOException;

/**
 * <a href="DBBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 * @author Alexander Chow
 */
public class DBBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 1) {
			new DBBuilder(args[0], DBUtil.TYPE_ALL);
		}
		else if (args.length == 2) {
			new DBBuilder(args[0], StringUtil.split(args[1]));
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public DBBuilder(String databaseName, String[] databaseTypes) {
		try {
			_databaseName = databaseName;
			_databaseTypes = databaseTypes;

			_buildSQLFile("portal");
			_buildSQLFile("portal-minimal");
			_buildSQLFile("indexes");
			_buildSQLFile("sequences");
			_buildSQLFile("update-4.2.0-4.3.0");
			_buildSQLFile("update-4.3.0-4.3.1");
			_buildSQLFile("update-4.3.1-4.3.2");
			_buildSQLFile("update-4.3.2-4.3.3");
			_buildSQLFile("update-4.3.3-4.3.4");
			_buildSQLFile("update-4.3.6-4.4.0");
			_buildSQLFile("update-4.4.0-5.0.0");
			_buildSQLFile("update-5.0.1-5.1.0");
			_buildSQLFile("update-5.1.1-5.1.2");
			_buildSQLFile("update-5.1.2-5.2.0");
			_buildSQLFile("update-5.2.0-5.2.1");
			_buildSQLFile("update-5.2.2-5.2.3");
			_buildSQLFile("update-5.2.3-5.3.0");

			_buildCreateFile();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _buildCreateFile() throws IOException {
		for (int i = 0; i < _databaseTypes.length; i++) {
			String databaseType = _databaseTypes[i];

			if (databaseType.equals(DBUtil.TYPE_HYPERSONIC) ||
				databaseType.equals(DBUtil.TYPE_INTERBASE) ||
				databaseType.equals(DBUtil.TYPE_JDATASTORE) ||
				databaseType.equals(DBUtil.TYPE_SAP)) {

				continue;
			}

			DBUtil dbUtil = _getDBUtil(_databaseTypes[i]);

			if (dbUtil != null) {
				dbUtil.buildCreateFile(_databaseName);
			}
		}
	}

	private void _buildSQLFile(String fileName) throws IOException {
		if (!FileUtil.exists("../sql/" + fileName + ".sql")) {
			return;
		}

		for (int i = 0; i < _databaseTypes.length; i++) {
			DBUtil dbUtil = _getDBUtil(_databaseTypes[i]);

			if (dbUtil != null) {
				dbUtil.buildSQLFile(fileName);
			}
		}
	}

	private DBUtil _getDBUtil(String type) {
		return DBUtil.getInstance(type);
	}

	private String _databaseName;
	private String[] _databaseTypes;

}