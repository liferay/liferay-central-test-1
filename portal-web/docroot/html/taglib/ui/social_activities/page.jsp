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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portlet.social.model.SocialActivity" %>
<%@ page import="com.liferay.portlet.social.model.SocialActivityFeedEntry" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %>

<%@ page import="com.liferay.util.RSSUtil" %>

<%@ page import="com.sun.syndication.feed.synd.SyndContent" %>
<%@ page import="com.sun.syndication.feed.synd.SyndContentImpl" %>
<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %>
<%@ page import="com.sun.syndication.feed.synd.SyndEntryImpl" %>
<%@ page import="com.sun.syndication.feed.synd.SyndFeed" %>
<%@ page import="com.sun.syndication.feed.synd.SyndFeedImpl" %>

<%
String className = (String)request.getAttribute("liferay-ui:social-activities:className");
long classPK = GetterUtil.getLong((String)request.getAttribute("liferay-ui:social-activities:classPK"));
List<SocialActivity> activities = (List<SocialActivity>)request.getAttribute("liferay-ui:social-activities:activities");
boolean feedEnabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:social-activities:feedEnabled"));
String feedTitle = (String)request.getAttribute("liferay-ui:social-activities:feedTitle");
String feedLink = (String)request.getAttribute("liferay-ui:social-activities:feedLink");
String feedLinkMessage = (String)request.getAttribute("liferay-ui:social-activities:feedLinkMessage");

if (activities == null) {
	activities = SocialActivityLocalServiceUtil.getActivities(0, className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
}

DateFormat dateFormatDate = new SimpleDateFormat("MMMM d", locale);

if (timeZone != null) {
	dateFormatDate.setTimeZone(timeZone);
}

DateFormat timeFormatDate = DateFormats.getTime(locale, timeZone);
%>

<c:choose>
	<c:when test="<%= themeDisplay.isStateExclusive() %>">

		<%
		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.DEFAULT_FEED_TYPE);
		syndFeed.setLink(feedLink);
		syndFeed.setTitle(feedTitle);
		syndFeed.setDescription(feedTitle);

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (SocialActivity activity : activities) {
			SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);

			if (activityFeedEntry == null) {
				continue;
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setTitle(HtmlUtil.extractText(activityFeedEntry.getTitle()));
			syndEntry.setPublishedDate(activity.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);
			syndContent.setValue(activityFeedEntry.getBody());

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		String feedXML = StringPool.BLANK;

		try {
			feedXML = RSSUtil.export(syndFeed);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		response.setContentType(ContentTypes.TEXT_XML_UTF8);
		%>

		<%= feedXML %>
	</c:when>
	<c:otherwise>
		<div class="taglib-social-activities">

			<%
			boolean firstDaySeparator = true;

			Date now = new Date();

			int daysBetween = -1;

			for (SocialActivity activity : activities) {
				SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);

				if (activityFeedEntry == null) {
					continue;
				}

				Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), activityFeedEntry.getPortletId());

				int curDaysBetween = DateUtil.getDaysBetween(activity.getCreateDate(), now, timeZone);
			%>

				<c:if test="<%= curDaysBetween > daysBetween %>">

					<%
					daysBetween = curDaysBetween;
					%>

					<div class="<%= firstDaySeparator ? "first-" : "" %>day-separator">
						<c:choose>
							<c:when test="<%= curDaysBetween == 0 %>">
								<liferay-ui:message key="today" />
							</c:when>
							<c:when test="<%= curDaysBetween == 1 %>">
								<liferay-ui:message key="yesterday" />
							</c:when>
							<c:otherwise>
								<%= dateFormatDate.format(activity.getCreateDate()) %>
							</c:otherwise>
						</c:choose>
					</div>

					<%
					firstDaySeparator = false;
					%>

				</c:if>

				<div class="activity-separator"><!-- --></div>

				<table class="lfr-table">
				<tr>
					<td>
						<liferay-ui:icon message="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>" src="<%= portlet.getContextPath() + portlet.getIcon() %>" />
					</td>
					<td>
						<%= activityFeedEntry.getTitle() %>

						<%= timeFormatDate.format(activity.getCreateDate()) %>
					</td>
				<tr>
					<td></td>
					<td>
						<%= activityFeedEntry.getBody() %>
					</td>
				</tr>
				</table>

			<%
			}
			%>

		</div>

		<c:if test="<%= feedEnabled && (activities.size() > 0) %>">
			<div class="separator"><!-- --></div>

			<liferay-ui:icon
				image="rss"
				message="<%= feedLinkMessage %>"
				url="<%= feedLink %>"
				method="get"
				target="_blank"
				label="<%= true %>"
			/>
		</c:if>
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.taglib.ui.social_activities.page.jsp");
%>