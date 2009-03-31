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

package com.liferay.portlet.tasks.service.persistence;

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

import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.model.impl.TasksReviewImpl;
import com.liferay.portlet.tasks.model.impl.TasksReviewModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TasksReviewPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewPersistenceImpl extends BasePersistenceImpl
	implements TasksReviewPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TasksReviewImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByProposalId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByProposalId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PROPOSALID = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByProposalId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_P = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_P = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S_C = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S_C",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_P_S_C_R = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByP_S_C_R",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(TasksReview tasksReview) {
		EntityCacheUtil.putResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey(), tasksReview);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
			new Object[] {
				new Long(tasksReview.getUserId()),
				new Long(tasksReview.getProposalId())
			}, tasksReview);
	}

	public void cacheResult(List<TasksReview> tasksReviews) {
		for (TasksReview tasksReview : tasksReviews) {
			if (EntityCacheUtil.getResult(
						TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
						TasksReviewImpl.class, tasksReview.getPrimaryKey(), this) == null) {
				cacheResult(tasksReview);
			}
		}
	}

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview remove(TasksReview tasksReview)
		throws SystemException {
		for (ModelListener<TasksReview> listener : listeners) {
			listener.onBeforeRemove(tasksReview);
		}

		tasksReview = removeImpl(tasksReview);

		for (ModelListener<TasksReview> listener : listeners) {
			listener.onAfterRemove(tasksReview);
		}

		return tasksReview;
	}

	protected TasksReview removeImpl(TasksReview tasksReview)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (tasksReview.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TasksReviewImpl.class,
						tasksReview.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tasksReview);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TasksReviewModelImpl tasksReviewModelImpl = (TasksReviewModelImpl)tasksReview;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_P,
			new Object[] {
				new Long(tasksReviewModelImpl.getOriginalUserId()),
				new Long(tasksReviewModelImpl.getOriginalProposalId())
			});

		EntityCacheUtil.removeResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey());

		return tasksReview;
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

		for (ModelListener<TasksReview> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tasksReview);
			}
			else {
				listener.onBeforeUpdate(tasksReview);
			}
		}

		tasksReview = updateImpl(tasksReview, merge);

		for (ModelListener<TasksReview> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tasksReview);
			}
			else {
				listener.onAfterUpdate(tasksReview);
			}
		}

		return tasksReview;
	}

	public TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws SystemException {
		boolean isNew = tasksReview.isNew();

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tasksReview, merge);

			tasksReview.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
			TasksReviewImpl.class, tasksReview.getPrimaryKey(), tasksReview);

		TasksReviewModelImpl tasksReviewModelImpl = (TasksReviewModelImpl)tasksReview;

		if (!isNew &&
				((tasksReview.getUserId() != tasksReviewModelImpl.getOriginalUserId()) ||
				(tasksReview.getProposalId() != tasksReviewModelImpl.getOriginalProposalId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_P,
				new Object[] {
					new Long(tasksReviewModelImpl.getOriginalUserId()),
					new Long(tasksReviewModelImpl.getOriginalProposalId())
				});
		}

		if (isNew ||
				((tasksReview.getUserId() != tasksReviewModelImpl.getOriginalUserId()) ||
				(tasksReview.getProposalId() != tasksReviewModelImpl.getOriginalProposalId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
				new Object[] {
					new Long(tasksReview.getUserId()),
					new Long(tasksReview.getProposalId())
				}, tasksReview);
		}

		return tasksReview;
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
		TasksReview tasksReview = (TasksReview)EntityCacheUtil.getResult(TasksReviewModelImpl.ENTITY_CACHE_ENABLED,
				TasksReviewImpl.class, reviewId, this);

		if (tasksReview == null) {
			Session session = null;

			try {
				session = openSession();

				tasksReview = (TasksReview)session.get(TasksReviewImpl.class,
						new Long(reviewId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tasksReview != null) {
					cacheResult(tasksReview);
				}

				closeSession(session);
			}
		}

		return tasksReview;
	}

	public List<TasksReview> findByUserId(long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(userId) };

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<TasksReview> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_USERID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_USERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByProposalId(long proposalId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PROPOSALID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PROPOSALID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end) throws SystemException {
		return findByProposalId(proposalId, start, end, null);
	}

	public List<TasksReview> findByProposalId(long proposalId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_PROPOSALID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_PROPOSALID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByProposalId_First(long proposalId,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByProposalId(proposalId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksReview findByU_P(long userId, long proposalId)
		throws NoSuchReviewException, SystemException {
		TasksReview tasksReview = fetchByU_P(userId, proposalId);

		if (tasksReview == null) {
			StringBuilder msg = new StringBuilder();

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
		return fetchByU_P(userId, proposalId, true);
	}

	public TasksReview fetchByU_P(long userId, long proposalId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				result = list;

				TasksReview tasksReview = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
						finderArgs, list);
				}
				else {
					tasksReview = list.get(0);

					cacheResult(tasksReview);
				}

				return tasksReview;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_P,
						finderArgs, new ArrayList<TasksReview>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (TasksReview)result;
			}
		}
	}

	public List<TasksReview> findByP_S(long proposalId, int stage)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end) throws SystemException {
		return findByP_S(proposalId, stage, start, end, null);
	}

	public List<TasksReview> findByP_S(long proposalId, int stage, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_First(long proposalId, int stage,
		OrderByComparator obc) throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S(proposalId, stage, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end) throws SystemException {
		return findByP_S_C(proposalId, stage, completed, start, end, null);
	}

	public List<TasksReview> findByP_S_C(long proposalId, int stage,
		boolean completed, int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S_C,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S_C,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_C_First(long proposalId, int stage,
		boolean completed, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C(proposalId, stage, completed, 0,
				1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksReview> findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_P_S_C_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_P_S_C_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_P_S_C_R,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_P_S_C_R,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksReview findByP_S_C_R_First(long proposalId, int stage,
		boolean completed, boolean rejected, OrderByComparator obc)
		throws NoSuchReviewException, SystemException {
		List<TasksReview> list = findByP_S_C_R(proposalId, stage, completed,
				rejected, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

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

			StringBuilder query = new StringBuilder();

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
			throw processException(e);
		}
		finally {
			closeSession(session);
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

	public List<TasksReview> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksReview> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TasksReview> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksReview> list = (List<TasksReview>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				if (obc == null) {
					list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TasksReview>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksReview>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] { new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByProposalId(long proposalId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(proposalId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PROPOSALID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(*) ");
				query.append(
					"FROM com.liferay.portlet.tasks.model.TasksReview WHERE ");

				query.append("proposalId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(proposalId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PROPOSALID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_P(long userId, long proposalId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(proposalId)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S(long proposalId, int stage) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S_C(long proposalId, int stage, boolean completed)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_S_C_R(long proposalId, int stage, boolean completed,
		boolean rejected) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(proposalId), new Integer(stage),
				Boolean.valueOf(completed), Boolean.valueOf(rejected)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_S_C_R,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

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

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_S_C_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
						"SELECT COUNT(*) FROM com.liferay.portlet.tasks.model.TasksReview");

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.tasks.model.TasksReview")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TasksReview>> listenersList = new ArrayList<ModelListener<TasksReview>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TasksReview>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence.impl")
	protected com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence tasksReviewPersistence;
	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence.impl")
	protected com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(TasksReviewPersistenceImpl.class);
}