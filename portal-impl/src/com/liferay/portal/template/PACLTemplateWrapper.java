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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.Template;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Tina Tian
 * @deprecated As of 6.2.0
 */
public class PACLTemplateWrapper implements Template {

	public static Template getTemplate(Template template) {
		return template;
	}

	public Object get(String key) {
		return null;
	}

	public String[] getKeys() {
		return null;
	}

	public void prepare(HttpServletRequest request) {
	}

	public void processTemplate(Writer writer) {
	}

	public void put(String key, Object value) {
	}

}