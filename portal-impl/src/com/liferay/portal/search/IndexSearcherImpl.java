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

package com.liferay.portal.search;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.messaging.SearchRequest;

/**
 * <a href="IndexSearcherImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class IndexSearcherImpl implements IndexSearcher {

	public Hits search(long companyId, Query query, int start, int end)
		throws SearchException {

		return search(companyId, query, _DEFAULT_SORT, start, end);
	}

	public Hits search(
			long companyId, Query query, Sort[] sorts, int start, int end)
		throws SearchException {

		try {
			SearchRequest searchRequest = new SearchRequest();

			searchRequest.setCommand(SearchRequest.COMMAND_SEARCH);
			searchRequest.setCompanyId(companyId);
			searchRequest.setQuery(query);
			searchRequest.setSorts(sorts);
			searchRequest.setStart(start);
			searchRequest.setEnd(end);

			Hits hits = (Hits)MessageBusUtil.sendSynchronousMessage(
				DestinationNames.SEARCH_READER, searchRequest);

			return hits;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private static final Sort[] _DEFAULT_SORT = new Sort[] {
		new Sort(Field.MODIFIED, Sort.LONG_TYPE, true)
	};

}