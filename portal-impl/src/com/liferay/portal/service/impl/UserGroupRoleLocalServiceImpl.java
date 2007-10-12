/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchUserGroupRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.base.UserGroupRoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.service.persistence.UserGroupRoleUtil;

import java.util.List;

/**
 * <a href="UserGroupRoleLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class UserGroupRoleLocalServiceImpl
	extends UserGroupRoleLocalServiceBaseImpl {

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		checkGroupResource(groupId);

		for (int i = 0; i < roleIds.length; i++) {
			long roleId = roleIds[i];

			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			if (UserGroupRoleUtil.fetchByPrimaryKey(pk) == null) {
				UserGroupRoleUtil.update(UserGroupRoleUtil.create(pk));
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		checkGroupResource(groupId);

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

			if (UserGroupRoleUtil.fetchByPrimaryKey(pk) == null) {
				UserGroupRoleUtil.update(UserGroupRoleUtil.create(pk));
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(
			long userId, long groupId, long[] roleIds)
		throws PortalException, SystemException {

		for (int i = 0; i < roleIds.length; i++) {
			long roleId = roleIds[i];

			try {
				UserGroupRoleUtil.remove(
					new UserGroupRolePK(userId, groupId, roleId));
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws PortalException, SystemException {

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			try {
				UserGroupRoleUtil.remove(
					new UserGroupRolePK(userId, groupId, roleId));
			}
			catch (NoSuchUserGroupRoleException nsugre) {
			}
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRoles(long[] userIds, long groupId)
		throws SystemException {

		for (int i = 0; i < userIds.length; i++) {
			long userId = userIds[i];

			UserGroupRoleUtil.removeByU_G(userId, groupId);
		}

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByGroupId(long groupId)
		throws SystemException {

		UserGroupRoleUtil.removeByGroupId(groupId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByRoleId(long roleId)
		throws SystemException {

		UserGroupRoleUtil.removeByRoleId(roleId);

		PermissionCacheUtil.clearCache();
	}

	public void deleteUserGroupRolesByUserId(long userId)
		throws SystemException {

		UserGroupRoleUtil.removeByUserId(userId);

		PermissionCacheUtil.clearCache();
	}

	public List getUserGroupRoles(long userId, long groupId)
		throws PortalException, SystemException {

		return UserGroupRoleUtil.findByU_G(userId, groupId);
	}

	public boolean hasUserGroupRole(long userId, long groupId, long roleId)
		throws PortalException, SystemException {

		UserGroupRolePK pk = new UserGroupRolePK(userId, groupId, roleId);

		if (UserGroupRoleUtil.fetchByPrimaryKey(pk) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected void checkGroupResource(long groupId)
		throws PortalException, SystemException {

		// Make sure that the individual resource for the group exists

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ResourceLocalServiceUtil.addResource(
			group.getCompanyId(), Group.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, String.valueOf(groupId));
	}

}