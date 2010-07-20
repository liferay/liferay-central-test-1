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

package com.liferay.portlet.documentlibrary.model;

/**
 * <p>
 * This interface is a model that represents the DLFileShortcut table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutModel
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl
 * @generated
 */
public interface DLFileShortcut extends DLFileShortcutModel {
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder();

	public java.lang.String getToTitle();
}