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

package com.liferay.portlet.communities.search;

import com.liferay.portal.kernel.dao.search.RowChecker;

import javax.portlet.RenderResponse;

public class ExportPageChecker extends RowChecker {

	public ExportPageChecker(RenderResponse renderResponse) {
		super(renderResponse);
	}

	public ExportPageChecker(
		RenderResponse renderResponse, String align, String valign,
		String formName, String allRowsId, String rowId) {

		super(
			renderResponse, align, valign, COLSPAN, formName, allRowsId, rowId);
	}

	public ExportPageChecker(
		RenderResponse renderResponse, String align, String valign, int colspan,
		String formName, String allRowsId, String rowId) {

		super(
			renderResponse, align, valign, colspan, formName, allRowsId, rowId);
	}

	public boolean isChecked(Object obj) {
		return true;
	}

}