/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.webcontentdisplay.portlet.configureportletenableratings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletSetupTest extends BaseTestCase {
	public void testTearDownPortletSetup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Web Content Display Test Page");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//img[@alt='Select Web Content']"));
				Thread.sleep(5000);
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				selenium.waitForVisible("link=Setup");
				selenium.clickAt("link=Setup", RuntimeVariables.replace("Setup"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_showAvailableLocalesCheckbox']"));

				boolean showAvailableLocalesChecked = selenium.isChecked(
						"_86_showAvailableLocalesCheckbox");

				if (!showAvailableLocalesChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_showAvailableLocalesCheckbox']",
					RuntimeVariables.replace(""));

			case 2:
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_enablePrintCheckbox']"));

				boolean enablePrintChecked = selenium.isChecked(
						"_86_enablePrintCheckbox");

				if (!enablePrintChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enablePrintCheckbox']",
					RuntimeVariables.replace(""));

			case 3:
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_enableRatingsCheckbox']"));

				boolean enableRatingsChecked = selenium.isChecked(
						"_86_enableRatingsCheckbox");

				if (!enableRatingsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableRatingsCheckbox']",
					RuntimeVariables.replace(""));

			case 4:
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_enableCommentsCheckbox']"));

				boolean enableCommentsChecked = selenium.isChecked(
						"_86_enableCommentsCheckbox");

				if (!enableCommentsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentsCheckbox']",
					RuntimeVariables.replace(""));

			case 5:
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_enableCommentRatingsCheckbox']"));

				boolean enableCommentRatingsChecked = selenium.isChecked(
						"_86_enableCommentRatingsCheckbox");

				if (!enableCommentRatingsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_86_enableCommentRatingsCheckbox']",
					RuntimeVariables.replace(""));

			case 6:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}