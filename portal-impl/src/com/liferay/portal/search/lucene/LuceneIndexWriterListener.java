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

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.IndexWriterRequestMessage;
import com.liferay.util.JSONUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LuceneIndexWriterListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneIndexWriterListener implements MessageListener {

	public void receive(String message) {
		try {
			IndexWriterRequestMessage iwrm =
				(IndexWriterRequestMessage)JSONUtil.deserialize(message);

			String command = iwrm.getCommand();

			if (command.equals(IndexWriterRequestMessage.ADD)) {
				LuceneSearchEngineUtil.addDocument(
					iwrm.getCompanyId(), iwrm.getDocument());
			}
			else if (command.equals(IndexWriterRequestMessage.DELETE)) {
				LuceneSearchEngineUtil.deleteDocument(
					iwrm.getCompanyId(), iwrm.getUid());
			}
			else if (command.equals(IndexWriterRequestMessage.UPDATE)) {
				LuceneSearchEngineUtil.updateDocument(
					iwrm.getCompanyId(), iwrm.getUid(), iwrm.getDocument());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log =
		LogFactory.getLog(LuceneIndexWriterListener.class);

}