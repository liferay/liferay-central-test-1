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

package com.liferay.portalweb.portlet.documentlibrary;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="TearDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"link=Document Library Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"link=Document Library Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean TestFolderAPresent = selenium.isElementPresent(
						"//td[4]/ul/li/strong/span");

				if (!TestFolderAPresent) {
					label = 2;

					continue;
				}

				selenium.click("//td[4]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 2:

				boolean TestFolderBPresent = selenium.isElementPresent(
						"//td[4]/ul/li/strong/span");

				if (!TestFolderBPresent) {
					label = 3;

					continue;
				}

				selenium.click("//td[4]/ul/li/strong/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//body/div[2]/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//body/div[2]/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));

			case 3:
				assertFalse(selenium.isElementPresent(
						"link=Edited Second Folder Test"));
				assertFalse(selenium.isElementPresent("link=Test Folder"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//img[@alt='Remove']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));
				assertFalse(selenium.isElementPresent(
						"//input[@value='Add Folder']"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//div[2]/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace("//div[2]/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//li[2]/ul/li[3]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isTextPresent(
						"Your request processed successfully."));
				selenium.click(RuntimeVariables.replace("//div[2]/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isElementPresent(
						"link=Document Library Test Page"));

			case 100:
				label = -1;
			}
		}
	}
}