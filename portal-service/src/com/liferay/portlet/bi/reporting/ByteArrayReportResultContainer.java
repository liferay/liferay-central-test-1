/*
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

package com.liferay.portlet.bi.reporting;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * <a href="ByteArrayResultHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class ByteArrayReportResultContainer
	implements ReportResultContainer, Serializable {
	public static final int DEFAULT_INITIAL_CAPCITY = 15360;

	public ByteArrayReportResultContainer() {
		this(null, DEFAULT_INITIAL_CAPCITY);
	}


	public ByteArrayReportResultContainer(String reportName) {
		this(reportName, DEFAULT_INITIAL_CAPCITY);
	}

	public ByteArrayReportResultContainer(String reportName, int initialCapacity) {
		_reportName = reportName;
		_initialCapacity = initialCapacity;
	}

	public ReportResultContainer clone(String reportName) {
		return new ByteArrayReportResultContainer(reportName, _initialCapacity);
	}

	public OutputStream getOutputStream() {
		if (_outputStream == null) {
			_outputStream =
				new ByteArrayOutputStream(_initialCapacity);
		}
		return _outputStream;
	}

	public String getReportName() {
		return _reportName;
	}

	public ReportGenerationException getReportGenerationException() {
		return _reportException;
	}

	public byte[] getResults() {
		return _outputStream.toByteArray();
	}

	public boolean hasError() {
		return (_reportException != null);
	}

	public void setReportGenerationException(ReportGenerationException e) {
		_reportException = e;
	}

	private ByteArrayOutputStream _outputStream;
	private int _initialCapacity;
	private String _reportName;
	private ReportGenerationException _reportException;
}
