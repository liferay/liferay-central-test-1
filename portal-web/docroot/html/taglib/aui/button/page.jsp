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
String cssClass = GetterUtil.getString((String)request.getAttribute("aui:button:cssClass"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:button:dynamicAttributes");
String name = namespace + GetterUtil.getString((String)request.getAttribute("aui:button:name"));
String onClick = GetterUtil.getString((String)request.getAttribute("aui:button:onClick"));
String type = GetterUtil.getString((String)request.getAttribute("aui:button:type"));
String value = (String)request.getAttribute("aui:button:value");

Boolean disabled = dynamicAttributes.get("disabled");

if (disabled == null) {
	disabled = Boolean.FALSE;
}

if (onClick.startsWith(Http.HTTP_WITH_SLASH) || onClick.startsWith(Http.HTTPS_WITH_SLASH) || onClick.startsWith(StringPool.SLASH)) {
	onClick = "location.href = '" +  HtmlUtil.escape(onClick) + "';";
}
%>

<span class="aui-form-button <%= !type.equals("button") ? "aui-form-" + type : StringPool.BLANK %> <%= cssClass %> <%= disabled ? "aui-input-disabled" : StringPool.BLANK %>">
	<input id="<%= name %>" <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> type="<%= type %>" value="<%= LanguageUtil.get(pageContext, value) %>" <%= _buildDynamicAttributes(dynamicAttributes) %>>
</span>