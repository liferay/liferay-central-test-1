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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.HashMap;

/**
 * <a href="UnicodeProperties.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This is a rewrite of java.util.Properties that is not synchronized and
 * natively supports non-ASCII encodings.  It can also be configured to be
 * "safe," allowing the values to have new line characters.  When stored to a
 * given BufferedWriter, "safe" properties will replace all new line characters
 * with a _SAFE_NEWLINE_CHARACTER_.
 * </p>
 *
 * <p>
 * In its current form, this is not intended to replace java.util.Properties for
 * reading properties flat files.
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class UnicodeProperties extends HashMap<String, String> {

	public UnicodeProperties() {
		super();
	}

	public UnicodeProperties(boolean safe) {
		super();

		_safe = safe;
	}

	public String getProperty(String key) {
		return get(key);
	}

	public String getProperty(String key, String defaultValue) {
		if (containsKey(key)) {
			return getProperty(key);
		}
		else {
			return defaultValue;
		}
	}

	public boolean isSafe() {
		return _safe;
	}

	public void load(String props) throws IOException {
		if (Validator.isNull(props)) {
			return;
		}

		BufferedReader br = null;

		try {
			br = new BufferedReader(new StringReader(props));

	        String line = br.readLine();

	        while (line != null) {
	        	line = line.trim();

	        	if (_isComment(line)) {
		        	line = br.readLine();

		        	continue;
	        	}

	        	int i = line.indexOf(StringPool.EQUAL);

	        	if (i != -1) {
	        		String key = line.substring(0, i).trim();
	        		String value = line.substring(i + 1).trim();

	        		if (_safe) {
	        			value = _decode(value);
	        		}

	        		setProperty(key, value);
	        	}
	        	else {
	        		_log.error("Invalid line in properties '" + line + "'");
	        	}

	        	line = br.readLine();
	        }
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (Exception e) {
				}
			}
		}
	}

	public String remove(Object key) {
		if (key == null) {
			return null;
		}
		else {
			return super.remove(key);
		}
	}

	public String put(String key, String value) {
		if (key == null) {
			return null;
		}
		else {
			if (value == null) {
				return remove(key);
			}
			else {
				return super.put(key, value);
			}
		}
	}

	public String setProperty(String key, String value) {
		return put(key, value);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (String key : keySet()) {
			String value = get(key);

			if (Validator.isNotNull(value)) {
				if (_safe) {
					value = _encode(value);
				}

				sb.append(key + StringPool.EQUAL + value + StringPool.NEW_LINE);
			}
		}

		return sb.toString();
	}

	private static String _decode(String value) {
		return StringUtil.replace(
			value, _SAFE_NEWLINE_CHARACTER, StringPool.NEW_LINE);
	}

	private static String _encode(String value) {
		return StringUtil.replace(
			value,
			new String[] {
				StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE,
				StringPool.RETURN
			},
			new String[] {
				_SAFE_NEWLINE_CHARACTER, _SAFE_NEWLINE_CHARACTER,
				_SAFE_NEWLINE_CHARACTER
			});
	}

	private boolean _isComment(String line) {
		return line.length() == 0 || line.startsWith(StringPool.POUND);
	}

	private boolean _safe = false;

	private static final String _SAFE_NEWLINE_CHARACTER =
		"_SAFE_NEWLINE_CHARACTER_";

	private static Log _log = LogFactoryUtil.getLog(UnicodeProperties.class);

}