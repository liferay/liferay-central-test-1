/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portal.permissions.blogs.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

public class Guest_AssertActionsTest extends BaseTestCase {
	public void testGuest_AssertActions() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Blogs Permissions Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Blogs Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertTrue(selenium.isElementPresent(
				"link=Permissions Blogs Test Entry"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Add Blog Entry']"));
		selenium.click(RuntimeVariables.replace(
				"link=Permissions Blogs Test Entry"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent("link=Add Comment"));
		assertFalse(selenium.isElementPresent("link=Edit"));
		assertFalse(selenium.isElementPresent("link=Permissions"));
		assertFalse(selenium.isElementPresent("link=Delete"));
		assertTrue(selenium.isElementPresent("link=Sign in to vote."));
	}
}