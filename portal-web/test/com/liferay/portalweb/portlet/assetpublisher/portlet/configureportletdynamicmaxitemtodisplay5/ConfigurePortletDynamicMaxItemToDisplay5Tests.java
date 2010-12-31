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

package com.liferay.portalweb.portlet.assetpublisher.portlet.configureportletdynamicmaxitemtodisplay5;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDynamicMaxItemToDisplay5Tests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddCategoryTest.class);
		testSuite.addTestSuite(AddMBMessage1Test.class);
		testSuite.addTestSuite(AddMBMessage2Test.class);
		testSuite.addTestSuite(AddMBMessage3Test.class);
		testSuite.addTestSuite(AddMBMessage4Test.class);
		testSuite.addTestSuite(AddMBMessage5Test.class);
		testSuite.addTestSuite(AddMBMessage6Test.class);
		testSuite.addTestSuite(View6MBMessagesTest.class);
		testSuite.addTestSuite(
			ConfigurePortletDynamicMaxItemToDisplay5Test.class);
		testSuite.addTestSuite(View5MBMessagesTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownMBMessageTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}