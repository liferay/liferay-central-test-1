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

<%@ include file="init.jsp" %>

<%
if (Validator.isNotNull(href)) {
	onClick = "location.href = '" + HtmlUtil.escapeJS(PortalUtil.escapeRedirect(href)) + "';";
}
else if (onClick.startsWith(Http.HTTP_WITH_SLASH) || onClick.startsWith(Http.HTTPS_WITH_SLASH) || onClick.startsWith(StringPool.SLASH)) {
	onClick = "location.href = '" + HtmlUtil.escape(PortalUtil.escapeRedirect(onClick)) + "';";
}
else if (onClick.startsWith("wsrp_rewrite?")){
	onClick = "location.href = '" + HtmlUtil.escape(onClick) + "';";
}
%>

<span class="<%= AUIUtil.buildCss(AUIUtil.BUTTON_PREFIX, type, false, disabled, false, false, false, cssClass) %>">
	<span class="yui3-aui-button-content">
		<input class="<%= AUIUtil.buildCss(AUIUtil.BUTTON_INPUT_PREFIX, type, false, false, false, false, false, inputCssClass) %>" <%= disabled ? "disabled" : StringPool.BLANK %> <%= Validator.isNotNull(name) ? "id=\"" + namespace + name + "\"" : StringPool.BLANK %> <%= Validator.isNotNull(onClick) ? "onClick=\"" + onClick + "\"" : StringPool.BLANK %> type='<%= type.equals("cancel") ? "button" : type %>' value="<%= LanguageUtil.get(pageContext, value) %>" <%= AUIUtil.buildData(data) %> <%= (customAttributes != null) ? customAttributes : StringPool.BLANK %> <%= InlineUtil.buildDynamicAttributes(dynamicAttributes) %> />
	</span>
</span>