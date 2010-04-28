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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.social.NoSuchEquityLogException;
import com.liferay.portlet.social.model.SocialEquityLog;

import java.util.List;

/**
 * <a href="SocialEquityLogPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialEquityLogPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialEquityLogPersistence)PortalBeanLocatorUtil.locate(SocialEquityLogPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialEquityLog socialEquityLog = _persistence.create(pk);

		assertNotNull(socialEquityLog);

		assertEquals(socialEquityLog.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		_persistence.remove(newSocialEquityLog);

		SocialEquityLog existingSocialEquityLog = _persistence.fetchByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertNull(existingSocialEquityLog);
	}

	public void testUpdateNew() throws Exception {
		addSocialEquityLog();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialEquityLog newSocialEquityLog = _persistence.create(pk);

		newSocialEquityLog.setGroupId(nextLong());
		newSocialEquityLog.setCompanyId(nextLong());
		newSocialEquityLog.setUserId(nextLong());
		newSocialEquityLog.setAssetEntryId(nextLong());
		newSocialEquityLog.setActionId(randomString());
		newSocialEquityLog.setActionDate(nextInt());
		newSocialEquityLog.setType(nextInt());
		newSocialEquityLog.setValue(nextInt());
		newSocialEquityLog.setValidity(nextInt());

		_persistence.update(newSocialEquityLog, false);

		SocialEquityLog existingSocialEquityLog = _persistence.findByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog.getEquityLogId(),
			newSocialEquityLog.getEquityLogId());
		assertEquals(existingSocialEquityLog.getGroupId(),
			newSocialEquityLog.getGroupId());
		assertEquals(existingSocialEquityLog.getCompanyId(),
			newSocialEquityLog.getCompanyId());
		assertEquals(existingSocialEquityLog.getUserId(),
			newSocialEquityLog.getUserId());
		assertEquals(existingSocialEquityLog.getAssetEntryId(),
			newSocialEquityLog.getAssetEntryId());
		assertEquals(existingSocialEquityLog.getActionId(),
			newSocialEquityLog.getActionId());
		assertEquals(existingSocialEquityLog.getActionDate(),
			newSocialEquityLog.getActionDate());
		assertEquals(existingSocialEquityLog.getType(),
			newSocialEquityLog.getType());
		assertEquals(existingSocialEquityLog.getValue(),
			newSocialEquityLog.getValue());
		assertEquals(existingSocialEquityLog.getValidity(),
			newSocialEquityLog.getValidity());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		SocialEquityLog existingSocialEquityLog = _persistence.findByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEquityLogException");
		}
		catch (NoSuchEquityLogException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		SocialEquityLog existingSocialEquityLog = _persistence.fetchByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialEquityLog missingSocialEquityLog = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialEquityLog);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityLogId",
				newSocialEquityLog.getEquityLogId()));

		List<SocialEquityLog> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialEquityLog existingSocialEquityLog = result.get(0);

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityLogId", nextLong()));

		List<SocialEquityLog> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SocialEquityLog addSocialEquityLog() throws Exception {
		long pk = nextLong();

		SocialEquityLog socialEquityLog = _persistence.create(pk);

		socialEquityLog.setGroupId(nextLong());
		socialEquityLog.setCompanyId(nextLong());
		socialEquityLog.setUserId(nextLong());
		socialEquityLog.setAssetEntryId(nextLong());
		socialEquityLog.setActionId(randomString());
		socialEquityLog.setActionDate(nextInt());
		socialEquityLog.setType(nextInt());
		socialEquityLog.setValue(nextInt());
		socialEquityLog.setValidity(nextInt());

		_persistence.update(socialEquityLog, false);

		return socialEquityLog;
	}

	private SocialEquityLogPersistence _persistence;
}