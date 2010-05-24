/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.dao.orm.hibernate.DB2Dialect;
import com.liferay.portal.dao.orm.hibernate.SQLServer2008Dialect;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.DB2400Dialect;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.SybaseASE15Dialect;
import org.hibernate.dialect.resolver.DialectFactory;

/**
 * <a href="DialectDetector.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DialectDetector {

	public static String determineDialect(DataSource dataSource) {
		Dialect dialect = getDialect(dataSource);

		DBFactoryUtil.setDB(dialect);

		if (_log.isInfoEnabled()) {
			_log.info("Using dialect " + dialect.getClass().getName());
		}

		return dialect.getClass().getName();
	}

	public static Dialect getDialect(DataSource dataSource) {
		Dialect dialect = null;

		Connection connection = null;

		try {
			connection = dataSource.getConnection();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			String dbName = databaseMetaData.getDatabaseProductName();
			int dbMajorVersion = databaseMetaData.getDatabaseMajorVersion();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Determining dialect for " + dbName + " " + dbMajorVersion);
			}

			if (dbName.startsWith("HSQL")) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(6);

					sb.append("Liferay is configured to use Hypersonic as ");
					sb.append("its database. Do NOT use Hypersonic in ");
					sb.append("production. Hypersonic is an embedded ");
					sb.append("database useful for development and demo'ing ");
					sb.append("purposes. The database settings can be ");
					sb.append("changed in portal.properties.");

					_log.warn(sb.toString());
				}
			}

			if (dbName.equals("ASE") && (dbMajorVersion == 15)) {
				dialect = new SybaseASE15Dialect();
			}
			else if (dbName.startsWith("DB2") && (dbMajorVersion == 9)) {
				dialect = new DB2Dialect();
			}
			else if (dbName.startsWith("Microsoft") && (dbMajorVersion >= 9)) {
				dialect = new SQLServer2008Dialect();
			}
			else {
				dialect = DialectFactory.buildDialect(
					new Properties(), connection);
			}
		}
		catch (Exception e) {
			String msg = GetterUtil.getString(e.getMessage());

			if (msg.indexOf("explicitly set for database: DB2") != -1) {
				dialect = new DB2400Dialect();

				if (_log.isWarnEnabled()) {
					_log.warn(
						"DB2400Dialect was dynamically chosen as the " +
							"Hibernate dialect for DB2. This can be " +
								"overriden in portal.properties");
				}
			}
			else {
				_log.error(e, e);
			}
		}
		finally {
			DataAccess.cleanUp(connection);
		}

		if (dialect == null) {
			throw new RuntimeException("No dialect found");
		}

		return dialect;
	}

	private static Log _log = LogFactoryUtil.getLog(DialectDetector.class);

}