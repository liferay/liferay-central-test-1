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

package com.liferay.portlet.communities.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSender;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.communities.util.StagingUtil;

import java.util.Date;
import java.util.Map;

/**
 * <a href="LayoutsLocalPublisherMessageListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Bruno Farache
 */
public class LayoutsLocalPublisherMessageListener extends BaseMessageListener {

	public LayoutsLocalPublisherMessageListener() {
	}

	/**
	 * @deprecated
	 */
	public LayoutsLocalPublisherMessageListener(
		SingleDestinationMessageSender statusSender,
		MessageSender responseSender) {

		super(statusSender, responseSender);
	}

	protected void doReceive(Message message, MessageStatus messageStatus)
		throws Exception {

		LayoutsLocalPublisherRequest publisherRequest =
			(LayoutsLocalPublisherRequest)message.getPayload();

		messageStatus.setPayload(publisherRequest);

		String command = publisherRequest.getCommand();
		long userId = publisherRequest.getUserId();
		long sourceGroupId = publisherRequest.getSourceGroupId();
		long targetGroupId = publisherRequest.getTargetGroupId();
		boolean privateLayout = publisherRequest.isPrivateLayout();
		Map<Long, Boolean> layoutIdMap = publisherRequest.getLayoutIdMap();
		Map<String, String[]> parameterMap = publisherRequest.getParameterMap();
		Date startDate = publisherRequest.getStartDate();
		Date endDate = publisherRequest.getEndDate();

		String range = MapUtil.getString(parameterMap, "range");

		if (range.equals("last")) {
			int last = MapUtil.getInteger(parameterMap, "last");

			if (last > 0) {
				Date scheduledFireTime =
					publisherRequest.getScheduledFireTime();

				startDate = new Date(
					scheduledFireTime.getTime() - (last * Time.HOUR));

				endDate = scheduledFireTime;
			}
		}

		PrincipalThreadLocal.setName(userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, false);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		try {
			if (command.equals(
					LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES)) {

				StagingUtil.publishLayouts(
					sourceGroupId, targetGroupId, privateLayout, parameterMap,
					startDate, endDate);
			}
			else if (command.equals(
				LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES)) {

				StagingUtil.publishLayouts(
					sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
					parameterMap, startDate, endDate);
			}
		}
		finally {
			PrincipalThreadLocal.setName(null);
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}

}