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

package com.liferay.portalweb.portal.tags.wiki.addwikipagetag;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownTagTest extends BaseTestCase {
	public void testTearDownTag() throws Exception {
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
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Tags", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean tag1Present = selenium.isElementPresent(
						"//div[4]/ul/li[1]/span/a");

				if (!tag1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[4]/ul/li[1]/span/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Delete']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));
				Thread.sleep(5000);

				boolean tag2Present = selenium.isElementPresent(
						"//div[4]/ul/li[1]/span/a");

				if (!tag2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[4]/ul/li[1]/span/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Delete']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));
				Thread.sleep(5000);

				boolean tag3Present = selenium.isElementPresent(
						"//div[4]/ul/li[1]/span/a");

				if (!tag3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[4]/ul/li[1]/span/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Delete']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));
				Thread.sleep(5000);

				boolean tag4Present = selenium.isElementPresent(
						"//div[4]/ul/li[1]/span/a");

				if (!tag4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//div[4]/ul/li[1]/span/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Delete']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));
				Thread.sleep(5000);

				boolean tag5Present = selenium.isElementPresent(
						"//div[4]/ul/li[1]/span/a");

				if (!tag5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//div[4]/ul/li[1]/span/a",
					RuntimeVariables.replace(""));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Delete']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Delete']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this tag[\\s\\S]$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}