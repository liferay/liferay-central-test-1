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

package com.liferay.portal.service;


/**
 * <a href="ImageLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ImageLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ImageLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ImageLocalService
 * @see com.liferay.portal.service.ImageLocalServiceFactory
 *
 */
public class ImageLocalServiceUtil {
	public static com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.addImage(image);
	}

	public static void deleteImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		imageLocalService.deleteImage(imageId);
	}

	public static void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		imageLocalService.deleteImage(image);
	}

	public static java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.Image> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.getImage(imageId);
	}

	public static com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.updateImage(image);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.getImages();
	}

	public static java.util.List<com.liferay.portal.model.Image> getImages(
		int start, int end) throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.getImages(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.getImagesBySize(size);
	}

	public static com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.SystemException {
		ImageLocalService imageLocalService = ImageLocalServiceFactory.getService();

		return imageLocalService.updateImage(imageId, bytes, type, height,
			width, size);
	}
}