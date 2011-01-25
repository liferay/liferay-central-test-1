/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet.filters.invoker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.DirectCallFilter;
import com.liferay.portal.kernel.servlet.LiferayFilter;
import com.liferay.portal.kernel.servlet.PortalClassLoaderFilter;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.servlet.TryFinallyFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletRequestFilter;
import com.liferay.portal.kernel.servlet.WrapHttpServletResponseFilter;

import java.io.IOException;

import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class InvokerFilterChain implements FilterChain {

	public InvokerFilterChain(FilterChain filterChain) {
		_filterChain = filterChain;
	}

	public void addFilter(Filter filter) {
		_filters.add(filter);
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		if (_filters.isEmpty()) {
			_filterChain.doFilter(servletRequest, servletResponse);
		}
		else {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			HttpServletResponse response = (HttpServletResponse)servletResponse;

			Filter filter = _filters.remove();

			boolean filterEnabled = true;

			if (filter instanceof LiferayFilter) {
				LiferayFilter liferayFilter = (LiferayFilter)filter;

				filterEnabled = liferayFilter.isFilterEnabled(
					request, response);
			}

			if (filterEnabled) {
				if (filter instanceof DirectCallFilter) {
					try {
						processDirectCallFilter(filter, request, response);
					}
					catch (IOException ioe) {
						throw ioe;
					}
					catch (ServletException se) {
						throw se;
					}
					catch (Exception e) {
						throw new ServletException(e);
					}
				}
				else {
					filter.doFilter(request, response, this);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Skip disabled filter " + filter.getClass());
				}

				doFilter(request, response);
			}
		}
	}

	protected void processDirectCallFilter(
			Filter filter, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		if (filter instanceof WrapHttpServletRequestFilter) {
			if (_log.isDebugEnabled()) {
				_log.debug("Wrap response with filter " + filter.getClass());
			}

			WrapHttpServletRequestFilter wrapHttpServletRequestFilter =
				(WrapHttpServletRequestFilter)filter;

			request = wrapHttpServletRequestFilter.getWrappedHttpServletRequest(
				request, response);
		}

		if (filter instanceof WrapHttpServletResponseFilter) {
			if (_log.isDebugEnabled()) {
				_log.debug("Wrap request with filter " + filter.getClass());
			}

			WrapHttpServletResponseFilter wrapHttpServletResponseFilter =
				(WrapHttpServletResponseFilter)filter;

			response =
				wrapHttpServletResponseFilter.getWrappedHttpServletResponse(
					request, response);
		}

		if (filter instanceof TryFinallyFilter) {
			TryFinallyFilter tryFinallyFilter = (TryFinallyFilter)filter;

			Object object = null;

			try {
				if (_log.isDebugEnabled()) {
					_log.debug("Invoke try for filter " + filter.getClass());
				}

				object = tryFinallyFilter.doFilterTry(request, response);

				doFilter(request, response);
			}
			finally {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Invoke finally for filter " + filter.getClass());
				}

				tryFinallyFilter.doFilterFinally(request, response, object);
			}
		}
		else if (filter instanceof TryFilter) {
			TryFilter tryFilter = (TryFilter)filter;

			if (_log.isDebugEnabled()) {
				_log.debug("Invoke try for filter " + filter.getClass());
			}

			tryFilter.doFilterTry(request, response);

			doFilter(request, response);
		}
		else {
			doFilter(request, response);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(InvokerFilterChain.class);

	private FilterChain _filterChain;
	private Queue<Filter> _filters = new LinkedList<Filter>();

}