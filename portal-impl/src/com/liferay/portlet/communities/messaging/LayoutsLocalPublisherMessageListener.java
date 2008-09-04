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

package com.liferay.portlet.communities.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.MapUtil;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutsLocalPublisherMessageListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LayoutsLocalPublisherMessageListener implements MessageListener {

	public void receive(Message message) {
		PermissionChecker permissionChecker = null;

		try {
			LayoutsLocalPublisherRequest publisherRequest =
				(LayoutsLocalPublisherRequest)JSONFactoryUtil.deserialize(
					(String)message.getPayload());

			String command = publisherRequest.getCommand();

			long userId = publisherRequest.getUserId();
			long sourceGroupId = publisherRequest.getSourceGroupId();
			long targetGroupId = publisherRequest.getTargetGroupId();
			boolean privateLayout = publisherRequest.isPrivateLayout();
			Map<Long, Boolean> layoutIdMap = publisherRequest.getLayoutIdMap();
			Map<String, String[]> parameterMap =
				publisherRequest.getParameterMap();
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

			permissionChecker = PermissionCheckerFactory.create(user, false);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

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
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			try {
				PermissionCheckerFactory.recycle(permissionChecker);
			}
			catch (Exception e) {
			}
		}
	}

	private static Log _log =
		LogFactory.getLog(LayoutsLocalPublisherMessageListener.class);

}