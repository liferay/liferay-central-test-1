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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.search.facet.util.FacetValueValidator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class DLIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {DLFileEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.DOCUMENT_LIBRARY;

	public DLIndexer() {
		IndexerRegistryUtil.register(
			new com.liferay.documentlibrary.util.DLIndexer());
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return DLFileEntryPermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "extension", true);
		addSearchTerm(searchQuery, searchContext, "path", true);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, true);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		String expandoAttributes = (String)params.get("expandoAttributes");

		if (Validator.isNotNull(expandoAttributes)) {
			addSearchExpando(searchQuery, searchContext, expandoAttributes);
		}
	}

	protected void addSearchFolderIds(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		MultiValueFacet folderIdsFacet = new MultiValueFacet(searchContext);

		folderIdsFacet.setFacetValueValidator(new FacetValueValidator() {
			public boolean check(SearchContext searchContext, String primKey) {
				try {
					DLFolderServiceUtil.getFolder(GetterUtil.getLong(primKey));
				}
				catch (Exception e) {
					return false;
				}

				return true;
			}
		});

		folderIdsFacet.setFieldName(Field.FOLDER_ID);
		folderIdsFacet.setStatic(true);

		searchContext.addFacet(folderIdsFacet);
	}

	protected void doDelete(Object obj) throws Exception {
		DLFileEntry fileEntry = (DLFileEntry)obj;

		FileModel fileModel = new FileModel();

		fileModel.setCompanyId(fileEntry.getCompanyId());
		fileModel.setFileName(fileEntry.getName());
		fileModel.setPortletId(PORTLET_ID);
		fileModel.setRepositoryId(fileEntry.getDataRepositoryId());

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

		indexer.delete(fileModel);
	}

	protected Document doGetDocument(Object obj) throws Exception {
		DLFileEntry fileEntry = (DLFileEntry)obj;

		long companyId = fileEntry.getCompanyId();
		long groupId = fileEntry.getGroupId();
		long userId = fileEntry.getUserId();
		long repositoryId = fileEntry.getDataRepositoryId();
		String fileName = fileEntry.getName();
		long fileEntryId = fileEntry.getFileEntryId();
		String properties = fileEntry.getLuceneProperties();
		Date modifiedDate = fileEntry.getModifiedDate();

		long[] assetCategoryIds = AssetCategoryLocalServiceUtil.getCategoryIds(
			DLFileEntry.class.getName(), fileEntryId);
		String[] assetCategoryNames =
			AssetCategoryLocalServiceUtil.getCategoryNames(
				DLFileEntry.class.getName(), fileEntryId);
		String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
			DLFileEntry.class.getName(), fileEntryId);

		FileModel fileModel = new FileModel();

		fileModel.setAssetCategoryIds(assetCategoryIds);
		fileModel.setAssetCategoryNames(assetCategoryNames);
		fileModel.setAssetTagNames(assetTagNames);
		fileModel.setCompanyId(companyId);
		fileModel.setFileEntryId(fileEntryId);
		fileModel.setFileName(fileName);
		fileModel.setGroupId(groupId);
		fileModel.setUserId(userId);
		fileModel.setModifiedDate(modifiedDate);
		fileModel.setPortletId(PORTLET_ID);
		fileModel.setProperties(properties);
		fileModel.setRepositoryId(repositoryId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

		return indexer.getDocument(fileModel);
	}

	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		LiferayPortletURL liferayPortletURL = (LiferayPortletURL)portletURL;

		liferayPortletURL.setLifecycle(PortletRequest.ACTION_PHASE);

		try {
			liferayPortletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		}
		catch (WindowStateException wse) {
		}

		String fileName = document.get("path");

		String title = fileName;

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String fileEntryId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/document_library/get_file");
		portletURL.setParameter("fileEntryId", fileEntryId);

		return new Summary(title, content, portletURL);
	}

	protected void doReindex(Object obj) throws Exception {
		DLFileEntry fileEntry = (DLFileEntry)obj;

		Document document = getDocument(fileEntry);

		if (document != null) {
			SearchEngineUtil.updateDocument(fileEntry.getCompanyId(), document);
		}
	}

	protected void doReindex(String className, long classPK) throws Exception {
		DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			classPK);

		doReindex(fileEntry);
	}

	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexFolders(companyId);
		reindexRoot(companyId);
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	public boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	protected void reindexFolders(long companyId) throws Exception {
		int folderCount = DLFolderLocalServiceUtil.getCompanyFoldersCount(
			companyId);

		int folderPages = folderCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= folderPages; i++) {
			int folderStart = (i * Indexer.DEFAULT_INTERVAL);
			int folderEnd = folderStart + Indexer.DEFAULT_INTERVAL;

			reindexFolders(companyId, folderStart, folderEnd);
		}
	}

	protected void reindexFolders(
			long companyId, int folderStart, int folderEnd)
		throws Exception {

		List<DLFolder> folders = DLFolderLocalServiceUtil.getCompanyFolders(
			companyId, folderStart, folderEnd);

		for (DLFolder folder : folders) {
			String portletId = PortletKeys.DOCUMENT_LIBRARY;
			long groupId = folder.getGroupId();
			long folderId = folder.getFolderId();

			String[] newIds = {
				String.valueOf(companyId), portletId,
				String.valueOf(groupId), String.valueOf(folderId)
			};

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			indexer.reindex(newIds);
		}
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			String portletId = PortletKeys.DOCUMENT_LIBRARY;
			long groupId = group.getGroupId();
			long folderId = groupId;

			String[] newIds = {
				String.valueOf(companyId), portletId,
				String.valueOf(groupId), String.valueOf(folderId)
			};

			Indexer indexer = IndexerRegistryUtil.getIndexer(FileModel.class);

			indexer.reindex(newIds);
		}
	}

	private static final boolean _FILTER_SEARCH = true;

}