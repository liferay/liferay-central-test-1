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
import com.liferay.portal.NoSuchUserTrackerPathException;
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
import com.liferay.portal.model.UserTrackerPath;
import com.liferay.portal.model.impl.UserTrackerPathImpl;
import com.liferay.portal.model.impl.UserTrackerPathModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

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
 * @see       UserTrackerPathPersistence
 * @see       UserTrackerPathUtil
 * @generated
 */
public class UserTrackerPathPersistenceImpl extends BasePersistenceImpl<UserTrackerPath>
	implements UserTrackerPathPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = UserTrackerPathImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByUserTrackerId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERTRACKERID = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByUserTrackerId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	public void cacheResult(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);
	}

	public void cacheResult(List<UserTrackerPath> userTrackerPaths) {
		for (UserTrackerPath userTrackerPath : userTrackerPaths) {
			if (EntityCacheUtil.getResult(
						UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
						UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKey(), this) == null) {
				cacheResult(userTrackerPath);
			}
		}
	}

	public void clearCache() {
		CacheRegistryUtil.clear(UserTrackerPathImpl.class.getName());
		EntityCacheUtil.clearCache(UserTrackerPathImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	public void clearCache(UserTrackerPath userTrackerPath) {
		EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey());
	}

	public UserTrackerPath create(long userTrackerPathId) {
		UserTrackerPath userTrackerPath = new UserTrackerPathImpl();

		userTrackerPath.setNew(true);
		userTrackerPath.setPrimaryKey(userTrackerPathId);

		return userTrackerPath;
	}

	public UserTrackerPath remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	public UserTrackerPath remove(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserTrackerPath userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
					new Long(userTrackerPathId));

			if (userTrackerPath == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userTrackerPathId);
				}

				throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userTrackerPathId);
			}

			return remove(userTrackerPath);
		}
		catch (NoSuchUserTrackerPathException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTrackerPath removeImpl(UserTrackerPath userTrackerPath)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			if (userTrackerPath.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(UserTrackerPathImpl.class,
						userTrackerPath.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(userTrackerPath);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey());

		return userTrackerPath;
	}

	public UserTrackerPath updateImpl(
		com.liferay.portal.model.UserTrackerPath userTrackerPath, boolean merge)
		throws SystemException {
		userTrackerPath = toUnwrappedModel(userTrackerPath);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userTrackerPath, merge);

			userTrackerPath.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
			UserTrackerPathImpl.class, userTrackerPath.getPrimaryKey(),
			userTrackerPath);

		return userTrackerPath;
	}

	protected UserTrackerPath toUnwrappedModel(UserTrackerPath userTrackerPath) {
		if (userTrackerPath instanceof UserTrackerPathImpl) {
			return userTrackerPath;
		}

		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setNew(userTrackerPath.isNew());
		userTrackerPathImpl.setPrimaryKey(userTrackerPath.getPrimaryKey());

		userTrackerPathImpl.setUserTrackerPathId(userTrackerPath.getUserTrackerPathId());
		userTrackerPathImpl.setUserTrackerId(userTrackerPath.getUserTrackerId());
		userTrackerPathImpl.setPath(userTrackerPath.getPath());
		userTrackerPathImpl.setPathDate(userTrackerPath.getPathDate());

		return userTrackerPathImpl;
	}

	public UserTrackerPath findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTrackerPath findByPrimaryKey(long userTrackerPathId)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = fetchByPrimaryKey(userTrackerPathId);

		if (userTrackerPath == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userTrackerPathId);
			}

			throw new NoSuchUserTrackerPathException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userTrackerPathId);
		}

		return userTrackerPath;
	}

	public UserTrackerPath fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	public UserTrackerPath fetchByPrimaryKey(long userTrackerPathId)
		throws SystemException {
		UserTrackerPath userTrackerPath = (UserTrackerPath)EntityCacheUtil.getResult(UserTrackerPathModelImpl.ENTITY_CACHE_ENABLED,
				UserTrackerPathImpl.class, userTrackerPathId, this);

		if (userTrackerPath == null) {
			Session session = null;

			try {
				session = openSession();

				userTrackerPath = (UserTrackerPath)session.get(UserTrackerPathImpl.class,
						new Long(userTrackerPathId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userTrackerPath != null) {
					cacheResult(userTrackerPath);
				}

				closeSession(session);
			}
		}

		return userTrackerPath;
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId)
		throws SystemException {
		return findByUserTrackerId(userTrackerId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end) throws SystemException {
		return findByUserTrackerId(userTrackerId, start, end, null);
	}

	public List<UserTrackerPath> findByUserTrackerId(long userTrackerId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				userTrackerId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERTRACKERID,
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

				query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

				query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				list = (List<UserTrackerPath>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTrackerPath>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERTRACKERID,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public UserTrackerPath findByUserTrackerId_First(long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userTrackerId=");
			msg.append(userTrackerId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTrackerPath findByUserTrackerId_Last(long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		int count = countByUserTrackerId(userTrackerId);

		List<UserTrackerPath> list = findByUserTrackerId(userTrackerId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userTrackerId=");
			msg.append(userTrackerId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserTrackerPathException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	public UserTrackerPath[] findByUserTrackerId_PrevAndNext(
		long userTrackerPathId, long userTrackerId,
		OrderByComparator orderByComparator)
		throws NoSuchUserTrackerPathException, SystemException {
		UserTrackerPath userTrackerPath = findByPrimaryKey(userTrackerPathId);

		Session session = null;

		try {
			session = openSession();

			UserTrackerPath[] array = new UserTrackerPathImpl[3];

			array[0] = getByUserTrackerId_PrevAndNext(session, userTrackerPath,
					userTrackerId, orderByComparator, true);

			array[1] = userTrackerPath;

			array[2] = getByUserTrackerId_PrevAndNext(session, userTrackerPath,
					userTrackerId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserTrackerPath getByUserTrackerId_PrevAndNext(Session session,
		UserTrackerPath userTrackerPath, long userTrackerId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERTRACKERPATH_WHERE);

		query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

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

		qPos.add(userTrackerId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userTrackerPath);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserTrackerPath> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	public List<UserTrackerPath> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public List<UserTrackerPath> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	public List<UserTrackerPath> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserTrackerPath> list = (List<UserTrackerPath>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_USERTRACKERPATH);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_USERTRACKERPATH;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserTrackerPath>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<UserTrackerPath>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	public void removeByUserTrackerId(long userTrackerId)
		throws SystemException {
		for (UserTrackerPath userTrackerPath : findByUserTrackerId(
				userTrackerId)) {
			remove(userTrackerPath);
		}
	}

	public void removeAll() throws SystemException {
		for (UserTrackerPath userTrackerPath : findAll()) {
			remove(userTrackerPath);
		}
	}

	public int countByUserTrackerId(long userTrackerId)
		throws SystemException {
		Object[] finderArgs = new Object[] { userTrackerId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(2);

				query.append(_SQL_COUNT_USERTRACKERPATH_WHERE);

				query.append(_FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userTrackerId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_USERTRACKERID,
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

				Query q = session.createQuery(_SQL_COUNT_USERTRACKERPATH);

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
						"value.object.listener.com.liferay.portal.model.UserTrackerPath")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserTrackerPath>> listenersList = new ArrayList<ModelListener<UserTrackerPath>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserTrackerPath>)InstanceFactory.newInstance(
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
	private static final String _SQL_SELECT_USERTRACKERPATH = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_SELECT_USERTRACKERPATH_WHERE = "SELECT userTrackerPath FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _SQL_COUNT_USERTRACKERPATH = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath";
	private static final String _SQL_COUNT_USERTRACKERPATH_WHERE = "SELECT COUNT(userTrackerPath) FROM UserTrackerPath userTrackerPath WHERE ";
	private static final String _FINDER_COLUMN_USERTRACKERID_USERTRACKERID_2 = "userTrackerPath.userTrackerId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userTrackerPath.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserTrackerPath exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserTrackerPath exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserTrackerPathPersistenceImpl.class);
}