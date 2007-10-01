/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.EntryContentException;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.EntryTitleException;
import com.liferay.portlet.blogs.NoSuchCategoryException;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsCategoryImpl;
import com.liferay.portlet.blogs.service.base.BlogsEntryLocalServiceBaseImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsCategoryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryFinder;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.util.Indexer;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.lucene.HitsImpl;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

/**
 * <a href="BlogsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson S. Man
 *
 */
public class BlogsEntryLocalServiceImpl extends BlogsEntryLocalServiceBaseImpl {

	public BlogsEntry addEntry(
			long userId, long plid, long categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			String[] tagsEntries, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			tagsEntries, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public BlogsEntry addEntry(
			long userId, long plid, long categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			String[] tagsEntries, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addEntry(
			userId, plid, categoryId, title, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			tagsEntries, null, null, communityPermissions, guestPermissions);
	}

	public BlogsEntry addEntry(
			long userId, long plid, long categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			String[] tagsEntries, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		categoryId = getCategoryId(user.getCompanyId(), categoryId);
		Date now = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		long entryId = CounterLocalServiceUtil.increment();

		BlogsEntry entry = BlogsEntryUtil.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setCategoryId(categoryId);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);

		BlogsEntryUtil.update(entry);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addEntryResources(
				entry, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addEntryResources(entry, communityPermissions, guestPermissions);
		}

		// Tags

		updateTagsAsset(entry, tagsEntries);

		// Lucene

		try {
			Indexer.addEntry(
				entry.getCompanyId(), entry.getGroupId(), userId, categoryId,
				entryId, title, content);
		}
		catch (IOException ioe) {
			_log.error("Indexing " + entryId, ioe);
		}

		return entry;
	}

