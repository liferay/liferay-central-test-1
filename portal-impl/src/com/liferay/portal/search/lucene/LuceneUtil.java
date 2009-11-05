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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * <a href="LuceneUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 */
public class LuceneUtil {

	public static void addDate(Document doc, String field, Date value) {
		doc.add(LuceneFields.getDate(field, value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		_lucene.addExactTerm(booleanQuery, field, value);
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addRequiredTerm(booleanQuery, field, value, false);
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		_lucene.addRequiredTerm(booleanQuery, field, value, like);
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, long value)
		throws ParseException {

		addTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, String value)
		throws ParseException {

		addTerm(booleanQuery, field, value, false);
	}

	public static void addTerm(
			BooleanQuery booleanQuery, String field, String value,
			boolean like)
		throws ParseException {

		_lucene.addTerm(booleanQuery, field, value, like);
	}

	public static void checkLuceneDir(long companyId) {
		_lucene.checkLuceneDir(companyId);
	}

	public static void delete(long companyId) {
		_lucene.delete(companyId);
	}

	public static void deleteDocuments(long companyId, Term term)
		throws IOException {

		_lucene.deleteDocuments(companyId, term);
	}

	public static Analyzer getAnalyzer() {
		return _lucene.getAnalyzer();
	}

	public static FSDirectory getDirectory(String path)
		throws IOException {

		return _lucene.getDirectory(path);
	}

	public static Directory getLuceneDir(long companyId) {
		return _lucene.getLuceneDir(companyId);
	}

	public static String[] getQueryTerms(Query query) {
		return _lucene.getQueryTerms(query);
	}

	public static IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		return _lucene.getSearcher(companyId, readOnly);
	}

	public static String getSnippet(Query query, String field, String s)
		throws IOException {

		return getSnippet(
			query, field, s, 3, 80, "...", StringPool.BLANK, StringPool.BLANK);
	}

	public static String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, String preTag,
			String postTag)
		throws IOException {

		return _lucene.getSnippet(
			query, field, s, maxNumFragments, fragmentLength, fragmentSuffix,
			preTag, postTag);
	}

	public static void write(long companyId, Document document)
		throws IOException {

		_lucene.write(companyId, document);
	}

	public void setLucene(Lucene lucene) {
		_lucene = lucene;
	}

	private static Lucene _lucene;

}