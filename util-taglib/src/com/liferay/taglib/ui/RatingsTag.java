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

import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="RatingsTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class RatingsTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setNumberOfStars(int numberOfStars) {
		_numberOfStars = numberOfStars;
	}

	public void setRatingsEntry(RatingsEntry ratingsEntry) {
		if (ratingsEntry == null) {
			_ratingsEntry = _EMPTY_RATINGS_ENTRY;
		}
		else {
			_ratingsEntry = ratingsEntry;
		}
	}

	public void setRatingsStats(RatingsStats ratingsStats) {
		_ratingsStats = ratingsStats;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUrl(String url) {
		_url = url;
	}

	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_numberOfStars = 5;
		_ratingsEntry = null;
		_ratingsStats = null;
		_type = "stars";
		_url = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:ratings:className", _className);
		request.setAttribute(
			"liferay-ui:ratings:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:ratings:numberOfStars", String.valueOf(_numberOfStars));
		request.setAttribute("liferay-ui:ratings:ratingsEntry", _ratingsEntry);
		request.setAttribute("liferay-ui:ratings:ratingsStats", _ratingsStats);
		request.setAttribute("liferay-ui:ratings:type", _type);
		request.setAttribute("liferay-ui:ratings:url", _url);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final RatingsEntry _EMPTY_RATINGS_ENTRY =
		new RatingsEntryImpl();

	private static final String _PAGE = "/html/taglib/ui/ratings/page.jsp";

	private String _className;
	private long _classPK;
	private int _numberOfStars = 5;
	private RatingsEntry _ratingsEntry;
	private RatingsStats _ratingsStats;
	private String _type = "stars";
	private String _url;

}