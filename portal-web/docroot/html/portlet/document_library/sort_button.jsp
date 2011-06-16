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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
%>

<liferay-ui:icon-menu align="left" direction="down" icon="" message="sort-by" showExpanded="<%= false %>" showWhenSingleIcon="<%= false %>">

	<portlet:resourceURL var="sortByTitle">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="title" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("title") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	String taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('title')";
	%>

	<liferay-ui:icon
		image="folder"
		message="title"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByCreationDate">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="creationDate" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("creationDate") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('creationDate')";
	%>

	<liferay-ui:icon
		image="folder"
		message="create-date"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByModifiedDate">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="modifiedDate" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("modifiedDate") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
		taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('modifiedDate')";
	%>

	<liferay-ui:icon
		image="folder"
		message="modified-date"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortByReadCount">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="readCount" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("readCount") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
		taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('readCount')";
	%>

	<liferay-ui:icon
		image="folder"
		message="read-count"
		url="<%= taglibUrl %>"
	/>

	<portlet:resourceURL var="sortBySize">
		<portlet:param name="struts_action" value="/document_library/view" />
		<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="orderByCol" value="size" />
		<portlet:param name="orderByType" value='<%= (orderByCol.equals("size") && orderByType.equals("asc")) ? "desc" : "asc" %>' />
	</portlet:resourceURL>

	<%
	taglibUrl = "javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('size')";
	%>

	<liferay-ui:icon
		image="folder"
		message="size"
		url="<%= taglibUrl %>"
	/>
</liferay-ui:icon-menu>

<aui:script use="aui-base">
	var documentLibraryContainer = A.one('#<portlet:namespace />documentLibraryContainer');

	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	function handleSortEntries(event){
		documentLibraryContainer.loadingmask.hide();

		var responseData = event.responseData;

		var content = A.Node.create(responseData);

		var sortButtonContainer = A.one('#<portlet:namespace />sortButtonContainer');
		var sortButton = content.one('#<portlet:namespace />sortButton')

		sortButtonContainer.setContent(sortButton);

		var entries = content.one('#<portlet:namespace />entries');

		entriesContainer.setContent(entries);
	}

	Liferay.on(
		'<portlet:namespace />dataRetrieveSuccess',
		function(event) {
			var data = event.data;

			if (event.callbackParams) {
				var action = event.callbackParams['<portlet:namespace />handler'];

				if (action === 'sortEntries') {
					handleSortEntries(event);
				}
			}
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />sortEntries',
		function (orderByCol) {
			var config = {
				'<portlet:namespace />struts_action': '/document_library/view',
				'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />viewSortButton': <%= Boolean.TRUE.toString() %>,
				'<portlet:namespace />orderByCol': orderByCol,
				'<portlet:namespace />orderByType': '<%= (orderByCol.equals("title") && orderByType.equals("asc")) ? "desc" : "asc" %>',
				'<portlet:namespace />handler': 'sortEntries'
			};

			Liferay.fire(
				'<portlet:namespace />dataRequest',
				{
					requestParams: config,
					callbackParams: {
						'<portlet:namespace />handler': 'sortEntries'
					}
				}
			);
		},
		['aui-base']
	);
</aui:script>