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

package com.liferay.portalweb.portlet.pagecomments.comment.canceladdcommentreply;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="CancelAddCommentReplyTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CancelAddCommentReplyTest extends BaseTestCase {
	public void testCancelAddCommentReply() throws Exception {
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

		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Post Reply", RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_107_postReplyBody1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible("_107_postReplyBody1"));
		selenium.type("_107_postReplyBody1",
			RuntimeVariables.replace("This is a test reply comment."));
		selenium.keyPress("_107_postReplyBody1",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("_107_postReplyBody1", RuntimeVariables.replace("\\8"));
		selenium.clickAt("//tr[@id='_107_postReplyForm1']/td/input[2]",
			RuntimeVariables.replace(""));
		assertFalse(selenium.isTextPresent("This is a test reply comment."));
		assertFalse(selenium.isVisible("_107_postReplyBody1"));
	}
}