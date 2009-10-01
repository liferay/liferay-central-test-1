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

package com.liferay.portal.convert;

import com.liferay.portal.dao.jdbc.util.DataSourceFactoryBean;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.spring.hibernate.DialectDetector;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.ShutdownUtil;

import java.sql.Connection;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.Dialect;

/**
 * <a href="ConvertDatabase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class ConvertDatabase extends ConvertProcess {

	public String getDescription() {
		return "migrate-data-from-one-database-to-another";
	}

	public String getParameterDescription() {
		return "please-enter-jdbc-information-for-new-database";
	}

	public String[] getParameterNames() {
		return new String[] {
			"jdbc-driver-class-name", "jdbc-url", "jdbc-user-name",
			"jdbc-password"
		};
	}

	public boolean isEnabled() {
		return true;
	}

	protected void doConvert() throws Exception {
		DataSource dataSource = _getDataSource();

		Dialect dialect = DialectDetector.getDialect(dataSource);

		DBUtil dbUtil = DBUtil.getInstance(dialect);

		List<String> modelNames = ModelHintsUtil.getModels();

		Collections.sort(modelNames);

		Connection connection = dataSource.getConnection();

		try {
			MaintenanceUtil.appendStatus(
				"Migrating " + modelNames.size() +
					" database tables to new schema.");

			for (int i = 0; i < modelNames.size(); i++) {
				if ((i > 0) && (i % (modelNames.size() / 4) == 0)) {
					MaintenanceUtil.appendStatus(
						 (i * 100. / modelNames.size()) + "%");
				}

				String name = modelNames.get(i);

				name = name.replaceFirst(
					"(\\.model\\.)(\\p{Upper}.*)", "$1impl.$2Impl");

				Class<?> implClass = InstancePool.get(name).getClass();

				String createSql =
					(String)implClass.getField("TABLE_SQL_CREATE").get(
						StringPool.BLANK);
				String tableName =
					(String)implClass.getField("TABLE_NAME").get(
						StringPool.BLANK);
				Object[][] columns =
					(Object[][])implClass.getField("TABLE_COLUMNS").get(
						new Object[0][0]);

				if (_log.isDebugEnabled()) {
					_log.debug("Migrating database table " + tableName);
				}

				Table table = new Table(tableName, columns);

				String tempFileName = table.generateTempFile();

				dbUtil.runSQL(connection, createSql);

				if (tempFileName != null) {
					table.populateTable(tempFileName, connection);
				}
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Please change your JDBC settings before restarting server.");
		}

		ShutdownUtil.shutdown(0);
	}

	private DataSource _getDataSource() throws Exception {
		String[] values = getParameterValues();

		String jdbcDriverClassName = values[0];
		String jdbcURL = values[1];
		String jdbcUserName = values[2];
		String jdbcPassword = values[3];

		Properties properties = new Properties();

		properties.setProperty(
			_JDBC_PREFIX + "driverClassName", jdbcDriverClassName);
		properties.setProperty(_JDBC_PREFIX + "url", jdbcURL);
		properties.setProperty(_JDBC_PREFIX + "username", jdbcUserName);
		properties.setProperty(_JDBC_PREFIX + "password", jdbcPassword);

		DataSourceFactoryBean dataSourceFactory = new DataSourceFactoryBean();

		dataSourceFactory.setProperties(properties);
		dataSourceFactory.setPropertyPrefix(_JDBC_PREFIX);

		return (DataSource)dataSourceFactory.createInstance();
	}

	private static final String _JDBC_PREFIX = "jdbc.upgrade.";

	private static Log _log = LogFactoryUtil.getLog(ConvertDatabase.class);

}