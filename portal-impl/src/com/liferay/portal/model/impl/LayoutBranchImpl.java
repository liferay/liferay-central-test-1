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

import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutBranchConstants;

/**
 * @author Raymond Augé
 */
public class LayoutBranchImpl
	extends LayoutBranchModelImpl implements LayoutBranch {

	public LayoutBranchImpl() {
	}

	public boolean isMaster() {
		if (getName().equals(LayoutBranchConstants.MASTER_BRANCH_NAME)) {
			return true;
		}

		return false;
	}

}