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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;

import java.io.IOException;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.SortField;

/**
 * <a href="LuceneIndexSearcherImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneIndexSearcherImpl implements IndexSearcher {

	public Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return search(companyId, query, null, start, end);
	}

	public Hits search(
			long companyId, Query query, Sort sort, int start, int end)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug("Query: " + query);
		}

		Hits hits = null;

		org.apache.lucene.search.IndexSearcher searcher = null;

		try {
			searcher = LuceneUtil.getSearcher(companyId);

			org.apache.lucene.search.Sort luceneSort = null;

			if (sort != null) {
				luceneSort = new org.apache.lucene.search.Sort(
					new SortField(sort.getFieldName(), sort.isReverse()));
			}

			org.apache.lucene.search.Hits luceneHits = searcher.search(
				QueryTranslator.translate(query), luceneSort);

			hits = subset(luceneHits, start, end);
		}
		catch (RuntimeException re) {

			// Trying to sort on a field when there are no results throws a
			// RuntimeException that should not be rethrown

			String msg = GetterUtil.getString(re.getMessage());

			if (!msg.endsWith("does not appear to be indexed")) {
				throw re;
			}
		}
		catch (Exception e) {
			if (e instanceof BooleanQuery.TooManyClauses ||
				e instanceof ParseException) {

				_log.error("Query: " + query, e);

				return new HitsImpl();
			}
			else {
				throw new SearchException(e);
			}
		}
		finally {
			try {
				if (searcher != null) {
					searcher.close();
				}
			}
			catch (IOException ioe) {
				throw new SearchException(ioe);
			}
		}

		return hits;
	}

	protected DocumentImpl getDocument(
		org.apache.lucene.document.Document oldDoc) {

		DocumentImpl newDoc = new DocumentImpl();

		List<org.apache.lucene.document.Field> oldFields = oldDoc.getFields();

		for (org.apache.lucene.document.Field oldField : oldFields) {
			String[] values = oldDoc.getValues(oldField.name());

			if ((values != null) && (values.length > 1)) {
				Field newField = new Field(
					oldField.name(), values, oldField.isTokenized());

				newDoc.add(newField);
			}
			else {
				Field newField = new Field(
					oldField.name(), oldField.stringValue(),
					oldField.isTokenized());

				newDoc.add(newField);
			}
		}

		return newDoc;
	}

	protected Hits subset(
			org.apache.lucene.search.Hits luceneHits, int start, int end)
		throws IOException {

		int length = luceneHits.length();

		if ((start == SearchEngineUtil.ALL_POS) &&
			(end == SearchEngineUtil.ALL_POS)) {

			start = 0;
			end = length;
		}

		long startTime = System.currentTimeMillis();

		Hits subset = new HitsImpl();

		if ((start > - 1) && (start <= end)) {
			if (end > length) {
				end = length;
			}

			int subsetTotal = end - start;

			Document[] subsetDocs = new DocumentImpl[subsetTotal];
			float[] subsetScores = new float[subsetTotal];

			int j = 0;

			for (int i = start; i < end; i++, j++) {
				subsetDocs[j] = getDocument(luceneHits.doc(i));
				subsetScores[j] = luceneHits.score(i);
			}

			subset.setLength(length);
			subset.setDocs(subsetDocs);
			subset.setScores(subsetScores);
			subset.setStart(startTime);

			float searchTime =
				(float)(System.currentTimeMillis() - startTime) / Time.SECOND;

			subset.setSearchTime(searchTime);
		}

		return subset;
	}

	private static Log _log = LogFactory.getLog(LuceneIndexSearcherImpl.class);

}