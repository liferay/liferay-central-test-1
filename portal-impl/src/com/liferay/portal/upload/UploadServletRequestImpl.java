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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Brian Wing Shun Chan
 * @author Zongliang Li
 * @author Harry Mark
 * @author Raymond Augé
 */
public class UploadServletRequestImpl
	extends HttpServletRequestWrapper implements UploadServletRequest {

	public static File getTempDir() throws SystemException {
		if (_tempDir == null) {
			_tempDir = new File(
				PrefsPropsUtil.getString(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
					SystemProperties.get(SystemProperties.TMP_DIR)));
		}

		return _tempDir;
	}

	public static void setTempDir(File tempDir) {
		_tempDir = tempDir;
	}

	public UploadServletRequestImpl(HttpServletRequest request) {
		super(request);

		_params = new LinkedHashMap<String, LiferayFileItem[]>();

		try {
			ServletFileUpload servletFileUpload = new LiferayFileUpload(
				new LiferayFileItemFactory(getTempDir()), request);

			servletFileUpload.setSizeMax(
				PrefsPropsUtil.getLong(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

			_liferayServletRequest = new LiferayServletRequest(request);

			List<LiferayFileItem> liferayFileItemsList =
				servletFileUpload.parseRequest(_liferayServletRequest);

			for (LiferayFileItem liferayFileItem : liferayFileItemsList) {
				if (liferayFileItem.isFormField()) {
					liferayFileItem.setString(request.getCharacterEncoding());
				}

				LiferayFileItem[] liferayFileItems = _params.get(
					liferayFileItem.getFieldName());

				if (liferayFileItems == null) {
					liferayFileItems = new LiferayFileItem[] {liferayFileItem};
				}
				else {
					LiferayFileItem[] newLiferayFileItems =
						new LiferayFileItem[liferayFileItems.length + 1];

					System.arraycopy(
						liferayFileItems, 0, newLiferayFileItems, 0,
						liferayFileItems.length);

					newLiferayFileItems[newLiferayFileItems.length - 1] =
						liferayFileItem;

					liferayFileItems = newLiferayFileItems;
				}

				_params.put(liferayFileItem.getFieldName(), liferayFileItems);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void cleanUp() {
		if ((_params != null) && !_params.isEmpty()) {
			for (LiferayFileItem[] liferayFileItems : _params.values()) {
				for (LiferayFileItem liferayFileItem : liferayFileItems) {
					liferayFileItem.delete();
				}
			}
		}
	}

	public String getContentType(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return liferayFileItems[0].getContentType();
		}
		else {
			return null;
		}
	}

	public File getFile(String name) {
		if (getFileName(name) == null) {
			return null;
		}

		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return liferayFileItems[0].getStoreLocation();
		}
		else {
			return null;
		}
	}

	public File[] getFiles(String name) {
		String[] fileNames = getFileNames(name);

		if (fileNames == null) {
			return null;
		}

		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			File[] files = new File[liferayFileItems.length];

			for (int i = 0; i < liferayFileItems.length; i++) {
				LiferayFileItem liferayFileItem = liferayFileItems[i];

				if (Validator.isNotNull(liferayFileItem.getFileName())) {
					files[i] = liferayFileItem.getStoreLocation();
				}
			}

			return files;
		}
		else {
			return null;
		}
	}

	public String getFileName(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return liferayFileItems[0].getFileName();
		}
		else {
			return null;
		}
	}

	public String[] getFileNames(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			String[] fileNames = new String[liferayFileItems.length];

			for (int i = 0; i < liferayFileItems.length; i++) {
				LiferayFileItem liferayFileItem = liferayFileItems[i];

				fileNames[i] = liferayFileItem.getFileName();
			}

			return fileNames;
		}
		else {
			return null;
		}
	}

	public String getFullFileName(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return liferayFileItems[0].getFullFileName();
		}
		else {
			return null;
		}
	}

	public ServletInputStream getInputStream() throws IOException {
		return _liferayServletRequest.getInputStream();
	}

	public Map<String, LiferayFileItem[]> getMultipartParameterMap() {
		return _params;
	}

	public String getParameter(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return liferayFileItems[0].getString();
		}
		else {
			return super.getParameter(name);
		}
	}

	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new HashMap<String, String[]>();

		Enumeration<String> enu = getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			map.put(name, getParameterValues(name));
		}

		return map;
	}

	public Enumeration<String> getParameterNames() {
		Set<String> parameterNames = new LinkedHashSet<String>();

		Enumeration<String> enu = super.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!_params.containsKey(name)) {
				parameterNames.add(name);
			}
		}

		parameterNames.addAll(_params.keySet());

		return Collections.enumeration(parameterNames);
	}

	public String[] getParameterValues(String name) {
		String[] parentValues = super.getParameterValues(name);

		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems == null) || (liferayFileItems.length == 0)) {
			return parentValues;
		}
		else if ((parentValues == null) || (parentValues.length == 0)) {
			String[] values = new String[liferayFileItems.length];

			for (int i = 0; i < values.length; i++) {
				values[i] = liferayFileItems[i].getString();
			}

			return values;
		}
		else {
			String[] values = new String[
				parentValues.length + liferayFileItems.length];

			System.arraycopy(
				parentValues, 0, values, 0, parentValues.length);

			for (int i = parentValues.length; i < values.length; i++) {
				values[i] =
					liferayFileItems[i - parentValues.length].getString();
			}

			return values;
		}
	}

	public Boolean isFormField(String name) {
		LiferayFileItem[] liferayFileItems = _params.get(name);

		if ((liferayFileItems != null) && (liferayFileItems.length > 0)) {
			return new Boolean(liferayFileItems[0].isFormField());
		}
		else {
			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UploadServletRequestImpl.class);

	private static File _tempDir;

	private LiferayServletRequest _liferayServletRequest;
	private Map<String, LiferayFileItem[]> _params;

}