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

package com.liferay.portlet.expando.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ExpandoRow table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRow
 * @see       com.liferay.portlet.expando.model.impl.ExpandoRowImpl
 * @see       com.liferay.portlet.expando.model.impl.ExpandoRowModelImpl
 * @generated
 */
public interface ExpandoRowModel extends BaseModel<ExpandoRow> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getRowId();

	public void setRowId(long rowId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getTableId();

	public void setTableId(long tableId);

	public long getClassPK();

	public void setClassPK(long classPK);

	public ExpandoRow toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(ExpandoRow expandoRow);

	public int hashCode();

	public String toString();

	public String toXmlString();
}