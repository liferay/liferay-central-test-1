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

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.util.PortalClassInvoker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

	public abstract void run(
			HttpServletRequest request, HttpServletResponse response)
		throws ActionException;

	public void run(RenderRequest renderRequest, RenderResponse renderResponse)
		throws ActionException {

		try {
			HttpServletRequest request = _getHttpServletRequest(renderRequest);
			HttpServletResponse response = _getHttpServletResponse(
				renderResponse);

			run(request, response);
		}
		catch (ActionException ae) {
			throw ae;
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private HttpServletRequest _getHttpServletRequest(
			PortletRequest portletRequest)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GETHTTPSERVLETREQUEST, portletRequest, false);

		if (returnObj != null) {
			return (HttpServletRequest)returnObj;
		}
		else {
			return null;
		}
	}

	private HttpServletResponse _getHttpServletResponse(
			PortletResponse portletResponse)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, _METHOD_GETHTTPSERVLETRESPONSE, portletResponse, false);

		if (returnObj != null) {
			return (HttpServletResponse)returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS = "com.liferay.portal.util.PortalUtil";

	private static final String _METHOD_GETHTTPSERVLETREQUEST =
		"getHttpServletRequest";

	private static final String _METHOD_GETHTTPSERVLETRESPONSE =
		"getHttpServletResponse";

}