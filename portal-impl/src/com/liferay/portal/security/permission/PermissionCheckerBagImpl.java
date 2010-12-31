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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerBagImpl implements PermissionCheckerBag {

	public PermissionCheckerBagImpl() {
	}

	public PermissionCheckerBagImpl(
		long userId, List<Group> userGroups, List<Organization> userOrgs,
		List<Group> userOrgGroups, List<Group> userUserGroupGroups,
		List<Group> groups, List<Role> roles) {

		_userId = userId;
		_userGroups = userGroups;
		_userOrgs = userOrgs;
		_userOrgGroups = userOrgGroups;
		_userUserGroupGroups = userUserGroupGroups;
		_groups = groups;
		_roles = roles;
	}

	public List<Group> getUserGroups() {
		return _userGroups;
	}

	public List<Organization> getUserOrgs() {
		return _userOrgs;
	}

	public List<Group> getUserOrgGroups() {
		return _userOrgGroups;
	}

	public List<Group> getUserUserGroupGroups() {
		return _userUserGroupGroups;
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public long[] getRoleIds() {
		if (_roleIds == null) {
			List<Role> roles = getRoles();

			long[] roleIds = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				Role role = roles.get(i);

				roleIds[i] = role.getRoleId();
			}

			Arrays.sort(roleIds);

			_roleIds = roleIds;
		}

		return _roleIds;
	}

	public List<Role> getRoles() {
		return _roles;
	}

	public boolean isCommunityAdmin(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = _communityAdmins.get(group.getGroupId());

		if (value == null) {
			value = Boolean.valueOf(
				isCommunityAdminImpl(permissionChecker, group));

			_communityAdmins.put(group.getGroupId(), value);
		}

		return value.booleanValue();
	}

	public boolean isCommunityOwner(
			PermissionChecker permissionChecker, Group group)
		throws Exception {

		Boolean value = _communityOwners.get(group.getGroupId());

		if (value == null) {
			value = Boolean.valueOf(
				isCommunityOwnerImpl(permissionChecker, group));

			_communityOwners.put(group.getGroupId(), value);
		}

		return value.booleanValue();
	}

	protected boolean isCommunityAdminImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException, SystemException {

		if (group.isCommunity()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_userId, group.getGroupId(),
					RoleConstants.COMMUNITY_ADMINISTRATOR, true) ||
				UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_userId, group.getGroupId(),
					RoleConstants.COMMUNITY_OWNER, true)) {

				return true;
			}
		}
		else if (group.isCompany()) {
			if (permissionChecker.isCompanyAdmin()) {
				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				Group organizationGroup = organization.getGroup();

				long organizationGroupId = organizationGroup.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						_userId, organizationGroupId,
						RoleConstants.ORGANIZATION_ADMINISTRATOR, true) ||
					UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						_userId, organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}
		else if (group.isUser()) {
			long userId = group.getClassPK();

			if (userId == _userId) {
				return true;
			}
		}

		return false;
	}

	protected boolean isCommunityOwnerImpl(
			PermissionChecker permissionChecker, Group group)
		throws PortalException, SystemException {

		if (group.isCommunity()) {
			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					_userId, group.getGroupId(),
					RoleConstants.COMMUNITY_OWNER, true)) {

				return true;
			}
		}
		else if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			while (organizationId !=
						OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

				Organization organization =
					OrganizationLocalServiceUtil.getOrganization(
						organizationId);

				Group organizationGroup = organization.getGroup();

				long organizationGroupId = organizationGroup.getGroupId();

				if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
						_userId, organizationGroupId,
						RoleConstants.ORGANIZATION_OWNER, true)) {

					return true;
				}

				organizationId = organization.getParentOrganizationId();
			}
		}
		else if (group.isUser()) {
			long userId = group.getClassPK();

			if (userId == _userId) {
				return true;
			}
		}

		return false;
	}

	private long _userId;
	private List<Group> _userGroups;
	private List<Organization> _userOrgs;
	private List<Group> _userOrgGroups;
	private List<Group> _userUserGroupGroups;
	private List<Group> _groups;
	private long[] _roleIds;
	private List<Role> _roles;
	private Map<Long, Boolean> _communityAdmins = new HashMap<Long, Boolean>();
	private Map<Long, Boolean> _communityOwners = new HashMap<Long, Boolean>();

}