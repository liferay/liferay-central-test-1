/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="TagsSelectorTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class TagsSelectorTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:tags_selector:className", _className);
		request.setAttribute(
			"liferay-ui:tags_selector:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:tags_selector:folksonomy", String.valueOf(_folksonomy));
		request.setAttribute(
			"liferay-ui:tags_selector:hiddenInput", _hiddenInput);
		request.setAttribute("liferay-ui:tags_selector:curTags", _curTags);
		request.setAttribute(
			"liferay-ui:tags_selector:focus", String.valueOf(_focus));
		request.setAttribute(
			"liferay-ui:tags_selector:contentCallback",
			String.valueOf(_contentCallback));

		return EVAL_BODY_BUFFERED;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setFolksonomy(boolean folksonomy) {
		_folksonomy = folksonomy;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setCurTags(String curTags) {
		_curTags = curTags;
	}

	public void setFocus(boolean focus) {
		_focus = focus;
	}

	public void setContentCallback(String contentCallback) {
		_contentCallback = contentCallback;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/tags_selector/page.jsp";

	private String _className;
	private long _classPK;
	private boolean _folksonomy = true;
	private String _hiddenInput;
	private String _curTags;
	private boolean _focus;
	private String _contentCallback;

}