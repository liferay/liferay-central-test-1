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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.BrowserTracker;

import java.util.List;

/**
 * <a href="BrowserTrackerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerPersistence
 * @see       BrowserTrackerPersistenceImpl
 * @generated
 */
public class BrowserTrackerUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static BrowserTracker remove(BrowserTracker browserTracker)
		throws SystemException {
		return getPersistence().remove(browserTracker);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static BrowserTracker update(BrowserTracker browserTracker,
		boolean merge) throws SystemException {
		return getPersistence().update(browserTracker, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.BrowserTracker browserTracker) {
		getPersistence().cacheResult(browserTracker);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.BrowserTracker> browserTrackers) {
		getPersistence().cacheResult(browserTrackers);
	}

	public static com.liferay.portal.model.BrowserTracker create(
		long browserTrackerId) {
		return getPersistence().create(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker remove(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(browserTracker, merge);
	}

	public static com.liferay.portal.model.BrowserTracker findByPrimaryKey(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByPrimaryKey(
		long browserTrackerId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker findByUserId(
		long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUserId(userId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUserId(userId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static BrowserTrackerPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(BrowserTrackerPersistence persistence) {
		_persistence = persistence;
	}

	private static BrowserTrackerPersistence _persistence;
}