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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobExecutionContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.util.SystemProperties;
import com.liferay.util.ant.DeleteTask;

/**
 * <a href="CleanUpJob.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CleanUpJob implements IntervalJob {

	public CleanUpJob() {
		_interval = Time.DAY;
	}

	public void execute(JobExecutionContext context) {
		try {

			// LEP-2180

			DeleteTask.deleteFiles(
				SystemProperties.TMP_DIR, "LUCENE_liferay_com*.ljt", null);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public long getInterval() {
		return _interval;
	}

	private static Log _log = LogFactoryUtil.getLog(CleanUpJob.class);

	private long _interval;

}