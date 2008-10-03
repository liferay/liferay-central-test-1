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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBThreadLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.messageboards.service.MBThreadLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBThreadLocalService
 *
 */
public class MBThreadLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBThread addMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.SystemException {
		return getService().addMBThread(mbThread);
	}

	public static com.liferay.portlet.messageboards.model.MBThread createMBThread(
		long threadId) {
		return getService().createMBThread(threadId);
	}

	public static void deleteMBThread(long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBThread(threadId);
	}

	public static void deleteMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.SystemException {
		getService().deleteMBThread(mbThread);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBThread getMBThread(
		long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBThread(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreads(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBThreads(start, end);
	}

	public static int getMBThreadsCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBThreadsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.SystemException {
		return getService().updateMBThread(mbThread);
	}

	public static void deleteThread(long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteThread(threadId);
	}

	public static void deleteThread(
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteThread(thread);
	}

	public static void deleteThreads(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteThreads(categoryId);
	}

	public static int getCategoriesThreadsCount(
		java.util.List<Long> categoryIds)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesThreadsCount(categoryIds);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupThreads(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getGroupThreads(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, boolean subscribed, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getGroupThreads(groupId, userId, subscribed, start, end);
	}

	public static int getGroupThreadsCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupThreadsCount(groupId);
	}

	public static int getGroupThreadsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupThreadsCount(groupId, userId);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed) throws com.liferay.portal.SystemException {
		return getService().getGroupThreadsCount(groupId, userId, subscribed);
	}

	public static com.liferay.portlet.messageboards.model.MBThread getThread(
		long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getThread(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> getThreads(
		long categoryId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getThreads(categoryId, start, end);
	}

	public static int getThreadsCount(long categoryId)
		throws com.liferay.portal.SystemException {
		return getService().getThreadsCount(categoryId);
	}

	public static boolean hasReadThread(long userId, long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().hasReadThread(userId, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread moveThread(
		long categoryId, long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().moveThread(categoryId, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread splitThread(
		long messageId, javax.portlet.PortletPreferences preferences,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().splitThread(messageId, preferences, themeDisplay);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateThread(
		long threadId, int viewCount)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateThread(threadId, viewCount);
	}

	public static MBThreadLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("MBThreadLocalService is not set");
		}

		return _service;
	}

	public void setService(MBThreadLocalService service) {
		_service = service;
	}

	private static MBThreadLocalService _service;
}