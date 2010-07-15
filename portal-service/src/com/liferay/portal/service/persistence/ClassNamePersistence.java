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

import com.liferay.portal.model.ClassName;

/**
 * @author    Brian Wing Shun Chan
 * @see       ClassNamePersistenceImpl
 * @see       ClassNameUtil
 * @generated
 */
public interface ClassNamePersistence extends BasePersistence<ClassName> {
	public void cacheResult(com.liferay.portal.model.ClassName className);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ClassName> classNames);

	public com.liferay.portal.model.ClassName create(long classNameId);

	public com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName updateImpl(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName findByPrimaryKey(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByValue(java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByValue(java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}