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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.BasePortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 */
public class EhcachePortalCache extends BasePortalCache {

	public EhcachePortalCache(Ehcache ehcache) {
		_ehcache = ehcache;
	}

	public Object get(String key) {
		String processedKey = processKey(key);

		Element element = _ehcache.get(processedKey);

		if (element == null) {
			return null;
		}
		else {
			return element.getObjectValue();
		}
	}

	public Collection<Object> get(Collection<String> keys) {
		List<Object> values = new ArrayList<Object>(keys.size());

		for (String key : keys) {
			values.add(get(key));
		}

		return values;
	}

	public void put(String key, Object value) {
		Element element = createElement(key, value);

		_ehcache.put(element);
	}

	public void put(String key, Object value, int timeToLive) {
		Element element = createElement(key, value);

		element.setTimeToLive(timeToLive);

		_ehcache.put(element);
	}

	public void put(String key, Serializable value) {
		Element element = createElement(key, value);

		_ehcache.put(element);
	}

	public void put(String key, Serializable value, int timeToLive) {
		Element element = createElement(key, value);

		element.setTimeToLive(timeToLive);

		_ehcache.put(element);
	}

	public void registerCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		registerCacheListener(cacheListener, CacheListenerScope.ALL);
	}

	public void registerCacheListener(
			CacheListener cacheListener, CacheListenerScope cacheListenerScope)
		throws PortalCacheException {

		if (_cacheEventListeners.containsKey(cacheListener)) {
			return;
		}

		CacheEventListener cacheEventListener =
			new PortalCacheCacheEventListener(cacheListener, this);

		_cacheEventListeners.put(cacheListener, cacheEventListener);

		NotificationScope notificationScope = getNotificationScope(
			cacheListenerScope);

		RegisteredEventListeners registeredEventListeners =
			_ehcache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			cacheEventListener, notificationScope);
	}

	public void remove(String key) {
		String processedKey = processKey(key);

		_ehcache.remove(processedKey);
	}

	public void removeAll() {
		_ehcache.removeAll();
	}

	public void unregisterCacheListener(CacheListener cacheListener)
		throws PortalCacheException {

		CacheEventListener cacheEventListener = _cacheEventListeners.get(
			cacheListener);

		if (cacheEventListener != null) {
			RegisteredEventListeners registeredEventListeners =
				_ehcache.getCacheEventNotificationService();

			registeredEventListeners.unregisterListener(cacheEventListener);
		}

		_cacheEventListeners.remove(cacheListener);
	}

	public void unregisterCacheListeners() {
		RegisteredEventListeners registeredEventListeners =
			_ehcache.getCacheEventNotificationService();

		for (CacheEventListener cacheEventListener :
				_cacheEventListeners.values()) {

			registeredEventListeners.unregisterListener(cacheEventListener);
		}

		_cacheEventListeners.clear();
	}

	protected Element createElement(String key, Object value) {
		String processedKey = processKey(key);

		Element element = new Element(processedKey, value);

		return element;
	}

	protected NotificationScope getNotificationScope(
		CacheListenerScope cacheListenerScope) {

		if (cacheListenerScope.equals(CacheListenerScope.ALL)) {
			return NotificationScope.ALL;
		}
		else if (cacheListenerScope.equals(CacheListenerScope.LOCAL)) {
			return NotificationScope.LOCAL;
		}
		else {
			return NotificationScope.REMOTE;
		}
	}

	private Map<CacheListener, CacheEventListener> _cacheEventListeners =
		new ConcurrentHashMap<CacheListener, CacheEventListener>();
	private Ehcache _ehcache;

}