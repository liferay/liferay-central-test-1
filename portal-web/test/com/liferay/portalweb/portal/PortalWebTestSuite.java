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

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portal.session.SessionExpirationTests;
import com.liferay.portalweb.portlet.announcements.AnnouncementsTests;
import com.liferay.portalweb.portlet.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.blogsaggregator.BlogsAggregatorTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.breadcrumb.BreadcrumbTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.currencyconverter.CurrencyConverterTests;
import com.liferay.portalweb.portlet.dictionary.DictionaryTests;
import com.liferay.portalweb.portlet.directory.DirectoryTests;
import com.liferay.portalweb.portlet.documentlibrary.DocumentLibraryTests;
import com.liferay.portalweb.portlet.documentlibrarydisplay.DocumentLibraryDisplayTests;
import com.liferay.portalweb.portlet.hellovelocity.HelloVelocityTests;
import com.liferay.portalweb.portlet.helloworld.HelloWorldTests;
import com.liferay.portalweb.portlet.iframe.IFrameTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.invitation.InvitationTests;
import com.liferay.portalweb.portlet.loancalculator.LoanCalculatorTests;
import com.liferay.portalweb.portlet.managepages.ManagePagesTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.navigation.NavigationTests;
import com.liferay.portalweb.portlet.nestedportlets.NestedPortletsTests;
import com.liferay.portalweb.portlet.networkutilities.NetworkUtilitiesTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;
import com.liferay.portalweb.portlet.passwordgenerator.PasswordGeneratorTests;
import com.liferay.portalweb.portlet.pollsdisplay.PollsDisplayTests;
import com.liferay.portalweb.portlet.quicknote.QuickNoteTests;
import com.liferay.portalweb.portlet.recentbloggers.RecentBloggersTests;
import com.liferay.portalweb.portlet.recentdocuments.RecentDocumentsTests;
import com.liferay.portalweb.portlet.reverendfun.ReverendFunTests;
import com.liferay.portalweb.portlet.rss.RSSTests;
import com.liferay.portalweb.portlet.search.SearchTests;
import com.liferay.portalweb.portlet.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.smstextmessenger.SMSTextMessengerTests;
import com.liferay.portalweb.portlet.softwarecatalog.SoftwareCatalogTests;
import com.liferay.portalweb.portlet.tagsadmin.TagsAdminTests;
import com.liferay.portalweb.portlet.translator.TranslatorTests;
import com.liferay.portalweb.portlet.unitconverter.UnitConverterTests;
import com.liferay.portalweb.portlet.webcontentdisplay.WebContentDisplayTests;
import com.liferay.portalweb.portlet.webcontentlist.WebContentListTests;
import com.liferay.portalweb.portlet.webcontentsearch.WebContentSearchTests;
import com.liferay.portalweb.portlet.webproxy.WebProxyTests;
import com.liferay.portalweb.portlet.wiki.WikiTests;
import com.liferay.portalweb.portlet.wikidisplay.WikiDisplayTests;
import com.liferay.portalweb.portlet.words.WordsTests;
import com.liferay.portalweb.portlet.xslcontent.XSLContentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AnnouncementsTests.suite());
		testSuite.addTest(AssetPublisherTests.suite());
		testSuite.addTest(BlogsTests.suite());
		testSuite.addTest(BlogsAggregatorTests.suite());
		testSuite.addTest(BookmarksTests.suite());
		testSuite.addTest(BreadcrumbTests.suite());
		testSuite.addTest(CalendarTests.suite());
		testSuite.addTest(CurrencyConverterTests.suite());
		testSuite.addTest(DictionaryTests.suite());
		testSuite.addTest(DirectoryTests.suite());
		testSuite.addTest(DocumentLibraryTests.suite());
		testSuite.addTest(DocumentLibraryDisplayTests.suite());
		testSuite.addTest(HelloVelocityTests.suite());
		testSuite.addTest(HelloWorldTests.suite());
		testSuite.addTest(IFrameTests.suite());
		testSuite.addTest(ImageGalleryTests.suite());
		testSuite.addTest(InvitationTests.suite());
		//testSuite.addTest(LanguageTests.suite());
		testSuite.addTest(LoanCalculatorTests.suite());
		testSuite.addTest(ManagePagesTests.suite());
		testSuite.addTest(MessageBoardsTests.suite());
		testSuite.addTest(NavigationTests.suite());
		testSuite.addTest(NestedPortletsTests.suite());
		testSuite.addTest(NetworkUtilitiesTests.suite());
		//testSuite.addTest(OrganizationAdminTests.suite());
		testSuite.addTest(PageCommentsTests.suite());
		testSuite.addTest(PageRatingsTests.suite());
		testSuite.addTest(PasswordGeneratorTests.suite());
		//testSuite.addTest(PluginInstallerTests.suite());
		testSuite.addTest(PollsDisplayTests.suite());
		testSuite.addTest(QuickNoteTests.suite());
		testSuite.addTest(RecentBloggersTests.suite());
		testSuite.addTest(RecentDocumentsTests.suite());
		testSuite.addTest(ReverendFunTests.suite());
		testSuite.addTest(RSSTests.suite());
		testSuite.addTest(SearchTests.suite());
		testSuite.addTest(SessionExpirationTests.suite());
		testSuite.addTest(ShoppingTests.suite());
		testSuite.addTest(SiteMapTests.suite());
		testSuite.addTest(SMSTextMessengerTests.suite());
		testSuite.addTest(SoftwareCatalogTests.suite());
		testSuite.addTest(TagsAdminTests.suite());
		testSuite.addTest(TranslatorTests.suite());
		testSuite.addTest(UnitConverterTests.suite());
		testSuite.addTest(WebContentDisplayTests.suite());
		testSuite.addTest(WebContentListTests.suite());
		testSuite.addTest(WebContentSearchTests.suite());
		testSuite.addTest(WebProxyTests.suite());
		testSuite.addTest(WikiTests.suite());
		testSuite.addTest(WikiDisplayTests.suite());
		testSuite.addTest(WordsTests.suite());
		testSuite.addTest(XSLContentTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}