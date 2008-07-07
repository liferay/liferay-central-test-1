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
 * <a href="ServiceComponentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ServiceComponentUtil {
	public static com.liferay.portal.model.ServiceComponent create(
		long serviceComponentId) {
		return getPersistence().create(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent remove(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent remove(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(serviceComponent);
	}

	/**
	 * @deprecated Use <code>update(ServiceComponent serviceComponent, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.ServiceComponent update(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(serviceComponent);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        serviceComponent the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when serviceComponent is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.ServiceComponent update(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(serviceComponent, merge);
	}

	public static com.liferay.portal.model.ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(serviceComponent, merge);
	}

	public static com.liferay.portal.model.ServiceComponent findByPrimaryKey(
		long serviceComponentId)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(serviceComponentId);
	}

	public static com.liferay.portal.model.ServiceComponent fetchByPrimaryKey(
		long serviceComponentId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(serviceComponentId);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByBuildNamespace(buildNamespace);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByBuildNamespace(buildNamespace, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findByBuildNamespace(
		java.lang.String buildNamespace, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByBuildNamespace(buildNamespace, start, end, obc);
	}

	public static com.liferay.portal.model.ServiceComponent findByBuildNamespace_First(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence().findByBuildNamespace_First(buildNamespace, obc);
	}

	public static com.liferay.portal.model.ServiceComponent findByBuildNamespace_Last(
		java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence().findByBuildNamespace_Last(buildNamespace, obc);
	}

	public static com.liferay.portal.model.ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, java.lang.String buildNamespace,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByBuildNamespace_PrevAndNext(serviceComponentId,
			buildNamespace, obc);
	}

	public static com.liferay.portal.model.ServiceComponent findByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		return getPersistence().findByBNS_BNU(buildNamespace, buildNumber);
	}

	public static com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByBNS_BNU(buildNamespace, buildNumber);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByBuildNamespace(buildNamespace);
	}

	public static void removeByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber)
		throws com.liferay.portal.NoSuchServiceComponentException,
			com.liferay.portal.SystemException {
		getPersistence().removeByBNS_BNU(buildNamespace, buildNumber);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByBuildNamespace(java.lang.String buildNamespace)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByBuildNamespace(buildNamespace);
	}

	public static int countByBNS_BNU(java.lang.String buildNamespace,
		long buildNumber) throws com.liferay.portal.SystemException {
		return getPersistence().countByBNS_BNU(buildNamespace, buildNumber);
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

	public static ServiceComponentPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(ServiceComponentPersistence persistence) {
		_persistence = persistence;
	}

	private static ServiceComponentUtil _getUtil() {
		if (_util == null) {
			_util = (ServiceComponentUtil)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = ServiceComponentUtil.class.getName();
	private static ServiceComponentUtil _util;
	private ServiceComponentPersistence _persistence;
}