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

import com.liferay.portal.NoSuchClusterGroupException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
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
import com.liferay.portal.model.ClusterGroup;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.ClusterGroupImpl;
import com.liferay.portal.model.impl.ClusterGroupModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the cluster group service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link ClusterGroupUtil} to access the cluster group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClusterGroupPersistence
 * @see ClusterGroupUtil
 * @generated
 */
public class ClusterGroupPersistenceImpl extends BasePersistenceImpl<ClusterGroup>
	implements ClusterGroupPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = ClusterGroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the cluster group in the entity cache if it is enabled.
	 *
	 * @param clusterGroup the cluster group to cache
	 */
	public void cacheResult(ClusterGroup clusterGroup) {
		EntityCacheUtil.putResult(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupImpl.class, clusterGroup.getPrimaryKey(), clusterGroup);
	}

	/**
	 * Caches the cluster groups in the entity cache if it is enabled.
	 *
	 * @param clusterGroups the cluster groups to cache
	 */
	public void cacheResult(List<ClusterGroup> clusterGroups) {
		for (ClusterGroup clusterGroup : clusterGroups) {
			if (EntityCacheUtil.getResult(
						ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
						ClusterGroupImpl.class, clusterGroup.getPrimaryKey(),
						this) == null) {
				cacheResult(clusterGroup);
			}
		}
	}

	/**
	 * Clears the cache for all cluster groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(ClusterGroupImpl.class.getName());
		EntityCacheUtil.clearCache(ClusterGroupImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the cluster group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(ClusterGroup clusterGroup) {
		EntityCacheUtil.removeResult(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupImpl.class, clusterGroup.getPrimaryKey());
	}

	/**
	 * Creates a new cluster group with the primary key. Does not add the cluster group to the database.
	 *
	 * @param clusterGroupId the primary key for the new cluster group
	 * @return the new cluster group
	 */
	public ClusterGroup create(long clusterGroupId) {
		ClusterGroup clusterGroup = new ClusterGroupImpl();

		clusterGroup.setNew(true);
		clusterGroup.setPrimaryKey(clusterGroupId);

		return clusterGroup;
	}

	/**
	 * Removes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cluster group to remove
	 * @return the cluster group that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the cluster group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param clusterGroupId the primary key of the cluster group to remove
	 * @return the cluster group that was removed
	 * @throws com.liferay.portal.NoSuchClusterGroupException if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup remove(long clusterGroupId)
		throws NoSuchClusterGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ClusterGroup clusterGroup = (ClusterGroup)session.get(ClusterGroupImpl.class,
					new Long(clusterGroupId));

			if (clusterGroup == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						clusterGroupId);
				}

				throw new NoSuchClusterGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					clusterGroupId);
			}

			return remove(clusterGroup);
		}
		catch (NoSuchClusterGroupException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ClusterGroup removeImpl(ClusterGroup clusterGroup)
		throws SystemException {
		clusterGroup = toUnwrappedModel(clusterGroup);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, clusterGroup);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.removeResult(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupImpl.class, clusterGroup.getPrimaryKey());

		return clusterGroup;
	}

	public ClusterGroup updateImpl(
		com.liferay.portal.model.ClusterGroup clusterGroup, boolean merge)
		throws SystemException {
		clusterGroup = toUnwrappedModel(clusterGroup);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, clusterGroup, merge);

			clusterGroup.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
			ClusterGroupImpl.class, clusterGroup.getPrimaryKey(), clusterGroup);

		return clusterGroup;
	}

	protected ClusterGroup toUnwrappedModel(ClusterGroup clusterGroup) {
		if (clusterGroup instanceof ClusterGroupImpl) {
			return clusterGroup;
		}

		ClusterGroupImpl clusterGroupImpl = new ClusterGroupImpl();

		clusterGroupImpl.setNew(clusterGroup.isNew());
		clusterGroupImpl.setPrimaryKey(clusterGroup.getPrimaryKey());

		clusterGroupImpl.setClusterGroupId(clusterGroup.getClusterGroupId());
		clusterGroupImpl.setName(clusterGroup.getName());
		clusterGroupImpl.setClusterNodeIds(clusterGroup.getClusterNodeIds());
		clusterGroupImpl.setWholeCluster(clusterGroup.isWholeCluster());

		return clusterGroupImpl;
	}

	/**
	 * Finds the cluster group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cluster group to find
	 * @return the cluster group
	 * @throws com.liferay.portal.NoSuchModelException if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the cluster group with the primary key or throws a {@link com.liferay.portal.NoSuchClusterGroupException} if it could not be found.
	 *
	 * @param clusterGroupId the primary key of the cluster group to find
	 * @return the cluster group
	 * @throws com.liferay.portal.NoSuchClusterGroupException if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup findByPrimaryKey(long clusterGroupId)
		throws NoSuchClusterGroupException, SystemException {
		ClusterGroup clusterGroup = fetchByPrimaryKey(clusterGroupId);

		if (clusterGroup == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + clusterGroupId);
			}

			throw new NoSuchClusterGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				clusterGroupId);
		}

		return clusterGroup;
	}

	/**
	 * Finds the cluster group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cluster group to find
	 * @return the cluster group, or <code>null</code> if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the cluster group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param clusterGroupId the primary key of the cluster group to find
	 * @return the cluster group, or <code>null</code> if a cluster group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ClusterGroup fetchByPrimaryKey(long clusterGroupId)
		throws SystemException {
		ClusterGroup clusterGroup = (ClusterGroup)EntityCacheUtil.getResult(ClusterGroupModelImpl.ENTITY_CACHE_ENABLED,
				ClusterGroupImpl.class, clusterGroupId, this);

		if (clusterGroup == null) {
			Session session = null;

			try {
				session = openSession();

				clusterGroup = (ClusterGroup)session.get(ClusterGroupImpl.class,
						new Long(clusterGroupId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (clusterGroup != null) {
					cacheResult(clusterGroup);
				}

				closeSession(session);
			}
		}

		return clusterGroup;
	}

	/**
	 * Finds all the cluster groups.
	 *
	 * @return the cluster groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<ClusterGroup> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the cluster groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of cluster groups to return
	 * @param end the upper bound of the range of cluster groups to return (not inclusive)
	 * @return the range of cluster groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<ClusterGroup> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the cluster groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of cluster groups to return
	 * @param end the upper bound of the range of cluster groups to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of cluster groups
	 * @throws SystemException if a system exception occurred
	 */
	public List<ClusterGroup> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ClusterGroup> list = (List<ClusterGroup>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CLUSTERGROUP);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CLUSTERGROUP;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ClusterGroup>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ClusterGroup>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the cluster groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ClusterGroup clusterGroup : findAll()) {
			remove(clusterGroup);
		}
	}

	/**
	 * Counts all the cluster groups.
	 *
	 * @return the number of cluster groups
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

				Query q = session.createQuery(_SQL_COUNT_CLUSTERGROUP);

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
	 * Initializes the cluster group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ClusterGroup")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ClusterGroup>> listenersList = new ArrayList<ModelListener<ClusterGroup>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ClusterGroup>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ClusterGroupImpl.class.getName());
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
	@BeanReference(type = LayoutBranchPersistence.class)
	protected LayoutBranchPersistence layoutBranchPersistence;
	@BeanReference(type = LayoutPrototypePersistence.class)
	protected LayoutPrototypePersistence layoutPrototypePersistence;
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
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
	private static final String _SQL_SELECT_CLUSTERGROUP = "SELECT clusterGroup FROM ClusterGroup clusterGroup";
	private static final String _SQL_COUNT_CLUSTERGROUP = "SELECT COUNT(clusterGroup) FROM ClusterGroup clusterGroup";
	private static final String _ORDER_BY_ENTITY_ALIAS = "clusterGroup.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ClusterGroup exists with the primary key ";
	private static Log _log = LogFactoryUtil.getLog(ClusterGroupPersistenceImpl.class);
}