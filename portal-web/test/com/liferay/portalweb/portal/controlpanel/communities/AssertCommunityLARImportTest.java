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

package com.liferay.portalweb.portal.controlpanel.communities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

public class AssertCommunityLARImportTest extends BaseTestCase {
	public void testAssertCommunityLARImport() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Back to My Community")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Back to My Community"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//div[@id='_145_myPlacesContainer']/ul/li[8]/a/span"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Community LAR Import Test Page"));
		selenium.click(RuntimeVariables.replace(
				"link=Community LAR Import Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Message Boards"));
		assertTrue(selenium.isElementPresent("link=LAR Import Test Category"));
		selenium.click(RuntimeVariables.replace("link=LAR Import Test Category"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Hello LAR Files"));
		selenium.click(RuntimeVariables.replace("link=Hello LAR Files"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"I hope that you are working. If you are not working. I will be sad."));
		selenium.click(RuntimeVariables.replace(
				"link=Community LAR Import Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//div[@id='_145_myPlacesContainer']/ul/li[2]/a/span[1]"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
	}
}