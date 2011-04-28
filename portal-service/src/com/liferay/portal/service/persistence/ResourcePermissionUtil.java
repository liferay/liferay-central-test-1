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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the resource permission service. This utility wraps {@link ResourcePermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionPersistence
 * @see ResourcePermissionPersistenceImpl
 * @generated
 */
public class ResourcePermissionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ResourcePermission resourcePermission) {
		getPersistence().clearCache(resourcePermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ResourcePermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ResourcePermission remove(
		ResourcePermission resourcePermission) throws SystemException {
		return getPersistence().remove(resourcePermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourcePermission update(
		ResourcePermission resourcePermission, boolean merge)
		throws SystemException {
		return getPersistence().update(resourcePermission, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ResourcePermission update(
		ResourcePermission resourcePermission, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(resourcePermission, merge, serviceContext);
	}

	/**
	* Caches the resource permission in the entity cache if it is enabled.
	*
	* @param resourcePermission the resource permission to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		getPersistence().cacheResult(resourcePermission);
	}

	/**
	* Caches the resource permissions in the entity cache if it is enabled.
	*
	* @param resourcePermissions the resource permissions to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions) {
		getPersistence().cacheResult(resourcePermissions);
	}

	/**
	* Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	*
	* @param resourcePermissionId the primary key for the new resource permission
	* @return the new resource permission
	*/
	public static com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId) {
		return getPersistence().create(resourcePermissionId);
	}

	/**
	* Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePermissionId the primary key of the resource permission to remove
	* @return the resource permission that was removed
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resourcePermission, merge);
	}

	/**
	* Finds the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission to find
	* @return the resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourcePermissionId);
	}

	/**
	* Finds the resource permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePermissionId the primary key of the resource permission to find
	* @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePermissionId);
	}

	/**
	* Finds all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	/**
	* Finds a range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	/**
	* Finds an ordered range of all the resource permissions where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	/**
	* Finds the first resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	/**
	* Finds the last resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param roleId the role ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param roleId the role ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourcePermissionId, roleId,
			orderByComparator);
	}

	/**
	* Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	/**
	* Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope, start, end);
	}

	/**
	* Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S(companyId, name, scope, start, end,
			orderByComparator);
	}

	/**
	* Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_First(companyId, name, scope, orderByComparator);
	}

	/**
	* Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_Last(companyId, name, scope, orderByComparator);
	}

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_PrevAndNext(resourcePermissionId, companyId,
			name, scope, orderByComparator);
	}

	/**
	* Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @return the matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @return the range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end);
	}

	/**
	* Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end,
			orderByComparator);
	}

	/**
	* Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_First(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_First(companyId, name, scope, primKey,
			orderByComparator);
	}

	/**
	* Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_Last(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_Last(companyId, name, scope, primKey,
			orderByComparator);
	}

	/**
	* Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param resourcePermissionId the primary key of the current resource permission
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, java.lang.String name,
		int scope, java.lang.String primKey,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_PrevAndNext(resourcePermissionId, companyId,
			name, scope, primKey, orderByComparator);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @return the matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId,
			retrieveFromCache);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @param ownerId the owner ID to search with
	* @param actionIds the action IDs to search with
	* @return the matching resource permission
	* @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @param ownerId the owner ID to search with
	* @param actionIds the action IDs to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @param ownerId the owner ID to search with
	* @param actionIds the action IDs to search with
	* @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R_O_A(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, long ownerId, long actionIds,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds, retrieveFromCache);
	}

	/**
	* Finds all the resource permissions.
	*
	* @return the resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the resource permissions.
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
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the resource permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of resource permissions to return
	* @param end the upper bound of the range of resource permissions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the resource permissions where roleId = &#63; from the database.
	*
	* @param roleId the role ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	/**
	* Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @param ownerId the owner ID to search with
	* @param actionIds the action IDs to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_N_S_P_R_O_A(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_S_P_R_O_A(companyId, name, scope, primKey, roleId,
			ownerId, actionIds);
	}

	/**
	* Removes all the resource permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the resource permissions where roleId = &#63;.
	*
	* @param roleId the role ID to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S_P(companyId, name, scope, primKey);
	}

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	/**
	* Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; and ownerId = &#63; and actionIds = &#63;.
	*
	* @param companyId the company ID to search with
	* @param name the name to search with
	* @param scope the scope to search with
	* @param primKey the prim key to search with
	* @param roleId the role ID to search with
	* @param ownerId the owner ID to search with
	* @param actionIds the action IDs to search with
	* @return the number of matching resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N_S_P_R_O_A(long companyId,
		java.lang.String name, int scope, java.lang.String primKey,
		long roleId, long ownerId, long actionIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R_O_A(companyId, name, scope, primKey,
			roleId, ownerId, actionIds);
	}

	/**
	* Counts all the resource permissions.
	*
	* @return the number of resource permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourcePermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(ResourcePermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ResourcePermissionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ResourcePermissionUtil.class,
			"_persistence");
	}

	private static ResourcePermissionPersistence _persistence;
}