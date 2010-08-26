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

	/**
	* Adds the class name to the database. Also notifies the appropriate model listeners.
	*
	* @param className the class name to add
	* @return the class name that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClassName addClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.addClassName(className);
	}

	/**
	* Creates a new class name with the primary key. Does not add the class name to the database.
	*
	* @param classNameId the primary key for the new class name
	* @return the new class name
	*/
	public com.liferay.portal.model.ClassName createClassName(long classNameId) {
		return _classNameLocalService.createClassName(classNameId);
	}

	/**
	* Deletes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param classNameId the primary key of the class name to delete
	* @throws PortalException if a class name with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(classNameId);
	}

	/**
	* Deletes the class name from the database. Also notifies the appropriate model listeners.
	*
	* @param className the class name to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteClassName(com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(className);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the class name with the primary key.
	*
	* @param classNameId the primary key of the class name to get
	* @return the class name
	* @throws PortalException if a class name with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClassName getClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassName(classNameId);
	}

	/**
	* Gets a range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of class names to return
	* @param end the upper bound of the range of class names to return (not inclusive)
	* @return the range of class names
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.ClassName> getClassNames(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNames(start, end);
	}

	/**
	* Gets the number of class names.
	*
	* @return the number of class names
	* @throws SystemException if a system exception occurred
	*/
	public int getClassNamesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNamesCount();
	}

	/**
	* Updates the class name in the database. Also notifies the appropriate model listeners.
	*
	* @param className the class name to update
	* @return the class name that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.updateClassName(className);
	}

	/**
	* Updates the class name in the database. Also notifies the appropriate model listeners.
	*
	* @param className the class name to update
	* @param merge whether to merge the class name with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the class name that was updated
	* @throws SystemException if a system exception occurred
	*/
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