/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.advancedsearchwebcontentworkflowtaskname;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchWebContentWorkflowTaskNameTest extends BaseTestCase {
	public void testAdvancedSearchWebContentWorkflowTaskName()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=My Workflow Tasks",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Pending", RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace(""));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("_153_andOperator")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("_153_andOperator",
					RuntimeVariables.replace("label=Any"));
				selenium.type("_153_name", RuntimeVariables.replace("Review"));
				selenium.saveScreenShotAndSource();
				selenium.select("_153_type",
					RuntimeVariables.replace("label=Blogs Entry"));
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"There are no pending tasks assigned to you with the specified search criteria."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("Review"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Web Content"),
					selenium.getText("//td[3]/a"));
				assertTrue(selenium.isElementPresent("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText("//td[5]/a"));
				selenium.type("_153_name", RuntimeVariables.replace("Review1"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertFalse(selenium.isTextPresent("Web Content Name"));
				assertEquals(RuntimeVariables.replace(
						"There are no pending tasks assigned to you with the specified search criteria."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace(
						"There are no pending tasks assigned to your roles with the specified search criteria."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-info'])[2]"));
				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 100:
				label = -1;
			}
		}
	}
}