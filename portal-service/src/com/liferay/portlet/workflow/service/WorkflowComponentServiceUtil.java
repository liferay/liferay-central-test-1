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
 * <a href="WorkflowComponentServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.workflow.service.WorkflowComponentService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.workflow.service.WorkflowComponentService
 *
 */
public class WorkflowComponentServiceUtil {
	public static java.util.List getCurrentTasks(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getCurrentTasks(instanceId, tokenId);
	}

	public static java.lang.String getCurrentTasksXml(long instanceId,
		long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getCurrentTasksXml(instanceId, tokenId);
	}

	public static java.lang.String deploy(java.lang.String xml)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().deploy(xml);
	}

	public static java.lang.Object getDefinition(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinition(definitionId);
	}

	public static java.util.List getDefinitions(long definitionId,
		java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinitions(definitionId, name, start, end);
	}

	public static java.lang.String getDefinitionsXml(long definitionId,
		java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinitionsXml(definitionId, name, start, end);
	}

	public static int getDefinitionsCount(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinitionsCount(definitionId, name);
	}

	public static java.lang.String getDefinitionsCountXml(long definitionId,
		java.lang.String name)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinitionsCountXml(definitionId, name);
	}

	public static java.lang.String getDefinitionXml(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getDefinitionXml(definitionId);
	}

	public static java.util.List getInstances(long definitionId,
		long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks,
		boolean retrieveUserInstances, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getInstances(definitionId, instanceId, definitionName,
			definitionVersion, startDateGT, startDateLT, endDateGT, endDateLT,
			hideEndedTasks, retrieveUserInstances, andOperator, start, end);
	}

	public static int getInstancesCount(long definitionId, long instanceId,
		java.lang.String definitionName, java.lang.String definitionVersion,
		java.lang.String startDateGT, java.lang.String startDateLT,
		java.lang.String endDateGT, java.lang.String endDateLT,
		boolean hideEndedTasks, boolean retrieveUserInstances,
		boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getInstancesCount(definitionId, instanceId, definitionName,
			definitionVersion, startDateGT, startDateLT, endDateGT, endDateLT,
			hideEndedTasks, retrieveUserInstances, andOperator);
	}

	public static java.lang.String getInstancesCountXml(long definitionId,
		long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks,
		boolean retrieveUserInstances, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getInstancesCountXml(definitionId, instanceId,
			definitionName, definitionVersion, startDateGT, startDateLT,
			endDateGT, endDateLT, hideEndedTasks, retrieveUserInstances,
			andOperator);
	}

	public static java.lang.String getInstancesXml(long definitionId,
		long instanceId, java.lang.String definitionName,
		java.lang.String definitionVersion, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks,
		boolean retrieveUserInstances, boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getInstancesXml(definitionId, instanceId, definitionName,
			definitionVersion, startDateGT, startDateLT, endDateGT, endDateLT,
			hideEndedTasks, retrieveUserInstances, andOperator, start, end);
	}

	public static com.liferay.portlet.workflow.model.WorkflowTask getTask(
		long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTask(taskId);
	}

	public static java.lang.String getTaskXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTaskXml(taskId);
	}

	public static java.util.List getTaskFormElements(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTaskFormElements(taskId);
	}

	public static java.lang.String getTaskFormElementsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTaskFormElementsXml(taskId);
	}

	public static java.util.List getTaskTransitions(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTaskTransitions(taskId);
	}

	public static java.lang.String getTaskTransitionsXml(long taskId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().getTaskTransitionsXml(taskId);
	}

	public static java.util.List getUserTasks(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getUserTasks(instanceId, taskName, definitionName,
			assignedTo, createDateGT, createDateLT, startDateGT, startDateLT,
			endDateGT, endDateLT, hideEndedTasks, andOperator, start, end);
	}

	public static int getUserTasksCount(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getUserTasksCount(instanceId, taskName, definitionName,
			assignedTo, createDateGT, createDateLT, startDateGT, startDateLT,
			endDateGT, endDateLT, hideEndedTasks, andOperator);
	}

	public static java.lang.String getUserTasksCountXml(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks, boolean andOperator)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getUserTasksCountXml(instanceId, taskName, definitionName,
			assignedTo, createDateGT, createDateLT, startDateGT, startDateLT,
			endDateGT, endDateLT, hideEndedTasks, andOperator);
	}

	public static java.lang.String getUserTasksXml(long instanceId,
		java.lang.String taskName, java.lang.String definitionName,
		java.lang.String assignedTo, java.lang.String createDateGT,
		java.lang.String createDateLT, java.lang.String startDateGT,
		java.lang.String startDateLT, java.lang.String endDateGT,
		java.lang.String endDateLT, boolean hideEndedTasks,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService()
				   .getUserTasksXml(instanceId, taskName, definitionName,
			assignedTo, createDateGT, createDateLT, startDateGT, startDateLT,
			endDateGT, endDateLT, hideEndedTasks, andOperator, start, end);
	}

	public static void signalInstance(long instanceId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		getService().signalInstance(instanceId);
	}

	public static void signalToken(long instanceId, long tokenId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		getService().signalToken(instanceId, tokenId);
	}

	public static java.lang.String startWorkflow(long definitionId)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().startWorkflow(definitionId);
	}

	public static java.util.Map updateTask(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().updateTask(taskId, transition, parameterMap);
	}

	public static java.lang.String updateTaskXml(long taskId,
		java.lang.String transition, java.util.Map parameterMap)
		throws com.liferay.portal.kernel.jbi.WorkflowComponentException,
			java.rmi.RemoteException {
		return getService().updateTaskXml(taskId, transition, parameterMap);
	}

	public static WorkflowComponentService getService() {
		if (_service == null) {
			throw new RuntimeException("WorkflowComponentService is not set");
		}

		return _service;
	}

	public void setService(WorkflowComponentService service) {
		_service = service;
	}

	private static WorkflowComponentService _service;
}