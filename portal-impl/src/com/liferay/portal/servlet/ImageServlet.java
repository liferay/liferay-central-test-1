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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ImageConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="ImageServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 */
public class ImageServlet extends HttpServlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		_lastModified = GetterUtil.getBoolean(
			servletConfig.getInitParameter("last_modified"), true);
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_lastModified) {
			long lastModified = getLastModified(request);

			if (lastModified > 0) {
				long ifModifiedSince = request.getDateHeader(
					HttpHeaders.IF_MODIFIED_SINCE);

				if ((ifModifiedSince > 0) &&
					(ifModifiedSince == lastModified)) {

					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

					return;
				}
			}

			if (lastModified > 0) {
				response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModified);
			}
		}

		try {
			writeImage(request, response);
		}
		catch (Exception e) {
			PortalUtil.sendError(
				HttpServletResponse.SC_NOT_FOUND, e, request, response);
		}
	}

	protected Image getDefaultImage(HttpServletRequest request, long imageId)
		throws NoSuchImageException {

		String path = GetterUtil.getString(request.getPathInfo());

		if (path.startsWith("/company_logo")) {
			return ImageLocalServiceUtil.getDefaultCompanyLogo();
		}
		else if (path.startsWith("/organization_logo")) {
			return ImageLocalServiceUtil.getDefaultOrganizationLogo();
		}
		else if (path.startsWith("/user_female_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserFemalePortrait();
		}
		else if (path.startsWith("/user_male_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
		}
		else if (path.startsWith("/user_portrait")) {
			return ImageLocalServiceUtil.getDefaultUserMalePortrait();
		}
		else {
			throw new NoSuchImageException(
				"No default image exists for " + imageId);
		}
	}

	protected Image getImage(HttpServletRequest request, boolean getDefault)
		throws PortalException, SystemException {

		long imageId = getImageId(request);

		Image image = null;

		if (imageId > 0) {
			image = ImageLocalServiceUtil.getImage(imageId);
		}
		else {
			String uuid = ParamUtil.getString(request, "uuid");
			long groupId = ParamUtil.getLong(request, "groupId");

			try {
				if (Validator.isNotNull(uuid) && (groupId > 0)) {
					IGImage igImage =
						IGImageLocalServiceUtil.getImageByUuidAndGroupId(
							uuid, groupId);

					image = ImageLocalServiceUtil.getImage(
						igImage.getLargeImageId());
				}
			}
			catch (Exception e) {
			}
		}

		if (getDefault) {
			if (image == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Get a default image for " + imageId);
				}

				image = getDefaultImage(request, imageId);
			}
		}

		return image;
	}

	protected long getImageId(HttpServletRequest request) {

		// The image id may be passed in as image_id, img_id, or i_id

		long imageId = ParamUtil.getLong(request, "image_id");

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "img_id");
		}

		if (imageId <= 0) {
			imageId = ParamUtil.getLong(request, "i_id");
		}

		if (imageId <= 0) {
			long companyId = ParamUtil.getLong(request, "companyId");
			String screenName = ParamUtil.getString(request, "screenName");

			try {
				if ((companyId > 0) && Validator.isNotNull(screenName)) {
					User user = UserLocalServiceUtil.getUserByScreenName(
						companyId, screenName);

					imageId = user.getPortraitId();
				}
			}
			catch (Exception e) {
			}
		}

		return imageId;
	}

	protected long getLastModified(HttpServletRequest request) {
		try {
			Image image = getImage(request, false);

			if (image == null) {
				return -1;
			}

			Date modifiedDate = image.getModifiedDate();

			if (modifiedDate == null) {
				modifiedDate = PortalUtil.getUptime();
			}

			// Round down and remove milliseconds

			return (modifiedDate.getTime() / 1000) * 1000;
		}
		catch (Exception e) {
			_log.error(e, e);

			return -1;
		}
	}

	protected void writeImage(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException, SystemException {

		Image image = getImage(request, true);

		if (image == null) {
			throw new NoSuchImageException("Image is null");
		}
		else {
			if (!image.getType().equals(ImageConstants.TYPE_NOT_AVAILABLE)) {
				String contentType = ContentTypeUtil.getContentType(
					image.getType());

				response.setContentType(contentType);
			}

			try {
				ServletResponseUtil.write(response, image.getTextObj());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImageServlet.class);

	private boolean _lastModified = true;

}