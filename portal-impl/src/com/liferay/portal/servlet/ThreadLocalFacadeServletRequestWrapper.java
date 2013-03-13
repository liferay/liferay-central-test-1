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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.io.Closeable;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalFacadeServletRequestWrapper
	extends ServletRequestWrapper implements Closeable {

	public ThreadLocalFacadeServletRequestWrapper(
		ServletRequestWrapper servletRequestWrapper,
		ServletRequest nextServletRequest) {

		super(nextServletRequest);

		_servletRequestWrapper = servletRequestWrapper;

		_nextServletRequestThreadLocal.set(nextServletRequest);
	}

	public void close() {
		if (_servletRequestWrapper != null) {
			ServletRequest nextServletRequest =
				_nextServletRequestThreadLocal.get();

			_servletRequestWrapper.setRequest(nextServletRequest);
		}
	}

	@Override
	public Object getAttribute(String name) {
		ServletRequest servletRequest = getRequest();

		return servletRequest.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		ServletRequest servletRequest = getRequest();

		return servletRequest.getAttributeNames();
	}

	@Override
	public ServletRequest getRequest() {
		return _nextServletRequestThreadLocal.get();
	}

	@Override
	public void removeAttribute(String name) {
		ServletRequest servletRequest = getRequest();

		servletRequest.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		ServletRequest servletRequest = getRequest();

		servletRequest.setAttribute(name, o);
	}

	@Override
	public void setRequest(ServletRequest servletRequest) {
		_nextServletRequestThreadLocal.set(servletRequest);
	}

	private static ThreadLocal<ServletRequest> _nextServletRequestThreadLocal =
		new AutoResetThreadLocal<ServletRequest>(
			ThreadLocalFacadeServletRequestWrapper.class +
				"._nextServletRequestThreadLocal") {

			@Override
			protected ServletRequest copy(ServletRequest servletRequest) {
				return servletRequest;
			}

		};

	private ServletRequestWrapper _servletRequestWrapper;

}