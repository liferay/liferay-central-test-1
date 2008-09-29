<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
boolean secure = request.isSecure();

if (PropsValues.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS && !secure) {
	secure = true;
}

String redirect = ParamUtil.getString(renderRequest, "redirect");
String login = LoginAction.getLogin(request, "login", company);
String password = StringPool.BLANK;
boolean rememberMe = ParamUtil.getBoolean(request, "rememberMe");
%>

<form action="<portlet:actionURL secure="<%= secure %>"><portlet:param name="saveLastPath" value="0" /><portlet:param name="struts_action" value="/login/view" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />rememberMe" type="hidden" value="<%= rememberMe %>" />

<liferay-ui:error exception="<%= AuthException.class %>" message="authentication-failed" />
<liferay-ui:error exception="<%= CookieNotSupportedException.class %>" message="authentication-failed-please-enable-browser-cookies" />
<liferay-ui:error exception="<%= NoSuchUserException.class %>" message="please-enter-a-valid-login" />
<liferay-ui:error exception="<%= PasswordExpiredException.class %>" message="your-password-has-expired" />
<liferay-ui:error exception="<%= UserEmailAddressException.class %>" message="please-enter-a-valid-login" />
<liferay-ui:error exception="<%= UserLockoutException.class %>" message="this-account-has-been-locked" />
<liferay-ui:error exception="<%= UserPasswordException.class %>" message="please-enter-a-valid-password" />
<liferay-ui:error exception="<%= UserScreenNameException.class %>" message="please-enter-a-valid-screen-name" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="login" />
	</td>
	<td>
		<input name="<portlet:namespace />login" style="width: 120px;" type="text" value="<%= login %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="password" />
	</td>
	<td>
		<input id="<portlet:namespace />password" name="<portlet:namespace />password" style="width: 120px;" type="password" value="<%= password %>" />

		<span id="<portlet:namespace />passwordCapsLockSpan" style="display: none;"><liferay-ui:message key="caps-lock-is-on" /></span>
	</td>
</tr>

<c:if test="<%= company.isAutoLogin() && !PropsValues.SESSION_DISABLED %>">
	<tr>
		<td>
			<span style="font-size: xx-small;">
				<liferay-ui:message key="remember-me" />
			</span>
		</td>
		<td>
			<input <%= rememberMe ? "checked" : "" %> type="checkbox" onClick="
				if (this.checked) {
					document.<portlet:namespace />fm.<portlet:namespace />rememberMe.value = 'on';
				}
				else {
					document.<portlet:namespace />fm.<portlet:namespace />rememberMe.value = 'off';
				}"
			>
		</td>
	</tr>
</c:if>

</table>

<br />

<input type="submit" value="<liferay-ui:message key="sign-in" />" />

<br /><br />

<c:if test="<%= company.isStrangers() %>">
	<a href="<%= themeDisplay.getURLCreateAccount() %>"><liferay-ui:message key="create-account" /></a>
</c:if>

<c:if test="<%= company.isSendPassword() %>">
	&nbsp; | &nbsp;
	<portlet:renderURL windowState="<%= WindowState.NORMAL.toString() %>" var="forgotPasswordURL">
		<portlet:param name="struts_action" value="/login/view" />
		<portlet:param name="<%= Constants.CMD %>" value="forgot-password" />
	</portlet:renderURL>

	<a href="<%= forgotPasswordURL.toString() %>"><liferay-ui:message key="forgot-password" />?</a>
</c:if>


</form>

<script type="text/javascript">
	jQuery(
		function() {
			jQuery('#<portlet:namespace />password').keypress(
				function(event) {
					Liferay.Util.showCapsLock(event, '<portlet:namespace />passwordCapsLockSpan');
				}
			);
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />login);
	</c:if>
</script>