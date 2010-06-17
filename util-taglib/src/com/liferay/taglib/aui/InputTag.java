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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.util.PwdGenerator;
import com.liferay.util.TextFormatter;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="InputTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class InputTag extends IncludeTag {

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setChangesContext(boolean changesContext) {
		_changesContext = changesContext;
	}

	public void setChecked(boolean checked) {
		_checked = checked;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setInlineField(boolean inlineField) {
		_inlineField = inlineField;
	}

	public void setInlineLabel(String inlineLabel) {
		_inlineLabel = inlineLabel;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setOnclick(String onclick) {
		setOnClick(onclick);
	}

	public void setPrefix(String prefix) {
		_prefix = prefix;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(Object value) {
		_value = value;
	}

	protected void cleanUp() {
		_bean = null;
		_changesContext = false;
		_checked = false;
		_cssClass = null;
		_disabled = false;
		_field = null;
		_first = false;
		_helpMessage = null;
		_id = null;
		_inlineField = false;
		_inlineLabel = null;
		_label = null;
		_last = false;
		_model = null;
		_name = null;
		_onClick = null;
		_prefix = null;
		_suffix = null;
		_title = null;
		_type = null;
		_value = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		Object bean = _bean;

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		String field = _field;

		if (Validator.isNull(field)) {
			field = _name;
		}

		String id = _id;

		if (Validator.isNull(id)) {
			if (!Validator.equals(_type, "radio")) {
				id = _name;
			}
			else {
				id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
			}
		}

		String label = _label;

		if (label == null) {
			label = TextFormatter.format(_name, TextFormatter.K);
		}

		Class<?> model = _model;

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		request.setAttribute("aui:input:bean", bean);
		request.setAttribute(
			"aui:input:changesContext", String.valueOf(_changesContext));
		request.setAttribute("aui:input:checked", String.valueOf(_checked));
		request.setAttribute("aui:input:cssClass", _cssClass);
		request.setAttribute("aui:input:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:input:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:input:field", field);
		request.setAttribute("aui:input:first", String.valueOf(_first));
		request.setAttribute("aui:input:helpMessage", _helpMessage);
		request.setAttribute("aui:input:id", id);
		request.setAttribute(
			"aui:input:inlineField", String.valueOf(_inlineField));
		request.setAttribute("aui:input:inlineLabel", _inlineLabel);
		request.setAttribute("aui:input:label", label);
		request.setAttribute("aui:input:last", String.valueOf(_last));
		request.setAttribute("aui:input:model", model);
		request.setAttribute("aui:input:name", _name);
		request.setAttribute("aui:input:onClick", _onClick);
		request.setAttribute("aui:input:prefix", _prefix);
		request.setAttribute("aui:input:suffix", _suffix);
		request.setAttribute("aui:input:title", _title);
		request.setAttribute("aui:input:type", _type);
		request.setAttribute("aui:input:value", _value);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/aui/input/page.jsp";

	private Object _bean;
	private boolean _changesContext;
	private boolean _checked;
	private String _cssClass;
	private boolean _disabled;
	private String _field;
	private boolean _first;
	private String _helpMessage;
	private String _id;
	private boolean _inlineField;
	private String _inlineLabel;
	private String _label;
	private boolean _last;
	private Class<?> _model;
	private String _name;
	private String _onClick;
	private String _prefix;
	private String _suffix;
	private String _title;
	private String _type;
	private Object _value;

}