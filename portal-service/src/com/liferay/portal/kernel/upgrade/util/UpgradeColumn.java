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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="UpgradeColumn.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface UpgradeColumn {

	public String getName();

	public long increment() throws SystemException;

	public boolean isApplicable(String name);

	public Integer getOldColumnType(Integer defaultType);

	public Object getOldValue();

	public void setOldValue(Object oldValue);

	public Integer getNewColumnType(Integer defaultType);

	public Object getNewValue(Object oldValue) throws Exception;

	public Object getNewValue();

	public void setNewValue(Object newValue);

}