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
String portletResource = ParamUtil.getString(request, "portletResource");

String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

// Make sure the redirect is correct. This is a workaround for a layout that
// has both the Journal and Journal Content portlets and the user edits an
// article through the Journal Content portlet and then hits cancel.

/*if (redirect.indexOf("p_p_id=" + PortletKeys.JOURNAL_CONTENT) != -1) {
	if (layoutTypePortlet.hasPortletId(PortletKeys.JOURNAL)) {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setWindowState(WindowState.NORMAL);
		portletURL.setPortletMode(PortletMode.VIEW);

		redirect = portletURL.toString();
	}
}*/

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

String articleId = BeanParamUtil.getString(article, request, "articleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
String instanceIdKey = PwdGenerator.KEY1 + PwdGenerator.KEY2 + PwdGenerator.KEY3;

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticleConstants.DEFAULT_VERSION);

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

Calendar expirationDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

expirationDate.add(Calendar.YEAR, 1);

if (article != null) {
	if (article.getExpirationDate() != null) {
		neverExpire = false;

		expirationDate.setTime(article.getExpirationDate());
	}
}

boolean neverReview = ParamUtil.getBoolean(request, "neverReview", true);

Calendar reviewDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

reviewDate.add(Calendar.MONTH, 9);

if (article != null) {
	if (article.getReviewDate() != null) {
		neverReview = false;

		reviewDate.setTime(article.getReviewDate());
	}
}

String type = BeanParamUtil.getString(article, request, "type");

if (Validator.isNull(type)) {
	type = "general";
}

String structureId = BeanParamUtil.getString(article, request, "structureId");

JournalStructure structure = null;

String parentStructureId = StringPool.BLANK;
long structureGroupdId = groupId;
String structureName = LanguageUtil.get(pageContext, "default");
String structureDescription = StringPool.BLANK;
String structureXSD = StringPool.BLANK;

if (Validator.isNotNull(structureId)) {
	try {
		structure = JournalStructureLocalServiceUtil.getStructure(groupId, structureId);
	}
	catch (NoSuchStructureException nsse1) {
		if (groupId != themeDisplay.getCompanyGroupId()) {
			try {
				structure = JournalStructureLocalServiceUtil.getStructure(themeDisplay.getCompanyGroupId(), structureId);

				structureGroupdId = themeDisplay.getCompanyGroupId();
			}
			catch (NoSuchStructureException nsse2) {
			}
		}
	}

	if (structure != null) {
		structureGroupdId = structure.getGroupId();
		parentStructureId = structure.getParentStructureId();
		structureName = structure.getName();
		structureDescription = structure.getDescription();
		structureXSD = structure.getMergedXsd();
	}
}

List<JournalTemplate> templates = new ArrayList();

