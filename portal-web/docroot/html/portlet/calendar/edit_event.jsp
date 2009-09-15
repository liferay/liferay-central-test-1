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

<%@ include file="/html/portlet/calendar/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

CalEvent event = (CalEvent)request.getAttribute(WebKeys.CALENDAR_EVENT);

long eventId = BeanParamUtil.getLong(event, request, "eventId");

Calendar startDate = CalendarUtil.roundByMinutes((Calendar)selCal.clone(), 15);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		startDate = CalendarFactoryUtil.getCalendar();
	}

	startDate.setTime(event.getStartDate());
}

Calendar endDate = (Calendar)curCal.clone();

endDate.add(Calendar.YEAR, 1);

if (event != null) {
	if (!event.isTimeZoneSensitive()) {
		endDate = CalendarFactoryUtil.getCalendar();
	}

	if (event.getEndDate() != null) {
		endDate.setTime(event.getEndDate());
	}
}

endDate.set(Calendar.HOUR_OF_DAY, 23);
endDate.set(Calendar.MINUTE, 59);
endDate.set(Calendar.SECOND, 59);

int durationHour = BeanParamUtil.getInteger(event, request, "durationHour", 1);
int durationMinute = BeanParamUtil.getInteger(event, request, "durationMinute");
String type = BeanParamUtil.getString(event, request, "type");
boolean repeating = BeanParamUtil.getBoolean(event, request, "repeating");

Recurrence recurrence = null;

int recurrenceType = ParamUtil.getInteger(request, "recurrenceType", Recurrence.NO_RECURRENCE);
String recurrenceTypeParam = ParamUtil.getString(request, "recurrenceType");
if (Validator.isNull(recurrenceTypeParam) && (event != null)) {
	if (event.getRepeating()) {
		recurrence = event.getRecurrenceObj();
		recurrenceType = recurrence.getFrequency();
	}
}

int endDateType = ParamUtil.getInteger(request, "endDateType");
String endDateTypeParam = ParamUtil.getString(request, "endDateType");
if (Validator.isNull(endDateTypeParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		if (recurrence.getUntil() != null) {
			endDateType = 2;
		}
		else if (recurrence.getOccurrence() > 0) {
			endDateType = 1;
		}
	}
}

int endDateOccurrence = ParamUtil.getInteger(request, "endDateOccurrence", 10);
String endDateOccurrenceParam = ParamUtil.getString(request, "endDateOccurrence");
if (Validator.isNull(endDateOccurrenceParam) && (event != null)) {
	if ((event.getRepeating()) && (recurrence != null)) {
		endDateOccurrence = recurrence.getOccurrence();
	}
}

int remindBy = BeanParamUtil.getInteger(event, request, "remindBy", CalEventImpl.REMIND_BY_EMAIL);
int firstReminder = BeanParamUtil.getInteger(event, request, "firstReminder", (int)Time.MINUTE * 15);
int secondReminder = BeanParamUtil.getInteger(event, request, "secondReminder", (int)Time.MINUTE * 5);
%>

