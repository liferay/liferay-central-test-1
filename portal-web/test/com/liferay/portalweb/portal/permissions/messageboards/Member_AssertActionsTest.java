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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertActionsTest extends BaseTestCase {
	public void testMember_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.waitForVisible("link=Message Boards Permissions Page");
		selenium.clickAt("link=Message Boards Permissions Page",
			RuntimeVariables.replace("Message Boards Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("RSS"),
			selenium.getText("//div[2]/div/span/a/span[1]"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
		assertTrue(selenium.isElementNotPresent("link=Move Thread"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementNotPresent("link=Add Category"));
		assertTrue(selenium.isElementNotPresent("link=Banned Users"));
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace("Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Thread Subject"),
			selenium.getText("//tr[3]/td/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Move"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		selenium.clickAt("//tr[3]/td/a",
			RuntimeVariables.replace("Thread Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Reply with Quote"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[3]/span/a"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
	}
}