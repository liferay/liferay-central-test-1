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

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ControlPanelTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ControlPanelTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(ControlPanelTest.class);
		testSuite.addTestSuite(CreateRolesTest.class);
		testSuite.addTestSuite(DefineContentAdministratorRolesTest.class);
		testSuite.addTestSuite(CA_PortalRolesTest.class);
		testSuite.addTestSuite(DefineContentCreatorRolesTest.class);
		testSuite.addTestSuite(CC_PortalRolesTest.class);
		testSuite.addTestSuite(CC_BlogsRolesTest.class);
		testSuite.addTestSuite(DefineContentPublisherRolesTest.class);
		testSuite.addTestSuite(CP_PortalRolesTest.class);
		testSuite.addTestSuite(AddContentAdministratorTest.class);
		testSuite.addTestSuite(AddContentCreatorTest.class);
		testSuite.addTestSuite(AddContentPublisherTest.class);
		testSuite.addTestSuite(AssignMembersTest.class);
		testSuite.addTestSuite(LoginUsersTest.class);
		testSuite.addTestSuite(LogoutTest.class);

		return testSuite;
	}

}