if (structure != null) {
	templates = JournalTemplateLocalServiceUtil.getStructureTemplates(structureGroupdId, structureId);
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

if ((structure == null) && Validator.isNotNull(templateId)) {
	JournalTemplate template = null;

	try {
		template = JournalTemplateLocalServiceUtil.getTemplate(groupId, templateId);
	}
	catch (NoSuchTemplateException nste1) {
		if (groupId != themeDisplay.getCompanyGroupId()) {
			try {
				template = JournalTemplateLocalServiceUtil.getTemplate(themeDisplay.getCompanyGroupId(), templateId);

				structureGroupdId = themeDisplay.getCompanyGroupId();
			}
			catch (NoSuchTemplateException nste2) {
			}
		}
	}

	if (template != null) {
		structureId = template.getStructureId();

		structure = JournalStructureLocalServiceUtil.getStructure(structureGroupdId, structureId);

		structureName = structure.getName();

		templates = JournalTemplateLocalServiceUtil.getStructureTemplates(structureGroupdId, structureId);
	}
}

String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

String languageId = LanguageUtil.getLanguageId(request);

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

if (Validator.isNotNull(toLanguageId)) {
	languageId = toLanguageId;
}

if (article == null && Validator.isNull(defaultLanguageId)) {
	defaultLanguageId = languageId;
}
else {
	if (Validator.isNull(defaultLanguageId)) {
		defaultLanguageId =	article.getDefaultLocale();
	}
}

Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

String content = null;

boolean preselectCurrentLayout = false;

if (article != null) {
	content = ParamUtil.getString(request, "content");

	if (Validator.isNull(content)) {
		content = article.getContent();
	}

	if (Validator.isNotNull(toLanguageId)) {
		content = JournalArticleImpl.getContentByLocale(content, Validator.isNotNull(structureId), toLanguageId);
	}
	else {
		content = JournalArticleImpl.getContentByLocale(content, Validator.isNotNull(structureId), defaultLanguageId);
	}
}
else {
	content = ParamUtil.getString(request, "content");

	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

	long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

	if (refererPlid > 0) {
		Layout refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);

		typeSettingsProperties = refererLayout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.getProperty(LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (Validator.isNotNull(defaultAssetPublisherPortletId)) {
			preselectCurrentLayout = true;
		}
	}
}

Document contentDoc = null;

String[] availableLocales = null;

if (Validator.isNotNull(content)) {
	try {
		contentDoc = SAXReaderUtil.read(content);

		Element contentEl = contentDoc.getRootElement();

		availableLocales = StringUtil.split(contentEl.attributeValue("available-locales"));

		if (!ArrayUtil.contains(availableLocales, defaultLanguageId)) {
			availableLocales = ArrayUtil.append(availableLocales, defaultLanguageId);
		}

		if (structure == null) {
			content = contentDoc.getRootElement().element("static-content").getText();
		}
	}
	catch (Exception e) {
		contentDoc = null;
	}
}

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");
String smallImageURL = BeanParamUtil.getString(article, request, "smallImageURL");
%>

<liferay-util:include page="/html/portlet/journal/article_header.jsp">
	<liferay-util:param name="tabs1" value="content" />
</liferay-util:include>

<div class="portlet-msg-info yui3-aui-helper-hidden" id="<portlet:namespace />translationsMessage">
	<liferay-ui:message key="the-changes-in-your-translations-will-be-available-once-the-content-is-published" />
</div>

<aui:form enctype="multipart/form-data" method="post" name="fm2">
	<input name="groupId" type="hidden" value="" />
	<input name="articleId" type="hidden" value="" />
	<input name="version" type="hidden" value="" />
	<input name="title" type="hidden" value="" />
	<input name="xml" type="hidden" value="" />
</aui:form>

<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
</portlet:actionURL>

<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
</portlet:renderURL>

<portlet:renderURL var="editArticleRenderPopUpURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:renderURL>

<portlet:renderURL var="updateDefaultLanguageURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="structureId" value="<%= structureId %>" />
	<portlet:param name="templateId" value="<%= templateId %>" />
</portlet:renderURL>

<aui:form action="<%= editArticleActionURL %>" enctype="multipart/form-data" method="post" name="fm1">
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="originalRedirect" type="hidden" value="<%= originalRedirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
	<aui:input name="version" type="hidden" value="<%= (article == null) ? version : article.getVersion() %>" />
	<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
	<aui:input name="content" type="hidden" />
	<aui:input name="parentStructureId" type="hidden" value="<%= parentStructureId %>" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />
	<aui:input name="deleteArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />
	<aui:input name="expireArticleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />

	<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

	<table class="lfr-table" id="<portlet:namespace />journalArticleWrapper" width="100%">
	<tr>
		<td class="lfr-top">
			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<liferay-util:include page="/html/portlet/journal/article_toolbar.jsp" />

				<liferay-ui:asset-categories-error />

				<liferay-ui:asset-tags-error />
			</c:if>

			<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
			<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
			<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-name" />
			<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
			<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />

			<table class="lfr-table journal-article-header-edit" id="<portlet:namespace />articleHeaderEdit">
			<tr>
				<td>
					<c:choose>
						<c:when test="<%= article == null %>">
							<c:choose>
								<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
									<aui:input name="newArticleId" type="hidden" />
									<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
								</c:when>
								<c:otherwise>
									<aui:input cssClass="lfr-input-text-container" field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

									<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:if test="<%= Validator.isNull(toLanguageId) %>">
								<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />
							</c:if>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<tr>
					<td class="article-structure-template-toolbar journal-metadata">
						<aui:layout>
							<aui:column cssClass="article-structure" columnWidth="50">
							<label class="article-structure-label"><liferay-ui:message key="structure" />:</label>

							<aui:fieldset cssClass="article-structure-toolbar">
								<div class="journal-form-presentation-label">
									<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
									<aui:input name="structureName" type="hidden" value="<%= structureName %>" />
									<aui:input name="structureDescription" type="hidden" value="<%= structureDescription %>" />
									<aui:input name="structureXSD" type="hidden" value="<%= JS.encodeURIComponent(structureXSD) %>" />

									<span id="<portlet:namespace />structureNameLabel" class="structure-name-label">
										<%= HtmlUtil.escape(structureName) %>
									</span>

									<liferay-ui:icon id="editStructureLink" image="edit" url="javascript:;" />

									<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="changeStructureURL">
										<portlet:param name="struts_action" value="/journal/select_structure" />
										<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getParentGroupId()) %>" />
									</portlet:renderURL>

									<span class="structure-links">
										<liferay-ui:icon id="changeStructureButton" image="configuration" message="change" url="<%= changeStructureURL %>" />
									</span>

									<c:if test="<%= Validator.isNotNull(structureId) %>">
										<span class="default-link">(<a href="javascript:;" id="<portlet:namespace />loadDefaultStructure"><liferay-ui:message key="use-default" /></a>)</span>
									</c:if>

									<span class="structure-controls">
										<span class="structure-buttons">
											<aui:button cssClass="save-structure-button yui3-aui-helper-hidden" name="saveStructureButton" value="save" />

											<aui:button cssClass="edit-structure-button yui3-aui-helper-hidden" name="editStructureButton" value="stop-editing" />
										</span>
									</span>

									<span id="<portlet:namespace />structureMessage" class="portlet-msg-alert structure-message yui3-aui-helper-hidden">
										<liferay-ui:message key="this-structure-has-not-been-saved" />
										<liferay-ui:message key="click-here-to-save-it-now" arguments='<%= new Object[] {"journal-save-structure-trigger", "#"} %>' />
									</span>
								</div>
							</aui:fieldset>
						</aui:column>

						<aui:column cssClass="article-template" columnWidth="50">
							<label class="article-template-label"><liferay-ui:message key="template" />:</label>

							<aui:fieldset cssClass="article-template-toolbar">
								<div class="journal-form-presentation-label">
									<c:choose>
										<c:when test="<%= templates.isEmpty() %>">
											<aui:input name="templateId" type="hidden" value="<%= templateId %>" />

											<div id="selectTemplateMessage"></div>

											<span class="template-name-label">
												<liferay-ui:message key="none" />
											</span>

											<liferay-ui:icon id="selectTemplateLink" image="configuration" message="choose" url="javascript:;" />
										</c:when>
										<c:when test="<%= templates.size() == 1 %>">
											<%
											JournalTemplate template = templates.get(0);
											%>

											<span class="template-name-label">
												<%= HtmlUtil.escape(template.getName()) %>
											</span>

											<c:if test="<%= template.isSmallImage() %>">
												<img class="article-template-image" id="<portlet:namespace />templateImage" src="<%= _getTemplateImage(themeDisplay, template) %>" />
											</c:if>

											<portlet:renderURL var="templateURL">
												<portlet:param name="struts_action" value="/journal/edit_template" />
												<portlet:param name="redirect" value="<%= currentURL %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(template.getGroupId()) %>" />
												<portlet:param name="templateId" value="<%= template.getTemplateId() %>" />
											</portlet:renderURL>

											<liferay-ui:icon url="<%= templateURL %>" image="edit" />
										</c:when>
										<c:otherwise>
											<aui:select inlineField="<%= true %>" label="" name="templateId">

												<%
												for (JournalTemplate template : templates) {
													String imageURL = _getTemplateImage(themeDisplay, template);
												%>
													<portlet:renderURL var="templateURL">
														<portlet:param name="struts_action" value="/journal/edit_template" />
														<portlet:param name="redirect" value="<%= currentURL %>" />
														<portlet:param name="groupId" value="<%= String.valueOf(template.getGroupId()) %>" />
														<portlet:param name="templateId" value="<%= template.getTemplateId() %>" />
													</portlet:renderURL>

													<aui:option
														data-img="<%=  imageURL != null ? imageURL : StringPool.BLANK %>"
														data-url="<%= templateURL %>"
														label="<%= HtmlUtil.escape(template.getName()) %>"
														selected="<%= templateId.equals(template.getTemplateId()) %>"
														value="<%= template.getTemplateId() %>"
													/>

												<%
												}
												%>

											</aui:select>

											<img border="0" class="yui3-aui-helper-hidden article-template-image" hspace="0" id="<portlet:namespace />templateImage" src="" vspace="0" />

											<liferay-ui:icon id="editTemplateLink" url="javascript:;" image="edit" />
										</c:otherwise>
									</c:choose>
								</div>
							</aui:fieldset>
						</aui:column>
						</aui:layout>
					</td>
				</tr>
			</c:if>

			<tr>
				<td class="article-translation-toolbar">
					<div>
						<c:choose>
							<c:when test="<%= Validator.isNull(toLanguageId) %>">
								<c:if test="<%= Validator.isNotNull(articleId) %>">
									<liferay-ui:icon-menu
										align="auto"
										cssClass="add-translations-menu"
										direction="right"
										icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>'
										message='<%= LanguageUtil.get(pageContext, "add-translation") %>'
										showArrow="<%= true %>"
										showWhenSingleIcon="<%= true %>"
									>

										<%
										Locale[] locales = LanguageUtil.getAvailableLocales();

										for (int i = 0; i < locales.length; i++) {
											if (ArrayUtil.contains(article.getAvailableLocales(), LocaleUtil.toLanguageId(locales[i]))) {
												continue;
											}

											String taglibEditArticleURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", LocaleUtil.toLanguageId(locales[i]));
											String taglibEditURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + LocaleUtil.toLanguageId(locales[i]) + "', title: '" + LanguageUtil.get(pageContext, "web-content-translation") + "', uri: '" + taglibEditArticleURL + "'});";
										%>

											<liferay-ui:icon
												image='<%= "../language/" + LocaleUtil.toLanguageId(locales[i]) %>'
												message="<%= locales[i].getDisplayName(locale) %>"
												url="<%= taglibEditURL %>"
											/>

										<%
										}
										%>

									</liferay-ui:icon-menu>
								</c:if>

								<label for="<portlet:namespace />defaultLanguageId"><liferay-ui:message key="web-content-default-language" /></label>:

								<span class="article-default-language journal-article-default-translation" id="<portlet:namespace />textLanguageId">
									<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + defaultLanguageId + ".png" %>' />

									<%= LocaleUtil.fromLanguageId(defaultLanguageId).getDisplayName(locale) %>
								</span>

								<liferay-ui:icon-help message="default-language-help" />

								<a href="javascript:;" id="<portlet:namespace />changeLanguageId"><liferay-ui:message key="change" /></a>

								<aui:select inputCssClass="yui3-aui-helper-hidden" id="defaultLocale" inlineField="<%= true %>" label="" name="defaultLanguageId">

									<%
									Locale[] locales = LanguageUtil.getAvailableLocales();

									for (int i = 0; i < locales.length; i++) {
									%>

										<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= defaultLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

									<%
									}
									%>

								</aui:select>
							</c:when>
							<c:otherwise>
								<aui:input id="defaultLocale" name="defaultLanguageId" type="hidden" value="<%= defaultLanguageId %>" />
							</c:otherwise>
						</c:choose>
					</div>

					<c:if test="<%= Validator.isNotNull(articleId) %>">

						<%
						String[] translations = article.getAvailableLocales();
						%>

						<div class='<%= (Validator.isNull(toLanguageId) && (translations.length > 1)) ? "contains-translations" :"" %>' id="<portlet:namespace />availableTranslationContainer">
							<c:choose>
								<c:when test="<%= Validator.isNotNull(toLanguageId) %>">
									<liferay-util:buffer var="languageLabel">
										<%= LocaleUtil.fromLanguageId(toLanguageId).getDisplayName(locale) %>

										<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + toLanguageId + ".png" %>' />
									</liferay-util:buffer>

									<%= LanguageUtil.format(pageContext, "translating-web-content-to-x", languageLabel) %>

									<aui:input name="toLanguageId" type="hidden" value="<%= toLanguageId %>" />
								</c:when>
								<c:otherwise>
									<span class='available-translations<%= (translations.length > 1) ? "" : " yui3-aui-helper-hidden" %>' id="<portlet:namespace />availableTranslationsLinks">
										<label><liferay-ui:message key="available-translations" /></label>

											<%
											for (int i = 0; i < translations.length; i++) {
												if (translations[i].equals(defaultLanguageId)){
													continue;
												}

												String editTranslationURL = HttpUtil.addParameter(editArticleRenderPopUpURL.toString(), renderResponse.getNamespace() + "toLanguageId", translations[i]);
											%>

											<a class="journal-article-translation journal-article-translation-<%= translations[i] %>" href="javascript:;" onClick="Liferay.Util.openWindow({id: '<portlet:namespace /><%= translations[i] %>', title: '<%= LanguageUtil.get(pageContext, "web-content-translation") %>', uri: '<%= editTranslationURL %>'});">
												<img alt="" src='<%= themeDisplay.getPathThemeImages() + "/language/" + translations[i] + ".png" %>' />

												<%= LocaleUtil.fromLanguageId(translations[i]).getDisplayName(locale) %>
											</a>

										<%
										}
										%>

									</span>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</td>
			</tr>
			</table>

			<aui:input label="name" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="title" />

			<div class="journal-article-container" id="<portlet:namespace />journalArticleContainer">
				<c:choose>
					<c:when test="<%= structure == null %>">
						<div id="<portlet:namespace />structureTreeWrapper">
							<ul class="structure-tree" id="<portlet:namespace />structureTree">
								<li class="structure-field" dataName="<liferay-ui:message key="content" />" dataType="text_area">
									<span class="journal-article-close"></span>

									<span class="folder">
										<div class="field-container">
											<div class="journal-article-move-handler"></div>

											<label class="journal-article-field-label" for="">
												<span><liferay-ui:message key="content" /></span>
											</label>

											<div class="journal-article-component-container">
												<liferay-ui:input-editor name='<%= renderResponse.getNamespace() + "structure_el_TextAreaField_content" %>' editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" toolbarSet="liferay-article" width="100%" />
											</div>

											<aui:input cssClass="journal-article-localized-checkbox" label="localizable" name="localized" type="hidden" value="<%= true %>" />

											<div class="journal-article-required-message portlet-msg-error">
												<liferay-ui:message key="this-field-is-required" />
											</div>

											<div class="journal-article-buttons">
												<aui:input cssClass="journal-article-variable-name" inlineField="<%= true %>" id="TextAreaFieldvariableName" label="variable-name" name="variableName" size="25" type="text" value="content" />

												<aui:button cssClass="edit-button" value="edit-options" />

												<aui:button cssClass="repeatable-button yui3-aui-helper-hidden" value="repeat" />
											</div>
										</div>

										<ul class="folder-droppable"></ul>
									</span>
								</li>
							</ul>
						</div>
					</c:when>
					<c:otherwise>

						<%
						Document xsdDoc = SAXReaderUtil.read(structure.getMergedXsd());

						if (contentDoc != null) {
							if ((availableLocales != null) && (availableLocales.length > 0)) {
								for (int i = 0; i < availableLocales.length ; i++) {
						%>

									<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(availableLocales[i]) %>" />

						<%
								}
							}

							if (Validator.isNotNull(toLanguageId)) {
						%>

								<input name="<portlet:namespace />available_locales" type="hidden" value="<%= languageId %>" />

						<%
							}
						}
						else {
							contentDoc = SAXReaderUtil.createDocument(SAXReaderUtil.createElement("root"));
						%>

							<input name="<portlet:namespace />available_locales" type="hidden" value="<%= HtmlUtil.escapeAttribute(defaultLanguageId) %>" />

						<%
						}
						%>

						<div class="structure-tree-wrapper" id="<portlet:namespace />structureTreeWrapper">
							<ul class="structure-tree" id="<portlet:namespace />structureTree">
								<% _format(groupId, contentDoc.getRootElement(), xsdDoc.getRootElement(), new IntegerWrapper(0), new Integer(-1), true, pageContext, request); %>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= Validator.isNull(toLanguageId) %>">
					<aui:input inlineLabel="left" label="searchable" name="indexable" />
				</c:if>

				<c:if test="<%= article == null %>">
					<aui:field-wrapper cssClass="journal-article-permissions" label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= JournalArticle.class.getName() %>"
						/>
					</aui:field-wrapper>
				</c:if>
			</div>

			<br />

			<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="journalAbstractPanel" persistState="<%= true %>" title="abstract">
				<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
				</liferay-ui:error>

				<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>" message="please-enter-a-small-image-with-a-valid-file-size" />

				<aui:fieldset>
					<aui:input name="description" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" />

					<c:if test="<%= Validator.isNull(toLanguageId) %>">
						<aui:input inlineLabel="left" label="use-small-image" name="smallImage" />

						<aui:input label="small-image-url" name="smallImageURL" />

						<span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span>

						<aui:input cssClass="lfr-input-text-container" label="small-image" name="smallFile" type="file" />

						<liferay-ui:custom-attributes-available className="<%= JournalArticle.class.getName() %>">
							<liferay-ui:custom-attribute-list
								className="<%= JournalArticle.class.getName() %>"
								classPK="<%= (article != null) ? article.getPrimaryKey() : 0 %>"
								editable="<%= true %>"
								label="<%= true %>"
							/>
						</liferay-ui:custom-attributes-available>
					</c:if>

				</aui:fieldset>
			</liferay-ui:panel>

			<br />

			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="journalCategorizationPanel" persistState="<%= true %>" title="categorization">
					<liferay-ui:error exception="<%= ArticleTypeException.class %>" message="please-select-a-type" />

					<aui:fieldset>
						<aui:select name="type" showEmptyOption="<%= true %>">

							<%
							for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
							%>

								<aui:option label="<%= JournalArticleConstants.TYPES[i] %>" selected="<%= type.equals(JournalArticleConstants.TYPES[i]) %>" />

							<%
							}
							%>

						</aui:select>

						<%
						long classPK = 0;

						if (article != null) {
							classPK = article.getResourcePrimKey();

							if (!article.isApproved() && (article.getVersion() != JournalArticleConstants.DEFAULT_VERSION)) {
								try {
									AssetEntryLocalServiceUtil.getEntry(JournalArticle.class.getName(), article.getPrimaryKey());

									classPK = article.getPrimaryKey();
								}
								catch (NoSuchEntryException nsee) {
								}
							}
						}
						%>

						<aui:input classPK="<%= classPK %>" name="categories" type="assetCategories" />

						<aui:input classPK="<%= classPK %>" name="tags" type="assetTags" />
					</aui:fieldset>
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="journalDisplayPagePanel" persistState="<%= true %>" title="display-page">

				<%
				List<Layout> privateGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, true);
				List<Layout> publicGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, false);

				if (privateGroupLayouts.isEmpty() && publicGroupLayouts.isEmpty()) {
				%>

					<liferay-ui:message key="there-are-no-pages-configured-to-be-the-display-page" />

				<%
				}
				else {
				%>

					<aui:select helpMessage="default-display-page-help" label="default-display-page" name="layoutUuid" showEmptyOption="<%= true %>">

					<%
					if (!publicGroupLayouts.isEmpty()) {
					%>

						<optgroup label="<liferay-ui:message key="public-pages" />">

					<%
						for (Layout groupLayout : publicGroupLayouts) {
					%>

							<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= layoutUuid.equals(groupLayout.getUuid()) || preselectCurrentLayout %>" value="<%= groupLayout.getUuid() %>" />

					<%
						}
					%>

						</optgroup>

					<%
					}

					if (!privateGroupLayouts.isEmpty()) {
					%>

						<optgroup label="<liferay-ui:message key="private-pages" />">

					<%
						for (Layout groupLayout : privateGroupLayouts) {
					%>

							<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= layoutUuid.equals(groupLayout.getUuid()) || preselectCurrentLayout %>" value="<%= groupLayout.getUuid() %>" />

					<%
						}
					%>

						</optgroup>
					<%
					}
					%>

					</aui:select>

				<%
				}
				%>

			</liferay-ui:panel>

			<br />

			<%
			boolean approved = false;
			boolean pending = false;

			if (article != null) {
				approved = article.isApproved();
				pending = article.isPending();
			}
			%>

			<c:if test="<%= approved %>">
				<div class="portlet-msg-info">
					<liferay-ui:message key="a-new-version-will-be-created-automatically-if-this-content-is-modified" />
				</div>
			</c:if>

			<c:if test="<%= pending %>">
				<div class="portlet-msg-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>

			<aui:button-row cssClass="journal-article-button-row">

				<%
				boolean hasSavePermission = false;

				if (article != null) {
					hasSavePermission = JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE);
				}
				else {
					hasSavePermission = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
				}

				String saveButtonLabel = "save";

				if ((article == null) || article.isDraft() || article.isApproved()) {
					saveButtonLabel = "save-as-draft";
				}

				String publishButtonLabel = "publish";

				if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName())) {
					publishButtonLabel = "submit-for-publication";
				}
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(toLanguageId) %>">
						<c:if test="<%= hasSavePermission %>">
							<aui:button name="saveButton" value="<%= saveButtonLabel %>" />

							<aui:button disabled="<%= pending %>" name="publishButton" value="<%= publishButtonLabel %>" />
						</c:if>
					</c:when>
					<c:otherwise>
						<aui:button name="translateButton" value="save" />

						<%
						String[] translations = article.getAvailableLocales();
						%>

						<aui:button name="removeArticleLocaleButton" onClick='<%= renderResponse.getNamespace() + "removeArticleLocale();" %>' value="remove-translation" disabled="<%= languageId.equals(defaultLanguageId) || !ArrayUtil.contains(translations, languageId) %>" />
					</c:otherwise>
				</c:choose>
				<aui:button onClick="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</td>

		<c:choose>
			<c:when test="<%= Validator.isNull(toLanguageId) %>">
				<td class="lfr-top">
					<%@ include file="edit_article_extra.jspf" %>
				</td>
			</c:when>
			<c:otherwise>
				<aui:input name="structureId" type="hidden" value="<%= structureId %>" />
			</c:otherwise>
		</c:choose>
	</tr>
	</table>
