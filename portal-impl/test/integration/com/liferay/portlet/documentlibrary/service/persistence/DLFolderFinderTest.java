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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLFolderFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		_folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder B", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_folder.getFolderId(), "Folder C", "",
			ServiceTestUtil.getServiceContext(_group.getGroupId()));

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false, "FE1.txt",
			"FE1.txt");

		_fileShortcut = DLAppTestUtil.addDLFileShortcut(
			_group.getGroupId(), fileEntry);

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(),
			ContentTypes.APPLICATION_PDF, "FE2.pdf", "FE2.pdf");

		fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false, "FE3.txt",
			"FE3.txt");

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());
	}

	@Test
	public void testCountF_FE_FS_ByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		int count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(), null, false,
			queryDefinition);

		Assert.assertEquals(count, 4);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, false, queryDefinition);

		Assert.assertEquals(count, 3);

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(), null, false,
			queryDefinition);

		Assert.assertEquals(count, 6);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, false, queryDefinition);

		Assert.assertEquals(count, 5);

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(), null, false,
			queryDefinition);

		Assert.assertEquals(count, 4);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.APPLICATION_PDF}, false,
			queryDefinition);

		Assert.assertEquals(count, 2);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		count = DLFolderFinderUtil.filterCountF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(), null, false,
			queryDefinition);

		Assert.assertEquals(count, 2);
	}

	@Test
	public void testCountFE_ByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_IN_TRASH);

		int count = DLFolderFinderUtil.countFE_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		count = DLFolderFinderUtil.countFE_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 2);

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		count = DLFolderFinderUtil.countFE_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 3);
	}

	@Test
	public void testCountFE_FS_ByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		int count = DLFolderFinderUtil.filterCountFE_FS_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 3);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
			_group.getGroupId(), _folder.getFolderId(), null, queryDefinition);

		Assert.assertEquals(count, 3);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, queryDefinition);

		Assert.assertEquals(count, 2);

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 4);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, queryDefinition);

		Assert.assertEquals(count, 3);

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 3);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.APPLICATION_PDF}, queryDefinition);

		Assert.assertEquals(count, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		count = DLFolderFinderUtil.filterCountFE_FS_ByG_F(
			_group.getGroupId(), _folder.getFolderId(), queryDefinition);

		Assert.assertEquals(count, 1);
	}

	@Test
	public void testFindF_FE_FS_ByG_F() throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		List<?> list = DLFolderFinderUtil.filterFindF_FE_FS_ByG_F_M_M(
			_group.getGroupId(), _folder.getFolderId(),
			new String[] {ContentTypes.TEXT_PLAIN}, false, queryDefinition);

		Assert.assertEquals(list.size(), 3);

		for (Object obj : list) {
			if (obj instanceof DLFolder) {
				Assert.assertEquals(((DLFolder)obj).getName(), "Folder B");
			}
			else if (obj instanceof DLFileEntry) {
				Assert.assertEquals(((DLFileEntry)obj).getTitle(), "FE1.txt");
			}
			else if (obj instanceof DLFileShortcut) {
				Assert.assertEquals(
					((DLFileShortcut)obj).getFileShortcutId(),
					_fileShortcut.getFileShortcutId());
			}
			else {
				Assert.fail("Invalid model returned: " + obj.getClass());

				continue;
			}
		}
	}

	private DLFileShortcut _fileShortcut;
	private Folder _folder;
	private Group _group;

}