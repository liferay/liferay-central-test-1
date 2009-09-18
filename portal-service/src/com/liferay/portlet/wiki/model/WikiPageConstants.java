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

package com.liferay.portlet.wiki.model;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * <a href="WikiPageConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class WikiPageConstants {

	public static final String DEFAULT_FORMAT =
		PropsUtil.get(PropsKeys.WIKI_FORMATS_DEFAULT);

	public static final double DEFAULT_VERSION = 1.0;

	public static final String[] FORMATS =
		PropsUtil.getArray(PropsKeys.WIKI_FORMATS);

	public static final String FRONT_PAGE =
		PropsUtil.get(PropsKeys.WIKI_FRONT_PAGE_NAME);

	public static final String MOVED = "Moved";

	public static final String NEW = "New";

	public static final String REVERTED = "Reverted";

}
