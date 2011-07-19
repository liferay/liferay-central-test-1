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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleUtil {

	public static Locale fromLanguageId(String languageId) {
		return _instance._fromLanguageId(languageId);
	}

	public static Locale[] fromLanguageIds(String[] languageIds) {
		return _instance._fromLanguageIds(languageIds);
	}

	public static Locale getDefault() {
		return _instance._getDefault();
	}

	public static LocaleUtil getInstance() {
		return _instance;
	}

	public static Map<String, String> getISOLanguages(Locale locale) {
		return _instance._getISOLanguages(locale);
	}

	public static void setDefault(
		String userLanguage, String userCountry, String userVariant) {

		_instance._setDefault(userLanguage, userCountry, userVariant);
	}

	public static String toLanguageId(Locale locale) {
		return _instance._toLanguageId(locale);
	}

	public static String[] toLanguageIds(Locale[] locales) {
		return _instance._toLanguageIds(locales);
	}

	public static String toW3cLanguageId(Locale locale) {
		return _instance._toW3cLanguageId(locale);
	}

	public static String toW3cLanguageId(String languageId) {
		return _instance._toW3cLanguageId(languageId);
	}

	public static String[] toW3cLanguageIds(Locale[] locales) {
		return _instance._toW3cLanguageIds(locales);
	}

	public static String[] toW3cLanguageIds(String[] languageIds) {
		return _instance._toW3cLanguageIds(languageIds);
	}

	private LocaleUtil() {
		_locale = new Locale("en", "US");

		_isoCountries = Locale.getISOCountries().clone();

		for (int i = 0; i < _isoCountries.length; i++) {
			_isoCountries[i] = _isoCountries[i].toUpperCase();
		}

		Arrays.sort(_isoCountries);

		_isoLanguages = Locale.getISOLanguages().clone();

		for (int i = 0; i < _isoLanguages.length; i++) {
			_isoLanguages[i] = _isoLanguages[i].toLowerCase();
		}

		Arrays.sort(_isoLanguages);
	}

	private Locale _fromLanguageId(String languageId) {
		if (languageId == null) {
			return _locale;
		}

		Locale locale = null;

		try {
			locale = _locales.get(languageId);

			if (locale == null) {
				int pos = languageId.indexOf(CharPool.UNDERLINE);

				if (pos == -1) {
					if (Arrays.binarySearch(_isoLanguages, languageId) < 0) {
						return _getDefault();
					}

					locale = new Locale(languageId);
				}
				else {
					String[] languageIdParts = StringUtil.split(
						languageId, StringPool.UNDERLINE);

					String languageCode = languageIdParts[0];
					String countryCode = languageIdParts[1];

					if ((Arrays.binarySearch(
							_isoLanguages, languageCode) < 0) ||
						(Arrays.binarySearch(_isoCountries, countryCode) < 0)) {

						return _getDefault();
					}

					String variant = null;

					if (languageIdParts.length > 2) {
						variant = languageIdParts[2];
					}

					if (Validator.isNotNull(variant)) {
						locale = new Locale(languageCode, countryCode, variant);
					}
					else {
						locale = new Locale(languageCode, countryCode);
					}
				}

				_locales.put(languageId, locale);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(languageId + " is not a valid language id");
			}
		}

		if (locale == null) {
			locale = _locale;
		}

		return locale;
	}

	private Locale[] _fromLanguageIds(String[] languageIds) {
		Locale[] locales = new Locale[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			locales[i] = _fromLanguageId(languageIds[i]);
		}

		return locales;
	}

	private Locale _getDefault() {
		Locale locale = LocaleThreadLocal.getLocale();

		if (locale != null) {
			return locale;
		}

		return _locale;
	}

	private Map<String, String> _getISOLanguages(Locale locale) {
		Map<String, String> isoLanguages = new TreeMap<String, String>(
			String.CASE_INSENSITIVE_ORDER);

		for (String isoLanguageId : Locale.getISOLanguages()) {
			Locale isoLocale = _fromLanguageId(isoLanguageId);

			isoLanguages.put(
				isoLocale.getDisplayLanguage(locale), isoLanguageId);
		}

		return isoLanguages;
	}

	private void _setDefault(
		String userLanguage, String userCountry, String userVariant) {

		if (Validator.isNotNull(userLanguage) &&
			Validator.isNull(userCountry) && Validator.isNull(userVariant)) {

			_locale = new Locale(userLanguage);
		}
		else if (Validator.isNotNull(userLanguage) &&
				 Validator.isNotNull(userCountry) &&
				 Validator.isNull(userVariant)) {

			_locale = new Locale(userLanguage, userCountry);
		}
		else if (Validator.isNotNull(userLanguage) &&
				 Validator.isNotNull(userCountry) &&
				 Validator.isNotNull(userVariant)) {

			_locale = new Locale(userLanguage, userCountry, userVariant);
		}
	}

	private String _toLanguageId(Locale locale) {
		if (locale == null) {
			locale = _locale;
		}

		String languageId = locale.getLanguage();
		String countryId = locale.getCountry();
		String variantId = locale.getVariant();

		boolean hasCountryId = countryId.length() != 0;
		boolean hasVariantId = variantId.length() != 0;

		if ((!hasCountryId) && (!hasVariantId)) {
			return languageId;
		}

		int length = 3;

		if (hasCountryId && hasVariantId) {
			length = 5;
		}

		StringBundler sb = new StringBundler(length);

		sb.append(languageId);

		if (hasCountryId) {
			sb.append(StringPool.UNDERLINE);
			sb.append(countryId);
		}

		if (hasVariantId) {
			sb.append(StringPool.UNDERLINE);
			sb.append(variantId);
		}

		return sb.toString();
	}

	private String[] _toLanguageIds(Locale[] locales) {
		String[] languageIds = new String[locales.length];

		for (int i = 0; i < locales.length; i++) {
			languageIds[i] = _toLanguageId(locales[i]);
		}

		return languageIds;
	}

	private String _toW3cLanguageId(Locale locale) {
		return _toW3cLanguageId(_toLanguageId(locale));
	}

	private String _toW3cLanguageId(String languageId) {
		return StringUtil.replace(
			languageId, CharPool.UNDERLINE, CharPool.MINUS);
	}

	private String[] _toW3cLanguageIds(Locale[] locales) {
		return _toW3cLanguageIds(_toLanguageIds(locales));
	}

	private String[] _toW3cLanguageIds(String[] languageIds) {
		String[] w3cLanguageIds = new String[languageIds.length];

		for (int i = 0; i < languageIds.length; i++) {
			w3cLanguageIds[i] = _toW3cLanguageId(languageIds[i]);
		}

		return w3cLanguageIds;
	}

	private static Log _log = LogFactoryUtil.getLog(LocaleUtil.class);

	private static LocaleUtil _instance = new LocaleUtil();

	private String[] _isoCountries;
	private String[] _isoLanguages;
	private Locale _locale;
	private Map<String, Locale> _locales = new HashMap<String, Locale>();

}