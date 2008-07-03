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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;

import java.io.Writer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JSONObjectImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JSONObjectImpl implements JSONObject {

	public JSONObjectImpl() {
		_jsonObj = new org.json.JSONObject();
	}

	public JSONObjectImpl(JSONObject jsonObj, String[] names)
		throws JSONException {

		try {
			JSONObjectImpl jsonObjImpl = (JSONObjectImpl)jsonObj;

			_jsonObj = new org.json.JSONObject(
				jsonObjImpl.getJSONObject(), names);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(Map<?, ?> map) {
		_jsonObj = new org.json.JSONObject(map);
	}

	public JSONObjectImpl(Object bean) {
		_jsonObj = new org.json.JSONObject(bean);
	}

	public JSONObjectImpl(Object obj, String[] names) {
		_jsonObj = new org.json.JSONObject(obj, names);
	}

	public JSONObjectImpl(String json) throws JSONException {
		try {
			_jsonObj = new org.json.JSONObject(json);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(org.json.JSONObject jsonObj) {
		_jsonObj = jsonObj;
	}

	public boolean getBoolean(String key) {
		return _jsonObj.optBoolean(key);
	}

	public double getDouble(String key) {
		return _jsonObj.optDouble(key);
	}

	public int getInt(String key) {
		return _jsonObj.optInt(key);
	}

	public JSONArray getJSONArray(String key) {
		org.json.JSONArray jsonArray = _jsonObj.optJSONArray(key);

		if (jsonArray == null) {
			return null;
		}

		return new JSONArrayImpl(jsonArray);
	}

	public org.json.JSONObject getJSONObject() {
		return _jsonObj;
	}

	public JSONObject getJSONObject(String key) {
		org.json.JSONObject jsonObj = _jsonObj.optJSONObject(key);

		if (jsonObj == null) {
			return null;
		}

		return new JSONObjectImpl(jsonObj);
	}

	public long getLong(String key) {
		return _jsonObj.optLong(key);
	}

	public String getString(String key) {
		return _jsonObj.optString(key);
	}

	public boolean has(String key) {
		return _jsonObj.has(key);
	}

	public boolean isNull(String key) {
		return _jsonObj.isNull(key);
	}

	public Iterator<String> keys() {
		return _jsonObj.keys();
	}

	public int length() {
		return _jsonObj.length();
	}

	public JSONObject put(String key, boolean value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, double value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, int value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, long value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, Date value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONArray value) {
		try {
			_jsonObj.put(key, ((JSONArrayImpl)value).getJSONArray());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONObject value) {
		try {
			_jsonObj.put(key, ((JSONObjectImpl)value).getJSONObject());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public JSONObject put(String key, String value) {
		try {
			_jsonObj.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return this;
	}

	public Object remove(String key) {
		return _jsonObj.remove(key);
	}

	public String toString() {
		return _jsonObj.toString();
	}

	public String toString(int indentFactor) throws JSONException {
		try {
			return _jsonObj.toString(indentFactor);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public Writer write(Writer writer) throws JSONException {
		try {
			return _jsonObj.write(writer);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	private static Log _log = LogFactory.getLog(JSONObjectImpl.class);

	private org.json.JSONObject _jsonObj;

}