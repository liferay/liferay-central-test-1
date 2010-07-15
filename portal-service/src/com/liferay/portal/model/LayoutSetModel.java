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
 * This interface is a model that represents the LayoutSet table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSet
 * @see       com.liferay.portal.model.impl.LayoutSetImpl
 * @see       com.liferay.portal.model.impl.LayoutSetModelImpl
 * @generated
 */
public interface LayoutSetModel extends BaseModel<LayoutSet> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getLayoutSetId();

	public void setLayoutSetId(long layoutSetId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public boolean getPrivateLayout();

	public boolean isPrivateLayout();

	public void setPrivateLayout(boolean privateLayout);

	public boolean getLogo();

	public boolean isLogo();

	public void setLogo(boolean logo);

	public long getLogoId();

	public void setLogoId(long logoId);

	@AutoEscape
	public String getThemeId();

	public void setThemeId(String themeId);

	@AutoEscape
	public String getColorSchemeId();

	public void setColorSchemeId(String colorSchemeId);

	@AutoEscape
	public String getWapThemeId();

	public void setWapThemeId(String wapThemeId);

	@AutoEscape
	public String getWapColorSchemeId();

	public void setWapColorSchemeId(String wapColorSchemeId);

	@AutoEscape
	public String getCss();

	public void setCss(String css);

	public int getPageCount();

	public void setPageCount(int pageCount);

	@AutoEscape
	public String getVirtualHost();

	public void setVirtualHost(String virtualHost);

	@AutoEscape
	public String getSettings();

	public void setSettings(String settings);

	public long getLayoutSetPrototypeId();

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId);

	public LayoutSet toEscapedModel();

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

	public int compareTo(LayoutSet layoutSet);

	public int hashCode();

	public String toString();

	public String toXmlString();
}