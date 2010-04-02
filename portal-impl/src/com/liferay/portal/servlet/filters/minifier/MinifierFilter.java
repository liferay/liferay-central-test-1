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

package com.liferay.portal.servlet.filters.minifier;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.etag.ETagUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SystemProperties;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="MinifierFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MinifierFilter extends BasePortalFilter {

	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);

		_servletContext = filterConfig.getServletContext();
		_servletContextName = GetterUtil.getString(
			_servletContext.getServletContextName());

		if (Validator.isNull(_servletContextName)) {
			_tempDir += "/portal";
		}
	}

	protected String aggregateCss(String dir, String content)
		throws IOException {

		StringBuilder sb = new StringBuilder(content.length());

		int pos = 0;

		while (true) {
			int x = content.indexOf(_CSS_IMPORT_BEGIN, pos);
			int y = content.indexOf(
				_CSS_IMPORT_END, x + _CSS_IMPORT_BEGIN.length());

			if ((x == -1) || (y == -1)) {
				sb.append(content.substring(pos, content.length()));

				break;
			}
			else {
				sb.append(content.substring(pos, x));

				String importFile = content.substring(
					x + _CSS_IMPORT_BEGIN.length(), y);

				String importContent = FileUtil.read(
					dir + StringPool.SLASH + importFile);

				String importFilePath = StringPool.BLANK;

				if (importFile.lastIndexOf(StringPool.SLASH) != -1) {
					importFilePath = StringPool.SLASH + importFile.substring(
						0, importFile.lastIndexOf(StringPool.SLASH) + 1);
				}

				importContent = aggregateCss(
					dir + importFilePath, importContent);

				int importDepth = StringUtil.count(
					importFile, StringPool.SLASH);

				// LEP-7540

				String relativePath = StringPool.BLANK;

				for (int i = 0; i < importDepth; i++) {
					relativePath += "../";
				}

				importContent = StringUtil.replace(
					importContent,
					new String[] {
						"url('" + relativePath,
						"url(\"" + relativePath,
						"url(" + relativePath
					},
					new String[] {
						"url('[$TEMP_RELATIVE_PATH$]",
						"url(\"[$TEMP_RELATIVE_PATH$]",
						"url([$TEMP_RELATIVE_PATH$]"
					});

				importContent = StringUtil.replace(
					importContent, "[$TEMP_RELATIVE_PATH$]", StringPool.BLANK);

				sb.append(importContent);

				pos = y + _CSS_IMPORT_END.length();
			}
		}

		return sb.toString();
	}

	protected String getMinifiedBundleContent(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		String minifierType = ParamUtil.getString(request, "minifierType");
		String minifierBundleId = ParamUtil.getString(
			request, "minifierBundleId");

		if (Validator.isNull(minifierType) ||
			Validator.isNull(minifierBundleId) ||
			!ArrayUtil.contains(
				PropsValues.JAVASCRIPT_BUNDLE_IDS, minifierBundleId)) {

			return null;
		}

		String minifierBundleDir = PropsUtil.get(
			PropsKeys.JAVASCRIPT_BUNDLE_DIR, new Filter(minifierBundleId));

		String bundleDirRealPath = ServletContextUtil.getRealPath(
			_servletContext, minifierBundleDir);

		if (bundleDirRealPath == null) {
			return null;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_tempDir);
		sb.append(request.getRequestURI());

		String queryString = request.getQueryString();

		if (queryString != null) {
			sb.append(_QUESTION_SEPARATOR);
			sb.append(sterilizeQueryString(queryString));
		}

		String cacheFileName = sb.toString();

		String[] fileNames = PropsUtil.getArray(minifierBundleId);

		File cacheFile = new File(cacheFileName);

		if (cacheFile.exists()) {
			boolean staleCache = false;

			for (String fileName : fileNames) {
				File file = new File(
					bundleDirRealPath + StringPool.SLASH + fileName);

				if (file.lastModified() > cacheFile.lastModified()) {
					staleCache = true;

					break;
				}
			}

			if (!staleCache) {
				response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

				return FileUtil.read(cacheFile);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Minifying JavaScript bundle " + minifierBundleId);
		}

		String minifiedContent = null;

		if (fileNames.length == 0) {
			minifiedContent = StringPool.BLANK;
		}
		else {
			sb = new StringBundler(fileNames.length * 2);

			for (String fileName : fileNames) {
				String content = FileUtil.read(
					bundleDirRealPath + StringPool.SLASH + fileName);

				sb.append(content);
				sb.append(StringPool.NEW_LINE);
			}

			minifiedContent = minifyJavaScript(sb.toString());
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		FileUtil.write(cacheFile, minifiedContent);

		return minifiedContent;
	}

	protected String getMinifiedContent(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String minifierType = ParamUtil.getString(request, "minifierType");
		String minifierBundleId = ParamUtil.getString(
			request, "minifierBundleId");
		String minifierBundleDir = ParamUtil.getString(
			request, "minifierBundleDir");

		if (Validator.isNull(minifierType) ||
			Validator.isNotNull(minifierBundleId) ||
			Validator.isNotNull(minifierBundleDir)) {

			return null;
		}

		String requestURI = request.getRequestURI();

		String requestPath = requestURI;

		String contextPath = request.getContextPath();

		if (!contextPath.equals(StringPool.SLASH)) {
			requestPath = requestPath.substring(contextPath.length());
		}

		String realPath = ServletContextUtil.getRealPath(
			_servletContext, requestPath);

		if (realPath == null) {
			return null;
		}

		realPath = StringUtil.replace(
			realPath, StringPool.BACK_SLASH, StringPool.SLASH);

		File file = new File(realPath);

		if (!file.exists()) {
			return null;
		}

		String minifiedContent = null;

		StringBundler sb = new StringBundler(4);

		sb.append(_tempDir);
		sb.append(requestURI);

		String queryString = request.getQueryString();

		if (queryString != null) {
			sb.append(_QUESTION_SEPARATOR);
			sb.append(sterilizeQueryString(queryString));
		}

		String cacheCommonFileName = sb.toString();

		File cacheContentTypeFile = new File(
			cacheCommonFileName + "_E_CONTENT_TYPE");
		File cacheDataFile = new File(cacheCommonFileName + "_E_DATA");

		if ((cacheDataFile.exists()) &&
			(cacheDataFile.lastModified() >= file.lastModified())) {

			minifiedContent = FileUtil.read(cacheDataFile);

			if (cacheContentTypeFile.exists()) {
				String contentType = FileUtil.read(cacheContentTypeFile);

				response.setContentType(contentType);
			}
		}
		else {
			if (realPath.endsWith(_CSS_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Minifying CSS " + file);
				}

				minifiedContent = minifyCss(request, file);

				response.setContentType(ContentTypes.TEXT_CSS);

				FileUtil.write(cacheContentTypeFile, ContentTypes.TEXT_CSS);
			}
			else if (realPath.endsWith(_JAVASCRIPT_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Minifying JavaScript " + file);
				}

				minifiedContent = minifyJavaScript(file);

				response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

				FileUtil.write(
					cacheContentTypeFile, ContentTypes.TEXT_JAVASCRIPT);
			}
			else if (realPath.endsWith(_JSP_EXTENSION)) {
				if (_log.isInfoEnabled()) {
					_log.info("Minifying JSP " + file);
				}

				StringServletResponse stringResponse =
					new StringServletResponse(response);

				processFilter(
					MinifierFilter.class, request, stringResponse, filterChain);

				CacheResponseUtil.addHeaders(
					response, stringResponse.getHeaders());

				response.setContentType(stringResponse.getContentType());

				minifiedContent = stringResponse.getString();

				if (minifierType.equals("css")) {
					minifiedContent = minifyCss(request, minifiedContent);
				}
				else if (minifierType.equals("js")) {
					minifiedContent = minifyJavaScript(minifiedContent);
				}

				FileUtil.write(
					cacheContentTypeFile, stringResponse.getContentType());
			}
			else {
				return null;
			}

			FileUtil.write(cacheDataFile, minifiedContent);
		}

		return minifiedContent;
	}

	protected String minifyCss(HttpServletRequest request, File file)
		throws IOException {

		String content = FileUtil.read(file);

		content = aggregateCss(file.getParent(), content);

		return minifyCss(request, content);
	}

	protected String minifyCss(HttpServletRequest request, String content) {
		String browserId = ParamUtil.getString(request, "browserId");

		if (!browserId.equals(BrowserSniffer.BROWSER_ID_IE)) {
			Matcher matcher = _pattern.matcher(content);

			content = matcher.replaceAll(StringPool.BLANK);
		}

		return MinifierUtil.minifyCss(content);
	}

	protected String minifyJavaScript(File file) throws IOException {
		String content = FileUtil.read(file);

		return minifyJavaScript(content);
	}

	protected String minifyJavaScript(String content) {
		return MinifierUtil.minifyJavaScript(content);
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		String minifiedContent = getMinifiedContent(
			request, response, filterChain);

		if (Validator.isNull(minifiedContent)) {
			minifiedContent = getMinifiedBundleContent(request, response);
		}

		if (Validator.isNull(minifiedContent)) {
			processFilter(MinifierFilter.class, request, response, filterChain);
		}
		else {
			if (!ETagUtil.processETag(request, response, minifiedContent)) {
				ServletResponseUtil.write(response, minifiedContent);
			}
		}
	}

	protected String sterilizeQueryString(String queryString) {
		return StringUtil.replace(
			queryString,
			new String[] {StringPool.SLASH, StringPool.BACK_SLASH},
			new String[] {StringPool.UNDERLINE, StringPool.UNDERLINE});
	}

	private static final String _CSS_IMPORT_BEGIN = "@import url(";

	private static final String _CSS_IMPORT_END = ");";

	private static final String _CSS_EXTENSION = ".css";

	private static final String _JAVASCRIPT_EXTENSION = ".js";

	private static final String _JSP_EXTENSION = ".jsp";

	private static final String _QUESTION_SEPARATOR = "_Q_";

	private static final String _TEMP_DIR =
		SystemProperties.get(SystemProperties.TMP_DIR) + "/liferay/minifier";

	private static Log _log = LogFactoryUtil.getLog(MinifierFilter.class);

	private static Pattern _pattern = Pattern.compile(
		"^(\\.ie|\\.js\\.ie)([^}]*)}", Pattern.MULTILINE);

	private ServletContext _servletContext;
	private String _servletContextName;
	private String _tempDir = _TEMP_DIR;

}