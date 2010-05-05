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

package com.liferay.portalweb.portal.tags.wiki.addwikipagetagmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddWikiPage2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddWikiPage2Test extends BaseTestCase {
	public void testAddWikiPage2() throws Exception {
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
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_36_title", RuntimeVariables.replace("Wiki2 Page2 Test2"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a wiki2 page2 test2."));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isElementPresent("link=Wiki2 Page2 Test2"));
		selenium.clickAt("link=Wiki2 Page2 Test2", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//h1[@class='page-title']",
				"Wiki2 Page2 Test2"));
		assertEquals(RuntimeVariables.replace("This is a wiki2 page2 test2."),
			selenium.getText("//div[@class='wiki-body']"));
	}
}