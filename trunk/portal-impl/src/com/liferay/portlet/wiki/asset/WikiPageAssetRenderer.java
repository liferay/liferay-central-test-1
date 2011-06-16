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

package com.liferay.portlet.wiki.asset;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.model.BaseAssetRenderer;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Julio Camarero
 * @author Sergio González
 */
public class WikiPageAssetRenderer extends BaseAssetRenderer {

	public WikiPageAssetRenderer(WikiPage page) {
		_page = page;
	}

	public long getClassPK() {
		if (!_page.isApproved() &&
			(_page.getVersion() != WikiPageConstants.DEFAULT_VERSION)) {

			return _page.getPageId();
		}
		else {
			return _page.getResourcePrimKey();
		}
	}

	@Override
	public String getDiscussionPath() {
		if (PropsValues.WIKI_PAGE_COMMENTS_ENABLED) {
			return "edit_page_discussion";
		}
		else {
			return null;
		}
	}

	public long getGroupId() {
		return _page.getGroupId();
	}

	public String getSummary(Locale locale) {
		String content = _page.getContent();

		String format = _page.getFormat();

		if (format.equals("html")) {
			content = HtmlUtil.stripHtml(content);
		}
		else {
			content = HtmlUtil.escape(content);
		}

		return content;
	}

	public String getTitle(Locale locale) {
		return _page.getTitle();
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest), PortletKeys.WIKI,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/wiki/edit_page");
		portletURL.setParameter("nodeId", String.valueOf(_page.getNodeId()));
		portletURL.setParameter("title", _page.getTitle());

		return portletURL;
	}

	@Override
	public PortletURL getURLExport(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		PortletURL exportPortletURL = liferayPortletResponse.createActionURL();

		exportPortletURL.setParameter(
			"struts_action", "/asset_publisher/export_wiki_page");
		exportPortletURL.setParameter(
			"nodeId", String.valueOf(_page.getNodeId()));
		exportPortletURL.setParameter("title", _page.getTitle());

		return exportPortletURL;
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathMain() +
			"/wiki/find_page?pageResourcePrimKey=" + _page.getResourcePrimKey();
	}

	public long getUserId() {
		return _page.getUserId();
	}

	public String getUuid() {
		return _page.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) {
		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) {
		return WikiPagePermission.contains(
			permissionChecker, _page, ActionKeys.VIEW);
	}

	@Override
	public boolean isConvertible() {
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

			renderRequest.setAttribute(WebKeys.WIKI_PAGE, _page);

			return "/html/portlet/wiki/asset/" + template + ".jsp";
		}
		else {
			return null;
		}
	}

	@Override
	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/pages.png";
	}

	private WikiPage _page;

}