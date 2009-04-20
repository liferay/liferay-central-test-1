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
 * <a href="MBMessageFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFinderUtil {
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByG_U(groupId, userId);
	}

	public static int countByG_U_A(long groupId, long userId, boolean anonymous)
		throws com.liferay.portal.SystemException {
		return getFinder().countByG_U_A(groupId, userId, anonymous);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByNoAssets()
		throws com.liferay.portal.SystemException {
		return getFinder().findByNoAssets();
	}

	public static java.util.List<Long> findByG_U(long groupId, long userId,
		int start, int end) throws com.liferay.portal.SystemException {
		return getFinder().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getFinder().findByC_C(classNameId, classPK);
	}

	public static java.util.List<Long> findByG_U_A(long groupId, long userId,
		boolean anonymous, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByG_U_A(groupId, userId, anonymous, start, end);
	}

	public static MBMessageFinder getFinder() {
		return _finder;
	}

	public void setFinder(MBMessageFinder finder) {
		_finder = finder;
	}

	private static MBMessageFinder _finder;
}