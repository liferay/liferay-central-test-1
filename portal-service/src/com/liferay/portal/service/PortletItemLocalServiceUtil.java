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
 * <a href="PortletItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.PortletItemLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.PortletItemLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.PortletItemLocalService
 * @see com.liferay.portal.service.PortletItemLocalServiceFactory
 *
 */
public class PortletItemLocalServiceUtil {
	public static com.liferay.portal.model.PortletItem addPortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.addPortletItem(portletItem);
	}

	public static void deletePortletItem(long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.deletePortletItem(portletItemId);
	}

	public static void deletePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.deletePortletItem(portletItem);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItem(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.updatePortletItem(portletItem);
	}

	public static void init() {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		portletItemLocalService.init();
	}

	public static com.liferay.portal.model.PortletItem addPortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.addPortletItem(userId, groupId, name,
			portletId, className);
	}

	public static com.liferay.portal.model.PortletItem getPortletItem(
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItem(groupId, name, portletId,
			className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItems(groupId, className);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.getPortletItems(groupId, portletId,
			className);
	}

	public static com.liferay.portal.model.PortletItem updatePortletItem(
		long userId, long groupId, java.lang.String name,
		java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		PortletItemLocalService portletItemLocalService = PortletItemLocalServiceFactory.getService();

		return portletItemLocalService.updatePortletItem(userId, groupId, name,
			portletId, className);
	}
}