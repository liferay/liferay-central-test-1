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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.SchedulerRequest;
import com.liferay.portal.util.PropsUtil;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * <a href="QuartzSchedulerEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Bruno Farache
 *
 */
public class QuartzSchedulerEngineImpl implements SchedulerEngine {

	public QuartzSchedulerEngineImpl() {
		try {
			Destination destination = new SerialDestination(
				DestinationNames.SCHEDULER);

			MessageBusUtil.addDestination(destination);

			MessageBusUtil.registerMessageListener(
				destination.getName(), new QuartzMessageListener());

			Properties props = new Properties();

			Enumeration<Object> enu = PropsUtil.getProperties().keys();

			while (enu.hasMoreElements()) {
				String key = (String)enu.nextElement();

				if (key.startsWith("org.quartz.")) {
					props.setProperty(key, PropsUtil.get(key));
				}
			}

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

			schedulerFactory.initialize(props);

			_scheduler = schedulerFactory.getScheduler();
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public Collection<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			String[] jobNames = _scheduler.getJobNames(groupName);

			List<SchedulerRequest> requests = new ArrayList<SchedulerRequest>();

			for (String jobName : jobNames) {
				JobDetail jobDetail = _scheduler.getJobDetail(
					jobName, groupName);

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				String description = jobDataMap.getString(DESCRIPTION);
				String messageBody = jobDataMap.getString(MESSAGE_BODY);

				CronTrigger cronTrigger = (CronTrigger)_scheduler.getTrigger(
					jobName, groupName);

				SchedulerRequest schedulerRequest = new SchedulerRequest(
					null, jobName, groupName, cronTrigger.getCronExpression(),
					cronTrigger.getStartTime(), cronTrigger.getEndTime(),
					description, null, messageBody);

				requests.add(schedulerRequest);
			}

			return requests;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to retrieve job", se);
		}
	}

	public void schedule(
			String jobName, String groupName, String cronText, Date startDate,
			Date endDate, String description, String destination,
			String messageBody)
		throws SchedulerException {

		try {
			CronTrigger cronTrigger = new CronTrigger(
				jobName, groupName, cronText);

			if (startDate != null) {
				cronTrigger.setStartTime(startDate);
			}

			if (endDate != null) {
				cronTrigger.setEndTime(endDate);
			}

			JobDetail jobDetail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION, destination);
			jobDataMap.put(MESSAGE_BODY, messageBody);

			_scheduler.scheduleJob(jobDetail, cronTrigger);
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
		catch (ParseException pe) {
			throw new SchedulerException("Unable to parse cron text", pe);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to scheduled job", se);
		}
	}

	public void shutdown() throws SchedulerException {
		try {
			_scheduler.shutdown(false);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to shutdown scheduler", se);
		}
	}

	public void start() throws SchedulerException {
		try {
			_scheduler.start();
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to start scheduler", se);
		}
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		try {
			_scheduler.unscheduleJob(jobName, groupName);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				se);
		}
	}

	private Log _log = LogFactory.getLog(QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

}