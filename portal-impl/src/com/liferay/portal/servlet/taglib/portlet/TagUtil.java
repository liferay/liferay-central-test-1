/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.servlet.taglib.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.portletcontainer.LiferayPortletURLImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletResponseImpl;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="TagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Deepak Gothe
 *
 */
public class TagUtil {

	public static LiferayPortletURL getLiferayPortletURL(
		HttpServletRequest request, String portletName, String lifecycle) {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return null;
		}

		LiferayPortletURL portletURL = null;

		if (PropsValues.PORTLET_CONTAINER_IMPL_SUN &&
				_isWARFile(portletRequest)) {

			portletURL = new LiferayPortletURLImpl(
				request, portletName, portletRequest.getWindowState(),
				portletRequest.getPortletMode(), _getPlid(request), lifecycle);
		}
		else {
			PortletResponseImpl portletResponse =
				(PortletResponseImpl)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			portletURL = portletResponse.createPortletURLImpl(
				portletName, lifecycle);
		}

		return portletURL;
	}

	public static String getPortletName(HttpServletRequest request) {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return null;
		}

		String portletName = null;

		if (PropsValues.PORTLET_CONTAINER_IMPL_SUN &&
				_isWARFile(portletRequest)) {
			portletName = (String)portletRequest.getAttribute(
				_PORTLET_CONTAINER_WINDOW_NAME);
		}
		else {
			PortletConfigImpl portletConfig =
				(PortletConfigImpl)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG);

			portletName = portletConfig.getPortletId();
		}

		return portletName;
	}

	private static long _getPlid(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPlid();
	}

	private static boolean _isWARFile(PortletRequest portletRequest) {
		Object name = portletRequest.getAttribute(
			_PORTLET_CONTAINER_WINDOW_NAME);

		return (name == null ? false : true);
	}

	private static final String _PORTLET_CONTAINER_WINDOW_NAME =
		"javax.portlet.portletc.portletWindowName";

}