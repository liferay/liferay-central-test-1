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
 * <a href="WebDAVPropsLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.WebDAVPropsLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.WebDAVPropsLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.WebDAVPropsLocalService
 * @see com.liferay.portal.service.WebDAVPropsLocalServiceFactory
 *
 */
public class WebDAVPropsLocalServiceUtil {
	public static com.liferay.portal.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		return webDAVPropsLocalService.addWebDAVProps(webDAVProps);
	}

	public static void deleteWebDAVProps(long webDavPropsId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		webDAVPropsLocalService.deleteWebDAVProps(webDavPropsId);
	}

	public static void deleteWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		webDAVPropsLocalService.deleteWebDAVProps(webDAVProps);
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		return webDAVPropsLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		return webDAVPropsLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		return webDAVPropsLocalService.updateWebDAVProps(webDAVProps);
	}

	public static void deleteWebDAVProps(java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		webDAVPropsLocalService.deleteWebDAVProps(className, classPK);
	}

	public static com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		return webDAVPropsLocalService.getWebDAVProps(companyId, className,
			classPK);
	}

	public static void storeWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDavProps)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		WebDAVPropsLocalService webDAVPropsLocalService = WebDAVPropsLocalServiceFactory.getService();

		webDAVPropsLocalService.storeWebDAVProps(webDavProps);
	}
}