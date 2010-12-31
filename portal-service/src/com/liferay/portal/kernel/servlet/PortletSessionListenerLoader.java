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

package com.liferay.portal.kernel.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

/**
 * <p>
 * See http://issues.liferay.com/browse/LEP-2299.
 * </p>
 *
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortletSessionListenerLoader implements ServletContextListener {

	public PortletSessionListenerLoader(HttpSessionListener listener) {
		_listener = listener;
	}

	public void contextInitialized(ServletContextEvent event) {
		PortletSessionListenerManager.addListener(_listener);
	}

	public void contextDestroyed(ServletContextEvent event) {
		PortletSessionListenerManager.removeListener(_listener);

		_listener = null;
	}

	private HttpSessionListener _listener;

}