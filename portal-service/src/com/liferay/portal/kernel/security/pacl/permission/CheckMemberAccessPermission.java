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

package com.liferay.portal.kernel.security.pacl.permission;

import java.security.BasicPermission;

/**
 * @author Raymond Augé
 * @author Zsolt Berentey
 */
public class CheckMemberAccessPermission extends BasicPermission {

	public CheckMemberAccessPermission(
		String name, Class<?> callerClass, ClassLoader callerClassLoader,
		Class<?> subjectClass, ClassLoader subjectClassLoader) {

		super(name);

		_callerClass = callerClass;
		_callerClassLoader = callerClassLoader;
		_subjectClass = subjectClass;
		_subjectClassLoader = subjectClassLoader;
	}

	public Class<?> getCallerClass() {
		return _callerClass;
	}

	public ClassLoader getCallerClassLoader() {
		return _callerClassLoader;
	}

	public Class<?> getSubjectClass() {
		return _subjectClass;
	}

	public ClassLoader getSubjectClassLoader() {
		return _subjectClassLoader;
	}

	private Class<?> _callerClass;
	private ClassLoader _callerClassLoader;
	private Class<?> _subjectClass;
	private ClassLoader _subjectClassLoader;

}