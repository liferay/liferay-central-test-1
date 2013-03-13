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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgadminroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersOrgAdminRoleUserTest extends BaseTestCase {
	public void testAssignMembersOrgAdminRoleUser() throws Exception {
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
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Organization Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Organization1 Name"),
					selenium.getText(
						"//tr[contains(.,'Organization1 Name')]/td[2]/a/strong"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]");
				assertEquals(RuntimeVariables.replace(
						"Assign Organization Roles"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Organization Role')]",
					RuntimeVariables.replace("Assign Organization Roles"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Organization1 Name"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace(
						"Organization Administrator"),
					selenium.getText(
						"//tr[contains(.,'Organization Administrator')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Organization"),
					selenium.getText(
						"//tr[contains(.,'Organization Administrator')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace(
						"Organization Administrators are super users of their organization but cannot make other users into Organization Administrators."),
					selenium.getText(
						"//tr[contains(.,'Organization Administrator')]/td[3]/a"));
				selenium.clickAt("//tr[contains(.,'Organization Administrator')]/td[1]/a",
					RuntimeVariables.replace("Organization Administrator"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Available"),
					selenium.getText(
						"//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Available')]"));
				selenium.clickAt("//ul[@class='aui-tabview-list']/li/span/a[contains(.,'Available')]",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[contains(.,'userfn userln')]/td[2]"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//tr[contains(.,'userfn userln')]/td[3]"));

				boolean organizationAvailableUser = selenium.isChecked(
						"//input[@name='_125_rowIds']");

				if (organizationAvailableUser) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace(
						"Organization Available User Checkbox"));

			case 2:
				assertTrue(selenium.isChecked("//input[@name='_125_rowIds']"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked("//input[@name='_125_rowIds']"));

			case 100:
				label = -1;
			}
		}
	}
}