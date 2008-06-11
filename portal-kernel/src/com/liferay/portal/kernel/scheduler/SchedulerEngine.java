/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler;

import java.util.Collection;
import java.util.Date;

/**
 * <a href="SchedulerEngine.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 *
 */
public interface SchedulerEngine {

	public static final String CRON_TEXT = "cronText";

	public static final String DESCRIPTION = "description";

	public static final String DESTINATION = "destination";

	public static final String MESSAGE_BODY = "messageBody";

	public Collection<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException;

	public void schedule(
			String jobName, String groupName, String cronText, Date startDate,
			Date endDate, String description, String destinationName,
			String messageBody)
		throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void start() throws SchedulerException;

	public void unschedule(String jobName, String groupName)
		throws SchedulerException;

}