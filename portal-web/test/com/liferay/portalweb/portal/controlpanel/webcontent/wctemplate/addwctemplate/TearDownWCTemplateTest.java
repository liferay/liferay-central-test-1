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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplate.addwctemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWCTemplateTest extends BaseTestCase {
	public void testTearDownWCTemplate() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText(
						"//span[@title='Manage']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
					RuntimeVariables.replace("Manage"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Template')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Templates"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Template')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Template')]",
					RuntimeVariables.replace("Templates"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//iframe[@id='_15_openTemplatesView']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("//iframe[@id='_15_openTemplatesView']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Add Template']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean templatePresent = selenium.isElementPresent(
						"//input[@name='_15_rowIds']");

				if (!templatePresent) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked("//input[@name='_15_allRowIds']"));
				selenium.clickAt("//input[@name='_15_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_15_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected templates[\\s\\S]$"));

			case 2:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}