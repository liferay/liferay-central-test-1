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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the user group remote service. This utility wraps {@link com.liferay.portal.service.impl.UserGroupServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserGroupServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupService
 * @see com.liferay.portal.service.base.UserGroupServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserGroupServiceImpl
 * @generated
 */
public class UserGroupServiceUtil {
	public static void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addGroupUserGroups(groupId, userGroupIds);
	}

	public static com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addUserGroup(name, description);
	}

	public static void deleteUserGroup(long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserGroup(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup getUserGroup(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroup(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroup(name);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserUserGroups(userId);
	}

	public static void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetGroupUserGroups(groupId, userGroupIds);
	}

	public static com.liferay.portal.model.UserGroup updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateUserGroup(userGroupId, name, description);
	}

	public static UserGroupService getService() {
		if (_service == null) {
			_service = (UserGroupService)PortalBeanLocatorUtil.locate(UserGroupService.class.getName());

			ReferenceRegistry.registerReference(UserGroupServiceUtil.class,
				"_service");
		}

		return _service;
	}

	public void setService(UserGroupService service) {
		_service = service;

		ReferenceRegistry.registerReference(UserGroupServiceUtil.class,
			"_service");
	}

	private static UserGroupService _service;
}