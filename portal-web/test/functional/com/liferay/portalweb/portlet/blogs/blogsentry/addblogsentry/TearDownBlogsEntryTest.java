/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBlogsEntryTest extends BaseTestCase {
	public void testTearDownBlogsEntry() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry1Present = selenium.isElementPresent(
						"link=Move to the Recycle Bin");

				if (!blogsEntry1Present) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry2Present = selenium.isElementPresent(
						"link=Move to the Recycle Bin");

				if (!blogsEntry2Present) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry3Present = selenium.isElementPresent(
						"link=Move to the Recycle Bin");

				if (!blogsEntry3Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry4Present = selenium.isElementPresent(
						"link=Move to the Recycle Bin");

				if (!blogsEntry4Present) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry5Present = selenium.isElementPresent(
						"link=Move to the Recycle Bin");

				if (!blogsEntry5Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"link=Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Recycle Bin",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean recycleBinPresent = selenium.isElementPresent(
						"//form[@id='_182_emptyForm']/a");

				if (!recycleBinPresent) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
					selenium.getText("//form[@id='_182_emptyForm']/a"));
				selenium.clickAt("//form[@id='_182_emptyForm']/a",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to empty the Recycle Bin?");

			case 7:
			case 100:
				label = -1;
			}
		}
	}
}