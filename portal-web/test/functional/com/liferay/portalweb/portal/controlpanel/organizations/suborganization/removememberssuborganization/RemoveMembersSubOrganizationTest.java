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

package com.liferay.portalweb.portal.controlpanel.organizations.suborganization.removememberssuborganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveMembersSubOrganizationTest extends BaseTestCase {
	public void testRemoveMembersSubOrganization() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Suborganization Name"),
					selenium.getText(
						"//tr[contains(.,'Suborganization Name')]/td[2]/a/strong"));
				assertEquals(RuntimeVariables.replace("1 User"),
					selenium.getText(
						"//tr[contains(.,'Suborganization Name')]/td[2]/em"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Available"),
					selenium.getText(
						"//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Available')]"));
				selenium.clickAt("//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Available')]",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent("//a[.='\u00ab Basic']");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean selectUserCheckbox = selenium.isChecked(
						"//input[@name='_125_rowIds']");

				if (!selectUserCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Select User"));

			case 3:
				assertFalse(selenium.isChecked("//input[@name='_125_rowIds']"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked("//input[@name='_125_rowIds']"));
				selenium.clickAt("//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Current')]",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isTextPresent("userfn"));

			case 100:
				label = -1;
			}
		}
	}
}