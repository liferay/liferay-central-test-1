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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCFrameworkVersionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCFrameworkVersionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionService
 * @generated
 */
public class SCFrameworkVersionServiceWrapper
	implements SCFrameworkVersionService {
	public SCFrameworkVersionServiceWrapper(
		SCFrameworkVersionService scFrameworkVersionService) {
		_scFrameworkVersionService = scFrameworkVersionService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionService.addFrameworkVersion(name, url,
			active, priority, serviceContext);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scFrameworkVersionService.deleteFrameworkVersion(frameworkVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionService.getFrameworkVersion(frameworkVersionId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active,
			start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scFrameworkVersionService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	public SCFrameworkVersionService getWrappedSCFrameworkVersionService() {
		return _scFrameworkVersionService;
	}

	private SCFrameworkVersionService _scFrameworkVersionService;
}