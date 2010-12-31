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

import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LayoutSetPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutSetPrototypePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		assertNotNull(layoutSetPrototype);

		assertEquals(layoutSetPrototype.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		_persistence.remove(newLayoutSetPrototype);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertNull(existingLayoutSetPrototype);
	}

	public void testUpdateNew() throws Exception {
		addLayoutSetPrototype();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype newLayoutSetPrototype = _persistence.create(pk);

		newLayoutSetPrototype.setCompanyId(nextLong());
		newLayoutSetPrototype.setName(randomString());
		newLayoutSetPrototype.setDescription(randomString());
		newLayoutSetPrototype.setSettings(randomString());
		newLayoutSetPrototype.setActive(randomBoolean());

		_persistence.update(newLayoutSetPrototype, false);

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype.getLayoutSetPrototypeId(),
			newLayoutSetPrototype.getLayoutSetPrototypeId());
		assertEquals(existingLayoutSetPrototype.getCompanyId(),
			newLayoutSetPrototype.getCompanyId());
		assertEquals(existingLayoutSetPrototype.getName(),
			newLayoutSetPrototype.getName());
		assertEquals(existingLayoutSetPrototype.getDescription(),
			newLayoutSetPrototype.getDescription());
		assertEquals(existingLayoutSetPrototype.getSettings(),
			newLayoutSetPrototype.getSettings());
		assertEquals(existingLayoutSetPrototype.getActive(),
			newLayoutSetPrototype.getActive());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.findByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchLayoutSetPrototypeException");
		}
		catch (NoSuchLayoutSetPrototypeException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		LayoutSetPrototype existingLayoutSetPrototype = _persistence.fetchByPrimaryKey(newLayoutSetPrototype.getPrimaryKey());

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		LayoutSetPrototype missingLayoutSetPrototype = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLayoutSetPrototype);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		LayoutSetPrototype newLayoutSetPrototype = addLayoutSetPrototype();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				newLayoutSetPrototype.getLayoutSetPrototypeId()));

		List<LayoutSetPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		LayoutSetPrototype existingLayoutSetPrototype = result.get(0);

		assertEquals(existingLayoutSetPrototype, newLayoutSetPrototype);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(LayoutSetPrototype.class,
				LayoutSetPrototype.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("layoutSetPrototypeId",
				nextLong()));

		List<LayoutSetPrototype> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected LayoutSetPrototype addLayoutSetPrototype()
		throws Exception {
		long pk = nextLong();

		LayoutSetPrototype layoutSetPrototype = _persistence.create(pk);

		layoutSetPrototype.setCompanyId(nextLong());
		layoutSetPrototype.setName(randomString());
		layoutSetPrototype.setDescription(randomString());
		layoutSetPrototype.setSettings(randomString());
		layoutSetPrototype.setActive(randomBoolean());

		_persistence.update(layoutSetPrototype, false);

		return layoutSetPrototype;
	}

	private LayoutSetPrototypePersistence _persistence;
}