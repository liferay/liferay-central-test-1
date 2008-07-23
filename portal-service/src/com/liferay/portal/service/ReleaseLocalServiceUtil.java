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

package com.liferay.portal.service;


/**
 * <a href="ReleaseLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ReleaseLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ReleaseLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ReleaseLocalService
 * @see com.liferay.portal.service.ReleaseLocalServiceFactory
 *
 */
public class ReleaseLocalServiceUtil {
	public static com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.addRelease(release);
	}

	public static void deleteRelease(long releaseId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		releaseLocalService.deleteRelease(releaseId);
	}

	public static void deleteRelease(com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		releaseLocalService.deleteRelease(release);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.getRelease(releaseId);
	}

	public static java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end) throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.getReleases(start, end);
	}

	public static int getReleasesCount()
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.getReleasesCount();
	}

	public static com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.updateRelease(release);
	}

	public static int getBuildNumberOrCreate()
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.getBuildNumberOrCreate();
	}

	public static com.liferay.portal.model.Release getRelease()
		throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.getRelease();
	}

	public static com.liferay.portal.model.Release updateRelease(
		boolean verified) throws com.liferay.portal.SystemException {
		ReleaseLocalService releaseLocalService = ReleaseLocalServiceFactory.getService();

		return releaseLocalService.updateRelease(verified);
	}
}