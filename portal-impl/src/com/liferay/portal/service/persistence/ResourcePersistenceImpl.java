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
import com.liferay.portal.NoSuchResourceException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.ResourceModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="ResourcePersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePersistence
 * @see       ResourceUtil
 * @generated
 */
public class ResourcePersistenceImpl extends BasePersistenceImpl<Resource>
	implements ResourcePersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ResourceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByCodeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CODEID = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByCodeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByC_P",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	public void cacheResult(Resource resource) {
		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] { new Long(resource.getCodeId()), resource.getPrimKey() },
			resource);
	}

	public void cacheResult(List<Resource> resources) {
		for (Resource resource : resources) {
			if (EntityCacheUtil.getResult(
						ResourceModelImpl.ENTITY_CACHE_ENABLED,
						ResourceImpl.class, resource.getPrimaryKey(), this) == null) {
				cacheResult(resource);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(ResourceImpl.class.getName());
		EntityCacheUtil.clearCache(ResourceImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(Resource resource) {
		EntityCacheUtil.removeResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] { new Long(resource.getCodeId()), resource.getPrimKey() });
	}

	public Resource create(long resourceId) {
		Resource resource = new ResourceImpl();

		resource.setNew(true);
		resource.setPrimaryKey(resourceId);

		return resource;
	}

	public Resource remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public Resource remove(long resourceId)
		throws NoSuchResourceException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Resource resource = (Resource)session.get(ResourceImpl.class,
					new Long(resourceId));

			if (resource == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
				}

				throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourceId);
			}

			return remove(resource);
		}
		catch (NoSuchResourceException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public Resource remove(Resource resource) throws SystemException {
		for (ModelListener<Resource> listener : listeners) {
			listener.onBeforeRemove(resource);
		}

		resource = removeImpl(resource);

		for (ModelListener<Resource> listener : listeners) {
			listener.onAfterRemove(resource);
		}

		return resource;
	}

	protected Resource removeImpl(Resource resource) throws SystemException {
		resource = toUnwrappedModel(resource);

		Session session = null;

		try {
			session = openSession();

			if (resource.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(ResourceImpl.class,
						resource.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(resource);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
			new Object[] {
				new Long(resourceModelImpl.getOriginalCodeId()),
				
			resourceModelImpl.getOriginalPrimKey()
			});

		EntityCacheUtil.removeResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey());

		return resource;
	}

	public Resource updateImpl(com.liferay.portal.model.Resource resource,
		boolean merge) throws SystemException {
		resource = toUnwrappedModel(resource);

		boolean isNew = resource.isNew();

		ResourceModelImpl resourceModelImpl = (ResourceModelImpl)resource;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resource, merge);

			resource.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
			ResourceImpl.class, resource.getPrimaryKey(), resource);

		if (!isNew &&
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resourceModelImpl.getOriginalCodeId()),
					
				resourceModelImpl.getOriginalPrimKey()
				});
		}

		if (isNew ||
				((resource.getCodeId() != resourceModelImpl.getOriginalCodeId()) ||
				!Validator.equals(resource.getPrimKey(),
					resourceModelImpl.getOriginalPrimKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
				new Object[] {
					new Long(resource.getCodeId()),
					
				resource.getPrimKey()
				}, resource);
		}

		return resource;
	}

	protected Resource toUnwrappedModel(Resource resource) {
		if (resource instanceof ResourceImpl) {
			return resource;
		}

		ResourceImpl resourceImpl = new ResourceImpl();

		resourceImpl.setNew(resource.isNew());
		resourceImpl.setPrimaryKey(resource.getPrimaryKey());

		resourceImpl.setResourceId(resource.getResourceId());
		resourceImpl.setCodeId(resource.getCodeId());
		resourceImpl.setPrimKey(resource.getPrimKey());

		return resourceImpl;
	}

	public Resource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Resource findByPrimaryKey(long resourceId)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByPrimaryKey(resourceId);

		if (resource == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + resourceId);
			}

			throw new NoSuchResourceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourceId);
		}

		return resource;
	}

	public Resource fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public Resource fetchByPrimaryKey(long resourceId)
		throws SystemException {
		Resource resource = (Resource)EntityCacheUtil.getResult(ResourceModelImpl.ENTITY_CACHE_ENABLED,
				ResourceImpl.class, resourceId, this);

		if (resource == null) {
			Session session = null;

			try {
				session = openSession();

				resource = (Resource)session.get(ResourceImpl.class,
						new Long(resourceId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resource != null) {
					cacheResult(resource);
				}

				closeSession(session);
			}
		}

		return resource;
	}

	public List<Resource> findByCodeId(long codeId) throws SystemException {
		return findByCodeId(codeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Resource> findByCodeId(long codeId, int start, int end)
		throws SystemException {
		return findByCodeId(codeId, start, end, null);
	}

	public List<Resource> findByCodeId(long codeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(codeId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODEID,
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

				query.append(_SQL_SELECT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_CODEID_CODEID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				list = (List<Resource>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Resource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CODEID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public Resource findByCodeId_First(long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		List<Resource> list = findByCodeId(codeId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Resource findByCodeId_Last(long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		int count = countByCodeId(codeId);

		List<Resource> list = findByCodeId(codeId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourceException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public Resource[] findByCodeId_PrevAndNext(long resourceId, long codeId,
		OrderByComparator orderByComparator)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByPrimaryKey(resourceId);

		Session session = null;

		try {
			session = openSession();

			Resource[] array = new ResourceImpl[3];

			array[0] = getByCodeId_PrevAndNext(session, resource, codeId,
					orderByComparator, true);

			array[1] = resource;

			array[2] = getByCodeId_PrevAndNext(session, resource, codeId,
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

	protected Resource getByCodeId_PrevAndNext(Session session,
		Resource resource, long codeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCE_WHERE);

		query.append(_FINDER_COLUMN_CODEID_CODEID_2);

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

		qPos.add(codeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resource);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Resource> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public Resource findByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = fetchByC_P(codeId, primKey);

		if (resource == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("codeId=");
			msg.append(codeId);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourceException(msg.toString());
		}

		return resource;
	}

	public Resource fetchByC_P(long codeId, String primKey)
		throws SystemException {
		return fetchByC_P(codeId, primKey, true);
	}

	public Resource fetchByC_P(long codeId, String primKey,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId), primKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_C_P_CODEID_2);

				if (primKey == null) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
				}

				List<Resource> list = q.list();

				result = list;

				Resource resource = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, list);
				}
				else {
					resource = list.get(0);

					cacheResult(resource);

					if ((resource.getCodeId() != codeId) ||
							(resource.getPrimKey() == null) ||
							!resource.getPrimKey().equals(primKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
							finderArgs, resource);
					}
				}

				return resource;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_P,
						finderArgs, new ArrayList<Resource>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (Resource)result;
			}
		}
	}

	public List<Resource> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<Resource> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	public List<Resource> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<Resource> list = (List<Resource>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_RESOURCE);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				sql = _SQL_SELECT_RESOURCE;

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<Resource>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<Resource>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCodeId(long codeId) throws SystemException {
		for (Resource resource : findByCodeId(codeId)) {
			remove(resource);
		}
	}

	public void removeByC_P(long codeId, String primKey)
		throws NoSuchResourceException, SystemException {
		Resource resource = findByC_P(codeId, primKey);

		remove(resource);
	}

	public void removeAll() throws SystemException {
		for (Resource resource : findAll()) {
			remove(resource);
		}
	}

	public int countByCodeId(long codeId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_CODEID_CODEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CODEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_P(long codeId, String primKey)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(codeId), primKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_RESOURCE_WHERE);

				query.append(_FINDER_COLUMN_C_P_CODEID_2);

				if (primKey == null) {
					query.append(_FINDER_COLUMN_C_P_PRIMKEY_1);
				}
				else {
					if (primKey.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_P_PRIMKEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(codeId);

				if (primKey != null) {
					qPos.add(primKey);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_P, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_RESOURCE);

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
						"value.object.listener.com.liferay.portal.model.Resource")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Resource>> listenersList = new ArrayList<ModelListener<Resource>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Resource>)Class.forName(
							listenerClassName).newInstance());
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
	private static final String _SQL_SELECT_RESOURCE = "SELECT resource FROM Resource resource";
	private static final String _SQL_SELECT_RESOURCE_WHERE = "SELECT resource FROM Resource resource WHERE ";
	private static final String _SQL_COUNT_RESOURCE = "SELECT COUNT(resource) FROM Resource resource";
	private static final String _SQL_COUNT_RESOURCE_WHERE = "SELECT COUNT(resource) FROM Resource resource WHERE ";
	private static final String _FINDER_COLUMN_CODEID_CODEID_2 = "resource.codeId = ?";
	private static final String _FINDER_COLUMN_C_P_CODEID_2 = "resource.codeId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_1 = "resource.primKey IS NULL";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_2 = "resource.primKey = ?";
	private static final String _FINDER_COLUMN_C_P_PRIMKEY_3 = "(resource.primKey IS NULL OR resource.primKey = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resource.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Resource exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Resource exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ResourcePersistenceImpl.class);
}