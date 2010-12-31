/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static boolean getBoolean(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getBoolean(preferences, companyId, name);
	}

	public static boolean getBoolean(
			long companyId, String name, boolean defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getBoolean(preferences, companyId, name, defaultValue);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return GetterUtil.getBoolean(getString(preferences, companyId, name));
	}

	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return GetterUtil.getBoolean(
			getString(preferences, companyId, name, defaultValue));
	}

	public static boolean getBoolean(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getBoolean(preferences, 0, name);
	}

	public static boolean getBoolean(String name, boolean defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getBoolean(preferences, 0, name, defaultValue);
	}

	public static String getContent(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getContent(preferences, companyId, name);
	}

	public static String getContent(
		PortletPreferences preferences, long companyId, String name) {

		String value = preferences.getValue(name, StringPool.BLANK);

		if (Validator.isNotNull(value)) {
			return value;
		}
		else {
			return ContentUtil.get(PropsUtil.get(name));
		}
	}

	public static String getContent(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getContent(preferences, 0, name);
	}

	public static double getDouble(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getDouble(preferences, companyId, name);
	}

	public static double getDouble(
			long companyId, String name, double defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getDouble(preferences, companyId, name, defaultValue);
	}

	public static double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return GetterUtil.getDouble(getString(preferences, companyId, name));
	}

	public static double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return GetterUtil.getDouble(
			getString(preferences, companyId, name, defaultValue));
	}

	public static double getDouble(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getDouble(preferences, 0, name);
	}

	public static double getDouble(String name, double defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getDouble(preferences, 0, name, defaultValue);
	}

	public static int getInteger(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getInteger(preferences, companyId, name);
	}

	public static int getInteger(long companyId, String name, int defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getInteger(preferences, companyId, name, defaultValue);
	}

	public static int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return GetterUtil.getInteger(getString(preferences, companyId, name));
	}

	public static int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return GetterUtil.getInteger(
			getString(preferences, companyId, name, defaultValue));
	}

	public static int getInteger(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getInteger(preferences, 0, name);
	}

	public static int getInteger(String name, int defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getInteger(preferences, 0, name, defaultValue);
	}

	public static long getLong(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getLong(preferences, companyId, name);
	}

	public static long getLong(long companyId, String name, long defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getLong(preferences, companyId, name, defaultValue);
	}

	public static long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return GetterUtil.getLong(getString(preferences, companyId, name));
	}

	public static long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return GetterUtil.getLong(
			getString(preferences, companyId, name, defaultValue));
	}

	public static long getLong(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getLong(preferences, 0, name);
	}

	public static long getLong(String name, long defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getLong(preferences, 0, name, defaultValue);
	}

	public static PortletPreferences getPreferences() throws SystemException {
		return getPreferences(0);
	}

	public static PortletPreferences getPreferences(long companyId)
		throws SystemException {

		long ownerId = companyId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		long plid = PortletKeys.PREFS_PLID_SHARED;
		String portletId = PortletKeys.LIFERAY_PORTAL;

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, ownerId, ownerType, plid, portletId);
	}

	public static short getShort(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getShort(preferences, companyId, name);
	}

	public static short getShort(
			long companyId, String name, short defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getShort(preferences, companyId, name, defaultValue);
	}

	public static short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return GetterUtil.getShort(getString(preferences, companyId, name));
	}

	public static short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return GetterUtil.getShort(
			getString(preferences, companyId, name, defaultValue));
	}

	public static short getShort(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getShort(preferences, 0, name);
	}

	public static short getShort(String name, short defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getShort(preferences, 0, name, defaultValue);
	}

	public static String getString(long companyId, String name)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getString(preferences, companyId, name);
	}

	public static String getString(
			long companyId, String name, String defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getString(preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name) {

		String value = PropsUtil.get(name);

		return preferences.getValue(name, value);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		if (defaultValue) {
			return preferences.getValue(name, StringPool.TRUE);
		}
		else {
			return preferences.getValue(name, StringPool.FALSE);
		}
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return preferences.getValue(name, String.valueOf(defaultValue));
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return preferences.getValue(name, String.valueOf(defaultValue));
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return preferences.getValue(name, String.valueOf(defaultValue));
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return preferences.getValue(name, String.valueOf(defaultValue));
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return preferences.getValue(name, defaultValue);
	}

	public static String getString(String name) throws SystemException {
		PortletPreferences preferences = getPreferences();

		return getString(preferences, 0, name);
	}

	public static String getString(String name, String defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getString(preferences, 0, name, defaultValue);
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getStringArray(preferences, companyId, name, delimiter);
	}

	public static String[] getStringArray(
			long companyId, String name, String delimiter,
			String[] defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences(companyId);

		return getStringArray(
			preferences, companyId, name, delimiter, defaultValue);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		String value = PropsUtil.get(name);

		value = preferences.getValue(name, value);

		return StringUtil.split(value, delimiter);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		String value = preferences.getValue(name, null);

		if (value == null) {
			return defaultValue;
		}
		else {
			return StringUtil.split(value, delimiter);
		}
	}

	public static String[] getStringArray(String name, String delimiter)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getStringArray(preferences, 0, name, delimiter);
	}

	public static String[] getStringArray(
			String name, String delimiter, String[] defaultValue)
		throws SystemException {

		PortletPreferences preferences = getPreferences();

		return getStringArray(preferences, 0, name, delimiter, defaultValue);
	}

}