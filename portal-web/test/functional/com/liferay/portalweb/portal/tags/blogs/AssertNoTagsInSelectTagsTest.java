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

package com.liferay.portalweb.portal.tags.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertNoTagsInSelectTagsTest extends BaseTestCase {
	public void testAssertNoTagsInSelectTags() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Tags Test Page",
					RuntimeVariables.replace("Blogs Tags Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Tags Blog Entry1 Title"),
					selenium.getText(
						"xPath=(//div[@class='entry-title']/h2/a)[3]"));
				selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[3]",
					RuntimeVariables.replace("Tags Blog Entry1 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//a[@class=' taglib-icon']/span[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//a[@class=' taglib-icon']/span[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button_disabled')]");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");

				boolean tagsVisible = selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Categorization"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[2]/span"));
				selenium.clickAt("xPath=(//div[@class='lfr-panel-title'])[2]/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForVisible(
					"//input[@class='lfr-tag-selector-input aui-field-input-text']");
				assertTrue(selenium.isVisible(
						"//input[@class='lfr-tag-selector-input aui-field-input-text']"));

			case 2:
				selenium.clickAt("//button[@id='select']",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				assertTrue(selenium.isElementNotPresent(
						"xpath=(//div[@class='lfr-tags-selector-list']/fieldset//label)[1]"));
				assertTrue(selenium.isElementNotPresent(
						"xpath=(//div[@class='lfr-tags-selector-list']/fieldset//label)[2]"));
				assertTrue(selenium.isElementNotPresent(
						"xpath=(//div[@class='lfr-tags-selector-list']/fieldset//label)[3]"));
				assertTrue(selenium.isElementNotPresent(
						"xpath=(//div[@class='lfr-tags-selector-list']/fieldset//label)[4]"));
				selenium.clickAt("//button[@title='Close dialog']",
					RuntimeVariables.replace("Close"));
				selenium.clickAt("//input[@value='Cancel']",
					RuntimeVariables.replace("Cancel"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}