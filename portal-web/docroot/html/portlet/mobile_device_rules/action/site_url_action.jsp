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

<%@ include file="/html/portlet/mobile_device_rules/action/init.jsp" %>

<%
long actionGroupId = 0L;
long actionLayoutId = 0L;

if (!isAdd) {
	actionGroupId = GetterUtil.getLong(actionTypeSettings.getProperty("groupId"));
	actionLayoutId = GetterUtil.get(actionTypeSettings.getProperty("layoutId"), 0L);
}

request.setAttribute("actionGroupId", actionGroupId);
request.setAttribute("actionLayoutId", actionLayoutId);

String namespace = StringPool.BLANK;

List<Group> groups = GroupServiceUtil.getUserSites();

if (resourceResponse != null) {
	namespace = resourceResponse.getNamespace();
}
else if (renderResponse != null) {
	namespace = renderResponse.getNamespace();
}
%>

<aui:select label="site" name="groupId" onChange='<%= namespace + "changeDisplay();" %>'>
	<aui:option disabled="<%= true %>" label="select-a-site" selected="<%= actionGroupId == 0 %>" value="0" />

	<%
	int groupCount = 0;

	for (Group selGroup : groups) {
		if (!selGroup.isUser() && !selGroup.isControlPanel()) {
			groupCount++;
	%>

		<aui:option label="<%= selGroup.getName() %>" selected="<%= selGroup.getGroupId() == actionGroupId %>" value="<%= selGroup.getGroupId() %>" />

	<%
		}
	}
	%>

	<c:if test="<%= groupCount == 0 %>">
		<aui:option disabled="<%= true %>" label="no-available-sites" value="0" />
	</c:if>
</aui:select>

<aui:input name="originalLayoutId" type="hidden" value="<%=actionLayoutId %>" />

<div id="<%= namespace %>layouts">
	<c:import url="/html/portlet/mobile_device_rules/action/site_url_action_layouts.jsp" />
</div>