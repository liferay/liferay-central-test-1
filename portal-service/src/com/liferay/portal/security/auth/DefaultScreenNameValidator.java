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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="DefaultScreenNameValidator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DefaultScreenNameValidator implements ScreenNameValidator {

	public static final String CYRUS = "cyrus";

	public static final String POSTFIX = "postfix";

	public boolean validate(long companyId, String screenName) {
		if (Validator.isEmailAddress(screenName) ||
			(screenName.equalsIgnoreCase(CYRUS)) ||
			(screenName.equalsIgnoreCase(POSTFIX)) ||
			(screenName.indexOf(StringPool.SLASH) != -1) ||
			(screenName.indexOf(StringPool.UNDERLINE) != -1)) {

			return false;
		}
		else {
			return true;
		}
	}

}