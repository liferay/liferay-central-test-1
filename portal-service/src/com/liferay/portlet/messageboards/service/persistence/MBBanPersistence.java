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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="MBBanPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
@Transactional
public interface MBBanPersistence {
	public com.liferay.portlet.messageboards.model.MBBan create(long banId);

	public com.liferay.portlet.messageboards.model.MBBan remove(long banId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	public com.liferay.portlet.messageboards.model.MBBan remove(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(MBBan mbBan, boolean merge)</code>.
	 */
	public com.liferay.portlet.messageboards.model.MBBan update(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbBan the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbBan is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.messageboards.model.MBBan update(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBBan updateImpl(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByPrimaryKey(
		long banId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	public com.liferay.portlet.messageboards.model.MBBan fetchByPrimaryKey(
		long banId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan[] findByGroupId_PrevAndNext(
		long banId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan[] findByUserId_PrevAndNext(
		long banId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findByBanUserId(
		long banUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByBanUserId_First(
		long banUserId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByBanUserId_Last(
		long banUserId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan[] findByBanUserId_PrevAndNext(
		long banId, long banUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBBan findByG_B(
		long groupId, long banUserId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	public com.liferay.portlet.messageboards.model.MBBan fetchByG_B(
		long groupId, long banUserId) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBBan> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByBanUserId(long banUserId)
		throws com.liferay.portal.SystemException;

	public void removeByG_B(long groupId, long banUserId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchBanException;

	public void removeAll() throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByBanUserId(long banUserId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countByG_B(long groupId, long banUserId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}