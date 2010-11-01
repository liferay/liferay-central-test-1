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

package com.liferay.portalweb.plugins.kaleo.assetpublisher.dldocument.viewdocumentcompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDocumentCompletedGuestTest extends BaseTestCase {
	public void testViewDocumentCompletedGuest() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Asset Publisher Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Asset Publisher Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent("//section"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("//h1/span[2]"));
		assertEquals(RuntimeVariables.replace("test_document.txt"),
			selenium.getText("//h3/a"));
		assertEquals(RuntimeVariables.replace("test_document.txt"),
			selenium.getText("//span/a/span"));
		assertTrue(selenium.isPartialText("//div[2]/a", "Read More"));
		assertFalse(selenium.isTextPresent("There are no results."));
		selenium.clickAt("//div[2]/a", RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("test_document.txt"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("test_document.txt"),
			selenium.getText("//div[2]/div[1]/div/a"));
	}
}