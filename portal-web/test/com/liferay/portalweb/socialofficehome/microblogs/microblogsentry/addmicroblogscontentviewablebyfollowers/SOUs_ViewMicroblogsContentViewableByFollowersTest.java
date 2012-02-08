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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.addmicroblogscontentviewablebyfollowers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewMicroblogsContentViewableByFollowersTest
	extends BaseTestCase {
	public void testSOUs_ViewMicroblogsContentViewableByFollowers()
		throws Exception {
		selenium.open("/user/socialoffice01/home1");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_2_WAR_microblogsportlet_autocompleteContent']"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Following"),
			selenium.getText("link=Following"));
		selenium.clickAt("link=Following", RuntimeVariables.replace("Following"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("There are no recent activities."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertFalse(selenium.isTextPresent("Microblogs Post"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//nav/ul/li[contains(.,'Microblogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent("Microblogs Post"));
		selenium.open("/web/joebloggs");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertFalse(selenium.isTextPresent("Microblogs Post"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//nav/ul/li[contains(.,'Microblogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent("Microblogs Post"));
	}
}