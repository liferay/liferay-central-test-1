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

package com.liferay.portalweb.portal.controlpanel.sites.sitepage.viewsitepublicpagedropdown;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSitesPublicPageDropDownTest extends BaseTestCase {
	public void testViewSitesPublicPageDropDown() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForText("//li[6]/a/span", "Community Name");
		assertEquals(RuntimeVariables.replace("Community Name"),
			selenium.getText("//li[6]/a/span"));
		selenium.clickAt("//li[6]/a/span",
			RuntimeVariables.replace("Community Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText("//nav/ul/li/a/span"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText("//nav/ul/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"http://localhost:8080/web/community-name/public-page"),
			selenium.getLocation());
	}
}