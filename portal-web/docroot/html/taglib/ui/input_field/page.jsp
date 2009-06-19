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

<%@ page import="com.liferay.portal.model.impl.BaseModelImpl" %>

<%@ page import="java.text.Format" %>

<%
String formName = (String)request.getAttribute("liferay-ui:input-field:formName");
String model = (String)request.getAttribute("liferay-ui:input-field:model");
Object bean = request.getAttribute("liferay-ui:input-field:bean");
String field = (String)request.getAttribute("liferay-ui:input-field:field");
String fieldParam = (String)request.getAttribute("liferay-ui:input-field:fieldParam");
Object defaultValue = request.getAttribute("liferay-ui:input-field:defaultValue");
boolean disabled = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:input-field:disabled"));
Format format = (Format)request.getAttribute("liferay-ui:input-field:format");

String type = ModelHintsUtil.getType(model, field);
Map<String, String> hints = ModelHintsUtil.getHints(model, field);
%>

<c:if test="<%= type != null %>">
	<c:choose>
		<c:when test='<%= type.equals("boolean") %>'>

			<%
			boolean defaultBoolean = GetterUtil.DEFAULT_BOOLEAN;

			if (defaultValue != null) {
				defaultBoolean = ((Boolean)defaultValue).booleanValue();
			}
			else {
				if (hints != null) {
					defaultBoolean = GetterUtil.getBoolean(hints.get("default-value"));
				}
			}

			if (fieldParam == null) {
				fieldParam = field;
			}

			boolean value = BeanParamUtil.getBoolean(bean, request, field, defaultBoolean);
			%>

			<liferay-ui:input-checkbox formName="<%= formName %>" param="<%= fieldParam %>" defaultValue="<%= value %>" disabled="<%= disabled %>" />
		</c:when>
		<c:when test='<%= type.equals("Date") %>'>

			<%
			Calendar now = CalendarFactoryUtil.getCalendar(timeZone, locale);

			String timeFormatPattern = ((SimpleDateFormat)(DateFormat.getTimeInstance(DateFormat.SHORT))).toPattern();

			boolean timeFormatAmPm = true;

			if (timeFormatPattern.indexOf("a") == -1) {
				timeFormatAmPm = false;
			}

			if (fieldParam == null) {
				fieldParam = field;
			}

			Calendar cal = (Calendar)defaultValue;

			int month = ParamUtil.getInteger(request, fieldParam + "Month", -1);

			if ((month == -1) && (cal != null)) {
				month = cal.get(Calendar.MONTH);
			}

			boolean monthNullable = false;

			if (hints != null) {
				monthNullable = GetterUtil.getBoolean(hints.get("month-nullable"), monthNullable);
			}

			int day = ParamUtil.getInteger(request, fieldParam + "Day", -1);

			if ((day == -1) && (cal != null)) {
				day = cal.get(Calendar.DATE);
			}

			boolean dayNullable = false;

			if (hints != null) {
				dayNullable = GetterUtil.getBoolean(hints.get("day-nullable"), dayNullable);
			}

			int year = ParamUtil.getInteger(request, fieldParam + "Year", -1);

			if ((year == -1) && (cal != null)) {
				year = cal.get(Calendar.YEAR);
			}

			boolean yearNullable = false;

			if (hints != null) {
				yearNullable = GetterUtil.getBoolean(hints.get("year-nullable"), yearNullable);
			}

			int yearRangeDelta = 5;

			if (hints != null) {
				yearRangeDelta = GetterUtil.getInteger(hints.get("year-range-delta"), yearRangeDelta);
			}

			int yearRangeStart = year - yearRangeDelta;
			int yearRangeEnd = year + yearRangeDelta;

			if (year == -1) {
				yearRangeStart = now.get(Calendar.YEAR) - yearRangeDelta;
				yearRangeEnd = now.get(Calendar.YEAR) + yearRangeDelta;
			}

			boolean yearRangePast = true;

			if (hints != null) {
				yearRangePast = GetterUtil.getBoolean(hints.get("year-range-past"), true);
			}

			if (!yearRangePast) {
				if (yearRangeStart < now.get(Calendar.YEAR)) {
					yearRangeStart = now.get(Calendar.YEAR);
				}

				if (yearRangeEnd < now.get(Calendar.YEAR)) {
					yearRangeEnd = now.get(Calendar.YEAR);
				}
			}

			boolean yearRangeFuture = true;

			if (hints != null) {
				yearRangeFuture = GetterUtil.getBoolean(hints.get("year-range-future"), true);
			}

			if (!yearRangeFuture) {
				if (yearRangeStart > now.get(Calendar.YEAR)) {
					yearRangeStart = now.get(Calendar.YEAR);
				}

				if (yearRangeEnd > now.get(Calendar.YEAR)) {
					yearRangeEnd = now.get(Calendar.YEAR);
				}
			}

			int firstDayOfWeek = Calendar.SUNDAY - 1;

			if (cal != null) {
				firstDayOfWeek = cal.getFirstDayOfWeek() - 1;
			}

			int hour = ParamUtil.getInteger(request, fieldParam + "Hour", -1);

			if ((hour == -1) && (cal != null)) {
				hour = cal.get(Calendar.HOUR_OF_DAY);

				if (timeFormatAmPm) {
					hour = cal.get(Calendar.HOUR);
				}
			}

			int minute = ParamUtil.getInteger(request, fieldParam + "Minute", -1);

			if ((minute == -1) && (cal != null)) {
				minute = cal.get(Calendar.MINUTE);
			}

			int amPm = ParamUtil.getInteger(request, fieldParam + "AmPm", -1);

			if ((amPm == -1) && (cal != null)) {
				amPm = Calendar.AM;

				if (timeFormatAmPm) {
					amPm = cal.get(Calendar.AM_PM);
				}
			}

			boolean showTime = true;

			if (hints != null) {
				showTime = GetterUtil.getBoolean(hints.get("show-time"), showTime);
			}
			%>

			<liferay-ui:input-date
 				formName="<%= formName %>"
				monthParam='<%= fieldParam + "Month" %>'
				monthValue="<%= month %>"
				monthNullable="<%= monthNullable %>"
				dayParam='<%= fieldParam + "Day" %>'
				dayValue="<%= day %>"
				dayNullable="<%= dayNullable %>"
				yearParam='<%= fieldParam + "Year" %>'
				yearValue="<%= year %>"
				yearNullable="<%= yearNullable %>"
				yearRangeStart="<%= yearRangeStart %>"
				yearRangeEnd="<%= yearRangeEnd %>"
				firstDayOfWeek="<%= firstDayOfWeek %>"
				imageInputId='<%= fieldParam + "ImageInputId" %>'
 				disabled="<%= disabled %>"
			/>

			<c:if test="<%= showTime %>">
				&nbsp;

				<liferay-ui:input-time
					hourParam='<%= fieldParam + "Hour" %>'
					hourValue="<%= hour %>"
					minuteParam='<%= fieldParam + "Minute" %>'
					minuteValue="<%= minute %>"
					minuteInterval="<%= 1 %>"
					amPmParam='<%= fieldParam + "AmPm" %>'
					amPmValue="<%= amPm %>"
					disabled="<%= disabled %>"
				/>
			</c:if>
		</c:when>
		<c:when test='<%= type.equals("double") || type.equals("int") || type.equals("String") %>'>

			<%
			String defaultString = GetterUtil.DEFAULT_STRING;

			if (defaultValue != null) {
				defaultString = (String)defaultValue;
			}

			String value = null;

			if (fieldParam == null) {
				fieldParam = field;
			}

			if (type.equals("double")) {
				double doubleValue = BeanParamUtil.getDouble(bean, request, field, GetterUtil.getDouble(defaultString));

				if (format != null) {
					value = format.format(doubleValue);
				}
				else {
					value = String.valueOf(doubleValue);
				}
			}
			else if (type.equals("int")) {
				int intValue = BeanParamUtil.getInteger(bean, request, field, GetterUtil.getInteger(defaultString));

				if (format != null) {
					value = format.format(intValue);
				}
				else {
					value = String.valueOf(intValue);
				}
			}
			else {
				value = BeanParamUtil.getString(bean, request, field, defaultString);

				String httpValue = request.getParameter(fieldParam);

				if (httpValue != null) {
					value = httpValue;
				}
			}

			boolean autoEscape = true;
			boolean localized = false;

			if (hints != null) {
				autoEscape = GetterUtil.getBoolean(hints.get("auto-escape"), true);
				localized = GetterUtil.getBoolean(hints.get("localized"));
			}

			if (autoEscape) {
				value = HtmlUtil.escape(value);
			}

			String displayHeight = ModelHintsConstants.TEXT_DISPLAY_HEIGHT;
			String displayWidth = ModelHintsConstants.TEXT_DISPLAY_WIDTH;
			String maxLength = ModelHintsConstants.TEXT_MAX_LENGTH;
			boolean secret = false;
			boolean upperCase = false;
			boolean checkTab = false;

			if (hints != null) {
				displayHeight = GetterUtil.getString(hints.get("display-height"), displayHeight);
				displayWidth = GetterUtil.getString(hints.get("display-width"), displayWidth);
				maxLength = GetterUtil.getString(hints.get("max-length"), maxLength);
				secret = GetterUtil.getBoolean(hints.get("secret"), secret);
				upperCase = GetterUtil.getBoolean(hints.get("upper-case"), upperCase);
				checkTab = GetterUtil.getBoolean(hints.get("check-tab"), checkTab);
			}

			if (!localized) {
				fieldParam = namespace + fieldParam;
			}
			%>

			<c:choose>
				<c:when test='<%= displayHeight.equals(ModelHintsConstants.TEXT_DISPLAY_HEIGHT) %>'>

					<%
					if (Validator.isNotNull(value)) {
						int maxLengthInt = GetterUtil.getInteger(maxLength);

						if (value.length() > maxLengthInt) {
							value = value.substring(0, maxLengthInt);
						}
					}
					%>

					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized disabled="<%= disabled %>" name="<%= fieldParam %>" xml="<%= BeanPropertiesUtil.getString(bean, field) %>" style='<%= "width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") + "; " + (upperCase ? "text-transform: uppercase;" : "" ) %>' onChange='<%= "Liferay.Util.checkMaxLength(this, " + maxLength + ");" %>' onKeyPress='<%= "Liferay.Util.checkMaxLength(this, " + maxLength + ");" %>' />
						</c:when>
						<c:otherwise>
							<input <%= disabled ? "disabled" : "" %> id="<%= fieldParam %>" name="<%= fieldParam %>" style="width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>; <%= upperCase ? "text-transform: uppercase;" : "" %>" type="<%= secret ? "password" : "text" %>" value="<%= value %>" onChange="Liferay.Util.checkMaxLength(this, <%= maxLength %>);" onKeyPress="Liferay.Util.checkMaxLength(this, <%= maxLength %>);" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="<%= localized %>">
							<liferay-ui:input-localized disabled="<%= disabled %>" name="<%= fieldParam %>" type="textarea" xml="<%= BeanPropertiesUtil.getString(bean, field) %>" style='<%= "height: " + displayHeight + (Validator.isDigit(displayHeight) ? "px" : "" ) + "; " + "width: " + displayWidth + (Validator.isDigit(displayWidth) ? "px" : "") +";" %>' wrap="soft" onChange='<%= "Liferay.Util.checkMaxLength(this, " + maxLength + ");" %>' onKeyDown='<%= (checkTab ? "Liferay.Util.checkTab(this); " : "") + "Liferay.Util.disableEsc();" %>' onKeyPress='<%= "Liferay.Util.checkMaxLength(this," + maxLength +");" %>' />
						</c:when>
						<c:otherwise>
							<textarea <%= disabled ? "disabled" : "" %> id="<%= fieldParam %>" name="<%= fieldParam %>" style="height: <%= displayHeight %><%= Validator.isDigit(displayHeight) ? "px" : "" %>; width: <%= displayWidth %><%= Validator.isDigit(displayWidth) ? "px" : "" %>;" wrap="soft" onChange="Liferay.Util.checkMaxLength(this, <%= maxLength %>);" onKeyDown="<%= checkTab ? "Liferay.Util.checkTab(this); " : "" %> Liferay.Util.disableEsc();" onKeyPress="Liferay.Util.checkMaxLength(this, <%= maxLength %>);"><%= value %></textarea>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
</c:if>