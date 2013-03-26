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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Juan Fernández
 * @author Sergio González
 * @author Raymond Augé
 */
public class JournalArticleAssetRenderer
	extends BaseAssetRenderer implements TrashRenderer {

	public static final String TYPE = "journal_article";

	public static long getClassPK(JournalArticle article) {
		if ((article.isDraft() || article.isPending()) &&
			(article.getVersion() !=
				JournalArticleConstants.VERSION_DEFAULT)) {

			return article.getPrimaryKey();
		}
		else {
			return article.getResourcePrimKey();
		}
	}

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public JournalArticle getArticle() {
		return _article;
	}

	@Override
	public String[] getAvailableLocales() {
		return _article.getAvailableLocales();
	}

	public String getClassName() {
		return JournalArticle.class.getName();
	}

	public long getClassPK() {
		return getClassPK(_article);
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.JOURNAL_ARTICLE_COMMENTS_ENABLED) {
			return "edit_article_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _article.getGroupId();
	}

	public String getPortletId() {
		return PortletKeys.JOURNAL;
	}

	public String getSummary(Locale locale) {
		return _article.getDescription(locale);
	}

	@Override
	public String getThumbnailPath(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String thumbnailSrc = _article.getArticleImageURL(themeDisplay);

		if (Validator.isNotNull(thumbnailSrc)) {
			return thumbnailSrc;
		}

		return themeDisplay.getPathThemeImages() +
			"/file_system/large/article.png";
	}

	public String getTitle(Locale locale) {
		return _article.getTitle(locale);
	}

	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/journal/edit_article");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter(
			"version", String.valueOf(_article.getVersion()));

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			"struts_action", "/asset_publisher/export_journal_article");
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());

		return portletURL;
	}

	@Override
	public String getUrlTitle() {
		return _article.getUrlTitle();
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String portletId = (String)liferayPortletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		String linkToLayoutUuid = GetterUtil.getString(
			portletSetup.getValue("portletSetupLinkToLayoutUuid", null));

		if (Validator.isNotNull(_article.getLayoutUuid()) &&
			Validator.isNull(linkToLayoutUuid)) {

			Group group = themeDisplay.getScopeGroup();

			if (group.getGroupId() != _article.getGroupId()) {
				group = GroupLocalServiceUtil.getGroup(_article.getGroupId());
			}

			String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
				group, false, themeDisplay);

			return groupFriendlyURL.concat(
				JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
					_article.getUrlTitle());
		}

		List<Long> hitLayoutIds =
			JournalContentSearchLocalServiceUtil.getLayoutIds(
				layout.getGroupId(), layout.isPrivateLayout(),
				_article.getArticleId());

		if (!hitLayoutIds.isEmpty()) {
			Long hitLayoutId = hitLayoutIds.get(0);

			Layout hitLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				hitLayoutId.longValue());

			return PortalUtil.getLayoutURL(hitLayout, themeDisplay);
		}

		return noSuchEntryRedirect;
	}

	public long getUserId() {
		return _article.getUserId();
	}

	public String getUserName() {
		return _article.getUserName();
	}

	public String getUuid() {
		return _article.getUuid();
	}

	@Override
	public String getViewInContextMessage() {
		return "view";
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		return JournalArticlePermission.contains(
			permissionChecker, _article, ActionKeys.VIEW);
	}

	@Override
	public boolean isConvertible() {
		return true;
	}

	@Override
	public boolean isDisplayable() {
		Date now = new Date();

		Date displayDate = _article.getDisplayDate();

		if ((displayDate != null) && displayDate.after(now)) {
			return false;
		}

		Date expirationDate = _article.getExpirationDate();

		if ((expirationDate != null) && expirationDate.before(now)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isLocalizable() {
		return true;
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String template)
		throws Exception {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			renderRequest.setAttribute(WebKeys.JOURNAL_ARTICLE, _article);

			return "/html/portlet/journal/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private JournalArticle _article;

}