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

package com.liferay.portlet.journal.service.persistence;

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

import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalArticleResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleResourcePersistenceImpl extends BasePersistenceImpl
	implements JournalArticleResourcePersistence {
	public JournalArticleResource create(long resourcePrimKey) {
		JournalArticleResource journalArticleResource = new JournalArticleResourceImpl();

		journalArticleResource.setNew(true);
		journalArticleResource.setPrimaryKey(resourcePrimKey);

		return journalArticleResource;
	}

	public JournalArticleResource remove(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleResource journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
					new Long(resourcePrimKey));

			if (journalArticleResource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalArticleResource exists with the primary key " +
						resourcePrimKey);
				}

				throw new NoSuchArticleResourceException(
					"No JournalArticleResource exists with the primary key " +
					resourcePrimKey);
			}

			return remove(journalArticleResource);
		}
		catch (NoSuchArticleResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleResource remove(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(journalArticleResource);
			}
		}

		journalArticleResource = removeImpl(journalArticleResource);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(journalArticleResource);
			}
		}

		return journalArticleResource;
	}

	protected JournalArticleResource removeImpl(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(journalArticleResource);

			session.flush();

			return journalArticleResource;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalArticleResource.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(JournalArticleResource journalArticleResource, boolean merge)</code>.
	 */
	public JournalArticleResource update(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(JournalArticleResource journalArticleResource) method. Use update(JournalArticleResource journalArticleResource, boolean merge) instead.");
		}

		return update(journalArticleResource, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        journalArticleResource the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when journalArticleResource is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public JournalArticleResource update(
		JournalArticleResource journalArticleResource, boolean merge)
		throws SystemException {
		boolean isNew = journalArticleResource.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(journalArticleResource);
				}
				else {
					listener.onBeforeUpdate(journalArticleResource);
				}
			}
		}

		journalArticleResource = updateImpl(journalArticleResource, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(journalArticleResource);
				}
				else {
					listener.onAfterUpdate(journalArticleResource);
				}
			}
		}

		return journalArticleResource;
	}

	public JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(journalArticleResource);
			}
			else {
				if (journalArticleResource.isNew()) {
					session.save(journalArticleResource);
				}
			}

			session.flush();

			journalArticleResource.setNew(false);

			return journalArticleResource;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalArticleResource.class.getName());
		}
	}

	public JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByPrimaryKey(resourcePrimKey);

		if (journalArticleResource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalArticleResource exists with the primary key " +
					resourcePrimKey);
			}

			throw new NoSuchArticleResourceException(
				"No JournalArticleResource exists with the primary key " +
				resourcePrimKey);
		}

		return journalArticleResource;
	}

	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
				new Long(resourcePrimKey));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticleResource> findByGroupId(long groupId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalArticleResource> list = q.list();

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
			return (List<JournalArticleResource>)result;
		}
	}

	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

				query.append("groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				List<JournalArticleResource> list = (List<JournalArticleResource>)QueryUtil.list(q,
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
			return (List<JournalArticleResource>)result;
		}
	}

	public JournalArticleResource findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchArticleResourceException, SystemException {
		List<JournalArticleResource> list = findByGroupId(groupId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleResource exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleResource findByGroupId_Last(long groupId,
		OrderByComparator obc)
		throws NoSuchArticleResourceException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalArticleResource> list = findByGroupId(groupId, count - 1,
				count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleResource exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchArticleResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId, OrderByComparator obc)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByPrimaryKey(resourcePrimKey);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

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
					journalArticleResource);

			JournalArticleResource[] array = new JournalArticleResourceImpl[3];

			array[0] = (JournalArticleResource)objArray[0];
			array[1] = (JournalArticleResource)objArray[1];
			array[2] = (JournalArticleResource)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalArticleResource findByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = fetchByG_A(groupId,
				articleId);

		if (journalArticleResource == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalArticleResource exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchArticleResourceException(msg.toString());
		}

		return journalArticleResource;
	}

	public JournalArticleResource fetchByG_A(long groupId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
		String finderMethodName = "fetchByG_A";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("articleId IS NULL");
				}
				else {
					query.append("articleId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalArticleResource> list = q.list();

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
			List<JournalArticleResource> list = (List<JournalArticleResource>)result;

			if (list.size() == 0) {
				return null;
			}
			else {
				return list.get(0);
			}
		}
	}

	public List<JournalArticleResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return dynamicQuery.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalArticleResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

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

	public List<JournalArticleResource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalArticleResource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalArticleResource> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<JournalArticleResource> list = (List<JournalArticleResource>)QueryUtil.list(q,
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
			return (List<JournalArticleResource>)result;
		}
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalArticleResource journalArticleResource : findByGroupId(
				groupId)) {
			remove(journalArticleResource);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws NoSuchArticleResourceException, SystemException {
		JournalArticleResource journalArticleResource = findByG_A(groupId,
				articleId);

		remove(journalArticleResource);
	}

	public void removeAll() throws SystemException {
		for (JournalArticleResource journalArticleResource : findAll()) {
			remove(journalArticleResource);
		}
	}

	public int countByGroupId(long groupId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

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

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
		String finderMethodName = "countByG_A";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

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
					"FROM com.liferay.portlet.journal.model.JournalArticleResource WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("articleId IS NULL");
				}
				else {
					query.append("articleId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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
		boolean finderClassNameCacheEnabled = JournalArticleResourceModelImpl.CACHE_ENABLED;
		String finderClassName = JournalArticleResource.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.journal.model.JournalArticleResource");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticleResource")));

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

	private static Log _log = LogFactory.getLog(JournalArticleResourcePersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}