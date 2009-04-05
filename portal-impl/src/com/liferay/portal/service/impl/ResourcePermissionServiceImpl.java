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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.base.ResourcePermissionServiceBaseImpl;

/**
 * <a href="ResourcePermissionServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionServiceImpl
	extends ResourcePermissionServiceBaseImpl {

	public void setResourcePermission(
			long roleId, long groupId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.setResourcePermission(
			roleId, getUser().getCompanyId(), name, scope, primKey, actionId);
	}

	public void setResourcePermissions(
			long roleId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		permissionService.checkPermission(groupId, resourceId);

		resourcePermissionLocalService.setResourcePermissions(
			roleId, actionIds, resourceId);
	}

	public void unsetResourcePermission(
			long roleId, long groupId, long resourceId, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.unsetResourcePermission(
			roleId, resourceId, actionId);
	}

	public void unsetResourcePermissions(
			long roleId, long groupId, String name, int scope, String actionId)
		throws PortalException, SystemException {

		permissionService.checkPermission(
			groupId, Role.class.getName(), roleId);

		resourcePermissionLocalService.unsetResourcePermissions(
			roleId, getUser().getCompanyId(), name, scope, actionId);
	}

}