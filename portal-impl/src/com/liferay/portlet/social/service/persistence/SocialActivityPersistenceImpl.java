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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.hibernate.Query;
import com.liferay.portal.kernel.dao.hibernate.QueryPos;
import com.liferay.portal.kernel.dao.hibernate.QueryUtil;
import com.liferay.portal.kernel.dao.hibernate.Session;
import com.liferay.portal.kernel.dao.search.DynamicQuery;
import com.liferay.portal.kernel.dao.search.DynamicQueryInitializer;
import com.liferay.portal.kernel.spring.hibernate.FinderCacheUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.impl.SocialActivityImpl;
import com.liferay.portlet.social.model.impl.SocialActivityModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="SocialActivityPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialActivityPersistenceImpl extends BasePersistenceImpl
	implements SocialActivityPersistence {
	public SocialActivity create(long activityId) {
		SocialActivity socialActivity = new SocialActivityImpl();

		socialActivity.setNew(true);
		socialActivity.setPrimaryKey(activityId);

		return socialActivity;
	}

	public SocialActivity remove(long activityId)
		throws NoSuchActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivity socialActivity = (SocialActivity)session.get(SocialActivityImpl.class,
					new Long(activityId));

			if (socialActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No SocialActivity exists with the primary key " +
						activityId);
				}

				throw new NoSuchActivityException(
					"No SocialActivity exists with the primary key " +
					activityId);
			}

			return remove(socialActivity);
		}
		catch (NoSuchActivityException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity remove(SocialActivity socialActivity)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(socialActivity);
			}
		}

		socialActivity = removeImpl(socialActivity);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(socialActivity);
			}
		}

		return socialActivity;
	}

	protected SocialActivity removeImpl(SocialActivity socialActivity)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(socialActivity);

			session.flush();

			return socialActivity;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SocialActivity.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(SocialActivity socialActivity, boolean merge)</code>.
	 */
	public SocialActivity update(SocialActivity socialActivity)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(SocialActivity socialActivity) method. Use update(SocialActivity socialActivity, boolean merge) instead.");
		}

		return update(socialActivity, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        socialActivity the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when socialActivity is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public SocialActivity update(SocialActivity socialActivity, boolean merge)
		throws SystemException {
		boolean isNew = socialActivity.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(socialActivity);
				}
				else {
					listener.onBeforeUpdate(socialActivity);
				}
			}
		}

		socialActivity = updateImpl(socialActivity, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(socialActivity);
				}
				else {
					listener.onAfterUpdate(socialActivity);
				}
			}
		}

		return socialActivity;
	}

	public SocialActivity updateImpl(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(socialActivity);
			}
			else {
				if (socialActivity.isNew()) {
					session.save(socialActivity);
				}
			}

			session.flush();

			socialActivity.setNew(false);

			return socialActivity;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(SocialActivity.class.getName());
		}
	}

	public SocialActivity findByPrimaryKey(long activityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByPrimaryKey(activityId);

		if (socialActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No SocialActivity exists with the primary key " +
					activityId);
			}

			throw new NoSuchActivityException(
				"No SocialActivity exists with the primary key " + activityId);
		}

		return socialActivity;
	}

	public SocialActivity fetchByPrimaryKey(long activityId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (SocialActivity)session.get(SocialActivityImpl.class,
				new Long(activityId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SocialActivity> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByGroupId_First(long groupId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByGroupId(groupId);

		List<SocialActivity> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByGroupId_PrevAndNext(long activityId,
		long groupId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByCompanyId(long companyId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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

				query.append(
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SocialActivity> findByCompanyId(long companyId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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

				query.append(
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByCompanyId_First(long companyId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByCompanyId_Last(long companyId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByCompanyId(companyId);

		List<SocialActivity> list = findByCompanyId(companyId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByCompanyId_PrevAndNext(long activityId,
		long companyId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByUserId(long userId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("userId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	public List<SocialActivity> findByUserId(long userId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("userId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByUserId_First(long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByUserId(userId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByUserId_Last(long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByUserId(userId);

		List<SocialActivity> list = findByUserId(userId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("userId=" + userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByUserId_PrevAndNext(long activityId,
		long userId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByUserId(userId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("userId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(userId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity findByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByMirrorActivityId(mirrorActivityId);

		if (socialActivity == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("mirrorActivityId=" + mirrorActivityId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityException(msg.toString());
		}

		return socialActivity;
	}

	public SocialActivity fetchByMirrorActivityId(long mirrorActivityId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "fetchByMirrorActivityId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(mirrorActivityId) };

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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("mirrorActivityId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				List<SocialActivity> list = q.list();

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
			List<SocialActivity> list = (List<SocialActivity>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SocialActivity> findByClassNameId(long classNameId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByClassNameId(long classNameId, int start,
		int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	public List<SocialActivity> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("classNameId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByClassNameId_First(long classNameId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByClassNameId(classNameId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByClassNameId_Last(long classNameId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByClassNameId(classNameId);

		List<SocialActivity> list = findByClassNameId(classNameId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByClassNameId_PrevAndNext(long activityId,
		long classNameId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByClassNameId(classNameId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("classNameId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "findByReceiverUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId,
		int start, int end) throws SystemException {
		return findByReceiverUserId(receiverUserId, start, end, null);
	}

	public List<SocialActivity> findByReceiverUserId(long receiverUserId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "findByReceiverUserId";
		String[] finderParams = new String[] {
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(receiverUserId),
				
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByReceiverUserId_First(long receiverUserId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByReceiverUserId(receiverUserId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByReceiverUserId_Last(long receiverUserId,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByReceiverUserId(receiverUserId);

		List<SocialActivity> list = findByReceiverUserId(receiverUserId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByReceiverUserId_PrevAndNext(long activityId,
		long receiverUserId, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByReceiverUserId(receiverUserId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("receiverUserId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			else {
				query.append("ORDER BY ");

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(receiverUserId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	public List<SocialActivity> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByC_C_First(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByC_C(classNameId, classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByC_C_Last(long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		int count = countByC_C(classNameId, classPK);

		List<SocialActivity> list = findByC_C(classNameId, classPK, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByC_C_PrevAndNext(long activityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByC_C(classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "findByM_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("mirrorActivityId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SocialActivity> list = q.list();

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
			return (List<SocialActivity>)result;
		}
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end)
		throws SystemException {
		return findByM_C_C(mirrorActivityId, classNameId, classPK, start, end,
			null);
	}

	public List<SocialActivity> findByM_C_C(long mirrorActivityId,
		long classNameId, long classPK, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "findByM_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("mirrorActivityId = ?");

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

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public SocialActivity findByM_C_C_First(long mirrorActivityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		List<SocialActivity> list = findByM_C_C(mirrorActivityId, classNameId,
				classPK, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("mirrorActivityId=" + mirrorActivityId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity findByM_C_C_Last(long mirrorActivityId,
		long classNameId, long classPK, OrderByComparator obc)
		throws NoSuchActivityException, SystemException {
		int count = countByM_C_C(mirrorActivityId, classNameId, classPK);

		List<SocialActivity> list = findByM_C_C(mirrorActivityId, classNameId,
				classPK, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("mirrorActivityId=" + mirrorActivityId);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchActivityException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SocialActivity[] findByM_C_C_PrevAndNext(long activityId,
		long mirrorActivityId, long classNameId, long classPK,
		OrderByComparator obc) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByPrimaryKey(activityId);

		int count = countByM_C_C(mirrorActivityId, classNameId, classPK);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

			query.append("mirrorActivityId = ?");

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

				query.append("createDate DESC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(mirrorActivityId);

			qPos.add(classNameId);

			qPos.add(classPK);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					socialActivity);

			SocialActivity[] array = new SocialActivityImpl[3];

			array[0] = (SocialActivity)objArray[0];
			array[1] = (SocialActivity)objArray[1];
			array[2] = (SocialActivity)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SocialActivity findByG_U_CD_C_C_T_R(long groupId, long userId,
		Date createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = fetchByG_U_CD_C_C_T_R(groupId, userId,
				createDate, classNameId, classPK, type, receiverUserId);

		if (socialActivity == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No SocialActivity exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("userId=" + userId);

			msg.append(", ");
			msg.append("createDate=" + createDate);

			msg.append(", ");
			msg.append("classNameId=" + classNameId);

			msg.append(", ");
			msg.append("classPK=" + classPK);

			msg.append(", ");
			msg.append("type=" + type);

			msg.append(", ");
			msg.append("receiverUserId=" + receiverUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchActivityException(msg.toString());
		}

		return socialActivity;
	}

	public SocialActivity fetchByG_U_CD_C_C_T_R(long groupId, long userId,
		Date createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "fetchByG_U_CD_C_C_T_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				createDate, new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (createDate == null) {
					query.append("createDate IS NULL");
				}
				else {
					query.append("createDate = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("createDate DESC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

				List<SocialActivity> list = q.list();

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
			List<SocialActivity> list = (List<SocialActivity>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<SocialActivity> findWithDynamicQuery(
		DynamicQueryInitializer queryInitializer) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			DynamicQuery query = queryInitializer.initialize(session);

			return query.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findWithDynamicQuery(
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
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SocialActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SocialActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SocialActivity> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				else {
					query.append("ORDER BY ");

					query.append("createDate DESC");
				}

				Query q = session.createQuery(query.toString());

				List<SocialActivity> list = (List<SocialActivity>)QueryUtil.list(q,
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
			return (List<SocialActivity>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SocialActivity socialActivity : findByGroupId(groupId)) {
			remove(socialActivity);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SocialActivity socialActivity : findByCompanyId(companyId)) {
			remove(socialActivity);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		for (SocialActivity socialActivity : findByUserId(userId)) {
			remove(socialActivity);
		}
	}

	public void removeByMirrorActivityId(long mirrorActivityId)
		throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByMirrorActivityId(mirrorActivityId);

		remove(socialActivity);
	}

	public void removeByClassNameId(long classNameId) throws SystemException {
		for (SocialActivity socialActivity : findByClassNameId(classNameId)) {
			remove(socialActivity);
		}
	}

	public void removeByReceiverUserId(long receiverUserId)
		throws SystemException {
		for (SocialActivity socialActivity : findByReceiverUserId(
				receiverUserId)) {
			remove(socialActivity);
		}
	}

	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (SocialActivity socialActivity : findByC_C(classNameId, classPK)) {
			remove(socialActivity);
		}
	}

	public void removeByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) throws SystemException {
		for (SocialActivity socialActivity : findByM_C_C(mirrorActivityId,
				classNameId, classPK)) {
			remove(socialActivity);
		}
	}

	public void removeByG_U_CD_C_C_T_R(long groupId, long userId,
		Date createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws NoSuchActivityException, SystemException {
		SocialActivity socialActivity = findByG_U_CD_C_C_T_R(groupId, userId,
				createDate, classNameId, classPK, type, receiverUserId);

		remove(socialActivity);
	}

	public void removeAll() throws SystemException {
		for (SocialActivity socialActivity : findAll()) {
			remove(socialActivity);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

	public int countByCompanyId(long companyId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
				query.append(
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

	public int countByMirrorActivityId(long mirrorActivityId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "countByMirrorActivityId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(mirrorActivityId) };

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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("mirrorActivityId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

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

	public int countByClassNameId(long classNameId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

	public int countByReceiverUserId(long receiverUserId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "countByReceiverUserId";
		String[] finderParams = new String[] { Long.class.getName() };
		Object[] finderArgs = new Object[] { new Long(receiverUserId) };

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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(receiverUserId);

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
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

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

	public int countByM_C_C(long mirrorActivityId, long classNameId,
		long classPK) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "countByM_C_C";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(mirrorActivityId), new Long(classNameId),
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("mirrorActivityId = ?");

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(mirrorActivityId);

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

	public int countByG_U_CD_C_C_T_R(long groupId, long userId,
		Date createDate, long classNameId, long classPK, int type,
		long receiverUserId) throws SystemException {
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
		String finderMethodName = "countByG_U_CD_C_C_T_R";
		String[] finderParams = new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), new Long(userId),
				
				createDate, new Long(classNameId), new Long(classPK),
				new Integer(type), new Long(receiverUserId)
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
					"FROM com.liferay.portlet.social.model.SocialActivity WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("userId = ?");

				query.append(" AND ");

				if (createDate == null) {
					query.append("createDate IS NULL");
				}
				else {
					query.append("createDate = ?");
				}

				query.append(" AND ");

				query.append("classNameId = ?");

				query.append(" AND ");

				query.append("classPK = ?");

				query.append(" AND ");

				query.append("type_ = ?");

				query.append(" AND ");

				query.append("receiverUserId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (createDate != null) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(type);

				qPos.add(receiverUserId);

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
		boolean finderClassNameCacheEnabled = SocialActivityModelImpl.CACHE_ENABLED;
		String finderClassName = SocialActivity.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.social.model.SocialActivity");

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
						"value.object.listener.com.liferay.portlet.social.model.SocialActivity")));

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

	private static Log _log = LogFactory.getLog(SocialActivityPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}