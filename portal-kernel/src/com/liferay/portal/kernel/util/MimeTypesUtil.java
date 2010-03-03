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

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.InputStream;

/**
 * <a href="MimeTypesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MimeTypesUtil {

	public static String getContentType(File file) {
		return getMimeTypes().getContentType(file);
	}

	/**
	 * Determine the content type from an input stream and file name.
	 *
	 * @param  fileName full name or extension of file (e.g., "Test.doc",
	 *		   ".doc")
	 * @return content type if it is a supported format or an empty string if it
	 *		   is an unsupported format
	 */
	public static String getContentType(
		InputStream inputStream, String fileName) {

		return getMimeTypes().getContentType(inputStream, fileName);
	}

	/**
	 * Determine the content type from a file name.
	 *
	 * @param  fileName full name or extension of file (e.g., "Test.doc",
	 *		   ".doc")
	 * @return content type if it is a supported format or an empty string if it
	 *		   is an unsupported format
	 */
	public static String getContentType(String fileName) {
		return getMimeTypes().getContentType(fileName);
	}

	public static MimeTypes getMimeTypes() {
		return _mimeTypes;
	}

	public void setMimeTypes(MimeTypes mimeTypes) {
		_mimeTypes = mimeTypes;
	}

	private static MimeTypes _mimeTypes;

}