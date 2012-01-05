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

package com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitesSite3TypeRestrictedTest extends BaseTestCase {
	public void testAddSitesSite3TypeRestricted() throws Exception {
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div/div/div/div/ul/li/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[3]/div/section/header/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//form/div[2]/span/span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add Site"),
			selenium.getText("//form/div[2]/span/span/button[1]"));
		selenium.clickAt("//form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Add Site"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//h1/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Information"),
			selenium.getText("//h1/span"));
		assertTrue(selenium.isVisible("//input"));
		selenium.type("//input",
			RuntimeVariables.replace("Restricted Site3 Name"));
		assertTrue(selenium.isVisible("//textarea"));
		selenium.type("//textarea",
			RuntimeVariables.replace("Restricted Site3 Description"));
		assertEquals("Next", selenium.getValue("//input[@value='Next']"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/div[1]/h1/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Settings"),
			selenium.getText("//div[2]/div[1]/h1/span"));
		assertTrue(selenium.isVisible("//select"));
		selenium.select("//select",
			RuntimeVariables.replace("Default Social Office Site"));
		selenium.select("//span[2]/span/span/select",
			RuntimeVariables.replace("Restricted"));
		assertTrue(selenium.isVisible("//span[2]/span/span/select"));
		assertEquals("Next", selenium.getValue("//input[@value='Next']"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[3]/div[1]/h1/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Customization"),
			selenium.getText("//div[3]/div[1]/h1/span"));
		assertTrue(selenium.isChecked("//div[4]/span[1]/input"));
		assertTrue(selenium.isChecked("//span[2]/input"));
		assertTrue(selenium.isChecked("//span[3]/input"));
		assertTrue(selenium.isChecked("//span[4]/input"));
		assertTrue(selenium.isChecked("//span[5]/input"));
		assertTrue(selenium.isChecked("//span[6]/input"));
		assertTrue(selenium.isChecked("//span[7]/input"));
		assertEquals("Save", selenium.getValue("//input[@value='Save']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Restricted Site3 Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')][3]/span[2]/a"));
	}
}