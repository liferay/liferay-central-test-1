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

package com.liferay.portalweb.demo.sitemanagement.staginglocalliveworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class WCC_AddPortletWCDSiteStagingTest extends BaseTestCase {
	public void testWCC_AddPortletWCDSiteStaging() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/community-site-test/home/");
		selenium.waitForVisible("link=Staging");
		selenium.clickAt("link=Staging", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent(
			"//div[@title='Web Content Display']/p/a");
		selenium.clickAt("//div[@title='Web Content Display']/p/a",
			RuntimeVariables.replace("Web Content Display"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
	}
}