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

package com.liferay.portalweb.portlet.messageboards.category.addsubcategorymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSubcategory3Test extends BaseTestCase {
	public void testAddSubcategory3() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a/strong"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Subcategory']",
			RuntimeVariables.replace("Add Subcategory"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_19_name']",
			RuntimeVariables.replace("MB Subcategory3 Name"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//div[4]/h1/span"));
		assertEquals(RuntimeVariables.replace("MB Subcategory1 Name"),
			selenium.getText("//tr[3]/td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("MB Subcategory2 Name"),
			selenium.getText("//tr[4]/td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("MB Subcategory3 Name"),
			selenium.getText("//tr[5]/td[1]/a/strong"));
	}
}