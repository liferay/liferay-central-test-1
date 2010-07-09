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

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public interface BasePersistence<T extends BaseModel<T>> {

	public void clearCache();

	public void clearCache(T model);

	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException;

	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	@SuppressWarnings("unchecked")
	public List findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	@SuppressWarnings("unchecked")
	public List findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException;

	@SuppressWarnings("unchecked")
	public List findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException;

	public DataSource getDataSource();

	public ModelListener<T>[] getListeners();

	public void registerListener(ModelListener<T> listener);

	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	public T remove(T model) throws SystemException;

	public void setDataSource(DataSource dataSource);

	public void unregisterListener(ModelListener<T> listener);

	public T update(T model, boolean merge) throws SystemException;

	public T update(T model, boolean merge, ServiceContext serviceContext)
		throws SystemException;

}