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
 * This class is a wrapper for {@link ResourcePermissionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionLocalService
 * @generated
 */
public class ResourcePermissionLocalServiceWrapper
	implements ResourcePermissionLocalService {
	public ResourcePermissionLocalServiceWrapper(
		ResourcePermissionLocalService resourcePermissionLocalService) {
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	/**
	* Adds the resource permission to the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission to add
	* @return the resource permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.addResourcePermission(resourcePermission);
	}

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId) {
		return _resourcePermissionLocalService.createResourcePermission(resourcePermissionId);
	}

	/**
	* Deletes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission to delete
	* @throws PortalException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteResourcePermission(long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.deleteResourcePermission(resourcePermissionId);
	}

	/**
	* Deletes the resource permission from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.deleteResourcePermission(resourcePermission);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery);
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
	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _resourcePermissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the resource permission with the primary key.
	*
	* @param resourcePermissionId the primary key of the resource permission to get
	* @return the resource permission
	* @throws PortalException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermission(resourcePermissionId);
	}

	/**
	* Gets a range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissions(start, end);
	}

	/**
	* Gets the number of resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public int getResourcePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissionsCount();
	}

	/**
	* Updates the resource permission in the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission to update
	* @return the resource permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.updateResourcePermission(resourcePermission);
	}

	/**
	* Updates the resource permission in the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermission the resource permission to update
	* @param merge whether to merge the resource permission with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the resource permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.updateResourcePermission(resourcePermission,
			merge);
	}

	public void addResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.addResourcePermission(companyId, name,
			scope, primKey, roleId, actionId);
	}

	public java.util.List<java.lang.String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId,
		java.util.List<java.lang.String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getAvailableResourcePermissionActionIds(companyId,
			name, scope, primKey, roleId, actionIds);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissions(companyId,
			name, scope, primKey);
	}

	public int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getResourcePermissionsCount(companyId,
			name, scope, primKey);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId);
	}

	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.getRoleResourcePermissions(roleId,
			scopes, start, end);
	}

	public boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourcePermissionLocalService.hasActionId(resourcePermission,
			resourceAction);
	}

	public boolean hasResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.hasResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
	}

	public boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourcePermissionLocalService.hasScopeResourcePermission(companyId,
			name, scope, roleId, actionId);
	}

	public void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.mergePermissions(fromRoleId, toRoleId);
	}

	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.reassignPermissions(resourcePermissionId,
			toRoleId);
	}

	public void removeResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.removeResourcePermission(companyId,
			name, scope, primKey, roleId, actionId);
	}

	public void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.removeResourcePermissions(companyId,
			name, scope, roleId, actionId);
	}

	public void setResourcePermissions(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_resourcePermissionLocalService.setResourcePermissions(companyId, name,
			scope, primKey, roleId, actionIds);
	}

	public ResourcePermissionLocalService getWrappedResourcePermissionLocalService() {
		return _resourcePermissionLocalService;
	}

	private ResourcePermissionLocalService _resourcePermissionLocalService;
}