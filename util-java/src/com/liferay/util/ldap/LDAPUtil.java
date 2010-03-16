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

package com.liferay.util.ldap;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * <a href="LDAPUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Toma Bedolla
 * @author Michael Young
 * @author Brian Wing Shun Chan
 */
public class LDAPUtil {

	public static String getAttributeValue(
			Attributes attributes, Properties properties, String key)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeValue(attributes, id);
	}

	public static String getAttributeValue(
			Attributes attributes, Properties properties, String key,
			String defaultValue)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeValue(attributes, id, defaultValue);
	}

	public static String getAttributeValue(Attributes attributes, String id)
		throws NamingException {

		return getAttributeValue(attributes, id, StringPool.BLANK);
	}

	public static String getAttributeValue(
			Attributes attributes, String id, String defaultValue)
		throws NamingException {

		try {
			Attribute attribute = attributes.get(id);

			Object obj = attribute.get();

			return obj.toString();
		}
		catch (NullPointerException npe) {
			return defaultValue;
		}
	}

	public static String getFullProviderURL(String baseURL, String baseDN) {
		return baseURL + StringPool.SLASH + baseDN;
	}

}