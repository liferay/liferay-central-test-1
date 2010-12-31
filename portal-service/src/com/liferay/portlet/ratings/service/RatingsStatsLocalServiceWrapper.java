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

package com.liferay.portlet.ratings.service;

/**
 * <p>
 * This class is a wrapper for {@link RatingsStatsLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStatsLocalService
 * @generated
 */
public class RatingsStatsLocalServiceWrapper implements RatingsStatsLocalService {
	public RatingsStatsLocalServiceWrapper(
		RatingsStatsLocalService ratingsStatsLocalService) {
		_ratingsStatsLocalService = ratingsStatsLocalService;
	}

	/**
	* Adds the ratings stats to the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats to add
	* @return the ratings stats that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats addRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.addRatingsStats(ratingsStats);
	}

	/**
	* Creates a new ratings stats with the primary key. Does not add the ratings stats to the database.
	*
	* @param statsId the primary key for the new ratings stats
	* @return the new ratings stats
	*/
	public com.liferay.portlet.ratings.model.RatingsStats createRatingsStats(
		long statsId) {
		return _ratingsStatsLocalService.createRatingsStats(statsId);
	}

	/**
	* Deletes the ratings stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the ratings stats to delete
	* @throws PortalException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRatingsStats(long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteRatingsStats(statsId);
	}

	/**
	* Deletes the ratings stats from the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteRatingsStats(ratingsStats);
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
		return _ratingsStatsLocalService.dynamicQuery(dynamicQuery);
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
		return _ratingsStatsLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _ratingsStatsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the ratings stats with the primary key.
	*
	* @param statsId the primary key of the ratings stats to get
	* @return the ratings stats
	* @throws PortalException if a ratings stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats getRatingsStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStats(statsId);
	}

	/**
	* Gets a range of all the ratings statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of ratings statses to return
	* @param end the upper bound of the range of ratings statses to return (not inclusive)
	* @return the range of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getRatingsStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStatses(start, end);
	}

	/**
	* Gets the number of ratings statses.
	*
	* @return the number of ratings statses
	* @throws SystemException if a system exception occurred
	*/
	public int getRatingsStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getRatingsStatsesCount();
	}

	/**
	* Updates the ratings stats in the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats to update
	* @return the ratings stats that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats);
	}

	/**
	* Updates the ratings stats in the database. Also notifies the appropriate model listeners.
	*
	* @param ratingsStats the ratings stats to update
	* @param merge whether to merge the ratings stats with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the ratings stats that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.ratings.model.RatingsStats updateRatingsStats(
		com.liferay.portlet.ratings.model.RatingsStats ratingsStats,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats, merge);
	}

	public com.liferay.portlet.ratings.model.RatingsStats addStats(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.addStats(classNameId, classPK);
	}

	public void deleteStats(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ratingsStatsLocalService.deleteStats(className, classPK);
	}

	public com.liferay.portlet.ratings.model.RatingsStats getStats(long statsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getStats(statsId);
	}

	public java.util.List<com.liferay.portlet.ratings.model.RatingsStats> getStats(
		java.lang.String className, java.util.List<java.lang.Long> classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getStats(className, classPKs);
	}

	public com.liferay.portlet.ratings.model.RatingsStats getStats(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsStatsLocalService.getStats(className, classPK);
	}

	public RatingsStatsLocalService getWrappedRatingsStatsLocalService() {
		return _ratingsStatsLocalService;
	}

	public void setWrappedRatingsStatsLocalService(
		RatingsStatsLocalService ratingsStatsLocalService) {
		_ratingsStatsLocalService = ratingsStatsLocalService;
	}

	private RatingsStatsLocalService _ratingsStatsLocalService;
}