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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <a href="SCFrameworkVersionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionPersistence
 * @see       SCFrameworkVersionUtil
 * @generated
 */
public class SCFrameworkVersionPersistenceImpl extends BasePersistenceImpl<SCFrameworkVersion>
	implements SCFrameworkVersionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCFrameworkVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_A",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCFrameworkVersion scFrameworkVersion) {
		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);
	}

	public void cacheResult(List<SCFrameworkVersion> scFrameworkVersions) {
		for (SCFrameworkVersion scFrameworkVersion : scFrameworkVersions) {
			if (EntityCacheUtil.getResult(
						SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
						SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKey(), this) == null) {
				cacheResult(scFrameworkVersion);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(SCFrameworkVersionImpl.class.getName());
		EntityCacheUtil.clearCache(SCFrameworkVersionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SCFrameworkVersion scFrameworkVersion) {
		EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());
	}

	public SCFrameworkVersion create(long frameworkVersionId) {
		SCFrameworkVersion scFrameworkVersion = new SCFrameworkVersionImpl();

		scFrameworkVersion.setNew(true);
		scFrameworkVersion.setPrimaryKey(frameworkVersionId);

		return scFrameworkVersion;
	}

	public SCFrameworkVersion remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SCFrameworkVersion remove(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
					new Long(frameworkVersionId));

			if (scFrameworkVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						frameworkVersionId);
				}

				throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					frameworkVersionId);
			}

			return remove(scFrameworkVersion);
		}
		catch (NoSuchFrameworkVersionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public SCFrameworkVersion remove(SCFrameworkVersion scFrameworkVersion)
		throws SystemException {
		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			listener.onBeforeRemove(scFrameworkVersion);
		}

		scFrameworkVersion = removeImpl(scFrameworkVersion);

		for (ModelListener<SCFrameworkVersion> listener : listeners) {
			listener.onAfterRemove(scFrameworkVersion);
		}

		return scFrameworkVersion;
	}

	protected SCFrameworkVersion removeImpl(
		SCFrameworkVersion scFrameworkVersion) throws SystemException {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		try {
			clearSCProductVersions.clear(scFrameworkVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (scFrameworkVersion.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCFrameworkVersionImpl.class,
						scFrameworkVersion.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scFrameworkVersion);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey());

		return scFrameworkVersion;
	}

	public SCFrameworkVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean merge) throws SystemException {
		scFrameworkVersion = toUnwrappedModel(scFrameworkVersion);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scFrameworkVersion, merge);

			scFrameworkVersion.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionImpl.class, scFrameworkVersion.getPrimaryKey(),
			scFrameworkVersion);

		return scFrameworkVersion;
	}

	protected SCFrameworkVersion toUnwrappedModel(
		SCFrameworkVersion scFrameworkVersion) {
		if (scFrameworkVersion instanceof SCFrameworkVersionImpl) {
			return scFrameworkVersion;
		}

		SCFrameworkVersionImpl scFrameworkVersionImpl = new SCFrameworkVersionImpl();

		scFrameworkVersionImpl.setNew(scFrameworkVersion.isNew());
		scFrameworkVersionImpl.setPrimaryKey(scFrameworkVersion.getPrimaryKey());

		scFrameworkVersionImpl.setFrameworkVersionId(scFrameworkVersion.getFrameworkVersionId());
		scFrameworkVersionImpl.setGroupId(scFrameworkVersion.getGroupId());
		scFrameworkVersionImpl.setCompanyId(scFrameworkVersion.getCompanyId());
		scFrameworkVersionImpl.setUserId(scFrameworkVersion.getUserId());
		scFrameworkVersionImpl.setUserName(scFrameworkVersion.getUserName());
		scFrameworkVersionImpl.setCreateDate(scFrameworkVersion.getCreateDate());
		scFrameworkVersionImpl.setModifiedDate(scFrameworkVersion.getModifiedDate());
		scFrameworkVersionImpl.setName(scFrameworkVersion.getName());
		scFrameworkVersionImpl.setUrl(scFrameworkVersion.getUrl());
		scFrameworkVersionImpl.setActive(scFrameworkVersion.isActive());
		scFrameworkVersionImpl.setPriority(scFrameworkVersion.getPriority());

		return scFrameworkVersionImpl;
	}

	public SCFrameworkVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCFrameworkVersion findByPrimaryKey(long frameworkVersionId)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = fetchByPrimaryKey(frameworkVersionId);

		if (scFrameworkVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					frameworkVersionId);
			}

			throw new NoSuchFrameworkVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				frameworkVersionId);
		}

		return scFrameworkVersion;
	}

	public SCFrameworkVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCFrameworkVersion fetchByPrimaryKey(long frameworkVersionId)
		throws SystemException {
		SCFrameworkVersion scFrameworkVersion = (SCFrameworkVersion)EntityCacheUtil.getResult(SCFrameworkVersionModelImpl.ENTITY_CACHE_ENABLED,
				SCFrameworkVersionImpl.class, frameworkVersionId, this);

		if (scFrameworkVersion == null) {
			Session session = null;

			try {
				session = openSession();

				scFrameworkVersion = (SCFrameworkVersion)session.get(SCFrameworkVersionImpl.class,
						new Long(frameworkVersionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scFrameworkVersion != null) {
					cacheResult(scFrameworkVersion);
				}

				closeSession(session);
			}
		}

		return scFrameworkVersion;
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<SCFrameworkVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCFrameworkVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCFrameworkVersion findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByGroupId(groupId);

		List<SCFrameworkVersion> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
					groupId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByGroupId_PrevAndNext(session, scFrameworkVersion,
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

	protected SCFrameworkVersion getByGroupId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

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

			query.append(WHERE_LIMIT_2);
		}

		else {
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SCFrameworkVersion> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	public List<SCFrameworkVersion> filterFindByGroupId(long groupId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					SCFrameworkVersion.class.getName(),
					_FILTER_COLUMN_FRAMEWORKVERSIONID, _FILTER_COLUMN_USERID,
					groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<SCFrameworkVersion> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
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

				query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCFrameworkVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCFrameworkVersion findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByCompanyId(companyId);

		List<SCFrameworkVersion> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
					companyId, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByCompanyId_PrevAndNext(session, scFrameworkVersion,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByCompanyId_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active)
		throws SystemException {
		return findByG_A(groupId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end) throws SystemException {
		return findByG_A(groupId, active, start, end, null);
	}

	public List<SCFrameworkVersion> findByG_A(long groupId, boolean active,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(active),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_A,
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

				query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

				query.append(_FINDER_COLUMN_G_A_GROUPID_2);

				query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

				list = (List<SCFrameworkVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCFrameworkVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_A, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCFrameworkVersion findByG_A_First(long groupId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		List<SCFrameworkVersion> list = findByG_A(groupId, active, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion findByG_A_Last(long groupId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		int count = countByG_A(groupId, active);

		List<SCFrameworkVersion> list = findByG_A(groupId, active, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", active=");
			msg.append(active);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFrameworkVersionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCFrameworkVersion[] findByG_A_PrevAndNext(long frameworkVersionId,
		long groupId, boolean active, OrderByComparator orderByComparator)
		throws NoSuchFrameworkVersionException, SystemException {
		SCFrameworkVersion scFrameworkVersion = findByPrimaryKey(frameworkVersionId);

		Session session = null;

		try {
			session = openSession();

			SCFrameworkVersion[] array = new SCFrameworkVersionImpl[3];

			array[0] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, true);

			array[1] = scFrameworkVersion;

			array[2] = getByG_A_PrevAndNext(session, scFrameworkVersion,
					groupId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCFrameworkVersion getByG_A_PrevAndNext(Session session,
		SCFrameworkVersion scFrameworkVersion, long groupId, boolean active,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

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
			query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(scFrameworkVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCFrameworkVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<SCFrameworkVersion> filterFindByG_A(long groupId, boolean active)
		throws SystemException {
		return filterFindByG_A(groupId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end) throws SystemException {
		return filterFindByG_A(groupId, active, start, end, null);
	}

	public List<SCFrameworkVersion> filterFindByG_A(long groupId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

			query.append(_FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					SCFrameworkVersion.class.getName(),
					_FILTER_COLUMN_FRAMEWORKVERSIONID, _FILTER_COLUMN_USERID,
					groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity(_FILTER_ENTITY_ALIAS, SCFrameworkVersionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

			return (List<SCFrameworkVersion>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<SCFrameworkVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCFrameworkVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCFrameworkVersion> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCFrameworkVersion> list = (List<SCFrameworkVersion>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SCFRAMEWORKVERSION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_SCFRAMEWORKVERSION.concat(SCFrameworkVersionModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCFrameworkVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCFrameworkVersion>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByGroupId(groupId)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByCompanyId(companyId)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeByG_A(long groupId, boolean active)
		throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findByG_A(groupId, active)) {
			remove(scFrameworkVersion);
		}
	}

	public void removeAll() throws SystemException {
		for (SCFrameworkVersion scFrameworkVersion : findAll()) {
			remove(scFrameworkVersion);
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

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

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
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(2);

			query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					SCFrameworkVersion.class.getName(),
					_FILTER_COLUMN_FRAMEWORKVERSIONID, _FILTER_COLUMN_USERID,
					groupId);

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

	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

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

	public int countByG_A(long groupId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId), Boolean.valueOf(active)
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

				query.append(_FINDER_COLUMN_G_A_GROUPID_2);

				query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(active);

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

	public int filterCountByG_A(long groupId, boolean active)
		throws SystemException {
		Session session = null;

		try {
			session = openSession();

			StringBundler query = new StringBundler(3);

			query.append(_FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ACTIVE_2);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
					SCFrameworkVersion.class.getName(),
					_FILTER_COLUMN_FRAMEWORKVERSIONID, _FILTER_COLUMN_USERID,
					groupId);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(active);

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

				Query q = session.createQuery(_SQL_COUNT_SCFRAMEWORKVERSION);

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

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) throws SystemException {
		return getSCProductVersions(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end) throws SystemException {
		return getSCProductVersions(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCProductVersions",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	public List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk), String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> list = (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETSCPRODUCTVERSIONS.concat(ORDER_BY_CLAUSE)
												   .concat(orderByComparator.getOrderBy());
				}

				else {
					sql = _SQL_GETSCPRODUCTVERSIONS.concat(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("SCProductVersion",
					com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.softwarecatalog.model.SCProductVersion>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<com.liferay.portlet.softwarecatalog.model.SCProductVersion>();
				}

				scProductVersionPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"getSCProductVersionsSize", new String[] { Long.class.getName() });

	public int getSCProductVersionsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(pk) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETSCPRODUCTVERSIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_SCPRODUCTVERSIONS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_SCPRODUCTVERSION = new FinderPath(com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl.ENTITY_CACHE_ENABLED,
			SCFrameworkVersionModelImpl.FINDER_CACHE_ENABLED_SCFRAMEWORKVERSI_SCPRODUCTVERS,
			SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME,
			"containsSCProductVersion",
			new String[] { Long.class.getName(), Long.class.getName() });

	public boolean containsSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(pk),
				
				new Long(scProductVersionPK)
			};

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsSCProductVersion.contains(pk,
							scProductVersionPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_SCPRODUCTVERSION,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	public boolean containsSCProductVersions(long pk) throws SystemException {
		if (getSCProductVersionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void addSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				addSCProductVersion.add(pk, scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void clearSCProductVersions(long pk) throws SystemException {
		try {
			clearSCProductVersions.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCProductVersion(long pk, long scProductVersionPK)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersionPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws SystemException {
		try {
			removeSCProductVersion.remove(pk, scProductVersion.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			for (long scProductVersionPK : scProductVersionPKs) {
				removeSCProductVersion.remove(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void removeSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				removeSCProductVersion.remove(pk,
					scProductVersion.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void setSCProductVersions(long pk, long[] scProductVersionPKs)
		throws SystemException {
		try {
			Set<Long> scProductVersionPKSet = SetUtil.fromArray(scProductVersionPKs);

			List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions =
				getSCProductVersions(pk);

			for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
				if (!scProductVersionPKSet.contains(
							scProductVersion.getPrimaryKey())) {
					removeSCProductVersion.remove(pk,
						scProductVersion.getPrimaryKey());
				}
				else {
					scProductVersionPKSet.remove(scProductVersion.getPrimaryKey());
				}
			}

			for (Long scProductVersionPK : scProductVersionPKSet) {
				addSCProductVersion.add(pk, scProductVersionPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void setSCProductVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws SystemException {
		try {
			long[] scProductVersionPKs = new long[scProductVersions.size()];

			for (int i = 0; i < scProductVersions.size(); i++) {
				com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion =
					scProductVersions.get(i);

				scProductVersionPKs[i] = scProductVersion.getPrimaryKey();
			}

			setSCProductVersions(pk, scProductVersionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(SCFrameworkVersionModelImpl.MAPPING_TABLE_SCFRAMEWORKVERSI_SCPRODUCTVERS_NAME);
		}
	}

	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCFrameworkVersion>> listenersList = new ArrayList<ModelListener<SCFrameworkVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCFrameworkVersion>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsSCProductVersion = new ContainsSCProductVersion(this);

		addSCProductVersion = new AddSCProductVersion(this);
		clearSCProductVersions = new ClearSCProductVersions(this);
		removeSCProductVersion = new RemoveSCProductVersion(this);
	}

	@BeanReference(type = SCLicensePersistence.class)
	protected SCLicensePersistence scLicensePersistence;
	@BeanReference(type = SCFrameworkVersionPersistence.class)
	protected SCFrameworkVersionPersistence scFrameworkVersionPersistence;
	@BeanReference(type = SCProductEntryPersistence.class)
	protected SCProductEntryPersistence scProductEntryPersistence;
	@BeanReference(type = SCProductScreenshotPersistence.class)
	protected SCProductScreenshotPersistence scProductScreenshotPersistence;
	@BeanReference(type = SCProductVersionPersistence.class)
	protected SCProductVersionPersistence scProductVersionPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected ContainsSCProductVersion containsSCProductVersion;
	protected AddSCProductVersion addSCProductVersion;
	protected ClearSCProductVersions clearSCProductVersions;
	protected RemoveSCProductVersion removeSCProductVersion;

	protected class ContainsSCProductVersion {
		protected ContainsSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			super();

			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSSCPRODUCTVERSION,
					new int[] { Types.BIGINT, Types.BIGINT }, RowMapper.COUNT);
		}

		protected boolean contains(long frameworkVersionId,
			long productVersionId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddSCProductVersion {
		protected AddSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO SCFrameworkVersi_SCProductVers (frameworkVersionId, productVersionId) VALUES (?, ?)",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void add(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (!_persistenceImpl.containsSCProductVersion.contains(
						frameworkVersionId, productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterAddAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterAddAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCFrameworkVersionPersistenceImpl _persistenceImpl;
	}

	protected class ClearSCProductVersions {
		protected ClearSCProductVersions(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?",
					new int[] { Types.BIGINT });
		}

		protected void clear(long frameworkVersionId) throws SystemException {
			ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
				scProductVersionPersistence.getListeners();

			List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions =
				null;

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				scProductVersions = getSCProductVersions(frameworkVersionId);

				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onBeforeRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onBeforeRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(frameworkVersionId) });

			if ((listeners.length > 0) ||
					(scProductVersionListeners.length > 0)) {
				for (com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion : scProductVersions) {
					for (ModelListener<SCFrameworkVersion> listener : listeners) {
						listener.onAfterRemoveAssociation(frameworkVersionId,
							com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
							scProductVersion.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
						listener.onAfterRemoveAssociation(scProductVersion.getPrimaryKey(),
							SCFrameworkVersion.class.getName(),
							frameworkVersionId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveSCProductVersion {
		protected RemoveSCProductVersion(
			SCFrameworkVersionPersistenceImpl persistenceImpl) {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?",
					new int[] { Types.BIGINT, Types.BIGINT });
			_persistenceImpl = persistenceImpl;
		}

		protected void remove(long frameworkVersionId, long productVersionId)
			throws SystemException {
			if (_persistenceImpl.containsSCProductVersion.contains(
						frameworkVersionId, productVersionId)) {
				ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion>[] scProductVersionListeners =
					scProductVersionPersistence.getListeners();

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onBeforeRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onBeforeRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(frameworkVersionId), new Long(productVersionId)
					});

				for (ModelListener<SCFrameworkVersion> listener : listeners) {
					listener.onAfterRemoveAssociation(frameworkVersionId,
						com.liferay.portlet.softwarecatalog.model.SCProductVersion.class.getName(),
						productVersionId);
				}

				for (ModelListener<com.liferay.portlet.softwarecatalog.model.SCProductVersion> listener : scProductVersionListeners) {
					listener.onAfterRemoveAssociation(productVersionId,
						SCFrameworkVersion.class.getName(), frameworkVersionId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
		private SCFrameworkVersionPersistenceImpl _persistenceImpl;
	}

	private static final String _SQL_SELECT_SCFRAMEWORKVERSION = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT scFrameworkVersion FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion";
	private static final String _SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(scFrameworkVersion) FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _SQL_GETSCPRODUCTVERSIONS = "SELECT {SCProductVersion.*} FROM SCProductVersion INNER JOIN SCFrameworkVersi_SCProductVers ON (SCFrameworkVersi_SCProductVers.productVersionId = SCProductVersion.productVersionId) WHERE (SCFrameworkVersi_SCProductVers.frameworkVersionId = ?)";
	private static final String _SQL_GETSCPRODUCTVERSIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ?";
	private static final String _SQL_CONTAINSSCPRODUCTVERSION = "SELECT COUNT(*) AS COUNT_VALUE FROM SCFrameworkVersi_SCProductVers WHERE frameworkVersionId = ? AND productVersionId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "scFrameworkVersion.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "scFrameworkVersion.companyId = ?";
	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "scFrameworkVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ACTIVE_2 = "scFrameworkVersion.active = ?";
	private static final String _FILTER_SQL_SELECT_SCFRAMEWORKVERSION_WHERE = "SELECT {scFrameworkVersion.*} FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_SQL_COUNT_SCFRAMEWORKVERSION_WHERE = "SELECT COUNT(scFrameworkVersion.frameworkVersionId) AS COUNT_VALUE FROM SCFrameworkVersion scFrameworkVersion WHERE ";
	private static final String _FILTER_COLUMN_FRAMEWORKVERSIONID = "scFrameworkVersion.frameworkVersionId";
	private static final String _FILTER_COLUMN_USERID = "scFrameworkVersion.userId";
	private static final String _FILTER_ENTITY_ALIAS = "scFrameworkVersion";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scFrameworkVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCFrameworkVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCFrameworkVersion exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SCFrameworkVersionPersistenceImpl.class);
}