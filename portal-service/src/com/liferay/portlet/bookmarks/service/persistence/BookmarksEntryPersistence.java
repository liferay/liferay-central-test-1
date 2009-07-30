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

package com.liferay.portlet.bookmarks.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * <a href="BookmarksEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    BookmarksEntryPersistenceImpl
 * @see    BookmarksEntryUtil
 */
public interface BookmarksEntryPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> bookmarksEntries);

	public void clearCache();

	public com.liferay.portlet.bookmarks.model.BookmarksEntry create(
		long entryId);

	public com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry remove(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use {@link #update(BookmarksEntry, boolean merge)}.
	 */
	public com.liferay.portlet.bookmarks.model.BookmarksEntry update(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry update(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksEntry bookmarksEntry,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByFolderId(
		long folderId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByFolderId(
		long folderId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByFolderId(
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_First(
		long folderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByFolderId_Last(
		long folderId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByFolderId_PrevAndNext(
		long entryId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public com.liferay.portlet.bookmarks.model.BookmarksEntry[] findByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.bookmarks.NoSuchEntryException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByFolderId(long folderId)
		throws com.liferay.portal.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByFolderId(long folderId)
		throws com.liferay.portal.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}