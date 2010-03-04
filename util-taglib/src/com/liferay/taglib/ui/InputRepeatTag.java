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

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="InputRepeatTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Julio Camarero
 */
public class InputRepeatTag extends IncludeTag {

	public int doEndTag() throws JspException {
		try {
			return super.doEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_cssClass = null;
				_event = null;
			}
		}
	}

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-repeat:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-repeat:event", _event);

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEvent(CalEvent event) {
		_event = event;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_repeat/page.jsp";

	private String _cssClass;
	private CalEvent _event;

}