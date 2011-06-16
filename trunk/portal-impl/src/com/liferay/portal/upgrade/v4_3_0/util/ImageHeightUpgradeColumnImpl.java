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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageHeightUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ImageHeightUpgradeColumnImpl(ImageTextUpgradeColumnImpl textColumn) {
		super("height");

		_textColumn = textColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Integer height = _textColumn.getHeight();

		if (height != null) {
			return height;
		}
		else {
			return oldValue;
		}
	}

	private ImageTextUpgradeColumnImpl _textColumn;

}