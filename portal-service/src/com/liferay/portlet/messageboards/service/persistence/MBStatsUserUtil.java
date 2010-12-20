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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.messageboards.model.MBStatsUser;

import java.util.List;

/**
 * The persistence utility for the message boards stats user service. This utility wraps {@link MBStatsUserPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserPersistence
 * @see MBStatsUserPersistenceImpl
 * @generated
 */
public class MBStatsUserUtil {
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
	public static void clearCache(MBStatsUser mbStatsUser) {
		getPersistence().clearCache(mbStatsUser);
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
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MBStatsUser> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static MBStatsUser remove(MBStatsUser mbStatsUser)
		throws SystemException {
		return getPersistence().remove(mbStatsUser);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBStatsUser update(MBStatsUser mbStatsUser, boolean merge)
		throws SystemException {
		return getPersistence().update(mbStatsUser, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static MBStatsUser update(MBStatsUser mbStatsUser, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mbStatsUser, merge, serviceContext);
	}

	/**
	* Caches the message boards stats user in the entity cache if it is enabled.
	*
	* @param mbStatsUser the message boards stats user to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser) {
		getPersistence().cacheResult(mbStatsUser);
	}

	/**
	* Caches the message boards stats users in the entity cache if it is enabled.
	*
	* @param mbStatsUsers the message boards stats users to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> mbStatsUsers) {
		getPersistence().cacheResult(mbStatsUsers);
	}

	/**
	* Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	*
	* @param statsUserId the primary key for the new message boards stats user
	* @return the new message boards stats user
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser create(
		long statsUserId) {
		return getPersistence().create(statsUserId);
	}

	/**
	* Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the message boards stats user to remove
	* @return the message boards stats user that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser remove(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().remove(statsUserId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser updateImpl(
		com.liferay.portlet.messageboards.model.MBStatsUser mbStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbStatsUser, merge);
	}

	/**
	* Finds the message boards stats user with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	*
	* @param statsUserId the primary key of the message boards stats user to find
	* @return the message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByPrimaryKey(statsUserId);
	}

	/**
	* Finds the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsUserId the primary key of the message boards stats user to find
	* @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser fetchByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(statsUserId);
	}

	/**
	* Finds all the message boards stats users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the message boards stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the message boards stats users where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByGroupId_PrevAndNext(
		long statsUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(statsUserId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the message boards stats users where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Finds a range of all the message boards stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Finds an ordered range of all the message boards stats users where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Finds the first message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Finds the last message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Finds the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByUserId_PrevAndNext(
		long statsUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence()
				   .findByUserId_PrevAndNext(statsUserId, userId,
			orderByComparator);
	}

	/**
	* Finds the message boards stats user where groupId = &#63; and userId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchStatsUserException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	* Finds the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId);
	}

	/**
	* Finds the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_U(groupId, userId, retrieveFromCache);
	}

	/**
	* Finds all the message boards stats users where groupId = &#63; and messageCount &ne; &#63;.
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @return the matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotM(
		long groupId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotM(groupId, messageCount);
	}

	/**
	* Finds a range of all the message boards stats users where groupId = &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @return the range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotM(
		long groupId, int messageCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_NotM(groupId, messageCount, start, end);
	}

	/**
	* Finds an ordered range of all the message boards stats users where groupId = &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findByG_NotM(
		long groupId, int messageCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_NotM(groupId, messageCount, start, end,
			orderByComparator);
	}

	/**
	* Finds the first message boards stats user in the ordered set where groupId = &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByG_NotM_First(
		long groupId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence()
				   .findByG_NotM_First(groupId, messageCount, orderByComparator);
	}

	/**
	* Finds the last message boards stats user in the ordered set where groupId = &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a matching message boards stats user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser findByG_NotM_Last(
		long groupId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence()
				   .findByG_NotM_Last(groupId, messageCount, orderByComparator);
	}

	/**
	* Finds the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and messageCount &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param statsUserId the primary key of the current message boards stats user
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next message boards stats user
	* @throws com.liferay.portlet.messageboards.NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.messageboards.model.MBStatsUser[] findByG_NotM_PrevAndNext(
		long statsUserId, long groupId, int messageCount,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		return getPersistence()
				   .findByG_NotM_PrevAndNext(statsUserId, groupId,
			messageCount, orderByComparator);
	}

	/**
	* Finds all the message boards stats users.
	*
	* @return the message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @return the range of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the message boards stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards stats users to return
	* @param end the upper bound of the range of message boards stats users to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.messageboards.model.MBStatsUser> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the message boards stats users where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the message boards stats users where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchStatsUserException {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	* Removes all the message boards stats users where groupId = &#63; and messageCount &ne; &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_NotM(long groupId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_NotM(groupId, messageCount);
	}

	/**
	* Removes all the message boards stats users from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the message boards stats users where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Counts all the message boards stats users where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Counts all the message boards stats users where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	* Counts all the message boards stats users where groupId = &#63; and messageCount &ne; &#63;.
	*
	* @param groupId the group ID to search with
	* @param messageCount the message count to search with
	* @return the number of matching message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_NotM(long groupId, int messageCount)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_NotM(groupId, messageCount);
	}

	/**
	* Counts all the message boards stats users.
	*
	* @return the number of message boards stats users
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBStatsUserPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBStatsUserPersistence)PortalBeanLocatorUtil.locate(MBStatsUserPersistence.class.getName());

			ReferenceRegistry.registerReference(MBStatsUserUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(MBStatsUserPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(MBStatsUserUtil.class,
			"_persistence");
	}

	private static MBStatsUserPersistence _persistence;
}