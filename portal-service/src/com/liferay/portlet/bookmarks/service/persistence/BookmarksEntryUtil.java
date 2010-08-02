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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.bookmarks.model.BookmarksEntry;

import java.util.List;

/**
 * The persistence utility for the bookmarks entry service. This utility wraps {@link BookmarksEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryPersistence
 * @see BookmarksEntryPersistenceImpl
 * @generated
 */
public class BookmarksEntryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(BookmarksEntry bookmarksEntry) {
		getPersistence().clearCache(bookmarksEntry);
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
	public static List<BookmarksEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<BookmarksEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<BookmarksEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static BookmarksEntry remove(BookmarksEntry bookmarksEntry)
		throws SystemException {
		return getPersistence().remove(bookmarksEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static BookmarksEntry update(BookmarksEntry bookmarksEntry,
		boolean merge) throws SystemException {
		return getPersistence().update(bookmarksEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static BookmarksEntry update(BookmarksEntry bookmarksEntry,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(bookmarksEntry, merge, serviceContext);
	}

	/**
	* Caches the bookmarks entry in the entity cache if it is enabled.
	*
	* @param bookmarksEntry the bookmarks entry to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry) {
		getPersistence().cacheResult(bookmarksEntry);
	}

	/**
	* Caches the bookmarks entries in the entity cache if it is enabled.
	*
	* @param bookmarksEntries the bookmarks entries to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> bookmarksEntries) {
		getPersistence().cacheResult(bookmarksEntries);
	}

	/**
	* Creates a new bookmarks entry with the primary key. Does not add the bookmarks entry to the database.
	*
	* @param entryId the primary key for the new bookmarks entry
	* @return the new bookmarks entry
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the bookmarks entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the bookmarks entry to remove
	* @return the bookmarks entry that was removed
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(bookmarksEntry, merge);
	}

	/**
	* Finds the bookmarks entry with the primary key or throws a {@link com.liferay.portlet.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry to find
	* @return the bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Finds the bookmarks entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the bookmarks entry to find
	* @return the bookmarks entry, or <code>null</code> if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Finds all the bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the bookmarks entries before and after the current bookmarks entry in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(entryId, uuid, orderByComparator);
	}

	/**
	* Finds the bookmarks entry where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.bookmarks.NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Finds the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Finds the bookmarks entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the matching bookmarks entry, or <code>null</code> if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Finds all the bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(entryId, groupId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the bookmarks entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Finds a range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	/**
	* Finds the first bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	/**
	* Finds the last bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	/**
	* Finds the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_PrevAndNext(entryId, groupId, userId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_U(groupId, userId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_U(groupId, userId, start, end,
			orderByComparator);
	}

	/**
	* Finds all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId);
	}

	/**
	* Finds a range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderId, start, end, orderByComparator);
	}

	/**
	* Finds the first bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_F_First(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_F_First(groupId, folderId, orderByComparator);
	}

	/**
	* Finds the last bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a matching bookmarks entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_F_Last(
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_F_Last(groupId, folderId, orderByComparator);
	}

	/**
	* Finds the bookmarks entries before and after the current bookmarks entry in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current bookmarks entry
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next bookmarks entry
	* @throws com.liferay.portlet.bookmarks.NoSuchEntryException if a bookmarks entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByG_F_PrevAndNext(
		long entryId, long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		return getPersistence()
				   .findByG_F_PrevAndNext(entryId, groupId, folderId,
			orderByComparator);
	}

	/**
	* Finds all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @return the matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderIds);
	}

	/**
	* Finds a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_F(groupId, folderIds, start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_F(groupId, folderIds, start, end, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderId, start, end,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @return the matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderIds);
	}

	/**
	* Filters by the user's permissions and finds a range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_F(groupId, folderIds, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_F(groupId, folderIds, start, end,
			orderByComparator);
	}

	/**
	* Finds all the bookmarks entries.
	*
	* @return the bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @return the range of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the bookmarks entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of bookmarks entries to return
	* @param end the upper bound of the range of bookmarks entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the bookmarks entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the bookmarks entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Removes all the bookmarks entries where groupId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the bookmarks entries where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Removes all the bookmarks entries where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_F(groupId, folderId);
	}

	/**
	* Removes all the bookmarks entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the bookmarks entries where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the bookmarks entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid to search with
	* @param groupId the group id to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Counts all the bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the bookmarks entries where groupId = &#63;.
	*
	* @param groupId the group id to search with
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Filters by the user's permissions and counts all the bookmarks entries where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group id to search with
	* @param userId the user id to search with
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_U(groupId, userId);
	}

	/**
	* Counts all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderId);
	}

	/**
	* Counts all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @return the number of matching bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_F(groupId, folderIds);
	}

	/**
	* Filters by the user's permissions and counts all the bookmarks entries where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group id to search with
	* @param folderId the folder id to search with
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	/**
	* Filters by the user's permissions and counts all the bookmarks entries where groupId = &#63; and folderId = any &#63;.
	*
	* @param groupId the group id to search with
	* @param folderIds the folder ids to search with
	* @return the number of matching bookmarks entries that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_F(long groupId, long[] folderIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_F(groupId, folderIds);
	}

	/**
	* Counts all the bookmarks entries.
	*
	* @return the number of bookmarks entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static BookmarksEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (BookmarksEntryPersistence)PortalBeanLocatorUtil.locate(BookmarksEntryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(BookmarksEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static BookmarksEntryPersistence _persistence;
}