/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_C_N(groupId, classNameId, name);
	}

	public static int countByG_N_P(long groupId, java.lang.String name,
		java.lang.String[] tagProperties)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_N_P(groupId, name, tagProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByEntryId(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetTag findByG_N(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagException {
		return getFinder().findByG_N(groupId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_C_N(groupId, classNameId, name);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_C_N(
		long groupId, long classNameId, java.lang.String name, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_C_N(groupId, classNameId, name, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_P(
		long groupId, java.lang.String name, java.lang.String[] tagProperties)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_N_P(groupId, name, tagProperties);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> findByG_N_P(
		long groupId, java.lang.String name, java.lang.String[] tagProperties,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
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