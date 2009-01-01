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

package com.liferay.portalweb.portlet.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddTemplateTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddTemplateTest extends BaseTestCase {
	public void testAddTemplate() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Web Content Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Web Content Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Templates"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Add Template']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace(
				"//input[@value='Add Template']"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_newTemplateId")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_15_newTemplateId", RuntimeVariables.replace("Test"));
		selenium.type("_15_newTemplateId", RuntimeVariables.replace("Test"));
		selenium.typeKeys("_15_name",
			RuntimeVariables.replace("Test Web Content Template"));
		selenium.type("_15_name",
			RuntimeVariables.replace("Test Web Content Template"));
		selenium.typeKeys("_15_description",
			RuntimeVariables.replace("This is a test web content!"));
		selenium.type("_15_description",
			RuntimeVariables.replace("This is a test web content!"));
		selenium.type("_15_xsl",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\webcontent\\Template.htm"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("save-and-continue")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("save-and-continue"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Cancel']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("//input[@value='Cancel']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Test Web Content Template"));
	}
}