</aui:form>

<%@ include file="edit_article_structure_extra.jspf" %>

<aui:script>
	var <portlet:namespace />documentLibraryInput = null;
	var <portlet:namespace />imageGalleryInput = null;

	function <portlet:namespace />deleteArticle() {
		<c:choose>
			<c:when test="<%= (article != null) && article.isDraft() %>">
				var confirmationMessage = '<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-discard-this-draft") %>';
			</c:when>
			<c:otherwise>
				var confirmationMessage = '<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-article-version") %>';
			</c:otherwise>
		</c:choose>

		if (confirm(confirmationMessage)) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />expireArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(content) %>";
	}

	function <portlet:namespace />removeArticleLocale() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this-language") %>")) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE_TRANSLATION %>";
			document.<portlet:namespace />fm1.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=<%= defaultLanguageId %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />selectDocumentLibrary(url) {
		document.getElementById(<portlet:namespace />documentLibraryInput).value = url;
	}

	function <portlet:namespace />selectImageGallery(url) {
		document.getElementById(<portlet:namespace />imageGalleryInput).value = url;
	}

	function <portlet:namespace />selectStructure(structureId, structureName, dialog) {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-new-structure-will-change-the-available-input-fields-and-available-templates") %>') && 	document.<portlet:namespace />fm1.<portlet:namespace />structureId.value != structureId) {
			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = "";

			if (dialog) {
				dialog.close();
			}

			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />selectTemplate(structureId, templateId, dialog) {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "selecting-a-template-will-change-the-structure,-available-input-fields,-and-available-templates") %>')) {
			document.<portlet:namespace />fm1.<portlet:namespace />structureId.value = structureId;
			document.<portlet:namespace />fm1.<portlet:namespace />templateId.value = templateId;

			if (dialog) {
				dialog.close();
			}

			submitForm(document.<portlet:namespace />fm1);
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />disableInputDate',
		function(date, checked) {
			var A = AUI();

			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Month"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Day"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Year"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Hour"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Minute"].disabled = checked;
			document.<portlet:namespace />fm1["<portlet:namespace />" + date + "AmPm"].disabled = checked;

			var calendarWidget = A.Widget.getByNode(document.<portlet:namespace />fm1["<portlet:namespace />" + date + "Month"]);

			if (calendarWidget) {
				calendarWidget.set('disabled', checked);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />postProcessTranslation',
		function(cmd, newVersion, newLanguageId, newLanguage) {
			var A = AUI();

			var availableTranslationContainer = A.one('#<portlet:namespace />availableTranslationContainer');
			var availableTranslationsLinks = A.one('#<portlet:namespace />availableTranslationsLinks');

			var chooseLanguageText = A.one('#<portlet:namespace />chooseLanguageText');
			var translationsMessage = A.one('#<portlet:namespace />translationsMessage');

			var taglibWorkflowStatus = A.one('#<portlet:namespace />journalArticleWrapper .taglib-workflow-status');
			var statusNode = taglibWorkflowStatus.one('.workflow-status strong');
			var versionNode = taglibWorkflowStatus.one('.workflow-version strong');

			document.<portlet:namespace />fm1.<portlet:namespace />version.value = newVersion;

			versionNode.html(newVersion);

			statusNode.removeClass('workflow-status-approved');
			statusNode.addClass('workflow-status-draft');
			statusNode.html('<%= LanguageUtil.get(pageContext, "draft") %>');

			availableTranslationContainer.addClass('contains-translations');
			availableTranslationsLinks.show();
			translationsMessage.show();

			var translationLink = availableTranslationContainer.one('.journal-article-translation-' + newLanguageId);

			if (cmd == '<%= Constants.DELETE_TRANSLATION %>') {
				translationLink.hide();
			}
			else if (!translationLink) {
				var TPL_TRANSLATION = '<a class="journal-article-translation journal-article-translation-{newLanguageId}" href="javascript:;"><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/language/{newLanguageId}.png" />{newLanguage}</a>';

				translationLinkTpl = A.Lang.sub(
					TPL_TRANSLATION,
					{
						newLanguageId: newLanguageId,
						newLanguage: newLanguage
					}
				);

				translationLink = A.Node.create(translationLinkTpl);

				var editTranslationURL = '<%= editArticleRenderPopUpURL %>&<portlet:namespace />toLanguageId=' + newLanguageId;

				translationLink.on(
					'click',
					function(event) {
						Liferay.Util.openWindow(
							{
								id: '<portlet:namespace />' + newLanguageId,
								title: '<%= LanguageUtil.get(pageContext, "web-content-translation") %>',
								uri: editTranslationURL
							}
						);
					}
				);

				availableTranslationsLinks.append(translationLink);

				var languageInput = A.Node.create('<input name="<portlet:namespace />available_locales" type="hidden" value="' + newLanguageId + '" />');

				A.one('#<portlet:namespace />fm1').append(languageInput);
			}
		},
		['aui-base']
	);

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoArticleIdCheckbox','<portlet:namespace />newArticleId', true);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />title);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (article == null) ? "newArticleId" : "title" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>

<aui:script use="aui-base,liferay-portlet-journal">
	var selectTemplateLink = A.one('#<portlet:namespace />selectTemplateLink');

	if (selectTemplateLink) {
		selectTemplateLink.on(
			'click',
			function() {
				Liferay.Util.openWindow(
					{
						dialog: {
							stack: false,
							width:680
						},
						title: '<liferay-ui:message key="template" />',
						uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/journal/select_template" /><portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getParentGroupId()) %>" /></portlet:renderURL>'
					}
				);
			}
		);
	}

	var templateIdSelector = A.one('select#<portlet:namespace />templateId');

	if (templateIdSelector) {
		var options = templateIdSelector.get('options');

		var editTemplateLink = A.one('#<portlet:namespace />editTemplateLink');
		var templateImage = A.one('#<portlet:namespace />templateImage');

		var changeTemplate = function() {
			var selectedOption = options.item(templateIdSelector.get('selectedIndex'));

			var imageURL = selectedOption.attr('data-img');
			var templateURL = selectedOption.attr('data-url');

			if (imageURL) {
				templateImage.attr('src', imageURL);
				templateImage.show();
			}
			else {
				templateImage.hide();
			}

			editTemplateLink.attr('href', templateURL);
		}

		changeTemplate();

		if (editTemplateLink) {
			templateIdSelector.on(
				'change',
				changeTemplate
			);

			editTemplateLink.on(
				'click',
				function() {
					var selectedOption = options.item(templateIdSelector.get('selectedIndex'))

					window.location = selectedOption.attr('data-url');
				}
			);
		}
	}

	<%
	String doAsUserId = themeDisplay.getDoAsUserId();

	if (Validator.isNull(doAsUserId)) {
		doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
	}
	%>

	<portlet:resourceURL var="editorURL">
		<portlet:param name="editorImpl" value="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />
		<portlet:param name="name" value="LIFERAY_NAME" />
		<portlet:param name="toolbarSet" value="liferay-article" />
		<portlet:param name="struts_action" value="/journal/edit_article" />
	</portlet:resourceURL>

	Liferay.Portlet.Journal.PROXY = {};
	Liferay.Portlet.Journal.PROXY.doAsUserId = '<%= HttpUtil.encodeURL(doAsUserId) %>';
	Liferay.Portlet.Journal.PROXY.editorImpl = '<%= EDITOR_WYSIWYG_IMPL_KEY %>';
	Liferay.Portlet.Journal.PROXY.instanceIdKey = '<%= instanceIdKey %>';
	Liferay.Portlet.Journal.PROXY.pathThemeCss = '<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>';
	Liferay.Portlet.Journal.PROXY.portletNamespace = '<portlet:namespace />';
	Liferay.Portlet.Journal.PROXY.editorURL = '<%= editorURL %>';

	new Liferay.Portlet.Journal(Liferay.Portlet.Journal.PROXY.portletNamespace, '<%= HtmlUtil.escape(articleId) %>');

	var defaultLocaleSelector = A.one('#<portlet:namespace/>defaultLocale');

	if (defaultLocaleSelector) {
		defaultLocaleSelector.on(
			'change',
			function(event) {
				var defaultLanguageId = defaultLocaleSelector.get('value');

				var url = '<%= updateDefaultLanguageURL %>' + '&<portlet:namespace />defaultLanguageId=' + defaultLanguageId;

				window.location.href = url;
			}
		);
	}

	var changeLink = A.one('#<portlet:namespace />changeLanguageId');
	var languageSelector = A.one('#<portlet:namespace />defaultLocale');
	var textLanguageId = A.one('#<portlet:namespace />textLanguageId');

	if (changeLink) {
		changeLink.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "changing-the-default-language-will-delete-all-unsaved-content") %>')) {
					languageSelector.show();
					languageSelector.focus();

					changeLink.hide();
					textLanguageId.hide();
				}
			}
		);
	}
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.journal.edit_article_content.jsp";

