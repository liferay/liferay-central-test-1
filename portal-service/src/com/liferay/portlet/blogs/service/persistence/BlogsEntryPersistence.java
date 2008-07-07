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

package com.liferay.portlet.blogs.service.persistence;

/**
 * <a href="BlogsEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface BlogsEntryPersistence {
	public com.liferay.portlet.blogs.model.BlogsEntry create(long entryId);

	public com.liferay.portlet.blogs.model.BlogsEntry remove(long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry remove(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(BlogsEntry blogsEntry, boolean merge)</code>.
	 */
	public com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry)
		throws com.liferay.portal.SystemException;

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
	public com.liferay.portlet.blogs.model.BlogsEntry update(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry updateImpl(
		com.liferay.portlet.blogs.model.BlogsEntry blogsEntry, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_PrevAndNext(
		long entryId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, boolean draft) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_D(
		long groupId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_D_First(
		long groupId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_D_Last(
		long groupId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByG_D_PrevAndNext(
		long entryId, long groupId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, boolean draft)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByC_D(
		long companyId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByC_D_First(
		long companyId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByC_D_Last(
		long companyId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByC_D_PrevAndNext(
		long entryId, long companyId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_UT(long groupId,
		java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry fetchByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, boolean draft)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, boolean draft, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByG_U_D(
		long groupId, long userId, boolean draft, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_First(
		long groupId, long userId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry findByG_U_D_Last(
		long groupId, long userId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public com.liferay.portlet.blogs.model.BlogsEntry[] findByG_U_D_PrevAndNext(
		long entryId, long groupId, long userId, boolean draft,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeByG_D(long groupId, boolean draft)
		throws com.liferay.portal.SystemException;

	public void removeByC_D(long companyId, boolean draft)
		throws com.liferay.portal.SystemException;

	public void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.blogs.NoSuchEntryException;

	public void removeByG_U_D(long groupId, long userId, boolean draft)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countByG_D(long groupId, boolean draft)
		throws com.liferay.portal.SystemException;

	public int countByC_D(long companyId, boolean draft)
		throws com.liferay.portal.SystemException;

	public int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException;

	public int countByG_U_D(long groupId, long userId, boolean draft)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}