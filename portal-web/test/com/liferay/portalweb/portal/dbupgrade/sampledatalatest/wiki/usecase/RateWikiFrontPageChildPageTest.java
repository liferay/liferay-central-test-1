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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWikiFrontPageChildPageTest extends BaseTestCase {
	public void testRateWikiFrontPageChildPage() throws Exception {
		selenium.open("/web/wiki-use-case-community/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Wiki Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText(
				"xPath=(//div[@class='aui-rating-label-element'])[2]"));
		selenium.clickAt("//a[4]", RuntimeVariables.replace("Rating"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Average (1 Vote)")
										.equals(selenium.getText(
								"xPath=(//div[@class='aui-rating-label-element'])[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText(
				"xPath=(//div[@class='aui-rating-label-element'])[2]"));
		assertEquals(RuntimeVariables.replace("Rate this 1 stars out of 5."),
			selenium.getText(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[1]"));
		assertEquals(RuntimeVariables.replace("Rate this 2 stars out of 5."),
			selenium.getText(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[2]"));
		assertEquals(RuntimeVariables.replace("Rate this 3 stars out of 5."),
			selenium.getText(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[3]"));
		assertEquals(RuntimeVariables.replace("Rate this 4 stars out of 5."),
			selenium.getText(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[4]"));
		assertEquals(RuntimeVariables.replace("Rate this 5 stars out of 5."),
			selenium.getText("//a[@class='aui-rating-element ']"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText(
				"xPath=(//div[@class='aui-rating-label-element'])[2]"));
		selenium.mouseOver(
			"xPath=(//div[@class='aui-rating-label-element'])[2]");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='yui3-widget-bd']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("4 Stars"),
			selenium.getText("//div[@class='yui3-widget-bd']"));
	}
}