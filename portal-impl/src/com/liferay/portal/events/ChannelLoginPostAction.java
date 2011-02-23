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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class ChannelLoginPostAction extends Action {

	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = PortalUtil.getUser(request);

			if (!user.isDefaultUser()) {
				ChannelHubManagerUtil.getChannel(
					user.getCompanyId(), user.getUserId(), true);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ChannelLoginPostAction.class);

}