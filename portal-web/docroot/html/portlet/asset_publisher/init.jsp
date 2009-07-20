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

<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portal.kernel.xml.Document" %>
<%@ page import="com.liferay.portal.kernel.xml.Element" %>
<%@ page import="com.liferay.portal.kernel.xml.SAXReaderUtil" %>
<%@ page import="com.liferay.portlet.PortalPreferences" %>
<%@ page import="com.liferay.portlet.asset.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.asset.NoSuchTagException" %>
<%@ page import="com.liferay.portlet.asset.NoSuchTagPropertyException" %>
<%@ page import="com.liferay.portlet.asset.model.AssetCategory" %>
<%@ page import="com.liferay.portlet.asset.model.AssetEntry" %>
<%@ page import="com.liferay.portlet.asset.model.AssetEntryType" %>
<%@ page import="com.liferay.portlet.asset.model.AssetTag" %>
<%@ page import="com.liferay.portlet.asset.model.AssetTagProperty" %>
<%@ page import="com.liferay.portlet.asset.model.AssetVocabulary" %>
<%@ page import="com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetEntryServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.service.persistence.AssetEntryQuery" %>
<%@ page import="com.liferay.portlet.asset.util.AssetUtil" %>
<%@ page import="com.liferay.portlet.assetpublisher.util.AssetPublisherUtil" %>
<%@ page import="com.liferay.portlet.blogs.model.BlogsEntry" %>
<%@ page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.blogs.service.BlogsEntryServiceUtil" %>
<%@ page import="com.liferay.portlet.blogs.service.permission.BlogsEntryPermission" %>
<%@ page import="com.liferay.portlet.blogs.service.permission.BlogsPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksEntry" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksFolder" %>
<%@ page import="com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl" %>
<%@ page import="com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.service.permission.BookmarksPermission" %>
<%@ page import="com.liferay.portlet.bookmarks.util.BookmarksUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFolder" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFolderConstants" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.permission.DLPermission" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.model.IGFolder" %>
<%@ page import="com.liferay.portlet.imagegallery.model.IGImage" %>
<%@ page import="com.liferay.portlet.imagegallery.model.impl.IGFolderImpl" %>
<%@ page import="com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.service.permission.IGImagePermission" %>
<%@ page import="com.liferay.portlet.imagegallery.service.permission.IGPermission" %>
<%@ page import="com.liferay.portlet.journalcontent.util.JournalContentUtil" %>
<%@ page import="com.liferay.portlet.journal.NoSuchArticleException" %>
<%@ page import="com.liferay.portlet.journal.action.EditArticleAction" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticleResource" %>
<%@ page import="com.liferay.portlet.journal.model.impl.JournalArticleImpl" %>
<%@ page import="com.liferay.portlet.journal.search.ArticleDisplayTerms" %>
<%@ page import="com.liferay.portlet.journal.search.ArticleSearch" %>
<%@ page import="com.liferay.portlet.journal.search.ArticleSearchTerms" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %>
<%@ page import="com.liferay.portlet.journal.service.permission.JournalPermission" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessage" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiNode" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPage" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageDisplay" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageResource" %>
<%@ page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiPagePermission" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiCacheUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiUtil" %>
<%@ page import="com.liferay.util.xml.DocUtil" %>

<%@ page import="java.io.StringReader" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String selectionStyle = preferences.getValue("selection-style", null);

if (Validator.isNull(selectionStyle)) {
	selectionStyle = "dynamic";
}

boolean defaultScope = GetterUtil.getBoolean(preferences.getValue("default-scope", null), true);

long[] groupIds = new long[] {scopeGroupId};

if (!defaultScope) {
	String[] scopeIds = preferences.getValues("scope-ids", new String[] {"group" + StringPool.UNDERLINE + scopeGroupId});

	groupIds = new long[scopeIds.length];

	for (int i = 0; i < scopeIds.length; i++) {
		String[] scopeIdFragments = StringUtil.split(scopeIds[i], StringPool.UNDERLINE);

		if (scopeIdFragments[0].equals("Layout")) {
			long scopeIdLayoutId = GetterUtil.getLong(scopeIdFragments[1]);

			Layout scopeIdLayout = LayoutLocalServiceUtil.getLayout(scopeGroupId, layout.isPrivateLayout(), scopeIdLayoutId);

			groupIds[i] = scopeIdLayout.getScopeGroup().getGroupId();
		}
		else {
			long scopeIdGroupId = GetterUtil.getLong(scopeIdFragments[1]);

			groupIds[i] = scopeIdGroupId;
		}
	}
}

AssetEntryType[] assetEntryTypes = AssetEntryServiceUtil.getEntryTypes(themeDisplay.getLanguageId());

long[] availableClassNameIds = new long[assetEntryTypes.length];

for (int i = 0; i < assetEntryTypes.length; i++) {
	availableClassNameIds[i] = assetEntryTypes[i].getClassNameId();
}

boolean anyAssetType = GetterUtil.getBoolean(preferences.getValue("any-asset-type", Boolean.TRUE.toString()));

long[] classNameIds = availableClassNameIds;

if (Validator.equals(selectionStyle, "dynamic") && (preferences.getValues("class-name-ids", null) != null)) {
	classNameIds = GetterUtil.getLongValues(preferences.getValues("class-name-ids", null));
}

long[] allAssetCategoryIds = new long[0];
long[] anyAssetCategoryIds = new long[0];
long[] notAllAssetCategoryIds = new long[0];
long[] notAnyAssetCategoryIds = new long[0];

