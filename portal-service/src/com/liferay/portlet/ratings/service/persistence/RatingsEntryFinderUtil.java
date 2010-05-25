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

package com.liferay.portlet.ratings.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="RatingsEntryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RatingsEntryFinderUtil {
	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> findByU_C_CS(
		long userId, java.lang.String className,
		java.util.List<java.lang.Long> classPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_C_CS(userId, className, classPKs);
	}

	public static RatingsEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (RatingsEntryFinder)PortalBeanLocatorUtil.locate(RatingsEntryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(RatingsEntryFinder finder) {
		_finder = finder;
	}

	private static RatingsEntryFinder _finder;
}