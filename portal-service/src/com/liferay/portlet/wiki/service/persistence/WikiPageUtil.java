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

package com.liferay.portlet.wiki.service.persistence;

/**
 * <a href="WikiPageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    WikiPagePersistence
 * @see    WikiPagePersistenceImpl
 */
public class WikiPageUtil {
	public static void cacheResult(
		com.liferay.portlet.wiki.model.WikiPage wikiPage) {
		getPersistence().cacheResult(wikiPage);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPage> wikiPages) {
		getPersistence().cacheResult(wikiPages);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.wiki.model.WikiPage create(long pageId) {
		return getPersistence().create(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage remove(long pageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().remove(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage remove(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(wikiPage);
	}

	/**
	 * @deprecated Use {@link #update(WikiPage, boolean merge)}.
	 */
	public static com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(wikiPage);
	}

	public static com.liferay.portlet.wiki.model.WikiPage update(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(wikiPage, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPage updateImpl(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(wikiPage, merge);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByPrimaryKey(
		long pageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByPrimaryKey(pageId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByPrimaryKey(
		long pageId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(pageId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByUuid_PrevAndNext(
		long pageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUuid_PrevAndNext(pageId, uuid, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId) throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByNodeId(nodeId, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_First(
		long nodeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_First(nodeId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByNodeId_Last(
		long nodeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_Last(nodeId, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByNodeId_PrevAndNext(
		long pageId, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByNodeId_PrevAndNext(pageId, nodeId, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format) throws com.liferay.portal.SystemException {
		return getPersistence().findByFormat(format);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFormat(format, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByFormat(format, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_First(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_First(format, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByFormat_Last(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_Last(format, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByFormat_PrevAndNext(
		long pageId, java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByFormat_PrevAndNext(pageId, format, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T(nodeId, title, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_First(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_First(nodeId, title, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_Last(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_Last(nodeId, title, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_PrevAndNext(
		long pageId, long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_PrevAndNext(pageId, nodeId, title, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head) throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H(nodeId, head, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_First(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_First(nodeId, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_Last(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_Last(nodeId, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_PrevAndNext(
		long pageId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_PrevAndNext(pageId, nodeId, head, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_P(nodeId, parentTitle, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_First(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_P_First(nodeId, parentTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_P_Last(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_P_Last(nodeId, parentTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_P_PrevAndNext(
		long pageId, long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_P_PrevAndNext(pageId, nodeId, parentTitle, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_R(nodeId, redirectTitle, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_First(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_R_First(nodeId, redirectTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_R_Last(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_R_Last(nodeId, redirectTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_R_PrevAndNext(
		long pageId, long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_R_PrevAndNext(pageId, nodeId, redirectTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_V(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByN_T_V(nodeId, title, version);
	}

	public static com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(
		long nodeId, java.lang.String title, double version,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByN_T_V(nodeId, title, version, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_T_H(nodeId, title, head, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_First(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_H_First(nodeId, title, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_T_H_Last(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_T_H_Last(nodeId, title, head, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_T_H_PrevAndNext(
		long pageId, long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_T_H_PrevAndNext(pageId, nodeId, title, head, obc);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByN_H_P(nodeId, head, parentTitle);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByN_H_P(nodeId, head, parentTitle, start, end, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_First(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_P_First(nodeId, head, parentTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByN_H_P_Last(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence().findByN_H_P_Last(nodeId, head, parentTitle, obc);
	}

	public static com.liferay.portlet.wiki.model.WikiPage[] findByN_H_P_PrevAndNext(
		long pageId, long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		return getPersistence()
				   .findByN_H_P_PrevAndNext(pageId, nodeId, head, parentTitle,
			obc);
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

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
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
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByNodeId(long nodeId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByNodeId(nodeId);
	}

	public static void removeByFormat(java.lang.String format)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByFormat(format);
	}

	public static void removeByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_T(nodeId, title);
	}

	public static void removeByN_H(long nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_H(nodeId, head);
	}

	public static void removeByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_P(nodeId, parentTitle);
	}

	public static void removeByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByN_R(nodeId, redirectTitle);
	}

	public static void removeByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException {
		getPersistence().removeByN_T_V(nodeId, title, version);
	}

	public static void removeByN_T_H(long nodeId, java.lang.String title,
		boolean head) throws com.liferay.portal.SystemException {
		getPersistence().removeByN_T_H(nodeId, title, head);
	}

	public static void removeByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) throws com.liferay.portal.SystemException {
		getPersistence().removeByN_H_P(nodeId, head, parentTitle);
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

	public static int countByNodeId(long nodeId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByNodeId(nodeId);
	}

	public static int countByFormat(java.lang.String format)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByFormat(format);
	}

	public static int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T(nodeId, title);
	}

	public static int countByN_H(long nodeId, boolean head)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_H(nodeId, head);
	}

	public static int countByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_P(nodeId, parentTitle);
	}

	public static int countByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByN_R(nodeId, redirectTitle);
	}

	public static int countByN_T_V(long nodeId, java.lang.String title,
		double version) throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T_V(nodeId, title, version);
	}

	public static int countByN_T_H(long nodeId, java.lang.String title,
		boolean head) throws com.liferay.portal.SystemException {
		return getPersistence().countByN_T_H(nodeId, title, head);
	}

	public static int countByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) throws com.liferay.portal.SystemException {
		return getPersistence().countByN_H_P(nodeId, head, parentTitle);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static WikiPagePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(WikiPagePersistence persistence) {
		_persistence = persistence;
	}

	private static WikiPagePersistence _persistence;
}