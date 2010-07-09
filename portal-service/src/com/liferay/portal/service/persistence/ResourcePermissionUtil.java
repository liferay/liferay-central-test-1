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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionPersistence
 * @see       ResourcePermissionPersistenceImpl
 * @generated
 */
public class ResourcePermissionUtil {
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

	public static void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		getPersistence().cacheResult(resourcePermission);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions) {
		getPersistence().cacheResult(resourcePermissions);
	}

	public static com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId) {
		return getPersistence().create(resourcePermissionId);
	}

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

	public static com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePermissionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId(roleId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_First(roleId, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRoleId_Last(roleId, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourcePermissionId, roleId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(roleId, scope);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S(roleId, scope, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByR_S(
		long roleId, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_S(roleId, scope, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByR_S_First(
		long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S_First(roleId, scope, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByR_S_Last(
		long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByR_S_Last(roleId, scope, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission[] findByR_S_PrevAndNext(
		long resourcePermissionId, long roleId, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByR_S_PrevAndNext(resourcePermissionId, roleId, scope,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S(
		long companyId, java.lang.String name, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S(companyId, name, scope, start, end,
			orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByC_N_S_First(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_First(companyId, name, scope, orderByComparator);
	}

	public static com.liferay.portal.model.ResourcePermission findByC_N_S_Last(
		long companyId, java.lang.String name, int scope,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_Last(companyId, name, scope, orderByComparator);
	}

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

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S_P(companyId, name, scope, primKey);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P(companyId, name, scope, primKey, start, end,
			orderByComparator);
	}

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

	public static com.liferay.portal.model.ResourcePermission findByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByC_N_S_P_R(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeByR_S(long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByR_S(roleId, scope);
	}

	public static void removeByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	public static void removeByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S_P(companyId, name, scope, primKey);
	}

	public static void removeByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countByR_S(long roleId, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByR_S(roleId, scope);
	}

	public static int countByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

	public static int countByC_N_S_P(long companyId, java.lang.String name,
		int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S_P(companyId, name, scope, primKey);
	}

	public static int countByC_N_S_P_R(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByC_N_S_P_R(companyId, name, scope, primKey, roleId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourcePermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourcePermissionPersistence)PortalBeanLocatorUtil.locate(ResourcePermissionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ResourcePermissionPersistence persistence) {
		_persistence = persistence;
	}

	private static ResourcePermissionPersistence _persistence;
}