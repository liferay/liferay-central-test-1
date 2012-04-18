/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

/**
 * @author Alexander Chow
 */
public interface TrashHandler {

	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException;

	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException;

	public AssetRendererFactory getAssetRendererFactory();

	public String getClassName();

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException;

	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException;

}