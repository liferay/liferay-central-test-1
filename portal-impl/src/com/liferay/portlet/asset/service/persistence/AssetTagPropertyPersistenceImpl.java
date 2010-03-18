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

package com.liferay.portlet.asset.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyImpl;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <a href="AssetTagPropertyPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyPersistence
 * @see       AssetTagPropertyUtil
 * @generated
 */
public class AssetTagPropertyPersistenceImpl extends BasePersistenceImpl<AssetTagProperty>
	implements AssetTagPropertyPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = AssetTagPropertyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_COMPANYID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTagId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByTagId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_TAGID = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByTagId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_OBC_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_K",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_K = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByT_K",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(AssetTagProperty assetTagProperty) {
		EntityCacheUtil.putResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey(),
			assetTagProperty);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
			new Object[] {
				new Long(assetTagProperty.getTagId()),
				
			assetTagProperty.getKey()
			}, assetTagProperty);
	}

	public void cacheResult(List<AssetTagProperty> assetTagProperties) {
		for (AssetTagProperty assetTagProperty : assetTagProperties) {
			if (EntityCacheUtil.getResult(
						AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
						AssetTagPropertyImpl.class,
						assetTagProperty.getPrimaryKey(), this) == null) {
				cacheResult(assetTagProperty);
			}
		}
	}

	public void clearCache() {
		CacheRegistry.clear(AssetTagPropertyImpl.class.getName());
		EntityCacheUtil.clearCache(AssetTagPropertyImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public AssetTagProperty create(long tagPropertyId) {
		AssetTagProperty assetTagProperty = new AssetTagPropertyImpl();

		assetTagProperty.setNew(true);
		assetTagProperty.setPrimaryKey(tagPropertyId);

		return assetTagProperty;
	}

	public AssetTagProperty remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public AssetTagProperty remove(long tagPropertyId)
		throws NoSuchTagPropertyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetTagProperty assetTagProperty = (AssetTagProperty)session.get(AssetTagPropertyImpl.class,
					new Long(tagPropertyId));

			if (assetTagProperty == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagPropertyId);
				}

				throw new NoSuchTagPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					tagPropertyId);
			}

			return remove(assetTagProperty);
		}
		catch (NoSuchTagPropertyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTagProperty remove(AssetTagProperty assetTagProperty)
		throws SystemException {
		for (ModelListener<AssetTagProperty> listener : listeners) {
			listener.onBeforeRemove(assetTagProperty);
		}

		assetTagProperty = removeImpl(assetTagProperty);

		for (ModelListener<AssetTagProperty> listener : listeners) {
			listener.onAfterRemove(assetTagProperty);
		}

		return assetTagProperty;
	}

	protected AssetTagProperty removeImpl(AssetTagProperty assetTagProperty)
		throws SystemException {
		assetTagProperty = toUnwrappedModel(assetTagProperty);

		Session session = null;

		try {
			session = openSession();

			if (assetTagProperty.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(AssetTagPropertyImpl.class,
						assetTagProperty.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(assetTagProperty);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		AssetTagPropertyModelImpl assetTagPropertyModelImpl = (AssetTagPropertyModelImpl)assetTagProperty;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_K,
			new Object[] {
				new Long(assetTagPropertyModelImpl.getOriginalTagId()),
				
			assetTagPropertyModelImpl.getOriginalKey()
			});

		EntityCacheUtil.removeResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey());

		return assetTagProperty;
	}

	public AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge) throws SystemException {
		assetTagProperty = toUnwrappedModel(assetTagProperty);

		boolean isNew = assetTagProperty.isNew();

		AssetTagPropertyModelImpl assetTagPropertyModelImpl = (AssetTagPropertyModelImpl)assetTagProperty;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, assetTagProperty, merge);

			assetTagProperty.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
			AssetTagPropertyImpl.class, assetTagProperty.getPrimaryKey(),
			assetTagProperty);

		if (!isNew &&
				((assetTagProperty.getTagId() != assetTagPropertyModelImpl.getOriginalTagId()) ||
				!Validator.equals(assetTagProperty.getKey(),
					assetTagPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_K,
				new Object[] {
					new Long(assetTagPropertyModelImpl.getOriginalTagId()),
					
				assetTagPropertyModelImpl.getOriginalKey()
				});
		}

		if (isNew ||
				((assetTagProperty.getTagId() != assetTagPropertyModelImpl.getOriginalTagId()) ||
				!Validator.equals(assetTagProperty.getKey(),
					assetTagPropertyModelImpl.getOriginalKey()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
				new Object[] {
					new Long(assetTagProperty.getTagId()),
					
				assetTagProperty.getKey()
				}, assetTagProperty);
		}

		return assetTagProperty;
	}

	protected AssetTagProperty toUnwrappedModel(
		AssetTagProperty assetTagProperty) {
		if (assetTagProperty instanceof AssetTagPropertyImpl) {
			return assetTagProperty;
		}

		AssetTagPropertyImpl assetTagPropertyImpl = new AssetTagPropertyImpl();

		assetTagPropertyImpl.setNew(assetTagProperty.isNew());
		assetTagPropertyImpl.setPrimaryKey(assetTagProperty.getPrimaryKey());

		assetTagPropertyImpl.setTagPropertyId(assetTagProperty.getTagPropertyId());
		assetTagPropertyImpl.setCompanyId(assetTagProperty.getCompanyId());
		assetTagPropertyImpl.setUserId(assetTagProperty.getUserId());
		assetTagPropertyImpl.setUserName(assetTagProperty.getUserName());
		assetTagPropertyImpl.setCreateDate(assetTagProperty.getCreateDate());
		assetTagPropertyImpl.setModifiedDate(assetTagProperty.getModifiedDate());
		assetTagPropertyImpl.setTagId(assetTagProperty.getTagId());
		assetTagPropertyImpl.setKey(assetTagProperty.getKey());
		assetTagPropertyImpl.setValue(assetTagProperty.getValue());

		return assetTagPropertyImpl;
	}

	public AssetTagProperty findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetTagProperty findByPrimaryKey(long tagPropertyId)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByPrimaryKey(tagPropertyId);

		if (assetTagProperty == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + tagPropertyId);
			}

			throw new NoSuchTagPropertyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				tagPropertyId);
		}

		return assetTagProperty;
	}

	public AssetTagProperty fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public AssetTagProperty fetchByPrimaryKey(long tagPropertyId)
		throws SystemException {
		AssetTagProperty assetTagProperty = (AssetTagProperty)EntityCacheUtil.getResult(AssetTagPropertyModelImpl.ENTITY_CACHE_ENABLED,
				AssetTagPropertyImpl.class, tagPropertyId, this);

		if (assetTagProperty == null) {
			Session session = null;

			try {
				session = openSession();

				assetTagProperty = (AssetTagProperty)session.get(AssetTagPropertyImpl.class,
						new Long(tagPropertyId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (assetTagProperty != null) {
					cacheResult(assetTagProperty);
				}

				closeSession(session);
			}
		}

		return assetTagProperty;
	}

	public List<AssetTagProperty> findByCompanyId(long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId) };

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTagProperty> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	public List<AssetTagProperty> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
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

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AssetTagProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_COMPANYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTagProperty findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		List<AssetTagProperty> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		int count = countByCompanyId(companyId);

		List<AssetTagProperty> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty[] findByCompanyId_PrevAndNext(long tagPropertyId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		int count = countByCompanyId(companyId);

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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetTagProperty);

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = (AssetTagProperty)objArray[0];
			array[1] = (AssetTagProperty)objArray[1];
			array[2] = (AssetTagProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTagProperty> findByTagId(long tagId)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId) };

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TAGID,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_TAGID_TAGID_2);

				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TAGID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTagProperty> findByTagId(long tagId, int start, int end)
		throws SystemException {
		return findByTagId(tagId, start, end, null);
	}

	public List<AssetTagProperty> findByTagId(long tagId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(tagId),
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_TAGID,
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

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_TAGID_TAGID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				list = (List<AssetTagProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_TAGID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTagProperty findByTagId_First(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		List<AssetTagProperty> list = findByTagId(tagId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty findByTagId_Last(long tagId,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		int count = countByTagId(tagId);

		List<AssetTagProperty> list = findByTagId(tagId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty[] findByTagId_PrevAndNext(long tagPropertyId,
		long tagId, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		int count = countByTagId(tagId);

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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_TAGID_TAGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(tagId);

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetTagProperty);

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = (AssetTagProperty)objArray[0];
			array[1] = (AssetTagProperty)objArray[1];
			array[2] = (AssetTagProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTagProperty> findByC_K(long companyId, String key)
		throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_K,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = q.list();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_K, finderArgs,
					list);

				closeSession(session);
			}
		}

		return list;
	}

	public List<AssetTagProperty> findByC_K(long companyId, String key,
		int start, int end) throws SystemException {
		return findByC_K(companyId, key, start, end, null);
	}

	public List<AssetTagProperty> findByC_K(long companyId, String key,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				new Long(companyId),
				
				key,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_OBC_C_K,
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

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
				}

				list = (List<AssetTagProperty>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_OBC_C_K,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public AssetTagProperty findByC_K_First(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		List<AssetTagProperty> list = findByC_K(companyId, key, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty findByC_K_Last(long companyId, String key,
		OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		int count = countByC_K(companyId, key);

		List<AssetTagProperty> list = findByC_K(companyId, key, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchTagPropertyException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public AssetTagProperty[] findByC_K_PrevAndNext(long tagPropertyId,
		long companyId, String key, OrderByComparator orderByComparator)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByPrimaryKey(tagPropertyId);

		int count = countByC_K(companyId, key);

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

			query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

			query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

			if (key == null) {
				query.append(_FINDER_COLUMN_C_K_KEY_1);
			}
			else {
				if (key.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_K_KEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_K_KEY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			if (key != null) {
				qPos.add(key);
			}

			Object[] objArray = QueryUtil.getPrevAndNext(q, count,
					orderByComparator, assetTagProperty);

			AssetTagProperty[] array = new AssetTagPropertyImpl[3];

			array[0] = (AssetTagProperty)objArray[0];
			array[1] = (AssetTagProperty)objArray[1];
			array[2] = (AssetTagProperty)objArray[2];

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTagProperty findByT_K(long tagId, String key)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = fetchByT_K(tagId, key);

		if (assetTagProperty == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tagId=");
			msg.append(tagId);

			msg.append(", key=");
			msg.append(key);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchTagPropertyException(msg.toString());
		}

		return assetTagProperty;
	}

	public AssetTagProperty fetchByT_K(long tagId, String key)
		throws SystemException {
		return fetchByT_K(tagId, key, true);
	}

	public AssetTagProperty fetchByT_K(long tagId, String key,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId), key };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_K,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_T_K_TAGID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_T_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_K_KEY_2);
					}
				}

				query.append(AssetTagPropertyModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				if (key != null) {
					qPos.add(key);
				}

				List<AssetTagProperty> list = q.list();

				result = list;

				AssetTagProperty assetTagProperty = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
						finderArgs, list);
				}
				else {
					assetTagProperty = list.get(0);

					cacheResult(assetTagProperty);

					if ((assetTagProperty.getTagId() != tagId) ||
							(assetTagProperty.getKey() == null) ||
							!assetTagProperty.getKey().equals(key)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
							finderArgs, assetTagProperty);
					}
				}

				return assetTagProperty;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_K,
						finderArgs, new ArrayList<AssetTagProperty>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (AssetTagProperty)result;
			}
		}
	}

	public List<AssetTagProperty> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<AssetTagProperty> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<AssetTagProperty> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<AssetTagProperty> list = (List<AssetTagProperty>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_ASSETTAGPROPERTY);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}

				else {
					sql = _SQL_SELECT_ASSETTAGPROPERTY.concat(AssetTagPropertyModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetTagProperty>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<AssetTagProperty>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetTagProperty assetTagProperty : findByCompanyId(companyId)) {
			remove(assetTagProperty);
		}
	}

	public void removeByTagId(long tagId) throws SystemException {
		for (AssetTagProperty assetTagProperty : findByTagId(tagId)) {
			remove(assetTagProperty);
		}
	}

	public void removeByC_K(long companyId, String key)
		throws SystemException {
		for (AssetTagProperty assetTagProperty : findByC_K(companyId, key)) {
			remove(assetTagProperty);
		}
	}

	public void removeByT_K(long tagId, String key)
		throws NoSuchTagPropertyException, SystemException {
		AssetTagProperty assetTagProperty = findByT_K(tagId, key);

		remove(assetTagProperty);
	}

	public void removeAll() throws SystemException {
		for (AssetTagProperty assetTagProperty : findAll()) {
			remove(assetTagProperty);
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

				query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

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

	public int countByTagId(long tagId) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TAGID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_TAGID_TAGID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TAGID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByC_K(long companyId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(companyId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_C_K_COMPANYID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_C_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_C_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_C_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_K, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByT_K(long tagId, String key) throws SystemException {
		Object[] finderArgs = new Object[] { new Long(tagId), key };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_K,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_ASSETTAGPROPERTY_WHERE);

				query.append(_FINDER_COLUMN_T_K_TAGID_2);

				if (key == null) {
					query.append(_FINDER_COLUMN_T_K_KEY_1);
				}
				else {
					if (key.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_T_K_KEY_3);
					}
					else {
						query.append(_FINDER_COLUMN_T_K_KEY_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tagId);

				if (key != null) {
					qPos.add(key);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_K, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_ASSETTAGPROPERTY);

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
						"value.object.listener.com.liferay.portlet.asset.model.AssetTagProperty")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetTagProperty>> listenersList = new ArrayList<ModelListener<AssetTagProperty>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetTagProperty>)Class.forName(
							listenerClassName).newInstance());
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_ASSETTAGPROPERTY = "SELECT assetTagProperty FROM AssetTagProperty assetTagProperty";
	private static final String _SQL_SELECT_ASSETTAGPROPERTY_WHERE = "SELECT assetTagProperty FROM AssetTagProperty assetTagProperty WHERE ";
	private static final String _SQL_COUNT_ASSETTAGPROPERTY = "SELECT COUNT(assetTagProperty) FROM AssetTagProperty assetTagProperty";
	private static final String _SQL_COUNT_ASSETTAGPROPERTY_WHERE = "SELECT COUNT(assetTagProperty) FROM AssetTagProperty assetTagProperty WHERE ";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetTagProperty.companyId = ?";
	private static final String _FINDER_COLUMN_TAGID_TAGID_2 = "assetTagProperty.tagId = ?";
	private static final String _FINDER_COLUMN_C_K_COMPANYID_2 = "assetTagProperty.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_K_KEY_1 = "assetTagProperty.key IS NULL";
	private static final String _FINDER_COLUMN_C_K_KEY_2 = "assetTagProperty.key = ?";
	private static final String _FINDER_COLUMN_C_K_KEY_3 = "(assetTagProperty.key IS NULL OR assetTagProperty.key = ?)";
	private static final String _FINDER_COLUMN_T_K_TAGID_2 = "assetTagProperty.tagId = ? AND ";
	private static final String _FINDER_COLUMN_T_K_KEY_1 = "assetTagProperty.key IS NULL";
	private static final String _FINDER_COLUMN_T_K_KEY_2 = "assetTagProperty.key = ?";
	private static final String _FINDER_COLUMN_T_K_KEY_3 = "(assetTagProperty.key IS NULL OR assetTagProperty.key = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetTagProperty.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetTagProperty exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetTagProperty exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(AssetTagPropertyPersistenceImpl.class);
}