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

package com.liferay.portal.servlet.filters.doubleclick;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.StopWatch;

public class DoubleClickFilter extends BasePortalFilter {

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		HttpSession session = request.getSession(false);

		if (session == null) {
			processFilter(
				DoubleClickFilter.class, request, response, filterChain);
		}
		else {
			DoubleClickController controller = null;

			synchronized (session) {
				controller = (DoubleClickController)session.getAttribute(
					_CONTROLLER_KEY);

				if (controller == null) {
					controller = new DoubleClickController();

					session.setAttribute(_CONTROLLER_KEY, controller);
				}
			}

			boolean ok = false;

			try {
				controller.control(request, response, filterChain);

				ok = true;
			}
			finally {
				if (_log.isDebugEnabled()) {
					String completeURL = HttpUtil.getCompleteURL(request);

					if (ok) {
						_log.debug(
							"Double click prevention succeded in " +
								stopWatch.getTime() + " ms for " + completeURL);
					}
					else {
						_log.debug(
							"Double click prevention failed in " +
								stopWatch.getTime() + " ms for " + completeURL);
					}
				}
			}
		}
	}

	private static final String _CONTROLLER_KEY =
		DoubleClickFilter.class.getName();

	private static Log _log = LogFactoryUtil.getLog(DoubleClickFilter.class);

}