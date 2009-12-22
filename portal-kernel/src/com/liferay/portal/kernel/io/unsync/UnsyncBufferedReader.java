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

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.Reader;

/**
 * <a href="UnsyncBufferedReader.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.BufferedReader}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Mark Reinhold
 * @author Shuyang Zhou
 * @see java.io.BufferedReader
 */
public class UnsyncBufferedReader extends Reader {

	private Reader in;
	private char cb[];
	private int nChars, nextChar;
	private static final int INVALIDATED = -2;
	private static final int UNMARKED = -1;
	private int markedChar = UNMARKED;
	private int readAheadLimit = 0; /* Valid only when markedChar > 0 */

	/** If the next character is a line feed, skip it */
	private boolean skipLF = false;
	/** The skipLF flag when the mark was set */
	private boolean markedSkipLF = false;
	private static int defaultCharBufferSize = 8192;
	private static int defaultExpectedLineLength = 80;

	/**
	 * @see java.io.BufferedReader#BufferedReader(java.io.Reader, int)
	 */
	public UnsyncBufferedReader(Reader in, int sz) {
		super(in);
		if (sz <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		this.in = in;
		cb = new char[sz];
		nextChar = nChars = 0;
	}

	/**
	 * @see java.io.BufferedReader#BufferedReader(java.io.Reader)
	 */
	public UnsyncBufferedReader(Reader in) {
		this(in, defaultCharBufferSize);
	}

	/**
	 * @see java.io.BufferedReader#ensureOpen()
	 */
	private void ensureOpen() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
	}

	/**
	 * @see java.io.BufferedReader#fill()
	 */
	private void fill() throws IOException {
		int dst;
		if (markedChar <= UNMARKED) {
			/* No mark */
			dst = 0;
		}
		else {
			/* Marked */
			int delta = nextChar - markedChar;
			if (delta >= readAheadLimit) {
				/* Gone past read-ahead limit: Invalidate mark */
				markedChar = INVALIDATED;
				readAheadLimit = 0;
				dst = 0;
			}
			else {
				if (readAheadLimit <= cb.length) {
					/* Shuffle in the current buffer */
					System.arraycopy(cb, markedChar, cb, 0, delta);
					markedChar = 0;
					dst = delta;
				}
				else {
					/* Reallocate buffer to accommodate read-ahead limit */
					char ncb[] = new char[readAheadLimit];
					System.arraycopy(cb, markedChar, ncb, 0, delta);
					cb = ncb;
					markedChar = 0;
					dst = delta;
				}
				nextChar = nChars = delta;
			}
		}

		int n;
		do {
			n = in.read(cb, dst, cb.length - dst);
		} while (n == 0);
		if (n > 0) {
			nChars = dst + n;
			nextChar = dst;
		}
	}

	/**
	 * @see java.io.BufferedReader#read()
	 */
	public int read() throws IOException {
		ensureOpen();
		for (;;) {
			if (nextChar >= nChars) {
				fill();
				if (nextChar >= nChars) {
					return -1;
				}
			}
			if (skipLF) {
				skipLF = false;
				if (cb[nextChar] == '\n') {
					nextChar++;
					continue;
				}
			}
			return cb[nextChar++];
		}
	}

	/**
	 * @see java.io.BufferedReader#read1(char[], int, int)
	 */
	private int read1(char[] cbuf, int off, int len) throws IOException {
		if (nextChar >= nChars) {
			/* If the requested length is at least as large as the buffer, and
			if there is no mark/reset activity, and if line feeds are not
			being skipped, do not bother to copy the characters into the
			local buffer.  In this way buffered streams will cascade
			harmlessly. */
			if (len >= cb.length && markedChar <= UNMARKED && !skipLF) {
				return in.read(cbuf, off, len);
			}
			fill();
		}
		if (nextChar >= nChars) {
			return -1;
		}
		if (skipLF) {
			skipLF = false;
			if (cb[nextChar] == '\n') {
				nextChar++;
				if (nextChar >= nChars) {
					fill();
				}
				if (nextChar >= nChars) {
					return -1;
				}
			}
		}
		int n = Math.min(len, nChars - nextChar);
		System.arraycopy(cb, nextChar, cbuf, off, n);
		nextChar += n;
		return n;
	}

