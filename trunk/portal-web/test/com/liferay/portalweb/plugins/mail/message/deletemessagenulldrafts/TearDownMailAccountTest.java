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

package com.liferay.portalweb.plugins.mail.message.deletemessagenulldrafts;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownMailAccountTest extends BaseTestCase {
	public void testTearDownMailAccount() throws Exception {
		selenium.open("/web/guest/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Mail Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Mail Test Page",
			RuntimeVariables.replace("Mail Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"liferay.qa.mail.portlet.60x@gmail.com"),
			selenium.getText("//div[1]/div/ul/li/span/span/a"));
		selenium.clickAt("//div[1]/div/ul/li/span/span/a",
			RuntimeVariables.replace("liferay.qa.mail.portlet.60x@gmail.com"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Edit Account")
										.equals(selenium.getText("//a[3]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Edit Account"),
			selenium.getText("//a[3]"));
		selenium.clickAt("//a[3]", RuntimeVariables.replace("Edit Account"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[@class='delete-account']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent("//a[@class='delete-account']"));
		selenium.clickAt("//a[@class='delete-account']",
			RuntimeVariables.replace("Delete Account"));
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this account[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.open("/web/guest/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Mail Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Mail Test Page",
			RuntimeVariables.replace("Mail Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isTextPresent("//div[1]/div/ul/li/span/span/a"));
	}
}