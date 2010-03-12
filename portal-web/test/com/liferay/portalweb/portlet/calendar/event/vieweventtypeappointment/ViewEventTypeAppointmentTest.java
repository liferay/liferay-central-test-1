/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.calendar.event.vieweventtypeappointment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="ViewEventTypeAppointmentTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewEventTypeAppointmentTest extends BaseTestCase {
	public void testViewEventTypeAppointment() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Day", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select",
			RuntimeVariables.replace("label=Appointment"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText("//div[@class='event-title']"));
		selenium.clickAt("link=Week", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select",
			RuntimeVariables.replace("label=Appointment"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]/a"));
		selenium.clickAt("link=Month", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select",
			RuntimeVariables.replace("label=Appointment"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]/a"));
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]/a"));
	}
}