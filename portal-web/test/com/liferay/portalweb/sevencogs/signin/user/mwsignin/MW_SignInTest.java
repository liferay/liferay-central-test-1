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

package com.liferay.portalweb.sevencogs.signin.user.mwsignin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MW_SignInTest extends BaseTestCase {
	public void testMW_SignIn() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("xPath=(//a[@class='express_login'])[3]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Login as Michelle."),
			selenium.getText("xPath=(//a[@class='express_login'])[3]"));
		selenium.clickAt("xPath=(//a[@class='express_login'])[3]",
			RuntimeVariables.replace("Login as Michelle."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Michelle Writer"),
			selenium.getText("//a[contains(@class,'user-fullname')]"));
		assertEquals(RuntimeVariables.replace(
				"You are signed in as Michelle Writer."),
			selenium.getText("//div[@id='p_p_id_58_']/div/div/div"));
	}
}