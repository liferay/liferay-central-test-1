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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");

String structureId = BeanParamUtil.getString(article, request, "structureId");

String deleteButtonLabel = "delete-this-version";

if ((article != null) && article.isDraft()) {
	deleteButtonLabel = "discard-draft";
}
%>

<div class="article-toolbar" id="<portlet:namespace />articleToobar"></div>

<aui:script use="aui-toolbar,aui-dialog-iframe">
	var permissionPopUp = null;

	new A.Toolbar(
		{
			activeState: false,
			boundingBox: '#<portlet:namespace />articleToobar',
			children: [
				<c:if test="<%= Validator.isNotNull(structureId) && (classNameId == 0) %>">
					{
						icon: 'search',
						id: '<portlet:namespace />previewArticleButton',
						label: '<liferay-ui:message key="preview" />'
					},
				</c:if>

				<c:if test="<%= Validator.isNotNull(structureId) %>">
					{
						icon: 'arrowreturnthick-1-b',
						id: '<portlet:namespace />downloadArticleContentButton',
						label: '<liferay-ui:message key="download" />'
					},
				</c:if>

				<c:if test="<%= article != null && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"
						modelResource="<%= JournalArticle.class.getName() %>"
						modelResourceDescription="<%= article.getTitle(locale) %>"
						resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
						var="permissionsURL"
					/>

					{
					handler: function(event) {
						if (!permissionPopUp) {
							permissionPopUp = new A.Dialog(
								{
									centered: true,
									modal: true,
									title: '<liferay-ui:message key="permissions" />',
									width: 700
								}
							).plug(
								A.Plugin.DialogIframe,
								{
									after: {
										load: Liferay.Util.afterIframeLoaded
									},
									uri: '<%= permissionsURL %>'
								}
							).render();
						}
						else {
							permissionPopUp.iframe.node.get('contentWindow.location').reload(true);
						}

						permissionPopUp.show();
						permissionPopUp.centered();

					},
					icon: 'key',
					label: '<liferay-ui:message key="permissions" />'
				},
				</c:if>

				<c:if test="<%= (article != null) && !article.isExpired() && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) && !article.isApproved() %>">
					{
						handler: function() {
							<portlet:namespace />expireArticle();
						},
						icon: 'minusthick',
						label: '<liferay-ui:message key="expire-this-version" />'
					},
				</c:if>

				<c:if test="<%= (article != null) && JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) && !article.isApproved() && !article.isDraft() %>">
					{
						handler: function() {
							<portlet:namespace />deleteArticle();
						},
						icon: 'circle-minus',
						label: '<liferay-ui:message key="<%= deleteButtonLabel %>" />'
					},
				</c:if>

				<c:if test="<%= article != null %>">
					<portlet:renderURL var="viewHistoryURL">
						<portlet:param name="struts_action" value="/journal/view_article_history" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
						<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
					</portlet:renderURL>

					{
						handler: function (event) {
							window.location = '<%= viewHistoryURL %>';
						},
						icon: 'clock',
						label: '<liferay-ui:message key="view-history" />'
					}
				</c:if>
			]
		}
	).render();
</aui:script>