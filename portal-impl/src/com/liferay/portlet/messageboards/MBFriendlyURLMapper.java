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

package com.liferay.portlet.messageboards;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="MBFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class MBFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String topLink = GetterUtil.getString(
			portletURL.getParameter("topLink"));

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/message_boards/search")) {
			friendlyURLPath = "/message_boards/search";
		}
		else if (strutsAction.equals("/message_boards/view")) {
			String categoryId = GetterUtil.getString(
				portletURL.getParameter("mbCategoryId"));

			if (Validator.isNotNull(categoryId) && !categoryId.equals("0")) {
				friendlyURLPath = "/message_boards/category/" + categoryId;

				portletURL.addParameterIncludedInPath("mbCategoryId");
			}
			else {
				friendlyURLPath = "/message_boards";

				if (Validator.isNotNull(topLink) &&
					!topLink.equals("message-boards-home")) {

					friendlyURLPath += StringPool.SLASH + topLink;
				}

				portletURL.addParameterIncludedInPath("topLink");

				if (categoryId.equals("0")) {
					portletURL.addParameterIncludedInPath("mbCategoryId");
				}
			}
		}
		else if (strutsAction.equals("/message_boards/view_message")) {
			String messageId = portletURL.getParameter("messageId");

			if (Validator.isNotNull(messageId)) {
				friendlyURLPath = "/message_boards/message/" + messageId;

				portletURL.addParameterIncludedInPath("messageId");
			}
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Struts action " + strutsAction +
						" does not have a friendly URL path ");
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			WindowState windowState = portletURL.getWindowState();

			if (!windowState.equals(WindowState.NORMAL)) {
				friendlyURLPath += StringPool.SLASH + windowState;
			}

			portletURL.addParameterIncludedInPath("p_p_id");

			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		addParameter(parameterMap, "p_p_id", _PORTLET_ID);
		addParameter(parameterMap, "p_p_lifecycle", "0");
		addParameter(parameterMap, "p_p_mode", PortletMode.VIEW);

		int x = friendlyURLPath.indexOf("/", 1);

		if ((x + 1) == friendlyURLPath.length()) {
			addParameter(parameterMap, "struts_action", "/message_boards/view");
			addParameter(
				parameterMap, "mbCategoryId",
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

			return;
		}

		int y = friendlyURLPath.indexOf("/", x + 1);

		if (y == -1) {
			y = friendlyURLPath.length();
		}

		int z = friendlyURLPath.indexOf("/", y + 1);

		if (z == -1) {
			z = friendlyURLPath.length();
		}

		String type = friendlyURLPath.substring(x + 1, y);

		if (type.equals("category")) {
			String categoryId =
				friendlyURLPath.substring(y + 1, z);

			addParameter(parameterMap, "struts_action", "/message_boards/view");
			addParameter(parameterMap, "mbCategoryId", categoryId);
		}
		else if (type.equals("message")) {
			String messageId =
				friendlyURLPath.substring(y + 1, z);

			addParameter(
				parameterMap, "struts_action", "/message_boards/view_message");
			addParameter(parameterMap, "messageId", messageId);
		}
		else if (type.equals("my-posts") || type.equals("my-subscriptions") ||
				 type.equals("recent-posts") || type.equals("statistics") ||
				 type.equals("banned-users")) {

			addParameter(parameterMap, "struts_action", "/message_boards/view");
			addParameter(parameterMap, "topLink", type);
		}
		else if (type.equals("search")) {
			addParameter(
				parameterMap, "struts_action", "/message_boards/search");
			addParameter(parameterMap, "topLink", "message-boards-home");
		}

		if (friendlyURLPath.indexOf("maximized", x) != -1) {
			addParameter(parameterMap, "p_p_state", WindowState.MAXIMIZED);
		}
	}

	private static final String _MAPPING = "message_boards";

	private static final String _PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	private static Log _log = LogFactoryUtil.getLog(MBFriendlyURLMapper.class);

}