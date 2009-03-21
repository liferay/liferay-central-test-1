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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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

import com.liferay.portlet.blogs.NoSuchStatsUserException;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.portlet.blogs.model.impl.BlogsStatsUserModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="BlogsStatsUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsStatsUserPersistenceImpl extends BasePersistenceImpl
	implements BlogsStatsUserPersistence {
	public BlogsStatsUser create(long statsUserId) {
		BlogsStatsUser blogsStatsUser = new BlogsStatsUserImpl();

		blogsStatsUser.setNew(true);
		blogsStatsUser.setPrimaryKey(statsUserId);

		return blogsStatsUser;
	}

	public BlogsStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BlogsStatsUser blogsStatsUser = (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
					new Long(statsUserId));

			if (blogsStatsUser == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No BlogsStatsUser exists with the primary key " +
						statsUserId);
				}

				throw new NoSuchStatsUserException(
					"No BlogsStatsUser exists with the primary key " +
					statsUserId);
			}

			return remove(blogsStatsUser);
		}
		catch (NoSuchStatsUserException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsStatsUser remove(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onBeforeRemove(blogsStatsUser);
		}

		blogsStatsUser = removeImpl(blogsStatsUser);

		for (ModelListener<BlogsStatsUser> listener : listeners) {
			listener.onAfterRemove(blogsStatsUser);
		}

		return blogsStatsUser;
	}

	protected BlogsStatsUser removeImpl(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BlogsStatsUserImpl.class,
						blogsStatsUser.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(blogsStatsUser);

			session.flush();

			return blogsStatsUser;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(BlogsStatsUser.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(BlogsStatsUser blogsStatsUser, boolean merge)</code>.
	 */
	public BlogsStatsUser update(BlogsStatsUser blogsStatsUser)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(BlogsStatsUser blogsStatsUser) method. Use update(BlogsStatsUser blogsStatsUser, boolean merge) instead.");
		}

		return update(blogsStatsUser, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        blogsStatsUser the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when blogsStatsUser is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public BlogsStatsUser update(BlogsStatsUser blogsStatsUser, boolean merge)
		throws SystemException {
		boolean isNew = blogsStatsUser.isNew();

		for (ModelListener<BlogsStatsUser> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(blogsStatsUser);
			}
			else {
				listener.onBeforeUpdate(blogsStatsUser);
			}
		}

		blogsStatsUser = updateImpl(blogsStatsUser, merge);

		for (ModelListener<BlogsStatsUser> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(blogsStatsUser);
			}
			else {
				listener.onAfterUpdate(blogsStatsUser);
			}
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, blogsStatsUser, merge);

			blogsStatsUser.setNew(false);

			return blogsStatsUser;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(BlogsStatsUser.class.getName());
		}
	}

	public BlogsStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByPrimaryKey(statsUserId);

		if (blogsStatsUser == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No BlogsStatsUser exists with the primary key " +
					statsUserId);
			}

			throw new NoSuchStatsUserException(
				"No BlogsStatsUser exists with the primary key " + statsUserId);
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByPrimaryKey(long statsUserId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (BlogsStatsUser)session.get(BlogsStatsUserImpl.class,
				new Long(statsUserId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsStatsUser> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<BlogsStatsUser> list = q.list();

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
			return (List<BlogsStatsUser>)result;
		}
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<BlogsStatsUser> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByGroupId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<BlogsStatsUser> list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
			return (List<BlogsStatsUser>)result;
		}
	}

	public BlogsStatsUser findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByGroupId(groupId);

		List<BlogsStatsUser> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByGroupId_PrevAndNext(long statsUserId,
		long groupId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsStatsUser> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

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
			return (List<BlogsStatsUser>)result;
		}
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<BlogsStatsUser> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
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
			result = FinderCacheUtil.getResult(finderClassName,
					finderMethodName, finderParams, finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<BlogsStatsUser> list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
			return (List<BlogsStatsUser>)result;
		}
	}

	public BlogsStatsUser findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByUserId(userId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		int count = countByUserId(userId);

		List<BlogsStatsUser> list = findByUserId(userId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByUserId_PrevAndNext(long statsUserId,
		long userId, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public BlogsStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = fetchByG_U(groupId, userId);

		if (blogsStatsUser == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStatsUserException(msg.toString());
		}

		return blogsStatsUser;
	}

	public BlogsStatsUser fetchByG_U(long groupId, long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "fetchByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				List<BlogsStatsUser> list = q.list();

				if (list.isEmpty()) {
					return null;
				}

				FinderCacheUtil.putResult(finderClassNameCacheEnabled,
					finderClassName, finderMethodName, finderParams,
					finderArgs, list);

				return list.get(0);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}
		else {
			List<BlogsStatsUser> list = (List<BlogsStatsUser>)result;

			if (list.isEmpty()) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByG_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount)
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				List<BlogsStatsUser> list = q.list();

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
			return (List<BlogsStatsUser>)result;
		}
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end) throws SystemException {
		return findByG_E(groupId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByG_E(long groupId, int entryCount,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByG_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount),
				
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

				List<BlogsStatsUser> list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
			return (List<BlogsStatsUser>)result;
		}
	}

	public BlogsStatsUser findByG_E_First(long groupId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByG_E_Last(long groupId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByG_E(groupId, entryCount);

		List<BlogsStatsUser> list = findByG_E(groupId, entryCount, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByG_E_PrevAndNext(long statsUserId,
		long groupId, int entryCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByG_E(groupId, entryCount);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("entryCount != ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(entryCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByC_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount)
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("entryCount DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				List<BlogsStatsUser> list = q.list();

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
			return (List<BlogsStatsUser>)result;
		}
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end) throws SystemException {
		return findByC_E(companyId, entryCount, start, end, null);
	}

	public List<BlogsStatsUser> findByC_E(long companyId, int entryCount,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "findByC_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount),
				
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

				List<BlogsStatsUser> list = (List<BlogsStatsUser>)QueryUtil.list(q,
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
			return (List<BlogsStatsUser>)result;
		}
	}

	public BlogsStatsUser findByC_E_First(long companyId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser findByC_E_Last(long companyId, int entryCount,
		OrderByComparator obc) throws NoSuchStatsUserException, SystemException {
		int count = countByC_E(companyId, entryCount);

		List<BlogsStatsUser> list = findByC_E(companyId, entryCount, count - 1,
				count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No BlogsStatsUser exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(", ");
			msg.append("entryCount=" + entryCount);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStatsUserException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public BlogsStatsUser[] findByC_E_PrevAndNext(long statsUserId,
		long companyId, int entryCount, OrderByComparator obc)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByPrimaryKey(statsUserId);

		int count = countByC_E(companyId, entryCount);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

			query.append("companyId = ?");

			query.append(" AND ");

			query.append("entryCount != ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("entryCount DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(entryCount);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					blogsStatsUser);

			BlogsStatsUser[] array = new BlogsStatsUserImpl[3];

			array[0] = (BlogsStatsUser)objArray[0];
			array[1] = (BlogsStatsUser)objArray[1];
			array[2] = (BlogsStatsUser)objArray[2];

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

	public List<BlogsStatsUser> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<BlogsStatsUser> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("entryCount DESC");
				}

				Query q = session.createQuery(query.toString());

				List<BlogsStatsUser> list = null;

				if (obc == null) {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BlogsStatsUser>)QueryUtil.list(q,
							getDialect(), start, end);
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
			return (List<BlogsStatsUser>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByGroupId(groupId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByUserId(userId)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException, SystemException {
		BlogsStatsUser blogsStatsUser = findByG_U(groupId, userId);

		remove(blogsStatsUser);
	}

	public void removeByG_E(long groupId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByG_E(groupId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeByC_E(long companyId, int entryCount)
		throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findByC_E(companyId, entryCount)) {
			remove(blogsStatsUser);
		}
	}

	public void removeAll() throws SystemException {
		for (BlogsStatsUser blogsStatsUser : findAll()) {
			remove(blogsStatsUser);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "countByGroupId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(groupId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	public int countByUserId(long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "countByUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(userId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

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

	public int countByG_U(long groupId, long userId) throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "countByG_U";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), new Long(userId) };

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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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

	public int countByG_E(long groupId, int entryCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "countByG_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Integer(entryCount)
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(entryCount);

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

	public int countByC_E(long companyId, int entryCount)
		throws SystemException {
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
		String finderMethodName = "countByC_E";
		String[] finderParams = new String[] {
				Long.class.getName(), Integer.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId), new Integer(entryCount)
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
					"FROM com.liferay.portlet.blogs.model.BlogsStatsUser WHERE ");

				query.append("companyId = ?");

				query.append(" AND ");

				query.append("entryCount != ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(entryCount);

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
		boolean finderClassNameCacheEnabled = BlogsStatsUserModelImpl.CACHE_ENABLED;
		String finderClassName = BlogsStatsUser.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.blogs.model.BlogsStatsUser");

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

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.blogs.model.BlogsStatsUser")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BlogsStatsUser>> listenersList = new ArrayList<ModelListener<BlogsStatsUser>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BlogsStatsUser>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence.impl")
	protected com.liferay.portlet.blogs.service.persistence.BlogsStatsUserPersistence blogsStatsUserPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence.impl")
	protected com.liferay.portal.service.persistence.GroupPersistence groupPersistence;
	private static Log _log = LogFactoryUtil.getLog(BlogsStatsUserPersistenceImpl.class);
}