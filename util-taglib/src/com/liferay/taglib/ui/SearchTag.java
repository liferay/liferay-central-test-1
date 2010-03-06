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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

/**
 * <a href="SearchTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchTag extends IncludeTag {

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _END_PAGE = "/html/taglib/ui/search/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/search/start.jsp";

}