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

package com.liferay.portlet.bookmarks.service;


/**
 * <a href="BookmarksFolderLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link BookmarksFolderLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolderLocalService
 * @generated
 */
public class BookmarksFolderLocalServiceWrapper
	implements BookmarksFolderLocalService {
	public BookmarksFolderLocalServiceWrapper(
		BookmarksFolderLocalService bookmarksFolderLocalService) {
		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.addBookmarksFolder(bookmarksFolder);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder createBookmarksFolder(
		long folderId) {
		return _bookmarksFolderLocalService.createBookmarksFolder(folderId);
	}

	public void deleteBookmarksFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.deleteBookmarksFolder(folderId);
	}

	public void deleteBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.deleteBookmarksFolder(bookmarksFolder);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getBookmarksFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getBookmarksFolders(
		int start, int end) throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFolders(start, end);
	}

	public int getBookmarksFoldersCount()
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getBookmarksFoldersCount();
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.updateBookmarksFolder(bookmarksFolder);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateBookmarksFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder,
		boolean merge) throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.updateBookmarksFolder(bookmarksFolder,
			merge);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long userId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.addFolder(userId, parentFolderId,
			name, description, serviceContext);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		java.lang.String uuid, long userId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.addFolder(uuid, userId,
			parentFolderId, name, description, serviceContext);
	}

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.addFolderResources(folder,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.addFolderResources(folder,
			communityPermissions, guestPermissions);
	}

	public void addFolderResources(long folderId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.addFolderResources(folderId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFolderResources(long folderId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.addFolderResources(folderId,
			communityPermissions, guestPermissions);
	}

	public void deleteFolder(
		com.liferay.portlet.bookmarks.model.BookmarksFolder folder)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.deleteFolder(folder);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.deleteFolder(folderId);
	}

	public void deleteFolders(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.deleteFolders(groupId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId) throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getFolders(groupId, parentFolderId,
			start, end);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.getFoldersCount(groupId,
			parentFolderId);
	}

	public void getSubfolderIds(java.util.List<Long> folderIds, long groupId,
		long folderId) throws com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.getSubfolderIds(folderIds, groupId,
			folderId);
	}

	public void reIndex(java.lang.String[] ids)
		throws com.liferay.portal.SystemException {
		_bookmarksFolderLocalService.reIndex(ids);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, long[] folderIds, java.lang.String keywords,
		int start, int end) throws com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.search(companyId, groupId, userId,
			folderIds, keywords, start, end);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _bookmarksFolderLocalService.updateFolder(folderId,
			parentFolderId, name, description, mergeWithParentFolder,
			serviceContext);
	}

	public BookmarksFolderLocalService getWrappedBookmarksFolderLocalService() {
		return _bookmarksFolderLocalService;
	}

	private BookmarksFolderLocalService _bookmarksFolderLocalService;
}