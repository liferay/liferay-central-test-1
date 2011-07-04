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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.io.File;

import java.util.List;
import java.util.StringTokenizer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Ivica Cardic
 */
public class DocumentCommandReceiver extends BaseCommandReceiver {

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(),
				StringPool.SLASH + commandArgument.getCurrentFolder());

			long parentFolderId = folder.getFolderId();
			String name = commandArgument.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolderId, name, description,
				serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	protected String fileUpload(
		CommandArgument commandArgument, String fileName, File file,
		String contentType) {

		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(), commandArgument.getCurrentFolder());

			long folderId = folder.getFolderId();
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, contentType, title, description,
				changeLog, file, serviceContext);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	protected void getFolders(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected void getFoldersAndFiles(
		CommandArgument commandArgument, Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
			_getFiles(commandArgument, document, rootNode);
		}
		catch (PrincipalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		}
		catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY);
	}

	private void _getFiles(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		if (Validator.isNull(commandArgument.getCurrentGroupName())) {
			return;
		}

		Group group = commandArgument.getCurrentGroup();

		Folder folder = _getFolder(
			group.getGroupId(), commandArgument.getCurrentFolder());

		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
			group.getGroupId(), folder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
			Element fileElement = document.createElement("File");

			filesElement.appendChild(fileElement);

			fileElement.setAttribute("name", fileEntry.getTitle());
			fileElement.setAttribute("desc", fileEntry.getTitle());
			fileElement.setAttribute("size", getSize(fileEntry.getSize()));

			StringBundler url = new StringBundler(6);

			ThemeDisplay themeDisplay = commandArgument.getThemeDisplay();

			url.append("/documents/");
			url.append(group.getGroupId());
			url.append(StringPool.SLASH);
			url.append(fileEntry.getFolderId());
			url.append(StringPool.SLASH);
			url.append(HttpUtil.encodeURL(fileEntry.getTitle()));

			fileElement.setAttribute("url", url.toString());
		}
	}

	private Folder _getFolder(long groupId, String folderName)
		throws Exception {

		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		dlFolder.setGroupId(groupId);

		Folder folder = new LiferayFolder(dlFolder);

		if (folderName.equals(StringPool.SLASH)) {
			return folder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<Folder> folders = DLAppServiceUtil.getFolders(
				groupId, folder.getFolderId());

			for (Folder curFolder : folders) {
				if (curFolder.getName().equals(curFolderName)) {
					folder = curFolder;

					break;
				}
			}
		}

		return folder;
	}

	private void _getFolders(
			CommandArgument commandArgument, Document document, Node rootNode)
		throws Exception {

		Element foldersElement = document.createElement("Folders");

		rootNode.appendChild(foldersElement);

		if (commandArgument.getCurrentFolder().equals(StringPool.SLASH)) {
			getRootFolders(commandArgument, document, foldersElement);
		}
		else {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(
				group.getGroupId(), commandArgument.getCurrentFolder());

			List<Folder> folders = DLAppServiceUtil.getFolders(
				group.getGroupId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				Element folderElement = document.createElement("Folder");

				foldersElement.appendChild(folderElement);

				folderElement.setAttribute("name", curFolder.getName());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DocumentCommandReceiver.class);

}