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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCWebContentWCDTest extends BaseTestCase {
	public void testLocalizeWCWebContentWCD() throws Exception {
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
						if (selenium.isElementPresent(
									"link=Web Content Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//img[@alt='Edit Web Content']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//img[@alt='Edit Web Content']",
					RuntimeVariables.replace("Edit Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//h1[@class='header-title']"));
				Thread.sleep(5000);
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.selectFrame(
					"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
				assertEquals(RuntimeVariables.replace("Web Content Content"),
					selenium.getText("//body"));
				selenium.selectFrame("relative=top");
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//select[@id='_15_languageIdSelect']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select[@id='_15_languageIdSelect']",
					RuntimeVariables.replace("label=Chinese (China)"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.selectFrame(
					"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
				selenium.type("//body",
					RuntimeVariables.replace(
						"\u4e16\u754c\u60a8\u597d Page Description"));
				selenium.selectFrame("relative=top");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isElementPresent(
						"//span[2]/div/span/span/span/input[2]"));

				boolean localizedPageDescriptionChecked = selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]");

				if (localizedPageDescriptionChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[2]/div/span/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 2:
				assertTrue(selenium.isChecked(
						"//span[2]/div/span/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}