/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;

/**
 * @author Manuel de la Peña
 */
public class GroupTestUtil {

	public static Group addGroup() throws Exception {
		return addGroup(ServiceTestUtil.randomString());
	}

	public static Group addGroup(long parentGroupId, String name)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;

		return GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), parentGroupId, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, description, type,
			friendlyURL, site, active, ServiceTestUtil.getServiceContext());
	}

	public static Group addGroup(String name) throws Exception {
		return addGroup(GroupConstants.DEFAULT_PARENT_GROUP_ID, name);
	}

	public static Group addGroup(
		String name, long parentGroupId, ServiceContext serviceContext)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;

		if (serviceContext == null) {
			serviceContext = ServiceTestUtil.getServiceContext();
		}

		return GroupServiceUtil.addGroup(
			parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, name,
			description, type, friendlyURL, site, active, serviceContext);
	}

}