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

package com.liferay.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.NumberFormat;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class TextFormatter {

	// Web Search --> WEB_SEARCH

	public static final int A = 0;

	// Web Search --> websearch

	public static final int B = 1;

	// Web Search --> web_search

	public static final int C = 2;

	// Web Search --> WebSearch

	public static final int D = 3;

	// Web Search --> web search

	public static final int E = 4;

	// Web Search --> webSearch

	public static final int F = 5;

	// formatId --> FormatId

	public static final int G = 6;

	// formatId --> format id

	public static final int H = 7;

	// FormatId --> formatId

	public static final int I = 8;

	// format-id --> Format Id

	public static final int J = 9;

	// formatId --> format-id

	public static final int K = 10;

	// FormatId --> formatId, FOrmatId --> FOrmatId

	public static final int L = 11;

	// format-id --> formatId

	public static final int M = 12;

	// format-id --> format_id

	public static final int N = 13;

	// format_id --> format-id

	public static final int O = 14;

	// formatID --> format-id

	public static final int P = 15;

	public static String format(String s, int style) {
		if (Validator.isNull(s)) {
			return null;
		}

		s = s.trim();

		if (style == A) {
			return _formatA(s);
		}
		else if (style == B) {
			return _formatB(s);
		}
		else if (style == C) {
			return _formatC(s);
		}
		else if (style == D) {
			return _formatD(s);
		}
		else if (style == E) {
			return _formatE(s);
		}
		else if (style == F) {
			return _formatF(s);
		}
		else if (style == G) {
			return _formatG(s);
		}
		else if (style == H) {
			return _formatH(s);
		}
		else if (style == I) {
			return _formatI(s);
		}
		else if (style == J) {
			return _formatJ(s);
		}
		else if (style == K) {
			return _formatK(s);
		}
		else if (style == L) {
			return _formatL(s);
		}
		else if (style == M) {
			return _formatM(s);
		}
		else if (style == N) {
			return _formatN(s);
		}
		else if (style == O) {
			return _formatO(s);
		}
		else if (style == P) {
			return _formatP(s);
		}
		else {
			return s;
		}
	}

	public static String formatKB(double size, Locale locale) {
		NumberFormat numberFormat = NumberFormat.getInstance(locale);

		numberFormat.setMaximumFractionDigits(1);
		numberFormat.setMinimumFractionDigits(1);

		return numberFormat.format(size / 1024.0);
	}

	public static String formatKB(int size, Locale locale) {
		return formatKB((double)size, locale);
	}

	public static String formatName(String name) {
		if (Validator.isNull(name)) {
			return name;
		}

		char[] charArray = name.toLowerCase().trim().toCharArray();

		if (charArray.length > 0) {
			charArray[0] = Character.toUpperCase(charArray[0]);
		}

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == ' ') {
				charArray[i + 1] = Character.toUpperCase(charArray[i + 1]);
			}
		}

		return new String(charArray);
	}

	public static String formatPlural(String s) {
		if (Validator.isNull(s)) {
			return s;
		}

		if (s.endsWith("s")) {
			s = s.substring(0, s.length() -1) + "ses";
		}
		else if (s.endsWith("y")) {
			s = s.substring(0, s.length() -1) + "ies";
		}
		else {
			s = s + "s";
		}

		return s;
	}

	private static String _formatA(String s) {
		return StringUtil.replace(
			s.toUpperCase(), CharPool.SPACE, CharPool.UNDERLINE);
	}

	private static String _formatB(String s) {
		return StringUtil.replace(
			s.toLowerCase(), StringPool.SPACE, StringPool.BLANK);
	}

	private static String _formatC(String s) {
		return StringUtil.replace(
			s.toLowerCase(), CharPool.SPACE, CharPool.UNDERLINE);
	}

	private static String _formatD(String s) {
		return StringUtil.replace(s, StringPool.SPACE, StringPool.BLANK);
	}

	private static String _formatE(String s) {
		return s.toLowerCase();
	}

	private static String _formatF(String s) {
		s = StringUtil.replace(s, StringPool.SPACE, StringPool.BLANK);
		s = Character.toLowerCase(s.charAt(0)) + s.substring(1, s.length());

		return s;
	}

	private static String _formatG(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
	}

	private static String _formatH(String s) {
		char[] charArray = s.toCharArray();

		StringBuilder sb = new StringBuilder(charArray.length * 2);

		for (int i = 0; i < charArray.length; i++) {
			if (Character.isUpperCase(charArray[i])) {
				sb.append(CharPool.SPACE);
				sb.append(Character.toLowerCase(charArray[i]));
			}
			else {
				sb.append(charArray[i]);
			}
		}

		return sb.toString().trim();
	}

	private static String _formatI(String s) {
		if (s.length() == 1) {
			return s.toLowerCase();
		}

		if (Character.isUpperCase(s.charAt(0)) &&
			Character.isLowerCase(s.charAt(1))) {

			return Character.toLowerCase(s.charAt(0)) +
				s.substring(1, s.length());
		}

		char[] charArray = s.toCharArray();

		StringBuilder sb = new StringBuilder(charArray.length);

		for (int i = 0; i < charArray.length; i++) {
			if ((i + 1 != charArray.length) &&
				(Character.isLowerCase(charArray[i + 1]))) {

				sb.append(s.substring(i, charArray.length));

				break;
			}
			else {
				sb.append(Character.toLowerCase(charArray[i]));
			}
		}

		return sb.toString();
	}

	private static String _formatJ(String s) {
		s = StringUtil.replace(s, CharPool.DASH, CharPool.SPACE);
		s = StringUtil.replace(s, CharPool.UNDERLINE, CharPool.SPACE);

		char[] charArray = s.toCharArray();

		StringBuilder sb = new StringBuilder(charArray.length);

		for (int i = 0; i < charArray.length; i++) {
			if ((i == 0) || (charArray[i - 1] == ' ')) {
				sb.append(Character.toUpperCase(charArray[i]));
			}
			else {
				sb.append(Character.toLowerCase(charArray[i]));
			}
		}

		return sb.toString();
	}

	private static String _formatK(String s) {
		s = _formatH(s);
		s = StringUtil.replace(s, CharPool.SPACE, CharPool.DASH);

		return s;
	}

	private static String _formatL(String s) {
		if (s.length() == 1) {
			return s.toLowerCase();
		}
		else if (Character.isUpperCase(s.charAt(0)) &&
				 Character.isUpperCase(s.charAt(1))) {

			return s;
		}
		else {
			return Character.toLowerCase(s.charAt(0)) + s.substring(1);
		}
	}

	private static String _formatM(String s) {
		char[] charArray = s.toCharArray();

		StringBuilder sb = new StringBuilder(charArray.length);

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '-') {
			}
			else if ((i > 0) && (charArray[i - 1] == '-')) {
				sb.append(Character.toUpperCase(charArray[i]));
			}
			else {
				sb.append(charArray[i]);
			}
		}

		return sb.toString();
	}

	private static String _formatN(String s) {
		return StringUtil.replace(s, CharPool.DASH, CharPool.UNDERLINE);
	}

	private static String _formatO(String s) {
		return StringUtil.replace(s, CharPool.UNDERLINE, CharPool.DASH);
	}

	private static String _formatP(String s) {
		char[] charArray = s.toCharArray();

		StringBuilder sb = new StringBuilder(charArray.length * 2);

		for (int i = 0; i < charArray.length; i++) {
			if (Character.isUpperCase(charArray[i]) && (i > 0) &&
				((i + 1) < charArray.length)) {

				if (Character.isUpperCase(charArray[i]) &&
					Character.isLowerCase(charArray[i + 1])) {

					sb.append(CharPool.DASH);
				}
				else if (Character.isLowerCase(charArray[i - 1]) &&
						 Character.isUpperCase(charArray[i])) {

					sb.append(CharPool.DASH);
				}
			}

			sb.append(Character.toLowerCase(charArray[i]));
		}

		return sb.toString();
	}

}