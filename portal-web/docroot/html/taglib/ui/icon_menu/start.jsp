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
String message = (String)request.getAttribute("liferay-ui:icon-menu:message");
String align = (String)request.getAttribute("liferay-ui:icon-menu:align");
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:cssClass"));
boolean showExpanded = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:icon-menu:showExpanded"));
%>

<c:choose>
	<c:when test="<%= showExpanded %>">
		<div class="lfr-component lfr-menu-list <%= align %> <%= cssClass %>">
	</c:when>
	<c:otherwise >
		<ul class="lfr-component lfr-actions <%= align %> <%= cssClass %>">

			<li class="lfr-trigger">
				<strong><span><liferay-ui:message key="<%= message %>" /></span></strong>
	</c:otherwise>
</c:choose>

<ul>