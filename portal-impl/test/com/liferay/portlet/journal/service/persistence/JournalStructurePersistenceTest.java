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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalStructure;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalStructurePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalStructurePersistence)PortalBeanLocatorUtil.locate(JournalStructurePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalStructure journalStructure = _persistence.create(pk);

		assertNotNull(journalStructure);

		assertEquals(journalStructure.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalStructure newJournalStructure = addJournalStructure();

		_persistence.remove(newJournalStructure);

		JournalStructure existingJournalStructure = _persistence.fetchByPrimaryKey(newJournalStructure.getPrimaryKey());

		assertNull(existingJournalStructure);
	}

	public void testUpdateNew() throws Exception {
		addJournalStructure();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalStructure newJournalStructure = _persistence.create(pk);

		newJournalStructure.setUuid(randomString());
		newJournalStructure.setGroupId(nextLong());
		newJournalStructure.setCompanyId(nextLong());
		newJournalStructure.setUserId(nextLong());
		newJournalStructure.setUserName(randomString());
		newJournalStructure.setCreateDate(nextDate());
		newJournalStructure.setModifiedDate(nextDate());
		newJournalStructure.setStructureId(randomString());
		newJournalStructure.setParentStructureId(randomString());
		newJournalStructure.setName(randomString());
		newJournalStructure.setDescription(randomString());
		newJournalStructure.setXsd(randomString());

		_persistence.update(newJournalStructure, false);

		JournalStructure existingJournalStructure = _persistence.findByPrimaryKey(newJournalStructure.getPrimaryKey());

		assertEquals(existingJournalStructure.getUuid(),
			newJournalStructure.getUuid());
		assertEquals(existingJournalStructure.getId(),
			newJournalStructure.getId());
		assertEquals(existingJournalStructure.getGroupId(),
			newJournalStructure.getGroupId());
		assertEquals(existingJournalStructure.getCompanyId(),
			newJournalStructure.getCompanyId());
		assertEquals(existingJournalStructure.getUserId(),
			newJournalStructure.getUserId());
		assertEquals(existingJournalStructure.getUserName(),
			newJournalStructure.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalStructure.getCreateDate()),
			Time.getShortTimestamp(newJournalStructure.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalStructure.getModifiedDate()),
			Time.getShortTimestamp(newJournalStructure.getModifiedDate()));
		assertEquals(existingJournalStructure.getStructureId(),
			newJournalStructure.getStructureId());
		assertEquals(existingJournalStructure.getParentStructureId(),
			newJournalStructure.getParentStructureId());
		assertEquals(existingJournalStructure.getName(),
			newJournalStructure.getName());
		assertEquals(existingJournalStructure.getDescription(),
			newJournalStructure.getDescription());
		assertEquals(existingJournalStructure.getXsd(),
			newJournalStructure.getXsd());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalStructure newJournalStructure = addJournalStructure();

		JournalStructure existingJournalStructure = _persistence.findByPrimaryKey(newJournalStructure.getPrimaryKey());

		assertEquals(existingJournalStructure, newJournalStructure);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchStructureException");
		}
		catch (NoSuchStructureException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalStructure newJournalStructure = addJournalStructure();

		JournalStructure existingJournalStructure = _persistence.fetchByPrimaryKey(newJournalStructure.getPrimaryKey());

		assertEquals(existingJournalStructure, newJournalStructure);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalStructure missingJournalStructure = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalStructure);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalStructure newJournalStructure = addJournalStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalStructure.class,
				JournalStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalStructure.getId()));

		List<JournalStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalStructure existingJournalStructure = result.get(0);

		assertEquals(existingJournalStructure, newJournalStructure);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalStructure.class,
				JournalStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<JournalStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected JournalStructure addJournalStructure() throws Exception {
		long pk = nextLong();

		JournalStructure journalStructure = _persistence.create(pk);

		journalStructure.setUuid(randomString());
		journalStructure.setGroupId(nextLong());
		journalStructure.setCompanyId(nextLong());
		journalStructure.setUserId(nextLong());
		journalStructure.setUserName(randomString());
		journalStructure.setCreateDate(nextDate());
		journalStructure.setModifiedDate(nextDate());
		journalStructure.setStructureId(randomString());
		journalStructure.setParentStructureId(randomString());
		journalStructure.setName(randomString());
		journalStructure.setDescription(randomString());
		journalStructure.setXsd(randomString());

		_persistence.update(journalStructure, false);

		return journalStructure;
	}

	private JournalStructurePersistence _persistence;
}