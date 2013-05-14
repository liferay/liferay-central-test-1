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

package com.liferay.portalweb.asset.documentsandmedia.dmdocument.selectexistingdmfolderdocumentapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectExistingDMFolderDocumentAPActionsTest extends BaseTestCase {
	public void testSelectExistingDMFolderDocumentAPActions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"xPath=(//span[@title='Select']/ul/li/strong/a/span)[2]");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"xPath=(//span[@title='Select']/ul/li/strong/a/span)[2]"));
		selenium.clickAt("xPath=(//span[@title='Select']/ul/li/strong/a/span)[2]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[8]/a[contains(.,'Document')]");
		assertEquals(RuntimeVariables.replace("Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[8]/a[contains(.,'Document')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[8]/a[contains(.,'Document')]"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent("//iframe[contains(@id,'selectAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'selectAsset')]");
		selenium.waitForVisible("//td[1]/a");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible(
			"//fieldset[3]/div/div/div/div/table/tbody/tr[3]/td[2]");
		assertEquals(RuntimeVariables.replace("Document"),
			selenium.getText(
				"//fieldset[3]/div/div/div/div/table/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//fieldset[3]/div/div/div/div/table/tbody/tr[3]/td"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("xPath=(//h3[@class='asset-title']/a)",
			"DM Folder Document Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("xPath=(//h3[@class='asset-title']/a)"));
	}
}