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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.io.FileCacheOutputStream;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.base.LayoutServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portlet.communities.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portlet.communities.messaging.LayoutsRemotePublisherRequest;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <a href="LayoutServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutServiceImpl extends LayoutServiceBaseImpl {

	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			String name, String title, String description, String type,
			boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), description, type, hidden,
			friendlyURL);
	}

	public Layout addLayout(
			long groupId, boolean privateLayout, long parentLayoutId,
			Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.addLayout(
			getUserId(), groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL);
	}

	public void deleteLayout(long plid)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.DELETE);

		layoutLocalService.deleteLayout(plid);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.DELETE);

		layoutLocalService.deleteLayout(groupId, privateLayout, layoutId);
	}

	public String getLayoutName(
			long groupId, boolean privateLayout, long layoutId,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		return layout.getName(languageId);
	}

	public LayoutReference[] getLayoutReferences(
			long companyId, String portletId, String preferencesKey,
			String preferencesValue)
		throws SystemException {

		return layoutLocalService.getLayouts(
			companyId, portletId, preferencesKey, preferencesValue);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, parameterMap, startDate, endDate);
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayouts(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	public FileCacheOutputStream exportLayoutsAsStream(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportLayoutsAsStream(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);
	}

	public byte[] exportPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportPortletInfo(
			plid, portletId, parameterMap, startDate, endDate);
	}

	public FileCacheOutputStream exportPortletInfoAsStream(
			long plid, String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		return layoutLocalService.exportPortletInfoAsStream(
			plid, portletId, parameterMap, startDate, endDate);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, file);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, bytes);
	}

	public void importLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importLayouts(
			getUserId(), groupId, privateLayout, parameterMap, is);
	}

	public void importPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			File file)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, portletId, parameterMap, file);
	}

	public void importPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			InputStream is)
		throws PortalException, SystemException {

		Layout layout = layoutLocalService.getLayout(plid);

		GroupPermissionUtil.check(
			getPermissionChecker(), layout.getGroupId(),
			ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.importPortletInfo(
			getUserId(), plid, portletId, parameterMap, is);
	}

	public void schedulePublishToLive(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			String scope, Date startDate, Date endDate, String groupName,
			String cronText, Date schedulerStartDate, Date schedulerEndDate,
			String description)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, targetGroupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, targetGroupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		String command = StringPool.BLANK;

		if (scope.equals("all-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES;
		}
		else if (scope.equals("selected-pages")) {
			command = LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES;
		}

		LayoutsLocalPublisherRequest publisherRequest =
			new LayoutsLocalPublisherRequest(
				command, getUserId(), sourceGroupId, targetGroupId,
				privateLayout, layoutIdMap, parameterMap, startDate, endDate);

		SchedulerEngineUtil.schedule(
			groupName, cronText, schedulerStartDate, schedulerEndDate,
			description, DestinationNames.LAYOUTS_LOCAL_PUBLISHER,
			JSONFactoryUtil.serialize(publisherRequest));
	}

	public void schedulePublishToRemote(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> parameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout, Date startDate, Date endDate,
			String groupName, String cronText, Date schedulerStartDate,
			Date schedulerEndDate, String description)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Group group = GroupLocalServiceUtil.getGroup(sourceGroupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isWorkflowEnabled() &&
			!GroupPermissionUtil.contains(
				permissionChecker, sourceGroupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, sourceGroupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}
		else {
			GroupPermissionUtil.check(
				permissionChecker, sourceGroupId, ActionKeys.MANAGE_LAYOUTS);
		}

		LayoutsRemotePublisherRequest publisherRequest =
			new LayoutsRemotePublisherRequest(
				getUserId(), sourceGroupId, privateLayout, layoutIdMap,
				parameterMap, remoteAddress, remotePort, secureConnection,
				remoteGroupId, remotePrivateLayout, startDate, endDate);

		SchedulerEngineUtil.schedule(
			groupName, cronText, schedulerStartDate, schedulerEndDate,
			description, DestinationNames.LAYOUTS_REMOTE_PUBLISHER,
			JSONFactoryUtil.serialize(publisherRequest));
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_LAYOUTS);

		layoutLocalService.setLayouts(
			groupId, privateLayout, parentLayoutId, layoutIds);
	}

	public void unschedulePublishToLive(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}

		SchedulerEngineUtil.unschedule(jobName, groupName);
	}

	public void unschedulePublishToRemote(
			long groupId, String jobName, String groupName)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isWorkflowEnabled() &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.MANAGE_STAGING) &&
			!GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.PUBLISH_STAGING)) {

			throw new PrincipalException();
		}
		else {
			GroupPermissionUtil.check(
				permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS);
		}

		SchedulerEngineUtil.unschedule(jobName, groupName);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL, iconImage,
			iconBytes);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateLayout(
			groupId, privateLayout, layoutId, typeSettings);
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		pluginSettingLocalService.checkPermission(
			getUserId(), themeId, Plugin.TYPE_THEME);

		return layoutLocalService.updateLookAndFeel(
			groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
			wapTheme);
	}

	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateName(plid, name, languageId);
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateName(
			groupId, privateLayout, layoutId, name, languageId);
	}

	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);
	}

	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), plid, ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(plid, priority);
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), groupId, privateLayout, layoutId,
			ActionKeys.UPDATE);

		return layoutLocalService.updatePriority(
			groupId, privateLayout, layoutId, priority);
	}

}