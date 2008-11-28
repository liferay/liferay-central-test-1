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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.util.Indexer;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BookmarksEntryLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceBaseImpl {

	public BookmarksEntry addEntry(
			long userId, long folderId, String name, String url,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addEntry(
			null, userId, folderId, name, url, comments, serviceContext);
	}

	public BookmarksEntry addEntry(
			String uuid, long userId, long folderId, String name, String url,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		BookmarksFolder folder =
			bookmarksFolderPersistence.findByPrimaryKey(folderId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		long entryId = counterLocalService.increment();

		BookmarksEntry entry = bookmarksEntryPersistence.create(entryId);

		entry.setUuid(uuid);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setFolderId(folderId);
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);

		bookmarksEntryPersistence.update(entry, false);

		// Resources

		if ((serviceContext.getAddCommunityPermissions() != null) &&
			(serviceContext.getAddGuestPermissions() != null)) {

			addEntryResources(
				folder, entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				folder, entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Tags

		updateTagsAsset(userId, entry, serviceContext.getTagsEntries());

		// Indexer

		try {
			Indexer.addEntry(
				entry.getCompanyId(), folder.getGroupId(), folderId, entryId,
				name, url, comments, serviceContext.getTagsEntries(),
				entry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + entryId, se);
		}

		return entry;
	}

	public void addEntryResources(
			long folderId, long entryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder =
			bookmarksFolderPersistence.findByPrimaryKey(folderId);
		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(
			folder, entry, addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			BookmarksFolder folder, BookmarksEntry entry,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), folder.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			long folderId, long entryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		BookmarksFolder folder =
			bookmarksFolderPersistence.findByPrimaryKey(folderId);
		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		addEntryResources(
			folder, entry, communityPermissions, guestPermissions);
	}

	public void addEntryResources(
			BookmarksFolder folder, BookmarksEntry entry,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), folder.getGroupId(), entry.getUserId(),
			BookmarksEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntries(long folderId)
		throws PortalException, SystemException {

		Iterator<BookmarksEntry> itr = bookmarksEntryPersistence.findByFolderId(
			folderId).iterator();

		while (itr.hasNext()) {
			BookmarksEntry entry = itr.next();

			deleteEntry(entry);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(BookmarksEntry entry)
		throws PortalException, SystemException {

		// Indexer

		try {
			Indexer.deleteEntry(entry.getCompanyId(), entry.getEntryId());
		}
		catch (SearchException se) {
			_log.error("Deleting index " + entry.getEntryId(), se);
		}

		// Tags

		tagsAssetLocalService.deleteAsset(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		bookmarksEntryPersistence.remove(entry);
	}

	public List<BookmarksEntry> getEntries(long folderId, int start, int end)
		throws SystemException {

		return bookmarksEntryPersistence.findByFolderId(folderId, start, end);
	}

	public List<BookmarksEntry> getEntries(
			long folderId, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return bookmarksEntryPersistence.findByFolderId(
			folderId, start, end, orderByComparator);
	}

	public int getEntriesCount(long folderId) throws SystemException {
		return bookmarksEntryPersistence.countByFolderId(folderId);
	}

	public BookmarksEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return bookmarksEntryPersistence.findByPrimaryKey(entryId);
	}

	public int getFoldersEntriesCount(List<Long> folderIds)
		throws SystemException {

		return bookmarksEntryFinder.countByFolderIds(folderIds);
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws SystemException {

		return bookmarksEntryFinder.findByGroupId(groupId, start, end);
	}

	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		if (userId <= 0) {
			return bookmarksEntryFinder.findByGroupId(groupId, start, end);
		}
		else {
			return bookmarksEntryFinder.findByG_U(groupId, userId, start, end);
		}
	}

	public int getGroupEntriesCount(long groupId) throws SystemException {
		return bookmarksEntryFinder.countByGroupId(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return bookmarksEntryFinder.countByGroupId(groupId);
		}
		else {
			return bookmarksEntryFinder.countByG_U(groupId, userId);
		}
	}

	public List<BookmarksEntry> getNoAssetEntries() throws SystemException {
		return bookmarksEntryFinder.findByNoAssets();
	}

	public BookmarksEntry openEntry(long entryId)
		throws PortalException, SystemException {

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		entry.setVisits(entry.getVisits() + 1);

		bookmarksEntryPersistence.update(entry, false);

		return entry;
	}

	public void reIndex(long entryId) throws SystemException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		BookmarksEntry entry = bookmarksEntryPersistence.fetchByPrimaryKey(
			entryId);

		if (entry == null) {
			return;
		}

		BookmarksFolder folder = bookmarksFolderPersistence.fetchByPrimaryKey(
			entry.getFolderId());

		long companyId = folder.getCompanyId();
		long groupId = folder.getGroupId();
		long folderId = folder.getFolderId();
		String name = entry.getName();
		String url = entry.getUrl();
		String comments = entry.getComments();

		String[] tagsEntries = tagsEntryLocalService.getEntryNames(
			BookmarksEntry.class.getName(), entryId);

		try {
			Indexer.updateEntry(
				companyId, groupId, folderId, entryId, name, url,
				comments, tagsEntries, entry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Reindexing " + entryId, se);
		}
	}

	public BookmarksEntry updateEntry(
			long userId, long entryId, long folderId, String name, String url,
			String comments, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		BookmarksEntry entry =
			bookmarksEntryPersistence.findByPrimaryKey(entryId);

		BookmarksFolder folder = getFolder(entry, folderId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		entry.setModifiedDate(new Date());
		entry.setFolderId(folder.getFolderId());
		entry.setName(name);
		entry.setUrl(url);
		entry.setComments(comments);

		bookmarksEntryPersistence.update(entry, false);

		// Tags

		updateTagsAsset(userId, entry, serviceContext.getTagsEntries());

		// Indexer

		try {
			Indexer.updateEntry(
				entry.getCompanyId(), folder.getGroupId(), entry.getFolderId(),
				entry.getEntryId(), name, url, comments,
				serviceContext.getTagsEntries(), entry.getExpandoBridge());
		}
		catch (SearchException se) {
			_log.error("Indexing " + entryId, se);
		}

		return entry;
	}

	public void updateTagsAsset(
			long userId, BookmarksEntry entry, String[] tagsEntries)
		throws PortalException, SystemException {

		tagsAssetLocalService.updateAsset(
			userId, entry.getFolder().getGroupId(),
			BookmarksEntry.class.getName(), entry.getEntryId(), null,
			tagsEntries, null, null, null, null, ContentTypes.TEXT_PLAIN,
			entry.getName(), entry.getComments(), null, entry.getUrl(), 0, 0,
			null, false);
	}

	protected BookmarksFolder getFolder(BookmarksEntry entry, long folderId)
		throws PortalException, SystemException {

		if (entry.getFolderId() != folderId) {
			BookmarksFolder oldFolder =
				bookmarksFolderPersistence.findByPrimaryKey(
					entry.getFolderId());

			BookmarksFolder newFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(oldFolder.getGroupId() != newFolder.getGroupId())) {

				folderId = entry.getFolderId();
			}
		}

		return bookmarksFolderPersistence.findByPrimaryKey(folderId);
	}

	protected void validate(String url) throws PortalException {
		if (Validator.isNull(url)) {
			throw new EntryURLException();
		}
		else {
			try {
				new URL(url);
			}
			catch (MalformedURLException murle) {
				throw new EntryURLException();
			}
		}
	}

	private static Log _log =
		LogFactory.getLog(BookmarksEntryLocalServiceImpl.class);

}