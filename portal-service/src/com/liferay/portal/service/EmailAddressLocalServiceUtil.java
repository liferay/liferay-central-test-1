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
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the email address local service. This utility wraps {@link com.liferay.portal.service.impl.EmailAddressLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.EmailAddressLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see EmailAddressLocalService
 * @see com.liferay.portal.service.base.EmailAddressLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.EmailAddressLocalServiceImpl
 * @generated
 */
public class EmailAddressLocalServiceUtil {
	/**
	* Adds the email address to the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address to add
	* @return the email address that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addEmailAddress(emailAddress);
	}

	/**
	* Creates a new email address with the primary key. Does not add the email address to the database.
	*
	* @param emailAddressId the primary key for the new email address
	* @return the new email address
	*/
	public static com.liferay.portal.model.EmailAddress createEmailAddress(
		long emailAddressId) {
		return getService().createEmailAddress(emailAddressId);
	}

	/**
	* Deletes the email address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddressId the primary key of the email address to delete
	* @throws PortalException if a email address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEmailAddress(long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEmailAddress(emailAddressId);
	}

	/**
	* Deletes the email address from the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEmailAddress(emailAddress);
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
	* Gets the email address with the primary key.
	*
	* @param emailAddressId the primary key of the email address to get
	* @return the email address
	* @throws PortalException if a email address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.EmailAddress getEmailAddress(
		long emailAddressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEmailAddress(emailAddressId);
	}

	/**
	* Gets a range of all the email addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of email addresses to return
	* @param end the upper bound of the range of email addresses to return (not inclusive)
	* @return the range of email addresses
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEmailAddresses(start, end);
	}

	/**
	* Gets the number of email addresses.
	*
	* @return the number of email addresses
	* @throws SystemException if a system exception occurred
	*/
	public static int getEmailAddressesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEmailAddressesCount();
	}

	/**
	* Updates the email address in the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address to update
	* @return the email address that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEmailAddress(emailAddress);
	}

	/**
	* Updates the email address in the database. Also notifies the appropriate model listeners.
	*
	* @param emailAddress the email address to update
	* @param merge whether to merge the email address with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the email address that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		com.liferay.portal.model.EmailAddress emailAddress, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEmailAddress(emailAddress, merge);
	}

	public static com.liferay.portal.model.EmailAddress addEmailAddress(
		long userId, java.lang.String className, long classPK,
		java.lang.String address, int typeId, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEmailAddress(userId, className, classPK, address,
			typeId, primary);
	}

	public static void deleteEmailAddresses(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEmailAddresses(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEmailAddresses();
	}

	public static java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEmailAddresses(companyId, className, classPK);
	}

	public static com.liferay.portal.model.EmailAddress updateEmailAddress(
		long emailAddressId, java.lang.String address, int typeId,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEmailAddress(emailAddressId, address, typeId, primary);
	}

	public static EmailAddressLocalService getService() {
		if (_service == null) {
			_service = (EmailAddressLocalService)PortalBeanLocatorUtil.locate(EmailAddressLocalService.class.getName());

			ReferenceRegistry.registerReference(EmailAddressLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	public void setService(EmailAddressLocalService service) {
		_service = service;

		ReferenceRegistry.registerReference(EmailAddressLocalServiceUtil.class,
			"_service");
	}

	private static EmailAddressLocalService _service;
}