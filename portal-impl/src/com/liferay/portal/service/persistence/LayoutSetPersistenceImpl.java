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

import com.liferay.portal.NoSuchLayoutSetException;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.LayoutSetModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the layout set service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link LayoutSetUtil} to access the layout set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPersistence
 * @see LayoutSetUtil
 * @generated
 */
public class LayoutSetPersistenceImpl extends BasePersistenceImpl<LayoutSet>
	implements LayoutSetPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByGroupId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_VIRTUALHOST = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_VIRTUALHOST = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByVirtualHost", new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the layout set in the entity cache if it is enabled.
	 *
	 * @param layoutSet the layout set to cache
	 */
	public void cacheResult(LayoutSet layoutSet) {
		EntityCacheUtil.putResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey(), layoutSet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { layoutSet.getVirtualHost() }, layoutSet);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				new Long(layoutSet.getGroupId()),
				Boolean.valueOf(layoutSet.getPrivateLayout())
			}, layoutSet);
	}

	/**
	 * Caches the layout sets in the entity cache if it is enabled.
	 *
	 * @param layoutSets the layout sets to cache
	 */
	public void cacheResult(List<LayoutSet> layoutSets) {
		for (LayoutSet layoutSet : layoutSets) {
			if (EntityCacheUtil.getResult(
						LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetImpl.class, layoutSet.getPrimaryKey(), this) == null) {
				cacheResult(layoutSet);
			}
		}
	}

	/**
	 * Clears the cache for all layout sets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(LayoutSetImpl.class.getName());
		EntityCacheUtil.clearCache(LayoutSetImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the layout set.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(LayoutSet layoutSet) {
		EntityCacheUtil.removeResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { layoutSet.getVirtualHost() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				new Long(layoutSet.getGroupId()),
				Boolean.valueOf(layoutSet.getPrivateLayout())
			});
	}

	/**
	 * Creates a new layout set with the primary key. Does not add the layout set to the database.
	 *
	 * @param layoutSetId the primary key for the new layout set
	 * @return the new layout set
	 */
	public LayoutSet create(long layoutSetId) {
		LayoutSet layoutSet = new LayoutSetImpl();

		layoutSet.setNew(true);
		layoutSet.setPrimaryKey(layoutSetId);

		return layoutSet;
	}

	/**
	 * Removes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set to remove
	 * @return the layout set that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetId the primary key of the layout set to remove
	 * @return the layout set that was removed
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet remove(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LayoutSet layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
					new Long(layoutSetId));

			if (layoutSet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutSetId);
				}

				throw new NoSuchLayoutSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					layoutSetId);
			}

			return remove(layoutSet);
		}
		catch (NoSuchLayoutSetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSet removeImpl(LayoutSet layoutSet)
		throws SystemException {
		layoutSet = toUnwrappedModel(layoutSet);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, layoutSet);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		LayoutSetModelImpl layoutSetModelImpl = (LayoutSetModelImpl)layoutSet;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
			new Object[] { layoutSetModelImpl.getOriginalVirtualHost() });

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
			new Object[] {
				new Long(layoutSetModelImpl.getOriginalGroupId()),
				Boolean.valueOf(layoutSetModelImpl.getOriginalPrivateLayout())
			});

		EntityCacheUtil.removeResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey());

		return layoutSet;
	}

	public LayoutSet updateImpl(com.liferay.portal.model.LayoutSet layoutSet,
		boolean merge) throws SystemException {
		layoutSet = toUnwrappedModel(layoutSet);

		boolean isNew = layoutSet.isNew();

		LayoutSetModelImpl layoutSetModelImpl = (LayoutSetModelImpl)layoutSet;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, layoutSet, merge);

			layoutSet.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetImpl.class, layoutSet.getPrimaryKey(), layoutSet);

		if (!isNew &&
				(!Validator.equals(layoutSet.getVirtualHost(),
					layoutSetModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { layoutSetModelImpl.getOriginalVirtualHost() });
		}

		if (isNew ||
				(!Validator.equals(layoutSet.getVirtualHost(),
					layoutSetModelImpl.getOriginalVirtualHost()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
				new Object[] { layoutSet.getVirtualHost() }, layoutSet);
		}

		if (!isNew &&
				((layoutSet.getGroupId() != layoutSetModelImpl.getOriginalGroupId()) ||
				(layoutSet.getPrivateLayout() != layoutSetModelImpl.getOriginalPrivateLayout()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P,
				new Object[] {
					new Long(layoutSetModelImpl.getOriginalGroupId()),
					Boolean.valueOf(
						layoutSetModelImpl.getOriginalPrivateLayout())
				});
		}

		if (isNew ||
				((layoutSet.getGroupId() != layoutSetModelImpl.getOriginalGroupId()) ||
				(layoutSet.getPrivateLayout() != layoutSetModelImpl.getOriginalPrivateLayout()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
				new Object[] {
					new Long(layoutSet.getGroupId()),
					Boolean.valueOf(layoutSet.getPrivateLayout())
				}, layoutSet);
		}

		return layoutSet;
	}

	protected LayoutSet toUnwrappedModel(LayoutSet layoutSet) {
		if (layoutSet instanceof LayoutSetImpl) {
			return layoutSet;
		}

		LayoutSetImpl layoutSetImpl = new LayoutSetImpl();

		layoutSetImpl.setNew(layoutSet.isNew());
		layoutSetImpl.setPrimaryKey(layoutSet.getPrimaryKey());

		layoutSetImpl.setLayoutSetId(layoutSet.getLayoutSetId());
		layoutSetImpl.setGroupId(layoutSet.getGroupId());
		layoutSetImpl.setCompanyId(layoutSet.getCompanyId());
		layoutSetImpl.setPrivateLayout(layoutSet.isPrivateLayout());
		layoutSetImpl.setLogo(layoutSet.isLogo());
		layoutSetImpl.setLogoId(layoutSet.getLogoId());
		layoutSetImpl.setThemeId(layoutSet.getThemeId());
		layoutSetImpl.setColorSchemeId(layoutSet.getColorSchemeId());
		layoutSetImpl.setWapThemeId(layoutSet.getWapThemeId());
		layoutSetImpl.setWapColorSchemeId(layoutSet.getWapColorSchemeId());
		layoutSetImpl.setCss(layoutSet.getCss());
		layoutSetImpl.setPageCount(layoutSet.getPageCount());
		layoutSetImpl.setVirtualHost(layoutSet.getVirtualHost());
		layoutSetImpl.setSettings(layoutSet.getSettings());
		layoutSetImpl.setLayoutSetPrototypeId(layoutSet.getLayoutSetPrototypeId());

		return layoutSetImpl;
	}

	/**
	 * Finds the layout set with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set to find
	 * @return the layout set
	 * @throws com.liferay.portal.NoSuchModelException if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout set with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutSetException} if it could not be found.
	 *
	 * @param layoutSetId the primary key of the layout set to find
	 * @return the layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByPrimaryKey(long layoutSetId)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByPrimaryKey(layoutSetId);

		if (layoutSet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + layoutSetId);
			}

			throw new NoSuchLayoutSetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				layoutSetId);
		}

		return layoutSet;
	}

	/**
	 * Finds the layout set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set to find
	 * @return the layout set, or <code>null</code> if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the layout set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetId the primary key of the layout set to find
	 * @return the layout set, or <code>null</code> if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByPrimaryKey(long layoutSetId)
		throws SystemException {
		LayoutSet layoutSet = (LayoutSet)EntityCacheUtil.getResult(LayoutSetModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetImpl.class, layoutSetId, this);

		if (layoutSet == null) {
			Session session = null;

			try {
				session = openSession();

				layoutSet = (LayoutSet)session.get(LayoutSetImpl.class,
						new Long(layoutSetId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (layoutSet != null) {
					cacheResult(layoutSet);
				}

				closeSession(session);
			}
		}

		return layoutSet;
	}

	/**
	 * Finds all the layout sets where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout sets to return
	 * @param end the upper bound of the range of layout sets to return (not inclusive)
	 * @return the range of matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout sets where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param start the lower bound of the range of layout sets to return
	 * @param end the upper bound of the range of layout sets to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutSet> list = (List<LayoutSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
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

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<LayoutSet>)QueryUtil.list(q, getDialect(), start,
						end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first layout set in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetException, SystemException {
		List<LayoutSet> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last layout set in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchLayoutSetException, SystemException {
		int count = countByGroupId(groupId);

		List<LayoutSet> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchLayoutSetException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the layout sets before and after the current layout set in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutSetId the primary key of the current layout set
	 * @param groupId the group id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a layout set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet[] findByGroupId_PrevAndNext(long layoutSetId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByPrimaryKey(layoutSetId);

		Session session = null;

		try {
			session = openSession();

			LayoutSet[] array = new LayoutSetImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutSet, groupId,
					orderByComparator, true);

			array[1] = layoutSet;

			array[2] = getByGroupId_PrevAndNext(session, layoutSet, groupId,
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

	protected LayoutSet getByGroupId_PrevAndNext(Session session,
		LayoutSet layoutSet, long groupId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSET_WHERE);

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
			Object[] values = orderByComparator.getOrderByValues(layoutSet);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutSet> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the layout set where virtualHost = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutSetException} if it could not be found.
	 *
	 * @param virtualHost the virtual host to search with
	 * @return the matching layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByVirtualHost(virtualHost);

		if (layoutSet == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("virtualHost=");
			msg.append(virtualHost);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	/**
	 * Finds the layout set where virtualHost = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param virtualHost the virtual host to search with
	 * @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByVirtualHost(String virtualHost)
		throws SystemException {
		return fetchByVirtualHost(virtualHost, true);
	}

	/**
	 * Finds the layout set where virtualHost = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param virtualHost the virtual host to search with
	 * @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByVirtualHost(String virtualHost,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
				}

				List<LayoutSet> list = q.list();

				result = list;

				LayoutSet layoutSet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, list);
				}
				else {
					layoutSet = list.get(0);

					cacheResult(layoutSet);

					if ((layoutSet.getVirtualHost() == null) ||
							!layoutSet.getVirtualHost().equals(virtualHost)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
							finderArgs, layoutSet);
					}
				}

				return layoutSet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_VIRTUALHOST,
						finderArgs, new ArrayList<LayoutSet>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutSet)result;
			}
		}
	}

	/**
	 * Finds the layout set where groupId = &#63; and privateLayout = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutSetException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param privateLayout the private layout to search with
	 * @return the matching layout set
	 * @throws com.liferay.portal.NoSuchLayoutSetException if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet findByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = fetchByG_P(groupId, privateLayout);

		if (layoutSet == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchLayoutSetException(msg.toString());
		}

		return layoutSet;
	}

	/**
	 * Finds the layout set where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param privateLayout the private layout to search with
	 * @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		return fetchByG_P(groupId, privateLayout, true);
	}

	/**
	 * Finds the layout set where groupId = &#63; and privateLayout = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param privateLayout the private layout to search with
	 * @return the matching layout set, or <code>null</code> if a matching layout set could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public LayoutSet fetchByG_P(long groupId, boolean privateLayout,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_SELECT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				List<LayoutSet> list = q.list();

				result = list;

				LayoutSet layoutSet = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
						finderArgs, list);
				}
				else {
					layoutSet = list.get(0);

					cacheResult(layoutSet);

					if ((layoutSet.getGroupId() != groupId) ||
							(layoutSet.getPrivateLayout() != privateLayout)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
							finderArgs, layoutSet);
					}
				}

				return layoutSet;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P,
						finderArgs, new ArrayList<LayoutSet>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (LayoutSet)result;
			}
		}
	}

	/**
	 * Finds all the layout sets.
	 *
	 * @return the layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the layout sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout sets to return
	 * @param end the upper bound of the range of layout sets to return (not inclusive)
	 * @return the range of layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the layout sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout sets to return
	 * @param end the upper bound of the range of layout sets to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<LayoutSet> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<LayoutSet> list = (List<LayoutSet>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_LAYOUTSET);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_LAYOUTSET;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<LayoutSet>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<LayoutSet>)QueryUtil.list(q, getDialect(),
							start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<LayoutSet>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout sets where groupId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (LayoutSet layoutSet : findByGroupId(groupId)) {
			remove(layoutSet);
		}
	}

	/**
	 * Removes the layout set where virtualHost = &#63; from the database.
	 *
	 * @param virtualHost the virtual host to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByVirtualHost(String virtualHost)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByVirtualHost(virtualHost);

		remove(layoutSet);
	}

	/**
	 * Removes the layout set where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param privateLayout the private layout to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, boolean privateLayout)
		throws NoSuchLayoutSetException, SystemException {
		LayoutSet layoutSet = findByG_P(groupId, privateLayout);

		remove(layoutSet);
	}

	/**
	 * Removes all the layout sets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (LayoutSet layoutSet : findAll()) {
			remove(layoutSet);
		}
	}

	/**
	 * Counts all the layout sets where groupId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @return the number of matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

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

	/**
	 * Counts all the layout sets where virtualHost = &#63;.
	 *
	 * @param virtualHost the virtual host to search with
	 * @return the number of matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByVirtualHost(String virtualHost) throws SystemException {
		Object[] finderArgs = new Object[] { virtualHost };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

				if (virtualHost == null) {
					query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1);
				}
				else {
					if (virtualHost.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3);
					}
					else {
						query.append(_FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (virtualHost != null) {
					qPos.add(virtualHost);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VIRTUALHOST,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the layout sets where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param privateLayout the private layout to search with
	 * @return the number of matching layout sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(3);

				query.append(_SQL_COUNT_LAYOUTSET_WHERE);

				query.append(_FINDER_COLUMN_G_P_GROUPID_2);

				query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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

	/**
	 * Counts all the layout sets.
	 *
	 * @return the number of layout sets
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

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSET);

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
	 * Initializes the layout set persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.LayoutSet")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LayoutSet>> listenersList = new ArrayList<ModelListener<LayoutSet>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LayoutSet>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LayoutSetImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = AccountPersistence.class)
	protected AccountPersistence accountPersistence;
	@BeanReference(type = AddressPersistence.class)
	protected AddressPersistence addressPersistence;
	@BeanReference(type = BrowserTrackerPersistence.class)
	protected BrowserTrackerPersistence browserTrackerPersistence;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = ClusterGroupPersistence.class)
	protected ClusterGroupPersistence clusterGroupPersistence;
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
	private static final String _SQL_SELECT_LAYOUTSET = "SELECT layoutSet FROM LayoutSet layoutSet";
	private static final String _SQL_SELECT_LAYOUTSET_WHERE = "SELECT layoutSet FROM LayoutSet layoutSet WHERE ";
	private static final String _SQL_COUNT_LAYOUTSET = "SELECT COUNT(layoutSet) FROM LayoutSet layoutSet";
	private static final String _SQL_COUNT_LAYOUTSET_WHERE = "SELECT COUNT(layoutSet) FROM LayoutSet layoutSet WHERE ";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutSet.groupId = ?";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_1 = "layoutSet.virtualHost IS NULL";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_2 = "layoutSet.virtualHost = ?";
	private static final String _FINDER_COLUMN_VIRTUALHOST_VIRTUALHOST_3 = "(layoutSet.virtualHost IS NULL OR layoutSet.virtualHost = ?)";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layoutSet.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layoutSet.privateLayout = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSet exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSet exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(LayoutSetPersistenceImpl.class);
}