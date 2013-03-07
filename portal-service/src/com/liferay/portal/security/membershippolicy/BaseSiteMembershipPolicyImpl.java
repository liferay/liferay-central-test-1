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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseSiteMembershipPolicyImpl
	implements SiteMembershipPolicy {

	public boolean isMembershipAllowed(long userId, long groupId)
		throws PortalException, SystemException {

		try {
			checkMembership(new long[] {userId}, new long[] {groupId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isMembershipProtected(
			PermissionChecker permissionChecker, long userId, long groupId)
		throws PortalException, SystemException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteAdministratorRole.getRoleId())) {

			return true;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteOwnerRole.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean isMembershipRequired(long userId, long groupId)
		throws PortalException, SystemException {

		try {
			checkMembership(new long[] {userId}, null, new long[] {groupId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public boolean isRoleAllowed(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(userGroupRoles, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isRoleProtected(
			PermissionChecker permissionChecker, long userId, long groupId,
			long roleId)
		throws PortalException, SystemException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role role = RoleLocalServiceUtil.getRole(roleId);

		String roleName = role.getName();

		if (!roleName.equals(RoleConstants.SITE_ADMINISTRATOR) &&
			!roleName.equals(RoleConstants.SITE_OWNER)) {

			return false;
		}

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, roleId)) {

			return true;
		}

		return false;
	}

	public boolean isRoleRequired(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userId, groupId, roleId);

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.fetchUserGroupRole(userGroupRolePK);

		List<UserGroupRole> userGroupRoles = new ArrayList<UserGroupRole>();

		userGroupRoles.add(userGroupRole);

		try {
			checkRoles(null, userGroupRoles);
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public void verifyPolicy() throws PortalException, SystemException {
		long[] companyIds = PortalUtil.getCompanyIds();

		for (long companyId : companyIds) {
			LinkedHashMap<String, Object> groupParams =
				new LinkedHashMap<String, Object>();

			groupParams.put("site", Boolean.TRUE);

			int start = 0;
			int end = SEARCH_INTERVAL;

			int total = GroupLocalServiceUtil.getGroupsCount();

			while (start <= total) {
				List<Group> groups = GroupLocalServiceUtil.search(
					companyId, groupParams, start, end);

				for (Group group : groups) {
					verifyPolicy(group);

					List<UserGroupRole> userGroupRoles =
						UserGroupRoleLocalServiceUtil.getUserGroupRolesByGroup(
							group.getGroupId());

					for (UserGroupRole userGroupRole : userGroupRoles) {
						verifyPolicy(userGroupRole.getRole());
					}
				}

				start = end;
				end += end;
			}
		}
	}

	public void verifyPolicy(Group group)
		throws PortalException, SystemException {

		verifyPolicy(group, null, null, null, null, null);
	}

}