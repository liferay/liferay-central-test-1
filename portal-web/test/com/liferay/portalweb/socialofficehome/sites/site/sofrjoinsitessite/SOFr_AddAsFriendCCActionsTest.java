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

package com.liferay.portalweb.socialofficehome.sites.site.sofrjoinsitessite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOFr_AddAsFriendCCActionsTest extends BaseTestCase {
	public void testSOFr_AddAsFriendCCActions() throws Exception {
		selenium.open("/user/socialofficefriendsn/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//section/div/div/div/div/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//section/div/div/div/div/ul/li[2]/a",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Find People"),
			selenium.getText("//span[3]/a/span"));
		selenium.clickAt("//span[3]/a/span",
			RuntimeVariables.replace("Find People"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_1_WAR_contactsportlet_keywords1']",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.clickAt("xPath=(//input[@value='Search'])[2]",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[2]/div[1]/a"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[3]/span"));
		selenium.clickAt("//strong/a/img", RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add as Friend"),
			selenium.getText("//li[2]/a"));
		selenium.click(RuntimeVariables.replace("//li[2]/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}