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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyeveryone;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWHEntryContentViewableByEveryoneTest extends BaseTestCase {
	public void testEditWHEntryContentViewableByEveryone()
		throws Exception {
		selenium.open("/web/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div[1]/div/div/div/ul[1]/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div/div[1]/div/div/div/ul[1]/li[1]/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("What's happening?"),
			selenium.getText("//div[1]/h1/span"));
		assertTrue(selenium.isVisible("//div[@class='my-entry-bubble ']"));
		assertTrue(selenium.isVisible("//div/span/a/img"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("//div[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Whats Happening Content"),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[2]/a/span"));
		selenium.clickAt("//span[2]/a/span", RuntimeVariables.replace("Edit"));

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"What do you want to say instead?"),
			selenium.getText("//h1/span"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Whats Happening Content"),
			selenium.getText("//textarea"));
		selenium.typeKeys("//textarea", RuntimeVariables.replace(" Edit"));
		selenium.saveScreenShotAndSource();
		assertEquals("Everyone", selenium.getSelectedLabel("//span/select"));
		assertEquals(RuntimeVariables.replace("123"),
			selenium.getText("//span[@class='microblogs-countdown']"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		assertTrue(selenium.isVisible("//div/span/a/img"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Joe Bloggs test@liferay.com")
										.equals(selenium.getText(
								"//div[@class='user-name']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("//div[@class='user-name']"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Whats Happening Content Edit")
										.equals(selenium.getText(
								"//div[@class='content']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Whats Happening Content Edit"),
			selenium.getText("//div[@class='content']"));
	}
}