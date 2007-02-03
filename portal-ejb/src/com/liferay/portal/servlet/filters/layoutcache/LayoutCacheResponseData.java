/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.layoutcache;

import java.io.Serializable;

import java.util.Map;

/**
 * <a href="LayoutCacheResponseData.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class LayoutCacheResponseData implements Serializable {

	public LayoutCacheResponseData(byte[] data, String contentType,
								   Map headers) {

		super();

		_data = data;
		_contentType = contentType;
		_headers = headers;
	}

	public byte[] getData() {
		return _data;
	}

	public String getContentType() {
		return _contentType;
	}

	public Map getHeaders() {
		return _headers;
	}

	private byte[] _data;
	private String _contentType;
	private Map _headers;

}