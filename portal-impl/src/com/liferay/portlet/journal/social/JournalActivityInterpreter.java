/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.social;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Roberto Diaz
 * @author Zsolt Berentey
 */
public class JournalActivityInterpreter extends BaseSocialActivityInterpreter {

	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getEntryTitle(
			SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			activity.getClassPK());

		return article.getTitle(themeDisplay.getLocale());
	}

	@Override
	protected String getLink(SocialActivity activity, ThemeDisplay themeDisplay)
		throws Exception {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			activity.getClassPK());

		if (TrashUtil.isInTrash(
				JournalArticle.class.getName(), article.getResourcePrimKey())) {

			return TrashUtil.getViewContentURL(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				themeDisplay);
		}

		JournalArticle lastestArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				article.getGroupId(), article.getArticleId());

		if (Validator.isNotNull(lastestArticle.getLayoutUuid()) &&
			!article.isInTrash()) {

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				themeDisplay.getScopeGroup(), false, themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					lastestArticle.getUrlTitle());
		}

		return null;
	}

	@Override
	protected String getTitlePattern(String groupName, SocialActivity activity)
		throws Exception {

		int activityType = activity.getType();

		if (activityType == JournalActivityKeys.ADD_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-add-article";
			}
			else {
				return "activity-journal-add-article-in";
			}
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-update-article";
			}
			else {
				return "activity-journal-update-article-in";
			}
		}
		else if (activityType == SocialActivityConstants.TYPE_MOVE_TO_TRASH) {
			if (Validator.isNull(groupName)) {
				return "activity-journal-move-to-trash";
			}
			else {
				return "activity-journal-move-to-trash-in";
			}
		}
		else if (activityType ==
					SocialActivityConstants.TYPE_RESTORE_FROM_TRASH) {

			if (Validator.isNull(groupName)) {
				return "activity-journal-restore-from-trash";
			}
			else {
				return "activity-journal-restore-from-trash-in";
			}
		}

		return null;
	}

	@Override
	protected boolean hasPermissions(
			PermissionChecker permissionChecker, SocialActivity activity,
			String actionId, ThemeDisplay themeDisplay)
		throws Exception {

		int activityType = activity.getType();

		if ((activityType == JournalActivityKeys.ADD_ARTICLE) &&
			!JournalPermission.contains(
				permissionChecker, activity.getGroupId(),
				ActionKeys.ADD_ARTICLE)) {

			return false;
		}
		else if (activityType == JournalActivityKeys.UPDATE_ARTICLE) {
			JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
				activity.getClassPK());

			if (!JournalArticlePermission.contains(
					permissionChecker, article, ActionKeys.UPDATE)) {

				return false;
			}
		}

		return true;
	}

	private static final String[] _CLASS_NAMES =
		{JournalArticle.class.getName()};

}