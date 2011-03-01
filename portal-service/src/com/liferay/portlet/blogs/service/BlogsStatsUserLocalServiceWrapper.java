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

package com.liferay.portlet.blogs.service;

/**
 * <p>
 * This class is a wrapper for {@link BlogsStatsUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserLocalService
 * @generated
 */
public class BlogsStatsUserLocalServiceWrapper
	implements BlogsStatsUserLocalService {
	public BlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	/**
	* Adds the blogs stats user to the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user to add
	* @return the blogs stats user that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsStatsUser addBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.addBlogsStatsUser(blogsStatsUser);
	}

	/**
	* Creates a new blogs stats user with the primary key. Does not add the blogs stats user to the database.
	*
	* @param statsUserId the primary key for the new blogs stats user
	* @return the new blogs stats user
	*/
	public com.liferay.portlet.blogs.model.BlogsStatsUser createBlogsStatsUser(
		long statsUserId) {
		return _blogsStatsUserLocalService.createBlogsStatsUser(statsUserId);
	}

	/**
	* Deletes the blogs stats user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsUserId the primary key of the blogs stats user to delete
	* @throws PortalException if a blogs stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBlogsStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteBlogsStatsUser(statsUserId);
	}

	/**
	* Deletes the blogs stats user from the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteBlogsStatsUser(blogsStatsUser);
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
		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery);
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
		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _blogsStatsUserLocalService.dynamicQuery(dynamicQuery, start,
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
		return _blogsStatsUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the blogs stats user with the primary key.
	*
	* @param statsUserId the primary key of the blogs stats user to get
	* @return the blogs stats user
	* @throws PortalException if a blogs stats user with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsStatsUser getBlogsStatsUser(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUser(statsUserId);
	}

	/**
	* Gets a range of all the blogs stats users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of blogs stats users to return
	* @param end the upper bound of the range of blogs stats users to return (not inclusive)
	* @return the range of blogs stats users
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getBlogsStatsUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUsers(start, end);
	}

	/**
	* Gets the number of blogs stats users.
	*
	* @return the number of blogs stats users
	* @throws SystemException if a system exception occurred
	*/
	public int getBlogsStatsUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getBlogsStatsUsersCount();
	}

	/**
	* Updates the blogs stats user in the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user to update
	* @return the blogs stats user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.updateBlogsStatsUser(blogsStatsUser);
	}

	/**
	* Updates the blogs stats user in the database. Also notifies the appropriate model listeners.
	*
	* @param blogsStatsUser the blogs stats user to update
	* @param merge whether to merge the blogs stats user with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the blogs stats user that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.blogs.model.BlogsStatsUser updateBlogsStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.updateBlogsStatsUser(blogsStatsUser,
			merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _blogsStatsUserLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_blogsStatsUserLocalService.setIdentifier(identifier);
	}

	public void deleteStatsUser(
		com.liferay.portlet.blogs.model.BlogsStatsUser statsUsers)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteStatsUser(statsUsers);
	}

	public void deleteStatsUser(long statsUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	public void deleteStatsUserByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	public void deleteStatsUserByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsers(companyId,
			start, end, obc);
	}

	public int getCompanyStatsUsersCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getCompanyStatsUsersCount(companyId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getGroupsStatsUsers(companyId,
			groupId, start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsers(groupId, start,
			end, obc);
	}

	public int getGroupStatsUsersCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getGroupStatsUsersCount(groupId);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsers(organizationId,
			start, end, obc);
	}

	public int getOrganizationStatsUsersCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getOrganizationStatsUsersCount(organizationId);
	}

	public com.liferay.portlet.blogs.model.BlogsStatsUser getStatsUser(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	public void updateStatsUser(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	public void updateStatsUser(long groupId, long userId,
		java.util.Date displayDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_blogsStatsUserLocalService.updateStatsUser(groupId, userId, displayDate);
	}

	public BlogsStatsUserLocalService getWrappedBlogsStatsUserLocalService() {
		return _blogsStatsUserLocalService;
	}

	public void setWrappedBlogsStatsUserLocalService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {
		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	private BlogsStatsUserLocalService _blogsStatsUserLocalService;
}