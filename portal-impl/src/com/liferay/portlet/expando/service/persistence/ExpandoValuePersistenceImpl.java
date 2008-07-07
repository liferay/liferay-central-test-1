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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoValuePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoValuePersistenceImpl extends BasePersistenceImpl
	implements ExpandoValuePersistence {
	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		return expandoValue;
	}

	public ExpandoValue remove(long valueId)
		throws NoSuchValueException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
					new Long(valueId));

			if (expandoValue == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoValue exists with the primary key " +
						valueId);
				}

				throw new NoSuchValueException(
					"No ExpandoValue exists with the primary key " + valueId);
			}

			return remove(expandoValue);
		}
		catch (NoSuchValueException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue remove(ExpandoValue expandoValue)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(expandoValue);
			}
		}

		expandoValue = removeImpl(expandoValue);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(expandoValue);
			}
		}

		return expandoValue;
	}

	protected ExpandoValue removeImpl(ExpandoValue expandoValue)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(expandoValue);

			session.flush();

			return expandoValue;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoValue.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(ExpandoValue expandoValue, boolean merge)</code>.
	 */
	public ExpandoValue update(ExpandoValue expandoValue)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoValue expandoValue) method. Use update(ExpandoValue expandoValue, boolean merge) instead.");
		}

		return update(expandoValue, false);
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
	public ExpandoValue update(ExpandoValue expandoValue, boolean merge)
		throws SystemException {
		boolean isNew = expandoValue.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(expandoValue);
				}
				else {
					listener.onBeforeUpdate(expandoValue);
				}
			}
		}

		expandoValue = updateImpl(expandoValue, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(expandoValue);
				}
				else {
					listener.onAfterUpdate(expandoValue);
				}
			}
		}

		return expandoValue;
	}

	public ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(expandoValue);
			}
			else {
				if (expandoValue.isNew()) {
					session.save(expandoValue);
				}
			}

			session.flush();

			expandoValue.setNew(false);

			return expandoValue;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(ExpandoValue.class.getName());
		}
	}

	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByPrimaryKey(valueId);

		if (expandoValue == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoValue exists with the primary key " +
					valueId);
			}

			throw new NoSuchValueException(
				"No ExpandoValue exists with the primary key " + valueId);
		}

		return expandoValue;
	}

	public ExpandoValue fetchByPrimaryKey(long valueId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (ExpandoValue)session.get(ExpandoValueImpl.class,
				new Long(valueId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByTableId(long tableId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoValue> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByTableId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByTableId(tableId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoValue> list = findByTableId(tableId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByTableId_PrevAndNext(long valueId, long tableId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByColumnId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(columnId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end)
		throws SystemException {
		return findByColumnId(columnId, start, end, null);
	}

	public List<ExpandoValue> findByColumnId(long columnId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByColumnId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(columnId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByColumnId_First(long columnId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByColumnId(columnId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByColumnId_Last(long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByColumnId(columnId);

		List<ExpandoValue> list = findByColumnId(columnId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByColumnId(columnId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("columnId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(columnId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByRowId(long rowId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByRowId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("rowId_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end)
		throws SystemException {
		return findByRowId(rowId, start, end, null);
	}

	public List<ExpandoValue> findByRowId(long rowId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByRowId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("rowId_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByRowId_First(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByRowId(rowId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByRowId_Last(long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByRowId(rowId);

		List<ExpandoValue> list = findByRowId(rowId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByRowId_PrevAndNext(long valueId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByRowId(rowId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("rowId_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByT_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end) throws SystemException {
		return findByT_R(tableId, rowId, start, end, null);
	}

	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByT_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(rowId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByT_R_First(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_R(tableId, rowId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_R_Last(long tableId, long rowId,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByT_R(tableId, rowId);

		List<ExpandoValue> list = findByT_R(tableId, rowId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_R_PrevAndNext(long valueId, long tableId,
		long rowId, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_R(tableId, rowId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("tableId = ?");

			query.append(" AND ");

			query.append("rowId_ = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(rowId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_R(columnId, rowId);

		if (expandoValue == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByC_R(long columnId, long rowId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "fetchByC_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ExpandoValue> list = (List<ExpandoValue>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchValueException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<ExpandoValue> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByC_C_PrevAndNext(long valueId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoValue findByT_C_R(long tableId, long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_R(tableId, columnId, rowId);

		if (expandoValue == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("rowId=" + rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	public ExpandoValue fetchByT_C_R(long tableId, long columnId, long rowId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "fetchByT_C_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(rowId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				if (list.size() == 0) {
					return null;
				}
				else {
					return list.get(0);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<ExpandoValue> list = (List<ExpandoValue>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<ExpandoValue> findByT_C_C_C(long tableId, long columnId,
		long classNameId, long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByT_C_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classNameId),
				new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<ExpandoValue> list = q.list();

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public List<ExpandoValue> findByT_C_C_C(long tableId, long columnId,
		long classNameId, long classPK, int start, int end)
		throws SystemException {
		return findByT_C_C_C(tableId, columnId, classNameId, classPK, start,
			end, null);
	}

	public List<ExpandoValue> findByT_C_C_C(long tableId, long columnId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findByT_C_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classNameId),
				new Long(classPK),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public ExpandoValue findByT_C_C_C_First(long tableId, long columnId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		List<ExpandoValue> list = findByT_C_C_C(tableId, columnId, classNameId,
				classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue findByT_C_C_C_Last(long tableId, long columnId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		int count = countByT_C_C_C(tableId, columnId, classNameId, classPK);

		List<ExpandoValue> list = findByT_C_C_C(tableId, columnId, classNameId,
				classPK, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoValue exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("columnId=" + columnId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchValueException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoValue[] findByT_C_C_C_PrevAndNext(long valueId, long tableId,
		long columnId, long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		int count = countByT_C_C_C(tableId, columnId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

			query.append("tableId = ?");

			query.append(" AND ");

			query.append("columnId = ?");

			query.append(" AND ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("tableId ASC, ");
				query.append("rowId_ ASC, ");
				query.append("columnId ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			qPos.add(columnId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoValue);

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = (ExpandoValue)objArray[0];
			array[1] = (ExpandoValue)objArray[1];
			array[2] = (ExpandoValue)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoValue> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoValue> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoValue> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "findAll";
		String[] finderParams = new String[] {
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("tableId ASC, ");
					query.append("rowId_ ASC, ");
					query.append("columnId ASC");
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoValue> list = (List<ExpandoValue>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<ExpandoValue>)result;
		}
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoValue expandoValue : findByTableId(tableId)) {
			remove(expandoValue);
		}
	}

	public void removeByColumnId(long columnId) throws SystemException {
		for (ExpandoValue expandoValue : findByColumnId(columnId)) {
			remove(expandoValue);
		}
	}

	public void removeByRowId(long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByRowId(rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByT_R(long tableId, long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByT_R(tableId, rowId)) {
			remove(expandoValue);
		}
	}

	public void removeByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByC_R(columnId, rowId);

		remove(expandoValue);
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByC_C(classNameId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeByT_C_R(long tableId, long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByT_C_R(tableId, columnId, rowId);

		remove(expandoValue);
	}

	public void removeByT_C_C_C(long tableId, long columnId, long classNameId,
		long classPK) throws SystemException {
		for (ExpandoValue expandoValue : findByT_C_C_C(tableId, columnId,
				classNameId, classPK)) {
			remove(expandoValue);
		}
	}

	public void removeAll() throws SystemException {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByTableId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByColumnId(long columnId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByColumnId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(columnId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByRowId(long rowId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByRowId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("rowId_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByT_R(long tableId, long rowId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByT_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(tableId), new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_R(long columnId, long rowId) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByC_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(columnId), new Long(rowId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByC_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId), new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByT_C_R(long tableId, long columnId, long rowId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByT_C_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(rowId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("rowId_ = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(rowId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByT_C_C_C(long tableId, long columnId, long classNameId,
		long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countByT_C_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(tableId), new Long(columnId), new Long(classNameId),
				new Long(classPK)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoValue WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				query.append("columnId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classNameId);

				qPos.add(classPK);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = ExpandoValueModelImpl.CACHE_ENABLED;
		String finderClassName = ExpandoValue.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.expando.model.ExpandoValue");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public void registerListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.add(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	public void unregisterListener(ModelListener listener) {
		List<ModelListener> listeners = ListUtil.fromArray(_listeners);

		listeners.remove(listener);

		_listeners = listeners.toArray(new ModelListener[listeners.size()]);
	}

	protected void init() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoValue")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener> listeners = new ArrayList<ModelListener>();

				for (String listenerClassName : listenerClassNames) {
					listeners.add((ModelListener)Class.forName(
							listenerClassName).newInstance());
				}

				_listeners = listeners.toArray(new ModelListener[listeners.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(ExpandoValuePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}