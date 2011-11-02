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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionTest extends BaseTestCase {
	public void testGuest_AssertAction() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Media Gallery Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Look and Feel"));
		assertFalse(selenium.isElementPresent("link=Configuration"));
		assertFalse(selenium.isElementPresent("link=Export / Import"));
		assertFalse(selenium.isElementPresent("//img[@alt='Remove']"));
		assertFalse(selenium.isElementPresent("link=Add Subfolder"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Add Folder"));
		assertFalse(selenium.isElementPresent("link=Add Repository"));
		assertFalse(selenium.isElementPresent("link=Add Shortcut"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Add Subfolder"));
		assertFalse(selenium.isElementPresent("link=Add Media"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Media Gallery Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Recent Images",
			RuntimeVariables.replace("Recent Images"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Permissions Image 2 Test"));
	}
}