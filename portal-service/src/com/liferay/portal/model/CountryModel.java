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
 * This interface is a model that represents the Country table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Country
 * @see       com.liferay.portal.model.impl.CountryImpl
 * @see       com.liferay.portal.model.impl.CountryModelImpl
 * @generated
 */
public interface CountryModel extends BaseModel<Country> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getCountryId();

	public void setCountryId(long countryId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getA2();

	public void setA2(String a2);

	@AutoEscape
	public String getA3();

	public void setA3(String a3);

	@AutoEscape
	public String getNumber();

	public void setNumber(String number);

	@AutoEscape
	public String getIdd();

	public void setIdd(String idd);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public Country toEscapedModel();

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

	public int compareTo(Country country);

	public int hashCode();

	public String toString();

	public String toXmlString();
}