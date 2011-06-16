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

package com.liferay.portalweb.portal.permissions.imagegallery;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.permissions.imagegallery.assertactions.AssertActionsTests;
import com.liferay.portalweb.portal.permissions.imagegallery.portlet.PortletTests;
import com.liferay.portalweb.portal.permissions.imagegallery.setup.SetupTests;
import com.liferay.portalweb.portal.permissions.imagegallery.teardown.TearDownTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageGalleryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(SetupTests.suite());
		testSuite.addTest(AssertActionsTests.suite());
		testSuite.addTest(PortletTests.suite());
		testSuite.addTest(TearDownTests.suite());

		return testSuite;
	}

}