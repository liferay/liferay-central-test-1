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

package com.liferay.portlet.social.service.persistence;

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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.social.NoSuchEquitySettingException;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.impl.SocialEquitySettingImpl;
import com.liferay.portlet.social.model.impl.SocialEquitySettingModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the social equity setting service.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Never reference this class directly, use {@link SocialEquitySettingUtil} instead.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquitySettingPersistence
 * @see SocialEquitySettingUtil
 * @generated
 */
public class SocialEquitySettingPersistenceImpl extends BasePersistenceImpl<SocialEquitySetting>
	implements SocialEquitySettingPersistence {
	public static final String FINDER_CLASS_NAME_ENTITY = SocialEquitySettingImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_G_C_A = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_A = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_A_T = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_A_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_A_T = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByG_C_A_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the social equity setting in the entity cache if it is enabled.
	 *
	 * @param socialEquitySetting the social equity setting to cache
	 */
	public void cacheResult(SocialEquitySetting socialEquitySetting) {
		EntityCacheUtil.putResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey(),
			socialEquitySetting);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_T,
			new Object[] {
				new Long(socialEquitySetting.getGroupId()),
				new Long(socialEquitySetting.getClassNameId()),
				
			socialEquitySetting.getActionId(),
				new Integer(socialEquitySetting.getType())
			}, socialEquitySetting);
	}

	/**
	 * Caches the social equity settings in the entity cache if it is enabled.
	 *
	 * @param socialEquitySettings the social equity settings to cache
	 */
	public void cacheResult(List<SocialEquitySetting> socialEquitySettings) {
		for (SocialEquitySetting socialEquitySetting : socialEquitySettings) {
			if (EntityCacheUtil.getResult(
						SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
						SocialEquitySettingImpl.class,
						socialEquitySetting.getPrimaryKey(), this) == null) {
				cacheResult(socialEquitySetting);
			}
		}
	}

	/**
	 * Clears the cache for all social equity settings.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(SocialEquitySettingImpl.class.getName());
		EntityCacheUtil.clearCache(SocialEquitySettingImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the social equity setting.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(SocialEquitySetting socialEquitySetting) {
		EntityCacheUtil.removeResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_T,
			new Object[] {
				new Long(socialEquitySetting.getGroupId()),
				new Long(socialEquitySetting.getClassNameId()),
				
			socialEquitySetting.getActionId(),
				new Integer(socialEquitySetting.getType())
			});
	}

	/**
	 * Creates a new social equity setting with the primary key. Does not add the social equity setting to the database.
	 *
	 * @param equitySettingId the primary key for the new social equity setting
	 * @return the new social equity setting
	 */
	public SocialEquitySetting create(long equitySettingId) {
		SocialEquitySetting socialEquitySetting = new SocialEquitySettingImpl();

		socialEquitySetting.setNew(true);
		socialEquitySetting.setPrimaryKey(equitySettingId);

		return socialEquitySetting;
	}

	/**
	 * Removes the social equity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the social equity setting to remove
	 * @return the social equity setting that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the social equity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param equitySettingId the primary key of the social equity setting to remove
	 * @return the social equity setting that was removed
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting remove(long equitySettingId)
		throws NoSuchEquitySettingException, SystemException {
		Session session = null;

		try {
			session = openSession();

			SocialEquitySetting socialEquitySetting = (SocialEquitySetting)session.get(SocialEquitySettingImpl.class,
					new Long(equitySettingId));

			if (socialEquitySetting == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						equitySettingId);
				}

				throw new NoSuchEquitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					equitySettingId);
			}

			return remove(socialEquitySetting);
		}
		catch (NoSuchEquitySettingException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquitySetting removeImpl(
		SocialEquitySetting socialEquitySetting) throws SystemException {
		socialEquitySetting = toUnwrappedModel(socialEquitySetting);

		Session session = null;

		try {
			session = openSession();

			if (socialEquitySetting.isCachedModel() ||
					BatchSessionUtil.isEnabled()) {
				Object staleObject = session.get(SocialEquitySettingImpl.class,
						socialEquitySetting.getPrimaryKeyObj());

				if (staleObject != null) {
					session.evict(staleObject);
				}
			}

			session.delete(socialEquitySetting);

			session.flush();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		SocialEquitySettingModelImpl socialEquitySettingModelImpl = (SocialEquitySettingModelImpl)socialEquitySetting;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_T,
			new Object[] {
				new Long(socialEquitySettingModelImpl.getOriginalGroupId()),
				new Long(socialEquitySettingModelImpl.getOriginalClassNameId()),
				
			socialEquitySettingModelImpl.getOriginalActionId(),
				new Integer(socialEquitySettingModelImpl.getOriginalType())
			});

		EntityCacheUtil.removeResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey());

		return socialEquitySetting;
	}

	public SocialEquitySetting updateImpl(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge) throws SystemException {
		socialEquitySetting = toUnwrappedModel(socialEquitySetting);

		boolean isNew = socialEquitySetting.isNew();

		SocialEquitySettingModelImpl socialEquitySettingModelImpl = (SocialEquitySettingModelImpl)socialEquitySetting;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, socialEquitySetting, merge);

			socialEquitySetting.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
			SocialEquitySettingImpl.class, socialEquitySetting.getPrimaryKey(),
			socialEquitySetting);

		if (!isNew &&
				((socialEquitySetting.getGroupId() != socialEquitySettingModelImpl.getOriginalGroupId()) ||
				(socialEquitySetting.getClassNameId() != socialEquitySettingModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(socialEquitySetting.getActionId(),
					socialEquitySettingModelImpl.getOriginalActionId()) ||
				(socialEquitySetting.getType() != socialEquitySettingModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_A_T,
				new Object[] {
					new Long(socialEquitySettingModelImpl.getOriginalGroupId()),
					new Long(socialEquitySettingModelImpl.getOriginalClassNameId()),
					
				socialEquitySettingModelImpl.getOriginalActionId(),
					new Integer(socialEquitySettingModelImpl.getOriginalType())
				});
		}

		if (isNew ||
				((socialEquitySetting.getGroupId() != socialEquitySettingModelImpl.getOriginalGroupId()) ||
				(socialEquitySetting.getClassNameId() != socialEquitySettingModelImpl.getOriginalClassNameId()) ||
				!Validator.equals(socialEquitySetting.getActionId(),
					socialEquitySettingModelImpl.getOriginalActionId()) ||
				(socialEquitySetting.getType() != socialEquitySettingModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_T,
				new Object[] {
					new Long(socialEquitySetting.getGroupId()),
					new Long(socialEquitySetting.getClassNameId()),
					
				socialEquitySetting.getActionId(),
					new Integer(socialEquitySetting.getType())
				}, socialEquitySetting);
		}

		return socialEquitySetting;
	}

	protected SocialEquitySetting toUnwrappedModel(
		SocialEquitySetting socialEquitySetting) {
		if (socialEquitySetting instanceof SocialEquitySettingImpl) {
			return socialEquitySetting;
		}

		SocialEquitySettingImpl socialEquitySettingImpl = new SocialEquitySettingImpl();

		socialEquitySettingImpl.setNew(socialEquitySetting.isNew());
		socialEquitySettingImpl.setPrimaryKey(socialEquitySetting.getPrimaryKey());

		socialEquitySettingImpl.setEquitySettingId(socialEquitySetting.getEquitySettingId());
		socialEquitySettingImpl.setGroupId(socialEquitySetting.getGroupId());
		socialEquitySettingImpl.setCompanyId(socialEquitySetting.getCompanyId());
		socialEquitySettingImpl.setClassNameId(socialEquitySetting.getClassNameId());
		socialEquitySettingImpl.setActionId(socialEquitySetting.getActionId());
		socialEquitySettingImpl.setDailyLimit(socialEquitySetting.getDailyLimit());
		socialEquitySettingImpl.setLifespan(socialEquitySetting.getLifespan());
		socialEquitySettingImpl.setType(socialEquitySetting.getType());
		socialEquitySettingImpl.setUniqueEntry(socialEquitySetting.isUniqueEntry());
		socialEquitySettingImpl.setValue(socialEquitySetting.getValue());

		return socialEquitySettingImpl;
	}

	/**
	 * Finds the social equity setting with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity setting to find
	 * @return the social equity setting
	 * @throws com.liferay.portal.NoSuchModelException if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the social equity setting with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	 *
	 * @param equitySettingId the primary key of the social equity setting to find
	 * @return the social equity setting
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting findByPrimaryKey(long equitySettingId)
		throws NoSuchEquitySettingException, SystemException {
		SocialEquitySetting socialEquitySetting = fetchByPrimaryKey(equitySettingId);

		if (socialEquitySetting == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + equitySettingId);
			}

			throw new NoSuchEquitySettingException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				equitySettingId);
		}

		return socialEquitySetting;
	}

	/**
	 * Finds the social equity setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the social equity setting to find
	 * @return the social equity setting, or <code>null</code> if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the social equity setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param equitySettingId the primary key of the social equity setting to find
	 * @return the social equity setting, or <code>null</code> if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting fetchByPrimaryKey(long equitySettingId)
		throws SystemException {
		SocialEquitySetting socialEquitySetting = (SocialEquitySetting)EntityCacheUtil.getResult(SocialEquitySettingModelImpl.ENTITY_CACHE_ENABLED,
				SocialEquitySettingImpl.class, equitySettingId, this);

		if (socialEquitySetting == null) {
			Session session = null;

			try {
				session = openSession();

				socialEquitySetting = (SocialEquitySetting)session.get(SocialEquitySettingImpl.class,
						new Long(equitySettingId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (socialEquitySetting != null) {
					cacheResult(socialEquitySetting);
				}

				closeSession(session);
			}
		}

		return socialEquitySetting;
	}

	/**
	 * Finds all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @return the matching social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findByG_C_A(long groupId,
		long classNameId, String actionId) throws SystemException {
		return findByG_C_A(groupId, classNameId, actionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param start the lower bound of the range of social equity settings to return
	 * @param end the upper bound of the range of social equity settings to return (not inclusive)
	 * @return the range of matching social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findByG_C_A(long groupId,
		long classNameId, String actionId, int start, int end)
		throws SystemException {
		return findByG_C_A(groupId, classNameId, actionId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param start the lower bound of the range of social equity settings to return
	 * @param end the upper bound of the range of social equity settings to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findByG_C_A(long groupId,
		long classNameId, String actionId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, classNameId, actionId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquitySetting> list = (List<SocialEquitySetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_C_A,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(5 +
							(orderByComparator.getOrderByFields().length * 3));
				}
				else {
					query = new StringBundler(4);
				}

				query.append(_SQL_SELECT_SOCIALEQUITYSETTING_WHERE);

				query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_G_C_A_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_C_A_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_C_A_ACTIONID_2);
					}
				}

				if (orderByComparator != null) {
					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				list = (List<SocialEquitySetting>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquitySetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_C_A,
					finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching social equity setting
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting findByG_C_A_First(long groupId,
		long classNameId, String actionId, OrderByComparator orderByComparator)
		throws NoSuchEquitySettingException, SystemException {
		List<SocialEquitySetting> list = findByG_C_A(groupId, classNameId,
				actionId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquitySettingException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching social equity setting
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting findByG_C_A_Last(long groupId, long classNameId,
		String actionId, OrderByComparator orderByComparator)
		throws NoSuchEquitySettingException, SystemException {
		int count = countByG_C_A(groupId, classNameId, actionId);

		List<SocialEquitySetting> list = findByG_C_A(groupId, classNameId,
				actionId, count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchEquitySettingException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the social equity settings before and after the current social equity setting in the ordered set where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param equitySettingId the primary key of the current social equity setting
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next social equity setting
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a social equity setting with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting[] findByG_C_A_PrevAndNext(long equitySettingId,
		long groupId, long classNameId, String actionId,
		OrderByComparator orderByComparator)
		throws NoSuchEquitySettingException, SystemException {
		SocialEquitySetting socialEquitySetting = findByPrimaryKey(equitySettingId);

		Session session = null;

		try {
			session = openSession();

			SocialEquitySetting[] array = new SocialEquitySettingImpl[3];

			array[0] = getByG_C_A_PrevAndNext(session, socialEquitySetting,
					groupId, classNameId, actionId, orderByComparator, true);

			array[1] = socialEquitySetting;

			array[2] = getByG_C_A_PrevAndNext(session, socialEquitySetting,
					groupId, classNameId, actionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SocialEquitySetting getByG_C_A_PrevAndNext(Session session,
		SocialEquitySetting socialEquitySetting, long groupId,
		long classNameId, String actionId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SOCIALEQUITYSETTING_WHERE);

		query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

		if (actionId == null) {
			query.append(_FINDER_COLUMN_G_C_A_ACTIONID_1);
		}
		else {
			if (actionId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_A_ACTIONID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_C_A_ACTIONID_2);
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

		qPos.add(groupId);

		qPos.add(classNameId);

		if (actionId != null) {
			qPos.add(actionId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(socialEquitySetting);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SocialEquitySetting> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquitySettingException} if it could not be found.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param type the type to search with
	 * @return the matching social equity setting
	 * @throws com.liferay.portlet.social.NoSuchEquitySettingException if a matching social equity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting findByG_C_A_T(long groupId, long classNameId,
		String actionId, int type)
		throws NoSuchEquitySettingException, SystemException {
		SocialEquitySetting socialEquitySetting = fetchByG_C_A_T(groupId,
				classNameId, actionId, type);

		if (socialEquitySetting == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", actionId=");
			msg.append(actionId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEquitySettingException(msg.toString());
		}

		return socialEquitySetting;
	}

	/**
	 * Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param type the type to search with
	 * @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting fetchByG_C_A_T(long groupId, long classNameId,
		String actionId, int type) throws SystemException {
		return fetchByG_C_A_T(groupId, classNameId, actionId, type, true);
	}

	/**
	 * Finds the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param type the type to search with
	 * @return the matching social equity setting, or <code>null</code> if a matching social equity setting could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public SocialEquitySetting fetchByG_C_A_T(long groupId, long classNameId,
		String actionId, int type, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, actionId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_A_T,
					finderArgs, this);
		}

		if (result == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_SELECT_SOCIALEQUITYSETTING_WHERE);

				query.append(_FINDER_COLUMN_G_C_A_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_A_T_CLASSNAMEID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_C_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(type);

				List<SocialEquitySetting> list = q.list();

				result = list;

				SocialEquitySetting socialEquitySetting = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_T,
						finderArgs, list);
				}
				else {
					socialEquitySetting = list.get(0);

					cacheResult(socialEquitySetting);

					if ((socialEquitySetting.getGroupId() != groupId) ||
							(socialEquitySetting.getClassNameId() != classNameId) ||
							(socialEquitySetting.getActionId() == null) ||
							!socialEquitySetting.getActionId().equals(actionId) ||
							(socialEquitySetting.getType() != type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_T,
							finderArgs, socialEquitySetting);
					}
				}

				return socialEquitySetting;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_A_T,
						finderArgs, new ArrayList<SocialEquitySetting>());
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (SocialEquitySetting)result;
			}
		}
	}

	/**
	 * Finds all the social equity settings.
	 *
	 * @return the social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the social equity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity settings to return
	 * @param end the upper bound of the range of social equity settings to return (not inclusive)
	 * @return the range of social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the social equity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of social equity settings to return
	 * @param end the upper bound of the range of social equity settings to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public List<SocialEquitySetting> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<SocialEquitySetting> list = (List<SocialEquitySetting>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
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

					query.append(_SQL_SELECT_SOCIALEQUITYSETTING);

					appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
						orderByComparator);

					sql = query.toString();
				}
				else {
					sql = _SQL_SELECT_SOCIALEQUITYSETTING;
				}

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<SocialEquitySetting>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<SocialEquitySetting>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					list = new ArrayList<SocialEquitySetting>();
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs, list);

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_A(long groupId, long classNameId, String actionId)
		throws SystemException {
		for (SocialEquitySetting socialEquitySetting : findByG_C_A(groupId,
				classNameId, actionId)) {
			remove(socialEquitySetting);
		}
	}

	/**
	 * Removes the social equity setting where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param type the type to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_C_A_T(long groupId, long classNameId,
		String actionId, int type)
		throws NoSuchEquitySettingException, SystemException {
		SocialEquitySetting socialEquitySetting = findByG_C_A_T(groupId,
				classNameId, actionId, type);

		remove(socialEquitySetting);
	}

	/**
	 * Removes all the social equity settings from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (SocialEquitySetting socialEquitySetting : findAll()) {
			remove(socialEquitySetting);
		}
	}

	/**
	 * Counts all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @return the number of matching social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_A(long groupId, long classNameId, String actionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, actionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_A,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(4);

				query.append(_SQL_COUNT_SOCIALEQUITYSETTING_WHERE);

				query.append(_FINDER_COLUMN_G_C_A_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_A_CLASSNAMEID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_G_C_A_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_C_A_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_C_A_ACTIONID_2);
					}
				}

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (actionId != null) {
					qPos.add(actionId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the social equity settings where groupId = &#63; and classNameId = &#63; and actionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group id to search with
	 * @param classNameId the class name id to search with
	 * @param actionId the action id to search with
	 * @param type the type to search with
	 * @return the number of matching social equity settings
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_C_A_T(long groupId, long classNameId, String actionId,
		int type) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classNameId, actionId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_C_A_T,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				StringBundler query = new StringBundler(5);

				query.append(_SQL_COUNT_SOCIALEQUITYSETTING_WHERE);

				query.append(_FINDER_COLUMN_G_C_A_T_GROUPID_2);

				query.append(_FINDER_COLUMN_G_C_A_T_CLASSNAMEID_2);

				if (actionId == null) {
					query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_1);
				}
				else {
					if (actionId.equals(StringPool.BLANK)) {
						query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_3);
					}
					else {
						query.append(_FINDER_COLUMN_G_C_A_T_ACTIONID_2);
					}
				}

				query.append(_FINDER_COLUMN_G_C_A_T_TYPE_2);

				String sql = query.toString();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (actionId != null) {
					qPos.add(actionId);
				}

				qPos.add(type);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_A_T,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the social equity settings.
	 *
	 * @return the number of social equity settings
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

				Query q = session.createQuery(_SQL_COUNT_SOCIALEQUITYSETTING);

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
	 * Initializes the social equity setting persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.social.model.SocialEquitySetting")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<SocialEquitySetting>> listenersList = new ArrayList<ModelListener<SocialEquitySetting>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<SocialEquitySetting>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialEquityAssetEntryPersistence.class)
	protected SocialEquityAssetEntryPersistence socialEquityAssetEntryPersistence;
	@BeanReference(type = SocialEquityHistoryPersistence.class)
	protected SocialEquityHistoryPersistence socialEquityHistoryPersistence;
	@BeanReference(type = SocialEquityLogPersistence.class)
	protected SocialEquityLogPersistence socialEquityLogPersistence;
	@BeanReference(type = SocialEquitySettingPersistence.class)
	protected SocialEquitySettingPersistence socialEquitySettingPersistence;
	@BeanReference(type = SocialEquityUserPersistence.class)
	protected SocialEquityUserPersistence socialEquityUserPersistence;
	@BeanReference(type = SocialRelationPersistence.class)
	protected SocialRelationPersistence socialRelationPersistence;
	@BeanReference(type = SocialRequestPersistence.class)
	protected SocialRequestPersistence socialRequestPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_SOCIALEQUITYSETTING = "SELECT socialEquitySetting FROM SocialEquitySetting socialEquitySetting";
	private static final String _SQL_SELECT_SOCIALEQUITYSETTING_WHERE = "SELECT socialEquitySetting FROM SocialEquitySetting socialEquitySetting WHERE ";
	private static final String _SQL_COUNT_SOCIALEQUITYSETTING = "SELECT COUNT(socialEquitySetting) FROM SocialEquitySetting socialEquitySetting";
	private static final String _SQL_COUNT_SOCIALEQUITYSETTING_WHERE = "SELECT COUNT(socialEquitySetting) FROM SocialEquitySetting socialEquitySetting WHERE ";
	private static final String _FINDER_COLUMN_G_C_A_GROUPID_2 = "socialEquitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_CLASSNAMEID_2 = "socialEquitySetting.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_ACTIONID_1 = "socialEquitySetting.actionId IS NULL";
	private static final String _FINDER_COLUMN_G_C_A_ACTIONID_2 = "socialEquitySetting.actionId = ?";
	private static final String _FINDER_COLUMN_G_C_A_ACTIONID_3 = "(socialEquitySetting.actionId IS NULL OR socialEquitySetting.actionId = ?)";
	private static final String _FINDER_COLUMN_G_C_A_T_GROUPID_2 = "socialEquitySetting.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_T_CLASSNAMEID_2 = "socialEquitySetting.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_T_ACTIONID_1 = "socialEquitySetting.actionId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_C_A_T_ACTIONID_2 = "socialEquitySetting.actionId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_A_T_ACTIONID_3 = "(socialEquitySetting.actionId IS NULL OR socialEquitySetting.actionId = ?) AND ";
	private static final String _FINDER_COLUMN_G_C_A_T_TYPE_2 = "socialEquitySetting.type = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "socialEquitySetting.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SocialEquitySetting exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SocialEquitySetting exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(SocialEquitySettingPersistenceImpl.class);
}