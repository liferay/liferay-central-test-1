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

package com.liferay.portlet.mobiledevicerules.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Edward Han
 */
public class MobileDeviceRulesPermissionImpl
	implements MobileDeviceRulesPermission {

	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		boolean hasPermission = contains(permissionChecker, groupId, actionId);

		if (!hasPermission) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return permissionChecker.hasPermission(
			groupId, MOBILE_NAME, groupId, actionId);
	}

	private static final String MOBILE_NAME =
		"com.liferay.portlet.mobiledevicerules";

}