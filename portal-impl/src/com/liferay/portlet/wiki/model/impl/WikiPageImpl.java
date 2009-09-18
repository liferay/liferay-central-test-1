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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WikiPageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiPageImpl extends WikiPageModelImpl implements WikiPage {

	public WikiPageImpl() {
	}

	public String getAttachmentsDir() {
		if (_attachmentDirs == null) {
			_attachmentDirs = "wiki/" + getResourcePrimKey();
		}

		return _attachmentDirs;
	}

	public String[] getAttachmentsFiles()
		throws PortalException, SystemException {

		String[] fileNames = new String[0];

		try {
			fileNames = DLServiceUtil.getFileNames(
				getCompanyId(), CompanyConstants.SYSTEM, getAttachmentsDir());
		}
		catch (NoSuchDirectoryException nsde) {
		}

		return fileNames;
	}

	public List<WikiPage> getChildPages() {
		List<WikiPage> pages = null;

		try {
			pages = WikiPageLocalServiceUtil.getChildren(
				getNodeId(), true, getTitle());
		}
		catch (Exception e) {
			pages = new ArrayList<WikiPage>();

			_log.error(e);
		}

		return pages;
	}

	public WikiNode getNode() {
		WikiNode node = null;

		try {
			node = WikiNodeLocalServiceUtil.getNode(getNodeId());
		}
		catch (Exception e) {
			node = new WikiNodeImpl();

			_log.error(e);
		}

		return node;
	}

	public WikiPage getParentPage() {
		if (Validator.isNull(getParentTitle())) {
			return null;
		}

		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(
				getNodeId(), getParentTitle());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return page;
	}

	public List<WikiPage> getParentPages() {
		List<WikiPage> parentPages = new ArrayList<WikiPage>();

		WikiPage parentPage = getParentPage();

		if (parentPage != null) {
			parentPages.addAll(parentPage.getParentPages());
			parentPages.add(parentPage);
		}

		return parentPages;
	}

	public WikiPage getRedirectPage() {
		if (Validator.isNull(getRedirectTitle())) {
			return null;
		}

		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(
				getNodeId(), getRedirectTitle());
		}
		catch (Exception e) {
			_log.error(e);
		}

		return page;
	}

	public void setAttachmentsDir(String attachmentsDir) {
		_attachmentDirs = attachmentsDir;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageImpl.class);

	private String _attachmentDirs;

}