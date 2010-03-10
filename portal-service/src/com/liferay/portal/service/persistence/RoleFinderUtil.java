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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="RoleFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class RoleFinderUtil {
	public static int countByR_U(long roleId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByR_U(roleId, userId);
	}

	public static int countByU_G_R(long userId, long groupId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByU_G_R(userId, groupId, roleId);
	}

	public static int countByC_N_D_T(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer[] types,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_N_D_T(companyId, name, description, types, params);
	}

	public static java.util.List<com.liferay.portal.model.Role> findBySystem(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findBySystem(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByUserGroupGroupRole(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserGroupGroupRole(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByUserGroupRole(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByUserGroupRole(userId, groupId);
	}

	public static com.liferay.portal.model.Role findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByC_N(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_G(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_G(userId, groupIds);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByU_G(userId, groups);
	}

	public static java.util.List<com.liferay.portal.model.Role> findByC_N_D_T(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer[] types,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByC_N_D_T(companyId, name, description, types, params,
			start, end, obc);
	}

	public static java.util.Map<String, java.util.List<String>> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByC_N_S_P(companyId, name, scope, primKey);
	}

	public static RoleFinder getFinder() {
		if (_finder == null) {
			_finder = (RoleFinder)PortalBeanLocatorUtil.locate(RoleFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(RoleFinder finder) {
		_finder = finder;
	}

	private static RoleFinder _finder;
}