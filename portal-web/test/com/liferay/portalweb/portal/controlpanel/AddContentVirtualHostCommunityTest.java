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

package com.liferay.portalweb.portal.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddContentVirtualHostCommunityTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddContentVirtualHostCommunityTest extends BaseTestCase {
	public void testAddContentVirtualHostCommunity() throws Exception {
		selenium.click(RuntimeVariables.replace("link=Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Back to My Community"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//li[6]/ul/li[1]/a[2]"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//div[2]/ul/li/a/span"));
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Add Application");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='News-Announcements']/p/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//div[@id='News-Announcements']/p/a");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Configuration")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Manage Entries"));
		selenium.waitForPageToLoad("30000");
		selenium.select("_84_distributionScope", "label=General");
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Add Entry']"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_84_title", RuntimeVariables.replace("Welcome"));
		selenium.type("_84_content",
			RuntimeVariables.replace(
				"Hello and welcome to the Virtual Hosting Test Community!"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=New Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Hello and welcome to the Virtual Hosting Test Community!"));
	}
}