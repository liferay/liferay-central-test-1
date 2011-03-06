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

package com.liferay.portal.scheduler.quartz;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
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
import java.util.Properties;

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
public class QuartzSchedulerEngine implements SchedulerEngine {

	public void afterPropertiesSet() {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			quartzLocalService.checkQuartzTables();

			_persistedScheduler = initializeScheduler(
				"persisted.scheduler.", true);

			initJobState();

			_memoryScheduler = initializeScheduler("memory.scheduler.", false);
		}
		catch (Exception e) {
			_log.error("Unable to initialize engine", e);
		}
	}

	public void delete(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			delete(scheduler, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to delete jobs", e);
		}
	}

	public void delete(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			delete(scheduler, jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to delete job {jobName=" + jobName + ", groupName=" +
					groupName + "}",
				e);
		}
	}

	public void destroy() {
		try {
			shutdown();
		}
		catch (SchedulerException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to shutdown", se);
			}
		}
	}

	public SchedulerResponse getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			return getScheduledJob(scheduler, jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get job", e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs()
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			String[] groupNames = _persistedScheduler.getJobGroupNames();

			List<SchedulerResponse> schedulerResponses =
				new ArrayList<SchedulerResponse>();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_persistedScheduler, groupName));
			}

			groupNames = _memoryScheduler.getJobGroupNames();

			for (String groupName : groupNames) {
				schedulerResponses.addAll(
					getScheduledJobs(_memoryScheduler, groupName));
			}

			return schedulerResponses;
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	public List<SchedulerResponse> getScheduledJobs(String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return null;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			return getScheduledJobs(scheduler, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to get jobs", e);
		}
	}

	public void pause(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			pause(scheduler, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to pause jobs", e);
		}
	}

	public void pause(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			pause(scheduler, jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to pause job", e);
		}
	}

	public void resume(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			resume(scheduler, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to resume jobs", e);
		}
	}

	public void resume(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			resume(scheduler, jobName, groupName);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to resume job", e);
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
			Scheduler scheduler = getScheduler(trigger.getGroupName());

			StorageType storageType = getStorageType(trigger.getGroupName());

			trigger = TriggerFactoryUtil.buildTrigger(
				trigger.getTriggerType(), trigger.getJobName(),
				getOriginalGroupName(trigger.getGroupName()),
				trigger.getStartDate(), trigger.getEndDate(),
				trigger.getTriggerContent());

			Trigger quartzTrigger = getQuartzTrigger(trigger);

			if (quartzTrigger == null) {
				return;
			}

			description = fixMaxLength(description, DESCRIPTION_MAX_LENGTH);

			if (message == null){
				message = new Message();
			}

			message.put(RECEIVER_KEY, quartzTrigger.getFullJobName());

			if (isPersisted(destination, message)) {
				message.put(PERSISTED, true);
			}

			schedule(
				scheduler, storageType, quartzTrigger, description, destination,
				message);
		}
		catch (RuntimeException re) {

			// ServerDetector will throw an exception when JobSchedulerImpl is
			// initialized in a test environment

		}
		catch (Exception e) {
			throw new SchedulerException("Unable to schedule job", e);
		}
	}

	public void shutdown() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			if (!_persistedScheduler.isShutdown()) {
				_persistedScheduler.shutdown(false);
			}

			if (!_memoryScheduler.isShutdown()) {
				_memoryScheduler.shutdown(false);
			}
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to shutdown scheduler", e);
		}
	}

	public void start() throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			_persistedScheduler.start();

			cleanTemporaryJobs();

			_memoryScheduler.start();
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to start scheduler", e);
		}
	}

	public void suppressError(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			suppressError(jobName, groupName, scheduler);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to suppress error for job", e);
		}
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		unschedule(trigger.getJobName(), trigger.getGroupName());
	}

	public void unschedule(String groupName) throws SchedulerException {
		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			unschedule(groupName, scheduler);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to unschedule jobs", e);
		}
	}

	public void unschedule(String jobName, String groupName)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(groupName);

			unschedule(jobName, groupName, scheduler);
		}
		catch (Exception e) {
			throw new SchedulerException(
				"Unable to unschedule job {jobName=" + jobName +
					", groupName=" + groupName + "}",
				e);
		}
	}

	public void update(com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (!PropsValues.SCHEDULER_ENABLED) {
			return;
		}

		try {
			Scheduler scheduler = getScheduler(trigger.getGroupName());

			trigger = TriggerFactoryUtil.buildTrigger(
				trigger.getTriggerType(), trigger.getJobName(),
				getOriginalGroupName(trigger.getGroupName()),
				trigger.getStartDate(), trigger.getEndDate(),
				trigger.getTriggerContent());

			update(trigger, scheduler);
		}
		catch (Exception e) {
			throw new SchedulerException("Unable to update trigger", e);
		}
	}

	protected void cleanTemporaryJobs() throws Exception {
		String[] groupNames = _persistedScheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			String[] jobNames = _persistedScheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				JobDetail jobDetail = _persistedScheduler.getJobDetail(
					jobName, groupName);

				if (jobDetail == null) {
					continue;
				}

				JobDataMap jobDataMap = jobDetail.getJobDataMap();

				Message message = getMessage(jobDataMap);

				if (!message.getBoolean(PERSISTED)) {
					_persistedScheduler.deleteJob(jobName, groupName);
				}
			}
		}
	}

	protected void delete(Scheduler scheduler, String groupName)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		String[] jobNames = scheduler.getJobNames(groupName);

		for (String jobName : jobNames) {
			delete(scheduler, jobName, groupName);
		}
	}

	protected void delete(Scheduler scheduler, String jobName, String groupName)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		SchedulerContext schedulerContext = scheduler.getContext();

		schedulerContext.remove(getFullName(jobName, groupName));

		scheduler.deleteJob(jobName, groupName);
	}

	protected String fixMaxLength(String argument, int maxLength) {
		if (argument == null) {
			return null;
		}

		if (argument.length() > maxLength) {
			argument = argument.substring(0, maxLength);
		}

		return argument;
	}

	protected String getFullName(String jobName, String groupName) {
		return groupName.concat(StringPool.PERIOD).concat(jobName);
	}

	protected JobState getJobState(
			Scheduler scheduler, String jobName, String groupName)
		throws Exception {

		groupName = getOriginalGroupName(groupName);

		SchedulerContext schedulerContext = scheduler.getContext();

		JobState jobState = (JobState)schedulerContext.get(
			getFullName(jobName, groupName));

		return jobState;
	}

	protected Message getMessage(JobDataMap jobDataMap) {
		String messageJSON = (String)jobDataMap.get(MESSAGE);

		return (Message)JSONFactoryUtil.deserialize(messageJSON);
	}

	protected String getOriginalGroupName(String groupName) {
		if (groupName.startsWith(
				StorageType.MEMORY_MULTIPLE_INSTANCES.toString())) {

			return groupName.substring(
				StorageType.MEMORY_MULTIPLE_INSTANCES.toString().length() + 1);
		}
		else if (groupName.startsWith(
					StorageType.MEMORY_SINGLE_INSTANCE.toString())) {

			return groupName.substring(
				StorageType.MEMORY_SINGLE_INSTANCE.toString().length() + 1);
		}
		else if (groupName.startsWith(StorageType.PERSISTED.toString())) {
			return groupName.substring(
				StorageType.PERSISTED.toString().length() + 1);
		}

		return groupName;
	}

	protected Trigger getQuartzTrigger(
			com.liferay.portal.kernel.scheduler.Trigger trigger)
		throws SchedulerException {

		if (trigger == null) {
			return null;
		}

		String jobName = fixMaxLength(
			trigger.getJobName(), JOB_NAME_MAX_LENGTH);
		String groupName = fixMaxLength(
			trigger.getGroupName(), GROUP_NAME_MAX_LENGTH);

		Trigger quartzTrigger = null;

		TriggerType triggerType = trigger.getTriggerType();

		if (triggerType.equals(TriggerType.CRON)) {
			try {
				quartzTrigger = new CronTrigger(
					jobName, groupName, (String)trigger.getTriggerContent());
			}
			catch (ParseException pe) {
				throw new SchedulerException(
					"Unable to parse cron text " + trigger.getTriggerContent());
			}
		}
		else if (triggerType.equals(TriggerType.SIMPLE)) {
			long interval = (Long)trigger.getTriggerContent();

			if (interval <= 0) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Not scheduling " + trigger.getJobName() +
							" because interval is less than or equal to 0");
				}

				return null;
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
			if (ServerDetector.getServerId().equals(ServerDetector.TOMCAT_ID)) {
				quartzTrigger.setStartTime(
					new Date(System.currentTimeMillis() + Time.MINUTE));
			}
			else {
				quartzTrigger.setStartTime(
					new Date(System.currentTimeMillis() + Time.MINUTE * 3));
			}
		}
		else {
			quartzTrigger.setStartTime(startDate);
		}

		Date endDate = trigger.getEndDate();

		if (endDate != null) {
			quartzTrigger.setEndTime(endDate);
		}

		return quartzTrigger;
	}

	protected SchedulerResponse getScheduledJob(
			Scheduler scheduler, String jobName, String groupName)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		JobDetail jobDetail = scheduler.getJobDetail(
			jobName, groupName);

		if (jobDetail == null) {
			return null;
		}

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		String description = jobDataMap.getString(DESCRIPTION);
		String destinationName = jobDataMap.getString(DESTINATION_NAME);
		Message message = getMessage(jobDataMap);
		StorageType storageType = StorageType.valueOf(
			jobDataMap.getString(STORAGE_TYPE));

		SchedulerResponse schedulerResponse = null;

		Trigger trigger = scheduler.getTrigger(jobName, groupName);

		handleJobState(scheduler, jobName, groupName, message, trigger);

		if (trigger == null) {
			schedulerResponse = new SchedulerResponse();

			schedulerResponse.setDescription(description);
			schedulerResponse.setDestinationName(destinationName);
			schedulerResponse.setGroupName(groupName);
			schedulerResponse.setJobName(jobName);
			schedulerResponse.setMessage(message);
			schedulerResponse.setStorageType(storageType);
		}
		else {
			if (CronTrigger.class.isAssignableFrom(trigger.getClass())) {

				CronTrigger cronTrigger = CronTrigger.class.cast(trigger);

				schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setStorageType(storageType);
				schedulerResponse.setTrigger(
					new com.liferay.portal.kernel.scheduler.CronTrigger(
						jobName, groupName, cronTrigger.getStartTime(),
						cronTrigger.getEndTime(),
						cronTrigger.getCronExpression()));
			}
			else if (SimpleTrigger.class.isAssignableFrom(
						trigger.getClass())) {

				SimpleTrigger simpleTrigger = SimpleTrigger.class.cast(
					trigger);

				schedulerResponse = new SchedulerResponse();

				schedulerResponse.setDescription(description);
				schedulerResponse.setDestinationName(destinationName);
				schedulerResponse.setMessage(message);
				schedulerResponse.setStorageType(storageType);
				schedulerResponse.setTrigger(
					new IntervalTrigger(
						jobName, groupName, simpleTrigger.getStartTime(),
						simpleTrigger.getEndTime(),
						simpleTrigger.getRepeatInterval()));
			}
		}

		return schedulerResponse;
	}

	protected List<SchedulerResponse> getScheduledJobs(
			Scheduler scheduler, String groupName)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		List<SchedulerResponse> schedulerResponses =
			new ArrayList<SchedulerResponse>();

		String[] jobNames = scheduler.getJobNames(groupName);

		for (String jobName : jobNames) {
			SchedulerResponse schedulerResponse = getScheduledJob(
				scheduler, jobName, groupName);

			if (schedulerResponse != null) {
				schedulerResponses.add(schedulerResponse);
			}
		}

		return schedulerResponses;
	}

	protected Scheduler getScheduler(String groupName) throws Exception {
		if (groupName.startsWith(StorageType.PERSISTED.toString())) {
			return _persistedScheduler;
		}
		else {
			return _memoryScheduler;
		}
	}

	protected StorageType getStorageType(String groupName) throws Exception {
		if (groupName.startsWith(StorageType.PERSISTED.toString())) {
			return StorageType.PERSISTED;
		}
		else if (groupName.startsWith(
					StorageType.MEMORY_MULTIPLE_INSTANCES.toString())) {

			return StorageType.MEMORY_MULTIPLE_INSTANCES;
		}
		else if (groupName.startsWith(
					StorageType.MEMORY_SINGLE_INSTANCE.toString())) {

			return StorageType.MEMORY_SINGLE_INSTANCE;
		}

		throw new Exception("Unable to get storage type");
	}

	protected void handleJobState(
			Scheduler scheduler, String jobName, String groupName,
			Message message, Trigger trigger)
		throws Exception {

		JobState jobState = getJobState(scheduler, jobName, groupName);

		if (trigger != null) {
			message.put(END_TIME, trigger.getEndTime());
			message.put(FINAL_FIRE_TIME, trigger.getFinalFireTime());
			message.put(NEXT_FIRE_TIME, trigger.getNextFireTime());
			message.put(PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
			message.put(START_TIME, trigger.getStartTime());
		}
		else {
			TriggerState triggerState = jobState.getTriggerState();

			if (triggerState.equals(TriggerState.NORMAL)) {
				jobState.setTriggerState(TriggerState.COMPLETE);
			}
		}

		message.put(JOB_STATE, jobState.clone());
	}

	protected Scheduler initializeScheduler(
			String propertiesPrefix, boolean useQuartzCluster)
		throws Exception {

		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

		Properties properties = PropsUtil.getProperties(propertiesPrefix, true);

		if (useQuartzCluster && PropsValues.CLUSTER_LINK_ENABLED) {
			String dbType = DBFactoryUtil.getDB().getType();

			if (dbType.equals(DB.TYPE_HYPERSONIC)) {
				_log.error("Unable to cluster scheduler on HSQL");
			}
			else {
				properties.put(
					"org.quartz.jobStore.isClustered", Boolean.TRUE.toString());
			}
		}

		schedulerFactory.initialize(properties);

		return schedulerFactory.getScheduler();
	}

	protected void initJobState() throws Exception {
		String[] groupNames = _persistedScheduler.getJobGroupNames();

		for (String groupName : groupNames) {
			String[] jobNames = _persistedScheduler.getJobNames(groupName);

			for (String jobName : jobNames) {
				initJobState(jobName, groupName);
			}
		}
	}

	protected void initJobState(String jobName, String groupName)
		throws Exception {

		JobDetail jobDetail = _persistedScheduler.getJobDetail(
			jobName, groupName);

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		Message message = getMessage(jobDataMap);

		if (!message.getBoolean(PERSISTED)) {
			return;
		}

		Trigger trigger = _persistedScheduler.getTrigger(jobName, groupName);

		JobState jobState = null;

		if (trigger != null) {
			jobState = new JobState(
				TriggerState.NORMAL, message.getInteger(EXCEPTIONS_MAX_SIZE));

			jobState.setTriggerTimeInfomation(
				SchedulerEngine.END_TIME, trigger.getEndTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.FINAL_FIRE_TIME, trigger.getFinalFireTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.NEXT_FIRE_TIME, null);
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.PREVIOUS_FIRE_TIME,
				trigger.getPreviousFireTime());
			jobState.setTriggerTimeInfomation(
				SchedulerEngine.START_TIME, trigger.getStartTime());

			jobDataMap.put(JOB_STATE, jobState);

			_persistedScheduler.addJob(jobDetail, true);
		}
		else {
			jobState = (JobState)jobDataMap.get(JOB_STATE);

			if (jobState == null) {
				throw new Exception(
					"Unable to get trigger details of job {jobName=" +
						jobName + ", groupName=" + groupName + "}");
			}
		}

		SchedulerContext schedulerContext = _persistedScheduler.getContext();

		schedulerContext.put(jobDetail.getFullName(), jobState);
	}

	protected boolean isPersisted(String destinationName, Message message) {
		if (destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
			return false;
		}
		else if (destinationName.equals(DestinationNames.SCHEDULER_SCRIPTING) &&
				 !message.getBoolean(SchedulerEngine.PERSISTED)) {

			return false;
		}
		else {
			return true;
		}
	}

	protected void pause(Scheduler scheduler, String groupName)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		String[] jobNames = scheduler.getJobNames(groupName);

		for (String jobName : jobNames) {
			JobState jobState = getJobState(scheduler, jobName, groupName);

			if (jobState != null) {
				jobState.setTriggerState(TriggerState.PAUSED);
			}
		}

		scheduler.pauseJobGroup(groupName);
	}

	protected void pause(Scheduler scheduler, String jobName, String groupName)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		JobState jobState = getJobState(scheduler, jobName, groupName);

		if (jobState != null) {
			jobState.setTriggerState(TriggerState.PAUSED);
		}

		scheduler.pauseJob(jobName, groupName);
	}

	protected void resume(Scheduler scheduler, String groupName)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		String[] jobNames = _persistedScheduler.getJobNames(groupName);

		for (String jobName : jobNames) {
			JobState jobState = getJobState(scheduler, jobName, groupName);

			if (jobState != null) {
				jobState.setTriggerState(TriggerState.NORMAL);
			}
		}

		scheduler.resumeJobGroup(groupName);
	}

	protected void resume(Scheduler scheduler, String jobName, String groupName)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		JobState jobState = getJobState(scheduler, jobName, groupName);

		if (jobState != null) {
			jobState.setTriggerState(TriggerState.NORMAL);
		}

		scheduler.resumeJob(jobName, groupName);
	}

	protected void schedule(
			Scheduler scheduler, StorageType storageType, Trigger trigger,
			String description, String destinationName, Message message)
		throws Exception {

		try {
			String jobName = trigger.getName();
			String groupName = trigger.getGroup();

			JobDetail jobDetail = new JobDetail(
				jobName, groupName, MessageSenderJob.class);

			JobDataMap jobDataMap = jobDetail.getJobDataMap();

			jobDataMap.put(DESCRIPTION, description);
			jobDataMap.put(DESTINATION_NAME, destinationName);
			jobDataMap.put(MESSAGE, JSONFactoryUtil.serialize(message));
			jobDataMap.put(STORAGE_TYPE, storageType.toString());

			JobState jobState = new JobState(
				TriggerState.NORMAL, message.getInteger(EXCEPTIONS_MAX_SIZE));

			SchedulerContext schedulerContext = scheduler.getContext();

			schedulerContext.put(jobDetail.getFullName(), jobState);

			if (scheduler == _persistedScheduler) {
				jobDetail.setDurability(true);
			}

			synchronized (this) {
				scheduler.deleteJob(jobName, groupName);
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}
		catch (ObjectAlreadyExistsException oare) {
			if (_log.isInfoEnabled()) {
				_log.info("Message is already scheduled");
			}
		}
	}

	protected void suppressError(
			String jobName, String groupName, Scheduler scheduler)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		JobState jobState = getJobState(scheduler, jobName, groupName);

		if (jobState != null) {
			jobState.clearExceptions();
		}
	}

	protected void unschedule(String groupName, Scheduler scheduler)
		throws Exception {

		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		String[] jobNames = scheduler.getJobNames(groupName);

		for (String jobName : jobNames) {
			unschedule(jobName, groupName, scheduler);
		}
	}

	protected void unschedule(
			String jobName, String groupName, Scheduler scheduler)
		throws Exception {

		jobName = fixMaxLength(jobName, JOB_NAME_MAX_LENGTH);
		groupName = fixMaxLength(
			getOriginalGroupName(groupName), GROUP_NAME_MAX_LENGTH);

		JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);

		if (jobDetail == null) {
			return;
		}

		if (scheduler == _memoryScheduler) {
			scheduler.unscheduleJob(jobName, groupName);

			return;
		}

		JobState jobState = getJobState(scheduler, jobName, groupName);

		Trigger trigger = scheduler.getTrigger(jobName, groupName);

		Date previousFireTime = trigger.getPreviousFireTime();

		jobState.setTriggerTimeInfomation(END_TIME, new Date());
		jobState.setTriggerTimeInfomation(
			FINAL_FIRE_TIME, previousFireTime);
		jobState.setTriggerTimeInfomation(NEXT_FIRE_TIME, null);
		jobState.setTriggerTimeInfomation(
			PREVIOUS_FIRE_TIME, previousFireTime);
		jobState.setTriggerTimeInfomation(
			START_TIME, trigger.getStartTime());

		jobState.setTriggerState(TriggerState.UNSCHEDULED);

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		Message message = getMessage(jobDataMap);

		if (message.getBoolean(PERSISTED)) {
			JobState jobStateClone = (JobState)jobState.clone();

			jobStateClone.clearExceptions();

			jobDataMap.put(JOB_STATE, jobStateClone);
		}

		scheduler.unscheduleJob(jobName, groupName);

		scheduler.addJob(jobDetail, true);
	}

	protected void update(
			com.liferay.portal.kernel.scheduler.Trigger trigger,
			Scheduler scheduler)
		throws Exception {

		Trigger quartzTrigger = getQuartzTrigger(trigger);

		if (quartzTrigger == null) {
			return;
		}

		String jobName = quartzTrigger.getJobName();
		String groupName = quartzTrigger.getGroup();

		if (scheduler.getTrigger(jobName, groupName) != null) {
			scheduler.rescheduleJob(jobName, groupName, quartzTrigger);
		}
		else {
			JobDetail jobDetail = scheduler.getJobDetail(
				jobName, groupName);

			if (jobDetail == null) {
				return;
			}

			JobState jobState = getJobState(scheduler, jobName, groupName);

			jobState.setTriggerState(TriggerState.NORMAL);

			synchronized (this) {
				scheduler.deleteJob(jobName, groupName);
				scheduler.scheduleJob(jobDetail, quartzTrigger);
			}
		}
	}

	@BeanReference(name = "com.liferay.portal.service.QuartzLocalService")
	protected QuartzLocalService quartzLocalService;

	private static Log _log = LogFactoryUtil.getLog(
		QuartzSchedulerEngine.class);

	private Scheduler _memoryScheduler;
	private Scheduler _persistedScheduler;

}