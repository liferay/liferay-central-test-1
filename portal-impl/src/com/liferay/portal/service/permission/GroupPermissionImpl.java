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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class GroupPermissionImpl implements GroupPermission {

	public void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, group, actionId)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException, SystemException {

		long groupId = group.getGroupId();

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isUser()) {

			// An individual user would never reach this block because he would
			// be an administrator of his own layouts. However, a user who
			// manages a set of organizations may be modifying pages of a user
			// he manages.

			User user = UserLocalServiceUtil.getUserById(group.getClassPK());

			if ((permissionChecker.getUserId() != user.getUserId()) &&
				 UserPermissionUtil.contains(
					permissionChecker, user.getUserId(),
					user.getOrganizationIds(), ActionKeys.UPDATE)) {

				return true;
			}
		}

		if (actionId.equals(ActionKeys.ADD_LAYOUT) &&
			permissionChecker.hasPermission(
				groupId, Group.class.getName(), groupId,
				ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}
		else if ((actionId.equals(ActionKeys.EXPORT_IMPORT_LAYOUTS) ||
				  actionId.equals(ActionKeys.EXPORT_IMPORT_PORTLET_INFO)) &&
				 permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.PUBLISH_STAGING)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW) &&
				 permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.ASSIGN_USER_ROLES)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.VIEW_STAGING) &&
				 (permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.MANAGE_LAYOUTS) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.MANAGE_STAGING) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.PUBLISH_STAGING) ||
				  permissionChecker.hasPermission(
					 groupId, Group.class.getName(), groupId,
					 ActionKeys.UPDATE))) {

			return true;
		}

		// Group id must be set so that users can modify their personal pages

		if (permissionChecker.hasPermission(
				groupId, Group.class.getName(), groupId, actionId)) {

			return true;
		}

		while (!group.isRoot()) {
			if (contains(
					permissionChecker, group.getParentGroupId(),
					ActionKeys.MANAGE_SUBGROUPS)) {

				return true;
			}

			group = group.getParentGroup();
		}

		return false;
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		if (groupId > 0) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return contains(permissionChecker, group, actionId);
		}
		else {
			return false;
		}
	}

	public boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return permissionChecker.hasPermission(
			0, Group.class.getName(), 0, actionId);
	}

	public boolean hasMembershipProtected(
			PermissionChecker permissionChecker, long groupId, long userId)
		throws PortalException, SystemException {

		if (permissionChecker.isGroupOwner(groupId)) {
			return false;
		}

		Role siteOwnerRole = RoleLocalServiceUtil.getRole(
				permissionChecker.getCompanyId(), RoleConstants.SITE_OWNER);
		Role siteAdministratorRole = RoleLocalServiceUtil.getRole(
			permissionChecker.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteOwnerRole.getRoleId()) ||
			UserGroupRoleLocalServiceUtil.hasUserGroupRole(
				userId, groupId, siteAdministratorRole.getRoleId())) {

			return true;
		}

		return false;
	}

	public boolean hasRoleProtected(
			PermissionChecker permissionChecker, long groupId, long userId,
			Role role)
		throws PortalException, SystemException {

		String roleName = role.getName();

		if ((roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
			 roleName.equals(RoleConstants.SITE_OWNER)) &&
			hasMembershipProtected(
				permissionChecker, groupId, userId)) {

			return true;
		}

		return false;
	}

}