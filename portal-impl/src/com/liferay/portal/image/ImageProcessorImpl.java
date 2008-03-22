/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessor;
import com.liferay.portal.kernel.util.JavaProps;
import com.liferay.util.FileUtil;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Enumeration;

import javax.imageio.ImageIO;

import javax.media.jai.RenderedImageAdapter;

import net.jmge.gif.Gif89Encoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ImageProcessorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ImageProcessorImpl implements ImageProcessor {

	public BufferedImage convertImageType(BufferedImage sourceImage, int type) {
	    BufferedImage targetImage = new BufferedImage(
	    	sourceImage.getWidth(), sourceImage.getHeight(), type);

	    Graphics2D graphics = targetImage.createGraphics();

	    graphics.drawRenderedImage(sourceImage, null);
	    graphics.dispose();

	    return targetImage;
	}

	public void encodeGIF(RenderedImage renderedImage, OutputStream out)
		throws IOException {

		if (JavaProps.isJDK6()) {
			ImageIO.write(renderedImage, "GIF", out);
		}
		else {
			BufferedImage bufferedImage = getBufferedImage(renderedImage);

			if (!(bufferedImage.getColorModel() instanceof IndexColorModel)) {
				bufferedImage = convertImageType(
					bufferedImage, BufferedImage.TYPE_BYTE_INDEXED);
			}

			Gif89Encoder encoder = new Gif89Encoder(bufferedImage);

			encoder.encode(out);
		}
	}

	public void encodeWBMP(RenderedImage renderedImage, OutputStream out)
		throws InterruptedException, IOException {

		BufferedImage bufferedImage = getBufferedImage(renderedImage);

		SampleModel sampleModel = bufferedImage.getSampleModel();

		int type = sampleModel.getDataType();

		if ((bufferedImage.getType() != BufferedImage.TYPE_BYTE_BINARY) ||
			(type < DataBuffer.TYPE_BYTE) || (type > DataBuffer.TYPE_INT) ||
			(sampleModel.getNumBands() != 1) ||
			(sampleModel.getSampleSize(0) != 1)) {

			BufferedImage binaryImage = new BufferedImage(
				bufferedImage.getWidth(), bufferedImage.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);

			binaryImage.getGraphics().drawImage(bufferedImage, 0, 0, null);

			renderedImage = binaryImage;
		}

		if (!ImageIO.write(renderedImage, "wbmp", out)) {

			// See http://www.jguru.com/faq/view.jsp?EID=127723

			out.write(0);
			out.write(0);
			out.write(_toMultiByte(bufferedImage.getWidth()));
			out.write(_toMultiByte(bufferedImage.getHeight()));

			DataBuffer dataBuffer = bufferedImage.getData().getDataBuffer();

			int size = dataBuffer.getSize();

			for (int i = 0; i < size; i++) {
				out.write((byte)dataBuffer.getElem(i));
			}
		}
	}

	public BufferedImage getBufferedImage(RenderedImage renderedImage) {
		if (renderedImage instanceof BufferedImage) {
			return (BufferedImage)renderedImage;
		}
		else {
			RenderedImageAdapter adapter = new RenderedImageAdapter(
				renderedImage);

			return adapter.getAsBufferedImage();
		}
	}

	public ImageBag read(File file) throws IOException {
		return read(FileUtil.getBytes(file));
	}

	public ImageBag read(byte[] bytes) throws IOException {
		RenderedImage renderedImage = null;
		String type = TYPE_NOT_AVAILABLE;

		Enumeration<ImageCodec> enu = ImageCodec.getCodecs();

		while (enu.hasMoreElements()) {
			ImageCodec codec = enu.nextElement();

			if (codec.isFormatRecognized(bytes)) {
				type = codec.getFormatName();

				ImageDecoder decoder = ImageCodec.createImageDecoder(
					type, new ByteArrayInputStream(bytes), null);

				try {
					renderedImage = decoder.decodeAsRenderedImage();
				}
				catch (IOException ioe) {
					if (_log.isDebugEnabled()) {
						_log.debug(type + ": " + ioe.getMessage());
					}
				}

				break;
			}
		}

		if (type.equals("jpeg")) {
			type = ImageProcessor.TYPE_JPEG;
		}

		return new ImageBag(renderedImage, type);
	}

	public RenderedImage scale(
		RenderedImage renderedImage, int maxHeight, int maxWidth) {

		int imageHeight = renderedImage.getHeight();
		int imageWidth = renderedImage.getWidth();

		if (maxHeight == 0) {
			maxHeight = imageHeight;
		}

		if (maxWidth == 0) {
			maxWidth = imageWidth;
		}

		if ((imageHeight <= maxHeight) && (imageWidth <= maxWidth)) {
			return renderedImage;
		}

		double factor = Math.min(
			(double)maxHeight / imageHeight, (double)maxWidth / imageWidth);

		int scaledHeight = (int)(factor * imageHeight);
		int scaledWidth = (int)(factor * imageWidth);

		BufferedImage bufferedImage = getBufferedImage(renderedImage);

		Image scaledImage = bufferedImage.getScaledInstance(
			scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

		BufferedImage scaledBufferedImage = new BufferedImage(
			scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);

		scaledBufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);

		return scaledBufferedImage;
	}

	private byte[] _toMultiByte(int intValue) {
		int numBits = 32;
		int mask = 0x80000000;

		while (mask != 0 && (intValue & mask) == 0) {
			numBits--;
			mask >>>= 1;
		}

		int numBitsLeft = numBits;
		byte[] multiBytes = new byte[(numBitsLeft + 6) / 7];

		int maxIndex = multiBytes.length - 1;

		for (int b = 0; b <= maxIndex; b++) {
			multiBytes[b] = (byte)((intValue >>> ((maxIndex - b) * 7)) & 0x7f);

			if (b != maxIndex) {
				multiBytes[b] |= (byte)0x80;
			}
		}

		return multiBytes;
	}

	private static Log _log = LogFactory.getLog(ImageProcessorImpl.class);

}