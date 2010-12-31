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

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetTagsSelectorTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentCallback(String contentCallback) {
		_contentCallback = contentCallback;
	}

	public void setCurTags(String curTags) {
		_curTags = curTags;
	}

	public void setFocus(boolean focus) {
		_focus = focus;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_contentCallback = null;
		_curTags = null;
		_focus = false;
		_hiddenInput = "assetTagNames";
	}

	protected String getPage() {
		return _PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:asset-tags-selector:className", _className);
		request.setAttribute(
			"liferay-ui:asset-tags-selector:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:contentCallback",
			String.valueOf(_contentCallback));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:curTags", _curTags);
		request.setAttribute(
			"liferay-ui:asset-tags-selector:focus", String.valueOf(_focus));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:hiddenInput", _hiddenInput);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_tags_selector/page.jsp";

	private String _className;
	private long _classPK;
	private String _contentCallback;
	private String _curTags;
	private boolean _focus;
	private String _hiddenInput = "assetTagNames";

}