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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import javax.portlet.PortletURL;

/**
 * <a href="BaseWorkflowHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 */
public abstract class BaseWorkflowHandler implements WorkflowHandler {

	public String getIconPath(LiferayPortletRequest liferayPortletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	public String getTitle(long classPK) {
		try {
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getTitle();
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	public String getType() {
		return TYPE_UNKNOWN;
	}

	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getURLEdit(
					liferayPortletRequest, liferayPortletResponse);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK,
			Object model)
		throws PortalException, SystemException {

		WorkflowInstanceLinkLocalServiceUtil.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK);
	}

	protected AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(classPK);
		}
		else {
			return null;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BaseWorkflowHandler.class);

}