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

package com.liferay.portalweb.portlet.shopping.archivedsetup.restorearchivedsetup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAcceptedCreditCardTest extends BaseTestCase {
	public void testConfigurePortletAcceptedCreditCard()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		selenium.waitForVisible("//select[@id='_86_current_cc_types']");
		selenium.addSelection("//select[@id='_86_current_cc_types']",
			RuntimeVariables.replace("MasterCard"));
		selenium.waitForVisible("//div[2]/div/span/span/button[1]");
		selenium.clickAt("//div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Right Arrow"));
		selenium.waitForPartialText("//select[@id='_86_available_cc_types']",
			"MasterCard");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "MasterCard"));
		selenium.addSelection("//select[@id='_86_current_cc_types']",
			RuntimeVariables.replace("American Express"));
		selenium.clickAt("//div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Right Arrow"));
		selenium.waitForPartialText("//select[@id='_86_available_cc_types']",
			"American Express");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "American Express"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Discover"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Visa"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "MasterCard"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "American Express"));
	}
}