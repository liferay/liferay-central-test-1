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

package com.liferay.portalweb.plugins.ddlform.portlet.addportletddlformmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletDDLForm2Test extends BaseTestCase {
	public void testAddPortletDDLForm2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Dynamic Data List Form Test Page");
		selenium.click(RuntimeVariables.replace(
				"link=Dynamic Data List Form Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent(
			"//div[@title='Dynamic Data List Form']/p/a");
		selenium.clickAt("//div[@title='Dynamic Data List Form']/p/a",
			RuntimeVariables.replace("Dynamic Data List Form"));
		selenium.waitForVisible("//div[1]/div/section");
		assertTrue(selenium.isVisible("//div[1]/div/section"));
		selenium.waitForVisible("//div[2]/div/section");
		assertTrue(selenium.isVisible("//div[2]/div/section"));
	}
}