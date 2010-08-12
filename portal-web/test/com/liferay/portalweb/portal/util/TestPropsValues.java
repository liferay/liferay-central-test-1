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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsValues {

	public static final String BROWSER_TYPE = TestPropsUtil.get("browser.type");

	public static final long COMPANY_ID = GetterUtil.getLong(
		TestPropsUtil.get("company.id"));

	public static final String CLUSTER_NODE_1 =
		TestPropsUtil.get("cluster.node1");

	public static final String CLUSTER_NODE_2 =
		TestPropsUtil.get("cluster.node2");

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final String SELENIUM_HOST =
		TestPropsUtil.get("selenium.host");

	public static final int SELENIUM_PORT = GetterUtil.getInteger(
		TestPropsUtil.get("selenium.port"));

	public static final String[] THEME_IDS = StringUtil.split(
		TestPropsUtil.get("theme.ids"));

	public static final String VM_HOST = TestPropsUtil.get("vm.host");

}