/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLFileEntryTypeService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileEntryTypeService
 * @generated
 */
public class DLFileEntryTypeServiceWrapper implements DLFileEntryTypeService {
	public DLFileEntryTypeServiceWrapper(
		DLFileEntryTypeService dlFileEntryTypeService) {
		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType addFileEntryType(
		long groupId, java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeService.addFileEntryType(groupId, name,
			description, ddmStructureIds, serviceContext);
	}

	public void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeService.deleteFileEntryType(fileEntryTypeId);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileEntryType getFileEntryType(
		long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeService.getFileEntryType(fileEntryTypeId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getFileEntryTypes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeService.getFileEntryTypes(groupId, start, end);
	}

	public int getFileEntryTypesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileEntryTypeService.getFileEntryTypesCount(groupId);
	}

	public void updateFileEntryType(long fileEntryTypeId,
		java.lang.String name, java.lang.String description,
		long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileEntryTypeService.updateFileEntryType(fileEntryTypeId, name,
			description, ddmStructureIds, serviceContext);
	}

	public DLFileEntryTypeService getWrappedDLFileEntryTypeService() {
		return _dlFileEntryTypeService;
	}

	public void setWrappedDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {
		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	private DLFileEntryTypeService _dlFileEntryTypeService;
}