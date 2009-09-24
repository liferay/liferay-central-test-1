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

package com.liferay.portal.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="ServiceContextFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Aug�
 */
public class ServiceContextFactory {

	public static ServiceContext getInstance(
			String className, PortletRequest portletRequest)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		serviceContext.setCompanyId(themeDisplay.getCompanyId());
		serviceContext.setLanguageId(themeDisplay.getLanguageId());
		serviceContext.setLayoutFullURL(
			PortalUtil.getLayoutFullURL(themeDisplay));
		serviceContext.setLayoutURL(PortalUtil.getLayoutURL(themeDisplay));
		serviceContext.setPathMain(PortalUtil.getPathMain());
		serviceContext.setPlid(themeDisplay.getPlid());
		serviceContext.setPortalURL(PortalUtil.getPortalURL(portletRequest));
		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
		serviceContext.setUserDisplayURL(
			themeDisplay.getUser().getDisplayURL(themeDisplay));
		serviceContext.setUserId(themeDisplay.getUserId());

		// Expando

		Map<String, Serializable> attributes =
			PortalUtil.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(className),
				portletRequest);

		serviceContext.setExpandoBridgeAttributes(attributes);

		// Permissions

		boolean addCommunityPermissions = ParamUtil.getBoolean(
			portletRequest, "addCommunityPermissions");
		boolean addGuestPermissions = ParamUtil.getBoolean(
			portletRequest, "addGuestPermissions");
		String[] communityPermissions = PortalUtil.getCommunityPermissions(
			portletRequest);
		String[] guestPermissions = PortalUtil.getGuestPermissions(
			portletRequest);

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCommunityPermissions(communityPermissions);
		serviceContext.setGuestPermissions(guestPermissions);

		// Portlet preferences ids

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		String portletId = PortalUtil.getPortletId(portletRequest);

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		serviceContext.setPortletPreferencesIds(portletPreferencesIds);

		// Asset

		long[] assetCategoryIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "assetCategoryIds"), 0L);
		String[] assetTagNames = StringUtil.split(
			ParamUtil.getString(portletRequest, "assetTagNames"));

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		//Workflow

		int status = ParamUtil.getInteger(
			portletRequest, "status", StatusConstants.APPROVED);

		serviceContext.setStatus(status);

		return serviceContext;
	}

}