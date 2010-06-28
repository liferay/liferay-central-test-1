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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.BaseAssetRendererFactory;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;

import javax.portlet.PortletURL;

/**
 * <a href="WikiPageAssetRendererFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class WikiPageAssetRendererFactory extends BaseAssetRendererFactory {

	public static final String CLASS_NAME =	WikiPage.class.getName();

	public static final String TYPE = "wiki";

	public AssetRenderer getAssetRenderer(long classPK, int type)
		throws PortalException, SystemException {

		WikiPage page = null;

		if (type == TYPE_LATEST_APPROVED) {
			page = WikiPageLocalServiceUtil.getPage(classPK);
		}
		else {
			WikiPageResource wikiPageResource =
				WikiPageResourceLocalServiceUtil.getPageResource(classPK);

			page = WikiPageLocalServiceUtil.getPage(
				wikiPageResource.getNodeId(), wikiPageResource.getTitle(),
				null);
		}

		return new WikiPageAssetRenderer(page);
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

		return null;
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

}