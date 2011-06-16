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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheHelper {

	public static void begin() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		_pushPortalCacheMap();
	}

	public static void commit() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_popPortalCacheMap();

		for (Map.Entry<PortalCache, Map<String, Object>> portalCacheMapEntry :
				portalCacheMap.entrySet()) {

			PortalCache portalCache = portalCacheMapEntry.getKey();

			Map<String, Object> uncommittedMap = portalCacheMapEntry.getValue();

			for (Map.Entry<String, Object> uncommittedMapEntry :
					uncommittedMap.entrySet()) {

				portalCache.put(
					uncommittedMapEntry.getKey(),
					uncommittedMapEntry.getValue());
			}
		}

		portalCacheMap.clear();
	}

	public static boolean isEnabled() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return false;
		}

		List<Map<PortalCache, Map<String, Object>>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return !portalCacheList.isEmpty();
	}

	public static void rollback() {
		if (!PropsValues.TRANSACTIONAL_CACHE_ENABLED) {
			return;
		}

		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_popPortalCacheMap();

		portalCacheMap.clear();
	}

	protected static Object get(PortalCache portalCache, String key) {
		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_peekPortalCacheMap();

		Map<String, Object> uncommittedMap = portalCacheMap.get(portalCache);

		if (uncommittedMap == null) {
			return null;
		}

		return uncommittedMap.get(key);
	}

	protected static void put(
		PortalCache portalCache, String key, Object value) {

		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_peekPortalCacheMap();

		Map<String, Object> uncommittedMap = portalCacheMap.get(portalCache);

		if (uncommittedMap == null) {
			uncommittedMap = new HashMap<String, Object>();

			portalCacheMap.put(portalCache, uncommittedMap);
		}

		uncommittedMap.put(key, value);
	}

	protected static void remove(PortalCache portalCache, String key) {
		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_peekPortalCacheMap();

		Map<String, Object> uncommittedMap = portalCacheMap.get(portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.remove(key);
		}
	}

	protected static void removeAll(PortalCache portalCache) {
		Map<PortalCache, Map<String, Object>> portalCacheMap =
			_peekPortalCacheMap();

		Map<String, Object> uncommittedMap = portalCacheMap.get(portalCache);

		if (uncommittedMap != null) {
			uncommittedMap.clear();
		}
	}

	private static Map<PortalCache, Map<String, Object>> _peekPortalCacheMap() {
		List<Map<PortalCache, Map<String, Object>>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return portalCacheList.get(portalCacheList.size() - 1);
	}

	private static Map<PortalCache, Map<String, Object>> _popPortalCacheMap() {
		List<Map<PortalCache, Map<String, Object>>> portalCacheList =
			_portalCacheListThreadLocal.get();

		return portalCacheList.remove(portalCacheList.size() - 1);
	}

	private static void _pushPortalCacheMap() {
		List<Map<PortalCache, Map<String, Object>>> portalCacheList =
			_portalCacheListThreadLocal.get();

		portalCacheList.add(new HashMap<PortalCache, Map<String, Object>>());
	}

	private static ThreadLocal<List<Map<PortalCache, Map<String, Object>>>>
		_portalCacheListThreadLocal =
			new InitialThreadLocal<List<Map<PortalCache, Map<String, Object>>>>(
				TransactionalPortalCacheHelper.class.getName() +
					"._portalCacheListThreadLocal",
				new ArrayList<Map<PortalCache, Map<String, Object>>>());

}