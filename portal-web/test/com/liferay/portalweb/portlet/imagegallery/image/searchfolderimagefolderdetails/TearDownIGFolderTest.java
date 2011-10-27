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

package com.liferay.portalweb.portlet.imagegallery.image.searchfolderimagefolderdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownIGFolderTest extends BaseTestCase {
	public void testTearDownIGFolder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Image Gallery Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Image Gallery Test Page",
					RuntimeVariables.replace("Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean igFolder1Present = selenium.isElementPresent(
						"//div[2]/a/span");

				if (!igFolder1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/a/span",
					RuntimeVariables.replace("MG Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a",
					RuntimeVariables.replace("Delete"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:

				boolean igFolder2Present = selenium.isElementPresent(
						"//div[2]/a/span");

				if (!igFolder2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[2]/a/span",
					RuntimeVariables.replace("MG Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a",
					RuntimeVariables.replace("Delete"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:

				boolean igFolder3Present = selenium.isElementPresent(
						"//div[2]/a/span");

				if (!igFolder3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[2]/a/span",
					RuntimeVariables.replace("MG Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a",
					RuntimeVariables.replace("Delete"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 4:

				boolean igFolder4Present = selenium.isElementPresent(
						"//div[2]/a/span");

				if (!igFolder4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//div[2]/a/span",
					RuntimeVariables.replace("MG Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a",
					RuntimeVariables.replace("Delete"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 5:

				boolean igFolder5Present = selenium.isElementPresent(
						"//div[2]/a/span");

				if (!igFolder5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//div[2]/a/span",
					RuntimeVariables.replace("MG DL Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a",
					RuntimeVariables.replace("Delete"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}