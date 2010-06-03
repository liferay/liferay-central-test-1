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

package com.liferay.portal.kernel.util;

import java.lang.reflect.Method;

/**
 * <a href="MethodTargetClassKey.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class MethodTargetClassKey {

	public MethodTargetClassKey(Method method, Class<?> targetClass) {
		_method = method;
		_targetClass = targetClass;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MethodTargetClassKey)) {
			return false;
		}

		MethodTargetClassKey methodTargetClassKey = (MethodTargetClassKey)obj;

		if (Validator.equals(_method, methodTargetClassKey._method) &&
			Validator.equals(_targetClass, methodTargetClassKey._targetClass)) {

			return true;
		}

		return false;
	}

	public Method getMethod() {
		return _method;
	}

	public Class<?> getTargetClass() {
		return _targetClass;
	}

	public int hashCode() {
		if (_hashCode == 0) {
			HashCode hashCode = HashCodeFactoryUtil.getHashCode();

			hashCode.append(_method);
			hashCode.append(_targetClass);

			_hashCode = hashCode.toHashCode();
		}

		return _hashCode;
	}

	public String toString() {
		return _toString();
	}

	private String _toString() {
		if (_toString == null) {
			Class<?>[] parameterTypes = _method.getParameterTypes();

			StringBundler sb = new StringBundler(parameterTypes.length * 2 + 6);

			sb.append(_method.getDeclaringClass().getName());
			sb.append(StringPool.PERIOD);
			sb.append(_method.getName());
			sb.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < parameterTypes.length; i++) {
				sb.append(parameterTypes[i].getName());

				if ((i + 1) < parameterTypes.length) {
					sb.append(StringPool.COMMA);
				}
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);

			if (_targetClass != null) {
				sb.append(StringPool.AT);
				sb.append(_targetClass.getName());
			}

			_toString = sb.toString();
		}

		return _toString;
	}

	private int _hashCode;
	private Method _method;
	private Class<?> _targetClass;
	private String _toString;

}