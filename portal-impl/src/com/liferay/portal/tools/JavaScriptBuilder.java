/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsUtil;

/**
 * <a href="JavaScriptBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JavaScriptBuilder {

	public static void main(String[] args) {
		if (args.length == 3) {
			new JavaScriptBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public JavaScriptBuilder(
		String jsProperty, String jsDir, String mergedFile) {

		try {
			StringBuilder sb = new StringBuilder();

			String[] files = PropsUtil.getArray(jsProperty);

			for (int i = 0; i < files.length; i++) {
				String content = _fileUtil.read(jsDir + files[i]);

				sb.append(content);
				sb.append("\n");
			}

			_fileUtil.write(mergedFile, sb.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}