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
String scriptURL = PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_SCRIPT + PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PUBLIC;
%>

<script type="text/javascript">
	var RecaptchaOptions = {
		lang : '<%= locale.getLanguage() %>',
		theme : 'white'
	};
</script>

<script src="<%= scriptURL %>" type="text/javascript">
</script>

<noscript>
	<iframe frameborder="0" height="300" src="<%= scriptURL %>" width="500"></iframe>

	<br />

	<textarea cols="40" name="recaptcha_challenge_field" rows="3"></textarea>

	<input name="recaptcha_response_field"  type="hidden"value="manual_challenge" />
</noscript>