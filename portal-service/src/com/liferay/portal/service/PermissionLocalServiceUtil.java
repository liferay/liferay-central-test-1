/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="PermissionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PermissionLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PermissionLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PermissionLocalService
 * @see com.liferay.portal.service.PermissionLocalServiceFactory
 *
 */
public class PermissionLocalServiceUtil {
	public static com.liferay.portal.model.Permission addPermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.addPermission(permission);
	}

	public static void deletePermission(long permissionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.deletePermission(permissionId);
	}

	public static void deletePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.deletePermission(permission);
	}

	public static java.util.List<com.liferay.portal.model.Permission> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.Permission> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.updatePermission(permission);
	}

	public static com.liferay.portal.model.Permission addPermission(
		long companyId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.addPermission(companyId, actionId,
			resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.lang.String name, long resourceId,
		boolean portletActions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.addPermissions(companyId, name,
			resourceId, portletActions);
	}

	public static void addUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.addUserPermissions(userId, actionIds, resourceId);
	}

	public static java.util.List<String> getActions(
		java.util.List<com.liferay.portal.model.Permission> permissions)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getActions(permissions);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getGroupPermissions(groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getGroupPermissions(groupId, companyId,
			name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getOrgGroupPermissions(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public static long getLatestPermissionId()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getLatestPermissionId();
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long companyId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getPermissions(companyId, actionIds,
			resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getRolePermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId, long resourceId) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getRolePermissions(roleId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long resourceId) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getUserPermissions(userId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.getUserPermissions(userId, companyId,
			name, scope, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, actionId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, primKey, actionId);
	}

	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasUserPermission(userId, actionId,
			resourceId);
	}

	public static boolean hasUserPermissions(long userId, long groupId,
		java.lang.String actionId, long[] resourceIds,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		return permissionLocalService.hasUserPermissions(userId, groupId,
			actionId, resourceIds, permissionCheckerBag);
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setGroupPermissions(groupId, actionIds,
			resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setGroupPermissions(className, classPK, groupId,
			actionIds, resourceId);
	}

	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public static void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public static void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setRolePermissions(roleId, companyId, name,
			scope, primKey, actionIds);
	}

	public static void setRolePermissions(long roleId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setRolePermissions(roleId, actionIds, resourceId);
	}

	public static void setUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.setUserPermissions(userId, actionIds, resourceId);
	}

	public static void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.unsetRolePermission(roleId, permissionId);
	}

	public static void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.unsetRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public static void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.unsetRolePermissions(roleId, companyId, name,
			scope, actionId);
	}

	public static void unsetUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PermissionLocalService permissionLocalService = PermissionLocalServiceFactory.getService();

		permissionLocalService.unsetUserPermissions(userId, actionIds,
			resourceId);
	}
}