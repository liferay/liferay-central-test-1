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

package com.liferay.portalweb.portal.dbupgrade.sampledata600.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentImageAssociationTest extends BaseTestCase {
	public void testAddWebContentImageAssociation() throws Exception {
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_134_name",
			RuntimeVariables.replace("Web Content Image Association Community"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Web Content']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("_15_title",
			RuntimeVariables.replace("Image Web Content Test"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace(""));
		assertTrue(selenium.getConfirmation()
						   .matches("^Selecting a template will change the structure, available input fields, and available templates[\\s\\S] Do you want to proceed[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.waitForPopUp("template", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=template");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=TEST_IMAGE")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click("link=TEST_IMAGE");
		selenium.selectWindow("null");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Image Structure Test (Use Default)")
										.equals(selenium.getText(
								"_15_structureNameLabel"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Image Structure Test (Use Default)"),
			selenium.getText("_15_structureNameLabel"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_15_text")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("_15_text", RuntimeVariables.replace("Text Test"));
		selenium.saveScreenShotAndSource();
		selenium.type("_15_image",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata600\\webcontent\\imageassociation\\dependencies\\Image.jpg"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace(""));
		selenium.waitForPopUp("ImageGallery", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=ImageGallery");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Folder Test")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Folder Test", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save and Approve']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertEquals(RuntimeVariables.replace("Image Web Content Test"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
	}
}