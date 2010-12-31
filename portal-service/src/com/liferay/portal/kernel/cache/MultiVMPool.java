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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public interface MultiVMPool {

	public void clear();

	public void clear(String name);

	public Object get(String name, String key);

	/**
	 * @deprecated
	 */
	public Object get(PortalCache portalCache, String key);

	public PortalCache getCache(String name);

	public PortalCache getCache(String name, boolean blocking);

	public void put(String name, String key, Object obj);

	/**
	 * @deprecated
	 */
	public void put(PortalCache portalCache, String key, Object obj);

	public void put(String name, String key, Serializable obj);

	/**
	 * @deprecated
	 */
	public void put(PortalCache portalCache, String key, Serializable obj);

	public void remove(String name, String key);

	/**
	 * @deprecated
	 */
	public void remove(PortalCache portalCache, String key);

	public void removeCache(String name);

}