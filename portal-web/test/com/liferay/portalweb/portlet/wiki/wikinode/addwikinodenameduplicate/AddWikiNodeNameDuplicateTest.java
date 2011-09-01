/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.wikinode.addwikinodenameduplicate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWikiNodeNameDuplicateTest extends BaseTestCase {
	public void testAddWikiNodeNameDuplicate() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//li[9]/a"));
		selenium.clickAt("//li[9]/a", RuntimeVariables.replace("Wiki"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Wiki']",
			RuntimeVariables.replace("Add Wiki"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_154_name']",
			RuntimeVariables.replace("Wiki Node Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_154_description']",
			RuntimeVariables.replace("Wiki Node Description"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Please enter a unique node name."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}