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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="StringType.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StringType extends org.hibernate.type.StringType {

	public boolean isEqual(Object x, Object y) {
		boolean equal = super.isEqual(x, y);

		if (!equal) {
			if (((x == null) || x.equals(StringPool.BLANK)) &&
				((y == null) || y.equals(StringPool.BLANK))) {

				equal = true;
			}
			else {
				equal = false;
			}
		}

		return equal;
	}

}