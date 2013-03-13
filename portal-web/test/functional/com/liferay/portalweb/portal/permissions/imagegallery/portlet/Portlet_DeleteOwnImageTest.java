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

package com.liferay.portalweb.portal.permissions.imagegallery.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Portlet_DeleteOwnImageTest extends BaseTestCase {
	public void testPortlet_DeleteOwnImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Media Gallery Temporary Folder"),
			selenium.getText("//a[@title='Media Gallery Temporary Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Temporary Folder - ']",
			RuntimeVariables.replace("Media Gallery Temporary Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Portlet Permissions Image Test Title"),
			selenium.getText(
				"//a[@title='Portlet Permissions Image Test Title - ']"));
		selenium.clickAt("//a[@title='Portlet Permissions Image Test Title - ']",
			RuntimeVariables.replace("Portlet Permissions Image Test Title"));
		selenium.waitForVisible("//img[@title='Move to the Recycle Bin']");
		selenium.click(RuntimeVariables.replace(
				"//img[@title='Move to the Recycle Bin']"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']"));
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}