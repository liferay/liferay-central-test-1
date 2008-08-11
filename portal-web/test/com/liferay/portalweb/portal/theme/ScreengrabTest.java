/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.theme;

import com.liferay.portal.kernel.util.FileUtil;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * <a href="ScreengrabTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ScreengrabTest extends BaseTestCase {
	public void testScreengrab() throws Exception {
		selenium.click("link=Theme Test Page");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=23")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		FileUtil.mkdirs(
			"L:\\portal\\build\\portal-web\\test-output\\brochure\\");
		selenium.captureEntirePageScreenshot("L:\\portal\\build\\portal-web\\test-output\\brochure\\ScreengrabTest.jpg",
			"");
		selenium.click("//div[@id='navigation']/ul/li[1]/a/span");
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='portlet-small-icon-bar_9']/nobr[2]/a/img")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		FileUtil.mkdirs(
			"L:\\portal\\build\\portal-web\\test-output\\brochure\\");
		selenium.captureEntirePageScreenshot("L:\\portal\\build\\portal-web\\test-output\\brochure\\ScreengrabTest2.jpg",
			"");
	}
}