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

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.BaseTrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;

/**
 * Represents the trash handler for the folder entity.
 *
 * @author Alexander Chow
 */
public class DLFolderTrashHandler extends BaseTrashHandler {

	public static final String CLASS_NAME = DLFolder.class.getName();

	@Override
	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(trashEntry.getClassPK());

		String restoredTitle = dlFolder.getName();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = restoredTitle;

		if (restoredTitle.indexOf(StringPool.FORWARD_SLASH) > 0) {
			originalTitle = restoredTitle.substring(
				0, restoredTitle.indexOf(StringPool.FORWARD_SLASH));
		}

		DLFolder duplicatedFolder = DLFolderLocalServiceUtil.fetchFolder(
			dlFolder.getGroupId(), dlFolder.getParentFolderId(), originalTitle);

		if (duplicatedFolder != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicatedFolder.getFolderId());
			dee.setOldName(duplicatedFolder.getName());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	/**
	 * Deletes all folders with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the folders to be deleted
	 * @throws PortalException if any one of the folders could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				DLAppServiceUtil.deleteFolder(classPK);
			}
			else {
				DLAppLocalServiceUtil.deleteFolder(classPK);
			}
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

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		dlFolder.setName(name);

		DLFolderLocalServiceUtil.updateDLFolder(dlFolder, false);
	}

	protected DLFolder getDLFolder(long classPK)
		throws SystemException, PortalException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		Folder folder = repository.getFolder(classPK);

		return (DLFolder)folder.getModel();
	}

}