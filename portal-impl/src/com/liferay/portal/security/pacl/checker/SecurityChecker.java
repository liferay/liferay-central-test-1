/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 */
public class SecurityChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public boolean implies(Permission permission) {
		String name = permission.getName();

		if (name.equals(SECURITY_PERMISSION_GET_POLICY)) {
			if (!hasGetPolicy()) {
				logSecurityException(_log, "Attempted to get the policy");

				return false;
			}
		}
		else if (name.equals(SECURITY_PERMISSION_SET_POLICY)) {
			if (!hasSetPolicy()) {
				logSecurityException(_log, "Attempted to set the policy");

				return false;
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Thread.dumpStack();
			}

			logSecurityException(
				_log,
				"Attempted to " + permission.getName() + " on " +
					permission.getActions());

			return false;
		}

		return true;
	}

	protected boolean hasGetPolicy() {

		// Temporarily return true

		return true;
	}

	protected boolean hasSetPolicy() {

		// Temporarily return true

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(SecurityChecker.class);

}