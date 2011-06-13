/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
<<<<<<< HEAD
import com.liferay.portal.kernel.util.ArrayUtil;
=======
import com.liferay.portal.kernel.servlet.HttpHeaders;
>>>>>>> LPS-17928 Add remote address, host and request headers to ServiceContext
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ServiceContextFactory {

	public static ServiceContext getInstance(HttpServletRequest request)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			serviceContext.setCompanyId(themeDisplay.getCompanyId());
			serviceContext.setLanguageId(themeDisplay.getLanguageId());
			serviceContext.setLayoutFullURL(
				PortalUtil.getLayoutFullURL(themeDisplay));
			serviceContext.setLayoutURL(PortalUtil.getLayoutURL(themeDisplay));
			serviceContext.setPathMain(PortalUtil.getPathMain());
			serviceContext.setPlid(themeDisplay.getPlid());
			serviceContext.setPortalURL(PortalUtil.getPortalURL(request));
			serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
			serviceContext.setSignedIn(themeDisplay.isSignedIn());

			User user = themeDisplay.getUser();

			serviceContext.setUserDisplayURL(user.getDisplayURL(themeDisplay));
			serviceContext.setUserId(user.getUserId());
		}
		else {
			long companyId = PortalUtil.getCompanyId(request);

			serviceContext.setCompanyId(companyId);

			serviceContext.setPathMain(PortalUtil.getPathMain());

			User user = PortalUtil.getUser(request);

			if (user != null) {
				serviceContext.setSignedIn(!user.isDefaultUser());
				serviceContext.setUserId(user.getUserId());
			}
			else {
				serviceContext.setSignedIn(false);
			}
		}

		// Attributes

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = request.getParameterValues(param);

			if ((values != null) && (values.length > 0)) {
				if (values.length == 1) {
					attributes.put(param, values[0]);
				}
				else {
					attributes.put(param, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		return serviceContext;
	}

	public static ServiceContext getInstance(PortletRequest portletRequest)
		throws PortalException, SystemException {

		// Theme display

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)portletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (serviceContext != null) {
			serviceContext = (ServiceContext)serviceContext.clone();
		}
		else {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(themeDisplay.getCompanyId());
			serviceContext.setLanguageId(themeDisplay.getLanguageId());
			serviceContext.setLayoutFullURL(
				PortalUtil.getLayoutFullURL(themeDisplay));
			serviceContext.setLayoutURL(PortalUtil.getLayoutURL(themeDisplay));
			serviceContext.setPathMain(PortalUtil.getPathMain());
			serviceContext.setPlid(themeDisplay.getPlid());
			serviceContext.setPortalURL(
				PortalUtil.getPortalURL(portletRequest));
			serviceContext.setSignedIn(themeDisplay.isSignedIn());

			User user = themeDisplay.getUser();

			serviceContext.setUserDisplayURL(user.getDisplayURL(themeDisplay));
			serviceContext.setUserId(user.getUserId());
		}

		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());

		// Attributes

		Map<String, Serializable> attributes =
			new HashMap<String, Serializable>();

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			String[] values = portletRequest.getParameterValues(param);

			if ((values != null) && (values.length > 0)) {
				if (values.length == 1) {
					attributes.put(param, values[0]);
				}
				else {
					attributes.put(param, values);
				}
			}
		}

		serviceContext.setAttributes(attributes);

		// Command

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

		serviceContext.setCommand(cmd);

		// Current URL

		String currentURL = PortalUtil.getCurrentURL(portletRequest);

		serviceContext.setCurrentURL(currentURL);

		// Permissions

		boolean addGroupPermissions = ParamUtil.getBoolean(
			portletRequest, "addGroupPermissions");
		boolean addGuestPermissions = ParamUtil.getBoolean(
			portletRequest, "addGuestPermissions");
		String[] groupPermissions = PortalUtil.getGroupPermissions(
			portletRequest);
		String[] guestPermissions = PortalUtil.getGuestPermissions(
			portletRequest);

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setGroupPermissions(groupPermissions);
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

		Map<String, String[]> parameterMap = portletRequest.getParameterMap();

		List<Long> assetCategoryIdsList = new ArrayList<Long>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String name = entry.getKey();

			if (name.startsWith("assetCategoryIds")) {
				long[] assetVocabularyAssetCategoryIds = StringUtil.split(
					ParamUtil.getString(portletRequest, name), 0L);

				for (long assetCategoryId : assetVocabularyAssetCategoryIds) {
					assetCategoryIdsList.add(assetCategoryId);
				}
			}
		}

		long[] assetCategoryIds = ArrayUtil.toArray(
			assetCategoryIdsList.toArray(
				new Long[assetCategoryIdsList.size()]));
		long[] assetLinkEntryIds = StringUtil.split(
			ParamUtil.getString(
				portletRequest, "assetLinkSearchContainerPrimaryKeys"), 0L);
		String[] assetTagNames = StringUtil.split(
			ParamUtil.getString(portletRequest, "assetTagNames"));

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetLinkEntryIds(assetLinkEntryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		// Workflow

		int workflowAction = ParamUtil.getInteger(
			portletRequest, "workflowAction", WorkflowConstants.ACTION_PUBLISH);

		serviceContext.setWorkflowAction(workflowAction);

		// Remote

		String remoteAddr = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();

		serviceContext.setRemoteAddr(remoteAddr);
		serviceContext.setRemoteHost(remoteHost);

		// Headers

		Map<String, String> headerMap = new HashMap<String, String>();

		enu = request.getHeaderNames();

		while (enu.hasMoreElements()) {
			String header = enu.nextElement();

			String value = request.getHeader(header);

			headerMap.put(header, value);
		}

		serviceContext.setHeaders(headerMap);

		return serviceContext;
	}

	public static ServiceContext getInstance(
			String className, PortletRequest portletRequest)
		throws PortalException, SystemException {

		ServiceContext serviceContext = getInstance(portletRequest);

		// Expando

		Map<String, Serializable> expandoBridgeAttributes =
			PortalUtil.getExpandoBridgeAttributes(
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(), className),
				portletRequest);

		serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);

		return serviceContext;
	}

}