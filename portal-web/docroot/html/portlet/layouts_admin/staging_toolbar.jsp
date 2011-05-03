<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();

PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

PortletURL publishToLiveURL = renderResponse.createRenderURL();

publishToLiveURL.setWindowState(LiferayWindowState.EXCLUSIVE);
publishToLiveURL.setPortletMode(PortletMode.VIEW);

publishToLiveURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
publishToLiveURL.setParameter(Constants.CMD, "publish_to_live");
publishToLiveURL.setParameter("pagesRedirect", redirectURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid);
publishToLiveURL.setParameter("groupId", String.valueOf(groupId));
publishToLiveURL.setParameter("selPlid", String.valueOf(selPlid));

PortletURL publishToRemoteURL = PortletURLUtil.clone(publishToLiveURL, renderResponse);

publishToRemoteURL.setParameter(Constants.CMD, "publish_to_remote");
%>

<liferay-ui:error exception="<%= RemoteExportException.class %>">

	<%
	RemoteExportException ree = (RemoteExportException)errorException;
	%>

	<c:if test="<%= ree.getType() == RemoteExportException.BAD_CONNECTION %>">
		<%= LanguageUtil.format(pageContext, "could-not-connect-to-address-x.-please-verify-that-the-specified-port-is-correct-and-that-the-remote-server-is-configured-to-accept-requests-from-this-server", "<em>" + ree.getURL() + "</em>") %>
	</c:if>
	<c:if test="<%= ree.getType() == RemoteExportException.NO_GROUP %>">
		<%= LanguageUtil.format(pageContext, "remote-group-with-id-x-does-not-exist", ree.getGroupId()) %>
	</c:if>
	<c:if test="<%= ree.getType() == RemoteExportException.NO_LAYOUTS %>">
		<liferay-ui:message key="no-pages-are-selected-for-export" />
	</c:if>
</liferay-ui:error>

<div class="portlet-msg-alert">
	<liferay-ui:message key="the-staging-environment-is-activated-changes-have-to-be-published-to-make-them-available-to-end-users" />

	<div id="<portlet:namespace />stagingToobar"></div>
</div>

<aui:script use="aui-toolbar">
	new A.Toolbar(
		{
			activeState: false,
			boundingBox: '#<portlet:namespace />stagingToobar',
			children: [
				<c:if test="<%= !liveGroup.isStagedRemotely() %>">
					{
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="publish-to-live-now" />',
									url: '<%= publishToLiveURL %>'
								}
							);
						},
						icon: 'arrowreturnthick-1-t',
						label: '<liferay-ui:message key="publish-to-live-now" />'
					},

					<%
					publishToLiveURL.setParameter("schedule", String.valueOf(true));
					%>

					{
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="schedule-publication-to-live" />',
									url: '<%= publishToLiveURL %>'
								}
							);
						},
						icon: 'clock',
						label: '<liferay-ui:message key="schedule-publication-to-live" />'
					},

					<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="importLayoutsURL">
						<portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
						<portlet:param name="<%= Constants.CMD %>" value="copy_from_live" />
						<portlet:param name="pagesRedirect" value='<%= redirectURL.toString() + "&" + renderResponse.getNamespace() + "selPlid=" + selPlid %>' />
						<portlet:param name="groupId" value="<%= String.valueOf(liveGroupId) %>" />
					</portlet:renderURL>

					{
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="copy-from-live" />',
									url: '<%= importLayoutsURL %>'
								}
							);
						},
						icon: 'copy',
						label: '<liferay-ui:message key="copy-from-live" />'
					}
				</c:if>

				<c:if test="<%= liveGroup.isStagedRemotely() %>">
					{
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="publish-to-remote-live-now" />',
									url: '<%= publishToRemoteURL %>'
								}
							);
						},
						icon: 'arrowreturnthick-1-t',
						label: '<liferay-ui:message key="publish-to-remote-live-now" />'
					},

					<%
					publishToRemoteURL.setParameter("schedule", String.valueOf(true));
					%>

					{
						handler: function (event) {
							Liferay.LayoutExporter.publishToLive(
								{
									title: '<liferay-ui:message key="schedule-publication-to-remote-live" />',
									url: '<%= publishToRemoteURL %>'
								}
							);
						},
						icon: 'clock',
						label: '<liferay-ui:message key="schedule-publication-to-remote-live" />'
					}
				</c:if>
			]
		}
	).render();
</aui:script>