/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ddm.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Bruno Basto
 * @generated
 */
public class BaseHTMLTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public com.liferay.portlet.dynamicdatamapping.storage.Fields getFields() {
		return _fields;
	}

	public java.lang.String getFieldsNamespace() {
		return _fieldsNamespace;
	}

	public java.lang.String getMode() {
		return _mode;
	}

	public boolean getReadOnly() {
		return _readOnly;
	}

	public boolean getRepeatable() {
		return _repeatable;
	}

	public void setClassName(java.lang.String className) {
		_className = className;

		setScopedAttribute("className", className);
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		setScopedAttribute("classPK", classPK);
	}

	public void setFields(com.liferay.portlet.dynamicdatamapping.storage.Fields fields) {
		_fields = fields;

		setScopedAttribute("fields", fields);
	}

	public void setFieldsNamespace(java.lang.String fieldsNamespace) {
		_fieldsNamespace = fieldsNamespace;

		setScopedAttribute("fieldsNamespace", fieldsNamespace);
	}

	public void setMode(java.lang.String mode) {
		_mode = mode;

		setScopedAttribute("mode", mode);
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;

		setScopedAttribute("readOnly", readOnly);
	}

	public void setRepeatable(boolean repeatable) {
		_repeatable = repeatable;

		setScopedAttribute("repeatable", repeatable);
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_fields = null;
		_fieldsNamespace = null;
		_mode = null;
		_readOnly = false;
		_repeatable = true;
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
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "className", _className);
		setNamespacedAttribute(request, "classPK", _classPK);
		setNamespacedAttribute(request, "fields", _fields);
		setNamespacedAttribute(request, "fieldsNamespace", _fieldsNamespace);
		setNamespacedAttribute(request, "mode", _mode);
		setNamespacedAttribute(request, "readOnly", _readOnly);
		setNamespacedAttribute(request, "repeatable", _repeatable);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "ddm:html:";

	private static final String _END_PAGE =
		"/html/taglib/ddm/html/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ddm/html/start.jsp";

	private java.lang.String _className = null;
	private long _classPK = 0;
	private com.liferay.portlet.dynamicdatamapping.storage.Fields _fields = null;
	private java.lang.String _fieldsNamespace = null;
	private java.lang.String _mode = null;
	private boolean _readOnly = false;
	private boolean _repeatable = true;

}