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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLTrashHandlerTest extends BaseDLTrashHandlerTestCase {

	@Test
	public void testDuplicateFileEntry() throws Exception {
		checkDuplicate(true, false);
	}

	@Test
	public void testDuplicateFolder() throws Exception {
		checkDuplicate(false, true);
	}

	@Test
	public void testDuplicateFolderAndFileEntry() throws Exception {
		checkDuplicate(true, true);
	}

	protected void addAndTrashFileEntry() throws Exception {
		FileEntry fileEntry = addFileEntry(false, _NAME);

		fileEntry = DLAppServiceUtil.moveFileEntryToTrash(
			fileEntry.getFileEntryId());

		Assert.assertFalse(fileEntry.getTitle().contains(StringPool.SLASH));
	}

	protected void addAndTrashFolder() throws Exception {
		Folder folder = addFolder(false, _NAME);

		folder = DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		Assert.assertFalse(folder.getName().contains(StringPool.SLASH));
	}

	@Override
	protected long addSubentry(long folderId1, long folderId2)
		throws Exception {

		return 0;
	}

	protected void checkDuplicate(
			boolean duplicateFileEntry, boolean duplicateFolder)
		throws Exception {

		int initialNotInTrashCount = getNotInTrashCount();
		int initialTrashEntriesCount = getTrashEntriesCount();

		int trashCount = 0;

		if (duplicateFileEntry) {
			addAndTrashFileEntry();

			trashCount++;
		}

		if (duplicateFolder) {
			addAndTrashFolder();

			trashCount++;
		}

		Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + trashCount, getTrashEntriesCount());

		if (duplicateFileEntry) {
			addAndTrashFileEntry();

			trashCount++;
		}

		if (duplicateFolder) {
			addAndTrashFolder();

			trashCount++;
		}

		Assert.assertEquals(initialNotInTrashCount, getNotInTrashCount());
		Assert.assertEquals(
			initialTrashEntriesCount + trashCount, getTrashEntriesCount());
	}

	@Override
	protected void moveSubentryFromTrash(long subentryId) throws Exception {
	}

	@Override
	protected void moveSubentryToTrash(long subentryId) throws Exception {
	}

	@Override
	protected void trashSubentry(boolean deleteFolder) throws Exception {
	}

	private static final String _NAME = "Test Name";

}