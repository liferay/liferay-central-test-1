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

package com.liferay.portal.webserver;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;
import java.io.InputStream;

import java.text.Format;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="WebServerServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class WebServerServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			long companyId = PortalUtil.getCompanyId(request);

			User user = PortalUtil.getUser(request);

			if (user == null) {
				Company company = CompanyLocalServiceUtil.getCompany(companyId);

				user = company.getDefaultUser();
			}

			PrincipalThreadLocal.setName(user.getUserId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			String path = HttpUtil.fixPath(request.getPathInfo());
			String[] pathArray = StringUtil.split(path, StringPool.SLASH);

			if (pathArray.length == 0) {
				sendGroups(
					response, user,
					request.getServletPath() + StringPool.SLASH + path);
			}
			else {
				if (Validator.isNumber(pathArray[0])) {
					sendFile(request, response, user, pathArray);
				}
				else {
					sendDocumentLibrary(
						request, response, user,
						request.getServletPath() + StringPool.SLASH + path,
						pathArray);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, nsfee, request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);
		}
	}

	protected long getGroupId(long companyId, String name) throws Exception {
		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				companyId, StringPool.SLASH + name);

			return group.getGroupId();
		}
		catch (NoSuchGroupException nsge) {
		}

		User user = UserLocalServiceUtil.getUserByScreenName(companyId, name);

		Group group = user.getGroup();

		return group.getGroupId();
	}

	protected void sendDocumentLibrary(
			HttpServletRequest request, HttpServletResponse response, User user,
			String path, String[] pathArray)
		throws Exception {

		long groupId = getGroupId(user.getCompanyId(), pathArray[0]);
		long dlFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (int i = 1; i < pathArray.length; i++) {
			String name = pathArray[i];

			try {
				DLFolder folder = DLFolderServiceUtil.getFolder(
					groupId, dlFolderId, name);

				dlFolderId = folder.getFolderId();
			}
			catch (NoSuchFolderException nsfe) {
				if (i != pathArray.length - 1) {
					throw nsfe;
				}

				String title = name;

				sendFile(response, user, groupId, dlFolderId, title);

				return;
			}
		}

		try {
			sendFile(response, user, groupId, dlFolderId, "index.html");

			return;
		}
		catch (Exception e) {
			if ((e instanceof NoSuchFileEntryException) ||
				(e instanceof PrincipalException)) {

				try {
					sendFile(response, user, groupId, dlFolderId, "index.htm");

					return;
				}
				catch (NoSuchFileEntryException nsfee) {
				}
				catch (PrincipalException pe) {
				}
			}
			else {
				throw e;
			}
		}

		List<WebServerEntry> webServerEntries = new ArrayList<WebServerEntry>();

		webServerEntries.add(new WebServerEntry(path, "../"));

		List<DLFolder> dlFolders = DLFolderServiceUtil.getFolders(
			groupId, dlFolderId);

		for (DLFolder dlFolder : dlFolders) {
			webServerEntries.add(
				new WebServerEntry(
					path, dlFolder.getName() + StringPool.SLASH,
					dlFolder.getCreateDate(), dlFolder.getModifiedDate(),
					dlFolder.getDescription(), 0));
		}

		List<DLFileEntry> dlFileEntries = DLFileEntryServiceUtil.getFileEntries(
			groupId, dlFolderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			webServerEntries.add(
				new WebServerEntry(
					path, dlFileEntry.getTitle(),
					dlFileEntry.getCreateDate(), dlFileEntry.getModifiedDate(),
					dlFileEntry.getDescription(), dlFileEntry.getSize()));
		}

		sendHTML(response, path, webServerEntries);
	}

	protected void sendFile(
			HttpServletRequest request, HttpServletResponse response,
			User user, String[] pathArray)
		throws Exception {

		long groupId = 0;
		long folderId = 0;
		String name = null;
		String fileName = null;

		DLFileEntry dlFileEntry = null;

		if (pathArray.length == 1) {
			long fileShortcutId = GetterUtil.getLong(pathArray[0]);

			DLFileShortcut fileShortcut =
				DLFileShortcutServiceUtil.getFileShortcut(fileShortcutId);

			groupId = fileShortcut.getGroupId();
			folderId = fileShortcut.getToFolderId();
			name = fileShortcut.getToName();

			dlFileEntry = DLFileEntryServiceUtil.getFileEntry(
				groupId, folderId, name);

			fileName = dlFileEntry.getTitle();
		}
		else if (pathArray.length == 2) {
			groupId = GetterUtil.getLong(pathArray[0]);

			dlFileEntry = DLFileEntryServiceUtil.getFileEntryByUuidAndGroupId(
				pathArray[1], groupId);

			folderId = dlFileEntry.getFolderId();
			fileName = dlFileEntry.getTitle();
			name = dlFileEntry.getName();
		}
		else {
			groupId = GetterUtil.getLong(pathArray[0]);
			folderId = GetterUtil.getLong(pathArray[1]);
			fileName = HttpUtil.decodeURL(pathArray[2], true);

			dlFileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
				groupId, folderId, fileName);

			name = dlFileEntry.getName();
		}

		if (dlFileEntry == null) {
			throw new NoSuchFileEntryException();
		}

		String version = ParamUtil.getString(request, "version");

		String targetExtension = ParamUtil.getString(
			request, "targetExtension");

		if (Validator.isNull(version)) {
			if (Validator.isNotNull(dlFileEntry.getVersion())) {
				version = dlFileEntry.getVersion();
			}
			else {
				throw new NoSuchFileEntryException();
			}
		}

		DLFileVersion fileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(
				groupId, folderId, name, version);

		fileName = fileVersion.getTitle();

		InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId, name,
			version);

		boolean converted = false;

		if (Validator.isNotNull(targetExtension)) {
			String id = DocumentConversionUtil.getTempFileId(
				dlFileEntry.getFileEntryId(), version);

			String sourceExtension = FileUtil.getExtension(fileName);

			InputStream convertedIS = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if ((convertedIS != null) && (convertedIS != is)) {
				fileName = FileUtil.stripExtension(fileName).concat(
					StringPool.PERIOD).concat(targetExtension);

				is = convertedIS;

				converted = true;
			}
		}

		int contentLength = 0;

		if (!converted) {
			if (DLUtil.compareVersions(version, dlFileEntry.getVersion()) >=
					0) {

				contentLength = (int)dlFileEntry.getSize();
			}
			else {
				contentLength = (int)fileVersion.getSize();
			}
		}

		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	protected void sendFile(
			HttpServletResponse response, User user, long groupId,
			long folderId, String title)
		throws Exception {

		DLFileEntry dlFileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
			groupId, folderId, title);

		String contentType = MimeTypesUtil.getContentType(
			dlFileEntry.getTitle());

		InputStream inputStream = DLFileEntryLocalServiceUtil.getFileAsStream(
			user.getCompanyId(), user.getUserId(), groupId, folderId,
			dlFileEntry.getName());

		response.setContentType(contentType);

		ServletResponseUtil.write(response, inputStream);
	}

	protected void sendGroups(
			HttpServletResponse response, User user, String path)
		throws Exception {

		List<WebServerEntry> webServerEntries = new ArrayList<WebServerEntry>();

		List<Group> groups = WebDAVUtil.getGroups(user);

		for (Group group : groups) {
			String name = HttpUtil.fixPath(group.getFriendlyURL());

			webServerEntries.add(
				new WebServerEntry(
					path, name + StringPool.SLASH, null, null,
					group.getDescription(), 0));
		}

		sendHTML(response, path, webServerEntries);
	}

	protected void sendHTML(
			HttpServletResponse response, String path,
			List<WebServerEntry> webServerEntries)
		throws Exception {

		FreeMarkerContext freeMarkerContext =
			FreeMarkerEngineUtil.getWrappedRestrictedToolsContext();

		freeMarkerContext.put("dateFormat", _dateFormat);
		freeMarkerContext.put("entries", webServerEntries);
		freeMarkerContext.put("path", HttpUtil.encodePath(path));
		freeMarkerContext.put("serverInfo", ReleaseInfo.getServerInfo());
		freeMarkerContext.put("validator", Validator_IW.getInstance());

		String html = FreeMarkerUtil.process(_TPL_TEMPLATE, freeMarkerContext);

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		ServletResponseUtil.write(response, html);
	}

	private static final String _DATE_FORMAT_PATTERN = "d MMM yyyy HH:mm z";

	private static final String _TPL_TEMPLATE =
		"com/liferay/portal/webserver/dependencies/template.ftl";

	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT_PATTERN);

}