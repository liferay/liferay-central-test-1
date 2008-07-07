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

package com.liferay.portal.service.persistence;

/**
 * <a href="OrgLaborUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrgLaborUtil {
	public static com.liferay.portal.model.OrgLabor create(long orgLaborId) {
		return getPersistence().create(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor remove(long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor remove(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(orgLabor);
	}

	/**
	 * @deprecated Use <code>update(OrgLabor orgLabor, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(orgLabor);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        orgLabor the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when orgLabor is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.OrgLabor update(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(orgLabor, merge);
	}

	public static com.liferay.portal.model.OrgLabor updateImpl(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(orgLabor, merge);
	}

	public static com.liferay.portal.model.OrgLabor findByPrimaryKey(
		long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor fetchByPrimaryKey(
		long orgLaborId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orgLaborId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId) throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByOrganizationId(organizationId, start, end, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_First(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_First(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_Last(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_Last(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor[] findByOrganizationId_PrevAndNext(
		long orgLaborId, long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByOrganizationId_PrevAndNext(orgLaborId,
			organizationId, obc);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByOrganizationId(long organizationId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOrganizationId(organizationId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByOrganizationId(long organizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOrganizationId(organizationId);
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

	public static OrgLaborPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(OrgLaborPersistence persistence) {
		_persistence = persistence;
	}

	private static OrgLaborUtil _getUtil() {
		if (_util == null) {
			_util = (OrgLaborUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = OrgLaborUtil.class.getName();
	private static OrgLaborUtil _util;
	private OrgLaborPersistence _persistence;
}