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

package com.liferay.portlet.bookmarks.util;

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class BookmarksTestUtil {

	public static BookmarksEntry addEntry(boolean approved)
		throws Exception {

		return addEntry(
			TestPropsValues.getGroupId(),
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, approved);
	}

	public static BookmarksEntry addEntry(long groupId, boolean approved)
		throws Exception {

		return addEntry(
			groupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			approved);
	}

	public static BookmarksEntry addEntry(
			long groupId, long folderId, boolean approved)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			String name = "Test Entry";
			String url = "http://www.liferay.com";
			String description = "This is a test entry.";

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setScopeGroupId(groupId);

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BookmarksEntry entry = BookmarksEntryServiceUtil.addEntry(
				groupId, folderId, name, url, description, serviceContext);

			if (approved) {
				entry.setStatus(WorkflowConstants.STATUS_APPROVED);

				entry = BookmarksEntryServiceUtil.updateEntry(
					entry.getEntryId(), groupId, entry.getFolderId(),
					entry.getName(), entry.getUrl(), entry.getUrl(),
					serviceContext);
			}

			return entry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static BookmarksFolder addFolder() throws Exception {
		return addFolder(TestPropsValues.getGroupId());
	}

	public static BookmarksFolder addFolder(long groupId) throws Exception {
		long parentFolderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		return addFolder(groupId, parentFolderId);
	}

	public static BookmarksFolder addFolder(long groupId, long parentFolderId)
		throws Exception {

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return BookmarksFolderServiceUtil.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public static SearchContext getSearchContext(
		long companyId, long groupId, long folderId, String keywords) {

		return getSearchContext(
			companyId, groupId, folderId, keywords, false, false);
	}

	public static SearchContext getSearchContext(
		long companyId, long groupId, long folderId, String keywords,
		boolean highlight, boolean score) {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(highlight);
		queryConfig.setScoreEnabled(score);

		searchContext.setQueryConfig(queryConfig);

		return searchContext;
	}

}