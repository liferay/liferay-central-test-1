/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;

import java.io.InputStream;

/**
 * <a href="DLFileEntryResourceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryResourceImpl extends BaseResourceImpl {

	public DLFileEntryResourceImpl(
		WebDAVRequest webDavRequest, DLFileEntry fileEntry, String parentPath,
		String name) {

		super(
			parentPath, name, fileEntry.getTitle(), fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), fileEntry.getSize());

		setModel(fileEntry);
		setClassName(DLFileEntry.class.getName());
		setPrimaryKey(fileEntry.getPrimaryKey());

		_webDavRequest = webDavRequest;
		_fileEntry = fileEntry;
	}

	public boolean isCollection() {
		return false;
	}

	public boolean isLocked() {
		try {
			return DLFileEntryServiceUtil.hasFileEntryLock(
				_fileEntry.getGroupId(), _fileEntry.getFolderId(),
				_fileEntry.getName());
		}
		catch (Exception e) {
		}

		return false;
	}

	public String getContentType() {
		return MimeTypesUtil.getContentType(_fileEntry.getTitle());
	}

	public InputStream getContentAsStream() throws WebDAVException {
		try {
			String version = StringPool.BLANK;

			if (!PropsValues.DL_WEBDAV_AUTO_UNLOCK) {
				DLFileVersion fileVersion =
					DLFileVersionLocalServiceUtil.getLatestFileVersion(
						_fileEntry.getGroupId(), _fileEntry.getFolderId(),
						_fileEntry.getName());

				version = fileVersion.getVersion();
			}

			return DLFileEntryLocalServiceUtil.getFileAsStream(
				_webDavRequest.getCompanyId(), _webDavRequest.getUserId(),
				_fileEntry.getGroupId(), _fileEntry.getFolderId(),
				_fileEntry.getName(), version);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	private WebDAVRequest _webDavRequest;
	private DLFileEntry _fileEntry;

}