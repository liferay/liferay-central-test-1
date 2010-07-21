/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoTable;

import java.util.List;

/**
 * The persistence utility for the expando table service.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this class.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTablePersistence
 * @see ExpandoTablePersistenceImpl
 * @generated
 */
public class ExpandoTableUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ExpandoTable expandoTable) {
		getPersistence().clearCache(expandoTable);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ExpandoTable> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ExpandoTable remove(ExpandoTable expandoTable)
		throws SystemException {
		return getPersistence().remove(expandoTable);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ExpandoTable update(ExpandoTable expandoTable, boolean merge)
		throws SystemException {
		return getPersistence().update(expandoTable, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ExpandoTable update(ExpandoTable expandoTable, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(expandoTable, merge, serviceContext);
	}

	/**
	* Caches the expando table in the entity cache if it is enabled.
	*
	* @param expandoTable the expando table to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		getPersistence().cacheResult(expandoTable);
	}

	/**
	* Caches the expando tables in the entity cache if it is enabled.
	*
	* @param expandoTables the expando tables to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> expandoTables) {
		getPersistence().cacheResult(expandoTables);
	}

	/**
	* Creates a new expando table with the primary key.
	*
	* @param tableId the primary key for the new expando table
	* @return the new expando table
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable create(
		long tableId) {
		return getPersistence().create(tableId);
	}

	/**
	* Removes the expando table with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tableId the primary key of the expando table to remove
	* @return the expando table that was removed
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable remove(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().remove(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(expandoTable, merge);
	}

	/**
	* Finds the expando table with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchTableException} if it could not be found.
	*
	* @param tableId the primary key of the expando table to find
	* @return the expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByPrimaryKey(tableId);
	}

	/**
	* Finds the expando table with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tableId the primary key of the expando table to find
	* @return the expando table, or <code>null</code> if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tableId);
	}

	/**
	* Finds all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @return the matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	/**
	* Finds a range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param start the lower bound of the range of expando tables to return
	* @param end the upper bound of the range of expando tables to return (not inclusive)
	* @return the range of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param start the lower bound of the range of expando tables to return
	* @param end the upper bound of the range of expando tables to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_First(companyId, classNameId, orderByComparator);
	}

	/**
	* Finds the last expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_Last(companyId, classNameId, orderByComparator);
	}

	/**
	* Finds the expando tables before and after the current expando table in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param tableId the primary key of the current expando table
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a expando table with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable[] findByC_C_PrevAndNext(
		long tableId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_PrevAndNext(tableId, companyId, classNameId,
			orderByComparator);
	}

	/**
	* Finds the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchTableException} if it could not be found.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param name the name to search with
	* @return the matching expando table
	* @throws com.liferay.portlet.expando.NoSuchTableException if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByC_C_N(companyId, classNameId, name);
	}

	/**
	* Finds the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param name the name to search with
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_N(companyId, classNameId, name);
	}

	/**
	* Finds the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param name the name to search with
	* @return the matching expando table, or <code>null</code> if a matching expando table could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_N(companyId, classNameId, name, retrieveFromCache);
	}

	/**
	* Finds all the expando tables.
	*
	* @return the expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando tables to return
	* @param end the upper bound of the range of expando tables to return (not inclusive)
	* @return the range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the expando tables.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of expando tables to return
	* @param end the upper bound of the range of expando tables to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the expando tables where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	/**
	* Removes the expando table where companyId = &#63; and classNameId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		getPersistence().removeByC_C_N(companyId, classNameId, name);
	}

	/**
	* Removes all the expando tables from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the expando tables where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(companyId, classNameId);
	}

	/**
	* Counts all the expando tables where companyId = &#63; and classNameId = &#63; and name = &#63;.
	*
	* @param companyId the company id to search with
	* @param classNameId the class name id to search with
	* @param name the name to search with
	* @return the number of matching expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_N(companyId, classNameId, name);
	}

	/**
	* Counts all the expando tables.
	*
	* @return the number of expando tables
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoTablePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ExpandoTablePersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoTablePersistence _persistence;
}