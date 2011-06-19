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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.RatingsEntryTable;
import com.liferay.portal.upgrade.v4_3_0.util.RatingsStatsTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeRatings extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// RatingsEntry

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		ClassNameIdUpgradeColumnImpl classNameIdColumn =
			new ClassNameIdUpgradeColumnImpl();

		Map<Long, ClassPKContainer> classPKContainers =
			new HashMap<Long, ClassPKContainer>();

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(DLFileEntry.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getDLFileEntryIdMapper(), false));

		UpgradeColumn upgradeClassPKColumn = new ClassPKUpgradeColumnImpl(
			classNameIdColumn, classPKContainers);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			RatingsEntryTable.TABLE_NAME, RatingsEntryTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("entryId", false), upgradeUserIdColumn,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.setCreateSQL(RatingsEntryTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(RatingsEntryTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// RatingsStats

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			RatingsStatsTable.TABLE_NAME, RatingsStatsTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("statsId", false), classNameIdColumn,
			upgradeClassPKColumn);

		upgradeTable.setCreateSQL(RatingsStatsTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(RatingsStatsTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

}