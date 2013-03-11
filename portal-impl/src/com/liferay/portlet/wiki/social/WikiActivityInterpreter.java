/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.social;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.trash.util.TrashUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

/**
 * @author Samuel Kong
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class WikiActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!WikiPagePermission.contains(
				permissionChecker, activity.getClassPK(), ActionKeys.VIEW)) {

			return null;
		}

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				activity.getClassPK());

		int activityType = activity.getType();

		if (activityType == WikiActivityKeys.UPDATE_PAGE) {
			double version = GetterUtil.getDouble(
				activity.getExtraDataProperty("version"));

			WikiPage page = WikiPageLocalServiceUtil.getPage(
				pageResource.getNodeId(), pageResource.getTitle(), version);

			if (!page.isApproved() &&
				!WikiPagePermission.contains(
					permissionChecker, activity.getClassPK(),
					ActionKeys.UPDATE)) {

				return null;
			}
		}

		String groupName = StringPool.BLANK;

		if (activity.getGroupId() != themeDisplay.getScopeGroupId()) {
			groupName = getGroupName(activity.getGroupId(), themeDisplay);
		}

		String creatorUserName = getUserName(
			activity.getUserId(), themeDisplay);

		// Link

		String link = getLink(
			WikiPage.class.getName(), pageResource.getResourcePrimKey(),
			"/wiki/find_page?pageResourcePrimKey=", themeDisplay);

		// Title

		Object[] titleArguments = new Object[] {
			groupName, creatorUserName,
			wrapLink(link, HtmlUtil.escape(getTitle(pageResource))),
			getAttachmentTitle(activity, pageResource, themeDisplay)
		};

		String title = themeDisplay.translate(
			getTitlePattern(groupName, activityType), titleArguments);

		// Body

		String body = StringPool.BLANK;

		return new SocialActivityFeedEntry(link, title, body);
	}

	protected String getAttachmentTitle(
			SocialActivity activity, WikiPageResource pageResource,
			ThemeDisplay themeDisplay)
		throws Exception {

		String attachmentTitle = StringPool.BLANK;

		int activityType = activity.getType();

		if ((activityType == SocialActivityConstants.TYPE_ADD_ATTACHMENT) ||
			(activityType ==
				SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) ||
			(activityType ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH)) {

			FileEntry fileEntry = null;

			try {
				long fileEntryId =
					GetterUtil.getLong(
						activity.getExtraDataProperty("fileEntryId"));

				fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
					fileEntryId);
			}
			catch (NoSuchModelException nsme) {
			}

			FileVersion fileVersion = null;

			if (fileEntry != null) {
				fileVersion = fileEntry.getFileVersion();
			}

			String fileEntryTitle = activity.getExtraDataProperty("title");

			if ((fileVersion != null) && !fileVersion.isInTrash()) {
				StringBundler sb = new StringBundler(9);

				sb.append(themeDisplay.getPathMain());
				sb.append("/wiki/get_page_attachment?p_l_id=");
				sb.append(themeDisplay.getPlid());
				sb.append("&nodeId=");
				sb.append(pageResource.getNodeId());
				sb.append("&title=");
				sb.append(HttpUtil.encodeURL(pageResource.getTitle()));
				sb.append("&fileName=");
				sb.append(fileEntryTitle);

				attachmentTitle = wrapLink(
					sb.toString(), HtmlUtil.escape(fileEntryTitle));
			}
			else {
				attachmentTitle = HtmlUtil.escape(fileEntryTitle);
			}
		}

		return attachmentTitle;
	}

	protected String getTitle(WikiPageResource pageResource) throws Exception {
		if (TrashUtil.isInTrash(
				WikiPage.class.getName(), pageResource.getResourcePrimKey())) {

			return TrashUtil.getOriginalTitle(pageResource.getTitle());
		}
		else {
			return pageResource.getTitle();
		}
	}

	protected String getTitlePattern(String groupName, int activityType) {
		if ((activityType == WikiActivityKeys.ADD_COMMENT) ||
			(activityType == SocialActivityConstants.TYPE_ADD_COMMENT)) {

			if (Validator.isNull(groupName)) {
				return "activity-wiki-add-comment";
			}
			else {
				return "activity-wiki-add-comment-in";
			}
		}
		else if (activityType == WikiActivityKeys.ADD_PAGE) {
			if (Validator.isNull(groupName)) {
				return "activity-wiki-add-page";
			}
			else {
				return "activity-wiki-add-page-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_ADD_ATTACHMENT) {
			if (Validator.isNull(groupName)) {
				return "activity-wiki-add-attachment";
			}
			else {
				return "activity-wiki-add-attachment-in";
			}
		}
		else if (
			activityType ==
				SocialActivityConstants.TYPE_MOVE_ATTACHMENT_TO_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-wiki-remove-attachment";
			}
			else {
				return "activity-wiki-remove-attachment-in";
			}
		}
		else if (
			activityType ==
				SocialActivityConstants.TYPE_RESTORE_ATTACHMENT_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-wiki-restore-attachment";
			}
			else {
				return "activity-wiki-restore-attachment-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-wiki-move-to-trash";
			}
			else {
				return "activity-wiki-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-wiki-restore-from-trash";
			}
			else {
				return "activity-wiki-restore-from-trash-in";
			}
		}
		else if (activityType == WikiActivityKeys.UPDATE_PAGE) {
			if (Validator.isNull(groupName)) {
				return "activity-wiki-update-page";
			}
			else {
				return "activity-wiki-update-page-in";
			}
		}

		return null;
	}

	private static final String[] _CLASS_NAMES = new String[] {
		WikiPage.class.getName()
	};

}