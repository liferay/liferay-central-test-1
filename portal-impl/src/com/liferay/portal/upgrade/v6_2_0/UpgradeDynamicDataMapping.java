/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.v6_2_0.util.DDMTemplateTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.sql.SQLException;

/**
 * @author Juan Fernández
 */
public class UpgradeDynamicDataMapping extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter table DDMTemplate add classNameId LONG");

			runSQL("alter table DDMTemplate add templateKey STRING");

			runSQL("alter_column_name DDMTemplate structureId classPK LONG");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DDMTemplateTable.TABLE_NAME, DDMTemplateTable.TABLE_COLUMNS,
				DDMTemplateTable.TABLE_SQL_CREATE,
				DDMTemplateTable.TABLE_SQL_ADD_INDEXES);
		}

		long classNameId = PortalUtil.getClassNameId(DDMTemplate.class);

		runSQL("update DDMTemplate set classNameId = " + classNameId);
	}

}