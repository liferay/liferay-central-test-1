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

package com.liferay.portal.model;

import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base interface for all model classes. This interface should never need to
 * be used directly.
 *
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.model.impl.BaseModelImpl
 */
public interface BaseModel<T> extends Cloneable, Comparable<T>, Serializable {

	/**
	 * Determines if this model instance does not yet exist in the database.
	 *
	 * @return <code>true</code> if this model instance does not yet exist in
	 *         the database; <code>false</code> otherwise
	 */
	public boolean isNew();

	/**
	 * Sets whether this model instance does not yet exist in the database.
	 *
	 * @param n whether this model instance does not yet exist in the database
	 */
	public void setNew(boolean n);

	/**
	 * Determines if this model instance was retrieved from the entity cache.
	 *
	 * @return <code>true</code> if this model instance was retrieved from the
	 *         entity cache; <code>false</code> otherwise
	 * @see    #setCachedModel(boolean)
	 */
	public boolean isCachedModel();

	/**
	 * Sets whether this model instance was retrieved from the entity cache.
	 *
	 * @param cachedModel whether this model instance was retrieved from the
	 *        entity cache
	 * @see   com.liferay.portal.kernel.dao.orm.EntityCache
	 */
	public void setCachedModel(boolean cachedModel);

	/**
	 * Determines if this model instance is escaped.
	 *
	 * @return <code>true</code> if this model instance is escaped;
	 *         <code>false</code> otherwise
	 * @see    #setEscapedModel(boolean)
	 */
	public boolean isEscapedModel();

	/**
	 * Sets whether this model instance is escaped, meaning that all strings
	 * returned from getter methods are HTML safe.
	 *
	 * <p>
	 * A model instance can be made escaped by wrapping it with an HTML auto
	 * escape handler using its <code>toEscapedModel</code> method. For example,
	 * {@link com.liferay.portal.model.UserModel#toEscapedModel()}.
	 * </p>
	 *
	 * @param escapedModel whether this model instance is escaped
	 * @see   com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public void setEscapedModel(boolean escapedModel);

	/**
	 * Gets the primary key of this model instance.
	 *
	 * @return the primary key of this model instance
	 */
	public Serializable getPrimaryKeyObj();

	/**
	 * Gets the expando bridge for this model instance.
	 *
	 * @return the expando bridge for this model instance
	 */
	public ExpandoBridge getExpandoBridge();

	/**
	 * Sets the expando bridge attributes for this model instance to the
	 * attributes stored in the service context.
	 *
	 * @param serviceContext the service context to retrieve the expando bridge
	 *        attributes from
	 * @see   com.liferay.portal.service.ServiceContext#getExpandoBridgeAttributes(
	 *        )
	 */
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	/**
	 * Creates a shallow clone of this model instance.
	 *
	 * @return the shallow clone of this model instance
	 */
	public Object clone();

	/**
	 * Gets a copy of this entity as an escaped model instance by wrapping it
	 * with an {@link com.liferay.portal.kernel.bean.AutoEscapeBeanHandler}.
	 *
	 * @return the escaped model instance
	 * @see    com.liferay.portal.kernel.bean.AutoEscapeBeanHandler
	 */
	public T toEscapedModel();

	/**
	 * Gets the XML representation of this model instance.
	 *
	 * @return the XML representation of this model instance
	 */
	public String toXmlString();

}