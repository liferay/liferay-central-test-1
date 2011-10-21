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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.Dialect;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;

import java.sql.Connection;

/**
 * @author Shuyang Zhou
 */
public class SQLRecorderSessionFactoryWrapper implements SessionFactory{

	public SQLRecorderSessionFactoryWrapper(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	public void closeSession(Session session) throws ORMException {
		_sessionFactory.closeSession(session);
	}

	public Dialect getDialect() throws ORMException {
		return _sessionFactory.getDialect();
	}

	public Session openNewSession(Connection connection) throws ORMException {
		return new SQLRecorderSessionWrapper(
			_sessionFactory.openNewSession(connection));
	}

	public Session openSession() throws ORMException {
		return new SQLRecorderSessionWrapper(_sessionFactory.openSession());
	}

	private SessionFactory _sessionFactory;

}