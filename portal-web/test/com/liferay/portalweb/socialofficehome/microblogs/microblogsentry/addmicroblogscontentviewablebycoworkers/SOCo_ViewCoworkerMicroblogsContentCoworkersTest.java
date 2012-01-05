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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebycoworkers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOCo_ViewCoworkerMicroblogsContentCoworkersTest
	extends BaseTestCase {
	public void testSOCo_ViewCoworkerMicroblogsContentCoworkers()
		throws Exception {
		selenium.open("/user/socialofficecoworkersn/home");
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
		assertEquals(RuntimeVariables.replace("What's happening?"),
			selenium.getText("//div[1]/h1/span"));
		assertTrue(selenium.isElementPresent("//textarea"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[1]"));
		assertFalse(selenium.isTextPresent("Microblogs Content"));
		selenium.clickAt("//div/div/div/div[1]/ul/li[3]/a",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//div[2]/div/div/div/section/header/h1/span[2]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='entry-bubble ']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible("//div/span/a/img"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs (joebloggs)"),
			selenium.getText("//div[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("//div[@class='content']"));
		assertFalse(selenium.isTextPresent("Reply"));
	}
}