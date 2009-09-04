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

package com.liferay.portlet.documentlibrary.service.persistence;

/**
 * <a href="DLFolderFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFolderFinderUtil {
	public static int countFE_FS_ByG_F(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return getFinder().countFE_FS_ByG_F(groupId, folderIds);
	}

	public static int countF_FE_FS_ByG_F(long groupId,
		java.util.List<Long> folderIds)
		throws com.liferay.portal.SystemException {
		return getFinder().countF_FE_FS_ByG_F(groupId, folderIds);
	}

	public static java.util.List<Object> findFE_FS_ByG_F(long groupId,
		java.util.List<Long> folderIds, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findFE_FS_ByG_F(groupId, folderIds, start, end);
	}

	public static java.util.List<Object> findF_FE_FS_ByG_F(long groupId,
		java.util.List<Long> folderIds, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findF_FE_FS_ByG_F(groupId, folderIds, start, end);
	}

	public static DLFolderFinder getFinder() {
		return _finder;
	}

	public void setFinder(DLFolderFinder finder) {
		_finder = finder;
	}

	private static DLFolderFinder _finder;
}