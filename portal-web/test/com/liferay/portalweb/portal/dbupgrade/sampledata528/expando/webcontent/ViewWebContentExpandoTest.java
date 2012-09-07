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

package com.liferay.portalweb.portal.dbupgrade.sampledata528.expando.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentExpandoTest extends BaseTestCase {
	public void testViewWebContentExpando() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Expando Web Content Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content Display Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("First Expando Bank"),
			selenium.getText("//div[@class='journal-content-article']/h1"));
		assertTrue(selenium.isElementPresent("//input[@value='Create Account']"));
		assertEquals(RuntimeVariables.replace("Michael"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Hashimoto"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[2]/td[3]"));
		assertEquals(RuntimeVariables.replace("$100.00"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[2]/td[4]"));
		assertEquals(RuntimeVariables.replace("JR"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Houn"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("$1,000,000.00"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Michael"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Saechang"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("$1,000,000.00"),
			selenium.getText("//table[@class='lfr-table']/tbody/tr[4]/td[4]"));
		assertTrue(selenium.isTextPresent("# of Accounts: 3"));
	}
}