	/**
	 * @see java.io.BufferedReader#read(char[], int, int)
	 */
	public int read(char cbuf[], int off, int len) throws IOException {
		ensureOpen();
		if ((off < 0) || (off > cbuf.length) || (len < 0)
			|| ((off + len) > cbuf.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return 0;
		}

		int n = read1(cbuf, off, len);
		if (n <= 0) {
			return n;
		}
		while ((n < len) && in.ready()) {
			int n1 = read1(cbuf, off + n, len - n);
			if (n1 <= 0) {
				break;
			}
			n += n1;

		}
		return n;

	}

	/**
	 * @see java.io.BufferedReader#readLine(boolean)
	 */
	String readLine(boolean ignoreLF) throws IOException {
		StringBuffer s = null;
		int startChar;
		ensureOpen();
		boolean omitLF = ignoreLF || skipLF;

		bufferLoop:
		for (;;) {

			if (nextChar >= nChars) {
				fill();
			}
			if (nextChar >= nChars) { /* EOF */
				if (s != null && s.length() > 0) {
					return s.toString();
				}
				else {
					return null;
				}
			}
			boolean eol = false;
			char c = 0;
			int i;

			/* Skip a leftover '\n', if necessary */
			if (omitLF && (cb[nextChar] == '\n')) {
				nextChar++;
			}
			skipLF = false;
			omitLF = false;

			charLoop:
			for (i = nextChar; i < nChars; i++) {
				c = cb[i];
				if ((c == '\n') || (c == '\r')) {
					eol = true;
					break charLoop;
				}
			}

			startChar = nextChar;
			nextChar = i;

			if (eol) {
				String str;
				if (s == null) {
					str = new String(cb, startChar, i - startChar);
				}
				else {
					s.append(cb, startChar, i - startChar);
					str = s.toString();
				}
				nextChar++;
				if (c == '\r') {
					skipLF = true;
				}
				return str;
			}

			if (s == null) {
				s = new StringBuffer(defaultExpectedLineLength);
			}
			s.append(cb, startChar, i - startChar);
		}
	}

	/**
	 * @see java.io.BufferedReader#readLine()
	 */
	public String readLine() throws IOException {
		return readLine(false);
	}

	/**
	 * @see java.io.BufferedReader#skip(long)
	 */
	public long skip(long n) throws IOException {
		if (n < 0L) {
			throw new IllegalArgumentException("skip value is negative");
		}
		ensureOpen();
		long r = n;
		while (r > 0) {
			if (nextChar >= nChars) {
				fill();
			}
			if (nextChar >= nChars) /* EOF */ {
				break;
			}
			if (skipLF) {
				skipLF = false;
				if (cb[nextChar] == '\n') {
					nextChar++;
				}
			}
			long d = nChars - nextChar;
			if (r <= d) {
				nextChar += r;
				r = 0;
				break;
			}
			else {
				r -= d;
				nextChar = nChars;
			}
		}
		return n - r;
	}

	/**
	 * @see java.io.BufferedReader#ready()
	 */
	public boolean ready() throws IOException {
		ensureOpen();

		/*
		 * If newline needs to be skipped and the next char to be read
		 * is a newline character, then just skip it right away.
		 */
		if (skipLF) {
			/* Note that in.ready() will return true if and only if the next
			 * read on the stream will not block.
			 */
			if (nextChar >= nChars && in.ready()) {
				fill();
			}
			if (nextChar < nChars) {
				if (cb[nextChar] == '\n') {
					nextChar++;
				}
				skipLF = false;
			}
		}
		return (nextChar < nChars) || in.ready();
	}

	/**
	 * @see java.io.BufferedReader#markSupported()
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * @see java.io.BufferedReader#mark(int)
	 */
	public void mark(int readAheadLimit) throws IOException {
		if (readAheadLimit < 0) {
			throw new IllegalArgumentException("Read-ahead limit < 0");
		}
		ensureOpen();
		this.readAheadLimit = readAheadLimit;
		markedChar = nextChar;
		markedSkipLF = skipLF;
	}

	/**
	 * @see java.io.BufferedReader#reset()
	 */
	public void reset() throws IOException {
		ensureOpen();
		if (markedChar < 0) {
			throw new IOException((markedChar == INVALIDATED)
				? "Mark invalid"
				: "Stream not marked");
		}
		nextChar = markedChar;
		skipLF = markedSkipLF;
	}

	public void close() throws IOException {
		if (in == null) {
			return;
		}
		in.close();
		in = null;
		cb = null;
	}

}