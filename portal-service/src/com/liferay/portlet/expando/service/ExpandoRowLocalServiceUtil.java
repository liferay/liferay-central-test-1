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

package com.liferay.portlet.expando.service;


/**
 * <a href="ExpandoRowLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoRowLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalService
 * @see com.liferay.portlet.expando.service.ExpandoRowLocalServiceFactory
 *
 */
public class ExpandoRowLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoRow addExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.addExpandoRow(expandoRow);
	}

	public static void deleteExpandoRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteExpandoRow(rowId);
	}

	public static void deleteExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteExpandoRow(expandoRow);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getExpandoRow(
		long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getExpandoRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow updateExpandoRow(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.updateExpandoRow(expandoRow);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow addRow(
		long tableId, long classPK) throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.addRow(tableId, classPK);
	}

	public static void deleteRow(long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteRow(rowId);
	}

	public static void deleteRow(long tableId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteRow(tableId, classPK);
	}

	public static void deleteRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteRow(className, tableName, classPK);
	}

	public static void deleteRow(long classNameId, java.lang.String tableName,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		expandoRowLocalService.deleteRow(classNameId, tableName, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getDefaultTableRows(className, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getDefaultTableRows(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getDefaultTableRows(classNameId, start,
			end);
	}

	public static int getDefaultTableRowsCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getDefaultTableRowsCount(className);
	}

	public static int getDefaultTableRowsCount(long classNameId)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getDefaultTableRowsCount(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long rowId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRow(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long tableId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRow(tableId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		java.lang.String className, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRow(className, tableName, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow getRow(
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRow(classNameId, tableName, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRows(tableId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		java.lang.String className, java.lang.String tableName, int start,
		int end) throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRows(className, tableName, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> getRows(
		long classNameId, java.lang.String tableName, int start, int end)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRows(classNameId, tableName, start, end);
	}

	public static int getRowsCount(long tableId)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRowsCount(tableId);
	}

	public static int getRowsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRowsCount(className, tableName);
	}

	public static int getRowsCount(long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		ExpandoRowLocalService expandoRowLocalService = ExpandoRowLocalServiceFactory.getService();

		return expandoRowLocalService.getRowsCount(classNameId, tableName);
	}
}