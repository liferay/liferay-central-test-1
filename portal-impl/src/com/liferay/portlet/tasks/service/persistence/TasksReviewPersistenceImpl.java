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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.DynamicQuery;
import com.liferay.portal.kernel.dao.DynamicQueryInitializer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.spring.hibernate.FinderCache;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.model.impl.TasksReviewImpl;
import com.liferay.portlet.tasks.model.impl.TasksReviewModelImpl;

import com.liferay.util.dao.hibernate.QueryPos;
import com.liferay.util.dao.hibernate.QueryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TasksReviewPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewPersistenceImpl extends BasePersistence
	implements TasksReviewPersistence {
	public TasksReview create(long reviewId) {
		TasksReview tasksReview = new TasksReviewImpl();

		tasksReview.setNew(true);
		tasksReview.setPrimaryKey(reviewId);

		return tasksReview;
	}

	public TasksReview remove(long reviewId)
		throws NoSuchReviewException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TasksReview tasksReview = (TasksReview)session.get(TasksReviewImpl.class,
					new Long(reviewId));

			if (tasksReview == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TasksReview exists with the primary key " +
						reviewId);
				}

				throw new NoSuchReviewException(
					"No TasksReview exists with the primary key " + reviewId);
			}

			return remove(tasksReview);
		}
		catch (NoSuchReviewException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview remove(TasksReview tasksReview)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(tasksReview);
			}
		}

		tasksReview = removeImpl(tasksReview);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(tasksReview);
			}
		}

		return tasksReview;
	}

	protected TasksReview removeImpl(TasksReview tasksReview)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(tasksReview);

			session.flush();

			return tasksReview;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TasksReview.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(TasksReview tasksReview, boolean merge)</code>.
	 */
	public TasksReview update(TasksReview tasksReview)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TasksReview tasksReview) method. Use update(TasksReview tasksReview, boolean merge) instead.");
		}

		return update(tasksReview, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        tasksReview the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when tasksReview is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public TasksReview update(TasksReview tasksReview, boolean merge)
		throws SystemException {
		boolean isNew = tasksReview.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(tasksReview);
				}
				else {
					listener.onBeforeUpdate(tasksReview);
				}
			}
		}

		tasksReview = updateImpl(tasksReview, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(tasksReview);
				}
				else {
					listener.onAfterUpdate(tasksReview);
				}
			}
		}

		return tasksReview;
	}

	public TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(tasksReview);
			}
			else {
				if (tasksReview.isNew()) {
					session.save(tasksReview);
				}
			}

			session.flush();

			tasksReview.setNew(false);

			return tasksReview;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);

			FinderCache.clearCache(TasksReview.class.getName());
		}
	}

	public TasksReview findByPrimaryKey(long reviewId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = fetchByPrimaryKey(reviewId);

		if (tasksReview == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TasksReview exists with the primary key " +
					reviewId);
			}

			throw new NoSuchReviewException(
				"No TasksReview exists with the primary key " + reviewId);
		}

		return tasksReview;
	}

	public TasksReview fetchByPrimaryKey(long reviewId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (TasksReview)session.get(TasksReviewImpl.class,
				new Long(reviewId));
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public List<TasksReview> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<TasksReview> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public TasksReview findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		int count = countByUserId(userId);

		List<TasksReview> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByUserId_PrevAndNext(long reviewId, long userId,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByProposalId(long proposalId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByProposalId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end) throws SystemException {
		return findByProposalId(proposalId, start, end, null);
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByProposalId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public TasksReview findByProposalId_First(long proposalId,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByProposalId(proposalId, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByProposalId_Last(long proposalId,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		int count = countByProposalId(proposalId);

		List<TasksReview> list = findByProposalId(proposalId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByProposalId_PrevAndNext(long reviewId,
		long proposalId, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByProposalId(proposalId);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

			query.append("proposalId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview findByU_P(long userId, long proposalId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = fetchByU_P(userId, proposalId);

		if (tasksReview == null) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("proposalId=" + proposalId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchReviewException(msg.toString());
		}

		return tasksReview;
	}

	public TasksReview fetchByU_P(long userId, long proposalId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "fetchByU_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("proposalId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(proposalId);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
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
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<TasksReview> list = (List<TasksReview>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<TasksReview> findByP_S(long proposalId, int stage)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end) throws SystemException {
		return findByP_S(proposalId, stage, start, end, null);
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public TasksReview findByP_S_First(long proposalId, int stage,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S(proposalId, stage, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_Last(long proposalId, int stage,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		int count = countByP_S(proposalId, stage);

		List<TasksReview> list = findByP_S(proposalId, stage, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_PrevAndNext(long reviewId, long proposalId,
		int stage, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S(proposalId, stage);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

			query.append("proposalId = ?");

			query.append(" AND ");

			query.append("stage = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end) throws SystemException {
		return findByP_S_C(proposalId, stage, completed, start, end, null);
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public TasksReview findByP_S_C_First(long proposalId, int stage,
		boolean completed, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C(proposalId, stage, completed, 0,
				1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(", ");
			msg.append("completed=" + completed);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_C_Last(long proposalId, int stage,
		boolean completed, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		int count = countByP_S_C(proposalId, stage, completed);

		List<TasksReview> list = findByP_S_C(proposalId, stage, completed,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(", ");
			msg.append("completed=" + completed);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_C_PrevAndNext(long reviewId,
		long proposalId, int stage, boolean completed, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S_C(proposalId, stage, completed);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

			query.append("proposalId = ?");

			query.append(" AND ");

			query.append("stage = ?");

			query.append(" AND ");

			query.append("completed = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			qPos.add(completed);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S_C_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" AND ");

				query.append("rejected = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				List<TasksReview> list = q.list();

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int start, int end)
		throws SystemException {
		return findByP_S_C_R(proposalId, stage, completed, rejected, start,
			end, null);
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "findByP_S_C_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" AND ");

				query.append("rejected = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public TasksReview findByP_S_C_R_First(long proposalId, int stage,
		boolean completed, boolean rejected, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C_R(proposalId, stage, completed,
				rejected, 0, 1, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(", ");
			msg.append("completed=" + completed);

			msg.append(", ");
			msg.append("rejected=" + rejected);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview findByP_S_C_R_Last(long proposalId, int stage,
		boolean completed, boolean rejected, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		int count = countByP_S_C_R(proposalId, stage, completed, rejected);

		List<TasksReview> list = findByP_S_C_R(proposalId, stage, completed,
				rejected, count - 1, count, obc);

		if (list.size() == 0) {
			StringMaker msg = new StringMaker();

			msg.append("No TasksReview exists with the key {");

			msg.append("proposalId=" + proposalId);

			msg.append(", ");
			msg.append("stage=" + stage);

			msg.append(", ");
			msg.append("completed=" + completed);

			msg.append(", ");
			msg.append("rejected=" + rejected);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchReviewException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksReview[] findByP_S_C_R_PrevAndNext(long reviewId,
		long proposalId, int stage, boolean completed, boolean rejected,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByPrimaryKey(reviewId);

		int count = countByP_S_C_R(proposalId, stage, completed, rejected);

		Session session = null;

		try {
			session = openSession();

			StringMaker query = new StringMaker();

			query.append(
				"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

			query.append("proposalId = ?");

			query.append(" AND ");

			query.append("stage = ?");

			query.append(" AND ");

			query.append("completed = ?");

			query.append(" AND ");

			query.append("rejected = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(proposalId);

			qPos.add(stage);

			qPos.add(completed);

			qPos.add(rejected);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksReview);

			TasksReview[] array = new TasksReviewImpl[3];

			array[0] = (TasksReview)objArray[0];
			array[1] = (TasksReview)objArray[1];
			array[2] = (TasksReview)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			query.setLimit(start, end);

			return query.list();
		}
		catch (Exception e) {
			throw HibernateUtil.processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksReview> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TasksReview> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
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
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				List<TasksReview> list = (List<TasksReview>)QueryUtil.list(q,
						getDialect(), start, end);

				if (obc == null) {
					Collections.sort(list);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list;
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return (List<TasksReview>)result;
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (TasksReview tasksReview : findByUserId(userId)) {
			remove(tasksReview);
		}
	}

	public void removeByProposalId(long proposalId) throws SystemException {
		for (TasksReview tasksReview : findByProposalId(proposalId)) {
			remove(tasksReview);
		}
	}

	public void removeByU_P(long userId, long proposalId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = findByU_P(userId, proposalId);

		remove(tasksReview);
	}

	public void removeByP_S(long proposalId, int stage)
		throws SystemException {
		for (TasksReview tasksReview : findByP_S(proposalId, stage)) {
			remove(tasksReview);
		}
	}

	public void removeByP_S_C(long proposalId, int stage, boolean completed)
		throws SystemException {
		for (TasksReview tasksReview : findByP_S_C(proposalId, stage, completed)) {
			remove(tasksReview);
		}
	}

	public void removeByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws SystemException {
		for (TasksReview tasksReview : findByP_S_C_R(proposalId, stage,
				completed, rejected)) {
			remove(tasksReview);
		}
	}

	public void removeAll() throws SystemException {
		for (TasksReview tasksReview : findAll()) {
			remove(tasksReview);
		}
	}

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByProposalId(long proposalId) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByProposalId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByU_P(long userId, long proposalId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByU_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" AND ");

				query.append("proposalId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(proposalId);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByP_S(long proposalId, int stage) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByP_S";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByP_S_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			return ((Long)result).intValue();
		}
	}

	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws SystemException {
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countByP_S_C_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringMaker query = new StringMaker();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" AND ");

				query.append("stage = ?");

				query.append(" AND ");

				query.append("completed = ?");

				query.append(" AND ");

				query.append("rejected = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				qPos.add(stage);

				qPos.add(completed);

				qPos.add(rejected);

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
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
		boolean finderClassNameCacheEnabled = TasksReviewModelImpl.CACHE_ENABLED;
		String finderClassName = TasksReview.class.getName();
		String finderMethodName = "countAll";
		String[] finderParams = new String[] {  };
		Object[] finderArgs = new Object[] {  };

		Object result = null;

		if (finderClassNameCacheEnabled) {
			result = FinderCache.getResult(finderClassName, finderMethodName,
					finderParams, finderArgs, getSessionFactory());
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.tasks.model.TasksReview");

				Long count = null;

				Iterator<Long> itr = q.list().iterator();

				if (itr.hasNext()) {
					count = itr.next();
				}

				if (count == null) {
					count = new Long(0);
				}

				FinderCache.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, count);

				return count.intValue();
			}
			catch (Exception e) {
				throw HibernateUtil.processException(e);
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

	protected void initDao() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tasks.model.TasksReview")));

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

	private static Log _log = LogFactory.getLog(TasksReviewPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}