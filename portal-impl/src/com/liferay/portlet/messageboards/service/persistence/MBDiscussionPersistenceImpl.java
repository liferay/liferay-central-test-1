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

package com.liferay.portlet.messageboards.service.persistence;

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

import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBDiscussionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBDiscussionPersistenceImpl extends BasePersistenceImpl
	implements MBDiscussionPersistence {
	public MBDiscussion create(long discussionId) {
		MBDiscussion mbDiscussion = new MBDiscussionImpl();

		mbDiscussion.setNew(true);
		mbDiscussion.setPrimaryKey(discussionId);

		return mbDiscussion;
	}

	public MBDiscussion remove(long discussionId)
		throws NoSuchDiscussionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MBDiscussion mbDiscussion = (MBDiscussion)session.get(MBDiscussionImpl.class,
					new Long(discussionId));

			if (mbDiscussion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No MBDiscussion exists with the primary key " +
						discussionId);
				}

				throw new NoSuchDiscussionException(
					"No MBDiscussion exists with the primary key " +
					discussionId);
			}

			return remove(mbDiscussion);
		}
		catch (NoSuchDiscussionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion remove(MBDiscussion mbDiscussion)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(mbDiscussion);
			}
		}

		mbDiscussion = removeImpl(mbDiscussion);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(mbDiscussion);
			}
		}

		return mbDiscussion;
	}

	protected MBDiscussion removeImpl(MBDiscussion mbDiscussion)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(mbDiscussion);

			session.flush();

			return mbDiscussion;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBDiscussion.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(MBDiscussion mbDiscussion, boolean merge)</code>.
	 */
	public MBDiscussion update(MBDiscussion mbDiscussion)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(MBDiscussion mbDiscussion) method. Use update(MBDiscussion mbDiscussion, boolean merge) instead.");
		}

		return update(mbDiscussion, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbDiscussion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbDiscussion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public MBDiscussion update(MBDiscussion mbDiscussion, boolean merge)
		throws SystemException {
		boolean isNew = mbDiscussion.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(mbDiscussion);
				}
				else {
					listener.onBeforeUpdate(mbDiscussion);
				}
			}
		}

		mbDiscussion = updateImpl(mbDiscussion, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(mbDiscussion);
				}
				else {
					listener.onAfterUpdate(mbDiscussion);
				}
			}
		}

		return mbDiscussion;
	}

	public MBDiscussion updateImpl(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(mbDiscussion);
			}
			else {
				if (mbDiscussion.isNew()) {
					session.save(mbDiscussion);
				}
			}

			session.flush();

			mbDiscussion.setNew(false);

			return mbDiscussion;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(MBDiscussion.class.getName());
		}
	}

	public MBDiscussion findByPrimaryKey(long discussionId)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByPrimaryKey(discussionId);

		if (mbDiscussion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No MBDiscussion exists with the primary key " +
					discussionId);
			}

			throw new NoSuchDiscussionException(
				"No MBDiscussion exists with the primary key " + discussionId);
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByPrimaryKey(long discussionId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (MBDiscussion)session.get(MBDiscussionImpl.class,
				new Long(discussionId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<MBDiscussion> findByClassNameId(long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
		String finderMethodName = "findByClassNameId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classNameId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				List<MBDiscussion> list = q.list();

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
			return (List<MBDiscussion>)result;
		}
	}

	public List<MBDiscussion> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	public List<MBDiscussion> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
		String finderMethodName = "findByClassNameId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(classNameId),
				
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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				List<MBDiscussion> list = (List<MBDiscussion>)QueryUtil.list(q,
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
			return (List<MBDiscussion>)result;
		}
	}

	public MBDiscussion findByClassNameId_First(long classNameId,
		OrderByComparator obc)
		throws NoSuchDiscussionException, SystemException {
		List<MBDiscussion> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBDiscussion exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDiscussionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBDiscussion findByClassNameId_Last(long classNameId,
		OrderByComparator obc)
		throws NoSuchDiscussionException, SystemException {
		int count = countByClassNameId(classNameId);

		List<MBDiscussion> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBDiscussion exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchDiscussionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public MBDiscussion[] findByClassNameId_PrevAndNext(long discussionId,
		long classNameId, OrderByComparator obc)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = findByPrimaryKey(discussionId);

		int count = countByClassNameId(classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					mbDiscussion);

			MBDiscussion[] array = new MBDiscussionImpl[3];

			array[0] = (MBDiscussion)objArray[0];
			array[1] = (MBDiscussion)objArray[1];
			array[2] = (MBDiscussion)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public MBDiscussion findByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = fetchByC_C(classNameId, classPK);

		if (mbDiscussion == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No MBDiscussion exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchDiscussionException(msg.toString());
		}

		return mbDiscussion;
	}

	public MBDiscussion fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
		String finderMethodName = "fetchByC_C";
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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<MBDiscussion> list = q.list();

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
			List<MBDiscussion> list = (List<MBDiscussion>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<MBDiscussion> findWithDynamicQuery(DynamicQuery dynamicQuery)
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

	public List<MBDiscussion> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			dynamicQuery.compile(session);

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

	public List<MBDiscussion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<MBDiscussion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<MBDiscussion> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<MBDiscussion> list = (List<MBDiscussion>)QueryUtil.list(q,
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
			return (List<MBDiscussion>)result;
		}
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (MBDiscussion mbDiscussion : findByClassNameId(classNameId)) {
			remove(mbDiscussion);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException, SystemException {
		MBDiscussion mbDiscussion = findByC_C(classNameId, classPK);

		remove(mbDiscussion);
	}

	public void removeAll() throws SystemException {
		for (MBDiscussion mbDiscussion : findAll()) {
			remove(mbDiscussion);
		}
	}

	public int countByClassNameId(long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
		String finderMethodName = "countByClassNameId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(classNameId) };

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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

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
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
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
					"FROM com.liferay.portlet.messageboards.model.MBDiscussion WHERE ");

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

	public int countAll() throws SystemException {
		boolean finderClassNameCacheEnabled = MBDiscussionModelImpl.CACHE_ENABLED;
		String finderClassName = MBDiscussion.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.messageboards.model.MBDiscussion");

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
						"value.object.listener.com.liferay.portlet.messageboards.model.MBDiscussion")));

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

	private static Log _log = LogFactory.getLog(MBDiscussionPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}