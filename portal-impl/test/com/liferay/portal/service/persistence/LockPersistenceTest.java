/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="LockPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LockPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (LockPersistence)PortalBeanLocatorUtil.locate(LockPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Lock lock = _persistence.create(pk);

		assertNotNull(lock);

		assertEquals(lock.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Lock newLock = addLock();

		_persistence.remove(newLock);

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		assertNull(existingLock);
	}

	public void testUpdateNew() throws Exception {
		addLock();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Lock newLock = _persistence.create(pk);

		newLock.setUuid(randomString());
		newLock.setCompanyId(nextLong());
		newLock.setUserId(nextLong());
		newLock.setUserName(randomString());
		newLock.setCreateDate(nextDate());
		newLock.setClassName(randomString());
		newLock.setKey(randomString());
		newLock.setOwner(randomString());
		newLock.setInheritable(randomBoolean());
		newLock.setExpirationDate(nextDate());

		_persistence.update(newLock, false);

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock.getUuid(), newLock.getUuid());
		assertEquals(existingLock.getLockId(), newLock.getLockId());
		assertEquals(existingLock.getCompanyId(), newLock.getCompanyId());
		assertEquals(existingLock.getUserId(), newLock.getUserId());
		assertEquals(existingLock.getUserName(), newLock.getUserName());
		assertEquals(Time.getShortTimestamp(existingLock.getCreateDate()),
			Time.getShortTimestamp(newLock.getCreateDate()));
		assertEquals(existingLock.getClassName(), newLock.getClassName());
		assertEquals(existingLock.getKey(), newLock.getKey());
		assertEquals(existingLock.getOwner(), newLock.getOwner());
		assertEquals(existingLock.getInheritable(), newLock.getInheritable());
		assertEquals(Time.getShortTimestamp(existingLock.getExpirationDate()),
			Time.getShortTimestamp(newLock.getExpirationDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.findByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock, newLock);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchLockException");
		}
		catch (NoSuchLockException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Lock newLock = addLock();

		Lock existingLock = _persistence.fetchByPrimaryKey(newLock.getPrimaryKey());

		assertEquals(existingLock, newLock);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Lock missingLock = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingLock);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Lock newLock = addLock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId",
				newLock.getLockId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Lock existingLock = (Lock)result.get(0);

		assertEquals(existingLock, newLock);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Lock.class,
				Lock.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("lockId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Lock addLock() throws Exception {
		long pk = nextLong();

		Lock lock = _persistence.create(pk);

		lock.setUuid(randomString());
		lock.setCompanyId(nextLong());
		lock.setUserId(nextLong());
		lock.setUserName(randomString());
		lock.setCreateDate(nextDate());
		lock.setClassName(randomString());
		lock.setKey(randomString());
		lock.setOwner(randomString());
		lock.setInheritable(randomBoolean());
		lock.setExpirationDate(nextDate());

		_persistence.update(lock, false);

		return lock;
	}

	private LockPersistence _persistence;
}