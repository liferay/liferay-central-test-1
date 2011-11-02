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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineMemberRoleTest extends BaseTestCase {
	public void testDefineMemberRole() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Member"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Member"),
			selenium.getText("//tr[3]/td/a"));
		selenium.clickAt("//tr[3]/td/a", RuntimeVariables.replace("Member"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Media Gallery"));
		selenium.waitForPageToLoad("30000");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_DOCUMENT']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_FOLDER']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_SHORTCUT']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryVIEW']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Add Document"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[4]/td[3]"));
		selenium.typeKeys("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("mmmm"));
		selenium.keyPress("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("\\13"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Media Gallery"),
			selenium.getText("//h3"));
		selenium.uncheck("//input[@value='31ADD_TO_PAGE']");
		selenium.uncheck("//input[@value='31CONFIGURATION']");
		selenium.uncheck("//input[@value='31PERMISSIONS']");
		selenium.check("//input[@value='31VIEW']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[5]/td[3]"));
	}
}