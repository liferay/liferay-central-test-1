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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.asset.NoSuchTagStatsException;
import com.liferay.portlet.asset.model.AssetTagStats;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagStatsPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetTagStatsPersistence)PortalBeanLocatorUtil.locate(AssetTagStatsPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assertNotNull(assetTagStats);

		assertEquals(assetTagStats.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		_persistence.remove(newAssetTagStats);

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertNull(existingAssetTagStats);
	}

	public void testUpdateNew() throws Exception {
		addAssetTagStats();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetTagStats newAssetTagStats = _persistence.create(pk);

		newAssetTagStats.setTagId(nextLong());
		newAssetTagStats.setClassNameId(nextLong());
		newAssetTagStats.setAssetCount(nextInt());

		_persistence.update(newAssetTagStats, false);

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats.getTagStatsId(),
			newAssetTagStats.getTagStatsId());
		assertEquals(existingAssetTagStats.getTagId(),
			newAssetTagStats.getTagId());
		assertEquals(existingAssetTagStats.getClassNameId(),
			newAssetTagStats.getClassNameId());
		assertEquals(existingAssetTagStats.getAssetCount(),
			newAssetTagStats.getAssetCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.findByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTagStatsException");
		}
		catch (NoSuchTagStatsException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		AssetTagStats existingAssetTagStats = _persistence.fetchByPrimaryKey(newAssetTagStats.getPrimaryKey());

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetTagStats missingAssetTagStats = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetTagStats);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagStats newAssetTagStats = addAssetTagStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId",
				newAssetTagStats.getTagStatsId()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetTagStats existingAssetTagStats = result.get(0);

		assertEquals(existingAssetTagStats, newAssetTagStats);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
				AssetTagStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagStatsId", nextLong()));

		List<AssetTagStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AssetTagStats addAssetTagStats() throws Exception {
		long pk = nextLong();

		AssetTagStats assetTagStats = _persistence.create(pk);

		assetTagStats.setTagId(nextLong());
		assetTagStats.setClassNameId(nextLong());
		assetTagStats.setAssetCount(nextInt());

		_persistence.update(assetTagStats, false);

		return assetTagStats;
	}

	private AssetTagStatsPersistence _persistence;
}