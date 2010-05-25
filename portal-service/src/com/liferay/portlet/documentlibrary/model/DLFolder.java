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
 * <a href="DLFolder.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the DLFolder table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFolderModel
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl
 * @see       com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl
 * @generated
 */
public interface DLFolder extends DLFolderModel {
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.documentlibrary.model.DLFolder getParentFolder()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getPath()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String[] getPathArray()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean hasLock(long userId);

	public boolean isLocked();

	public boolean isRoot();
}