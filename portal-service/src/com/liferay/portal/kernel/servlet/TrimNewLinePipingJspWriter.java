/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;

/**
 * @author Shuyang Zhou
 */
public class TrimNewLinePipingJspWriter extends JspWriter {

	public TrimNewLinePipingJspWriter(PrintWriter printWriter) {
		super(NO_BUFFER, false);

		_printWriter = printWriter;
	}

	public TrimNewLinePipingJspWriter(Writer writer) {
		super(NO_BUFFER, false);

		_printWriter = new UnsyncPrintWriter(writer, true);
	}

	public void clear() throws IOException {
		throw new IOException();
	}

	public void clearBuffer() {
	}

	public void close() {
		_printWriter.close();
	}

	public void flush() {
		_printWriter.flush();
	}

	public int getRemaining() {
		return 0;
	}

	public void newLine() {
		if (!_lastNewLine) {
			_printWriter.println();
			_lastNewLine = true;
		}
	}

	public void print(boolean b) {
		_printWriter.print(b);
		_lastNewLine = false;
	}

	public void print(char c) {
		boolean isNewLine = c == CharPool.NEW_LINE || c == CharPool.RETURN;
		if (!_lastNewLine || !isNewLine) {
			_printWriter.print(c);
		}
		if (isNewLine) {
			_lastNewLine = true;
		}
	}

	public void print(char[] charArray) {
		_printWriter.print(charArray);
		_lastNewLine = false;
	}

	public void print(double d) {
		_printWriter.print(d);
		_lastNewLine = false;
	}

	public void print(float f) {
		_printWriter.print(f);
		_lastNewLine = false;
	}

	public void print(int i) {
		_printWriter.print(i);
		_lastNewLine = false;
	}

	public void print(long l) {
		_printWriter.print(l);
		_lastNewLine = false;
	}

	public void print(Object object) {
		_printWriter.print(object);
		_lastNewLine = false;
	}

	public void print(String string) {
		String trim = trim(string);
		if (trim.length() > 0) {
			_printWriter.print(trim);
			_lastNewLine = false;
		}
	}

	public void println() {
		if (!_lastNewLine) {
			_printWriter.println();
			_lastNewLine = true;
		}
	}

	public void println(boolean b) {
		_printWriter.println(b);
		_lastNewLine = true;
	}

	public void println(char c) {
		_printWriter.println(c);
		_lastNewLine = true;
	}

	public void println(char[] charArray) {
		_printWriter.println(charArray);
		_lastNewLine = true;
	}

	public void println(double d) {
		_printWriter.println(d);
		_lastNewLine = true;
	}

	public void println(float f) {
		_printWriter.println(f);
		_lastNewLine = true;
	}

	public void println(int i) {
		_printWriter.println(i);
		_lastNewLine = true;
	}

	public void println(long l) {
		_printWriter.println(l);
		_lastNewLine = true;
	}

	public void println(Object object) {
		_printWriter.println(object);
		_lastNewLine = true;
	}

	public void println(String string) {
		String trim = trim(string);
		if (trim.length() > 0) {
			_printWriter.println(trim);
			_lastNewLine = true;
		}
	}

	public void write(char[] charArray) {
		_printWriter.write(charArray);
		_lastNewLine = false;
	}

	public void write(char[] charArray, int offset, int length) {
		_printWriter.write(charArray, offset, length);
		_lastNewLine = false;
	}

	public void write(int c) {
		boolean isNewLine = c == CharPool.NEW_LINE || c == CharPool.RETURN;
		if (!_lastNewLine || !isNewLine) {
			_printWriter.write(c);
		}
		if (isNewLine) {
			_lastNewLine = true;
		}
	}

	public void write(String string) {
		String trim = trim(string);
		if (trim.length() > 0) {
			_printWriter.write(trim);
			_lastNewLine = false;
		}
	}

	public void write(String string, int offset, int length) {
		String trim = trim(string.substring(offset, offset + length));
		if (trim.length() > 0) {
			_printWriter.write(trim);
			_lastNewLine = false;
		}
	}

	private String trim(String string) {
		int length = string.length();
		int start = length;
		for(int i = 0; i < length; i++) {
			char c = string.charAt(i);
			if (c != CharPool.NEW_LINE && c != CharPool.RETURN) {
				start = i;
				break;
			}
		}
		int end = 0;
		for(int i = length - 1; i >=0 ; i--) {
			char c = string.charAt(i);
			if (c != CharPool.NEW_LINE && c != CharPool.RETURN) {
				end = i + 1;
				break;
			}
		}
		if (end > start) {
			return string.substring(start, end);
		}
		else {
			return StringPool.BLANK;
		}
	}

	private boolean _lastNewLine;
	private PrintWriter _printWriter;

}