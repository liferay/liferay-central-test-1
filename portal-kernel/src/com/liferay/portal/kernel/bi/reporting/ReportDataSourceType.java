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

package com.liferay.portal.kernel.bi.reporting;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="ReportDataSourceType.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Gavin Wan
 */
public enum ReportDataSourceType {

	JDBCDataSource("JDBCDataSource"), JRCsvDataSource("JRCsvDataSource"),
		JREmptyDataSource("JREmptyDataSource"), JRXmlDataSource(
			"JRXmlDataSource"), PortalDataSource("PortalDataSource");

	public static ReportDataSourceType parse(String value) {

		ReportDataSourceType format = _reportDataSourceType.get(value);

		if (format != null) {
			return format;
		}

		if (JDBCDataSource.toString().equalsIgnoreCase(value)) {
			return JDBCDataSource;
		}
		else if (JRCsvDataSource.toString().equalsIgnoreCase(value)) {
			return JRCsvDataSource;
		}
		else if (JREmptyDataSource.toString().equalsIgnoreCase(value)) {
			return JREmptyDataSource;
		}
		else if (JRXmlDataSource.toString().equalsIgnoreCase(value)) {
			return JRXmlDataSource;
		}
		else if (PortalDataSource.toString().equalsIgnoreCase(value)) {
			return PortalDataSource;
		}
		else {
			throw new IllegalArgumentException("Invalid format " + value);
		}
	}

	public String toString() {

		return _value;
	}

	private ReportDataSourceType(String value) {

		_value = value;
	}

	private static final Map<String, ReportDataSourceType> 
		_reportDataSourceType = new HashMap<String, ReportDataSourceType>();

	static {
		_reportDataSourceType.put(JDBCDataSource.toString(), JDBCDataSource);
		_reportDataSourceType.put(JRCsvDataSource.toString(), JRCsvDataSource);
		_reportDataSourceType.put(
			JREmptyDataSource.toString(), JREmptyDataSource);
		_reportDataSourceType.put(JRXmlDataSource.toString(), JRXmlDataSource);
		_reportDataSourceType
			.put(PortalDataSource.toString(), PortalDataSource);
	}

	private String _value;

}
