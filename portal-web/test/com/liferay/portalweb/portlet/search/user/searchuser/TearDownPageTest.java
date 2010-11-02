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

package com.liferay.portalweb.portlet.search.user.searchuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageTest extends BaseTestCase {
	public void testTearDownPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));

				String guestCommunityName = selenium.getText(
						"//nav[@id='breadcrumbs']/ul/li/span/a");
				RuntimeVariables.setValue("guestCommunityName",
					guestCommunityName);

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

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("${guestCommunityName}")
												.equals(selenium.getText(
										"//div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:

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

				selenium.saveScreenShotAndSource();

				boolean page1Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page1Present) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean page2Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page2Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean page3Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page3Present) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean page4Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page4Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:

				boolean page5Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page5Present) {
					label = 7;

					continue;
				}

				selenium.click(RuntimeVariables.replace("//li[2]/div/div[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//td[2]/ul/li[1]/span/span/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 7:
			case 100:
				label = -1;
			}
		}
	}
}