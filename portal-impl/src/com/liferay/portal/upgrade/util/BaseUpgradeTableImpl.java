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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * <a href="BaseUpgradeTableImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradeTableImpl extends Table {

	public BaseUpgradeTableImpl(String tableName) {
		super(tableName);
	}

	public BaseUpgradeTableImpl(String tableName, Object[][] columns) {
		super(tableName, columns);
	}

	public void setCreateSQL(String createSQL) throws Exception {
		if (_calledUpdateTable) {
			throw new UpgradeException(
				"setCreateSQL is called after updateTable");
		}

		super.setCreateSQL(createSQL);
	}

	public void updateTable() throws Exception {
		_calledUpdateTable = true;

		String tempFileName = generateTempFile();

		try {
			DB db = DBFactoryUtil.getDB();

			String createSQL = getCreateSQL();

			if (Validator.isNotNull(createSQL)) {
				db.runSQL("drop table " + getTableName());

				db.runSQL(createSQL);
			}

			if (Validator.isNotNull(tempFileName)) {
				String deleteSQL = getDeleteSQL();

				db.runSQL(deleteSQL);

				populateTable(tempFileName);
			}
		}
		finally {
			if (Validator.isNotNull(tempFileName)) {
				FileUtil.delete(tempFileName);
			}
		}
	}

	static Log _log = LogFactoryUtil.getLog(BaseUpgradeTableImpl.class);

	private boolean _calledUpdateTable;

}