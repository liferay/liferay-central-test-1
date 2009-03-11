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

package com.liferay.portal.service;


/**
 * <a href="ClassNameLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ClassNameLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ClassNameLocalService
 *
 */
public class ClassNameLocalServiceUtil {
	public static com.liferay.portal.model.ClassName addClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException {
		return getService().addClassName(className);
	}

	public static com.liferay.portal.model.ClassName createClassName(
		long classNameId) {
		return getService().createClassName(classNameId);
	}

	public static void deleteClassName(long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteClassName(classNameId);
	}

	public static void deleteClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException {
		getService().deleteClassName(className);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.ClassName getClassName(
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getClassName(classNameId);
	}

	public static java.util.List<com.liferay.portal.model.ClassName> getClassNames(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getClassNames(start, end);
	}

	public static int getClassNamesCount()
		throws com.liferay.portal.SystemException {
		return getService().getClassNamesCount();
	}

	public static com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.SystemException {
		return getService().updateClassName(className);
	}

	public static com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateClassName(className, merge);
	}

	public static com.liferay.portal.model.ClassName addClassName(
		java.lang.String value) throws com.liferay.portal.SystemException {
		return getService().addClassName(value);
	}

	public static void checkClassNames()
		throws com.liferay.portal.SystemException {
		getService().checkClassNames();
	}

	public static com.liferay.portal.model.ClassName getClassName(
		java.lang.String value) throws com.liferay.portal.SystemException {
		return getService().getClassName(value);
	}

	public static long getClassNameId(java.lang.Class<?> classObj) {
		return getService().getClassNameId(classObj);
	}

	public static long getClassNameId(java.lang.String value) {
		return getService().getClassNameId(value);
	}

	public static ClassNameLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("ClassNameLocalService is not set");
		}

		return _service;
	}

	public void setService(ClassNameLocalService service) {
		_service = service;
	}

	private static ClassNameLocalService _service;
}