/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy.membershippolicysamples;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.BaseSiteMembershipPolicy;
import com.liferay.portal.security.membershippolicy.BaseSiteMembershipPolicyTestCase;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyTestImpl extends BaseSiteMembershipPolicy {

	public void checkMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException {

		for (long forbiddenGroupId : getForbiddenGroupIds()) {
			if (forbiddenGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addGroupIds, forbiddenGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.SITE_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredGroupId : getRequiredGroupIds()) {
			if (requiredGroupId == 0) {
				continue;
			}

			if (ArrayUtil.contains(removeGroupIds, requiredGroupId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.SITE_MEMBERSHIP_REQUIRED);
			}
		}
	}

	public void checkRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {

		long[] addUserGroupRoleIds = new long[2];
		long[] removeUserGroupRoleIds = new long[2];

		if ((addUserGroupRoles != null) && !addUserGroupRoles.isEmpty()) {
			for (UserGroupRole addUserGroupRole : addUserGroupRoles) {
				addUserGroupRoleIds[0] = addUserGroupRole.getRoleId();
			}
		}

		if ((removeUserGroupRoles != null) && !removeUserGroupRoles.isEmpty()) {
			for (UserGroupRole removeUserGroupRole : removeUserGroupRoles) {
				removeUserGroupRoleIds[0] = removeUserGroupRole.getRoleId();
			}
		}

		for (long forbiddenGroupRoleId : getForbiddenGroupRoleIds()) {
			if (forbiddenGroupRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(addUserGroupRoleIds, forbiddenGroupRoleId)) {
				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_NOT_ALLOWED);
			}
		}

		for (long requiredGroupRoleId : getRequiredGroupRoleIds()) {
			if (requiredGroupRoleId == 0) {
				continue;
			}

			if (ArrayUtil.contains(
					removeUserGroupRoleIds, requiredGroupRoleId)) {

				throw new MembershipPolicyException(
					MembershipPolicyException.ROLE_MEMBERSHIP_REQUIRED);
			}
		}
	}

	public void propagateMembership(
			long[] userIds, long[] addGroupIds, long[] removeGroupIds)
		throws PortalException, SystemException {

		BaseSiteMembershipPolicyTestCase.setPropagateMembershipMethodFlag(true);
	}

	public void propagateRoles(
			List<UserGroupRole> addUserGroupRoles,
			List<UserGroupRole> removeUserGroupRoles)
		throws PortalException, SystemException {

		BaseSiteMembershipPolicyTestCase.setPropagateRolesMethodFlag(true);
	}

	public void verifyPolicy() throws PortalException, SystemException {
		BaseSiteMembershipPolicyTestCase.setVerifyMethodFlag(true);
	}

	@Override
	public void verifyPolicy(Group group)
		throws PortalException, SystemException {

		verifyPolicy();
	}

	public void verifyPolicy(
			Group group, Group oldGroup, List<AssetCategory> oldAssetCategories,
			List<AssetTag> oldAssetTags,
			Map<String, Serializable> oldExpandoAttributes,
			String oldTypeSettings)
		throws PortalException, SystemException {

		Assert.assertNotNull(group);
		Assert.assertNotNull(oldGroup);

		if (oldTypeSettings == null) {
			Assert.assertNotNull(oldAssetCategories);
			Assert.assertNotNull(oldAssetTags);
			Assert.assertNotNull(oldExpandoAttributes);
		}
		else {
			Assert.assertNull(oldAssetCategories);
			Assert.assertNull(oldAssetTags);
			Assert.assertNull(oldExpandoAttributes);
		}

		verifyPolicy(group);
	}

	public void verifyPolicy(Role role)
		throws PortalException, SystemException {

		verifyPolicy();
	}

	public void verifyPolicy(
			Role role, Role oldRole, Map<String,
			Serializable> oldExpandoAttributes)
		throws PortalException, SystemException {

		Assert.assertNotNull(role);
		Assert.assertNotNull(oldRole);
		Assert.assertNotNull(oldExpandoAttributes);

		verifyPolicy(role);
	}

	protected long[] getForbiddenGroupIds() {
		return BaseSiteMembershipPolicyTestCase.getForbiddenSiteIds();
	}

	protected long[] getForbiddenGroupRoleIds() {
		return BaseSiteMembershipPolicyTestCase.getForbiddenRoleIds();
	}

	protected long[] getRequiredGroupIds() {
		return BaseSiteMembershipPolicyTestCase.getRequiredSiteIds();
	}

	protected long[] getRequiredGroupRoleIds() {
		return BaseSiteMembershipPolicyTestCase.getRequiredRoleIds();
	}

}