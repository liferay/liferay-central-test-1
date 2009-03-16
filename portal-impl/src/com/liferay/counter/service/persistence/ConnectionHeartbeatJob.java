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

package com.liferay.counter.service.persistence;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.job.IntervalJob;
import com.liferay.portal.kernel.job.JobExecutionContext;
import com.liferay.portal.kernel.job.JobExecutionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.Statement;

/**
 * <a href="ConnectionHeartbeatJob.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class ConnectionHeartbeatJob implements IntervalJob {

	public static final long INTERVAL =
		PropsValues.COUNTER_CONNECTION_HEARTBEAT_JOB_INTERVAL * Time.MINUTE;

	public void execute(JobExecutionContext context)
		throws JobExecutionException {

		try {
			sendHeartbeat();
		}
		catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	public long getInterval() {
		return INTERVAL;
	}

	protected void sendHeartbeat() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Sending heartbeat");
		}

		Connection connection = CounterUtil.getPersistence().getConnection();

		Statement statement = null;

		try {
			statement = connection.createStatement();

			statement.execute(_SELECT_RELEASE);
		}
		finally {
			DataAccess.cleanUp(statement);
		}
	}

	private static String _SELECT_RELEASE = "SELECT releaseId FROM Release_";

	private static final Log _log =
		LogFactoryUtil.getLog(ConnectionHeartbeatJob.class);

}