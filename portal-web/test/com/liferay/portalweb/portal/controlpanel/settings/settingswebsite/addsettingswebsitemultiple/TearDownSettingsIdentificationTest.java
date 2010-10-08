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

package com.liferay.portalweb.portal.controlpanel.settings.settingswebsite.addsettingswebsitemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsIdentificationTest extends BaseTestCase {
	public void testTearDownSettingsIdentification() throws Exception {
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[7]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[9]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div"));
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[7]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[9]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div"));
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[7]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[9]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div"));
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[7]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[9]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div"));
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("phoneNumbersLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[7]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("additionalEmailAddressesLink",
			RuntimeVariables.replace(""));
		selenium.clickAt("//div[8]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("websitesLink", RuntimeVariables.replace(""));
		selenium.clickAt("//div[9]/fieldset/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Delete Row"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//section/div/div/div/div"));
	}
}