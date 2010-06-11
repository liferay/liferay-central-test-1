/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchFeedException;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalFeedPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedPersistence
 * @see       JournalFeedUtil
 * @generated
 */
public class JournalFeedPersistenceImpl extends BasePersistenceImpl<JournalFeed>
	implements JournalFeedPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalFeedImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_F",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(JournalFeed journalFeed) {
		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeed.getUuid(), new Long(journalFeed.getGroupId())
			}, journalFeed);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(journalFeed.getGroupId()),
				
			journalFeed.getFeedId()
			}, journalFeed);
	}

	public void cacheResult(List<JournalFeed> journalFeeds) {
		for (JournalFeed journalFeed : journalFeeds) {
			if (EntityCacheUtil.getResult(
						JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
						JournalFeedImpl.class, journalFeed.getPrimaryKey(), this) == null) {
				cacheResult(journalFeed);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalFeedImpl.class.getName());
		EntityCacheUtil.clearCache(JournalFeedImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(JournalFeed journalFeed) {
		EntityCacheUtil.removeResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeed.getUuid(), new Long(journalFeed.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(journalFeed.getGroupId()),
				
			journalFeed.getFeedId()
			});
	}

	public JournalFeed create(long id) {
		JournalFeed journalFeed = new JournalFeedImpl();

		journalFeed.setNew(true);
		journalFeed.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalFeed.setUuid(uuid);

		return journalFeed;
	}

	public JournalFeed remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalFeed remove(long id)
		throws NoSuchFeedException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalFeed journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
					new Long(id));

			if (journalFeed == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return remove(journalFeed);
		}
		catch (NoSuchFeedException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed removeImpl(JournalFeed journalFeed)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		Session session = null;

		try {
			session = openSession();

			if (journalFeed.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalFeedImpl.class,
						journalFeed.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalFeed);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalFeedModelImpl journalFeedModelImpl = (JournalFeedModelImpl)journalFeed;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFeedModelImpl.getOriginalUuid(),
				new Long(journalFeedModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
			new Object[] {
				new Long(journalFeedModelImpl.getOriginalGroupId()),
				
			journalFeedModelImpl.getOriginalFeedId()
			});

		EntityCacheUtil.removeResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey());

		return journalFeed;
	}

	public JournalFeed updateImpl(
		com.liferay.portlet.journal.model.JournalFeed journalFeed, boolean merge)
		throws SystemException {
		journalFeed = toUnwrappedModel(journalFeed);

		boolean isNew = journalFeed.isNew();

		JournalFeedModelImpl journalFeedModelImpl = (JournalFeedModelImpl)journalFeed;

		if (Validator.isNull(journalFeed.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFeed.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalFeed, merge);

			journalFeed.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
			JournalFeedImpl.class, journalFeed.getPrimaryKey(), journalFeed);

		if (!isNew &&
				(!Validator.equals(journalFeed.getUuid(),
					journalFeedModelImpl.getOriginalUuid()) ||
				(journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFeedModelImpl.getOriginalUuid(),
					new Long(journalFeedModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalFeed.getUuid(),
					journalFeedModelImpl.getOriginalUuid()) ||
				(journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFeed.getUuid(), new Long(journalFeed.getGroupId())
				}, journalFeed);
		}

		if (!isNew &&
				((journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalFeed.getFeedId(),
					journalFeedModelImpl.getOriginalFeedId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(journalFeedModelImpl.getOriginalGroupId()),
					
				journalFeedModelImpl.getOriginalFeedId()
				});
		}

		if (isNew ||
				((journalFeed.getGroupId() != journalFeedModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalFeed.getFeedId(),
					journalFeedModelImpl.getOriginalFeedId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
				new Object[] {
					new Long(journalFeed.getGroupId()),
					
				journalFeed.getFeedId()
				}, journalFeed);
		}

		return journalFeed;
	}

	protected JournalFeed toUnwrappedModel(JournalFeed journalFeed) {
		if (journalFeed instanceof JournalFeedImpl) {
			return journalFeed;
		}

		JournalFeedImpl journalFeedImpl = new JournalFeedImpl();

		journalFeedImpl.setNew(journalFeed.isNew());
		journalFeedImpl.setPrimaryKey(journalFeed.getPrimaryKey());

		journalFeedImpl.setUuid(journalFeed.getUuid());
		journalFeedImpl.setId(journalFeed.getId());
		journalFeedImpl.setGroupId(journalFeed.getGroupId());
		journalFeedImpl.setCompanyId(journalFeed.getCompanyId());
		journalFeedImpl.setUserId(journalFeed.getUserId());
		journalFeedImpl.setUserName(journalFeed.getUserName());
		journalFeedImpl.setCreateDate(journalFeed.getCreateDate());
		journalFeedImpl.setModifiedDate(journalFeed.getModifiedDate());
		journalFeedImpl.setFeedId(journalFeed.getFeedId());
		journalFeedImpl.setName(journalFeed.getName());
		journalFeedImpl.setDescription(journalFeed.getDescription());
		journalFeedImpl.setType(journalFeed.getType());
		journalFeedImpl.setStructureId(journalFeed.getStructureId());
		journalFeedImpl.setTemplateId(journalFeed.getTemplateId());
		journalFeedImpl.setRendererTemplateId(journalFeed.getRendererTemplateId());
		journalFeedImpl.setDelta(journalFeed.getDelta());
		journalFeedImpl.setOrderByCol(journalFeed.getOrderByCol());
		journalFeedImpl.setOrderByType(journalFeed.getOrderByType());
		journalFeedImpl.setTargetLayoutFriendlyUrl(journalFeed.getTargetLayoutFriendlyUrl());
		journalFeedImpl.setTargetPortletId(journalFeed.getTargetPortletId());
		journalFeedImpl.setContentField(journalFeed.getContentField());
		journalFeedImpl.setFeedType(journalFeed.getFeedType());
		journalFeedImpl.setFeedVersion(journalFeed.getFeedVersion());

		return journalFeedImpl;
	}

	public JournalFeed findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalFeed findByPrimaryKey(long id)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByPrimaryKey(id);

		if (journalFeed == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchFeedException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalFeed;
	}

	public JournalFeed fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalFeed fetchByPrimaryKey(long id) throws SystemException {
		JournalFeed journalFeed = (JournalFeed)EntityCacheUtil.getResult(JournalFeedModelImpl.ENTITY_CACHE_ENABLED,
				JournalFeedImpl.class, id, this);

		if (journalFeed == null) {
			Session session = null;

			try {
				session = openSession();

				journalFeed = (JournalFeed)session.get(JournalFeedImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalFeed != null) {
					cacheResult(journalFeed);
				}

				closeSession(session);
			}
		}

		return journalFeed;
	}

	public List<JournalFeed> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalFeed> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalFeed> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JOURNALFEED_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalFeed findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		List<JournalFeed> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		int count = countByUuid(uuid);

		List<JournalFeed> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalFeed, uuid,
					orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByUuid_PrevAndNext(session, journalFeed, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed getByUuid_PrevAndNext(Session session,
		JournalFeed journalFeed, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFEED_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
		}

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public JournalFeed findByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByUUID_G(uuid, groupId);

		if (journalFeed == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	public JournalFeed fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalFeed fetchByUUID_G(String uuid, long groupId,
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

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALFEED_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getUuid() == null) ||
							!journalFeed.getUuid().equals(uuid) ||
							(journalFeed.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalFeed>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalFeed)result;
			}
		}
	}

	public List<JournalFeed> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalFeed> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalFeed> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(3 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(3);
				}

				query.append(_SQL_SELECT_JOURNALFEED_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalFeed findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		List<JournalFeed> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalFeed> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFeedException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalFeed[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalFeed[] array = new JournalFeedImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalFeed, groupId,
					orderByComparator, true);

			array[1] = journalFeed;

			array[2] = getByGroupId_PrevAndNext(session, journalFeed, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFeed getByGroupId_PrevAndNext(Session session,
		JournalFeed journalFeed, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFEED_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}

		else {
			query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalFeed);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFeed> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<JournalFeed> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalFeed> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<JournalFeed> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_FILTER_SQL_SELECT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalFeed.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, JournalFeedImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalFeed>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalFeed findByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = fetchByG_F(groupId, feedId);

		if (journalFeed == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", feedId=");
			msg.append(feedId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFeedException(msg.toString());
		}

		return journalFeed;
	}

	public JournalFeed fetchByG_F(long groupId, String feedId)
		throws SystemException {
		return fetchByG_F(groupId, feedId, true);
	}

	public JournalFeed fetchByG_F(long groupId, String feedId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), feedId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_F,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALFEED_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (feedId == null) {
					query.append(_FINDER_COLUMN_G_F_FEEDID_1);
				}
				else {
					if (feedId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_FEEDID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_FEEDID_2);
					}
				}

				query.append(JournalFeedModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
				}

				List<JournalFeed> list = q.list();

				result = list;

				JournalFeed journalFeed = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, list);
				}
				else {
					journalFeed = list.get(0);

					cacheResult(journalFeed);

					if ((journalFeed.getGroupId() != groupId) ||
							(journalFeed.getFeedId() == null) ||
							!journalFeed.getFeedId().equals(feedId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
							finderArgs, journalFeed);
					}
				}

				return journalFeed;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_F,
						finderArgs, new ArrayList<JournalFeed>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalFeed)result;
			}
		}
	}

	public List<JournalFeed> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalFeed> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalFeed> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalFeed> list = (List<JournalFeed>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;
				String sql = null;

				if (orderByComparator != null) {
					query = new StringBundler(2 +
							(orderByComparator.getOrderByFields().length * 3));

					query.append(_SQL_SELECT_JOURNALFEED);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JOURNALFEED.concat(JournalFeedModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalFeed>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalFeed>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalFeed journalFeed : findByUuid(uuid)) {
			remove(journalFeed);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByUUID_G(uuid, groupId);

		remove(journalFeed);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalFeed journalFeed : findByGroupId(groupId)) {
			remove(journalFeed);
		}
	}

	public void removeByG_F(long groupId, String feedId)
		throws NoSuchFeedException, SystemException {
		JournalFeed journalFeed = findByG_F(groupId, feedId);

		remove(journalFeed);
	}

	public void removeAll() throws SystemException {
		for (JournalFeed journalFeed : findAll()) {
			remove(journalFeed);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALFEED_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_UUID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALFEED_WHERE);

				if (uuid == null) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_1);
				}
				else {
					if (uuid.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_UUID_G_UUID_3);
					}
					else {
						query.append(_FINDER_COLUMN_UUID_G_UUID_2);
					}
				}

				query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALFEED_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(2);

			query.append(_FILTER_SQL_COUNT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalFeed.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countByG_F(long groupId, String feedId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), feedId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALFEED_WHERE);

				query.append(_FINDER_COLUMN_G_F_GROUPID_2);

				if (feedId == null) {
					query.append(_FINDER_COLUMN_G_F_FEEDID_1);
				}
				else {
					if (feedId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_F_FEEDID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_F_FEEDID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (feedId != null) {
					qPos.add(feedId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_F(long groupId, String feedId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, feedId);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_JOURNALFEED_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (feedId == null) {
				query.append(_FINDER_COLUMN_G_F_FEEDID_1);
			}
			else {
				if (feedId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_FEEDID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_FEEDID_2);
				}
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalFeed.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (feedId != null) {
				qPos.add(feedId);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALFEED);

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalFeed")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalFeed>> listenersList = new ArrayList<ModelListener<JournalFeed>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalFeed>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALFEED = "SELECT journalFeed FROM JournalFeed journalFeed";
	private static final String _SQL_SELECT_JOURNALFEED_WHERE = "SELECT journalFeed FROM JournalFeed journalFeed WHERE ";
	private static final String _SQL_COUNT_JOURNALFEED = "SELECT COUNT(journalFeed) FROM JournalFeed journalFeed";
	private static final String _SQL_COUNT_JOURNALFEED_WHERE = "SELECT COUNT(journalFeed) FROM JournalFeed journalFeed WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalFeed.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalFeed.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalFeed.uuid IS NULL OR journalFeed.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalFeed.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalFeed.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalFeed.uuid IS NULL OR journalFeed.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalFeed.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalFeed.groupId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "journalFeed.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FEEDID_1 = "journalFeed.feedId IS NULL";
	private static final String _FINDER_COLUMN_G_F_FEEDID_2 = "journalFeed.feedId = ?";
	private static final String _FINDER_COLUMN_G_F_FEEDID_3 = "(journalFeed.feedId IS NULL OR journalFeed.feedId = ?)";
	private static final String _FILTER_SQL_SELECT_JOURNALFEED_WHERE = "SELECT DISTINCT {journalFeed.*} FROM JournalFeed journalFeed WHERE ";
	private static final String _FILTER_SQL_COUNT_JOURNALFEED_WHERE = "SELECT COUNT(DISTINCT journalFeed.id) AS COUNT_VALUE FROM JournalFeed journalFeed WHERE ";
	private static final String _FILTER_COLUMN_ID = "journalFeed.id";
	private static final String _FILTER_COLUMN_USERID = "journalFeed.userId";
	private static final String _FILTER_ENTITY_ALIAS = "journalFeed";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalFeed.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalFeed exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalFeed exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalFeedPersistenceImpl.class);
}