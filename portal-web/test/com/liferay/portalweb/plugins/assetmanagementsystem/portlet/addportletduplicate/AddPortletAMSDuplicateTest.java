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

package com.liferay.portalweb.plugins.assetmanagementsystem.portlet.addportletduplicate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletAMSDuplicateTest extends BaseTestCase {
	public void testAddPortletAMSDuplicate() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Management System Test Page");
		selenium.clickAt("link=Asset Management System Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_145_addApplication", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.waitForVisible("layout_configuration_content");
		selenium.typeKeys("layout_configuration_content",
			RuntimeVariables.replace("a"));
		selenium.waitForVisible("//div[@title='Asset Management System']");
		assertFalse(selenium.isVisible(
				"//div[@title='Asset Management System']/p/a"));
	}
}