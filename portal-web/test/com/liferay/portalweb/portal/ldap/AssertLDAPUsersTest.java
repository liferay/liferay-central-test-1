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

package com.liferay.portalweb.portal.ldap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertLDAPUsersTest extends BaseTestCase {
	public void testAssertLDAPUsers() throws Exception {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_authenticationLink']", "Authentication"));
		selenium.clickAt("//a[@id='_130_authenticationLink']",
			RuntimeVariables.replace("Authentication"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=LDAP")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=LDAP", RuntimeVariables.replace("LDAP"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@id='_130_ldap.auth.enabledCheckbox']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked(
				"//input[@id='_130_ldap.auth.enabledCheckbox']"));
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test LDAP 1"),
			selenium.getText("//fieldset[2]/div/div/table/tbody/tr/td[1]"));
		selenium.clickAt("//img[@alt='Edit']", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Test LDAP Users']",
			RuntimeVariables.replace("Test LDAP Users"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[1]/div[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("janesmith")
										.equals(selenium.getText("//td[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("janesmith"),
			selenium.getText("//td[2]"));
		assertEquals(RuntimeVariables.replace("janesmith@liferay.com"),
			selenium.getText("//td[3]"));
		assertEquals(RuntimeVariables.replace("Jane"),
			selenium.getText("//td[4]"));
		assertEquals(RuntimeVariables.replace("Smith"),
			selenium.getText("//td[5]"));
		assertEquals(RuntimeVariables.replace("********"),
			selenium.getText("//td[6]"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[7]"));
		assertEquals(RuntimeVariables.replace("1"), selenium.getText("//td[8]"));
		assertEquals(RuntimeVariables.replace("lukeskywalker"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("lukeskywalker@liferay.com"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Luke"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Skywalker"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("********"),
			selenium.getText("//tr[3]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("martinluther"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("martinluther@liferay.com"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("Martin"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("Luther"),
			selenium.getText("//tr[4]/td[5]"));
		assertEquals(RuntimeVariables.replace("********"),
			selenium.getText("//tr[4]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[4]/td[7]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[4]/td[8]"));
		System.out.println("LDAP Users have been detected.");
		selenium.click("//button");
	}
}