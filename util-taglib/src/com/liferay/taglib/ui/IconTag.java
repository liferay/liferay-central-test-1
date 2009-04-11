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

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * <a href="IconTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IconTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:icon:image", _image);
		request.setAttribute("liferay-ui:icon:imageHover", _imageHover);
		request.setAttribute("liferay-ui:icon:message", _message);
		request.setAttribute("liferay-ui:icon:src", _src);
		request.setAttribute("liferay-ui:icon:srcHover", _srcHover);
		request.setAttribute("liferay-ui:icon:url", _url);
		request.setAttribute("liferay-ui:icon:method", _method);
		request.setAttribute("liferay-ui:icon:target", _target);
		request.setAttribute("liferay-ui:icon:label", String.valueOf(_label));
		request.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(_toolTip));
		request.setAttribute("liferay-ui:icon:cssClass", _cssClass);

		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		int value = super.doEndTag();

		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			request.removeAttribute("liferay-ui:icon:image");
			request.removeAttribute("liferay-ui:icon:imageHover");
			request.removeAttribute("liferay-ui:icon:message");
			request.removeAttribute("liferay-ui:icon:src");
			request.removeAttribute("liferay-ui:icon:srcHover");
			request.removeAttribute("liferay-ui:icon:url");
			request.removeAttribute("liferay-ui:icon:method");
			request.removeAttribute("liferay-ui:icon:target");
			request.removeAttribute("liferay-ui:icon:label");
			request.removeAttribute("liferay-ui:icon:toolTip");
			request.removeAttribute("liferay-ui:icon:cssClass");

			return value;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_image = null;
				_imageHover = null;
				_message = null;
				_src = null;
				_srcHover = null;
				_url = null;
				_method = null;
				_target = null;
				_label = false;
				_toolTip = false;
				_cssClass = null;
			}
		}
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setImageHover(String imageHover) {
		_imageHover = imageHover;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setSrc(String src) {
		_src = src;
	}

	public void setSrcHover(String srcHover) {
		_srcHover = srcHover;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setToolTip(boolean toolTip) {
		_toolTip = toolTip;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/icon/page.jsp";

	private String _image;
	private String _imageHover;
	private String _message;
	private String _src;
	private String _srcHover;
	private String _url;
	private String _method;
	private String _target = "_self";
	private boolean _label;
	private boolean _toolTip = true;
	private String _cssClass;

}