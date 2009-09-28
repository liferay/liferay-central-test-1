<%
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
%>

<%@ include file="/html/portlet/workflow_tasks/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "pending");

boolean completed = false;

if (tabs1.equals("completed")) {
	completed = true;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<liferay-ui:tabs
	names="pending,completed"
	url="<%= portletURL.toString() %>"
/>

<%
try {
%>

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.get(pageContext, completed ? "there-are-no-completed-tasks" : "there-are-no-pending-tasks-assigned-to-you") %>'
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= TaskInstanceManagerUtil.getTaskInstanceInfosByUser(user.getUserId(), completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null) %>"
			total="<%= TaskInstanceManagerUtil.getTaskInstanceInfoCountByUser(user.getUserId()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.TaskInstanceInfo"
			modelVar="taskInstanceInfo"
			stringKey="<%= true %>"
		>
			<liferay-ui:search-container-row-parameter
				name="taskInstanceInfo"
				value="<%= taskInstanceInfo %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				property="description"
			/>

			<liferay-ui:search-container-column-text
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-text
				name="due-date"
				property="dueDate"
			/>

			<c:if test="<%= !completed %>">
				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/workflow_tasks/task_action.jsp"
				/>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" />
	</liferay-ui:search-container>

<%
}
catch (WorkflowException we) {
	if (_log.isWarnEnabled()) {
		_log.warn("Error retrieving the tasks of user " + user.getUserId(), we);
	}
%>

	<div class="portlet-msg-error">
		<liferay-ui:message key="an-error-occurred-while-retrieving-the-list-of-tasks" />
	</div>

<%
}
%>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.workflow_tasks.view.jsp");
%>