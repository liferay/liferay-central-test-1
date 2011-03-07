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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLRepositoryLocalService;
import com.liferay.portlet.documentlibrary.service.DLRepositoryService;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class LiferayLocalRepository
	extends LiferayRepositoryBase implements LocalRepository {

	public LiferayLocalRepository(
		RepositoryService repositoryService,
		DLRepositoryLocalService dlRepositoryLocalService,
		DLRepositoryService dlRepositoryService, long repositoryId) {

		super(
			repositoryService, dlRepositoryLocalService, dlRepositoryService,
			repositoryId);
	}

	public LiferayLocalRepository(
		RepositoryService repositoryService,
		DLRepositoryLocalService dlRepositoryLocalService,
		DLRepositoryService dlRepositoryService, long folderId,
		long fileEntryId, long fileVersionId) {

		super(
			repositoryService, dlRepositoryLocalService, dlRepositoryService,
			folderId, fileEntryId, fileVersionId);
	}

	public FileEntry addFileEntry(
			long userId, long folderId, String title, String description,
			String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlRepositoryLocalService.addFileEntry(
			userId, getGroupId(), getRepositoryId(), toFolderId(folderId),
			title, description, changeLog, is, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlRepositoryLocalService.addFolder(
			userId, getGroupId(), getRepositoryId(), toFolderId(parentFolderId),
			title, description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public void addRepository(
		long groupId, String name, String description, String portletKey,
		UnicodeProperties typeSettingsProperties) {
	}

	public void deleteAll() throws PortalException, SystemException {
		dlRepositoryLocalService.deleteAll(getGroupId());
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		dlRepositoryLocalService.deleteFileEntry(fileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		dlRepositoryLocalService.deleteFolder(folderId);
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			dlRepositoryLocalService.getFileEntries(
				getGroupId(), toFolderId(folderId), start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws SystemException {

		List<Object> dlFileEntriesAndFileShortcuts =
			dlRepositoryLocalService.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, start, end);

		return toFileEntriesAndFolders(dlFileEntriesAndFileShortcuts);
	}

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws SystemException {

		return dlRepositoryLocalService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return dlRepositoryLocalService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlRepositoryLocalService.getFileEntry(
			fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlRepositoryLocalService.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			dlRepositoryLocalService.getFileEntryByUuidAndGroupId(
				uuid, getGroupId());

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion = dlRepositoryLocalService.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(dlFileVersion);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlRepositoryLocalService.getFolder(folderId);

		return new LiferayFolder(dlFolder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlRepositoryLocalService.getFolder(
			getGroupId(), toFolderId(parentFolderId), title);

		return new LiferayFolder(dlFolder);
	}

	public List<Folder> getFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFolder> dlFolders = dlRepositoryLocalService.getFolders(
			getGroupId(), toFolderId(parentFolderId), start, end, obc);

		return toFolders(dlFolders);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws SystemException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			dlRepositoryLocalService.getFoldersAndFileEntriesAndFileShortcuts(
					getGroupId(), toFolderId(folderId), status, start, end);

		return toFileEntriesAndFolders(dlFoldersAndFileEntriesAndFileShortcuts);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status)
		throws SystemException {

		return dlRepositoryLocalService.
			getFoldersAndFileEntriesAndFileShortcutsCount(
				getGroupId(), toFolderId(folderId), status);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return dlRepositoryLocalService.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId));
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return dlRepositoryLocalService.getFoldersFileEntriesCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlRepositoryLocalService.moveFileEntry(
			userId, fileEntryId, toFolderId(newFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();
		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		dlRepositoryLocalService.updateAsset(
			userId, dlFileEntry, dlFileVersion, assetCategoryIds,
			assetTagNames);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName, String title,
			String description, String changeLog, boolean majorVersion,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlRepositoryLocalService.updateFileEntry(
			userId, fileEntryId, sourceFileName, title, description, changeLog,
			majorVersion, is, size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlRepositoryLocalService.updateFolder(
			toFolderId(folderId), toFolderId(parentFolderId), title,
			description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public UnicodeProperties updateRepository(
		UnicodeProperties typeSettingsProperties) {

		return typeSettingsProperties;
	}

}