<script type="text/javascript">
	function <portlet:namespace />init() {
		<c:choose>
			<c:when test="<%= recurrenceType == Recurrence.NO_RECURRENCE %>">
				<portlet:namespace />showTable("<portlet:namespace />neverTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.DAILY %>">
				<portlet:namespace />showTable("<portlet:namespace />dailyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.WEEKLY %>">
				<portlet:namespace />showTable("<portlet:namespace />weeklyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.MONTHLY %>">
				<portlet:namespace />showTable("<portlet:namespace />monthlyTable");
			</c:when>
			<c:when test="<%= recurrenceType == Recurrence.YEARLY %>">
				<portlet:namespace />showTable("<portlet:namespace />yearlyTable");
			</c:when>
		</c:choose>
	}

	function <portlet:namespace />showTable(id) {
		document.getElementById("<portlet:namespace />neverTable").style.display = "none";
		document.getElementById("<portlet:namespace />dailyTable").style.display = "none";
		document.getElementById("<portlet:namespace />weeklyTable").style.display = "none";
		document.getElementById("<portlet:namespace />monthlyTable").style.display = "none";
		document.getElementById("<portlet:namespace />yearlyTable").style.display = "none";

		document.getElementById(id).style.display = "block";
	}

	function <portlet:namespace />saveEvent() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= event == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<portlet:actionURL var="editEventURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="struts_action" value="/calendar/edit_event" />
</portlet:actionURL>

<aui:form action="<%= editEventURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveEvent(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="eventId" type="hidden" value="<%= eventId %>" />

	<liferay-ui:error exception="<%= EventDurationException.class %>" message="please-enter-a-longer-duration" />
	<liferay-ui:error exception="<%= EventStartDateException.class %>" message="please-enter-a-valid-start-date" />
	<liferay-ui:error exception="<%= EventTitleException.class %>" message="please-enter-a-valid-title" />

	<aui:model-context bean="<%= event %>" model="<%= CalEvent.class %>" />

	<aui:fieldset>
		<aui:input name="startDate" value="<%= startDate %>" />

		<aui:field-wrapper label="duration">
			<aui:column>
				<aui:select label="hours" name="durationHour">

					<%
					for (int i = 0; i <= 24 ; i++) {
					%>

						<aui:option label="<%= i %>" selected="<%= durationHour == i %>" />

					<%
					}
					%>

				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="minutes" name="durationMinute">

					<%
					for (int i=0; i < 60 ; i = i + 5) {
					%>

						<aui:option label='<%= ":" + (i < 10 ? "0" : StringPool.BLANK) + i %>' selected="<%= durationMinute == i %>" value="<%= i %>" />

					<%
					}
					%>

				</aui:select>
			</aui:column>
		</aui:field-wrapper>

		<aui:input inlineLabel="left" label="all-day-event" name="allDay" type="checkbox" value="<%= event == null ? false : event.isAllDay() %>" />

		<aui:input inlineLabel="left" name="timeZoneSensitive" type="checkbox" value="<%= event == null ? true : event.isTimeZoneSensitive() %>" />

		<aui:input name="title" value='<%= event == null ? LanguageUtil.get(pageContext, "new-event") : event.getTitle() %>' />

		<aui:input name="description" />

		<aui:select name="type">

			<%
			for (int i = 0; i < CalEventImpl.TYPES.length; i++) {
			%>

				<aui:option label="<%= CalEventImpl.TYPES[i] %>" selected="<%= type.equals(CalEventImpl.TYPES[i]) %>" />

			<%
			}
			%>

		</aui:select>

		<liferay-ui:custom-attributes-available className="<%= CalEvent.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= CalEvent.class.getName() %>"
				classPK="<%= (event != null) ? event.getEventId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= event == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= CalEvent.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>
	</aui:fieldset>

	<br />

	<liferay-ui:panel-container id="editEvent" extended="<%= Boolean.TRUE %>" persistState="<%= true %>">
		<liferay-ui:panel id="repeat" title='<%= LanguageUtil.get(pageContext, "repeat") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
			<liferay-ui:error exception="<%= EventEndDateException.class %>" message="please-enter-a-valid-end-date" />

			<liferay-ui:input-repeat event="<%= event %>" />

			<aui:fieldset>
				<aui:field-wrapper cssClass="end-date-field" label="end-date" name="endDateType">
					<aui:input checked="<%= endDateType == 0 %>" cssClass="input-container" label="no-end-date" name="endDateType" type="radio" value="0" />

					<%--<aui:input checked="<%= endDateType == 1 %>" cssClass="input-container" inlineField="<%= true %>" label="end-after" name="endDateType" type="radio" value="1" />--%>

					<%--<aui:input inlineLabel="right" label="occurrence-s" maxlength="3" name="endDateOccurrence" size="3" type="text" value="<%= endDateOccurrence %>" />--%>

					<aui:input checked="<%= endDateType == 2 %>" cssClass="input-container" inlineField="<%= true %>" label="end-by" name="endDateType" type="radio" value="2" />

					<aui:input label="" name="endDate" value="<%= endDate %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel id="reminders" title='<%= LanguageUtil.get(pageContext, "reminders") %>' collapsible="<%= true %>" persistState="<%= true %>" extended="<%= true %>">
			<aui:fieldset>
				<aui:select inlineField="<%= true %>" inlineLabel="left" label="remind-me" name="firstReminder">

					<%
					for (int i = 0; i < CalEventImpl.REMINDERS.length; i++) {
					%>

						<aui:option selected="<%= (firstReminder == CalEventImpl.REMINDERS[i]) %>" value="<%= CalEventImpl.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventImpl.REMINDERS[i]) %></aui:option>

					<%
					}
					%>

				</aui:select>

				<aui:select inlineField="<%= true %>" inlineLabel="left" label="before-and-again" name="secondReminder" suffix="before-the-event-by">

					<%
					for (int i = 0; i < CalEventImpl.REMINDERS.length; i++) {
					%>

						<aui:option selected="<%= (secondReminder == CalEventImpl.REMINDERS[i]) %>" value="<%= CalEventImpl.REMINDERS[i] %>"><%= LanguageUtil.getTimeDescription(pageContext, CalEventImpl.REMINDERS[i]) %></aui:option>

					<%
					}
					%>

				</aui:select>

				<aui:field-wrapper cssClass="reminders" label="">
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_NONE %>" label="do-not-send-a-reminder" name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_NONE %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_EMAIL %>" label='<%= LanguageUtil.get(pageContext, "email-address") + " (" + user.getEmailAddress() + ")" %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_EMAIL %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_SMS %>" label='<%= LanguageUtil.get(pageContext, "sms") + (Validator.isNotNull(contact.getSmsSn()) ? " (" + contact.getSmsSn() + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_SMS %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_AIM %>" label='<%= LanguageUtil.get(pageContext, "aim") + (Validator.isNotNull(contact.getAimSn()) ? " (" + contact.getAimSn() + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_AIM %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_ICQ %>" label='<%= LanguageUtil.get(pageContext, "icq") + (Validator.isNotNull(contact.getIcqSn()) ? " (" + contact.getIcqSn() + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_ICQ %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_MSN %>" label='<%= LanguageUtil.get(pageContext, "msn") + (Validator.isNotNull(contact.getMsnSn()) ? " (" + contact.getMsnSn() + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_MSN %>" />
					<aui:input checked="<%= remindBy == CalEventImpl.REMIND_BY_YM %>" label='<%= LanguageUtil.get(pageContext, "ym") + (Validator.isNotNull(contact.getYmSn()) ? " (" + contact.getYmSn() + ")" : "") %>' name="remindBy" type="radio" value="<%= CalEventImpl.REMIND_BY_YM %>" />
				</aui:field-wrapper>
			</aui:fieldset>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:button-row>
		<aui:button type="submit" value="save" />

		<aui:button onClick="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<script type="text/javascript">
	<portlet:namespace />init();

	document.<portlet:namespace />fm.<portlet:namespace />endDateHour.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateMinute.disabled = true;
	document.<portlet:namespace />fm.<portlet:namespace />endDateAmPm.disabled = true;
</script>

<%
if (event != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setWindowState(WindowState.MAXIMIZED);

	portletURL.setParameter("struts_action", "/calendar/view_event");
	portletURL.setParameter("redirect", currentURL);
	portletURL.setParameter("eventId", String.valueOf(event.getEventId()));

	PortalUtil.addPortletBreadcrumbEntry(request, event.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-event"), currentURL);
}
%>