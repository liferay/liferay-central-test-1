<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
List<AssetRendererFactory> classTypesAssetRendererFactories = (List<AssetRendererFactory>)request.getAttribute("configuration.jsp-classTypesAssetRendererFactories");
PortletURL configurationRenderURL = (PortletURL)request.getAttribute("configuration.jsp-configurationRenderURL");
String redirect = (String)request.getAttribute("configuration.jsp-redirect");
String rootPortletId = (String)request.getAttribute("configuration.jsp-rootPortletId");
String selectScope = (String)request.getAttribute("configuration.jsp-selectScope");
String selectStyle = (String)request.getAttribute("configuration.jsp-selectStyle");
%>

<liferay-ui:tabs
	formName="fm"
	names="asset-selection,display-settings,subscriptions"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="asset-selection" />

		<%= selectStyle %>

		<liferay-ui:panel-container extended="<%= true %>" id="assetPublisherDynamicSelectionAssetSelectionPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherSourcePanel" persistState="<%= true %>" title="source">
				<aui:fieldset cssClass='<%= rootPortletId.equals(PortletKeys.RELATED_ASSETS) ? "aui-helper-hidden" : "" %>' label="scope">
					<%= selectScope %>
				</aui:fieldset>

				<aui:fieldset label="asset-entry-type">

					<%
					Set<Long> availableClassNameIdsSet = SetUtil.fromArray(availableClassNameIds);

					// Left list

					List<KeyValuePair> typesLeftList = new ArrayList<KeyValuePair>();

					for (long classNameId : classNameIds) {
						String className = PortalUtil.getClassName(classNameId);

						typesLeftList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className)));
					}

					// Right list

					List<KeyValuePair> typesRightList = new ArrayList<KeyValuePair>();

					Arrays.sort(classNameIds);
					%>

					<aui:select label="" name="preferences--anyAssetType--">
						<aui:option label="any" selected="<%= anyAssetType %>" value="<%= true %>" />
						<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetType && (classNameIds.length > 1) %>" value="<%= false %>" />

						<optgroup label="<liferay-ui:message key="asset-type" />">

							<%
							for (long classNameId : availableClassNameIdsSet) {
								ClassName className = ClassNameLocalServiceUtil.getClassName(classNameId);

								if (Arrays.binarySearch(classNameIds, classNameId) < 0) {
									typesRightList.add(new KeyValuePair(String.valueOf(classNameId), ResourceActionsUtil.getModelResource(locale, className.getValue())));
								}
							%>

							<aui:option label="<%= ResourceActionsUtil.getModelResource(locale, className.getValue()) %>" selected="<%= (classNameIds.length == 1) && (classNameId == classNameIds[0]) %>" value="<%= classNameId %>" />

							<%
							}
							%>

						</optgroup>
					</aui:select>

					<aui:input name="preferences--classNameIds--" type="hidden" />

					<%
					typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
					%>

					<div class="<%= anyAssetType ? "aui-helper-hidden" : "" %>" id="<portlet:namespace />classNamesBoxes">
						<liferay-ui:input-move-boxes
							leftBoxName="currentClassNameIds"
							leftList="<%= typesLeftList %>"
							leftReorder="true"
							leftTitle="selected"
							rightBoxName="availableClassNameIds"
							rightList="<%= typesRightList %>"
							rightTitle="available"
						/>
					</div>

					<%
					for (AssetRendererFactory assetRendererFactory : AssetRendererFactoryRegistryUtil.getAssetRendererFactories()) {
						if (assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale()) == null) {
							continue;
						}

						classTypesAssetRendererFactories.add(assetRendererFactory);

						Map<Long, String> assetAvailableClassTypes = assetRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale());

						String className = AssetPublisherUtil.getClassName(assetRendererFactory);

						Set<Long> assetAvailableClassTypeIdsSet = assetAvailableClassTypes.keySet();

						Long[] assetAvailableClassTypeIds = assetAvailableClassTypeIdsSet.toArray(new Long[assetAvailableClassTypeIdsSet.size()]);

						Long[] assetSelectedClassTypeIds = AssetPublisherUtil.getClassTypeIds(preferences, className, assetAvailableClassTypeIds);

						// Left list

						List<KeyValuePair> subTypesLeftList = new ArrayList<KeyValuePair>();

						for (long subTypeId : assetSelectedClassTypeIds) {
							subTypesLeftList.add(new KeyValuePair(String.valueOf(subTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(subTypeId))));
						}

						Arrays.sort(assetSelectedClassTypeIds);

						// Right list

						List<KeyValuePair> subTypesRightList = new ArrayList<KeyValuePair>();

						boolean anyAssetSubType = GetterUtil.getBoolean(preferences.getValue("anyClassType" + className, Boolean.TRUE.toString()));
					%>

						<div class='asset-subtype <%= (assetSelectedClassTypeIds.length < 1) ? "" : "aui-helper-hidden" %>' id="<portlet:namespace /><%= className %>Options">
							<aui:select label='<%= LanguageUtil.format(pageContext, "x-subtype", ResourceActionsUtil.getModelResource(locale, assetRendererFactory.getClassName())) %>' name='<%= "preferences--anyClassType" + className + "--" %>'>
								<aui:option label="any" selected="<%= anyAssetSubType %>" value="<%= true %>" />
								<aui:option label='<%= LanguageUtil.get(pageContext, "select-more-than-one") + StringPool.TRIPLE_PERIOD %>' selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length > 1) %>" value="<%= false %>" />

								<optgroup label="<liferay-ui:message key="subtype" />">

									<%
									for (Long classTypeId : assetAvailableClassTypes.keySet()) {
										if (Arrays.binarySearch(assetSelectedClassTypeIds, classTypeId) < 0) {
											subTypesRightList.add(new KeyValuePair(String.valueOf(classTypeId), HtmlUtil.escape(assetAvailableClassTypes.get(classTypeId))));
										}
									%>

										<aui:option label="<%= HtmlUtil.escapeAttribute(assetAvailableClassTypes.get(classTypeId)) %>" selected="<%= !anyAssetSubType && (assetSelectedClassTypeIds.length == 1) && (classTypeId.equals(assetSelectedClassTypeIds[0])) %>" value="<%= classTypeId %>" />

									<%
									}
									%>

								</optgroup>
							</aui:select>

							<aui:input name='<%= "preferences--classTypeIds" + className + "--" %>' type="hidden" />

							<%
							typesRightList = ListUtil.sort(typesRightList, new KeyValuePairComparator(false, true));
							%>

							<div class="<%= assetSelectedClassTypeIds.length > 1 ? "" : "aui-helper-hidden" %>" id="<portlet:namespace /><%= className %>Boxes">
								<liferay-ui:input-move-boxes
									leftBoxName='<%= className + "currentClassTypeIds" %>'
									leftList="<%= subTypesLeftList %>"
									leftReorder="true"
									leftTitle="selected"
									rightBoxName='<%= className + "availableClassTypeIds" %>'
									rightList="<%= subTypesRightList %>"
									rightTitle="available"
								/>
							</div>

							<%
							for (long assetAvailableClassTypeId : assetAvailableClassTypeIds) {
								if (!assetRendererFactory.hasClassTypeFieldNames(assetAvailableClassTypeId, locale)) {
									continue;
								}

								Map<String, Map<String, String>> classTypeFieldNames = assetRendererFactory.getClassTypeFieldNames(assetAvailableClassTypeId, locale);

								boolean anySubTypeField = GetterUtil.getBoolean(preferences.getValue("ddmStructureFieldName" + assetAvailableClassTypeId + "_" + className, Boolean.TRUE.toString()));

								String ddmStructureFieldName = GetterUtil.getString(preferences.getValue("ddmStructureFieldName" + assetAvailableClassTypeId + "_" + className, StringPool.BLANK));
							%>

								<div class="asset-subtypefields aui-helper-hidden" id="<portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options">
									<aui:select label='<%= LanguageUtil.format(pageContext, "x-fields", HtmlUtil.escapeAttribute(assetAvailableClassTypes.get(assetAvailableClassTypeId))) %>' name='<%= "preferences--ddmStructureFieldName" + assetAvailableClassTypeId + "_" + className + "--" %>'>
										<aui:option label="any" selected="<%= anySubTypeField %>" value="<%= true %>" />

										<optgroup label="<liferay-ui:message key="fields" />">

											<%
											for (Map.Entry<String, Map<String, String>> classTypeFieldName : classTypeFieldNames.entrySet()) {
												Map<String, String> attributes = classTypeFieldName.getValue();

												String label = attributes.get("label");
											%>

												<aui:option label="<%= HtmlUtil.escapeAttribute(label) %>" selected="<%= !anySubTypeField && classTypeFieldName.getKey().equals(ddmStructureFieldName) %>" value="<%= classTypeFieldName.getKey() %>" />

											<%
											}
											%>

										</optgroup>
									</aui:select>

									<%
									for (Map.Entry<String, Map<String, String>> classTypeFieldName : classTypeFieldNames.entrySet()) {
										Map<String, String> attributes = classTypeFieldName.getValue();

										String label = attributes.get("label");
										String name = attributes.get("name");

										String ddmStructureFieldValue = GetterUtil.getString(preferences.getValue("ddmStructureFieldValue" + assetAvailableClassTypeId + "_" + className + "_" + classTypeFieldName.getKey(), StringPool.BLANK));
									%>

										<span class="aui-helper-hidden" id="<portlet:namespace />ddmStructureFieldValue<%= assetAvailableClassTypeId %>_<%= className %>_<%= HtmlUtil.escapeJS(name) %>">
											<aui:input label='<%= LanguageUtil.format(pageContext, "value-for-x", HtmlUtil.escapeAttribute(label)) %>' name='<%= "preferences--ddmStructureFieldValue" + assetAvailableClassTypeId + "_" + className + "_" + classTypeFieldName.getKey() + "--" %>' value="<%= ddmStructureFieldValue %>" />
										</span>

									<%
									}
									%>

								</div>

							<%
							}
							%>

						</div>

					<%
					}
					%>

				</aui:fieldset>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherQueryRulesPanelContainer" persistState="<%= true %>" title="filter[action]">
				<liferay-ui:asset-tags-error />

				<div id="<portlet:namespace />queryRules">
					<aui:fieldset label="displayed-assets-must-match-these-rules">

						<%
						String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes");

						int[] queryLogicIndexes = null;

						if (Validator.isNotNull(queryLogicIndexesParam)) {
							queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
						}
						else {
							queryLogicIndexes = new int[0];

							for (int i = 0; true; i++) {
								String queryValues = PrefsParamUtil.getString(preferences, request, "queryValues" + i);

								if (Validator.isNull(queryValues)) {
									break;
								}

								queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, i);
							}

							if (queryLogicIndexes.length == 0) {
								queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, -1);
							}
						}

						int index = 0;

						for (int queryLogicIndex : queryLogicIndexes) {
							String queryValues = StringUtil.merge(preferences.getValues("queryValues" + queryLogicIndex , new String[0]));
							String tagNames = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
							String categoryIds = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

							if (Validator.isNotNull(tagNames) || Validator.isNotNull(categoryIds) || (queryLogicIndexes.length == 1)) {
								request.setAttribute("configuration.jsp-categorizableGroupIds", _getCategorizableGroupIds(groupIds));
								request.setAttribute("configuration.jsp-index", String.valueOf(index));
								request.setAttribute("configuration.jsp-queryLogicIndex", String.valueOf(queryLogicIndex));
						%>

								<div class="lfr-form-row">
									<div class="row-fields">
										<liferay-util:include page="/html/portlet/asset_publisher/edit_query_rule.jsp" />
									</div>
								</div>

						<%
							}

							index++;
						}
						%>

					</aui:fieldset>
				</div>

				<c:if test="<%= !PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX %>">
					<aui:input label='<%= LanguageUtil.format(pageContext, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= showOnlyLayoutAssets %>" />
				</c:if>

				<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= mergeUrlTags %>" />

				<aui:input helpMessage="include-tags-set-by-other-applications-help" label="include-tags-set-by-other-applications" name="preferences--mergeLayoutTags--" type="checkbox" value="<%= mergeLayoutTags %>" />

				<aui:script use="liferay-auto-fields">
					var autoFields = new Liferay.AutoFields(
						{
							contentBox: '#<portlet:namespace />queryRules > fieldset',
							fieldIndexes: '<portlet:namespace />queryLogicIndexes',
							url: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/portlet_configuration/edit_query_rule" /></portlet:renderURL>'
						}
					).render();
				</aui:script>
			</liferay-ui:panel>

			<c:if test="<%= !PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX %>">
				<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherCustomUserAttributesQueryRulesPanelContainer" persistState="<%= true %>" title="custom-user-attributes">
					<aui:input helpMessage="custom-user-attributes-help" label="displayed-assets-must-match-these-custom-user-profile-attributes" name="preferences--customUserAttributes--" value="<%= customUserAttributes %>" />
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="assetPublisherOrderingAndGroupingPanel" persistState="<%= true %>" title="ordering-and-grouping">
				<aui:fieldset>
					<span class="aui-field-row">
						<aui:select inlineField="<%= true %>" inlineLabel="left" label="order-by" name="preferences--orderByColumn1--">
							<aui:option label="title" selected='<%= orderByColumn1.equals("title") %>' />
							<aui:option label="create-date" selected='<%= orderByColumn1.equals("createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= orderByColumn1.equals("modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= orderByColumn1.equals("publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= orderByColumn1.equals("expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= orderByColumn1.equals("priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= orderByColumn1.equals("viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= orderByColumn1.equals("ratings") %>' value="ratings" />
						</aui:select>

						<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType1--">
							<aui:option label="ascending" selected='<%= orderByType1.equals("ASC") %>' value="ASC" />
							<aui:option label="descending" selected='<%= orderByType1.equals("DESC") %>' value="DESC" />
						</aui:select>
					</span>

					<span class="aui-field-row">
						<aui:select inlineField="<%= true %>" inlineLabel="left" label="and-then-by" name="preferences--orderByColumn2--">
							<aui:option label="title" selected='<%= orderByColumn2.equals("title") %>' />
							<aui:option label="create-date" selected='<%= orderByColumn2.equals("createDate") %>' value="createDate" />
							<aui:option label="modified-date" selected='<%= orderByColumn2.equals("modifiedDate") %>' value="modifiedDate" />
							<aui:option label="publish-date" selected='<%= orderByColumn2.equals("publishDate") %>' value="publishDate" />
							<aui:option label="expiration-date" selected='<%= orderByColumn2.equals("expirationDate") %>' value="expirationDate" />
							<aui:option label="priority" selected='<%= orderByColumn2.equals("priority") %>' value="priority" />
							<aui:option label="view-count" selected='<%= orderByColumn2.equals("viewCount") %>' value="viewCount" />
							<aui:option label="ratings" selected='<%= orderByColumn2.equals("ratings") %>' value="ratings" />
						</aui:select>

						<aui:select inlineField="<%= true %>" label="" name="preferences--orderByType2--">
							<aui:option label="ascending" selected='<%= orderByType2.equals("ASC") %>' value="ASC" />
							<aui:option label="descending" selected='<%= orderByType2.equals("DESC") %>' value="DESC" />
						</aui:select>
					</span>

					<span class="aui-field-row">
						<aui:select inlineField="<%= true %>" inlineLabel="left" label="group-by" name="preferences--assetVocabularyId--">
							<aui:option value="" />
							<aui:option label="asset-types" selected="<%= assetVocabularyId == -1 %>" value="-1" />

							<%
							Group companyGroup = company.getGroup();

							if (scopeGroupId != companyGroup.getGroupId()) {
								List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(scopeGroupId, false);

								if (!assetVocabularies.isEmpty()) {
								%>

									<optgroup label="<liferay-ui:message key="vocabularies" />">

										<%
										for (AssetVocabulary assetVocabulary : assetVocabularies) {
											assetVocabulary = assetVocabulary.toEscapedModel();
										%>

											<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

										<%
										}
										%>

									</optgroup>

								<%
								}
							}
							%>

							<%
							List<AssetVocabulary> assetVocabularies = AssetVocabularyLocalServiceUtil.getGroupVocabularies(companyGroup.getGroupId(), false);

							if (!assetVocabularies.isEmpty()) {
							%>

								<optgroup label="<liferay-ui:message key="vocabularies" /> (<liferay-ui:message key="global" />)">

									<%
									for (AssetVocabulary assetVocabulary : assetVocabularies) {
										assetVocabulary = assetVocabulary.toEscapedModel();
									%>

										<aui:option label="<%= assetVocabulary.getTitle(locale) %>" selected="<%= assetVocabularyId == assetVocabulary.getVocabularyId() %>" value="<%= assetVocabulary.getVocabularyId() %>" />

									<%
									}
									%>

								</optgroup>

							<%
							}
							%>

						</aui:select>
					</span>
				</aui:fieldset>
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="display-settings" />

		<%@ include file="/html/portlet/asset_publisher/display_settings.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-ui:error-marker key="errorSection" value="subscriptions" />

		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() %>">
			<liferay-ui:rss-settings
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				displayStyles="<%= new String[] {RSSUtil.DISPLAY_STYLE_ABSTRACT, RSSUtil.DISPLAY_STYLE_TITLE} %>"
				enabled="<%= enableRSS %>"
				feedType="<%= rssFeedType %>"
				name="<%= rssName %>"
				nameEnabled="<%= true %>"
			/>
		</c:if>

		<%@ include file="/html/portlet/asset_publisher/email_subscription_settings.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "saveSelectBoxes();" %>' type="submit" />
