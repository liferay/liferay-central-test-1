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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPage1DLFolder1Document1Comment1Test extends BaseTestCase {
	public void testAddPage1DLFolder1Document1Comment1()
		throws Exception {
		selenium.open("/web/document-library-page-scope-community/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=DL Page1 Name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=DL Page1 Name",
			RuntimeVariables.replace("DL Page1 Name"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Documents and Media")
										.equals(selenium.getText(
								"//span[@class='portlet-title-text']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("DL Folder1 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder1 Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@id='_20_breadcrumbContainer']/ul/li[2]/span/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder1 Name"),
			selenium.getText(
				"//div[@id='_20_breadcrumbContainer']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("DL Folder1 Document1 Title.doc"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder1 Document1 Title.doc"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//fieldset[contains(@class,'add-comment')]/div/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset[contains(@class,'add-comment')]/div/a"));
		selenium.clickAt("//fieldset[contains(@class,'add-comment')]/div/a",
			RuntimeVariables.replace("Be the first."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//textarea[@name='_20_postReplyBody0']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//textarea[@name='_20_postReplyBody0']",
			RuntimeVariables.replace("DL Folder1 Document1 Comment1 Body"));
		selenium.clickAt("//input[@value='Reply']",
			RuntimeVariables.replace("Reply"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@id='_20_discussion-status-messages']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@id='_20_discussion-status-messages']"));
		assertEquals(RuntimeVariables.replace(
				"DL Folder1 Document1 Comment1 Body"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
	}
}