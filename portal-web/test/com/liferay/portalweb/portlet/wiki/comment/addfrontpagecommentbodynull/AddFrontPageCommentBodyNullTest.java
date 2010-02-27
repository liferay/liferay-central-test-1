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

package com.liferay.portalweb.portlet.wiki.comment.addfrontpagecommentbodynull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddFrontPageCommentBodyNullTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageCommentBodyNullTest extends BaseTestCase {
	public void testAddFrontPageCommentBodyNull() throws Exception {
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
		selenium.clickAt("link=Be the first.", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_36_postReplyBody0")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_postReplyBody0", RuntimeVariables.replace("T"));
		selenium.type("_36_postReplyBody0", RuntimeVariables.replace(""));
		selenium.keyPress("_36_postReplyBody0", RuntimeVariables.replace("\\48"));
		selenium.keyPress("_36_postReplyBody0", RuntimeVariables.replace("\\8"));
		selenium.clickAt("_36_postReplyButton0", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent("link=Be the first."));
		assertTrue(selenium.isElementPresent("_36_postReplyBody0"));
	}
}