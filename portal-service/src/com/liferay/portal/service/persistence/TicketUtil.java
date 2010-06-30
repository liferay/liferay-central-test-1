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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * <a href="TicketUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TicketPersistence
 * @see       TicketPersistenceImpl
 * @generated
 */
public class TicketUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Ticket ticket) {
		getPersistence().clearCache(ticket);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Ticket> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Ticket remove(Ticket ticket) throws SystemException {
		return getPersistence().remove(ticket);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Ticket update(Ticket ticket, boolean merge)
		throws SystemException {
		return getPersistence().update(ticket, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Ticket update(Ticket ticket, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(ticket, merge, serviceContext);
	}

	public static void cacheResult(com.liferay.portal.model.Ticket ticket) {
		getPersistence().cacheResult(ticket);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Ticket> tickets) {
		getPersistence().cacheResult(tickets);
	}

	public static com.liferay.portal.model.Ticket create(long ticketId) {
		return getPersistence().create(ticketId);
	}

	public static com.liferay.portal.model.Ticket remove(long ticketId)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(ticketId);
	}

	public static com.liferay.portal.model.Ticket updateImpl(
		com.liferay.portal.model.Ticket ticket, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(ticket, merge);
	}

	public static com.liferay.portal.model.Ticket findByPrimaryKey(
		long ticketId)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(ticketId);
	}

	public static com.liferay.portal.model.Ticket fetchByPrimaryKey(
		long ticketId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(ticketId);
	}

	public static com.liferay.portal.model.Ticket findByKey(
		java.lang.String key)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByKey(key);
	}

	public static com.liferay.portal.model.Ticket fetchByKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByKey(key);
	}

	public static com.liferay.portal.model.Ticket fetchByKey(
		java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByKey(key, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Ticket> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Ticket> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Ticket> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByKey(java.lang.String key)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByKey(key);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByKey(java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByKey(key);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TicketPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TicketPersistence)PortalBeanLocatorUtil.locate(TicketPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(TicketPersistence persistence) {
		_persistence = persistence;
	}

	private static TicketPersistence _persistence;
}