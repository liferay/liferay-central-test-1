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

<%@ page import="java.text.Format" %>

<%
Object bean = (Object)request.getAttribute("aui:input:bean");
boolean changesContext = GetterUtil.getBoolean((String)request.getAttribute("aui:input:changesContext"));
boolean checked = GetterUtil.getBoolean((String)request.getAttribute("aui:input:checked"));
long classPK = GetterUtil.getLong((String)request.getAttribute("aui:input:classPK"));
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:input:cssClass"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("aui:input:disabled"));
Map<String, Object> data = (Map<String, Object>)request.getAttribute("aui:input:data");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");
String field = GetterUtil.getString((String)request.getAttribute("aui:input:field"));
String fieldParam = GetterUtil.getString((String)request.getAttribute("aui:input:fieldParam"));
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:input:first"));
String formName = GetterUtil.getString((String)request.getAttribute("aui:input:formName"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:input:helpMessage"));
String id = namespace + GetterUtil.getString((String)request.getAttribute("aui:input:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean((String)request.getAttribute("aui:input:ignoreRequestValue"));
boolean inlineField = GetterUtil.getBoolean((String)request.getAttribute("aui:input:inlineField"));
String inlineLabel = GetterUtil.getString((String)request.getAttribute("aui:input:inlineLabel"));
String inputCssClass = GetterUtil.getString((String)request.getAttribute("aui:input:inputCssClass"));
String label = GetterUtil.getString((String)request.getAttribute("aui:input:label"));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:input:last"));
Class<?> model = (Class<?>)request.getAttribute("aui:input:model");
String name = GetterUtil.getString((String)request.getAttribute("aui:input:name"));
String onChange = GetterUtil.getString((String)request.getAttribute("aui:input:onChange"));
String onClick = GetterUtil.getString((String)request.getAttribute("aui:input:onClick"));
String prefix = GetterUtil.getString((String)request.getAttribute("aui:input:prefix"));
boolean required = GetterUtil.getBoolean((String)request.getAttribute("aui:input:required"));
String suffix = GetterUtil.getString((String)request.getAttribute("aui:input:suffix"));
String title = GetterUtil.getString((String)request.getAttribute("aui:input:title"));
String type = GetterUtil.getString((String)request.getAttribute("aui:input:type"));
Object value = request.getAttribute("aui:input:value");

if (Validator.isNull(label) && changesContext) {
	StringBundler sb = new StringBundler(5);

	sb.append(LanguageUtil.get(pageContext, title));
	sb.append(StringPool.SPACE);
	sb.append(StringPool.OPEN_PARENTHESIS);
	sb.append(LanguageUtil.get(pageContext, "changing-the-value-of-this-field-will-reload-the-page"));
	sb.append(StringPool.CLOSE_PARENTHESIS);

	title = sb.toString();
}
else if (Validator.isNotNull(title)) {
	title = LanguageUtil.get(pageContext, title);
}

String baseType = type;
String forLabel = id;

if ((model != null) && Validator.isNull(type)) {
	baseType = ModelHintsUtil.getType(model.getName(), field);

	if (Validator.isNotNull(fieldParam)) {
		forLabel = namespace + fieldParam;
	}

	if (ModelHintsUtil.isLocalized(model.getName(), field)) {
		Locale defaultLocale = LocaleUtil.getDefault();
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		forLabel += StringPool.UNDERLINE + defaultLanguageId;
	}
}

if (Validator.isNull(baseType)){
	baseType = "text";
}

boolean checkboxField = baseType.equals("checkbox") || baseType.equals("boolean");
boolean choiceField = checkboxField || baseType.equals("radio");

boolean showForLabel = true;

if ((baseType.equals("assetCategories")) || (baseType.equals("assetTags")) || baseType.equals(Date.class.getName())) {
	showForLabel = false;
}

if (checkboxField) {
	forLabel += "Checkbox";
}

if (choiceField) {
	inlineLabel = "right";
}

String baseTypeCss = TextFormatter.format(baseType.toLowerCase(), TextFormatter.K);

String fieldCss = AUIUtil.buildCss(AUIUtil.FIELD_PREFIX, baseTypeCss, inlineField, disabled, choiceField, first, last, cssClass);
String inputCss = AUIUtil.buildCss(AUIUtil.INPUT_PREFIX, baseTypeCss, false, false, choiceField, false, false, inputCssClass);
String labelTag = AUIUtil.buildLabel(inlineLabel, showForLabel, forLabel);
%>

<c:if test='<%= !type.equals("hidden") %>'>
	<span class="<%= fieldCss %>">
		<span class="aui-field-content">
			<c:if test='<%= Validator.isNotNull(label) && !inlineLabel.equals("right") %>'>
				<label <%= labelTag %>>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= required %>">
						<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
					</c:if>

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>

					<c:if test="<%= changesContext %>">
						<span class="aui-helper-hidden-accessible"><liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
					</c:if>
				</label>
			</c:if>

			<c:if test="<%= Validator.isNotNull(prefix) %>">
				<span class="aui-prefix"><liferay-ui:message key="<%= prefix %>" /></span>
			</c:if>

			<span class='aui-field-element <%= Validator.isNotNull(label) && inlineLabel.equals("right") ? "aui-field-label-right" : StringPool.BLANK %>'>
</c:if>

<c:choose>
	<c:when test='<%= (model != null) && type.equals("assetCategories") %>'>
		<liferay-ui:asset-categories-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, classPK) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</c:when>
	<c:when test='<%= (model != null) && type.equals("assetTags") %>'>
		<liferay-ui:asset-tags-selector
			className="<%= model.getName() %>"
			classPK="<%= _getClassPK(bean, classPK) %>"
			contentCallback='<%= portletResponse.getNamespace() + "getSuggestionsContent" %>'
		/>
	</c:when>
	<c:when test="<%= (model != null) && Validator.isNull(type) %>">
		<liferay-ui:input-field
			bean="<%= bean %>"
			cssClass="<%= inputCss %>"
			defaultValue="<%= value %>"
			disabled="<%= disabled %>"
			field="<%= field %>"
			fieldParam='<%= fieldParam %>'
			format='<%= (Format)dynamicAttributes.get("format") %>'
			formName="<%= formName %>"
			model="<%= model %>"
		/>
	</c:when>
	<c:when test='<%= type.equals("checkbox") %>'>

		<%
		boolean valueBoolean = checked;

		if (value != null) {
			String valueString = value.toString();

			valueBoolean = GetterUtil.getBoolean(valueString);
		}

		if (!ignoreRequestValue) {
			valueBoolean = ParamUtil.getBoolean(request, name, valueBoolean);
		}
		%>

		<input id="<%= id %>" name="<%= namespace + name %>" type="hidden" value="<%= valueBoolean %>" />

		<input <%= valueBoolean ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>Checkbox" name="<%= namespace + name %>Checkbox" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> onClick="Liferay.Util.updateCheckboxValue(this); <%= onClick %>" <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="checkbox" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</c:when>
	<c:when test='<%= type.equals("radio") %>'>

		<%
		boolean valueBoolean = checked;

		String valueString = StringPool.BLANK;

		if (value != null) {
			valueString = value.toString();

			if (!ignoreRequestValue) {
				String requestValue = ParamUtil.getString(request, name);

				if (Validator.isNotNull(requestValue)) {
					valueBoolean = valueString.equals(requestValue);
				}
			}
		}
		%>

		<input <%= valueBoolean ? "checked" : StringPool.BLANK %> class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="radio" value="<%= valueString %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</c:when>
	<c:when test='<%= type.equals("timeZone") %>'>
		<span class="<%= fieldCss %>">
			<span class="aui-field-content">

				<%
				int displayStyle = TimeZone.LONG;

				if (dynamicAttributes.get("displayStyle") != null) {
					displayStyle = GetterUtil.getInteger((String)dynamicAttributes.get("displayStyle"));
				}
				%>

				<liferay-ui:input-time-zone
					daylight='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("daylight")) %>'
					disabled="<%= disabled %>"
					displayStyle="<%= displayStyle %>"
					name="<%= name %>"
					nullable='<%= GetterUtil.getBoolean((String)dynamicAttributes.get("nullable")) %>'
					value="<%= value.toString() %>"
				/>
			</span>
		</span>
	</c:when>
	<c:otherwise>

		<%
		String valueString = StringPool.BLANK;

		if (value != null) {
			valueString = value.toString();
		}

		if (!ignoreRequestValue && (type.equals("text") || type.equals("textarea"))) {
			valueString = BeanParamUtil.getStringSilent(bean, request, name, valueString);

			if (Validator.isNotNull(fieldParam)) {
				valueString = ParamUtil.getString(request, fieldParam, valueString);
			}
		}
		%>

		<c:choose>
			<c:when test='<%= type.equals("textarea") %>'>
				<textarea class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %>><%= HtmlUtil.escape(valueString) %></textarea>
			</c:when>
			<c:otherwise>
				<input class="<%= inputCss %>" <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= namespace + name %>" <%= Validator.isNotNull(onChange) ? "onChange=\"" + onChange + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(title) ? "title=\"" + title + "\"" : StringPool.BLANK %> type="<%= Validator.isNull(type) ? "text" : type %>" value="<%= HtmlUtil.escapeAttribute(valueString) %>" <%= AUIUtil.buildData(data) %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test='<%= !type.equals("hidden") %>'>
			</span>

			<c:if test="<%= Validator.isNotNull(suffix) %>">
				<span class="aui-suffix"><liferay-ui:message key="<%= suffix %>" /></span>
			</c:if>

			<c:if test='<%= Validator.isNotNull(label) && inlineLabel.equals("right") %>'>
				<label <%= labelTag %>>
					<liferay-ui:message key="<%= label %>" />

					<c:if test="<%= required %>">
						<span class="aui-label-required">(<liferay-ui:message key="required" />)</span>
					</c:if>

					<c:if test="<%= Validator.isNotNull(helpMessage) %>">
						<liferay-ui:icon-help message="<%= helpMessage %>" />
					</c:if>

					<c:if test="<%= changesContext %>">
						<span class="aui-helper-hidden-accessible"><liferay-ui:message key="changing-the-value-of-this-field-will-reload-the-page" />)</span>
					</c:if>
				</label>
			</c:if>
		</span>
	</span>
</c:if>

<%!
private long _getClassPK(Object bean, long classPK) {
	if ((bean != null) && (classPK <= 0)) {
		if (bean instanceof BaseModel) {
			BaseModel baseModel = (BaseModel)bean;

			Serializable primaryKeyObj = baseModel.getPrimaryKeyObj();

			if (primaryKeyObj instanceof Long) {
				classPK = (Long)primaryKeyObj;
			}
			else {
				classPK = GetterUtil.getLong(primaryKeyObj.toString());
			}
		}
		else if (bean instanceof RepositoryModel) {
			RepositoryModel repositoryModel = (RepositoryModel)bean;

			classPK = repositoryModel.getPrimaryKey();
		}
	}

	return classPK;
}
%>