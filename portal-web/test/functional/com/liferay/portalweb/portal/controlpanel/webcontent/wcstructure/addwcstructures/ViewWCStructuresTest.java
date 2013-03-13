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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructures;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCStructuresTest extends BaseTestCase {
	public void testViewWCStructures() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
		selenium.selectFrame("//iframe[contains(@src,'Structures')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("WC Structure1 "));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure1 Name')]/td[2]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure1 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//tr[contains(.,'WC Structure1 Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure1 Name')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Structure1 Name')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'WC Structure1 Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure1 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//h1[@class='header-title']");
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals("WC Structure1 Name",
			selenium.getValue("//input[@id='_166_name_en_US']"));
		assertEquals("WC Structure1 Description",
			selenium.getValue("//textarea[@id='_166_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Parent Structure"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[2]/div/label"));
		assertEquals("Select", selenium.getValue("//input[@value='Select']"));
		assertEquals("Remove", selenium.getValue("//input[@value='Remove']"));
		assertEquals(RuntimeVariables.replace("URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/input"));
		assertEquals(RuntimeVariables.replace("WebDAV URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/input"));
		selenium.waitForVisible(
			"//div[@class='aui-diagram-builder-drop-container']/div[1]");
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]/div/label"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		selenium.clickAt("//span[@class='header-back-to']/a",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure2 Name')]/td[2]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Structure2 Name')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'WC Structure2 Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//h1[@class='header-title']");
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals("WC Structure2 Name",
			selenium.getValue("//input[@id='_166_name_en_US']"));
		assertEquals("WC Structure2 Description",
			selenium.getValue("//textarea[@id='_166_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Parent Structure"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[2]/div/label"));
		assertEquals("Select", selenium.getValue("//input[@value='Select']"));
		assertEquals("Remove", selenium.getValue("//input[@value='Remove']"));
		assertEquals(RuntimeVariables.replace("URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/input"));
		assertEquals(RuntimeVariables.replace("WebDAV URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/input"));
		selenium.waitForVisible(
			"//div[@class='aui-diagram-builder-drop-container']/div[1]");
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]/div/label"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		selenium.clickAt("//span[@class='header-back-to']/a",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("WC Structure3 Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure3 Name')]/td[2]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure3 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure3 Name"),
			selenium.getText("//tr[contains(.,'WC Structure3 Name')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure3 Name')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Structure3 Name')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'WC Structure3 Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure3 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//h1[@class='header-title']");
		assertEquals(RuntimeVariables.replace("WC Structure3 Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals("WC Structure3 Name",
			selenium.getValue("//input[@id='_166_name_en_US']"));
		assertEquals("WC Structure3 Description",
			selenium.getValue("//textarea[@id='_166_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Parent Structure"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[2]/div/label"));
		assertEquals("Select", selenium.getValue("//input[@value='Select']"));
		assertEquals("Remove", selenium.getValue("//input[@value='Remove']"));
		assertEquals(RuntimeVariables.replace("URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/input"));
		assertEquals(RuntimeVariables.replace("WebDAV URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/input"));
		selenium.waitForVisible(
			"//div[@class='aui-diagram-builder-drop-container']/div[1]");
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[12]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[13]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[14]/div/label"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		selenium.selectFrame("relative=top");
	}
}