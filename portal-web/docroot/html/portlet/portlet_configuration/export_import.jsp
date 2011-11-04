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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "export");

String redirect = ParamUtil.getString(request, "redirect");
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

String selPortletPrimaryKey = PortletPermissionUtil.getPrimaryKey(layout.getPlid(), selPortlet.getPortletId());

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/export_import");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
portletURL.setParameter("portletResource", portletResource);

boolean supportsLAR = Validator.isNotNull(selPortlet.getPortletDataHandlerClass());

boolean supportsSetup = Validator.isNotNull(selPortlet.getConfigurationActionClass());

boolean controlPanel = false;

if (layout.isTypeControlPanel()) {
	Group scopeGroup = themeDisplay.getScopeGroup();

	if (scopeGroup.isLayout()) {
		layout = LayoutLocalServiceUtil.getLayout(scopeGroup.getClassPK());
	}
	else if (!scopeGroup.isCompany()) {
		long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(scopeGroupId);

		if (defaultPlid > 0) {
			layout = LayoutLocalServiceUtil.getLayout(defaultPlid);
		}
	}

	supportsSetup = false;

	controlPanel = true;
}
%>

<c:choose>
	<c:when test="<%= supportsLAR || supportsSetup %>">

		<%
		String tabs2Names = "export,import";

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			tabs2Names += ",staging";
		}
		else if (scopeGroup.isLayout()) {
			Group parentScopeGroup = GroupServiceUtil.getGroup(scopeGroup.getParentGroupId());

			if (parentScopeGroup.isStagingGroup()) {
				tabs2Names += ",staging";
			}
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs2Names %>"
			param="tabs2"
			url="<%= portletURL.toString() %>"
			backURL="<%= redirect %>"
		/>

		<liferay-ui:error exception="<%= LARFileException.class %>" message="please-specify-a-lar-file-to-import" />
		<liferay-ui:error exception="<%= LARTypeException.class %>" message="please-import-a-lar-file-of-the-correct-type" />
		<liferay-ui:error exception="<%= LayoutImportException.class %>" message="an-unexpected-error-occurred-while-importing-your-file" />
		<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="an-error-occurred-because-the-live-group-does-not-have-the-current-page" />
		<liferay-ui:error exception="<%= PortletIdException.class %>" message="please-import-a-lar-file-for-the-current-portlet" />

		<portlet:actionURL var="exportImportPagesURL">
			<portlet:param name="struts_action" value="/portlet_configuration/export_import" />
		</portlet:actionURL>

		<aui:form action="<%= exportImportPagesURL %>" method="post" name="fm">
			<aui:input name="tabs1" type="hidden" value="export_import" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="plid" type="hidden" value="<%= layout.getPlid() %>" />
			<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
			<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<c:choose>
				<c:when test='<%= tabs2.equals("export") %>'>
				<aui:fieldset>
					<aui:input label="export-the-selected-data-to-the-given-lar-file-name" name="exportFileName" size="50" value='<%= StringUtil.replace(selPortlet.getDisplayName(), " ", "_") + "-" + Time.getShortTimestamp() + ".portlet.lar" %>' />

					<aui:field-wrapper label="what-would-you-like-to-export">
						<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>
					</aui:field-wrapper>

					<aui:button-row>
						<aui:button onClick='<%= renderResponse.getNamespace() + "exportData();" %>' value="export" />

						<aui:button href="<%= redirect %>" type="cancel" />
					</aui:button-row>
				</aui:fieldset>
				</c:when>
				<c:when test='<%= tabs2.equals("import") %>'>
					<aui:fieldset>
						<aui:input label="import-a-lar-file-to-overwrite-the-selected-data" name="importFileName" size="50" type="file" />

						<aui:field-wrapper label="what-would-you-like-to-import">
							<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>
						</aui:field-wrapper>

						<aui:button-row>
							<aui:button onClick='<%= renderResponse.getNamespace() + "importData();" %>' value="import" />

							<aui:button href="<%= redirect %>" type="cancel" />
						</aui:button-row>
					</aui:fieldset>
				</c:when>
				<c:when test='<%= tabs2.equals("staging") %>'>

					<%
					String errorMessageKey = StringPool.BLANK;

					Group stagingGroup = themeDisplay.getScopeGroup();
					Group liveGroup = stagingGroup.getLiveGroup();

					Layout targetLayout = null;

					if (!controlPanel) {
						if (liveGroup == null) {
							errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
						}
						else {
							try {
								if (stagingGroup.isLayout()) {
									targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
								}
								else {
									targetLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId());
								}
							}
							catch (NoSuchLayoutException nsle) {
								errorMessageKey = "this-portlet-is-placed-in-a-page-that-does-not-exist-in-the-live-site-publish-the-page-first";
							}

							if (targetLayout != null) {
								LayoutType layoutType = targetLayout.getLayoutType();

								if (!(layoutType instanceof LayoutTypePortlet) || !((LayoutTypePortlet)layoutType).hasPortletId(selPortlet.getPortletId())) {
									errorMessageKey = "this-portlet-has-not-been-added-to-the-live-page-publish-the-page-first";
								}
							}
						}
					}
					else if (stagingGroup.isLayout()) {
						if (liveGroup == null) {
							errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
						}
						else {
							try {
								targetLayout = LayoutLocalServiceUtil.getLayout(liveGroup.getClassPK());
							}
							catch (NoSuchLayoutException nsle) {
								errorMessageKey = "a-portlet-is-placed-in-this-page-of-scope-that-does-not-exist-in-the-live-site-publish-the-page-first";
							}
						}
					}
					%>

					<c:choose>
						<c:when test="<%= Validator.isNull(errorMessageKey) %>">
							<aui:fieldset>
								<aui:field-wrapper label="what-would-you-like-to-copy-from-live-or-publish-to-live">
									<%@ include file="/html/portlet/portlet_configuration/export_import_options.jspf" %>
								</aui:field-wrapper>

								<c:if test="<%= (themeDisplay.getURLPublishToLive() != null) || controlPanel %>">
									<aui:button-row>
										<aui:button onClick='<%= renderResponse.getNamespace() + "publishToLive();" %>' value="publish-to-live" />

										<aui:button onClick='<%= renderResponse.getNamespace() + "copyFromLive();" %>' value="copy-from-live" />
									</aui:button-row>
								</c:if>
							</aui:fieldset>
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="<%= errorMessageKey %>" />
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
		</aui:form>

		<aui:script use="aui-base,selector-css3">
			var toggleHandlerControl = function(item, index, collection) {
				var container = item.ancestor('.<portlet:namespace />handler-control').one('ul');

				if (container) {
					var action = 'hide';

					if (item.get('checked')) {
						action = 'show';
					}

					container[action]();
				}
			};

			var checkboxes = A.all('.<portlet:namespace />handler-control input[type=checkbox]');

			if (checkboxes) {
				var uncheckedBoxes = checkboxes.filter(':not(:checked)');

				if (uncheckedBoxes) {
					uncheckedBoxes.each(toggleHandlerControl);
				}

				checkboxes.detach('click');

				checkboxes.on(
					'click',
					function(event) {
						toggleHandlerControl(event.currentTarget);
					}
				);
			}
		</aui:script>
	</c:when>
	<c:otherwise>
		<%= LanguageUtil.format(locale, "the-x-portlet-does-not-have-any-data-that-can-be-exported-or-does-not-include-support-for-it", HtmlUtil.escape(PortalUtil.getPortletTitle(selPortlet, application, locale))) %>
	</c:otherwise>
</c:choose>

<aui:script>
	function <portlet:namespace />copyFromLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-copy-from-live-and-update-the-existing-staging-portlet-information") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "copy_from_live";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	function <portlet:namespace />exportData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "export";

		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/export_import" /></portlet:actionURL>&etag=0', false);
	}

	function <portlet:namespace />importData() {
		document.<portlet:namespace />fm.encoding = "multipart/form-data";

		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "import";

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />publishToLive() {
		if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-publish-to-live-and-update-the-existing-portlet-data") %>')) {
			document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.PUBLISH_TO_LIVE %>";

			submitForm(document.<portlet:namespace />fm);
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />toggleChildren',
		function(checkbox, parentDivId) {
			var A = AUI();

			var parentDiv = A.one('#' + parentDivId);

			var enabled = checkbox.checked;

			if (parentDiv) {
				parentDiv.all('input').each(
					function(item, index, collection) {
						var disabled = !enabled;

						if (enabled && item.hasClass('disabled')) {
							disabled = true;
						}

						item.set('disabled', disabled);
					}
				);
			}
		},
		['aui-base']
	);
</aui:script>