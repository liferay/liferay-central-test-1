/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="PortalContextImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalContextImpl implements PortalContext {

	static Properties props = new Properties();
	static List<PortletMode> portletModes = new ArrayList<PortletMode>();
	static List<WindowState> windowStates = new ArrayList<WindowState>();

	static {
		props.setProperty(MARKUP_HEAD_ELEMENT_SUPPORT, StringPool.TRUE);

		portletModes.add(PortletMode.EDIT);
		portletModes.add(PortletMode.HELP);
		portletModes.add(PortletMode.VIEW);
		portletModes.add(LiferayPortletMode.ABOUT);
		portletModes.add(LiferayPortletMode.CONFIG);
		portletModes.add(LiferayPortletMode.EDIT_DEFAULTS);
		portletModes.add(LiferayPortletMode.PREVIEW);
		portletModes.add(LiferayPortletMode.PRINT);

		windowStates.add(WindowState.MAXIMIZED);
		windowStates.add(WindowState.MINIMIZED);
		windowStates.add(WindowState.NORMAL);
		windowStates.add(LiferayWindowState.EXCLUSIVE);
		windowStates.add(LiferayWindowState.POP_UP);
	}

	public static boolean isSupportedPortletMode(PortletMode portletMode) {
		Enumeration<PortletMode> enu = Collections.enumeration(portletModes);

		while (enu.hasMoreElements()) {
			PortletMode supported = enu.nextElement();

			if (supported.equals(portletMode)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isSupportedWindowState(WindowState windowState) {
		Enumeration<WindowState> enu = Collections.enumeration(windowStates);

		while (enu.hasMoreElements()) {
			WindowState supported = enu.nextElement();

			if (supported.equals(windowState)) {
				return true;
			}
		}

		return false;
	}

	public String getPortalInfo() {
		return ReleaseInfo.getReleaseInfo();
	}

	public String getProperty(String name) {
		return props.getProperty(name);
	}

	public Enumeration<String> getPropertyNames() {
		return (Enumeration<String>)props.propertyNames();
	}

	public Enumeration<PortletMode> getSupportedPortletModes() {
		return Collections.enumeration(portletModes);
	}

	public Enumeration<WindowState> getSupportedWindowStates() {
		return Collections.enumeration(windowStates);
	}

}