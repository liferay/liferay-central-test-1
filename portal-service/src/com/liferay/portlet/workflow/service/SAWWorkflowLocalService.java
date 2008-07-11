/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service;


/**
 * <a href="SAWWorkflowLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.workflow.service.impl.SAWWorkflowLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.workflow.service.SAWWorkflowLocalServiceFactory
 * @see com.liferay.portlet.workflow.service.SAWWorkflowLocalServiceUtil
 *
 */
public interface SAWWorkflowLocalService {
	public java.lang.String deploy(java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getDefinitionXml(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getDefinitionsXml(long definitionId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getDefinitionsCountXml(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getInstancesXml(long definitionId, long instanceId,
		java.lang.String definitionName, java.lang.String definitionVersion,
		java.lang.String startDateGT, java.lang.String startDateLT,
		java.lang.String endDateGT, java.lang.String endDateLT,
		java.lang.String userId, boolean hideEndedTasks,
		boolean retrieveUserInstances, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getInstancesCountXml(long definitionId,
		long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, java.lang.String userId,
		boolean hideEndedTasks, boolean retrieveUserInstances,
		boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getTaskXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getTaskFormElementsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getTaskTransitionsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getUserTasksXml(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, java.lang.String userId,
		boolean hideEndedTasks, boolean andOperator, int begin, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getUserTasksCountXml(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public void signalInstance(long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public void signalToken(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String startWorkflow(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String getCurrentTasksXml(long instanceId, long tokenId,
		java.lang.String userId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.util.Map updateTask(long taskId, java.lang.String transition,
		java.lang.String userId, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;

	public java.lang.String updateTaskXml(long taskId,
		java.lang.String transition, java.lang.String userId,
		java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException;
}