</aui:button-row>

<aui:script use="aui-base">
	var assetSelector = A.one('#<portlet:namespace />anyAssetType');
	var assetMulitpleSelector = A.one('#<portlet:namespace />currentClassNameIds');

	<%
	for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
		String className = AssetPublisherUtil.getClassName(curRendererFactory);
	%>

		Liferay.Util.toggleSelectBox('<portlet:namespace />anyClassType<%= className %>','false','<portlet:namespace /><%= className %>Boxes');

		var <portlet:namespace /><%= className %>Options = A.one('#<portlet:namespace /><%= className %>Options');

		function <portlet:namespace />toggle<%= className %>() {
			var assetOptions = assetMulitpleSelector.all('option');

			if ((assetSelector.val() == '<%= curRendererFactory.getClassNameId() %>') ||
				((assetSelector.val() == 'false') && (assetOptions.size() == 1) && (assetOptions.item(0).val() == '<%= curRendererFactory.getClassNameId() %>'))) {

				<portlet:namespace /><%= className %>Options.show();
			}
			else {
				<portlet:namespace /><%= className %>Options.hide();
			}
		}

		<%
		if (curRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale()) == null) {
			continue;
		}
	  	%>

		var <portlet:namespace /><%= className %>SubTypeSelector = A.one('#<portlet:namespace />anyClassType<%= className %>');

		<%
		Map<Long, String> assetAvailableClassTypes = curRendererFactory.getClassTypes(new long[] {themeDisplay.getCompanyGroupId(), scopeGroupId}, themeDisplay.getLocale());

		Set<Long> assetAvailableClassTypeIdsSet = assetAvailableClassTypes.keySet();

		Long[] assetAvailableClassTypeIds = assetAvailableClassTypeIdsSet.toArray(new Long[assetAvailableClassTypeIdsSet.size()]);

		for (long assetAvailableClassTypeId : assetAvailableClassTypeIds) {
			if (!curRendererFactory.hasClassTypeFieldNames(assetAvailableClassTypeId, locale)) {
				continue;
			}
		%>

			var <portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options = A.one('#<portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options');

			function <portlet:namespace />toggle<%= assetAvailableClassTypeId %>_<%= className %>() {
				if (<portlet:namespace /><%= className %>SubTypeSelector.val() == '<%= assetAvailableClassTypeId %>') {
					<portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options.show();
				}
				else {
					<portlet:namespace /><%= assetAvailableClassTypeId %>_<%= className %>Options.hide();
				}
			}

			<%
			Map<String, Map<String, String>> classTypeFieldNames = curRendererFactory.getClassTypeFieldNames(assetAvailableClassTypeId, locale);

			for (Map.Entry<String, Map<String, String>> classTypeFieldName : classTypeFieldNames.entrySet()) {
				Map<String, String> attributes = classTypeFieldName.getValue();

				String name = attributes.get("name");
			%>

				Liferay.Util.toggleSelectBox('<portlet:namespace />ddmStructureFieldName<%= assetAvailableClassTypeId %>_<%= className %>', '<%= classTypeFieldName.getKey() %>', '<portlet:namespace />ddmStructureFieldValue<%= assetAvailableClassTypeId %>_<%= className %>_<%= HtmlUtil.escapeJS(name) %>');

		<%
			}
		}
		%>

		function <portlet:namespace /><%= className %>toggleSubclassesFields() {

			<%
			for (long assetAvailableClassTypeId : assetAvailableClassTypeIds) {
				if (!curRendererFactory.hasClassTypeFieldNames(assetAvailableClassTypeId, locale)) {
					continue;
				}
			%>

				<portlet:namespace />toggle<%= assetAvailableClassTypeId %>_<%= className %>();

			<%
			}
			%>

		}

		<portlet:namespace /><%= className %>toggleSubclassesFields();

		<portlet:namespace /><%= className %>SubTypeSelector.on(
			'change',
			function(event) {
				<portlet:namespace /><%= className %>toggleSubclassesFields();
			}
		);

	<%
	}
	%>

	function <portlet:namespace />toggleSubclasses() {

		<%
		for (AssetRendererFactory curRendererFactory : classTypesAssetRendererFactories) {
			String className = AssetPublisherUtil.getClassName(curRendererFactory);
		%>

			<portlet:namespace />toggle<%= className %>();

		<%
		}
		%>

	}

	<portlet:namespace />toggleSubclasses();

	assetSelector.on(
		'change',
		function(event) {
			<portlet:namespace />toggleSubclasses();
		}
	);

	Liferay.after(
		'inputmoveboxes:moveItem',
		function(event) {
			if ((event.fromBox.get('id') == '<portlet:namespace />currentClassNameIds') || (event.toBox.get('id') == '<portlet:namespace />currentClassNameIds')) {
				<portlet:namespace />toggleSubclasses();
			}
		}
	);
</aui:script>

<%!
private long[] _getCategorizableGroupIds(long[] groupIds) throws Exception {
	Set<Long> categorizableGroupIds = new HashSet<Long>(groupIds.length);

	for (long groupId : groupIds) {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayout()) {
			groupId = group.getParentGroupId();
		}

		categorizableGroupIds.add(groupId);
	}

	return ArrayUtil.toLongArray(categorizableGroupIds);
}
%>