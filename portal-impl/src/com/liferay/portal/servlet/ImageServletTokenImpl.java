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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.servlet.ImageServletToken;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

/**
 * <a href="ImageServletTokenImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ImageServletTokenImpl implements ImageServletToken {

	public static final String CACHE_NAME = ImageServletToken.class.getName();

	public void afterPropertiesSet() {
		_portalCache = _multiVMPool.getCache(CACHE_NAME);
	}

	public String getToken(long imageId) {
		String key = _encodeKey(imageId);

		String token = (String)_portalCache.get(key);

		if (token == null) {
			token = _createToken(imageId);

			_portalCache.put(key, token);
		}

		return token;
	}

	public void resetToken(long imageId) {
		String key = _encodeKey(imageId);

		_portalCache.remove(key);

		// Journal content

		JournalContentUtil.clearCache();

		// Layout cache

		CacheUtil.clearCache();
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private String _createToken(long imageId) {
		return String.valueOf(System.currentTimeMillis());
	}

	private String _encodeKey(long imageId) {
		return CACHE_NAME.concat(StringPool.POUND).concat(
			String.valueOf(imageId));
	}

	private MultiVMPool _multiVMPool;
	private PortalCache _portalCache;

}