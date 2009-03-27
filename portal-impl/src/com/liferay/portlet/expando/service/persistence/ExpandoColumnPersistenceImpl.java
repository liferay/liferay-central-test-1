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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchColumnException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.impl.ExpandoColumnImpl;
import com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="ExpandoColumnPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnPersistenceImpl extends BasePersistenceImpl
	implements ExpandoColumnPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoColumn.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = ExpandoColumn.class.getName() +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_TABLEID = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTableId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TABLEID = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTableId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTableId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_N = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_N = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumnModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(ExpandoColumn expandoColumn) {
		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
			new Object[] {
				new Long(expandoColumn.getTableId()),
				
			expandoColumn.getName()
			}, expandoColumn);

		EntityCacheUtil.putResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumn.class, expandoColumn.getPrimaryKey(), expandoColumn);
	}

	public void cacheResult(List<ExpandoColumn> expandoColumns) {
		for (ExpandoColumn expandoColumn : expandoColumns) {
			if (EntityCacheUtil.getResult(
						ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoColumn.class, expandoColumn.getPrimaryKey(), this) == null) {
				cacheResult(expandoColumn);
			}
		}
	}

	public ExpandoColumn create(long columnId) {
		ExpandoColumn expandoColumn = new ExpandoColumnImpl();

		expandoColumn.setNew(true);
		expandoColumn.setPrimaryKey(columnId);

		return expandoColumn;
	}

	public ExpandoColumn remove(long columnId)
		throws NoSuchColumnException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoColumn expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
					new Long(columnId));

			if (expandoColumn == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No ExpandoColumn exists with the primary key " +
						columnId);
				}

				throw new NoSuchColumnException(
					"No ExpandoColumn exists with the primary key " + columnId);
			}

			return remove(expandoColumn);
		}
		catch (NoSuchColumnException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		for (ModelListener<ExpandoColumn> listener : listeners) {
			listener.onBeforeRemove(expandoColumn);
		}

		expandoColumn = removeImpl(expandoColumn);

		for (ModelListener<ExpandoColumn> listener : listeners) {
			listener.onAfterRemove(expandoColumn);
		}

		return expandoColumn;
	}

	protected ExpandoColumn removeImpl(ExpandoColumn expandoColumn)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ExpandoColumnImpl.class,
						expandoColumn.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(expandoColumn);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoColumnModelImpl expandoColumnModelImpl = (ExpandoColumnModelImpl)expandoColumn;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_N,
			new Object[] {
				new Long(expandoColumnModelImpl.getOriginalTableId()),
				
			expandoColumnModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumn.class, expandoColumn.getPrimaryKey());

		return expandoColumn;
	}

	/**
	 * @deprecated Use <code>update(ExpandoColumn expandoColumn, boolean merge)</code>.
	 */
	public ExpandoColumn update(ExpandoColumn expandoColumn)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(ExpandoColumn expandoColumn) method. Use update(ExpandoColumn expandoColumn, boolean merge) instead.");
		}

		return update(expandoColumn, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        expandoColumn the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when expandoColumn is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public ExpandoColumn update(ExpandoColumn expandoColumn, boolean merge)
		throws SystemException {
		boolean isNew = expandoColumn.isNew();

		for (ModelListener<ExpandoColumn> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(expandoColumn);
			}
			else {
				listener.onBeforeUpdate(expandoColumn);
			}
		}

		expandoColumn = updateImpl(expandoColumn, merge);

		for (ModelListener<ExpandoColumn> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(expandoColumn);
			}
			else {
				listener.onAfterUpdate(expandoColumn);
			}
		}

		return expandoColumn;
	}

	public ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws SystemException {
		boolean isNew = expandoColumn.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, expandoColumn, merge);

			expandoColumn.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ExpandoColumnModelImpl expandoColumnModelImpl = (ExpandoColumnModelImpl)expandoColumn;

		if (!isNew &&
				((expandoColumn.getTableId() != expandoColumnModelImpl.getOriginalTableId()) ||
				!expandoColumn.getName()
								  .equals(expandoColumnModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_N,
				new Object[] {
					new Long(expandoColumnModelImpl.getOriginalTableId()),
					
				expandoColumnModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((expandoColumn.getTableId() != expandoColumnModelImpl.getOriginalTableId()) ||
				!expandoColumn.getName()
								  .equals(expandoColumnModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
				new Object[] {
					new Long(expandoColumn.getTableId()),
					
				expandoColumn.getName()
				}, expandoColumn);
		}

		EntityCacheUtil.putResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoColumn.class, expandoColumn.getPrimaryKey(), expandoColumn);

		return expandoColumn;
	}

	public ExpandoColumn findByPrimaryKey(long columnId)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByPrimaryKey(columnId);

		if (expandoColumn == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No ExpandoColumn exists with the primary key " +
					columnId);
			}

			throw new NoSuchColumnException(
				"No ExpandoColumn exists with the primary key " + columnId);
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByPrimaryKey(long columnId)
		throws SystemException {
		ExpandoColumn result = (ExpandoColumn)EntityCacheUtil.getResult(ExpandoColumnModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoColumn.class, columnId, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				ExpandoColumn expandoColumn = (ExpandoColumn)session.get(ExpandoColumnImpl.class,
						new Long(columnId));

				cacheResult(expandoColumn);

				return expandoColumn;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (ExpandoColumn)result;
		}
	}

	public List<ExpandoColumn> findByTableId(long tableId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TABLEID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoColumn> list = q.list();

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TABLEID,
					finderArgs, list);

				cacheResult(list);

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
			return (List<ExpandoColumn>)result;
		}
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	public List<ExpandoColumn> findByTableId(long tableId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tableId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				List<ExpandoColumn> list = (List<ExpandoColumn>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TABLEID,
					finderArgs, list);

				cacheResult(list);

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
			return (List<ExpandoColumn>)result;
		}
	}

	public ExpandoColumn findByTableId_First(long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		List<ExpandoColumn> list = findByTableId(tableId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn findByTableId_Last(long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		int count = countByTableId(tableId);

		List<ExpandoColumn> list = findByTableId(tableId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchColumnException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public ExpandoColumn[] findByTableId_PrevAndNext(long columnId,
		long tableId, OrderByComparator obc)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByPrimaryKey(columnId);

		int count = countByTableId(tableId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

			query.append("tableId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tableId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					expandoColumn);

			ExpandoColumn[] array = new ExpandoColumnImpl[3];

			array[0] = (ExpandoColumn)objArray[0];
			array[1] = (ExpandoColumn)objArray[1];
			array[2] = (ExpandoColumn)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public ExpandoColumn findByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = fetchByT_N(tableId, name);

		if (expandoColumn == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No ExpandoColumn exists with the key {");

			msg.append("tableId=" + tableId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchColumnException(msg.toString());
		}

		return expandoColumn;
	}

	public ExpandoColumn fetchByT_N(long tableId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), name };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_N,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (name != null) {
					qPos.add(name);
				}

				List<ExpandoColumn> list = q.list();

				ExpandoColumn expandoColumn = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_N,
						finderArgs, list);
				}
				else {
					expandoColumn = list.get(0);

					cacheResult(expandoColumn);
				}

				return expandoColumn;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (ExpandoColumn)result;
			}
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.setLimit(start, end);

			dynamicQuery.compile(session);

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<ExpandoColumn> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<ExpandoColumn> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<ExpandoColumn> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("name ASC");
				}

				Query q = session.createQuery(query.toString());

				List<ExpandoColumn> list = null;

				if (obc == null) {
					list = (List<ExpandoColumn>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ExpandoColumn>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				cacheResult(list);

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
			return (List<ExpandoColumn>)result;
		}
	}

	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoColumn expandoColumn : findByTableId(tableId)) {
			remove(expandoColumn);
		}
	}

	public void removeByT_N(long tableId, String name)
		throws NoSuchColumnException, SystemException {
		ExpandoColumn expandoColumn = findByT_N(tableId, name);

		remove(expandoColumn);
	}

	public void removeAll() throws SystemException {
		for (ExpandoColumn expandoColumn : findAll()) {
			remove(expandoColumn);
		}
	}

	public int countByTableId(long tableId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId) };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TABLEID,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TABLEID,
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

	public int countByT_N(long tableId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tableId), name };

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_N,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.expando.model.ExpandoColumn WHERE ");

				query.append("tableId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("name IS NULL");
				}
				else {
					query.append("name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (name != null) {
					qPos.add(name);
				}

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_N, finderArgs,
					count);

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
		Object[] finderArgs = new Object[0];

		Object result = FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.expando.model.ExpandoColumn");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoColumn")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoColumn>> listenersList = new ArrayList<ModelListener<ExpandoColumn>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoColumn>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoColumnPersistence expandoColumnPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoTablePersistence expandoTablePersistence;
	@BeanReference(name = "com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence.impl")
	protected com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence expandoValuePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnPersistenceImpl.class);
}