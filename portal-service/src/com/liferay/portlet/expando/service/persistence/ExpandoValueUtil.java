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

package com.liferay.portlet.expando.service.persistence;

/**
 * <a href="ExpandoValueUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValueUtil {
	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue) {
		getPersistence().cacheResult(expandoValue);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> expandoValues) {
		getPersistence().cacheResult(expandoValues);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.expando.model.ExpandoValue create(
		long valueId) {
		return getPersistence().create(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue remove(
		long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().remove(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue remove(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(expandoValue);
	}

	/**
	 * @deprecated Use <code>update(ExpandoValue expandoValue, boolean merge)</code>.
	 */
	public static com.liferay.portlet.expando.model.ExpandoValue update(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoValue);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoValue the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoValue is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.expando.model.ExpandoValue update(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(expandoValue, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoValue, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByPrimaryKey(
		long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByPrimaryKey(valueId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByPrimaryKey(
		long valueId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(valueId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByTableId_First(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByTableId_Last(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByTableId_PrevAndNext(
		long valueId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByTableId_PrevAndNext(valueId, tableId, obc);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByColumnId(
		long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByColumnId(columnId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByColumnId_First(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByColumnId_First(columnId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByColumnId_Last(
		long columnId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByColumnId_Last(columnId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByColumnId_PrevAndNext(
		long valueId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByColumnId_PrevAndNext(valueId, columnId, obc);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId) throws com.liferay.portal.SystemException {
		return getPersistence().findByRowId(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRowId(rowId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByRowId(
		long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRowId(rowId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByRowId_First(
		long rowId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByRowId_First(rowId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByRowId_Last(
		long rowId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByRowId_Last(rowId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByRowId_PrevAndNext(
		long valueId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByRowId_PrevAndNext(valueId, rowId, obc);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().findByT_C(tableId, columnId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_C(tableId, columnId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C(
		long tableId, long columnId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_C(tableId, columnId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_First(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_First(tableId, columnId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_Last(
		long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_Last(tableId, columnId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_PrevAndNext(
		long valueId, long tableId, long columnId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_PrevAndNext(valueId, tableId, columnId, obc);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().findByT_CPK(tableId, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_CPK(tableId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_CPK(
		long tableId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_CPK(tableId, classPK, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_First(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_CPK_First(tableId, classPK, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_CPK_Last(
		long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_CPK_Last(tableId, classPK, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_CPK_PrevAndNext(
		long valueId, long tableId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_CPK_PrevAndNext(valueId, tableId, classPK, obc);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId) throws com.liferay.portal.SystemException {
		return getPersistence().findByT_R(tableId, rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_R(tableId, rowId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_R(
		long tableId, long rowId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_R(tableId, rowId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_R_First(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_R_First(tableId, rowId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_R_Last(
		long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_R_Last(tableId, rowId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_R_PrevAndNext(
		long valueId, long tableId, long rowId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_R_PrevAndNext(valueId, tableId, rowId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByC_R(
		long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByC_R(columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_R(columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByC_R(
		long columnId, long rowId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_R(columnId, rowId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByC_C_PrevAndNext(
		long valueId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByC_C_PrevAndNext(valueId, classNameId, classPK, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_R(
		long tableId, long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_R(tableId, columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_R(
		long tableId, long columnId, long rowId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_C_R(tableId, columnId, rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_R(
		long tableId, long columnId, long rowId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByT_C_R(tableId, columnId, rowId, retrieveFromCache);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_C(tableId, columnId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_C_C(tableId, columnId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue fetchByT_C_C(
		long tableId, long columnId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByT_C_C(tableId, columnId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_C_D(tableId, columnId, data);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_C_D(tableId, columnId, data, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findByT_C_D(
		long tableId, long columnId, java.lang.String data, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByT_C_D(tableId, columnId, data, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_First(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_D_First(tableId, columnId, data, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue findByT_C_D_Last(
		long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence().findByT_C_D_Last(tableId, columnId, data, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoValue[] findByT_C_D_PrevAndNext(
		long valueId, long tableId, long columnId, java.lang.String data,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		return getPersistence()
				   .findByT_C_D_PrevAndNext(valueId, tableId, columnId, data,
			obc);
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

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	public static void removeByColumnId(long columnId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByColumnId(columnId);
	}

	public static void removeByRowId(long rowId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByRowId(rowId);
	}

	public static void removeByT_C(long tableId, long columnId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_C(tableId, columnId);
	}

	public static void removeByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_CPK(tableId, classPK);
	}

	public static void removeByT_R(long tableId, long rowId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_R(tableId, rowId);
	}

	public static void removeByC_R(long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		getPersistence().removeByC_R(columnId, rowId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByT_C_R(long tableId, long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		getPersistence().removeByT_C_R(tableId, columnId, rowId);
	}

	public static void removeByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchValueException {
		getPersistence().removeByT_C_C(tableId, columnId, classPK);
	}

	public static void removeByT_C_D(long tableId, long columnId,
		java.lang.String data) throws com.liferay.portal.SystemException {
		getPersistence().removeByT_C_D(tableId, columnId, data);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	public static int countByColumnId(long columnId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByColumnId(columnId);
	}

	public static int countByRowId(long rowId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByRowId(rowId);
	}

	public static int countByT_C(long tableId, long columnId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_C(tableId, columnId);
	}

	public static int countByT_CPK(long tableId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_CPK(tableId, classPK);
	}

	public static int countByT_R(long tableId, long rowId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_R(tableId, rowId);
	}

	public static int countByC_R(long columnId, long rowId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_R(columnId, rowId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByT_C_R(long tableId, long columnId, long rowId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_C_R(tableId, columnId, rowId);
	}

	public static int countByT_C_C(long tableId, long columnId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_C_C(tableId, columnId, classPK);
	}

	public static int countByT_C_D(long tableId, long columnId,
		java.lang.String data) throws com.liferay.portal.SystemException {
		return getPersistence().countByT_C_D(tableId, columnId, data);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoValuePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ExpandoValuePersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoValuePersistence _persistence;
}