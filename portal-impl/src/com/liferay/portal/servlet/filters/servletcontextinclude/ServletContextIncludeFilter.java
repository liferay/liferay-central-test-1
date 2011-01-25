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

package com.liferay.portal.servlet.filters.servletcontextinclude;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ThemeHelper;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ThemeLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Raymond Augé
 */
public class ServletContextIncludeFilter extends BasePortalFilter {

	public boolean isFilterEnabled(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			String requestPath = ThemeHelper.getRequestPath(
				getFilterConfig().getServletContext(), request);

			if (!requestPath.endsWith(".jsp")) {
				return false;
			}

			String servletContextIncludePath = (String)request.getAttribute(
				WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_PATH);

			if ((servletContextIncludePath != null) &&
				servletContextIncludePath.equals(requestPath)) {

				return false;
			}

			Theme theme = (Theme)request.getAttribute(
				WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_THEME);

			if (theme == null) {
				theme = getTheme(request);
			}

			boolean resourceExists = ThemeHelper.resourceExists(
				getFilterConfig().getServletContext(), theme, requestPath);

			if ((theme != null) && resourceExists) {
				request.setAttribute(
					WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_THEME, theme);

				return true;
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected Theme getTheme(HttpServletRequest request) throws Exception {
		String themeId = ParamUtil.getString(request, "themeId");

		if (Validator.isNotNull(themeId)) {
			long companyId = PortalUtil.getCompanyId(request);

			return ThemeLocalServiceUtil.getTheme(companyId, themeId, false);
		}

		long plid = ParamUtil.getLong(request, "plid");

		if (plid <= 0) {
			plid = ParamUtil.getLong(request, "p_l_id");
		}

		if (plid > 0) {
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			return layout.getTheme();
		}

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		if (theme != null) {
			return theme;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return themeDisplay.getTheme();
		}

		LayoutSet layoutSet = (LayoutSet)request.getAttribute(
			WebKeys.VIRTUAL_HOST_LAYOUT_SET);

		if (layoutSet != null) {
			return layoutSet.getTheme();
		}

		return null;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		FilterConfig filterConfig = getFilterConfig();

		ServletContext servletContext = filterConfig.getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				"/WEB-INF/jsp/_servlet_context_include.jsp");

		String requestPath = ThemeHelper.getRequestPath(
			servletContext, request);

		Theme theme = (Theme)request.getAttribute(
			WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_THEME);

		if (!ThemeHelper.resourceExists(servletContext, theme, requestPath)) {
			return;
		}

		request.setAttribute(
			WebKeys.SERVLET_CONTEXT_INCLUDE_FILTER_PATH, requestPath);

		request.setAttribute(WebKeys.THEME, theme);

		requestDispatcher.include(request, response);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextIncludeFilter.class);

}