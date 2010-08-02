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

package com.liferay.counter.service;

/**
 * <p>
 * This class is a wrapper for {@link CounterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CounterLocalService
 * @generated
 */
public class CounterLocalServiceWrapper implements CounterLocalService {
	public CounterLocalServiceWrapper(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	/**
	* Adds the counter to the database. Also notifies the appropriate model listeners.
	*
	* @param counter the counter to add
	* @return the counter that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter addCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.addCounter(counter);
	}

	/**
	* Creates a new counter with the primary key. Does not add the counter to the database.
	*
	* @param name the primary key for the new counter
	* @return the new counter
	*/
	public com.liferay.counter.model.Counter createCounter(
		java.lang.String name) {
		return _counterLocalService.createCounter(name);
	}

	/**
	* Deletes the counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param name the primary key of the counter to delete
	* @throws PortalException if a counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCounter(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.deleteCounter(name);
	}

	/**
	* Deletes the counter from the database. Also notifies the appropriate model listeners.
	*
	* @param counter the counter to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteCounter(com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.deleteCounter(counter);
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
		return _counterLocalService.dynamicQuery(dynamicQuery);
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
		return _counterLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _counterLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _counterLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the counter with the primary key.
	*
	* @param name the primary key of the counter to get
	* @return the counter
	* @throws PortalException if a counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter getCounter(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCounter(name);
	}

	/**
	* Gets a range of all the counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of counters to return
	* @param end the upper bound of the range of counters to return (not inclusive)
	* @return the range of counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.counter.model.Counter> getCounters(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCounters(start, end);
	}

	/**
	* Gets the number of counters.
	*
	* @return the number of counters
	* @throws SystemException if a system exception occurred
	*/
	public int getCountersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCountersCount();
	}

	/**
	* Updates the counter in the database. Also notifies the appropriate model listeners.
	*
	* @param counter the counter to update
	* @return the counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter updateCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.updateCounter(counter);
	}

	/**
	* Updates the counter in the database. Also notifies the appropriate model listeners.
	*
	* @param counter the counter to update
	* @param merge whether to merge the counter with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.counter.model.Counter updateCounter(
		com.liferay.counter.model.Counter counter, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.updateCounter(counter, merge);
	}

	public java.util.List<java.lang.String> getNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getNames();
	}

	public long increment()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.increment();
	}

	public long increment(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.increment(name);
	}

	public long increment(java.lang.String name, int size)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.increment(name, size);
	}

	public void rename(java.lang.String oldName, java.lang.String newName)
		throws com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.rename(oldName, newName);
	}

	public void reset(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.reset(name);
	}

	public void reset(java.lang.String name, long size)
		throws com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.reset(name, size);
	}

	public CounterLocalService getWrappedCounterLocalService() {
		return _counterLocalService;
	}

	private CounterLocalService _counterLocalService;
}