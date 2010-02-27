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

package com.liferay.portal.upgrade.v4_4_0.util;

import java.sql.Types;

/**
 * <a href="GroupTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class GroupTable {

	public static final String TABLE_NAME = "Group_";

	public static final Object[][] TABLE_COLUMNS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"creatorUserId", new Integer(Types.BIGINT)},
		{"classNameId", new Integer(Types.BIGINT)},
		{"classPK", new Integer(Types.BIGINT)},
		{"parentGroupId", new Integer(Types.BIGINT)},
		{"liveGroupId", new Integer(Types.BIGINT)},
		{"name", new Integer(Types.VARCHAR)},
		{"description", new Integer(Types.VARCHAR)},
		{"type_", new Integer(Types.INTEGER)},
		{"typeSettings", new Integer(Types.VARCHAR)},
		{"friendlyURL", new Integer(Types.VARCHAR)},
		{"active_", new Integer(Types.BOOLEAN)}
	};

	public static final String TABLE_SQL_CREATE = "create table Group_ (groupId LONG not null primary key,companyId LONG,creatorUserId LONG,classNameId LONG,classPK LONG,parentGroupId LONG,liveGroupId LONG,name VARCHAR(75) null,description STRING null,type_ INTEGER,typeSettings STRING null,friendlyURL VARCHAR(100) null,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Group_";

}