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

package com.liferay.portlet.imagegallery.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.assetpublisher.util.AssetPublisherUtil;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.permission.IGPermission;

import javax.portlet.PortletURL;

/**
 * <a href="IGImageAssetRendererFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class IGImageAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME = IGImage.class.getName();

	public static final String TYPE = "image";

	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		IGImage image = IGImageLocalServiceUtil.getImage(classPK);

		return new IGImageAssetRenderer(image);
	}

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE;
	}

	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL addAssetURL = null;

		if (IGPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), ActionKeys.ADD_IMAGE)) {

			addAssetURL = liferayPortletResponse.createRenderURL(
				PortletKeys.IMAGE_GALLERY);

			addAssetURL.setParameter(
				"struts_action", "/image_gallery/edit_image");
			addAssetURL.setParameter(
				"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
			addAssetURL.setParameter(
				"folderId",
				String.valueOf(
					AssetPublisherUtil.getRecentFolderId(
						liferayPortletRequest, CLASS_NAME)));
			addAssetURL.setParameter("uploader", "classic");
		}

		return addAssetURL;
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/file_system/small/bmp.png";
	}

}