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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.editwcwebcontentwcddetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWCWebContentWCDDetailsTest extends BaseTestCase {
	public void testEditWCWebContentWCDDetails() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='icon-action icon-action-edit']/a/span"));
		selenium.clickAt("//span[@class='icon-action icon-action-edit']/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC WebContent Title Edit"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("WC WebContent Content Edit"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content Edit"),
			selenium.getText("//div[@class='journal-content-article']/p"));
	}
}