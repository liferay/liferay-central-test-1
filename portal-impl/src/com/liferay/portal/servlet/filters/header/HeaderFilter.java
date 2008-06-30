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

package com.liferay.portal.servlet.filters.header;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="HeaderFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 *
 */
public class HeaderFilter extends BasePortalFilter {

	public void init(FilterConfig config) {
		super.init(config);

		_config = config;
		_timeZone = TimeZone.getTimeZone(_TIME_ZONE);
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain chain)
		throws IOException, ServletException {

		Enumeration<String> enu = _config.getInitParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = _config.getInitParameter(name);

			if (name.equals(_EXPIRES) && Validator.isNumber(value)) {
				int seconds = GetterUtil.getInteger(value);

				Calendar cal = new GregorianCalendar();

				cal.add(Calendar.SECOND, seconds);

				DateFormat dateFormat = new SimpleDateFormat(
					_DATE_FORMAT, Locale.US);

				dateFormat.setTimeZone(_timeZone);

				value = dateFormat.format(cal.getTime());
			}

			// LEP-5895

			boolean addHeader = true;

			if (name.equalsIgnoreCase(HttpHeaders.CACHE_CONTROL)) {
				HttpSession session = request.getSession(false);

				if ((session == null) || session.isNew()) {
					addHeader = false;
				}
			}

			if (addHeader) {
				response.addHeader(name, value);
			}
		}

		processFilter(HeaderFilter.class, request, response, chain);
	}

	private static final String _DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	private static final String _EXPIRES = "Expires";

	private static final String _TIME_ZONE = "GMT";

	private FilterConfig _config;
	private TimeZone _timeZone;

}