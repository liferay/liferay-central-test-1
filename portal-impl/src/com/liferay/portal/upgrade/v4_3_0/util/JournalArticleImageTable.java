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

package com.liferay.portal.upgrade.v4_3_0.util;

import java.sql.Types;

/**
 * <a href="JournalArticleImageTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleImageTable {

	public static String TABLE_NAME = "JournalArticleImage";

	public static Object[][] TABLE_COLUMNS = {
		{"articleImageId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)},
		{"articleId", new Integer(Types.VARCHAR)},
		{"version", new Integer(Types.DOUBLE)},
		{"elName", new Integer(Types.VARCHAR)},
		{"languageId", new Integer(Types.VARCHAR)},
		{"tempImage", new Integer(Types.BOOLEAN)}
	};

	public static String TABLE_SQL_CREATE = "create table JournalArticleImage (articleImageId LONG not null primary key,groupId LONG,articleId VARCHAR(75) null,version DOUBLE,elName VARCHAR(75) null,languageId VARCHAR(75) null,tempImage BOOLEAN)";

	public static String TABLE_SQL_DROP = "drop table JournalArticleImage";

}