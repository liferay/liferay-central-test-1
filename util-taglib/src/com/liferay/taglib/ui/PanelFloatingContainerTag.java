/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * <a href="PanelFloatingContainerTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PanelFloatingContainerTag extends BodyTagSupport {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute(
			"liferay-ui:panel-floating-container:id", _id);
		request.setAttribute(
			"liferay-ui:panel-floating-container:trigger", _trigger);
		request.setAttribute(
			"liferay-ui:panel-floating-container:accordion",
			String.valueOf(_accordion));
		request.setAttribute(
			"liferay-ui:panel-floating-container:persistState",
			String.valueOf(_persistState));
		request.setAttribute(
			"liferay-ui:panel-floating-container:paging",
			String.valueOf(_paging));
		request.setAttribute(
			"liferay-ui:panel-floating-container:pagingElements",
			_pagingElements);
		request.setAttribute(
			"liferay-ui:panel-floating-container:resultsPerPage",
			String.valueOf(_resultsPerPage));
		request.setAttribute(
			"liferay-ui:panel-floating-container:width",
			String.valueOf(_width));
		request.setAttribute(
			"liferay-ui:panel-floating-container:extended", _extended);
		request.setAttribute(
			"liferay-ui:panel-floating-container:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:panel-container:panel-count", new IntegerWrapper());

		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() {
		BodyContent bodyContent = getBodyContent();

		_bodyContentString = bodyContent.getString();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:panel-container:panel-count");

		if ((panelCount != null) && (panelCount.getValue() == 1)) {

			bodyContent.clearBody();

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			IntegerWrapper panelCount = (IntegerWrapper)request.getAttribute(
				"liferay-ui:panel-container:panel-count");

			request.removeAttribute("liferay-ui:panel-container:panel-count");

			if ((panelCount != null) && (panelCount.getValue() >= 1)) {
				PortalIncludeUtil.include(pageContext, getStartPage());
			}

			pageContext.getOut().print(_bodyContentString);

			if ((panelCount != null) && (panelCount.getValue() >= 1)) {
				PortalIncludeUtil.include(pageContext, getEndPage());
			}

			request.removeAttribute("liferay-ui:panel-floating-container:id");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:trigger");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:accordion");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:persistState");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:paging");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:pagingElements");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:resultsPerPage");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:width");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:extended");
			request.removeAttribute(
				"liferay-ui:panel-floating-container:cssClass");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setTrigger(String trigger) {
		_trigger = trigger;
	}

	public void setAccordion(boolean accordion) {
		_accordion = accordion;
	}

	public void setPersistState(boolean persistState) {
		_persistState = persistState;
	}

	public void setPaging(boolean paging) {
		_paging = paging;
	}

	public void setPagingElements(String pagingElements) {
		_pagingElements = pagingElements;
	}

	public void setResultsPerPage(int resultsPerPage) {
		_resultsPerPage = resultsPerPage;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public void setExtended(Boolean extended) {
		_extended = extended;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	private static final String _START_PAGE =
		"/html/taglib/ui/panel_floating_container/start.jsp";

	private static final String _END_PAGE =
		"/html/taglib/ui/panel_floating_container/end.jsp";

	private String _startPage;
	private String _endPage;
	private String _id;
	private String _trigger;
	private boolean _accordion;
	private boolean _persistState;
	private boolean _paging;
	private String _pagingElements = "ul";
	private int _resultsPerPage = 1;
	private int _width = 300;
	private Boolean _extended;
 	private String _cssClass = StringPool.BLANK;
 	private String _bodyContentString = StringPool.BLANK;

}