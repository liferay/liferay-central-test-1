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

package com.liferay.portal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <p>
 * This interface is a model that represents the ResourceCode table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCode
 * @see       com.liferay.portal.model.impl.ResourceCodeImpl
 * @see       com.liferay.portal.model.impl.ResourceCodeModelImpl
 * @generated
 */
public interface ResourceCodeModel extends BaseModel<ResourceCode> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getCodeId();

	public void setCodeId(long codeId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	public int getScope();

	public void setScope(int scope);

	public ResourceCode toEscapedModel();

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(ResourceCode resourceCode);

	public int hashCode();

	public String toString();

	public String toXmlString();
}