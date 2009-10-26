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

<%@ include file="/html/portlet/workflow_admin/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "resources");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/workflow_admin/view");
portletURL.setParameter("tabs1", tabs1);

List<WorkflowDefinition> workflowDefinitions = WorkflowDefinitionManagerUtil.getWorkflowDefinitions(0, 100, null);
%>

<liferay-ui:tabs
	names="resources,workflow-definitions"
	url="<%= portletURL.toString() %>"
/>

<c:choose>
	<c:when test='<%= tabs1.equals("resources") %>'>
		<%
		List<String> modelResources = ListUtil.fromArray(_WORKFLOW_RESOURCES);
		%>

		<portlet:actionURL var="editWorkflowLinkURL">
			<portlet:param name="struts_action" value="/workflow_admin/edit_workflow_link" />
		</portlet:actionURL>

		<aui:form action="<%= editWorkflowLinkURL %>" method="post">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= ListUtil.subList(modelResources, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= modelResources.size() %>"
				/>

				<liferay-ui:search-container-row
					className="java.lang.String"
					modelVar="modelResource"
					stringKey="<%= true %>"
				>

					<liferay-ui:search-container-row-parameter
						name="modelResource"
						value="<%= modelResource %>"
					/>

					<liferay-ui:search-container-column-text
						buffer="buffer"
						name="resource"
					>

						<%
						buffer.append("<img align=\"left\" border=\"0\" src=\"");
						buffer.append(themeDisplay.getPathThemeImages());
						buffer.append(_getIconPath(modelResource));
						buffer.append("\" style=\"margin-right: 5px\">");
						buffer.append("<strong>");
						buffer.append(LanguageUtil.get(pageContext, "model.resource." + modelResource));
						buffer.append("</strong>");
						%>

					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="workflow"
					>
						<%
						long classNameId = PortalUtil.getClassNameId(modelResource);
						%>

						<aui:select label="" name='<%= "workflowDefinitionName@" + classNameId %>'>
							<aui:option><liferay-ui:message key="no-workflow" /></aui:option>

							<%
							WorkflowLink workflowLink = null;

							try {
								workflowLink = WorkflowLinkLocalServiceUtil.getWorkflowLink(company.getCompanyId(), 0, classNameId);
							}
							catch (NoSuchWorkflowLinkException nswle) {
							}

							for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
								boolean selected = false;

								if ((workflowLink != null) && (workflowLink.getDefinitionName().equals(workflowDefinition.getWorkflowDefinitionName()))) {
									selected = true;
								}
							%>

								<aui:option label="<%= workflowDefinition.getWorkflowDefinitionName() %>" selected="<%= selected %>" />

							<%
							}
							%>

						</aui:select>
					</liferay-ui:search-container-column-text>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" />
			</liferay-ui:search-container>

			<aui:button-row>
				<aui:button type="submit" value="save" />
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:when test='<%= tabs1.equals("workflow-definitions") %>'>
		<liferay-util:include page="/html/portlet/workflow_admin/toolbar.jsp">
			<liferay-util:param name="toolbarItem" value="view-all" />
		</liferay-util:include>

		<liferay-ui:search-container
			emptyResultsMessage="no-workflow-definitions-are-defined"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= ListUtil.subList(workflowDefinitions, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= workflowDefinitions.size() %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
				modelVar="workflowDefinition"
			>

				<liferay-ui:search-container-column-text
					name="name"
					value="<%= workflowDefinition.getWorkflowDefinitionName() %>"
				/>

				<liferay-ui:search-container-column-text
					name="version"
					value="<%= String.valueOf(workflowDefinition.getWorkflowDefinitionVersion()) %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/html/portlet/workflow_admin/workflow_definition_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator paginate="<%= false %>" />
		</liferay-ui:search-container>
	</c:when>
</c:choose>

<%!
private static final String[] _WORKFLOW_RESOURCES = {
	BlogsEntry.class.getName(),
	BookmarksEntry.class.getName(),
	CalEvent.class.getName(),
	DLFileEntry.class.getName(),
	IGImage.class.getName(),
	JournalArticle.class.getName(),
	MBMessage.class.getName(),
	WikiPage.class.getName()
};

private String _getIconPath(String modelResource) {
	if (modelResource.equals(BlogsEntry.class.getName())) {
		return "/common/page.png";
	}
	else if (modelResource.equals(BookmarksEntry.class.getName())) {
		return "/ratings/star_hover.png";
	}
	else if (modelResource.equals(DLFileEntry.class.getName())) {
		return "/document_library/page.png";
	}
	else if (modelResource.equals(IGImage.class.getName())) {
		return "/document_library/bmp.png";
	}
	else if (modelResource.equals(JournalArticle.class.getName())) {
		return "/common/history.png";
	}
	else if (modelResource.equals(Layout.class.getName())) {
		return "/common/page.png";
	}
	else if (modelResource.equals(MBMessage.class.getName())) {
		return "/common/conversation.png";
	}
	else if (modelResource.equals(Organization.class.getName())) {
		return "/common/organization_icon.png";
	}
	else if (modelResource.equals(User.class.getName())) {
		return "/common/user_icon.png";
	}
	else if (modelResource.equals(WikiPage.class.getName())) {
		return "/common/pages.png";
	}
	else {
		return "/common/page.png";
	}
}
%>