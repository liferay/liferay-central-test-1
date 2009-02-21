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
themeDisplay.setIncludeCalendarJs(true);

String randomNamespace = PwdGenerator.getPassword(PwdGenerator.KEY3, 4) + StringPool.UNDERLINE;

if (GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disableNamespace"))) {
	namespace = StringPool.BLANK;
}

String formName = namespace + request.getAttribute("liferay-ui:input-date:formName");
String monthParam = namespace + request.getAttribute("liferay-ui:input-date:monthParam");
int monthValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:monthValue"));
boolean monthNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:monthNullable"));
String dayParam = namespace + request.getAttribute("liferay-ui:input-date:dayParam");
int dayValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:dayValue"));
boolean dayNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:dayNullable"));
String yearParam = namespace + request.getAttribute("liferay-ui:input-date:yearParam");
int yearValue = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearValue"));
boolean yearNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:yearNullable"));
int yearRangeStart = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearRangeStart"));
int yearRangeEnd = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:yearRangeEnd"));
String monthAndYearParam = namespace + request.getAttribute("liferay-ui:input-date:monthAndYearParam");
boolean monthAndYearNullable = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:monthAndYearNullable"));
int firstDayOfWeek = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:input-date:firstDayOfWeek"));
String imageInputId = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-date:imageInputId"));
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-date:disabled"));

if (Validator.isNull(imageInputId)) {
	imageInputId = randomNamespace + "imageInputId";
}
else {
	imageInputId = namespace + imageInputId;
}

String dateFormatPattern = ((SimpleDateFormat)(DateFormat.getDateInstance(DateFormat.SHORT))).toPattern();

boolean dateFormatMDY = true;

if (dateFormatPattern.indexOf("y") == 0) {
	dateFormatMDY = false;
}
%>

<script type="text/javascript">
	if (!Liferay.DatePicker) {
		Liferay.DatePicker = new Expanse.Class({
			initialize: function(options) {
				var instance = this;

				instance._input = jQuery(options.input);
				instance._monthField = jQuery(options.monthField);
				instance._dayField = jQuery(options.dayField);
				instance._yearField = jQuery(options.yearField);
				instance._monthYearField = jQuery(options.monthAndYearField);
				instance._combinedMonthYear = options.combinedMonthYear;
				instance._yearRange = options.yearRange.join(':');
				instance._firstDay = options.firstDay;

				if (!instance._input.is('.disabled')) {
					instance._createDatepicker();
				}
			},

			_beforeShow: function() {
				var instance = this;

				var month = null;
				var day = null;
				var year = null;

				if (!instance._combinedMonthYear) {
					month = instance._monthField.val();
					year = instance._yearField.val();
				}
				else {
					var value = instance._monthYearField.val();

					value = value.split('_');

					month = value[0];
					year = value[1];
				}

				month++;

				day = instance._dayField.val();

				instance._input.val(
					month + '/' + day + '/' + year
				);

				return {};
			},

			_createDatepicker: function() {
				var instance = this;

				instance._input.datepicker(
					{
						buttonImage: '<%= themeDisplay.getPathThemeImages() %>/common/calendar.png',
						buttonImageOnly: true,
						clearText: ' ',
						firstDay: instance._firstDay,
						showOn: 'both',
						showOtherMonths: true,
						showWeeks: true,
						yearRange: instance._yearRange,
						beforeShow: function() {
							instance._beforeShow();
						},
						onSelect: function(date, datepicker) {
							instance._onSelect(date, datepicker);
						}
					}
				);
			},

			_onSelect: function(date, datepicker) {
				var instance = this;

				var day = datepicker.selectedDay;
				var month = datepicker.selectedMonth;
				var year = datepicker.selectedYear;

				if (!instance._combinedMonthYear) {
					instance._monthField.val(month);
					instance._yearField.val(year);
				}
				else {
					var monthYearValue = month + '_' + year;
					instance._monthYearField.val(monthYearValue);
				}

				instance._dayField.val(day);
			}
		});
	}

	jQuery(
		function() {
			new Liferay.DatePicker(
				{
					combinedMonthYear: (!<%= monthAndYearParam.equals(namespace) %>),
					dayField: '#<%= dayParam %>',
					firstDay: <%= firstDayOfWeek %>,
					input: '#<%= imageInputId %>Input',
					monthField: '#<%= monthParam %>',
					monthAndYearField: '#<%= monthAndYearParam %>',
					yearField: '#<%= yearParam %>',
					yearRange: [<%= yearRangeStart %>, <%= yearRangeEnd %>]
				}
			);
		}
	);
</script>

<c:choose>
	<c:when test="<%= monthAndYearParam.equals(namespace) %>">

		<%
		int[] monthIds = CalendarUtil.getMonthIds();
		String[] months = CalendarUtil.getMonths(locale);
		%>

		<c:choose>
			<c:when test="<%= dateFormatMDY %>">
				<%@ include file="select_month.jspf" %>

				<%@ include file="select_day.jspf" %>

				<%@ include file="select_year.jspf" %>
			</c:when>

			<c:otherwise>
				<%@ include file="select_year.jspf" %>

				<%@ include file="select_month.jspf" %>

				<%@ include file="select_day.jspf" %>
			</c:otherwise>
		</c:choose>
	</c:when>

	<c:otherwise>

		<%
		int[] monthIds = CalendarUtil.getMonthIds();
		String[] months = CalendarUtil.getMonths(locale, "MMM");
		%>

		<select <%= disabled ? "disabled" : "" %> id="<%= monthAndYearParam %>" name="<%= monthAndYearParam %>">
			<c:if test="<%= monthAndYearNullable %>">
				<option value=""></option>
			</c:if>

			<%
			for (int i = yearRangeStart; i <= yearRangeEnd; i++) {
				for (int j = 0; j < months.length; j++) {
			%>

					<option <%= (monthValue == monthIds[j]) && (yearValue == i) ? "selected" : "" %> value="<%= monthIds[j] %>_<%= i %>"><%= months[j] %> <%= i %></option>

			<%
				}
			}
			%>

		</select>

		<%@ include file="select_day.jspf" %>
	</c:otherwise>
</c:choose>

<input class="<%= disabled ? "disabled" : "" %>" id="<%= imageInputId %>Input" type="hidden" />