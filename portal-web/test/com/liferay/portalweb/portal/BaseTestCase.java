/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal;

import com.liferay.portal.util.InitUtil;
import com.liferay.portalweb.portal.util.LiferaySeleneseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseTestCase extends LiferaySeleneseTestCase {

	public BaseTestCase() {
		InitUtil.initWithSpring();
	}

	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();
	}

	@Override
	public void tearDown() throws Exception {
	}

	protected void loadRequiredJavaScriptModules() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		if (className.contains(".sampledata523.")) {
			return;
		}

		String location = selenium.getLocation();

		if (location.contains("/documents/")) {
			return;
		}

		if (!location.contains(TestPropsValues.PORTAL_URL)) {
			return;
		}

		selenium.getEval("window.Liferay.fire(\'initDockbar\');");
	}

}