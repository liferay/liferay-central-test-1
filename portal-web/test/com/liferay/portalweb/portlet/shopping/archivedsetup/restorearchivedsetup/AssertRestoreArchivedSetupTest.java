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

package com.liferay.portalweb.portlet.shopping.archivedsetup.restorearchivedsetup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertRestoreArchivedSetupTest extends BaseTestCase {
	public void testAssertRestoreArchivedSetup() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Shopping Test Page");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Cart", RuntimeVariables.replace("Cart"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//img[@alt='visa']"));
		assertTrue(selenium.isElementPresent("//img[@alt='discover']"));
		assertTrue(selenium.isElementPresent("//img[@alt='mastercard']"));
		assertTrue(selenium.isElementPresent("//img[@alt='amex']"));
	}
}