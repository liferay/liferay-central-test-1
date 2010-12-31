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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (DLFileEntryPersistence)PortalBeanLocatorUtil.locate(DLFileEntryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		assertNotNull(dlFileEntry);

		assertEquals(dlFileEntry.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		_persistence.remove(newDLFileEntry);

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertNull(existingDLFileEntry);
	}

	public void testUpdateNew() throws Exception {
		addDLFileEntry();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		DLFileEntry newDLFileEntry = _persistence.create(pk);

		newDLFileEntry.setUuid(randomString());
		newDLFileEntry.setGroupId(nextLong());
		newDLFileEntry.setCompanyId(nextLong());
		newDLFileEntry.setUserId(nextLong());
		newDLFileEntry.setUserName(randomString());
		newDLFileEntry.setVersionUserId(nextLong());
		newDLFileEntry.setVersionUserName(randomString());
		newDLFileEntry.setCreateDate(nextDate());
		newDLFileEntry.setModifiedDate(nextDate());
		newDLFileEntry.setFolderId(nextLong());
		newDLFileEntry.setName(randomString());
		newDLFileEntry.setExtension(randomString());
		newDLFileEntry.setTitle(randomString());
		newDLFileEntry.setDescription(randomString());
		newDLFileEntry.setExtraSettings(randomString());
		newDLFileEntry.setVersion(randomString());
		newDLFileEntry.setSize(nextLong());
		newDLFileEntry.setReadCount(nextInt());

		_persistence.update(newDLFileEntry, false);

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry.getUuid(), newDLFileEntry.getUuid());
		assertEquals(existingDLFileEntry.getFileEntryId(),
			newDLFileEntry.getFileEntryId());
		assertEquals(existingDLFileEntry.getGroupId(),
			newDLFileEntry.getGroupId());
		assertEquals(existingDLFileEntry.getCompanyId(),
			newDLFileEntry.getCompanyId());
		assertEquals(existingDLFileEntry.getUserId(), newDLFileEntry.getUserId());
		assertEquals(existingDLFileEntry.getUserName(),
			newDLFileEntry.getUserName());
		assertEquals(existingDLFileEntry.getVersionUserId(),
			newDLFileEntry.getVersionUserId());
		assertEquals(existingDLFileEntry.getVersionUserName(),
			newDLFileEntry.getVersionUserName());
		assertEquals(Time.getShortTimestamp(existingDLFileEntry.getCreateDate()),
			Time.getShortTimestamp(newDLFileEntry.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingDLFileEntry.getModifiedDate()),
			Time.getShortTimestamp(newDLFileEntry.getModifiedDate()));
		assertEquals(existingDLFileEntry.getFolderId(),
			newDLFileEntry.getFolderId());
		assertEquals(existingDLFileEntry.getName(), newDLFileEntry.getName());
		assertEquals(existingDLFileEntry.getExtension(),
			newDLFileEntry.getExtension());
		assertEquals(existingDLFileEntry.getTitle(), newDLFileEntry.getTitle());
		assertEquals(existingDLFileEntry.getDescription(),
			newDLFileEntry.getDescription());
		assertEquals(existingDLFileEntry.getExtraSettings(),
			newDLFileEntry.getExtraSettings());
		assertEquals(existingDLFileEntry.getVersion(),
			newDLFileEntry.getVersion());
		assertEquals(existingDLFileEntry.getSize(), newDLFileEntry.getSize());
		assertEquals(existingDLFileEntry.getReadCount(),
			newDLFileEntry.getReadCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.findByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFileEntryException");
		}
		catch (NoSuchFileEntryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DLFileEntry existingDLFileEntry = _persistence.fetchByPrimaryKey(newDLFileEntry.getPrimaryKey());

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		DLFileEntry missingDLFileEntry = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingDLFileEntry);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DLFileEntry newDLFileEntry = addDLFileEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId",
				newDLFileEntry.getFileEntryId()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		DLFileEntry existingDLFileEntry = result.get(0);

		assertEquals(existingDLFileEntry, newDLFileEntry);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DLFileEntry.class,
				DLFileEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("fileEntryId", nextLong()));

		List<DLFileEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected DLFileEntry addDLFileEntry() throws Exception {
		long pk = nextLong();

		DLFileEntry dlFileEntry = _persistence.create(pk);

		dlFileEntry.setUuid(randomString());
		dlFileEntry.setGroupId(nextLong());
		dlFileEntry.setCompanyId(nextLong());
		dlFileEntry.setUserId(nextLong());
		dlFileEntry.setUserName(randomString());
		dlFileEntry.setVersionUserId(nextLong());
		dlFileEntry.setVersionUserName(randomString());
		dlFileEntry.setCreateDate(nextDate());
		dlFileEntry.setModifiedDate(nextDate());
		dlFileEntry.setFolderId(nextLong());
		dlFileEntry.setName(randomString());
		dlFileEntry.setExtension(randomString());
		dlFileEntry.setTitle(randomString());
		dlFileEntry.setDescription(randomString());
		dlFileEntry.setExtraSettings(randomString());
		dlFileEntry.setVersion(randomString());
		dlFileEntry.setSize(nextLong());
		dlFileEntry.setReadCount(nextInt());

		_persistence.update(dlFileEntry, false);

		return dlFileEntry;
	}

	private DLFileEntryPersistence _persistence;
}