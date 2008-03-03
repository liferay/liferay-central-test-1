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

package com.liferay.portal.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import javax.servlet.http.HttpSession;

/**
 * <a href="SharedSessionAttributeCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class SharedSessionAttributeCache implements Serializable {

	public static SharedSessionAttributeCache getInstance(HttpSession ses) {
		synchronized (ses) {
			SharedSessionAttributeCache cache =
				(SharedSessionAttributeCache)ses.getAttribute(_SESSION_KEY);

			if (cache == null) {
				cache = new SharedSessionAttributeCache();

				ses.setAttribute(_SESSION_KEY, cache);
			}

			return cache;
		}
	}

	public boolean contains(String name) {
		return _attributes.containsKey(name);
	}

	public Map<String, Object> getValues() {
		Map<String, Object> values = new HashMap<String, Object>();

		Set<Map.Entry<String, Object>> entries = _attributes.entrySet();

		for (Map.Entry<String, Object> entry : entries) {
			values.put(entry.getKey(), entry.getValue());
		}

		return values;
	}

	public void removeAttribute(String key) {
		_attributes.remove(key);
	}

	public void setAttribute(String key, Object value) {
		_attributes.put(key, value);
	}

	private SharedSessionAttributeCache() {
		_attributes = new ConcurrentHashMap<String, Object>();
	}

	private static final String _SESSION_KEY =
		SharedSessionAttributeCache.class.getName();

	private transient Map<String, Object> _attributes;

}