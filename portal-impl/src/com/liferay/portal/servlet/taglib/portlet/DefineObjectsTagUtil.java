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

package com.liferay.portal.servlet.taglib.portlet;

import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portlet.PortletConfigImpl;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * <a href="DefineObjectsTagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DefineObjectsTagUtil {

	public static void doStartTag(PageContext pageContext) {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String lifecycle = (String)request.getAttribute(
			PortletRequest.LIFECYCLE_PHASE);

		PortletConfigImpl portletConfig =
			(PortletConfigImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		if (portletConfig != null) {
			pageContext.setAttribute("portletConfig", portletConfig);
			pageContext.setAttribute(
				"portletName", portletConfig.getPortletName());
		}

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			String portletRequestAttrName = null;

			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				portletRequestAttrName = "actionRequest";
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				portletRequestAttrName = "eventRequest";
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletRequestAttrName = "renderRequest";
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				portletRequestAttrName = "resourceRequest";
			}

			pageContext.setAttribute(portletRequestAttrName, portletRequest);

			PortletPreferences portletPreferences =
				portletRequest.getPreferences();

			pageContext.setAttribute("portletPreferences", portletPreferences);
			pageContext.setAttribute(
				"portletPreferencesValues", portletPreferences.getMap());

			PortletSession portletSession = portletRequest.getPortletSession();

			pageContext.setAttribute("portletSession", portletSession);

			try {
				pageContext.setAttribute(
					"portletSessionScope", portletSession.getAttributeMap());
			}
			catch (IllegalStateException ise) {
			}
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (portletResponse != null) {
			String portletResponseAttrName = null;

			if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
				portletResponseAttrName = "actionResponse";
			}
			else if (lifecycle.equals(PortletRequest.EVENT_PHASE)) {
				portletResponseAttrName = "eventResponse";
			}
			else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponseAttrName = "renderResponse";
			}
			else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
				portletResponseAttrName = "resourceResponse";
			}

			pageContext.setAttribute(portletResponseAttrName, portletResponse);
		}
	}

}