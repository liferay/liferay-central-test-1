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

package com.liferay.portal.kernel.portlet;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="Route.java.html"><b><i>View Source</i></b></a>
 *
 * @author Connor McKay
 * @author Brian Wing Shun Chan
 */
public class Route {

	public void addDefaultValue(String parameter, String value) {
		_defaultValues.put(parameter, value);
	}

	public Map<String, String> getDefaultValues() {
		return _defaultValues;
	}

	public String getPattern() {
		return _pattern;
	}

	public void setPattern(String pattern) {
		_pattern = pattern;
	}

	private Map<String, String> _defaultValues = new HashMap<String, String>();
	private String _pattern;

}