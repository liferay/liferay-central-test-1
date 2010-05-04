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

package com.liferay.portalweb.portal.controlpanel.communities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * <a href="AssertCommunityDropDownTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssertCommunityDropDownTest extends BaseTestCase {
	public void testAssertCommunityDropDown() throws Exception {
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Test Community"),
			selenium.getText("//li[6]/a/span[1]"));
		assertEquals(RuntimeVariables.replace("Public"),
			selenium.getText("//li[6]/a/span[2]"));
		assertEquals(RuntimeVariables.replace("Test Community"),
			selenium.getText("//li[7]/a/span[1]"));
		assertEquals(RuntimeVariables.replace("Private"),
			selenium.getText("//li[7]/a/span[2]"));
	}
}