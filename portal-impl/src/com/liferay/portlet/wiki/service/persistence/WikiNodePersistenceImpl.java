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

package com.liferay.portlet.wiki.service.persistence;

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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiNodeModelImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WikiNodePersistenceImpl extends BasePersistenceImpl
	implements WikiNodePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = WikiNodeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_UUID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_GROUPID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCompanyId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(WikiNode wikiNode) {
		EntityCacheUtil.putResult(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeImpl.class, wikiNode.getPrimaryKey(), wikiNode);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { wikiNode.getUuid(), new Long(wikiNode.getGroupId()) },
			wikiNode);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] { new Long(wikiNode.getGroupId()), wikiNode.getName() },
			wikiNode);
	}

	public void cacheResult(List<WikiNode> wikiNodes) {
		for (WikiNode wikiNode : wikiNodes) {
			if (EntityCacheUtil.getResult(
						WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
						WikiNodeImpl.class, wikiNode.getPrimaryKey(), this) == null) {
				cacheResult(wikiNode);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(WikiNodeImpl.class.getName());
		EntityCacheUtil.clearCache(WikiNodeImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public WikiNode create(long nodeId) {
		WikiNode wikiNode = new WikiNodeImpl();

		wikiNode.setNew(true);
		wikiNode.setPrimaryKey(nodeId);

		String uuid = PortalUUIDUtil.generate();

		wikiNode.setUuid(uuid);

		return wikiNode;
	}

	public WikiNode remove(long nodeId)
		throws NoSuchNodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			WikiNode wikiNode = (WikiNode)session.get(WikiNodeImpl.class,
					new Long(nodeId));

			if (wikiNode == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No WikiNode exists with the primary key " +
						nodeId);
				}

				throw new NoSuchNodeException(
					"No WikiNode exists with the primary key " + nodeId);
			}

			return remove(wikiNode);
		}
		catch (NoSuchNodeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiNode remove(WikiNode wikiNode) throws SystemException {
		for (ModelListener<WikiNode> listener : listeners) {
			listener.onBeforeRemove(wikiNode);
		}

		wikiNode = removeImpl(wikiNode);

		for (ModelListener<WikiNode> listener : listeners) {
			listener.onAfterRemove(wikiNode);
		}

		return wikiNode;
	}

	protected WikiNode removeImpl(WikiNode wikiNode) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			if (wikiNode.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(WikiNodeImpl.class,
						wikiNode.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(wikiNode);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		WikiNodeModelImpl wikiNodeModelImpl = (WikiNodeModelImpl)wikiNode;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				wikiNodeModelImpl.getOriginalUuid(),
				new Long(wikiNodeModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				new Long(wikiNodeModelImpl.getOriginalGroupId()),
				
			wikiNodeModelImpl.getOriginalName()
			});

		EntityCacheUtil.removeResult(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeImpl.class, wikiNode.getPrimaryKey());

		return wikiNode;
	}

	public WikiNode update(WikiNode wikiNode) throws SystemException {
		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using the deprecated update(WikiNode wikiNode) method. Use update(WikiNode wikiNode, boolean merge) instead.");
		}

		return update(wikiNode, false);
	}

	public WikiNode update(WikiNode wikiNode, boolean merge)
		throws SystemException {
		boolean isNew = wikiNode.isNew();

		for (ModelListener<WikiNode> listener : listeners) {
			if (isNew) {
				listener.onBeforeCreate(wikiNode);
			}
			else {
				listener.onBeforeUpdate(wikiNode);
			}
		}

		wikiNode = updateImpl(wikiNode, merge);

		for (ModelListener<WikiNode> listener : listeners) {
			if (isNew) {
				listener.onAfterCreate(wikiNode);
			}
			else {
				listener.onAfterUpdate(wikiNode);
			}
		}

		return wikiNode;
	}

	public WikiNode updateImpl(
		com.liferay.portlet.wiki.model.WikiNode wikiNode, boolean merge)
		throws SystemException {
		boolean isNew = wikiNode.isNew();

		WikiNodeModelImpl wikiNodeModelImpl = (WikiNodeModelImpl)wikiNode;

		if (Validator.isNull(wikiNode.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiNode.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, wikiNode, merge);

			wikiNode.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
			WikiNodeImpl.class, wikiNode.getPrimaryKey(), wikiNode);

		if (!isNew &&
				(!Validator.equals(wikiNode.getUuid(),
					wikiNodeModelImpl.getOriginalUuid()) ||
				(wikiNode.getGroupId() != wikiNodeModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					wikiNodeModelImpl.getOriginalUuid(),
					new Long(wikiNodeModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(wikiNode.getUuid(),
					wikiNodeModelImpl.getOriginalUuid()) ||
				(wikiNode.getGroupId() != wikiNodeModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] { wikiNode.getUuid(), new Long(
						wikiNode.getGroupId()) }, wikiNode);
		}

		if (!isNew &&
				((wikiNode.getGroupId() != wikiNodeModelImpl.getOriginalGroupId()) ||
				!Validator.equals(wikiNode.getName(),
					wikiNodeModelImpl.getOriginalName()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					new Long(wikiNodeModelImpl.getOriginalGroupId()),
					
				wikiNodeModelImpl.getOriginalName()
				});
		}

		if (isNew ||
				((wikiNode.getGroupId() != wikiNodeModelImpl.getOriginalGroupId()) ||
				!Validator.equals(wikiNode.getName(),
					wikiNodeModelImpl.getOriginalName()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] { new Long(wikiNode.getGroupId()), wikiNode.getName() },
				wikiNode);
		}

		return wikiNode;
	}

	public WikiNode findByPrimaryKey(long nodeId)
		throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = fetchByPrimaryKey(nodeId);

		if (wikiNode == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No WikiNode exists with the primary key " + nodeId);
			}

			throw new NoSuchNodeException(
				"No WikiNode exists with the primary key " + nodeId);
		}

		return wikiNode;
	}

	public WikiNode fetchByPrimaryKey(long nodeId) throws SystemException {
		WikiNode wikiNode = (WikiNode)EntityCacheUtil.getResult(WikiNodeModelImpl.ENTITY_CACHE_ENABLED,
				WikiNodeImpl.class, nodeId, this);

		if (wikiNode == null) {
			Session session = null;

			try {
				session = openSession();

				wikiNode = (WikiNode)session.get(WikiNodeImpl.class,
						new Long(nodeId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (wikiNode != null) {
					cacheResult(wikiNode);
				}

				closeSession(session);
			}
		}

		return wikiNode;
	}

	public List<WikiNode> findByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				if (uuid == null) {
					query.append("wikiNode.uuid IS NULL");
				}
				else {
					query.append("wikiNode.uuid = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiNode.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WikiNode> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<WikiNode> findByUuid(String uuid, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				if (uuid == null) {
					query.append("wikiNode.uuid IS NULL");
				}
				else {
					query.append("wikiNode.uuid = ?");
				}

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiNode.");
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

					query.append("wikiNode.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<WikiNode>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_UUID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiNode findByUuid_First(String uuid, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		List<WikiNode> list = findByUuid(uuid, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode findByUuid_Last(String uuid, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		int count = countByUuid(uuid);

		List<WikiNode> list = findByUuid(uuid, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode[] findByUuid_PrevAndNext(long nodeId, String uuid,
		OrderByComparator obc) throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = findByPrimaryKey(nodeId);

		int count = countByUuid(uuid);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

			if (uuid == null) {
				query.append("wikiNode.uuid IS NULL");
			}
			else {
				query.append("wikiNode.uuid = ?");
			}

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiNode.");
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

				query.append("wikiNode.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			if (uuid != null) {
				qPos.add(uuid);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiNode);

			WikiNode[] array = new WikiNodeImpl[3];

			array[0] = (WikiNode)objArray[0];
			array[1] = (WikiNode)objArray[1];
			array[2] = (WikiNode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiNode findByUUID_G(String uuid, long groupId)
		throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = fetchByUUID_G(uuid, groupId);

		if (wikiNode == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("uuid=" + uuid);

			msg.append(", ");
			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchNodeException(msg.toString());
		}

		return wikiNode;
	}

	public WikiNode fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public WikiNode fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				if (uuid == null) {
					query.append("wikiNode.uuid IS NULL");
				}
				else {
					query.append("wikiNode.uuid = ?");
				}

				query.append(" AND ");

				query.append("wikiNode.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiNode.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<WikiNode> list = q.list();

				result = list;

				WikiNode wikiNode = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					wikiNode = list.get(0);

					cacheResult(wikiNode);

					if ((wikiNode.getUuid() == null) ||
							!wikiNode.getUuid().equals(uuid) ||
							(wikiNode.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, wikiNode);
					}
				}

				return wikiNode;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<WikiNode>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (WikiNode)result;
			}
		}
	}

	public List<WikiNode> findByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId) };

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.groupId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiNode.name ASC");

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
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WikiNode> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<WikiNode> findByGroupId(long groupId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.groupId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiNode.");
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

					query.append("wikiNode.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<WikiNode>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiNode findByGroupId_First(long groupId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		List<WikiNode> list = findByGroupId(groupId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode findByGroupId_Last(long groupId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		int count = countByGroupId(groupId);

		List<WikiNode> list = findByGroupId(groupId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode[] findByGroupId_PrevAndNext(long nodeId, long groupId,
		OrderByComparator obc) throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = findByPrimaryKey(nodeId);

		int count = countByGroupId(groupId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

			query.append("wikiNode.groupId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiNode.");
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

				query.append("wikiNode.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiNode);

			WikiNode[] array = new WikiNodeImpl[3];

			array[0] = (WikiNode)objArray[0];
			array[1] = (WikiNode)objArray[1];
			array[2] = (WikiNode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<WikiNode> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.companyId = ?");

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiNode.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<WikiNode> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<WikiNode> findByCompanyId(long companyId, int start, int end,
		OrderByComparator obc) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.companyId = ?");

				query.append(" ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiNode.");
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

					query.append("wikiNode.name ASC");
				}

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<WikiNode>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public WikiNode findByCompanyId_First(long companyId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		List<WikiNode> list = findByCompanyId(companyId, 0, 1, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode findByCompanyId_Last(long companyId, OrderByComparator obc)
		throws NoSuchNodeException, SystemException {
		int count = countByCompanyId(companyId);

		List<WikiNode> list = findByCompanyId(companyId, count - 1, count, obc);

		if (list.isEmpty()) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("companyId=" + companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchNodeException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public WikiNode[] findByCompanyId_PrevAndNext(long nodeId, long companyId,
		OrderByComparator obc) throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = findByPrimaryKey(nodeId);

		int count = countByCompanyId(companyId);

		Session session = null;

		try {
			session = openSession();

			StringBuilder query = new StringBuilder();

			query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

			query.append("wikiNode.companyId = ?");

			query.append(" ");

			if (obc != null) {
				query.append("ORDER BY ");

				String[] orderByFields = obc.getOrderByFields();

				for (int i = 0; i < orderByFields.length; i++) {
					query.append("wikiNode.");
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

				query.append("wikiNode.name ASC");
			}

			Query q = session.createQuery(query.toString());

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, wikiNode);

			WikiNode[] array = new WikiNodeImpl[3];

			array[0] = (WikiNode)objArray[0];
			array[1] = (WikiNode)objArray[1];
			array[2] = (WikiNode)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public WikiNode findByG_N(long groupId, String name)
		throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = fetchByG_N(groupId, name);

		if (wikiNode == null) {
			StringBuilder msg = new StringBuilder();

			msg.append("No WikiNode exists with the key {");

			msg.append("groupId=" + groupId);

			msg.append(", ");
			msg.append("name=" + name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchNodeException(msg.toString());
		}

		return wikiNode;
	}

	public WikiNode fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	public WikiNode fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.groupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("wikiNode.name IS NULL");
				}
				else {
					query.append("wikiNode.name = ?");
				}

				query.append(" ");

				query.append("ORDER BY ");

				query.append("wikiNode.name ASC");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<WikiNode> list = q.list();

				result = list;

				WikiNode wikiNode = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					wikiNode = list.get(0);

					cacheResult(wikiNode);

					if ((wikiNode.getGroupId() != groupId) ||
							(wikiNode.getName() == null) ||
							!wikiNode.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, wikiNode);
					}
				}

				return wikiNode;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, new ArrayList<WikiNode>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List) {
				return null;
			}
			else {
				return (WikiNode)result;
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

	public List<WikiNode> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<WikiNode> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<WikiNode> findAll(int start, int end, OrderByComparator obc)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end), String.valueOf(obc)
			};

		List<WikiNode> list = (List<WikiNode>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT wikiNode FROM WikiNode wikiNode ");

				if (obc != null) {
					query.append("ORDER BY ");

					String[] orderByFields = obc.getOrderByFields();

					for (int i = 0; i < orderByFields.length; i++) {
						query.append("wikiNode.");
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

					query.append("wikiNode.name ASC");
				}

				Query q = session.createQuery(query.toString());

				if (obc == null) {
					list = (List<WikiNode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<WikiNode>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<WikiNode>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (WikiNode wikiNode : findByUuid(uuid)) {
			remove(wikiNode);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = findByUUID_G(uuid, groupId);

		remove(wikiNode);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (WikiNode wikiNode : findByGroupId(groupId)) {
			remove(wikiNode);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (WikiNode wikiNode : findByCompanyId(companyId)) {
			remove(wikiNode);
		}
	}

	public void removeByG_N(long groupId, String name)
		throws NoSuchNodeException, SystemException {
		WikiNode wikiNode = findByG_N(groupId, name);

		remove(wikiNode);
	}

	public void removeAll() throws SystemException {
		for (WikiNode wikiNode : findAll()) {
			remove(wikiNode);
		}
	}

	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiNode) ");
				query.append("FROM WikiNode wikiNode WHERE ");

				if (uuid == null) {
					query.append("wikiNode.uuid IS NULL");
				}
				else {
					query.append("wikiNode.uuid = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, new Long(groupId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiNode) ");
				query.append("FROM WikiNode wikiNode WHERE ");

				if (uuid == null) {
					query.append("wikiNode.uuid IS NULL");
				}
				else {
					query.append("wikiNode.uuid = ?");
				}

				query.append(" AND ");

				query.append("wikiNode.groupId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
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

				query.append("SELECT COUNT(wikiNode) ");
				query.append("FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.groupId = ?");

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiNode) ");
				query.append("FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.companyId = ?");

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBuilder query = new StringBuilder();

				query.append("SELECT COUNT(wikiNode) ");
				query.append("FROM WikiNode wikiNode WHERE ");

				query.append("wikiNode.groupId = ?");

				query.append(" AND ");

				if (name == null) {
					query.append("wikiNode.name IS NULL");
				}
				else {
					query.append("wikiNode.name = ?");
				}

				query.append(" ");

				Query q = session.createQuery(query.toString());

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
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
						"SELECT COUNT(wikiNode) FROM WikiNode wikiNode");

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
						"value.object.listener.com.liferay.portlet.wiki.model.WikiNode")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<WikiNode>> listenersList = new ArrayList<ModelListener<WikiNode>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<WikiNode>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiNodePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiNodePersistence wikiNodePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPagePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiPagePersistence wikiPagePersistence;
	@BeanReference(name = "com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence.impl")
	protected com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence wikiPageResourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence.impl")
	protected com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.SubscriptionPersistence.impl")
	protected com.liferay.portal.service.persistence.SubscriptionPersistence subscriptionPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence.impl")
	protected com.liferay.portal.service.persistence.UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(WikiNodePersistenceImpl.class);
}