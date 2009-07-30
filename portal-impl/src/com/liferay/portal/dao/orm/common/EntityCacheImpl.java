/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * <a href="EntityCacheImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EntityCacheImpl implements CacheRegistryItem, EntityCache {

	public static final String CACHE_NAME = EntityCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistry.register(this);
	}

	public void clearCache() {
		clearLocalCache();

		PortalCache[] portalCaches = _portalCaches.values().toArray(
			new PortalCache[_portalCaches.size()]);

		for (PortalCache portalCache : portalCaches) {
			portalCache.removeAll();
		}
	}

	public void clearCache(String className) {
		clearLocalCache();

		PortalCache portalCache = _getPortalCache(className);

		portalCache.removeAll();
	}

	public void clearLocalCache() {
		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			localCache.clear();
		}
	}

	public String getRegistryName() {
		return CACHE_NAME;
	}

	public Object getResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return null;
		}

		Object result = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheEnabled.get().booleanValue()) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = _multiVMPool.get(portalCache, cacheKey);

			if (result == null) {
				result = StringPool.BLANK;

				_multiVMPool.put(portalCache, cacheKey, result);
			}

			if (_localCacheEnabled.get().booleanValue()) {
				localCache.put(localCacheKey, result);
			}
		}

		if (result != null) {
			result = _objectToResult(result);
		}

		return result;
	}

	public void invalidate() {
		clearCache();
	}

	public Object loadResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			Session session = null;

			try {
				session = sessionFactory.openSession();

				return session.load(classObj, primaryKeyObj);
			}
			finally {
				sessionFactory.closeSession(session);
			}
		}

		Object result = null;

		Map<String, Object> localCache = null;

		String localCacheKey = null;

		if (_localCacheEnabled.get().booleanValue()) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(classObj, primaryKeyObj);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache portalCache = _getPortalCache(classObj.getName());

			String cacheKey = _encodeCacheKey(primaryKeyObj);

			result = _multiVMPool.get(portalCache, cacheKey);

			if (result == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Load " + classObj + " " + primaryKeyObj +
							" from session");
				}

				Session session = null;

				try {
					session = sessionFactory.openSession();

					result = session.load(classObj, primaryKeyObj);
				}
				finally {
					if (result == null) {
						result = StringPool.BLANK;
					}

					result = _objectToResult(result);

					_multiVMPool.put(portalCache, cacheKey, result);

					sessionFactory.closeSession(session);
				}
			}

			if (_localCacheEnabled.get().booleanValue()) {
				localCache.put(localCacheKey, result);
			}
		}

		result = _objectToResult(result);

		return result;
	}

	public void putResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj, Object result) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive() ||
			(result == null)) {

			return;
		}

		result = _objectToResult(result);

		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = _encodeLocalCacheKey(
				classObj, primaryKeyObj);

			localCache.put(localCacheKey, result);
		}

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		_multiVMPool.put(portalCache, cacheKey, result);
	}

	public void removeResult(
		boolean entityCacheEnabled, Class<?> classObj,
		Serializable primaryKeyObj) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistry.isActive()) {

			return;
		}

		if (_localCacheEnabled.get().booleanValue()) {
			Map<String, Object> localCache = _localCache.get();

			String localCacheKey = _encodeLocalCacheKey(
				classObj, primaryKeyObj);

			localCache.remove(localCacheKey);
		}

		PortalCache portalCache = _getPortalCache(classObj.getName());

		String cacheKey = _encodeCacheKey(primaryKeyObj);

		_multiVMPool.remove(portalCache, cacheKey);
	}

	public void setLocalCacheEnabled(boolean localCacheEnabled) {
		if (_localCacheAvailable) {
			_localCacheEnabled.set(Boolean.valueOf(localCacheEnabled));
		}
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _encodeCacheKey(Serializable primaryKeyObj) {
		return String.valueOf(primaryKeyObj);
	}

	private String _encodeGroupKey(String className) {
		StringBuilder sb = new StringBuilder();

		sb.append(CACHE_NAME);
		sb.append(StringPool.PERIOD);
		sb.append(className);

		return sb.toString();
	}

	private String _encodeLocalCacheKey(
		Class<?> classObj, Serializable primaryKeyObj) {

		StringBuilder sb = new StringBuilder();

		sb.append(classObj.getName());
		sb.append(StringPool.PERIOD);
		sb.append(primaryKeyObj);

		return sb.toString();
	}

	private PortalCache _getPortalCache(String className) {
		String groupKey = _encodeGroupKey(className);

		PortalCache portalCache = _portalCaches.get(groupKey);

		if (portalCache == null) {
			portalCache = _multiVMPool.getCache(groupKey, true);

			_portalCaches.put(groupKey, portalCache);
		}

		return portalCache;
	}

	private Object _objectToResult(Object result) {
		if (result instanceof String) {
			return null;
		}
		else {
			result = ((BaseModel<?>)result).clone();

			BaseModel<?> model = (BaseModel<?>)result;

			model.setCachedModel(true);

			return model;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(EntityCacheImpl.class);

	private static ThreadLocal<Map> _localCache;
	private static boolean _localCacheAvailable;
	private static ThreadLocal<Boolean> _localCacheEnabled =
		new InitialThreadLocal<Boolean>(Boolean.FALSE);

	static {
		if (PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new InitialThreadLocal<Map>(new LRUMap(
				PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private MultiVMPool _multiVMPool;
	private Map<String, PortalCache> _portalCaches =
		new ConcurrentHashMap<String, PortalCache>();

}