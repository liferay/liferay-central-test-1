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

package com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CompareVersionEditFrontPageMinorChangeTest extends BaseTestCase {
	public void testCompareVersionEditFrontPageMinorChange()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Wiki Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Details", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=1.2 (Minor Edit)"));
		assertTrue(selenium.isElementPresent("link=1.1"));
		assertTrue(selenium.isElementPresent("link=1.0 (Minor Edit)"));
		selenium.check("//input[@name='_36_rowIds' and @value='1.2']");
		selenium.check("//input[@name='_36_rowIds' and @value='1.1']");
		selenium.uncheck("//input[@name='_36_rowIds' and @value='1.0']");
		selenium.clickAt("//input[@value='Compare Versions']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Wiki Article"),
			selenium.getText("//div[@class='taglib-diff-html']/h2"));
		assertEquals(RuntimeVariables.replace("this is italics"),
			selenium.getText("//i"));
		assertEquals(RuntimeVariables.replace("bold"), selenium.getText("//b"));
		assertTrue(selenium.isElementPresent("link=Link to website"));
		assertEquals(RuntimeVariables.replace(
				"this is a list item this is a sub list item"),
			selenium.getText("//div[@class='taglib-diff-html']/ul/li"));
		assertEquals(RuntimeVariables.replace("Minor Edit."),
			selenium.getText("//span[@id='added-diff-0']"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Text Mode")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Text Mode", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage 1.1"),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("FrontPage 1.2"),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[1]/table/tbody/tr[4]/td"));
		assertEquals(RuntimeVariables.replace("Minor Edit."),
			selenium.getText(
				"//table[@id='taglib-diff-results']/tbody/tr[3]/td[2]/table/tbody/tr[4]/td/ins"));
	}
}