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

import com.liferay.portal.NoSuchBrowserTrackerException;
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
import com.liferay.portal.model.BrowserTracker;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.BrowserTrackerImpl;
import com.liferay.portal.model.impl.BrowserTrackerModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence for the browser tracker service.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BrowserTrackerPersistence
 * @see BrowserTrackerUtil
 * @generated
 */
public class BrowserTrackerPersistenceImpl extends BasePersistenceImpl<BrowserTracker>
	implements BrowserTrackerPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = BrowserTrackerImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FETCH_BY_USERID = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the browser tracker in the entity cache if it is enabled.
	 *
	 * @param browserTracker the browser tracker to cache
	 */
	public void cacheResult(BrowserTracker browserTracker) {
		EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey(),
			browserTracker);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(browserTracker.getUserId()) },
			browserTracker);
	}

	/**
	 * Caches the browser trackers in the entity cache if it is enabled.
	 *
	 * @param browserTrackers the browser trackers to cache
	 */
	public void cacheResult(List<BrowserTracker> browserTrackers) {
		for (BrowserTracker browserTracker : browserTrackers) {
			if (EntityCacheUtil.getResult(
						BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
						BrowserTrackerImpl.class,
						browserTracker.getPrimaryKey(), this) == null) {
				cacheResult(browserTracker);
			}
		}
	}

	/**
	 * Clears the cache for all browser trackers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(BrowserTrackerImpl.class.getName());
		EntityCacheUtil.clearCache(BrowserTrackerImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the browser tracker.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(BrowserTracker browserTracker) {
		EntityCacheUtil.removeResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(browserTracker.getUserId()) });
	}

	/**
	 * Creates a new browser tracker with the primary key.
	 *
	 * @param browserTrackerId the primary key for the new browser tracker
	 * @return the new browser tracker
	 */
	public BrowserTracker create(long browserTrackerId) {
		BrowserTracker browserTracker = new BrowserTrackerImpl();

		browserTracker.setNew(true);
		browserTracker.setPrimaryKey(browserTrackerId);

		return browserTracker;
	}

	/**
	 * Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the browser tracker to remove
	 * @return the browser tracker that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the browser tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param browserTrackerId the primary key of the browser tracker to remove
	 * @return the browser tracker that was removed
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker remove(long browserTrackerId)
		throws NoSuchBrowserTrackerException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BrowserTracker browserTracker = (BrowserTracker)session.get(BrowserTrackerImpl.class,
					new Long(browserTrackerId));

			if (browserTracker == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						browserTrackerId);
				}

				throw new NoSuchBrowserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					browserTrackerId);
			}

			return remove(browserTracker);
		}
		catch (NoSuchBrowserTrackerException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BrowserTracker removeImpl(BrowserTracker browserTracker)
		throws SystemException {
		browserTracker = toUnwrappedModel(browserTracker);

		Session session = null;

		try {
			session = openSession();

			if (browserTracker.isCachedModel() || BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(BrowserTrackerImpl.class,
						browserTracker.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(browserTracker);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BrowserTrackerModelImpl browserTrackerModelImpl = (BrowserTrackerModelImpl)browserTracker;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
			new Object[] { new Long(browserTrackerModelImpl.getOriginalUserId()) });

		EntityCacheUtil.removeResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey());

		return browserTracker;
	}

	public BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws SystemException {
		browserTracker = toUnwrappedModel(browserTracker);

		boolean isNew = browserTracker.isNew();

		BrowserTrackerModelImpl browserTrackerModelImpl = (BrowserTrackerModelImpl)browserTracker;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, browserTracker, merge);

			browserTracker.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
			BrowserTrackerImpl.class, browserTracker.getPrimaryKey(),
			browserTracker);

		if (!isNew &&
				(browserTracker.getUserId() != browserTrackerModelImpl.getOriginalUserId())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] {
					new Long(browserTrackerModelImpl.getOriginalUserId())
				});
		}

		if (isNew ||
				(browserTracker.getUserId() != browserTrackerModelImpl.getOriginalUserId())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
				new Object[] { new Long(browserTracker.getUserId()) },
				browserTracker);
		}

		return browserTracker;
	}

	protected BrowserTracker toUnwrappedModel(BrowserTracker browserTracker) {
		if (browserTracker instanceof BrowserTrackerImpl) {
			return browserTracker;
		}

		BrowserTrackerImpl browserTrackerImpl = new BrowserTrackerImpl();

		browserTrackerImpl.setNew(browserTracker.isNew());
		browserTrackerImpl.setPrimaryKey(browserTracker.getPrimaryKey());

		browserTrackerImpl.setBrowserTrackerId(browserTracker.getBrowserTrackerId());
		browserTrackerImpl.setUserId(browserTracker.getUserId());
		browserTrackerImpl.setBrowserKey(browserTracker.getBrowserKey());

		return browserTrackerImpl;
	}

	/**
	 * Finds the browser tracker with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the browser tracker to find
	 * @return the browser tracker
	 * @throws com.liferay.portal.NoSuchModelException if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the browser tracker with the primary key or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	 *
	 * @param browserTrackerId the primary key of the browser tracker to find
	 * @return the browser tracker
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker findByPrimaryKey(long browserTrackerId)
		throws NoSuchBrowserTrackerException, SystemException {
		BrowserTracker browserTracker = fetchByPrimaryKey(browserTrackerId);

		if (browserTracker == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + browserTrackerId);
			}

			throw new NoSuchBrowserTrackerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				browserTrackerId);
		}

		return browserTracker;
	}

	/**
	 * Finds the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the browser tracker to find
	 * @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the browser tracker with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param browserTrackerId the primary key of the browser tracker to find
	 * @return the browser tracker, or <code>null</code> if a browser tracker with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker fetchByPrimaryKey(long browserTrackerId)
		throws SystemException {
		BrowserTracker browserTracker = (BrowserTracker)EntityCacheUtil.getResult(BrowserTrackerModelImpl.ENTITY_CACHE_ENABLED,
				BrowserTrackerImpl.class, browserTrackerId, this);

		if (browserTracker == null) {
			Session session = null;

			try {
				session = openSession();

				browserTracker = (BrowserTracker)session.get(BrowserTrackerImpl.class,
						new Long(browserTrackerId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (browserTracker != null) {
					cacheResult(browserTracker);
				}

				closeSession(session);
			}
		}

		return browserTracker;
	}

	/**
	 * Finds the browser tracker where userId = &#63; or throws a {@link com.liferay.portal.NoSuchBrowserTrackerException} if it could not be found.
	 *
	 * @param userId the user id to search with
	 * @return the matching browser tracker
	 * @throws com.liferay.portal.NoSuchBrowserTrackerException if a matching browser tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker findByUserId(long userId)
		throws NoSuchBrowserTrackerException, SystemException {
		BrowserTracker browserTracker = fetchByUserId(userId);

		if (browserTracker == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchBrowserTrackerException(msg.toString());
		}

		return browserTracker;
	}

	/**
	 * Finds the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user id to search with
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker fetchByUserId(long userId) throws SystemException {
		return fetchByUserId(userId, true);
	}

	/**
	 * Finds the browser tracker where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user id to search with
	 * @return the matching browser tracker, or <code>null</code> if a matching browser tracker could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BrowserTracker fetchByUserId(long userId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_USERID,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_SELECT_BROWSERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<BrowserTracker> list = q.list();

				result = list;

				BrowserTracker browserTracker = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, list);
				}
				else {
					browserTracker = list.get(0);

					cacheResult(browserTracker);

					if ((browserTracker.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
							finderArgs, browserTracker);
					}
				}

				return browserTracker;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_USERID,
						finderArgs, new ArrayList<BrowserTracker>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (BrowserTracker)result;
			}
		}
	}

	/**
	 * Finds all the browser trackers.
	 *
	 * @return the browser trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<BrowserTracker> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the browser trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of browser trackers to return
	 * @param end the upper bound of the range of browser trackers to return (not inclusive)
	 * @return the range of browser trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<BrowserTracker> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the browser trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of browser trackers to return
	 * @param end the upper bound of the range of browser trackers to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of browser trackers
	 * @throws SystemException if a system exception occurred
	 */
	public List<BrowserTracker> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<BrowserTracker> list = (List<BrowserTracker>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_BROWSERTRACKER);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_BROWSERTRACKER;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BrowserTracker>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BrowserTracker>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<BrowserTracker>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes the browser tracker where userId = &#63; from the database.
	 *
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId)
		throws NoSuchBrowserTrackerException, SystemException {
		BrowserTracker browserTracker = findByUserId(userId);

		remove(browserTracker);
	}

	/**
	 * Removes all the browser trackers from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (BrowserTracker browserTracker : findAll()) {
			remove(browserTracker);
		}
	}

	/**
	 * Counts all the browser trackers where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the number of matching browser trackers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_BROWSERTRACKER_WHERE);

				query.append(_FINDER_COLUMN_USERID_USERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the browser trackers.
	 *
	 * @return the number of browser trackers
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

				Query q = session.createQuery(_SQL_COUNT_BROWSERTRACKER);

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
	 * Initializes the browser tracker persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.BrowserTracker")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BrowserTracker>> listenersList = new ArrayList<ModelListener<BrowserTracker>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BrowserTracker>)InstanceFactory.newInstance(
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
	private static final String _SQL_SELECT_BROWSERTRACKER = "SELECT browserTracker FROM BrowserTracker browserTracker";
	private static final String _SQL_SELECT_BROWSERTRACKER_WHERE = "SELECT browserTracker FROM BrowserTracker browserTracker WHERE ";
	private static final String _SQL_COUNT_BROWSERTRACKER = "SELECT COUNT(browserTracker) FROM BrowserTracker browserTracker";
	private static final String _SQL_COUNT_BROWSERTRACKER_WHERE = "SELECT COUNT(browserTracker) FROM BrowserTracker browserTracker WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "browserTracker.userId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "browserTracker.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BrowserTracker exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BrowserTracker exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(BrowserTrackerPersistenceImpl.class);
}