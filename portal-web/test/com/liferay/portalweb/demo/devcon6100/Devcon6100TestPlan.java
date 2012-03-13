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

package com.liferay.portalweb.demo.devcon6100;

import com.liferay.portalweb.demo.devcon6100.dynamicdata.DynamicDataTestPlan;
import com.liferay.portalweb.demo.devcon6100.fundamentals.FundamentalsTestPlan;
import com.liferay.portalweb.demo.devcon6100.media.MediaTestPlan;
import com.liferay.portalweb.demo.devcon6100.sitemanagement.SiteManagementTestPlan;
import com.liferay.portalweb.demo.devcon6100.useradmin.UserAdminTestPlan;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Devcon6100TestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DynamicDataTestPlan.suite());
		testSuite.addTest(FundamentalsTestPlan.suite());
		testSuite.addTest(MediaTestPlan.suite());
		testSuite.addTest(SiteManagementTestPlan.suite());
		testSuite.addTest(UserAdminTestPlan.suite());

		return testSuite;
	}

}