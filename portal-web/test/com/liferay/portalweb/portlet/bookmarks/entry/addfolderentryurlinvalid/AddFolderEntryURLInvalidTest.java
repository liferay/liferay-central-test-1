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

package com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolderEntryURLInvalidTest extends BaseTestCase {
	public void testAddFolderEntryURLInvalid() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Bookmarks Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/ul/li[5]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_28_name", RuntimeVariables.replace("Test Folder Entry"));
		selenium.saveScreenShotAndSource();
		selenium.type("_28_url", RuntimeVariables.replace("www.liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("_28_description",
			RuntimeVariables.replace("This is a test folder entry."));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isTextPresent("Please enter a valid URL."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		assertEquals(RuntimeVariables.replace("Please enter a valid URL."),
			selenium.getText(
				"//div[@class='yui3-aui-form-validator-message url']"));
	}
}