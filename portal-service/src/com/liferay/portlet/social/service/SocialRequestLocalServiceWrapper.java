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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialRequestLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestLocalService
 * @generated
 */
public class SocialRequestLocalServiceWrapper
	implements SocialRequestLocalService {
	public SocialRequestLocalServiceWrapper(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	/**
	* Adds the social request to the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request to add
	* @return the social request that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest addSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addSocialRequest(socialRequest);
	}

	/**
	* Creates a new social request with the primary key. Does not add the social request to the database.
	*
	* @param requestId the primary key for the new social request
	* @return the new social request
	*/
	public com.liferay.portlet.social.model.SocialRequest createSocialRequest(
		long requestId) {
		return _socialRequestLocalService.createSocialRequest(requestId);
	}

	/**
	* Deletes the social request with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param requestId the primary key of the social request to delete
	* @throws PortalException if a social request with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteSocialRequest(requestId);
	}

	/**
	* Deletes the social request from the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteSocialRequest(socialRequest);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery);
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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start, end);
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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.dynamicQuery(dynamicQuery, start,
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
		return _socialRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the social request with the primary key.
	*
	* @param requestId the primary key of the social request to get
	* @return the social request
	* @throws PortalException if a social request with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest getSocialRequest(
		long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequest(requestId);
	}

	/**
	* Gets the social request with the UUID and group id.
	*
	* @param uuid the UUID of social request to get
	* @param groupId the group id of the social request to get
	* @return the social request
	* @throws PortalException if a social request with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest getSocialRequestByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the social requests.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social requests to return
	* @param end the upper bound of the range of social requests to return (not inclusive)
	* @return the range of social requests
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getSocialRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequests(start, end);
	}

	/**
	* Gets the number of social requests.
	*
	* @return the number of social requests
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getSocialRequestsCount();
	}

	/**
	* Updates the social request in the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request to update
	* @return the social request that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateSocialRequest(socialRequest);
	}

	/**
	* Updates the social request in the database. Also notifies the appropriate model listeners.
	*
	* @param socialRequest the social request to update
	* @param merge whether to merge the social request with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social request that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialRequest updateSocialRequest(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateSocialRequest(socialRequest,
			merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialRequestLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialRequestLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.social.model.SocialRequest addRequest(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.addRequest(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public void deleteReceiverUserRequests(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteReceiverUserRequests(receiverUserId);
	}

	public void deleteRequest(long requestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteRequest(requestId);
	}

	public void deleteRequest(
		com.liferay.portlet.social.model.SocialRequest request)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteRequest(request);
	}

	public void deleteUserRequests(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRequestLocalService.deleteUserRequests(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getReceiverUserRequests(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequests(receiverUserId,
			status, start, end);
	}

	public int getReceiverUserRequestsCount(long receiverUserId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId);
	}

	public int getReceiverUserRequestsCount(long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getReceiverUserRequestsCount(receiverUserId,
			status);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> getUserRequests(
		long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequests(userId, status,
			start, end);
	}

	public int getUserRequestsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId);
	}

	public int getUserRequestsCount(long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.getUserRequestsCount(userId, status);
	}

	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, status);
	}

	public boolean hasRequest(long userId, java.lang.String className,
		long classPK, int type, long receiverUserId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.hasRequest(userId, className,
			classPK, type, receiverUserId, status);
	}

	public com.liferay.portlet.social.model.SocialRequest updateRequest(
		long requestId, int status,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRequestLocalService.updateRequest(requestId, status,
			themeDisplay);
	}

	public SocialRequestLocalService getWrappedSocialRequestLocalService() {
		return _socialRequestLocalService;
	}

	public void setWrappedSocialRequestLocalService(
		SocialRequestLocalService socialRequestLocalService) {
		_socialRequestLocalService = socialRequestLocalService;
	}

	private SocialRequestLocalService _socialRequestLocalService;
}