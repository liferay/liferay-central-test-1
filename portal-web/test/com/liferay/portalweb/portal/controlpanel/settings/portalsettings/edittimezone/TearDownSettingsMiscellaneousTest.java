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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.edittimezone;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsMiscellaneousTest extends BaseTestCase {
	public void testTearDownSettingsMiscellaneous() throws Exception {
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
		selenium.clickAt("displaySettingsLink", RuntimeVariables.replace(""));
		selenium.select("_130_languageId",
			RuntimeVariables.replace("label=English (United States)"));
		selenium.type("_130_settings--locales--",
			RuntimeVariables.replace(
				"ar_SA,eu_ES,bg_BG,ca_AD,ca_ES,zh_CN,zh_TW,cs_CZ,nl_NL,en_US,et_EE,fi_FI,fr_FR,gl_ES,de_DE,el_GR,iw_IL,hi_IN,hu_HU,it_IT,ja_JP,ko_KR,nb_NO,fa_IR,pl_PL,pt_BR,pt_PT,ru_RU,sk_SK,es_ES,sv_SE,tr_TR,uk_UA,vi_VN"));
		selenium.select("_130_timeZoneId",
			RuntimeVariables.replace("label=(UTC ) Coordinated Universal Time"));
		selenium.select("_130_settings--default.regular.theme.id--",
			RuntimeVariables.replace("label=Classic"));
		selenium.select("//fieldset[3]/div/span[2]/span/span/select",
			RuntimeVariables.replace("label=Mobile"));
		selenium.select("_130_settings--control.panel.layout.regular.theme.id--",
			RuntimeVariables.replace("label=Control Panel"));
		selenium.clickAt("googleAppsLink", RuntimeVariables.replace(""));
		selenium.type("_130_settings--google.apps.username--",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--google.apps.password--",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}