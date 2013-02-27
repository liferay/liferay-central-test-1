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
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import javax.portlet.PortletRequest;

/**
 * Implements trash handling for the file shortcut entity.
 *
 * @author Zsolt Berentey
 */
public class DLFileShortcutTrashHandler extends DLBaseTrashHandler {

	public static final String CLASS_NAME = DLFileShortcut.class.getName();

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				DLAppServiceUtil.deleteFileShortcut(classPK);
			}
			else {
				DLAppLocalServiceUtil.deleteFileShortcut(classPK);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		long parentFolderId = dlFileShortcut.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);

		return DLUtil.getDLControlPanelLink(
			portletRequest, fileShortcut.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);

		return DLUtil.getAbsolutePath(
			portletRequest, fileShortcut.getFolderId());
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);

		return new DLFileShortcutTrashRenderer(fileShortcut);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return DLFolderPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_SHORTCUT);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);

		return fileShortcut.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut fileShortcut =
			DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);

		return fileShortcut.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		return !dlFileShortcut.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		DLAppServiceUtil.updateFileShortcut(
			classPK, containerModelId, dlFileShortcut.getToFileEntryId(),
			serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLAppServiceUtil.moveFileShortcutFromTrash(
			classPK, containerModelId, serviceContext);
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.restoreFileShortcutFromTrash(classPK);
		}
	}

	protected DLFileShortcut getDLFileShortcut(long classPK)
		throws PortalException, SystemException {

		return DLFileShortcutLocalServiceUtil.getDLFileShortcut(classPK);
	}

	@Override
	protected Repository getRepository(long classPK)
		throws PortalException, SystemException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, dlFileShortcut.getToFileEntryId(), 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		return DLFileShortcutPermission.contains(
			permissionChecker, classPK, actionId);
	}

}