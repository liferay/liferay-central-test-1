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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchTicketException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class TicketPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (TicketPersistence)PortalBeanLocatorUtil.locate(TicketPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Ticket ticket = _persistence.create(pk);

		assertNotNull(ticket);

		assertEquals(ticket.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Ticket newTicket = addTicket();

		_persistence.remove(newTicket);

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		assertNull(existingTicket);
	}

	public void testUpdateNew() throws Exception {
		addTicket();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Ticket newTicket = _persistence.create(pk);

		newTicket.setCompanyId(nextLong());
		newTicket.setCreateDate(nextDate());
		newTicket.setClassNameId(nextLong());
		newTicket.setClassPK(nextLong());
		newTicket.setKey(randomString());
		newTicket.setExpirationDate(nextDate());

		_persistence.update(newTicket, false);

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		assertEquals(existingTicket.getTicketId(), newTicket.getTicketId());
		assertEquals(existingTicket.getCompanyId(), newTicket.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingTicket.getCreateDate()),
			Time.getShortTimestamp(newTicket.getCreateDate()));
		assertEquals(existingTicket.getClassNameId(), newTicket.getClassNameId());
		assertEquals(existingTicket.getClassPK(), newTicket.getClassPK());
		assertEquals(existingTicket.getKey(), newTicket.getKey());
		assertEquals(Time.getShortTimestamp(existingTicket.getExpirationDate()),
			Time.getShortTimestamp(newTicket.getExpirationDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		assertEquals(existingTicket, newTicket);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTicketException");
		}
		catch (NoSuchTicketException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		assertEquals(existingTicket, newTicket);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Ticket missingTicket = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingTicket);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Ticket newTicket = addTicket();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId",
				newTicket.getTicketId()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Ticket existingTicket = result.get(0);

		assertEquals(existingTicket, newTicket);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				Ticket.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId", nextLong()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Ticket addTicket() throws Exception {
		long pk = nextLong();

		Ticket ticket = _persistence.create(pk);

		ticket.setCompanyId(nextLong());
		ticket.setCreateDate(nextDate());
		ticket.setClassNameId(nextLong());
		ticket.setClassPK(nextLong());
		ticket.setKey(randomString());
		ticket.setExpirationDate(nextDate());

		_persistence.update(ticket, false);

		return ticket;
	}

	private TicketPersistence _persistence;
}