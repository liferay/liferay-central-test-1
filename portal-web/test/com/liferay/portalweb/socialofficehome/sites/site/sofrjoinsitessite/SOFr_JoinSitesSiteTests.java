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

package com.liferay.portalweb.socialofficehome.sites.site.sofrjoinsitessite;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SOFr_JoinSitesSiteTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddUserSOFriendTest.class);
		testSuite.addTestSuite(AddSitesSiteTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_NewPasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_SignInTest.class);
		testSuite.addTestSuite(SOFr_AddAsFriendCCActionsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(ConfirmCCFriendRequestTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_SignInTest.class);
		testSuite.addTestSuite(SOFr_JoinSitesSiteTest.class);
		testSuite.addTestSuite(SOFr_ViewSitesSiteTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);
		testSuite.addTestSuite(TearDownSOSitesTest.class);

		return testSuite;
	}
}