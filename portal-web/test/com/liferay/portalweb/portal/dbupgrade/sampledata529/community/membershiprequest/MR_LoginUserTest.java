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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.community.membershiprequest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MR_LoginUserTest extends BaseTestCase {
	public void testMR_LoginUser() throws Exception {
		selenium.setTimeout("180000");
		selenium.open("/web/guest/home");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Sign In")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Sign In"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("_58_login",
			RuntimeVariables.replace("requestmemberea@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.click("_58_rememberMeCheckbox");
		selenium.click(RuntimeVariables.replace("//input[@value='Sign In']"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.click(RuntimeVariables.replace("//input[@value='I Agree']"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.click(RuntimeVariables.replace(
				"document.getElementById('my-community-private-pages')"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
	}
}