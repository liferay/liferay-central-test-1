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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;

import java.util.List;

/**
 * The persistence utility for the announcements flag service. This utility wraps {@link AnnouncementsFlagPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
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
 * @see AnnouncementsFlagPersistence
 * @see AnnouncementsFlagPersistenceImpl
 * @generated
 */
public class AnnouncementsFlagUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(AnnouncementsFlag announcementsFlag) {
		getPersistence().clearCache(announcementsFlag);
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
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnnouncementsFlag> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static AnnouncementsFlag remove(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		return getPersistence().remove(announcementsFlag);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static AnnouncementsFlag update(
		AnnouncementsFlag announcementsFlag, boolean merge)
		throws SystemException {
		return getPersistence().update(announcementsFlag, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static AnnouncementsFlag update(
		AnnouncementsFlag announcementsFlag, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(announcementsFlag, merge, serviceContext);
	}

	/**
	* Caches the announcements flag in the entity cache if it is enabled.
	*
	* @param announcementsFlag the announcements flag to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag) {
		getPersistence().cacheResult(announcementsFlag);
	}

	/**
	* Caches the announcements flags in the entity cache if it is enabled.
	*
	* @param announcementsFlags the announcements flags to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> announcementsFlags) {
		getPersistence().cacheResult(announcementsFlags);
	}

	/**
	* Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	*
	* @param flagId the primary key for the new announcements flag
	* @return the new announcements flag
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag create(
		long flagId) {
		return getPersistence().create(flagId);
	}

	/**
	* Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param flagId the primary key of the announcements flag to remove
	* @return the announcements flag that was removed
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag remove(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().remove(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(announcementsFlag, merge);
	}

	/**
	* Finds the announcements flag with the primary key or throws a {@link com.liferay.portlet.announcements.NoSuchFlagException} if it could not be found.
	*
	* @param flagId the primary key of the announcements flag to find
	* @return the announcements flag
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByPrimaryKey(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByPrimaryKey(flagId);
	}

	/**
	* Finds the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param flagId the primary key of the announcements flag to find
	* @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByPrimaryKey(
		long flagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(flagId);
	}

	/**
	* Finds all the announcements flags where entryId = &#63;.
	*
	* @param entryId the entry id to search with
	* @return the matching announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	/**
	* Finds a range of all the announcements flags where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry id to search with
	* @param start the lower bound of the range of announcements flags to return
	* @param end the upper bound of the range of announcements flags to return (not inclusive)
	* @return the range of matching announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	/**
	* Finds an ordered range of all the announcements flags where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry id to search with
	* @param start the lower bound of the range of announcements flags to return
	* @param end the upper bound of the range of announcements flags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Finds the first announcements flag in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching announcements flag
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_First(entryId, orderByComparator);
	}

	/**
	* Finds the last announcements flag in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching announcements flag
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Finds the announcements flags before and after the current announcements flag in the ordered set where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param flagId the primary key of the current announcements flag
	* @param entryId the entry id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next announcements flag
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag[] findByEntryId_PrevAndNext(
		long flagId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence()
				   .findByEntryId_PrevAndNext(flagId, entryId, orderByComparator);
	}

	/**
	* Finds the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a {@link com.liferay.portlet.announcements.NoSuchFlagException} if it could not be found.
	*
	* @param userId the user id to search with
	* @param entryId the entry id to search with
	* @param value the value to search with
	* @return the matching announcements flag
	* @throws com.liferay.portlet.announcements.NoSuchFlagException if a matching announcements flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByU_E_V(userId, entryId, value);
	}

	/**
	* Finds the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user id to search with
	* @param entryId the entry id to search with
	* @param value the value to search with
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByU_E_V(userId, entryId, value);
	}

	/**
	* Finds the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user id to search with
	* @param entryId the entry id to search with
	* @param value the value to search with
	* @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_E_V(userId, entryId, value, retrieveFromCache);
	}

	/**
	* Finds all the announcements flags.
	*
	* @return the announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags to return
	* @param end the upper bound of the range of announcements flags to return (not inclusive)
	* @return the range of announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags to return
	* @param end the upper bound of the range of announcements flags to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the announcements flags where entryId = &#63; from the database.
	*
	* @param entryId the entry id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	/**
	* Removes the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; from the database.
	*
	* @param userId the user id to search with
	* @param entryId the entry id to search with
	* @param value the value to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		getPersistence().removeByU_E_V(userId, entryId, value);
	}

	/**
	* Removes all the announcements flags from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the announcements flags where entryId = &#63;.
	*
	* @param entryId the entry id to search with
	* @return the number of matching announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	/**
	* Counts all the announcements flags where userId = &#63; and entryId = &#63; and value = &#63;.
	*
	* @param userId the user id to search with
	* @param entryId the entry id to search with
	* @param value the value to search with
	* @return the number of matching announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_E_V(userId, entryId, value);
	}

	/**
	* Counts all the announcements flags.
	*
	* @return the number of announcements flags
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementsFlagPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AnnouncementsFlagPersistence)PortalBeanLocatorUtil.locate(AnnouncementsFlagPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(AnnouncementsFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementsFlagPersistence _persistence;
}