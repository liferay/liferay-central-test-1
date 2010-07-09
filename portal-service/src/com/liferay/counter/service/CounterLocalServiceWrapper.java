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
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public com.liferay.counter.model.Counter addCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.addCounter(counter);
	}

	public com.liferay.counter.model.Counter createCounter(
		java.lang.String name) {
		return _counterLocalService.createCounter(name);
	}

	public void deleteCounter(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.deleteCounter(name);
	}

	public void deleteCounter(com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		_counterLocalService.deleteCounter(counter);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.counter.model.Counter getCounter(java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCounter(name);
	}

	public java.util.List<com.liferay.counter.model.Counter> getCounters(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCounters(start, end);
	}

	public int getCountersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.getCountersCount();
	}

	public com.liferay.counter.model.Counter updateCounter(
		com.liferay.counter.model.Counter counter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _counterLocalService.updateCounter(counter);
	}

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