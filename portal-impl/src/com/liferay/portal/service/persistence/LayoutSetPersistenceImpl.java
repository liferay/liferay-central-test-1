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

import com.liferay.portal.NoSuchLayoutSetException;
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
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="LayoutSetPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutSetPersistenceImpl extends BasePersistenceImpl
	implements LayoutSetPersistence {
	public LayoutSet create(long layoutSetId) {
		LayoutSet layoutSet = new LayoutSetImpl();

		layoutSet.setNew(true);
		layoutSet.setPrimaryKey(layoutSetId);

		return layoutSet;
	}

	public LayoutSet remove(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
					new Long(layoutSetId));

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No LayoutSet exists with the primary key " +
						layoutSetId);
				}

				throw new NoSuchLayoutSetException(
					"No LayoutSet exists with the primary key " + layoutSetId);
			}

			return remove(layoutSet);
		}
		catch (NoSuchLayoutSetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet remove(LayoutSet layoutSet) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(layoutSet);
			}
		}

		layoutSet = removeImpl(layoutSet);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(layoutSet);
			}
		}

		return layoutSet;
	}

	protected LayoutSet removeImpl(LayoutSet layoutSet)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(LayoutSetImpl.class,
						layoutSet.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(layoutSet);

			session.flush();

			return layoutSet;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(LayoutSet.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(LayoutSet layoutSet, boolean merge)</code>.
	 */
	public LayoutSet update(LayoutSet layoutSet) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(LayoutSet layoutSet) method. Use update(LayoutSet layoutSet, boolean merge) instead.");
		}

		return update(layoutSet, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        layoutSet the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when layoutSet is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public LayoutSet update(LayoutSet layoutSet, boolean merge)
		throws SystemException {
		boolean isNew = layoutSet.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(layoutSet);
				}
				else {
					listener.onBeforeUpdate(layoutSet);
				}
			}
		}

		layoutSet = updateImpl(layoutSet, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(layoutSet);
				}
				else {
					listener.onAfterUpdate(layoutSet);
				}
			}
		}

		return layoutSet;
	}

	public LayoutSet updateImpl(com.liferay.portal.model.LayoutSet layoutSet,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutSet, merge);

			layoutSet.setNew(false);

			return layoutSet;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(LayoutSet.class.getName());
		}
	}

	public LayoutSet findByPrimaryKey(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByPrimaryKey(layoutSetId);

		if (layoutSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No LayoutSet exists with the primary key " +
					layoutSetId);
			}

			throw new NoSuchLayoutSetException(
				"No LayoutSet exists with the primary key " + layoutSetId);
		}

		return layoutSet;
	}

	public LayoutSet fetchByPrimaryKey(long layoutSetId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (LayoutSet)session.get(LayoutSetImpl.class,
				new Long(layoutSetId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<LayoutSet> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
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

				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<LayoutSet> list = q.list();

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
			return (List<LayoutSet>)result;
		}
	}

	public List<LayoutSet> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<LayoutSet> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
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

				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<LayoutSet> list = (List<LayoutSet>)QueryUtil.list(q,
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
			return (List<LayoutSet>)result;
		}
	}

	public LayoutSet findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		List<LayoutSet> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSet exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSet findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		int count = countByGroupId(groupId);

		List<LayoutSet> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSet exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public LayoutSet[] findByGroupId_PrevAndNext(long layoutSetId,
		long groupId, OrderByComparator obc)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByPrimaryKey(layoutSetId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

			query.append("groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					layoutSet);

			LayoutSet[] array = new LayoutSetImpl[3];

			array[0] = (LayoutSet)objArray[0];
			array[1] = (LayoutSet)objArray[1];
			array[2] = (LayoutSet)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public LayoutSet findByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByVirtualHost(virtualHost);

		if (layoutSet == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSet exists with the key {");

			msg.append("virtualHost=" + virtualHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	public LayoutSet fetchByVirtualHost(String virtualHost)
		throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
		String finderMethodName = "fetchByVirtualHost";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { virtualHost };

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

				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				List<LayoutSet> list = q.list();

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
			List<LayoutSet> list = (List<LayoutSet>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public LayoutSet findByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByG_P(groupId, privateLayout);

		if (layoutSet == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No LayoutSet exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	public LayoutSet fetchByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
		String finderMethodName = "fetchByG_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
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

				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<LayoutSet> list = q.list();

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
			List<LayoutSet> list = (List<LayoutSet>)result;

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

	public List<LayoutSet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<LayoutSet> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<LayoutSet> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
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

				query.append("FROM com.liferay.portal.model.LayoutSet ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<LayoutSet> list = (List<LayoutSet>)QueryUtil.list(q,
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
			return (List<LayoutSet>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (LayoutSet layoutSet : findByGroupId(groupId)) {
			remove(layoutSet);
		}
	}

	public void removeByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByVirtualHost(virtualHost);

		remove(layoutSet);
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByG_P(groupId, privateLayout);

		remove(layoutSet);
	}

	public void removeAll() throws SystemException {
		for (LayoutSet layoutSet : findAll()) {
			remove(layoutSet);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
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
				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

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

	public int countByVirtualHost(String virtualHost) throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
		String finderMethodName = "countByVirtualHost";
		String[] finderParams = new String[] { String.class.getName() };
		Object[] finderArgs = new Object[] { virtualHost };

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
				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				if (virtualHost == null) {
					query.append("virtualHost IS NULL");
				}
				else {
					query.append("virtualHost = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
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

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
		String finderMethodName = "countByG_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout)
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
				query.append("FROM com.liferay.portal.model.LayoutSet WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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
		boolean finderClassNameCacheEnabled = LayoutSetModelImpl.CACHE_ENABLED;
		String finderClassName = LayoutSet.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portal.model.LayoutSet");

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
						"value.object.listener.com.liferay.portal.model.LayoutSet")));

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

	private static Log _log = LogFactory.getLog(LayoutSetPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}