/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.wiki.wikipage.searchrenamewikipagetitle;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RenameWikiPageTitleTest extends BaseTestCase {
	public void testRenameWikiPageTitle() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=All Pages", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Wiki Page Test", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//h1[@class='page-title']",
				"Wiki Page Test"));
		assertFalse(selenium.isPartialText("//h1[@class='page-title']",
				"Wiki Page Test Renamed"));
		assertFalse(selenium.isTextPresent("(Redirected from Wiki Page Test)"));
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Move", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_36_newTitle",
			RuntimeVariables.replace("Wiki Page Test Renamed"));
		selenium.clickAt("//input[@value='Rename']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//h1[@class='page-title']",
				"Wiki Page Test Renamed"));
		assertEquals(RuntimeVariables.replace(
				"(Redirected from Wiki Page Test)"),
			selenium.getText("//div[@class='page-redirect']"));
		assertNotEquals(RuntimeVariables.replace("Wiki Page Test"),
			selenium.getText("//h1[@class='page-title']"));
	}
}