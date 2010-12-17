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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.cache.transactional.TransactionalPortalCache;
import com.liferay.portal.kernel.cache.CacheKVP;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author Brian Wing Shun Chan
 */
public class FinderCacheImpl implements CacheRegistryItem, FinderCache {

	public static final String CACHE_NAME = FinderCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistryUtil.register(this);
	}

	public void clearCache() {
		clearLocalCache();

		for (PortalCache portalCache : _portalCaches.values()) {
			portalCache.removeAll();
		}
	}

	public void clearCache(String className) {
		clearLocalCache();

		PortalCache portalCache = _getPortalCache(className, false);

		if (portalCache != null) {
			portalCache.removeAll();
		}
	}

	public void clearLocalCache() {
		if (_localCacheAvailable) {
			_localCache.remove();
		}
	}

	public String getRegistryName() {
		return CACHE_NAME;
	}

	public Object getResult(
		FinderPath finderPath, Object[] args, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		Object primaryKey = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheAvailable) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(args);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache portalCache = _getPortalCache(
				finderPath.getClassName(), true);

			String cacheKey = finderPath.encodeCacheKey(args);

			primaryKey = portalCache.get(cacheKey);

			if (primaryKey != null) {
				if (_localCacheAvailable) {
					localCache.put(localCacheKey, primaryKey);
				}
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(finderPath, sessionFactory, primaryKey);
		}
		else {
			return null;
		}
	}

	public void invalidate() {
		clearCache();
	}

	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive() ||
			(result == null)) {

			return;
		}

		Object primaryKey = _resultToPrimaryKey(result);

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.put(localCacheKey, primaryKey);
		}

		PortalCache portalCache = _getPortalCache(
			finderPath.getClassName(), true);

		String cacheKey = finderPath.encodeCacheKey(args);

		portalCache.put(cacheKey, primaryKey);
	}

	public void removeCache(String className) {
		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_portalCaches.remove(groupKey);
		_multiVMPool.removeCache(groupKey);
	}

	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return;
		}

		if (_localCacheAvailable) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.remove(localCacheKey);
		}

		PortalCache portalCache = _getPortalCache(
			finderPath.getClassName(), true);

		String cacheKey = finderPath.encodeCacheKey(args);

		portalCache.remove(cacheKey);
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private PortalCache _getPortalCache(
		String className, boolean createIfAbsent) {

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		PortalCache portalCache = _portalCaches.get(groupKey);

		if ((portalCache == null) && createIfAbsent) {
			portalCache = _multiVMPool.getCache(
				groupKey, PropsValues.VALUE_OBJECT_FINDER_BLOCKING_CACHE);

			if (PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
				portalCache = new TransactionalPortalCache(portalCache);
			}

			PortalCache previousPortalCache = _portalCaches.putIfAbsent(
				groupKey, portalCache);

			if (previousPortalCache != null) {
				portalCache = previousPortalCache;
			}

			portalCache.setDebug(true);
		}

		return portalCache;
	}

	private Object _primaryKeyToResult(
		FinderPath finderPath, SessionFactory sessionFactory,
		Object primaryKey) {

		if (primaryKey instanceof CacheKVP) {
			CacheKVP cacheKVP = (CacheKVP)primaryKey;

			Class<?> modelClass = cacheKVP.getModelClass();
			Serializable primaryKeyObj = cacheKVP.getPrimaryKeyObj();

			return EntityCacheUtil.loadResult(
				finderPath.isEntityCacheEnabled(), modelClass, primaryKeyObj,
				sessionFactory);
		}
		else if (primaryKey instanceof List<?>) {
			List<Object> cachedList = (List<Object>)primaryKey;

			if (cachedList.isEmpty()) {
				return Collections.emptyList();
			}

			List<Object> list = new ArrayList<Object>(cachedList.size());

			for (Object curPrimaryKey : cachedList) {
				Object result = _primaryKeyToResult(
					finderPath, sessionFactory, curPrimaryKey);

				list.add(result);
			}

			return list;
		}
		else {
			return primaryKey;
		}
	}

	private Object _resultToPrimaryKey(Object result) {
		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			Class<?> modelClass = model.getClass();
			Serializable primaryKeyObj = model.getPrimaryKeyObj();

			return new CacheKVP(modelClass, primaryKeyObj);
		}
		else if (result instanceof List<?>) {
			List<Object> list = (List<Object>)result;

			if (list.isEmpty()) {
				return Collections.emptyList();
			}

			List<Object> cachedList = new ArrayList<Object>(list.size());

			for (Object curResult : list) {
				Object primaryKey = _resultToPrimaryKey(curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}
		else {
			return result;
		}
	}

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;

	static {
		if (PropsValues.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new AutoResetThreadLocal<LRUMap>(
				FinderCacheImpl.class + "._localCache",
				new LRUMap(
					PropsValues.
						VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private MultiVMPool _multiVMPool;
	private ConcurrentMap<String, PortalCache> _portalCaches =
		new ConcurrentHashMap<String, PortalCache>();

}