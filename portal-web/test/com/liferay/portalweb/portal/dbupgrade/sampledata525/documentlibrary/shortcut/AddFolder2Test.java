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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.documentlibrary.shortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolder2Test extends BaseTestCase {
	public void testAddFolder2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/home/");
		selenium.waitForElementPresent("link=Communities I Own");
		selenium.clickAt("link=Communities I Own", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_29_name",
			RuntimeVariables.replace("Document Library Shortcut Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Open", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Document Library Page");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Folder']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_20_name", RuntimeVariables.replace("Test2 Folder2"));
		selenium.type("_20_description",
			RuntimeVariables.replace("This is test2 folder2."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request processed successfully."));
		assertTrue(selenium.isElementPresent("//tr[4]/td[1]/a[2]/b"));
	}
}