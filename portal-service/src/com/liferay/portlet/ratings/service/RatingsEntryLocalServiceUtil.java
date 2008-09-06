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

package com.liferay.portlet.ratings.service;


/**
 * <a href="RatingsEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.ratings.service.RatingsEntryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.ratings.service.RatingsEntryLocalService
 *
 */
public class RatingsEntryLocalServiceUtil {
	public static com.liferay.portlet.ratings.model.RatingsEntry addRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		return getService().addRatingsEntry(ratingsEntry);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry createRatingsEntry(
		long entryId) {
		return getService().createRatingsEntry(entryId);
	}

	public static void deleteRatingsEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteRatingsEntry(entryId);
	}

	public static void deleteRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		getService().deleteRatingsEntry(ratingsEntry);
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

	public static com.liferay.portlet.ratings.model.RatingsEntry getRatingsEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRatingsEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getRatingsEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getRatingsEntries(start, end);
	}

	public static int getRatingsEntriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getRatingsEntriesCount();
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateRatingsEntry(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry)
		throws com.liferay.portal.SystemException {
		return getService().updateRatingsEntry(ratingsEntry);
	}

	public static void deleteEntry(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteEntry(userId, className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry getEntry(
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getEntry(userId, className, classPK);
	}

	public static java.util.List<com.liferay.portlet.ratings.model.RatingsEntry> getEntries(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getEntries(className, classPK);
	}

	public static com.liferay.portlet.ratings.model.RatingsEntry updateEntry(
		long userId, java.lang.String className, long classPK, double score)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateEntry(userId, className, classPK, score);
	}

	public static RatingsEntryLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("RatingsEntryLocalService is not set");
		}

		return _service;
	}

	public void setService(RatingsEntryLocalService service) {
		_service = service;
	}

	private static RatingsEntryLocalService _service;
}