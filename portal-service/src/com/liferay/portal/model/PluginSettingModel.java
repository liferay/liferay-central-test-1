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
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PluginSetting table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PluginSetting
 * @see       com.liferay.portal.model.impl.PluginSettingImpl
 * @see       com.liferay.portal.model.impl.PluginSettingModelImpl
 * @generated
 */
public interface PluginSettingModel extends BaseModel<PluginSetting> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getPluginSettingId();

	public void setPluginSettingId(long pluginSettingId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	@AutoEscape
	public String getPluginId();

	public void setPluginId(String pluginId);

	@AutoEscape
	public String getPluginType();

	public void setPluginType(String pluginType);

	@AutoEscape
	public String getRoles();

	public void setRoles(String roles);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public PluginSetting toEscapedModel();

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

	public int compareTo(PluginSetting pluginSetting);

	public int hashCode();

	public String toString();

	public String toXmlString();
}