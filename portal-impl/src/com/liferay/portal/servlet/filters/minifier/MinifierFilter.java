/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.servlet.filters.minifier;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.servlet.filters.etag.ETagUtil;
import com.liferay.portal.util.MinifierUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SystemProperties;
import com.liferay.util.servlet.ServletResponseUtil;
import com.liferay.util.servlet.filters.CacheResponse;
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

		String cacheFileName = _tempDir + request.getRequestURI();

		String queryString = request.getQueryString();

		if (queryString != null) {
			cacheFileName += _QUESTION_SEPARATOR + queryString;
		}

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

		StringBuilder sb = new StringBuilder();

		for (String fileName : fileNames) {
			String content = FileUtil.read(
				bundleDirRealPath + StringPool.SLASH + fileName);

			sb.append(content);
			sb.append(StringPool.NEW_LINE);
		}

		String minifiedContent = minifyJavaScript(sb.toString());

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

		String realPath = ServletContextUtil.getRealPath(
			_servletContext, requestURI);

		if (realPath == null) {
			return null;
		}

		realPath = StringUtil.replace(
			realPath, StringPool.BACK_SLASH, StringPool.SLASH);

		File file = new File(realPath);

		if (!file.exists()) {
			if (Validator.isNotNull(_servletContextName)) {

				// Tomcat incorrectly returns the a real path to a resource that
				// exists in another web application. For example, it returns
				// ".../webapps/abc-theme/abc-theme/css/main.css" instead of
				// ".../webapps/abc-theme/css/main.css".

				int lastIndex = realPath.lastIndexOf(
					StringPool.SLASH + _servletContextName);

				if (lastIndex > -1) {
					realPath = StringUtil.replace(
						realPath, StringPool.SLASH + _servletContextName,
						StringPool.BLANK, lastIndex);
				}

				file = new File(realPath);
			}
		}

		if (!file.exists()) {
			return null;
		}

		String minifiedContent = null;

		String cacheCommonFileName = _tempDir + requestURI;

		String queryString = request.getQueryString();

		if (queryString != null) {
			cacheCommonFileName += _QUESTION_SEPARATOR + queryString;
		}

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

				CacheResponse cacheResponse = new CacheResponse(
					response, StringPool.UTF8);

				processFilter(
					MinifierFilter.class, request, cacheResponse, filterChain);

				CacheResponseUtil.addHeaders(
					response, cacheResponse.getHeaders());

				response.setContentType(cacheResponse.getContentType());

				minifiedContent = new String(
					cacheResponse.getData(), StringPool.UTF8);

				if (minifierType.equals("css")) {
					minifiedContent = minifyCss(request, minifiedContent);
				}
				else if (minifierType.equals("js")) {
					minifiedContent = minifyJavaScript(minifiedContent);
				}

				FileUtil.write(
					cacheContentTypeFile, cacheResponse.getContentType());
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