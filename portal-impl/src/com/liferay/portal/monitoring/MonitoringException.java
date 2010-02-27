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

package com.liferay.portal.monitoring;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * <a href="MonitoringException.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MonitoringException extends PortalException {

	public MonitoringException() {
		super();
	}

	public MonitoringException(String msg) {
		super(msg);
	}

	public MonitoringException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MonitoringException(Throwable cause) {
		super(cause);
	}

}