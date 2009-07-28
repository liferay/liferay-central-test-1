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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddInvalidRepeatEventTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddInvalidRepeatEventTest extends BaseTestCase {
	public void testAddInvalidRepeatEvent() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("//input[@value='Add Event']"));
		selenium.waitForPageToLoad("30000");
		selenium.click("_8_allDayCheckbox");
		selenium.typeKeys("_8_title",
			RuntimeVariables.replace("Invalid Repeat Test Event"));
		selenium.type("_8_title",
			RuntimeVariables.replace("Invalid Repeat Test Event"));
		selenium.select("_8_startDateMonth",
			RuntimeVariables.replace("label=February"));
		selenium.select("_8_startDateDay", RuntimeVariables.replace("label=25"));
		selenium.select("_8_startDateYear",
			RuntimeVariables.replace("label=2009"));
		selenium.click("//input[@name='_8_recurrenceType' and @value='3']");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_8_dailyInterval")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("_8_dailyInterval", RuntimeVariables.replace("1"));
		selenium.click("//input[@name='_8_endDateType' and @value='2']");
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=February"));
		selenium.select("_8_endDateDay", RuntimeVariables.replace("label=24"));
		selenium.select("_8_endDateYear", RuntimeVariables.replace("label=2009"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"You have entered invalid data. Please try again."));
		assertTrue(selenium.isTextPresent("Please enter a valid end date."));
	}
}