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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.service.impl.DLServiceImpl;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties, String[] tagsEntries)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName, properties,
			tagsEntries);

		SearchEngineUtil.addDocument(companyId, doc);
	}

	public static void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws SearchException {

		SearchEngineUtil.deleteDocument(companyId, getFileUID(
			portletId, repositoryId, fileName));
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName)
		throws SearchException {

		try {
			DLFileEntry fileEntry = null;

			try {
				fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
					repositoryId, fileName);
			}
			catch (NoSuchFileEntryException nsfe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"File " + fileName + " in repository " +
							repositoryId + " exists in the JCR but does " +
								"not exist in the database");
				}

				return null;
			}

			StringBuilder sb = new StringBuilder();

			sb.append(fileEntry.getTitle());
			sb.append(StringPool.SPACE);
			sb.append(fileEntry.getDescription());
			sb.append(StringPool.SPACE);

			Properties extraSettingsProps =
				fileEntry.getExtraSettingsProperties();

			Iterator<Map.Entry<Object, Object>> itr =
				extraSettingsProps.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<Object, Object> entry = itr.next();

				String value = GetterUtil.getString((String)entry.getValue());

				sb.append(value);
			}

			String properties = sb.toString();

			String[] tagsEntries = TagsEntryLocalServiceUtil.getEntryNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			return getFileDocument(
				companyId, portletId, groupId, repositoryId, fileName,
				properties, tagsEntries);
		}
		catch (PortalException pe) {
			throw new SearchException(pe.getMessage());
		}
		catch (SystemException se) {
			throw new SearchException(se.getMessage());
		}
	}

	public static Document getFileDocument(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties, String[] tagsEntries)
		throws SearchException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing document " + companyId + " " + portletId + " " +
					groupId + " " + repositoryId + " " + fileName);
		}

		String fileExt = StringPool.BLANK;

		int fileExtVersionPos = fileName.indexOf(DLServiceImpl.VERSION);

		if (fileExtVersionPos != -1) {
			int fileExtPos = fileName.lastIndexOf(
				StringPool.PERIOD, fileExtVersionPos);

			if (fileExtPos != -1) {
				fileExt = fileName.substring(fileExtPos, fileExtVersionPos);
			}
		}
		else {
			int fileExtPos = fileName.lastIndexOf(StringPool.PERIOD);

			if (fileExtPos != -1) {
				fileExt = fileName.substring(fileExtPos, fileName.length());
			}
		}

		InputStream is = null;

		try {
			Hook hook = HookFactory.getInstance();

			is = hook.getFileAsStream(companyId, repositoryId, fileName);
		}
		catch (Exception e) {
		}

		if (is == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Document " + companyId + " " + portletId + " " + groupId +
						" " + repositoryId + " " + fileName +
							" does not have any content");
			}

			return null;
		}

		Document doc = new DocumentImpl();

		doc.addUID(portletId, repositoryId, fileName);

		doc.addModifiedDate();

		doc.addKeyword(Field.COMPANY_ID, companyId);
		doc.addKeyword(Field.PORTLET_ID, portletId);
		doc.addKeyword(Field.GROUP_ID, groupId);

		try {
			doc.addFile(Field.CONTENT, is, fileExt);
		}
		catch (IOException ioe) {
			throw new SearchException(
				"Cannot extract text from file" + companyId + " " + portletId +
					" " + groupId + " " + repositoryId + " " + fileName);
		}

		doc.addText(Field.PROPERTIES, properties);
		doc.addKeyword(Field.TAGS_ENTRIES, tagsEntries);

		doc.addKeyword("repositoryId", repositoryId);
		doc.addKeyword("path", fileName);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + companyId + " " + portletId + " " + groupId +
					" " + repositoryId + " " + fileName +
						" indexed successfully");
		}

		return doc;
	}

	public static String getFileUID(
			String portletId, long repositoryId, String fileName) {
		Document doc = new DocumentImpl();

		doc.addUID(portletId, repositoryId, fileName);

		return doc.get(Field.UID);
	}

	public static void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String properties, String[] tagsEntries)
		throws SearchException {

		Document doc = getFileDocument(
			companyId, portletId, groupId, repositoryId, fileName, properties,
			tagsEntries);

		SearchEngineUtil.updateDocument(companyId, doc.get(Field.UID), doc);
	}

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		return null;
	}

	public void reIndex(String className, long classPK) {
	}

	public void reIndex(String[] ids) throws SearchException {
		if (PropsValues.INDEX_READ_ONLY) {
			return;
		}

		Hook hook = HookFactory.getInstance();

		hook.reIndex(ids);
	}

	private static final String[] _CLASS_NAMES = new String[0];

	private static Log _log = LogFactory.getLog(Indexer.class);

}