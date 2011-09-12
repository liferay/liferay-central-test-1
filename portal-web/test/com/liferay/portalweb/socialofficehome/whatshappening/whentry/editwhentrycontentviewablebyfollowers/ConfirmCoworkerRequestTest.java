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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyfollowers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfirmCoworkerRequestTest extends BaseTestCase {
	public void testConfirmCoworkerRequest() throws Exception {
		selenium.open("/web/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//nav/ul/li[1]/a/span")) {
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
				if (selenium.isVisible("//section/div/div/div/div/div[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln says you are a coworker."),
			selenium.getText("//section/div/div/div/div/div[2]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[3]/span[1]/a"));
		selenium.clickAt("//div[3]/span[1]/a",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/ul[2]/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div/div/div/ul[2]/li[1]/a",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("//div[1]/a/img"));
		assertEquals(RuntimeVariables.replace("View 1 coworkers."),
			selenium.getText("//div[1]/div/div/div/div[2]/a"));
		selenium.mouseOver("//div[1]/a/img");
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln"),
			selenium.getText("//li/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerea@liferay.com"),
			selenium.getText("//div[3]/span"));
		selenium.open("/web/joebloggs/home/");
		assertFalse(selenium.isTextPresent("Notifications"));
	}
}