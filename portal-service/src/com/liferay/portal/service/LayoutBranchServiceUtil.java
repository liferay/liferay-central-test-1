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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the layout branch remote service. This utility wraps {@link com.liferay.portal.service.impl.LayoutBranchServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutBranchServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchService
 * @see com.liferay.portal.service.base.LayoutBranchServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutBranchServiceImpl
 * @generated
 */
public class LayoutBranchServiceUtil {
	public static com.liferay.portal.model.LayoutBranch addLayoutBranch(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayoutBranch(groupId, name, description, serviceContext);
	}

	public static void deleteLayoutBranch(long groupId, long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutBranch(groupId, layoutBranchId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutBranch> getLayoutBranches(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutBranches(groupId);
	}

	public static com.liferay.portal.model.LayoutBranch updateLayoutBranch(
		long groupId, long layoutBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayoutBranch(groupId, layoutBranchId, name,
			description, serviceContext);
	}

	public static LayoutBranchService getService() {
		if (_service == null) {
			_service = (LayoutBranchService)PortalBeanLocatorUtil.locate(LayoutBranchService.class.getName());

			ReferenceRegistry.registerReference(LayoutBranchServiceUtil.class,
				"_service");
			MethodCache.remove(LayoutBranchService.class);
		}

		return _service;
	}

	public void setService(LayoutBranchService service) {
		MethodCache.remove(LayoutBranchService.class);

		_service = service;

		ReferenceRegistry.registerReference(LayoutBranchServiceUtil.class,
			"_service");
		MethodCache.remove(LayoutBranchService.class);
	}

	private static LayoutBranchService _service;
}