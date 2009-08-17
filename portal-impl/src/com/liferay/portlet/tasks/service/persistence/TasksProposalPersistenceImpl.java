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
import com.liferay.portal.kernel.cache.CacheRegistry;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.impl.TasksProposalImpl;
import com.liferay.portlet.tasks.model.impl.TasksProposalModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="TasksProposalPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalPersistence
 * @see       TasksProposalUtil
 * @generated
 */
public class TasksProposalPersistenceImpl extends BasePersistenceImpl
	implements TasksProposalPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = TasksProposalImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_U = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_G_U = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_C",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(TasksProposal tasksProposal) {
		EntityCacheUtil.putResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
			tasksProposal);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposal.getClassNameId()),
				
			tasksProposal.getClassPK()
			}, tasksProposal);
	}

	public void cacheResult(List<TasksProposal> tasksProposals) {
		for (TasksProposal tasksProposal : tasksProposals) {
			if (EntityCacheUtil.getResult(
						TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
						TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
						this) == null) {
				cacheResult(tasksProposal);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(TasksProposalImpl.class.getName());
		EntityCacheUtil.clearCache(TasksProposalImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public TasksProposal create(long proposalId) {
		TasksProposal tasksProposal = new TasksProposalImpl();

		tasksProposal.setNew(true);
		tasksProposal.setPrimaryKey(proposalId);

		return tasksProposal;
	}

	public TasksProposal remove(long proposalId)
		throws NoSuchProposalException, SystemException {
		Session session = null;

		try {
			session = openSession();

			TasksProposal tasksProposal = (TasksProposal)session.get(TasksProposalImpl.class,
					new Long(proposalId));

			if (tasksProposal == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No TasksProposal exists with the primary key " +
						proposalId);
				}

				throw new NoSuchProposalException(
					"No TasksProposal exists with the primary key " +
					proposalId);
			}

			return remove(tasksProposal);
		}
		catch (NoSuchProposalException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal remove(TasksProposal tasksProposal)
		throws SystemException {
		for (ModelListener<TasksProposal> listener : listeners) {
			listener.onBeforeRemove(tasksProposal);
		}

		tasksProposal = removeImpl(tasksProposal);

		for (ModelListener<TasksProposal> listener : listeners) {
			listener.onAfterRemove(tasksProposal);
		}

		return tasksProposal;
	}

	protected TasksProposal removeImpl(TasksProposal tasksProposal)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (tasksProposal.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(TasksProposalImpl.class,
						tasksProposal.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(tasksProposal);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		TasksProposalModelImpl tasksProposalModelImpl = (TasksProposalModelImpl)tasksProposal;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				new Long(tasksProposalModelImpl.getOriginalClassNameId()),
				
			tasksProposalModelImpl.getOriginalClassPK()
			});

		EntityCacheUtil.removeResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey());

		return tasksProposal;
	}

	/**
	 * @deprecated Use {@link #update(TasksProposal, boolean merge)}.
	 */
	public TasksProposal update(TasksProposal tasksProposal)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(TasksProposal tasksProposal) method. Use update(TasksProposal tasksProposal, boolean merge) instead.");
		}

		return update(tasksProposal, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  tasksProposal the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when tasksProposal is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public TasksProposal update(TasksProposal tasksProposal, boolean merge)
		throws SystemException {
		boolean isNew = tasksProposal.isNew();

		for (ModelListener<TasksProposal> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(tasksProposal);
			}
			else {
				listener.onBeforeUpdate(tasksProposal);
			}
		}

		tasksProposal = updateImpl(tasksProposal, merge);

		for (ModelListener<TasksProposal> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(tasksProposal);
			}
			else {
				listener.onAfterUpdate(tasksProposal);
			}
		}

		return tasksProposal;
	}

	public TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws SystemException {
		boolean isNew = tasksProposal.isNew();

		TasksProposalModelImpl tasksProposalModelImpl = (TasksProposalModelImpl)tasksProposal;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, tasksProposal, merge);

			tasksProposal.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
			TasksProposalImpl.class, tasksProposal.getPrimaryKey(),
			tasksProposal);

		if (!isNew &&
				((tasksProposal.getClassNameId() != tasksProposalModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(tasksProposal.getClassPK(),
					tasksProposalModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(tasksProposalModelImpl.getOriginalClassNameId()),
					
				tasksProposalModelImpl.getOriginalClassPK()
				});
		}

		if (isNew ||
				((tasksProposal.getClassNameId() != tasksProposalModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(tasksProposal.getClassPK(),
					tasksProposalModelImpl.getOriginalClassPK()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					new Long(tasksProposal.getClassNameId()),
					
				tasksProposal.getClassPK()
				}, tasksProposal);
		}

		return tasksProposal;
	}

	public TasksProposal findByPrimaryKey(long proposalId)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByPrimaryKey(proposalId);

		if (tasksProposal == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No TasksProposal exists with the primary key " +
					proposalId);
			}

			throw new NoSuchProposalException(
				"No TasksProposal exists with the primary key " + proposalId);
		}

		return tasksProposal;
	}

	public TasksProposal fetchByPrimaryKey(long proposalId)
		throws SystemException {
		TasksProposal tasksProposal = (TasksProposal)EntityCacheUtil.getResult(TasksProposalModelImpl.ENTITY_CACHE_ENABLED,
				TasksProposalImpl.class, proposalId, this);

		if (tasksProposal == null) {
			Session session = null;

			try {
				session = openSession();

				tasksProposal = (TasksProposal)session.get(TasksProposalImpl.class,
						new Long(proposalId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (tasksProposal != null) {
					cacheResult(tasksProposal);
				}

				closeSession(session);
			}
		}

		return tasksProposal;
	}

	public List<TasksProposal> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tasksProposal.dueDate ASC, ");
				query.append("tasksProposal.createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksProposal> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<TasksProposal> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tasksProposal.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tasksProposal.dueDate ASC, ");
					query.append("tasksProposal.createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksProposal findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TasksProposal exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		int count = countByGroupId(groupId);

		List<TasksProposal> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TasksProposal exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByGroupId_PrevAndNext(long proposalId,
		long groupId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

			query.append("tasksProposal.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("tasksProposal.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("tasksProposal.dueDate ASC, ");
				query.append("tasksProposal.createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksProposal);

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = (TasksProposal)objArray[0];
			array[1] = (TasksProposal)objArray[1];
			array[2] = (TasksProposal)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<TasksProposal> findByG_U(long groupId, long userId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" AND ");

				query.append("tasksProposal.userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tasksProposal.dueDate ASC, ");
				query.append("tasksProposal.createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_U, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end) throws SystemException {
		return findByG_U(groupId, userId, start, end, null);
	}

	public List<TasksProposal> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_G_U,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" AND ");

				query.append("tasksProposal.userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tasksProposal.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tasksProposal.dueDate ASC, ");
					query.append("tasksProposal.createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_G_U,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public TasksProposal findByG_U_First(long groupId, long userId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		List<TasksProposal> list = findByG_U(groupId, userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TasksProposal exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal findByG_U_Last(long groupId, long userId,
		OrderByComparator obc) throws NoSuchProposalException, SystemException {
		int count = countByG_U(groupId, userId);

		List<TasksProposal> list = findByG_U(groupId, userId, count - 1, count,
				obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TasksProposal exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProposalException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public TasksProposal[] findByG_U_PrevAndNext(long proposalId, long groupId,
		long userId, OrderByComparator obc)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByPrimaryKey(proposalId);

		int count = countByG_U(groupId, userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

			query.append("tasksProposal.groupId = ?");

			query.append(" AND ");

			query.append("tasksProposal.userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("tasksProposal.");
					query.append(orderByFields[i]);

					if (obc.isAscending()) {
						query.append(" ASC");
					}
					else {
						query.append(" DESC");
					}

					if ((i + 1) < orderByFields.length) {
						query.append(", ");
					}
				}
			}

			else {
				query.append("ORDER BY ");

				query.append("tasksProposal.dueDate ASC, ");
				query.append("tasksProposal.createDate ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					tasksProposal);

			TasksProposal[] array = new TasksProposalImpl[3];

			array[0] = (TasksProposal)objArray[0];
			array[1] = (TasksProposal)objArray[1];
			array[2] = (TasksProposal)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public TasksProposal findByC_C(long classNameId, String classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = fetchByC_C(classNameId, classPK);

		if (tasksProposal == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No TasksProposal exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProposalException(msg.toString());
		}

		return tasksProposal;
	}

	public TasksProposal fetchByC_C(long classNameId, String classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	public TasksProposal fetchByC_C(long classNameId, String classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId), classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.classNameId = ?");

				query.append(" AND ");

				if (classPK == null) {
					query.append("tasksProposal.classPK IS NULL");
				}
				else {
					query.append("tasksProposal.classPK = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("tasksProposal.dueDate ASC, ");
				query.append("tasksProposal.createDate ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (classPK != null) {
					qPos.add(classPK);
				}

				List<TasksProposal> list = q.list();

				result = list;

				TasksProposal tasksProposal = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					tasksProposal = list.get(0);

					cacheResult(tasksProposal);

					if ((tasksProposal.getClassNameId() != classNameId) ||
							(tasksProposal.getClassPK() == null) ||
							!tasksProposal.getClassPK().equals(classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, tasksProposal);
					}
				}

				return tasksProposal;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, new ArrayList<TasksProposal>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (TasksProposal)result;
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

	public List<TasksProposal> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<TasksProposal> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<TasksProposal> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<TasksProposal> list = (List<TasksProposal>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT tasksProposal FROM TasksProposal tasksProposal ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("tasksProposal.");
						query.append(orderByFields[i]);

						if (obc.isAscending()) {
							query.append(" ASC");
						}
						else {
							query.append(" DESC");
						}

						if ((i + 1) < orderByFields.length) {
							query.append(", ");
						}
					}
				}

				else {
					query.append("ORDER BY ");

					query.append("tasksProposal.dueDate ASC, ");
					query.append("tasksProposal.createDate ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<TasksProposal>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<TasksProposal>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (TasksProposal tasksProposal : findByGroupId(groupId)) {
			remove(tasksProposal);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws SystemException {
		for (TasksProposal tasksProposal : findByG_U(groupId, userId)) {
			remove(tasksProposal);
		}
	}

	public void removeByC_C(long classNameId, String classPK)
		throws NoSuchProposalException, SystemException {
		TasksProposal tasksProposal = findByC_C(classNameId, classPK);

		remove(tasksProposal);
	}

	public void removeAll() throws SystemException {
		for (TasksProposal tasksProposal : findAll()) {
			remove(tasksProposal);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(tasksProposal) ");
				query.append("FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_U(long groupId, long userId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_U,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(tasksProposal) ");
				query.append("FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.groupId = ?");

				query.append(" AND ");

				query.append("tasksProposal.userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_U, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_C(long classNameId, String classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(classNameId), classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(tasksProposal) ");
				query.append("FROM TasksProposal tasksProposal WHERE ");

				query.append("tasksProposal.classNameId = ?");

				query.append(" AND ");

				if (classPK == null) {
					query.append("tasksProposal.classPK IS NULL");
				}
				else {
					query.append("tasksProposal.classPK = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (classPK != null) {
					qPos.add(classPK);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

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
						"SELECT COUNT(tasksProposal) FROM TasksProposal tasksProposal");

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
						"value.object.listener.com.liferay.portlet.tasks.model.TasksProposal")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<TasksProposal>> listenersList = new ArrayList<ModelListener<TasksProposal>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<TasksProposal>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence.impl")
	protected com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence;
	@BeanReference(name = "com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence.impl")
	protected com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence tasksReviewPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence.impl")
	protected com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence;
	@BeanReference(name = "com.liferay.portlet.social.service.persistence.SocialActivityPersistence.impl")
	protected com.liferay.portlet.social.service.persistence.SocialActivityPersistence socialActivityPersistence;
	private static Log _log = LogFactoryUtil.getLog(TasksProposalPersistenceImpl.class);
}