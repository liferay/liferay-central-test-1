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

package com.liferay.portalweb.portlet.assetpublisher.wikipage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportletabstractswikipageap.ViewConfigurePortletAbstractsWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportletavailablewikipageap.ViewConfigurePortletAvailableWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportletcurrentwikipageap.ViewConfigurePortletCurrentWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportletfullcontentwikipageap.ViewConfigurePortletFullContentWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportlettablewikipageap.ViewConfigurePortletTableWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewconfigureportlettitlelistwikipageap.ViewConfigurePortletTitleListWikiPageAPTests;
import com.liferay.portalweb.portlet.assetpublisher.wikipage.viewwikipageviewcountap.ViewWikiPageViewCountAPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewWikiPageViewCountAPTests.suite());
		testSuite.addTest(ViewConfigurePortletAbstractsWikiPageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletAvailableWikiPageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletCurrentWikiPageAPTests.suite());
		testSuite.addTest(
			ViewConfigurePortletFullContentWikiPageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTableWikiPageAPTests.suite());
		testSuite.addTest(ViewConfigurePortletTitleListWikiPageAPTests.suite());

		return testSuite;
	}

}