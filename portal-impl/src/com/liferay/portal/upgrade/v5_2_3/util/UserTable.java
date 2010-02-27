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

package com.liferay.portal.upgrade.v5_2_3.util;

import java.sql.Types;

/**
 * <a href="UserTable.java.html"><b><i>View Source</i></b></a>
 *
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class UserTable {

	public static final String TABLE_NAME = "User_";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", new Integer(Types.VARCHAR)},
		{"userId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.BIGINT)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"defaultUser", new Integer(Types.BOOLEAN)},
		{"contactId", new Integer(Types.BIGINT)},
		{"password_", new Integer(Types.VARCHAR)},
		{"passwordEncrypted", new Integer(Types.BOOLEAN)},
		{"passwordReset", new Integer(Types.BOOLEAN)},
		{"passwordModifiedDate", new Integer(Types.TIMESTAMP)},
		{"reminderQueryQuestion", new Integer(Types.VARCHAR)},
		{"reminderQueryAnswer", new Integer(Types.VARCHAR)},
		{"graceLoginCount", new Integer(Types.INTEGER)},
		{"screenName", new Integer(Types.VARCHAR)},
		{"emailAddress", new Integer(Types.VARCHAR)},
		{"openId", new Integer(Types.VARCHAR)},
		{"portraitId", new Integer(Types.BIGINT)},
		{"languageId", new Integer(Types.VARCHAR)},
		{"timeZoneId", new Integer(Types.VARCHAR)},
		{"greeting", new Integer(Types.VARCHAR)},
		{"comments", new Integer(Types.VARCHAR)},
		{"firstName", new Integer(Types.VARCHAR)},
		{"middleName", new Integer(Types.VARCHAR)},
		{"lastName", new Integer(Types.VARCHAR)},
		{"jobTitle", new Integer(Types.VARCHAR)},
		{"loginDate", new Integer(Types.TIMESTAMP)},
		{"loginIP", new Integer(Types.VARCHAR)},
		{"lastLoginDate", new Integer(Types.TIMESTAMP)},
		{"lastLoginIP", new Integer(Types.VARCHAR)},
		{"lastFailedLoginDate", new Integer(Types.TIMESTAMP)},
		{"failedLoginAttempts", new Integer(Types.INTEGER)},
		{"lockout", new Integer(Types.BOOLEAN)},
		{"lockoutDate", new Integer(Types.TIMESTAMP)},
		{"agreedToTermsOfUse", new Integer(Types.BOOLEAN)},
		{"active_", new Integer(Types.BOOLEAN)}
	};

	public static final String TABLE_SQL_CREATE = "create table User_ (uuid_ VARCHAR(75) null,userId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,defaultUser BOOLEAN,contactId LONG,password_ VARCHAR(75) null,passwordEncrypted BOOLEAN,passwordReset BOOLEAN,passwordModifiedDate DATE null,reminderQueryQuestion VARCHAR(75) null,reminderQueryAnswer VARCHAR(75) null,graceLoginCount INTEGER,screenName VARCHAR(75) null,emailAddress VARCHAR(75) null,openId VARCHAR(1024) null,portraitId LONG,languageId VARCHAR(75) null,timeZoneId VARCHAR(75) null,greeting VARCHAR(255) null,comments STRING null,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,jobTitle VARCHAR(75) null,loginDate DATE null,loginIP VARCHAR(75) null,lastLoginDate DATE null,lastLoginIP VARCHAR(75) null,lastFailedLoginDate DATE null,failedLoginAttempts INTEGER,lockout BOOLEAN,lockoutDate DATE null,agreedToTermsOfUse BOOLEAN,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table User_";

}