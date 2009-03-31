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

package com.liferay.portlet.blogs.service.persistence;

/**
 * <a href="BlogsEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BlogsEntryUtil {
	public static void cacheResult(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry) {
		getPersistence().cacheResult(blogsEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> blogsEntries) {
		getPersistence().cacheResult(blogsEntries);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry remove(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(blogsEntry);
	}

	/**
	 * @deprecated Use <code>update(BlogsEntry blogsEntry, boolean merge)</code>.
	 */
	public static com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(blogsEntry);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        blogsEntry the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when blogsEntry is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(blogsEntry, merge);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(blogsEntry, merge);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUuid_PrevAndNext(entryId, uuid, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByGroupId_PrevAndNext(entryId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_PrevAndNext(entryId, groupId, userId, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_UT(groupId, urlTitle);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry fetchByG_UT(
		long groupId, java.lang.String urlTitle, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_UT(groupId, urlTitle, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_D(
		long groupId, java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_D_D(groupId, displayDate, draft);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_D(
		long groupId, java.util.Date displayDate, boolean draft, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_D_D(groupId, displayDate, draft, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D_D(
		long groupId, java.util.Date displayDate, boolean draft, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_D_D(groupId, displayDate, draft, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_D_First(
		long groupId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_D_First(groupId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_D_D_Last(
		long groupId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_D_Last(groupId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_D_D_PrevAndNext(
		long entryId, long groupId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_D_D_PrevAndNext(entryId, groupId, displayDate,
			draft, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_D(
		long companyId, java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_D_D(companyId, displayDate, draft);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_D(
		long companyId, java.util.Date displayDate, boolean draft, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_D_D(companyId, displayDate, draft, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D_D(
		long companyId, java.util.Date displayDate, boolean draft, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_D_D(companyId, displayDate, draft, start, end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_D_First(
		long companyId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_D_First(companyId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByC_D_D_Last(
		long companyId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_D_Last(companyId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByC_D_D_PrevAndNext(
		long entryId, long companyId, java.util.Date displayDate,
		boolean draft, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByC_D_D_PrevAndNext(entryId, companyId, displayDate,
			draft, obc);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_D(
		long groupId, long userId, java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_D(groupId, userId, displayDate, draft);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_D(
		long groupId, long userId, java.util.Date displayDate, boolean draft,
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_D(groupId, userId, displayDate, draft, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D_D(
		long groupId, long userId, java.util.Date displayDate, boolean draft,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_U_D_D(groupId, userId, displayDate, draft, start,
			end, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_D_First(
		long groupId, long userId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_D_First(groupId, userId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_D_Last(
		long groupId, long userId, java.util.Date displayDate, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_D_Last(groupId, userId, displayDate, draft, obc);
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_D_D_PrevAndNext(
		long entryId, long groupId, long userId, java.util.Date displayDate,
		boolean draft, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		return getPersistence()
				   .findByG_U_D_D_PrevAndNext(entryId, groupId, userId,
			displayDate, draft, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException {
		getPersistence().removeByG_UT(groupId, urlTitle);
	}

	public static void removeByG_D_D(long groupId, java.util.Date displayDate,
		boolean draft) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_D_D(groupId, displayDate, draft);
	}

	public static void removeByC_D_D(long companyId,
		java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_D_D(companyId, displayDate, draft);
	}

	public static void removeByG_U_D_D(long groupId, long userId,
		java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_U_D_D(groupId, userId, displayDate, draft);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	public static int countByG_D_D(long groupId, java.util.Date displayDate,
		boolean draft) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_D_D(groupId, displayDate, draft);
	}

	public static int countByC_D_D(long companyId, java.util.Date displayDate,
		boolean draft) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_D_D(companyId, displayDate, draft);
	}

	public static int countByG_U_D_D(long groupId, long userId,
		java.util.Date displayDate, boolean draft)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByG_U_D_D(groupId, userId, displayDate, draft);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static BlogsEntryPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(BlogsEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static BlogsEntryPersistence _persistence;
}