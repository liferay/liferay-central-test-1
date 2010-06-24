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

package com.liferay.portlet.journal.asset;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="JournalArticleAssetRenderer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Juan Fernández
 */
public class JournalArticleAssetRenderer extends BaseAssetRenderer {

	public JournalArticleAssetRenderer(JournalArticle article) {
		_article = article;
	}

	public String[] getAvailableLocales() {
		return _article.getAvailableLocales();
	}

	public long getClassPK() {
		return _article.getResourcePrimKey();
	}

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

	public String getSummary() {
		return HtmlUtil.stripHtml(_article.getContent());
	}

	public String getTitle() {
			return _article.getTitle();
	}

	public PortletURL getURLEdit(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL editPortletURL = liferayPortletResponse.createRenderURL(
			PortletKeys.JOURNAL);

		editPortletURL.setParameter(
			"struts_action", "/journal/edit_article");
		editPortletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		editPortletURL.setParameter(
			"articleId", _article.getArticleId());
		editPortletURL.setParameter(
			"version", String.valueOf(_article.getVersion()));

		return editPortletURL;
	}

	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/export_journal_article");
		exportPortletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		exportPortletURL.setParameter("articleId", _article.getArticleId());

		return exportPortletURL;
	}

	public String getUrlTitle() {
		return _article.getUrlTitle();
	}

	public String getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String noSuchEntryRedirect)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String languageId = LanguageUtil.getLanguageId(liferayPortletRequest);

		JournalArticleDisplay articleDisplay =
			JournalContentUtil.getDisplay(
				_article.getGroupId(), _article.getArticleId(),
				null, null, languageId, themeDisplay);

		String viewURL = StringPool.BLANK;

		if (articleDisplay != null) {

			PortletURL viewPortletURL =
				liferayPortletResponse.createRenderURL();

			viewPortletURL.setParameter(
				"struts_action", "/asset_publisher/view_content");
			viewPortletURL.setParameter("urlTitle", _article.getUrlTitle());
			viewPortletURL.setParameter(
				"type", JournalArticleAssetRendererFactory.TYPE);

			viewURL = viewPortletURL.toString();
		}

		return viewURL;
	}

	public long getUserId() {
		return _article.getUserId();
	}

	public String getUuid() {
		return _article.getUuid();
	}

	public String getViewInContextMessage() {
		return "view";
	}

	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker,_article, ActionKeys.UPDATE);
	}

	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return JournalArticlePermission.contains(
			permissionChecker,_article, ActionKeys.VIEW);
	}

	public boolean isConvertible() {
		return true;
	}

	public boolean isLocalizable() {
		return true;
	}

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

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/history.png";
	}

	private JournalArticle _article;

}