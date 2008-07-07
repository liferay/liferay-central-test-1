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

/**
 * <a href="MBMessagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface MBMessagePersistence {
	public com.liferay.portlet.messageboards.model.MBMessage create(
		long messageId);

	public com.liferay.portlet.messageboards.model.MBMessage remove(
		long messageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage remove(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(MBMessage mbMessage, boolean merge)</code>.
	 */
	public com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        mbMessage the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when mbMessage is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portlet.messageboards.model.MBMessage update(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByPrimaryKey(
		long messageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage fetchByPrimaryKey(
		long messageId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByUuid_PrevAndNext(
		long messageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByCompanyId_PrevAndNext(
		long messageId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCategoryId(
		long categoryId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCategoryId(
		long categoryId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCategoryId(
		long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCategoryId_First(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCategoryId_Last(
		long categoryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByCategoryId_PrevAndNext(
		long messageId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadId_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadId_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByThreadId_PrevAndNext(
		long messageId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_T(
		long categoryId, long threadId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_T(
		long categoryId, long threadId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_T(
		long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_T_First(
		long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_T_Last(
		long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByC_T_PrevAndNext(
		long messageId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_P_First(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_P_Last(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByT_P_PrevAndNext(
		long messageId, long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException;

	public void removeByThreadId(long threadId)
		throws com.liferay.portal.SystemException;

	public void removeByC_T(long categoryId, long threadId)
		throws com.liferay.portal.SystemException;

	public void removeByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByCategoryId(long categoryId)
		throws com.liferay.portal.SystemException;

	public int countByThreadId(long threadId)
		throws com.liferay.portal.SystemException;

	public int countByC_T(long categoryId, long threadId)
		throws com.liferay.portal.SystemException;

	public int countByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;

	public void registerListener(
		com.liferay.portal.model.ModelListener listener);

	public void unregisterListener(
		com.liferay.portal.model.ModelListener listener);
}