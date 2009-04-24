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

package com.liferay.portalweb.portlet.wiki;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddSymbolTitleChildPageTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddSymbolTitleChildPageTest extends BaseTestCase {
	public void testAddSymbolTitleChildPage() throws Exception {
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

		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_36_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_title", RuntimeVariables.replace("Test@"));
		selenium.type("_36_title", RuntimeVariables.replace("Test@"));
		selenium.typeKeys("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid title."));
		assertTrue(selenium.isTextPresent(
				"This page does not exist yet and the title is not valid."));
		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_36_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_title", RuntimeVariables.replace("Test%"));
		selenium.type("_36_title", RuntimeVariables.replace("Test%"));
		selenium.typeKeys("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid title."));
		assertTrue(selenium.isTextPresent(
				"This page does not exist yet and the title is not valid."));
		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_36_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_title", RuntimeVariables.replace("Test&"));
		selenium.type("_36_title", RuntimeVariables.replace("Test&"));
		selenium.typeKeys("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid title."));
		assertTrue(selenium.isTextPresent(
				"This page does not exist yet and the title is not valid."));
		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_36_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_title", RuntimeVariables.replace("Test/"));
		selenium.type("_36_title", RuntimeVariables.replace("Test/"));
		selenium.typeKeys("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid title."));
		assertTrue(selenium.isTextPresent(
				"This page does not exist yet and the title is not valid."));
		selenium.click(RuntimeVariables.replace("link=Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Add Child Page"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_36_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_36_title", RuntimeVariables.replace("Test?"));
		selenium.type("_36_title", RuntimeVariables.replace("Test?"));
		selenium.typeKeys("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.type("_36_content",
			RuntimeVariables.replace("This is a Symbol Title Child Page Test!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid title."));
		assertTrue(selenium.isTextPresent(
				"This page does not exist yet and the title is not valid."));
	}
}