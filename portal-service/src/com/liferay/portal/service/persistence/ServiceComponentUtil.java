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
 * <a href="ServiceComponentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    ServiceComponentPersistence
 * @see    ServiceComponentPersistenceImpl
 */
public class ServiceComponentUtil {
	public static void cacheResult(
		com.liferay.portal.model.ServiceComponent serviceComponent) {
		getPersistence().cacheResult(serviceComponent);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ServiceComponent> serviceComponents) {
		getPersistence().cacheResult(serviceComponents);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

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
	 * @deprecated Use {@link #update(ServiceComponent, boolean merge)}.
	 */
	public static com.liferay.portal.model.ServiceComponent update(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(serviceComponent);
	}

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

	public static com.liferay.portal.model.ServiceComponent fetchByBNS_BNU(
		java.lang.String buildNamespace, long buildNumber,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByBNS_BNU(buildNamespace, buildNumber,
			retrieveFromCache);
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

	public static ServiceComponentPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ServiceComponentPersistence persistence) {
		_persistence = persistence;
	}

	private static ServiceComponentPersistence _persistence;
}