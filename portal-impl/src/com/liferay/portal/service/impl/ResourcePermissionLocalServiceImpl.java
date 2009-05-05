/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ResourceAction;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourcePermissionConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.ResourcePermissionLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePermissionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionLocalServiceImpl
	extends ResourcePermissionLocalServiceBaseImpl {

	public void addResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		if (scope == ResourceConstants.SCOPE_COMPANY) {

			// Remove group permission

			removeResourcePermissions(
				companyId, name, ResourceConstants.SCOPE_GROUP, roleId,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {

			// Remove company permission

			removeResourcePermissions(
				companyId, name, ResourceConstants.SCOPE_COMPANY, roleId,
				actionId);
		}
		else if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
			throw new NoSuchResourcePermissionException();
		}

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, new String[] {actionId},
			ResourcePermissionConstants.OPERATOR_ADD);

		PermissionCacheUtil.clearCache();
	}

	public List<String> getAvailableResourcePermissionActionIds(
			long companyId, String name, int scope, String primKey, long roleId,
			List<String> actionIds)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			return Collections.EMPTY_LIST;
		}

		List<String> availableActionIds = new ArrayList<String>(
			actionIds.size());

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			if (hasActionId(resourcePermission, resourceAction)) {
				availableActionIds.add(actionId);
			}
		}

		return availableActionIds;
	}

	public int getResourcePermissionsCount(
			long companyId, String name, int scope, String primKey)
		throws SystemException {

		return resourcePermissionPersistence.countByC_N_S_P(
			companyId, name, scope, primKey);
	}

	public List<ResourcePermission> getRoleResourcePermissions(long roleId)
		throws SystemException {

		return resourcePermissionPersistence.findByRoleId(roleId);
	}

	public boolean hasActionId(
		ResourcePermission resourcePermission, ResourceAction resourceAction) {

		long actionIds = resourcePermission.getActionIds();
		long bitwiseValue = resourceAction.getBitwiseValue();

		if ((actionIds & bitwiseValue) == bitwiseValue) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			return false;
		}

		ResourceAction resourceAction =
			resourceActionLocalService.getResourceAction(name, actionId);

		if (hasActionId(resourcePermission, resourceAction)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasScopeResourcePermission(
			long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S(companyId, name, scope);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (hasResourcePermission(
					companyId, name, scope, resourcePermission.getPrimKey(),
					roleId, actionId)) {

				return true;
			}
		}

		return false;
	}

	public void mergePermissions(long fromRoleId, long toRoleId)
		throws PortalException, SystemException {

		Role fromRole = rolePersistence.findByPrimaryKey(fromRoleId);
		Role toRole = rolePersistence.findByPrimaryKey(toRoleId);

		if (fromRole.getType() != toRole.getType()) {
			throw new PortalException("Role types are mismatched");
		}
		else if (PortalUtil.isSystemRole(toRole.getName())) {
			throw new PortalException("Cannot move permissions to system role");
		}
		else if (PortalUtil.isSystemRole(fromRole.getName())) {
			throw new PortalException(
				"Cannot move permissions from system role");
		}

		List<ResourcePermission> resourcePermissions =
			getRoleResourcePermissions(fromRoleId);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			resourcePermission.setRoleId(toRoleId);

			resourcePermissionPersistence.update(resourcePermission, false);
		}

		roleLocalService.deleteRole(fromRoleId);

		PermissionCacheUtil.clearCache();
	}

	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws PortalException, SystemException {

		Role toRole = roleLocalService.getRole(toRoleId);
		ResourcePermission resourcePermission =
			getResourcePermission(resourcePermissionId);
		long fromRoleId = resourcePermission.getRoleId();

		long companyId = resourcePermission.getCompanyId();
		String name = resourcePermission.getName();
		int scope = resourcePermission.getScope();
		String primKey = resourcePermission.getPrimKey();

		// Set new actions with new role

		String[] actionIds;

		if (toRole.getType() == RoleConstants.TYPE_REGULAR) {
			actionIds = ResourceActionsUtil.
				getModelResourceActions(name).toArray(new String[0]);
		}
		else {
			actionIds = ResourceActionsUtil.
				getModelResourceCommunityDefaultActions(name).toArray(new String[0]);
		}

		setResourcePermissions(
			companyId, name, scope, primKey, toRoleId, actionIds);

		// Clean up

		resourcePermissionPersistence.remove(resourcePermissionId);

		if (getRoleResourcePermissions(fromRoleId).isEmpty()) {
			roleLocalService.deleteRole(fromRoleId);
		}
	}

	public void removeResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String actionId)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, new String[] {actionId},
			ResourcePermissionConstants.OPERATOR_REMOVE);

		PermissionCacheUtil.clearCache();
	}

	public void removeResourcePermissions(
			long companyId, String name, int scope, long roleId,
			String actionId)
		throws PortalException, SystemException {

		List<ResourcePermission> resourcePermissions =
			resourcePermissionPersistence.findByC_N_S(companyId, name, scope);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			updateResourcePermission(
				companyId, name, scope, resourcePermission.getPrimKey(), roleId,
				new String[] {actionId},
				ResourcePermissionConstants.OPERATOR_REMOVE);
		}

		PermissionCacheUtil.clearCache();
	}

	public void setResourcePermissions(
			long companyId, String name, int scope, String primKey, long roleId,
			String[] actionIds)
		throws PortalException, SystemException {

		updateResourcePermission(
			companyId, name, scope, primKey, roleId, actionIds,
			ResourcePermissionConstants.OPERATOR_SET);
	}

	protected void updateResourcePermission(
			long companyId, String name, int scope, String primKey, long roleId,
			String[] actionIds, int operator)
		throws PortalException, SystemException {

		ResourcePermission resourcePermission =
			resourcePermissionPersistence.fetchByC_N_S_P_R(
				companyId, name, scope, primKey, roleId);

		if (resourcePermission == null) {
			if (operator == ResourcePermissionConstants.OPERATOR_REMOVE) {
				return;
			}

			long resourcePermissionId = counterLocalService.increment(
				ResourcePermission.class.getName());

			resourcePermission = resourcePermissionPersistence.create(
				resourcePermissionId);

			resourcePermission.setCompanyId(companyId);
			resourcePermission.setName(name);
			resourcePermission.setScope(scope);
			resourcePermission.setPrimKey(primKey);
			resourcePermission.setRoleId(roleId);
		}

		long actionIdsLong = resourcePermission.getActionIds();

		if (operator == ResourcePermissionConstants.OPERATOR_SET) {
			actionIdsLong = 0;
		}

		for (String actionId : actionIds) {
			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(name, actionId);

			if ((operator == ResourcePermissionConstants.OPERATOR_ADD) ||
				(operator == ResourcePermissionConstants.OPERATOR_SET)) {

				actionIdsLong |= resourceAction.getBitwiseValue();
			}
			else {
				actionIdsLong =
					actionIdsLong & (~resourceAction.getBitwiseValue());
			}
		}

		resourcePermission.setActionIds(actionIdsLong);

		resourcePermissionPersistence.update(resourcePermission, false);

		PermissionCacheUtil.clearCache();
	}

}