private String _getTemplateImage(ThemeDisplay themeDisplay, JournalTemplate template) {
	String imageURL = null;

	if (template.isSmallImage()) {
		if (Validator.isNotNull(template.getSmallImageURL())) {
			imageURL = template.getSmallImageURL();
		}
		else {
			imageURL = themeDisplay.getPathImage() + "/journal/template?img_id=" + template.getSmallImageId() + "&t=" + ImageServletTokenUtil.getToken(template.getSmallImageId());
		}
	}

	return imageURL;
}

private void _format(long groupId, Element contentParentElement, Element xsdParentElement, IntegerWrapper count, Integer depth, boolean repeatablePrototype, PageContext pageContext, HttpServletRequest request) throws Exception {
	depth = new Integer(depth.intValue() + 1);

	String languageId = LanguageUtil.getLanguageId(request);

	String toLanguageId = ParamUtil.getString(request, "toLanguageId");

	List<Element> xsdElements = xsdParentElement.elements();

	for (Element xsdElement : xsdElements) {
		String nodeName = xsdElement.getName();

		if (nodeName.equals("meta-data") || nodeName.equals("entry")) {
			continue;
		}

		String elName = xsdElement.attributeValue("name", StringPool.BLANK);
		String elType = xsdElement.attributeValue("type", StringPool.BLANK);
		String elIndexType = xsdElement.attributeValue("index-type", StringPool.BLANK);
		String repeatable = xsdElement.attributeValue("repeatable");
		boolean elRepeatable = GetterUtil.getBoolean(repeatable);
		String elParentStructureId = xsdElement.attributeValue("parent-structure-id");

		Map<String, String> elMetaData = _getMetaData(xsdElement, elName);

		List<Element> elSiblings = null;

		List<Element> contentElements = contentParentElement.elements();

		for (Element contentElement : contentElements) {
			if (elName.equals(contentElement.attributeValue("name", StringPool.BLANK))) {
				elSiblings = _getSiblings(contentParentElement, elName);

				break;
			}
		}

		if (elSiblings == null) {
			elSiblings = new ArrayList<Element>();

			Element contentElement = SAXReaderUtil.createElement("dynamic-element");

			contentElement.addAttribute("instance-id", PwdGenerator.getPassword());
			contentElement.addAttribute("name", elName);
			contentElement.addAttribute("type", elType);
			contentElement.addAttribute("index-type", elIndexType);

			contentElement.add(SAXReaderUtil.createElement("dynamic-content"));

			elSiblings.add(contentElement);
		}

		for (int siblingIndex = 0; siblingIndex < elSiblings.size(); siblingIndex++) {
			Element contentElement = elSiblings.get(siblingIndex);

			String elInstanceId = contentElement.attributeValue("instance-id");

			String elContent = GetterUtil.getString(contentElement.elementText("dynamic-content"));

			if (!elType.equals("document_library") && !elType.equals("image_gallery") && !elType.equals("text") && !elType.equals("text_area") && !elType.equals("text_box")) {
				elContent = HtmlUtil.toInputSafe(elContent);
			}

			if (elType.equals("list") || elType.equals("multi-list") || elType.equals("text") || elType.equals("text_box")) {
				elContent = HtmlUtil.unescapeCDATA(elContent);
			}

			String elLanguageId = StringPool.BLANK;

			Element dynamicContentEl = contentElement.element("dynamic-content");

			if (dynamicContentEl != null) {
				elLanguageId = dynamicContentEl.attributeValue("language-id", StringPool.BLANK);

				if (Validator.isNotNull(toLanguageId)) {
					continue;
				}
				else if (Validator.isNull(elLanguageId)) {
					elLanguageId = languageId;
				}
			}
			else {
				elLanguageId = languageId;
			}

			if (!_hasRepeatedParent(contentElement)) {
				repeatablePrototype = (siblingIndex == 0);
			}

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_GROUP_ID, String.valueOf(groupId));

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT_EL, contentElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL, xsdElement);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_CONTENT, elContent);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_COUNT, count);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_DEPTH, depth);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INSTANCE_ID, elInstanceId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_LANGUAGE_ID, elLanguageId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_META_DATA, elMetaData);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_NAME, elName);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_PARENT_ID, elParentStructureId);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE, String.valueOf(elRepeatable));
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_REPEATABLE_PROTOTYPE, String.valueOf(repeatablePrototype));
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_TYPE, elType);
			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_EL_INDEX_TYPE, elIndexType);

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el.jsp");

			count.increment();

			if (!elType.equals("list") && !elType.equals("multi-list") && !contentElement.elements().isEmpty()) {
				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_top.jsp");

				_format(groupId, contentElement, xsdElement, count, depth, repeatablePrototype, pageContext, request);

				request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.TRUE.toString());

				pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
			}

			request.setAttribute(WebKeys.JOURNAL_STRUCTURE_CLOSE_DROPPABLE_TAG, Boolean.FALSE.toString());

			pageContext.include("/html/portlet/journal/edit_article_content_xsd_el_bottom.jsp");
		}
	}
}

