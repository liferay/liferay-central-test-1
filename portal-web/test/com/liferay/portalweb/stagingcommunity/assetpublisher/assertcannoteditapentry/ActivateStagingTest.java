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

package com.liferay.portalweb.stagingcommunity.assetpublisher.assertcannoteditapentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingTest extends BaseTestCase {
	public void testActivateStaging() throws Exception {
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
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Site Name"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.click("//td[7]/span/ul/li/strong/a/span");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a",
					RuntimeVariables.replace("Edit Settings"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_165_stagingType']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select[@id='_165_stagingType']",
					RuntimeVariables.replace("Local Live"));

				boolean blogsChecked = selenium.isChecked(
						"_165_staged-portlet_161Checkbox");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_161Checkbox']",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean bookmarksChecked = selenium.isChecked(
						"_165_staged-portlet_28Checkbox");

				if (bookmarksChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_28Checkbox']",
					RuntimeVariables.replace("Bookmarks"));

			case 3:

				boolean calendarChecked = selenium.isChecked(
						"_165_staged-portlet_8Checkbox");

				if (calendarChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_8Checkbox']",
					RuntimeVariables.replace("Calendar"));

			case 4:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_20Checkbox");

				if (documentLibraryDisplayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_20Checkbox']",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 5:

				boolean messageBoardsChecked = selenium.isChecked(
						"_165_staged-portlet_162Checkbox");

				if (messageBoardsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_162Checkbox']",
					RuntimeVariables.replace("Message Boards"));

			case 6:

				boolean pageCommentsChecked = selenium.isChecked(
						"_165_staged-portlet_107Checkbox");

				if (pageCommentsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_107Checkbox']",
					RuntimeVariables.replace("Page Comments"));

			case 7:

				boolean pageRatingsChecked = selenium.isChecked(
						"_165_staged-portlet_108Checkbox");

				if (pageRatingsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_108Checkbox']",
					RuntimeVariables.replace("Page Ratings"));

			case 8:

				boolean pollsDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_59Checkbox");

				if (pollsDisplayChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_59Checkbox']",
					RuntimeVariables.replace("Polls Display"));

			case 9:

				boolean webContentDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_56Checkbox");

				if (webContentDisplayChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_56Checkbox']",
					RuntimeVariables.replace("Web Content Display"));

			case 10:

				boolean wikiChecked = selenium.isChecked(
						"_165_staged-portlet_36Checkbox");

				if (wikiChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_36Checkbox']",
					RuntimeVariables.replace("Wiki"));

			case 11:

				boolean wikiDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_54Checkbox");

				if (wikiDisplayChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_54Checkbox']",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 12:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to activate local staging for Site Name[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("//td[1]/a"));
				selenium.clickAt("//td[1]/a",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Site Name (Staging)"),
					selenium.getText("//h1[@class='header-title']/span"));

			case 100:
				label = -1;
			}
		}
	}
}