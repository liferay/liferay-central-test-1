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


/**
 * <p>
 * This interface is a model that represents the ExpandoColumn table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.expando.model.impl.ExpandoColumnImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnModel
 * @see       com.liferay.portlet.expando.model.impl.ExpandoColumnImpl
 * @see       com.liferay.portlet.expando.model.impl.ExpandoColumnModelImpl
 * @generated
 */
public interface ExpandoColumn extends ExpandoColumnModel {
	public java.io.Serializable getDefaultValue();

	public java.lang.String getDisplayName(java.util.Locale locale);

	public java.lang.String getTypeSettings();

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettings(java.lang.String typeSettings);

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}