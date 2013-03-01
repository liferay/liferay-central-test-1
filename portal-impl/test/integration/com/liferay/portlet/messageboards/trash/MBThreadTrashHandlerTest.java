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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class MBThreadTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashAndDeleteDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashAndRestoreDraft() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashDuplicate() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionBaseModelAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionBaseModelAndRestore() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionParentBaseModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		MBCategory category = (MBCategory)parentBaseModel;

		MBMessage message = MBTestUtil.addMessage(
			category.getCategoryId(), getSearchKeywords(), approved,
			serviceContext);

		return message.getThread();
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return MBThreadLocalServiceUtil.getThread(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBThread.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		MBCategory category = (MBCategory)parentBaseModel;

		return MBThreadLocalServiceUtil.getGroupThreadsCount(
			category.getGroupId(), queryDefinition);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return MBTestUtil.addCategory(serviceContext);
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return MBCategory.class;
	}

	@Override
	protected String getSearchKeywords() {
		return _SUBJECT;
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected boolean isAssetEntryVisible(ClassedModel classedModel)
		throws Exception {

		MBMessage rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			((MBThread)classedModel).getRootMessageId());

		return super.isAssetEntryVisible(rootMessage);
	}

	@Override
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		MBThreadServiceUtil.moveThreadFromTrash(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			(Long)classedModel.getPrimaryKeyObj());

		return parentBaseModel;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		MBThreadServiceUtil.moveThreadToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		MBCategoryServiceUtil.moveCategoryToTrash(primaryKey);
	}

	@Override
	protected int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return super.searchBaseModelsCount(MBMessage.class, groupId);
	}

	private static final String _SUBJECT = "Subject";

}