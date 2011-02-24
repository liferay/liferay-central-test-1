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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialEquitySetting;

/**
 * The persistence interface for the social equity setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquitySettingPersistenceImpl
 * @see SocialEquitySettingUtil
 * @generated
 */
public interface SocialEquitySettingPersistence extends BasePersistence<SocialEquitySetting> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialEquitySettingUtil} to access the social equity setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the social equity setting in the entity cache if it is enabled.
	*
	* @param socialEquitySetting the social equity setting to cache
	*/
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting);

	/**
	* Caches the social equity settings in the entity cache if it is enabled.
	*
	* @param socialEquitySettings the social equity settings to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> socialEquitySettings);

	/**
	* Creates a new social equity setting with the primary key. Does not add the social equity setting to the database.
	*
	* @param equitySettingId the primary key for the new social equity setting
	* @return the new social equity setting
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting create(
		long equitySettingId);

	/**
	* Removes the social equity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equitySettingId the primary key of the social equity setting to remove
	* @return the social equity setting that was removed
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting remove(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting updateImpl(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the social equity setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	*
	* @param equitySettingId the primary key of the social equity setting to find
	* @return the social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting findByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Finds the social equity setting with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equitySettingId the primary key of the social equity setting to find
	* @return the social equity setting, or <code>null</code> if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting fetchByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @return the matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param start the lower bound of the range of social equity settings to return
	* @param end the upper bound of the range of social equity settings to return (not inclusive)
	* @return the range of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param start the lower bound of the range of social equity settings to return
	* @param end the upper bound of the range of social equity settings to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_First(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Finds the last social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_Last(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Finds the social equity settings before and after the current social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param equitySettingId the primary key of the current social equity setting
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting[] findByG_C_A_PrevAndNext(
		long equitySettingId, long groupId, long classNameId,
		java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param type the type to search with
	* @return the matching social equity setting
	* @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param type the type to search with
	* @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param type the type to search with
	* @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the social equity settings.
	*
	* @return the social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the social equity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity settings to return
	* @param end the upper bound of the range of social equity settings to return (not inclusive)
	* @return the range of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the social equity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity settings to return
	* @param end the upper bound of the range of social equity settings to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param type the type to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	/**
	* Removes all the social equity settings from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @return the number of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param actionId the action ID to search with
	* @param type the type to search with
	* @return the number of matching social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the social equity settings.
	*
	* @return the number of social equity settings
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public SocialEquitySetting remove(SocialEquitySetting socialEquitySetting)
		throws SystemException;
}