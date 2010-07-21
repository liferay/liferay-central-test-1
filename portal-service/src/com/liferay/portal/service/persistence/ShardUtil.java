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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Shard;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the shard service.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this class.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShardPersistence
 * @see ShardPersistenceImpl
 * @generated
 */
public class ShardUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Shard shard) {
		getPersistence().clearCache(shard);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Shard> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Shard remove(Shard shard) throws SystemException {
		return getPersistence().remove(shard);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Shard update(Shard shard, boolean merge)
		throws SystemException {
		return getPersistence().update(shard, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static Shard update(Shard shard, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(shard, merge, serviceContext);
	}

	/**
	* Caches the shard in the entity cache if it is enabled.
	*
	* @param shard the shard to cache
	*/
	public static void cacheResult(com.liferay.portal.model.Shard shard) {
		getPersistence().cacheResult(shard);
	}

	/**
	* Caches the shards in the entity cache if it is enabled.
	*
	* @param shards the shards to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Shard> shards) {
		getPersistence().cacheResult(shards);
	}

	/**
	* Creates a new shard with the primary key.
	*
	* @param shardId the primary key for the new shard
	* @return the new shard
	*/
	public static com.liferay.portal.model.Shard create(long shardId) {
		return getPersistence().create(shardId);
	}

	/**
	* Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shardId the primary key of the shard to remove
	* @return the shard that was removed
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(shardId);
	}

	public static com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shard, merge);
	}

	/**
	* Finds the shard with the primary key or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param shardId the primary key of the shard to find
	* @return the shard
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(shardId);
	}

	/**
	* Finds the shard with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shardId the primary key of the shard to find
	* @return the shard, or <code>null</code> if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(shardId);
	}

	/**
	* Finds the shard where name = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param name the name to search with
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard findByName(
		java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName(name);
	}

	/**
	* Finds the shard where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard fetchByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByName(name);
	}

	/**
	* Finds the shard where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard fetchByName(
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByName(name, retrieveFromCache);
	}

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard findByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Finds all the shards.
	*
	* @return the shards
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Shard> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the shards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shards to return
	* @param end the upper bound of the range of shards to return (not inclusive)
	* @return the range of shards
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Shard> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the shards.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shards to return
	* @param end the upper bound of the range of shards to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of shards
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Shard> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the shard where name = &#63; from the database.
	*
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByName(name);
	}

	/**
	* Removes the shard where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Removes all the shards from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the shards where name = &#63;.
	*
	* @param name the name to search with
	* @return the number of matching shards
	* @throws SystemException if a system exception occurred
	*/
	public static int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByName(name);
	}

	/**
	* Counts all the shards where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the number of matching shards
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Counts all the shards.
	*
	* @return the number of shards
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShardPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShardPersistence)PortalBeanLocatorUtil.locate(ShardPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ShardPersistence persistence) {
		_persistence = persistence;
	}

	private static ShardPersistence _persistence;
}