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
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Group liveGroup = (Group)request.getAttribute("edit_pages.jsp-liveGroup");

long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
long liveGroupId = ((Long)request.getAttribute("edit_pages.jsp-liveGroupId")).longValue();
long stagingGroupId = ((Long)request.getAttribute("edit_pages.jsp-stagingGroupId")).longValue();

Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();
long layoutId = ((Long)request.getAttribute("edit_pages.jsp-layoutId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

PortletURL portletURL = (PortletURL)request.getAttribute("edit_pages.jsp-portletURL");
PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

String[] mainSections = PropsValues.LAYOUT_FORM_UPDATE;

if (!group.isUser() && selLayout.isTypePortlet()) {
	mainSections = ArrayUtil.append(mainSections, "customization-settings");
}

String[][] categorySections = {mainSections};
%>

<div class="header-row title">
	<div class="header-row-content">
		<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />

		<aui:button-row cssClass="edit-toolbar" id='<%= liferayPortletResponse.getNamespace() + "layoutToolbar" %>'>
			<c:if test="<%= stagingGroupId > 0 %>">
				<liferay-ui:staging groupId="<%= groupId %>" privateLayout="<%= privateLayout %>" selPlid="<%= selPlid %>" showManageBackstages="<%= true %>" />
			</c:if>
		</aui:button-row>
	</div>
</div>

<portlet:actionURL var="editLayoutURL">
	<portlet:param name="struts_action" value="/manage_pages/edit_layouts" />
</portlet:actionURL>

<aui:form action="<%= editLayoutURL %>" cssClass="edit-layout-form"  enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveLayout();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selPlid) %>' />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= stagingGroupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutId %>" />
	<aui:input name="selPlid" type="hidden" value="<%= selPlid %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<c:if test="<%= !group.isLayoutPrototype() && (selLayout != null) %>">
		<c:if test="<%= liveGroup.isStaged() %>">
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
			</div>
		</c:if>

		<liferay-security:permissionsURL
			modelResource="<%= Layout.class.getName() %>"
			modelResourceDescription="<%= selLayout.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(selLayout.getPlid()) %>"
			var="permissionURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<aui:script use="aui-dialog,aui-dialog-iframe,aui-toolbar">
			var buttonRow = A.one('#<portlet:namespace />layoutToolbar');

			var popUp = null;

			var layoutToolbar = new A.Toolbar(
				{
					activeState: false,
					boundingBox: buttonRow,
					children: [
						{
							handler: function(event) {
								if (!popUp) {
									var content = A.one('#<portlet:namespace />addLayout');

									popUp = new A.Dialog(
										{
											bodyContent: content.show(),
											centered: true,
											title: '<liferay-ui:message key="add-child-page" />',
											modal: true,
											width: 500
										}
									).render();
								}

								popUp.show();

								Liferay.Util.focusFormField(content.one('input:text'));
							},
							icon: 'circle-plus',
							label: '<liferay-ui:message key="add-child-page" />'
						},
						{
							handler: function(event) {
								Liferay.Util.openWindow(
									{
										cache: false,
										dialog: {
											width: 700
										},
										id: '<portlet:namespace /><%= selPlid %>_permissions',
										title: '<liferay-ui:message key="permissions" />',
										uri: '<%= permissionURL %>'
									}
								);
							},
							icon: 'key',
							label: '<liferay-ui:message key="permissions" />'
						},
						{
							handler: function(event) {
								<c:choose>
									<c:when test="<%= (selPlid == themeDisplay.getPlid()) || (selPlid == refererPlid) %>">
										alert('<%= UnicodeLanguageUtil.get(pageContext, "you-cannot-delete-this-page-because-you-are-currently-accessing-this-page") %>');
									</c:when>
									<c:otherwise>
										if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
											document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
											document.<portlet:namespace />fm.<portlet:namespace />redirect.value = '<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", selLayout.getParentPlid()) %>';
											submitForm(document.<portlet:namespace />fm);
										}
									</c:otherwise>
								</c:choose>
							},
							icon: 'circle-minus',
							label: '<liferay-ui:message key="delete" />'
						}
					]
				}
			).render();

			buttonRow.setData('layoutToolbar', layoutToolbar);
		</aui:script>
	</c:if>

	<liferay-ui:form-navigator
		categoryNames="<%= _CATEGORY_NAMES %>"
		categorySections="<%= categorySections %>"
		jspPath="/html/portlet/layouts_admin/layout/"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveLayout(action) {
		if (action) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
		}
		else {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = 'update';
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value += Liferay.Util.getHistoryParam('<portlet:namespace />');

		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>

<%!
private static String[] _CATEGORY_NAMES = {""};
%>