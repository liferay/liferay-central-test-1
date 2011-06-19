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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class OptionTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setLabel(Object label) {
		_label = String.valueOf(label);
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setStyle(String style) {
		_style = style;
	}

	public void setValue(Object value) {
		_value = String.valueOf(value);
	}

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_disabled = false;
		_label = null;
		_selected = false;
		_style = null;
		_value = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</option>");

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String value = _value;

		if (value == null) {
			value = _label;
		}

		boolean selected = _selected;

		String selectValue = GetterUtil.getString(
			(String)request.getAttribute("aui:select:value"));

		if (Validator.isNotNull(selectValue)) {
			selected = selectValue.equals(value);
		}

		request.setAttribute("aui:option:cssClass", _cssClass);
		request.setAttribute(
			"aui:option:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:option:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:option:label", _label);
		request.setAttribute("aui:option:selected", String.valueOf(selected));
		request.setAttribute("aui:option:style", _style);
		request.setAttribute("aui:option:value", value);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/html/taglib/aui/option/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/option/start.jsp";

	private String _cssClass;
	private boolean _disabled;
	private String _label;
	private boolean _selected;
	private String _style;
	private String _value;

}