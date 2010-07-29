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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ClusterGroup;

/**
 * The model implementation for the ClusterGroup service. Represents a row in the &quot;ClusterGroup&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.model.ClusterGroup} interface.
 * </p>
 */
public class ClusterGroupImpl extends ClusterGroupModelImpl
	implements ClusterGroup {
	public ClusterGroupImpl() {
	}

	public String[] getClusterNodeIdsArray() {
		return StringUtil.split(getClusterNodeIds());
	}

}