/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class BookmarksEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<BookmarksEntry> {

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		if (entry.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, entry.getFolder());
		}

		Element entryElement =
			portletDataContext.getExportDataStagedModelElement(entry);

		portletDataContext.addClassedModel(
			entryElement, StagedModelPathUtil.getPath(entry), entry,
			BookmarksPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, BookmarksEntry entry)
		throws Exception {

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				BookmarksFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, entry.getFolderId(), entry.getFolderId());

		if ((folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == entry.getFolderId())) {

			String parentFolderPath = StagedModelPathUtil.getPath(
				portletDataContext, BookmarksFolder.class.getName(), folderId);

			BookmarksFolder parentFolder =
				(BookmarksFolder)portletDataContext.getZipEntryAsObject(
					parentFolderPath);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, parentFolder);

			folderId = MapUtil.getLong(
				folderIds, entry.getFolderId(), entry.getFolderId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			entry, BookmarksPortletDataHandler.NAMESPACE);

		BookmarksEntry importedEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			BookmarksEntry existingEntry = BookmarksEntryUtil.fetchByUUID_G(
				entry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingEntry == null) {
				serviceContext.setUuid(entry.getUuid());

				importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
			else {
				importedEntry = BookmarksEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(),
					portletDataContext.getScopeGroupId(), folderId,
					entry.getName(), entry.getUrl(), entry.getDescription(),
					serviceContext);
			}
		}
		else {
			importedEntry = BookmarksEntryLocalServiceUtil.addEntry(
				userId, portletDataContext.getScopeGroupId(), folderId,
				entry.getName(), entry.getUrl(), entry.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			entry, importedEntry, BookmarksPortletDataHandler.NAMESPACE);
	}

}