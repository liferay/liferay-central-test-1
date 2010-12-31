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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;

import java.sql.Connection;

import org.quartz.utils.ConnectionProvider;

/**
 * @author Bruno Farache
 */
public class QuartzConnectionProviderImpl implements ConnectionProvider {

	public Connection getConnection() {
		Connection con = null;

		try {
			con = InfrastructureUtil.getDataSource().getConnection();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return con;
	}

	public void shutdown() {
	}

	private static Log _log = LogFactoryUtil.getLog(
		QuartzConnectionProviderImpl.class);

}