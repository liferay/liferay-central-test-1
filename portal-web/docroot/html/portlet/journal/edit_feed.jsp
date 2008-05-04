<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

JournalFeed feed = (JournalFeed)request.getAttribute(WebKeys.JOURNAL_FEED);

long groupId = BeanParamUtil.getLong(feed, request, "groupId", portletGroupId.longValue());

String feedId = BeanParamUtil.getString(feed, request, "feedId");
String newFeedId = ParamUtil.getString(request, "newFeedId");
String type = BeanParamUtil.getString(feed, request, "type");

String structureId = BeanParamUtil.getString(feed, request, "structureId");

JournalStructure structure = null;

String structureName = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();
	}
	catch (NoSuchStructureException nsse) {
	}
}

List<JournalTemplate> templates = new ArrayList<JournalTemplate>();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
}

String templateId = BeanParamUtil.getString(feed, request, "templateId");

if ((structure == null) && Validator.isNotNull(templateId)) {
	JournalTemplate template = null;

	try {
		template = JournalTemplateLocalServiceUtil.getTemplate(groupId, templateId);

		structureId = template.getStructureId();

		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);

		structureName = structure.getName();

		templates = JournalTemplateLocalServiceUtil.getStructureTemplates(groupId, structureId);
	}
	catch (NoSuchTemplateException nste) {
	}
}

String rendererTemplateId = BeanParamUtil.getString(feed, request, "rendererTemplateId");

String contentField = BeanParamUtil.getString(feed, request, "contentField");

if (Validator.isNull(contentField) || ((structure == null) && !contentField.equals(JournalFeedImpl.ARTICLE_DESCRIPTION) && !contentField.equals(JournalFeedImpl.RENDERED_ARTICLE))) {
	contentField = JournalFeedImpl.ARTICLE_DESCRIPTION;
}

String feedType = BeanParamUtil.getString(feed, request, "feedType", RSSUtil.DEFAULT_TYPE);
double feedVersion = BeanParamUtil.getDouble(feed, request, "feedVersion", RSSUtil.DEFAULT_VERSION);

int delta = BeanParamUtil.getInteger(feed, request, "delta", 10);
String orderByCol = BeanParamUtil.getString(feed, request, "orderByCol");
String orderByType = BeanParamUtil.getString(feed, request, "orderByType");
%>

