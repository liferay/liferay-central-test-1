/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.trash.NoSuchEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.impl.TrashEntryModelImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TrashEntryPersistenceTest {
	@Before
	public void setUp() throws Exception {
		_persistence = (TrashEntryPersistence)PortalBeanLocatorUtil.locate(TrashEntryPersistence.class.getName());
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashEntry trashEntry = _persistence.create(pk);

		Assert.assertNotNull(trashEntry);

		Assert.assertEquals(trashEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		_persistence.remove(newTrashEntry);

		TrashEntry existingTrashEntry = _persistence.fetchByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertNull(existingTrashEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTrashEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashEntry newTrashEntry = _persistence.create(pk);

		newTrashEntry.setGroupId(ServiceTestUtil.nextLong());

		newTrashEntry.setCompanyId(ServiceTestUtil.nextLong());

		newTrashEntry.setClassNameId(ServiceTestUtil.nextLong());

		newTrashEntry.setClassPK(ServiceTestUtil.nextLong());

		newTrashEntry.setStatus(ServiceTestUtil.nextInt());

		newTrashEntry.setTrashedDate(ServiceTestUtil.nextDate());

		newTrashEntry.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(newTrashEntry, false);

		TrashEntry existingTrashEntry = _persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry.getEntryId(),
			newTrashEntry.getEntryId());
		Assert.assertEquals(existingTrashEntry.getGroupId(),
			newTrashEntry.getGroupId());
		Assert.assertEquals(existingTrashEntry.getCompanyId(),
			newTrashEntry.getCompanyId());
		Assert.assertEquals(existingTrashEntry.getClassNameId(),
			newTrashEntry.getClassNameId());
		Assert.assertEquals(existingTrashEntry.getClassPK(),
			newTrashEntry.getClassPK());
		Assert.assertEquals(existingTrashEntry.getStatus(),
			newTrashEntry.getStatus());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTrashEntry.getTrashedDate()),
			Time.getShortTimestamp(newTrashEntry.getTrashedDate()));
		Assert.assertEquals(existingTrashEntry.getTypeSettings(),
			newTrashEntry.getTypeSettings());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		TrashEntry existingTrashEntry = _persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEntryException");
		}
		catch (NoSuchEntryException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		TrashEntry existingTrashEntry = _persistence.fetchByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashEntry missingTrashEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTrashEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newTrashEntry.getEntryId()));

		List<TrashEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TrashEntry existingTrashEntry = result.get(0);

		Assert.assertEquals(existingTrashEntry, newTrashEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				ServiceTestUtil.nextLong()));

		List<TrashEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TrashEntry newTrashEntry = addTrashEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newTrashEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TrashEntry.class,
				TrashEntry.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		TrashEntry newTrashEntry = addTrashEntry();

		_persistence.clearCache();

		TrashEntryModelImpl existingTrashEntryModelImpl = (TrashEntryModelImpl)_persistence.findByPrimaryKey(newTrashEntry.getPrimaryKey());

		Assert.assertEquals(existingTrashEntryModelImpl.getClassNameId(),
			existingTrashEntryModelImpl.getOriginalClassNameId());
		Assert.assertEquals(existingTrashEntryModelImpl.getClassPK(),
			existingTrashEntryModelImpl.getOriginalClassPK());
	}

	protected TrashEntry addTrashEntry() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		TrashEntry trashEntry = _persistence.create(pk);

		trashEntry.setGroupId(ServiceTestUtil.nextLong());

		trashEntry.setCompanyId(ServiceTestUtil.nextLong());

		trashEntry.setClassNameId(ServiceTestUtil.nextLong());

		trashEntry.setClassPK(ServiceTestUtil.nextLong());

		trashEntry.setStatus(ServiceTestUtil.nextInt());

		trashEntry.setTrashedDate(ServiceTestUtil.nextDate());

		trashEntry.setTypeSettings(ServiceTestUtil.randomString());

		_persistence.update(trashEntry, false);

		return trashEntry;
	}

	private TrashEntryPersistence _persistence;
}