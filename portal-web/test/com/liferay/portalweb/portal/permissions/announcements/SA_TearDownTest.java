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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_TearDownTest extends BaseTestCase {
	public void testSA_TearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Welcome")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Welcome", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_58_login",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("_58_password", RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Announcements Permissions Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Manage Entries")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Manage Entries",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_84_distributionScope")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("_84_distributionScope",
					RuntimeVariables.replace("label=Guest"));
				selenium.waitForPageToLoad("30000");

				boolean GuestAnnouncementPresent = selenium.isElementPresent(
						"//td[6]/ul/li/strong/a");

				if (!GuestAnnouncementPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
				selenium.select("_84_distributionScope",
					RuntimeVariables.replace("label=General"));
				selenium.waitForPageToLoad("30000");

				boolean GeneralAnnouncementPresent = selenium.isElementPresent(
						"//td[6]/ul/li/strong/a");

				if (!GeneralAnnouncementPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:
				selenium.select("_84_distributionScope",
					RuntimeVariables.replace("label=Community Admin"));
				selenium.waitForPageToLoad("30000");

				boolean CAAnnouncementPresent = selenium.isElementPresent(
						"//td[6]/ul/li/strong/a");

				if (!CAAnnouncementPresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:
				selenium.select("_84_distributionScope",
					RuntimeVariables.replace("label=Member"));
				selenium.waitForPageToLoad("30000");

				boolean MemberAnnouncementPresent = selenium.isElementPresent(
						"//td[6]/ul/li/strong/a");

				if (!MemberAnnouncementPresent) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[6]/ul/li/strong/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Delete")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:
				selenium.clickAt("link=Announcements Permissions Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div/div[3]/div/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Liferay")
												.equals(selenium.getText(
										"//div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 6;

					continue;
				}

				selenium.clickAt("//li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 6:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Welcome")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page1Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page1Present) {
					label = 7;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 7:

				boolean page2Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page2Present) {
					label = 8;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 8:

				boolean page3Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page3Present) {
					label = 9;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 9:

				boolean page4Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page4Present) {
					label = 10;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 10:

				boolean page5Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page5Present) {
					label = 11;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 11:
			case 100:
				label = -1;
			}
		}
	}
}