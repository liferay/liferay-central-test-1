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

package com.liferay.portlet.communities.action;

import com.liferay.portal.ImageTypeException;
import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ColorScheme;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.LayoutPrototypePermissionUtil;
import com.liferay.portal.service.permission.LayoutSetPrototypePermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutSettings;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.util.CommunitiesUtil;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.util.servlet.UploadException;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPagesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditPagesAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			Layout layout = null;
			String oldFriendlyURL = StringPool.BLANK;

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateLayout(
					actionRequest, actionResponse);

				layout = (Layout)returnValue[0];
				oldFriendlyURL = (String)returnValue[1];
			}
			else if (cmd.equals(Constants.DELETE)) {
				CommunitiesUtil.deleteLayout(actionRequest, actionResponse);
			}
			else if (cmd.equals("copy_from_live")) {
				StagingUtil.copyFromLive(actionRequest);
			}
			else if (cmd.equals("display_order")) {
				updateDisplayOrder(actionRequest);
			}
			else if (cmd.equals("logo")) {
				updateLogo(actionRequest);
			}
			else if (cmd.equals("look_and_feel")) {
				updateLookAndFeel(actionRequest);
			}
			else if (cmd.equals("merge_pages")) {
				updateMergePages(actionRequest);
			}
			else if (cmd.equals("monitoring")) {
				updateMonitoring(actionRequest);
			}
			else if (cmd.equals("publish_to_live")) {
				StagingUtil.publishToLive(actionRequest);
			}
			else if (cmd.equals("publish_to_remote")) {
				StagingUtil.publishToRemote(actionRequest);
			}
			else if (cmd.equals("schedule_copy_from_live")) {
				StagingUtil.scheduleCopyFromLive(actionRequest);
			}
			else if (cmd.equals("schedule_publish_to_live")) {
				StagingUtil.schedulePublishToLive(actionRequest);
			}
			else if (cmd.equals("schedule_publish_to_remote")) {
				StagingUtil.schedulePublishToRemote(actionRequest);
			}
			else if (cmd.equals("staging")) {
				StagingUtil.updateStaging(actionRequest);
			}
			else if (cmd.equals("unschedule_copy_from_live")) {
				StagingUtil.unscheduleCopyFromLive(actionRequest);
			}
			else if (cmd.equals("unschedule_publish_to_live")) {
				StagingUtil.unschedulePublishToLive(actionRequest);
			}
			else if (cmd.equals("unschedule_publish_to_remote")) {
				StagingUtil.unschedulePublishToRemote(actionRequest);
			}
			else if (cmd.equals("virtual_host")) {
				updateVirtualHost(actionRequest);
			}
			else if (cmd.equals("workflow")) {
				updateWorkflow(actionRequest);
			}

			String redirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			if ((layout != null) && Validator.isNotNull(oldFriendlyURL)) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)actionRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				if (layout.getPlid() == themeDisplay.getPlid()) {
					Group group = layout.getGroup();

					String oldPath = group.getFriendlyURL() + oldFriendlyURL;
					String newPath =
						group.getFriendlyURL() + layout.getFriendlyURL();

					redirect = StringUtil.replace(redirect, oldPath, newPath);

					redirect = StringUtil.replace(
						redirect, HttpUtil.encodeURL(oldPath),
						HttpUtil.encodeURL(newPath));
				}
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException ||
				e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.communities.error");
			}
			else if (e instanceof RemoteExportException) {
				SessionErrors.add(actionRequest, e.getClass().getName(), e);

				String redirect = ParamUtil.getString(
					actionRequest, "pagesRedirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (e instanceof ImageTypeException ||
					 e instanceof LayoutFriendlyURLException ||
					 e instanceof LayoutHiddenException ||
					 e instanceof LayoutNameException ||
					 e instanceof LayoutParentLayoutIdException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LayoutTypeException ||
					 e instanceof RequiredLayoutException ||
					 e instanceof UploadException) {

				if (e instanceof LayoutFriendlyURLException) {
					SessionErrors.add(
						actionRequest,
						LayoutFriendlyURLException.class.getName(), e);
				}
				else {
					SessionErrors.add(actionRequest, e.getClass().getName(), e);
				}
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.communities.error");
		}

		try {
			getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.communities.edit_pages"));
	}

	public void serveResource(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		PortletRequestDispatcher portletRequestDispatcher =
			portletConfig.getPortletContext().getRequestDispatcher(
				"/html/portlet/communities/scheduled_publishing_events.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		// LEP-850

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Group group = getGroup(portletRequest);

		if (group == null) {
			throw new PrincipalException();
		}

		Layout layout = themeDisplay.getLayout();

		boolean hasUpdateLayoutPermission = false;

		if (layout != null) {
			hasUpdateLayoutPermission = LayoutPermissionUtil.contains(
				permissionChecker, layout.getGroupId(),
				layout.isPrivateLayout(), layout.getLayoutId(),
				ActionKeys.UPDATE);
		}

		if (group.isCommunity()) {
			String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

			boolean publishToLive =
				GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.PUBLISH_STAGING) &&
				cmd.equals("publish_to_live");

			if (!GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.APPROVE_PROPOSAL) &&
				!GroupPermissionUtil.contains(
					permissionChecker, group.getGroupId(),
					ActionKeys.MANAGE_LAYOUTS) &&
				!hasUpdateLayoutPermission && !publishToLive) {

				throw new PrincipalException();
			}
		}
		else if (group.isCompany()) {
			if (!permissionChecker.isCompanyAdmin()) {
				throw new PrincipalException();
			}
		}
		else if (group.isLayoutPrototype()) {
			LayoutPrototypePermissionUtil.check(
				permissionChecker, group.getClassPK(), ActionKeys.UPDATE);
		}
		else if (group.isLayoutSetPrototype()) {
			LayoutSetPrototypePermissionUtil.check(
				permissionChecker, group.getClassPK(), ActionKeys.UPDATE);
		}
		else if (group.isOrganization()) {
			long organizationId = group.getClassPK();

			String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

			boolean publishToLive =
				OrganizationPermissionUtil.contains(
					permissionChecker, organizationId,
					ActionKeys.PUBLISH_STAGING) &&
				cmd.equals("publish_to_live");

			if (!OrganizationPermissionUtil.contains(
					permissionChecker, organizationId,
					ActionKeys.APPROVE_PROPOSAL) &&
				!OrganizationPermissionUtil.contains(
					permissionChecker, organizationId,
					ActionKeys.MANAGE_LAYOUTS) &&
				!hasUpdateLayoutPermission && !publishToLive) {

				throw new PrincipalException();
			}
		}
		else if (group.isUser()) {
			long groupUserId = group.getClassPK();

			User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

			long[] organizationIds = groupUser.getOrganizationIds();

			UserPermissionUtil.check(
				permissionChecker, groupUserId, organizationIds,
				ActionKeys.UPDATE);

			if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE &&
				 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE) {

				throw new PrincipalException();
			}
		}
	}

	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		return ActionUtil.getGroup(portletRequest);
	}

	protected UnicodeProperties getTypeSettingsProperties(
		ActionRequest actionRequest) {

		return PropertiesParamUtil.getProperties(
			actionRequest, "TypeSettingsProperties--");
	}

	protected void updateDisplayOrder(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");
		long[] layoutIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "layoutIds"), 0L);

		LayoutServiceUtil.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds);
	}

	protected Object[] updateLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		String cmd = ParamUtil.getString(uploadRequest, Constants.CMD);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
		long parentLayoutId = ParamUtil.getLong(
			uploadRequest, "parentLayoutId");
		String description = ParamUtil.getString(uploadRequest, "description");
		String type = ParamUtil.getString(uploadRequest, "type");
		boolean hidden = ParamUtil.getBoolean(uploadRequest, "hidden");
		String friendlyURL = ParamUtil.getString(uploadRequest, "friendlyURL");
		boolean iconImage = ParamUtil.getBoolean(uploadRequest, "iconImage");
		byte[] iconBytes = FileUtil.getBytes(
			uploadRequest.getFile("iconFileName"));
		long layoutPrototypeId = ParamUtil.getLong(
			uploadRequest, "layoutPrototypeId");

		boolean inheritFromParentLayoutId = ParamUtil.getBoolean(
			uploadRequest, "inheritFromParentLayoutId");

		long copyLayoutId = ParamUtil.getLong(uploadRequest, "copyLayoutId");

		Map<Locale, String> localeNamesMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "name");
		Map<Locale, String> localeTitlesMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "title");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = null;
		String oldFriendlyURL = StringPool.BLANK;

		if (cmd.equals(Constants.ADD)) {

			// Add layout

			if (inheritFromParentLayoutId && (parentLayoutId > 0)) {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					groupId, privateLayout, parentLayoutId);

				layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, parentLayout.getType(),
					parentLayout.isHidden(), friendlyURL,
					serviceContext);

				LayoutServiceUtil.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(), parentLayout.getTypeSettings());

				if (parentLayout.isTypePortlet()) {
					ActionUtil.copyPreferences(
						actionRequest, layout, parentLayout);
				}
			}
			else if (layoutPrototypeId > 0) {
				LayoutPrototype layoutPrototype =
					LayoutPrototypeServiceUtil.getLayoutPrototype(
						layoutPrototypeId);

				Layout layoutPrototypeLayout = layoutPrototype.getLayout();

				layout = LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, LayoutConstants.TYPE_PORTLET,
					false, friendlyURL, serviceContext);

				LayoutServiceUtil.updateLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getLayoutId(),
					layoutPrototypeLayout.getTypeSettings());

				ActionUtil.copyPreferences(
					actionRequest, layout, layoutPrototypeLayout);

				if (Validator.isNotNull(layoutPrototypeLayout.getThemeId())) {
					LayoutServiceUtil.updateLookAndFeel(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(),
						layoutPrototypeLayout.getThemeId(),
						layoutPrototypeLayout.getColorSchemeId(),
						layoutPrototypeLayout.getCss(),	false);
				}

				if (Validator.isNotNull(
						layoutPrototypeLayout.getWapThemeId())) {

					LayoutServiceUtil.updateLookAndFeel(
						layout.getGroupId(), layout.isPrivateLayout(),
						layout.getLayoutId(),
						layoutPrototypeLayout.getWapThemeId(),
						layoutPrototypeLayout.getWapColorSchemeId(),
						layoutPrototypeLayout.getCss(),	true);
				}
			}
			else {
				LayoutServiceUtil.addLayout(
					groupId, privateLayout, parentLayoutId, localeNamesMap,
					localeTitlesMap, description, type, hidden, friendlyURL,
					serviceContext);
			}
		}
		else {

			// Update layout

			layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);

			oldFriendlyURL = layout.getFriendlyURL();

			layout = LayoutServiceUtil.updateLayout(
				groupId, privateLayout, layoutId, layout.getParentLayoutId(),
				localeNamesMap, localeTitlesMap, description, type, hidden,
				friendlyURL, Boolean.valueOf(iconImage), iconBytes,
				serviceContext);

			if (oldFriendlyURL.equals(layout.getFriendlyURL())) {
				oldFriendlyURL = StringPool.BLANK;
			}

			UnicodeProperties formTypeSettingsProperties =
				getTypeSettingsProperties(actionRequest);

			if (type.equals(LayoutConstants.TYPE_PORTLET)) {
				if ((copyLayoutId > 0) &&
					(copyLayoutId != layout.getLayoutId())) {

					try {
						Layout copyLayout = LayoutLocalServiceUtil.getLayout(
							groupId, privateLayout, copyLayoutId);

						if (copyLayout.isTypePortlet()) {
							LayoutServiceUtil.updateLayout(
								groupId, privateLayout, layoutId,
								copyLayout.getTypeSettings());

							ActionUtil.copyPreferences(
								actionRequest, layout, copyLayout);
						}
					}
					catch (NoSuchLayoutException nsle) {
					}
				}
				else {
					UnicodeProperties layoutTypeSettingsProperties =
						layout.getTypeSettingsProperties();

					for (String property: formTypeSettingsProperties.keySet()) {
						layoutTypeSettingsProperties.setProperty(
							property,
							formTypeSettingsProperties.getProperty(property));
					}

					LayoutServiceUtil.updateLayout(
						groupId, privateLayout, layoutId,
						layout.getTypeSettings());
				}
			}
			else {
				layout.setTypeSettingsProperties(formTypeSettingsProperties);

				LayoutServiceUtil.updateLayout(
					groupId, privateLayout, layoutId, layout.getTypeSettings());
			}

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			LayoutSettings layoutSettings = LayoutSettings.getInstance(layout);

			EventsProcessorUtil.process(
				PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
				layoutSettings.getConfigurationActionUpdate(), uploadRequest,
				response);
		}

		return new Object[] {layout, oldFriendlyURL};
	}

	protected void updateLogo(ActionRequest actionRequest) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(
			actionRequest);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		boolean logo = ParamUtil.getBoolean(actionRequest, "logo");

		File file = uploadRequest.getFile("logoFileName");
		byte[] bytes = FileUtil.getBytes(file);

		if (logo && ((bytes == null) || (bytes.length == 0))) {
			throw new UploadException();
		}

		LayoutSetServiceUtil.updateLogo(liveGroupId, privateLayout, logo, file);

		if (stagingGroupId > 0) {
			LayoutSetServiceUtil.updateLogo(
				stagingGroupId, privateLayout, logo, file);
		}
	}

	protected void updateLookAndFeel(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long layoutId = ParamUtil.getLong(actionRequest, "layoutId");
		String themeId = ParamUtil.getString(actionRequest, "themeId");
		String colorSchemeId = ParamUtil.getString(
			actionRequest, "colorSchemeId");
		String css = ParamUtil.getString(actionRequest, "css");
		boolean wapTheme = ParamUtil.getBoolean(actionRequest, "wapTheme");

		if (stagingGroupId > 0) {
			updateLookAndFeel(
				companyId, stagingGroupId, privateLayout, layoutId, themeId,
				colorSchemeId, css, wapTheme);
		}
		else {
			updateLookAndFeel(
				companyId, liveGroupId, privateLayout, layoutId, themeId,
				colorSchemeId, css, wapTheme);
		}
	}

	protected void updateLookAndFeel(
			long companyId, long groupId, boolean privateLayout, long layoutId,
			String themeId, String colorSchemeId, String css, boolean wapTheme)
		throws Exception {

		if (Validator.isNotNull(themeId) && Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = ThemeLocalServiceUtil.getColorScheme(
				companyId, themeId, colorSchemeId, wapTheme);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		if (layoutId <= 0) {
			LayoutSetServiceUtil.updateLookAndFeel(
				groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);
		}
		else {
			LayoutServiceUtil.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
				wapTheme);
		}
	}

	protected void updateMergePages(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		boolean mergeGuestPublicPages = ParamUtil.getBoolean(
			actionRequest, "mergeGuestPublicPages");

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		UnicodeProperties props = liveGroup.getTypeSettingsProperties();

		props.setProperty(
			"mergeGuestPublicPages", String.valueOf(mergeGuestPublicPages));

		GroupServiceUtil.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateMonitoring(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		String googleAnalyticsId = ParamUtil.getString(
			actionRequest, "googleAnalyticsId");

		Group liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

		UnicodeProperties props = liveGroup.getTypeSettingsProperties();

		props.setProperty("googleAnalyticsId", googleAnalyticsId);

		GroupServiceUtil.updateGroup(liveGroupId, liveGroup.getTypeSettings());
	}

	protected void updateVirtualHost(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		String publicVirtualHost = ParamUtil.getString(
			actionRequest, "publicVirtualHost");
		String privateVirtualHost = ParamUtil.getString(
			actionRequest, "privateVirtualHost");
		String friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, false, publicVirtualHost);

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroupId, true, privateVirtualHost);

		GroupServiceUtil.updateFriendlyURL(liveGroupId, friendlyURL);

		Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			publicVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPublicVirtualHost");
			privateVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPrivateVirtualHost");
			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL");

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), false, publicVirtualHost);

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), true, privateVirtualHost);

			GroupServiceUtil.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);
		}
	}

	protected void updateWorkflow(ActionRequest actionRequest)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		boolean workflowEnabled = ParamUtil.getBoolean(
			actionRequest, "workflowEnabled");
		int workflowStages = ParamUtil.getInteger(
			actionRequest, "workflowStages");

		String workflowRoleNames;

		if (workflowStages == 0) {
			workflowRoleNames = StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(workflowStages * 2 - 1);

			for (int i = 1; i <= workflowStages; i++) {
				String workflowRoleName = ParamUtil.getString(
					actionRequest, "workflowRoleName_" + i);

				sb.append(workflowRoleName);

				if ((i + 1) <= workflowStages) {
					sb.append(",");
				}
			}

			workflowRoleNames = sb.toString();
		}

		GroupServiceUtil.updateWorkflow(
			liveGroupId, workflowEnabled, workflowStages, workflowRoleNames);
	}

}