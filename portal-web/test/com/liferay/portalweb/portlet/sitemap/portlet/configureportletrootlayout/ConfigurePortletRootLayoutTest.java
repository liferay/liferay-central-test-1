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

package com.liferay.portalweb.portlet.sitemap.portlet.configureportletrootlayout;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletRootLayoutTest extends BaseTestCase {
	public void testConfigurePortletRootLayout() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Site Map Test Page");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[1]"));
		assertEquals(RuntimeVariables.replace("Site Map Test Page"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[2]"));
		assertEquals(RuntimeVariables.replace("Site Map Test Child Page"),
			selenium.getText("//div[@class='portlet-body']/ul/li/ul/li/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		selenium.waitForVisible("//select[@id='_86_rootLayoutUuid']");
		selenium.select("//select[@id='_86_rootLayoutUuid']",
			RuntimeVariables.replace("regexp:-\\sSite Map Test Page"));
		selenium.select("//select[@id='_86_displayDepth']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Site Map Test Page");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map Test Child Page"),
			selenium.getText("//div[@class='portlet-body']/ul/li/a"));
	}
}