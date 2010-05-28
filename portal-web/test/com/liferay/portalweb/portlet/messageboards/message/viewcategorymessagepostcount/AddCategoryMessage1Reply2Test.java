/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.messageboards.message.viewcategorymessagepostcount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AddCategoryMessage1Reply2Test.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddCategoryMessage1Reply2Test extends BaseTestCase {
	public void testAddCategoryMessage1Reply2() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[5]/table/tbody/tr[1]/td[2]/div[1]/ul/li[2]/span/a/span",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals("RE: T\u00e9st1 M\u00e9ssag\u00e91",
			selenium.getValue("_19_subject"));
		selenium.type("_19_textArea",
			RuntimeVariables.replace("This is a test1 message1 reply2."));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st1 M\u00e9ssag\u00e91"),
			selenium.getText("//form/div[2]"));
		assertEquals(RuntimeVariables.replace(
				"RE: T\u00e9st1 M\u00e9ssag\u00e91"),
			selenium.getText("//a/strong"));
		assertEquals(RuntimeVariables.replace(
				"RE: T\u00e9st1 M\u00e9ssag\u00e91"),
			selenium.getText(
				"//div[6]/table/tbody/tr[1]/td[2]/div[1]/div/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"This is a test1 message1 reply2."),
			selenium.getText("//div[6]/table/tbody/tr[1]/td[2]/div[2]"));
	}
}