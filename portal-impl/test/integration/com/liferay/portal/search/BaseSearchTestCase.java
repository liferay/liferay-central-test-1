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

package com.liferay.portal.search;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseSearchTestCase {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testSearchAttachments() throws Exception {
		searchAttachments();
	}

	@Test
	public void testSearchBaseModel() throws Exception {
		searchBaseModel();
	}

	@Test
	public void testSearchByDDMStructureField() throws Exception {
		searchByDDMStructureField();
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		searchByKeywords();
	}

	@Test
	@Transactional
	public void testSearchComments() throws Exception {
		searchComments();
	}

	@Test
	@Transactional
	public void testSearchExpireAllVersions() throws Exception {
		searchExpireVersions(false);
	}

	@Test
	@Transactional
	public void testSearchExpireLatestVersion() throws Exception {
		searchExpireVersions(true);
	}

	@Test
	@Transactional
	public void testSearchStatus() throws Exception {
		searchStatus();
	}

	@Test
	@Transactional
	public void testSearchVersions() throws Exception {
		searchVersions();
	}

	@Test
	public void testSearchWithinDDMStructure() throws Exception {
		searchWithinDDMStructure();
	}

	protected void addAttachment(ClassedModel classedModel) throws Exception {
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			BaseModel<?> baseModel = addBaseModelWithWorkflow(
				parentBaseModel, approved, keywords, serviceContext);

			return baseModel;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected BaseModel<?> addBaseModelWithDDMStructure(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModel(parentBaseModel, true, keywords, serviceContext);
	}

	protected abstract BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception;

	protected void addComment(
			ClassedModel classedModel, String body,
			ServiceContext serviceContext)
		throws Exception {

		User user = TestPropsValues.getUser();

		List<MBMessage> messages = MBMessageLocalServiceUtil.getMessages(
			getBaseModelClassName(), getBaseModelClassPK(classedModel),
			WorkflowConstants.STATUS_ANY);

		MBMessage message = messages.get(0);

		MBMessageLocalServiceUtil.addDiscussionMessage(
			user.getUserId(), user.getFullName(),
			serviceContext.getScopeGroupId(), getBaseModelClassName(),
			getBaseModelClassPK(classedModel), message.getThreadId(),
			message.getMessageId(), message.getSubject(), body, serviceContext);
	}

	protected void expireBaseModelVersions(
			BaseModel<?> baseModel, boolean expireAll,
			ServiceContext serviceContext)
		throws Exception {
	}

	protected abstract Class<?> getBaseModelClass();

	protected String getBaseModelClassName() {
		Class<?> clazz = getBaseModelClass();

		return clazz.getName();
	}

	protected Long getBaseModelClassPK(ClassedModel classedModel) {
		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected String getDDMStructureFieldName() {
		return StringPool.BLANK;
	}

	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected abstract String getSearchKeywords();

	protected boolean isExpirableAllVersions() {
		return false;
	}

	protected void searchAttachments() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setIncludeAttachments(true);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, ServiceTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		addAttachment(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchBaseModel() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, ServiceTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected int searchBaseModelsCount(
			Class<?> clazz, long groupId, SearchContext searchContext)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(clazz);

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchBaseModelsCount(
			Class<?> clazz, long groupId, String keywords,
			SearchContext searchContext)
		throws Exception {

		searchContext.setKeywords(keywords);

		return searchBaseModelsCount(clazz, groupId, searchContext);
	}

	protected void searchByDDMStructureField() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModelWithDDMStructure(
			parentBaseModel, getSearchKeywords(), serviceContext);

		searchContext.setAttribute(
			"ddmStructureFieldName", getDDMStructureFieldName());
		searchContext.setAttribute(
			"ddmStructureFieldValue", getSearchKeywords());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchByKeywords() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchComments() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setIncludeDiscussions(true);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, ServiceTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		addComment(baseModel, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchExpireVersions(boolean expireAll) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "liferay", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		expireBaseModelVersions(baseModel, expireAll, serviceContext);

		if (expireAll && isExpirableAllVersions()) {
			Assert.assertEquals(
				initialBaseModelsCount,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		} else {
			Assert.assertEquals(
				initialBaseModelsCount + 1,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		}
	}

	protected void searchStatus() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), "1.0", searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, false, "Version 1.0", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(baseModel, "Version 1.1", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.0", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.1", searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(baseModel, "Version 1.2", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.1", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.2", searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(baseModel, "Version 1.3", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.2", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.3", searchContext));
	}

	protected void searchVersions() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "liferay", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "portal", serviceContext);

		searchContext.setKeywords("portal");

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchWithinDDMStructure() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		serviceContext.setScopeGroupId(group.getGroupId());

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModelWithDDMStructure(
			parentBaseModel,  getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return baseModel;
	}

	protected BaseModel<?> baseModel;
	protected Group group;

}