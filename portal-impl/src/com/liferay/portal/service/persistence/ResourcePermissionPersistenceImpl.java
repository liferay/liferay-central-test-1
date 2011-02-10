/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.model.impl.ResourcePermissionModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the resource permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermissionPersistence
 * @see ResourcePermissionUtil
 * @generated
 */
public class ResourcePermissionPersistenceImpl extends BasePersistenceImpl<ResourcePermission>
	implements ResourcePermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ResourcePermissionUtil} to access the resource permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ResourcePermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_ROLEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByRoleId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_ROLEID = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByRoleId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_R_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByR_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_R_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByR_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_C_N_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_BY_C_N_S_P = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S_P = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N_S_P_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N_S_P_R = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByC_N_S_P_R",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), String.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the resource permission in the entity cache if it is enabled.
	 *
	 * @param resourcePermission the resource permission to cache
	 */
	public void cacheResult(ResourcePermission resourcePermission) {
		EntityCacheUtil.putResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
			new Object[] {
				new Long(resourcePermission.getCompanyId()),
				
			resourcePermission.getName(),
				new Integer(resourcePermission.getScope()),
				
			resourcePermission.getPrimKey(),
				new Long(resourcePermission.getRoleId())
			}, resourcePermission);
	}

	/**
	 * Caches the resource permissions in the entity cache if it is enabled.
	 *
	 * @param resourcePermissions the resource permissions to cache
	 */
	public void cacheResult(List<ResourcePermission> resourcePermissions) {
		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (EntityCacheUtil.getResult(
						ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
						ResourcePermissionImpl.class,
						resourcePermission.getPrimaryKey(), this) == null) {
				cacheResult(resourcePermission);
			}
		}
	}

	/**
	 * Clears the cache for all resource permissions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(ResourcePermissionImpl.class.getName());
		EntityCacheUtil.clearCache(ResourcePermissionImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the resource permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(ResourcePermission resourcePermission) {
		EntityCacheUtil.removeResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
			new Object[] {
				new Long(resourcePermission.getCompanyId()),
				
			resourcePermission.getName(),
				new Integer(resourcePermission.getScope()),
				
			resourcePermission.getPrimKey(),
				new Long(resourcePermission.getRoleId())
			});
	}

	/**
	 * Creates a new resource permission with the primary key. Does not add the resource permission to the database.
	 *
	 * @param resourcePermissionId the primary key for the new resource permission
	 * @return the new resource permission
	 */
	public ResourcePermission create(long resourcePermissionId) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		resourcePermission.setNew(true);
		resourcePermission.setPrimaryKey(resourcePermissionId);

		return resourcePermission;
	}

	/**
	 * Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the resource permission to remove
	 * @return the resource permission that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the resource permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePermissionId the primary key of the resource permission to remove
	 * @return the resource permission that was removed
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission remove(long resourcePermissionId)
		throws NoSuchResourcePermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ResourcePermission resourcePermission = (ResourcePermission)session.get(ResourcePermissionImpl.class,
					new Long(resourcePermissionId));

			if (resourcePermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						resourcePermissionId);
				}

				throw new NoSuchResourcePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourcePermissionId);
			}

			return resourcePermissionPersistence.remove(resourcePermission);
		}
		catch (NoSuchResourcePermissionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the resource permission from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePermission the resource permission to remove
	 * @return the resource permission that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission remove(ResourcePermission resourcePermission)
		throws SystemException {
		return super.remove(resourcePermission);
	}

	protected ResourcePermission removeImpl(
		ResourcePermission resourcePermission) throws SystemException {
		resourcePermission = toUnwrappedModel(resourcePermission);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, resourcePermission);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		ResourcePermissionModelImpl resourcePermissionModelImpl = (ResourcePermissionModelImpl)resourcePermission;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
			new Object[] {
				new Long(resourcePermissionModelImpl.getOriginalCompanyId()),
				
			resourcePermissionModelImpl.getOriginalName(),
				new Integer(resourcePermissionModelImpl.getOriginalScope()),
				
			resourcePermissionModelImpl.getOriginalPrimKey(),
				new Long(resourcePermissionModelImpl.getOriginalRoleId())
			});

		EntityCacheUtil.removeResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey());

		return resourcePermission;
	}

	public ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws SystemException {
		resourcePermission = toUnwrappedModel(resourcePermission);

		boolean isNew = resourcePermission.isNew();

		ResourcePermissionModelImpl resourcePermissionModelImpl = (ResourcePermissionModelImpl)resourcePermission;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, resourcePermission, merge);

			resourcePermission.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
			ResourcePermissionImpl.class, resourcePermission.getPrimaryKey(),
			resourcePermission);

		if (!isNew &&
				((resourcePermission.getCompanyId() != resourcePermissionModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourcePermission.getName(),
					resourcePermissionModelImpl.getOriginalName()) ||
				(resourcePermission.getScope() != resourcePermissionModelImpl.getOriginalScope()) ||
				!Validator.equals(resourcePermission.getPrimKey(),
					resourcePermissionModelImpl.getOriginalPrimKey()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
				new Object[] {
					new Long(resourcePermissionModelImpl.getOriginalCompanyId()),
					
				resourcePermissionModelImpl.getOriginalName(),
					new Integer(resourcePermissionModelImpl.getOriginalScope()),
					
				resourcePermissionModelImpl.getOriginalPrimKey(),
					new Long(resourcePermissionModelImpl.getOriginalRoleId())
				});
		}

		if (isNew ||
				((resourcePermission.getCompanyId() != resourcePermissionModelImpl.getOriginalCompanyId()) ||
				!Validator.equals(resourcePermission.getName(),
					resourcePermissionModelImpl.getOriginalName()) ||
				(resourcePermission.getScope() != resourcePermissionModelImpl.getOriginalScope()) ||
				!Validator.equals(resourcePermission.getPrimKey(),
					resourcePermissionModelImpl.getOriginalPrimKey()) ||
				(resourcePermission.getRoleId() != resourcePermissionModelImpl.getOriginalRoleId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
				new Object[] {
					new Long(resourcePermission.getCompanyId()),
					
				resourcePermission.getName(),
					new Integer(resourcePermission.getScope()),
					
				resourcePermission.getPrimKey(),
					new Long(resourcePermission.getRoleId())
				}, resourcePermission);
		}

		return resourcePermission;
	}

	protected ResourcePermission toUnwrappedModel(
		ResourcePermission resourcePermission) {
		if (resourcePermission instanceof ResourcePermissionImpl) {
			return resourcePermission;
		}

		ResourcePermissionImpl resourcePermissionImpl = new ResourcePermissionImpl();

		resourcePermissionImpl.setNew(resourcePermission.isNew());
		resourcePermissionImpl.setPrimaryKey(resourcePermission.getPrimaryKey());

		resourcePermissionImpl.setResourcePermissionId(resourcePermission.getResourcePermissionId());
		resourcePermissionImpl.setCompanyId(resourcePermission.getCompanyId());
		resourcePermissionImpl.setName(resourcePermission.getName());
		resourcePermissionImpl.setScope(resourcePermission.getScope());
		resourcePermissionImpl.setPrimKey(resourcePermission.getPrimKey());
		resourcePermissionImpl.setRoleId(resourcePermission.getRoleId());
		resourcePermissionImpl.setActionIds(resourcePermission.getActionIds());

		return resourcePermissionImpl;
	}

	/**
	 * Finds the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource permission to find
	 * @return the resource permission
	 * @throws com.liferay.portal.NoSuchModelException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the resource permission with the primary key or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	 *
	 * @param resourcePermissionId the primary key of the resource permission to find
	 * @return the resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByPrimaryKey(long resourcePermissionId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = fetchByPrimaryKey(resourcePermissionId);

		if (resourcePermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					resourcePermissionId);
			}

			throw new NoSuchResourcePermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				resourcePermissionId);
		}

		return resourcePermission;
	}

	/**
	 * Finds the resource permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the resource permission to find
	 * @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the resource permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePermissionId the primary key of the resource permission to find
	 * @return the resource permission, or <code>null</code> if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission fetchByPrimaryKey(long resourcePermissionId)
		throws SystemException {
		ResourcePermission resourcePermission = (ResourcePermission)EntityCacheUtil.getResult(ResourcePermissionModelImpl.ENTITY_CACHE_ENABLED,
				ResourcePermissionImpl.class, resourcePermissionId, this);

		if (resourcePermission == null) {
			Session session = null;

			try {
				session = openSession();

				resourcePermission = (ResourcePermission)session.get(ResourcePermissionImpl.class,
						new Long(resourcePermissionId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (resourcePermission != null) {
					cacheResult(resourcePermission);
				}

				closeSession(session);
			}
		}

		return resourcePermission;
	}

	/**
	 * Finds all the resource permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID to search with
	 * @return the matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByRoleId(long roleId)
		throws SystemException {
		return findByRoleId(roleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the resource permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @return the range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByRoleId(long roleId, int start, int end)
		throws SystemException {
		return findByRoleId(roleId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the resource permissions where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByRoleId(long roleId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				roleId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ROLEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ROLEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ROLEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first resource permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByRoleId_First(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByRoleId(roleId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last resource permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByRoleId_Last(long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByRoleId(roleId);

		List<ResourcePermission> list = findByRoleId(roleId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the resource permissions before and after the current resource permission in the ordered set where roleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param roleId the role ID to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByRoleId_PrevAndNext(session, resourcePermission,
					roleId, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByRoleId_PrevAndNext(session, resourcePermission,
					roleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByRoleId_PrevAndNext(Session session,
		ResourcePermission resourcePermission, long roleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

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

		qPos.add(roleId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourcePermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the resource permissions where roleId = &#63; and scope = &#63;.
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @return the matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByR_S(long roleId, int scope)
		throws SystemException {
		return findByR_S(roleId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Finds a range of all the resource permissions where roleId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @return the range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByR_S(long roleId, int scope,
		int start, int end) throws SystemException {
		return findByR_S(roleId, scope, start, end, null);
	}

	/**
	 * Finds an ordered range of all the resource permissions where roleId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByR_S(long roleId, int scope,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				roleId, scope,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_R_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_R_S_ROLEID_2);

			query.append(_FINDER_COLUMN_R_S_SCOPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				qPos.add(scope);

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_R_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_R_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByR_S_First(long roleId, int scope,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByR_S(roleId, scope, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByR_S_Last(long roleId, int scope,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByR_S(roleId, scope);

		List<ResourcePermission> list = findByR_S(roleId, scope, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("roleId=");
			msg.append(roleId);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the resource permissions before and after the current resource permission in the ordered set where roleId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission[] findByR_S_PrevAndNext(
		long resourcePermissionId, long roleId, int scope,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByR_S_PrevAndNext(session, resourcePermission,
					roleId, scope, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByR_S_PrevAndNext(session, resourcePermission,
					roleId, scope, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByR_S_PrevAndNext(Session session,
		ResourcePermission resourcePermission, long roleId, int scope,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_R_S_ROLEID_2);

		query.append(_FINDER_COLUMN_R_S_SCOPE_2);

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

		qPos.add(roleId);

		qPos.add(scope);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourcePermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @return the matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope) throws SystemException {
		return findByC_N_S(companyId, name, scope, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @return the range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope, int start, int end) throws SystemException {
		return findByC_N_S(companyId, name, scope, start, end, null);
	}

	/**
	 * Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S(long companyId, String name,
		int scope, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, name, scope,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_N_S,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_SCOPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_N_S,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_N_S,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByC_N_S_First(long companyId, String name,
		int scope, OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByC_N_S(companyId, name, scope, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByC_N_S_Last(long companyId, String name,
		int scope, OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByC_N_S(companyId, name, scope);

		List<ResourcePermission> list = findByC_N_S(companyId, name, scope,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission[] findByC_N_S_PrevAndNext(
		long resourcePermissionId, long companyId, String name, int scope,
		OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_N_S_PrevAndNext(session, resourcePermission,
					companyId, name, scope, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_N_S_PrevAndNext(session, resourcePermission,
					companyId, name, scope, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_N_S_PrevAndNext(Session session,
		ResourcePermission resourcePermission, long companyId, String name,
		int scope, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_COMPANYID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_S_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_S_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_N_S_NAME_2);
			}
		}

		query.append(_FINDER_COLUMN_C_N_S_SCOPE_2);

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

		qPos.add(companyId);

		if (name != null) {
			qPos.add(name);
		}

		qPos.add(scope);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourcePermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @return the matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey) throws SystemException {
		return findByC_N_S_P(companyId, name, scope, primKey,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @return the range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey, int start, int end)
		throws SystemException {
		return findByC_N_S_P(companyId, name, scope, primKey, start, end, null);
	}

	/**
	 * Finds an ordered range of all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findByC_N_S_P(long companyId, String name,
		int scope, String primKey, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, name, scope, primKey,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_C_N_S_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

				list = (List<ResourcePermission>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_C_N_S_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_C_N_S_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByC_N_S_P_First(long companyId, String name,
		int scope, String primKey, OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		List<ResourcePermission> list = findByC_N_S_P(companyId, name, scope,
				primKey, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByC_N_S_P_Last(long companyId, String name,
		int scope, String primKey, OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		int count = countByC_N_S_P(companyId, name, scope, primKey);

		List<ResourcePermission> list = findByC_N_S_P(companyId, name, scope,
				primKey, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchResourcePermissionException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the resource permissions before and after the current resource permission in the ordered set where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourcePermissionId the primary key of the current resource permission
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a resource permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission[] findByC_N_S_P_PrevAndNext(
		long resourcePermissionId, long companyId, String name, int scope,
		String primKey, OrderByComparator orderByComparator)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByPrimaryKey(resourcePermissionId);

		Session session = null;

		try {
			session = openSession();

			ResourcePermission[] array = new ResourcePermissionImpl[3];

			array[0] = getByC_N_S_P_PrevAndNext(session, resourcePermission,
					companyId, name, scope, primKey, orderByComparator, true);

			array[1] = resourcePermission;

			array[2] = getByC_N_S_P_PrevAndNext(session, resourcePermission,
					companyId, name, scope, primKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ResourcePermission getByC_N_S_P_PrevAndNext(Session session,
		ResourcePermission resourcePermission, long companyId, String name,
		int scope, String primKey, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

		if (name == null) {
			query.append(_FINDER_COLUMN_C_N_S_P_NAME_1);
		}
		else {
			if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
			}
		}

		query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

		if (primKey == null) {
			query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_1);
		}
		else {
			if (primKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
			}
			else {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
			}
		}

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

		qPos.add(companyId);

		if (name != null) {
			qPos.add(name);
		}

		qPos.add(scope);

		if (primKey != null) {
			qPos.add(primKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(resourcePermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ResourcePermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or throws a {@link com.liferay.portal.NoSuchResourcePermissionException} if it could not be found.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param roleId the role ID to search with
	 * @return the matching resource permission
	 * @throws com.liferay.portal.NoSuchResourcePermissionException if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission findByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = fetchByC_N_S_P_R(companyId,
				name, scope, primKey, roleId);

		if (resourcePermission == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", scope=");
			msg.append(scope);

			msg.append(", primKey=");
			msg.append(primKey);

			msg.append(", roleId=");
			msg.append(roleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchResourcePermissionException(msg.toString());
		}

		return resourcePermission;
	}

	/**
	 * Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param roleId the role ID to search with
	 * @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission fetchByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId) throws SystemException {
		return fetchByC_N_S_P_R(companyId, name, scope, primKey, roleId, true);
	}

	/**
	 * Finds the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param roleId the role ID to search with
	 * @return the matching resource permission, or <code>null</code> if a matching resource permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ResourcePermission fetchByC_N_S_P_R(long companyId, String name,
		int scope, String primKey, long roleId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, name, scope, primKey, roleId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

				qPos.add(roleId);

				List<ResourcePermission> list = q.list();

				result = list;

				ResourcePermission resourcePermission = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
						finderArgs, list);
				}
				else {
					resourcePermission = list.get(0);

					cacheResult(resourcePermission);

					if ((resourcePermission.getCompanyId() != companyId) ||
							(resourcePermission.getName() == null) ||
							!resourcePermission.getName().equals(name) ||
							(resourcePermission.getScope() != scope) ||
							(resourcePermission.getPrimKey() == null) ||
							!resourcePermission.getPrimKey().equals(primKey) ||
							(resourcePermission.getRoleId() != roleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
							finderArgs, resourcePermission);
					}
				}

				return resourcePermission;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N_S_P_R,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (ResourcePermission)result;
			}
		}
	}

	/**
	 * Finds all the resource permissions.
	 *
	 * @return the resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the resource permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @return the range of resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the resource permissions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of resource permissions to return
	 * @param end the upper bound of the range of resource permissions to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public List<ResourcePermission> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<ResourcePermission> list = (List<ResourcePermission>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_RESOURCEPERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_RESOURCEPERMISSION;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<ResourcePermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<ResourcePermission>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the resource permissions where roleId = &#63; from the database.
	 *
	 * @param roleId the role ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRoleId(long roleId) throws SystemException {
		for (ResourcePermission resourcePermission : findByRoleId(roleId)) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
	}

	/**
	 * Removes all the resource permissions where roleId = &#63; and scope = &#63; from the database.
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByR_S(long roleId, int scope) throws SystemException {
		for (ResourcePermission resourcePermission : findByR_S(roleId, scope)) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		for (ResourcePermission resourcePermission : findByC_N_S(companyId,
				name, scope)) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
	}

	/**
	 * Removes all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_N_S_P(long companyId, String name, int scope,
		String primKey) throws SystemException {
		for (ResourcePermission resourcePermission : findByC_N_S_P(companyId,
				name, scope, primKey)) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
	}

	/**
	 * Removes the resource permission where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63; from the database.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param roleId the role ID to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_N_S_P_R(long companyId, String name, int scope,
		String primKey, long roleId)
		throws NoSuchResourcePermissionException, SystemException {
		ResourcePermission resourcePermission = findByC_N_S_P_R(companyId,
				name, scope, primKey, roleId);

		resourcePermissionPersistence.remove(resourcePermission);
	}

	/**
	 * Removes all the resource permissions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ResourcePermission resourcePermission : findAll()) {
			resourcePermissionPersistence.remove(resourcePermission);
		}
	}

	/**
	 * Counts all the resource permissions where roleId = &#63;.
	 *
	 * @param roleId the role ID to search with
	 * @return the number of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRoleId(long roleId) throws SystemException {
		Object[] finderArgs = new Object[] { roleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ROLEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_ROLEID_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ROLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the resource permissions where roleId = &#63; and scope = &#63;.
	 *
	 * @param roleId the role ID to search with
	 * @param scope the scope to search with
	 * @return the number of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByR_S(long roleId, int scope) throws SystemException {
		Object[] finderArgs = new Object[] { roleId, scope };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_R_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_R_S_ROLEID_2);

			query.append(_FINDER_COLUMN_R_S_SCOPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(roleId);

				qPos.add(scope);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_R_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @return the number of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N_S(long companyId, String name, int scope)
		throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name, scope };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_SCOPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @return the number of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N_S_P(long companyId, String name, int scope,
		String primKey) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name, scope, primKey };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_SCOPE_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_PRIMKEY_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the resource permissions where companyId = &#63; and name = &#63; and scope = &#63; and primKey = &#63; and roleId = &#63;.
	 *
	 * @param companyId the company ID to search with
	 * @param name the name to search with
	 * @param scope the scope to search with
	 * @param primKey the prim key to search with
	 * @param roleId the role ID to search with
	 * @return the number of matching resource permissions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_N_S_P_R(long companyId, String name, int scope,
		String primKey, long roleId) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, name, scope, primKey, roleId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_N_S_P_R,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_RESOURCEPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_S_P_R_COMPANYID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_R_NAME_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_SCOPE_2);

			if (primKey == null) {
				query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_1);
			}
			else {
				if (primKey.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3);
				}
				else {
					query.append(_FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2);
				}
			}

			query.append(_FINDER_COLUMN_C_N_S_P_R_ROLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (name != null) {
					qPos.add(name);
				}

				qPos.add(scope);

				if (primKey != null) {
					qPos.add(primKey);
				}

				qPos.add(roleId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N_S_P_R,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the resource permissions.
	 *
	 * @return the number of resource permissions
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

				Query q = session.createQuery(_SQL_COUNT_RESOURCEPERMISSION);

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
	 * Initializes the resource permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ResourcePermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ResourcePermission>> listenersList = new ArrayList<ModelListener<ResourcePermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ResourcePermission>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(ResourcePermissionImpl.class.getName());
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
	@BeanReference(type = LayoutRevisionPersistence.class)
	protected LayoutRevisionPersistence layoutRevisionPersistence;
	@BeanReference(type = LayoutSetPersistence.class)
	protected LayoutSetPersistence layoutSetPersistence;
	@BeanReference(type = LayoutSetBranchPersistence.class)
	protected LayoutSetBranchPersistence layoutSetBranchPersistence;
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
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = RepositoryEntryPersistence.class)
	protected RepositoryEntryPersistence repositoryEntryPersistence;
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
	@BeanReference(type = VirtualHostPersistence.class)
	protected VirtualHostPersistence virtualHostPersistence;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = WebsitePersistence.class)
	protected WebsitePersistence websitePersistence;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	private static final String _SQL_SELECT_RESOURCEPERMISSION = "SELECT resourcePermission FROM ResourcePermission resourcePermission";
	private static final String _SQL_SELECT_RESOURCEPERMISSION_WHERE = "SELECT resourcePermission FROM ResourcePermission resourcePermission WHERE ";
	private static final String _SQL_COUNT_RESOURCEPERMISSION = "SELECT COUNT(resourcePermission) FROM ResourcePermission resourcePermission";
	private static final String _SQL_COUNT_RESOURCEPERMISSION_WHERE = "SELECT COUNT(resourcePermission) FROM ResourcePermission resourcePermission WHERE ";
	private static final String _FINDER_COLUMN_ROLEID_ROLEID_2 = "resourcePermission.roleId = ?";
	private static final String _FINDER_COLUMN_R_S_ROLEID_2 = "resourcePermission.roleId = ? AND ";
	private static final String _FINDER_COLUMN_R_S_SCOPE_2 = "resourcePermission.scope = ?";
	private static final String _FINDER_COLUMN_C_N_S_COMPANYID_2 = "resourcePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_1 = "resourcePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_2 = "resourcePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_NAME_3 = "(resourcePermission.name IS NULL OR resourcePermission.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_S_SCOPE_2 = "resourcePermission.scope = ?";
	private static final String _FINDER_COLUMN_C_N_S_P_COMPANYID_2 = "resourcePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_NAME_1 = "resourcePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_NAME_2 = "resourcePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_NAME_3 = "(resourcePermission.name IS NULL OR resourcePermission.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_SCOPE_2 = "resourcePermission.scope = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_PRIMKEY_1 = "resourcePermission.primKey IS NULL";
	private static final String _FINDER_COLUMN_C_N_S_P_PRIMKEY_2 = "resourcePermission.primKey = ?";
	private static final String _FINDER_COLUMN_C_N_S_P_PRIMKEY_3 = "(resourcePermission.primKey IS NULL OR resourcePermission.primKey = ?)";
	private static final String _FINDER_COLUMN_C_N_S_P_R_COMPANYID_2 = "resourcePermission.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_NAME_1 = "resourcePermission.name IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_NAME_2 = "resourcePermission.name = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_NAME_3 = "(resourcePermission.name IS NULL OR resourcePermission.name = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_SCOPE_2 = "resourcePermission.scope = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_PRIMKEY_1 = "resourcePermission.primKey IS NULL AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_PRIMKEY_2 = "resourcePermission.primKey = ? AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_PRIMKEY_3 = "(resourcePermission.primKey IS NULL OR resourcePermission.primKey = ?) AND ";
	private static final String _FINDER_COLUMN_C_N_S_P_R_ROLEID_2 = "resourcePermission.roleId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "resourcePermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ResourcePermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ResourcePermission exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(ResourcePermissionPersistenceImpl.class);
}