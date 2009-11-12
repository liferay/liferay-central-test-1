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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.AutoInject;
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

import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleResourceModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalArticleResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourcePersistence
 * @see       JournalArticleResourceUtil
 * @generated
 */
@AutoInject
public class JournalArticleResourcePersistenceImpl extends BasePersistenceImpl<JournalArticleResource>
	implements JournalArticleResourcePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_A = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalArticleResource journalArticleResource) {
		EntityCacheUtil.putResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey(), journalArticleResource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
			new Object[] {
				new Long(journalArticleResource.getGroupId()),
				
			journalArticleResource.getArticleId()
			}, journalArticleResource);
	}

	public void cacheResult(
		List<JournalArticleResource> journalArticleResources) {
		for (JournalArticleResource journalArticleResource : journalArticleResources) {
			if (EntityCacheUtil.getResult(
						JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKey(), this) == null) {
				cacheResult(journalArticleResource);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalArticleResourceImpl.class.getName());
		EntityCacheUtil.clearCache(JournalArticleResourceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public JournalArticleResource create(long resourcePrimKey) {
		JournalArticleResource journalArticleResource = new JournalArticleResourceImpl();

		journalArticleResource.setNew(true);
		journalArticleResource.setPrimaryKey(resourcePrimKey);

		return journalArticleResource;
	}

	public JournalArticleResource remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
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
		for (ModelListener<JournalArticleResource> listener : listeners) {
			listener.onBeforeRemove(journalArticleResource);
		}

		journalArticleResource = removeImpl(journalArticleResource);

		for (ModelListener<JournalArticleResource> listener : listeners) {
			listener.onAfterRemove(journalArticleResource);
		}

		return journalArticleResource;
	}

	protected JournalArticleResource removeImpl(
		JournalArticleResource journalArticleResource)
		throws SystemException {
		journalArticleResource = toUnwrappedModel(journalArticleResource);

		Session session = null;

		try {
			session = openSession();

			if (journalArticleResource.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalArticleResourceImpl.class,
						journalArticleResource.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalArticleResource);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalArticleResourceModelImpl journalArticleResourceModelImpl = (JournalArticleResourceModelImpl)journalArticleResource;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A,
			new Object[] {
				new Long(journalArticleResourceModelImpl.getOriginalGroupId()),
				
			journalArticleResourceModelImpl.getOriginalArticleId()
			});

		EntityCacheUtil.removeResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey());

		return journalArticleResource;
	}

	public JournalArticleResource updateImpl(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource,
		boolean merge) throws SystemException {
		journalArticleResource = toUnwrappedModel(journalArticleResource);

		boolean isNew = journalArticleResource.isNew();

		JournalArticleResourceModelImpl journalArticleResourceModelImpl = (JournalArticleResourceModelImpl)journalArticleResource;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalArticleResource, merge);

			journalArticleResource.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleResourceImpl.class,
			journalArticleResource.getPrimaryKey(), journalArticleResource);

		if (!isNew &&
				((journalArticleResource.getGroupId() != journalArticleResourceModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticleResource.getArticleId(),
					journalArticleResourceModelImpl.getOriginalArticleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_A,
				new Object[] {
					new Long(journalArticleResourceModelImpl.getOriginalGroupId()),
					
				journalArticleResourceModelImpl.getOriginalArticleId()
				});
		}

		if (isNew ||
				((journalArticleResource.getGroupId() != journalArticleResourceModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalArticleResource.getArticleId(),
					journalArticleResourceModelImpl.getOriginalArticleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
				new Object[] {
					new Long(journalArticleResource.getGroupId()),
					
				journalArticleResource.getArticleId()
				}, journalArticleResource);
		}

		return journalArticleResource;
	}

	protected JournalArticleResource toUnwrappedModel(
		JournalArticleResource journalArticleResource) {
		if (journalArticleResource instanceof JournalArticleResourceImpl) {
			return journalArticleResource;
		}

		JournalArticleResourceImpl journalArticleResourceImpl = new JournalArticleResourceImpl();

		journalArticleResourceImpl.setNew(journalArticleResource.isNew());
		journalArticleResourceImpl.setPrimaryKey(journalArticleResource.getPrimaryKey());

		journalArticleResourceImpl.setResourcePrimKey(journalArticleResource.getResourcePrimKey());
		journalArticleResourceImpl.setGroupId(journalArticleResource.getGroupId());
		journalArticleResourceImpl.setArticleId(journalArticleResource.getArticleId());

		return journalArticleResourceImpl;
	}

	public JournalArticleResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
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

	public JournalArticleResource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalArticleResource fetchByPrimaryKey(long resourcePrimKey)
		throws SystemException {
		JournalArticleResource journalArticleResource = (JournalArticleResource)EntityCacheUtil.getResult(JournalArticleResourceModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleResourceImpl.class, resourcePrimKey, this);

		if (journalArticleResource == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticleResource = (JournalArticleResource)session.get(JournalArticleResourceImpl.class,
						new Long(resourcePrimKey));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalArticleResource != null) {
					cacheResult(journalArticleResource);
				}

				closeSession(session);
			}
		}

		return journalArticleResource;
	}

	public List<JournalArticleResource> findByGroupId(long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ");

				query.append("journalArticleResource.groupId = ?");

				query.append(" ");

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
					list = new ArrayList<JournalArticleResource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalArticleResource> findByGroupId(long groupId, int start,
		int end, OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ");

				query.append("journalArticleResource.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleResource.");
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

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalArticleResource>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleResource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalArticleResource findByGroupId_First(long groupId,
		OrderByComparator obc)
		throws NoSuchArticleResourceException, SystemException {
		List<JournalArticleResource> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
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

		if (list.isEmpty()) {
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
				"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ");

			query.append("journalArticleResource.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("journalArticleResource.");
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
		return fetchByG_A(groupId, articleId, true);
	}

	public JournalArticleResource fetchByG_A(long groupId, String articleId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_A,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource WHERE ");

				query.append("journalArticleResource.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleResource.articleId IS NULL");
				}
				else {
					query.append("journalArticleResource.articleId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalArticleResource> list = q.list();

				result = list;

				JournalArticleResource journalArticleResource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
						finderArgs, list);
				}
				else {
					journalArticleResource = list.get(0);

					cacheResult(journalArticleResource);

					if ((journalArticleResource.getGroupId() != groupId) ||
							(journalArticleResource.getArticleId() == null) ||
							!journalArticleResource.getArticleId()
													   .equals(articleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
							finderArgs, journalArticleResource);
					}
				}

				return journalArticleResource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_A,
						finderArgs, new ArrayList<JournalArticleResource>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalArticleResource)result;
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

	public List<JournalArticleResource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalArticleResource> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalArticleResource> findAll(int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<JournalArticleResource> list = (List<JournalArticleResource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append(
					"SELECT journalArticleResource FROM JournalArticleResource journalArticleResource ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("journalArticleResource.");
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

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<JournalArticleResource>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalArticleResource>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalArticleResource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
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
		Object[] finderArgs = new Object[] { new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticleResource) ");
				query.append(
					"FROM JournalArticleResource journalArticleResource WHERE ");

				query.append("journalArticleResource.groupId = ?");

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

	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(journalArticleResource) ");
				query.append(
					"FROM JournalArticleResource journalArticleResource WHERE ");

				query.append("journalArticleResource.groupId = ?");

				query.append(" AND ");

				if (articleId == null) {
					query.append("journalArticleResource.articleId IS NULL");
				}
				else {
					query.append("journalArticleResource.articleId = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
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
						"SELECT COUNT(journalArticleResource) FROM JournalArticleResource journalArticleResource");

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalArticleResource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalArticleResource>> listenersList = new ArrayList<ModelListener<JournalArticleResource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalArticleResource>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticlePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticlePersistence journalArticlePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalFeedPersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalFeedPersistence journalFeedPersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalStructurePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalStructurePersistence journalStructurePersistence;
	@BeanReference(name = "com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence")
	protected com.liferay.portlet.journal.service.persistence.JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(JournalArticleResourcePersistenceImpl.class);
}