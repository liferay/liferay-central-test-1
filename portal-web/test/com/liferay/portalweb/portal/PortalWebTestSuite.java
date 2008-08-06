/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portalweb.portlet.admin.AdminTests;
import com.liferay.portalweb.portlet.announcements.AnnouncementsTests;
import com.liferay.portalweb.portlet.assetpublisher.AssetPublisherTests;
import com.liferay.portalweb.portlet.blogs.BlogsTests;
import com.liferay.portalweb.portlet.blogsaggregator.BlogsAggregatorTests;
import com.liferay.portalweb.portlet.bookmarks.BookmarksTests;
import com.liferay.portalweb.portlet.breadcrumb.BreadcrumbTests;
import com.liferay.portalweb.portlet.calendar.CalendarTests;
import com.liferay.portalweb.portlet.communities.CommunitiesTests;
import com.liferay.portalweb.portlet.enterpriseadmin.EnterpriseAdminTests;
import com.liferay.portalweb.portlet.hellovelocity.HelloVelocityTests;
import com.liferay.portalweb.portlet.helloworld.HelloWorldTests;
import com.liferay.portalweb.portlet.iframe.IFrameTests;
import com.liferay.portalweb.portlet.imagegallery.ImageGalleryTests;
import com.liferay.portalweb.portlet.invitation.InvitationTests;
import com.liferay.portalweb.portlet.journal.JournalTests;
import com.liferay.portalweb.portlet.journalarticles.JournalArticlesTests;
import com.liferay.portalweb.portlet.journalcontent.JournalContentTests;
import com.liferay.portalweb.portlet.journalcontentsearch.JournalContentSearchTests;
import com.liferay.portalweb.portlet.language.LanguageTests;
import com.liferay.portalweb.portlet.managepages.ManagePagesTests;
import com.liferay.portalweb.portlet.messageboards.MessageBoardsTests;
import com.liferay.portalweb.portlet.navigation.NavigationTests;
import com.liferay.portalweb.portlet.nestedportlets.NestedPortletsTests;
import com.liferay.portalweb.portlet.openidsignin.OpenIDSignInTests;
import com.liferay.portalweb.portlet.organizationadmin.OrganizationAdminTests;
import com.liferay.portalweb.portlet.pagecomments.PageCommentsTests;
import com.liferay.portalweb.portlet.pageratings.PageRatingsTests;
import com.liferay.portalweb.portlet.recentbloggers.RecentBloggersTests;
import com.liferay.portalweb.portlet.recentdocuments.RecentDocumentsTests;
import com.liferay.portalweb.portlet.rss.RSSTests;
import com.liferay.portalweb.portlet.search.SearchTests;
import com.liferay.portalweb.portlet.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.sitemap.SiteMapTests;
import com.liferay.portalweb.portlet.softwarecatalog.SoftwareCatalogTests;
import com.liferay.portalweb.portlet.stocks.StocksTests;
import com.liferay.portalweb.portlet.tagsadmin.TagsAdminTests;
import com.liferay.portalweb.portlet.translator.TranslatorTests;
import com.liferay.portalweb.portlet.updatemanager.UpdateManagerTests;
import com.liferay.portalweb.portlet.weather.WeatherTests;
import com.liferay.portalweb.portlet.webform.WebFormTests;
import com.liferay.portalweb.portlet.webproxy.WebProxyTests;
import com.liferay.portalweb.portlet.wiki.WikiTests;
import com.liferay.portalweb.portlet.wikidisplay.WikiDisplayTests;
import com.liferay.portalweb.portlet.xslcontent.XSLContentTests;

/**
 * <a href="PortalWebTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalWebTestSuite extends BaseTests {

	public PortalWebTestSuite() {
		addTestSuite(LoginTests.class);
		addTestSuite(EnterpriseAdminTests.class);
		addTestSuite(AdminTests.class);
		addTestSuite(AnnouncementsTests.class);
		addTestSuite(BlogsTests.class);
		addTestSuite(BlogsAggregatorTests.class);
		addTestSuite(AssetPublisherTests.class);
		addTestSuite(BookmarksTests.class);
		addTestSuite(BreadcrumbTests.class);
		addTestSuite(CalendarTests.class);
		addTestSuite(CommunitiesTests.class);
		addTestSuite(HelloVelocityTests.class);
		addTestSuite(HelloWorldTests.class);
		addTestSuite(IFrameTests.class);
		addTestSuite(ImageGalleryTests.class);
		addTestSuite(InvitationTests.class);
		addTestSuite(JournalTests.class);
		addTestSuite(JournalArticlesTests.class);
		addTestSuite(JournalContentTests.class);
		addTestSuite(JournalContentSearchTests.class);
		addTestSuite(LanguageTests.class);
		addTestSuite(ManagePagesTests.class);
		addTestSuite(MessageBoardsTests.class);
		addTestSuite(NavigationTests.class);
		addTestSuite(NestedPortletsTests.class);
		addTestSuite(OpenIDSignInTests.class);
		addTestSuite(OrganizationAdminTests.class);
		addTestSuite(PageCommentsTests.class);
		addTestSuite(PageRatingsTests.class);
		addTestSuite(RecentBloggersTests.class);
		addTestSuite(RecentDocumentsTests.class);
		addTestSuite(RSSTests.class);
		addTestSuite(SearchTests.class);
		addTestSuite(SessionExpirationTests.class);
		addTestSuite(ShoppingTests.class);
		addTestSuite(SiteMapTests.class);
		addTestSuite(SoftwareCatalogTests.class);
		addTestSuite(StocksTests.class);
		addTestSuite(TagsAdminTests.class);
		addTestSuite(TranslatorTests.class);
		addTestSuite(UpdateManagerTests.class);
		addTestSuite(WeatherTests.class);
		addTestSuite(WebFormTests.class);
		addTestSuite(WebProxyTests.class);
		addTestSuite(WikiTests.class);
		addTestSuite(WikiDisplayTests.class);
		addTestSuite(XSLContentTests.class);

		addTestSuite(StopSeleniumTest.class);
	}

}