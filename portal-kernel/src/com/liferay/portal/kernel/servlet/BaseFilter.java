/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="BaseFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Aug�
 */
public abstract class BaseFilter implements Filter {

	public void init(FilterConfig filterConfig) {
		_filterConfig = filterConfig;

		String urlRegexPattern = GetterUtil.getString(
			filterConfig.getInitParameter("url-regex-pattern"));

		if (Validator.isNotNull(urlRegexPattern)) {
			_urlRegexPattern = Pattern.compile(urlRegexPattern);
		}

		_servlet24Dispatcher = filterConfig.getInitParameter(
			"servlet-2.4-dispatcher");
	}

	public void doFilter(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain)
		throws IOException, ServletException {

		Log log = getLog();

		if (log.isDebugEnabled()) {
			if (isFilterEnabled()) {
				log.debug(_filterClass + " is enabled");
			}
			else {
				log.debug(_filterClass + " is disabled");
			}
		}

		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;

		boolean filterEnabled = isFilterEnabled();

		if (filterEnabled && Validator.isNotNull(_servlet24Dispatcher)) {
			if ((_servlet24Dispatcher.equalsIgnoreCase(
					_SERVLET_24_DISPATCHER_REQUEST)) &&
				(request.getRequestURL() == null)) {

				filterEnabled = false;
			}
		}

		if (filterEnabled && (_urlRegexPattern != null)) {
			StringBuilder sb = new StringBuilder();

			sb.append(request.getRequestURL());

			if (Validator.isNotNull(request.getQueryString())) {
				sb.append(StringPool.QUESTION);
				sb.append(request.getQueryString());
			}

			Matcher matcher = _urlRegexPattern.matcher(sb.toString());

			filterEnabled = matcher.find();
		}

		try {
			if (filterEnabled) {
				processFilter(request, response, filterChain);
			}
			else {
				processFilter(_filterClass, request, response, filterChain);
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ServletException se) {
			throw se;
		}
		catch (Exception e) {
			getLog().error(e, e);
		}
	}

	public FilterConfig getFilterConfig() {
		return _filterConfig;
	}

	public void destroy() {
	}

	protected abstract Log getLog();

	protected boolean isFilterEnabled() {
		return _filterEnabled;
	}

	protected abstract void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception;

	protected void processFilter(
			Class<?> filterClass, HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
		throws Exception {

		long startTime = 0;

		String threadName = null;
		String depther = null;
		String path = null;

		Log log = getLog();

		if (log.isDebugEnabled()) {
			startTime = System.currentTimeMillis();

			Thread currentThread = Thread.currentThread();

			threadName = currentThread.getName();

			depther = (String)request.getAttribute(_DEPTHER);

			if (depther == null) {
				depther = StringPool.BLANK;
			}
			else {
				depther += StringPool.EQUAL;
			}

			request.setAttribute(_DEPTHER, depther);

			path = request.getRequestURI();

			log.debug(
				"[" + threadName + "]" + depther + "> " +
					filterClass.getName() + " " + path);
		}

		filterChain.doFilter(request, response);

		if (log.isDebugEnabled()) {
			long endTime = System.currentTimeMillis();

			depther = (String)request.getAttribute(_DEPTHER);

			log.debug(
				"[" + threadName + "]" + depther + "< " +
					filterClass.getName() + " " + path + " " +
						(endTime - startTime) + " ms");

			if (depther.length() > 0) {
				depther = depther.substring(1);
			}

			request.setAttribute(_DEPTHER, depther);
		}
	}

	private static final String _DEPTHER = "DEPTHER";

	private static final String _SERVLET_24_DISPATCHER_REQUEST = "REQUEST";

	private FilterConfig _filterConfig;
	private Class<?> _filterClass = getClass();
	private boolean _filterEnabled = true;
	private String _servlet24Dispatcher;
	private Pattern _urlRegexPattern;

}