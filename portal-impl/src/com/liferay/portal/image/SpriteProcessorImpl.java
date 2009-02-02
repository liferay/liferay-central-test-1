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

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.SpriteProcessor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import javax.media.jai.JAI;
import javax.media.jai.LookupTableJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;
import javax.media.jai.operator.FileLoadDescriptor;
import javax.media.jai.operator.LookupDescriptor;
import javax.media.jai.operator.MosaicDescriptor;
import javax.media.jai.operator.TranslateDescriptor;

/**
 * <a href="SpriteProcessorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SpriteProcessorImpl implements SpriteProcessor {

	static {
		System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	public Properties generate(
			List<File> images, String spriteFileName,
			String spritePropertiesFileName, String spritePropertiesRootPath,
			int maxHeight, int maxWidth, int maxSize)
		throws IOException {

		if (images.size() < 1) {
			return null;
		}

		if (spritePropertiesRootPath.endsWith(StringPool.SLASH) ||
			spritePropertiesRootPath.endsWith(StringPool.BACK_SLASH)) {

			spritePropertiesRootPath = spritePropertiesRootPath.substring(
				0, spritePropertiesRootPath.length() - 1);
		}

		File dir = images.get(0).getParentFile();

		File spritePropertiesFile = new File(
			dir.toString() + StringPool.SLASH + spritePropertiesFileName);

		boolean build = false;

		long lastModified = 0;

		if (spritePropertiesFile.exists()) {
			lastModified = spritePropertiesFile.lastModified();

			for (File image : images) {
				if (image.lastModified() > lastModified) {
					build = true;

					break;
				}
			}
		}
		else {
			build = true;
		}

		if (!build) {
			String spritePropertiesString = FileUtil.read(spritePropertiesFile);

			if (Validator.isNull(spritePropertiesString)) {
				return null;
			}
			else {
				return PropertiesUtil.load(spritePropertiesString);
			}
		}

		List<RenderedImage> renderedImages = new ArrayList<RenderedImage>();

		Properties spriteProperties = new SortedProperties();

		float x = 0;
		float y = 0;

		for (File file : images) {
			if (file.length() > maxSize) {
				continue;
			}

			try {
				RenderedOp renderedOp = FileLoadDescriptor.create(
					file.toString(), null, null, null);

				RenderedImage renderedImage = convert(renderedOp);

				int height = renderedImage.getHeight();
				int width = renderedImage.getWidth();

				if ((height <= maxHeight) && (width <= maxWidth)) {
					renderedImage = TranslateDescriptor.create(
						renderedImage, x, y, null, null);

					renderedImages.add(renderedImage);

					String key = StringUtil.replace(
						file.toString(), StringPool.BACK_SLASH,
						StringPool.SLASH);

					key = key.substring(
						spritePropertiesRootPath.toString().length());

					String value = (int)y + "," + height + "," + width;

					spriteProperties.setProperty(key, value);

					y += renderedOp.getHeight();
				}
			}
			catch (Exception e) {
				_log.warn("Unable to convert " + file, e);
			}
		}

		if (renderedImages.size() <= 1) {
			renderedImages.clear();
			spriteProperties.clear();
		}
		else {
			RenderedOp renderedOp = MosaicDescriptor.create(
				renderedImages.toArray(
					new RenderedImage[renderedImages.size()]),
				MosaicDescriptor.MOSAIC_TYPE_OVERLAY, null, null, null, null,
				null);

			File spriteFile = new File(
				dir.toString() + StringPool.SLASH + spriteFileName);

			ImageIO.write(renderedOp, "png", spriteFile);

			if (lastModified > 0) {
				spriteFile.setLastModified(lastModified);
			}
		}

		FileUtil.write(
			spritePropertiesFile, PropertiesUtil.toString(spriteProperties));

		if (lastModified > 0) {
			spritePropertiesFile.setLastModified(lastModified);
		}

		return spriteProperties;
	}

	protected RenderedImage convert(RenderedOp renderedOp) throws Exception {
		RenderedImage renderedImage = renderedOp;

		int height = renderedOp.getHeight();
		int width = renderedOp.getWidth();

		SampleModel sampleModel = renderedOp.getSampleModel();
		ColorModel colorModel = renderedOp.getColorModel();

		Raster raster = renderedOp.getData();

		DataBuffer dataBuffer = raster.getDataBuffer();

		if (colorModel instanceof IndexColorModel) {
			IndexColorModel indexColorModel = (IndexColorModel)colorModel;

			int mapSize = indexColorModel.getMapSize();

			byte[][] data = new byte[4][mapSize];

			indexColorModel.getReds(data[0]);
			indexColorModel.getGreens(data[1]);
			indexColorModel.getBlues(data[2]);
			indexColorModel.getAlphas(data[3]);

			LookupTableJAI lookupTableJAI = new LookupTableJAI(data);

			renderedImage = LookupDescriptor.create(
				renderedOp, lookupTableJAI, null);
		}
		else if (sampleModel.getNumBands() == 2) {
			List<Byte> bytesList = new ArrayList<Byte>(
				height * width * _NUM_OF_BANDS);

			List<Byte> tempBytesList = new ArrayList<Byte>(_NUM_OF_BANDS);

			for (int i = 0; i < dataBuffer.getSize(); i++) {
				int mod = (i + 1) % 2;

				int elemPos = i;

				if (mod == 0) {
					tempBytesList.add((byte)dataBuffer.getElem(elemPos - 1));
					tempBytesList.add((byte)dataBuffer.getElem(elemPos - 1));
				}

				tempBytesList.add((byte)dataBuffer.getElem(elemPos));

				if (mod == 0) {
					Collections.reverse(tempBytesList);

					bytesList.addAll(tempBytesList);

					tempBytesList.clear();
				}
			}

			byte[] data = ArrayUtil.toArray(
				bytesList.toArray(new Byte[bytesList.size()]));

			DataBuffer newDataBuffer = new DataBufferByte(data, data.length);

			renderedImage = createRenderedImage(
				renderedOp, height, width, newDataBuffer);
		}
		else if (colorModel.getTransparency() != Transparency.TRANSLUCENT) {
			List<Byte> bytesList = new ArrayList<Byte>(
				height * width * _NUM_OF_BANDS);

			List<Byte> tempBytesList = new ArrayList<Byte>(_NUM_OF_BANDS);

			for (int i = 0; i < dataBuffer.getSize(); i++) {
				int mod = (i + 1) % 3;

				int elemPos = i;

				tempBytesList.add((byte)dataBuffer.getElem(elemPos));

				if (mod == 0) {
					tempBytesList.add((byte)255);

					Collections.reverse(tempBytesList);

					bytesList.addAll(tempBytesList);

					tempBytesList.clear();
				}
			}

			byte[] data = ArrayUtil.toArray(
				bytesList.toArray(new Byte[bytesList.size()]));

			DataBuffer newDataBuffer = new DataBufferByte(data, data.length);

			renderedImage = createRenderedImage(
				renderedOp, height, width, newDataBuffer);
		}

		return renderedImage;
	}

	protected RenderedImage createRenderedImage(
		RenderedOp renderedOp, int height, int width, DataBuffer dataBuffer) {

		SampleModel sampleModel =
			RasterFactory.createPixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, _NUM_OF_BANDS);
		ColorModel colorModel = PlanarImage.createColorModel(sampleModel);

		TiledImage tiledImage = new TiledImage(
			0, 0, width, height, 0, 0, sampleModel, colorModel);

		Raster raster = RasterFactory.createWritableRaster(
			sampleModel, dataBuffer, new Point(0, 0));

		tiledImage.setData(raster);

		if (false) {
			JAI.create("filestore", tiledImage, "test.png", "PNG");

			printImage(renderedOp);
			printImage(tiledImage);
		}

		return tiledImage;
	}

	protected void printImage(PlanarImage planarImage) {
		SampleModel sampleModel = planarImage.getSampleModel();

		int height = planarImage.getHeight();
		int width = planarImage.getWidth();
		int numOfBands = sampleModel.getNumBands();

		int[] pixels = new int[height * width * numOfBands];

		Raster raster = planarImage.getData();

		raster.getPixels(0, 0, width, height, pixels);

		int offset = 0;

		for (int h = 0; h < height; h++) {
			 for (int w = 0; w < width; w++) {
				offset = (h * width * numOfBands) + (w * numOfBands);

				System.out.print("[" + w + ", " + h + "] = ");

				for (int b = 0; b < numOfBands; b++) {
					System.out.print(pixels[offset + b] + " ");
				}
			}

			System.out.println();
		}
	}

	private static final int _NUM_OF_BANDS = 4;

	private static Log _log = LogFactoryUtil.getLog(SpriteProcessorImpl.class);

}