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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserLocalService
 * @generated
 */
public class SocialEquityUserLocalServiceWrapper
	implements SocialEquityUserLocalService {
	public SocialEquityUserLocalServiceWrapper(
		SocialEquityUserLocalService socialEquityUserLocalService) {
		_socialEquityUserLocalService = socialEquityUserLocalService;
	}

	/**
	* Adds the social equity user to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user to add
	* @return the social equity user that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser addSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.addSocialEquityUser(socialEquityUser);
	}

	/**
	* Creates a new social equity user with the primary key. Does not add the social equity user to the database.
	*
	* @param equityUserId the primary key for the new social equity user
	* @return the new social equity user
	*/
	public com.liferay.portlet.social.model.SocialEquityUser createSocialEquityUser(
		long equityUserId) {
		return _socialEquityUserLocalService.createSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityUserId the primary key of the social equity user to delete
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityUser(long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(equityUserId);
	}

	/**
	* Deletes the social equity user from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(socialEquityUser);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _socialEquityUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the social equity user with the primary key.
	*
	* @param equityUserId the primary key of the social equity user to get
	* @return the social equity user
	* @throws PortalException if a social equity user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUser(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUser(equityUserId);
	}

	/**
	* Gets a range of all the social equity users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity users to return
	* @param end the upper bound of the range of social equity users to return (not inclusive)
	* @return the range of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getSocialEquityUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsers(start, end);
	}

	/**
	* Gets the number of social equity users.
	*
	* @return the number of social equity users
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialEquityUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsersCount();
	}

	/**
	* Updates the social equity user in the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user to update
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser);
	}

	/**
	* Updates the social equity user in the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityUser the social equity user to update
	* @param merge whether to merge the social equity user with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser,
			merge);
	}

	public com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getContributionEquity(userId);
	}

	public com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getParticipationEquity(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getRankedSocialEquityUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getRankedSocialEquityUsers(groupId,
			start, end, orderByComparator);
	}

	public int getRankedSocialEquityUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getRankedSocialEquityUsersCount(groupId);
	}

	public SocialEquityUserLocalService getWrappedSocialEquityUserLocalService() {
		return _socialEquityUserLocalService;
	}

	private SocialEquityUserLocalService _socialEquityUserLocalService;
}