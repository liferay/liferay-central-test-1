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

<%@ include file="/html/portlet/activities/init.jsp" %>

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

int start = 0;
int end = 10;

List<SocialActivity> activities = null;

if (group.isCommunity()) {
	activities = SocialActivityLocalServiceUtil.getGroupActivities(group.getGroupId(), start, end);
}
else if (group.isOrganization()) {
	activities = SocialActivityLocalServiceUtil.getOrganizationActivities(group.getClassPK(), start, end);
}
else if (group.isUser()) {
	activities = SocialActivityLocalServiceUtil.getUserActivities(group.getClassPK(), start, end);
}

PortletURL rssURL = renderResponse.createRenderURL();

rssURL.setParameter("rss", "1");

String taglibFeedTitle = LanguageUtil.format(pageContext, "subscribe-to-x's-activities", group.getDescriptiveName());
String taglibFeedLinkMessage = LanguageUtil.format(pageContext, "subscribe-to-x's-activities", group.getDescriptiveName());
%>

<liferay-ui:social-activities
	activities="<%= activities %>"
	feedEnabled="<%= true %>"
	feedTitle="<%= HtmlUtil.escape(taglibFeedTitle) %>"
	feedLink="<%= rssURL.toString() %>"
	feedLinkMessage="<%= HtmlUtil.escape(taglibFeedLinkMessage) %>"
/>