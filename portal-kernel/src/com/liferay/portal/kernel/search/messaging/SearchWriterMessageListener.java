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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;

/**
 * <a href="SearchWriterMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SearchWriterMessageListener
	extends BaseSearchEngineMessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		Object payload = message.getPayload();

		if (!(payload instanceof SearchRequest)) {
			return;
		}

		SearchRequest searchRequest = (SearchRequest)payload;

		SearchEngineCommand searchEngineCommand =
			searchRequest.getSearchEngineCommand();

		long companyId = searchRequest.getCompanyId();
		String id = searchRequest.getId();
		Document doc = searchRequest.getDocument();

		IndexWriter indexWriter = searchEngine.getWriter();

		if (searchEngineCommand.equals(SearchEngineCommand.ADD)) {
			indexWriter.addDocument(companyId, doc);
		}
		else if (searchEngineCommand.equals(SearchEngineCommand.DELETE)) {
			indexWriter.deleteDocument(companyId, id);
		}
		else if (searchEngineCommand.equals(
					SearchEngineCommand.DELETE_PORTLET_DOCUMENTS)) {

			indexWriter.deletePortletDocuments(companyId, id);
		}
		else if (searchEngineCommand.equals(SearchEngineCommand.UPDATE)) {
			indexWriter.updateDocument(companyId, id, doc);
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(SearchWriterMessageListener.class);

}