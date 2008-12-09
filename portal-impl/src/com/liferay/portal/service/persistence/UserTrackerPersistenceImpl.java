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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserTrackerException;
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
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.model.impl.UserTrackerImpl;
import com.liferay.portal.model.impl.UserTrackerModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="UserTrackerPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserTrackerPersistenceImpl extends BasePersistenceImpl
	implements UserTrackerPersistence {
	public UserTracker create(long userTrackerId) {
		UserTracker userTracker = new UserTrackerImpl();

		userTracker.setNew(true);
		userTracker.setPrimaryKey(userTrackerId);

		return userTracker;
	}

	public UserTracker remove(long userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTracker userTracker = (UserTracker)session.get(UserTrackerImpl.class,
					new Long(userTrackerId));

			if (userTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No UserTracker exists with the primary key " +
						userTrackerId);
				}

				throw new NoSuchUserTrackerException(
					"No UserTracker exists with the primary key " +
					userTrackerId);
			}

			return remove(userTracker);
		}
		catch (NoSuchUserTrackerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public UserTracker remove(UserTracker userTracker)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(userTracker);
			}
		}

		userTracker = removeImpl(userTracker);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(userTracker);
			}
		}

		return userTracker;
	}

	protected UserTracker removeImpl(UserTracker userTracker)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserTrackerImpl.class,
						userTracker.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userTracker);

			session.flush();

			return userTracker;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserTracker.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(UserTracker userTracker, boolean merge)</code>.
	 */
	public UserTracker update(UserTracker userTracker)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(UserTracker userTracker) method. Use update(UserTracker userTracker, boolean merge) instead.");
		}

		return update(userTracker, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        userTracker the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when userTracker is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public UserTracker update(UserTracker userTracker, boolean merge)
		throws SystemException {
		boolean isNew = userTracker.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(userTracker);
				}
				else {
					listener.onBeforeUpdate(userTracker);
				}
			}
		}

		userTracker = updateImpl(userTracker, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(userTracker);
				}
				else {
					listener.onAfterUpdate(userTracker);
				}
			}
		}

		return userTracker;
	}

	public UserTracker updateImpl(
		com.liferay.portal.model.UserTracker userTracker, boolean merge)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userTracker, merge);

			userTracker.setNew(false);

			return userTracker;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(UserTracker.class.getName());
		}
	}

	public UserTracker findByPrimaryKey(long userTrackerId)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = fetchByPrimaryKey(userTrackerId);

		if (userTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No UserTracker exists with the primary key " +
					userTrackerId);
			}

			throw new NoSuchUserTrackerException(
				"No UserTracker exists with the primary key " + userTrackerId);
		}

		return userTracker;
	}

	public UserTracker fetchByPrimaryKey(long userTrackerId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (UserTracker)session.get(UserTrackerImpl.class,
				new Long(userTrackerId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTracker> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<UserTracker> list = q.list();

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
			return (List<UserTracker>)result;
		}
	}

	public List<UserTracker> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<UserTracker> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "findByCompanyId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<UserTracker> list = (List<UserTracker>)QueryUtil.list(q,
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
			return (List<UserTracker>)result;
		}
	}

	public UserTracker findByCompanyId_First(long companyId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findByCompanyId_Last(long companyId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByCompanyId(companyId);

		List<UserTracker> list = findByCompanyId(companyId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findByCompanyId_PrevAndNext(long userTrackerId,
		long companyId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTracker> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				query.append("userId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<UserTracker> list = q.list();

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
			return (List<UserTracker>)result;
		}
	}

	public List<UserTracker> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<UserTracker> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<UserTracker> list = (List<UserTracker>)QueryUtil.list(q,
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
			return (List<UserTracker>)result;
		}
	}

	public UserTracker findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countByUserId(userId);

		List<UserTracker> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findByUserId_PrevAndNext(long userTrackerId,
		long userId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<UserTracker> findBySessionId(String sessionId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "findBySessionId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { sessionId };

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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				if (sessionId == null) {
					query.append("sessionId IS NULL");
				}
				else {
					query.append("sessionId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

				List<UserTracker> list = q.list();

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
			return (List<UserTracker>)result;
		}
	}

	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end) throws SystemException {
		return findBySessionId(sessionId, start, end, null);
	}

	public List<UserTracker> findBySessionId(String sessionId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "findBySessionId";
		String[] finderParams = new String[] {
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				sessionId,
				
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

				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				if (sessionId == null) {
					query.append("sessionId IS NULL");
				}
				else {
					query.append("sessionId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

				List<UserTracker> list = (List<UserTracker>)QueryUtil.list(q,
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
			return (List<UserTracker>)result;
		}
	}

	public UserTracker findBySessionId_First(String sessionId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		List<UserTracker> list = findBySessionId(sessionId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("sessionId=" + sessionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker findBySessionId_Last(String sessionId,
		OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		int count = countBySessionId(sessionId);

		List<UserTracker> list = findBySessionId(sessionId, count - 1, count,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No UserTracker exists with the key {");

			msg.append("sessionId=" + sessionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTracker[] findBySessionId_PrevAndNext(long userTrackerId,
		String sessionId, OrderByComparator obc)
		throws NoSuchUserTrackerException, SystemException {
		UserTracker userTracker = findByPrimaryKey(userTrackerId);

		int count = countBySessionId(sessionId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

			if (sessionId == null) {
				query.append("sessionId IS NULL");
			}
			else {
				query.append("sessionId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (sessionId != null) {
				qPos.add(sessionId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					userTracker);

			UserTracker[] array = new UserTrackerImpl[3];

			array[0] = (UserTracker)objArray[0];
			array[1] = (UserTracker)objArray[1];
			array[2] = (UserTracker)objArray[2];

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

	public List<UserTracker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserTracker> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserTracker> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
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

				query.append("FROM com.liferay.portal.model.UserTracker ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<UserTracker> list = null;

				if (obc == null) {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserTracker>)QueryUtil.list(q, getDialect(),
							start, end);
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
			return (List<UserTracker>)result;
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (UserTracker userTracker : findByCompanyId(companyId)) {
			remove(userTracker);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (UserTracker userTracker : findByUserId(userId)) {
			remove(userTracker);
		}
	}

	public void removeBySessionId(String sessionId) throws SystemException {
		for (UserTracker userTracker : findBySessionId(sessionId)) {
			remove(userTracker);
		}
	}

	public void removeAll() throws SystemException {
		for (UserTracker userTracker : findAll()) {
			remove(userTracker);
		}
	}

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "countByCompanyId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(companyId) };

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
				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
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
				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

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

	public int countBySessionId(String sessionId) throws SystemException {
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
		String finderMethodName = "countBySessionId";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { sessionId };

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
				query.append("FROM com.liferay.portal.model.UserTracker WHERE ");

				if (sessionId == null) {
					query.append("sessionId IS NULL");
				}
				else {
					query.append("sessionId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (sessionId != null) {
					qPos.add(sessionId);
				}

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
		boolean finderClassNameCacheEnabled = UserTrackerModelImpl.CACHE_ENABLED;
		String finderClassName = UserTracker.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.UserTracker");

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
						"value.object.listener.com.liferay.portal.model.UserTracker")));

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

	private static Log _log = LogFactory.getLog(UserTrackerPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}