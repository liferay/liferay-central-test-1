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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.NoSuchOrgGroupPermissionException;
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
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.model.impl.OrgGroupPermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="OrgGroupPermissionPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupPermissionPersistence
 * @see       OrgGroupPermissionUtil
 * @generated
 */
public class OrgGroupPermissionPersistenceImpl extends BasePersistenceImpl<OrgGroupPermission>
	implements OrgGroupPermissionPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = OrgGroupPermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_PERMISSIONID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByPermissionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PERMISSIONID = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByPermissionId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(OrgGroupPermission orgGroupPermission) {
		EntityCacheUtil.putResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey(),
			orgGroupPermission);
	}

	public void cacheResult(List<OrgGroupPermission> orgGroupPermissions) {
		for (OrgGroupPermission orgGroupPermission : orgGroupPermissions) {
			if (EntityCacheUtil.getResult(
						OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
						OrgGroupPermissionImpl.class,
						orgGroupPermission.getPrimaryKey(), this) == null) {
				cacheResult(orgGroupPermission);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(OrgGroupPermissionImpl.class.getName());
		EntityCacheUtil.clearCache(OrgGroupPermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(OrgGroupPermission orgGroupPermission) {
		EntityCacheUtil.removeResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey());
	}

	public OrgGroupPermission create(OrgGroupPermissionPK orgGroupPermissionPK) {
		OrgGroupPermission orgGroupPermission = new OrgGroupPermissionImpl();

		orgGroupPermission.setNew(true);
		orgGroupPermission.setPrimaryKey(orgGroupPermissionPK);

		return orgGroupPermission;
	}

	public OrgGroupPermission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove((OrgGroupPermissionPK)primaryKey);
	}

	public OrgGroupPermission remove(OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
					orgGroupPermissionPK);

			if (orgGroupPermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						orgGroupPermissionPK);
				}

				throw new NoSuchOrgGroupPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					orgGroupPermissionPK);
			}

			return remove(orgGroupPermission);
		}
		catch (NoSuchOrgGroupPermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgGroupPermission removeImpl(
		OrgGroupPermission orgGroupPermission) throws SystemException {
		orgGroupPermission = toUnwrappedModel(orgGroupPermission);

		Session session = null;

		try {
			session = openSession();

			if (orgGroupPermission.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(OrgGroupPermissionImpl.class,
						orgGroupPermission.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(orgGroupPermission);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey());

		return orgGroupPermission;
	}

	public OrgGroupPermission updateImpl(
		com.liferay.portal.model.OrgGroupPermission orgGroupPermission,
		boolean merge) throws SystemException {
		orgGroupPermission = toUnwrappedModel(orgGroupPermission);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, orgGroupPermission, merge);

			orgGroupPermission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
			OrgGroupPermissionImpl.class, orgGroupPermission.getPrimaryKey(),
			orgGroupPermission);

		return orgGroupPermission;
	}

	protected OrgGroupPermission toUnwrappedModel(
		OrgGroupPermission orgGroupPermission) {
		if (orgGroupPermission instanceof OrgGroupPermissionImpl) {
			return orgGroupPermission;
		}

		OrgGroupPermissionImpl orgGroupPermissionImpl = new OrgGroupPermissionImpl();

		orgGroupPermissionImpl.setNew(orgGroupPermission.isNew());
		orgGroupPermissionImpl.setPrimaryKey(orgGroupPermission.getPrimaryKey());

		orgGroupPermissionImpl.setOrganizationId(orgGroupPermission.getOrganizationId());
		orgGroupPermissionImpl.setGroupId(orgGroupPermission.getGroupId());
		orgGroupPermissionImpl.setPermissionId(orgGroupPermission.getPermissionId());

		return orgGroupPermissionImpl;
	}

	public OrgGroupPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey((OrgGroupPermissionPK)primaryKey);
	}

	public OrgGroupPermission findByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = fetchByPrimaryKey(orgGroupPermissionPK);

		if (orgGroupPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					orgGroupPermissionPK);
			}

			throw new NoSuchOrgGroupPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				orgGroupPermissionPK);
		}

		return orgGroupPermission;
	}

	public OrgGroupPermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey((OrgGroupPermissionPK)primaryKey);
	}

	public OrgGroupPermission fetchByPrimaryKey(
		OrgGroupPermissionPK orgGroupPermissionPK) throws SystemException {
		OrgGroupPermission orgGroupPermission = (OrgGroupPermission)EntityCacheUtil.getResult(OrgGroupPermissionModelImpl.ENTITY_CACHE_ENABLED,
				OrgGroupPermissionImpl.class, orgGroupPermissionPK, this);

		if (orgGroupPermission == null) {
			Session session = null;

			try {
				session = openSession();

				orgGroupPermission = (OrgGroupPermission)session.get(OrgGroupPermissionImpl.class,
						orgGroupPermissionPK);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (orgGroupPermission != null) {
					cacheResult(orgGroupPermission);
				}

				closeSession(session);
			}
		}

		return orgGroupPermission;
	}

	public List<OrgGroupPermission> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end) throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	public List<OrgGroupPermission> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(groupId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgGroupPermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public OrgGroupPermission findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByGroupId(groupId);

		List<OrgGroupPermission> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission[] findByGroupId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, orgGroupPermission,
					groupId, orderByComparator, true);

			array[1] = orgGroupPermission;

			array[2] = getByGroupId_PrevAndNext(session, orgGroupPermission,
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

	protected OrgGroupPermission getByGroupId_PrevAndNext(Session session,
		OrgGroupPermission orgGroupPermission, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(orgGroupPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId)
		throws SystemException {
		return findByPermissionId(permissionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end) throws SystemException {
		return findByPermissionId(permissionId, start, end, null);
	}

	public List<OrgGroupPermission> findByPermissionId(long permissionId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(permissionId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PERMISSIONID,
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
					query = new StringBundler(2);
				}

				query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

				query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				list = (List<OrgGroupPermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgGroupPermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PERMISSIONID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public OrgGroupPermission findByPermissionId_First(long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		List<OrgGroupPermission> list = findByPermissionId(permissionId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("permissionId=");
			msg.append(permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission findByPermissionId_Last(long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		int count = countByPermissionId(permissionId);

		List<OrgGroupPermission> list = findByPermissionId(permissionId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("permissionId=");
			msg.append(permissionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchOrgGroupPermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public OrgGroupPermission[] findByPermissionId_PrevAndNext(
		OrgGroupPermissionPK orgGroupPermissionPK, long permissionId,
		OrderByComparator orderByComparator)
		throws NoSuchOrgGroupPermissionException, SystemException {
		OrgGroupPermission orgGroupPermission = findByPrimaryKey(orgGroupPermissionPK);

		Session session = null;

		try {
			session = openSession();

			OrgGroupPermission[] array = new OrgGroupPermissionImpl[3];

			array[0] = getByPermissionId_PrevAndNext(session,
					orgGroupPermission, permissionId, orderByComparator, true);

			array[1] = orgGroupPermission;

			array[2] = getByPermissionId_PrevAndNext(session,
					orgGroupPermission, permissionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgGroupPermission getByPermissionId_PrevAndNext(
		Session session, OrgGroupPermission orgGroupPermission,
		long permissionId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ORGGROUPPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

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

		qPos.add(permissionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(orgGroupPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<OrgGroupPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<OrgGroupPermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<OrgGroupPermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<OrgGroupPermission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<OrgGroupPermission> list = (List<OrgGroupPermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ORGGROUPPERMISSION);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_ORGGROUPPERMISSION;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<OrgGroupPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<OrgGroupPermission>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<OrgGroupPermission>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByGroupId(long groupId) throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByGroupId(groupId)) {
			remove(orgGroupPermission);
		}
	}

	public void removeByPermissionId(long permissionId)
		throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findByPermissionId(
				permissionId)) {
			remove(orgGroupPermission);
		}
	}

	public void removeAll() throws SystemException {
		for (OrgGroupPermission orgGroupPermission : findAll()) {
			remove(orgGroupPermission);
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

				query.append(_SQL_COUNT_ORGGROUPPERMISSION_WHERE);

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

	public int countByPermissionId(long permissionId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(permissionId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PERMISSIONID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ORGGROUPPERMISSION_WHERE);

				query.append(_FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(permissionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PERMISSIONID,
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

				Query q = session.createQuery(_SQL_COUNT_ORGGROUPPERMISSION);

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
						"value.object.listener.com.liferay.portal.model.OrgGroupPermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<OrgGroupPermission>> listenersList = new ArrayList<ModelListener<OrgGroupPermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<OrgGroupPermission>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = ContactPersistence.class)
	protected ContactPersistence contactPersistence;
	@BeanReference(type = CountryPersistence.class)
	protected CountryPersistence countryPersistence;
	@BeanReference(type = EmailAddressPersistence.class)
	protected EmailAddressPersistence emailAddressPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetPrototypePersistence.class)
	protected LayoutSetPrototypePersistence layoutSetPrototypePersistence;
	@BeanReference(type = ListTypePersistence.class)
	protected ListTypePersistence listTypePersistence;
	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;
	@BeanReference(type = MembershipRequestPersistence.class)
	protected MembershipRequestPersistence membershipRequestPersistence;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	@BeanReference(type = OrgGroupPermissionPersistence.class)
	protected OrgGroupPermissionPersistence orgGroupPermissionPersistence;
	@BeanReference(type = OrgGroupRolePersistence.class)
	protected OrgGroupRolePersistence orgGroupRolePersistence;
	@BeanReference(type = OrgLaborPersistence.class)
	protected OrgLaborPersistence orgLaborPersistence;
	@BeanReference(type = PasswordPolicyPersistence.class)
	protected PasswordPolicyPersistence passwordPolicyPersistence;
	@BeanReference(type = PasswordPolicyRelPersistence.class)
	protected PasswordPolicyRelPersistence passwordPolicyRelPersistence;
	@BeanReference(type = PasswordTrackerPersistence.class)
	protected PasswordTrackerPersistence passwordTrackerPersistence;
	@BeanReference(type = PermissionPersistence.class)
	protected PermissionPersistence permissionPersistence;
	@BeanReference(type = PhonePersistence.class)
	protected PhonePersistence phonePersistence;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
	@BeanReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@BeanReference(type = PortletItemPersistence.class)
	protected PortletItemPersistence portletItemPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = RegionPersistence.class)
	protected RegionPersistence regionPersistence;
	@BeanReference(type = ReleasePersistence.class)
	protected ReleasePersistence releasePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = ResourceActionPersistence.class)
	protected ResourceActionPersistence resourceActionPersistence;
	@BeanReference(type = ResourceCodePersistence.class)
	protected ResourceCodePersistence resourceCodePersistence;
	@BeanReference(type = ResourcePermissionPersistence.class)
	protected ResourcePermissionPersistence resourcePermissionPersistence;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;
	@BeanReference(type = ShardPersistence.class)
	protected ShardPersistence shardPersistence;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = TicketPersistence.class)
	protected TicketPersistence ticketPersistence;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(type = UserGroupGroupRolePersistence.class)
	protected UserGroupGroupRolePersistence userGroupGroupRolePersistence;
	@BeanReference(type = UserGroupRolePersistence.class)
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(type = UserIdMapperPersistence.class)
	protected UserIdMapperPersistence userIdMapperPersistence;
	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;
	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_ORGGROUPPERMISSION = "SELECT orgGroupPermission FROM OrgGroupPermission orgGroupPermission";
	private static final String _SQL_SELECT_ORGGROUPPERMISSION_WHERE = "SELECT orgGroupPermission FROM OrgGroupPermission orgGroupPermission WHERE ";
	private static final String _SQL_COUNT_ORGGROUPPERMISSION = "SELECT COUNT(orgGroupPermission) FROM OrgGroupPermission orgGroupPermission";
	private static final String _SQL_COUNT_ORGGROUPPERMISSION_WHERE = "SELECT COUNT(orgGroupPermission) FROM OrgGroupPermission orgGroupPermission WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "orgGroupPermission.id.groupId = ?";
	private static final String _FINDER_COLUMN_PERMISSIONID_PERMISSIONID_2 = "orgGroupPermission.id.permissionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "orgGroupPermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OrgGroupPermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No OrgGroupPermission exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(OrgGroupPermissionPersistenceImpl.class);
}