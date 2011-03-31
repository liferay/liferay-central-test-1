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

package com.liferay.taglib.aui.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public class BaseColumnTag extends com.liferay.taglib.util.IncludeTag {

	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public int getColumnWidth() {
		return _columnWidth;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public boolean getFirst() {
		return _first;
	}

	public boolean getLast() {
		return _last;
	}

	public void setColumnWidth(int columnWidth) {
		_columnWidth = columnWidth;

		setScopedAttribute("columnWidth", columnWidth);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setFirst(boolean first) {
		_first = first;

		setScopedAttribute("first", first);
	}

	public void setLast(boolean last) {
		_last = last;

		setScopedAttribute("last", last);
	}

	protected void cleanUp() {
		_columnWidth = 0;
		_cssClass = null;
		_first = false;
		_last = false;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "columnWidth", _columnWidth);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "first", _first);
		setNamespacedAttribute(request, "last", _last);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:column:";

	private static final String _END_PAGE =
		"/html/taglib/aui/column/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/column/start.jsp";

	private int _columnWidth;
	private java.lang.String _cssClass;
	private boolean _first;
	private boolean _last;

}