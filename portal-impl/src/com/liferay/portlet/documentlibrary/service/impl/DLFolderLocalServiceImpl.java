/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFolderLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLFolderLocalServiceImpl extends DLFolderLocalServiceBaseImpl {

	public DLFolder addFolder(
			long userId, long groupId, long repositoryId, long parentFolderId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validateFolder(groupId, parentFolderId, name);

		long folderId = counterLocalService.increment();

		DLFolder dlFolder = dlFolderPersistence.create(folderId);

		dlFolder.setUuid(serviceContext.getUuid());
		dlFolder.setGroupId(groupId);
		dlFolder.setCompanyId(user.getCompanyId());
		dlFolder.setUserId(user.getUserId());
		dlFolder.setCreateDate(serviceContext.getCreateDate(now));
		dlFolder.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFolder.setRepositoryId(repositoryId);
		dlFolder.setMountPoint(
			GetterUtil.getBoolean(serviceContext.getAttribute("mountPoint")));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		// Resources

		if (serviceContext.getAddGroupPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFolderResources(
				dlFolder, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFolderResources(
				dlFolder, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Parent folder

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.findByPrimaryKey(
				parentFolderId);

			parentDLFolder.setLastPostDate(now);

			dlFolderPersistence.update(parentDLFolder, false);
		}

		// DLApp

		dlAppHelperLocalService.addFolder(
			new LiferayFolder(dlFolder), serviceContext);

		return dlFolder;
	}

	public void deleteAll(long groupId)
		throws PortalException, SystemException {

		Group group = groupLocalService.getGroup(groupId);

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFolder dlFolder : dlFolders) {
			deleteFolder(dlFolder);
		}

		dlFileEntryLocalService.deleteFileEntries(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		try {
			DLStoreUtil.deleteDirectory(
				group.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY, groupId,
				StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		deleteFolder(dlFolder);
	}

	public List<DLFolder> getCompanyFolders(long companyId, int start, int end)
		throws SystemException {

		return dlFolderPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return dlFolderPersistence.countByCompanyId(companyId);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		return dlFolderFinder.findFE_FS_ByG_F_S(
			groupId, folderId, status, start, end);
	}

	public int getFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.countFE_FS_ByG_F_S(groupId, folderId, status);
	}

	public DLFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	public DLFolder getFolder(long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByG_P_N(groupId, parentFolderId, name);
	}

	public long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	public List<DLFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<DLFolder> getFolders(
			long groupId, long parentFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderPersistence.findByG_P(
			groupId, parentFolderId, start, end, obc);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFolderFinder.findF_FE_FS_ByG_F_S(
			groupId, folderId, status, start, end, obc);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFolderFinder.countF_FE_FS_ByG_F_S(groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return dlFolderPersistence.countByG_P(groupId, parentFolderId);
	}

	public int getFoldersFileEntriesCount(
			long groupId, List<Long> folderIds, int status)
		throws SystemException {

		if (folderIds.size() <= PropsValues.SQL_DATA_MAX_PARAMETERS) {
			return dlFileEntryFinder.countByG_F_S(groupId, folderIds, status);
		}
		else {
			int start = 0;
			int end = PropsValues.SQL_DATA_MAX_PARAMETERS;

			int filesCount = dlFileEntryFinder.countByG_F_S(
				groupId, folderIds.subList(start, end), status);

			folderIds.subList(start, end).clear();

			filesCount += getFoldersFileEntriesCount(
				groupId, folderIds, status);

			return filesCount;
		}
	}

	public DLFolder getMountFolder(long repositoryId)
		throws PortalException, SystemException {

		return dlFolderPersistence.findByRepositoryId(repositoryId);
	}

	public DLFolder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
			dlFolder.getName());

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
	}

	public DLFolder updateFolder(
			long folderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		parentFolderId = getParentFolderId(dlFolder, parentFolderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(), parentFolderId,
			name);

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setParentFolderId(parentFolderId);
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
	}

	public DLFolder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		validateFolder(
			dlFolder.getFolderId(), dlFolder.getGroupId(),
			dlFolder.getParentFolderId(), name);

		dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFolder.setName(name);
		dlFolder.setDescription(description);
		dlFolder.setExpandoBridgeAttributes(serviceContext);

		dlFolderPersistence.update(dlFolder, false);

		return dlFolder;
	}

	protected void addFolderResources(
			DLFolder dlFolder, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFolderResources(
			DLFolder dlFolder, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			dlFolder.getCompanyId(), dlFolder.getGroupId(),
			dlFolder.getUserId(), DLFolder.class.getName(),
			dlFolder.getFolderId(), groupPermissions, guestPermissions);
	}

	protected void addFolderResources(
			long folderId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, addGroupPermissions, addGuestPermissions);
	}

	protected void addFolderResources(
			long folderId, String[] groupPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

		addFolderResources(dlFolder, groupPermissions, guestPermissions);
	}

	protected void deleteFolder(DLFolder dlFolder)
		throws PortalException, SystemException {

		// Folders

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		for (DLFolder curDLFolder : dlFolders) {
			deleteFolder(curDLFolder);
		}

		// Folder

		dlFolderPersistence.remove(dlFolder);

		// Resources

		resourceLocalService.deleteResource(
			dlFolder.getCompanyId(), DLFolder.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFolder.getFolderId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// File entries

		dlFileEntryLocalService.deleteFileEntries(
			dlFolder.getGroupId(), dlFolder.getFolderId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFolder.class.getName(), dlFolder.getFolderId());

		// DLApp

		dlAppHelperLocalService.deleteFolder(new LiferayFolder(dlFolder));

		// Directory

		try {
			DLStoreUtil.deleteDirectory(
				dlFolder.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				dlFolder.getFolderId(), StringPool.BLANK);
		}
		catch (NoSuchDirectoryException nsde) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsde.getMessage());
			}
		}
	}

	protected long getParentFolderId(DLFolder dlFolder, long parentFolderId)
		throws SystemException {

		if (parentFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return parentFolderId;
		}

		if (dlFolder.getFolderId() == parentFolderId) {
			return dlFolder.getParentFolderId();
		}
		else {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(dlFolder.getGroupId() != parentDLFolder.getGroupId())) {

				return dlFolder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, dlFolder.getGroupId(), dlFolder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return dlFolder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.fetchByPrimaryKey(
				parentFolderId);

			if ((parentDLFolder == null) ||
				(groupId != parentDLFolder.getGroupId())) {

				parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_P(
			groupId, folderId);

		for (DLFolder dlFolder : dlFolders) {
			folderIds.add(dlFolder.getFolderId());

			getSubfolderIds(
				folderIds, dlFolder.getGroupId(), dlFolder.getFolderId());
		}
	}

	protected void validateFolder(
			long folderId, long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		validateFolderName(name);

		try {
			dlFileEntryLocalService.getFileEntry(groupId, parentFolderId, name);

			throw new DuplicateFileException(name);
		}
		catch (NoSuchFileEntryException nsfee) {
		}

		DLFolder dlFolder = dlFolderPersistence.fetchByG_P_N(
			groupId, parentFolderId, name);

		if ((dlFolder != null) && (dlFolder.getFolderId() != folderId)) {
			throw new DuplicateFolderNameException(name);
		}
	}

	protected void validateFolder(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException {

		long folderId = 0;

		validateFolder(folderId, groupId, parentFolderId, name);
	}

	protected void validateFolderName(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new FolderNameException();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFolderLocalServiceImpl.class);

}