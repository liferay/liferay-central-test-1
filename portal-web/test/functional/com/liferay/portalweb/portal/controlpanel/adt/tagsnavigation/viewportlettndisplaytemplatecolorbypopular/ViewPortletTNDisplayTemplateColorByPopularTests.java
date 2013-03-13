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

package com.liferay.portalweb.portal.controlpanel.adt.tagsnavigation.viewportlettndisplaytemplatecolorbypopular;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.tags.tagsadmin.AddTagTest;
import com.liferay.portalweb.portal.tags.tagsadmin.TearDownTagTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.tagsnavigation.portlet.addportlettn.AddPageTNTest;
import com.liferay.portalweb.portlet.tagsnavigation.portlet.addportlettn.AddPortletTNTest;
import com.liferay.portalweb.portlet.tagsnavigation.portlet.configureportlettndisplaytemplatecolorbypopular.ConfigurePortletTNDisplayTemplateCloudTest;
import com.liferay.portalweb.portlet.tagsnavigation.portlet.configureportlettndisplaytemplatecolorbypopular.ConfigurePortletTNDisplayTemplateColorByPopularTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTNDisplayTemplateColorByPopularTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageTNTest.class);
		testSuite.addTestSuite(AddPortletTNTest.class);
		testSuite.addTestSuite(AddTagTest.class);
		testSuite.addTestSuite(ConfigurePortletTNDisplayTemplateColorByPopularTest.class);
		testSuite.addTestSuite(ViewPortletTNDisplayTemplateColorByPopularTest.class);
		testSuite.addTestSuite(ConfigurePortletTNDisplayTemplateCloudTest.class);
		testSuite.addTestSuite(TearDownTagTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}