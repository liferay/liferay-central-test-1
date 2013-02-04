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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Michael C. Han
 */
public class FullNameGeneratorFactory {

	public static FullNameGenerator getInstance() {
		if (_originalFullNameGenerator == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.USERS_FULL_NAME_GENERATOR);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_originalFullNameGenerator =
					(FullNameGenerator)InstanceFactory.newInstance(
						classLoader, PropsValues.USERS_FULL_NAME_GENERATOR);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_fullNameGenerator == null) {
			_fullNameGenerator = _originalFullNameGenerator;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + ClassUtil.getClassName(_fullNameGenerator));
		}

		return _fullNameGenerator;
	}

	public static void setInstance(FullNameGenerator fullNameValidator) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(fullNameValidator));
		}

		if (fullNameValidator == null) {
			_fullNameGenerator = _originalFullNameGenerator;
		}
		else {
			_fullNameGenerator = fullNameValidator;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		FullNameValidatorFactory.class);

	private static FullNameGenerator _fullNameGenerator;
	private static FullNameGenerator _originalFullNameGenerator;

}