String[] allAssetTagNames = new String[0];
String[] anyAssetTagNames = new String[0];
String[] notAllAssetTagNames = new String[0];
String[] notAnyAssetTagNames = new String[0];

if (selectionStyle.equals("dynamic")) {
	for (int i = 0; true; i++) {
		String[] queryValues = preferences.getValues("queryValues" + i, null);

		if ((queryValues == null) || (queryValues.length == 0)) {
			break;
		}

		boolean queryContains = GetterUtil.getBoolean(preferences.getValue("queryContains" + i, StringPool.BLANK));
		boolean queryAndOperator = GetterUtil.getBoolean(preferences.getValue("queryAndOperator" + i, StringPool.BLANK));
		String queryName = preferences.getValue("queryName" + i, StringPool.BLANK);

		if (Validator.equals(queryName, "assetCategories")) {
			long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

			if (queryContains  && queryAndOperator) {
				allAssetCategoryIds = assetCategoryIds;
			}
			else if (queryContains  && !queryAndOperator) {
				anyAssetCategoryIds = assetCategoryIds;
			}
			else if (!queryContains  && queryAndOperator) {
				notAllAssetCategoryIds = assetCategoryIds;
			}
			else {
				notAnyAssetCategoryIds = assetCategoryIds;
			}
		}
		else {
			if (queryContains && queryAndOperator) {
				allAssetTagNames = queryValues;
			}
			else if (queryContains && !queryAndOperator) {
				anyAssetTagNames = queryValues;
			}
			else if (!queryContains && queryAndOperator) {
				notAllAssetTagNames = queryValues;
			}
			else {
				notAnyAssetTagNames = queryValues;
			}
		}
	}
}

long assetVocabularyId = GetterUtil.getLong(preferences.getValue("asset-vocabulary-id", StringPool.BLANK));

long assetCategoryId = ParamUtil.getLong(request, "categoryId");

String assetCategoryName = null;
String assetVocabularyName = null;

if (assetCategoryId > 0) {
	allAssetCategoryIds = new long[] {assetCategoryId};

	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);

	assetCategoryName = assetCategory.getName();

	AssetVocabulary assetVocabulary =  AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabularyName = assetVocabulary.getName();

	PortalUtil.setPageKeywords(assetCategory.getName(), request);
}

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName)) {
	allAssetTagNames = new String[] {assetTagName};

	PortalUtil.setPageKeywords(assetTagName, request);
}

boolean mergeUrlTags = GetterUtil.getBoolean(preferences.getValue("merge-url-tags", null), true);

String displayStyle = GetterUtil.getString(preferences.getValue("display-style", "abstracts"));

if (Validator.isNull(displayStyle)) {
	displayStyle = "abstracts";
}

boolean showAssetTitle = GetterUtil.getBoolean(preferences.getValue("show-asset-title", null), true);
boolean showContextLink = GetterUtil.getBoolean(preferences.getValue("show-context-link", null), true);
int abstractLength = GetterUtil.getInteger(preferences.getValue("abstract-length", StringPool.BLANK), 200);
String assetLinkBehaviour = GetterUtil.getString(preferences.getValue("asset-link-behaviour", "showFullContent"));
String orderByColumn1 = GetterUtil.getString(preferences.getValue("order-by-column-1", "modifiedDate"));
String orderByColumn2 = GetterUtil.getString(preferences.getValue("order-by-column-2", "title"));
String orderByType1 = GetterUtil.getString(preferences.getValue("order-by-type-1", "DESC"));
String orderByType2 = GetterUtil.getString(preferences.getValue("order-by-type-2", "ASC"));
boolean excludeZeroViewCount = GetterUtil.getBoolean(preferences.getValue("exclude-zero-view-count", "0"));
int delta = GetterUtil.getInteger(preferences.getValue("delta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String paginationType = GetterUtil.getString(preferences.getValue("pagination-type", "none"));
boolean showAvailableLocales = GetterUtil.getBoolean(preferences.getValue("show-available-locales", StringPool.BLANK));
boolean enableRatings = GetterUtil.getBoolean(preferences.getValue("enable-ratings", null));
boolean enableComments = GetterUtil.getBoolean(preferences.getValue("enable-comments", null));
boolean enableCommentRatings = GetterUtil.getBoolean(preferences.getValue("enable-comment-ratings", null));
boolean enableTagBasedNavigation = GetterUtil.getBoolean(preferences.getValue("enable-tag-based-navigation", null));

String defaultMetadataFields = StringPool.BLANK;
String allMetadataFields = "create-date,modified-date,publish-date,expiration-date,priority,author,view-count,categories,tags";

String[] metadataFields = StringUtil.split(preferences.getValue("metadata-fields", defaultMetadataFields));

String[] assetEntryXmls = preferences.getValues("asset-entry-xml", new String[0]);

boolean viewInContext = assetLinkBehaviour.equals("viewInPortlet");

boolean showPortletWithNoResults = false;
boolean groupByClass = (assetVocabularyId == -1);
boolean allowEmptyResults = false;

DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/asset_publisher/init-ext.jsp" %>

<%!
private String _checkViewURL(String viewURL, String currentURL, ThemeDisplay themeDisplay) {
	if (viewURL.startsWith(themeDisplay.getURLPortal())) {
		viewURL = HttpUtil.setParameter(viewURL, "redirect", currentURL);
	}

	return viewURL;
}
%>