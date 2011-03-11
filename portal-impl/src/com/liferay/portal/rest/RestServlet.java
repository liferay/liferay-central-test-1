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

package com.liferay.portal.rest;

import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.servlet.JSONServlet;
import com.liferay.portal.struts.JSONAction;

import javax.servlet.ServletContext;

/**
 * @author Igor Spasic
 */
public class RestServlet extends JSONServlet {

	protected JSONAction getJSONAction(ServletContext servletContext) {
		ClassLoader portletClassLoader =
			(ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

		JSONAction jsonAction = new RESTServiceAction(portletClassLoader);

		jsonAction.setServletContext(servletContext);

		return jsonAction;
	}

}