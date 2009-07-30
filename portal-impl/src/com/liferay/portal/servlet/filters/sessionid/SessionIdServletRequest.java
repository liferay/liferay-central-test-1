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

package com.liferay.portal.servlet.filters.sessionid;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.CookieKeys;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="SessionIdServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SessionIdServletRequest extends HttpServletRequestWrapper {

	public SessionIdServletRequest(
		HttpServletRequest request, HttpServletResponse response) {

		super(request);

		_response = response;
	}

	public HttpSession getSession() {
		HttpSession session = super.getSession();

		process(session);

		return session;
	}

	public HttpSession getSession(boolean create) {
		HttpSession session = super.getSession(create);

		process(session);

		return session;
	}

	protected void process(HttpSession session) {
		if ((session == null) || !session.isNew() || !isSecure() ||
			isRequestedSessionIdFromCookie()) {

			return;
		}

		Object jsessionIdAlreadySet = getAttribute(_JESSIONID_ALREADY_SET);

		if (jsessionIdAlreadySet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing " + session.getId());
			}

			Cookie cookie = new Cookie(_JESSIONID, session.getId());

			cookie.setMaxAge(-1);

			String contextPath = getContextPath();

			if (Validator.isNotNull(contextPath)) {
				cookie.setPath(contextPath);
			}
			else {
				cookie.setPath(StringPool.SLASH);
			}

			CookieKeys.addCookie(
				(HttpServletRequest)super.getRequest(), _response, cookie);

			setAttribute(_JESSIONID_ALREADY_SET, Boolean.TRUE);
		}
	}

	private static final String _JESSIONID = "JSESSIONID";

	private static final String _JESSIONID_ALREADY_SET =
		"JESSIONID_ALREADY_SET";

	private static Log _log =
		LogFactoryUtil.getLog(SessionIdServletRequest.class);

	private HttpServletResponse _response;

}