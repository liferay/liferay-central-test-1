<%
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
%>

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowInstance workflowInstance = null;

if (row != null) {
	Object result = row.getObject();

	workflowInstance = (WorkflowInstance)row.getParameter("workflowInstance");
}
else {
	workflowInstance = (WorkflowInstance)request.getAttribute(WebKeys.WORKFLOW_INSTANCE);
}
%>

<liferay-ui:icon-menu showExpanded="<%= (row == null) %>" showWhenSingleIcon="<%= (row == null) %>">
	<c:if test="<%= !workflowInstance.isComplete() %>">

		<%
		List<String> transitionNames = WorkflowInstanceManagerUtil.getNextTransitionNames(company.getCompanyId(), user.getUserId(), workflowInstance.getWorkflowInstanceId());

		for (String transitionName : transitionNames) {
			String message = "proceed";

			if (Validator.isNotNull(transitionName)) {
				message = transitionName;
			}
		%>

			<portlet:actionURL var="editURL">
				<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SIGNAL %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="workflowInstanceId" value="<%= StringUtil.valueOf(workflowInstance.getWorkflowInstanceId()) %>" />

				<c:if test="<%= transitionName != null %>">
					<portlet:param name="transitionName" value="<%= transitionName %>" />
				</c:if>
			</portlet:actionURL>

			<liferay-ui:icon
				image="../aui/shuffle"
				message="<%= message %>"
				method="get"
				url="<%= editURL %>"
			/>

		<%
		}
		%>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowInstanceId" value="<%= StringUtil.valueOf(workflowInstance.getWorkflowInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="undo"
			message="withdraw-request"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>