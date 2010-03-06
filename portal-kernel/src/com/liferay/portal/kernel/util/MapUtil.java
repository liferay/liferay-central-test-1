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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Constructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <a href="MapUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class MapUtil {

	public static<K, V> void copy(
		Map<K, V> master, Map<? super K, ? super V> copy) {

		copy.clear();

		merge(master, copy);
	}

	public static boolean getBoolean(Map<String, ?> map, String key) {
		return getBoolean(map, key, GetterUtil.DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(
		Map<String, ?> map, String key, boolean defaultValue) {

		return GetterUtil.getBoolean(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static int getInteger(Map<String, ?> map, String key) {
		return getInteger(map, key, GetterUtil.DEFAULT_INTEGER);
	}

	public static int getInteger(
		Map<String, ?> map, String key, int defaultValue) {

		return GetterUtil.getInteger(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static long getLong(Map<Long, Long> map, long key) {
		return getLong(map, key, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Map<Long, Long> map, long key, long defaultValue) {

		Long keyObj = new Long(key);

		if (map.containsKey(keyObj)) {
			return map.get(keyObj);
		}

		return defaultValue;
	}

	public static long getLong(Map<String, ?> map, String key) {
		return getLong(map, key, GetterUtil.DEFAULT_LONG);
	}

	public static long getLong(
		Map<String, ?> map, String key, long defaultValue) {

		return GetterUtil.getLong(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static short getShort(Map<String, ?> map, String key) {
		return getShort(map, key, GetterUtil.DEFAULT_SHORT);
	}

	public static short getShort(
		Map<String, ?> map, String key, short defaultValue) {

		return GetterUtil.getShort(
			getString(map, key, String.valueOf(defaultValue)), defaultValue);
	}

	public static String getString(Map<String, ?> map, String key) {
		return getString(map, key, GetterUtil.DEFAULT_STRING);
	}

	public static String getString(
		Map<String, ?> map, String key, String defaultValue) {

		if (map.containsKey(key)) {
			Object value = map.get(key);

			if (value instanceof String[]) {
				String[] array = (String[])value;

				if (array.length > 0) {
					return GetterUtil.getString(array[0], defaultValue);
				}
			}
			else if (value instanceof String) {
				return GetterUtil.getString((String)value, defaultValue);
			}
		}

		return defaultValue;
	}

	public static<K, V> void merge(
		Map<K, V> master, Map<? super K, ? super V> copy) {

		copy.putAll(master);
	}

	public static<T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params) {

		return toLinkedHashMap(params, StringPool.COLON);
	}

	public static<T> LinkedHashMap<String, T> toLinkedHashMap(
		String[] params, String delimiter) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		for (int i = 0; i < params.length; i++) {
			String[] kvp = StringUtil.split(params[i], delimiter);

			if (kvp.length == 2) {
				map.put(kvp[0], kvp[1]);
			}
			else if (kvp.length == 3) {
				String type = kvp[2];

				if (type.equalsIgnoreCase("boolean") ||
					type.equals(Boolean.class.getName())) {

					map.put(kvp[0], new Boolean(kvp[1]));
				}
				else if (type.equalsIgnoreCase("double") ||
					type.equals(Double.class.getName())) {

					map.put(kvp[0], new Double(kvp[1]));
				}
				else if (type.equalsIgnoreCase("int") ||
					type.equals(Integer.class.getName())) {

					map.put(kvp[0], new Integer(kvp[1]));
				}
				else if (type.equalsIgnoreCase("long") ||
					type.equals(Long.class.getName())) {

					map.put(kvp[0], new Long(kvp[1]));
				}
				else if (type.equalsIgnoreCase("short") ||
					type.equals(Short.class.getName())) {

					map.put(kvp[0], new Short(kvp[1]));
				}
				else if (type.equals(String.class.getName())) {
					map.put(kvp[0], kvp[1]);
				}
				else {
					try {
						Class<?> classObj = Class.forName(type);

						Constructor<?> constructor = classObj.getConstructor(
							new Class<?>[] {String.class});

						map.put(kvp[0], constructor.newInstance(kvp[1]));
					}
					catch (Exception e) {
						_log.error(e, e);
					}
				}
			}
		}

		return (LinkedHashMap<String, T>) map;
	}

	private static Log _log = LogFactoryUtil.getLog(MapUtil.class);

}