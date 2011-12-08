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

package com.liferay.portalweb.portlet.documentlibrary.image.searchdlfolderimagefolderdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchDLFolderImageFolderDetailsTest extends BaseTestCase {
	public void testSearchDLFolderImageFolderDetails()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents and Media Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DL Folder Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder Image Title")
										.equals(selenium.getText(
								"//a[contains(@class,'document-link')]/span[@class='entry-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("DL Folder Image Title"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Searched for DL Folder Image Title in DL Folder Name")
										.equals(selenium.getText(
								"//span[@class='keywords']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Searched for DL Folder Image Title in DL Folder Name"),
			selenium.getText("//span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DL Folder Image Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Search in every folder.']",
			RuntimeVariables.replace("Search in every folder."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Searched for DL Folder Image Title in every folder.")
										.equals(selenium.getText(
								"//span[@class='keywords']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Searched for DL Folder Image Title in every folder."),
			selenium.getText("//span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DL Folder Image Title"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("DL1 Folder1 Image1 Title1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Searched for DL1 Folder1 Image1 Title1 in DL Folder Name")
										.equals(selenium.getText(
								"//span[@class='keywords']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Searched for DL1 Folder1 Image1 Title1 in DL Folder Name"),
			selenium.getText("//span[@class='keywords']"));
		assertFalse(selenium.isElementPresent(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Search in every folder.']",
			RuntimeVariables.replace("Search in every folder."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Searched for DL1 Folder1 Image1 Title1 in every folder.")
										.equals(selenium.getText(
								"//span[@class='keywords']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Searched for DL1 Folder1 Image1 Title1 in every folder."),
			selenium.getText("//span[@class='keywords']"));
		assertFalse(selenium.isElementPresent(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
	}
}