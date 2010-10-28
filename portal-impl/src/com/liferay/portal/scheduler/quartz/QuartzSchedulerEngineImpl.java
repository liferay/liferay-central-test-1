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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.scheduler.job.MessageSenderJob;
import com.liferay.portal.service.QuartzLocalService;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Wesley Gong
 * @author Tina Tian
 */
public class QuartzSchedulerEngineImpl implements SchedulerEngine {

	public void afterPropertiesSet() {
		try {
			if (!PropsValues.SCHEDULER_ENABLED) {
				return;
			}

			StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

			schedulerFactory.initialize(
				PropsUtil.getProperties("org.quartz.", false));

			quartzLocalService.checkQuartzTables();

			_scheduler = schedulerFactory.getScheduler();
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public SchedulerRequest getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			JobDetail jobDetail = _scheduler.getJobDetail(
				jobName, groupName);

			if (jobDetail == null) {
				return null;
			}

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			String description = jobDataMap.getString(DESCRIPTION);
			String destination = jobDataMap.getString(DESTINATION);
			Message message = (Message)jobDataMap.get(MESSAGE);

			SchedulerRequest schedulerRequest = null;

			Trigger trigger = _scheduler.getTrigger(jobName, groupName);

			handleJobState(jobDetail.getFullName(), message, trigger);

			if (trigger == null) {
				schedulerRequest =
					SchedulerRequest.createRetrieveResponseRequest(
						jobName, groupName, description, destination, message);
			}
			else {
				if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {
					CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							new com.liferay.portal.kernel.scheduler.CronTrigger(
								jobName, groupName, cronTrigger.getStartTime(),
								cronTrigger.getEndTime(),
								cronTrigger.getCronExpression()),
							description, destination, message);
				}
				else if (SimpleTrigger.class.isAssignableFrom(
							trigger.getClass())) {

					SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(
						trigger);

					schedulerRequest =
						SchedulerRequest.createRetrieveResponseRequest(
							new IntervalTrigger(
								jobName, groupName,
								simpleTrigger.getStartTime(),
								simpleTrigger.getEndTime(),
								simpleTrigger.getRepeatInterval()),
							description, destination, message);
				}
			}

			return schedulerRequest;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to get job", se);
		}
	}

	public List<SchedulerRequest> getScheduledJobs() throws SchedulerException {
		try {
			String[] groupNames = _scheduler.getJobGroupNames();

			List<SchedulerRequest> schedulerRequests =
				new ArrayList<SchedulerRequest>();

			for (String groupName : groupNames) {
				schedulerRequests.addAll(getScheduledJobs(groupName));
			}

			return schedulerRequests;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to get jobs", se);
		}
	}

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		try {
			List<SchedulerRequest> schedulerRequests =
				new ArrayList<SchedulerRequest>();

			String[] jobNames = _scheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				SchedulerRequest schedulerRequest = getScheduledJob(
					jobName, groupName);

				if (schedulerRequest != null) {
					schedulerRequests.add(schedulerRequest);
				}
			}

			return schedulerRequests;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to get jobs", se);
		}
	}

	public void schedule(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			String description, String destination, Message message)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			String jobName = trigger.getJobName();
			String groupName = trigger.getGroupName();

			if (jobName.length() > JOB_NAME_MAX_LENGTH) {
				jobName = jobName.substring(0, JOB_NAME_MAX_LENGTH);
			}

			if (groupName.length() > GROUP_NAME_MAX_LENGTH) {
				groupName = groupName.substring(0, GROUP_NAME_MAX_LENGTH);
			}

			Trigger quartzTrigger = null;

			if (trigger.getTriggerType() == TriggerType.CRON) {
				try {
					quartzTrigger = new CronTrigger(
						jobName, groupName,
						(String)trigger.getTriggerContent());
				}
				catch (ParseException pe) {
					throw new SchedulerException(
						"Unable to parse cron text " +
							trigger.getTriggerContent());
				}
			}
			else if (trigger.getTriggerType() == TriggerType.SIMPLE) {
				long interval = (Long)trigger.getTriggerContent();

				if (interval <= 0) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Not scheduling " + trigger.getJobName() +
								" because interval is less than or equal to 0");
					}

					return;
				}

				quartzTrigger = new SimpleTrigger(
					jobName, groupName, SimpleTrigger.REPEAT_INDEFINITELY,
					interval);
			}
			else {
				throw new SchedulerException(
					"Unknown trigger type " + trigger.getTriggerType());
			}

			quartzTrigger.setJobName(jobName);
			quartzTrigger.setJobGroup(groupName);

			Date startDate = trigger.getStartDate();

			if (startDate == null) {
				if (ServerDetector.getServerId().equals(
						ServerDetector.TOMCAT_ID)) {

					quartzTrigger.setStartTime(
						new Date(System.currentTimeMillis() + Time.MINUTE));
				}
				else {
					quartzTrigger.setStartTime(
						new Date(
						System.currentTimeMillis() + Time.MINUTE * 3));
				}
			}
			else {
				quartzTrigger.setStartTime(startDate);
			}

			Date endDate = trigger.getEndDate();

			if (endDate != null) {
				quartzTrigger.setEndTime(endDate);
			}

			if ((description != null) &&
				(description.length() > DESCRIPTION_MAX_LENGTH)) {

				description = description.substring(0, DESCRIPTION_MAX_LENGTH);
			}

			if (message == null){
				message = new Message();
			}

			message.put(
				RECEIVER_KEY,
				jobName.concat(StringPool.COLON).concat(groupName));

			schedule(quartzTrigger, description, destination, message);
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			clearJobs();
			_scheduler.shutdown(false);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to shutdown scheduler", se);
		}
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_scheduler.start();
			clearJobs();
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to start scheduler", se);
		}
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		try {
			JobDetail jobDetail = _scheduler.getJobDetail(jobName, groupName);

			if (jobDetail == null) {
				return;
			}

			ObjectValuePair<TriggerState, Exception> jobState = getJobState(
				jobDetail.getFullName());

			if (jobState.getValue() != null) {
				setJobState(jobDetail.getFullName(), TriggerState.NORMAL, null);
			}
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException(
				"Unable to suppress error for job", se);
		}
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		unschedule(trigger.getJobName(), trigger.getGroupName());
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			JobDetail jobDetail = _scheduler.getJobDetail(jobName, groupName);

			if (jobDetail == null) {
				return;
			}

			setJobState(
				jobDetail.getFullName(), TriggerState.UNSCHEDULED, null);

			_scheduler.unscheduleJob(jobName, groupName);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				se);
		}
	}

	protected void clearJobs() throws SchedulerException {
		try {
			String[] groups = _scheduler.getJobGroupNames();

			for(String groupName : groups) {
				String[] jobNames = _scheduler.getJobNames(groupName);

				for (String jobName : jobNames) {
					_scheduler.deleteJob(jobName, groupName);
				}
			}
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to delete jobs", se);
		}
	}

	protected ObjectValuePair<TriggerState, Exception> getJobState(
			String jobFullName)
		throws SchedulerException {

		try {
			SchedulerContext schedulerContext = _scheduler.getContext();

			ObjectValuePair<TriggerState, Exception> jobState =
				(ObjectValuePair<TriggerState, Exception>)schedulerContext.get(
					jobFullName);

			return jobState;
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to get job state", se);
		}
	}

	protected void handleJobState(
			String fullName, Message message, Trigger trigger)
		throws SchedulerException {

		ObjectValuePair<TriggerState, Exception> jobState = getJobState(
			fullName);

		if (jobState == null) {
			return;
		}

		Exception exception = jobState.getValue();

		if (exception != null) {
			message.put(JOB_STATE, jobState);

			return;
		}

		TriggerState triggerState = jobState.getKey();

		try {
			if (trigger == null) {
				if (triggerState == null) {
					triggerState = TriggerState.COMPLETE;
				}
				else if (!triggerState.equals(TriggerState.UNSCHEDULED) &&
						 !triggerState.equals(TriggerState.ERROR)) {

					triggerState = TriggerState.COMPLETE;
				}
			}
			else {
				int quartzTriggerState = _scheduler.getTriggerState(
					trigger.getName(), trigger.getGroup());

				if (quartzTriggerState == Trigger.STATE_ERROR) {
					triggerState = TriggerState.ERROR;
				}
				else if (quartzTriggerState == Trigger.STATE_NORMAL) {
					triggerState = TriggerState.NORMAL;
				}
				else if (quartzTriggerState == Trigger.STATE_PAUSED) {
					triggerState = TriggerState.PAUSED;
				}
				else {
					triggerState = TriggerState.ERROR;

					exception = new SchedulerException(
						"Unable to handle Quartz job state " +
							quartzTriggerState);
				}
			}

			if (triggerState.equals(TriggerState.ERROR) &&
				exception == null) {

				exception =
					new SchedulerException(
						"Unable to get exception for job :" + fullName );
			}

			jobState = setJobState(fullName, triggerState, exception);

			message.put(JOB_STATE, jobState);
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to handle job state", se);
		}
	}

	protected void schedule(
			Trigger trigger, String description,
			String destination, Message message)
		throws SchedulerException {

		try {
			String jobName = trigger.getName();
			String groupName = trigger.getGroup();

			JobDetail jobDetail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			jobDetail.setDurability(true);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION, destination);
			jobDataMap.put(MESSAGE, message);

			SchedulerContext schedulerContext = _scheduler.getContext();

			schedulerContext.put(
				jobDetail.getFullName(),
				new ObjectValuePair<TriggerState, Exception>());

			synchronized (this) {
				_scheduler.deleteJob(jobName, groupName);
				_scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
		catch (org.quartz.SchedulerException se) {
			throw new SchedulerException("Unable to schedule job", se);
		}
	}

	protected ObjectValuePair<TriggerState, Exception> setJobState(
			String jobFullName, TriggerState state, Exception exception)
		throws SchedulerException {

		ObjectValuePair<TriggerState, Exception> jobState = getJobState(
			jobFullName);

		jobState.setKey(state);
		jobState.setValue(exception);

		return jobState;
	}

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService")
	protected QuartzLocalService quartzLocalService;

	private static Log _log = LogFactoryUtil.getLog(
		QuartzSchedulerEngineImpl.class);

	private Scheduler _scheduler;

}