private Map<String, String> _getMetaData(Element xsdElement, String elName) {
	Map<String, String> elMetaData = new HashMap<String, String>();

	Element metaData = xsdElement.element("meta-data");

	if (Validator.isNotNull(metaData)) {
		List<Element> elMetaDataements = metaData.elements();

		for (Element elMetaDataement : elMetaDataements) {
			String name = elMetaDataement.attributeValue("name");
			String content = elMetaDataement.getText().trim();

			elMetaData.put(name, content);
		}
	}
	else {
		elMetaData.put("label", elName);
	}

	return elMetaData;
}

private List<Element> _getSiblings(Element element, String name) {
	List<Element> elements = new ArrayList<Element>();

	Iterator<Element> itr = element.elements().iterator();

	while (itr.hasNext()) {
		Element curElement = itr.next();

		if (name.equals(curElement.attributeValue("name", StringPool.BLANK))) {
			elements.add(curElement);
		}
	}

	return elements;
}

private boolean _hasRepeatedParent(Element element) {
	Element parentElement = element.getParent();

	while (parentElement != null) {
		Element parentParentElement = parentElement.getParent();

		if (parentParentElement != null) {
			List<Element> parentSiblings = _getSiblings(parentParentElement, parentElement.attributeValue("name", StringPool.BLANK));

			if (parentSiblings.indexOf(parentElement) > 0) {
				return true;
			}
		}

		parentElement = parentParentElement;
	}

	return false;
}
%>
