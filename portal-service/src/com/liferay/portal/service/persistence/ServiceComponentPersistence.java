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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ServiceComponent;

/**
 * The persistence interface for the service component service.
 *
 * <p>
 * Never modify this interface directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentPersistenceImpl
 * @see ServiceComponentUtil
 * @generated
 */
public interface ServiceComponentPersistence extends BasePersistence<ServiceComponent> {
	/**
	* Caches the service component in the entity cache if it is enabled.
	*
	* @param serviceComponent the service component to cache
	*/
	public void cacheResult(
		com.liferay.portal.model.ServiceComponent serviceComponent);

	/**
	* Caches the service components in the entity cache if it is enabled.
	*
	* @param serviceComponents the service components to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.ServiceComponent> serviceComponents);

	/**
	* Creates a new service component with the primary key.
	*
	* @param serviceComponentId the primary key for the new service component
	* @return the new service component
	*/
	public com.liferay.portal.model.ServiceComponent create(
		long serviceComponentId);

	/**
	* Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceComponentId the primary key of the service component to remove
	* @return the service component that was removed
	* @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent remove(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service component with the primary key or throws a {@link com.liferay.portal.NoSuchServiceComponentException} if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component to find
	* @return the service component
	* @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent findByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service component with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceComponentId the primary key of the service component to find
	* @return the service component, or <code>null</code> if a service component with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent fetchByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace to search with
	* @return the matching service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param buildNamespace the build namespace to search with
	* @param start the lower bound of the range of service components to return
	* @param end the upper bound of the range of service components to return (not inclusive)
	* @return the range of matching service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the service components where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param buildNamespace the build namespace to search with
	* @param start the lower bound of the range of service components to return
	* @param end the upper bound of the range of service components to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first service component in the ordered set where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param buildNamespace the build namespace to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching service component
	* @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last service component in the ordered set where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param buildNamespace the build namespace to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching service component
	* @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service components before and after the current service component in the ordered set where buildNamespace = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param serviceComponentId the primary key of the current service component
	* @param buildNamespace the build namespace to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next service component
	* @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service component where buildNamespace = &#63; and buildNumber = &#63; or throws a {@link com.liferay.portal.NoSuchServiceComponentException} if it could not be found.
	*
	* @param buildNamespace the build namespace to search with
	* @param buildNumber the build number to search with
	* @return the matching service component
	* @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent findByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param buildNamespace the build namespace to search with
	* @param buildNumber the build number to search with
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param buildNamespace the build namespace to search with
	* @param buildNumber the build number to search with
	* @return the matching service component, or <code>null</code> if a matching service component could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the service components.
	*
	* @return the service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of service components to return
	* @param end the upper bound of the range of service components to return (not inclusive)
	* @return the range of service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the service components.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of service components to return
	* @param end the upper bound of the range of service components to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of service components
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the service components where buildNamespace = &#63; from the database.
	*
	* @param buildNamespace the build namespace to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the service component where buildNamespace = &#63; and buildNumber = &#63; from the database.
	*
	* @param buildNamespace the build namespace to search with
	* @param buildNumber the build number to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the service components from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the service components where buildNamespace = &#63;.
	*
	* @param buildNamespace the build namespace to search with
	* @return the number of matching service components
	* @throws SystemException if a system exception occurred
	*/
	public int countByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the service components where buildNamespace = &#63; and buildNumber = &#63;.
	*
	* @param buildNamespace the build namespace to search with
	* @param buildNumber the build number to search with
	* @return the number of matching service components
	* @throws SystemException if a system exception occurred
	*/
	public int countByBNS_BNU(java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the service components.
	*
	* @return the number of service components
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}