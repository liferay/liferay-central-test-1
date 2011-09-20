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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.AudioProcessor;
import com.liferay.portlet.documentlibrary.util.VideoProcessor;

import com.xuggle.xuggler.IContainer;

import java.io.File;
import java.io.InputStream;

import jodd.util.StringPool;

import org.apache.tika.metadata.Metadata;

/**
 * @author Juan González
 * @author Alexander Chow
 */
public class XugglerRawMetadataProcessor extends BaseRawMetadataProcessor {

	@Override
	public Metadata extractMetadata(
			String extension, String mimeType, File file)
		throws SystemException {

		Metadata metadata = null;

		if (!isSupported(mimeType)) {
			return metadata;
		}

		try {
			metadata = extractMetadata(file);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return metadata;
	}

	@Override
	public Metadata extractMetadata(
			String extension, String mimeType, InputStream inputStream)
		throws SystemException {

		Metadata metadata = null;

		File file = null;

		if (!isSupported(mimeType)) {
			return metadata;
		}

		try {
			file = FileUtil.createTempFile(extension);

			FileUtil.write(file, inputStream);

			metadata = extractMetadata(file);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			FileUtil.delete(file);
		}

		return metadata;
	}

	protected String convertTime(long microseconds) {
		long milliseconds = microseconds / 1000L;

		StringBundler sb = new StringBundler(5);

		sb.append(milliseconds / Time.HOUR);
		sb.append(StringPool.COLON);
		sb.append(milliseconds % Time.HOUR / Time.MINUTE);
		sb.append(StringPool.COLON);
		sb.append(milliseconds % Time.MINUTE / Time.SECOND);

		return sb.toString();
	}

	protected Metadata extractMetadata(File file) throws Exception {
		IContainer container = IContainer.make();

		try {
			Metadata metadata = new Metadata();

			if (container.open(
					file.getCanonicalPath(), IContainer.Type.READ, null) < 0) {

				throw new IllegalArgumentException("Could not open stream");
			}

			if (container.queryStreamMetaData() < 0) {
				throw new IllegalStateException(
					"Could not query stream metadata");
			}

			long microseconds = container.getDuration();

			metadata.add(Metadata.TOTAL_TIME, convertTime(microseconds));

			return metadata;

		}
		finally {
			if (container.isOpened()) {
				container.close();
			}
		}
	}

	protected boolean isSupported(String mimeType) throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) &&
			(AudioProcessor.isSupportedAudio(mimeType) ||
			 VideoProcessor.isSupportedVideo(mimeType))) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		XugglerRawMetadataProcessor.class);

}