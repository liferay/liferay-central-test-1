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

package com.liferay.imagegallery.util;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.model.Image;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * <a href="DatabaseHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class DatabaseHook extends BaseHook {

	public DatabaseHook() {
		_rootDir = new File(_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	public void deleteImage(Image image)
		throws PortalException, SystemException {
	}

	public byte[] getImageAsBytes(Image image) {
		return (byte[]) Base64.stringToObject(image.getText());
	}

	public InputStream getImageAsStream(Image image)
		throws PortalException, SystemException {

		return new ByteArrayInputStream(image.getTextObj());
	}

	public void updateImage(Image image, String type, byte[] bytes)
		throws SystemException {

		image.setTextObj(bytes);
	}

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.IG_HOOK_FILE_SYSTEM_ROOT_DIR);

	private File _rootDir;

}