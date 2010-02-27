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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.monitoring.statistics.DataSampleThreadLocal;

/**
 * <a href="DataSampleThreadLocalManager.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DataSampleThreadLocalManager
	implements DataSampleThreadLocalManagerMBean {

	public boolean isMonitoringDataSampleThreadLocal() {
		return DataSampleThreadLocal.isMonitoringDataSampleThreadLocal();
	}

	public void setMonitoringDataSampleThreadLocal(
		boolean monitoringDataSampleThreadLocal) {

		DataSampleThreadLocal.setMonitoringDataSampleThreadLocal(
			monitoringDataSampleThreadLocal);
	}

}