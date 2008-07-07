/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

/**
 * <a href="AnnouncementsFlagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsFlagUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag create(
		long flagId) {
		return getPersistence().create(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag remove(
		long flagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().remove(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag remove(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(announcementsFlag);
	}

	/**
	 * @deprecated Use <code>update(AnnouncementsFlag announcementsFlag, boolean merge)</code>.
	 */
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag update(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementsFlag);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        announcementsFlag the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when announcementsFlag is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.announcements.model.AnnouncementsFlag update(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(announcementsFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByPrimaryKey(
		long flagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByPrimaryKey(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByPrimaryKey(
		long flagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId, start, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_First(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_First(entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_Last(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_Last(entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag[] findByEntryId_PrevAndNext(
		long flagId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByEntryId_PrevAndNext(flagId, entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag findByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		return getPersistence().findByU_E_V(userId, entryId, value);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_E_V(userId, entryId, value);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	public static void removeByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException {
		getPersistence().removeByU_E_V(userId, entryId, value);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByEntryId(long entryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	public static int countByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_E_V(userId, entryId, value);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void registerListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().registerListener(listener);
	}

	public static void unregisterListener(
		com.liferay.portal.model.ModelListener listener) {
		getPersistence().unregisterListener(listener);
	}

	public static AnnouncementsFlagPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AnnouncementsFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementsFlagUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementsFlagUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementsFlagUtil.class.getName();
	private static AnnouncementsFlagUtil _util;
	private AnnouncementsFlagPersistence _persistence;
}