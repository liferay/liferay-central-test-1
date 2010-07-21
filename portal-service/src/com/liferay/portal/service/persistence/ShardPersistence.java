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

import com.liferay.portal.model.Shard;

/**
 * The persistence interface for the shard service.
 *
 * <p>
 * Never modify this interface directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regnerate this interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShardPersistenceImpl
 * @see ShardUtil
 * @generated
 */
public interface ShardPersistence extends BasePersistence<Shard> {
	/**
	* Caches the shard in the entity cache if it is enabled.
	*
	* @param shard the shard to cache
	*/
	public void cacheResult(com.liferay.portal.model.Shard shard);

	/**
	* Caches the shards in the entity cache if it is enabled.
	*
	* @param shards the shards to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Shard> shards);

	/**
	* Creates a new shard with the primary key.
	*
	* @param shardId the primary key for the new shard
	* @return the new shard
	*/
	public com.liferay.portal.model.Shard create(long shardId);

	/**
	* Removes the shard with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shardId the primary key of the shard to remove
	* @return the shard that was removed
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard remove(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Shard updateImpl(
		com.liferay.portal.model.Shard shard, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard with the primary key or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param shardId the primary key of the shard to find
	* @return the shard
	* @throws com.liferay.portal.NoSuchShardException if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard findByPrimaryKey(long shardId)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shardId the primary key of the shard to find
	* @return the shard, or <code>null</code> if a shard with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard fetchByPrimaryKey(long shardId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where name = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param name the name to search with
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard findByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param name the name to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard fetchByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param name the name to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard fetchByName(java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchShardException} if it could not be found.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard
	* @throws com.liferay.portal.NoSuchShardException if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard findByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the shard where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the matching shard, or <code>null</code> if a matching shard could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Shard fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the shards.
	*
	* @return the shards
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Shard> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.Shard> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the shard where name = &#63; from the database.
	*
	* @param name the name to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByName(java.lang.String name)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the shard where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.NoSuchShardException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shards from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the shards where name = &#63;.
	*
	* @param name the name to search with
	* @return the number of matching shards
	* @throws SystemException if a system exception occurred
	*/
	public int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the shards where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name id to search with
	* @param classPK the class p k to search with
	* @return the number of matching shards
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the shards.
	*
	* @return the number of shards
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}