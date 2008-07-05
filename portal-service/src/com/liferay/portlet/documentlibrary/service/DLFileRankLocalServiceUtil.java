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

package com.liferay.portlet.documentlibrary.service;


/**
 * <a href="DLFileRankLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.documentlibrary.service.DLFileRankLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.documentlibrary.service.DLFileRankLocalService
 * @see com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceFactory
 *
 */
public class DLFileRankLocalServiceUtil {
	public static com.liferay.portlet.documentlibrary.model.DLFileRank addDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.addDLFileRank(dlFileRank);
	}

	public static void deleteDLFileRank(long fileRankId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		dlFileRankLocalService.deleteDLFileRank(fileRankId);
	}

	public static void deleteDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		dlFileRankLocalService.deleteDLFileRank(dlFileRank);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank getDLFileRank(
		long fileRankId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.getDLFileRank(fileRankId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateDLFileRank(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.updateDLFileRank(dlFileRank);
	}

	public static void init() {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		dlFileRankLocalService.init();
	}

	public static void deleteFileRanks(long userId)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		dlFileRankLocalService.deleteFileRanks(userId);
	}

	public static void deleteFileRanks(long folderId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		dlFileRankLocalService.deleteFileRanks(folderId, name);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId) throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.getFileRanks(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileRank> getFileRanks(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.getFileRanks(groupId, userId, start, end);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long folderId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		DLFileRankLocalService dlFileRankLocalService = DLFileRankLocalServiceFactory.getService();

		return dlFileRankLocalService.updateFileRank(groupId, companyId,
			userId, folderId, name);
	}
}