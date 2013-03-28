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

package com.liferay.portalweb.permissions.blogs.blogsentry.deleteblogsentry.ownerinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveOwnerInlineBlogsDeleteEntryTest extends BaseTestCase {
	public void testRemoveOwnerInlineBlogsDeleteEntry()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//td[contains(.,'Permissions')]/span/a/span"));
				selenium.clickAt("//td[contains(.,'Permissions')]/span/a/span",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntryDeleteChecked = selenium.isChecked(
						"//input[@id='owner_ACTION_DELETE']");

				if (!blogsEntryDeleteChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='owner_ACTION_DELETE']",
					RuntimeVariables.replace("Blogs Entry Delete"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='owner_ACTION_DELETE']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='owner_ACTION_DELETE']"));

			case 100:
				label = -1;
			}
		}
	}
}