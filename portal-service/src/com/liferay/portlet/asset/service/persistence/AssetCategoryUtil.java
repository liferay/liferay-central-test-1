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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetCategory;

import java.util.List;

/**
 * The persistence utility for the asset category service. This utility wraps {@link AssetCategoryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetCategoryPersistence
 * @see AssetCategoryPersistenceImpl
 * @generated
 */
public class AssetCategoryUtil {
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
	public static void clearCache(AssetCategory assetCategory) {
		getPersistence().clearCache(assetCategory);
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
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetCategory> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AssetCategory remove(AssetCategory assetCategory)
		throws SystemException {
		return getPersistence().remove(assetCategory);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetCategory update(AssetCategory assetCategory,
		boolean merge) throws SystemException {
		return getPersistence().update(assetCategory, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static AssetCategory update(AssetCategory assetCategory,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetCategory, merge, serviceContext);
	}

	/**
	* Caches the asset category in the entity cache if it is enabled.
	*
	* @param assetCategory the asset category to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetCategory assetCategory) {
		getPersistence().cacheResult(assetCategory);
	}

	/**
	* Caches the asset categories in the entity cache if it is enabled.
	*
	* @param assetCategories the asset categories to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories) {
		getPersistence().cacheResult(assetCategories);
	}

	/**
	* Creates a new asset category with the primary key. Does not add the asset category to the database.
	*
	* @param categoryId the primary key for the new asset category
	* @return the new asset category
	*/
	public static com.liferay.portlet.asset.model.AssetCategory create(
		long categoryId) {
		return getPersistence().create(categoryId);
	}

	/**
	* Removes the asset category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the asset category to remove
	* @return the asset category that was removed
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory remove(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().remove(categoryId);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateImpl(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetCategory, merge);
	}

	/**
	* Finds the asset category with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	*
	* @param categoryId the primary key of the asset category to find
	* @return the asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByPrimaryKey(categoryId);
	}

	/**
	* Finds the asset category with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param categoryId the primary key of the asset category to find
	* @return the asset category, or <code>null</code> if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchByPrimaryKey(
		long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(categoryId);
	}

	/**
	* Finds all the asset categories where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the asset categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByUuid_PrevAndNext(
		long categoryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(categoryId, uuid, orderByComparator);
	}

	/**
	* Finds the asset category where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the asset category where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the asset categories where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the asset categories where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching asset categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the asset categories where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Filters the asset categories before and after the current asset category in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] filterFindByGroupId_PrevAndNext(
		long categoryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(categoryId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the asset categories where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByParentCategoryId(parentCategoryId);
	}

	/**
	* Finds a range of all the asset categories where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByParentCategoryId(
		long parentCategoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParentCategoryId(parentCategoryId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_First(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_First(parentCategoryId,
			orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByParentCategoryId_Last(
		long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_Last(parentCategoryId,
			orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByParentCategoryId_PrevAndNext(
		long categoryId, long parentCategoryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByParentCategoryId_PrevAndNext(categoryId,
			parentCategoryId, orderByComparator);
	}

	/**
	* Finds all the asset categories where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId);
	}

	/**
	* Finds a range of all the asset categories where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByVocabularyId(
		long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByVocabularyId(vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_First(
		long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_First(vocabularyId, orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByVocabularyId_Last(
		long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_Last(vocabularyId, orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByVocabularyId_PrevAndNext(
		long categoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByVocabularyId_PrevAndNext(categoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Finds all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name);
	}

	/**
	* Finds a range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_N(parentCategoryId, name, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_N(
		long parentCategoryId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_N(parentCategoryId, name, start, end,
			orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_First(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_First(parentCategoryId, name, orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_Last(
		long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_Last(parentCategoryId, name, orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_N_PrevAndNext(
		long categoryId, long parentCategoryId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_N_PrevAndNext(categoryId, parentCategoryId, name,
			orderByComparator);
	}

	/**
	* Finds all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Finds a range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByP_V(
		long parentCategoryId, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByP_V(parentCategoryId, vocabularyId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_First(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_First(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByP_V_Last(
		long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_Last(parentCategoryId, vocabularyId,
			orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByP_V_PrevAndNext(
		long categoryId, long parentCategoryId, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByP_V_PrevAndNext(categoryId, parentCategoryId,
			vocabularyId, orderByComparator);
	}

	/**
	* Finds all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_V(name, vocabularyId);
	}

	/**
	* Finds a range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByN_V(name, vocabularyId, start, end);
	}

	/**
	* Finds an ordered range of all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findByN_V(
		java.lang.String name, long vocabularyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByN_V(name, vocabularyId, start, end, orderByComparator);
	}

	/**
	* Finds the first asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByN_V_First(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_First(name, vocabularyId, orderByComparator);
	}

	/**
	* Finds the last asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByN_V_Last(
		java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_Last(name, vocabularyId, orderByComparator);
	}

	/**
	* Finds the asset categories before and after the current asset category in the ordered set where name = &#63; and vocabularyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param categoryId the primary key of the current asset category
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a asset category with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory[] findByN_V_PrevAndNext(
		long categoryId, java.lang.String name, long vocabularyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence()
				   .findByN_V_PrevAndNext(categoryId, name, vocabularyId,
			orderByComparator);
	}

	/**
	* Finds the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchCategoryException} if it could not be found.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset category
	* @throws com.liferay.portlet.asset.NoSuchCategoryException if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory findByP_N_V(
		long parentCategoryId, java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		return getPersistence().findByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Finds the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchByP_N_V(
		long parentCategoryId, java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Finds the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the matching asset category, or <code>null</code> if a matching asset category could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetCategory fetchByP_N_V(
		long parentCategoryId, java.lang.String name, long vocabularyId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByP_N_V(parentCategoryId, name, vocabularyId,
			retrieveFromCache);
	}

	/**
	* Finds all the asset categories.
	*
	* @return the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the asset categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset categories where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the asset category where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the asset categories where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByParentCategoryId(parentCategoryId);
	}

	/**
	* Removes all the asset categories where vocabularyId = &#63; from the database.
	*
	* @param vocabularyId the vocabulary ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByVocabularyId(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; and name = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_N(parentCategoryId, name);
	}

	/**
	* Removes all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Removes all the asset categories where name = &#63; and vocabularyId = &#63; from the database.
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByN_V(name, vocabularyId);
	}

	/**
	* Removes the asset category where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63; from the database.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchCategoryException {
		getPersistence().removeByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Removes all the asset categories from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the asset categories where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the asset categories where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the asset categories where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the asset categories where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching asset categories that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the asset categories where parentCategoryId = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByParentCategoryId(long parentCategoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByParentCategoryId(parentCategoryId);
	}

	/**
	* Counts all the asset categories where vocabularyId = &#63;.
	*
	* @param vocabularyId the vocabulary ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByVocabularyId(long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	/**
	* Counts all the asset categories where parentCategoryId = &#63; and name = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_N(long parentCategoryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_N(parentCategoryId, name);
	}

	/**
	* Counts all the asset categories where parentCategoryId = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_V(long parentCategoryId, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByP_V(parentCategoryId, vocabularyId);
	}

	/**
	* Counts all the asset categories where name = &#63; and vocabularyId = &#63;.
	*
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByN_V(java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByN_V(name, vocabularyId);
	}

	/**
	* Counts all the asset categories where parentCategoryId = &#63; and name = &#63; and vocabularyId = &#63;.
	*
	* @param parentCategoryId the parent category ID to search with
	* @param name the name to search with
	* @param vocabularyId the vocabulary ID to search with
	* @return the number of matching asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countByP_N_V(long parentCategoryId,
		java.lang.String name, long vocabularyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByP_N_V(parentCategoryId, name, vocabularyId);
	}

	/**
	* Counts all the asset categories.
	*
	* @return the number of asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Gets all the asset entries associated with the asset category.
	*
	* @param pk the primary key of the asset category to get the associated asset entries for
	* @return the asset entries associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntries(pk);
	}

	/**
	* Gets a range of all the asset entries associated with the asset category.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset category to get the associated asset entries for
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @return the range of asset entries associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntries(pk, start, end);
	}

	/**
	* Gets an ordered range of all the asset entries associated with the asset category.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset category to get the associated asset entries for
	* @param start the lower bound of the range of asset categories to return
	* @param end the upper bound of the range of asset categories to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getAssetEntries(pk, start, end, orderByComparator);
	}

	/**
	* Gets the number of asset entries associated with the asset category.
	*
	* @param pk the primary key of the asset category to get the number of associated asset entries for
	* @return the number of asset entries associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetEntriesSize(pk);
	}

	/**
	* Determines if the asset entry is associated with the asset category.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	* @return <code>true</code> if the asset entry is associated with the asset category; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetEntry(pk, assetEntryPK);
	}

	/**
	* Determines if the asset category has any asset entries associated with it.
	*
	* @param pk the primary key of the asset category to check for associations with asset entries
	* @return <code>true</code> if the asset category has any asset entries associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetEntries(pk);
	}

	/**
	* Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntry(pk, assetEntryPK);
	}

	/**
	* Adds an association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntry(pk, assetEntry);
	}

	/**
	* Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Adds an association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetEntries(pk, assetEntries);
	}

	/**
	* Clears all associations between the asset category and its asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category to clear the associated asset entries from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetEntries(pk);
	}

	/**
	* Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPK the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntry(long pk, long assetEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntryPK);
	}

	/**
	* Removes the association between the asset category and the asset entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntry the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntry(long pk,
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntry(pk, assetEntry);
	}

	/**
	* Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntryPKs the primary keys of the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Removes the association between the asset category and the asset entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category
	* @param assetEntries the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetEntries(pk, assetEntries);
	}

	/**
	* Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category to set the associations for
	* @param assetEntryPKs the primary keys of the asset entries to be associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetEntries(long pk, long[] assetEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetEntries(pk, assetEntryPKs);
	}

	/**
	* Sets the asset entries associated with the asset category, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset category to set the associations for
	* @param assetEntries the asset entries to be associated with the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetEntries(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetEntries(pk, assetEntries);
	}

	/**
	* Rebuilds the asset categories tree for the scope using the modified pre-order tree traversal algorithm.
	*
	* <p>
	* Only call this method if the tree has become stale through operations other than normal CRUD. Under normal circumstances the tree is automatically rebuilt whenver necessary.
	* </p>
	*
	* @param groupId the id of the scope to rebuild the tree for
	* @param force whether to force the rebuild even if the tree is not stale
	*/
	public static void rebuildTree(long groupId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().rebuildTree(groupId, force);
	}

	public static AssetCategoryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetCategoryPersistence)PortalBeanLocatorUtil.locate(AssetCategoryPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetCategoryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(AssetCategoryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(AssetCategoryUtil.class,
			"_persistence");
	}

	private static AssetCategoryPersistence _persistence;
}