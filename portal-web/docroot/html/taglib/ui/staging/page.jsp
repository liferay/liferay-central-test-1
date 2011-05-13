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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean extended = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:staging:extended"));
long groupId = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:groupId"));
long layoutSetBranchId = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:layoutSetBranchId"));
long selPlid = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:selPlid"));

LayoutSetBranch layoutSetBranch = null;

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = themeDisplay.getScopeGroup();

	if (group.isLayout()) {
		group = layout.getGroup();
	}
}

String publishNowDialogTitle = null;
String publishScheduleDialogTitle = null;

if (layoutSetBranchId > 0) {
	publishNowDialogTitle = "publish-x-to-live-now";
	publishScheduleDialogTitle = "schedule-publication-of-x-to-live";
}
else {
	publishNowDialogTitle = "publish-to-live-now";
	publishScheduleDialogTitle = "schedule-publication-to-live";
}

Group liveGroup = null;
Group stagingGroup = null;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
	stagingGroup = group;
}
else if (group.isStaged()) {
	if (group.isStagedRemotely()) {
		stagingGroup = group;

		if (layoutSetBranchId > 0) {
			publishNowDialogTitle = "publish-x-to-remote-live-now";
			publishScheduleDialogTitle = "schedule-publication-of-x-to-remote-live";
		}
		else {
			publishNowDialogTitle = "publish-to-remote-live-now";
			publishScheduleDialogTitle = "schedule-publication-to-remote-live";
		}
	}
	else {
		liveGroup = group;
		stagingGroup = group.getStagingGroup();
	}
}
%>

<c:if test="<%= stagingGroup != null %>">
	<span class="staging-icon-menu-container">
		<liferay-ui:icon-menu align="auto" cssClass="staging-icon-menu" direction="down" extended="<%= extended %>" icon='<%= extended ? themeDisplay.getPathThemeImages() + "/dockbar/staging.png" : StringPool.BLANK %>' message='<%= extended ? "backstage" : StringPool.BLANK %>' showWhenSingleIcon="<%= true %>">
			<c:if test="<%= (stagingGroup.isStagedRemotely() || GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.PUBLISH_STAGING)) %>">

				<%
				PortletURL publishToLiveURL = null;

				if (groupId > 0) {
					publishToLiveURL = new PortletURLImpl(request, PortletKeys.LAYOUTS_ADMIN, plid, PortletRequest.RENDER_PHASE);

					publishToLiveURL.setWindowState(LiferayWindowState.EXCLUSIVE);
					publishToLiveURL.setPortletMode(PortletMode.VIEW);

					publishToLiveURL.setParameter("struts_action", "/layouts_admin/publish_layouts");
					publishToLiveURL.setParameter(Constants.CMD, "publish_to_live");
					publishToLiveURL.setParameter("pagesRedirect", currentURL);
					publishToLiveURL.setParameter("groupId", String.valueOf(groupId));
					publishToLiveURL.setParameter("selPlid", String.valueOf(selPlid));
				}
				else {
					publishToLiveURL = themeDisplay.getURLPublishToLive();
				}

				String publishNowMessage = null;
				String publishScheduleMessage = null;

				if (layoutSetBranchId > 0) {
					layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

					publishToLiveURL.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
					publishToLiveURL.setParameter("layoutSetBranchName", layoutSetBranch.getName());

					publishNowMessage = LanguageUtil.format(pageContext, publishNowDialogTitle, layoutSetBranch.getName());
					publishScheduleMessage = LanguageUtil.format(pageContext, publishScheduleDialogTitle, layoutSetBranch.getName());
				}
				else {
					publishNowMessage = LanguageUtil.get(pageContext, publishNowDialogTitle);
					publishScheduleMessage = LanguageUtil.get(pageContext, publishScheduleDialogTitle);
				}

				String publishLayoutSetBranchToLiveURL = publishToLiveURL.toString();
				%>

				<liferay-ui:icon url="<%= publishLayoutSetBranchToLiveURL %>" message="<%= publishNowMessage %>" id='<%= layoutSetBranchId + "publishNowLink" %>' image="maximize" />

				<%
				publishLayoutSetBranchToLiveURL = HttpUtil.addParameter(publishLayoutSetBranchToLiveURL, "schedule", String.valueOf(true));
				%>

				<liferay-ui:icon url="<%= publishLayoutSetBranchToLiveURL.toString() %>" message="<%= publishScheduleMessage %>" id='<%= layoutSetBranchId + "publishScheduleLink" %>' image="time" />

				<aui:script use="aui-base">
					var publishnowLink = A.one('#<portlet:namespace /><%= layoutSetBranchId + "publishNowLink" %>');

					if (publishnowLink) {
						publishnowLink.detach('click');

						publishnowLink.on(
							'click',
							function(event) {
								event.preventDefault();

								Liferay.LayoutExporter.publishToLive(
									{
									title: '<%= UnicodeFormatter.toString(publishNowMessage) %>',
									url: event.currentTarget.attr('href')
								});

							}
						);
					}

					var publishScheduleLink = A.one('#<portlet:namespace /><%= layoutSetBranchId + "publishScheduleLink" %>');

					if (publishScheduleLink) {
						publishScheduleLink.detach('click');

						publishScheduleLink.on(
							'click',
							function(event) {
								event.preventDefault();

								Liferay.LayoutExporter.publishToLive(
									{
									title: '<%= UnicodeFormatter.toString(publishScheduleMessage) %>',
									url: event.currentTarget.attr('href')
								});
							}
						);
					}
				</aui:script>
			</c:if>
		</liferay-ui:icon-menu>
	</span>
</c:if>