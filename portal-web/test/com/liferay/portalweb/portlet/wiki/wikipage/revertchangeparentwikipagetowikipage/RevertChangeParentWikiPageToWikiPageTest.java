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

package com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentwikipagetowikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RevertChangeParentWikiPageToWikiPageTest extends BaseTestCase {
	public void testRevertChangeParentWikiPageToWikiPage()
		throws Exception {
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
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Wiki2 Page2 Test2", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Wiki1 Page1 Test1"));
		selenium.clickAt("link=Wiki1 Page1 Test1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki2 Page2 Test2"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Wiki1 Page1 Test1"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[4]/span/a"));
		assertEquals(RuntimeVariables.replace("This is a wiki1 page1 test1."),
			selenium.getText("//div[@class='wiki-body']"));
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Changed parent from \".\""),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[7]"));
		selenium.clickAt("link=Revert", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reverted to 1.0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[7]"));
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
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Wiki2 Page2 Test2", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Wiki1 Page1 Test1"));
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
		selenium.click(RuntimeVariables.replace("link=All Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Wiki1 Page1 Test1", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki1 Page1 Test1"),
			selenium.getText(
				"//ul[@class='breadcrumbs lfr-component']/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("This is a wiki1 page1 test1."),
			selenium.getText("//div[@class='wiki-body']"));
	}
}