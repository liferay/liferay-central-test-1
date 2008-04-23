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
 * <a href="ExpandoColumnLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.expando.service.ExpandoColumnLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.expando.service.ExpandoColumnLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoColumnLocalService
 * @see com.liferay.portlet.expando.service.ExpandoColumnLocalServiceFactory
 *
 */
public class ExpandoColumnLocalServiceUtil {
	public static com.liferay.portlet.expando.model.ExpandoColumn addExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.addExpandoColumn(expandoColumn);
	}

	public static void deleteExpandoColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteExpandoColumn(columnId);
	}

	public static void deleteExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteExpandoColumn(expandoColumn);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateExpandoColumn(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateExpandoColumn(expandoColumn);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		long tableId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public static void deleteColumn(long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public static void deleteColumn(long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumn(tableId, name);
	}

	public static void deleteColumn(java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumn(className, tableName, name);
	}

	public static void deleteColumn(long classNameId,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumn(classNameId, tableName, name);
	}

	public static void deleteColumns(long tableId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumns(tableId);
	}

	public static void deleteColumns(java.lang.String className,
		java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumns(className, tableName);
	}

	public static void deleteColumns(long classNameId,
		java.lang.String tableName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		expandoColumnLocalService.deleteColumns(classNameId, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long columnId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long tableId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(tableId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		java.lang.String className, java.lang.String tableName,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(className, tableName, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		long classNameId, java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumn(classNameId, tableName, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long tableId) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumns(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumns(className, tableName);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		long classNameId, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumns(classNameId, tableName);
	}

	public static int getColumnsCount(long tableId)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumnsCount(tableId);
	}

	public static int getColumnsCount(java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumnsCount(className, tableName);
	}

	public static int getColumnsCount(long classNameId,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getColumnsCount(classNameId, tableName);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		java.lang.String className, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumn(className, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		long classNameId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumn(classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		java.lang.String className) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumns(className);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		long classNameId) throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumns(classNameId);
	}

	public static int getDefaultTableColumnsCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumnsCount(className);
	}

	public static int getDefaultTableColumnsCount(long classNameId)
		throws com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.getDefaultTableColumnsCount(classNameId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		long columnId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ExpandoColumnLocalService expandoColumnLocalService = ExpandoColumnLocalServiceFactory.getService();

		return expandoColumnLocalService.updateColumn(columnId, name, type);
	}
}