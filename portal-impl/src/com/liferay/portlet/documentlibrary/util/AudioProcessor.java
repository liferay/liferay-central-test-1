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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Juan González
 * @author Sergio González
 * @author Mika Koivisto
 */
public class AudioProcessor extends DefaultPreviewableProcessor {

	public static final String PREVIEW_TYPE = "mp3";

	public static void generateAudio(FileVersion fileVersion) throws Exception {
		_instance._generateAudio(fileVersion);
	}

	public static Set<String> getAudioMimeTypes() {
		return _audioMimeTypes;
	}

	public static DLProcessor getInstance() {
		return _instance;
	}

	public static InputStream getPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewAsStream(fileVersion);
	}

	public static long getPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetPreviewFileSize(fileVersion);
	}

	public static boolean hasAudio(FileVersion fileVersion) {
		boolean hasAudio = false;

		try {
			hasAudio = _instance._hasAudio(fileVersion);

			if (!hasAudio && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasAudio;
	}

	public AudioProcessor() {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
	}

	public boolean isSupported(String mimeType) {
		return _audioMimeTypes.contains(mimeType);
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	@Override
	protected String getPreviewType() {
		return PREVIEW_TYPE;
	}

	private void _generateAudio(FileVersion fileVersion) throws Exception {
		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File audioTempFile = _getAudioTempFile(
			tempFileId, fileVersion.getExtension());
		File previewTempFile = getPreviewTempFile(tempFileId);

		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasAudio(fileVersion)) {

				return;
			}

			if (_isGeneratePreview(fileVersion)) {
				File file = null;

				if (fileVersion instanceof LiferayFileVersion) {
					try {
						LiferayFileVersion liferayFileVersion =
							(LiferayFileVersion)fileVersion;

						file = liferayFileVersion.getFile(false);
					}
					catch (UnsupportedOperationException uoe) {
					}
				}

				if (file == null) {
					InputStream inputStream = fileVersion.getContentStream(
						false);

					FileUtil.write(audioTempFile, inputStream);

					file = audioTempFile;
				}

				try {
					_generateAudioXuggler(fileVersion, file, previewTempFile);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		finally {
			_fileVersionIds.remove(fileVersion.getFileVersionId());

			FileUtil.delete(audioTempFile);
			FileUtil.delete(previewTempFile);
		}
	}

	private void _generateAudioXuggler(
			FileVersion fileVersion, File srcFile, File destFile)
		throws Exception {

		try {
			LiferayAudioConverter liferayAudioConverter =
				new LiferayAudioConverter(
					srcFile.getCanonicalPath(), destFile.getCanonicalPath(),
					_SAMPLE_RATE);

			liferayAudioConverter.convert();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion), destFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Xuggler generated a preview audio for " +
					fileVersion.getFileVersionId());
		}
	}

	private File _getAudioTempFile(String tempFileId, String targetExtension) {
		String audioTempFilePath = _getAudioTempFilePath(
			tempFileId, targetExtension);

		return new File(audioTempFilePath);
	}

	private String _getAudioTempFilePath(
		String tempFileId, String targetExtension) {

		StringBundler sb = new StringBundler(5);

		sb.append(PREVIEW_TMP_PATH);
		sb.append(tempFileId);

		if (PREVIEW_TYPE.equals(targetExtension)) {
			sb.append("_tmp");
		}

		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		return sb.toString();
	}

	private boolean _hasAudio(FileVersion fileVersion) throws Exception {
		if (!isSupported(fileVersion)) {
			return false;
		}

		boolean previewExists = DLStoreUtil.hasFile(
			fileVersion.getCompanyId(), REPOSITORY_ID,
			getPreviewFilePath(fileVersion));

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED && previewExists) {
			return true;
		}

		return false;
	}

	private boolean _isGeneratePreview(FileVersion fileVersion)
		throws Exception {

		String previewFilePath = getPreviewFilePath(fileVersion);

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, previewFilePath)) {

			return true;
		}
		else {
			return false;
		}
	}

	private void _queueGeneration(FileVersion fileVersion) {
		if (_fileVersionIds.contains(fileVersion.getFileVersionId()) ||
			!isSupported(fileVersion)) {

			return;
		}

		_fileVersionIds.add(fileVersion.getFileVersionId());

		MessageBusUtil.sendMessage(
			DestinationNames.DOCUMENT_LIBRARY_AUDIO_PROCESSOR, fileVersion);
	}

	private static int _SAMPLE_RATE = 44100;

	private static Log _log = LogFactoryUtil.getLog(AudioProcessor.class);

	private static AudioProcessor _instance = new AudioProcessor();

	private static Set<String> _audioMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES);
	private static List<Long> _fileVersionIds = new Vector<Long>();

}