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

package com.liferay.portalweb.portlet.pagecomments.comment.editcommentreplybodynull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditCommentReplyBodyNullTest extends BaseTestCase {
	public void testEditCommentReplyBodyNull() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Page Comments Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("This is a test reply comment."),
			selenium.getText("//form/div/div/div[3]/div/div[3]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[3]/div/div[3]/div/div[3]/div/div[4]/div/span/a/span"));
		selenium.clickAt("//div[3]/div/div[3]/div/div[3]/div/div[4]/div/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.type("_107_editReplyBody2", RuntimeVariables.replace(""));
		selenium.saveScreenShotAndSource();
		selenium.keyPress("_107_editReplyBody2",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("_107_editReplyBody2", RuntimeVariables.replace("\\8"));
		selenium.clickAt("//input[@value='Publish' and @disabled='']",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("_107_editReplyBody2"));
		assertTrue(selenium.isVisible(
				"//input[@value='Publish' and @disabled='']"));
		assertFalse(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Page Comments Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("This is a test reply comment."),
			selenium.getText("//form/div/div/div[3]/div/div[3]/div/div[1]"));
	}
}