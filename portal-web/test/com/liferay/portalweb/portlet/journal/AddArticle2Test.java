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

package com.liferay.portalweb.portlet.journal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddArticle2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AddArticle2Test extends BaseTestCase {
	public void testAddArticle2() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Add Article']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("//input[@value='Add Article']");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_title")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.typeKeys("_15_title",
			RuntimeVariables.replace("Test Journal Article 2"));
		selenium.type("_15_title",
			RuntimeVariables.replace("Test Journal Article 2"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_15_editor")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("FCKeditor1___Frame")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe[@id=\"_15_editor\"]");
		selenium.selectFrame("//iframe[@id=\"FCKeditor1___Frame\"]");
		selenium.selectFrame("//iframe");
		selenium.typeKeys("//body",
			RuntimeVariables.replace("This is a test Journal Article 2!"));
		selenium.type("//body",
			RuntimeVariables.replace("This is a test Journal Article 2!"));
		selenium.selectFrame("relative=top");
		selenium.select("_15_type",
			RuntimeVariables.replace("label=Announcements"));
		selenium.select("_15_displayDateMonth",
			RuntimeVariables.replace("label=April"));
		selenium.select("_15_displayDateDay",
			RuntimeVariables.replace("label=10"));
		selenium.select("_15_displayDateYear",
			RuntimeVariables.replace("label=2010"));
		selenium.select("_15_displayDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_displayDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.click("_15_neverExpireCheckbox");
		selenium.select("_15_expirationDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_15_expirationDateDay",
			RuntimeVariables.replace("label=27"));
		selenium.select("_15_expirationDateYear",
			RuntimeVariables.replace("label=2011"));
		selenium.select("_15_expirationDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_expirationDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.click("_15_neverReviewCheckbox");
		selenium.select("_15_reviewDateMonth",
			RuntimeVariables.replace("label=September"));
		selenium.select("_15_reviewDateDay",
			RuntimeVariables.replace("label=16"));
		selenium.select("_15_reviewDateYear",
			RuntimeVariables.replace("label=2011"));
		selenium.select("_15_reviewDateHour",
			RuntimeVariables.replace("label=12"));
		selenium.select("_15_reviewDateMinute",
			RuntimeVariables.replace("label=:00"));
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Save and Approve']"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//input[@value='Add Article']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}
}