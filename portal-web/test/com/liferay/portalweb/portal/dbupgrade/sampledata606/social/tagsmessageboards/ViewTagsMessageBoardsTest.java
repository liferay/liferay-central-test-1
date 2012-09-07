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

package com.liferay.portalweb.portal.dbupgrade.sampledata606.social.tagsmessageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewTagsMessageBoardsTest extends BaseTestCase {
	public void testViewTagsMessageBoards() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/home/");
		selenium.waitForElementPresent("link=Activities Page");
		selenium.clickAt("link=Activities Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=Message1 Tag1 Test1"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, Message1 Tag1 Test1, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent("link=Message2 Tag2 Test2"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, Message2 Tag2 Test2, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent("link=Message3 Tag3 Test3"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, Message3 Tag3 Test3, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent("link=MessageA TagA TestA"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, MessageA TagA TestA, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent("link=MessageB TagB TestB"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, MessageB TagB TestB, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent("link=MessageC TagC TestC"));
		assertTrue(selenium.isTextPresent(
				"Joe wrote a new message board post, MessageC TagC TestC, in Tags Message Board Community."));
		assertTrue(selenium.isElementPresent(
				"link=Tags Message Board Community"));
	}
}