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

package com.liferay.portalweb.portal.permissions.blogs.scope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="SA_CleanUpTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SA_CleanUpTest extends BaseTestCase {
	public void testSA_CleanUp() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:

				boolean InControlPanel = selenium.isElementPresent(
						"link=Back to Guest");

				if (!InControlPanel) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Back to Guest"));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.click(RuntimeVariables.replace(
						"//div[@id='_145_myPlacesContainer']/ul/li[6]/a/span"));
				selenium.waitForPageToLoad("30000");

				boolean ScopePagePresent = selenium.isElementPresent(
						"link=Blogs Scope Permissions Page");

				if (!ScopePagePresent) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Blogs Scope Permissions Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean ScopePortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!ScopePortletPresent) {
					label = 5;

					continue;
				}

				boolean EntryAPresent = selenium.isElementPresent("link=Delete");

				if (!EntryAPresent) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:

				boolean EntryBPresent = selenium.isElementPresent("link=Delete");

				if (!EntryBPresent) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

			case 5:
				selenium.click(RuntimeVariables.replace("link=Scope Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace("link=Manage Pages"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='_88_layoutsTreeOutput']/ul/li/ul/li[2]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click(RuntimeVariables.replace(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/ul/li[2]/a/span"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//li[@id='_88_tabs3pageTabsId']/a"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 6:
				selenium.click(RuntimeVariables.replace("link=Guest"));
				selenium.waitForPageToLoad("30000");
				selenium.click(RuntimeVariables.replace(
						"link=Blogs Permissions Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				boolean GuestPortletPresent = selenium.isElementPresent(
						"//span[3]/a/img");

				if (!GuestPortletPresent) {
					label = 8;

					continue;
				}

				boolean EntryCPresent = selenium.isElementPresent("link=Delete");

				if (!EntryCPresent) {
					label = 7;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 7:
				selenium.click("//img[@alt='Remove']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to remove this component[\\s\\S]$"));

			case 8:
				selenium.click("link=Application");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@id='Collaboration-Blogs']/p/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//div[@id='Collaboration-Blogs']/p/a");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@value='Add Blog Entry']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 100:
				label = -1;
			}
		}
	}
}