<script type="text/javascript">
	function <portlet:namespace />saveFeed() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= feed == null ? Constants.ADD : Constants.UPDATE %>";

		<c:if test="<%= feed == null %>">
			document.<portlet:namespace />fm.<portlet:namespace />feedId.value = document.<portlet:namespace />fm.<portlet:namespace />newFeedId.value;
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />removeStructure() {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
		document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedImpl.ARTICLE_DESCRIPTION %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectRendererTemplate(rendererTemplateId) {
		document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = rendererTemplateId;
	}

	function <portlet:namespace />selectStructure(structureId) {
		if (document.<portlet:namespace />fm.<portlet:namespace />structureId.value != structureId) {
			document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm.<portlet:namespace />templateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />rendererTemplateId.value = "";
			document.<portlet:namespace />fm.<portlet:namespace />contentField.value = "<%= JournalFeedImpl.ARTICLE_DESCRIPTION %>";
			<portlet:namespace />saveFeed();
		}
	}

	function <portlet:namespace />selectTemplate(structureId, templateId) {
		document.<portlet:namespace />fm.<portlet:namespace />structureId.value = structureId;
		document.<portlet:namespace />fm.<portlet:namespace />templateId.value = templateId;
		<portlet:namespace />saveFeed();
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_feed" /></portlet:actionURL>" class="uni-form" enctype="multipart/form-data" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveFeed(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= feed == null ? Constants.ADD : Constants.UPDATE %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />feedId" type="hidden" value="<%= feedId %>" />
<input name="<portlet:namespace />rendererTemplateId" type="hidden" value="<%= rendererTemplateId %>" />

<liferay-ui:tabs
	names="feed"
	backURL="<%= redirect %>"
/>

<liferay-ui:error exception="<%= DuplicateFeedIdException.class %>" message="please-enter-a-unique-id" />
<liferay-ui:error exception="<%= FeedContentFieldException.class %>" message="please-select-a-valid-feed-item-content" />
<liferay-ui:error exception="<%= FeedDescriptionException.class %>" message="please-enter-a-valid-description" />
<liferay-ui:error exception="<%= FeedIdException.class %>" message="please-enter-a-valid-id" />
<liferay-ui:error exception="<%= FeedNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= FeedTargetLayoutFriendlyUrlException.class %>" message="please-enter-a-valid-target-layout-friendly-url" />
<liferay-ui:error exception="<%= FeedTargetPortletIdException.class %>" message="please-enter-a-valid-portlet-id" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="id" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
				<c:choose>
					<c:when test="<%= feed == null %>">
						<liferay-ui:message key="autogenerate-id" />

						<input name="<portlet:namespace />newFeedId" type="hidden" value="" />
						<input name="<portlet:namespace />autoFeedId" type="hidden" value="true" />
					</c:when>
					<c:otherwise>
						<%= feedId %>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<table class="lfr-table">
				<tr>
					<td>
						<c:choose>
							<c:when test="<%= feed == null %>">
								<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="feedId" fieldParam="newFeedId" defaultValue="<%= newFeedId %>" />
							</c:when>
							<c:otherwise>
								<%= feedId %>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:if test="<%= feed == null %>">
							<liferay-ui:input-checkbox param="autoFeedId" />

							<liferay-ui:message key="autogenerate-id" />
						</c:if>
					</td>
				</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="name" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="name" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="description" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="description" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="target-layout-friendly-url" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="targetLayoutFriendlyUrl" />

		<liferay-ui:icon-help message="journal-feed-target-layout-friendly-url-help" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="target-portlet-id" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= JournalFeed.class %>" bean="<%= feed %>" field="targetPortletId" />

		<liferay-ui:icon-help message="journal-feed-target-portlet-id-help" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>

<c:choose>
	<c:when test="<%= feed == null %>">
		<tr>
			<td>
				<liferay-ui:message key="permissions" />
			</td>
			<td>
				<liferay-ui:input-permissions
					modelName="<%= JournalFeed.class.getName() %>"
				/>
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>
				<liferay-ui:message key="url" />
			</td>
			<td>

				<%
				long targetLayoutPlid = PortalUtil.getPlidFromFriendlyURL(feed.getCompanyId(), feed.getTargetLayoutFriendlyUrl());

				Layout targetLayout = LayoutLocalServiceUtil.getLayout(targetLayoutPlid);
				%>

				<liferay-ui:input-resource
					url='<%= PortalUtil.getLayoutFriendlyURL(targetLayout, themeDisplay) + "/journal/rss/" + groupId + "/" + feedId %>'
				/>
			</td>
		</tr>
	</c:otherwise>
</c:choose>

</table>

<br />

<liferay-ui:tabs names="article-contraints" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="article-type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">
			<option value=""></option>

			<%
			for (String curType : JournalArticleImpl.TYPES) {
			%>

				<option <%= type.equals(curType) ? "selected" : "" %> value="<%= curType %>"><liferay-ui:message key="<%= curType %>" /></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="structure" />
	</td>
	<td>
		<input name="<portlet:namespace />structureId" type="hidden" value="<%= structureId %>" />

		<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="structureId" value="<%= structureId %>" /></portlet:renderURL>" id="<portlet:namespace />structureName">
		<%= structureName %></a>

		<input type="button" value="<liferay-ui:message key="select" />"
			onClick="
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-templates-and-available-feed-item-content") %>')) {
					var structureWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_structure" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'structure', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
					void('');
					structureWindow.focus();
				}"
		/>

		<input <%= Validator.isNull(structureId) ? "disabled" : "" %> id="<portlet:namespace />removeStructureButton" type="button" value="<liferay-ui:message key="remove" />" onClick="<portlet:namespace />removeStructure();" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="template" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= templates.size() == 0 %>">
				<input name="<portlet:namespace />templateId" type="hidden" value="<%= templateId %>" />

				<input type="button" value="<liferay-ui:message key="select" />"
					onClick="
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-templates,-and-available-feed-item-content") %>')) {
							var templateWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /></portlet:renderURL>', 'template', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=no,status=no,toolbar=no,width=680');
							void('');
							templateWindow.focus();
						}"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:table-iterator
					list="<%= templates %>"
					listType="com.liferay.portlet.journal.model.JournalTemplate"
					rowLength="3"
					rowPadding="30"
				>

					<%
					boolean templateChecked = false;

					if (templateId.equals(tableIteratorObj.getTemplateId())) {
						templateChecked = true;
					}
					%>

					<input <%= templateChecked ? "checked" : "" %> name="<portlet:namespace />templateId" type="radio" value="<%= tableIteratorObj.getTemplateId() %>" />

					<a href="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /><portlet:param name="groupId" value="<%= String.valueOf(tableIteratorObj.getGroupId()) %>" /><portlet:param name="templateId" value="<%= tableIteratorObj.getTemplateId() %>" /></portlet:renderURL>">
					<%= tableIteratorObj.getName() %></a>

					<c:if test="<%= tableIteratorObj.isSmallImage() %>">
						<br />

						<img border="0" hspace="0" src="<%= Validator.isNotNull(tableIteratorObj.getSmallImageURL()) ? tableIteratorObj.getSmallImageURL() : themeDisplay.getPathImage() + "/journal/template?img_id=" + tableIteratorObj.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(tableIteratorObj.getSmallImageId()) %>" vspace="0" />
					</c:if>
				</liferay-ui:table-iterator>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>

<br />

<liferay-ui:tabs names="presentation-settings" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="feed-item-content" />
	</td>
	<td>
		<select name="<portlet:namespace />contentField">
			<option <%= contentField.equals(JournalFeedImpl.ARTICLE_DESCRIPTION) ? "selected" : "" %> value="<%= JournalFeedImpl.ARTICLE_DESCRIPTION %>" onClick="<portlet:namespace />selectRendererTemplate('');"><liferay-ui:message key="<%= JournalFeedImpl.ARTICLE_DESCRIPTION %>" /></option>
			<optgroup label='<liferay-ui:message key="<%= JournalFeedImpl.RENDERED_ARTICLE %>" />'>
				<option <%= contentField.equals(JournalFeedImpl.RENDERED_ARTICLE) ? "selected" : "" %> value="<%= JournalFeedImpl.RENDERED_ARTICLE %>" onClick="<portlet:namespace />selectRendererTemplate('');"><liferay-ui:message key="use-default-template" /></option>

				<c:if test="<%= (structure != null) && (templates.size() > 1) %>">

					<%
					for (JournalTemplate currTemplate : templates) {
					%>

						<option <%= rendererTemplateId.equals(currTemplate.getName()) ? "selected" : "" %> value="<%= JournalFeedImpl.RENDERED_ARTICLE %>" onClick="<portlet:namespace />selectRendererTemplate('<%= currTemplate.getName() %>');"><%= LanguageUtil.format(pageContext, "use-template-x", currTemplate.getName()) %></option>

					<%
					}
					%>

				</c:if>
			</optgroup>

			<c:if test="<%= structure != null %>">
				<optgroup label="<liferay-ui:message key="structure-fields" />">

					<%
					Document doc = DocumentUtil.readDocumentFromXML(structure.getXsd());

					XPath xpathSelector = DocumentHelper.createXPath("//dynamic-element");

					List<Element> els = xpathSelector.selectNodes(doc);

					for (Element el : els) {
						String elName = el.attributeValue("name");
						String elType = StringUtil.replace(el.attributeValue("type"), StringPool.UNDERLINE, StringPool.DASH);

						if (!elType.equals("boolean") && !elType.equals("list") && !elType.equals("multi-list")) {
					%>

							<option <%= contentField.equals(elName) ? "selected" : "" %> value="<%= elName %>" onClick="<portlet:namespace />selectRendererTemplate('');"><%= TextFormatter.format(elName, TextFormatter.J) %> (<%= LanguageUtil.get(pageContext, elType) %>)</option>

					<%
						}
					}
					%>

				</optgroup>
			</c:if>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="feed-type" />
	</td>
	<td>
		<select name="<portlet:namespace />feedTypeAndVersion">

			<%
			StringMaker sm = new StringMaker();

			for (int i = 4; i < RSSUtil.RSS_VERSIONS.length; i++) {
				sm.append("<option ");

				if (feedType.equals(RSSUtil.RSS) && (feedVersion == RSSUtil.RSS_VERSIONS[i])) {
					sm.append("selected ");
				}

				sm.append("value=\"");
				sm.append(RSSUtil.RSS);
				sm.append(":");
				sm.append(RSSUtil.RSS_VERSIONS[i]);
				sm.append("\">");
				sm.append(LanguageUtil.get(pageContext, RSSUtil.RSS));
				sm.append(" ");
				sm.append(RSSUtil.RSS_VERSIONS[i]);
				sm.append("</option>");
			}

			for (int i = 1; i < RSSUtil.ATOM_VERSIONS.length; i++) {
				sm.append("<option ");

				if (feedType.equals(RSSUtil.ATOM) && (feedVersion == RSSUtil.ATOM_VERSIONS[i])) {
					sm.append("selected ");
				}

				sm.append("value=\"");
				sm.append(RSSUtil.ATOM);
				sm.append(":");
				sm.append(RSSUtil.ATOM_VERSIONS[i]);
				sm.append("\">");
				sm.append(LanguageUtil.get(pageContext, RSSUtil.ATOM));
				sm.append(" ");
				sm.append(RSSUtil.ATOM_VERSIONS[i]);
				sm.append("</option>");
			}
			%>

			<%= sm.toString() %>
		</select>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="maximum-items-to-display" />
	</td>
	<td>
		<input id="<portlet:namespace />delta" name="<portlet:namespace />delta" style="width: 50px;" value="<%= delta %>" type="text">
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-by-column" />
	</td>
	<td>
		<select name="<portlet:namespace />orderByCol">
			<option <%= orderByCol.equals("modified-date") ? "selected" : "" %> value="modified-date"><liferay-ui:message key="modified-date" /></option>
			<option <%= orderByCol.equals("display-date") ? "selected" : "" %> value="display-date"><liferay-ui:message key="display-date" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="order-by-type" />
	</td>
	<td>
		<select name="<portlet:namespace />orderByType">
			<option <%= orderByType.equals("asc") ? "selected" : "" %> value="asc"><liferay-ui:message key="ascending" /></option>
			<option <%= orderByType.equals("desc") ? "selected" : "" %> value="desc"><liferay-ui:message key="descending" /></option>
		</select>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<c:if test="<%= feed != null %>">
	<input type="button" value="<liferay-ui:message key="preview" />" onClick="window.open('<%= themeDisplay.getPortalURL() + feed.getTargetLayoutFriendlyUrl() + "/journal/rss/" + groupId + "/" + feedId %>', 'feed');" />
</c:if>

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_FEED_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= (feed == null) ? "newFeedId" : "name" %>);
			</c:otherwise>
		</c:choose>
	</script>
</c:if>