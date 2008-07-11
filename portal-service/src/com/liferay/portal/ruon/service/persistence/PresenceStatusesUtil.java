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

package com.liferay.portal.ruon.service.persistence;

/**
 * <a href="PresenceStatusesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PresenceStatusesUtil {
	public static com.liferay.portal.ruon.model.PresenceStatuses create(
		long presenceStatusId) {
		return getPersistence().create(presenceStatusId);
	}

	public static com.liferay.portal.ruon.model.PresenceStatuses remove(
		long presenceStatusId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.ruon.NoSuchPresenceStatusesException {
		return getPersistence().remove(presenceStatusId);
	}

	public static com.liferay.portal.ruon.model.PresenceStatuses remove(
		com.liferay.portal.ruon.model.PresenceStatuses presenceStatuses)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(presenceStatuses);
	}

	/**
	 * @deprecated Use <code>update(PresenceStatuses presenceStatuses, boolean merge)</code>.
	 */
	public static com.liferay.portal.ruon.model.PresenceStatuses update(
		com.liferay.portal.ruon.model.PresenceStatuses presenceStatuses)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(presenceStatuses);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        presenceStatuses the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when presenceStatuses is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.ruon.model.PresenceStatuses update(
		com.liferay.portal.ruon.model.PresenceStatuses presenceStatuses,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(presenceStatuses, merge);
	}

	public static com.liferay.portal.ruon.model.PresenceStatuses updateImpl(
		com.liferay.portal.ruon.model.PresenceStatuses presenceStatuses,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(presenceStatuses, merge);
	}

	public static com.liferay.portal.ruon.model.PresenceStatuses findByPrimaryKey(
		long presenceStatusId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.ruon.NoSuchPresenceStatusesException {
		return getPersistence().findByPrimaryKey(presenceStatusId);
	}

	public static com.liferay.portal.ruon.model.PresenceStatuses fetchByPrimaryKey(
		long presenceStatusId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(presenceStatusId);
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

	public static java.util.List<com.liferay.portal.ruon.model.PresenceStatuses> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.ruon.model.PresenceStatuses> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.ruon.model.PresenceStatuses> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
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

	public static PresenceStatusesPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PresenceStatusesPersistence persistence) {
		_persistence = persistence;
	}

	private static PresenceStatusesUtil _getUtil() {
		if (_util == null) {
			_util = (PresenceStatusesUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = PresenceStatusesUtil.class.getName();
	private static PresenceStatusesUtil _util;
	private PresenceStatusesPersistence _persistence;
}