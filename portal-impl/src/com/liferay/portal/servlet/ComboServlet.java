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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ComboServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Eduardo Lundgren
 */
public class ComboServlet extends HttpServlet {

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String[] modulePaths = request.getParameterValues("m");

		if (modulePaths.length == 0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);

			return;
		}

		String minifierType = ParamUtil.getString(request, "minifierType");

		int length = modulePaths.length;

		byte[][] bytesArray = new byte[length][];

		for (String modulePath : modulePaths) {
			bytesArray[--length] = getFileContent(_JAVASCRIPT_DIR + modulePath, minifierType);
		}

		String contentType = ContentTypes.TEXT_JAVASCRIPT;

		String firstModulePath =
			(String)request.getParameterNames().nextElement();

		String extension = FileUtil.getExtension(firstModulePath);

		if (extension.equalsIgnoreCase(_CSS_EXTENSION)) {
			contentType = ContentTypes.TEXT_CSS;
		}

		response.setContentType(contentType);

		ServletResponseUtil.write(response, bytesArray);
	}

	protected File getFile(String path) throws IOException {
		ServletContext servletContext = getServletContext();

		String basePath = ServletContextUtil.getRealPath(
			servletContext, _JAVASCRIPT_DIR);

		if (basePath == null) {
			return null;
		}

		basePath = StringUtil.replace(
			basePath, StringPool.BACK_SLASH, StringPool.SLASH);

		File baseDir = new File(basePath);

		if (!baseDir.exists()) {
			return null;
		}

		String filePath = ServletContextUtil.getRealPath(servletContext, path);

		if (filePath == null) {
			return null;
		}

		filePath = StringUtil.replace(
			filePath, StringPool.BACK_SLASH, StringPool.SLASH);

		File file = new File(filePath);

		if (!file.exists()) {
			return null;
		}

		String baseCanonicalPath = baseDir.getCanonicalPath();
		String fileCanonicalPath = file.getCanonicalPath();

		if (fileCanonicalPath.indexOf(baseCanonicalPath) == 0) {
			return file;
		}

		return null;
	}

	protected byte[] getFileContent(String path, String minifierType)
		throws IOException {

		String fileContentKey = path.concat(StringPool.QUESTION).concat(
			minifierType);

		FileContentBag fileContentBag = _fileContents.get(fileContentKey);

		if ((fileContentBag != null) &&
			!PropsValues.COMBO_CHECK_TIMESTAMP) {

			return fileContentBag._fileContent;
		}

		File file = getFile(path);

		if ((fileContentBag != null) && PropsValues.COMBO_CHECK_TIMESTAMP) {
			if ((file != null) &&
				(file.lastModified() == fileContentBag._lastModified)) {

				return fileContentBag._fileContent;
			}
			else {
				_fileContents.remove(fileContentKey, fileContentBag);
			}
 		}

		if (file == null) {
			fileContentBag = _EMPTY_FILE_CONTENT_BAG;
		}
		else {
			String stringFileContent = FileUtil.read(file);

			if (minifierType.equals("css")) {
				stringFileContent = MinifierUtil.minifyCss(
					stringFileContent);
			}
			else if (minifierType.equals("js")) {
				stringFileContent = MinifierUtil.minifyJavaScript(
					stringFileContent);
			}

			fileContentBag = new FileContentBag(
				stringFileContent.getBytes(StringPool.UTF8),
				file.lastModified());
		}

		FileContentBag oldFileContentBag = _fileContents.putIfAbsent(
			fileContentKey, fileContentBag);

		if (oldFileContentBag != null) {
			fileContentBag = oldFileContentBag;
		}

		return fileContentBag._fileContent;
	}

	private static final String _CSS_EXTENSION = "css";

	private static final FileContentBag _EMPTY_FILE_CONTENT_BAG =
		new FileContentBag(new byte[0], 0);

	private static final String _JAVASCRIPT_DIR = "html/js";

	private ConcurrentMap<String, FileContentBag> _fileContents =
		new ConcurrentHashMap<String, FileContentBag>();

	private static class FileContentBag {

		public FileContentBag(byte[] fileContent, long lastModifiedTime) {
			_fileContent = fileContent;
			_lastModified = lastModifiedTime;
		}

		private byte[] _fileContent;
		private long _lastModified;

	}

}