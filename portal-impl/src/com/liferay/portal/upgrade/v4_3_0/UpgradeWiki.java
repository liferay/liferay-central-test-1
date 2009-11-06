/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.WikiNodeTable;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageTable;

import java.sql.Types;

/**
 * <a href="UpgradeWiki.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeWiki extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// WikiNode

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"nodeId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			WikiNodeTable.TABLE_NAME, WikiNodeTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(WikiNodeTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper nodeIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setWikiNodeIdMapper(nodeIdMapper);

		UpgradeColumn upgradeNodeIdColumn = new SwapUpgradeColumnImpl(
			"nodeId", nodeIdMapper);

		// WikiPage

		UpgradeColumn upgradeTitleColumn = new TempUpgradeColumnImpl("title");

		WikiPageIdUpgradeColumnImpl upgradePageIdColumn =
			new WikiPageIdUpgradeColumnImpl(
				upgradeNodeIdColumn, upgradeTitleColumn);

		UpgradeColumn upgradePageResourcePrimKeyColumn =
			new WikiPageResourcePrimKeyUpgradeColumnImpl(
				upgradePageIdColumn);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			WikiPageTable.TABLE_NAME, WikiPageTable.TABLE_COLUMNS,
			upgradeNodeIdColumn, upgradeTitleColumn, upgradePageIdColumn,
			upgradePageResourcePrimKeyColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(WikiPageTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper pageIdMapper = upgradePageIdColumn.getValueMapper();

		AvailableMappersUtil.setWikiPageIdMapper(pageIdMapper);
	}

}