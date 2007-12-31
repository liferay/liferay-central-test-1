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

<%@ include file="/html/portlet/rss/init.jsp" %>

<%
String url = ParamUtil.getString(request, "url");
String title = StringPool.BLANK;
boolean hide = false;
%>

<c:choose>
	<c:when test="<%= windowState.equals(WindowState.MAXIMIZED) && Validator.isNotNull(url) %>">

		<%
		int i = 0;
		entriesPerFeed = 20;
		%>

		<%@ include file="/html/portlet/rss/feed.jspf" %>
	</c:when>
	<c:otherwise>

		<%
		for (int i = 0; i < urls.length; i++) {
			url = urls[i];

			if (i < titles.length) {
				title = titles[i];
			}
			else {
				title = StringPool.BLANK;
			}

			if (i == 0) {
				hide = false;
			}
			else {
				hide = true;
			}
		%>

			<%@ include file="/html/portlet/rss/feed.jspf" %>

		<%
		}
		%>

		<script type="text/javascript">
			jQuery(
				function() {
					var currentPortlet = jQuery('#p_p_id<portlet:namespace />');

					currentPortlet.Accordion({
						headerSelector: '.header',
						panelSelector: '.feeds',
						panelHeight: jQuery("#p_p_id<portlet:namespace /> .feeds:first").height(),
						speed: 300
					});

					if (!jQuery.browser.msie) {
						currentPortlet.css('overflow', 'visible');
					}
				}
			);
		</script>
	</c:otherwise>
</c:choose>