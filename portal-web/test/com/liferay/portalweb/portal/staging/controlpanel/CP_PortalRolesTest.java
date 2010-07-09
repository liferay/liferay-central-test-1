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

package com.liferay.portalweb.portal.staging.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CP_PortalRolesTest extends BaseTestCase {
	public void testCP_PortalRoles() throws Exception {
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Communities"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupASSIGN_MEMBERS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupASSIGN_REVIEWER']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ARCHIVED_SETUPS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupPUBLISH_STAGING']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.LayoutADD_DISCUSSION']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.LayoutUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.LayoutVIEW']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.tasks.model.TasksProposalADD_DISCUSSION']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.tasks.model.TasksProposalDELETE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.tasks.model.TasksProposalUPDATE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.tasks.model.TasksProposalVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}