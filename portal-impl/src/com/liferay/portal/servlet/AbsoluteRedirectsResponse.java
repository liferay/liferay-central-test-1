/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <a href="AbsoluteRedirectsResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class AbsoluteRedirectsResponse extends HttpServletResponseWrapper {
	public AbsoluteRedirectsResponse(
		HttpServletRequest req, HttpServletResponse res) {

		super(res);

		this._req = req;
	}

	public void sendRedirect(String redirect) throws IOException {
		String portalURL = getPortalURL();

		if (redirect.startsWith(StringPool.SLASH) && (portalURL != null)) {
			redirect = portalURL + redirect;
		}

		super.sendRedirect(redirect);
	}

	protected String getPortalURL() {
		return PortalUtil.getPortalURL(_req);
	}

	private HttpServletRequest _req = null;
	
}
