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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the password tracker local service. This utility wraps {@link com.liferay.portal.service.impl.PasswordTrackerLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PasswordTrackerLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerLocalService
 * @see com.liferay.portal.service.base.PasswordTrackerLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.PasswordTrackerLocalServiceImpl
 * @generated
 */
public class PasswordTrackerLocalServiceUtil {
	/**
	* Adds the password tracker to the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to add
	* @return the password tracker that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PasswordTracker addPasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPasswordTracker(passwordTracker);
	}

	/**
	* Creates a new password tracker with the primary key. Does not add the password tracker to the database.
	*
	* @param passwordTrackerId the primary key for the new password tracker
	* @return the new password tracker
	*/
	public static com.liferay.portal.model.PasswordTracker createPasswordTracker(
		long passwordTrackerId) {
		return getService().createPasswordTracker(passwordTrackerId);
	}

	/**
	* Deletes the password tracker with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTrackerId the primary key of the password tracker to delete
	* @throws PortalException if a password tracker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deletePasswordTracker(long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTracker(passwordTrackerId);
	}

	/**
	* Deletes the password tracker from the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deletePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTracker(passwordTracker);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
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
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
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
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
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
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the password tracker with the primary key.
	*
	* @param passwordTrackerId the primary key of the password tracker to get
	* @return the password tracker
	* @throws PortalException if a password tracker with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PasswordTracker getPasswordTracker(
		long passwordTrackerId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTracker(passwordTrackerId);
	}

	/**
	* Gets a range of all the password trackers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of password trackers to return
	* @param end the upper bound of the range of password trackers to return (not inclusive)
	* @return the range of password trackers
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.PasswordTracker> getPasswordTrackers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTrackers(start, end);
	}

	/**
	* Gets the number of password trackers.
	*
	* @return the number of password trackers
	* @throws SystemException if a system exception occurred
	*/
	public static int getPasswordTrackersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPasswordTrackersCount();
	}

	/**
	* Updates the password tracker in the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to update
	* @return the password tracker that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePasswordTracker(passwordTracker);
	}

	/**
	* Updates the password tracker in the database. Also notifies the appropriate model listeners.
	*
	* @param passwordTracker the password tracker to update
	* @param merge whether to merge the password tracker with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the password tracker that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.PasswordTracker updatePasswordTracker(
		com.liferay.portal.model.PasswordTracker passwordTracker, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePasswordTracker(passwordTracker, merge);
	}

	public static void deletePasswordTrackers(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePasswordTrackers(userId);
	}

	public static boolean isSameAsCurrentPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isSameAsCurrentPassword(userId, newClearTextPwd);
	}

	public static boolean isValidPassword(long userId,
		java.lang.String newClearTextPwd)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().isValidPassword(userId, newClearTextPwd);
	}

	public static void trackPassword(long userId, java.lang.String encPassword)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().trackPassword(userId, encPassword);
	}

	public static PasswordTrackerLocalService getService() {
		if (_service == null) {
			_service = (PasswordTrackerLocalService)PortalBeanLocatorUtil.locate(PasswordTrackerLocalService.class.getName());

			ReferenceRegistry.registerReference(PasswordTrackerLocalServiceUtil.class,
				"_service");
			MethodCache.remove(PasswordTrackerLocalService.class);
		}

		return _service;
	}

	public void setService(PasswordTrackerLocalService service) {
		MethodCache.remove(PasswordTrackerLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(PasswordTrackerLocalServiceUtil.class,
			"_service");
		MethodCache.remove(PasswordTrackerLocalService.class);
	}

	private static PasswordTrackerLocalService _service;
}