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

package com.liferay.portalweb.portlet.managepages.page.addpagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPage3Test extends BaseTestCase {
	public void testAddPage3() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("main-content", RuntimeVariables.replace(""));
				selenium.clickAt("dockbar", RuntimeVariables.replace(""));
				selenium.clickAt("navigation", RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[4]/div/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[4]/div/ul/li[1]/a",
					RuntimeVariables.replace("Manage Pages"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("//div/div[3]/a", "Liferay")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Pages (Liferay)"),
					selenium.getText("//div/div[3]/a"));

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[3]/ul/li/div/div[1]",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//li/ul/li[1]/div/div[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div/div[3]/a", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("_88_name_en_US",
					RuntimeVariables.replace("Manage3 Pages3 Test3 Page3"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Manage3 Pages3 Test3 Page3")
												.equals(selenium.getText(
										"//li/ul/li[4]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Manage3 Pages3 Test3 Page3"),
					selenium.getText("//li/ul/li[4]/div/div[3]/a"));

			case 100:
				label = -1;
			}
		}
	}
}