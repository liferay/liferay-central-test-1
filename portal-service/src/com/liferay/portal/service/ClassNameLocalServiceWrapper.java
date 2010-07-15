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


/**
 * <p>
 * This class is a wrapper for {@link ClassNameLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNameLocalService
 * @generated
 */
public class ClassNameLocalServiceWrapper implements ClassNameLocalService {
	public ClassNameLocalServiceWrapper(
		ClassNameLocalService classNameLocalService) {
		_classNameLocalService = classNameLocalService;
	}

	public com.liferay.portal.model.ClassName addClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.addClassName(className);
	}

	public com.liferay.portal.model.ClassName createClassName(long classNameId) {
		return _classNameLocalService.createClassName(classNameId);
	}

	public void deleteClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(classNameId);
	}

	public void deleteClassName(com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(className);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.ClassName getClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassName(classNameId);
	}

	public java.util.List<com.liferay.portal.model.ClassName> getClassNames(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNames(start, end);
	}

	public int getClassNamesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNamesCount();
	}

	public com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.updateClassName(className);
	}

	public com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.updateClassName(className, merge);
	}

	public com.liferay.portal.model.ClassName addClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.addClassName(value);
	}

	public void checkClassNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.checkClassNames();
	}

	public com.liferay.portal.model.ClassName getClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassName(value);
	}

	public long getClassNameId(java.lang.Class<?> classObj) {
		return _classNameLocalService.getClassNameId(classObj);
	}

	public long getClassNameId(java.lang.String value) {
		return _classNameLocalService.getClassNameId(value);
	}

	public ClassNameLocalService getWrappedClassNameLocalService() {
		return _classNameLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
}