<%
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
%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.documentlibrary.service.DLServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portlet.asset.NoSuchCategoryException" %>
<%@ page import="com.liferay.portlet.asset.NoSuchCategoryPropertyException" %>
<%@ page import="com.liferay.portlet.asset.NoSuchTagException" %>
<%@ page import="com.liferay.portlet.asset.NoSuchTagPropertyException" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategory" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategoryProperty" %>
<%@ page import="com.liferay.portlet.asset.model.AssetEntry" %>
<%@ page import="com.liferay.portlet.asset.model.AssetTag" %>
<%@ page import="com.liferay.portlet.asset.model.AssetTagProperty" %>
<%@ page import="com.liferay.portlet.asset.model.AssetVocabulary" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %>
<%@ page import="com.liferay.portlet.asset.util.AssetUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DocumentConversionUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.DuplicateNodeNameException" %>
<%@ page import="com.liferay.portlet.wiki.DuplicatePageException" %>
<%@ page import="com.liferay.portlet.wiki.ImportFilesException" %>
<%@ page import="com.liferay.portlet.wiki.NodeNameException" %>
<%@ page import="com.liferay.portlet.wiki.NoSuchNodeException" %>
<%@ page import="com.liferay.portlet.wiki.NoSuchPageException" %>
<%@ page import="com.liferay.portlet.wiki.PageContentException" %>
<%@ page import="com.liferay.portlet.wiki.PageTitleException" %>
<%@ page import="com.liferay.portlet.wiki.PageVersionException" %>
<%@ page import="com.liferay.portlet.wiki.WikiFormatException" %>
<%@ page import="com.liferay.portlet.wiki.importers.WikiImporterKeys" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiNode" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPage" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageConstants" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageDisplay" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageResource" %>
<%@ page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiNodePermission" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiPagePermission" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiPermission" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiCacheUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.comparator.PageVersionComparator" %>
<%@ page import="com.liferay.util.RSSUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean enablePageRatings = PropsValues.WIKI_PAGE_RATINGS_ENABLED && GetterUtil.getBoolean(preferences.getValue("enable-page-ratings", null), true);
boolean enableComments = PropsValues.WIKI_PAGE_COMMENTS_ENABLED && GetterUtil.getBoolean(preferences.getValue("enable-comments", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(preferences.getValue("enable-comment-ratings", null), true);

String allNodes = ListUtil.toString(WikiNodeLocalServiceUtil.getNodes(scopeGroupId), "name");

String[] visibleNodes = StringUtil.split(preferences.getValue("visible-nodes", allNodes));
String[] hiddenNodes = StringUtil.split(preferences.getValue("hidden-nodes", null));

int rssDelta = GetterUtil.getInteger(preferences.getValue("rss-delta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = preferences.getValue("rss-display-style", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

StringBuilder rssURLParams = new StringBuilder();

if ((rssDelta != SearchContainer.DEFAULT_DELTA) || !rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
	if (rssDelta != SearchContainer.DEFAULT_DELTA) {
		rssURLParams.append("&max=");
		rssURLParams.append(rssDelta);
	}

	if (!rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		rssURLParams.append("&displayStyle=");
		rssURLParams.append(rssDisplayStyle);
	}
}

StringBuilder rssURLAtomParams = new StringBuilder(rssURLParams.toString());

rssURLAtomParams.append("&type=");
rssURLAtomParams.append(RSSUtil.ATOM);
rssURLAtomParams.append("&version=1.0");

StringBuilder rssURLRSS10Params = new StringBuilder(rssURLParams.toString());

rssURLRSS10Params.append("&type=");
rssURLRSS10Params.append(RSSUtil.RSS);
rssURLRSS10Params.append("&version=1.0");

StringBuilder rssURLRSS20Params = new StringBuilder(rssURLParams.toString());

rssURLRSS20Params.append("&type=");
rssURLRSS20Params.append(RSSUtil.RSS);
rssURLRSS20Params.append("&version=2.0");

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>