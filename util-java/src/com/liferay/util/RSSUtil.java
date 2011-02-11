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

package com.liferay.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import java.util.List;

import org.jdom.IllegalDataException;

/**
 * @author Brian Wing Shun Chan
 */
public class RSSUtil {

	public static final String RSS = "rss";

	public static final double[] RSS_VERSIONS = new double[] {
		0.9, 0.91, 0.93, 0.94, 1.0, 2.0
	};

	public static final String ATOM = "atom";

	public static final double[] ATOM_VERSIONS = new double[] {0.3, 1.0};

	public static final String DEFAULT_TYPE = ATOM;

	public static final double DEFAULT_VERSION = 1.0;

	public static final String DEFAULT_ENTRY_TYPE = "html";

	public static final String DEFAULT_FEED_TYPE = getFeedType(
		DEFAULT_TYPE, DEFAULT_VERSION);

	public static final String DISPLAY_STYLE_ABSTRACT = "abstract";

	public static final String DISPLAY_STYLE_FULL_CONTENT = "full-content";

	public static final String DISPLAY_STYLE_TITLE = "title";

	public static String export(SyndFeed feed) throws FeedException {
		RSSThreadLocal.setExportRSS(true);

		feed.setEncoding(StringPool.UTF8);

		SyndFeedOutput output = new SyndFeedOutput();

		try {
			return output.outputString(feed);
		}
		catch (IllegalDataException ide) {

			// LEP-4450

			_regexpStrip(feed);

			return output.outputString(feed);
		}
	}

	public static String getFeedType(String type, double version) {
		return type + StringPool.UNDERLINE + version;
	}

	public static String getFormatType(String format) {
		String formatType = DEFAULT_TYPE;

		if (StringUtil.contains(format, ATOM)) {
			formatType = RSSUtil.ATOM;
		}
		else if (StringUtil.contains(format, RSS)) {
			formatType = RSSUtil.RSS;
		}

		return formatType;
	}

	public static double getFormatVersion(String format) {
		double formatVersion = DEFAULT_VERSION;

		if (StringUtil.contains(format, "10")) {
			formatVersion = 1.0;
		}
		else if (StringUtil.contains(format, "20")) {
			formatVersion = 2.0;
		}

		return formatVersion;
	}

	private static void _regexpStrip(SyndFeed feed) {
		feed.setTitle(_regexpStrip(feed.getTitle()));
		feed.setDescription(_regexpStrip(feed.getDescription()));

		List<SyndEntry> entries = feed.getEntries();

		for (SyndEntry entry : entries) {
			entry.setTitle(_regexpStrip(entry.getTitle()));

			SyndContent content = entry.getDescription();

			content.setValue(_regexpStrip(content.getValue()));
		}
	}

	private static String _regexpStrip(String text) {
		text = Normalizer.normalizeToAscii(text);

		char[] array = text.toCharArray();

		for (int i = 0; i < array.length; i++) {
			String s = String.valueOf(array[i]);

			if (!s.matches(_REGEXP_STRIP)) {
				array[i] = CharPool.SPACE;
			}
		}

		return new String(array);
	}

	private static final String _REGEXP_STRIP = "[\\d\\w]";

}