/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.IOException;

/**
 * @author Jorge Ferrer
 */
public class DLFileVersionImpl extends DLFileVersionBaseImpl {

	public DLFileVersionImpl() {
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
				getCompanyId(), DLFileEntry.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	@Override
	public String getExtraSettings() {
		if (_extraSettingsProperties == null) {
			return super.getExtraSettings();
		}
		else {
			return _extraSettingsProperties.toString();
		}
	}

	public UnicodeProperties getExtraSettingsProperties() {
		if (_extraSettingsProperties == null) {
			_extraSettingsProperties = new UnicodeProperties(true);

			try {
				_extraSettingsProperties.load(super.getExtraSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _extraSettingsProperties;
	}

	public DLFileEntry getFileEntry() throws PortalException, SystemException {
		return DLFileEntryLocalServiceUtil.getFileEntry(getFileEntryId());
	}

	public DLFolder getFolder() throws PortalException, SystemException {
		DLFileEntry dlFileEntry = getFileEntry();

		return dlFileEntry.getFolder();
	}

	public long getFolderId() throws PortalException, SystemException {
		DLFileEntry dlFileEntry = getFileEntry();

		return dlFileEntry.getFolderId();
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	public String getImageType() {
		if (_imageType == null) {
			try {
				Image largeImage = ImageLocalServiceUtil.getImage(
					getLargeImageId());

				_imageType = largeImage.getType();
			}
			catch (Exception e) {
				_imageType = StringPool.BLANK;

				_log.error(e);
			}
		}

		return _imageType;
	}

	@Override
	public void setExtraSettings(String extraSettings) {
		_extraSettingsProperties = null;

		super.setExtraSettings(extraSettings);
	}

	public void setExtraSettingsProperties(
		UnicodeProperties extraSettingsProperties) {

		_extraSettingsProperties = extraSettingsProperties;

		super.setExtraSettings(_extraSettingsProperties.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(DLFileVersionImpl.class);

	private ExpandoBridge _expandoBridge;
	private UnicodeProperties _extraSettingsProperties;
	private String _imageType;

}