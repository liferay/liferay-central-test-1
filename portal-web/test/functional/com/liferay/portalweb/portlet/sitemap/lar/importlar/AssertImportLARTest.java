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

package com.liferay.portalweb.portlet.sitemap.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[1]"));
		assertEquals(RuntimeVariables.replace("Site Map Test Page"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[2]"));
		assertEquals(RuntimeVariables.replace("Site Map Test Child Page"),
			selenium.getText("//div[@class='portlet-body']/ul/li/ul/li/a"));
	}
}