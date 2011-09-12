/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyfriends;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWHEntryContentViewableByFriendsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddUserSOFriendTest.class);
		testSuite.addTestSuite(AddUserSOFollowerTest.class);
		testSuite.addTestSuite(AddWHEntryContentViewableByFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_NewPasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_SignInTest.class);
		testSuite.addTestSuite(SOFr_AddAsFriendCCTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFo_NewPasswordTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFo_SignInTest.class);
		testSuite.addTestSuite(SOFo_AddAsFollowerCCTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(ConfirmFriendRequestTest.class);
		testSuite.addTestSuite(EditWHEntryContentViewableByFriendsTest.class);
		testSuite.addTestSuite(
			ViewEditWHEntryContentViewableByFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFr_SignInTest.class);
		testSuite.addTestSuite(
			SOFr_ViewEditWHContentViewableByFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOFo_SignInTest.class);
		testSuite.addTestSuite(
			SOFo_ViewEditWHContentViewableByFriendsTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(SOSignInTest.class);
		testSuite.addTestSuite(TearDownWHEntryContentTest.class);
		testSuite.addTestSuite(TearDownSOUserTest.class);

		return testSuite;
	}

}