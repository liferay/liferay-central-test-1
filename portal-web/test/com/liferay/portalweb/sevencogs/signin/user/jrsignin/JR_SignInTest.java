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

package com.liferay.portalweb.sevencogs.signin.user.jrsignin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class JR_SignInTest extends BaseTestCase {
	public void testJR_SignIn() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("xPath=(//a[@class='express_login'])[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Login as john"),
			selenium.getText("xPath=(//a[@class='express_login'])[4]"));
		selenium.clickAt("xPath=(//a[@class='express_login'])[4]",
			RuntimeVariables.replace("Login as john"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("John Regular"),
			selenium.getText("//a[@class='user-fullname']"));
		assertEquals(RuntimeVariables.replace(
				"You are signed in as John Regular."),
			selenium.getText("//section[@id='portlet_58']/div/div/div"));
	}
}