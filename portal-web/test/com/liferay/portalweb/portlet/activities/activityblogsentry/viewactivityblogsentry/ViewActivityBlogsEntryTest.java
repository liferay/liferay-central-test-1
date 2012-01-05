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

package com.liferay.portalweb.portlet.activities.activityblogsentry.viewactivityblogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActivityBlogsEntryTest extends BaseTestCase {
	public void testViewActivityBlogsEntry() throws Exception {
		selenium.open("/group/joebloggs/home/");
		selenium.getEval("window.Liferay.fire('initDockbar');");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Activities Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Activities Test Page",
			RuntimeVariables.replace("Activities Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.getEval("window.Liferay.fire('initDockbar');");
		assertEquals(RuntimeVariables.replace(
				"Joe wrote a new blog entry, Blogs Entry Title, in Liferay."),
			selenium.getText("//td[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//td[2]/div[1]/a[1]"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//td[2]/div[1]/a[2]"));
	}
}