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

package com.liferay.portal.kernel.scheduler;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerEventMessageListenerWrapper;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.lang.reflect.Proxy;

import java.util.List;
import java.util.Set;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public class SchedulerEngineUtil {

	public static SchedulerRequest getScheduledJob(
			String jobName, String groupName)
		throws SchedulerException {

		return _instance._getScheduledJob(jobName, groupName);
	}

	public static List<SchedulerRequest> getScheduledJobs()
		throws SchedulerException {

		return _instance._getScheduledJobs();
	}

	public static List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException {

		return _instance._getScheduledJobs(groupName);
	}

	public static void init(SchedulerEngine defaultScheduler) {
		_instance._init(defaultScheduler);
	}

	public static void schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		_instance._schedule(schedulerEntry, classLoader);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_instance._schedule(trigger, description, destinationName, message);
	}

	public static void schedule(
			Trigger trigger, String description, String destinationName,
			Object payload)
		throws SchedulerException {

		_instance._schedule(trigger, description, destinationName, payload);
	}

	public static void shutdown() throws SchedulerException {
		unschedule();

		_instance._shutdown();
	}

	public static void start() throws SchedulerException {
		_instance._start();

		unschedule();
	}

	public static void suppressError(String jobName, String groupName)
		throws SchedulerException {

		_instance._suppressError(jobName, groupName);
	}

	public static void unschedule() throws SchedulerException {
		List<SchedulerRequest> scheduledJobs = getScheduledJobs();

		for (SchedulerRequest scheduledJob : scheduledJobs) {
			String destinationName = scheduledJob.getDestination();

			if (destinationName.equals(DestinationNames.SCHEDULER_DISPATCH)) {
				String messageListenerUUID =
					(String)scheduledJob.getMessage().get(
						SchedulerEngine.MESSAGE_LISTENER_UUID);

				_instance._unregisterMessageListener(messageListenerUUID);
			}

			Trigger trigger = scheduledJob.getTrigger();

			if (trigger != null) {
				_instance._unschedule(
						trigger.getJobName(), trigger.getGroupName());
			}
		}
	}

	public static void unschedule(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		_instance._unschedule(schedulerEntry);
	}

	public static void unschedule(String jobName, String groupName)
		throws SchedulerException {

		_instance._unschedule(jobName, groupName);
	}

	public static void unschedule(
			String jobName, String groupName, String messageListenerUUID)
		throws SchedulerException {

		_instance._unregisterMessageListener(messageListenerUUID);
		_instance._unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public static void unschedule(Trigger trigger) throws SchedulerException {
		_instance._unschedule(trigger);
	}

	private SchedulerRequest _getScheduledJob(String jobName, String groupName)
		throws SchedulerException {

		SchedulerRequest schedulerRequest = _schedulerEngine.getScheduledJob(
			jobName, groupName);

		if ((schedulerRequest.getGroupName() == null) &&
			(schedulerRequest.getJobName() == null) &&
			(schedulerRequest.getTrigger() == null)) {

			return null;
		}

		return schedulerRequest;
	}

	private List<SchedulerRequest> _getScheduledJobs()
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs();
	}

	private List<SchedulerRequest> _getScheduledJobs(String groupName)
		throws SchedulerException {

		return _schedulerEngine.getScheduledJobs(groupName);
	}

	private void _init(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	private void _schedule(
			SchedulerEntry schedulerEntry, ClassLoader classLoader)
		throws SchedulerException {

		MessageListener schedulerEventListener = null;

		try {
			schedulerEventListener = (MessageListener)classLoader.loadClass(
				schedulerEntry.getEventListenerClass()).newInstance();

			schedulerEventListener = (MessageListener)Proxy.newProxyInstance(
				classLoader, new Class[] {MessageListener.class},
				new ClassLoaderBeanHandler(
					schedulerEventListener, classLoader));
		}
		catch (Exception e) {
			throw new SchedulerException(e);
		}

		String messageListenerUUID = PortalUUIDUtil.generate();

		schedulerEventListener = new SchedulerEventMessageListenerWrapper(
			schedulerEventListener, messageListenerUUID,
			schedulerEntry.getEventListenerClass());

		schedulerEntry.setEventListener(schedulerEventListener);

		MessageBusUtil.registerMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Message message = new Message();

		message.put(SchedulerEngine.MESSAGE_LISTENER_UUID, messageListenerUUID);

		_schedule(
			schedulerEntry.getTrigger(), schedulerEntry.getDescription(),
			DestinationNames.SCHEDULER_DISPATCH, message);
	}

	private void _schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException {

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	private void _schedule(
			Trigger trigger, String description, String destinationName,
			Object payload)
		throws SchedulerException {

		Message message = new Message();

		message.setPayload(payload);

		_schedulerEngine.schedule(
			trigger, description, destinationName, message);
	}

	private void _shutdown() throws SchedulerException {
		_schedulerEngine.shutdown();
	}

	private void _start() throws SchedulerException {
		_schedulerEngine.start();
	}

	private void _suppressError(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.suppressError(jobName, groupName);
	}

	private void _unregisterMessageListener(String messageListenerUUID)
		throws SchedulerException {

		Destination destination = MessageBusUtil.getMessageBus().getDestination(
			DestinationNames.SCHEDULER_DISPATCH);

		Set<MessageListener> messageListeners =
			destination.getMessageListeners();

		MessageListener targetMessageListener = null;

		for (MessageListener messageListener : messageListeners) {
			InvokerMessageListener invokerMessageListener =
				(InvokerMessageListener)messageListener;

			SchedulerEventMessageListenerWrapper schedulerMessageListener =
				(SchedulerEventMessageListenerWrapper)
				invokerMessageListener.getMessageListener();

			String uuid = schedulerMessageListener.getMessageListenerUUID();

			if (uuid.equals(messageListenerUUID)) {
				targetMessageListener = schedulerMessageListener;

				break;
			}
		}

		if (targetMessageListener != null) {
			MessageBusUtil.unregisterMessageListener(
				DestinationNames.SCHEDULER_DISPATCH, targetMessageListener);
		}
	}

	private void _unschedule(SchedulerEntry schedulerEntry)
		throws SchedulerException {

		MessageListener schedulerEventListener =
			schedulerEntry.getEventListener();

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SCHEDULER_DISPATCH, schedulerEventListener);

		Trigger trigger = schedulerEntry.getTrigger();

		_unschedule(trigger.getJobName(), trigger.getGroupName());
	}

	private void _unschedule(String jobName, String groupName)
		throws SchedulerException {

		_schedulerEngine.unschedule(jobName, groupName);
	}

	/**
	 * @deprecated {@link #_unschedule(String, String)}
	 */
	private void _unschedule(Trigger trigger) throws SchedulerException {
		_schedulerEngine.unschedule(trigger);
	}

	private static SchedulerEngineUtil _instance = new SchedulerEngineUtil();

	private SchedulerEngine _schedulerEngine;

}