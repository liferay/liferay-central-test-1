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

package com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletabstractsdldocumentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewDLFolderDocumentAPActionsTest extends BaseTestCase {
	public void testAddNewDLFolderDocumentAPActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add New']/ul/li/strong/a",
			RuntimeVariables.replace("Add New"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
			RuntimeVariables.replace("Basic Document"));
		Thread.sleep(5000);
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		assertEquals("Select",
			selenium.getValue("//input[@id='_20_openFolderSelectorButton']"));
		selenium.click("//input[@id='_20_openFolderSelectorButton']");
		selenium.waitForPopUp("folder", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=folder");
		selenium.waitForVisible("//input[@value='Choose']");
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		Thread.sleep(5000);
		selenium.waitForText("//a[@id='_20_folderName']", "DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\assetpublisher\\dependencies\\DLDocument.txt"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h3[@class='asset-title']/a",
			"DM Folder Document Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//div[@class='asset-resource-info']/span/a/span"));
	}
}