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

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="JournalContentSearchPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalContentSearchPersistenceImpl extends BasePersistenceImpl
	implements JournalContentSearchPersistence {
	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch = new JournalContentSearchImpl();

		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		return journalContentSearch;
	}

	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
					new Long(contentSearchId));

			if (journalContentSearch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No JournalContentSearch exists with the primary key " +
						contentSearchId);
				}

				throw new NoSuchContentSearchException(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			return remove(journalContentSearch);
		}
		catch (NoSuchContentSearchException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch remove(
		JournalContentSearch journalContentSearch) throws SystemException {
		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onBeforeRemove(journalContentSearch);
			}
		}

		journalContentSearch = removeImpl(journalContentSearch);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				listener.onAfterRemove(journalContentSearch);
			}
		}

		return journalContentSearch;
	}

	protected JournalContentSearch removeImpl(
		JournalContentSearch journalContentSearch) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			session.delete(journalContentSearch);

			session.flush();

			return journalContentSearch;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalContentSearch.class.getName());
		}
	}

	/**
	 * @deprecated Use <code>update(JournalContentSearch journalContentSearch, boolean merge)</code>.
	 */
	public JournalContentSearch update(
		JournalContentSearch journalContentSearch) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(JournalContentSearch journalContentSearch) method. Use update(JournalContentSearch journalContentSearch, boolean merge) instead.");
		}

		return update(journalContentSearch, false);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        journalContentSearch the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when journalContentSearch is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public JournalContentSearch update(
		JournalContentSearch journalContentSearch, boolean merge)
		throws SystemException {
		boolean isNew = journalContentSearch.isNew();

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onBeforeCreate(journalContentSearch);
				}
				else {
					listener.onBeforeUpdate(journalContentSearch);
				}
			}
		}

		journalContentSearch = updateImpl(journalContentSearch, merge);

		if (_listeners.length > 0) {
			for (ModelListener listener : _listeners) {
				if (isNew) {
					listener.onAfterCreate(journalContentSearch);
				}
				else {
					listener.onAfterUpdate(journalContentSearch);
				}
			}
		}

		return journalContentSearch;
	}

	public JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (merge) {
				session.merge(journalContentSearch);
			}
			else {
				if (journalContentSearch.isNew()) {
					session.save(journalContentSearch);
				}
			}

			session.flush();

			journalContentSearch.setNew(false);

			return journalContentSearch;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);

			FinderCacheUtil.clearCache(JournalContentSearch.class.getName());
		}
	}

	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPrimaryKey(contentSearchId);

		if (journalContentSearch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No JournalContentSearch exists with the primary key " +
					contentSearchId);
			}

			throw new NoSuchContentSearchException(
				"No JournalContentSearch exists with the primary key " +
				contentSearchId);
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByPrimaryKey(long contentSearchId)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			return (JournalContentSearch)session.get(JournalContentSearchImpl.class,
				new Long(contentSearchId));
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P";
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

				query.append(
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<JournalContentSearch> list = q.list();

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
			return (List<JournalContentSearch>)result;
		}
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end) throws SystemException {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end, OrderByComparator obc)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P(groupId, privateLayout, 0,
				1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P(groupId, privateLayout);

		List<JournalContentSearch> list = findByG_P(groupId, privateLayout,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_PrevAndNext(long contentSearchId,
		long groupId, boolean privateLayout, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P(groupId, privateLayout);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("privateLayout = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_A";
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

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

				List<JournalContentSearch> list = q.list();

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
			return (List<JournalContentSearch>)result;
		}
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_A";
		String[] finderParams = new String[] {
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				articleId,
				
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("articleId IS NULL");
				}
				else {
					query.append("articleId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public JournalContentSearch findByG_A_First(long groupId, String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_A(groupId, articleId, 0, 1,
				obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_A_Last(long groupId, String articleId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_A(groupId, articleId);

		List<JournalContentSearch> list = findByG_A(groupId, articleId,
				count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_A_PrevAndNext(long contentSearchId,
		long groupId, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_A(groupId, articleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_L";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId)
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				List<JournalContentSearch> list = q.list();

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
			return (List<JournalContentSearch>)result;
		}
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end)
		throws SystemException {
		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_L";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("layoutId=" + layoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_L(groupId, privateLayout, layoutId);

		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("layoutId=" + layoutId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_L(groupId, privateLayout, layoutId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("privateLayout = ?");

			query.append(" AND ");

			query.append("layoutId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(layoutId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

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

				qPos.add(privateLayout);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = q.list();

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
			return (List<JournalContentSearch>)result;
		}
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end)
		throws SystemException {
		return findByG_P_A(groupId, privateLayout, articleId, start, end, null);
	}

	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId,
				
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("articleId IS NULL");
				}
				else {
					query.append("articleId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_A(groupId, privateLayout, articleId);

		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		String articleId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_A(groupId, privateLayout, articleId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("privateLayout = ?");

			query.append(" AND ");

			if (articleId == null) {
				query.append("articleId IS NULL");
			}
			else {
				query.append("articleId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			if (articleId != null) {
				qPos.add(articleId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_L_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				List<JournalContentSearch> list = q.list();

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
			return (List<JournalContentSearch>)result;
		}
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end) throws SystemException {
		return findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, null);
	}

	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end, OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "findByG_P_L_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				
				"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public JournalContentSearch findByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, 0, 1, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("layoutId=" + layoutId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch findByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, count - 1, count, obc);

		if (list.size() == 0) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("layoutId=" + layoutId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchContentSearchException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, String portletId, OrderByComparator obc)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append(
				"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

			query.append("groupId = ?");

			query.append(" AND ");

			query.append("privateLayout = ?");

			query.append(" AND ");

			query.append("layoutId = ?");

			query.append(" AND ");

			if (portletId == null) {
				query.append("portletId IS NULL");
			}
			else {
				query.append("portletId = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");
				query.append(obc.getOrderBy());
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(privateLayout);

			qPos.add(layoutId);

			if (portletId != null) {
				qPos.add(portletId);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc,
					journalContentSearch);

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = (JournalContentSearch)objArray[0];
			array[1] = (JournalContentSearch)objArray[1];
			array[2] = (JournalContentSearch)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No JournalContentSearch exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("privateLayout=" + privateLayout);

			msg.append(", ");
			msg.append("layoutId=" + layoutId);

			msg.append(", ");
			msg.append("portletId=" + portletId);

			msg.append(", ");
			msg.append("articleId=" + articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentSearchException(msg.toString());
		}

		return journalContentSearch;
	}

	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "fetchByG_P_L_P_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
				articleId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

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

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = q.list();

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
			List<JournalContentSearch> list = (List<JournalContentSearch>)result;

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

	public List<JournalContentSearch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalContentSearch> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalContentSearch> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch ");

				if (obc != null) {
					query.append("ORDER BY ");
					query.append(obc.getOrderBy());
				}

				Query q = session.createQuery(query.toString());

				List<JournalContentSearch> list = (List<JournalContentSearch>)QueryUtil.list(q,
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
			return (List<JournalContentSearch>)result;
		}
	}

	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P(groupId,
				privateLayout)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_A(groupId,
				articleId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L(groupId,
				privateLayout, layoutId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_A(groupId,
				privateLayout, articleId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L_P(
				groupId, privateLayout, layoutId, portletId)) {
			remove(journalContentSearch);
		}
	}

	public void removeByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		remove(journalContentSearch);
	}

	public void removeAll() throws SystemException {
		for (JournalContentSearch journalContentSearch : findAll()) {
			remove(journalContentSearch);
		}
	}

	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
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
				query.append(
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

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

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

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

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "countByG_P_L";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId)
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

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

	public int countByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "countByG_P_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				
				articleId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

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

				qPos.add(privateLayout);

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

	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "countByG_P_L_P";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
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

	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws SystemException {
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
		String finderMethodName = "countByG_P_L_P_A";
		String[] finderParams = new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			};
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(privateLayout),
				new Long(layoutId),
				
				portletId,
				
				articleId
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
					"FROM com.liferay.portlet.journal.model.JournalContentSearch WHERE ");

				query.append("groupId = ?");

				query.append(" AND ");

				query.append("privateLayout = ?");

				query.append(" AND ");

				query.append("layoutId = ?");

				query.append(" AND ");

				if (portletId == null) {
					query.append("portletId IS NULL");
				}
				else {
					query.append("portletId = ?");
				}

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

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

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
		boolean finderClassNameCacheEnabled = JournalContentSearchModelImpl.CACHE_ENABLED;
		String finderClassName = JournalContentSearch.class.getName();
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
						"SELECT COUNT(*) FROM com.liferay.portlet.journal.model.JournalContentSearch");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalContentSearch")));

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

	private static Log _log = LogFactory.getLog(JournalContentSearchPersistenceImpl.class);
	private ModelListener[] _listeners = new ModelListener[0];
}