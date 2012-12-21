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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

import java.util.Set;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alnso
 */
public interface DL {

	public void addPortletBreadcrumbEntries(
			DLFileShortcut dlFileShortcut, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			FileEntry fileEntry, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request, PortletURL portletURL)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			Folder folder, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public void addPortletBreadcrumbEntries(
			long folderId, HttpServletRequest request,
			RenderResponse renderResponse)
		throws Exception;

	public int compareVersions(String version1, String version2);

	public String getAbsolutePath(PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException;

	public Set<String> getAllMediaGalleryMimeTypes();

	public String getDDMStructureKey(DLFileEntryType dlFileEntryType);

	public String getDDMStructureKey(String fileEntryTypeUuid);

	public String getDeprecatedDDMStructureKey(DLFileEntryType dlFileEntryType);

	public String getDeprecatedDDMStructureKey(long fileEntryTypeId);

	public String getDividedPath(long id);

	public String getDLControlPanelLink(
			PortletRequest portletRequest, long folderId)
		throws PortalException, SystemException;

	public String getFileEntryImage(
		FileEntry fileEntry, ThemeDisplay themeDisplay);

	public String getFileIcon(String extension);

	public String getGenericName(String extension);

	public String[] getMediaGalleryMimeTypes(
		PortletPreferences portletPreferences, PortletRequest portletRequest);

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString);

	/**
	 * @deprecated {@link #getPreviewURL(FileEntry, FileVersion, ThemeDisplay,
	 *             String, boolean, boolean)}
	 */
	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendToken);

	public String getPreviewURL(
		FileEntry fileEntry, FileVersion fileVersion, ThemeDisplay themeDisplay,
		String queryString, boolean appendVersion, boolean absoluteURL);

	public OrderByComparator getRepositoryModelOrderByComparator(
		String orderByCol, String orderByType);

	public String getTempFileId(long id, String version);

	public String getTempFileId(long id, String version, String languageId);

	public String getThumbnailSrc(
			FileEntry fileEntry, DLFileShortcut dlFileShortcut,
			ThemeDisplay themeDisplay)
		throws Exception;

	public String getThumbnailSrc(
			FileEntry fileEntry, FileVersion fileVersion,
			DLFileShortcut dlFileShortcut, ThemeDisplay themeDisplay)
		throws Exception;

	public String getThumbnailStyle() throws Exception;

	public String getThumbnailStyle(boolean max, int margin)
		throws Exception;

	public String getTitleWithExtension(FileEntry fileEntry);

	public String getTitleWithExtension(String title, String extension);

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry)
		throws PortalException, SystemException;

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired)
		throws PortalException, SystemException;

	public String getWebDavURL(
			ThemeDisplay themeDisplay, Folder folder, FileEntry fileEntry,
			boolean manualCheckInRequired, boolean officeExtensionRequired)
		throws PortalException, SystemException;

	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, long folderId, long fileEntryTypeId)
		throws Exception;

	public boolean isAutoGeneratedDLFileEntryTypeDDMStructureKey(
		String ddmStructureKey);

	public abstract boolean isOfficeExtension(String extension);

}