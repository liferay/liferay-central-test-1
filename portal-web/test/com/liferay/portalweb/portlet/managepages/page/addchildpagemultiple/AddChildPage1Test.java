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

package com.liferay.portalweb.portlet.managepages.page.addchildpagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddChildPage1Test extends BaseTestCase {
	public void testAddChildPage1() throws Exception {
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
						if (selenium.isElementPresent(
									"link=Manage Pages Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Manage Pages Test Page",
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

				selenium.clickAt("link=Children", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.type("_88_name_en_US",
					RuntimeVariables.replace("Child1 Test1 Page1"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//section/div/div/div/div"));

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li/div/div[3]/a");

				if (childPagePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[1]",
					RuntimeVariables.replace(""));

			case 2:
				assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
					selenium.getText("//nav/ul/li[2]/ul/li"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Child1 Test1 Page1")
												.equals(selenium.getText(
										"//li[2]/ul/li/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Child1 Test1 Page1"),
					selenium.getText("//li[2]/ul/li/div/div[3]/a"));

			case 100:
				label = -1;
			}
		}
	}
}