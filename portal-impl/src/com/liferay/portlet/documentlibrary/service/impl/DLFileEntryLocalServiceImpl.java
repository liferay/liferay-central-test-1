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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.util.JCRHook;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileEntryModifiedDateComparator;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * For DLFileEntries, the naming convention for some of the variables is not
 * very informative, due to legacy code. Each DLFileEntry has a corresponding
 * name and title. The "name" is a unique identifier for a given file and
 * usually follows the format "1234" whereas the "title" is the actual name
 * specified by the user (e.g., "Budget.xls").
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 */
public class DLFileEntryLocalServiceImpl
	extends DLFileEntryLocalServiceBaseImpl {

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (bytes == null) {
			throw new FileSizeException();
		}

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		return addFileEntry(
			userId, groupId, folderId, name, title, description,
			changeLog, extraSettings, is, bytes.length, serviceContext);
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (file == null) {
			throw new FileSizeException();
		}

		try {
			InputStream is = new UnsyncBufferedInputStream(
				new FileInputStream(file));

			return addFileEntry(
				userId, groupId, folderId, name, title, description,
				changeLog, extraSettings, is, file.length(), serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileSizeException();
		}
	}

	public DLFileEntry addFileEntry(
			long userId, long groupId, long folderId, String name, String title,
			String description, String changeLog, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);

		String extension = FileUtil.getExtension(name);

		if (Validator.isNull(title)) {
			title = name;
		}

		name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));

		Date now = new Date();

		validate(groupId, folderId, title, is);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry fileEntry = dlFileEntryPersistence.create(fileEntryId);

		fileEntry.setUuid(serviceContext.getUuid());
		fileEntry.setGroupId(groupId);
		fileEntry.setCompanyId(user.getCompanyId());
		fileEntry.setUserId(user.getUserId());
		fileEntry.setUserName(user.getFullName());
		fileEntry.setVersionUserId(user.getUserId());
		fileEntry.setVersionUserName(user.getFullName());
		fileEntry.setCreateDate(serviceContext.getCreateDate(now));
		fileEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		fileEntry.setFolderId(folderId);
		fileEntry.setName(name);
		fileEntry.setExtension(extension);
		fileEntry.setTitle(title);
		fileEntry.setDescription(description);
		fileEntry.setExtraSettings(extraSettings);
		fileEntry.setVersion(DLFileEntryConstants.DEFAULT_VERSION);
		fileEntry.setSize(size);
		fileEntry.setReadCount(DLFileEntryConstants.DEFAULT_READ_COUNT);

		dlFileEntryPersistence.update(fileEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFileEntryResources(
				fileEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileEntryResources(
				fileEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// File version

		DLFileVersion fileVersion = addFileVersion(
			user, fileEntry, serviceContext.getModifiedDate(now),
			extension, title, description, null, extraSettings,
			DLFileEntryConstants.DEFAULT_VERSION, size,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = dlFolderPersistence.findByPrimaryKey(folderId);

			folder.setLastPostDate(fileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// Asset

		updateAsset(
			userId, fileEntry, fileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Message boards

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				userId, fileEntry.getUserName(), groupId,
				DLFileEntry.class.getName(), fileEntryId,
				WorkflowConstants.ACTION_PUBLISH);
		}

		// File

		dlLocalService.addFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			fileEntry.getGroupId(), fileEntry.getRepositoryId(), name, false,
			fileEntryId, fileEntry.getLuceneProperties(),
			fileEntry.getModifiedDate(), serviceContext, is);

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, DLFileEntry.class.getName(),
			fileEntryId, fileEntry, serviceContext);

		return fileEntry;
	}

	public void addFileEntryResources(
			DLFileEntry fileEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFileEntryResources(
			DLFileEntry fileEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getUserId(), DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), communityPermissions, guestPermissions);
	}

	public void addFileEntryResources(
			long fileEntryId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			fileEntry, addCommunityPermissions, addGuestPermissions);
	}

	public void addFileEntryResources(
			long fileEntryId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		addFileEntryResources(
			fileEntry, communityPermissions, guestPermissions);
	}

	public DLFileEntry addOrOverwriteFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceName, String title, String description,
			String changeLog, String extraSettings, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);

			return updateFileEntry(
				userId, groupId, folderId, name, sourceName, title, description,
				changeLog, false, extraSettings, file, serviceContext);
		}
		catch (NoSuchFileEntryException nsfee) {
			return addFileEntry(
				userId, groupId, folderId, name, title, description, changeLog,
				extraSettings, file, serviceContext);
		}
	}

	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> fileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry fileEntry : fileEntries) {
			deleteFileEntry(fileEntry);
		}
	}

	public void deleteFileEntry(DLFileEntry fileEntry)
		throws PortalException, SystemException {

		// File entry

		dlFileEntryPersistence.remove(fileEntry);

		// Resources

		resourceLocalService.deleteResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, fileEntry.getFileEntryId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// File ranks

		dlFileRankLocalService.deleteFileRanks(
			fileEntry.getFolderId(), fileEntry.getName());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		// File versions

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByG_F_N(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		for (DLFileVersion fileVersion : fileVersions) {
			dlFileVersionPersistence.remove(fileVersion);
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Lock

		String lockId = DLUtil.getLockId(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		lockLocalService.unlock(DLFileEntry.class.getName(), lockId);

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// File

		try {
			dlService.deleteFile(
				fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				fileEntry.getRepositoryId(), fileEntry.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	public void deleteFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		deleteFileEntry(groupId, folderId, name, null);
	}

	public void deleteFileEntry(
			long groupId, long folderId, String name, String version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		if (Validator.isNotNull(version)) {
			try {
				dlService.deleteFile(
					fileEntry.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					fileEntry.getRepositoryId(), fileEntry.getName(), version);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			long fileVersionsCount = dlFileVersionPersistence.countByG_F_N(
				groupId, folderId, name);

			dlFileVersionPersistence.removeByG_F_N_V(
				groupId, folderId, name, version);

			if (fileVersionsCount == 1) {
				dlFileEntryPersistence.remove(fileEntry);
			}
			else {
				if (version.equals(fileEntry.getVersion())) {
					try {
						DLFileVersion fileVersion =
							dlFileVersionLocalService.getLatestFileVersion(
								groupId, folderId, name);

						fileEntry.setVersion(fileVersion.getVersion());
						fileEntry.setSize(fileVersion.getSize());
					}
					catch (NoSuchFileVersionException nsfve) {
					}
				}

				dlFileEntryPersistence.update(fileEntry, false);
			}
		}
		else {
			deleteFileEntry(fileEntry);
		}
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public List<DLFileEntry> getCompanyFileEntries(
			long companyId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByCompanyId(
			companyId, start, end, obc);
	}

	public int getCompanyFileEntriesCount(long companyId)
		throws SystemException {

		return dlFileEntryPersistence.countByCompanyId(companyId);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name)
		throws PortalException, SystemException {

		return getFileAsStream(
			companyId, userId, groupId, folderId, name, StringPool.BLANK);
	}

	public InputStream getFileAsStream(
			long companyId, long userId, long groupId, long folderId,
			String name, String version)
		throws PortalException, SystemException {

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		if (userId > 0) {
			dlFileRankLocalService.updateFileRank(
				groupId, companyId, userId, folderId, name,
				new ServiceContext());
		}

		if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED) {
			fileEntry.setReadCount(fileEntry.getReadCount() + 1);

			dlFileEntryPersistence.update(fileEntry, false);

			assetEntryLocalService.incrementViewCounter(
				userId, DLFileEntry.class.getName(),
				fileEntry.getFileEntryId());

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByG_TF_TN(
					groupId, folderId, name);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.incrementViewCounter(
					userId, DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId());
			}
		}

		if (Validator.isNotNull(version)) {
			return dlLocalService.getFileAsStream(
				companyId, fileEntry.getRepositoryId(), name, version);
		}
		else {
			return dlLocalService.getFileAsStream(
				companyId, fileEntry.getRepositoryId(), name,
				fileEntry.getVersion());
		}
	}

	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(groupId, folderId);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(groupId, folderId, start, end);
	}

	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(
			groupId, folderId, start, end, obc);
	}

	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.countByG_F(groupId, folderId);
	}

	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	public DLFileEntry getFileEntry(long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_N(groupId, folderId, name);
	}

	public DLFileEntry getFileEntryByTitle(
			long groupId, long folderId, String title)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);
	}

	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
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

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, start, end, new FileEntryModifiedDateComparator());
	}

	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.findByGroupId(
				groupId, start, end, obc);
		}
		else {
			return dlFileEntryPersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
	}

	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return dlFileEntryPersistence.countByGroupId(groupId);
	}

	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.countByGroupId(groupId);
		}
		else {
			return dlFileEntryPersistence.countByG_U(groupId, userId);
		}
	}

	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryFinder.findByNoAssets();
	}

	public DLFileEntry moveFileEntry(
			long userId, long groupId, long folderId, long newFolderId,
			String name, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		Date now = new Date();

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		long oldFileEntryId = fileEntry.getFileEntryId();

		if (dlLocalService.hasFile(
				user.getCompanyId(),
				DLFileEntryImpl.getRepositoryId(groupId, newFolderId), name,
				StringPool.BLANK)) {

			throw new DuplicateFileException(name);
		}

		long newFileEntryId = counterLocalService.increment();

		DLFileEntry newFileEntry = dlFileEntryPersistence.create(
			newFileEntryId);

		newFileEntry.setGroupId(fileEntry.getGroupId());
		newFileEntry.setCompanyId(fileEntry.getCompanyId());
		newFileEntry.setUserId(fileEntry.getUserId());
		newFileEntry.setUserName(fileEntry.getUserName());
		newFileEntry.setVersionUserId(fileEntry.getVersionUserId());
		newFileEntry.setVersionUserName(fileEntry.getVersionUserName());
		newFileEntry.setCreateDate(fileEntry.getCreateDate());
		newFileEntry.setModifiedDate(fileEntry.getModifiedDate());
		newFileEntry.setFolderId(newFolderId);
		newFileEntry.setName(name);
		newFileEntry.setExtension(fileEntry.getExtension());
		newFileEntry.setTitle(fileEntry.getTitle());
		newFileEntry.setDescription(fileEntry.getDescription());
		newFileEntry.setExtraSettings(fileEntry.getExtraSettings());
		newFileEntry.setVersion(fileEntry.getVersion());
		newFileEntry.setSize(fileEntry.getSize());
		newFileEntry.setReadCount(fileEntry.getReadCount());

		dlFileEntryPersistence.update(newFileEntry, false);

		dlFileEntryPersistence.remove(fileEntry);

		workflowInstanceLinkLocalService.updateClassPK(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			DLFileEntry.class.getName(), oldFileEntryId, newFileEntryId);

		List<DLFileVersion> fileVersions = dlFileVersionPersistence.findByG_F_N(
			groupId, folderId, name);

		for (DLFileVersion fileVersion : fileVersions) {
			long newFileVersionId = counterLocalService.increment();

			DLFileVersion newFileVersion = dlFileVersionPersistence.create(
				newFileVersionId);

			newFileVersion.setGroupId(fileVersion.getGroupId());
			newFileVersion.setCompanyId(fileVersion.getCompanyId());
			newFileVersion.setUserId(fileVersion.getUserId());
			newFileVersion.setUserName(fileVersion.getUserName());
			newFileVersion.setCreateDate(fileVersion.getCreateDate());
			newFileVersion.setFolderId(newFolderId);
			newFileVersion.setName(name);
			newFileVersion.setExtension(fileVersion.getExtension());
			newFileVersion.setTitle(fileVersion.getTitle());
			newFileVersion.setDescription(fileVersion.getDescription());
			newFileVersion.setChangeLog(fileVersion.getChangeLog());
			newFileVersion.setExtraSettings(fileVersion.getExtraSettings());
			newFileVersion.setVersion(fileVersion.getVersion());
			newFileVersion.setSize(fileVersion.getSize());
			newFileVersion.setStatus(fileVersion.getStatus());
			newFileVersion.setStatusByUserId(userId);
			newFileVersion.setStatusByUserName(user.getFullName());
			newFileVersion.setStatusDate(serviceContext.getModifiedDate(now));

			dlFileVersionPersistence.update(newFileVersion, false);

			dlFileVersionPersistence.remove(fileVersion);
		}

		dlFileShortcutLocalService.updateFileShortcuts(
			groupId, folderId, name, newFolderId, name);

		// Resources

		Resource resource = resourceLocalService.getResource(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(fileEntry.getFileEntryId()));

		resource.setPrimKey(String.valueOf(newFileEntryId));

		resourcePersistence.update(resource, false);

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_TF_TN(
				groupId, folderId, name);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			assetEntryLocalService.deleteEntry(
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId());
		}

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// Ratings

		RatingsStats stats = ratingsStatsLocalService.getStats(
			DLFileEntry.class.getName(), oldFileEntryId);

		stats.setClassPK(newFileEntryId);

		ratingsStatsPersistence.update(stats, false);

		long classNameId = PortalUtil.getClassNameId(
			DLFileEntry.class.getName());

		List<RatingsEntry> entries = ratingsEntryPersistence.findByC_C(
			classNameId, oldFileEntryId);

		for (RatingsEntry entry : entries) {
			entry.setClassPK(newFileEntryId);

			ratingsEntryPersistence.update(entry, false);
		}

		// Message boards

		MBDiscussion discussion = mbDiscussionPersistence.fetchByC_C(
			classNameId, oldFileEntryId);

		if (discussion != null) {
			discussion.setClassPK(newFileEntryId);

			mbDiscussionPersistence.update(discussion, false);
		}

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		// File

		dlService.updateFile(
			user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
			newFileEntry.getGroupId(), fileEntry.getRepositoryId(),
			newFileEntry.getRepositoryId(), name, newFileEntryId);

		return newFileEntry;
	}

	public void updateAsset(
			long userId, DLFileEntry fileEntry, DLFileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames)
		throws PortalException, SystemException {

		String mimeType = MimeTypesUtil.getContentType(fileEntry.getTitle());

		boolean addDraftAssetEntry = false;

		if ((fileVersion != null) && !fileVersion.isApproved() &&
			(fileVersion.getVersion() !=
				DLFileEntryConstants.DEFAULT_VERSION)) {

			int approvedArticlesCount =
				dlFileVersionPersistence.countByG_F_N_S(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName(), WorkflowConstants.STATUS_APPROVED);

			if (approvedArticlesCount > 0) {
				addDraftAssetEntry = true;
			}
		}

		if (addDraftAssetEntry) {
			assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, false, null, null, null, null,
				mimeType, fileEntry.getTitle(), fileEntry.getDescription(),
				null, null, 0, 0, null, false);
		}
		else {
			boolean visible = true;

			if ((fileVersion != null) && !fileVersion.isApproved()) {
				visible = false;
			}

			assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), DLFileEntry.class.getName(),
				fileEntry.getFileEntryId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, visible, null, null, null,
				null, mimeType, fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, 0, 0, null, false);

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByG_TF_TN(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, fileShortcut.getGroupId(),
					DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId(), fileShortcut.getUuid(),
					assetCategoryIds, assetTagNames, true, null, null, null,
					null, mimeType, fileEntry.getTitle(),
					fileEntry.getDescription(), null, null, 0, 0, null, false);
			}
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			byte[] bytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = null;
		long size = 0;

		if (bytes != null) {
			is = new UnsyncByteArrayInputStream(bytes);
			size = bytes.length;
		}

		return updateFileEntry(
			userId, groupId, folderId, name, sourceFileName, title, description,
			changeLog, majorVersion, extraSettings, is, size, serviceContext);
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			InputStream is = null;
			long size = 0;

			if ((file != null) && file.exists()) {
				is = new UnsyncBufferedInputStream(new FileInputStream(file));
				size = file.length();
			}

			return updateFileEntry(
				userId, groupId, folderId, name, sourceFileName, title,
				description, changeLog, majorVersion, extraSettings, is, size,
				serviceContext);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException();
		}
	}

	public DLFileEntry updateFileEntry(
			long userId, long groupId, long folderId, String name,
			String sourceFileName, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(title)) {
			title = sourceFileName;

			if (Validator.isNull(title)) {
				title = name;
			}
		}

		Date now = new Date();

		DLFileEntry fileEntry = dlFileEntryPersistence.findByG_F_N(
			groupId, folderId, name);

		validate(
			groupId, folderId, name, fileEntry.getExtension(), title,
			sourceFileName, is);

		// File version

		String version = getNextVersion(
			fileEntry, majorVersion, serviceContext.getWorkflowAction());

		DLFileVersion fileVersion = null;

		String extension = null;

		if (Validator.isNotNull(sourceFileName)) {
			extension = FileUtil.getExtension(sourceFileName);
		}
		else {
			extension = fileEntry.getExtension();
		}

		try {
			DLFileVersion latestFileVersion =
				dlFileVersionLocalService.getLatestFileVersion(
					groupId, folderId, name);

			if (size == 0) {
				size = latestFileVersion.getSize();
			}

			if (latestFileVersion.getStatus() !=
					WorkflowConstants.STATUS_APPROVED) {

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);

				version = latestFileVersion.getVersion();

				updateFileVersion(
					user, latestFileVersion, sourceFileName, extension, title,
					description, changeLog, extraSettings, version, size,
					latestFileVersion.getStatus(),
					serviceContext.getModifiedDate(now), serviceContext);
			}
			else {
				fileVersion = addFileVersion(
					user, fileEntry, serviceContext.getModifiedDate(now),
					extension, title, description, changeLog, extraSettings,
					version, size, WorkflowConstants.STATUS_DRAFT,
					serviceContext);
			}

			if (fileVersion == null) {
				fileVersion = latestFileVersion;
			}
		}
		catch (NoSuchFileVersionException nsfve) {
			fileVersion = addFileVersion(
				user, fileEntry, serviceContext.getModifiedDate(now),
				extension, title, description, changeLog, extraSettings,
				version, size, WorkflowConstants.STATUS_DRAFT, serviceContext);
		}

		if ((is == null) && !version.equals(fileEntry.getVersion())) {
			int fetchFailures = 0;

			while (is == null) {
				try {
					is = dlLocalService.getFileAsStream(
						user.getCompanyId(), fileEntry.getRepositoryId(), name);
				}
				catch (NoSuchFileException nsfe) {
					fetchFailures++;

					if (PropsValues.DL_HOOK_IMPL.equals(
							JCRHook.class.getName()) &&
						(fetchFailures <
							PropsValues.DL_HOOK_JCR_FETCH_MAX_FAILURES)) {

						try {
							Thread.sleep(PropsValues.DL_HOOK_JCR_FETCH_DELAY);
						}
						catch (InterruptedException ie) {
						}
					}
					else {
						throw nsfe;
					}
				}
			}
		}

		// Asset

		updateAsset(
			userId, fileEntry, fileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		// Folder

		if (fileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder folder = dlFolderPersistence.findByPrimaryKey(
				fileEntry.getFolderId());

			folder.setLastPostDate(fileEntry.getModifiedDate());

			dlFolderPersistence.update(folder, false);
		}

		// File

		if (is != null) {
			try {
				dlService.deleteFile(
					user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					fileEntry.getRepositoryId(), fileEntry.getName(), version);
			}
			catch (NoSuchFileException nsfe) {
			}

			dlLocalService.updateFile(
				user.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				fileEntry.getGroupId(), fileEntry.getRepositoryId(), name,
				fileEntry.getExtension(), false, version, sourceFileName,
				fileEntry.getFileEntryId(),	fileEntry.getLuceneProperties(),
				fileEntry.getModifiedDate(), serviceContext, is);
		}

		// Workflow

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			user.getCompanyId(), groupId, userId, DLFileEntry.class.getName(),
			fileEntry.getFileEntryId(), fileEntry, serviceContext);

		return fileEntry;
	}

	public DLFileEntry updateStatus(
			long userId, long fileEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileEntry fileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		// File version

		DLFileVersion latestFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				fileEntry.getName());

		latestFileVersion.setStatus(status);
		latestFileVersion.setStatusByUserId(user.getUserId());
		latestFileVersion.setStatusByUserName(user.getFullName());
		latestFileVersion.setStatusDate(new Date());

		dlFileVersionPersistence.update(latestFileVersion, false);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// File entry

			if (DLUtil.compareVersions(
					fileEntry.getVersion(),
					latestFileVersion.getVersion()) < 0) {

				fileEntry.setTitle(latestFileVersion.getTitle());
				fileEntry.setDescription(latestFileVersion.getDescription());
				fileEntry.setExtraSettings(
					latestFileVersion.getExtraSettings());
				fileEntry.setVersion(latestFileVersion.getVersion());
				fileEntry.setVersionUserId(latestFileVersion.getUserId());
				fileEntry.setVersionUserName(latestFileVersion.getUserName());
				fileEntry.setModifiedDate(latestFileVersion.getCreateDate());
				fileEntry.setSize(latestFileVersion.getSize());

				dlFileEntryPersistence.update(fileEntry, false);
			}

			// Asset

			if (fileEntry.getVersion().equals(latestFileVersion.getVersion())) {
				if ((latestFileVersion.getVersion() !=
						DLFileEntryConstants.DEFAULT_VERSION)) {

					AssetEntry draftAssetEntry = null;

					try {
						draftAssetEntry = assetEntryLocalService.getEntry(
							DLFileEntry.class.getName(),
							latestFileVersion.getPrimaryKey());

						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						assetEntryLocalService.updateEntry(
							userId, fileEntry.getGroupId(),
							DLFileEntry.class.getName(),
							fileEntry.getFileEntryId(), fileEntry.getUuid(),
							assetCategoryIds, assetTagNames, true, null, null,
							null, null, draftAssetEntry.getMimeType(),
							fileEntry.getTitle(), fileEntry.getDescription(),
							null, null, 0, 0, null, false);

						assetEntryLocalService.deleteEntry(
							draftAssetEntry.getEntryId());
					}
					catch (NoSuchEntryException nsee) {
					}
				}

				assetEntryLocalService.updateVisible(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
					true);
			}

			// Social

			socialActivityLocalService.addUniqueActivity(
				latestFileVersion.getUserId(), latestFileVersion.getGroupId(),
				latestFileVersion.getCreateDate(), DLFileEntry.class.getName(),
				fileEntryId, DLActivityKeys.ADD_FILE_ENTRY,
				StringPool.BLANK, 0);

			// Indexer

			Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);

			indexer.reindex(fileEntry);
		}
		else {

			// File entry

			if (fileEntry.getVersion().equals(latestFileVersion.getVersion())) {
				String newVersion = DLFileEntryConstants.DEFAULT_VERSION;

				List<DLFileVersion> approvedFileVersions =
					dlFileVersionPersistence.findByG_F_N_S(
						fileEntry.getGroupId(), fileEntry.getFolderId(),
						fileEntry.getName(), WorkflowConstants.STATUS_APPROVED);

				if (!approvedFileVersions.isEmpty()) {
					newVersion = approvedFileVersions.get(0).getVersion();
				}

				fileEntry.setVersion(newVersion);

				dlFileEntryPersistence.update(fileEntry, false);
			}

			// Asset

			if (Validator.isNull(fileEntry.getVersion())) {
				assetEntryLocalService.updateVisible(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
					false);
			}

			// Indexer

			if (latestFileVersion.getVersion().equals(
					DLFileEntryConstants.DEFAULT_VERSION)) {

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					DLFileEntry.class);

				indexer.delete(fileEntry);
			}
		}

		return fileEntry;
	}

	protected DLFileVersion addFileVersion(
			User user, DLFileEntry fileEntry, Date modifiedDate,
			String extension, String title, String description,
			String changeLog, String extraSettings, String version, long size,
			int status, ServiceContext serviceContext)
		throws SystemException {

		long fileVersionId = counterLocalService.increment();

		DLFileVersion fileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		long versionUserId = fileEntry.getVersionUserId();

		if (versionUserId <= 0) {
			versionUserId = fileEntry.getUserId();
		}

		String versionUserName = GetterUtil.getString(
			fileEntry.getVersionUserName(), fileEntry.getUserName());

		fileVersion.setGroupId(fileEntry.getGroupId());
		fileVersion.setCompanyId(fileEntry.getCompanyId());
		fileVersion.setUserId(versionUserId);
		fileVersion.setUserName(versionUserName);
		fileVersion.setCreateDate(modifiedDate);
		fileVersion.setFolderId(fileEntry.getFolderId());
		fileVersion.setName(fileEntry.getName());
		fileVersion.setExtension(extension);
		fileVersion.setTitle(title);
		fileVersion.setDescription(description);
		fileVersion.setChangeLog(changeLog);
		fileVersion.setExtraSettings(extraSettings);
		fileVersion.setVersion(version);
		fileVersion.setSize(size);
		fileVersion.setStatus(status);
		fileVersion.setStatusByUserId(user.getUserId());
		fileVersion.setStatusByUserName(user.getFullName());
		fileVersion.setStatusDate(fileEntry.getModifiedDate());
		fileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersionPersistence.update(fileVersion, false);

		return fileVersion;
	}

	protected long getFolderId(long companyId, long folderId)
		throws SystemException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder folder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((folder == null) || (companyId != folder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected String getNextVersion(
		DLFileEntry fileEntry, boolean majorVersion, int workflowAction) {

		if (Validator.isNull(fileEntry.getVersion())) {
			return DLFileEntryConstants.DEFAULT_VERSION;
		}

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(
			fileEntry.getVersion(), StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected void updateFileVersion(
			User user, DLFileVersion fileVersion, String sourceFileName,
			String extension, String title, String description,
			String changeLog, String extraSettings, String version, long size,
			int status, Date statusDate, ServiceContext serviceContext)
		throws SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			fileVersion.setExtension(extension);
		}

		fileVersion.setTitle(title);
		fileVersion.setDescription(description);
		fileVersion.setChangeLog(changeLog);
		fileVersion.setExtraSettings(extraSettings);
		fileVersion.setVersion(version);
		fileVersion.setSize(size);
		fileVersion.setStatus(status);
		fileVersion.setStatusByUserId(user.getUserId());
		fileVersion.setStatusByUserName(user.getFullName());
		fileVersion.setStatusDate(statusDate);
		fileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersionPersistence.update(fileVersion, false);
	}

	protected void validate(
			long groupId, long folderId, String title, InputStream is)
		throws PortalException, SystemException {

		dlLocalService.validate(title, true, is);

		validate(groupId, folderId, null, title);
	}

	protected void validate(
			long groupId, long folderId, String name, String title)
		throws PortalException, SystemException {

		try {
			dlFolderLocalService.getFolder(groupId, folderId, title);

			throw new DuplicateFolderNameException();
		}
		catch (NoSuchFolderException nsfe) {
		}

		try {
			DLFileEntry fileEntry =
				dlFileEntryPersistence.findByG_F_T(groupId, folderId, title);

			if (!fileEntry.getName().equals(name)) {
				throw new DuplicateFileException(title);
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
	}

	protected void validate(
			long groupId, long folderId, String name, String extension,
			String title, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			dlLocalService.validate(
				sourceFileName, extension, sourceFileName, true, is);
		}

		validate(groupId, folderId, name, title);
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryLocalServiceImpl.class);

}