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

/**
 * <a href="MBMessageFlagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    MBMessageFlagPersistence
 * @see    MBMessageFlagPersistenceImpl
 */
public class MBMessageFlagUtil {
	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag) {
		getPersistence().cacheResult(mbMessageFlag);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> mbMessageFlags) {
		getPersistence().cacheResult(mbMessageFlags);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag create(
		long messageFlagId) {
		return getPersistence().create(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		long messageFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().remove(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(mbMessageFlag);
	}

	/**
	 * @deprecated Use {@link #update(MBMessageFlag, boolean merge)}.
	 */
	public static com.liferay.portlet.messageboards.model.MBMessageFlag update(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(mbMessageFlag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag update(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(mbMessageFlag, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(mbMessageFlag, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByPrimaryKey(messageFlagId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByPrimaryKey(
		long messageFlagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(messageFlagId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByUserId_PrevAndNext(
		long messageFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByUserId_PrevAndNext(messageFlagId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId) throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByThreadId(threadId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_First(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByThreadId_Last(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByThreadId_PrevAndNext(
		long messageFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(messageFlagId, threadId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId) throws com.liferay.portal.SystemException {
		return getPersistence().findByMessageId(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByMessageId(messageId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByMessageId(messageId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_First(
		long messageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByMessageId_First(messageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_Last(
		long messageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByMessageId_Last(messageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByMessageId_PrevAndNext(
		long messageFlagId, long messageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByMessageId_PrevAndNext(messageFlagId, messageId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag) throws com.liferay.portal.SystemException {
		return getPersistence().findByT_F(threadId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_F(threadId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByT_F(threadId, flag, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_First(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByT_F_First(threadId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_Last(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByT_F_Last(threadId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_F_PrevAndNext(
		long messageFlagId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByT_F_PrevAndNext(messageFlagId, threadId, flag, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag) throws com.liferay.portal.SystemException {
		return getPersistence().findByM_F(messageId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByM_F(messageId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByM_F(messageId, flag, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_First(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByM_F_First(messageId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_Last(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByM_F_Last(messageId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByM_F_PrevAndNext(
		long messageFlagId, long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByM_F_PrevAndNext(messageFlagId, messageId, flag, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByU_T_F(userId, threadId, flag, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByU_T_F(userId, threadId, flag, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_First(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByU_T_F_First(userId, threadId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_Last(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByU_T_F_Last(userId, threadId, flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag[] findByU_T_F_PrevAndNext(
		long messageFlagId, long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence()
				   .findByU_T_F_PrevAndNext(messageFlagId, userId, threadId,
			flag, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag findByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		return getPersistence().findByU_M_F(userId, messageId, flag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_M_F(userId, messageId, flag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByU_M_F(userId, messageId, flag, retrieveFromCache);
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

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	public static void removeByMessageId(long messageId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByMessageId(messageId);
	}

	public static void removeByT_F(long threadId, int flag)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByT_F(threadId, flag);
	}

	public static void removeByM_F(long messageId, int flag)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByM_F(messageId, flag);
	}

	public static void removeByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByU_T_F(userId, threadId, flag);
	}

	public static void removeByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException {
		getPersistence().removeByU_M_F(userId, messageId, flag);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByThreadId(long threadId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	public static int countByMessageId(long messageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByMessageId(messageId);
	}

	public static int countByT_F(long threadId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_F(threadId, flag);
	}

	public static int countByM_F(long messageId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByM_F(messageId, flag);
	}

	public static int countByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_T_F(userId, threadId, flag);
	}

	public static int countByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_M_F(userId, messageId, flag);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MBMessageFlagPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(MBMessageFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static MBMessageFlagPersistence _persistence;
}