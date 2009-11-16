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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetTagFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagFinderUtil {
	public static int countByG_C_N(long groupId, long classNameId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static int countByG_N_P(long groupId, java.lang.String name,
		java.lang.String[] tagProperties)
		throws com.liferay.portal.SystemException {
		return getFinder().countByG_N_P(groupId, name, tagProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByEntryId(
		long entryId) throws com.liferay.portal.SystemException {
		return getFinder().findByEntryId(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetTag findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getFinder().findByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getFinder().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getFinder().findByG_C_N(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.SystemException {
		return getFinder().findByG_C_N(groupId, classNameId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_P(
		long groupId, java.lang.String name, java.lang.String[] tagProperties)
		throws com.liferay.portal.SystemException {
		return getFinder().findByG_N_P(groupId, name, tagProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_P(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end) throws com.liferay.portal.SystemException {
		return getFinder().findByG_N_P(groupId, name, tagProperties, start, end);
	}

	public static AssetTagFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetTagFinder)PortalBeanLocatorUtil.locate(AssetTagFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetTagFinder finder) {
		_finder = finder;
	}

	private static AssetTagFinder _finder;
}