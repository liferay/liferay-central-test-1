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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetEntryDisplay;
import com.liferay.portlet.asset.model.AssetEntryType;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.base.AssetEntryLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetEntryValidator;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AssetEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 *
 */
public class AssetEntryLocalServiceImpl extends AssetEntryLocalServiceBaseImpl {

	public void deleteEntry(AssetEntry entry) throws SystemException {
		assetEntryPersistence.remove(entry);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		AssetEntry entry = assetEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			deleteEntry(entry);
		}
	}

	public List<AssetEntry> getCompanyEntries(
			long companyId, int start, int end)
		throws SystemException {

		return assetEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return assetEntryPersistence.countByCompanyId(companyId);
	}

	public AssetEntryDisplay[] getCompanyEntryDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return getEntryDisplays(
			getCompanyEntries(companyId, start, end), languageId);
	}

	public List<AssetEntry> getEntries(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetEntryFinder.findEntries(
				groupId, classNameIds, null, null, null, null,
				excludeZeroViewCount, publishDate, expirationDate, start, end);
		}
		else if (andOperator) {
			return assetEntryFinder.findByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
		else {
			return assetEntryFinder.findByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds, null, null, null,
				null, excludeZeroViewCount, publishDate, expirationDate, start,
				end);
		}
	}

	public List<AssetEntry> getEntries(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getEntries(
			groupId, classNameIds, tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<AssetEntry> getEntries(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetEntryFinder.findEntries(
				groupId, classNameIds, orderByCol1, orderByCol2, orderByType1,
				orderByType2, excludeZeroViewCount, publishDate, expirationDate,
				start, end);
		}
		else if (andOperator) {
			return assetEntryFinder.findByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
		else {
			return assetEntryFinder.findByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds, orderByCol1,
				orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
				publishDate, expirationDate, start, end);
		}
	}

	public List<AssetEntry> getEntries(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws SystemException {

		return getEntries(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public List<AssetEntry> getEntries(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, int start, int end)
		throws SystemException {

		return getEntries(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null, start, end);
	}

	public List<AssetEntry> getEntries(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			String orderByCol1, String orderByCol2, String orderByType1,
			String orderByType2, boolean excludeZeroViewCount, Date publishDate,
			Date expirationDate, int start, int end)
		throws SystemException {

		return getEntries(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public int getEntriesCount(
			long groupId, long[] tagIds, long[] notTagIds,
			boolean andOperator, boolean excludeZeroViewCount)
		throws SystemException {

		return getEntriesCount(
			groupId, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getEntriesCount(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		if ((tagIds.length == 0) && (notTagIds.length == 0)) {
			return assetEntryFinder.countEntries(
				groupId, classNameIds, excludeZeroViewCount, publishDate,
				expirationDate);
		}
		else if (andOperator) {
			return assetEntryFinder.countByAndTagIds(
				groupId, classNameIds, tagIds, notTagIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
		else {
			return assetEntryFinder.countByOrTagIds(
				groupId, classNameIds, tagIds, notTagIds,
				excludeZeroViewCount, publishDate, expirationDate);
		}
	}

	public int getEntriesCount(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount)
		throws SystemException {

		return getEntriesCount(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, null, null);
	}

	public int getEntriesCount(
			long[] tagIds, long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws SystemException {

		return getEntriesCount(
			0, new long[0], tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public AssetEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return assetEntryPersistence.findByPrimaryKey(entryId);
	}

	public AssetEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return assetEntryPersistence.findByC_C(classNameId, classPK);
	}

	public AssetEntryType[] getEntryTypes(String languageId) {
		AssetEntryType[] entryTypes =
			new AssetEntryType[AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES.length];

		for (int i = 0; i < AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES.length;
				i++) {

			entryTypes[i] = getEntryType(
				AssetUtil.ASSET_ENTRY_TYPE_CLASS_NAMES[i], languageId);
		}

		return entryTypes;
	}

	public List<AssetEntry> getTopViewedEntries(
			String className, boolean asc, int start, int end)
		throws SystemException {

		return getTopViewedEntries(new String[] {className}, asc, start, end);
	}

	public List<AssetEntry> getTopViewedEntries(
			String[] className, boolean asc, int start, int end)
		throws SystemException {

		long[] classNameIds = new long[className.length];

		for (int i = 0; i < className.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(className[i]);
		}

		return assetEntryFinder.findByViewCount(classNameIds, asc, start, end);
	}

	public AssetEntry incrementViewCounter(String className, long classPK)
		throws SystemException {

		if (classPK <= 0) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			entry.setViewCount(entry.getViewCount() + 1);

			assetEntryPersistence.update(entry, false);
		}

		return entry;
	}

	public Hits search(
			long companyId, String portletId, String keywords, int start,
			int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(portletId)) {
				contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);
			}
			else {
				BooleanQuery portletIdsQuery = BooleanQueryFactoryUtil.create();

				for (String entryTypePortletId :
						AssetUtil.ASSET_ENTRY_TYPE_PORTLET_IDS) {

					TermQuery termQuery = TermQueryFactoryUtil.create(
						Field.PORTLET_ID, entryTypePortletId);

					portletIdsQuery.add(termQuery, BooleanClauseOccur.SHOULD);
				}

				contextQuery.add(portletIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.TITLE, keywords);
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.DESCRIPTION, keywords);
				searchQuery.addTerm(Field.PROPERTIES, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(companyId, fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public AssetEntryDisplay[] searchEntryDisplays(
			long companyId, String portletId, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		List<AssetEntry> entries = new ArrayList<AssetEntry>();

		Hits hits = search(companyId, portletId, keywords, start, end);

		List<Document> hitsList = hits.toList();

		for (Document doc : hitsList) {
			try {
				AssetEntry entry = getEntry(doc);

				if (entry != null) {
					entries.add(entry);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}

		return getEntryDisplays(entries, languageId);
	}

	public int searchEntryDisplaysCount(
			long companyId, String portletId, String keywords,
			String languageId)
		throws SystemException {

		Hits hits = search(
			companyId, portletId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		return hits.getLength();
	}

	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws PortalException, SystemException {

		return updateEntry(
			userId, groupId, className, classPK, categoryIds, tagNames,
			true, null, null, null, null, null, null, null, null, null, 0, 0,
			null, false);
	}

	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		// Asset

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		title = StringUtil.shorten(title, 300, StringPool.BLANK);
		Date now = new Date();

		validate(className, categoryIds, tagNames);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			long entryId = counterLocalService.increment();

			entry = assetEntryPersistence.create(entryId);

			entry.setCompanyId(user.getCompanyId());
			entry.setUserId(user.getUserId());
			entry.setUserName(user.getFullName());
			entry.setCreateDate(now);
			entry.setClassNameId(classNameId);
			entry.setClassPK(classPK);
			entry.setVisible(visible);
			entry.setPublishDate(publishDate);
			entry.setExpirationDate(expirationDate);

			if (priority == null) {
				entry.setPriority(0);
			}

			entry.setViewCount(0);
		}

		entry.setGroupId(groupId);
		entry.setModifiedDate(now);
		entry.setVisible(visible);
		entry.setStartDate(startDate);
		entry.setEndDate(endDate);
		entry.setPublishDate(publishDate);
		entry.setExpirationDate(expirationDate);
		entry.setMimeType(mimeType);
		entry.setTitle(title);
		entry.setDescription(description);
		entry.setSummary(summary);
		entry.setUrl(url);
		entry.setHeight(height);
		entry.setWidth(width);

		if (priority != null) {
			entry.setPriority(priority.intValue());
		}

		// Categories

		if (categoryIds != null) {
			assetEntryPersistence.setAssetCategories(
				entry.getEntryId(), categoryIds);
		}

		// Tags

		if (tagNames != null) {
			List<AssetTag> tags = new ArrayList<AssetTag>(tagNames.length);

			for (String tagName : tagNames) {
				AssetTag tag = null;

				try {
					tag = assetTagLocalService.getTag(groupId, tagName);
				}
				catch (NoSuchTagException nste) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setAddCommunityPermissions(true);
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setScopeGroupId(groupId);

					tag = assetTagLocalService.addTag(
						user.getUserId(), tagName,
						PropsValues.ASSET_TAG_PROPERTIES_DEFAULT,
						serviceContext);
				}

				if (tag != null) {
					tags.add(tag);
				}
			}

			assetEntryPersistence.setAssetTags(entry.getEntryId(), tags);
		}

		// Update entry after tags so that entry listeners have access to the
		// saved categories and tags

		assetEntryPersistence.update(entry, false);

		// Synchronize

		if (!sync) {
			return entry;
		}

		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry blogsEntry = blogsEntryPersistence.findByPrimaryKey(
				classPK);

			blogsEntry.setTitle(title);

			blogsEntryPersistence.update(blogsEntry, false);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			BookmarksEntry bookmarksEntry =
				bookmarksEntryPersistence.findByPrimaryKey(classPK);

			bookmarksEntry.setName(title);
			bookmarksEntry.setComments(description);
			bookmarksEntry.setUrl(url);

			bookmarksEntryPersistence.update(bookmarksEntry, false);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
				classPK);

			dlFileEntry.setTitle(title);
			dlFileEntry.setDescription(description);

			dlFileEntryPersistence.update(dlFileEntry, false);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticle journalArticle =
				journalArticlePersistence.findByPrimaryKey(classPK);

			journalArticle.setTitle(title);
			journalArticle.setDescription(description);

			journalArticlePersistence.update(journalArticle, false);
		}
		else if (className.equals(MBMessage.class.getName())) {
			MBMessage mbMessage = mbMessagePersistence.findByPrimaryKey(
				classPK);

			mbMessage.setSubject(title);

			mbMessagePersistence.update(mbMessage, false);
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPage wikiPage = wikiPagePersistence.findByPrimaryKey(classPK);

			wikiPage.setTitle(title);

			wikiPagePersistence.update(wikiPage, false);
		}

		return entry;
	}

	public AssetEntry updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		entry.setVisible(visible);

		assetEntryPersistence.update(entry, false);

		return entry;
	}

	public void validate(
			String className, long[] categoryIds, String[] tagNames)
		throws PortalException {

		AssetEntryValidator validator = (AssetEntryValidator)InstancePool.get(
			PropsValues.ASSET_ENTRY_VALIDATOR);

		validator.validate(className, categoryIds, tagNames);
	}

	protected AssetEntry getEntry(Document doc)
		throws PortalException, SystemException {

		String portletId = GetterUtil.getString(doc.get(Field.PORTLET_ID));

		if (portletId.equals(PortletKeys.BLOGS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BlogsEntry.class.getName());
			long classPK = entryId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.BOOKMARKS)) {
			long entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BookmarksEntry.class.getName());
			long classPK = entryId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			long folderId = GetterUtil.getLong(doc.get("repositoryId"));
			String name = doc.get("path");

			DLFileEntry fileEntry = dlFileEntryLocalService.getFileEntry(
				folderId, name);

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());
			long classPK = fileEntry.getFileEntryId();

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.IMAGE_GALLERY)) {
			long imageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				IGImage.class.getName());
			long classPK = imageId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.JOURNAL)) {
			long groupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));
			String articleId = doc.get(Field.ENTRY_CLASS_PK);
			//double version = GetterUtil.getDouble(doc.get("version"));

			long articleResourcePrimKey =
				journalArticleResourceLocalService.getArticleResourcePrimKey(
					groupId, articleId);

			long classNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());
			long classPK = articleResourcePrimKey;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.MESSAGE_BOARDS)) {
			long messageId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				MBMessage.class.getName());
			long classPK = messageId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.WIKI)) {
			long nodeId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
			String title = doc.get(Field.TITLE);

			long pageResourcePrimKey =
				wikiPageResourceLocalService.getPageResourcePrimKey(
					nodeId, title);

			long classNameId = PortalUtil.getClassNameId(
				WikiPage.class.getName());
			long classPK = pageResourcePrimKey;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}

		return null;
	}

	protected AssetEntryDisplay[] getEntryDisplays(
			List<AssetEntry> entries, String languageId)
		throws SystemException {

		AssetEntryDisplay[] entryDisplays =
			new AssetEntryDisplay[entries.size()];

		for (int i = 0; i < entries.size(); i++) {
			AssetEntry entry = entries.get(i);

			String className = PortalUtil.getClassName(entry.getClassNameId());
			String portletId = PortalUtil.getClassNamePortletId(className);
			String portletTitle = PortalUtil.getPortletTitle(
				portletId, entry.getCompanyId(), languageId);

			List<AssetCategory> categories =
				assetEntryPersistence.getAssetCategories(entry.getEntryId());

			String categoryIdsString = ListUtil.toString(
				categories, "assetCategoryId", StringPool.COMMA);
			long[] categoryIds = StringUtil.split(
				categoryIdsString, StringPool.COMMA, 0L);

			List<AssetTag> tags = assetEntryPersistence.getAssetTags(
				entry.getEntryId());

			String tagNames = ListUtil.toString(tags, "name", ", ");

			AssetEntryDisplay entryDisplay = new AssetEntryDisplay();

			entryDisplay.setEntryId(entry.getEntryId());
			entryDisplay.setCompanyId(entry.getCompanyId());
			entryDisplay.setUserId(entry.getUserId());
			entryDisplay.setUserName(entry.getUserName());
			entryDisplay.setCreateDate(entry.getCreateDate());
			entryDisplay.setModifiedDate(entry.getModifiedDate());
			entryDisplay.setClassNameId(entry.getClassNameId());
			entryDisplay.setClassName(className);
			entryDisplay.setClassPK(entry.getClassPK());
			entryDisplay.setPortletId(portletId);
			entryDisplay.setPortletTitle(portletTitle);
			entryDisplay.setStartDate(entry.getStartDate());
			entryDisplay.setEndDate(entry.getEndDate());
			entryDisplay.setPublishDate(entry.getPublishDate());
			entryDisplay.setExpirationDate(entry.getExpirationDate());
			entryDisplay.setMimeType(entry.getMimeType());
			entryDisplay.setTitle(entry.getTitle());
			entryDisplay.setDescription(entry.getDescription());
			entryDisplay.setSummary(entry.getSummary());
			entryDisplay.setUrl(entry.getUrl());
			entryDisplay.setHeight(entry.getHeight());
			entryDisplay.setWidth(entry.getWidth());
			entryDisplay.setPriority(entry.getPriority());
			entryDisplay.setViewCount(entry.getViewCount());
			entryDisplay.setCategoryIds(categoryIds);
			entryDisplay.setTagNames(tagNames);

			entryDisplays[i] = entryDisplay;
		}

		return entryDisplays;
	}

	protected AssetEntryType getEntryType(String className, String languageId) {
		long companyId = PortalInstances.getDefaultCompanyId();

		long classNameId = PortalUtil.getClassNameId(className);

		String portletId = PortalUtil.getClassNamePortletId(className);
		String portletTitle = PortalUtil.getPortletTitle(
			portletId, companyId, languageId);

		AssetEntryType entryType = new AssetEntryType();

		entryType.setClassNameId(classNameId);
		entryType.setClassName(className);
		entryType.setPortletId(portletId);
		entryType.setPortletTitle(portletTitle);

		return entryType;
	}

	private static Log _log =
		LogFactoryUtil.getLog(AssetEntryLocalServiceImpl.class);

}