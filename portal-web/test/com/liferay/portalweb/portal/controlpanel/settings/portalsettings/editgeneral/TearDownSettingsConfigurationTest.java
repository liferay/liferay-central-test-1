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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.editgeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsConfigurationTest extends BaseTestCase {
	public void testTearDownSettingsConfiguration() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_130_name", RuntimeVariables.replace("Liferay"));
		selenium.type("_130_virtualHost", RuntimeVariables.replace("localhost"));
		selenium.type("_130_mx", RuntimeVariables.replace("liferay.com"));
		selenium.type("_130_homeURL", RuntimeVariables.replace(""));
		selenium.type("_130_settings--default.landing.page.path--",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--default.logout.page.path--",
			RuntimeVariables.replace(""));
		selenium.type("_130_legalName",
			RuntimeVariables.replace("Liferay, Inc."));
		selenium.type("_130_sicCode", RuntimeVariables.replace(""));
		selenium.type("_130_legalId", RuntimeVariables.replace(""));
		selenium.type("_130_tickerSymbol", RuntimeVariables.replace(""));
		selenium.type("_130_legalType", RuntimeVariables.replace(""));
		selenium.type("_130_industry", RuntimeVariables.replace(""));
		selenium.type("_130_type", RuntimeVariables.replace(""));
		selenium.clickAt("usersLink", RuntimeVariables.replace(""));
		selenium.clickAt("link=Reserved Credentials",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.reserved.screen.names--",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.reserved.email.addresses--",
			RuntimeVariables.replace(""));
		selenium.clickAt("link=Default User Associations",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.default.group.names--",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.default.role.names--",
			RuntimeVariables.replace("Power User\nUser"));
		selenium.type("_130_settings--admin.default.user.group.names--",
			RuntimeVariables.replace(""));
		selenium.clickAt("mailHostNamesLink", RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.mail.host.names--",
			RuntimeVariables.replace(""));
		selenium.clickAt("emailNotificationsLink", RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.email.from.name--",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.type("_130_settings--admin.email.from.address--",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}