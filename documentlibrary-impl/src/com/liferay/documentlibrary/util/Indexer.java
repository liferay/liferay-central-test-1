/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.service.jms.IndexProducer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;

import java.io.IOException;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "addFile",
				new Object[] {
					new LongWrapper(companyId), portletId,
					new LongWrapper(groupId), new LongWrapper(repositoryId),
					fileName
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "addFile",
				new Object[] {
					new LongWrapper(companyId), portletId,
					new LongWrapper(groupId), new LongWrapper(repositoryId),
					fileName, properties
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "deleteFile",
				new Object[] {
					new LongWrapper(companyId), portletId,
					new LongWrapper(repositoryId), fileName
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "updateFile",
				new Object[] {
					new LongWrapper(companyId), portletId,
					new LongWrapper(groupId), new LongWrapper(repositoryId),
					fileName, properties
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public DocumentSummary getDocumentSummary(
		Document doc, PortletURL portletURL) {

		return null;
	}

	public void reIndex(String[] ids) throws SearchException {
		IndexerImpl.reIndex(ids);
	}

	private static Log _log = LogFactory.getLog(Indexer.class);

}