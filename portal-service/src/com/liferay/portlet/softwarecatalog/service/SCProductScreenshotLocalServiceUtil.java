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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCProductScreenshotLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalService
 * @see com.liferay.portlet.softwarecatalog.service.SCProductScreenshotLocalServiceFactory
 *
 */
public class SCProductScreenshotLocalServiceUtil {
	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot addSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.addSCProductScreenshot(scProductScreenshot);
	}

	public static void deleteSCProductScreenshot(long productScreenshotId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		scProductScreenshotLocalService.deleteSCProductScreenshot(productScreenshotId);
	}

	public static void deleteSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		scProductScreenshotLocalService.deleteSCProductScreenshot(scProductScreenshot);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getSCProductScreenshot(
		long productScreenshotId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.getSCProductScreenshot(productScreenshotId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot updateSCProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot)
		throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.updateSCProductScreenshot(scProductScreenshot);
	}

	public static void init() {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		scProductScreenshotLocalService.init();
	}

	public static void deleteProductScreenshot(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot productScreenshot)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		scProductScreenshotLocalService.deleteProductScreenshot(productScreenshot);
	}

	public static void deleteProductScreenshots(long productEntryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		scProductScreenshotLocalService.deleteProductScreenshots(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshot(
		long productEntryId, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.getProductScreenshot(productEntryId,
			priority);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductScreenshot> getProductScreenshots(
		long productEntryId) throws com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.getProductScreenshots(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByFullImageId(
		long fullImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.getProductScreenshotByFullImageId(fullImageId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductScreenshot getProductScreenshotByThumbnailId(
		long thumbnailId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SCProductScreenshotLocalService scProductScreenshotLocalService = SCProductScreenshotLocalServiceFactory.getService();

		return scProductScreenshotLocalService.getProductScreenshotByThumbnailId(thumbnailId);
	}
}