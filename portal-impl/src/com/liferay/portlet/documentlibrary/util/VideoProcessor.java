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

import com.liferay.portal.kernel.exception.SystemException;
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
public class VideoProcessor extends DefaultPreviewableProcessor {

	public static final String PREVIEW_TYPE = "flv";

	public static final String THUMBNAIL_TYPE = "jpg";

	public static void generateVideo(FileVersion fileVersion)
		throws Exception {

		_instance._generateVideo(fileVersion);
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

	public static InputStream getThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailAsStream(fileVersion);
	}

	public static long getThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return _instance.doGetThumbnailFileSize(fileVersion);
	}

	public static Set<String> getVideoMimeTypes() {
		return _videoMimeTypes;
	}

	public static boolean hasVideo(FileVersion fileVersion) {
		boolean hasVideo = false;

		try {
			hasVideo = _instance._hasVideo(fileVersion);

			if (!hasVideo && _instance.isSupported(fileVersion)) {
				_instance._queueGeneration(fileVersion);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hasVideo;
	}

	public VideoProcessor() {
		FileUtil.mkdirs(PREVIEW_TMP_PATH);
		FileUtil.mkdirs(THUMBNAIL_TMP_PATH);
	}

	public boolean isSupported(String mimeType) {
		return _videoMimeTypes.contains(mimeType);
	}

	public void trigger(FileVersion fileVersion) {
		_instance._queueGeneration(fileVersion);
	}

	@Override
	protected String getPreviewType() {
		return PREVIEW_TYPE;
	}

	@Override
	protected String getThumbnailType() {
		return THUMBNAIL_TYPE;
	}

	private void _generateThumbnailXuggler(
			FileVersion fileVersion, File file, int height, int width)
		throws Exception {

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File thumbnailTempFile = getThumbnailTempFile(tempFileId);

		try {
			try {
				LiferayVideoThumbnailConverter liferayVideoThumbnailConverter =
					new LiferayVideoThumbnailConverter(
						file.getCanonicalPath(), thumbnailTempFile,
						THUMBNAIL_TYPE, height, width, _THUMBNAIL_PERCENTAGE);

				liferayVideoThumbnailConverter.convert();
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH,
				getThumbnailFilePath(fileVersion), thumbnailTempFile);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			FileUtil.delete(thumbnailTempFile);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Xuggler generated a thumbnail for " +
					fileVersion.getFileVersionId());
		}
	}

	private void _generateVideo(FileVersion fileVersion) throws Exception {
		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		File videoTempFile = _getVideoTempFile(
			tempFileId, fileVersion.getExtension());
		File previewTempFile = getPreviewTempFile(tempFileId);

		try {
			if (!PrefsPropsUtil.getBoolean(
					PropsKeys.XUGGLER_ENABLED, PropsValues.XUGGLER_ENABLED) ||
				_hasVideo(fileVersion)) {

				return;
			}

			File file = null;

			if (_isGeneratePreview(fileVersion) ||
				_isGenerateThumbnail(fileVersion)) {

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

					FileUtil.write(videoTempFile, inputStream);

					file = videoTempFile;
				}
			}

			if (_isGeneratePreview(fileVersion)) {
				try {
					_generateVideoXuggler(
						fileVersion, file, previewTempFile,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			if (_isGenerateThumbnail(fileVersion)) {
				try {
					_generateThumbnailXuggler(
						fileVersion, file,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_HEIGHT,
						PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_WIDTH);
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

			FileUtil.delete(previewTempFile);
			FileUtil.delete(videoTempFile);
		}
	}

	private void _generateVideoXuggler(
			FileVersion fileVersion, File srcFile, File destFile, int height,
			int width)
		throws Exception {

		try {
			LiferayVideoConverter liferayVideoConverter =
				new LiferayVideoConverter(
					srcFile.getCanonicalPath(), destFile.getCanonicalPath(),
					height, width, _SAMPLE_RATE);

			liferayVideoConverter.convert();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		addFileToStore(
			fileVersion.getCompanyId(), PREVIEW_PATH,
			getPreviewFilePath(fileVersion), destFile);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Xuggler generated a preview video for " +
					fileVersion.getFileVersionId());
		}
	}

	private File _getVideoTempFile(String tempFileId, String targetExtension) {
		String videoTempFilePath = _getVideoTempFilePath(
			tempFileId, targetExtension);

		return new File(videoTempFilePath);
	}

	private String _getVideoTempFilePath(
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

	private boolean _hasVideo(FileVersion fileVersion) throws Exception {
		if (!isSupported(fileVersion)) {
			return false;
		}

		boolean previewExists = DLStoreUtil.hasFile(
			fileVersion.getCompanyId(), REPOSITORY_ID,
			getPreviewFilePath(fileVersion));
		boolean thumbnailExists = DLStoreUtil.hasFile(
			fileVersion.getCompanyId(), REPOSITORY_ID,
			getThumbnailFilePath(fileVersion));

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED &&
			PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED) {

			if (previewExists && thumbnailExists) {
				return true;
			}
		}
		else if (PropsValues.DL_FILE_ENTRY_PREVIEW_ENABLED && previewExists) {
			return true;
		}
		else if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
				 thumbnailExists) {

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

	private boolean _isGenerateThumbnail(FileVersion fileVersion)
		throws Exception {

		String thumbnailFilePath = getThumbnailFilePath(fileVersion);

		if (PropsValues.DL_FILE_ENTRY_THUMBNAIL_ENABLED &&
			!DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID, thumbnailFilePath)) {

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
			DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR, fileVersion);
	}

	private static int _SAMPLE_RATE = 44100;

	private static int _THUMBNAIL_PERCENTAGE =
		PropsValues.DL_FILE_ENTRY_THUMBNAIL_VIDEO_FRAME_PERCENTAGE;

	private static Log _log = LogFactoryUtil.getLog(VideoProcessor.class);

	private static VideoProcessor _instance = new VideoProcessor();

	private static List<Long> _fileVersionIds = new Vector<Long>();
	private static Set<String> _videoMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES);

}