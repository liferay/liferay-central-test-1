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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ImagePersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotImpl;
import com.liferay.portlet.softwarecatalog.model.impl.SCProductScreenshotModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCProductScreenshotPersistence
 * @see       SCProductScreenshotUtil
 * @generated
 */
public class SCProductScreenshotPersistenceImpl extends BasePersistenceImpl<SCProductScreenshot>
	implements SCProductScreenshotPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SCProductScreenshotImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByProductEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_PRODUCTENTRYID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByProductEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByThumbnailId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_THUMBNAILID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByThumbnailId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByFullImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_FULLIMAGEID = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByFullImageId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_P_P = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByP_P",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(SCProductScreenshot scProductScreenshot) {
		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] { new Long(scProductScreenshot.getThumbnailId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] { new Long(scProductScreenshot.getFullImageId()) },
			scProductScreenshot);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				new Long(scProductScreenshot.getProductEntryId()),
				new Integer(scProductScreenshot.getPriority())
			}, scProductScreenshot);
	}

	public void cacheResult(List<SCProductScreenshot> scProductScreenshots) {
		for (SCProductScreenshot scProductScreenshot : scProductScreenshots) {
			if (EntityCacheUtil.getResult(
						SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
						SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKey(), this) == null) {
				cacheResult(scProductScreenshot);
			}
		}
	}

	public void clearCache() {
		CacheRegistryUtil.clear(SCProductScreenshotImpl.class.getName());
		EntityCacheUtil.clearCache(SCProductScreenshotImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(SCProductScreenshot scProductScreenshot) {
		EntityCacheUtil.removeResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] { new Long(scProductScreenshot.getThumbnailId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] { new Long(scProductScreenshot.getFullImageId()) });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				new Long(scProductScreenshot.getProductEntryId()),
				new Integer(scProductScreenshot.getPriority())
			});
	}

	public SCProductScreenshot create(long productScreenshotId) {
		SCProductScreenshot scProductScreenshot = new SCProductScreenshotImpl();

		scProductScreenshot.setNew(true);
		scProductScreenshot.setPrimaryKey(productScreenshotId);

		return scProductScreenshot;
	}

	public SCProductScreenshot remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public SCProductScreenshot remove(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
					new Long(productScreenshotId));

			if (scProductScreenshot == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						productScreenshotId);
				}

				throw new NoSuchProductScreenshotException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					productScreenshotId);
			}

			return remove(scProductScreenshot);
		}
		catch (NoSuchProductScreenshotException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductScreenshot removeImpl(
		SCProductScreenshot scProductScreenshot) throws SystemException {
		scProductScreenshot = toUnwrappedModel(scProductScreenshot);

		Session session = null;

		try {
			session = openSession();

			if (scProductScreenshot.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SCProductScreenshotImpl.class,
						scProductScreenshot.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(scProductScreenshot);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SCProductScreenshotModelImpl scProductScreenshotModelImpl = (SCProductScreenshotModelImpl)scProductScreenshot;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalThumbnailId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalFullImageId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
			new Object[] {
				new Long(scProductScreenshotModelImpl.getOriginalProductEntryId()),
				new Integer(scProductScreenshotModelImpl.getOriginalPriority())
			});

		EntityCacheUtil.removeResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey());

		return scProductScreenshot;
	}

	public SCProductScreenshot updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCProductScreenshot scProductScreenshot,
		boolean merge) throws SystemException {
		scProductScreenshot = toUnwrappedModel(scProductScreenshot);

		boolean isNew = scProductScreenshot.isNew();

		SCProductScreenshotModelImpl scProductScreenshotModelImpl = (SCProductScreenshotModelImpl)scProductScreenshot;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, scProductScreenshot, merge);

			scProductScreenshot.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
			SCProductScreenshotImpl.class, scProductScreenshot.getPrimaryKey(),
			scProductScreenshot);

		if (!isNew &&
				(scProductScreenshot.getThumbnailId() != scProductScreenshotModelImpl.getOriginalThumbnailId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalThumbnailId())
				});
		}

		if (isNew ||
				(scProductScreenshot.getThumbnailId() != scProductScreenshotModelImpl.getOriginalThumbnailId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
				new Object[] { new Long(scProductScreenshot.getThumbnailId()) },
				scProductScreenshot);
		}

		if (!isNew &&
				(scProductScreenshot.getFullImageId() != scProductScreenshotModelImpl.getOriginalFullImageId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalFullImageId())
				});
		}

		if (isNew ||
				(scProductScreenshot.getFullImageId() != scProductScreenshotModelImpl.getOriginalFullImageId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
				new Object[] { new Long(scProductScreenshot.getFullImageId()) },
				scProductScreenshot);
		}

		if (!isNew &&
				((scProductScreenshot.getProductEntryId() != scProductScreenshotModelImpl.getOriginalProductEntryId()) ||
				(scProductScreenshot.getPriority() != scProductScreenshotModelImpl.getOriginalPriority()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_P_P,
				new Object[] {
					new Long(scProductScreenshotModelImpl.getOriginalProductEntryId()),
					new Integer(scProductScreenshotModelImpl.getOriginalPriority())
				});
		}

		if (isNew ||
				((scProductScreenshot.getProductEntryId() != scProductScreenshotModelImpl.getOriginalProductEntryId()) ||
				(scProductScreenshot.getPriority() != scProductScreenshotModelImpl.getOriginalPriority()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
				new Object[] {
					new Long(scProductScreenshot.getProductEntryId()),
					new Integer(scProductScreenshot.getPriority())
				}, scProductScreenshot);
		}

		return scProductScreenshot;
	}

	protected SCProductScreenshot toUnwrappedModel(
		SCProductScreenshot scProductScreenshot) {
		if (scProductScreenshot instanceof SCProductScreenshotImpl) {
			return scProductScreenshot;
		}

		SCProductScreenshotImpl scProductScreenshotImpl = new SCProductScreenshotImpl();

		scProductScreenshotImpl.setNew(scProductScreenshot.isNew());
		scProductScreenshotImpl.setPrimaryKey(scProductScreenshot.getPrimaryKey());

		scProductScreenshotImpl.setProductScreenshotId(scProductScreenshot.getProductScreenshotId());
		scProductScreenshotImpl.setCompanyId(scProductScreenshot.getCompanyId());
		scProductScreenshotImpl.setGroupId(scProductScreenshot.getGroupId());
		scProductScreenshotImpl.setProductEntryId(scProductScreenshot.getProductEntryId());
		scProductScreenshotImpl.setThumbnailId(scProductScreenshot.getThumbnailId());
		scProductScreenshotImpl.setFullImageId(scProductScreenshot.getFullImageId());
		scProductScreenshotImpl.setPriority(scProductScreenshot.getPriority());

		return scProductScreenshotImpl;
	}

	public SCProductScreenshot findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByPrimaryKey(productScreenshotId);

		if (scProductScreenshot == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					productScreenshotId);
			}

			throw new NoSuchProductScreenshotException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				productScreenshotId);
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId)
		throws SystemException {
		SCProductScreenshot scProductScreenshot = (SCProductScreenshot)EntityCacheUtil.getResult(SCProductScreenshotModelImpl.ENTITY_CACHE_ENABLED,
				SCProductScreenshotImpl.class, productScreenshotId, this);

		if (scProductScreenshot == null) {
			Session session = null;

			try {
				session = openSession();

				scProductScreenshot = (SCProductScreenshot)session.get(SCProductScreenshotImpl.class,
						new Long(productScreenshotId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (scProductScreenshot != null) {
					cacheResult(scProductScreenshot);
				}

				closeSession(session);
			}
		}

		return scProductScreenshot;
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId)
		throws SystemException {
		return findByProductEntryId(productEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end) throws SystemException {
		return findByProductEntryId(productEntryId, start, end, null);
	}

	public List<SCProductScreenshot> findByProductEntryId(long productEntryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				productEntryId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
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

				query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				else {
					query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				list = (List<SCProductScreenshot>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductScreenshot>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_PRODUCTENTRYID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		int count = countByProductEntryId(productEntryId);

		List<SCProductScreenshot> list = findByProductEntryId(productEntryId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchProductScreenshotException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByPrimaryKey(productScreenshotId);

		Session session = null;

		try {
			session = openSession();

			SCProductScreenshot[] array = new SCProductScreenshotImpl[3];

			array[0] = getByProductEntryId_PrevAndNext(session,
					scProductScreenshot, productEntryId, orderByComparator, true);

			array[1] = scProductScreenshot;

			array[2] = getByProductEntryId_PrevAndNext(session,
					scProductScreenshot, productEntryId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SCProductScreenshot getByProductEntryId_PrevAndNext(
		Session session, SCProductScreenshot scProductScreenshot,
		long productEntryId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

		query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

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
			query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(productEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(scProductScreenshot);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SCProductScreenshot> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByThumbnailId(thumbnailId);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("thumbnailId=");
			msg.append(thumbnailId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByThumbnailId(long thumbnailId)
		throws SystemException {
		return fetchByThumbnailId(thumbnailId, true);
	}

	public SCProductScreenshot fetchByThumbnailId(long thumbnailId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { thumbnailId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2);

				query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getThumbnailId() != thumbnailId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_THUMBNAILID,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
			}
		}
	}

	public SCProductScreenshot findByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByFullImageId(fullImageId);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fullImageId=");
			msg.append(fullImageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByFullImageId(long fullImageId)
		throws SystemException {
		return fetchByFullImageId(fullImageId, true);
	}

	public SCProductScreenshot fetchByFullImageId(long fullImageId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { fullImageId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2);

				query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getFullImageId() != fullImageId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_FULLIMAGEID,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
			}
		}
	}

	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = fetchByP_P(productEntryId,
				priority);

		if (scProductScreenshot == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("productEntryId=");
			msg.append(productEntryId);

			msg.append(", priority=");
			msg.append(priority);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchProductScreenshotException(msg.toString());
		}

		return scProductScreenshot;
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority)
		throws SystemException {
		return fetchByP_P(productEntryId, priority, true);
	}

	public SCProductScreenshot fetchByP_P(long productEntryId, int priority,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId, priority };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_P_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_P_P_PRODUCTENTRYID_2);

				query.append(_FINDER_COLUMN_P_P_PRIORITY_2);

				query.append(SCProductScreenshotModelImpl.ORDER_BY_JPQL);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				List<SCProductScreenshot> list = q.list();

				result = list;

				SCProductScreenshot scProductScreenshot = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
						finderArgs, list);
				}
				else {
					scProductScreenshot = list.get(0);

					cacheResult(scProductScreenshot);

					if ((scProductScreenshot.getProductEntryId() != productEntryId) ||
							(scProductScreenshot.getPriority() != priority)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
							finderArgs, scProductScreenshot);
					}
				}

				return scProductScreenshot;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_P_P,
						finderArgs, new ArrayList<SCProductScreenshot>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SCProductScreenshot)result;
			}
		}
	}

	public List<SCProductScreenshot> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<SCProductScreenshot> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SCProductScreenshot> list = (List<SCProductScreenshot>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SCPRODUCTSCREENSHOT);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_SCPRODUCTSCREENSHOT.concat(SCProductScreenshotModelImpl.ORDER_BY_JPQL);
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SCProductScreenshot>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SCProductScreenshot>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByProductEntryId(long productEntryId)
		throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findByProductEntryId(
				productEntryId)) {
			remove(scProductScreenshot);
		}
	}

	public void removeByThumbnailId(long thumbnailId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByThumbnailId(thumbnailId);

		remove(scProductScreenshot);
	}

	public void removeByFullImageId(long fullImageId)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByFullImageId(fullImageId);

		remove(scProductScreenshot);
	}

	public void removeByP_P(long productEntryId, int priority)
		throws NoSuchProductScreenshotException, SystemException {
		SCProductScreenshot scProductScreenshot = findByP_P(productEntryId,
				priority);

		remove(scProductScreenshot);
	}

	public void removeAll() throws SystemException {
		for (SCProductScreenshot scProductScreenshot : findAll()) {
			remove(scProductScreenshot);
		}
	}

	public int countByProductEntryId(long productEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PRODUCTENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByThumbnailId(long thumbnailId) throws SystemException {
		Object[] finderArgs = new Object[] { thumbnailId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(thumbnailId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_THUMBNAILID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByFullImageId(long fullImageId) throws SystemException {
		Object[] finderArgs = new Object[] { fullImageId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fullImageId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FULLIMAGEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public int countByP_P(long productEntryId, int priority)
		throws SystemException {
		Object[] finderArgs = new Object[] { productEntryId, priority };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_P_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE);

				query.append(_FINDER_COLUMN_P_P_PRODUCTENTRYID_2);

				query.append(_FINDER_COLUMN_P_P_PRIORITY_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(productEntryId);

				qPos.add(priority);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_P_P, finderArgs,
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

				Query q = session.createQuery(_SQL_COUNT_SCPRODUCTSCREENSHOT);

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
						"value.object.listener.com.liferay.portlet.softwarecatalog.model.SCProductScreenshot")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SCProductScreenshot>> listenersList = new ArrayList<ModelListener<SCProductScreenshot>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SCProductScreenshot>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
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
	@BeanReference(type = ImagePersistence.class)
	protected ImagePersistence imagePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SCPRODUCTSCREENSHOT = "SELECT scProductScreenshot FROM SCProductScreenshot scProductScreenshot";
	private static final String _SQL_SELECT_SCPRODUCTSCREENSHOT_WHERE = "SELECT scProductScreenshot FROM SCProductScreenshot scProductScreenshot WHERE ";
	private static final String _SQL_COUNT_SCPRODUCTSCREENSHOT = "SELECT COUNT(scProductScreenshot) FROM SCProductScreenshot scProductScreenshot";
	private static final String _SQL_COUNT_SCPRODUCTSCREENSHOT_WHERE = "SELECT COUNT(scProductScreenshot) FROM SCProductScreenshot scProductScreenshot WHERE ";
	private static final String _FINDER_COLUMN_PRODUCTENTRYID_PRODUCTENTRYID_2 = "scProductScreenshot.productEntryId = ?";
	private static final String _FINDER_COLUMN_THUMBNAILID_THUMBNAILID_2 = "scProductScreenshot.thumbnailId = ?";
	private static final String _FINDER_COLUMN_FULLIMAGEID_FULLIMAGEID_2 = "scProductScreenshot.fullImageId = ?";
	private static final String _FINDER_COLUMN_P_P_PRODUCTENTRYID_2 = "scProductScreenshot.productEntryId = ? AND ";
	private static final String _FINDER_COLUMN_P_P_PRIORITY_2 = "scProductScreenshot.priority = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "scProductScreenshot.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SCProductScreenshot exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SCProductScreenshot exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SCProductScreenshotPersistenceImpl.class);
}