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

package com.liferay.portalweb.portlet.webcontentlist.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.webcontentlist.portlet.addportletwcl.AddPortletWCLTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.addportletwclduplicate.AddPortletWCLDuplicateTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.configureportletwcldisplayguest.ConfigurePortletWCLDisplayGuestTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.configureportletwclfiltersitescopeglobal.ConfigurePortletWCLFilterSiteScopeGlobalTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.configureportletwclfiltersitescopeliferay.ConfigurePortletWCLFilterSiteScopeLiferayTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.configureportletwclfiltersitescopemysite.ConfigurePortletWCLFilterSiteScopeMySiteTests;
import com.liferay.portalweb.portlet.webcontentlist.portlet.configureportletwclfilterstructure.ConfigurePortletWCLFilterStructureTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletWCLTests.suite());
		testSuite.addTest(AddPortletWCLDuplicateTests.suite());
		testSuite.addTest(ConfigurePortletWCLDisplayGuestTests.suite());
		testSuite.addTest(
			ConfigurePortletWCLFilterSiteScopeGlobalTests.suite());
		testSuite.addTest(
			ConfigurePortletWCLFilterSiteScopeLiferayTests.suite());
		testSuite.addTest(
			ConfigurePortletWCLFilterSiteScopeMySiteTests.suite());
		testSuite.addTest(ConfigurePortletWCLFilterStructureTests.suite());

		return testSuite;
	}

}