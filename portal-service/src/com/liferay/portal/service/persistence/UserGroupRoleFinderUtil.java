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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UserGroupRoleFinderUtil {
	public static java.util.List<com.liferay.portal.model.UserGroupRole> findByUserUserGroupGroupRole(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserUserGroupGroupRole(userId, groupId);
	}

	public static UserGroupRoleFinder getFinder() {
		if (_finder == null) {
			_finder = (UserGroupRoleFinder)PortalBeanLocatorUtil.locate(UserGroupRoleFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(UserGroupRoleFinder finder) {
		_finder = finder;
	}

	private static UserGroupRoleFinder _finder;
}