	public void addEntryResources(
			long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		addEntryResources(entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			BlogsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			BlogsEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		Iterator itr = BlogsEntryUtil.findByGroupId(groupId).iterator();

		while (itr.hasNext()) {
			BlogsEntry entry = (BlogsEntry)itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BlogsEntry entry)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteEntry(entry.getCompanyId(), entry.getEntryId());
		}
		catch (IOException ioe) {
			_log.error("Deleting index " + entry.getEntryId(), ioe);
		}

		// Tags

		TagsAssetLocalServiceUtil.deleteAsset(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Message boards

		MBMessageLocalServiceUtil.deleteDiscussionMessages(
			BlogsEntry.class.getName(), entry.getEntryId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			entry.getCompanyId(), BlogsEntry.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		BlogsEntryUtil.remove(entry.getEntryId());
	}

	public int getCategoriesEntriesCount(List categoryIds)
		throws SystemException {

		return BlogsEntryFinder.countByCategoryIds(categoryIds);
	}

	public List getCompanyEntries(
			long companyId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return BlogsEntryUtil.findByCompanyId(companyId, begin, end, obc);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return BlogsEntryUtil.countByCompanyId(companyId);
	}

	public List getEntries(long categoryId, int begin, int end)
		throws SystemException {

		return BlogsEntryUtil.findByCategoryId(categoryId, begin, end);
	}

	public int getEntriesCount(long categoryId) throws SystemException {
		return BlogsEntryUtil.countByCategoryId(categoryId);
	}

	public BlogsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return BlogsEntryUtil.findByPrimaryKey(entryId);
	}

	public List getGroupEntries(long groupId, int begin, int end)
		throws SystemException {

		return BlogsEntryUtil.findByGroupId(groupId, begin, end);
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return BlogsEntryUtil.countByGroupId(groupId);
	}

	public List getNoAssetEntries() throws SystemException {
		return BlogsEntryFinder.findByNoAssets();
	}

	public void reIndex(String[] ids) throws SystemException {
		try {
			long companyId = GetterUtil.getLong(ids[0]);

			Iterator itr = BlogsEntryUtil.findByCompanyId(companyId).iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				long groupId = entry.getGroupId();
				long userId = entry.getUserId();
				long categoryId = entry.getCategoryId();
				long entryId = entry.getEntryId();
				String title = entry.getTitle();
				String content = entry.getContent();

				try {
					Indexer.addEntry(
						companyId, groupId, userId, categoryId, entryId, title,
						content);
				}
				catch (Exception e1) {
					_log.error("Reindexing " + entryId, e1);
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public Hits search(
			long companyId, long groupId, long userId, long[] categoryIds,
			String keywords)
		throws SystemException {

		Searcher searcher = null;

		try {
			HitsImpl hits = new HitsImpl();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				LuceneUtil.addRequiredTerm(
					contextQuery, LuceneFields.GROUP_ID, groupId);
			}

			if (userId > 0) {
				LuceneUtil.addRequiredTerm(
					contextQuery, LuceneFields.USER_ID, userId);
			}

			if ((categoryIds != null) && (categoryIds.length > 0)) {
				BooleanQuery categoryIdsQuery = new BooleanQuery();

				for (int i = 0; i < categoryIds.length; i++) {
					Term term = new Term(
						"categoryId", String.valueOf(categoryIds[i]));
					TermQuery termQuery = new TermQuery(term);

					categoryIdsQuery.add(termQuery, BooleanClause.Occur.SHOULD);
				}

				contextQuery.add(categoryIdsQuery, BooleanClause.Occur.MUST);
			}

			BooleanQuery searchQuery = new BooleanQuery();

			LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);
			fullQuery.add(searchQuery, BooleanClause.Occur.MUST);

			searcher = LuceneUtil.getSearcher(companyId);

			hits.recordHits(searcher.search(fullQuery), searcher);

			return hits;
		}
		catch (Exception e) {
			return LuceneUtil.closeSearcher(searcher, keywords, e);
		}
	}

	public BlogsEntry updateEntry(
			long userId, long entryId, long categoryId, String title,
			String content, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			String[] tagsEntries)
		throws PortalException, SystemException {

		// Entry

		User user = UserUtil.findByPrimaryKey(userId);
		categoryId = getCategoryId(user.getCompanyId(), categoryId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			new EntryDisplayDateException());

		validate(title, content);

		BlogsEntry entry = BlogsEntryUtil.findByPrimaryKey(entryId);

		entry.setModifiedDate(new Date());
		entry.setCategoryId(categoryId);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setDisplayDate(displayDate);

		BlogsEntryUtil.update(entry);

		// Tags

		updateTagsAsset(entry, tagsEntries);

		// Lucene

		try {
			Indexer.updateEntry(
				entry.getCompanyId(), entry.getGroupId(), userId, categoryId,
				entryId, title, content);
		}
		catch (IOException ioe) {
			_log.error("Indexing " + entryId, ioe);
		}

		return entry;
	}

	public void updateTagsAsset(BlogsEntry entry, String[] tagsEntries)
		throws PortalException, SystemException {

		TagsAssetLocalServiceUtil.updateAsset(
			entry.getUserId(), BlogsEntry.class.getName(),
			entry.getEntryId(), tagsEntries, null, null, null, null,
			ContentTypes.TEXT_HTML, entry.getTitle(), entry.getTitle(),
			entry.getTitle(), null, 0, 0);
	}

	protected long getCategoryId(long companyId, long categoryId)
		throws PortalException, SystemException {

		if (categoryId != BlogsCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {

			// Ensure category exists and belongs to the proper company

			try {
				BlogsCategory category =
					BlogsCategoryUtil.findByPrimaryKey(categoryId);

				if (companyId != category.getCompanyId()) {
					categoryId = BlogsCategoryImpl.DEFAULT_PARENT_CATEGORY_ID;
				}
			}
			catch (NoSuchCategoryException nsfe) {
				categoryId = BlogsCategoryImpl.DEFAULT_PARENT_CATEGORY_ID;
			}
		}

		return categoryId;
	}

	protected void validate(String title, String content)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new EntryContentException();
		}
	}

	private static Log _log =
		LogFactory.getLog(BlogsEntryLocalServiceImpl.class);

}