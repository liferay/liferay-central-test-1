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

package com.liferay.portalweb.portlet.blogs.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Entry"),
			selenium.getText("//div[2]/div[1]/div[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"This is a test entry comment! \n Read More About Test Entry \u00bb"),
			selenium.getText("//div[2]/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"Read More About Test Entry \u00bb"),
			selenium.getText("//div[2]/div[3]/a"));
		selenium.clickAt("//div[2]/div[3]/a",
			RuntimeVariables.replace("Read More \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Entry"),
			selenium.getText("//span/span/span"));
		assertEquals(RuntimeVariables.replace("This is a test entry comment!"),
			selenium.getText("//form/div/div[3]"));
	}
}