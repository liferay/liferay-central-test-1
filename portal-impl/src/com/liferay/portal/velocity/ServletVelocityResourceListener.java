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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author Alexander Chow
 */
public class ServletVelocityResourceListener extends VelocityResourceListener {

	public InputStream getResourceStream(String source)
		throws ResourceNotFoundException {

		InputStream is = null;

		int pos = source.indexOf(SERVLET_SEPARATOR);

		if (pos != -1) {
			String servletContextName = source.substring(0, pos);

			if (Validator.isNull(servletContextName)) {
				servletContextName = PortalUtil.getPathContext();
			}

			ServletContext servletContext = ServletContextPool.get(
				servletContextName);

			if (servletContext != null) {
				String name =
					source.substring(pos + SERVLET_SEPARATOR.length());

				if (_log.isDebugEnabled()) {
					_log.debug(
						name + " is associated with the servlet context " +
							servletContextName + " " + servletContext);
				}

				is = servletContext.getResourceAsStream(name);

				if ((is == null) && (name.endsWith("/init_custom.vm"))) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"The template " + name + " should be created");
					}

					is = new UnsyncByteArrayInputStream(new byte[0]);
				}
			}
			else {
				_log.error(
					source + " is not valid because " + servletContextName +
						" does not map to a servlet context");
			}
		}

		return is;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletVelocityResourceListener.class);

}