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

package com.liferay.portalweb.portlet.bookmarks.folder.deletesubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSubfolderTest extends BaseTestCase {
	public void testAddSubfolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Bookmarks Test Page");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText("//div[2]/ul/li[4]/a"));
		selenium.clickAt("//div[2]/ul/li[4]/a",
			RuntimeVariables.replace("Add Subfolder"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_28_name", RuntimeVariables.replace("Test Subfolder"));
		selenium.type("_28_description",
			RuntimeVariables.replace("This is a test subfolder."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Test Subfolder"),
			selenium.getText("//td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"Test Subfolder\nThis is a test subfolder."),
			selenium.getText("//td[1]/a"));
	}
}