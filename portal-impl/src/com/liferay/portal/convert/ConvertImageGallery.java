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

package com.liferay.portal.convert;

import com.liferay.portal.image.DatabaseHook;
import com.liferay.portal.image.HookFactory;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class ConvertImageGallery extends ConvertProcess {

	public String getDescription() {
		return "migrate-images-from-one-repository-to-another";
	}

	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	public String[] getParameterNames() {
		StringBundler sb = new StringBundler(_HOOKS.length * 2 + 2);

		sb.append(PropsKeys.IMAGE_HOOK_IMPL);
		sb.append(StringPool.EQUAL);

		for (String hook : _HOOKS) {
			if (!hook.equals(PropsValues.IMAGE_HOOK_IMPL)) {
				sb.append(hook);
				sb.append(StringPool.SEMICOLON);
			}
		}

		return new String[] {sb.toString()};
	}

	public boolean isEnabled() {
		return true;
	}

	protected void doConvert() throws Exception {
		boolean cacheRegistryActive = CacheRegistryUtil.isActive();

		try {
			CacheRegistryUtil.setActive(false);

			_sourceHook = HookFactory.getInstance();

			String[] values = getParameterValues();

			String targetHookClassName = values[0];

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			_targetHook = (Hook)classLoader.loadClass(
				targetHookClassName).newInstance();

			migrateImages();

			HookFactory.setInstance(_targetHook);

			MaintenanceUtil.appendStatus(
				"Please set " + PropsKeys.IMAGE_HOOK_IMPL +
					" in your portal-ext.properties to use " +
						targetHookClassName);

			if (_sourceHook instanceof DatabaseHook) {
				DB db = DBFactoryUtil.getDB();

				db.runSQL("update Image set text_ = \"\"");
			}

			PropsValues.IMAGE_HOOK_IMPL = targetHookClassName;
		}
		finally {
			CacheRegistryUtil.setActive(cacheRegistryActive);
		}
	}

	protected void migrateImage(Image image) throws Exception {
		try {
			InputStream is = _sourceHook.getImageAsStream(image);

			byte[] bytes = FileUtil.getBytes(is);

			_targetHook.updateImage(image, image.getType(), bytes);

			if (_targetHook instanceof DatabaseHook) {
				ImageLocalServiceUtil.updateImage(image, false);
			}
		}
		catch (Exception e) {
			_log.error("Migration failed for " + image.getImageId(), e);
		}
	}

	protected void migrateImages() throws Exception {
		int count = ImageLocalServiceUtil.getImagesCount();
		int pages = count / Indexer.DEFAULT_INTERVAL;

		MaintenanceUtil.appendStatus("Migrating " + count + " images");

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<Image> images = ImageLocalServiceUtil.getImages(start, end);

			for (Image image : images) {
				migrateImage(image);
			}
		}
	}

	private static final String[] _HOOKS = new String[] {
		"com.liferay.portal.image.DatabaseHook",
		"com.liferay.portal.image.DLHook",
		"com.liferay.portal.image.FileSystemHook"
	};

	private static Log _log = LogFactoryUtil.getLog(ConvertImageGallery.class);

	private Hook _sourceHook;
	private Hook _targetHook;

}