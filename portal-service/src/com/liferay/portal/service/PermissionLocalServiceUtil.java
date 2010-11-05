/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the permission local service. This utility wraps {@link com.liferay.portal.service.impl.PermissionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionLocalService
 * @see com.liferay.portal.service.base.PermissionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PermissionLocalServiceImpl
 * @generated
 */
public class PermissionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PermissionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the permission to the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to add
	* @return the permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission addPermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermission(permission);
	}

	/**
	* Creates a new permission with the primary key. Does not add the permission to the database.
	*
	* @param permissionId the primary key for the new permission
	* @return the new permission
	*/
	public static com.liferay.portal.model.Permission createPermission(
		long permissionId) {
		return getService().createPermission(permissionId);
	}

	/**
	* Deletes the permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param permissionId the primary key of the permission to delete
	* @throws PortalException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deletePermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePermission(permissionId);
	}

	/**
	* Deletes the permission from the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deletePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePermission(permission);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the permission with the primary key.
	*
	* @param permissionId the primary key of the permission to get
	* @return the permission
	* @throws PortalException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission getPermission(
		long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermission(permissionId);
	}

	/**
	* Gets a range of all the permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of permissions to return
	* @param end the upper bound of the range of permissions to return (not inclusive)
	* @return the range of permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissions(start, end);
	}

	/**
	* Gets the number of permissions.
	*
	* @return the number of permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int getPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissionsCount();
	}

	/**
	* Updates the permission in the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to update
	* @return the permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePermission(permission);
	}

	/**
	* Updates the permission in the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to update
	* @param merge whether to merge the permission with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePermission(permission, merge);
	}

	public static com.liferay.portal.model.Permission addPermission(
		long companyId, java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermission(companyId, actionId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.lang.String name, long resourceId,
		boolean portletActions)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPermissions(companyId, name, resourceId, portletActions);
	}

	public static java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.util.List<java.lang.String> actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPermissions(companyId, actionIds, resourceId);
	}

	public static void addUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserPermissions(userId, actionIds, resourceId);
	}

	public static java.util.List<java.lang.String> getActions(
		java.util.List<com.liferay.portal.model.Permission> permissions) {
		return getService().getActions(permissions);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupPermissions(groupId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getGroupPermissions(groupId, companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getOrgGroupPermissions(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getOrgGroupPermissions(organizationId, groupId, resourceId);
	}

	public static long getLatestPermissionId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestPermissionId();
	}

	public static java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long companyId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPermissions(companyId, actionIds, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRolePermissions(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRolePermissions(roleId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserPermissions(userId, resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserPermissions(userId, companyId, name, scope, primKey);
	}

	public static boolean hasGroupPermission(long groupId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasGroupPermission(groupId, actionId, resourceId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasRolePermission(roleId, companyId, name, scope, actionId);
	}

	public static boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasRolePermission(roleId, companyId, name, scope, primKey,
			actionId);
	}

	public static boolean hasUserPermission(long userId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserPermission(userId, actionId, resourceId);
	}

	public static boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasUserPermissions(userId, groupId, resources, actionId,
			permissionCheckerBag);
	}

	public static void setGroupPermissions(long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setGroupPermissions(groupId, actionIds, resourceId);
	}

	public static void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setGroupPermissions(className, classPK, groupId, actionIds,
			resourceId);
	}

	public static void setOrgGroupPermissions(long organizationId,
		long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setOrgGroupPermissions(organizationId, groupId, actionIds,
			resourceId);
	}

	public static void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setRolePermission(roleId, companyId, name, scope, primKey, actionId);
	}

	public static void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setRolePermissions(roleId, companyId, name, scope, primKey,
			actionIds);
	}

	public static void setRolePermissions(long roleId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setRolePermissions(roleId, actionIds, resourceId);
	}

	public static void setUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserPermissions(userId, actionIds, resourceId);
	}

	public static void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRolePermission(roleId, permissionId);
	}

	public static void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetRolePermission(roleId, companyId, name, scope, primKey,
			actionId);
	}

	public static void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.unsetRolePermissions(roleId, companyId, name, scope, actionId);
	}

	public static void unsetUserPermissions(long userId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserPermissions(userId, actionIds, resourceId);
	}

	public static PermissionLocalService getService() {
		if (_service == null) {
			_service = (PermissionLocalService)PortalBeanLocatorUtil.locate(PermissionLocalService.class.getName());

			ReferenceRegistry.registerReference(PermissionLocalServiceUtil.class,
				"_service");
			MethodCache.remove(PermissionLocalService.class);
		}

		return _service;
	}

	public void setService(PermissionLocalService service) {
		MethodCache.remove(PermissionLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(PermissionLocalServiceUtil.class,
			"_service");
		MethodCache.remove(PermissionLocalService.class);
	}

	private static PermissionLocalService _service;
}