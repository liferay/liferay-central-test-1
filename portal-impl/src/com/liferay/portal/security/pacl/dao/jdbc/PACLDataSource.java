/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.dao.jdbc;

import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLUtil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLDataSource extends DataSourceWrapper {

	public PACLDataSource(DataSource dataSource) {
		super(dataSource);

		_dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = _dataSource.getConnection();

		PACLPolicy paclPolicy = PACLUtil.getPACLPolicy();

		if (paclPolicy == null) {
			return connection;
		}

		return (Connection)ProxyUtil.newProxyInstance(
			paclPolicy.getClassLoader(), new Class<?>[] {Connection.class},
			new PACLConnectionHandler(connection, paclPolicy));
	}

	private DataSource _dataSource;

}