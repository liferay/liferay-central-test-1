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

/**
 * <p>
 * This class is a wrapper for {@link PermissionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PermissionLocalService
 * @generated
 */
public class PermissionLocalServiceWrapper implements PermissionLocalService {
	public PermissionLocalServiceWrapper(
		PermissionLocalService permissionLocalService) {
		_permissionLocalService = permissionLocalService;
	}

	/**
	* Adds the permission to the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to add
	* @return the permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Permission addPermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermission(permission);
	}

	/**
	* Creates a new permission with the primary key. Does not add the permission to the database.
	*
	* @param permissionId the primary key for the new permission
	* @return the new permission
	*/
	public com.liferay.portal.model.Permission createPermission(
		long permissionId) {
		return _permissionLocalService.createPermission(permissionId);
	}

	/**
	* Deletes the permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param permissionId the primary key of the permission to delete
	* @throws PortalException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deletePermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.deletePermission(permissionId);
	}

	/**
	* Deletes the permission from the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deletePermission(com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.deletePermission(permission);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the permission with the primary key.
	*
	* @param permissionId the primary key of the permission to get
	* @return the permission
	* @throws PortalException if a permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Permission getPermission(long permissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermission(permissionId);
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
	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissions(start, end);
	}

	/**
	* Gets the number of permissions.
	*
	* @return the number of permissions
	* @throws SystemException if a system exception occurred
	*/
	public int getPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissionsCount();
	}

	/**
	* Updates the permission in the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to update
	* @return the permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.updatePermission(permission);
	}

	/**
	* Updates the permission in the database. Also notifies the appropriate model listeners.
	*
	* @param permission the permission to update
	* @param merge whether to merge the permission with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Permission updatePermission(
		com.liferay.portal.model.Permission permission, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.updatePermission(permission, merge);
	}

	public com.liferay.portal.model.Permission addPermission(long companyId,
		java.lang.String actionId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermission(companyId, actionId,
			resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.lang.String name, long resourceId,
		boolean portletActions)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermissions(companyId, name,
			resourceId, portletActions);
	}

	public java.util.List<com.liferay.portal.model.Permission> addPermissions(
		long companyId, java.util.List<java.lang.String> actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.addPermissions(companyId, actionIds,
			resourceId);
	}

	public void addUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.addUserPermissions(userId, actionIds, resourceId);
	}

	public java.util.List<java.lang.String> getActions(
		java.util.List<com.liferay.portal.model.Permission> permissions) {
		return _permissionLocalService.getActions(permissions);
	}

	public java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getGroupPermissions(groupId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getGroupPermissions(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getGroupPermissions(groupId, companyId,
			name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.Permission> getOrgGroupPermissions(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getOrgGroupPermissions(organizationId,
			groupId, resourceId);
	}

	public long getLatestPermissionId()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getLatestPermissionId();
	}

	public java.util.List<com.liferay.portal.model.Permission> getPermissions(
		long companyId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getPermissions(companyId, actionIds,
			resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getRolePermissions(roleId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getRolePermissions(
		long roleId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getRolePermissions(roleId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getUserPermissions(userId, resourceId);
	}

	public java.util.List<com.liferay.portal.model.Permission> getUserPermissions(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.getUserPermissions(userId, companyId,
			name, scope, primKey);
	}

	public boolean hasGroupPermission(long groupId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasGroupPermission(groupId, actionId,
			resourceId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, actionId);
	}

	public boolean hasRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasRolePermission(roleId, companyId,
			name, scope, primKey, actionId);
	}

	public boolean hasUserPermission(long userId, java.lang.String actionId,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasUserPermission(userId, actionId,
			resourceId);
	}

	public boolean hasUserPermissions(long userId, long groupId,
		java.util.List<com.liferay.portal.model.Resource> resources,
		java.lang.String actionId,
		com.liferay.portal.security.permission.PermissionCheckerBag permissionCheckerBag)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _permissionLocalService.hasUserPermissions(userId, groupId,
			resources, actionId, permissionCheckerBag);
	}

	public void setGroupPermissions(long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setGroupPermissions(groupId, actionIds,
			resourceId);
	}

	public void setGroupPermissions(java.lang.String className,
		java.lang.String classPK, long groupId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setGroupPermissions(className, classPK,
			groupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(long organizationId, long groupId,
		java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setOrgGroupPermissions(organizationId, groupId,
			actionIds, resourceId);
	}

	public void setRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public void setRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermissions(roleId, companyId, name,
			scope, primKey, actionIds);
	}

	public void setRolePermissions(long roleId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setRolePermissions(roleId, actionIds, resourceId);
	}

	public void setUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.setUserPermissions(userId, actionIds, resourceId);
	}

	public void unsetRolePermission(long roleId, long permissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermission(roleId, permissionId);
	}

	public void unsetRolePermission(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermission(roleId, companyId, name,
			scope, primKey, actionId);
	}

	public void unsetRolePermissions(long roleId, long companyId,
		java.lang.String name, int scope, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetRolePermissions(roleId, companyId, name,
			scope, actionId);
	}

	public void unsetUserPermissions(long userId, java.lang.String[] actionIds,
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_permissionLocalService.unsetUserPermissions(userId, actionIds,
			resourceId);
	}

	public PermissionLocalService getWrappedPermissionLocalService() {
		return _permissionLocalService;
	}

	public void setWrappedPermissionLocalService(
		PermissionLocalService permissionLocalService) {
		_permissionLocalService = permissionLocalService;
	}

	private PermissionLocalService _permissionLocalService;
}