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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.util.Set;

import javax.servlet.ServletContext;

/**
 * <a href="ServletContextUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ServletContextUtil {

	public static long getLastModified(ServletContext servletContext) {
		return getLastModified(servletContext, StringPool.SLASH);
	}

	public static long getLastModified(
		ServletContext servletContext, String resourcePath) {

		return getLastModified(servletContext, resourcePath, false);
	}

	public static long getLastModified(
		ServletContext servletContext, String resourcePath, boolean cache) {

		if (resourcePath.startsWith(Http.HTTP) ||
			resourcePath.startsWith(Http.HTTPS)) {

			int pos = resourcePath.indexOf(Http.PROTOCOL_DELIMITER);

			resourcePath = resourcePath.substring(pos + 3);
		}

		int pos = resourcePath.indexOf(StringPool.SLASH);

		if (pos != -1) {
			resourcePath = resourcePath.substring(pos);
		}

		if (cache) {
			Long lastModified = (Long)servletContext.getAttribute(
				ServletContextUtil.class.getName() + StringPool.PERIOD +
					resourcePath);

			if (lastModified != null) {
				return lastModified.longValue();
			}
		}

		long lastModified = 0;

		Set<String> resourcePaths = servletContext.getResourcePaths(
			resourcePath);

		if (resourcePaths != null) {
			for (String curResourcePath : resourcePaths) {
				if (curResourcePath.endsWith(StringPool.SLASH)) {
					long curLastModified = getLastModified(
						servletContext, curResourcePath);

					if (curLastModified > lastModified) {
						lastModified = curLastModified;
					}
				}
				else {
					String realPath = servletContext.getRealPath(
						curResourcePath);

					if (realPath == null) {
						continue;
					}

					File file = new File(realPath);

					if (file.lastModified() > lastModified) {
						lastModified = file.lastModified();
					}
				}
			}
		}

		if (cache) {
			servletContext.setAttribute(
				ServletContextUtil.class.getName() + StringPool.PERIOD +
					resourcePath,
				new Long(lastModified));
		}

		return lastModified;
	}

	public static String getRealPath(
		ServletContext servletContext, String path) {

		String realPath = servletContext.getRealPath(path);

		if ((realPath == null) && ServerDetector.isWebLogic()) {
			String rootDir = getRootDir(servletContext);

			if (path.startsWith(StringPool.SLASH)) {
				realPath = rootDir + path.substring(1);
			}
			else {
				realPath = rootDir + path;
			}

			if (!FileUtil.exists(realPath)) {
				realPath = null;
			}
		}

		return realPath;
	}

	protected static String getRootDir(ServletContext servletContext) {
		String key = ServletContextUtil.class.getName() + ".rootDir";

		String rootDir = (String)servletContext.getAttribute(key);

		if (rootDir == null) {
			ClassLoader classLoader = (ClassLoader)servletContext.getAttribute(
				PortletServlet.PORTLET_CLASS_LOADER);

			if (classLoader == null) {
				classLoader = PortalClassLoaderUtil.getClassLoader();
			}

			rootDir = WebDirDetector.getRootDir(classLoader);

			servletContext.setAttribute(key, rootDir);
		}

		return rootDir;
	}

}