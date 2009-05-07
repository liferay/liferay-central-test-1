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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
Contact selContact = (Contact)request.getAttribute("user.selContact");
%>

<h3><liferay-ui:message key="instant-messenger" /></h3>

<c:choose>
	<c:when test="<%= selContact != null %>">
		<fieldset class="exp-block-labels">
			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />aimSn"><liferay-ui:message key="aim" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="aimSn" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />icqSn"><liferay-ui:message key="icq" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="icqSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getIcqSn()) %>">
					<img class="instant-messenger-logo" src="http://web.icq.com/whitepages/online?icq=<%= selContact.getIcqSn() %>&img=5" />
				</c:if>
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />jabberSn">	<liferay-ui:message key="jabber" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="jabberSn" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />msnSn"><liferay-ui:message key="msn" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="msnSn" />
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />skypeSn"><liferay-ui:message key="skype" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="skypeSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getSkypeSn()) %>">
					<a href="callto://<%= selContact.getSkypeSn() %>"><img alt="<liferay-ui:message key="skype" />" class="instant-messenger-logo" src="http://mystatus.skype.com/smallicon/<%= selContact.getSkypeSn() %>" /></a>
				</c:if>
			</div>

			<div class="exp-ctrl-holder">
				<label for="<portlet:namespace />ymSn"><liferay-ui:message key="ym" /></label>

				<liferay-ui:input-field model="<%= Contact.class %>" bean="<%= selContact %>" field="ymSn" />

				<c:if test="<%= Validator.isNotNull(selContact.getYmSn()) %>">
					<img class="instant-messenger-logo" src="http://opi.yahoo.com/online?u=<%= selContact.getYmSn() %>&m=g&t=0" />
				</c:if>
			</div>
		</fieldset>
	</c:when>
	<c:otherwise>
		<span class="portlet-msg-info"><liferay-ui:message key="this-section-will-be-editable-after-creating-the-user" /></span>
	</c:otherwise>
</c:choose>