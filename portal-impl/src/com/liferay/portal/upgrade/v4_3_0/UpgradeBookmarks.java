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
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.BookmarksEntryTable;
import com.liferay.portal.upgrade.v4_3_0.util.BookmarksFolderTable;

import java.sql.Types;

/**
 * <a href="UpgradeBookmarks.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class UpgradeBookmarks extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// BookmarksFolder

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"folderId", true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			BookmarksFolderTable.TABLE_NAME, BookmarksFolderTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(BookmarksFolderTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper folderIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setBookmarksFolderIdMapper(folderIdMapper);

		UpgradeColumn upgradeParentFolderIdColumn = new SwapUpgradeColumnImpl(
			"parentFolderId", folderIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			BookmarksFolderTable.TABLE_NAME, BookmarksFolderTable.TABLE_COLUMNS,
			upgradeParentFolderIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeFolderIdColumn = new SwapUpgradeColumnImpl(
			"folderId", folderIdMapper);

		// BookmarksEntry

		upgradePKColumn = new PKUpgradeColumnImpl("entryId", true);

		upgradeTable = new DefaultUpgradeTableImpl(
			BookmarksEntryTable.TABLE_NAME, BookmarksEntryTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeFolderIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(BookmarksEntryTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper entryIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setBookmarksEntryIdMapper(entryIdMapper);
	}

}