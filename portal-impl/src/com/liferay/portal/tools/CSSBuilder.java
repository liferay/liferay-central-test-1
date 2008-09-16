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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

/**
 * <a href="CSSBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CSSBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new CSSBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public CSSBuilder(String cssDir, String mergedFile) {
		try {
			File mainCssFile = new File(cssDir + "/main.css");

			if (!mainCssFile.exists()) {
				System.out.println(
					"Do not pack " + cssDir +
						"/main.css because it does not exist.");

				return;
			}

			String content = FileUtil.read(mainCssFile);

			content = replaceImports(cssDir, content, 0);

			FileUtil.write(mergedFile, content);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String replaceImports(String cssDir, String s, int level)
		throws Exception {

		StringBuilder sb = new StringBuilder(s.length());

		int pos = 0;

		while (true) {
			int x = s.indexOf(_BEGIN, pos);
			int y = s.indexOf(_END, x + _BEGIN.length());

			if ((x == -1) || (y == -1)) {
				sb.append(s.substring(pos, s.length()));

				break;
			}
			else {
				sb.append(s.substring(pos, x));

				String importFile = s.substring(x + _BEGIN.length(), y);

				String importContent = FileUtil.read(cssDir + "/" + importFile);

				String importFilePath = "";

				if (importFile.lastIndexOf("/") != -1) {
					importFilePath = "/" + importFile.substring(
						0, importFile.lastIndexOf("/") + 1);
				}

				importContent = replaceImports(
					cssDir + importFilePath, importContent, level + 1);

				// LEP-7540

				String relativePath = StringPool.BLANK;

				for (int i = 0; i < level; i++) {
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

				pos = y + _END.length();
			}
		}

		return sb.toString();
	}

	private static final String _BEGIN = "@import url(";

	private static final String _END = ");";

}