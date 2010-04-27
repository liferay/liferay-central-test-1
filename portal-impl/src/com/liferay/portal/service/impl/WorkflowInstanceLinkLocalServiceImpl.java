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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.ContextConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="WorkflowInstanceLinkLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class WorkflowInstanceLinkLocalServiceImpl
	extends WorkflowInstanceLinkLocalServiceBaseImpl {

	public WorkflowInstanceLink addWorkflowInstanceLink(
			long userId, long companyId, long groupId, String className,
			long classPK, long workflowInstanceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long workflowInstanceLinkId = counterLocalService.increment();

		WorkflowInstanceLink workflowInstanceLink =
			workflowInstanceLinkPersistence.create(workflowInstanceLinkId);

		workflowInstanceLink.setCreateDate(now);
		workflowInstanceLink.setModifiedDate(now);
		workflowInstanceLink.setUserId(userId);
		workflowInstanceLink.setUserName(user.getFullName());
		workflowInstanceLink.setGroupId(groupId);
		workflowInstanceLink.setCompanyId(companyId);
		workflowInstanceLink.setClassNameId(classNameId);
		workflowInstanceLink.setClassPK(classPK);
		workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);

		workflowInstanceLinkPersistence.update(workflowInstanceLink, false);

		return workflowInstanceLink;
	}

	public void deleteWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		try {
			WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
				companyId, groupId, className, classPK);

			deleteWorkflowInstanceLink(workflowInstanceLink);

			WorkflowInstanceManagerUtil.deleteWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
		}
	}

	public void deleteWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		for (WorkflowInstanceLink workflowInstanceLink :
				workflowInstanceLinks) {

			deleteWorkflowInstanceLink(workflowInstanceLink);

			WorkflowInstanceManagerUtil.deleteWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());
		}
	}

	public String getState(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				companyId, workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstance.getState();
	}

	public WorkflowInstanceLink getWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		List<WorkflowInstanceLink> workflowInstaneLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, classPK);

		if (workflowInstaneLinks.isEmpty()) {
			throw new NoSuchWorkflowInstanceLinkException();
		}
		else {
			return workflowInstaneLinks.get(0);
		}
	}

	public List<WorkflowInstanceLink> getWorkflowInstanceLinks(
			long companyId, long groupId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowInstanceLinkPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	public boolean hasWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		try {
			getWorkflowInstanceLink(companyId, groupId, className, classPK);

			return true;
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
			return false;
		}
	}

	public boolean isEnded(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		try {
			WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
				companyId, groupId, className, classPK);

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					companyId, workflowInstanceLink.getWorkflowInstanceId());

			if (workflowInstance.getEndDate() != null) {
				return true;
			}
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
		}

		return false;
	}

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		try {
			WorkflowDefinitionLink workflowDefinitionLink =
				workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
					companyId, groupId, className);

			String workflowDefinitionName =
				workflowDefinitionLink.getWorkflowDefinitionName();
			int workflowDefinitionVersion =
				workflowDefinitionLink.getWorkflowDefinitionVersion();

			Map<String, Serializable> context = null;

			if (workflowContext != null) {
				workflowContext = new HashMap<String, Serializable>(
					workflowContext);
			}
			else {
				workflowContext = new HashMap<String, Serializable>();
			}

			workflowContext.put(ContextConstants.COMPANY_ID, companyId);
			workflowContext.put(ContextConstants.GROUP_ID, groupId);
			workflowContext.put(ContextConstants.ENTRY_CLASS_NAME, className);
			workflowContext.put(ContextConstants.ENTRY_CLASS_PK, classPK);

			WorkflowHandler workflowHandler =
				WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

			workflowContext.put(
				ContextConstants.ENTRY_TYPE, workflowHandler.getType());

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.startWorkflowInstance(
					companyId, userId, workflowDefinitionName,
					workflowDefinitionVersion, null, workflowContext);

			addWorkflowInstanceLink(
				userId, companyId, groupId, className, classPK,
				workflowInstance.getWorkflowInstanceId());
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return;
		}
	}

	public void updateClassPK(
			long companyId, long groupId, String className, long oldClassPK,
			long newClassPK)
		throws PortalException, SystemException {

		List<WorkflowInstanceLink> workflowInstanceLinks =
			getWorkflowInstanceLinks(companyId, groupId, className, oldClassPK);

		for (WorkflowInstanceLink workflowInstanceLink :
				workflowInstanceLinks) {

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.getWorkflowInstance(
					workflowInstanceLink.getCompanyId(),
					workflowInstanceLink.getWorkflowInstanceId());

			workflowInstanceLink.setClassPK(newClassPK);

			workflowInstanceLinkPersistence.update(
				workflowInstanceLink, false);

			Map<String, Serializable> workflowContext =
				new HashMap<String, Serializable>(
					workflowInstance.getContext());

			workflowContext.put(ContextConstants.ENTRY_CLASS_PK, newClassPK);

			WorkflowInstanceManagerUtil.updateContext(
				workflowInstanceLink.getCompanyId(),
				workflowInstanceLink.getWorkflowInstanceId(), workflowContext);
		}
	}

}