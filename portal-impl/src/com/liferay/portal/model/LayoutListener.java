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

package com.liferay.portal.model;

import com.liferay.portal.servlet.filters.cache.CacheUtil;

/**
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class LayoutListener extends BaseModelListener<Layout> {

	public void onAfterCreate(Layout layout) {
		clearCache(layout);
	}

	public void onAfterRemove(Layout layout) {
		clearCache(layout);
	}

	public void onAfterUpdate(Layout layout) {
		clearCache(layout);
	}

	protected void clearCache(Layout layout) {
		if (!layout.isPrivateLayout()) {
			CacheUtil.clearCache(layout.getCompanyId());
		}
	}

}