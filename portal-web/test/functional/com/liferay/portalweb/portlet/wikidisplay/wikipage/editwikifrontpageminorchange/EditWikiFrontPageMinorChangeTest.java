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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWikiFrontPageMinorChangeTest extends BaseTestCase {
	public void testEditWikiFrontPageMinorChange() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='wiki-body']/p",
			"Wiki FrontPage Content");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Wiki FrontPage Content Edit"));
		assertFalse(selenium.isChecked(
				"//input[contains(@id,'_minorEditCheckbox')]"));
		selenium.clickAt("//input[contains(@id,'_minorEditCheckbox')]",
			RuntimeVariables.replace("This is a minor edit."));
		assertTrue(selenium.isChecked(
				"//input[contains(@id,'_minorEditCheckbox')]"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content Edit"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertNotEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]",
			RuntimeVariables.replace("Details"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=History", RuntimeVariables.replace("History"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.2 (Minor Edit)')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[contains(.,'1.1')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
			selenium.getText("//tr[contains(.,'1.0 (Minor Edit)')]/td[4]/a"));
	}
}