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

package com.liferay.portalweb.socialofficehome.notifications.requests.requestccaddascoworker;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class IgnoreNotificationsAddAsCoworkerCCTest extends BaseTestCase {
	public void testIgnoreNotificationsAddAsCoworkerCC()
		throws Exception {
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div[1]/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div/div/div/div[1]/ul/li[1]/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Notifications"),
			selenium.getText("//div[2]/div/section/header/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//section/div/div/div/div/div[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln says you are a coworker."),
			selenium.getText("//section/div/div/div/div/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("Ignore"),
			selenium.getText("//div[2]/span[2]/a"));
		selenium.clickAt("//div[2]/span[2]/a",
			RuntimeVariables.replace("Ignore"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div[1]/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div/div/div/div[1]/ul/li[2]/a",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("You have no contacts."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isElementPresent(
				"//section/div/div/div/div/div[2]"));
		assertFalse(selenium.isElementPresent(
				"//section/div/div/div/div/div[2]/div[1]"));
		assertFalse(selenium.isTextPresent(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln says you are a coworker."));
	}
}