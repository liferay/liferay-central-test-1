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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.PwdGenerator;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportFileEntry(
			PortletDataContext context, Element foldersElement,
			Element fileEntriesElement, Element fileRanksElement,
			DLFileEntry fileEntry, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!context.isWithinDateRange(fileEntry.getModifiedDate())) {

			return;
		}

		DLFileVersion fileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(
				context.getScopeGroupId(), fileEntry.getFolderId(),
				fileEntry.getName(), fileEntry.getVersion());

		if (fileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				context, foldersElement, fileEntry.getFolderId());
		}

		String path = getFileEntryPath(context, fileEntry);

		if (context.isPathNotProcessed(path)) {
			Element fileEntryElement = fileEntriesElement.addElement(
				"file-entry");

			fileEntryElement.addAttribute("path", path);

			String binPath = getFileEntryBinPath(context, fileEntry);

			fileEntryElement.addAttribute("bin-path", binPath);

			fileEntry.setUserUuid(fileEntry.getUserUuid());

			context.addLocks(
				DLFileEntry.class,
				DLUtil.getLockId(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName()));

			context.addPermissions(
				DLFileEntry.class, fileEntry.getFileEntryId());

			if (context.getBooleanParameter(_NAMESPACE, "categories")) {
				context.addAssetCategories(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "comments")) {
				context.addComments(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(
					DLFileEntry.class, fileEntry.getFileEntryId());
			}

			long repositoryId = getRepositoryId(
				fileEntry.getGroupId(), fileEntry.getFolderId());

			InputStream is = DLLocalServiceUtil.getFileAsStream(
				fileEntry.getCompanyId(), repositoryId, fileEntry.getName(),
				fileEntry.getVersion());

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No file found for file entry " +
							fileEntry.getFileEntryId());
				}

				fileEntryElement.detach();

				return;
			}

			try {
				context.addZipEntry(
					getFileEntryBinPath(context, fileEntry), is);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}

			context.addZipEntry(path, fileEntry);

			if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
				List<DLFileRank> fileRanks = DLFileRankUtil.findByF_N(
					fileEntry.getFolderId(), fileEntry.getName());

				for (DLFileRank fileRank : fileRanks) {
					exportFileRank(context, fileRanksElement, fileRank);
				}
			}
		}
	}

	public static String getFileEntryPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/file-entries/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFileEntry(
			PortletDataContext context, Element fileEntryElement)
		throws Exception {

		String path = fileEntryElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		DLFileEntry fileEntry = (DLFileEntry)context.getZipEntryAsObject(path);

		String binPath = fileEntryElement.attributeValue("bin-path");

		long userId = context.getUserId(fileEntry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(fileEntry.getCreateDate());
		serviceContext.setModifiedDate(fileEntry.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		InputStream is = context.getZipEntryAsInputStream(binPath);

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == fileEntry.getFolderId())) {

			String folderPath = getImportFolderPath(context, folderId);

			DLFolder folder = (DLFolder)context.getZipEntryAsObject(folderPath);

			importFolder(context, folder);

			folderId = MapUtil.getLong(
				folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());
		}

		DLFileEntry importedFileEntry = null;

		String nameWithExtension =
			fileEntry.getName().concat(StringPool.PERIOD).concat(
				fileEntry.getExtension());

		if (context.isDataStrategyMirror()) {
			DLFileEntry existingFileEntry = DLFileEntryUtil.fetchByUUID_G(
				fileEntry.getUuid(), context.getScopeGroupId());

			if (existingFileEntry == null) {
				String fileEntryTitle = fileEntry.getTitle();

				DLFileEntry existingTitleFileEntry =
					DLFileEntryUtil.fetchByG_F_T(
						context.getScopeGroupId(), folderId,
						fileEntry.getTitle());

				if (existingTitleFileEntry != null) {
					if (context.isDataStrategyMirrorWithOverwritting()) {
						DLFileEntryLocalServiceUtil.deleteDLFileEntry(
							existingTitleFileEntry);
					}
					else {
						String originalTitle = 	fileEntryTitle;

						for (int i = 1;; i++) {
							fileEntryTitle =
								originalTitle + StringPool.SPACE + i;

							existingTitleFileEntry =
								DLFileEntryUtil.findByG_F_T(
									context.getScopeGroupId(), folderId,
									fileEntry.getTitle());

							if (existingTitleFileEntry == null) {
								break;
							}
						}
					}
				}

				serviceContext.setUuid(fileEntry.getUuid());

				importedFileEntry =
					DLFileEntryLocalServiceUtil.addFileEntry(
						userId, context.getScopeGroupId(), folderId,
						nameWithExtension, fileEntryTitle,
						fileEntry.getDescription(), null,
						fileEntry.getExtraSettings(), is, fileEntry.getSize(),
						serviceContext);
			}
			else if (!isDuplicateFileEntry(fileEntry, existingFileEntry)) {
				importedFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(
					userId, context.getScopeGroupId(),
					existingFileEntry.getFolderId(),
					existingFileEntry.getName(), fileEntry.getTitle(),
					fileEntry.getTitle(), fileEntry.getDescription(), null,
					true, fileEntry.getExtraSettings(), is, fileEntry.getSize(),
					serviceContext);
			}
			else {
				DLFileVersion latestFileVersion =
					DLFileVersionLocalServiceUtil.getLatestFileVersion(
						context.getScopeGroupId(), folderId,
						existingFileEntry.getName());

				DLFileEntryLocalServiceUtil.updateAsset(
					userId, existingFileEntry, latestFileVersion,
					assetCategoryIds, assetTagNames);

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					DLFileEntry.class);

				indexer.reindex(existingFileEntry);

				importedFileEntry = existingFileEntry;
			}
		}
		else {
			String title = fileEntry.getTitle();

			try {
				importedFileEntry =
					DLFileEntryLocalServiceUtil.addFileEntry(
						userId, context.getScopeGroupId(), folderId,
						nameWithExtension, title, fileEntry.getDescription(),
						null, fileEntry.getExtraSettings(), is,
						fileEntry.getSize(), serviceContext);
			}
			catch (DuplicateFileException dfe) {
				String[] titleParts = title.split("\\.", 2);

				title = titleParts[0] + PwdGenerator.getPassword();

				if (titleParts.length > 1) {
					title += StringPool.PERIOD + titleParts[1];
				}

				importedFileEntry =
					DLFileEntryLocalServiceUtil.addFileEntry(
						userId, context.getScopeGroupId(), folderId,
						nameWithExtension, title, fileEntry.getDescription(),
						null, fileEntry.getExtraSettings(), is,
						fileEntry.getSize(), serviceContext);
			}
		}

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFileEntry.class);

		fileEntryPKs.put(
			fileEntry.getFileEntryId(), importedFileEntry.getFileEntryId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		fileEntryNames.put(fileEntry.getName(), importedFileEntry.getName());

		String lockKey = DLUtil.getLockId(
			fileEntry.getGroupId(), fileEntry.getFolderId(),
			fileEntry.getName());

		String newLockKey = DLUtil.getLockId(
			importedFileEntry.getGroupId(), importedFileEntry.getFolderId(),
			importedFileEntry.getName());

		context.importLocks(DLFileEntry.class, lockKey, newLockKey);

		context.importPermissions(
			DLFileEntry.class, fileEntry.getFileEntryId(),
			importedFileEntry.getFileEntryId());

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				DLFileEntry.class, fileEntry.getFileEntryId(),
				importedFileEntry.getFileEntryId(), context.getScopeGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				DLFileEntry.class, fileEntry.getFileEntryId(),
				importedFileEntry.getFileEntryId());
		}
	}

	public static void importFileRank(
			PortletDataContext context, Element fileRankElement)
		throws Exception {

		String path = fileRankElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		DLFileRank fileRank =
			(DLFileRank)context.getZipEntryAsObject(path);

		importFileRank(context, fileRank);
	}

	public static void importFolder(
			PortletDataContext context, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

		importFolder(context, folder);
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void exportFileRank(
			PortletDataContext context, Element fileRanksElement,
			DLFileRank fileRank)
		throws Exception {

		String path = getFileRankPath(context, fileRank);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element fileRankElement = fileRanksElement.addElement("file-rank");

		fileRankElement.addAttribute("path", path);

		fileRank.setUserUuid(fileRank.getUserUuid());

		context.addZipEntry(path, fileRank);
	}

	protected static void exportFileShortcut(
			PortletDataContext context, Element foldersElement,
			Element fileShortcutsElement, DLFileShortcut fileShortcut)
		throws Exception {

		exportParentFolder(context, foldersElement, fileShortcut.getFolderId());

		String path = getFileShortcutPath(context, fileShortcut);

		if (context.isPathNotProcessed(path)) {
			Element fileShortcutElement = fileShortcutsElement.addElement(
				"file-shortcut");

			fileShortcutElement.addAttribute("path", path);

			fileShortcut.setUserUuid(fileShortcut.getUserUuid());

			context.addPermissions(
				DLFileShortcut.class, fileShortcut.getFileShortcutId());

			context.addZipEntry(path, fileShortcut);
		}
	}

	protected static void exportFolder(
			PortletDataContext context, Element foldersElement,
			Element fileEntriesElement, Element fileShortcutsElement,
			Element fileRanksElement, DLFolder folder)
		throws Exception {

		if (context.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				context, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(context, folder);

			if (context.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				folderElement.addAttribute("path", path);

				folder.setUserUuid(folder.getUserUuid());

				context.addPermissions(DLFolder.class, folder.getFolderId());

				context.addZipEntry(path, folder);
			}
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
			folder.getGroupId(), folder.getFolderId());

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				context, foldersElement, fileEntriesElement, fileRanksElement,
				fileEntry, true);
		}

		if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				folder.getGroupId(), folder.getFolderId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					context, foldersElement, fileShortcutsElement,
					fileShortcut);
			}
		}
	}

	protected static void exportParentFolder(
			PortletDataContext context, Element foldersElement, long folderId)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(context, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(context, folder);

		if (context.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			folderElement.addAttribute("path", path);

			folder.setUserUuid(folder.getUserUuid());

			context.addPermissions(DLFolder.class, folder.getFolderId());

			context.addZipEntry(path, folder);
		}
	}

	protected static String getFileEntryBinPath(
		PortletDataContext context, DLFileEntry fileEntry) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());

		return sb.toString();
	}

	protected static String getFileRankPath(
		PortletDataContext context, DLFileRank fileRank) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/ranks/");
		sb.append(fileRank.getFileRankId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileShortcutPath(
		PortletDataContext context, DLFileShortcut fileShortcut) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/shortcuts/");
		sb.append(fileShortcut.getFileShortcutId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFolderName(
			String uuid, long companyId, long groupId, long parentFolderId,
			String name, int count)
		throws Exception {

		DLFolder folder = DLFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(
			uuid, companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext context, DLFolder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext context, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getSourcePortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static long getRepositoryId(long groupId, long folderId) {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return groupId;
		}
		else {
			return folderId;
		}
	}

	protected static void importFileRank(
			PortletDataContext context, DLFileRank rank)
		throws Exception {

		long userId = context.getUserId(rank.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, rank.getFolderId(), rank.getFolderId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		String name = fileEntryNames.get(rank.getName());

		if (name == null) {
			name = rank.getName();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(rank.getCreateDate());

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == rank.getFolderId())) {

			String path = getImportFolderPath(context, folderId);

			DLFolder folder = (DLFolder)context.getZipEntryAsObject(path);

			importFolder(context, folder);

			folderId = MapUtil.getLong(
				folderPKs, rank.getFolderId(), rank.getFolderId());
		}

		DLFileRankLocalServiceUtil.updateFileRank(
			context.getScopeGroupId(), context.getCompanyId(), userId, folderId,
			name, serviceContext);
	}

	protected static void importFileShortcut(
			PortletDataContext context, DLFileShortcut fileShortcut)
		throws Exception {

		long userId = context.getUserId(fileShortcut.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileShortcut.getFolderId(), fileShortcut.getFolderId());
		long toFolderId = MapUtil.getLong(
			folderPKs, fileShortcut.getToFolderId(),
			fileShortcut.getToFolderId());

		Map<String, String> fileEntryNames =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				DLFileEntry.class.getName() + ".name");

		String toName = MapUtil.getString(
			fileEntryNames, fileShortcut.getToName(), fileShortcut.getToName());

		DLFolder folder = DLFolderUtil.findByPrimaryKey(folderId);

		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			folder.getGroupId(), toFolderId, toName);

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				DLFileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setCreateDate(fileShortcut.getCreateDate());
		serviceContext.setModifiedDate(fileShortcut.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		DLFileShortcut importedFileShortcut = null;

		if (context.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				DLFileShortcutUtil.fetchByUUID_G(
					fileShortcut.getUuid(), context.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut =
					DLFileShortcutLocalServiceUtil.addFileShortcut(
						userId, folder.getGroupId(), folderId, toFolderId,
						toName, serviceContext);
			}
			else {
				importedFileShortcut =
					DLFileShortcutLocalServiceUtil.updateFileShortcut(
						userId, existingFileShortcut.getFileShortcutId(),
						folderId, toFolderId, toName, serviceContext);
			}
		}
		else {
			importedFileShortcut =
				DLFileShortcutLocalServiceUtil.addFileShortcut(
					userId, folder.getGroupId(), folderId, toFolderId, toName,
					serviceContext);
		}

		context.importPermissions(
			DLFileShortcut.class, fileShortcut.getPrimaryKey(),
			importedFileShortcut.getPrimaryKey());
	}

	protected static void importFileShortcut(
			PortletDataContext context, Element fileShortcutElement)
		throws Exception {

		String path = fileShortcutElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		DLFileShortcut fileShortcut =
			(DLFileShortcut)context.getZipEntryAsObject(path);

		importFileShortcut(context, fileShortcut);
	}

	protected static void importFolder(
			PortletDataContext context, DLFolder folder)
		throws Exception {

		long userId = context.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(folder.getCreateDate());
		serviceContext.setModifiedDate(folder.getModifiedDate());

		if ((parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(context, parentFolderId);

			DLFolder parentFolder = (DLFolder)context.getZipEntryAsObject(path);

			importFolder(context, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		DLFolder importedFolder = null;

		if (context.isDataStrategyMirror()) {
			DLFolder existingFolder = DLFolderUtil.fetchByUUID_G(
				folder.getUuid(), context.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, context.getCompanyId(), context.getScopeGroupId(),
					parentFolderId, folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = DLFolderLocalServiceUtil.addFolder(
					userId, context.getScopeGroupId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), context.getCompanyId(),
					context.getScopeGroupId(), parentFolderId, folder.getName(),
					2);

				importedFolder = DLFolderLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, context.getCompanyId(), context.getScopeGroupId(),
				parentFolderId, folder.getName(), 2);

			importedFolder = DLFolderLocalServiceUtil.addFolder(
				userId, context.getScopeGroupId(), parentFolderId, name,
				folder.getDescription(), serviceContext);
		}

		folderPKs.put(folder.getFolderId(), importedFolder.getFolderId());

		context.importPermissions(
			DLFolder.class, folder.getFolderId(), importedFolder.getFolderId());
	}

	protected static boolean isDuplicateFileEntry(
		DLFileEntry fileEntry1, DLFileEntry fileEntry2) {

		try {
			DLFolder folder1 = fileEntry1.getFolder();
			DLFolder folder2 = fileEntry2.getFolder();

			if ((folder1.getUuid().equals(folder2.getUuid())) &&
				(fileEntry1.getSize() == fileEntry2.getSize()) &&
				(DLUtil.compareVersions(
					fileEntry1.getVersion(), fileEntry2.getVersion()) == 0) &&
				(fileEntry1.getVersionUserUuid().equals(
					fileEntry2.getVersionUserUuid()))) {

				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				DLPortletDataHandlerImpl.class, "deleteData")) {

			DLFolderLocalServiceUtil.deleteFolders(context.getScopeGroupId());

			DLFileEntryLocalServiceUtil.deleteFileEntries(
				context.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		return null;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.documentlibrary", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("documentlibrary-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		long rootFolderId = GetterUtil.getLong(
			preferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			rootElement.addAttribute(
				"root-folder-id", String.valueOf(rootFolderId));
		}

		Element foldersElement = rootElement.addElement("folders");
		Element fileEntriesElement = rootElement.addElement("file-entries");
		Element fileShortcutsElement = rootElement.addElement("file-shortcuts");
		Element fileRanksElement = rootElement.addElement("file-ranks");

		List<DLFolder> folders = DLFolderUtil.findByGroupId(
			context.getScopeGroupId());

		for (DLFolder folder : folders) {
			exportFolder(
				context, foldersElement, fileEntriesElement,
				fileShortcutsElement, fileRanksElement, folder);
		}

		List<DLFileEntry> fileEntries = DLFileEntryUtil.findByG_F(
			context.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (DLFileEntry fileEntry : fileEntries) {
			exportFileEntry(
				context, foldersElement, fileEntriesElement, fileRanksElement,
				fileEntry, true);
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.documentlibrary",
			context.getSourceGroupId(), context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			importFolder(context, folderElement);
		}

		Element fileEntriesElement = rootElement.element("file-entries");

		List<Element> fileEntryElements = fileEntriesElement.elements(
			"file-entry");

		for (Element fileEntryElement : fileEntryElements) {
			importFileEntry(context, fileEntryElement);
		}

		if (context.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<Element> fileShortcutElements = rootElement.element(
				"file-shortcuts").elements("file-shortcut");

			for (Element fileShortcutElement : fileShortcutElements) {
				importFileShortcut(context, fileShortcutElement);
			}
		}

		if (context.getBooleanParameter(_NAMESPACE, "ranks")) {
			Element fileRanksElement = rootElement.element("file-ranks");

			List<Element> fileRankElements = fileRanksElement.elements(
				"file-rank");

			for (Element fileRankElement : fileRankElements) {
				importFileRank(context, fileRankElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));

		if (rootFolderId > 0) {
			Map<Long, Long> folderPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFolder.class);

			rootFolderId = MapUtil.getLong(
				folderPKs, rootFolderId, rootFolderId);

			preferences.setValue("rootFolderId", String.valueOf(rootFolderId));
		}

		return preferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "document_library";

	private static Log _log = LogFactoryUtil.getLog(
		DLPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}