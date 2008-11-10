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

package com.liferay.wsrp.producer.usermanager;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.portletcontainer.PortletWindowContextImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import com.sun.portal.container.PortletWindowContext;
import com.sun.portal.wsrp.common.WSRPSpecKeys;
import com.sun.portal.wsrp.common.stubs.v2.UserContext;
import com.sun.portal.wsrp.producer.Producer;
import com.sun.portal.wsrp.producer.ProducerException;
import com.sun.portal.wsrp.producer.usermanager.UserManager;

import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="UserManagerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Manish Gupta
 * @author Brian Wing Shun Chan
 *
 */

public class UserManagerImpl implements UserManager{

	public UserManagerImpl(Producer producer) {
		//_producer = producer;
	}

	public void createConsumerUserStore(String registrationHandle) {
	}

	public void createDefaultUserStore() {
	}

	public void deleteConsumerUserStore(String registrationHandle) {
	}

	public void deleteDefaultUserStore() {
	}

	public PortletWindowContext getPortletWindowContext(
			HttpServletRequest request, String registrationHandle,
			String userKey)
		throws ProducerException{

		try{
			request.setAttribute(_IS_WSRP_REQUEST, "true");

			String portletId = (String)request.getAttribute(_PORTAL_ID);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				PortalInstances.getCompanyId(request), portletId);

			PortletWindowContext portletWindowContext =
				new PortletWindowContextImpl(
					request, portlet, PortletRequest.ACTION_PHASE);

			initializeLiferayRequest(request);

			return portletWindowContext;
		}
		catch (Exception e) {
			throw new ProducerException(e);
		}
	}

	public String getUserKey(UserContext userContext) {
		String userKey = null;

		if (userContext != null) {
			userKey = userContext.getUserContextKey();

			if ((userKey != null) &&
				(userKey.equals(WSRPSpecKeys.WSRP_GUEST_KEY))) {

				userKey = null;
			}
		}

		return userKey;
	}

	public boolean supportsRoleCreation() {
		return false;
	}

	public boolean supportsUserCreation() {
		return true;
	}

	protected void initializeLiferayRequest(HttpServletRequest request)
		throws Exception {

		// Manually create a ThemeDisplay object. Do not use the factory unless
		// you manually recycle the object or else there will be a memory leak.

		//ThemeDisplay themeDisplay = ThemeDisplayFactory.create();
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(PortalUtil.getCompany(request));
		themeDisplay.setUser(themeDisplay.getDefaultUser());

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			PortalInstances.getCompanyId(request), GroupConstants.GUEST);

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			guestGroup.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Layout layout = null;

		if (layouts.size() > 0) {
			layout = layouts.get(0);
		}

		themeDisplay.setLayout(layout);

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		request.setAttribute(WebKeys.LAYOUT, layout);
	}

	private static final String _IS_WSRP_REQUEST = "is.wsrp.request";

	private static final String _PORTAL_ID = "com.sun.portal.portlet.id";

	//private Producer _producer = null;

}