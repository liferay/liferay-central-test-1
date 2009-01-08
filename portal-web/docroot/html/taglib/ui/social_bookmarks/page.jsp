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

<%@ include file="/html/taglib/ui/social_bookmarks/init.jsp" %>

<%
if( typesArray.length > 0 ) {
	String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;
%>

<div class="taglib-social-bookmarks" id="<%= randomNamespace %>socialBookmarks">
	<a class="show-bookmarks" href="javascript: ;"><liferay-ui:message key="add-this-to" /><img alt="delicious" src="<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/delicious.png" width="10" /> <img alt="digg" src="<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/digg.png" width="10" /> <img alt="furl" src="<%= themeDisplay.getPathThemeImages() %>/social_bookmarks/furl.png" width="10" /></a>

	<ul class="lfr-component">

		<%
		for (int i = 0; i < typesArray.length; i++) {
		%>

			<li>
				<liferay-ui:social-bookmark type="<%= typesArray[i] %>" url="<%= url %>" title="<%= title %>" target="<%= target %>" />
			</li>

		<%
		}
		%>

	</ul>
</div>

<script>
	jQuery(
		function () {
			var socialBookmarks = jQuery('#<%= randomNamespace %>socialBookmarks');
			var linkSocialBookmarks = socialBookmarks.find('.show-bookmarks');

			linkSocialBookmarks.click(
				function(event) {
					socialBookmarks.toggleClass('visible');
				}
			);
		}
	);
</script>

<% } %>
