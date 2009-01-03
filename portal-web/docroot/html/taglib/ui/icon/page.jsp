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

<%@ include file="/html/taglib/ui/icon/init.jsp" %>

<%
if (method.equals("post") && (url.startsWith(Http.HTTP_WITH_SLASH) || url.startsWith(Http.HTTPS_WITH_SLASH))) {
	url = "javascript: submitForm(document.hrefFm, '" + HttpUtil.encodeURL(url) + "');";
}

String cssClassHtml = StringPool.BLANK;

if (Validator.isNotNull(cssClass)) {
	cssClassHtml = "class=\"nobr " + cssClass + "\"";
}

String targetHtml = StringPool.BLANK;

if (!target.equals("_self")) {
	targetHtml = "target=\"" + target + "\"";
}

if (themeDisplay.isThemeImagesFastLoad()) {
	ThemeSpriteImage themeSpriteImage = null;

	String imageFileName = StringUtil.replace(src, "common/../", "");

	String imagesPath = theme.getContextPath() + theme.getImagesPath();

	if (imageFileName.startsWith(imagesPath)) {
		imageFileName = imageFileName.substring(imagesPath.length() + 1);

		themeSpriteImage = theme.getThemeSpriteImage(imageFileName);

		if (themeSpriteImage != null) {
			src = themeDisplay.getPathThemeImages() + "/spacer.png";

			details += " style=\"background-image: url('" + themeDisplay.getPathThemeImages() + themeSpriteImage.getSpriteFileName() + "'); background-position: 0px -" + themeSpriteImage.getOffset() + "px; background-repeat: no-repeat; height: " + themeSpriteImage.getHeight() + "px; width: " + themeSpriteImage.getWidth() + "px;\"";
		}
	}
}
%>

<c:choose>
	<c:when test="<%= (iconListIconCount != null) && ((iconListSingleIcon == null) || iconListShowWhenSingleIcon) %>">
		<li <%= cssClassHtml %>><c:if test="<%= Validator.isNotNull(url) %>"><a href="<%= url %>" <%= targetHtml %>></c:if><img class="icon" src="<%= src %>" <%= details %> /><c:if test="<%= Validator.isNotNull(url) %>"></a></c:if> <c:if test="<%= Validator.isNotNull(url) %>"><a href="<%= url %>" <%= targetHtml %>></c:if><liferay-ui:message key="<%= message %>" /><c:if test="<%= Validator.isNotNull(url) %>"></a></c:if></li>
	</c:when>
	<c:when test="<%= (iconMenuIconCount != null) && ((iconMenuSingleIcon == null) || iconMenuShowWhenSingleIcon) %>">
		<li <%= cssClassHtml %>><c:if test="<%= Validator.isNotNull(url) %>"><a href="<%= url %>" <%= targetHtml %>></c:if><img class="icon" src="<%= src %>" <%= details %> /> <liferay-ui:message key="<%= message %>" /><c:if test="<%= Validator.isNotNull(url) %>"></a></c:if></li>
	</c:when>
	<c:otherwise>
		<span <%= cssClassHtml %>><c:if test="<%= Validator.isNotNull(url) %>"><a href="<%= url %>" <%= targetHtml %>></c:if><img class="icon" src="<%= src %>" <%= details %> /><c:if test="<%= Validator.isNotNull(url) %>"></a></c:if><c:if test="<%= label %>"> <c:if test="<%= Validator.isNotNull(url) %>"><a href="<%= url %>" <%= targetHtml %>></c:if><liferay-ui:message key="<%= message %>" /><c:if test="<%= Validator.isNotNull(url) %>"></a></c:if></c:if></span>
	</c:otherwise>
</c:choose>