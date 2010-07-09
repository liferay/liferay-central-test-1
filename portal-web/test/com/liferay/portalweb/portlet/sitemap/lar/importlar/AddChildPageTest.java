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

package com.liferay.portalweb.portlet.sitemap.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddChildPageTest extends BaseTestCase {
	public void testAddChildPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Site Map Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Site Map Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div/div[3]/div/ul/li[1]/a",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Guest")
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
					label = 2;

					continue;
				}

				selenium.clickAt("//li/div/div[1]", RuntimeVariables.replace(""));

			case 2:
				selenium.clickAt("link=Children", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_88_name_en_US",
					RuntimeVariables.replace("Child Test Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//section/div/div/div/div"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Site Map Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Site Map Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("link=Child Test Page"));
				selenium.clickAt("link=Child Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText("//nav/ul/li[3]/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}