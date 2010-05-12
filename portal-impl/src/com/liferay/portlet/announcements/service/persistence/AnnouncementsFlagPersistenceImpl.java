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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AnnouncementsFlagPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagPersistence
 * @see       AnnouncementsFlagUtil
 * @generated
 */
public class AnnouncementsFlagPersistenceImpl extends BasePersistenceImpl<AnnouncementsFlag>
	implements AnnouncementsFlagPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				new Long(announcementsFlag.getUserId()),
				new Long(announcementsFlag.getEntryId()),
				new Integer(announcementsFlag.getValue())
			}, announcementsFlag);
	}

	public void cacheResult(List<AnnouncementsFlag> announcementsFlags) {
		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			if (EntityCacheUtil.getResult(
						AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKey(), this) == null) {
				cacheResult(announcementsFlag);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AnnouncementsFlagImpl.class.getName());
		EntityCacheUtil.clearCache(AnnouncementsFlagImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				new Long(announcementsFlag.getUserId()),
				new Long(announcementsFlag.getEntryId()),
				new Integer(announcementsFlag.getValue())
			});
	}

	public AnnouncementsFlag create(long flagId) {
		AnnouncementsFlag announcementsFlag = new AnnouncementsFlagImpl();

		announcementsFlag.setNew(true);
		announcementsFlag.setPrimaryKey(flagId);

		return announcementsFlag;
	}

	public AnnouncementsFlag remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AnnouncementsFlag remove(long flagId)
		throws NoSuchFlagException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
					new Long(flagId));

			if (announcementsFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + flagId);
				}

				throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					flagId);
			}

			return remove(announcementsFlag);
		}
		catch (NoSuchFlagException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AnnouncementsFlag remove(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			listener.onBeforeRemove(announcementsFlag);
		}

		announcementsFlag = removeImpl(announcementsFlag);

		for (ModelListener<AnnouncementsFlag> listener : listeners) {
			listener.onAfterRemove(announcementsFlag);
		}

		return announcementsFlag;
	}

	protected AnnouncementsFlag removeImpl(AnnouncementsFlag announcementsFlag)
		throws SystemException {
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AnnouncementsFlagImpl.class,
						announcementsFlag.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(announcementsFlag);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				new Long(announcementsFlagModelImpl.getOriginalUserId()),
				new Long(announcementsFlagModelImpl.getOriginalEntryId()),
				new Integer(announcementsFlagModelImpl.getOriginalValue())
			});

		EntityCacheUtil.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		return announcementsFlag;
	}

	public AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge) throws SystemException {
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		boolean isNew = announcementsFlag.isNew();

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, announcementsFlag, merge);

			announcementsFlag.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		if (!isNew &&
				((announcementsFlag.getUserId() != announcementsFlagModelImpl.getOriginalUserId()) ||
				(announcementsFlag.getEntryId() != announcementsFlagModelImpl.getOriginalEntryId()) ||
				(announcementsFlag.getValue() != announcementsFlagModelImpl.getOriginalValue()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_E_V,
				new Object[] {
					new Long(announcementsFlagModelImpl.getOriginalUserId()),
					new Long(announcementsFlagModelImpl.getOriginalEntryId()),
					new Integer(announcementsFlagModelImpl.getOriginalValue())
				});
		}

		if (isNew ||
				((announcementsFlag.getUserId() != announcementsFlagModelImpl.getOriginalUserId()) ||
				(announcementsFlag.getEntryId() != announcementsFlagModelImpl.getOriginalEntryId()) ||
				(announcementsFlag.getValue() != announcementsFlagModelImpl.getOriginalValue()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
				new Object[] {
					new Long(announcementsFlag.getUserId()),
					new Long(announcementsFlag.getEntryId()),
					new Integer(announcementsFlag.getValue())
				}, announcementsFlag);
		}

		return announcementsFlag;
	}

	protected AnnouncementsFlag toUnwrappedModel(
		AnnouncementsFlag announcementsFlag) {
		if (announcementsFlag instanceof AnnouncementsFlagImpl) {
			return announcementsFlag;
		}

		AnnouncementsFlagImpl announcementsFlagImpl = new AnnouncementsFlagImpl();

		announcementsFlagImpl.setNew(announcementsFlag.isNew());
		announcementsFlagImpl.setPrimaryKey(announcementsFlag.getPrimaryKey());

		announcementsFlagImpl.setFlagId(announcementsFlag.getFlagId());
		announcementsFlagImpl.setUserId(announcementsFlag.getUserId());
		announcementsFlagImpl.setCreateDate(announcementsFlag.getCreateDate());
		announcementsFlagImpl.setEntryId(announcementsFlag.getEntryId());
		announcementsFlagImpl.setValue(announcementsFlag.getValue());

		return announcementsFlagImpl;
	}

	public AnnouncementsFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(flagId);

		if (announcementsFlag == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + flagId);
			}

			throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				flagId);
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AnnouncementsFlag fetchByPrimaryKey(long flagId)
		throws SystemException {
		AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)EntityCacheUtil.getResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, flagId, this);

		if (announcementsFlag == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
						new Long(flagId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (announcementsFlag != null) {
					cacheResult(announcementsFlag);
				}

				closeSession(session);
			}
		}

		return announcementsFlag;
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId)
		throws SystemException {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end) throws SystemException {
		return findByEntryId(entryId, start, end, null);
	}

	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(entryId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ENTRYID,
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

				query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

				query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<AnnouncementsFlag>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		List<AnnouncementsFlag> list = findByEntryId(entryId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		int count = countByEntryId(entryId);

		List<AnnouncementsFlag> list = findByEntryId(entryId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFlagException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator orderByComparator)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, true);

			array[1] = announcementsFlag;

			array[2] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsFlag getByEntryId_PrevAndNext(Session session,
		AnnouncementsFlag announcementsFlag, long entryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(announcementsFlag);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AnnouncementsFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = fetchByU_E_V(userId, entryId,
				value);

		if (announcementsFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", entryId=");
			msg.append(entryId);

			msg.append(", value=");
			msg.append(value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFlagException(msg.toString());
		}

		return announcementsFlag;
	}

	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		return fetchByU_E_V(userId, entryId, value, true);
	}

	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_E_V,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_E_V_USERID_2);

				query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

				query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

				query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				List<AnnouncementsFlag> list = q.list();

				result = list;

				AnnouncementsFlag announcementsFlag = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, list);
				}
				else {
					announcementsFlag = list.get(0);

					cacheResult(announcementsFlag);

					if ((announcementsFlag.getUserId() != userId) ||
							(announcementsFlag.getEntryId() != entryId) ||
							(announcementsFlag.getValue() != value)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
							finderArgs, announcementsFlag);
					}
				}

				return announcementsFlag;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, new ArrayList<AnnouncementsFlag>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AnnouncementsFlag)result;
			}
		}
	}

	public List<AnnouncementsFlag> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AnnouncementsFlag> list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ANNOUNCEMENTSFLAG.concat(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AnnouncementsFlag>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByEntryId(long entryId) throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findByEntryId(entryId)) {
			remove(announcementsFlag);
		}
	}

	public void removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException, SystemException {
		AnnouncementsFlag announcementsFlag = findByU_E_V(userId, entryId, value);

		remove(announcementsFlag);
	}

	public void removeAll() throws SystemException {
		for (AnnouncementsFlag announcementsFlag : findAll()) {
			remove(announcementsFlag);
		}
	}

	public int countByEntryId(long entryId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(entryId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

				query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByU_E_V(long userId, long entryId, int value)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(userId), new Long(entryId), new Integer(value)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_E_V,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

				query.append(_FINDER_COLUMN_U_E_V_USERID_2);

				query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

				query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_E_V,
					finderArgs, count);

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

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSFLAG);

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
						"value.object.listener.com.liferay.portlet.announcements.model.AnnouncementsFlag")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AnnouncementsFlag>> listenersList = new ArrayList<ModelListener<AnnouncementsFlag>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AnnouncementsFlag>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AnnouncementsDeliveryPersistence.class)
	protected AnnouncementsDeliveryPersistence announcementsDeliveryPersistence;
	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;
	@BeanReference(type = AnnouncementsFlagPersistence.class)
	protected AnnouncementsFlagPersistence announcementsFlagPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "announcementsFlag.entryId = ?";
	private static final String _FINDER_COLUMN_U_E_V_USERID_2 = "announcementsFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_ENTRYID_2 = "announcementsFlag.entryId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_VALUE_2 = "announcementsFlag.value = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsFlag exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AnnouncementsFlagPersistenceImpl.class);
}