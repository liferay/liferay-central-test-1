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

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.ParallelDispatchedDestination;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;

/**
 * <a href="LuceneSearchEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneSearchEngineImpl implements SearchEngine {

	public LuceneSearchEngineImpl() {
		_searcher = new LuceneIndexSearcherImpl();
		_writer = new LuceneIndexWriterImpl();
		_messageBusListener = true;

		Destination destination = new ParallelDispatchedDestination(
			DestinationNames.SEARCH_INDEX_WRITER);

		MessageBusUtil.addDestination(destination);

		destination.register(new LuceneIndexWriterMessageListener());
	}

	public String getName() {
		return _NAME;
	}

	public IndexSearcher getSearcher() {
		return _searcher;
	}

	public IndexWriter getWriter() {
		return _writer;
	}

	public boolean isIndexReadOnly() {
		return LuceneUtil.INDEX_READ_ONLY;
	}

	public boolean isMessageBusListener() {
		return _messageBusListener;
	}

	private static final String _NAME = "LUCENE";

	private IndexSearcher _searcher;
	private IndexWriter _writer;
	private boolean _messageBusListener;

}