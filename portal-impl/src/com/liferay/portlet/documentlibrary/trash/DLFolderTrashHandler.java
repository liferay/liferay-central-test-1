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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

/**
 * Represents the trash handler for the folder entity.
 *
 * @author Alexander Chow
 */
public class DLFolderTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = DLFolder.class.getName();

	/**
	 * Deletes all folders with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the folders to be deleted
	 * @throws PortalException if any one of the folders could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.deleteFolder(classPK);
		}
	}

	/**
	 * Returns the folder entity's class name
	 *
	 * @return the folder entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	/**
	 * Returns the trash renderer associated to the trash entry.
	 *
	 * @param  classPK the primary key of the folder
	 * @return the trash renderer associated to the folder
	 * @throws PortalException if the folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		Folder folder = DLAppLocalServiceUtil.getFolder(classPK);

		return new DLFolderTrashRenderer(folder);
	}

	/**
	 * Restores all folders with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the folders to be deleted
	 * @throws PortalException if any one of the folders could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.restoreFolderFromTrash(classPK);
		}
	}

}