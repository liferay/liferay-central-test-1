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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.List;

/**
 * The persistence utility for the asset entry service. This utility wraps {@link AssetEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistence
 * @see AssetEntryPersistenceImpl
 * @generated
 */
public class AssetEntryUtil {
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
	public static void clearCache(AssetEntry assetEntry) {
		getPersistence().clearCache(assetEntry);
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
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AssetEntry remove(AssetEntry assetEntry)
		throws SystemException {
		return getPersistence().remove(assetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AssetEntry update(AssetEntry assetEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(assetEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static AssetEntry update(AssetEntry assetEntry, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetEntry, merge, serviceContext);
	}

	/**
	* Caches the asset entry in the entity cache if it is enabled.
	*
	* @param assetEntry the asset entry to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().cacheResult(assetEntry);
	}

	/**
	* Caches the asset entries in the entity cache if it is enabled.
	*
	* @param assetEntries the asset entries to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().cacheResult(assetEntries);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public static com.liferay.portlet.asset.model.AssetEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry to remove
	* @return the asset entry that was removed
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetEntry, merge);
	}

	/**
	* Finds the asset entry with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the asset entry to find
	* @return the asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Finds the asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the asset entry to find
	* @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Finds all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Finds a range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Finds an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Finds the first asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Finds the last asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Finds the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current asset entry
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId,
			orderByComparator);
	}

	/**
	* Finds the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param classUuid the class uuid to search with
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByG_CU(groupId, classUuid);
	}

	/**
	* Finds the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param classUuid the class uuid to search with
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_CU(groupId, classUuid);
	}

	/**
	* Finds the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param classUuid the class uuid to search with
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CU(groupId, classUuid, retrieveFromCache);
	}

	/**
	* Finds the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Finds the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Finds the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Finds all the asset entries.
	*
	* @return the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @return the range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classUuid the class uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_CU(long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		getPersistence().removeByG_CU(groupId, classUuid);
	}

	/**
	* Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Removes all the asset entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Counts all the asset entries where groupId = &#63; and classUuid = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classUuid the class uuid to search with
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_CU(long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_CU(groupId, classUuid);
	}

	/**
	* Counts all the asset entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Counts all the asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Gets all the asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry to get the associated asset categories for
	* @return the asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk);
	}

	/**
	* Gets a range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry to get the associated asset categories for
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @return the range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk, start, end);
	}

	/**
	* Gets an ordered range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry to get the associated asset categories for
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getAssetCategories(pk, start, end, orderByComparator);
	}

	/**
	* Gets the number of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry to get the number of associated asset categories for
	* @return the number of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategoriesSize(pk);
	}

	/**
	* Determines if the asset category is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Determines if the asset entry has any asset categories associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset categories
	* @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategories(pk);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategory);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategories);
	}

	/**
	* Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset categories from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetCategories(pk);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategory);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategories);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to set the associations for
	* @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to set the associations for
	* @param assetCategories the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategories);
	}

	/**
	* Gets all the asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry to get the associated asset tags for
	* @return the asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk);
	}

	/**
	* Gets a range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry to get the associated asset tags for
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @return the range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end);
	}

	/**
	* Gets an ordered range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the asset entry to get the associated asset tags for
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end, orderByComparator);
	}

	/**
	* Gets the number of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry to get the number of associated asset tags for
	* @return the number of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetTagsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTagsSize(pk);
	}

	/**
	* Determines if the asset tag is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTag(pk, assetTagPK);
	}

	/**
	* Determines if the asset entry has any asset tags associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset tags
	* @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTags(pk);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTagPK);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTag);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTagPKs);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTags);
	}

	/**
	* Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset tags from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetTags(pk);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTagPK);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTag);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTagPKs);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTags);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to set the associations for
	* @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTagPKs);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to set the associations for
	* @param assetTags the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(AssetEntryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(AssetEntryUtil.class, "_persistence");
	}

	private static AssetEntryPersistence _persistence;
}