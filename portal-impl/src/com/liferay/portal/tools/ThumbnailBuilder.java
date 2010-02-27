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

package com.liferay.portal.tools;

import com.liferay.portal.image.ImageProcessorImpl;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.util.GetterUtil;

import java.awt.image.RenderedImage;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * <a href="ThumbnailBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThumbnailBuilder {

	public static void main(String[] args) {
		File originalFile = new File(
			System.getProperty("thumbnail.original.file"));
		File thumbnailFile = new File(
			System.getProperty("thumbnail.thumbnail.file"));
		int height = GetterUtil.getInteger(
			System.getProperty("thumbnail.height"));
		int width = GetterUtil.getInteger(
			System.getProperty("thumbnail.width"));
		boolean overwrite = GetterUtil.getBoolean(
			System.getProperty("thumbnail.overwrite"));

		new ThumbnailBuilder(
			originalFile, thumbnailFile, height, width, overwrite);
	}

	public ThumbnailBuilder(
		File originalFile, File thumbnailFile, int height, int width,
		boolean overwrite) {

		try {
			if (!originalFile.exists()) {
				return;
			}

			if (!overwrite) {
				if (thumbnailFile.lastModified() >
						originalFile.lastModified()) {

					return;
				}
			}

			ImageBag imageBag = _imageProcessorUtil.read(originalFile);

			RenderedImage thumbnail = _imageProcessorUtil.scale(
				imageBag.getRenderedImage(), height, width);

			ImageIO.write(thumbnail, imageBag.getType(), thumbnailFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ImageProcessorImpl _imageProcessorUtil =
		ImageProcessorImpl.getInstance();

}