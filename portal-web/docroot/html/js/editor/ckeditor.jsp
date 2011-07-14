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

<%@ include file="/html/taglib/init.jsp" %>

<%
String portletId = portletDisplay.getRootPortletId();

String mainPath = themeDisplay.getPathMain();

String doAsUserId = themeDisplay.getDoAsUserId();

if (Validator.isNull(doAsUserId)) {
	doAsUserId = Encryptor.encrypt(company.getKeyObj(), String.valueOf(themeDisplay.getUserId()));
}

long doAsGroupId = themeDisplay.getDoAsGroupId();

String ckEditorConfigFileName = ParamUtil.getString(request, "ckEditorConfigFileName", "ckconfig.jsp");

boolean useCustomDataProcessor = false;

if (!ckEditorConfigFileName.equals("ckconfig.jsp")) {
	useCustomDataProcessor = true;
}

StringBundler configParamsSB = new StringBundler();

Map<String, String> configParams = (Map<String, String>)request.getAttribute("liferay-ui:input-editor:configParams");

if (configParams != null) {
	for (Map.Entry<String, String> configParam : configParams.entrySet()) {
		configParamsSB.append(StringPool.AMPERSAND);
		configParamsSB.append(configParam.getKey());
		configParamsSB.append(StringPool.EQUAL);
		configParamsSB.append(HttpUtil.encodeURL(configParam.getValue()));
	}
}

String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClass"));
String cssClasses = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:cssClasses"));
String name = namespace + GetterUtil.getString((String)request.getAttribute("liferay-ui:input-editor:name"));
String initMethod = (String)request.getAttribute("liferay-ui:input-editor:initMethod");

String onChangeMethod = (String)request.getAttribute("liferay-ui:input-editor:onChangeMethod");

if (Validator.isNotNull(onChangeMethod)) {
	onChangeMethod = namespace + onChangeMethod;
}

boolean skipEditorLoading = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-editor:skipEditorLoading"));
String toolbarSet = (String)request.getAttribute("liferay-ui:input-editor:toolbarSet");
%>

<c:if test="<%= !skipEditorLoading %>">
	<liferay-util:html-top outputKey="js_editor_ckeditor">
		<style type="text/css">
			table.cke_dialog {
				position: absolute !important;
			}
		</style>

		<%
		long javaScriptLastModified = ServletContextUtil.getLastModified(application, "/html/js/", true);
		%>

		<script src="<%= HtmlUtil.escape(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathJavaScript() + "/editor/ckeditor/ckeditor.js", javaScriptLastModified)) %>" type="text/javascript"></script>
	</liferay-util:html-top>
</c:if>

<aui:script>
	window['<%= name %>'] = {
		destroy: function() {
			CKEDITOR.instances['<%= name %>'].destroy();
		},

		focus: function() {
			CKEDITOR.instances['<%= name %>'].focus();
		},

		getCkData: function() {
			var data = CKEDITOR.instances['<%= name %>'].getData();

			if (CKEDITOR.env.gecko && (CKEDITOR.tools.trim(data) == '<br />')) {
				data = '';
			}

			return data;
		},

		getHTML: function() {
			return window['<%= name %>'].getCkData();
		},

		getText: function() {
			return window['<%= name %>'].getCkData();
		},

		<%
		if (Validator.isNotNull(onChangeMethod)) {
		%>

			onChangeCallback: function () {
				var ckEditor = CKEDITOR.instances['<%= name %>'];
				var dirty = ckEditor.checkDirty();

				if (dirty) {
					<%= HtmlUtil.escape(onChangeMethod) %>(window['<%= name %>'].getText());

					ckEditor.resetDirty();
				}
			},

		<%
		}
		%>

		setHTML: function(value) {
			CKEDITOR.instances['<%= name %>'].setData(value);
		}
	};
</aui:script>

<div class="<%= cssClass %>">
	<textarea id="<%= name %>" name="<%= name %>" style="display: none;"></textarea>
</div>

<aui:script>
	(function() {
		function setData() {
			<c:if test="<%= Validator.isNotNull(initMethod) %>">
				ckEditor.setData(<%= HtmlUtil.escape(namespace + initMethod) %>());
			</c:if>
		}

		<%
		String connectorURL = HttpUtil.encodeURL(mainPath + "/portal/fckeditor?p_l_id=" + plid + "&p_p_id=" + HttpUtil.encodeURL(portletId) + "&doAsUserId=" + HttpUtil.encodeURL(doAsUserId) + "&doAsGroupId=" + HttpUtil.encodeURL(String.valueOf(doAsGroupId)));
		%>

		CKEDITOR.replace(
			'<%= name %>',
			{
				customConfig: '<%= themeDisplay.getPathJavaScript() %>/editor/ckeditor/<%= ckEditorConfigFileName %>?p_l_id=<%= plid %>&p_p_id=<%= HttpUtil.encodeURL(portletId) %>&p_main_path=<%= HttpUtil.encodeURL(mainPath) %>&doAsUserId=<%= HttpUtil.encodeURL(doAsUserId) %>&doAsGroupId=<%= HttpUtil.encodeURL(String.valueOf(doAsGroupId)) %>&cssPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeCss()) %>&cssClasses=<%= HttpUtil.encodeURL(cssClasses) %>&imagesPath=<%= HttpUtil.encodeURL(themeDisplay.getPathThemeImages()) %>&languageId=<%= HttpUtil.encodeURL(LocaleUtil.toLanguageId(locale)) %><%= configParamsSB.toString() %>',
				filebrowserBrowseUrl: '<%= themeDisplay.getPathJavaScript() %>/editor/ckeditor/editor/filemanager/browser/liferay/browser.html?Connector=<%= connectorURL %>',
				filebrowserUploadUrl: null,
				toolbar: '<%= TextFormatter.format(HtmlUtil.escape(toolbarSet), TextFormatter.M) %>'
			}
		);

		var ckEditor = CKEDITOR.instances['<%= name %>'];

		var customDataProcessorLoaded = false;

		<%
		if (useCustomDataProcessor) {
		%>

			ckEditor.on(
				'customDataProcessorLoaded',
				function() {
					customDataProcessorLoaded = true;

					if (instanceReady) {
						setData();
					}
				}
			);

		<%
		}
		%>

		var instanceReady = false;

		ckEditor.on(
			'instanceReady',
			function() {

				<%
				if (useCustomDataProcessor) {
				%>

					instanceReady = true;

					if (customDataProcessorLoaded) {
						setData();
					}

				<%
				}
				else {
				%>

					setData();

				<%
				}

				if (Validator.isNotNull(onChangeMethod)) {
				%>

					setInterval(
						function() {
							try {
								window['<%= name %>'].onChangeCallback();
							}
							catch (e) {
							}
						},
						300
					);

				<%
				}
				%>

			}
		);
	})();
</aui:script>