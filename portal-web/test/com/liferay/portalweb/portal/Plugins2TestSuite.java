/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portalweb.portal;

import com.liferay.portalweb.plugins.sampleorbeonforms.SampleOrbeonFormsTests;
import com.liferay.portalweb.plugins.samplepermissions.SamplePermissionsTests;
import com.liferay.portalweb.plugins.samplephp.SamplePHPTests;
import com.liferay.portalweb.plugins.sampleportalclient.SamplePortalClientTests;
import com.liferay.portalweb.plugins.sampleportalservice.SamplePortalServiceTests;
import com.liferay.portalweb.plugins.samplepython.SamplePythonTests;
import com.liferay.portalweb.plugins.sampleruby.SampleRubyTests;
import com.liferay.portalweb.plugins.sampleservicebuilder.SampleServiceBuilderTests;
import com.liferay.portalweb.plugins.samplesignin.SampleSignInTests;
import com.liferay.portalweb.plugins.samplespring.SampleSpringTests;
import com.liferay.portalweb.plugins.samplestruts.SampleStrutsTests;
import com.liferay.portalweb.plugins.sampletapestry.SampleTapestryTests;
import com.liferay.portalweb.plugins.sampletest.SampleTestTests;
import com.liferay.portalweb.plugins.sampletestdependency.SampleTestDependencyTests;
import com.liferay.portalweb.plugins.sampletesthook.SampleTestHookTests;
import com.liferay.portalweb.plugins.sampleuitaglibs.SampleUITagLibsTests;
import com.liferay.portalweb.plugins.samplewap.SampleWAPTests;
import com.liferay.portalweb.plugins.stocks.StocksTests;
import com.liferay.portalweb.plugins.sunbookmark.SunBookmarkTests;
import com.liferay.portalweb.plugins.sunelluminate.SunElluminateTests;
import com.liferay.portalweb.plugins.sunflickr.SunFlickrTests;
import com.liferay.portalweb.plugins.suniframe.SunIFrameTests;
import com.liferay.portalweb.plugins.sunmashup.SunMashupTests;
import com.liferay.portalweb.plugins.sunnotepad.SunNotepadTests;
import com.liferay.portalweb.plugins.sunphotoshowajax.SunPhotoShowAjaxTests;
import com.liferay.portalweb.plugins.sunprivacyguard.SunPrivacyGuardTests;
import com.liferay.portalweb.plugins.sunrss.SunRSSTests;
import com.liferay.portalweb.plugins.sunshowtime.SunShowTimeTests;
import com.liferay.portalweb.plugins.sunsinglevideo.SunSingleVideoTests;
import com.liferay.portalweb.plugins.suntourdetails.SunTourDetailsTests;
import com.liferay.portalweb.plugins.suntourlisting.SunTourListingTests;
import com.liferay.portalweb.plugins.suntourmap.SunTourMapTests;
import com.liferay.portalweb.plugins.suntourweather.SunTourWeatherTests;
import com.liferay.portalweb.plugins.sunyoutube.SunYoutubeTests;
import com.liferay.portalweb.plugins.todayinchristianhistory.TodayinChristianHistoryTests;
import com.liferay.portalweb.plugins.twitter.TwitterTests;
import com.liferay.portalweb.plugins.weather.WeatherTests;
import com.liferay.portalweb.plugins.webform.WebFormTests;
import com.liferay.portalweb.plugins.westminstercatechism.WestminsterCatechismTests;
import com.liferay.portalweb.plugins.widgetconsumer.WidgetConsumerTests;
import com.liferay.portalweb.plugins.workflow.WorkflowTests;
import com.liferay.portalweb.portal.login.LoginTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="Plugins2TestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Plugins2TestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(SampleOrbeonFormsTests.suite());
		testSuite.addTest(SamplePermissionsTests.suite());
		testSuite.addTest(SamplePHPTests.suite());
		testSuite.addTest(SamplePortalClientTests.suite());
		testSuite.addTest(SamplePortalServiceTests.suite());
		testSuite.addTest(SamplePythonTests.suite());
		testSuite.addTest(SampleRubyTests.suite());
		testSuite.addTest(SampleServiceBuilderTests.suite());
		testSuite.addTest(SampleSignInTests.suite());
		testSuite.addTest(SampleSpringTests.suite());
		testSuite.addTest(SampleStrutsTests.suite());
		//testSuite.addTest(SampleStrutsLiferayTests.suite());
		testSuite.addTest(SampleTapestryTests.suite());
		testSuite.addTest(SampleTestTests.suite());
		testSuite.addTest(SampleTestDependencyTests.suite());
		testSuite.addTest(SampleTestHookTests.suite());
		testSuite.addTest(SampleUITagLibsTests.suite());
		testSuite.addTest(SampleWAPTests.suite());
		testSuite.addTest(StocksTests.suite());
		testSuite.addTest(SunBookmarkTests.suite());
		testSuite.addTest(SunElluminateTests.suite());
		testSuite.addTest(SunFlickrTests.suite());
		testSuite.addTest(SunIFrameTests.suite());
		testSuite.addTest(SunMashupTests.suite());
		testSuite.addTest(SunNotepadTests.suite());
		testSuite.addTest(SunPhotoShowAjaxTests.suite());
		testSuite.addTest(SunPrivacyGuardTests.suite());
		testSuite.addTest(SunRSSTests.suite());
		testSuite.addTest(SunShowTimeTests.suite());
		testSuite.addTest(SunSingleVideoTests.suite());
		testSuite.addTest(SunTourDetailsTests.suite());
		testSuite.addTest(SunTourListingTests.suite());
		testSuite.addTest(SunTourMapTests.suite());
		testSuite.addTest(SunTourWeatherTests.suite());
		testSuite.addTest(SunYoutubeTests.suite());
		testSuite.addTest(TodayinChristianHistoryTests.suite());
		testSuite.addTest(TwitterTests.suite());
		testSuite.addTest(WeatherTests.suite());
		testSuite.addTest(WebFormTests.suite());
		testSuite.addTest(WestminsterCatechismTests.suite());
		testSuite.addTest(WidgetConsumerTests.suite());
		testSuite.addTest(WorkflowTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}