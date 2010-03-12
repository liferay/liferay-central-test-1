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

import java.text.DateFormat;

import java.util.Date;

/**
 * <a href="ReleaseInfo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ReleaseInfo {

	static String name = "Liferay Portal Community Edition";

	static String version = "6.0.1";

	static String versionDisplayName = "6.0.1 CE";

	static String codeName = "Bunyan";

	static String build = "6001";

	static String date = "March 31, 2010";

	static String releaseInfoPrefix = System.getProperty(
		"liferay.release.info.prefix" , StringPool.BLANK);

	static String releaseInfoSuffix = System.getProperty(
		"liferay.release.info.suffix" , StringPool.BLANK);

	static String releaseInfo =
		releaseInfoPrefix + name + " " + versionDisplayName + " (" + codeName +
			" / Build " + build + " / " + date + ")" + releaseInfoSuffix;

	static String serverInfo = name + " / " + version;

	public static int RELEASE_4_2_0_BUILD_NUMBER = 3500;

	public static int RELEASE_4_2_1_BUILD_NUMBER = 3501;

	public static int RELEASE_4_2_2_BUILD_NUMBER = 3502;

	public static int RELEASE_4_3_0_BUILD_NUMBER = 4300;

	public static int RELEASE_4_3_1_BUILD_NUMBER = 4301;

	public static int RELEASE_4_3_2_BUILD_NUMBER = 4302;

	public static int RELEASE_4_3_3_BUILD_NUMBER = 4303;

	public static int RELEASE_4_3_4_BUILD_NUMBER = 4304;

	public static int RELEASE_4_3_5_BUILD_NUMBER = 4305;

	public static int RELEASE_4_3_6_BUILD_NUMBER = 4306;

	public static int RELEASE_4_4_0_BUILD_NUMBER = 4400;

	public static int RELEASE_5_0_0_BUILD_NUMBER = 5000;

	public static int RELEASE_5_0_1_BUILD_NUMBER = 5001;

	public static int RELEASE_5_1_0_BUILD_NUMBER = 5100;

	public static int RELEASE_5_1_1_BUILD_NUMBER = 5101;

	public static int RELEASE_5_1_2_BUILD_NUMBER = 5102;

	public static int RELEASE_5_2_0_BUILD_NUMBER = 5200;

	public static int RELEASE_5_2_1_BUILD_NUMBER = 5201;

	public static int RELEASE_5_2_2_BUILD_NUMBER = 5202;

	public static int RELEASE_5_2_3_BUILD_NUMBER = 5203;

	public static int RELEASE_6_0_0_BUILD_NUMBER = 6000;

	public static int RELEASE_6_0_1_BUILD_NUMBER = 6001;

	public static final String getVersion() {
		return version;
	}

	public static final String getCodeName() {
		return codeName;
	}

	public static final int getBuildNumber() {
		return Integer.parseInt(build);
	}

	public static final Date getBuildDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

		return GetterUtil.getDate(date, df);
	}

	public static final String getReleaseInfo() {
		return releaseInfo;
	}

	public static final String getServerInfo() {
		return serverInfo;
	}

}