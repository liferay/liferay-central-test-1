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
import com.liferay.portal.NoSuchUserIdMapperException;
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
import com.liferay.portal.model.UserIdMapper;
import com.liferay.portal.model.impl.UserIdMapperImpl;
import com.liferay.portal.model.impl.UserIdMapperModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the user id mapper service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapperPersistence
 * @see UserIdMapperUtil
 * @generated
 */
public class UserIdMapperPersistenceImpl extends BasePersistenceImpl<UserIdMapper>
	implements UserIdMapperPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserIdMapperUtil} to access the user id mapper persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserIdMapperImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_USERID = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findByUserId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByUserId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_U_T = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_U_T = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByU_T",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_T_E = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_E",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_T_E = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countByT_E",
			new String[] { String.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
			"countAll", new String[0]);

	/**
	 * Caches the user id mapper in the entity cache if it is enabled.
	 *
	 * @param userIdMapper the user id mapper to cache
	 */
	public void cacheResult(UserIdMapper userIdMapper) {
		EntityCacheUtil.putResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey(), userIdMapper);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(userIdMapper.getUserId()),
				
			userIdMapper.getType()
			}, userIdMapper);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
			new Object[] {
				userIdMapper.getType(),
				
			userIdMapper.getExternalUserId()
			}, userIdMapper);
	}

	/**
	 * Caches the user id mappers in the entity cache if it is enabled.
	 *
	 * @param userIdMappers the user id mappers to cache
	 */
	public void cacheResult(List<UserIdMapper> userIdMappers) {
		for (UserIdMapper userIdMapper : userIdMappers) {
			if (EntityCacheUtil.getResult(
						UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
						UserIdMapperImpl.class, userIdMapper.getPrimaryKey(),
						this) == null) {
				cacheResult(userIdMapper);
			}
		}
	}

	/**
	 * Clears the cache for all user id mappers.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache() {
		CacheRegistryUtil.clear(UserIdMapperImpl.class.getName());
		EntityCacheUtil.clearCache(UserIdMapperImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the user id mapper.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	public void clearCache(UserIdMapper userIdMapper) {
		EntityCacheUtil.removeResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(userIdMapper.getUserId()),
				
			userIdMapper.getType()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
			new Object[] {
				userIdMapper.getType(),
				
			userIdMapper.getExternalUserId()
			});
	}

	/**
	 * Creates a new user id mapper with the primary key. Does not add the user id mapper to the database.
	 *
	 * @param userIdMapperId the primary key for the new user id mapper
	 * @return the new user id mapper
	 */
	public UserIdMapper create(long userIdMapperId) {
		UserIdMapper userIdMapper = new UserIdMapperImpl();

		userIdMapper.setNew(true);
		userIdMapper.setPrimaryKey(userIdMapperId);

		return userIdMapper;
	}

	/**
	 * Removes the user id mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user id mapper to remove
	 * @return the user id mapper that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the user id mapper with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userIdMapperId the primary key of the user id mapper to remove
	 * @return the user id mapper that was removed
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper remove(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		Session session = null;

		try {
			session = openSession();

			UserIdMapper userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
					new Long(userIdMapperId));

			if (userIdMapper == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						userIdMapperId);
				}

				throw new NoSuchUserIdMapperException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					userIdMapperId);
			}

			return remove(userIdMapper);
		}
		catch (NoSuchUserIdMapperException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected UserIdMapper removeImpl(UserIdMapper userIdMapper)
		throws SystemException {
		userIdMapper = toUnwrappedModel(userIdMapper);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, userIdMapper);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		UserIdMapperModelImpl userIdMapperModelImpl = (UserIdMapperModelImpl)userIdMapper;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
			new Object[] {
				new Long(userIdMapperModelImpl.getOriginalUserId()),
				
			userIdMapperModelImpl.getOriginalType()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
			new Object[] {
				userIdMapperModelImpl.getOriginalType(),
				
			userIdMapperModelImpl.getOriginalExternalUserId()
			});

		EntityCacheUtil.removeResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey());

		return userIdMapper;
	}

	public UserIdMapper updateImpl(
		com.liferay.portal.model.UserIdMapper userIdMapper, boolean merge)
		throws SystemException {
		userIdMapper = toUnwrappedModel(userIdMapper);

		boolean isNew = userIdMapper.isNew();

		UserIdMapperModelImpl userIdMapperModelImpl = (UserIdMapperModelImpl)userIdMapper;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, userIdMapper, merge);

			userIdMapper.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
			UserIdMapperImpl.class, userIdMapper.getPrimaryKey(), userIdMapper);

		if (!isNew &&
				((userIdMapper.getUserId() != userIdMapperModelImpl.getOriginalUserId()) ||
				!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(userIdMapperModelImpl.getOriginalUserId()),
					
				userIdMapperModelImpl.getOriginalType()
				});
		}

		if (isNew ||
				((userIdMapper.getUserId() != userIdMapperModelImpl.getOriginalUserId()) ||
				!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
				new Object[] {
					new Long(userIdMapper.getUserId()),
					
				userIdMapper.getType()
				}, userIdMapper);
		}

		if (!isNew &&
				(!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()) ||
				!Validator.equals(userIdMapper.getExternalUserId(),
					userIdMapperModelImpl.getOriginalExternalUserId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
				new Object[] {
					userIdMapperModelImpl.getOriginalType(),
					
				userIdMapperModelImpl.getOriginalExternalUserId()
				});
		}

		if (isNew ||
				(!Validator.equals(userIdMapper.getType(),
					userIdMapperModelImpl.getOriginalType()) ||
				!Validator.equals(userIdMapper.getExternalUserId(),
					userIdMapperModelImpl.getOriginalExternalUserId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
				new Object[] {
					userIdMapper.getType(),
					
				userIdMapper.getExternalUserId()
				}, userIdMapper);
		}

		return userIdMapper;
	}

	protected UserIdMapper toUnwrappedModel(UserIdMapper userIdMapper) {
		if (userIdMapper instanceof UserIdMapperImpl) {
			return userIdMapper;
		}

		UserIdMapperImpl userIdMapperImpl = new UserIdMapperImpl();

		userIdMapperImpl.setNew(userIdMapper.isNew());
		userIdMapperImpl.setPrimaryKey(userIdMapper.getPrimaryKey());

		userIdMapperImpl.setUserIdMapperId(userIdMapper.getUserIdMapperId());
		userIdMapperImpl.setUserId(userIdMapper.getUserId());
		userIdMapperImpl.setType(userIdMapper.getType());
		userIdMapperImpl.setDescription(userIdMapper.getDescription());
		userIdMapperImpl.setExternalUserId(userIdMapper.getExternalUserId());

		return userIdMapperImpl;
	}

	/**
	 * Finds the user id mapper with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user id mapper to find
	 * @return the user id mapper
	 * @throws com.liferay.portal.NoSuchModelException if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the user id mapper with the primary key or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user id mapper to find
	 * @return the user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByPrimaryKey(long userIdMapperId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByPrimaryKey(userIdMapperId);

		if (userIdMapper == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + userIdMapperId);
			}

			throw new NoSuchUserIdMapperException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				userIdMapperId);
		}

		return userIdMapper;
	}

	/**
	 * Finds the user id mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user id mapper to find
	 * @return the user id mapper, or <code>null</code> if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Finds the user id mapper with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userIdMapperId the primary key of the user id mapper to find
	 * @return the user id mapper, or <code>null</code> if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByPrimaryKey(long userIdMapperId)
		throws SystemException {
		UserIdMapper userIdMapper = (UserIdMapper)EntityCacheUtil.getResult(UserIdMapperModelImpl.ENTITY_CACHE_ENABLED,
				UserIdMapperImpl.class, userIdMapperId, this);

		if (userIdMapper == null) {
			Session session = null;

			try {
				session = openSession();

				userIdMapper = (UserIdMapper)session.get(UserIdMapperImpl.class,
						new Long(userIdMapperId));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (userIdMapper != null) {
					cacheResult(userIdMapper);
				}

				closeSession(session);
			}
		}

		return userIdMapper;
	}

	/**
	 * Finds all the user id mappers where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the user id mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of user id mappers to return
	 * @param end the upper bound of the range of user id mappers to return (not inclusive)
	 * @return the range of matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Finds an ordered range of all the user id mappers where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param start the lower bound of the range of user id mappers to return
	 * @param end the upper bound of the range of user id mappers to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findByUserId(long userId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				userId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserIdMapper> list = (List<UserIdMapper>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_USERID,
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

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

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

				qPos.add(userId);

				list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_USERID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Finds the first user id mapper in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the first matching user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserIdMapperException, SystemException {
		List<UserIdMapper> list = findByUserId(userId, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the last user id mapper in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the last matching user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchUserIdMapperException, SystemException {
		int count = countByUserId(userId);

		List<UserIdMapper> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchUserIdMapperException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Finds the user id mappers before and after the current user id mapper in the ordered set where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param userIdMapperId the primary key of the current user id mapper
	 * @param userId the user id to search with
	 * @param orderByComparator the comparator to order the set by
	 * @return the previous, current, and next user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a user id mapper with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper[] findByUserId_PrevAndNext(long userIdMapperId,
		long userId, OrderByComparator orderByComparator)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByPrimaryKey(userIdMapperId);

		Session session = null;

		try {
			session = openSession();

			UserIdMapper[] array = new UserIdMapperImpl[3];

			array[0] = getByUserId_PrevAndNext(session, userIdMapper, userId,
					orderByComparator, true);

			array[1] = userIdMapper;

			array[2] = getByUserId_PrevAndNext(session, userIdMapper, userId,
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

	protected UserIdMapper getByUserId_PrevAndNext(Session session,
		UserIdMapper userIdMapper, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(userIdMapper);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<UserIdMapper> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Finds the user id mapper where userId = &#63; and type = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	 *
	 * @param userId the user id to search with
	 * @param type the type to search with
	 * @return the matching user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByU_T(userId, type);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	/**
	 * Finds the user id mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user id to search with
	 * @param type the type to search with
	 * @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByU_T(long userId, String type)
		throws SystemException {
		return fetchByU_T(userId, type, true);
	}

	/**
	 * Finds the user id mapper where userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user id to search with
	 * @param type the type to search with
	 * @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByU_T(long userId, String type,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { userId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_U_T,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
				}

				List<UserIdMapper> list = q.list();

				result = list;

				UserIdMapper userIdMapper = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
						finderArgs, list);
				}
				else {
					userIdMapper = list.get(0);

					cacheResult(userIdMapper);

					if ((userIdMapper.getUserId() != userId) ||
							(userIdMapper.getType() == null) ||
							!userIdMapper.getType().equals(type)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_U_T,
							finderArgs, userIdMapper);
					}
				}

				return userIdMapper;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_U_T,
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
				return (UserIdMapper)result;
			}
		}
	}

	/**
	 * Finds the user id mapper where type = &#63; and externalUserId = &#63; or throws a {@link com.liferay.portal.NoSuchUserIdMapperException} if it could not be found.
	 *
	 * @param type the type to search with
	 * @param externalUserId the external user id to search with
	 * @return the matching user id mapper
	 * @throws com.liferay.portal.NoSuchUserIdMapperException if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper findByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = fetchByT_E(type, externalUserId);

		if (userIdMapper == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("type=");
			msg.append(type);

			msg.append(", externalUserId=");
			msg.append(externalUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserIdMapperException(msg.toString());
		}

		return userIdMapper;
	}

	/**
	 * Finds the user id mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param type the type to search with
	 * @param externalUserId the external user id to search with
	 * @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByT_E(String type, String externalUserId)
		throws SystemException {
		return fetchByT_E(type, externalUserId, true);
	}

	/**
	 * Finds the user id mapper where type = &#63; and externalUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param type the type to search with
	 * @param externalUserId the external user id to search with
	 * @return the matching user id mapper, or <code>null</code> if a matching user id mapper could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public UserIdMapper fetchByT_E(String type, String externalUserId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { type, externalUserId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_E,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USERIDMAPPER_WHERE);

			if (type == null) {
				query.append(_FINDER_COLUMN_T_E_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_E_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_E_TYPE_2);
				}
			}

			if (externalUserId == null) {
				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_1);
			}
			else {
				if (externalUserId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
				}

				List<UserIdMapper> list = q.list();

				result = list;

				UserIdMapper userIdMapper = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
						finderArgs, list);
				}
				else {
					userIdMapper = list.get(0);

					cacheResult(userIdMapper);

					if ((userIdMapper.getType() == null) ||
							!userIdMapper.getType().equals(type) ||
							(userIdMapper.getExternalUserId() == null) ||
							!userIdMapper.getExternalUserId()
											 .equals(externalUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_E,
							finderArgs, userIdMapper);
					}
				}

				return userIdMapper;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_E,
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
				return (UserIdMapper)result;
			}
		}
	}

	/**
	 * Finds all the user id mappers.
	 *
	 * @return the user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Finds a range of all the user id mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user id mappers to return
	 * @param end the upper bound of the range of user id mappers to return (not inclusive)
	 * @return the range of user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Finds an ordered range of all the user id mappers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of user id mappers to return
	 * @param end the upper bound of the range of user id mappers to return (not inclusive)
	 * @param orderByComparator the comparator to order the results by
	 * @return the ordered range of user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public List<UserIdMapper> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<UserIdMapper> list = (List<UserIdMapper>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USERIDMAPPER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USERIDMAPPER;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<UserIdMapper>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the user id mappers where userId = &#63; from the database.
	 *
	 * @param userId the user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUserId(long userId) throws SystemException {
		for (UserIdMapper userIdMapper : findByUserId(userId)) {
			remove(userIdMapper);
		}
	}

	/**
	 * Removes the user id mapper where userId = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user id to search with
	 * @param type the type to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByU_T(long userId, String type)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByU_T(userId, type);

		remove(userIdMapper);
	}

	/**
	 * Removes the user id mapper where type = &#63; and externalUserId = &#63; from the database.
	 *
	 * @param type the type to search with
	 * @param externalUserId the external user id to search with
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_E(String type, String externalUserId)
		throws NoSuchUserIdMapperException, SystemException {
		UserIdMapper userIdMapper = findByT_E(type, externalUserId);

		remove(userIdMapper);
	}

	/**
	 * Removes all the user id mappers from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (UserIdMapper userIdMapper : findAll()) {
			remove(userIdMapper);
		}
	}

	/**
	 * Counts all the user id mappers where userId = &#63;.
	 *
	 * @param userId the user id to search with
	 * @return the number of matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUserId(long userId) throws SystemException {
		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_USERID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

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
	 * Counts all the user id mappers where userId = &#63; and type = &#63;.
	 *
	 * @param userId the user id to search with
	 * @param type the type to search with
	 * @return the number of matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByU_T(long userId, String type) throws SystemException {
		Object[] finderArgs = new Object[] { userId, type };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_U_T,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			query.append(_FINDER_COLUMN_U_T_USERID_2);

			if (type == null) {
				query.append(_FINDER_COLUMN_U_T_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_U_T_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_U_T_TYPE_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (type != null) {
					qPos.add(type);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_U_T, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the user id mappers where type = &#63; and externalUserId = &#63;.
	 *
	 * @param type the type to search with
	 * @param externalUserId the external user id to search with
	 * @return the number of matching user id mappers
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_E(String type, String externalUserId)
		throws SystemException {
		Object[] finderArgs = new Object[] { type, externalUserId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_T_E,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USERIDMAPPER_WHERE);

			if (type == null) {
				query.append(_FINDER_COLUMN_T_E_TYPE_1);
			}
			else {
				if (type.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_E_TYPE_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_E_TYPE_2);
				}
			}

			if (externalUserId == null) {
				query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_1);
			}
			else {
				if (externalUserId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_3);
				}
				else {
					query.append(_FINDER_COLUMN_T_E_EXTERNALUSERID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (type != null) {
					qPos.add(type);
				}

				if (externalUserId != null) {
					qPos.add(externalUserId);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_E, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Counts all the user id mappers.
	 *
	 * @return the number of user id mappers
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

				Query q = session.createQuery(_SQL_COUNT_USERIDMAPPER);

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
	 * Initializes the user id mapper persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.UserIdMapper")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<UserIdMapper>> listenersList = new ArrayList<ModelListener<UserIdMapper>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<UserIdMapper>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(UserIdMapperImpl.class.getName());
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
	private static final String _SQL_SELECT_USERIDMAPPER = "SELECT userIdMapper FROM UserIdMapper userIdMapper";
	private static final String _SQL_SELECT_USERIDMAPPER_WHERE = "SELECT userIdMapper FROM UserIdMapper userIdMapper WHERE ";
	private static final String _SQL_COUNT_USERIDMAPPER = "SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper";
	private static final String _SQL_COUNT_USERIDMAPPER_WHERE = "SELECT COUNT(userIdMapper) FROM UserIdMapper userIdMapper WHERE ";
	private static final String _FINDER_COLUMN_USERID_USERID_2 = "userIdMapper.userId = ?";
	private static final String _FINDER_COLUMN_U_T_USERID_2 = "userIdMapper.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_T_TYPE_1 = "userIdMapper.type IS NULL";
	private static final String _FINDER_COLUMN_U_T_TYPE_2 = "userIdMapper.type = ?";
	private static final String _FINDER_COLUMN_U_T_TYPE_3 = "(userIdMapper.type IS NULL OR userIdMapper.type = ?)";
	private static final String _FINDER_COLUMN_T_E_TYPE_1 = "userIdMapper.type IS NULL AND ";
	private static final String _FINDER_COLUMN_T_E_TYPE_2 = "userIdMapper.type = ? AND ";
	private static final String _FINDER_COLUMN_T_E_TYPE_3 = "(userIdMapper.type IS NULL OR userIdMapper.type = ?) AND ";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_1 = "userIdMapper.externalUserId IS NULL";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_2 = "userIdMapper.externalUserId = ?";
	private static final String _FINDER_COLUMN_T_E_EXTERNALUSERID_3 = "(userIdMapper.externalUserId IS NULL OR userIdMapper.externalUserId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "userIdMapper.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No UserIdMapper exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No UserIdMapper exists with the key {";
	private static Log _log = LogFactoryUtil.getLog(UserIdMapperPersistenceImpl.class);
}