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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerRequest;

import java.util.List;

/**
 * @author Michael C. Han
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 */
public interface SchedulerEngine {

	public static final String DESCRIPTION = "description";

	public static final int DESCRIPTION_MAX_LENGTH = 120;

	public static final String DESTINATION = "destination";

	public static final String DISABLE = "disable";

	public static final int GROUP_NAME_MAX_LENGTH = 80;

	public static final int JOB_NAME_MAX_LENGTH = 80;

	public static final String MESSAGE = "message";

	public static final String MESSAGE_LISTENER_UUID = "message_listener_uuid";

	public static final String RECEIVER_KEY = "receiver_key";

	public List<SchedulerRequest> getScheduledJobs(String groupName)
		throws SchedulerException;

	public void schedule(
			Trigger trigger, String description, String destinationName,
			Message message)
		throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void start() throws SchedulerException;

	public void unschedule(String jobName, String groupName)
		throws SchedulerException;

	/**
	 * @deprecated {@link #unschedule(String, String)}
	 */
	public void unschedule(Trigger trigger) throws SchedulerException;

}