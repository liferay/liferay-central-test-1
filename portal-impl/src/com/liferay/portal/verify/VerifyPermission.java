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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Tobias Kaefer
 * @author Douglas Wong
 * @author Matthew Kong
 */
public class VerifyPermission extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			try {
				if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
					deleteDefaultPrivateLayoutPermissions_5(companyId);
				}
				else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
					deleteDefaultPrivateLayoutPermissions_6(companyId);
				}
				else {
					deleteDefaultPrivateLayoutPermissions_1to4(companyId);
				}
			}
			catch (Exception e) {
			}
		}

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) &&
			(PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6)) {

			return;
		}

		List<String> modelNames = ResourceActionsUtil.getModelNames();

		for (String modelName : modelNames) {
			List<String> actionIds =
				ResourceActionsUtil.getModelResourceActions(modelName);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				PermissionLocalServiceUtil.checkPermissions(
					modelName, actionIds);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, actionIds, true);
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_1to4(long companyId)
		throws Exception {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<Permission> permissions =
			PermissionLocalServiceUtil.getUserPermissions(defaultUserId);

		for (Permission permission : permissions) {

			long resourceId = permission.getResourceId();

			Resource resource = ResourceLocalServiceUtil.getResource(
				resourceId);

			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(
					resource.getCodeId());

			if (isPrivateLayout(
					resourceCode.getName(), resource.getPrimKey())) {

				String[] actionIds = new String[] {permission.getActionId()};

				PermissionLocalServiceUtil.unsetUserPermissions(
					defaultUserId, actionIds, resourceId);
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_5(long companyId)
		throws Exception {

		Role defaultUserRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		long defaultRoleId = defaultUserRole.getRoleId();

		List<Permission> permissions =
			PermissionLocalServiceUtil.getRolePermissions(defaultRoleId);

		for (Permission permission : permissions) {

			long resourceId = permission.getResourceId();

			Resource resource = ResourceLocalServiceUtil.getResource(
				resourceId);

			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(
					resource.getCodeId());

			if (isPrivateLayout(
					resourceCode.getName(), resource.getPrimKey())) {

				PermissionLocalServiceUtil.unsetRolePermission(
					defaultRoleId, permission.getPermissionId());
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_6(long companyId)
		throws Exception {

		Role defaultRole = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		long defaultRoleId = defaultRole.getRoleId();

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				defaultRoleId);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (isPrivateLayout(
					resourcePermission.getName(),
					resourcePermission.getPrimKey())) {

				ResourcePermissionLocalServiceUtil.deleteResourcePermission(
					resourcePermission.getResourcePermissionId());
			}
		}
	}

	protected boolean isPrivateLayout(String name, String primKey)
		throws Exception {

		if (!name.equals(Layout.class.getName())) {
			return false;
		}

		long plid = GetterUtil.getLong(primKey);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPublicLayout()) {
			return false;
		}

		String type = layout.getType();

		if (type.equals(LayoutConstants.TYPE_CONTROL_PANEL)) {
			return false;
		}

		return true;
	}

}