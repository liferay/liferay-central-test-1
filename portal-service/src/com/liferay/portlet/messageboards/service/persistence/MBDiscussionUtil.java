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

package com.liferay.portlet.messageboards.service.persistence;

public class MBDiscussionUtil {
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion) {
		getPersistence().cacheResult(mbDiscussion);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> mbDiscussions) {
		getPersistence().cacheResult(mbDiscussions);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion create(
		long discussionId) {
		return getPersistence().create(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion remove(
		long discussionId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().remove(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion remove(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(mbDiscussion);
	}

	/**
	 * @deprecated Use <code>update(MBDiscussion mbDiscussion, boolean merge)</code>.
	 */
	public static com.liferay.portlet.messageboards.model.MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(mbDiscussion);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbDiscussion the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbDiscussion is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.messageboards.model.MBDiscussion update(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(mbDiscussion, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion updateImpl(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(mbDiscussion, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByPrimaryKey(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByPrimaryKey(
		long discussionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(discussionId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByClassNameId(classNameId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByClassNameId_First(classNameId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByClassNameId_Last(classNameId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion[] findByClassNameId_PrevAndNext(
		long discussionId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence()
				   .findByClassNameId_PrevAndNext(discussionId, classNameId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByThreadId(
		long threadId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByThreadId(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByThreadId(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByThreadId(threadId, retrieveFromCache);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
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

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByClassNameId(classNameId);
	}

	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		getPersistence().removeByThreadId(threadId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByClassNameId(long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByClassNameId(classNameId);
	}

	public static int countByThreadId(long threadId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MBDiscussionPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(MBDiscussionPersistence persistence) {
		_persistence = persistence;
	}

	private static MBDiscussionPersistence _persistence;
}