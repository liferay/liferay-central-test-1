/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchActivitySetException;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.model.impl.SocialActivitySetImpl;
import com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social activity set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetPersistence
 * @see SocialActivitySetUtil
 * @generated
 */
public class SocialActivitySetPersistenceImpl extends BasePersistenceImpl<SocialActivitySet>
	implements SocialActivitySetPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SocialActivitySetUtil} to access the social activity set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SocialActivitySetImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED,
			SocialActivitySetImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the social activity set in the entity cache if it is enabled.
	 *
	 * @param socialActivitySet the social activity set
	 */
	public void cacheResult(SocialActivitySet socialActivitySet) {
		EntityCacheUtil.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet);

		socialActivitySet.resetOriginalValues();
	}

	/**
	 * Caches the social activity sets in the entity cache if it is enabled.
	 *
	 * @param socialActivitySets the social activity sets
	 */
	public void cacheResult(List<SocialActivitySet> socialActivitySets) {
		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			if (EntityCacheUtil.getResult(
						SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySetImpl.class,
						socialActivitySet.getPrimaryKey()) == null) {
				cacheResult(socialActivitySet);
			}
			else {
				socialActivitySet.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all social activity sets.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SocialActivitySetImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SocialActivitySetImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the social activity set.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SocialActivitySet socialActivitySet) {
		EntityCacheUtil.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SocialActivitySet> socialActivitySets) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SocialActivitySet socialActivitySet : socialActivitySets) {
			EntityCacheUtil.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey());
		}
	}

	/**
	 * Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	 *
	 * @param activitySetId the primary key for the new social activity set
	 * @return the new social activity set
	 */
	public SocialActivitySet create(long activitySetId) {
		SocialActivitySet socialActivitySet = new SocialActivitySetImpl();

		socialActivitySet.setNew(true);
		socialActivitySet.setPrimaryKey(activitySetId);

		return socialActivitySet;
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivitySetException if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySet remove(long activitySetId)
		throws NoSuchActivitySetException, SystemException {
		return remove((Serializable)activitySetId);
	}

	/**
	 * Removes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set that was removed
	 * @throws com.liferay.portlet.social.NoSuchActivitySetException if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySet remove(Serializable primaryKey)
		throws NoSuchActivitySetException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialActivitySet socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
					primaryKey);

			if (socialActivitySet == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActivitySetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(socialActivitySet);
		}
		catch (NoSuchActivitySetException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SocialActivitySet removeImpl(SocialActivitySet socialActivitySet)
		throws SystemException {
		socialActivitySet = toUnwrappedModel(socialActivitySet);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(socialActivitySet)) {
				socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
						socialActivitySet.getPrimaryKeyObj());
			}

			if (socialActivitySet != null) {
				session.delete(socialActivitySet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (socialActivitySet != null) {
			clearCache(socialActivitySet);
		}

		return socialActivitySet;
	}

	@Override
	public SocialActivitySet updateImpl(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet)
		throws SystemException {
		socialActivitySet = toUnwrappedModel(socialActivitySet);

		boolean isNew = socialActivitySet.isNew();

		Session session = null;

		try {
			session = openSession();

			if (socialActivitySet.isNew()) {
				session.save(socialActivitySet);

				socialActivitySet.setNew(false);
			}
			else {
				session.merge(socialActivitySet);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
			SocialActivitySetImpl.class, socialActivitySet.getPrimaryKey(),
			socialActivitySet);

		return socialActivitySet;
	}

	protected SocialActivitySet toUnwrappedModel(
		SocialActivitySet socialActivitySet) {
		if (socialActivitySet instanceof SocialActivitySetImpl) {
			return socialActivitySet;
		}

		SocialActivitySetImpl socialActivitySetImpl = new SocialActivitySetImpl();

		socialActivitySetImpl.setNew(socialActivitySet.isNew());
		socialActivitySetImpl.setPrimaryKey(socialActivitySet.getPrimaryKey());

		socialActivitySetImpl.setActivitySetId(socialActivitySet.getActivitySetId());
		socialActivitySetImpl.setGroupId(socialActivitySet.getGroupId());
		socialActivitySetImpl.setCompanyId(socialActivitySet.getCompanyId());
		socialActivitySetImpl.setUserId(socialActivitySet.getUserId());
		socialActivitySetImpl.setCreateDate(socialActivitySet.getCreateDate());
		socialActivitySetImpl.setModifiedDate(socialActivitySet.getModifiedDate());
		socialActivitySetImpl.setClassNameId(socialActivitySet.getClassNameId());
		socialActivitySetImpl.setClassPK(socialActivitySet.getClassPK());
		socialActivitySetImpl.setType(socialActivitySet.getType());
		socialActivitySetImpl.setActivityCount(socialActivitySet.getActivityCount());

		return socialActivitySetImpl;
	}

	/**
	 * Returns the social activity set with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set
	 * @throws com.liferay.portlet.social.NoSuchActivitySetException if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySet findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActivitySetException, SystemException {
		SocialActivitySet socialActivitySet = fetchByPrimaryKey(primaryKey);

		if (socialActivitySet == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActivitySetException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or throws a {@link com.liferay.portlet.social.NoSuchActivitySetException} if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set
	 * @throws com.liferay.portlet.social.NoSuchActivitySetException if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySet findByPrimaryKey(long activitySetId)
		throws NoSuchActivitySetException, SystemException {
		return findByPrimaryKey((Serializable)activitySetId);
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public SocialActivitySet fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		SocialActivitySet socialActivitySet = (SocialActivitySet)EntityCacheUtil.getResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
				SocialActivitySetImpl.class, primaryKey);

		if (socialActivitySet == _nullSocialActivitySet) {
			return null;
		}

		if (socialActivitySet == null) {
			Session session = null;

			try {
				session = openSession();

				socialActivitySet = (SocialActivitySet)session.get(SocialActivitySetImpl.class,
						primaryKey);

				if (socialActivitySet != null) {
					cacheResult(socialActivitySet);
				}
				else {
					EntityCacheUtil.putResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
						SocialActivitySetImpl.class, primaryKey,
						_nullSocialActivitySet);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SocialActivitySetModelImpl.ENTITY_CACHE_ENABLED,
					SocialActivitySetImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return socialActivitySet;
	}

	/**
	 * Returns the social activity set with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param activitySetId the primary key of the social activity set
	 * @return the social activity set, or <code>null</code> if a social activity set with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialActivitySet fetchByPrimaryKey(long activitySetId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)activitySetId);
	}

	/**
	 * Returns all the social activity sets.
	 *
	 * @return the social activity sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySet> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @return the range of social activity sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySet> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the social activity sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity sets
	 * @param end the upper bound of the range of social activity sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of social activity sets
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialActivitySet> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<SocialActivitySet> list = (List<SocialActivitySet>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SOCIALACTIVITYSET);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SOCIALACTIVITYSET;

				if (pagination) {
					sql = sql.concat(SocialActivitySetModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<SocialActivitySet>(list);
				}
				else {
					list = (List<SocialActivitySet>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the social activity sets from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialActivitySet socialActivitySet : findAll()) {
			remove(socialActivitySet);
		}
	}

	/**
	 * Returns the number of social activity sets.
	 *
	 * @return the number of social activity sets
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SOCIALACTIVITYSET);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the social activity set persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialActivitySet")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialActivitySet>> listenersList = new ArrayList<ModelListener<SocialActivitySet>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialActivitySet>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SocialActivitySetImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SOCIALACTIVITYSET = "SELECT socialActivitySet FROM SocialActivitySet socialActivitySet";
	private static final String _SQL_COUNT_SOCIALACTIVITYSET = "SELECT COUNT(socialActivitySet) FROM SocialActivitySet socialActivitySet";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialActivitySet.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialActivitySet exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(SocialActivitySetPersistenceImpl.class);
	private static SocialActivitySet _nullSocialActivitySet = new SocialActivitySetImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SocialActivitySet> toCacheModel() {
				return _nullSocialActivitySetCacheModel;
			}
		};

	private static CacheModel<SocialActivitySet> _nullSocialActivitySetCacheModel =
		new CacheModel<SocialActivitySet>() {
			public SocialActivitySet toEntityModel() {
				return _nullSocialActivitySet;
			}
		};
}