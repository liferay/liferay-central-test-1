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

package com.liferay.portal.servlet.filters.validhtml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.util.servlet.ServletResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ValidHtmlFilter extends BasePortalFilter {

	public static final String SKIP_FILTER =
		ValidHtmlFilter.class.getName() + "SKIP_FILTER";

	protected String getContent(HttpServletRequest request, String content) {
		content = StringUtil.replaceLast(
			content, _CLOSE_BODY, StringPool.BLANK);
		content = StringUtil.replaceLast(
			content, _CLOSE_HTML, _CLOSE_BODY + _CLOSE_HTML);

		return content;
	}

	protected boolean isAlreadyFiltered(HttpServletRequest request) {
		if (request.getAttribute(SKIP_FILTER) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isEnsureValidHtml(
		HttpServletRequest request, HttpServletResponse response) {

		String contentType = response.getContentType();

		if ((contentType != null) &&
			contentType.startsWith(ContentTypes.TEXT_HTML)) {

			return true;
		}
		else {
			return false;
		}
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		if (isEnsureValidHtml(request, response) &&
			!isAlreadyFiltered(request)) {

			request.setAttribute(SKIP_FILTER, Boolean.TRUE);

			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Ensuring valid HTML " + completeURL);
			}

			StringServletResponse stringServerResponse =
				new StringServletResponse(response);

			processFilter(
				ValidHtmlFilter.class, request, stringServerResponse,
				filterChain);

			String content = getContent(
				request, stringServerResponse.getString());

			ServletResponseUtil.write(response, content);
		}
		else {
			if (_log.isDebugEnabled()) {
				String completeURL = HttpUtil.getCompleteURL(request);

				_log.debug("Not ensuring valid HTML " + completeURL);
			}

			processFilter(
				ValidHtmlFilter.class, request, response, filterChain);
		}
	}

	private static final String _CLOSE_BODY = "</body>";

	private static final String _CLOSE_HTML = "</html>";

	private static Log _log = LogFactoryUtil.getLog(ValidHtmlFilter.class);

}