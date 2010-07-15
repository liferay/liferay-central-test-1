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

import com.liferay.portal.model.Ticket;

/**
 * @author    Brian Wing Shun Chan
 * @see       TicketPersistenceImpl
 * @see       TicketUtil
 * @generated
 */
public interface TicketPersistence extends BasePersistence<Ticket> {
	public void cacheResult(com.liferay.portal.model.Ticket ticket);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Ticket> tickets);

	public com.liferay.portal.model.Ticket create(long ticketId);

	public com.liferay.portal.model.Ticket remove(long ticketId)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket updateImpl(
		com.liferay.portal.model.Ticket ticket, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket findByPrimaryKey(long ticketId)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket fetchByPrimaryKey(long ticketId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket findByKey(java.lang.String key)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket fetchByKey(java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Ticket fetchByKey(java.lang.String key,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Ticket> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Ticket> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Ticket> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByKey(java.lang.String key)
		throws com.liferay.portal.NoSuchTicketException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByKey(java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}