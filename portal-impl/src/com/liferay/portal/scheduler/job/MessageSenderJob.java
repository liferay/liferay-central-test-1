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

package com.liferay.portal.scheduler.job;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.scheduler.JobState;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.util.PropsValues;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.Trigger;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 */
public class MessageSenderJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) {
		try {
			doExecute(jobExecutionContext);
		}
		catch (Exception e) {
			_log.error("Unable to execute job", e);
		}
	}

	protected void doExecute(JobExecutionContext jobExecutionContext)
		throws Exception {

		JobDetail jobDetail = jobExecutionContext.getJobDetail();

		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		String destinationName = jobDataMap.getString(
			SchedulerEngine.DESTINATION_NAME);

		String messageJSON = (String)jobDataMap.get(SchedulerEngine.MESSAGE);

		Message message = null;

		if (messageJSON == null) {
			message = new Message();
		}
		else {
			message = (Message)JSONFactoryUtil.deserialize(messageJSON);
		}

		message.put(SchedulerEngine.DESTINATION_NAME, destinationName);

		Scheduler scheduler = jobExecutionContext.getScheduler();

		SchedulerContext schedulerContext = scheduler.getContext();

		JobState jobState = (JobState)schedulerContext.get(
			jobDetail.getFullName());

		if (jobExecutionContext.getNextFireTime() == null) {
			Trigger trigger = jobExecutionContext.getTrigger();

			StorageType storageType = StorageType.valueOf(
				jobDataMap.getString(SchedulerEngine.STORAGE_TYPE));

			if (storageType.equals(StorageType.PERSISTED)) {
				JobState jobStateClone = updatePersistedJobState(
					jobState, trigger);

				jobDataMap.put(SchedulerEngine.JOB_STATE, jobStateClone);

				scheduler.addJob(jobDetail, true);
			}
			else {
				message.put(SchedulerEngine.DISABLE, true);

				if (PropsValues.CLUSTER_LINK_ENABLED &&
					storageType.equals(StorageType.MEMORY_SINGLE_INSTANCE)) {

					notifyClusterMember(
						trigger.getJobName(), trigger.getGroup(), storageType);
				}
			}
		}

		message.put(SchedulerEngine.JOB_STATE, jobState);

		MessageBusUtil.sendMessage(destinationName, message);
	}

	protected void notifyClusterMember(
			String jobName, String groupName, StorageType storageType)
		throws Exception {

		MethodHandler methodHandler = new MethodHandler(
			_deleteJobMethodKey, jobName, groupName, storageType);

		ClusterRequest clusterRequest =
			ClusterRequest.createMulticastRequest(methodHandler, true);

		ClusterExecutorUtil.execute(clusterRequest);
	}

	protected JobState updatePersistedJobState(
		JobState jobState, Trigger trigger) {

		jobState.setTriggerState(TriggerState.COMPLETE);
		jobState.setTriggerTimeInfomation(
			SchedulerEngine.END_TIME, trigger.getEndTime());
		jobState.setTriggerTimeInfomation(
			SchedulerEngine.FINAL_FIRE_TIME, trigger.getFinalFireTime());
		jobState.setTriggerTimeInfomation(SchedulerEngine.NEXT_FIRE_TIME, null);
		jobState.setTriggerTimeInfomation(
			SchedulerEngine.PREVIOUS_FIRE_TIME, trigger.getPreviousFireTime());
		jobState.setTriggerTimeInfomation(
			SchedulerEngine.START_TIME, trigger.getStartTime());

		JobState jobStateClone = (JobState)jobState.clone();

		jobStateClone.clearExceptions();

		return jobStateClone;
	}

	private static Log _log = LogFactoryUtil.getLog(MessageSenderJob.class);

	private static MethodKey _deleteJobMethodKey = new MethodKey(
		SchedulerEngineUtil.class.getName(), "delete", String.class,
		String.class, StorageType.class);

}