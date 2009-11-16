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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");

String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = BeanParamUtil.getLong(category, request, "mbCategoryId", MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

Set<Long> categorySubscriptionClassPKs = null;
Set<Long> threadSubscriptionClassPKs = null;

if (themeDisplay.isSignedIn()) {
	List<Subscription> categorySubscriptions = SubscriptionLocalServiceUtil.getUserSubscriptions(user.getUserId(), MBCategory.class.getName());

	categorySubscriptionClassPKs = new HashSet<Long>(categorySubscriptions.size());

	for (Subscription subscription : categorySubscriptions) {
		categorySubscriptionClassPKs.add(subscription.getClassPK());
	}

	threadSubscriptionClassPKs = new HashSet<Long>();

	List<Subscription> threadSubscriptions = SubscriptionLocalServiceUtil.getUserSubscriptions(user.getUserId(), MBThread.class.getName());

	threadSubscriptionClassPKs = new HashSet<Long>(threadSubscriptions.size());

	for (Subscription subscription : threadSubscriptions) {
		threadSubscriptionClassPKs.add(subscription.getClassPK());
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/message_boards/view");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));
%>

<liferay-util:include page="/html/portlet/message_boards/tabs1.jsp" />

<liferay-portlet:renderURL varImpl="searchURL"><portlet:param name="struts_action" value="/message_boards/search" /></liferay-portlet:renderURL>

<c:choose>
	<c:when test='<%= tabs1.equals("categories") %>'>
		<c:if test="<%= category == null %>">
			<div class="category-subscriptions">
				<div class="category-subscription-types">
					<liferay-ui:icon image="rss" url='<%= themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/message_boards/rss?p_l_id=" + plid + "&mbCategoryId=" + scopeGroupId + rssURLParams %>' label="<%= true %>" method="get" target="_blank" />

					<c:if test="<%= MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) %>">
						<c:choose>
							<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBCategory.class.getName(), scopeGroupId) %>">
								<portlet:actionURL var="unsubscribeURL">
									<portlet:param name="struts_action" value="/message_boards/edit_category" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="mbCategoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
								</portlet:actionURL>

								<liferay-ui:icon image="unsubscribe" url="<%= unsubscribeURL %>" label="<%= true %>" />
							</c:when>
							<c:otherwise>
								<portlet:actionURL var="subscribeURL">
									<portlet:param name="struts_action" value="/message_boards/edit_category" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="mbCategoryId" value="<%= String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
								</portlet:actionURL>

								<liferay-ui:icon image="subscribe" url="<%= subscribeURL %>" label="<%= true %>" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</div>
			</div>
		</c:if>

		<aui:form action="<%= searchURL %>" method="get" name="fm1" onSubmit="submitForm(this); return false;">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryIds" type="hidden" value="<%= categoryId %>" />

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("category");
			headerNames.add("categories");
			headerNames.add("threads");
			headerNames.add("posts");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			List results = categoryDisplay.getCategories();

			int total = results.size();

			searchContainer.setTotal(total);

			results = ListUtil.subList(results, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBCategory curCategory = (MBCategory)results.get(i);

				curCategory = curCategory.toEscapedModel();

				ResultRow row = new ResultRow(new Object[] {curCategory, categorySubscriptionClassPKs}, curCategory.getCategoryId(), i);

				boolean restricted = !MBCategoryPermission.contains(permissionChecker, curCategory, ActionKeys.VIEW);

				row.setRestricted(restricted);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/message_boards/view");
				rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));

				// Name and description

				StringBuilder sb = new StringBuilder();

				if (!restricted) {
					sb.append("<a href=\"");
					sb.append(rowURL);
					sb.append("\">");
				}

				sb.append("<strong>");
				sb.append(curCategory.getName());
				sb.append("</strong>");

				if (Validator.isNotNull(curCategory.getDescription())) {
					sb.append("<br />");
					sb.append(curCategory.getDescription());
				}

				if (!restricted) {
					sb.append("</a>");

					List subcategories = categoryDisplay.getCategories(curCategory);

					int subcategoriesCount = subcategories.size();

					subcategories = ListUtil.subList(subcategories, 0, 5);

					if (subcategoriesCount > 0) {
						sb.append("<br /><span class=\"subcategories\">");
						sb.append(LanguageUtil.get(pageContext, "subcategories"));
						sb.append("</span>: ");

						for (int j = 0; j < subcategories.size(); j++) {
							MBCategory subcategory = (MBCategory)subcategories.get(j);

							rowURL.setParameter("mbCategoryId", String.valueOf(subcategory.getCategoryId()));

							sb.append("<a href=\"");
							sb.append(rowURL);
							sb.append("\">");
							sb.append(subcategory.getName());
							sb.append("</a>");

							if ((j + 1) < subcategories.size()) {
								sb.append(", ");
							}
						}

						if (subcategoriesCount > subcategories.size()) {
							rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));

							sb.append(", <a href=\"");
							sb.append(rowURL);
							sb.append("\">");
							sb.append(LanguageUtil.get(pageContext, "more"));
							sb.append(" &raquo;");
							sb.append("</a>");
						}

						rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));
					}
				}

				row.addText(sb.toString());

				// Statistics

				int categoriesCount = categoryDisplay.getSubcategoriesCount(curCategory);
				int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(curCategory);
				int messagesCount = categoryDisplay.getSubcategoriesMessagesCount(curCategory);

				row.addText(String.valueOf(categoriesCount), rowURL);
				row.addText(String.valueOf(threadsCount), rowURL);
				row.addText(String.valueOf(messagesCount), rowURL);

				// Action

				if (restricted) {
					row.addText(StringPool.BLANK);
				}
				else {
					row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/category_action.jsp");
				}

				// Add result row

				resultRows.add(row);
			}

			boolean showAddCategoryButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_CATEGORY);
			boolean showPermissionsButton = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
			showSearchCategory = showSearchCategory && (results.size() > 0);
			%>

			<c:if test="<%= showAddCategoryButton || showPermissionsButton || showSearchCategory %>">
				<div>
					<c:if test="<%= showSearchCategory %>">
						<aui:input cssClass="input-text-search" id="keywords1" label="" name="keywords" size="30" type="text" />

						<aui:button type="submit" value="search-categories" />
					</c:if>

					<c:if test="<%= showAddCategoryButton %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "addCategory();" %>' value='<%= (category == null) ? "add-category" : "add-subcategory" %>' />
					</c:if>

					<c:if test="<%= showPermissionsButton %>">

						<%
						String modelResource = "com.liferay.portlet.messageboards";
						String modelResourceDescription = themeDisplay.getScopeGroupName();
						String resourcePrimKey = String.valueOf(scopeGroupId);

						if (category != null) {
							modelResource = MBCategory.class.getName();
							modelResourceDescription = category.getName();
							resourcePrimKey = String.valueOf(category.getCategoryId());
						}
						%>

						<liferay-security:permissionsURL
							modelResource="<%= modelResource %>"
							modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
							resourcePrimKey="<%= resourcePrimKey %>"
							var="permissionsURL"
						/>

						<aui:button onClick="<%= permissionsURL %>" value="permissions" />
					</c:if>
				</div>

				<br />
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</aui:form>

		<script type="text/javascript">
			function <portlet:namespace />addCategory() {
				var url = '<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/edit_category" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="parentCategoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>';

				if (document.<portlet:namespace />fm1.<portlet:namespace />keywords) {
					url += '&<portlet:namespace />name=' + document.<portlet:namespace />fm1.<portlet:namespace />keywords.value;
				}

				submitForm(document.hrefFm, url);
			}

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</c:if>
		</script>

		<br />

		<aui:form action="<%= searchURL %>" method="get" name="fm2" onSubmit="submitForm(this); return false;">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="searchCategoryId" type="hidden" value="<%= categoryId %>" />

			<liferay-ui:tabs names="threads" />

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("thread");
			headerNames.add("status");
			headerNames.add("started-by");
			headerNames.add("posts");
			headerNames.add("views");
			headerNames.add("last-post");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

			int total = MBThreadLocalServiceUtil.getThreadsCount(scopeGroupId, categoryId, StatusConstants.APPROVED);

			searchContainer.setTotal(total);

			List results = MBThreadLocalServiceUtil.getThreads(scopeGroupId, categoryId, StatusConstants.APPROVED, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBThread thread = (MBThread)results.get(i);

				MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());

				message = message.toEscapedModel();

				boolean readThread = MBMessageFlagLocalServiceUtil.hasReadFlag(themeDisplay.getUserId(), thread);

				ResultRow row = new ResultRow(new Object[] {message, threadSubscriptionClassPKs}, thread.getThreadId(), i, !readThread);

				row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/message_boards/view_message");
				rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

				// Thread

				StringBuilder sb = new StringBuilder();

				String[] threadPriority = MBUtil.getThreadPriority(preferences, themeDisplay.getLanguageId(), thread.getPriority(), themeDisplay);

				if ((threadPriority != null) && (thread.getPriority() > 0)) {
					sb.append("<img align=\"left\" alt=\"");
					sb.append(threadPriority[0]);
					sb.append("\" border=\"0\" src=\"");
					sb.append(threadPriority[1]);
					sb.append("\" title=\"");
					sb.append(threadPriority[0]);
					sb.append("\" />");
				}

				sb.append(message.getSubject());

				row.addText(sb.toString(), rowURL);

				// Status

				sb = new StringBuilder();

				if (MBMessageFlagLocalServiceUtil.hasQuestionFlag(message.getMessageId())) {
					sb.append(LanguageUtil.get(pageContext, "waiting-for-an-answer"));
				}
				if (MBMessageFlagLocalServiceUtil.hasAnswerFlag(message.getMessageId())) {
					sb.append(LanguageUtil.get(pageContext, "resolved"));
				}

				row.addText(sb.toString(), rowURL);

				// Started by

				if (message.isAnonymous()) {
					row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
				}
				else {
					row.addText(PortalUtil.getUserName(message.getUserId(), message.getUserName()), rowURL);
				}

				// Number of posts

				row.addText(String.valueOf(thread.getMessageCount()), rowURL);

				// Number of views

				row.addText(String.valueOf(thread.getViewCount()), rowURL);

				// Last post

				if (thread.getLastPostDate() == null) {
					row.addText(LanguageUtil.get(pageContext, "none"), rowURL);
				}
				else {
					sb = new StringBuilder();

					sb.append(LanguageUtil.get(pageContext, "date"));
					sb.append(": ");
					sb.append(dateFormatDateTime.format(thread.getLastPostDate()));

					String lastPostByUserName = PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK);

					if (Validator.isNotNull(lastPostByUserName)) {
						sb.append("<br />");
						sb.append(LanguageUtil.get(pageContext, "by"));
						sb.append(": ");
						sb.append(lastPostByUserName);
					}

					row.addText(sb.toString(), rowURL);
				}

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

				// Add result row

				resultRows.add(row);
			}

			boolean showAddMessageButton = MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_MESSAGE);

			if (showAddMessageButton && !themeDisplay.isSignedIn()) {
				if (!allowAnonymousPosting) {
					showAddMessageButton = false;
				}
			}

			showSearchThread = showSearchThread && (results.size() > 0);
			%>

			<c:if test="<%= showAddMessageButton || showSearchThread %>">
				<div>
					<c:if test="<%= showSearchThread %>">
						<aui:input cssClass="input-text-search" id="keywords2" label="" name="keywords" size="30" type="text" />

						<aui:button type="submit" value="search-this-category" />
					</c:if>

					<c:if test="<%= showAddMessageButton %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "addMessage();" %>' value="post-new-thread" />
					</c:if>
				</div>

				<br />
			</c:if>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		</aui:form>

		<script type="text/javascript">
			function <portlet:namespace />addMessage() {
				var url = '<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/edit_message" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="mbCategoryId" value="<%= String.valueOf(categoryId) %>" /></portlet:renderURL>';

				if (document.<portlet:namespace />fm2.<portlet:namespace />keywords) {
					url += '&<portlet:namespace />subject=' + document.<portlet:namespace />fm2.<portlet:namespace />keywords.value;
				}

				submitForm(document.hrefFm, url);
			}

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm2.<portlet:namespace />keywords);
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</c:if>
		</script>

		<%
		if (category != null) {
			PortalUtil.setPageSubtitle(category.getName(), request);
			PortalUtil.setPageDescription(category.getDescription(), request);

			MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
		}
		%>
	</c:when>
	<c:when test='<%= tabs1.equals("my_posts") || tabs1.equals("my_subscriptions") || tabs1.equals("recent_posts") %>'>

		<%
		long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

		if ((tabs1.equals("my_posts") || tabs1.equals("my_subscriptions")) && themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		if (groupThreadsUserId > 0) {
			portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
		}
		%>

		<c:if test='<%= tabs1.equals("recent_posts") && (groupThreadsUserId > 0) %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="filter-by-user" />: <%= PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK) %>
			</div>
		</c:if>

		<%
		int totalCategories = 0;
		%>

		<c:if test='<%= tabs1.equals("my_subscriptions") %>'>

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("category");
			headerNames.add("categories");
			headerNames.add("threads");
			headerNames.add("posts");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "you-are-not-subscribed-to-any-categories");

			int total = MBCategoryLocalServiceUtil.getSubscribedCategoriesCount(scopeGroupId, user.getUserId());

			searchContainer.setTotal(total);

			totalCategories = total;

			List results = MBCategoryLocalServiceUtil.getSubscribedCategories(scopeGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				MBCategory curCategory = (MBCategory)results.get(i);

				curCategory = curCategory.toEscapedModel();

				ResultRow row = new ResultRow(new Object[] {curCategory, categorySubscriptionClassPKs}, curCategory.getCategoryId(), i);

				boolean restricted = !MBCategoryPermission.contains(permissionChecker, curCategory, ActionKeys.VIEW);

				row.setRestricted(restricted);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/message_boards/view");
				rowURL.setParameter("mbCategoryId", String.valueOf(curCategory.getCategoryId()));

				// Name and description

				StringBuilder sb = new StringBuilder();

				if (!restricted) {
					sb.append("<a href=\"");
					sb.append(rowURL);
					sb.append("\">");
				}

				sb.append("<strong>");
				sb.append(curCategory.getName());
				sb.append("</strong>");

				if (Validator.isNotNull(curCategory.getDescription())) {
					sb.append("<br />");
					sb.append(curCategory.getDescription());
				}

				row.addText(sb.toString());

				// Statistics

				int categoriesCount = categoryDisplay.getSubcategoriesCount(curCategory);
				int threadsCount = categoryDisplay.getSubcategoriesThreadsCount(curCategory);
				int messagesCount = categoryDisplay.getSubcategoriesMessagesCount(curCategory);

				row.addText(String.valueOf(categoriesCount), rowURL);
				row.addText(String.valueOf(threadsCount), rowURL);
				row.addText(String.valueOf(messagesCount), rowURL);

				// Action

				if (restricted) {
					row.addText(StringPool.BLANK);
				}
				else {
					row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/category_action.jsp");
				}

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("thread");
		headerNames.add("started-by");
		headerNames.add("posts");
		headerNames.add("views");
		headerNames.add("last-post");
		headerNames.add(StringPool.BLANK);

		String emptyResultsMessage = null;

		if (tabs1.equals("my_posts")) {
			emptyResultsMessage = "you-do-not-have-any-posts";
		}
		else if (tabs1.equals("my_subscriptions")) {
			emptyResultsMessage = "you-are-not-subscribed-to-any-threads";
		}
		else if (tabs1.equals("recent_posts")) {
			emptyResultsMessage = "there-are-no-recent-posts";
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, emptyResultsMessage);

		List results = null;

		if (tabs1.equals("my_posts")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}
		else if (tabs1.equals("my_subscriptions")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED, true);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED, true, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}
		else if (tabs1.equals("recent_posts")) {
			int total = MBThreadLocalServiceUtil.getGroupThreadsCount(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED, false, false);

			searchContainer.setTotal(total);

			results = MBThreadLocalServiceUtil.getGroupThreads(scopeGroupId, groupThreadsUserId, StatusConstants.APPROVED, false, false, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);
		}

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBThread thread = (MBThread)results.get(i);

			MBMessage message = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());

			message = message.toEscapedModel();

			boolean readThread = MBMessageFlagLocalServiceUtil.hasReadFlag(themeDisplay.getUserId(), thread);

			ResultRow row = new ResultRow(new Object[] {message, threadSubscriptionClassPKs}, thread.getThreadId(), i, !readThread);

			row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/message_boards/view_message");
			rowURL.setParameter("messageId", String.valueOf(message.getMessageId()));

			// Thread

			StringBuilder sb = new StringBuilder();

			String[] threadPriority = MBUtil.getThreadPriority(preferences, themeDisplay.getLanguageId(), thread.getPriority(), themeDisplay);

			if ((threadPriority != null) && (thread.getPriority() > 0)) {
				sb.append("<img align=\"left\" alt=\"");
				sb.append(threadPriority[0]);
				sb.append("\" border=\"0\" src=\"");
				sb.append(threadPriority[1]);
				sb.append("\" title=\"");
				sb.append(threadPriority[0]);
				sb.append("\" />");
			}

			sb.append(message.getSubject());

			row.addText(sb.toString(), rowURL);

			// Started by

			if (message.isAnonymous()) {
				row.addText(LanguageUtil.get(pageContext, "anonymous"), rowURL);
			}
			else {
				row.addText(PortalUtil.getUserName(message.getUserId(), message.getUserName()), rowURL);
			}

			// Number of posts

			row.addText(String.valueOf(thread.getMessageCount()), rowURL);

			// Number of views

			row.addText(String.valueOf(thread.getViewCount()), rowURL);

			// Last post

			if (thread.getLastPostDate() == null) {
				row.addText(LanguageUtil.get(pageContext, "none"), rowURL);
			}
			else {
				sb = new StringBuilder();

				sb.append(LanguageUtil.get(pageContext, "date"));
				sb.append(": ");
				sb.append(dateFormatDateTime.format(thread.getLastPostDate()));

				String lastPostByUserName = PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK);

				if (Validator.isNotNull(lastPostByUserName)) {
					sb.append("<br />");
					sb.append(LanguageUtil.get(pageContext, "by"));
					sb.append(": ");
					sb.append(lastPostByUserName);
				}

				row.addText(sb.toString(), rowURL);
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/message_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<c:if test='<%= tabs1.equals("my_subscriptions") %>'>
			<br />
		</c:if>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<c:if test='<%= tabs1.equals("recent_posts") %>'>

			<%
			String rssURL = themeDisplay.getPortalURL() + themeDisplay.getPathMain() + "/message_boards/rss?p_l_id=" + plid + "&groupId=" + scopeGroupId;

			if (groupThreadsUserId > 0) {
				rssURL += "&userId=" + groupThreadsUserId;
			}

			rssURL += rssURLParams;
			%>

			<br />

			<table class="lfr-table">
			<tr>
				<td>
					<liferay-ui:icon
						image="rss"
						message="subscribe-to-recent-posts"
						url="<%= rssURL %>"
						method="get"
						target="_blank"
						label="<%= true %>"
					/>
				</td>
			</tr>
			</table>
		</c:if>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(tabs1, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= tabs1.equals("statistics") %>'>
		<liferay-ui:panel-container cssClass="statistics-panel" id="statistics" extended="<%= Boolean.FALSE %>" persistState="<%= true %>">
			<liferay-ui:panel cssClass="statistics-panel-content" id="general" title='<%= LanguageUtil.get(pageContext, "general") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
				<dl>
					<dt><liferay-ui:message key="num-of-categories" />:</dt><dd><%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %></dd>
					<dt><liferay-ui:message key="num-of-posts" />:</dt><dd><%= numberFormat.format(MBMessageLocalServiceUtil.getGroupMessagesCount(scopeGroupId, StatusConstants.APPROVED)) %></dd>
					<dt><liferay-ui:message key="num-of-participants" />:</dt><dd><%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %></dd>
				</dl>
			</liferay-ui:panel>

			<liferay-ui:panel cssClass="statistics-panel-content" id="topPosters" title='<%= LanguageUtil.get(pageContext, "top-posters") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
				<%
				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "there-are-no-top-posters");

				int total = MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId);

				searchContainer.setTotal(total);

				List results = MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.size(); i++) {
					MBStatsUser statsUser = (MBStatsUser)results.get(i);

					ResultRow row = new ResultRow(statsUser, statsUser.getStatsUserId(), i);

					// User display

					row.addJSP("/html/portlet/message_boards/top_posters_user_display.jsp");

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(tabs1, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= tabs1.equals("banned_users") %>'>

		<%
		List<String> headerNames = new ArrayList<String>();

		headerNames.add("banned-user");
		headerNames.add("banned-by");
		headerNames.add("ban-date");

		if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0) {
			headerNames.add("unban-date");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-banned-users");

		int total = MBBanLocalServiceUtil.getBansCount(scopeGroupId);

		searchContainer.setTotal(total);

		List results = MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			MBBan ban = (MBBan)results.get(i);

			ResultRow row = new ResultRow(ban, ban.getBanId(), i);

			// Banned user

			row.addText(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK));

			// Banned by

			row.addText(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK));

			// Ban date

			row.addText(dateFormatDateTime.format(ban.getCreateDate()));

			// Unban date

			if (PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0) {
				row.addText(dateFormatDateTime.format(MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL)));
			}

			// Action

			row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/message_boards/ban_user_action.jsp");

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(pageContext, StringUtil.replace(tabs1, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
</c:choose>