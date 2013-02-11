/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.staging;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * @author Raymond Augé
 */
public class MergeLayoutPrototypesThreadLocal {

	public static boolean isCompleted() {
		return _completed.get();
	}

	public static boolean isInProgress() {
		return _inProgress.get();
	}

	public static void setInProgress(boolean inProgress) {
		if (_inProgress.get() && !inProgress) {
			_completed.set(true);
		}

		_inProgress.set(inProgress);

	}

	private static ThreadLocal<Boolean> _completed =
		new AutoResetThreadLocal<Boolean>(
			MergeLayoutPrototypesThreadLocal.class + "._completed", false);

	private static ThreadLocal<Boolean> _inProgress =
		new AutoResetThreadLocal<Boolean>(
			MergeLayoutPrototypesThreadLocal.class + "._inProgress", false);

}