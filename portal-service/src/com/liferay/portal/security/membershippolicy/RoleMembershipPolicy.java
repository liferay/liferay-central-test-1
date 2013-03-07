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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Roberto Díaz
 */
public interface RoleMembershipPolicy {

	public static final int SEARCH_INTERVAL = 500;

	public void checkAddRoles(long[] userIds, long[] roleIds)
		throws PortalException, SystemException;

	public void checkRemoveRoles(long[] userIds, long[] roleIds)
		throws PortalException, SystemException;

	boolean isRoleAllowed(long userId, long roleId)
		throws PortalException, SystemException;

	boolean isRoleRequired(long userId, long roleId)
		throws PortalException, SystemException;

	public void propagateAddRoles(long[] userIds, long roleId)
		throws PortalException, SystemException;

	public void propagateRemoveRoles(long[] userIds, long roleId)
		throws PortalException, SystemException;

	public void verifyPolicy() throws PortalException, SystemException;

	public void verifyPolicy(Role role) throws PortalException, SystemException;

	public void verifyUpdatePolicy(
			Role role, Role oldRole,
			Map<String, Serializable> oldExpandoAttributes)
		throws PortalException, SystemException;

}