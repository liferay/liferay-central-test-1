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
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
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
import com.liferay.portal.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="JournalStructurePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructurePersistence
 * @see       JournalStructureUtil
 * @generated
 */
public class JournalStructurePersistenceImpl extends BasePersistenceImpl<JournalStructure>
	implements JournalStructurePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = JournalStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_STRUCTUREID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByStructureId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByStructureId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_S = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(JournalStructure journalStructure) {
		EntityCacheUtil.putResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey(),
			journalStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalStructure.getUuid(),
				new Long(journalStructure.getGroupId())
			}, journalStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(journalStructure.getGroupId()),
				
			journalStructure.getStructureId()
			}, journalStructure);
	}

	public void cacheResult(List<JournalStructure> journalStructures) {
		for (JournalStructure journalStructure : journalStructures) {
			if (EntityCacheUtil.getResult(
						JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
						JournalStructureImpl.class,
						journalStructure.getPrimaryKey(), this) == null) {
				cacheResult(journalStructure);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(JournalStructureImpl.class.getName());
		EntityCacheUtil.clearCache(JournalStructureImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(JournalStructure journalStructure) {
		EntityCacheUtil.removeResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalStructure.getUuid(),
				new Long(journalStructure.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(journalStructure.getGroupId()),
				
			journalStructure.getStructureId()
			});
	}

	public JournalStructure create(long id) {
		JournalStructure journalStructure = new JournalStructureImpl();

		journalStructure.setNew(true);
		journalStructure.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalStructure.setUuid(uuid);

		return journalStructure;
	}

	public JournalStructure remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public JournalStructure remove(long id)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructure journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
					new Long(id));

			if (journalStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
				}

				throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					id);
			}

			return remove(journalStructure);
		}
		catch (NoSuchStructureException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public JournalStructure remove(JournalStructure journalStructure)
		throws SystemException {
		for (ModelListener<JournalStructure> listener : listeners) {
			listener.onBeforeRemove(journalStructure);
		}

		journalStructure = removeImpl(journalStructure);

		for (ModelListener<JournalStructure> listener : listeners) {
			listener.onAfterRemove(journalStructure);
		}

		return journalStructure;
	}

	protected JournalStructure removeImpl(JournalStructure journalStructure)
		throws SystemException {
		journalStructure = toUnwrappedModel(journalStructure);

		Session session = null;

		try {
			session = openSession();

			if (journalStructure.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(JournalStructureImpl.class,
						journalStructure.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(journalStructure);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		JournalStructureModelImpl journalStructureModelImpl = (JournalStructureModelImpl)journalStructure;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalStructureModelImpl.getOriginalUuid(),
				new Long(journalStructureModelImpl.getOriginalGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				new Long(journalStructureModelImpl.getOriginalGroupId()),
				
			journalStructureModelImpl.getOriginalStructureId()
			});

		EntityCacheUtil.removeResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey());

		return journalStructure;
	}

	public JournalStructure updateImpl(
		com.liferay.portlet.journal.model.JournalStructure journalStructure,
		boolean merge) throws SystemException {
		journalStructure = toUnwrappedModel(journalStructure);

		boolean isNew = journalStructure.isNew();

		JournalStructureModelImpl journalStructureModelImpl = (JournalStructureModelImpl)journalStructure;

		if (Validator.isNull(journalStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, journalStructure, merge);

			journalStructure.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey(),
			journalStructure);

		if (!isNew &&
				(!Validator.equals(journalStructure.getUuid(),
					journalStructureModelImpl.getOriginalUuid()) ||
				(journalStructure.getGroupId() != journalStructureModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalStructureModelImpl.getOriginalUuid(),
					new Long(journalStructureModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(journalStructure.getUuid(),
					journalStructureModelImpl.getOriginalUuid()) ||
				(journalStructure.getGroupId() != journalStructureModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalStructure.getUuid(),
					new Long(journalStructure.getGroupId())
				}, journalStructure);
		}

		if (!isNew &&
				((journalStructure.getGroupId() != journalStructureModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalStructure.getStructureId(),
					journalStructureModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					new Long(journalStructureModelImpl.getOriginalGroupId()),
					
				journalStructureModelImpl.getOriginalStructureId()
				});
		}

		if (isNew ||
				((journalStructure.getGroupId() != journalStructureModelImpl.getOriginalGroupId()) ||
				!Validator.equals(journalStructure.getStructureId(),
					journalStructureModelImpl.getOriginalStructureId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
				new Object[] {
					new Long(journalStructure.getGroupId()),
					
				journalStructure.getStructureId()
				}, journalStructure);
		}

		return journalStructure;
	}

	protected JournalStructure toUnwrappedModel(
		JournalStructure journalStructure) {
		if (journalStructure instanceof JournalStructureImpl) {
			return journalStructure;
		}

		JournalStructureImpl journalStructureImpl = new JournalStructureImpl();

		journalStructureImpl.setNew(journalStructure.isNew());
		journalStructureImpl.setPrimaryKey(journalStructure.getPrimaryKey());

		journalStructureImpl.setUuid(journalStructure.getUuid());
		journalStructureImpl.setId(journalStructure.getId());
		journalStructureImpl.setGroupId(journalStructure.getGroupId());
		journalStructureImpl.setCompanyId(journalStructure.getCompanyId());
		journalStructureImpl.setUserId(journalStructure.getUserId());
		journalStructureImpl.setUserName(journalStructure.getUserName());
		journalStructureImpl.setCreateDate(journalStructure.getCreateDate());
		journalStructureImpl.setModifiedDate(journalStructure.getModifiedDate());
		journalStructureImpl.setStructureId(journalStructure.getStructureId());
		journalStructureImpl.setParentStructureId(journalStructure.getParentStructureId());
		journalStructureImpl.setName(journalStructure.getName());
		journalStructureImpl.setDescription(journalStructure.getDescription());
		journalStructureImpl.setXsd(journalStructure.getXsd());

		return journalStructureImpl;
	}

	public JournalStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalStructure findByPrimaryKey(long id)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByPrimaryKey(id);

		if (journalStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalStructure;
	}

	public JournalStructure fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public JournalStructure fetchByPrimaryKey(long id)
		throws SystemException {
		JournalStructure journalStructure = (JournalStructure)EntityCacheUtil.getResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
				JournalStructureImpl.class, id, this);

		if (journalStructure == null) {
			Session session = null;

			try {
				session = openSession();

				journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
						new Long(id));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (journalStructure != null) {
					cacheResult(journalStructure);
				}

				closeSession(session);
			}
		}

		return journalStructure;
	}

	public List<JournalStructure> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	public List<JournalStructure> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
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

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

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
					query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalStructure>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalStructure findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		int count = countByUuid(uuid);

		List<JournalStructure> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalStructure, uuid,
					orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByUuid_PrevAndNext(session, journalStructure, uuid,
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

	protected JournalStructure getByUuid_PrevAndNext(Session session,
		JournalStructure journalStructure, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public JournalStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUUID_G(uuid, groupId);

		if (journalStructure == null) {
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

			throw new NoSuchStructureException(msg.toString());
		}

		return journalStructure;
	}

	public JournalStructure fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	public JournalStructure fetchByUUID_G(String uuid, long groupId,
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

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

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

				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalStructure> list = q.list();

				result = list;

				JournalStructure journalStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalStructure = list.get(0);

					cacheResult(journalStructure);

					if ((journalStructure.getUuid() == null) ||
							!journalStructure.getUuid().equals(uuid) ||
							(journalStructure.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalStructure);
					}
				}

				return journalStructure;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, new ArrayList<JournalStructure>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalStructure)result;
			}
		}
	}

	public List<JournalStructure> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<JournalStructure> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalStructure>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalStructure findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		int count = countByGroupId(groupId);

		List<JournalStructure> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalStructure,
					groupId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByGroupId_PrevAndNext(session, journalStructure,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalStructure getByGroupId_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<JournalStructure> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<JournalStructure> filterFindByGroupId(long groupId, int start,
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

			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalStructure.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalStructure>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalStructure> findByStructureId(String structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_STRUCTUREID,
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

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				list = (List<JournalStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalStructure>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_STRUCTUREID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalStructure findByStructureId_First(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByStructureId_Last(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		int count = countByStructureId(structureId);

		List<JournalStructure> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByStructureId_PrevAndNext(long id,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, journalStructure,
					structureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByStructureId_PrevAndNext(session, journalStructure,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalStructure getByStructureId_PrevAndNext(Session session,
		JournalStructure journalStructure, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public JournalStructure findByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByG_S(groupId, structureId);

		if (journalStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return journalStructure;
	}

	public JournalStructure fetchByG_S(long groupId, String structureId)
		throws SystemException {
		return fetchByG_S(groupId, structureId, true);
	}

	public JournalStructure fetchByG_S(long groupId, String structureId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_S,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalStructure> list = q.list();

				result = list;

				JournalStructure journalStructure = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, list);
				}
				else {
					journalStructure = list.get(0);

					cacheResult(journalStructure);

					if ((journalStructure.getGroupId() != groupId) ||
							(journalStructure.getStructureId() == null) ||
							!journalStructure.getStructureId()
												 .equals(structureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
							finderArgs, journalStructure);
					}
				}

				return journalStructure;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, new ArrayList<JournalStructure>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalStructure)result;
			}
		}
	}

	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId) throws SystemException {
		return findByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId, int start, int end) throws SystemException {
		return findByG_P(groupId, parentStructureId, start, end, null);
	}

	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				parentStructureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(4 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				if (parentStructureId == null) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
				}
				else {
					if (parentStructureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
				}

				list = (List<JournalStructure>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalStructure>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public JournalStructure findByG_P_First(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		List<JournalStructure> list = findByG_P(groupId, parentStructureId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentStructureId=");
			msg.append(parentStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure findByG_P_Last(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		int count = countByG_P(groupId, parentStructureId);

		List<JournalStructure> list = findByG_P(groupId, parentStructureId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentStructureId=");
			msg.append(parentStructureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public JournalStructure[] findByG_P_PrevAndNext(long id, long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByG_P_PrevAndNext(session, journalStructure, groupId,
					parentStructureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByG_P_PrevAndNext(session, journalStructure, groupId,
					parentStructureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalStructure getByG_P_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		String parentStructureId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (parentStructureId != null) {
			qPos.add(parentStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId) throws SystemException {
		return filterFindByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentStructureId, start, end, null);
	}

	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentStructureId, start, end,
				orderByComparator);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalStructure.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentStructureId != null) {
				qPos.add(parentStructureId);
			}

			return (List<JournalStructure>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<JournalStructure> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<JournalStructure> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<JournalStructure> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_JOURNALSTRUCTURE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_JOURNALSTRUCTURE.concat(JournalStructureModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<JournalStructure>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUuid(String uuid) throws SystemException {
		for (JournalStructure journalStructure : findByUuid(uuid)) {
			remove(journalStructure);
		}
	}

	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByUUID_G(uuid, groupId);

		remove(journalStructure);
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalStructure journalStructure : findByGroupId(groupId)) {
			remove(journalStructure);
		}
	}

	public void removeByStructureId(String structureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByStructureId(structureId)) {
			remove(journalStructure);
		}
	}

	public void removeByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByG_S(groupId, structureId);

		remove(journalStructure);
	}

	public void removeByG_P(long groupId, String parentStructureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByG_P(groupId,
				parentStructureId)) {
			remove(journalStructure);
		}
	}

	public void removeAll() throws SystemException {
		for (JournalStructure journalStructure : findAll()) {
			remove(journalStructure);
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

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

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

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

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

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

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

			query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalStructure.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

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

	public int countByStructureId(String structureId) throws SystemException {
		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_S,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

				query.append(_FINDER_COLUMN_G_S_GROUPID_2);

				if (structureId == null) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
				}
				else {
					if (structureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_S(long groupId, String structureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_S(groupId, structureId);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalStructure.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (structureId != null) {
				qPos.add(structureId);
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

	public int countByG_P(long groupId, String parentStructureId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(groupId), parentStructureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				if (parentStructureId == null) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
				}
				else {
					if (parentStructureId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int filterCountByG_P(long groupId, String parentStructureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentStructureId);
		}

		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
				}
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					JournalStructure.class.getName(), _FILTER_COLUMN_ID,
					_FILTER_COLUMN_USERID, groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentStructureId != null) {
				qPos.add(parentStructureId);
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

				Query q = session.createQuery(_SQL_COUNT_JOURNALSTRUCTURE);

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
						"value.object.listener.com.liferay.portlet.journal.model.JournalStructure")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalStructure>> listenersList = new ArrayList<ModelListener<JournalStructure>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalStructure>)Class.forName(
							listenerClassName).newInstance());
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
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALSTRUCTURE = "SELECT journalStructure FROM JournalStructure journalStructure";
	private static final String _SQL_SELECT_JOURNALSTRUCTURE_WHERE = "SELECT journalStructure FROM JournalStructure journalStructure WHERE ";
	private static final String _SQL_COUNT_JOURNALSTRUCTURE = "SELECT COUNT(journalStructure) FROM JournalStructure journalStructure";
	private static final String _SQL_COUNT_JOURNALSTRUCTURE_WHERE = "SELECT COUNT(journalStructure) FROM JournalStructure journalStructure WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalStructure.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalStructure.uuid IS NULL OR journalStructure.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalStructure.uuid IS NULL OR journalStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalStructure.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalStructure.groupId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1 = "journalStructure.structureId IS NULL";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "journalStructure.structureId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3 = "(journalStructure.structureId IS NULL OR journalStructure.structureId = ?)";
	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalStructure.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalStructure.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalStructure.structureId IS NULL OR journalStructure.structureId = ?)";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "journalStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1 = "journalStructure.parentStructureId IS NULL";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2 = "journalStructure.parentStructureId = ?";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3 = "(journalStructure.parentStructureId IS NULL OR journalStructure.parentStructureId = ?)";
	private static final String _FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE = "SELECT {journalStructure.*} FROM JournalStructure journalStructure WHERE ";
	private static final String _FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE = "SELECT COUNT(journalStructure.id) AS COUNT_VALUE FROM JournalStructure journalStructure WHERE ";
	private static final String _FILTER_COLUMN_ID = "journalStructure.id";
	private static final String _FILTER_COLUMN_USERID = "journalStructure.userId";
	private static final String _FILTER_ENTITY_ALIAS = "journalStructure";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalStructure exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(JournalStructurePersistenceImpl.class);
}