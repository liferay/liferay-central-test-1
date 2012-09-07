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

package com.liferay.portalweb.portal.dbfailover;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMessage3Test extends BaseTestCase {
	public void testAddMessage3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/home/");
		selenium.waitForVisible(
			"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9");
		selenium.click(RuntimeVariables.replace(
				"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Category"),
			selenium.getText("//tr[3]/td/a"));
		selenium.click(RuntimeVariables.replace("//tr[3]/td/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Post New Thread']"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("Test Message 3 Subject"));
		Thread.sleep(5000);
		selenium.clickAt("link=Source", RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__19_editor']/textarea");
		selenium.type("//td[@id='cke_contents__19_editor']/textarea",
			RuntimeVariables.replace("Test Message 3 Content"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Message 3 Content"),
			selenium.getText("//div[@class='thread-body']"));
		assertEquals(RuntimeVariables.replace("Test Message 3 Subject"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[6]/span/a"));
		assertEquals(RuntimeVariables.replace("Test Category"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[5]/span/a"));
		selenium.clickAt("//nav[@id='breadcrumbs']/ul/li[5]/span/a",
			RuntimeVariables.replace("Test Category"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Message 3 Subject"),
			selenium.getText("//tr[3]/td/a"));
		System.out.println("Sample data 3 added successfully.\\");
	}
}