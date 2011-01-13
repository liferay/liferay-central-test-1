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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;

/**
 * The persistence interface for the layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPersistenceImpl
 * @see LayoutUtil
 * @generated
 */
public interface LayoutPersistence extends BasePersistence<Layout> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutUtil} to access the layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the layout in the entity cache if it is enabled.
	*
	* @param layout the layout to cache
	*/
	public void cacheResult(com.liferay.portal.model.Layout layout);

	/**
	* Caches the layouts in the entity cache if it is enabled.
	*
	* @param layouts the layouts to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Layout> layouts);

	/**
	* Creates a new layout with the primary key. Does not add the layout to the database.
	*
	* @param plid the primary key for the new layout
	* @return the new layout
	*/
	public com.liferay.portal.model.Layout create(long plid);

	/**
	* Removes the layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the layout to remove
	* @return the layout that was removed
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout remove(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Layout updateImpl(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param plid the primary key of the layout to find
	* @return the layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByPrimaryKey(long plid)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param plid the primary key of the layout to find
	* @return the layout, or <code>null</code> if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByPrimaryKey(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByUuid_PrevAndNext(long plid,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByGroupId_PrevAndNext(
		long plid, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByCompanyId_PrevAndNext(
		long plid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where iconImageId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByIconImageId(long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where iconImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param iconImageId the icon image ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByIconImageId(
		long iconImageId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_First(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_Last(long groupId,
		boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByG_P_PrevAndNext(long plid,
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByG_P_L(long groupId,
		boolean privateLayout, long layoutId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_P_First(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_P_Last(long groupId,
		boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByG_P_P_PrevAndNext(
		long plid, long groupId, boolean privateLayout, long parentLayoutId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the matching layout, or <code>null</code> if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout fetchByG_P_F(long groupId,
		boolean privateLayout, java.lang.String friendlyURL,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_T_First(long groupId,
		boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout
	* @throws com.liferay.portal.NoSuchLayoutException if a matching layout could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout findByG_P_T_Last(long groupId,
		boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layouts before and after the current layout in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the primary key of the current layout
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout
	* @throws com.liferay.portal.NoSuchLayoutException if a layout with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Layout[] findByG_P_T_PrevAndNext(
		long plid, long groupId, boolean privateLayout, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> filterFindByG_P_T(
		long groupId, boolean privateLayout, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layouts.
	*
	* @return the layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @return the range of layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layouts to return
	* @param end the upper bound of the range of layouts to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of layouts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Layout> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout where iconImageId = &#63; from the database.
	*
	* @param iconImageId the icon image ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByIconImageId(long iconImageId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layouts from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where iconImageId = &#63;.
	*
	* @param iconImageId the icon image ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByIconImageId(long iconImageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param layoutId the layout ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param parentLayoutId the parent layout ID to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_P_P(long groupId, boolean privateLayout,
		long parentLayoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param friendlyURL the friendly u r l to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_F(long groupId, boolean privateLayout,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the number of matching layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the layouts where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param privateLayout the private layout to search with
	* @param type the type to search with
	* @return the number of matching layouts that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_P_T(long groupId, boolean privateLayout,
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layouts.
	*
	* @return the number of layouts
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public Layout remove(Layout layout) throws SystemException;
}