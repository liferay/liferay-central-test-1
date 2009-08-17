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


/**
 * <a href="LockUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LockPersistence
 * @see       LockPersistenceImpl
 * @generated
 */
public class LockUtil {
	public static void cacheResult(com.liferay.portal.model.Lock lock) {
		getPersistence().cacheResult(lock);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Lock> locks) {
		getPersistence().cacheResult(locks);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.Lock create(long lockId) {
		return getPersistence().create(lockId);
	}

	public static com.liferay.portal.model.Lock remove(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(lockId);
	}

	public static com.liferay.portal.model.Lock remove(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(lock);
	}

	/**
	 * @deprecated Use {@link #update(Lock, boolean merge)}.
	 */
	public static com.liferay.portal.model.Lock update(
		com.liferay.portal.model.Lock lock)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(lock);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  lock the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when lock is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public static com.liferay.portal.model.Lock update(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(lock, merge);
	}

	public static com.liferay.portal.model.Lock updateImpl(
		com.liferay.portal.model.Lock lock, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(lock, merge);
	}

	public static com.liferay.portal.model.Lock findByPrimaryKey(long lockId)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(lockId);
	}

	public static com.liferay.portal.model.Lock fetchByPrimaryKey(long lockId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(lockId);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portal.model.Lock findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portal.model.Lock findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portal.model.Lock[] findByUuid_PrevAndNext(
		long lockId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUuid_PrevAndNext(lockId, uuid, obc);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByExpirationDate(expirationDate);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByExpirationDate(expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByExpirationDate(expirationDate, start, end, obc);
	}

	public static com.liferay.portal.model.Lock findByExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByExpirationDate_First(expirationDate, obc);
	}

	public static com.liferay.portal.model.Lock findByExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByExpirationDate_Last(expirationDate, obc);
	}

	public static com.liferay.portal.model.Lock[] findByExpirationDate_PrevAndNext(
		long lockId, java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByExpirationDate_PrevAndNext(lockId, expirationDate, obc);
	}

	public static com.liferay.portal.model.Lock findByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_K(className, key);
	}

	public static com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_K(className, key);
	}

	public static com.liferay.portal.model.Lock fetchByC_K(
		java.lang.String className, java.lang.String key,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_K(className, key, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Lock> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Lock> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByExpirationDate(expirationDate);
	}

	public static void removeByC_K(java.lang.String className,
		java.lang.String key)
		throws com.liferay.portal.NoSuchLockException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_K(className, key);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByExpirationDate(expirationDate);
	}

	public static int countByC_K(java.lang.String className,
		java.lang.String key) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_K(className, key);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static LockPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(LockPersistence persistence) {
		_persistence = persistence;
	}

	private static LockPersistence _persistence;
}