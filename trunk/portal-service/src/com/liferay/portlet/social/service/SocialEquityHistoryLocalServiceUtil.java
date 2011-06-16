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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the social equity history local service. This utility wraps {@link com.liferay.portlet.social.service.impl.SocialEquityHistoryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityHistoryLocalService
 * @see com.liferay.portlet.social.service.base.SocialEquityHistoryLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialEquityHistoryLocalServiceImpl
 * @generated
 */
public class SocialEquityHistoryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialEquityHistoryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the social equity history to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @return the social equity history that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory addSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityHistory(socialEquityHistory);
	}

	/**
	* Creates a new social equity history with the primary key. Does not add the social equity history to the database.
	*
	* @param equityHistoryId the primary key for the new social equity history
	* @return the new social equity history
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory createSocialEquityHistory(
		long equityHistoryId) {
		return getService().createSocialEquityHistory(equityHistoryId);
	}

	/**
	* Deletes the social equity history with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityHistoryId the primary key of the social equity history
	* @throws PortalException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityHistory(long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityHistory(equityHistoryId);
	}

	/**
	* Deletes the social equity history from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityHistory(socialEquityHistory);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social equity history with the primary key.
	*
	* @param equityHistoryId the primary key of the social equity history
	* @return the social equity history
	* @throws PortalException if a social equity history with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory getSocialEquityHistory(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistory(equityHistoryId);
	}

	/**
	* Returns a range of all the social equity histories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity histories
	* @param end the upper bound of the range of social equity histories (not inclusive)
	* @return the range of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> getSocialEquityHistories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistories(start, end);
	}

	/**
	* Returns the number of social equity histories.
	*
	* @return the number of social equity histories
	* @throws SystemException if a system exception occurred
	*/
	public static int getSocialEquityHistoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistoriesCount();
	}

	/**
	* Updates the social equity history in the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @return the social equity history that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityHistory(socialEquityHistory);
	}

	/**
	* Updates the social equity history in the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityHistory the social equity history
	* @param merge whether to merge the social equity history with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity history that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityHistory(socialEquityHistory, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static SocialEquityHistoryLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityHistoryLocalService)PortalBeanLocatorUtil.locate(SocialEquityHistoryLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialEquityHistoryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(SocialEquityHistoryLocalService.class);
		}

		return _service;
	}

	public void setService(SocialEquityHistoryLocalService service) {
		MethodCache.remove(SocialEquityHistoryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(SocialEquityHistoryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(SocialEquityHistoryLocalService.class);
	}

	private static SocialEquityHistoryLocalService _service;
}