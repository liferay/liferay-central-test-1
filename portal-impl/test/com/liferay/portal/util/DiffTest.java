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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="DiffTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class DiffTest extends BaseTestCase {

	public void testOne() {
		StringReader reader1 = new StringReader("liferay");
		StringReader reader2 = new StringReader("liferay");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testTwo() {
		StringReader reader1 = new StringReader("liferay");
		StringReader reader2 = new StringReader("LifeRay");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		List<String> changedLines = new ArrayList<String>();

		changedLines.add(
			Diff.OPEN_DEL + "l" + Diff.CLOSE_DEL + "ife" + Diff.OPEN_DEL + "r" +
				Diff.CLOSE_DEL + "ay");

		expectedSource.add(new DiffResult(0, changedLines));

		changedLines = new ArrayList<String>();

		changedLines.add(
			Diff.OPEN_INS + "L" + Diff.CLOSE_INS + "ife" + Diff.OPEN_INS + "R" +
				Diff.CLOSE_INS + "ay");

		expectedTarget.add(new DiffResult(0, changedLines));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testThree() {
		StringReader reader1 = new StringReader("aaa");
		StringReader reader2 = new StringReader("bbb");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "bbb" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "aaa" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testFour() {
		StringReader reader1 = new StringReader("rahab");
		StringReader reader2 = new StringReader("boaz");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "boaz" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "rahab" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testFive() {
		StringReader reader1 = new StringReader("aaa\nbbb");
		StringReader reader2 = new StringReader("ccc\naaa");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		List<String> changedLines = new ArrayList<String>();

		changedLines.add("aaa");
		changedLines.add(Diff.OPEN_DEL + "bbb" + Diff.CLOSE_DEL);

		expectedSource.add(new DiffResult(1, changedLines));

		changedLines = new ArrayList<String>();

		changedLines.add("aaa");
		changedLines.add(Diff.CONTEXT_LINE);

		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testSix() {
		StringReader reader1 = new StringReader("ccc\naaa");
		StringReader reader2 = new StringReader("aaa\nbbb");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "ccc" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<String> changedLines = new ArrayList<String>();

		changedLines.add("aaa");
		changedLines.add(Diff.CONTEXT_LINE);

		expectedSource.add(new DiffResult(1, changedLines));

		changedLines = new ArrayList<String>();

		changedLines.add("aaa");
		changedLines.add(Diff.OPEN_INS + "bbb" + Diff.CLOSE_INS);

		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testSeven() {
		StringReader reader1 = new StringReader("ccc\naaa\nbbe");
		StringReader reader2 = new StringReader("aaa\nbbb");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		List<String> changedLines = new ArrayList<String>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "ccc" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		changedLines = new ArrayList<String>();

		changedLines.add("bb" + Diff.OPEN_DEL + "e" + Diff.CLOSE_DEL);
		expectedSource.add(new DiffResult(2, changedLines));

		changedLines = new ArrayList<String>();

		changedLines.add("bb" + Diff.OPEN_INS + "b" + Diff.CLOSE_INS);
		expectedTarget.add(new DiffResult(1, changedLines));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testEight() {
		StringReader reader1 = new StringReader("add\nbbb\nccc");
		StringReader reader2 = new StringReader("bbb\nccc\naee");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "add" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<String> changedLines = new ArrayList<String>();

		changedLines.add("bbb");
		changedLines.add("ccc");
		changedLines.add(Diff.CONTEXT_LINE);
		expectedSource.add(new DiffResult(2, changedLines));

		changedLines = new ArrayList<String>();

		changedLines.add("bbb");
		changedLines.add("ccc");
		changedLines.add(Diff.OPEN_INS + "aee" + Diff.CLOSE_INS);
		expectedTarget.add(new DiffResult(2, changedLines));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testNine() {
		StringReader reader1 = new StringReader("abcd");
		StringReader reader2 = new StringReader("abcdee");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, "abcd"));

		expectedTarget.add(
			new DiffResult(
				0, "abcd" + Diff.OPEN_INS + "ee" + Diff.CLOSE_INS));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testTen() {
		StringReader reader1 = new StringReader("abcd");
		StringReader reader2 = new StringReader("abcdeee");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "abcdeee" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(0, Diff.OPEN_DEL + "abcd" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(0, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testEleven() {
		StringReader reader1 = new StringReader("aaa\nbbb\nfff");
		StringReader reader2 = new StringReader("ccc\nada\nbeb");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(new DiffResult(0, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(0, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		expectedSource.add(
			new DiffResult(
				0, "a" + Diff.OPEN_DEL + "a" + Diff.CLOSE_DEL + "a"));

		expectedTarget.add(
			new DiffResult(
				1, "a" + Diff.OPEN_INS + "d" + Diff.CLOSE_INS + "a"));

		expectedSource.add(
			new DiffResult(
				1, "b" + Diff.OPEN_DEL + "b" + Diff.CLOSE_DEL + "b"));

		expectedTarget.add(
			new DiffResult(
				2, "b" + Diff.OPEN_INS + "e" + Diff.CLOSE_INS + "b"));

		expectedSource.add(
			new DiffResult(2, Diff.OPEN_DEL + "fff" + Diff.CLOSE_DEL));

		expectedTarget.add(new DiffResult(2, Diff.CONTEXT_LINE));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

	public void testTwelve() {
		StringReader reader1 = new StringReader("ada");
		StringReader reader2 = new StringReader("aaa\nccc");

		List<DiffResult> expectedSource = new ArrayList<DiffResult>();
		List<DiffResult> expectedTarget = new ArrayList<DiffResult>();

		expectedSource.add(
			new DiffResult(
				0, "a" + Diff.OPEN_DEL + "d" + Diff.CLOSE_DEL + "a"));

		expectedTarget.add(
			new DiffResult(
				0, "a" + Diff.OPEN_INS + "a" + Diff.CLOSE_INS + "a"));

		expectedSource.add(new DiffResult(1, Diff.CONTEXT_LINE));

		expectedTarget.add(
			new DiffResult(1, Diff.OPEN_INS + "ccc" + Diff.CLOSE_INS));

		List<DiffResult>[] actual = DiffUtil.diff(reader1, reader2);

		assertEquals(expectedSource, actual[0]);
		assertEquals(expectedTarget, actual[1]);
	}

}