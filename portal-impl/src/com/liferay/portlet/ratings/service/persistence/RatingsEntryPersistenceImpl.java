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

package com.liferay.portlet.ratings.service.persistence;

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
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.ratings.NoSuchEntryException;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="RatingsEntryPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RatingsEntryPersistenceImpl extends BasePersistenceImpl
	implements RatingsEntryPersistence {
	public RatingsEntry create(long entryId) {
		RatingsEntry ratingsEntry = new RatingsEntryImpl();

		ratingsEntry.setNew(true);
		ratingsEntry.setPrimaryKey(entryId);

		return ratingsEntry;
	}

	public RatingsEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			RatingsEntry ratingsEntry = (RatingsEntry)session.get(RatingsEntryImpl.class,
					new Long(entryId));

			if (ratingsEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No RatingsEntry exists with the primary key " +
						entryId);
				}

				throw new NoSuchEntryException(
					"No RatingsEntry exists with the primary key " + entryId);
			}

			return remove(ratingsEntry);
		}
		catch (NoSuchEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsEntry remove(RatingsEntry ratingsEntry)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(ratingsEntry);
			}
		}

		ratingsEntry = removeImpl(ratingsEntry);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(ratingsEntry);
			}
		}

		return ratingsEntry;
	}

	protected RatingsEntry removeImpl(RatingsEntry ratingsEntry)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(ratingsEntry);

			session.flush();

			return ratingsEntry;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(RatingsEntry.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(RatingsEntry ratingsEntry, boolean merge)</code>.
	 */
	public RatingsEntry update(RatingsEntry ratingsEntry)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(RatingsEntry ratingsEntry) method. Use update(RatingsEntry ratingsEntry, boolean merge) instead.");
		}

		return update(ratingsEntry, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        ratingsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when ratingsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public RatingsEntry update(RatingsEntry ratingsEntry, boolean merge)
		throws SystemException {
		boolean isNew = ratingsEntry.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(ratingsEntry);
				}
				else {
					listener.onBeforeUpdate(ratingsEntry);
				}
			}
		}

		ratingsEntry = updateImpl(ratingsEntry, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(ratingsEntry);
				}
				else {
					listener.onAfterUpdate(ratingsEntry);
				}
			}
		}

		return ratingsEntry;
	}

	public RatingsEntry updateImpl(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ratingsEntry, merge);

			ratingsEntry.setNew(false);

			return ratingsEntry;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(RatingsEntry.class.getName());
		}
	}

	public RatingsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByPrimaryKey(entryId);

		if (ratingsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No RatingsEntry exists with the primary key " +
					entryId);
			}

			throw new NoSuchEntryException(
				"No RatingsEntry exists with the primary key " + entryId);
		}

		return ratingsEntry;
	}

	public RatingsEntry fetchByPrimaryKey(long entryId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (RatingsEntry)session.get(RatingsEntryImpl.class,
				new Long(entryId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsEntry> list = q.list();

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
			return (List<RatingsEntry>)result;
		}
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<RatingsEntry> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsEntry> list = (List<RatingsEntry>)QueryUtil.list(q,
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
			return (List<RatingsEntry>)result;
		}
	}

	public RatingsEntry findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		List<RatingsEntry> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No RatingsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public RatingsEntry findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchEntryException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<RatingsEntry> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No RatingsEntry exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public RatingsEntry[] findByC_C_PrevAndNext(long entryId, long classNameId,
		long classPK, OrderByComparator obc)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

			query.append("classNameId = ?");

			query.append(" AND ");

			query.append("classPK = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					ratingsEntry);

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = (RatingsEntry)objArray[0];
			array[1] = (RatingsEntry)objArray[1];
			array[2] = (RatingsEntry)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public RatingsEntry findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = fetchByU_C_C(userId, classNameId, classPK);

		if (ratingsEntry == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No RatingsEntry exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ratingsEntry;
	}

	public RatingsEntry fetchByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
		String finderMethodName = "fetchByU_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK)
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<RatingsEntry> list = q.list();

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
			List<RatingsEntry> list = (List<RatingsEntry>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
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

	public List<RatingsEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<RatingsEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<RatingsEntry> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<RatingsEntry> list = (List<RatingsEntry>)QueryUtil.list(q,
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
			return (List<RatingsEntry>)result;
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (RatingsEntry ratingsEntry : findByC_C(classNameId, classPK)) {
			remove(ratingsEntry);
		}
	}

	public void removeByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		RatingsEntry ratingsEntry = findByU_C_C(userId, classNameId, classPK);

		remove(ratingsEntry);
	}

	public void removeAll() throws SystemException {
		for (RatingsEntry ratingsEntry : findAll()) {
			remove(ratingsEntry);
		}
	}

	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

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

	public int countByU_C_C(long userId, long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
		String finderMethodName = "countByU_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(classNameId), new Long(classPK)
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
					"FROM com.liferay.portlet.ratings.model.RatingsEntry WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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
		boolean finderClassNameCacheEnabled = RatingsEntryModelImpl.CACHE_ENABLED;
		String finderClassName = RatingsEntry.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.ratings.model.RatingsEntry");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.ratings.model.RatingsEntry")));

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

	private static Log _log = LogFactory.getLog(RatingsEntryPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}