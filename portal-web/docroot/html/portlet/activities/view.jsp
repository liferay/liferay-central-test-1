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

<%@ include file="/html/portlet/activities/init.jsp" %>

<%
Group group = GroupLocalServiceUtil.getGroup(themeDisplay.getPortletGroupId());

User user2 = user;

if (group.isUser()) {
	user2 = UserLocalServiceUtil.getUserById(group.getClassPK());
}

List<SocialActivity> activities = SocialActivityLocalServiceUtil.getUserActivities(user2.getUserId(), 0, SearchContainer.DEFAULT_DELTA);
%>

<%
Date now = new Date();

int daysBetween = 0;

for (SocialActivity activity : activities) {
	SocialActivityFeedEntry activityFeedEntry = SocialActivityInterpreterLocalServiceUtil.interpret(activity, themeDisplay);

	if (activityFeedEntry != null) {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), activityFeedEntry.getPortletId());

		int curDaysBetween = DateUtil.getDaysBetween(activity.getCreateDate(), now, timeZone);
%>

		<c:if test="<%= curDaysBetween > 0 %>">
			<c:if test="<%= daysBetween != curDaysBetween %>">

				<%
				daysBetween = curDaysBetween;
				%>

				<div class="day-separator">
					<c:choose>
						<c:when test="<%= curDaysBetween == 1 %>">
							<liferay-ui:message key="yesterday" />
						</c:when>
						<c:otherwise>
							<%= dateFormatDate.format(activity.getCreateDate()) %>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
		</c:if>

		<div style="padding-bottom: 10px;"><!-- --></div>

		<table class="lfr-table">
		<tr>
			<td>
				<img src="<%= portlet.getContextPath() + portlet.getIcon() %>" />
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
}
%>