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

package com.liferay.portalweb.portal.controlpanel.organizations.suborganization.deletesuborganizationassignmembers;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.AddOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization.TearDownOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.addsuborganization.AddSubOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.addsuborganization.TearDownSubOrganizationTest;
import com.liferay.portalweb.portal.controlpanel.organizations.suborganization.assignmemberssuborganizationuser.AssignMembersSubOrganizationUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.AddUserTest;
import com.liferay.portalweb.portal.controlpanel.users.user.adduser.TearDownUserTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteSubOrganizationAssignMembersTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddOrganizationTest.class);
		testSuite.addTestSuite(AddSubOrganizationTest.class);
		testSuite.addTestSuite(AddUserTest.class);
		testSuite.addTestSuite(AssignMembersSubOrganizationUserTest.class);
		testSuite.addTestSuite(DeleteSubOrganizationAssignMembersTest.class);
		testSuite.addTestSuite(ViewDeleteSubOrganizationAssignMembersTest.class);
		testSuite.addTestSuite(TearDownUserTest.class);
		testSuite.addTestSuite(TearDownSubOrganizationTest.class);
		testSuite.addTestSuite(TearDownOrganizationTest.class);

		return testSuite;
	}
}