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
 * <a href="AnnouncementsDeliveryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsDeliveryUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery create(
		long deliveryId) {
		return getPersistence().create(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery remove(
		long deliveryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().remove(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery remove(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(announcementsDelivery);
	}

	/**
	 * @deprecated Use <code>update(AnnouncementsDelivery announcementsDelivery, boolean merge)</code>.
	 */
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery update(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementsDelivery);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        announcementsDelivery the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when announcementsDelivery is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery update(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementsDelivery, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(announcementsDelivery, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByPrimaryKey(
		long deliveryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByPrimaryKey(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByPrimaryKey(
		long deliveryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(deliveryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findByUserId(
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery[] findByUserId_PrevAndNext(
		long deliveryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByUserId_PrevAndNext(deliveryId, userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery findByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		return getPersistence().findByU_T(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery fetchByU_T(
		long userId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_T(userId, type);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchDeliveryException {
		getPersistence().removeByU_T(userId, type);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByU_T(long userId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_T(userId, type);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementsDeliveryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AnnouncementsDeliveryPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementsDeliveryUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementsDeliveryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementsDeliveryUtil.class.getName();
	private static AnnouncementsDeliveryUtil _util;
	private AnnouncementsDeliveryPersistence _persistence;
}