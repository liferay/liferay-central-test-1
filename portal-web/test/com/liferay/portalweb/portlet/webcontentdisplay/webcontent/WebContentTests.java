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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd.AddPortletScopeCurrentPageWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopedefaultwcwebcontentwcd.AddPortletScopeDefaultWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addwcdwebcontent.AddWCDWebContentTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.deletethisversionwebcontentwcddetails.DeleteThisVersionWebContentWCDDetailsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.editwebcontentwcddetails.EditWebContentWCDDetailsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.expirethisversionwebcontentwcddetails.ExpireThisVersionWebContentWCDDetailsTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd.LocalizeWCWebContentTemplateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontentwcd.LocalizeWCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.ratewcdwebcontent.RateWCDWebContentTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.searchwcdwebcontent.SearchWCDWebContentTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectportletscopepage2wcwebcontentwcd.SelectPortletScopePage2WCWebContentWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewlocalizewebcontentlanguagewcd.ViewLocalizeWebContentLanguageWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewlocalizewebcontenttemplatelanguagewcd.ViewLocalizeWebContentTemplateLanguageWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontenttemplatewcd.ViewPortletShowLocalesWebContentTemplateWCDTests;
import com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewportletshowlocaleswebcontentwcd.ViewPortletShowLocalesWebContentWCDTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			AddPortletScopeCurrentPageWCWebContentWCDTests.suite());
		testSuite.addTest(AddPortletScopeDefaultWCWebContentWCDTests.suite());
		testSuite.addTest(AddWCDWebContentTests.suite());
		testSuite.addTest(DeleteThisVersionWebContentWCDDetailsTests.suite());
		testSuite.addTest(EditWebContentWCDDetailsTests.suite());
		testSuite.addTest(ExpireThisVersionWebContentWCDDetailsTests.suite());
		testSuite.addTest(LocalizeWCWebContentTemplateWCDTests.suite());
		testSuite.addTest(LocalizeWCWebContentWCDTests.suite());
		testSuite.addTest(RateWCDWebContentTests.suite());
		testSuite.addTest(SearchWCDWebContentTests.suite());
		testSuite.addTest(SelectPortletScopePage2WCWebContentWCDTests.suite());
		testSuite.addTest(ViewLocalizeWebContentLanguageWCDTests.suite());
		testSuite.addTest(
			ViewLocalizeWebContentTemplateLanguageWCDTests.suite());
		testSuite.addTest(
			ViewPortletShowLocalesWebContentTemplateWCDTests.suite());
		testSuite.addTest(ViewPortletShowLocalesWebContentWCDTests.suite());

		return testSuite;
	}

}