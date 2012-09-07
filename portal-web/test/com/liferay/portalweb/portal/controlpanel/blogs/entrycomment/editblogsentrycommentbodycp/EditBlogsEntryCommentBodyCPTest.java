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

package com.liferay.portalweb.portal.controlpanel.blogs.entrycomment.editblogsentrycommentbodycp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditBlogsEntryCommentBodyCPTest extends BaseTestCase {
	public void testEditBlogsEntryCommentBodyCP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Comment Body"),
			selenium.getText("//form/div/div/div/div[3]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//li[3]/span/a/span"));
		selenium.clickAt("//li[3]/span/a/span", RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//textarea[@name='_161_editReplyBody1']");
		selenium.type("//textarea[@name='_161_editReplyBody1']",
			RuntimeVariables.replace("Blogs Entry Comment Body Edited"));
		selenium.keyPress("//textarea[@name='_161_editReplyBody1']",
			RuntimeVariables.replace("\\48"));
		selenium.keyPress("//textarea[@name='_161_editReplyBody1']",
			RuntimeVariables.replace("\\8"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@class='comments']");
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("//span[@class='comments']"));
		selenium.waitForText("//form/div/div/div/div[3]/div/div[1]",
			"Blogs Entry Comment Body Edited");
		assertEquals(RuntimeVariables.replace("Blogs Entry Comment Body Edited"),
			selenium.getText("//form/div/div/div/div[3]/div/div[1]"));
	}
}