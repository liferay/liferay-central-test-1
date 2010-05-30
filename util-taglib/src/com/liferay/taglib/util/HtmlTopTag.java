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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.BaseBodyTagSupport;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="HtmlTopTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class HtmlTopTag extends BaseBodyTagSupport {

	public int doStartTag() {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		StringBuilder sb = (StringBuilder)request.getAttribute(
			WebKeys.PAGE_TOP);

		if (sb == null) {
			sb = new StringBuilder();

			request.setAttribute(WebKeys.PAGE_TOP, sb);
		}

		sb.append(getBodyContentAsStringBundler());

		return EVAL_PAGE;
	}

}