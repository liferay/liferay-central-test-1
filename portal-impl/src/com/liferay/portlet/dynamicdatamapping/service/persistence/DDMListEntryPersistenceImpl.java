/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException;
import com.liferay.portlet.dynamicdatamapping.model.DDMListEntry;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMListEntryImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMListEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m list entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMListEntryPersistence
 * @see DDMListEntryUtil
 * @generated
 */
public class DDMListEntryPersistenceImpl extends BasePersistenceImpl<DDMListEntry>
	implements DDMListEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMListEntryUtil} to access the d d m list entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMListEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUuid", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_LISTID = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByListId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_LISTID = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByListId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the d d m list entry in the entity cache if it is enabled.
	 *
	 * @param ddmListEntry the d d m list entry to cache
	 */
	public void cacheResult(DDMListEntry ddmListEntry) {
		EntityCacheUtil.putResult(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryImpl.class, ddmListEntry.getPrimaryKey(), ddmListEntry);

		ddmListEntry.resetOriginalValues();
	}

	/**
	 * Caches the d d m list entries in the entity cache if it is enabled.
	 *
	 * @param ddmListEntries the d d m list entries to cache
	 */
	public void cacheResult(List<DDMListEntry> ddmListEntries) {
		for (DDMListEntry ddmListEntry : ddmListEntries) {
			if (EntityCacheUtil.getResult(
						DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
						DDMListEntryImpl.class, ddmListEntry.getPrimaryKey(),
						this) == null) {
				cacheResult(ddmListEntry);
			}
		}
	}

	/**
	 * Clears the cache for all d d m list entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMListEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMListEntryImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d d m list entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(DDMListEntry ddmListEntry) {
		EntityCacheUtil.removeResult(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryImpl.class, ddmListEntry.getPrimaryKey());
	}

	/**
	 * Creates a new d d m list entry with the primary key. Does not add the d d m list entry to the database.
	 *
	 * @param listEntryId the primary key for the new d d m list entry
	 * @return the new d d m list entry
	 */
	public DDMListEntry create(long listEntryId) {
		DDMListEntry ddmListEntry = new DDMListEntryImpl();

		ddmListEntry.setNew(true);
		ddmListEntry.setPrimaryKey(listEntryId);

		String uuid = PortalUUIDUtil.generate();

		ddmListEntry.setUuid(uuid);

		return ddmListEntry;
	}

	/**
	 * Removes the d d m list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m list entry to remove
	 * @return the d d m list entry that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d m list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listEntryId the primary key of the d d m list entry to remove
	 * @return the d d m list entry that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry remove(long listEntryId)
		throws NoSuchListEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMListEntry ddmListEntry = (DDMListEntry)session.get(DDMListEntryImpl.class,
					Long.valueOf(listEntryId));

			if (ddmListEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + listEntryId);
				}

				throw new NoSuchListEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					listEntryId);
			}

			return ddmListEntryPersistence.remove(ddmListEntry);
		}
		catch (NoSuchListEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the d d m list entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmListEntry the d d m list entry to remove
	 * @return the d d m list entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry remove(DDMListEntry ddmListEntry)
		throws SystemException {
		return super.remove(ddmListEntry);
	}

	protected DDMListEntry removeImpl(DDMListEntry ddmListEntry)
		throws SystemException {
		ddmListEntry = toUnwrappedModel(ddmListEntry);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddmListEntry);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryImpl.class, ddmListEntry.getPrimaryKey());

		return ddmListEntry;
	}

	public DDMListEntry updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry,
		boolean merge) throws SystemException {
		ddmListEntry = toUnwrappedModel(ddmListEntry);

		if (Validator.isNull(ddmListEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmListEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddmListEntry, merge);

			ddmListEntry.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
			DDMListEntryImpl.class, ddmListEntry.getPrimaryKey(), ddmListEntry);

		return ddmListEntry;
	}

	protected DDMListEntry toUnwrappedModel(DDMListEntry ddmListEntry) {
		if (ddmListEntry instanceof DDMListEntryImpl) {
			return ddmListEntry;
		}

		DDMListEntryImpl ddmListEntryImpl = new DDMListEntryImpl();

		ddmListEntryImpl.setNew(ddmListEntry.isNew());
		ddmListEntryImpl.setPrimaryKey(ddmListEntry.getPrimaryKey());

		ddmListEntryImpl.setUuid(ddmListEntry.getUuid());
		ddmListEntryImpl.setListEntryId(ddmListEntry.getListEntryId());
		ddmListEntryImpl.setListId(ddmListEntry.getListId());
		ddmListEntryImpl.setClassPK(ddmListEntry.getClassPK());

		return ddmListEntryImpl;
	}

	/**
	 * Finds the d d m list entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m list entry to find
	 * @return the d d m list entry
	 * @throws com.liferay.portal.NoSuchModelException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m list entry with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException} if it could not be found.
	 *
	 * @param listEntryId the primary key of the d d m list entry to find
	 * @return the d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByPrimaryKey(long listEntryId)
		throws NoSuchListEntryException, SystemException {
		DDMListEntry ddmListEntry = fetchByPrimaryKey(listEntryId);

		if (ddmListEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + listEntryId);
			}

			throw new NoSuchListEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				listEntryId);
		}

		return ddmListEntry;
	}

	/**
	 * Finds the d d m list entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m list entry to find
	 * @return the d d m list entry, or <code>null</code> if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the d d m list entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listEntryId the primary key of the d d m list entry to find
	 * @return the d d m list entry, or <code>null</code> if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry fetchByPrimaryKey(long listEntryId)
		throws SystemException {
		DDMListEntry ddmListEntry = (DDMListEntry)EntityCacheUtil.getResult(DDMListEntryModelImpl.ENTITY_CACHE_ENABLED,
				DDMListEntryImpl.class, listEntryId, this);

		if (ddmListEntry == null) {
			Session session = null;

			try {
				session = openSession();

				ddmListEntry = (DDMListEntry)session.get(DDMListEntryImpl.class,
						Long.valueOf(listEntryId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (ddmListEntry != null) {
					cacheResult(ddmListEntry);
				}

				closeSession(session);
			}
		}

		return ddmListEntry;
	}

	/**
	 * Finds all the d d m list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @return the range of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				uuid,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMListEntry> list = (List<DDMListEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMLISTENTRY_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DDMListEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d m list entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a matching d d m list entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		List<DDMListEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d m list entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a matching d d m list entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		int count = countByUuid(uuid);

		List<DDMListEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d m list entries before and after the current d d m list entry in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listEntryId the primary key of the current d d m list entry
	 * @param uuid the uuid to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry[] findByUuid_PrevAndNext(long listEntryId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		DDMListEntry ddmListEntry = findByPrimaryKey(listEntryId);

		Session session = null;

		try {
			session = openSession();

			DDMListEntry[] array = new DDMListEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmListEntry, uuid,
					orderByComparator, true);

			array[1] = ddmListEntry;

			array[2] = getByUuid_PrevAndNext(session, ddmListEntry, uuid,
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

	protected DDMListEntry getByUuid_PrevAndNext(Session session,
		DDMListEntry ddmListEntry, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMLISTENTRY_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmListEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMListEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d d m list entries where listId = &#63;.
	 *
	 * @param listId the list ID to search with
	 * @return the matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByListId(long listId)
		throws SystemException {
		return findByListId(listId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m list entries where listId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listId the list ID to search with
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @return the range of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByListId(long listId, int start, int end)
		throws SystemException {
		return findByListId(listId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m list entries where listId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listId the list ID to search with
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findByListId(long listId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				listId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMListEntry> list = (List<DDMListEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_LISTID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMLISTENTRY_WHERE);

			query.append(_FINDER_COLUMN_LISTID_LISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(listId);

				list = (List<DDMListEntry>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_LISTID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_LISTID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first d d m list entry in the ordered set where listId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listId the list ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a matching d d m list entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByListId_First(long listId,
		OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		List<DDMListEntry> list = findByListId(listId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("listId=");
			msg.append(listId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last d d m list entry in the ordered set where listId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listId the list ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a matching d d m list entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry findByListId_Last(long listId,
		OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		int count = countByListId(listId);

		List<DDMListEntry> list = findByListId(listId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("listId=");
			msg.append(listId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchListEntryException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the d d m list entries before and after the current d d m list entry in the ordered set where listId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param listEntryId the primary key of the current d d m list entry
	 * @param listId the list ID to search with
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m list entry
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchListEntryException if a d d m list entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMListEntry[] findByListId_PrevAndNext(long listEntryId,
		long listId, OrderByComparator orderByComparator)
		throws NoSuchListEntryException, SystemException {
		DDMListEntry ddmListEntry = findByPrimaryKey(listEntryId);

		Session session = null;

		try {
			session = openSession();

			DDMListEntry[] array = new DDMListEntryImpl[3];

			array[0] = getByListId_PrevAndNext(session, ddmListEntry, listId,
					orderByComparator, true);

			array[1] = ddmListEntry;

			array[2] = getByListId_PrevAndNext(session, ddmListEntry, listId,
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

	protected DDMListEntry getByListId_PrevAndNext(Session session,
		DDMListEntry ddmListEntry, long listId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMLISTENTRY_WHERE);

		query.append(_FINDER_COLUMN_LISTID_LISTID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(listId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmListEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMListEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the d d m list entries.
	 *
	 * @return the d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the d d m list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @return the range of d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the d d m list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m list entries to return
	 * @param end the upper bound of the range of d d m list entries to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMListEntry> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMListEntry> list = (List<DDMListEntry>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMLISTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMLISTENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMListEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMListEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m list entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DDMListEntry ddmListEntry : findByUuid(uuid)) {
			ddmListEntryPersistence.remove(ddmListEntry);
		}
	}

	/**
	 * Removes all the d d m list entries where listId = &#63; from the database.
	 *
	 * @param listId the list ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByListId(long listId) throws SystemException {
		for (DDMListEntry ddmListEntry : findByListId(listId)) {
			ddmListEntryPersistence.remove(ddmListEntry);
		}
	}

	/**
	 * Removes all the d d m list entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMListEntry ddmListEntry : findAll()) {
			ddmListEntryPersistence.remove(ddmListEntry);
		}
	}

	/**
	 * Counts all the d d m list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid to search with
	 * @return the number of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMLISTENTRY_WHERE);

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

			Session session = null;

			try {
				session = openSession();

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

	/**
	 * Counts all the d d m list entries where listId = &#63;.
	 *
	 * @param listId the list ID to search with
	 * @return the number of matching d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByListId(long listId) throws SystemException {
		Object[] finderArgs = new Object[] { listId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LISTID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMLISTENTRY_WHERE);

			query.append(_FINDER_COLUMN_LISTID_LISTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(listId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LISTID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the d d m list entries.
	 *
	 * @return the number of d d m list entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMLISTENTRY);

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

	/**
	 * Initializes the d d m list entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMListEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMListEntry>> listenersList = new ArrayList<ModelListener<DDMListEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMListEntry>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DDMListEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMListPersistence.class)
	protected DDMListPersistence ddmListPersistence;
	@BeanReference(type = DDMListEntryPersistence.class)
	protected DDMListEntryPersistence ddmListEntryPersistence;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DDMLISTENTRY = "SELECT ddmListEntry FROM DDMListEntry ddmListEntry";
	private static final String _SQL_SELECT_DDMLISTENTRY_WHERE = "SELECT ddmListEntry FROM DDMListEntry ddmListEntry WHERE ";
	private static final String _SQL_COUNT_DDMLISTENTRY = "SELECT COUNT(ddmListEntry) FROM DDMListEntry ddmListEntry";
	private static final String _SQL_COUNT_DDMLISTENTRY_WHERE = "SELECT COUNT(ddmListEntry) FROM DDMListEntry ddmListEntry WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmListEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmListEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmListEntry.uuid IS NULL OR ddmListEntry.uuid = ?)";
	private static final String _FINDER_COLUMN_LISTID_LISTID_2 = "ddmListEntry.listId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmListEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMListEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMListEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMListEntryPersistenceImpl.class);
}