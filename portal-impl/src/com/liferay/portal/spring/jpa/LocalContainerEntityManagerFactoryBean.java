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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import javax.sql.DataSource;

import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.TopLinkJpaVendorAdapter;

/**
 * <a href="LocalContainerEntityManagerFactoryBean.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class LocalContainerEntityManagerFactoryBean extends
	 org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean {

	public LocalContainerEntityManagerFactoryBean() {
		try {
			if (Validator.isNotNull(PropsValues.JPA_LOAD_TIME_WEAVER)) {
				Class<?> loadTimeWeaverClass = Class.forName(
					PropsValues.JPA_LOAD_TIME_WEAVER);

				LoadTimeWeaver loadTimeWeaver =
					(LoadTimeWeaver)loadTimeWeaverClass.newInstance();

				setLoadTimeWeaver(loadTimeWeaver);
			}
		}
		catch(Exception e) {
			_log.error(e, e);

			throw new RuntimeException(e);
		}

		setPersistenceXmlLocation("classpath*:META-INF/persistence-custom.xml");

		PersistenceUnitPostProcessor[] persistenceUnitPostProcessors =
			{new LiferayPersistenceUnitPostProcessor()};

		setPersistenceUnitPostProcessors(persistenceUnitPostProcessors);
	}

	public void setDataSource(DataSource dataSource) {
		Database database = DatabaseDetector.determineDatabase(dataSource);

		AbstractJpaVendorAdapter jpaVendorAdapter = null;

		String provider = PropsValues.JPA_PROVIDER;

		try {
			Class<?> providerClass = getProviderClass(provider);

			if (_log.isInfoEnabled()) {
				_log.info("Using provider class " + providerClass.getName());
			}

			jpaVendorAdapter =
				(AbstractJpaVendorAdapter)providerClass.newInstance();
		}
		catch(Exception e) {
			_log.error(e, e);

			return;
		}

		String databasePlatform = PropsValues.JPA_DATABASE_PLATFORM;

		if (provider.equalsIgnoreCase("eclipselink") ||
			provider.equalsIgnoreCase("toplink")) {

			if (databasePlatform == null) {
				databasePlatform = getDatabasePlatform(database);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Using database platform " + databasePlatform);
			}

			jpaVendorAdapter.setDatabasePlatform(databasePlatform);
		}
		else {
			if (databasePlatform == null) {
				jpaVendorAdapter.setDatabase(database);

				if (_log.isInfoEnabled()) {
					_log.info("Using database name " + database.toString());
				}
			}
			else {
				jpaVendorAdapter.setDatabase(
					Database.valueOf(databasePlatform));

				if (_log.isInfoEnabled()) {
					_log.info("Using database name " + databasePlatform);
				}
			}
		}

		setJpaVendorAdapter(jpaVendorAdapter);

		super.setDataSource(dataSource);
	}

	protected String getDatabasePlatform(Database database) {
		String databasePlatform = null;

		String packageName = null;

		boolean eclipseLink = false;

		if (PropsValues.JPA_PROVIDER.equalsIgnoreCase("eclipselink")) {
			packageName = "org.eclipse.persistence.platform.database.";

			eclipseLink = true;
		}
		else {
			packageName = "oracle.toplink.essentials.platform.database.";
		}

		if (database.equals(Database.DB2)) {
			databasePlatform = packageName + "DB2Platform";
		}
		else if (database.equals(Database.DERBY)) {
			databasePlatform = packageName + "DerbyPlatform";
		}
		else if (database.equals(Database.HSQL)) {
			databasePlatform = packageName + "HSQLPlatform";
		}
		else if (database.equals(Database.INFORMIX)) {
			databasePlatform = packageName + "InformixPlatform";
		}
		else if (database.equals(Database.MYSQL)) {
			if (eclipseLink) {
				databasePlatform = packageName + "MySQLPlatform";
			}
			else {
				databasePlatform = packageName + "MySQL4Platform";
			}
		}
		else if (database.equals(Database.ORACLE)) {
			if (eclipseLink) {
				databasePlatform = packageName + "OraclePlatform";
			}
			else {
				databasePlatform = packageName + "oracle.OraclePlatform";
			}
		}
		else if (database.equals(Database.POSTGRESQL)) {
			databasePlatform = packageName + "PostgreSQLPlatform";
		}
		else if (database.equals(Database.SQL_SERVER)) {
			databasePlatform = packageName + "SQLServerPlatform";
		}
		else if (database.equals(Database.SYBASE)) {
			databasePlatform = packageName + "SybasePlatform";
		}
		else {
			_log.error(
				"Unable to detect database platform for \"" +
					database.toString() + "\". Override by configuring the " +
						"\"jpa.database.platform\" property.");
		}

		return databasePlatform;
	}

	protected Class<?> getProviderClass(String provider) throws Exception {
		if (provider.equalsIgnoreCase("eclipselink")) {
			return EclipseLinkJpaVendorAdapter.class;
		}
		else if (provider.equalsIgnoreCase("hibernate")) {
			return HibernateJpaVendorAdapter.class;
		}
		else if (provider.equalsIgnoreCase("openjpa")) {
			return OpenJpaVendorAdapter.class;
		}
		else if (provider.equalsIgnoreCase("toplink")) {
			return TopLinkJpaVendorAdapter.class;
		}

		return null;
	}

	private static Log _log =
		LogFactoryUtil.getLog(LocalContainerEntityManagerFactoryBean.class);

}