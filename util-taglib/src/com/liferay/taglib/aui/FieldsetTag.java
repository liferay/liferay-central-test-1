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

package com.liferay.taglib.aui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="FieldsetTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class FieldsetTag extends IncludeTag {

	public void setColumn(boolean column) {
		_column = column;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setLabel(String label) {
		_label = label;
	}

	protected void cleanUp() {
		_column = false;
		_cssClass = null;
		_label = null;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("aui:fieldset:column", String.valueOf(_column));
		request.setAttribute("aui:fieldset:cssClass", _cssClass);
		request.setAttribute(
			"aui:fieldset:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:fieldset:label", _label);
	}

	private static final String _END_PAGE =
		"/html/taglib/aui/fieldset/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/fieldset/start.jsp";

	private boolean _column;
	private String _cssClass;
	private String _label;

}