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

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * <a href="SearchContainerColumnJSPTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class SearchContainerColumnJSPTag
	extends SearchContainerColumnTag {

	public int doEndTag() {
		try {
			SearchContainerRowTag parentTag =
				(SearchContainerRowTag)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow row = parentTag.getRow();

			if (_index <= -1) {
				_index = row.getEntries().size();
			}

			row.addJSP(
				_index, getAlign(), getValign(), getColspan(), getPath(),
				pageContext.getServletContext(), getServletRequest(),
				getServletResponse());

			return EVAL_PAGE;
		}
		finally {
			_align = SearchEntry.DEFAULT_ALIGN;
			_colspan = SearchEntry.DEFAULT_COLSPAN;
			_index = -1;
			_name = StringPool.BLANK;
			_path = null;
			_valign = SearchEntry.DEFAULT_VALIGN;
		}
	}

	public int doStartTag() throws JspException {
		SearchContainerRowTag parentRowTag =
			(SearchContainerRowTag)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (parentRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!parentRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = parentRowTag.getHeaderNames();

			headerNames.add(_name);
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	private String _path;

}