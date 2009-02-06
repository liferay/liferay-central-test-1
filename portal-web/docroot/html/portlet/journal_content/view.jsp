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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);
%>

<c:choose>
	<c:when test="<%= themeDisplay.isStateExclusive() %>">

		<%
		RuntimeLogic portletLogic = new PortletLogic(application, request, response, renderRequest, renderResponse);
		RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
		RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

		String content = articleDisplay.getContent();

		content = RuntimePortletUtil.processXML(request, content, portletLogic);
		content = RuntimePortletUtil.processXML(request, content, actionURLLogic);
		content = RuntimePortletUtil.processXML(request, content, renderURLLogic);
		%>

		<%= content %>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= articleDisplay != null %>">

				<%
				RuntimeLogic portletLogic = new PortletLogic(application, request, response, renderRequest, renderResponse);
				RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
				RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

				String content = articleDisplay.getContent();

				content = RuntimePortletUtil.processXML(request, content, portletLogic);
				content = RuntimePortletUtil.processXML(request, content, actionURLLogic);
				content = RuntimePortletUtil.processXML(request, content, renderURLLogic);

				PortletURL portletURL = renderResponse.createRenderURL();
				%>

				<c:if test="<%= showAvailableLocales %>">

					<%
					String[] availableLocales = articleDisplay.getAvailableLocales();
					%>

					<c:if test="<%= availableLocales.length > 0 %>">
						<div class="lfr-meta-actions locale-controls">
							<liferay-ui:language languageIds="<%= availableLocales %>" displayStyle="<%= 0 %>" />
						</div>
					</c:if>
				</c:if>

				<div class="journal-content-article" id="article_<%= articleDisplay.getGroupId() %>_<%= articleDisplay.getArticleId() %>_<%= articleDisplay.getVersion() %>">
					<%= content %>
				</div>

				<c:if test="<%= articleDisplay.isPaginate() %>">
					<liferay-ui:page-iterator
						cur="<%= articleDisplay.getCurrentPage() %>"
						curParam='<%= "page" %>'
						delta="<%= 1 %>"
						maxPages="<%= 25 %>"
						total="<%= articleDisplay.getNumberOfPages() %>"
						type="article"
						url="<%= portletURL.toString() %>"
					/>

					<br />
				</c:if>
			</c:when>
			<c:otherwise>

				<%
				renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
				%>

				<br />

				<div class="portlet-msg-info">
					<liferay-ui:message key="select-existing-web-content-or-add-some-web-content-to-be-displayed-in-this-portlet" />
				</div>

				<br />

				<c:if test="<%= Validator.isNotNull(articleId) %>">
					<br />

					<span class="portlet-msg-error">
					<%= LanguageUtil.format(pageContext, "x-is-expired,-is-not-approved,-does-not-have-any-content,-or-no-longer-exists", articleId) %>
					</span>

					<br />
				</c:if>
			</c:otherwise>
		</c:choose>

		<%
		boolean showEditArticleIcon = (articleDisplay != null) && JournalArticlePermission.contains(permissionChecker, articleDisplay.getGroupId(), articleDisplay.getArticleId(), ActionKeys.UPDATE);
		boolean showEditTemplateIcon = (articleDisplay != null) && Validator.isNotNull(articleDisplay.getTemplateId()) && JournalTemplatePermission.contains(permissionChecker, articleDisplay.getGroupId(), articleDisplay.getTemplateId(), ActionKeys.UPDATE);
		boolean showSelectArticleIcon = PortletPermissionUtil.contains(permissionChecker, plid, portletDisplay.getId(), ActionKeys.CONFIGURATION);
		boolean showAddArticleIcon = PortletPermissionUtil.contains(permissionChecker, plid, portletDisplay.getId(), ActionKeys.CONFIGURATION) && JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE) && JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.APPROVE_ARTICLE);

		boolean isStaged = (layout != null) && layout.getGroup().hasStagingGroup();
		%>

		<c:if test="<%= ((showEditArticleIcon || showEditTemplateIcon || showSelectArticleIcon || showAddArticleIcon) && !isStaged) || (enablePrint || enableRatings || enableComments || enableConversions) %>">
			<div class="lfr-meta-actions edit-controls">
				<c:if test="<%= themeDisplay.isSignedIn() && ((showEditArticleIcon || showEditTemplateIcon || showSelectArticleIcon || showAddArticleIcon) && !isStaged) %>">
					<c:if test="<%= showEditArticleIcon %>">
						<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL" portletName="<%= PortletKeys.JOURNAL %>">
							<portlet:param name="struts_action" value="/journal/edit_article" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
							<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
							<portlet:param name="version" value="<%= String.valueOf(articleDisplay.getVersion()) %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon image="edit" message="edit-web-content" url="<%= editURL %>" />
					</c:if>

					<c:if test="<%= showEditTemplateIcon %>">
						<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editTemplateURL" portletName="<%= PortletKeys.JOURNAL %>">
							<portlet:param name="struts_action" value="/journal/edit_template" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
							<portlet:param name="templateId" value="<%= articleDisplay.getTemplateId() %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon image="../document_library/xml" message="edit-template" url="<%= editTemplateURL %>" />
					</c:if>

					<c:if test="<%= showSelectArticleIcon %>">
						<liferay-ui:icon image="configuration" message="select-web-content" url="<%= portletDisplay.getURLConfiguration() %>" />
					</c:if>

					<c:if test="<%= showAddArticleIcon %>">
						<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addArticleURL" portletName="<%= PortletKeys.JOURNAL %>">
							<portlet:param name="struts_action" value="/journal/edit_article" />
							<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon image="add_article" message="add-web-content" url="<%= addArticleURL %>" />
					</c:if>
				</c:if>

				<c:if test="<%= (articleDisplay != null) %>">
					<c:if test="<%= enableConversions %>">

						<%
						PortletURL exportArticleURL = renderResponse.createActionURL();

						exportArticleURL.setWindowState(LiferayWindowState.EXCLUSIVE);

						exportArticleURL.setParameter("struts_action", "/journal_content/export_article");
						exportArticleURL.setParameter("groupId", String.valueOf(articleDisplay.getGroupId()));
						exportArticleURL.setParameter("articleId", articleDisplay.getArticleId());
						%>

						<liferay-ui:icon-list>

							<%
							for (String extension : extensions) {
								exportArticleURL.setParameter("targetExtension", extension);
							%>

								<liferay-ui:icon
									image='<%= "../document_library/" + extension %>'
									message="<%= extension.toUpperCase() %>"
									url="<%= exportArticleURL.toString() %>"
									label="<%= true %>"
								/>

							<%
							}
							%>

						</liferay-ui:icon-list>
					</c:if>

					<c:if test="<%= enablePrint %>">

						<%
						PortletURL printPageURL = renderResponse.createRenderURL();

						printPageURL.setWindowState(LiferayWindowState.EXCLUSIVE);

						printPageURL.setParameter("struts_action", "/journal_content/view");
						printPageURL.setParameter("groupId", String.valueOf(articleDisplay.getGroupId()));
						printPageURL.setParameter("articleId", articleDisplay.getArticleId());
						printPageURL.setParameter("viewMode", Constants.PRINT);
						%>

						<script type="text/javascript">
							function <portlet:namespace />printPage() {
								window.open('<%= printPageURL %>', '', "directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640");
							}
						</script>

						<liferay-ui:icon image="print" url='<%= "javascript: " + renderResponse.getNamespace() + "printPage();" %>' />
					</c:if>

					<c:if test="<%= enableRatings %>">
						<liferay-ui:ratings
							className="<%= JournalArticle.class.getName() %>"
							classPK="<%= articleDisplay.getResourcePrimKey() %>"
							url='<%= themeDisplay.getPathMain() + "/journal_content/rate_entry?p_p_id=" + portletDisplay.getId() %>'
						/>
					</c:if>

					<c:if test="<%= enableComments %>">
						<portlet:actionURL var="discussionURL">
							<portlet:param name="struts_action" value="/journal_content/edit_article_discussion" />
						</portlet:actionURL>

						<liferay-ui:discussion
							formAction="<%= discussionURL %>"
							className="<%= JournalArticle.class.getName() %>"
							classPK="<%= articleDisplay.getResourcePrimKey() %>"
							userId="<%= articleDisplay.getUserId() %>"
							subject="<%= articleDisplay.getTitle() %>"
							redirect="<%= currentURL %>"
							ratingsEnabled="<%= enableCommentRatings %>"
						/>
					</c:if>
				</c:if>
			</div>
		</c:if>
	</c:otherwise>
</c:choose>