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

<%@ include file="/html/taglib/init.jsp" %>

<%
Object bean = request.getAttribute("aui:select:bean");
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:select:cssClass"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("aui:select:disabled"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:select:dynamicAttributes");
boolean first = GetterUtil.getBoolean((String)request.getAttribute("aui:select:first"));
String helpMessage = GetterUtil.getString((String)request.getAttribute("aui:select:helpMessage"));
boolean inlineField = GetterUtil.getBoolean((String)request.getAttribute("aui:select:inlineField"));
boolean inlineLabel = GetterUtil.getBoolean((String)request.getAttribute("aui:select:inlineLabel"));
String id = namespace + GetterUtil.getString((String)request.getAttribute("aui:select:id"));
String label = GetterUtil.getString((String)request.getAttribute("aui:select:label"));
boolean last = GetterUtil.getBoolean((String)request.getAttribute("aui:select:last"));
String listType = GetterUtil.getString((String)request.getAttribute("aui:select:listType"));
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:select:name"));
boolean showEmptyOption = GetterUtil.getBoolean((String)request.getAttribute("aui:select:showEmptyOption"));
%>

<div class="aui-ctrl-holder <%= inlineField ? "inline-field" : StringPool.BLANK %> <%= cssClass %> <%= first ? "aui-first" : StringPool.BLANK %> <%= last ? "aui-last" : StringPool.BLANK %>">
	<c:if test="<%= Validator.isNotNull(label) %>">
		<label class="aui-form-label <%= inlineLabel ? "inline-label" : StringPool.BLANK  %>" for="<%= name %>">
			<liferay-ui:message key="<%= label %>" />

			<c:if test="<%= Validator.isNotNull(helpMessage) %>">
				<liferay-ui:icon-help message="<%= helpMessage %>" />
			</c:if>

			<c:if test="<%= !inlineLabel %>">
				</label>
			</c:if>
	</c:if>

	<span class="aui-form-field aui-form-select">
		<select <%= disabled ? "disabled" : StringPool.BLANK %> id="<%= id %>" name="<%= name %>" <%= _buildDynamicAttributes(dynamicAttributes) %>>
			<c:if test="<%= showEmptyOption %>">
				<aui:option />
			</c:if>

			<c:if test="<%= Validator.isNotNull(listType) %>">

				<%
				int listTypeId = ParamUtil.getInteger(request, (String)request.getAttribute("aui:select:name"), BeanParamUtil.getInteger(bean, request, "typeId"));

				List<ListType> listTypeModels = ListTypeServiceUtil.getListTypes(listType);

				for (ListType listTypeModel : listTypeModels) {
				%>

					<aui:option selected="<%= listTypeId == listTypeModel.getListTypeId() %>" value="<%= listTypeModel.getListTypeId() %>"><liferay-ui:message key="<%= listTypeModel.getName() %>" /></aui:option>

				<%
				}
				%>

			</c:if>